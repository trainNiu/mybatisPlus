package com.example.aicaiframework.demos.entity.base;


import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 自定义MyBatis Plus BaseMapper扩展类,用于自定义通用Mapper方法的扩展
 * @author zhaohl
 * @since 2021-11-29 10:17:13
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 通过构造多Values形式实现批量新增操作,仅适用于MySQL数据库
     * @param entityList
     * @return
     */
    int insertBatchSomeColumn(Collection<T> entityList);

    /**
     * 根据ID更新固定的字段,不包含逻辑删除
     * @param entity
     * @return
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);

    /**
     * 插入数据，如果中已经存在相同的记录，则忽略当前新数据
     * {@link com.whzp.framework.config.methods.InsertIgnore}
     * @param entity 实体类
     * @return 影响条数
     */
    int insertIgnore(T entity);

    /**
     * 批量插入数据，如果中已经存在相同的记录，则忽略当前新数据
     * {@link com.whzp.framework.config.methods.InsertIgnoreBatch}
     * @param entityList 实体类列表
     * @return 影响条数
     */
    int insertIgnoreBatch(List<T> entityList);
}
