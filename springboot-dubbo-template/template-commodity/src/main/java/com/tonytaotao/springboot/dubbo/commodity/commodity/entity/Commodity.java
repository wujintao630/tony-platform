package com.tonytaotao.springboot.dubbo.commodity.commodity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author tonytaotao
 * @since 2019-10-25
 */
@ApiModel(description = "商品")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Commodity extends Model<Commodity> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;
    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    /**
     * 库存数量
     */
    @ApiModelProperty(value = "库存数量")
    private Integer stockAmount;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
