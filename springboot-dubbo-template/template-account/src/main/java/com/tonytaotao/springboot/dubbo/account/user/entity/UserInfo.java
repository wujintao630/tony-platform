package com.tonytaotao.springboot.dubbo.account.user.entity;

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
@ApiModel(description = "用户")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
