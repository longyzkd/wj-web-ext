<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>wj-web</title>
	
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

    <script type="text/javascript" src="${ctx}/ext/ext-all.js"></script>
    <script type="text/javascript" src="${ctx}/common.js"></script>
    <script type="text/javascript" src="${ctx}/app.js"></script>
</head>

</html>
