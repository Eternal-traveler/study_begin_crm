package com.haoyang.workbench.service.tran;

import com.haoyang.workbench.pojo.TranHistory;

import java.util.List;

/**
 * @author hao yang
 * @date 2022-08-11-20:26
 */
public interface TranHistoryService {
    List<TranHistory> queryTranHistoryForDetailByTranId(String id);
}
