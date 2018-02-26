function createXHR() {
	var xhr;
	if (window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();
	} else {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xhr;
}
function handleXHRText(method, url, param, asyn, handle200, loading, handle404,
		handle500) {
	var xhr = createXHR();
	
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4) {
			if (xhr.status === 200) {
				/*
				 * 下面这个参照js此用法
				 * var handle200=function(){
				 * }
				 * 把函数当做一个对象传进来;
				 * 其实这里有个匿名函数自调的知识点
				 */
				if (handle200) {
					handle200(xhr.responseText);
				}
			} else if (xhr.status === 404) {
				if (handle404) {
					handle404();
				}
			} else if (xhr.status === 500) {
				if (handle505) {
					handle500();
				}
			}
		} else {
			if (loading) {
				loading();
			}
		}
	}
	if("get"==method.toLowerCase()){
		var s=(param==null)?"":"?"+param;
		xhr.open(method, url+s, asyn);
		xhr.send(null);
	}else if("post"==method.toLowerCase()){
		xhr.open(method,url,asyn);
		xhr.setRequestHeader("Content-Type","application/x-www-form-unlencoded");
		xhr.send(param);
	}
}