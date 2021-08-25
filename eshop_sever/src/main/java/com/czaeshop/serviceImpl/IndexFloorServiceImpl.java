package com.czaeshop.serviceImpl;

import com.czaeshop.dao.IndexFloorProductDao;
import com.czaeshop.dao.IndexFloorTitleDao;
import com.czaeshop.pojo.IndexFloor;
import com.czaeshop.pojo.IndexFloorProduct;
import com.czaeshop.pojo.IndexFloorTitle;
import com.czaeshop.pojo.ServerName;
import com.czaeshop.service.IndexFloorsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service("IndexFloorService")
public class IndexFloorServiceImpl implements IndexFloorsService {
    @Resource
    private IndexFloorTitleDao indexFloorTitleDao;
    @Resource
    private IndexFloorProductDao indexFloorProductDao;

    public List<IndexFloor> getFloors() {
        //服务器域名（以及端口号，本地测试为localhost:8080）
        String serverName = ServerName.access().getServerName();
        //创建结果列表
        List<IndexFloor> indexFloorList = new ArrayList<>();
        //查询出所有楼层标题
        List<IndexFloorTitle> indexFloorTitleList = indexFloorTitleDao.getTitlesOfFloors();
        //根据楼层数查询商品信息
        int numOfFloor = 1;

        for(IndexFloorTitle indexFloorTitle : indexFloorTitleList) {
            indexFloorTitle.setImgURL(serverName + indexFloorTitle.getImgURL());
            //新建一个楼层
            IndexFloor indexFloor = new IndexFloor();
            //设置楼层主题
            indexFloor.setIndexFloorTitle(indexFloorTitle);
            //为这个楼层设置商品列表
            List<IndexFloorProduct> productList = indexFloorProductDao.getProductsOfFloor(numOfFloor);
            for(IndexFloorProduct indexFloorProduct : productList) {
                indexFloorProduct.setImgURL(serverName + indexFloorProduct.getImgURL());
            }
            indexFloor.setIndexFloorProductList(productList);

            numOfFloor += 1;

            indexFloorList.add(indexFloor);
        }

        return indexFloorList;
    }
}
