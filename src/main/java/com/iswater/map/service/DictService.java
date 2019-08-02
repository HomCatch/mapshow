package com.iswater.map.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iswater.map.mapper.DictMapper;
import com.iswater.map.pojos.Dict;

@Service
public class DictService {

	@Autowired
	private DictMapper dictMapper;
	
	
	public List<Dict> getDictList(String dictName){
		return dictMapper.selectByName(dictName);
	}
	
	public Dict getDict(String dictCode){
		return dictMapper.selectByCode(dictCode);
	}
}
