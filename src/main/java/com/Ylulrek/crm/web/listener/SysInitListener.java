package com.Ylulrek.crm.web.listener;

import com.Ylulrek.crm.settings.domain.DicValue;
import com.Ylulrek.crm.settings.service.DicService;
import com.Ylulrek.crm.settings.service.impl.DicServiceImpl;
import com.Ylulrek.crm.utils.ServiceFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysInitListener implements ServletContextListener {
    @Override
    //监听上下文域对象创建的方法，服务器启动时创建
    public void contextInitialized(ServletContextEvent event) {
        //System.out.println("上下文与对象创建了");
        System.out.println("服务器缓存处理数据字典开始");
        ServletContext appliaction = event.getServletContext();
        DicService ds= (DicService) ServiceFactory.getService(new DicServiceImpl());
        Map<String, List<DicValue>> map= ds.getAll();
        Set<String> set = map.keySet();
        for (String key:set){
            appliaction.setAttribute(key,map.get(key));
        }
        System.out.println("服务器缓存处理数据字典结束");
    }
}
