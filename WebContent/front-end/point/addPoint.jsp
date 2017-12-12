<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="memSvc" class="com.member_profile.model.MemberProfileService"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.all.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.min.js"></script>
<jsp:include page="/front-end/member_top.jsp" flush="true" />
<jsp:include page="/front-end/coupon_notify.jsp" />
<script src="<%=request.getContextPath()%>/js/card.js"></script>
<title>點數儲值 </title>
</head>
 <style>
        .demo-container {
            width: 100%;
            max-width: 350px;
            margin: 30px auto;
        }

        form {
            margin: 30px;
        }
        input {
            width: 200px;
            margin: 10px auto;
            display: block;
           
        }
        .alert-infoeric {
    color: #fcf8e3;
    background-color: #3c9682;
    
}

    </style>
<body>


<div style="margin-top:50px;" class="card-wrapper"></div>

<FORM class="form-horizontal addpoint"  method=post enctype="text/html">

<div class="container">
    <div class="row">
        <!-- You can make it whatever width you want. I'm making it full width
             on <= small devices and 4/12 page width on >= medium devices -->
        <div class="col-xs-12 col-md-4 col-md-offset-4">
       <h3> <div class="alert alert-infoeric" style="text-align:center;"id="remPoint">剩餘點數 : ${memSvc.getOneMemInfo(sessionScope.mem_num).rem_point}</div></h3>
            <!-- CREDIT CARD FORM STARTS HERE -->
            <div class="panel panel-default credit-card-box" style="padding-left: 20px;padding-right: 20px;">
                <div class="panel-body">
<!--                     <form role="form" id="payment-form" method="POST" action="javascript:void(0);"> -->
                          <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label for="couponCode">信用卡卡號:</label>
                                      <input class="form-control" placeholder="Card number" type="tel" name="number" id="number" maxlength="19"  >            
                                </div>
                            </div>                        
                        </div>
                        
                        <div class="row">
                            <div class="col-xs-7 col-md-7">
                                <div class="form-group">
                                    <label for="cardExpiry"><span class="hidden-xs">到期日:</span><span class="visible-xs-inline">EXP</span> DATE</label>
                                     <input class="form-control" placeholder="MM/YY" type="tel" name="expiry" maxlength="7"  >
                                </div>
                            </div>
                            <div class="col-xs-5 col-md-5 pull-right">
                                <div class="form-group">
                                    <label for="cardCVC">安全碼</label>
                                    <input class="form-control" placeholder="CVC" type="number" name="cvc">
                                </div>
                            </div>
                        </div>
                          <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label for="couponCode">持卡人姓名 : </label>
                                    <input class="form-control" type="text"  name="name">                     
                                </div>
                            </div>                        
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="form-group">
                                    <label for="couponCode">儲值金額 : </label>
                                    <input class="form-control" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  maxlength="7" onafterpaste="this.value=this.value.replace(/\D/g,'')" id="value_cash" name="value_cash">                     
                                </div>
                            </div>                        
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                             <input type="hidden" name="action" id="action" value="addPoint">      
                               <input type="button" id="doSubmit"  value="送出" class="subscribe btn btn-green btn-lg btn-block" ></button>
                            <button style="width:30px;height:20px;" onClick="inPutInfo()" type="button"  />
                            </div>
                        </div>
                        <div class="row" style="display:none;">
                            <div class="col-xs-12">
                                <p class="payment-errors"></p>
                            </div>
                        </div>
<!--                     </form> -->
                </div>
            </div>            
            <!-- CREDIT CARD FORM ENDS HERE -->
            
            
        </div>            
        
        
    </div>
</div>

 </FORM> 

 

<jsp:include page="/front-end/member_foot.jsp" flush="true" />
</body>

<script>
function inPutInfo(){


	document.getElementsByName("expiry")[0].value= '06/21';
	document.getElementsByName("cvc")[0].value= '615';
	document.getElementsByName("number")[0].value= '4560 3245 6234 665';
	document.getElementsByName("name")[0].value= '王小明';
	
}

	
 new Card({
	 form: document.querySelector('.addpoint'),
     container: '.card-wrapper'
 });
 
 

 $(function(){
		$('#doSubmit').on('click', function(){
			var number = $('#number').val();
			var value_cash = $('#value_cash').val();
			var action =  $('#action').val();			
		
			$.ajax({
				 type: "POST",
				 url: "<%=request.getContextPath()%>/ValueRecordServlet",
				 data: {"number": number, "value_cash":value_cash, "action":action},
				 dataType: "json",
				 async: false, // true預設是非同步，也就是"不會等待"; false是同步，"會等待"
				 success: function(data){
					  console.log(data.message);
					  if(data.message != null){
						  $('#remPoint').html('<h3>剩餘點數 : '+data.rem_point+'</h3>');
						  swal({
							  position: 'center',
							  type: 'success',
							  title: data.message,
							  showConfirmButton: true,
							  timer: 2500
							})
						}	
					  if(data.errormessage != null){
						  swal({
							  position: 'center',
							  type: 'error',
							  title: data.errormessage,
							  showConfirmButton: true,
							  timer: 2500
							})
						}		
					},
				error: function(){alert("系統忙碌中 ， 請稍後再試 。")}
	        })

		});
	});

   </script>
</html>