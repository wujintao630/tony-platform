package com.tonytaotao.service.common.base;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

/**
 * 分页查询实体
 * @author tonytaotao
 */
@Data
public class QueryPage<T> {

    private Integer pageSize;

    private Integer currentPage;

    private T queryEntity;

    public Page<T> getPage() {
        return new Page<>(currentPage, pageSize);
    }

}
