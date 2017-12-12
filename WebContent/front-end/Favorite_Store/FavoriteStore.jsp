<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.favorite_store.model.*"%>

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>揪茶趣</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery.js"></script>


<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->


<style>

	.card {
		font-size: 1em;
		overflow: hidden;
		padding: 0;
		border: none;
		border-radius: 3%;
		box-shadow: 0px 25px 50px rgba(0, 0, 0, 0.5);;
	}
	
	.card-block {
		font-size: 1em;
		position: relative;
		margin: 0;
		padding: 1em;
		border: none;
		border-top: 1px solid rgba(34, 36, 38, .1);
		box-shadow: none;
	}
	
	.card-img-top {
		display: block;
		width: 265.5px;
		height: 198.69px;
		max-width: 265.5px;
		max-height: 198.69px;
	}
	
	.card-title {
		font-size: 1.5em;
		font-weight: 700;
		line-height: 0.2em;
	}
	
	.card-text {
		clear: both;
		margin-top: 0.5em;
		color: rgba(0, 0, 0, .68);
	}
	
	.card-footer {
		font-size: 1em;
		position: static;
		top: 0;
		left: 0;
		max-width: 100%;
		padding: .75em 1em;
		color: rgba(0, 0, 0, .4);
		border-top: 1px solid rgba(0, 0, 0, .05) !important;
		background: #fff;
	}
	
	.card-inverse .btn {
		border: 1px solid rgba(0, 0, 0, .05);
	}
	
	.profile {
		position: absolute;
		top: -16px;
		display: inline-block;
		overflow: hidden;
		box-sizing: border-box;
		width: 48px;
		height: 48px;
		margin-left: 13.5em;
		/*margin: 0;*/
		/*border: 1px solid #fff;
	    border-radius: 50%;*/
	}
	
	.profile-avatar {
		display: block;
		width: 100%;
		height: auto;
		border-radius: 50%;
	}
	
	.meta {
		margin-top: 1em;
		font-size: 1em;
		color: rgba(0, 0, 0, .4);
	}
	
	.meta a {
		text-decoration: none;
		color: rgba(0, 0, 0, .4);
	}
	
	.meta a:hover {
		color: rgba(0, 0, 0, .87);
	}
	
	.card-footer p {
		font-size: 1.46em;
	}
	
	.stores {
		margin-top: 1.5em;
	}

</style>


<script>
	$(document).ready(function() {

		$(".favorite").mouseenter(function() {
			$(this).css("display", "none");
			$(this).next().css("display", "inline-block");
		});

		$(".remove").mouseleave(function() {
			$(this).css("display", "none");
			$(this).prev().css("display", "inline-block");
		});
	});
</script>

</head>
<body>

	<jsp:include page="/front-end/member_top.jsp" flush="true" />

	<!-- =============================Body==================================== -->
	<div class="container">
		<div class="row" id="favoriteStoreContent">
			
			<jsp:include page="/front-end/Favorite_Store/MyFavoriteStores.jsp" flush="true" />
			
		</div>
	</div>
	<!-- =============================Body==================================== -->

	<jsp:include page="/front-end/member_foot.jsp" flush="true" />

</body>
</html>