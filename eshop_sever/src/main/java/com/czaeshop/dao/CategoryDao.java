package com.czaeshop.dao;

import com.czaeshop.pojo.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getCategoriesOfLevel0();
    List<Category> getCategoriesByParentId(int pid);
}
