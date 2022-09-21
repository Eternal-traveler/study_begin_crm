package com.haoyang.settings.service.Impl;

import com.haoyang.settings.mapper.DicValueMapper;
import com.haoyang.settings.pojo.DicValue;
import com.haoyang.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hao yang
 * @date 2022-08-08-16:19
 */

@Service("dicValueService")
public class DicValueServiceImpl implements DicValueService {

    private final DicValueMapper dicValueMapper;

    public DicValueServiceImpl(DicValueMapper dicValueMapper) {
        this.dicValueMapper = dicValueMapper;
    }


    @Override
    public List<DicValue> queryDicValueByTypeCode(String typeCode) {
        return dicValueMapper.selectDicValueByTypeCode(typeCode);
    }

    @Override
    public DicValue queryDicValueById(String id) {
        return  dicValueMapper.selectDicValueById(id);
    }
}
