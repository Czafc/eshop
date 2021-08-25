package com.czaeshop.serviceImpl;

import com.czaeshop.dao.NavigatorIconDao;
import com.czaeshop.pojo.NavigatorIcon;
import com.czaeshop.pojo.ServerName;
import com.czaeshop.service.NavigatorIconService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("NavigatorIconService")
public class NavigatorIconServiceImpl implements NavigatorIconService {
    @Resource
    private NavigatorIconDao navigatorIconDao;

    @Override
    public List<NavigatorIcon> getNavigatorIcons() {
        List<NavigatorIcon> resList = navigatorIconDao.getNavigatorIcons();
        for(NavigatorIcon navigatorIcon : resList) {
            navigatorIcon.setIconURL(ServerName.access().getServerName() + navigatorIcon.getIconURL());
        }
        return resList;
    }
}
