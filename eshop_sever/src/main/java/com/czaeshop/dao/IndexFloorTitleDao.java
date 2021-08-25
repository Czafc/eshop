package com.czaeshop.dao;

import com.czaeshop.pojo.IndexFloorTitle;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IndexFloorTitleDao {
    List<IndexFloorTitle> getTitlesOfFloors();
}
