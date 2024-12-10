package com.example.aicaimp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.aicaiframework.demos.entity.base.BaseEntity;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author nh
 * @since 2024-12-10
 */
@Getter
@Setter
@TableName("user")
@ApiModel(value = "UserEntity对象", description = "")
public class UserEntity extends BaseEntity<UserEntity> {

    @TableField("username")
    private String username;

    @TableField("birthdate")
    private Date birthdate;

}
