package com.example.aicaiorder.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.aicaiframework.demos.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
@TableName("order")
@ApiModel(value = "OrderEntity对象", description = "")
public class OrderEntity extends BaseEntity<OrderEntity> {

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("订单编号")
    @TableField("order_num")
    private String orderNum;

    @TableField("amount")
    private BigDecimal amount;
}
