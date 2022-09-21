package com.haoyang.workbench.service.clue;

import com.haoyang.workbench.pojo.ClueRemark;

import java.util.List;

/**
 * @author hao yang
 * @date 2022-08-10-10:15
 */
public interface ClueRemarkService {

    /**
     * 根据线索id查询该线索的所有备注详细信息
     * @param clueId 线索id
     * @return 备注列表
     */
    List<ClueRemark> queryClueRemarkForDetailByClueId(String clueId);

    /**
     * 添加线索备注
     * @param clueRemark 线索备注
     * @return 添加的条数
     */
    int saveCreateClueRemark(ClueRemark clueRemark);

    /**
     * 通过线索备注的id删除线索备注
     * @param id 线索备注的id
     * @return 删除的条数
     */
    int deleteClueRemarkById(String id);

    /**
     * 更新线索备注
     * @param clueRemark 更新的线索备注
     * @return 更新的条数
     */
    int saveEditClueRemark(ClueRemark clueRemark);
}
