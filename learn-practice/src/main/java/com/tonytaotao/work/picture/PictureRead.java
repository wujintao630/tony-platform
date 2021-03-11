package com.tonytaotao.work.picture;

import com.alibaba.excel.annotation.ExcelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class PictureRead implements Serializable {

    @ExcelProperty(index = 0)
    private String productCode;

    @ExcelProperty(index = 1)
    private String pictureVersion;

    @ExcelProperty(index = 2)
    private String pictureUrl;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("productCode", productCode)
                .append("pictureVersion", pictureVersion)
                .append("pictureUrl", pictureUrl)
                .toString();
    }
}
