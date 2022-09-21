package com.haoyang.workbench.service.tran;

import com.haoyang.workbench.mapper.tran.TranRemarkMapper;
import com.haoyang.workbench.pojo.TranRemark;

import java.util.List;

/**
 * @author hao yang
 * @date 2022-08-11-20:26
 */
public interface TranRemarkService {

    List<TranRemark> queryTranRemarkForDetailByTranId(String id);

    int saveCreateTranRemark(TranRemark tranRemark);

    int deleteTranRemarkById(String id);

    int saveEditTranRemark(TranRemark tranRemark);
}
