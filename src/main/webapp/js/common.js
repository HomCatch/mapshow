var root = "";

var common = {
		
	serializeObject : function(array) {
		var json = {};
		if (array && array.length > 0) {
			for (var i = 0; i < array.length; i++) {
				var key = array[i]['name'];
				json[key] = array[i]['value'];
			}
		}
		return json;
	},

	trim : function(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
}