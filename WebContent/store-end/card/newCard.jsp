<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>店家首頁</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/store_base.css">
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->

<style type="text/css">
textarea{/* Text Area 固定大小*/
 max-width:400px;
 max-height:150px;
 min-width:400px;
 min-height:150px;


}
.input-group{
	margin-top: 20px;
}

</style>
	</head>

	<body>
	
		<div class="container">
			<div class="row">
				<div class="col-xs-12 col-sm-5 col-sm-offset-1">
					
		<FORM action="<%=request.getContextPath()%>/CardServlet" method=post enctype="text/html" >
		
<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">需收集</span>
 					 <input type="text" class="form-control" name="points" aria-describedby="basic-addon1">
 					  <span class="input-group-addon">點</span>
					</div>				
					<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">可折價金額</span>
 					 <input type="text" class="form-control" name="points_cash" aria-describedby="basic-addon1">
 					  <span class="input-group-addon">元</span>
					</div>
					<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">集點卡使用期限</span>
 					<input type="text" class="form-control" name="exp_date" aria-describedby="basic-addon1">
 					<span class="input-group-addon">個月</span>
					</div>
					<div>
					<h3><p>使用規則：</p></h3>
						<p><textarea  " class="form-control" name="card_des" id="comments" name="comments" placeholder="Comment" rows="3">
需集滿10點方可折價。							
集點卡有效期限為第一次取得後6個月。
集點卡僅能使用一次且不找零，一經使用無法恢復。
</textarea></p>
					</div>
					<div>
					<input type="hidden" name="action" value="newCard">
					<input type="submit" value="送出" style="margin-right: 100px"> <input type="submit" value="取消">
					</div>

						<!--========================== 功能放這邊 =================================================================================================-->
	
			</FORM>
			
	</div>
			</div>
		</div>

		<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</body>
</html>