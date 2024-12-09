package com.example.aicaiframework.demos.entity.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 自定义MP通用Service扩展接口实现类,封装自定义的通用业务操作方法,所有业务Service实现类均继承该类
 * @author zhaohl
 * @since 2021-11-29 13:18:44
 */
@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements BaseService<T> {

    private static final int BATCH_SIZE = 1000;

    /**
     * 扩展批量新增操作,仅适用于MySQL数据库,此外需要注意以下几点:
     * 1. 会造成数据库默认值无法自动填充
     * 2. 因为批量提交会导致无法回填主键字段
     * 3. 适合用于数据量不是特别大的场景,如果数据量过大,则建议使用 {@link #saveBatch(Collection)}
     * 4. 当不指定分片大小时,默认按 1000条 每批次执行
     * @param entityList 实体集合
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertBatch(Collection<T> entityList) {
        return insertBatch(entityList,DEFAULT_BATCH_SIZE);
    }

    /**
     * 扩展批量新增操作,仅适用于MySQL数据库,此外需要注意以下几点:
     * 1. 会造成数据库默认值无法自动填充
     * 2. 因为批量提交会导致无法回填主键字段
     * 3. 适合用于数据量不是特别大的场景,如果数据量过大,则建议使用 {@link #saveBatch(Collection)}
     * 4. 当不指定分片大小时,默认按 1000条 每批次执行
     * @param entityList 实体集合
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertBatch(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            return false;
        }
        int size = entityList.size();
        int limit = (size + batchSize - 1) / batchSize;
        log.info("本次数据新增将分 " + limit + " 次执行");
        return SqlHelper.executeBatch(this.entityClass,super.log,sqlSession -> {
            Stream.iterate(0,n -> n + 1).limit(limit).forEach(i -> {
                List<T> entities = entityList.stream().skip(i * batchSize).limit(batchSize).collect(Collectors.toList());
                baseMapper.insertBatchSomeColumn(entities);
            });
        });
    }

    /**
     * 插入数据，如果中已经存在相同的记录，则忽略当前新数据
     * @param entity 实体类
     * @return
     */
    @Override
    public boolean insertIgnore(T entity) {
        return SqlHelper.retBool(baseMapper.insertIgnore(entity));
    }

    /**
     * 批量插入数据，如果中已经存在相同的记录，则忽略当前新数据
     * @param entityList 实体类列表
     * @return
     */
    @Override
    public boolean insertIgnoreBatch(List<T> entityList) {
        return this.insertIgnoreBatch(entityList,BATCH_SIZE);
    }

    /**
     * 批量插入数据，如果中已经存在相同的记录，则忽略当前新数据
     * @param entityList 实体类列表
     * @param batchSize 批次大小
     * @return
     */
    @Override
    public boolean insertIgnoreBatch(List<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            return false;
        }
        int size = entityList.size();
        int limit = (size + batchSize - 1) / batchSize;
        return SqlHelper.executeBatch(this.entityClass,super.log,sqlSession -> {
            Stream.iterate(0,n -> n + 1).limit(limit).forEach(i -> {
                List<T> entities = entityList.stream().skip(i * batchSize).limit(batchSize).collect(Collectors.toList());
                baseMapper.insertIgnoreBatch(entities);
            });
        });
    }

    /**
     * 根据非主键字段条件批量更新
     * @param entityList 实体列表
     * @param queryWrapperFunction 条件构造器
     * @param batchSize 批次大小
     * @return
     */
    @Override
    public boolean updateBatchByQueryWrapper(Collection<T> entityList, Function<T, Wrapper<T>> queryWrapperFunction, int batchSize) {
        String sqlStatement = this.getSqlStatement(SqlMethod.UPDATE);
        return SqlHelper.executeBatch(this.entityClass,super.log,entityList,batchSize,(sqlSession, entity) -> {
            MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY,entity);
            param.put(Constants.WRAPPER,queryWrapperFunction.apply(entity));
            sqlSession.update(sqlStatement,param);
        });
    }

    /**
     * 根据非主键字段条件批量更新
     * @param entityList 实体列表
     * @param queryWrapperFunction 条件构造器
     * @return
     */
    @Override
    public boolean updateBatchByQueryWrapper(Collection<T> entityList, Function<T, Wrapper<T>> queryWrapperFunction) {
        return this.updateBatchByQueryWrapper(entityList,queryWrapperFunction,DEFAULT_BATCH_SIZE);
    }

    protected Log getLog() {
        return super.log;
    }
}
