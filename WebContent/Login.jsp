<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Examples</title>
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

	<!-- =============================Body==================================== -->
	<div class="container body">
		<div class="row">
			<div class="container">
				<div class="row">

					<div class="col-xs-12 col-sm-3 member">
						<form action="Session_Set" method="post">
							<table class="table">
								<tr>
									<td>會員編號<input type="text" name="mem_num"></td>
								</tr>
								<tr>
									<td>店家編號<input type="text" name="sto_num"></td>
								</tr>
								<tr>
									<td>訂單編號<input type="text" name="or_num"></td>
									<td>EDITORDER<input type="checkbox" name="action" value="EDITORDER"></td>
								</tr>
								<tr>
									<td>接受<input type="radio" name="isAccept" value="ACCEPTINVITE">
										拒絕<input type="radio" name="isAccept" value="REJECTINVITE"></td>
								</tr>
								<tr>
									<td>合併訂單編號<input type="text" name="meror_num"></td>
								</tr>
								<tr>
									<td><button type="submit">送出</button></td>
								</tr>
							</table>
						</form>
					</div>

				</div>
			</div>


		</div>
	</div>
	
	
	<!-- =============================Body==================================== -->

</body>
</html>