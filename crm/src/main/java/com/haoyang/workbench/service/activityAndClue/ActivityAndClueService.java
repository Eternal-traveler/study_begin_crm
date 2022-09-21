package com.haoyang.workbench.service.activityAndClue;

import com.haoyang.workbench.pojo.ClueActivityRelation;

import java.util.List;

/**
 * @author hao yang
 * @date 2022-08-10-16:43
 */


public interface ActivityAndClueService {

    /**
     * 批量保存线索和市场活动的关联关系
     * @param clueActivityRelationList 关联关系集合
     * @return int
     */
    int saveCreateClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelationList);

    /**
     * 通过市场活动id和线索id删除两者间的关系（绑定的市场活动）
     * @param clueActivityRelation 线索和市场活动的关联关系
     * @return 删除条数
     */
    int deleteClueActivityRelationByClueIdAndActivityId(ClueActivityRelation clueActivityRelation);
}
