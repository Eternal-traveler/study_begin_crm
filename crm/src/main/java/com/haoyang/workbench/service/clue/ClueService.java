package com.haoyang.workbench.service.clue;

import com.haoyang.workbench.pojo.Activity;
import com.haoyang.workbench.pojo.Clue;

import java.util.List;
import java.util.Map;

/**
 * @author hao yang
 * @date 2022-08-08-16:47
 */
public interface ClueService {

    /**
     * 新增线索
     * @param clue 新增的线索
     * @return 新增条数
     */
    int saveCreateClue(Clue clue);

    /**
     * 根据条件分页查询线索
     * @param map 查询条件
     * @return 查询到的线索
     */
    List<Clue> queryClueByConditionForPage(Map<String, Object> map);

    /**
     * 根据条件查询线索总条数
     * @param map 查询条件
     * @return 线索总条数
     */
    int queryCountOfClueByCondition(Map<String, Object> map);

    /**
     * 根据id批量删除线索参数
     * @param ids id信息
     */
    void deleteClue(String[] ids);

    /**
     * 通过id查询线索
     * @param id 线索id
     * @return 线索活动信息
     */
    Clue queryClueById(String id);

    /**
     * 修改线索
     * @param clue 要修改的线索
     * @return 修改的条数
     */
    int saveEditClue(Clue clue);

    /**
     * 通过id查询线索详情
     * @param id 线索id
     * @return 对应id的线索
     */
    Clue queryClueForDetailById(String id);

    /**
     * 线索转换功能的具体实现
     * @param map 封装的参数
     */
    void saveConvertClue(Map<String, Object> map);

    List<String> queryClueStageOfClueGroupByClueStage();

    List<Integer> queryCountOfClueGroupByClueStage();

}
