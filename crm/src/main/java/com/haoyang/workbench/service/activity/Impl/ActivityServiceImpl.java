package com.haoyang.workbench.service.activity.Impl;

import com.haoyang.workbench.mapper.activity.ActivityMapper;
import com.haoyang.workbench.mapper.activity.ActivityRemarkMapper;
import com.haoyang.workbench.pojo.Activity;
import com.haoyang.workbench.pojo.FunnelVO;
import com.haoyang.workbench.service.activity.ActivityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hao yang
 * @date 2022-08-02-18:53
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    private final ActivityMapper activityMapper;

    private final ActivityRemarkMapper activityRemarkMapper;

    public ActivityServiceImpl(ActivityMapper activityMapper, ActivityRemarkMapper activityRemarkMapper) {
        this.activityMapper = activityMapper;
        this.activityRemarkMapper = activityRemarkMapper;
    }


    @Override
    public int saveCreateActivity(Activity activity){
        return activityMapper.insertActivity(activity);
    }

    @Override
    public List<Activity> queryActivityByConditionForPage(Map<String,Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    @Override
    public int queryCountOfActivityByCondition(Map<String, Object> map) {
        return activityMapper.selectCountOfActivityByCondition(map);
    }

    /**
     * 通过市场活动id数组删除对应id的市场活动以及该市场活动中绑定的所有信息
     * @param ids 市场活动id数组
     * @return
     */
    @Override
    public void deleteActivity(String[] ids) {
        activityRemarkMapper.deleteActivityRemarkByActivityId(ids);
        activityMapper.deleteActivityByIds(ids);
    }

    @Override
    public Activity queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public int saveEditActivity(Activity activity) {
        return activityMapper.updateActivity(activity);
    }

    @Override
    public List<Activity> queryAllActivity(){
        return activityMapper.selectAllActivity();
    }

    @Override
    public List<Activity> queryCheckedActivity(String[] id){
        return activityMapper.selectCheckedActivity(id);
    }

//    @Override
//    public int saveActivityByList(List<Activity> activityList){
//        return activityMapper.insertActivityByList(activityList);
//    }

    @Override
    public Activity queryActivityForDetailById(String id){
        return activityMapper.selectActivityForDetailById(id);
    }

    @Override
    public List<Activity> queryActivityForDetailByClueId(String clueId) {
        return activityMapper.selectActivityForDetailByClueId(clueId);
    }

    @Override
    public List<Activity> queryActivityForDetailByNameAndClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForDetailByNameClueId(map);
    }

    @Override
    public List<Activity> queryActivityForDetailByIds(String[] ids) {
        return activityMapper.selectActivityForDetailByIds(ids);
    }

    @Override
    public List<Activity> queryActivityForConvertByNameAndClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForConvertByNameAndClueId(map);
    }

    @Override
    public List<Activity> queryActivityByFuzzyName(String activityName){
        return activityMapper.selectActivityByFuzzyName(activityName);
    }

    @Override
    public List<FunnelVO> queryCountOfActivityGroupByOwner() {
        return activityMapper.selectCountOfActivityGroupByOwner();
    }


}
