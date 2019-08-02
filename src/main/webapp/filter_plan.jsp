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

        <body style="width: 1350px;" class="am-table-centered">
          <!--记录列表开始-->
          <div class="jilu-list">
            <table class="am-table am-table-bordered am-table-striped">
              <thead>
                <tr>
                  <th>设备编号 </th>
                  <th>上一次滤芯更换记录</th>
                  <th>下一次滤芯更换记录</th>
                </tr>
              </thead>
              <tbody>
                <c:if test="${fn:length(page.list)>0 }">
                  <c:forEach items="${ page.list }" var="item">
                    <tr>
                      <td>${item.deviceId}</td>
                      <td>
                        <div style="display: inline-block;">
                          <div style="float: left;text-align: center;">
                            <fmt:formatDate value="${item.previousTime }" pattern="yyyy-MM-dd" />
                            <br/>
                            <span>${item.previousRemark}</span>
                          </div>
                          <!-- userId == 1000时显示 -->
                          <c:if test="${userId==10000}">
                            <div style="float: left;margin-left: 50px">
                              <button type="button" class="am-btn am-btn-default zzq_edit_btn am-btn-xs" data-id="${item.id}" data-deviceId="${item.deviceId}"
                                data-type="previous">编辑</button>
                            </div>
                          </c:if>
                        </div>
                      </td>
                      <td>
                        <div style="display: inline-block;">
                          <div style="float: left;text-align: center;">
                            <fmt:formatDate value="${item.nextTime }" pattern="yyyy-MM-dd" />
                            <br/>
                            <span>${item.nextRemark}</span>
                          </div>
                          <c:if test="${userId==10000}">
                            <div style="float: left;margin-left: 50px">
                              <button type="button" class="am-btn am-btn-default zzq_edit_btn am-btn-xs" data-id="${item.id}" data-deviceId="${item.deviceId}"
                                data-type="next">编辑</button>
                            </div>
                          </c:if>

                        </div>
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
          <!-- 编辑弹窗开始 -->
          <div class="am-modal am-modal-confirm" tabindex="-1" id="zzq_edit">
            <div class="am-modal-dialog">
              <div class="am-modal-hd">设备信息修改
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
              </div>
              <div class="am-modal-bd">
                <form action="${pageContext.request.contextPath}//savePlan" class="am-form" id="zzq_edit_form" method="post">
                  <fieldset>
                    <div class="am-form-group">
                      <label for="time">日期</label>
                      <input type="text" class="am-form-field" name="time" id="time" placeholder="日期" data-am-datepicker="{format: 'yyyy-mm-dd'}"
                        readonly required />
                    </div>
                    <div class="am-form-group">
                      <label for="remark">请输入更换滤芯内容</label>
                      <input type="text" class="am-form-field win-put1" value="" name="remark" id="remark" placeholder="更换滤芯内容" required="true">
                    </div>
                  </fieldset>
                  </fieldset>
                </form>
              </div>
              <div class="am-modal-footer">
                <span class="am-modal-btn" data-am-modal-cancel>取消</span>
                <span class="am-modal-btn" data-am-modal-confirm>确定</span>
              </div>
            </div>
          </div>
          <!-- 编辑弹窗关闭 -->
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
                  <span>
                    <i class="am-icon-times win-close"></i>
                  </span>
                  <h4>设备信息</h4>
                </div>
                <form id="edit_form">
                  <div class="win-max-txt">

                    <input type="hidden" name="id" />
                    <input type="hidden" name="deviceId" />
                    <div class="set-li">
                      <span>请选择日期：</span>
                      <input type="text" class="am-form-field win-put1" value="" name="date" id="date" placeholder="日期" data-am-datepicker readonly required
                      />
                      <!-- <input type="text" class="am-form-field win-put1" name="date" id="date" placeholder="日期" required="true"> -->
                    </div>
                    <div class="set-li">
                      <span>请输入更换滤芯内容：</span>
                      <input type="text" class="am-form-field win-put1" name="remark" id="remark" placeholder="更换滤芯内容" required="true">
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
              <span id="list_url">${pageContext.request.contextPath}/filter_plan</span>
              <span id="save_url">${pageContext.request.contextPath}//savePlan</span>
              <span id="get_url">${pageContext.request.contextPath}/getPlanInfo</span>
              <span id="zzq_currentPage">${page.pageCurrent}</span>
            </div>

            </div>
            <script src="./js/index.js"></script>
            <script>

              function doConfirm(id) {
                layer.confirm('确定物联网卡号是否合法?', {
                  icon: 3,
                  btn: ['确认', '取消']
                }, function () {
                  var url = "${pageContext.request.contextPath}/project/simcard/confirm";
                  var params = {
                    id: id
                  };
                  $.post(url, params, function (result) {
                    if (result.code == 200) {
                      layer.msg(result.msg, {
                        time: 1000
                      }, function () {
                        $("#btn_search").click();
                      });
                    } else {
                      layer.alert(result.msg, {
                        icon: 2
                      });
                    }
                  }, "json");
                }, function () {
                  return;
                });
              }

              $(function () { // 提交验证
                $(".win-btn1").click(function () {
                  var error_msg = admin.doValidate();


                  var y = document.getElementById("simka").value;
                  if (y == "") {
                    layer.msg('设备别名不能为空', function () { });
                    return false;
                  }

                  if (error_msg.length > 0) {
                    layer.alert(error_msg, {
                      icon: 2
                    });
                  } else {
                    var url = $("#save_url").text();
                    var fromdata = $("#edit_form").serializeArray();


                    var params = common.serializeObject(fromdata);
                    $.post(url, params, function (result) {
                      if (result.code == 200) {
                        layer.msg(result.msg, {
                          time: 1000
                        }, function () {
                          $("#btn_search").click();
                        });
                      } else {
                        layer.alert(result.msg, {
                          icon: 2
                        });
                      }
                    }, "json");
                  }
                });
                

                $('.zzq_edit_btn').on('click', function () {
                  var id = $(this).attr('data-id');
                  var type = 0;
                  var deviceId = $(this).attr('data-deviceId');
                  var url = $('#get_url').text();
                  var editType = $(this).attr('data-type');
                  $('#zzq_edit').find('#time').attr('value', '')
                  $('#zzq_edit').find('#remark').val('')
                  $.ajax({
                    url: url,
                    type: 'POST',
                    data: {
                      id: id
                    },
                    success: function (res) {
                      var time = '';
                      var remark = ''
                      console.log(res)
                      if (editType === 'previous') {  // 编辑上一次
                        type = 1;
                        res.obj.map(function (item) {
                          if (item.name == "previousTime") {
                            time = item.value;
                          }
                          if (item.name == "previousRemark") {
                            remark = item.value;
                          }
                        })
                      } else {
                        type = 2;
                        res.obj.map(function (item) {
                          if (item.name == "nextTime") {
                            time = item.value;
                          }
                          if (item.name == "nextRemark") {
                            remark = item.value;
                          }
                        })
                      }
                      $('#zzq_edit').find('#time').attr('value', time)  // 将time写入组件
                      $('#zzq_edit').find('#remark').val(remark)
                    }
                  })
                  $('#zzq_edit').modal({
                    relatedTarget: this,
                    onConfirm: function (options) {
                      var commitUrl = $('#save_url').text()
                      $.ajax({
                        url: commitUrl,
                        type: 'POST',
                        data: {
                          id: id,
                          type: type,
                          deviceId: deviceId,
                          time: $('#zzq_edit').find('#time').attr('value'),
                          remark: $('#zzq_edit').find('#remark').val()
                        },
                        success: function (res) {
                          console.log('success')
                          // 修改成功后刷新当前页面
                          var zzq_page = $('#zzq_currentPage').text();
                          admin.doPage(zzq_page)
                        }
                      })
                    },
                    // closeOnConfirm: false,
                    onCancel: function () {
                      // alert('算求，不弄了');
                    }
                  })
                })
              })
            </script>
        </body>

        </html>
        <script src="${pageContext.request.contextPath}/js/admin.js?time=1409"></script>