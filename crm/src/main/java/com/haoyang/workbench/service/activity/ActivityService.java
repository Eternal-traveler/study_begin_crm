package com.haoyang.workbench.service.activity;

import com.haoyang.workbench.pojo.Activity;
import com.haoyang.workbench.pojo.FunnelVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hao yang
 * @date 2022-08-02-18:52
 */

public interface ActivityService {

    /**
     * 新增添加的市场活动
     * @param activity 创建的市场活动
     * @return 插入数据的条数
     */
    int saveCreateActivity(Activity activity);

    /**
     * 根据条件分页查询市场活动列表
     * @param map 查询条件
     * @return 查询到的市场活动
     */
    List<Activity> queryActivityByConditionForPage(Map<String, Object> map);

    /**
     * 根据条件查询市场活动总条数
     * @param map 查询条件
     * @return 市场活动总条数
     */
    int queryCountOfActivityByCondition(Map<String, Object> map);

    /**
     * 根据id批量删除市场活动参数
     * @param ids id信息
     * @return
     */
    void deleteActivity(String[] ids);

    /**
     * 根据id查询市场活动
     * @param id 市场活动id
     * @return 市场活动
     */
    Activity queryActivityById(String id);

    /**
     * 修改市场活动
     * @param activity 要修改的市场活动
     * @return 修改的条数
     */
    int saveEditActivity(Activity activity);

    /**
     * 查询所有市场活动
     * @return 返回市场活动集合
     */
    List<Activity> queryAllActivity();

    /**
     * 查询根据id选择到的市场活动
     * @param id 被选中的市场活动id
     * @return 选择的市场活动
     */
    List<Activity> queryCheckedActivity(String[] id);

    /**
     * 批量新增创建的市场活动
     * @param activityList 市场活动集合
     * @return 新增的条数
     */
//    int saveActivityByList(List<Activity> activityList);

    /**
     * 根据id查找对应市场活动明细
     * @param id 对应id
     * @return 市场活动明细
     */
    Activity queryActivityForDetailById(String id);

    /**
     * 根据clueId查询线索相关联的市场活动信息
     * @param clueId 线索对应的id
     * @return 线索对应市场活动集合
     */
    List<Activity> queryActivityForDetailByClueId(String clueId);

    /**
     * 通过市场活动名和线索id模糊查询市场活动（线索转换查询未绑定的市场活动）
     * 根据name模糊查询市场活动，并且把已经跟clueId关联过的市场活动排除
     * @param map 封装的线索id和市场活动名参数
     * @return 市场活动集合
     */
    List<Activity> queryActivityForDetailByNameAndClueId(Map<String , Object> map);

    /**
     * 根据ids查询市场活动的信息
     * 关联成功之后，为了刷新已经关联过的市场活动列表，调用该方法
     * @param ids 市场活动id
     * @return 市场活动集合
     */
    List<Activity> queryActivityForDetailByIds(String[] ids);

    /**
     * 通过市场活动名和线索id模糊查询市场活动
     * @param map 封装的线索id和市场活动名参数
     * @return 市场活动集合
     */
    List<Activity> queryActivityForConvertByNameAndClueId(Map<String, Object> map);

    List<Activity> queryActivityByFuzzyName(String activityName);

    List<FunnelVO> queryCountOfActivityGroupByOwner();

}
