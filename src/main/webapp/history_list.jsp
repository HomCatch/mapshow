<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<link href="${pageContext.request.contextPath}/css/amazeui.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/xiaohesys.css?time=201706141048" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery/jquery.uploadify.min.js"></script>
</head>
<body style="width: 1350px;">
	<!--查询开始-->
	<div class="he-soso" style="display:none;">
		<form id="query_form">
			<span style="margin-left: 20px;">设备号：</span>
			<div class="so-jian">
				<input type="text" class="am-form-field" name="sim_code" value="${param.device_id }" placeholder="设备号" onkeyup="value=value.replace(/[^\d.]/g,'')">
			</div>
			<input type="button" value="搜索" id="btn_search" class="so-btn" />
		</form>


	</div>
	<!--查询结束-->

	<!--记录列表开始-->
	<div class="jilu-list">
		<table class="am-table am-table-bordered am-table-striped">
			<thead>
				<tr>
					<th>设备别名</th>
					<th>设备ID</th>
					<th>记录时间</th>
					<!-- <th>上报水量</th> -->
					<th>入水TDS</th>
					<th>出水TDS</th>
					<!-- <th>入水ph</th>
					<th>出水ph</th> -->
					<th>入水色度</th>
					<th>出水色度</th>
					<th>入水浊度</th>
					<th>出水浊度</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(page.list)>0 }">
					<c:forEach items="${ page.list }" var="item">
						<tr>
							<td>${item.deviceName}</td>
							<td>${item.deviceId}</td>	
							<td><fmt:formatDate value="${item.recordTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<%-- <td>${item.purifyAmount}</td> --%>
							<td>${item.tds}</td>	
							<td>${item.purifyTds}</td>	  
							<%-- <td>${item.ph}</td>	
							<td>${item.purifyPh}</td>	 --%>
							<td>${item.color}</td>	  
							<td>${item.purifyColor}</td>	
							<td>${item.tbdt}</td>	
							<td>${item.purifyTbdt}</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${ page.list ==null || fn:length(page.list)==0 }">
					<tr>
						<td colspan="100">没有记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<!--记录列表结束-->

	<!--分页开始-->
	<div class="he-page">
	<%@ include file="public/page.jsp"%>   
	</div>
	<!--分页结束-->
	
	<!--批量上传开始-->
	<%@ include file="public/import.jsp"%>
	<!--批量上传结束-->

	<!--添加记录开始-->
	<div class="win-tc win-set">
		<div class="win-bg"></div>
		<div class="win-max-main" style="height:200px;">
			<div class="win-title">
				<span><i class="am-icon-times win-close"></i></span>
				<h4>物联网卡信息</h4>
			</div>
			<form id="edit_form">
			<div class="win-max-txt">
				
					<input type="hidden" name="id" />
					
					<div class="set-li">
					  <span>卡号：</span>
					  <input type="text" class="am-form-field win-put1" name="code" id="simka" placeholder="物联网卡号" required="true" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
					</div>
	
			</div>
			<div class="win-anniu">
			    <a href="#" class="anniu1 win-btn1">确定</a>
			    <a href="#" class="anniu2">取消</a>
			</div>
			</form>
		</div>
	</div>
	<!--添加记录结束-->
	
	
	<div style="display: none;">
		<span id="list_url">${pageContext.request.contextPath}/details</span> 
	</div>
	<script>
	
	
	
	</script>
</body>
</html>
<script src="${pageContext.request.contextPath}/js/admin.js"></script>