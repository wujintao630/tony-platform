package com.tonytaotao.work.picture;

import com.alibaba.excel.annotation.ExcelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class PictureWrite implements Serializable {

    @ExcelProperty(value = "商品编码",index = 0)
    private String productCode;

    @ExcelProperty(value = "图片版本号",index = 1)
    private String pictureVersion;

    @ExcelProperty(value = "图片地址",index = 2)
    private String pictureUrl;

    @ExcelProperty(value = "图片宽度",index = 3)
    private Integer widthSize;

    @ExcelProperty(value = "图片高度",index = 4)
    private Integer heightSize;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPictureVersion() {
        return pictureVersion;
    }

    public void setPictureVersion(String pictureVersion) {
        this.pictureVersion = pictureVersion;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getWidthSize() {
        return widthSize;
    }

    public void setWidthSize(Integer widthSize) {
        this.widthSize = widthSize;
    }

    public Integer getHeightSize() {
        return heightSize;
    }

    public void setHeightSize(Integer heightSize) {
        this.heightSize = heightSize;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("productCode", productCode)
                .append("pictureVersion", pictureVersion)
                .append("pictureUrl", pictureUrl)
                .append("widthSize", widthSize)
                .append("heightSize", heightSize)
                .toString();
    }
}
