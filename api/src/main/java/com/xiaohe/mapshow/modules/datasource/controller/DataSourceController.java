package com.xiaohe.mapshow.modules.datasource.controller;

import com.baomidou.dynamic.datasource.DynamicDataSourceCreator;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.xiaohe.mapshow.utils.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;


/**
 * @author Gmq
 * @since 2019-07-09 15:20
 **/
@RestController
@RequestMapping("/datasource")
public class DataSourceController {
	private static Logger log = LoggerFactory.getLogger(DataSourceController.class);

	@Autowired
	private DynamicRoutingDataSource dynamicRoutingDataSource;
	@Autowired
	private DynamicDataSourceCreator createDruidDataSource;
	@Autowired
	private static RestTemplate restTemplate;

	@PostMapping("/addDs")
	public void add(){
		DataSourceProperty dataSourceProperty=new DataSourceProperty();
		dataSourceProperty.setUrl("jdbc:mysql://iotsvr.he-live.com:3309/js_saas_lisi?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull");
		dataSourceProperty.setDriverClassName("com.mysql.jdbc.Driver");
		dataSourceProperty.setUsername("root");
		dataSourceProperty.setPassword("iswater");
		DataSource druidDataSource = createDruidDataSource.createDruidDataSource(dataSourceProperty);
		dynamicRoutingDataSource.addDataSource("js_saas_lisi",druidDataSource);
		log.info("新增js_saas_lisi数据源成功！");

		log.info("当前数据源"+getCurrentDataSources());
	}
	@GetMapping("/getAllDs")
	public CommonResult getCurrentDataSources(){
		CommonResult result=new CommonResult();
		result.ok();
		result.setDatas(dynamicRoutingDataSource.getCurrentDataSources());
		return result;
	}
	@PostMapping("/addDs2")
	public void add2(){
		DataSourceProperty dataSourceProperty=new DataSourceProperty();
		dataSourceProperty.setUrl("jdbc:mysql://iotsvr.he-live.com:3309/base?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull");
		dataSourceProperty.setDriverClassName("com.mysql.jdbc.Driver");
		dataSourceProperty.setUsername("root");
		dataSourceProperty.setPassword("iswater");
		DataSource druidDataSource = createDruidDataSource.createDruidDataSource(dataSourceProperty);
		dynamicRoutingDataSource.addDataSource("base",druidDataSource);
		log.info("新增js_saas_lisi数据源成功！");

		log.info("当前数据源"+getCurrentDataSources());
	}




}
