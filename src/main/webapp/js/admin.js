$(function() {
	var limit_count = 1;

	$("#btn_search").click(function() {
		var url = $("#list_url").text();
		admin.doSearch(url, 1);
	});

	$(".import").click(function() {
		$("#edit_form").find("input").val("");
		$(".win-btn").attr("value", "添加");
		$(".win-tc").css("display", "block");
	});

	$("a[name=a_edit]").click(function() {
		$("#edit_form").find("input").val("");
		$("#edit_form").find("select").val("");
		$(".win-btn").attr("value", "保存");
		$(".win-tc").css("display", "block");
		
		var index = layer.load(1, {
			shade: [0.1,'#fff'] 
		});
		
		
		var id = $(this).attr("value");
		var url = $("#get_url").text();
		var params = {
			id : id
		};
		
		
		$.post(url, params, function(result) {
			
			
			if (result.code == 200) {
								
				var obj = result.obj;
				if (obj && obj.length > 0) {
					for (var i = 0; i < obj.length; i++) {
						var name = obj[i].name;
						var value = obj[i].value;
						$("[name=" + name + "]").val(value);
					}
					layer.close(index);
				}
			}
		}, "json");
	});

	$("a[name=a_delete]").click(function() {
		var id = $(this).attr("value");
		layer.confirm('确定要删除?', {
			icon : 3,
			btn : [ '删除', '取消' ]
		}, function() {
			var url = $("#delete_url").text();
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
	});

	$(".win-close").click(function() {
		$(".win-tc").css("display", "none");
	});

	$(".win-btn").click(function() {
		var error_msg = admin.doValidate();
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

	$(".win-batch").click(function() {
		$(".win-max").css("display", "block");
		var template = $(this).attr("value");
		$(".max-dw a").attr("href", $(".max-dw a").attr("href") + template);
	});
	
	$(".win-close1").click(function() {
		$(".win-max").css("display", "none");
		$("#btn_search").click();
	});
	$(".anniu2").click(function() {
		$(".win-max").css("display", "none");
		$("#btn_search").click();
	});

	$(".anniu1").click(function() {
		$('#doc-form-file').uploadify('upload');
	});

	$('#doc-form-file').uploadify({
		'debug' : false,
		'auto' : false,
		'buttonText' : '选择要上传的文件',
		'buttonClass' : 'am-icon-cloud-upload',
		'buttonCursor' : 'hand',
		'fileTypeExts' : '*.xlsx',
		'removeTimeout' : 1,
		'uploadLimit' : limit_count,
		'multi' : false,
		'formData' : {},
		'swf' : '../js/jquery/uploadify.swf',
		'uploader' : $("#import_url").text(),
		'onUploadStart' : function(file) {
			console.log('Starting to upload ' + file.name);
		},
		'onUploadSuccess' : function(file, data, response) {
			if (data && data.length > 0) {
				var obj = eval('(' + data + ')');
				if (obj.code == 200) {
					layer.alert(obj.msg, {
						icon : 1
					}, function() {
						$("#btn_search").click();
					});
				} else {
					limit_count++;
					$('#doc-form-file').uploadify('settings', 'uploadLimit', limit_count);
					
					if(obj.obj && obj.obj.length>0){
						layer.alert(obj.msg, {
							btn : '下载',
							icon: 7
						}, function() {
							window.location.href = "/project/dowload/filepath?filename=" + obj.obj;
						});
					}else{
						layer.alert(obj.msg, {
							icon: 2
						});
					}
				}
			}
		},
		'onCancel' : function(file) {
			console.log('The file ' + file.name + ' was cancelled.');
		},
		'onUploadError' : function(file, errorCode, errorMsg, errorString) {
			console.log('The file ' + file.name + ' could not be uploaded: ' + errorString);
		}
	});
});

var admin = {

	doSearch : function(url, index) {
		var fromdata = $("#query_form").serializeArray();
		var params = common.serializeObject(fromdata);
		if (index == null) {
			index = 1;
		}
		params["pageCurrent"] = index;
		if (params && params != '') {
			params = (function(obj) {
				var str = "";
				for ( var prop in obj) {
					str += prop + "=" + obj[prop] + "&"
				}
				return str;
			})(params);
			url = url + "?" + params;
		}
		window.location.href = url;
		
		var index = layer.load(0, {
			shade: [0.1,'#fff'] 
		});
	},

	doPage : function(index) {
		var url = $("#list_url").text();
		admin.doSearch(url, index);
	},

	doValidate : function() {
		var msg = '';
		$("input[required=true]").each(function() {
			var value = common.trim($(this).val());
			var placeholder = $(this).attr("placeholder");
			if (value == '') {
				msg = placeholder + "不能为空";
				return false;
			}
		});
		return msg;
	}
}