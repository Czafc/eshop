package com.czaeshop.serviceImpl;

import com.czaeshop.dao.CategoryDao;
import com.czaeshop.pojo.Category;
import com.czaeshop.pojo.ServerName;
import com.czaeshop.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService {
    @Resource
    CategoryDao categoryDao;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categoryLevel0 = categoryDao.getCategoriesOfLevel0();
        for(Category cate0 : categoryLevel0) {
            List<Category> categoryLevel1 = categoryDao.getCategoriesByParentId(cate0.getCatId());
            for(Category cate1 : categoryLevel1) {
                List<Category> categoryLevel2 = categoryDao.getCategoriesByParentId(cate1.getCatId());
                for(Category cate2: categoryLevel2) {
                    cate2.setCatIcon(ServerName.access().getServerName() + cate2.getCatIcon());
                }
                cate1.setChildren(categoryLevel2);
            }
            cate0.setChildren(categoryLevel1);
        }
        return categoryLevel0;
    }
}
