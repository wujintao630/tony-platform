package com.tonytaotao.springboot.dubbo.order.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author tonytaotao
 * @since 2019-10-25
 */
@ApiModel(description = "订单")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserOrder extends Model<UserOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
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
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private Double totalMoney;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
