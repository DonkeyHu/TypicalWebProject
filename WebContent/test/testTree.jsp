<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style type="text/css">
	#tree{width:200px;cursor:pointer}
	.a{list-style:none;background-color:grey;}
	.b{list-style:none;display:none;}
	.b ul li{list-style:none;}
</style>
<script>
	function show(li){
		var next=li.nextSibling;
		while(next.nodeType!=1){
			next=next.nextSibling;
		}
		if(next.style.display==="none"){
			next.style.display="block";
		}else {
			next.style.display="none";
		}
	}
</script>
</head>
<body>
	
	<div id="tree">
		<ul>
			<li class="a" onclick="show(this);">上海</li>		
			<li class="b">
				<ul>
					<li>黄埔</li>
					<li>杨浦</li>
					<li>静安</li>
				</ul>
			</li>
		</ul>
	</div>
	
</body>
</html>