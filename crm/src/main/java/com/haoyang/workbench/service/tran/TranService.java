package com.haoyang.workbench.service.tran;

import com.haoyang.workbench.pojo.FunnelVO;
import com.haoyang.workbench.pojo.Tran;

import java.util.List;
import java.util.Map;

/**
 * @author hao yang
 * @date 2022-08-11-20:26
 */
public interface TranService {
    /**
     * 根据条件分页查询客户列表
     * @param map 查询条件
     * @return 查询到的客户
     */
    List<Tran> queryTransactionByConditionForPage(Map<String, Object> map);

    /**
     * 根据条件查询客户总条数
     * @param map 查询条件
     * @return 客户总条数
     */
    int queryCountOfTransactionByCondition(Map<String, Object> map);

    /**
     * 保存创造的交易
     * @param tran 交易信息
     */
    void saveCreateTransaction(Tran tran);

    /**
     * 根据id查询交易信息，这里为修改获取交易信息
     * @param id 交易id
     * @return 交易信息
     */
    Tran queryTransactionById(String id);

    /**
     * 保存修改的交易信息
     * @param tran 交易信息
     */
    void saveEditTransaction(Tran tran);

    /**
     * 根据id删除交易
     * @param ids 交易id集合
     */
    void deleteTranByIds(String[] ids);

    /**
     * 根据id查询交易备注表信息
     * @param id
     * @return
     */
    Tran queryTranForDetailById(String id);

    List<FunnelVO> queryCountOfTranGroupByStage();

}
