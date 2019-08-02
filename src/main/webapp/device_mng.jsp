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
	<div class="he-soso">
		<form id="query_form" style="display:none;">
			<span style="margin-left: 20px;">设备号：</span>
			<div class="so-jian">
				<input type="text" class="am-form-field" name="sim_code" value="${param.device_id }" placeholder="设备号" onkeyup="value=value.replace(/[^\d.]/g,'')">
			</div>
			<input type="button" value="搜索" id="btn_search" class="so-btn" />
		</form>


		<div class="dao-main">
			  
				<div class="am-dropdown" data-am-dropdown>
					<button class="am-btn am-btn-primary am-dropdown-toggle" data-am-dropdown-toggle>
						添加记录 <span class="am-icon-caret-down"></span>
					</button>
					<ul class="am-dropdown-content">
						<li><a href="javascript:void(0)" class="import">手动录入</a></li>
					</ul>
				</div>
			</div>
		<!--导入导出结束-->

	</div>
	<!--查询结束-->

	<!--记录列表开始-->
	<div class="jilu-list">
		<table class="am-table am-table-bordered am-table-striped">
			<thead>
				<tr>
					<th>设备ID</th>
					<th>设备别名</th>
					<th>经度</th>
					<th>纬度</th>
					<th>更新时间</th>
					<th>备注</th>
					<th>管理状态</th>
					
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(page.list)>0 }">
					<c:forEach items="${ page.list }" var="item">
						<tr>
					

							<td>${item.deviceId}</td>	
							<td>${item.deviceName}</td>
							<td>
								<fmt:formatNumber type="number" value="${item.deviceX} " maxFractionDigits="6"/>
							</td>
							<td>
								<fmt:formatNumber type="number" value="${item.deviceY} " maxFractionDigits="6"/>
							</td>
							<td>							
								<fmt:formatDate value="${item.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>

							<td>${item.remark}</td>


							<td>
							
								<c:if test="${item.status == 0 }">
								
									<span class="am-badge am-badge-success am-round">管理中</span>
								</c:if>		
								
								<c:if test="${item.status == 1 }">
								
									<span class="am-badge am-badge-danger am-round">申请管理中，请等待批准</span>
									
								</c:if>										
											
							</td>	
							<td>
							<a name="a_edit" value="${item.id}" href='#' class="am-btn am-btn-default am-btn-xs">编辑</a> 	
							</td>
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
		<div class="win-max-main" style="height:300px;">
			<div class="win-title">
				<span><i class="am-icon-times win-close"></i></span>
				<h4>设备信息</h4>
			</div>
			<form id="edit_form">
			<div class="win-max-txt">
				
					<input type="hidden" name="id" />
					<input type="hidden" name="deviceId" />
					
					
					<div class="set-li">
					  <span>设备编码：</span>
					  <input type="text" class="am-form-field win-put1" name="deviceId" id="deviceId" placeholder="设备编码" required="true">
					</div>
					
					
					<div class="set-li">
					  <span>设备别名：</span>
					  <input type="text" class="am-form-field win-put1" name="deviceName" id="simka" placeholder="设备名称" required="true" />
					</div>
					
					<div class="set-li">
					  <span>所在经度：</span>
					  <input type="text" class="am-form-field win-put1" name="deviceX" id="deviceX" placeholder="所在经度" required="true" />
					</div>

					<div class="set-li">
					  <span>所在纬度：</span>
					  <input type="text" class="am-form-field win-put1" name="deviceY" id="deviceY" placeholder="所在维度" required="true" />
					</div>
					<div class="set-li">
						<span>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span>
						<input type="text" class="am-form-field win-put1" name="remark" id="remark" placeholder="备注" required="true" />
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
		<span id="list_url">${pageContext.request.contextPath}/devmng</span> 
		<span id="save_url">${pageContext.request.contextPath}//saveDevice</span>
		<span id="get_url">${pageContext.request.contextPath}/getDeviceInfo</span>
	</div>
		
	</div>
	
	<script>
	
	function doConfirm(id){
		layer.confirm('确定物联网卡号是否合法?', {
			icon : 3,
			btn : [ '确认', '取消' ]
		}, function() {
			var url = "${pageContext.request.contextPath}/project/simcard/confirm";
			var params = {
				id : id
			};
			$.post(url, params, function(result) {
				if (result.code == 200) {
					layer.msg(result.msg, {
						time : 1000
					}, function() {
						$("#btn_search").click();
					});
				} else {
					layer.alert(result.msg, {
						icon : 2
					});
				}
			}, "json");
		}, function() {
			return;
		});	
	}
	
	
	$(function(){
		$(".win-btn1").click(function() {
			var error_msg = admin.doValidate();
			
			
			var y=document.getElementById("simka").value;
			if(y == ""){
				layer.msg('设备别名不能为空', function(){});
				return false;
			}
			
			if (error_msg.length > 0) {
				layer.alert(error_msg, {
					icon : 2
				});
			} else {
				var url = $("#save_url").text();
				var fromdata = $("#edit_form").serializeArray();
				
				
				var params = common.serializeObject(fromdata);
				$.post(url, params, function(result) {
					if (result.code == 200) {
						layer.msg(result.msg, {
							time : 1000
						}, function() {
							$("#btn_search").click();
						});
					} else {
						layer.alert(result.msg, {
							icon : 2
						});
					}
				}, "json");
			}
		});
	})
		
	
	</script>
</body>
</html>
<script src="${pageContext.request.contextPath}/js/admin.js?time=1409"></script>
