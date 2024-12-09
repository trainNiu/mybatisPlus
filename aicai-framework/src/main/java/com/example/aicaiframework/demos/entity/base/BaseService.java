package com.example.aicaiframework.demos.entity.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * 自定义MP通用Service扩展接口,封装自定义的通用业务操作方法
 * @author zhaohl
 * @since 2021-11-29 13:18:17
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 扩展批量新增操作,仅适用于MySQL数据库,此外需要注意以下几点:
     * 1. 会造成数据库默认值无法自动填充
     * 2. 因为批量提交会导致无法回填主键字段
     * 3. 适合用于数据量不是特别大的场景,如果数据量过大,则建议使用 {@link #saveBatch(Collection)}
     * 4. 当不指定分片大小时,默认按 1000条 每批次执行
     * @param entityList 实体集合
     * @return
     */
    boolean insertBatch(Collection<T> entityList);

    /**
     * 扩展批量新增操作,仅适用于MySQL数据库,此外需要注意以下几点:
     * 1. 会造成数据库默认值无法自动填充
     * 2. 因为批量提交会导致无法回填主键字段
     * 3. 适合用于数据量不是特别大的场景,如果数据量过大,则建议使用 {@link #saveBatch(Collection)}
     * 4. 当不指定分片大小时,默认按 1000条 每批次执行
     * @param entityList 实体集合
     * @param batchSize 分片大小
     * @return
     */
    boolean insertBatch(Collection<T> entityList,int batchSize);

    /**
     * 插入数据，如果中已经存在相同的记录，则忽略当前新数据
     * @param entity 实体类
     * @return 影响条数
     */
    boolean insertIgnore(T entity);

    /**
     * 批量插入数据，如果中已经存在相同的记录，则忽略当前新数据
     * @param entityList 实体类列表
     * @return 影响条数
     */
    boolean insertIgnoreBatch(List<T> entityList);

    /**
     * 批量插入数据，如果中已经存在相同的记录，则忽略当前新数据
     * @param entityList 实体类列表
     * @param batchSize 批次大小
     * @return 影响条数
     */
    boolean insertIgnoreBatch(List<T> entityList,int batchSize);

    /**
     * 根据非主键字段条件批量更新
     * @param entityList 实体列表
     * @param queryWrapperFunction 条件构造器
     * @param batchSize 批次大小
     * @return
     */
    boolean updateBatchByQueryWrapper(Collection<T> entityList, Function<T, Wrapper<T>> queryWrapperFunction, int batchSize);

    /**
     * 根据非主键字段条件批量更新
     * @param entityList 实体列表
     * @param queryWrapperFunction 条件构造器
     * @return
     */
    boolean updateBatchByQueryWrapper(Collection<T> entityList, Function<T, Wrapper<T>> queryWrapperFunction);
}
