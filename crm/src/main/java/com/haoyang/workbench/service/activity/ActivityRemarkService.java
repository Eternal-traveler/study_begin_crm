package com.haoyang.workbench.service.activity;

import com.haoyang.workbench.pojo.ActivityRemark;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hao yang
 * @date 2022-08-07-15:54
 */

public interface ActivityRemarkService {

    /**
     * 根据市场活动id查询该市场活动的所有备注
     * @param activityId 市场活动id
     * @return 备注列表
     */
    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId);

    /**
     * 添加市场活动备注
     * @param activityRemark 市场活动备注
     * @return 添加的条数
     */
    int saveActivityRemark(ActivityRemark activityRemark);


    /**
     * 根据备注id删除市场活动备注
     * @param id 备注的id
     * @return 删除的条数
     */
    int deleteActivityRemarkById(String id);

    /**
     * 更新市场活动备注
     * @param activityRemark 更新的市场活动备注
     * @return 更新的条数
     */
    int saveEditActivityRemark(ActivityRemark activityRemark);
}
