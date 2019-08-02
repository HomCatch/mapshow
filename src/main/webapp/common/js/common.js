var common = {
	
	context : "/mapShow",
		
	logoImg : "./common/img/he21.png",
	
	mapStyle : mapstyle.style3,

	getlineGraphData : function(deviceId) {
		var url = "/mapShow/getlineGraphData?deviceId=" + deviceId;
		var result;
		$.ajax({
			url : url,
			async : false,
			success : function(data) {
				result = data;
			}
		});
		return result;
	},
	
	getDeviceList : function(deviceId) {
		var url = "/mapShow/getDeviceList?deviceId=" + deviceId;
		var result;
		$.ajax({
			url : url,
			async : false,
			success : function(data) {
				result = data;
			}
		});
		return result;
	},
	
	formatRecordTime:function(time){
		var recordTime = time;
		var seconds = recordTime.seconds;
		if (seconds == 0) {
			seconds = "00";
		} else {
			if (seconds.toString().length == 1) {
				seconds = "0" + seconds;
			}
		}
		var minutes = recordTime.minutes;
		if (minutes == 0) {
			minutes = "00";
		} else {
			if (minutes.toString().length == 1) {
				minutes = "0" + minutes;
			}
		}
		var hours = recordTime.hours;
		if (hours == 0) {
			hours = "00";
		}
		recordTime = hours + ":" + minutes + ":" + seconds;
		return recordTime;
	}
}