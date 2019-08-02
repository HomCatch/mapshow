<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<c:if test="${page.total>0 }">
	<ul data-am-widget="pagination" class="am-pagination am-pagination-default">
		<c:choose>
			<c:when test="${page.pageCurrent == 1 }">
				<li class="am-pagination-first "><a href="javascript:void(0);" style="color: rgb(190, 181, 181);">首页</a></li>
				<li class="am-pagination-prev "><a href="javascript:void(0);" style="color: rgb(190, 181, 181);">上一页</a></li>
			</c:when>
			<c:otherwise>
				<li class="am-pagination-first "><a href='javascript:admin.doPage("1");' class="">首页</a></li>
				<li class="am-pagination-prev "><a href='javascript:admin.doPage("${page.pageCurrent-1}");' class="">上一页</a></li>
			</c:otherwise>
		</c:choose>
		<c:forEach items="${page.nums }" var="v">
			<c:choose>
				<c:when test="${page.pageCurrent == v }">
					<li class="am-active"><a href="javascript:void(0);" class="am-active">${v }</a></li>
				</c:when>
				<c:otherwise>
					<li class=""><a href='javascript:admin.doPage("${v}");' class="">${v }</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:choose>
			<c:when test="${page.pageCurrent == page.pageCount }">
				<li class="am-pagination-next "><a href="javascript:void(0);" style="color: rgb(190, 181, 181);">下一页</a></li>
				<li class="am-pagination-last "><a href="javascript:void(0);" style="color: rgb(190, 181, 181);">末页</a></li>
			</c:when>
			<c:otherwise>
				<li class="am-pagination-next "><a href='javascript:admin.doPage("${page.pageCurrent+1}");' class="">下一页</a></li>
				<li class="am-pagination-last "><a href='javascript:admin.doPage("${page.pageCount}");' class="">末页</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</c:if>