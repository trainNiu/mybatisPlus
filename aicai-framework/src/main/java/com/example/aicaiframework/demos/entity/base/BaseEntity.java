package com.example.aicaiframework.demos.entity.base;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.aicaiframework.demos.constant.DateFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mengyu
 * @title: BaseEntity
 * @description: 基础对象
 * @date 2021/2/310:15
 */
@Getter
@Setter
public abstract class BaseEntity<T extends BaseEntity<T>> extends Model<T> {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "状态：1 有效，0 无效")
    @TableField(value = "is_active",fill = FieldFill.INSERT)
    @TableLogic(value = "1",delval = "0")
    private Integer isActive;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonFormat(pattern = DateFormat.DATE_TIME, timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建人ID")
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private Long createBy;

    @ApiModelProperty(value = "创建人姓名")
    @TableField(value = "create_by_name",fill = FieldFill.INSERT)
    private String createByName;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    @JsonFormat(pattern = DateFormat.DATE_TIME, timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "更新人ID")
    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private Long updateBy;

    @ApiModelProperty(value = "更新人姓名")
    @TableField(value = "update_by_name",fill = FieldFill.UPDATE)
    private String updateByName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    /**
     * 自定义sql添加时 使用
     * @param passport
     */
//    public void setCreated(Passport passport) {
//        this.createByName = passport != null ? passport.getUserName() : "";
//        this.createBy = passport != null && passport.getUserId()!=null ? passport.getUserId() : NumberEnum.ZERO.longValue();
//        this.createTime = new Date();
//        this.updateByName = this.createByName;
//        this.updateBy = this.createBy;
//        this.updateTime = this.createTime;
//    }
//
//    /**
//     * 自定义sql添加时 使用
//     * @param passport
//     */
//    public void setModified(Passport passport) {
//        this.updateByName = passport != null ? passport.getUserName() : "";
//        this.updateBy = passport != null && passport.getUserId()!=null ? passport.getUserId() : NumberEnum.ZERO.longValue();
//        this.updateTime = new Date();
//    }
}
