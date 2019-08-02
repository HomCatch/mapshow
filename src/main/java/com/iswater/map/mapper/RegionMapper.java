package com.iswater.map.mapper;

import java.util.List;

import com.iswater.map.pojos.Region;

public interface RegionMapper {
    int deleteByPrimaryKey(Integer regionId);

    int insert(Region record);

    int insertSelective(Region record);
    
    public List<Region> selectAll();
    
    public Region selectByName(String name);

    public Region selectSumAll();
    
    Region selectByPrimaryKey(Integer regionId);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);
}