package com.example.aicaiframework.demos.entity.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenglu
 * @date 2024/6/17
 */
@Data
public class SortField implements Serializable {

    @ApiModelProperty("排序字段名称")
    private String fieldName;

    @ApiModelProperty("是否升序")
    private Integer ascending;


}
