package com.czaeshop.pojo;

import java.util.List;

public class Product {
    private int productId; //商品id
    private int catId; //类别id
    private String productName; //商品名称
    private double price;
    private int productNum; //商品数量（库存）
    private String coverImg; //商品缩略图
    private int sales; //商品销量
    private String productIntroduction; //商品详情介绍，富文本内容
    private String seller; //出售此商品的用户id（用于二手商品）
    private List<ProductPicture> productImages; //商品图片，用于商品详情展示页面
    private List<ProductAttribute> productAttributes; //商品属性列表
    private boolean collected = false;


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getProductIntroduction() {
        return productIntroduction;
    }

    public void setProductIntroduction(String productIntroduction) {
        this.productIntroduction = productIntroduction;
    }

    public List<ProductPicture> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductPicture> productImages) {
        this.productImages = productImages;
    }

    public List<ProductAttribute> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(List<ProductAttribute> productAttributes) {
        this.productAttributes = productAttributes;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
