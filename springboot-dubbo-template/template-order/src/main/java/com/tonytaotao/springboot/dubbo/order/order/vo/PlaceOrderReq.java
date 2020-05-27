package com.tonytaotao.springboot.dubbo.order.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.checkerframework.checker.units.qual.min;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author tonytaotao
 * @since 2019-10-25
 */
@ApiModel(description = "下订单请求")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PlaceOrderReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "{param.null}")
    private Long userId;
    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private Long commodityId;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private Integer quantity;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(min = 5, max = 25, message = "{param.length}")
    private String remark;

}
