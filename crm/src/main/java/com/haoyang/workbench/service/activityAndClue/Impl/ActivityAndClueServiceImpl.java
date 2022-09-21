package com.haoyang.workbench.service.activityAndClue.Impl;

import com.haoyang.workbench.mapper.activityAndClue.ClueActivityRelationMapper;
import com.haoyang.workbench.pojo.ClueActivityRelation;
import com.haoyang.workbench.service.activityAndClue.ActivityAndClueService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hao yang
 * @date 2022-08-10-16:44
 */

@Service("activityAndClueService")
public class ActivityAndClueServiceImpl implements ActivityAndClueService {

    private final ClueActivityRelationMapper clueActivityRelationMapper;

    public ActivityAndClueServiceImpl(ClueActivityRelationMapper clueActivityRelationMapper) {
        this.clueActivityRelationMapper = clueActivityRelationMapper;
    }

    @Override
    public int saveCreateClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelationList) {
        return clueActivityRelationMapper.insertClueActivityRelationByList(clueActivityRelationList);
    }

    @Override
    public int deleteClueActivityRelationByClueIdAndActivityId(ClueActivityRelation clueActivityRelation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByClueIdAndActivityId(clueActivityRelation);
    }
}
