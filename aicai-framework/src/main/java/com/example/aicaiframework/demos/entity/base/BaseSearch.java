package com.example.aicaiframework.demos.entity.base;

import cn.hutool.core.util.StrUtil;
import com.whzp.framework.utils.SortField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询对象
 *
 * @author 小鱼
 */
@Data
public class BaseSearch implements Serializable {

    @ApiModelProperty("默认模糊搜索框")
    private String keyWord;

    @ApiModelProperty("默认模糊搜索框")
    private String keyword;

    @ApiModelProperty("排序字段")
    private List<SortField> sortFields;

    public String getKeyWord() {
        this.keyWord = StrUtil.isNotBlank(this.keyword) ? this.keyword : this.keyWord;
        return keyWord;
    }
}
