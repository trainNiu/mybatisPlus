package com.example.aicaiuser.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.aicaiframework.demos.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author nh
 * @since 2024-12-30
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
