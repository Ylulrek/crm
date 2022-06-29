package com.Ylulrek.crm.workbench.service.impl;

import com.Ylulrek.crm.settings.domain.User;
import com.Ylulrek.crm.utils.DateTimeUtil;
import com.Ylulrek.crm.utils.SqlSessionUtil;
import com.Ylulrek.crm.utils.UUIDUtil;
import com.Ylulrek.crm.vo.PaginationVo;
import com.Ylulrek.crm.workbench.dao.CustomerDao;
import com.Ylulrek.crm.workbench.dao.TranDao;
import com.Ylulrek.crm.workbench.dao.TranHistoryDao;
import com.Ylulrek.crm.workbench.domain.Customer;
import com.Ylulrek.crm.workbench.domain.Tran;
import com.Ylulrek.crm.workbench.domain.TranHistory;
import com.Ylulrek.crm.workbench.service.TranService;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {
    private TranDao tranDao= SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao= SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerDao customerDao= SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public PaginationVo<Tran> pageList(Map<String, Object> map) {
        int total = tranDao.getTotalByCondition(map);

        List<Tran> dataList = tranDao.getTranByCondition(map);

        PaginationVo<Tran> vo = new PaginationVo<Tran>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public Map<String, Object> getCharts() {
        //获得Total
        int total=tranDao.getTotal();
        //获得dataLiat
        List<Map<String,Object>> dataList=tranDao.getCharts();
        //将Total与dataList保存到Map中
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",total);
        map.put("dataList",dataList);
        return map;
    }

    @Override
    public boolean save(Tran t, String customerName) {
        boolean flag=true;
        Customer cus=customerDao.getCustomerByName(customerName);
        //如果cus为空。创建客户对象
        if (cus==null){
            cus=new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setName(customerName);
            cus.setCreateTime(DateTimeUtil.getSysTime());
            cus.setCreateBy(t.getCreateBy());
            cus.setContactSummary(t.getContactSummary());
            cus.setOwner(t.getOwner());
            cus.setNextContactTime(t.getNextContactTime());
            int num1=customerDao .save(cus);
            if (num1!=1){
                flag=false;
            }
        }
        //执行交易添加操作
        String id=cus.getId();
        t.setCustomerId(id);
        int num2=tranDao.save(t);
        if (num2!=1){
            flag=false;
        }

        //添加交易历史
        TranHistory th=new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setMoney(t.getMoney());
        th.setTranId(t.getId());
        th.setStage(t.getStage());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(t.getCreateBy());
        th.setExpectedDate(t.getExpectedDate());
        int num3=tranHistoryDao.save(th);
        if (num3!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Tran detail(String id) {

        Tran t=tranDao.detail(id);

        return t;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {
        List<TranHistory> thList=tranHistoryDao.getHistoryListByTranId(tranId);
        return thList;
    }

    @Override
    public boolean changeStage(Tran t) {
        boolean flag=true;
        int num1=tranDao.changeStage(t);
        if (num1!=1){
            flag=false;
        }
        //交易阶段改变后，生成一条交易历史
        TranHistory th=new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setStage(t.getStage());
        th.setTranId(t.getId());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateBy(t.getEditBy());
        th.setCreateTime(t.getEditTime());
        int num2=tranHistoryDao.save(th);
        if (num2!=1){
            flag=false;
        }
        return flag;
    }
}
