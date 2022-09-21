package com.haoyang.workbench.service.tran.Impl;

import com.haoyang.commons.utils.DateUtils;
import com.haoyang.commons.utils.UUIDUtils;
import com.haoyang.workbench.mapper.customer.CustomerMapper;
import com.haoyang.workbench.mapper.tran.TranHistoryMapper;
import com.haoyang.workbench.mapper.tran.TranMapper;
import com.haoyang.workbench.pojo.Customer;
import com.haoyang.workbench.pojo.FunnelVO;
import com.haoyang.workbench.pojo.Tran;
import com.haoyang.workbench.pojo.TranHistory;
import com.haoyang.workbench.service.tran.TranService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hao yang
 * @date 2022-08-11-20:28
 */

@Service("tranService")
public class TranServiceImpl implements TranService {
    private final TranMapper tranMapper;

    private final CustomerMapper customerMapper;

    private final TranHistoryMapper tranHistoryMapper;

    public TranServiceImpl(TranMapper tranMapper, CustomerMapper customerMapper, TranHistoryMapper tranHistoryMapper) {
        this.tranMapper = tranMapper;
        this.customerMapper = customerMapper;
        this.tranHistoryMapper = tranHistoryMapper;
    }

    @Override
    public List<Tran> queryTransactionByConditionForPage(Map<String, Object> map) {
        return tranMapper.selectTransactionByConditionForPage(map);
    }

    @Override
    public int queryCountOfTransactionByCondition(Map<String, Object> map) {
        return tranMapper.selectCountOfTransactionByCondition(map);
    }

    @Override
    public void saveCreateTransaction(Tran tran) {
        // 获取前端传来的用户对应的id（前端传来的是用户名称：tran.getCustomerId()，而数据库需要存放该用户的id）
        String customerId = customerMapper.selectCustomerIdByName(tran.getCustomerId());
        // 如果存在该用户，则将tran中的用户名改为对应的用户id
        if (customerId != null) {
            tran.setCustomerId(customerId);
        } else {
            // 不存在该用户，则新创建用户，并将tran中的用户名改为新创建的用户id
            Customer customer = new Customer();
            customer.setOwner(tran.getCreateBy());
            customer.setName(tran.getCustomerId());
            customer.setId(UUIDUtils.getUUID());
            customer.setCreateTime(DateUtils.formatDateTime(new Date()));
            customer.setCreateBy(tran.getCreateBy());
            customerMapper.insertCustomer(customer);
            // 修改联系人的用户为该用户id
            tran.setCustomerId(customer.getId());
        }
        // 新增线索
        tranMapper.insertTran(tran);
        // 新增线索历史记录
        TranHistory tranHistory = new TranHistory();
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setCreateTime(DateUtils.formatDateTime(new Date()));
        tranHistory.setTranId(tran.getId());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setId(UUIDUtils.getUUID());
        // 新增历史记录
        tranHistoryMapper.insertTransactionHistory(tranHistory);
    }

    @Override
    public Tran queryTransactionById(String id) {
        return tranMapper.selectTransactionById(id);
    }

    @Override
    public void saveEditTransaction(Tran tran) {
        tranMapper.updateTran(tran);
    }

    @Override
    public void deleteTranByIds(String[] ids) {

    }

    @Override
    public Tran queryTranForDetailById(String id) {
        return tranMapper.selectTranForDetailById(id);
    }

    @Override
    public List<FunnelVO> queryCountOfTranGroupByStage() {
        return tranMapper.selectCountOfTranGroupByStage();
    }
}
