package com.czaeshop.pojo;

import java.util.List;

public class Category {
    private int catId;
    private String catName;
    private int catPid;
    private int catLevel;
    private int catDeleted;
    private String catIcon;
    private List<Category> children;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatPid() {
        return catPid;
    }

    public void setCatPid(int catPid) {
        this.catPid = catPid;
    }

    public int getCatLevel() {
        return catLevel;
    }

    public void setCatLevel(int catLevel) {
        this.catLevel = catLevel;
    }

    public int getCatDeleted() {
        return catDeleted;
    }

    public void setCatDeleted(int catDeleted) {
        this.catDeleted = catDeleted;
    }

    public String getCatIcon() {
        return catIcon;
    }

    public void setCatIcon(String catIcon) {
        this.catIcon = catIcon;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
}
