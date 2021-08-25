package com.czaeshop.pojo;

public class ProductPicture {
    private int picId;
    private int productId;
    private String picBig;
    private String picMid;
    private String picSma;

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getPicBig() {
        return picBig;
    }

    public void setPicBig(String picBig) {
        this.picBig = picBig;
    }

    public String getPicMid() {
        return picMid;
    }

    public void setPicMid(String picMid) {
        this.picMid = picMid;
    }

    public String getPicSma() {
        return picSma;
    }

    public void setPicSma(String picSma) {
        this.picSma = picSma;
    }
}
