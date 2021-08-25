package com.czaeshop.dao;

import com.czaeshop.pojo.NavigatorIcon;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NavigatorIconDao {
    List<NavigatorIcon> getNavigatorIcons();
}
