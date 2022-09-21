package com.haoyang.settings.service;

import com.haoyang.settings.pojo.DicValue;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hao yang
 * @date 2022-08-08-16:18
 */

public interface DicValueService {


    /**
     * 通过自定义的类型编码查询对应的字典表数据
     * @param typeCode 类型编码
     * @return 对应字典表数据
     */
    List<DicValue> queryDicValueByTypeCode(String typeCode);


    /**
     * 通过id查询对应的字典表数据
     * @param id 字典数据id
     * @return 字典表数据
     */
    DicValue queryDicValueById(String id);
}
