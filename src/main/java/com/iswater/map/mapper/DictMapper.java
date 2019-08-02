package com.iswater.map.mapper;

import java.util.List;

import com.iswater.map.pojos.Dict;

public interface DictMapper {
    int deleteByPrimaryKey(Integer dictId);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Integer dictId);
    
    public List<Dict> selectByName(String dictName);
    
    public Dict selectByCode(String dictCode);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);
}