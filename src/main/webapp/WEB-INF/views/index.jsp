<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>wj-web</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	
	<style type="text/css">
		p {
		    margin:5px;
		}
		.nav {
		    background-image:url(${ctx}/static/icons/folder_go.png);
		}
	</style>

    <link rel="stylesheet" type="text/css" href="${ctx}/ext/resources/css/ext-all.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/app.css">

    <script type="text/javascript" src="${ctx}/ext/ext-all-debug.js"></script>
    <script type="text/javascript" src="${ctx}/app.js"></script>
    <script type="text/javascript" src="${ctx}/common.js"></script>
    
    
    <script type="text/javascript">
    	curUser = '<shiro:principal property="name"/>';
    	ctx = '${ctx}';
    	//TODO 从后台带过来
    	pageSize=2;
    </script>
    <script type="text/javascript">
	    var ajax = function(config) { // 封装、简化AJAX
	    	Ext.Ajax.request({
	    				url : config.url, // 请求地址
	    				params : config.params, // 请求参数
	    				method : config.method, // 方法
	    				callback : function(options, success, response) {
	    					config.callback(Ext.JSON.decode(response.responseText).root);
	    					// 调用回调函数
	    				}
	    			});
	    	//return false;
	    };
	    
	    //var officeTree= convert(Ext.decode('${officeTree}'));
	    
    </script>
    
</head>

</html>
