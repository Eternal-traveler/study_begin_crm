package com.haoyang.workbench.service.tran.Impl;

import com.haoyang.workbench.mapper.tran.TranHistoryMapper;
import com.haoyang.workbench.pojo.TranHistory;
import com.haoyang.workbench.service.tran.TranHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hao yang
 * @date 2022-08-11-20:27
 */
@Service("tranHistoryService")
public class TranHistoryServiceImpl implements TranHistoryService {
    private final TranHistoryMapper tranHistoryMapper;

    public TranHistoryServiceImpl(TranHistoryMapper tranHistoryMapper) {
        this.tranHistoryMapper = tranHistoryMapper;
    }

    @Override
    public List<TranHistory> queryTranHistoryForDetailByTranId(String id) {
        return tranHistoryMapper.selectTranHistoryForDetailByTranId(id);
    }
}
