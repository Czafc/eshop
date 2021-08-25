package com.czaeshop.serviceImpl;

import com.czaeshop.dao.CollectionDao;
import com.czaeshop.dao.ProductAttrDao;
import com.czaeshop.dao.ProductDao;
import com.czaeshop.dao.ProductPictureDao;
import com.czaeshop.pojo.Product;
import com.czaeshop.pojo.ProductList;
import com.czaeshop.pojo.ServerName;
import com.czaeshop.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {

    @Resource
    ProductDao productDao;
    @Resource
    ProductAttrDao productAttrDao;
    @Resource
    ProductPictureDao productPictureDao;
    @Resource
    CollectionDao collectionDao;

    @Override
    public ProductList queryProductList(int catId, int pageSize, int pageNum, int sort) {
        int from = (pageNum - 1) * pageSize;
        int to = pageNum * pageSize;
        List<Product> products = null;
        if(sort == 1) { //按销量排序
            products = productDao.queryProductListByCatIdSortBySaleNum(catId,from,to);
        }else if(sort == 2) { //按价格排序
            products = productDao.queryProductListByCatIdSortByPrice(catId,from,to);
        }else { //默认排序（即不排序）
            products = productDao.queryProductListByCatId(catId,from,to);
        }
        for(Product product : products) {
            product.setCoverImg(ServerName.access().getServerName() + product.getCoverImg());
        }
        return new ProductList(productDao.getProductNumByCatId(catId), pageNum, products);
    }

    @Override
    public ProductList queryProductList(String name,int pageSize,int pageNum, int sort) {
        int from = (pageNum - 1) * pageSize;
        int to = pageNum * pageSize;
        List<Product> products = null;
        if(sort == 1) {
            products = productDao.queryProductListByNameSortBySaleNum(name,from,to);
        }else if(sort == 2) {
            products = productDao.queryProductListByNameSortByPrice(name,from,to);
        }else {
            products = productDao.queryProductListByName(name,from,to);
        }
        if(null != products) {
            for(Product product : products) {
                product.setCoverImg(ServerName.access().getServerName() + product.getCoverImg());
            }
        }
        return new ProductList(productDao.getProductNumByName(name), pageNum, products);
    }

    @Override
    public Product getProductInfo(int productId, String userId) {
        Product product = getProductInfo(productId);
        Integer collectionItemId = collectionDao.getCollectionItemId(productId,userId);
        product.setCollected(null != collectionItemId);
        return product;
    }

    @Override
    public Product getProductInfo(int productId) {
        Product product = productDao.queryProductByProductId(productId);
        if(null == product) return null;
        product.setCoverImg(ServerName.access().getServerName() + product.getCoverImg());
        product.setProductAttributes(productAttrDao.getAttributesByProductId(productId));
        product.setProductImages(productPictureDao.getPicturesByProductId(productId));
        return product;
    }
}
