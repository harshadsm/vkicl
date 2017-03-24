<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="vkicl.util.Constants"%>
<%@page import="vkicl.vo.UserInfoVO"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>


<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-core.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>

<!DOCTYPE html>
<!--[if lt IE 7]>	<html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>		<html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>		<html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title><tiles:getAsString name="title" ignore="true" /></title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/icomoon-social.css">
<link href="./css/fonts.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="./css/leaflet.css" />
<link rel="stylesheet" href="./css/fileinput.min.css" />
<!--[if lte IE 8]>
	<link rel="stylesheet" href="./css/leaflet.ie.css" />
<![endif]-->
<link rel="stylesheet" href="./css/bootstrap-datetimepicker.css">
<link rel="stylesheet" href="./css/main-orange.css">
<link rel="stylesheet" href="./jquery-ui.css">
<link rel="stylesheet" href="./css/app.css">
<link rel="stylesheet" href="./css/excel.css">
<link rel="stylesheet" href="./css/report.css">
<!-- Javascripts -->
<script src="./js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<script src="./js/jquery-1.9.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<script src="./js/leaflet.js"></script>
<script src="./js/jquery.fitvids.js"></script>
<script src="./js/jquery.sequence-min.js"></script>
<script src="./js/jquery.bxslider.js"></script>
<script src="./js/main-menu.js"></script>
<script src="./js/template.js"></script>
<script src="./js/moment.js"></script>
<script src="./js/bootstrap-datetimepicker.js"></script>
<script src="./js/bootbox.min.js"></script>
<script src="./js/fileinput.js"></script>
<script src="./js/app.js"></script>
<script src="./js/jquery.metadata.js"></script>
<script src="./js/jquery.tablesorter.js"></script>
<script src="./js/jquery.tablesorter.pager.js"></script>
<script src="./jquery-ui.js"></script>

<script src="./js/export/jspdf/jspdf.js"></script>
<script src="./js/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>

<script src="./js/jquery-number-master/jquery.number.min.js"></script>

<!-- jQgrid start -->
<link rel="stylesheet" type="text/css" media="screen" href="./js/jquery.jqGrid-4.6.0/css/ui.jqgrid.css" />
<script src="./js/jquery.jqGrid-4.6.0/js/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="./js/jquery.jqGrid-4.6.0/js/jquery.jqGrid.min.js" type="text/javascript"></script> 

<script src="./js/jquery.jqGrid-4.6.0/src/jqModal.js" type="text/javascript"></script> 
<script src="./js/jquery.jqGrid-4.6.0/src/jqDnR.js" type="text/javascript"></script>
<script src="./js/jquery.jqGrid-4.6.0/src/jquery.fmatter.js" type="text/javascript"></script>
<script src="./js/jquery.jqGrid-4.6.0/src/JsonXml.js" type="text/javascript"></script>
<!-- jQgrid end -->

<link rel="apple-touch-icon" sizes="57x57" href="./img/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="./img/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="./img/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="./img/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="./img/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="./img/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="./img/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="./img/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="./img/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192"  href="./android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="./img/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="./img/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="./img/favicon/favicon-16x16.png">
<link rel="manifest" href="./img/favicon/manifest.json">

<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage" content="./img/favicon/ms-icon-144x144.png">
<meta name="theme-color" content="#ffffff">

</head>
<body class="body-container">
	<span id="pageType"><tiles:getAsString name="pageType" ignore="true" /></span>
	<div>
		<div>
			<tiles:insert attribute="header" ignore="true" />
		</div>
		<div class="body-background">
			<tiles:insert attribute="body" />
		</div>
		<div>
			<tiles:insert attribute="footer" />
		</div>
	</div>

	<!-- <span id="top-link-block" class="hidden"> <a href="#top"
		class="well well-sm"
		onclick="$('html,body').animate({scrollTop:0},'slow');return false;">
			<i class="glyphicon glyphicon-chevron-up"></i> Back to Top
	</a>
	</span>

	<script>
		if (($(window).height() + 1) < $(document).height()) {
			$('#top-link-block').removeClass('hidden').affix({
				// how far to scroll down before link "slides" into view
				offset : {
					top : 0
				}
			});
		}
	</script> -->
	
	<div class="modal-loader"><!-- Place at bottom of page --></div>

</body>

<%			
	UserInfoVO userInfoVO = (UserInfoVO) session.getAttribute(Constants.USER_INFO_SESSION);
	if(null != userInfoVO) {
		String message = (null==userInfoVO)?"":userInfoVO.getMessage().trim();
		if (!"".equalsIgnoreCase(message) && !"Success".equalsIgnoreCase(message)) {
			out.print("<script type='text/javascript'>bootbox.alert(\""
					+ StringEscapeUtils.escapeHtml(message)
					+ "\");</script>");
		}
		userInfoVO.setMessage("");
		session.setAttribute(Constants.USER_INFO_SESSION, userInfoVO);
	}
%>

</html>
