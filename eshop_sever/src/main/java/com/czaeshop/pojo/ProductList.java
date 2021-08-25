package com.czaeshop.pojo;

import java.util.List;

public class ProductList {
    private int total = 0;
    private int pageNum = 1;
    private List<Product> productList;

    public ProductList(){}
    public ProductList(int t, int pn, List<Product> pl) {
        this.total = t;
        this.pageNum = pn;
        this.productList = pl;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
