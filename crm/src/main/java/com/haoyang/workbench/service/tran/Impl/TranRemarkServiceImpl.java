package com.haoyang.workbench.service.tran.Impl;

import com.haoyang.workbench.mapper.tran.TranRemarkMapper;
import com.haoyang.workbench.pojo.TranRemark;
import com.haoyang.workbench.service.tran.TranRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hao yang
 * @date 2022-08-11-20:27
 */

@Service("tranRemarkService")
public class TranRemarkServiceImpl implements TranRemarkService {
    private final TranRemarkMapper tranRemarkMapper;

    public TranRemarkServiceImpl(TranRemarkMapper tranRemarkMapper) {
        this.tranRemarkMapper = tranRemarkMapper;
    }

    @Override
    public List<TranRemark> queryTranRemarkForDetailByTranId(String id) {
        return tranRemarkMapper.selectTranRemarkForDetailByTranId(id);
    }

    @Override
    public int saveCreateTranRemark(TranRemark tranRemark) {
        return tranRemarkMapper.insertTranRemark(tranRemark);
    }

    @Override
    public int deleteTranRemarkById(String id) {
        return tranRemarkMapper.deleteTranRemarkById(id);
    }

    @Override
    public int saveEditTranRemark(TranRemark tranRemark) {
        return tranRemarkMapper.updateTranRemark(tranRemark);
    }
}
