<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div class="win-max">
	<div class="win-bg"></div>
	<div class="win-max-main">
		<div class="win-title">
			<span><i class="am-icon-times win-close1"></i></span>
			<h4>批量导入</h4>
		</div>
		<div class="win-max-txt">
			<div class="max-li1">
				<h4>1、下载模板</h4>
				<div class="max-ll max-dw">
					<a href="${pageContext.request.contextPath}/project/dowload/templatefile?filename="> 
					<img src="${pageContext.request.contextPath}/project/img/escel_img.png"/>
					<p>点击下载模板</p>
					<div class="max-bg">
						<i class="am-icon-cloud-download"></i>
					</div>
					</a>
				</div>
			</div>
			<div class="max-li2">
				<h4>2、上传模板</h4>
				<div class="max-ll">
					<div class="am-form-group am-form-file">
						<input id="doc-form-file" type="file" multiple>
					</div>
					<div id="file-list"></div>
					<script>
						
					</script>
				</div>
			</div>
			<div style="clear: both"></div>
		</div>
		<div class="win-anniu">
			<a href="#" class="anniu1">确定上传</a> <a href="#" class="anniu2">取消</a>
		</div>
	</div>
</div>
