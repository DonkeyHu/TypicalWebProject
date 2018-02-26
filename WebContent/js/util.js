
function $(e) {
	return document.getElementById(e);
}
String.prototype.trim=function(){
	return this.replace(/(^\s+)|(\s+$)/g,"");
}
function checkFormField(fieldObj,mesObj,re,nullMeg,errorMeg){
	mesObj.innerHTML="";
	var v=fieldObj.value.trim();
	var flag=true;
	if(v.length==0){
		mesObj.innerHTML=nullMeg;
		flag=false;
	}else{
		if(!re.test(v)){
			mesObj.innerHTML=errorMeg;
			flag=false;
		}
	}
	return flag;
}