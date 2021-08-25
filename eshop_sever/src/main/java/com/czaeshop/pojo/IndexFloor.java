package com.czaeshop.pojo;

import java.util.List;

public class IndexFloor {
    private IndexFloorTitle indexFloorTitle;
    private List<IndexFloorProduct> indexFloorProductList;

    public IndexFloorTitle getIndexFloorTitle() {
        return indexFloorTitle;
    }

    public void setIndexFloorTitle(IndexFloorTitle indexFloorTitle) {
        this.indexFloorTitle = indexFloorTitle;
    }

    public List<IndexFloorProduct> getIndexFloorProductList() {
        return indexFloorProductList;
    }

    public void setIndexFloorProductList(List<IndexFloorProduct> indexFloorProductList) {
        this.indexFloorProductList = indexFloorProductList;
    }
}
