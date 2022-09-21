package com.haoyang.workbench.service.activity.Impl;

import com.haoyang.workbench.mapper.activity.ActivityRemarkMapper;
import com.haoyang.workbench.pojo.ActivityRemark;
import com.haoyang.workbench.service.activity.ActivityRemarkService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hao yang
 * @date 2022-08-07-15:53
 */

@Service("activityRemarkService")
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    private final ActivityRemarkMapper activityRemarkMapper;

    public ActivityRemarkServiceImpl(ActivityRemarkMapper activityRemarkMapper) {
        this.activityRemarkMapper = activityRemarkMapper;
    }

    @Override
    public List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId) {
        return activityRemarkMapper.selectActivityRemarkForDetailByActivityId(activityId);
    }

    @Override
    public int saveActivityRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.insertActivityRemark(activityRemark);
    }

    @Override
    public int deleteActivityRemarkById(String id) {
        return activityRemarkMapper.deleteActivityRemarkById(id);
    }

    @Override
    public int saveEditActivityRemark(ActivityRemark activityRemark) {
        return activityRemarkMapper.updateActivityRemark(activityRemark);
    }
}
