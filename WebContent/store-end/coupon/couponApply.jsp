<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>Title Page</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		<style type="text/css">
textarea{/* Text Area 固定大小*/
 max-width:530px;
 max-height:150px;
 min-width:530px;
 min-height:150px;


}
.input-group{
	margin-top: 20px;
}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>折價券申請</title>
</head>
	<body>
	
<jsp:include page="/store-end/store_top.jsp" /> <!-- navbar -->
	<!-- 1層大框框 -->
	<div class="container-fluid">
		<div class="row">
	<!-- 2層框左 -->	
			<jsp:include page="/store-end/store_left.jsp" /> <!-- leftSidebar -->
	<!-- 2層框右 -->		
			<div class="col-xs-12 col-sm-8 col-sm-offset-3" >				
				<div class="block-center panelheight">
				
	<!--========================== 功能放這邊 ↓↓↓↓↓↓===============================-->
	
<center>
<FORM style="margin-top:50px;" action="<%=request.getContextPath()%>/CouponServlet" method=post enctype="multipart/form-data" >
		<div class="container ">
			<div class="row" >
			<div class="col-xs-12 col-sm-3">
					<div class="col-xs-12 col-sm-12">
						 <h4>選擇折價券圖片 :</h4>
						 	<img  class="img-thumbnail" id="output1"  height="135" width="240" >
						 	<input type="file" id="output" name="upfile"   onchange="onFileSelected(event,this.id)"></p>					 	
					</div>
				</div >
				<div class="col-xs-12 col-sm-3">
					<div class="input-group">
  					<span  class="input-group-addon " id="basic-addon1">折價金額</span>
 					 <input  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" name="coupon_cash" maxlength="2" class="form-control"  aria-describedby="basic-addon1">
 					  <span class="input-group-addon">元</span>
					</div>				
					<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">活動開始日</span>
 					 <input type="text"  id="f_date1"  name="up_date" class="form-control"  aria-describedby="basic-addon1">
					</div>			
					<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">預告開始日</span>
 					 <input type="text" id="f_date3" name="notice_up_date" class="form-control"   aria-describedby="basic-addon1">
					</div>			
					<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">有效期限</span>
 					 <input type="text" id="f_date5" name="exp_date" class="form-control" aria-describedby="basic-addon1" >
					</div>
				</div>
				<div class="col-xs-12 col-sm-3">
					<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">發送張數</span>
 					 <input onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  type="text"  name="total"  maxlength="3" class="form-control"  aria-describedby="basic-addon1">
 					  <span class="input-group-addon">張</span>
					</div>
					<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">活動截止日</span>
 					 <input type="text" id="f_date2"  name="down_date" class="form-control"   aria-describedby="basic-addon1" >
					</div>
					<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">預告截止日</span>
 					 <input type="text" id="f_date4"  name="notice_down_date" class="form-control"  aria-describedby="basic-addon1">
					</div>
				</div>
				<div class="col-xs-12 col-sm-3">
				</div >
			</div>
		</div>
		<div class="container">
			<div class="row">
			<div class="col-xs-12 col-sm-3">
				</div >
				<div class="col-xs-12 col-sm-6">
					<div class="col-xs-12 col-sm-12">
						<h3><p>活動敘述：</p></h3>
						<p><textarea   class="form-control" name="coupon_desc"   id="comments"  placeholder="Comment" rows="3">
							</textarea></p>
					</div>
					<div class="col-xs-12 col-sm-12">
						<h3><p>預告敘述 :</p></h3>
						<p><textarea class="form-control" name="notice_desc" id="comments"  placeholder="Comment" rows="3">開幕慶 ！ 2017/10/25 ~ 2017/10/31
			</textarea></p>
					</div>
			
				</div>
				<div class="col-xs-12 col-sm-6"></div>			
			</div>
		</div>
		<input type="hidden" name="action" value="applyCoupon">
<br><input type="submit" class="btn btn-green btn-lg" value="送出" style="margin-right: 100px" >
    <button style="width:30px;height:20px;" onClick="inPutInfo()" type="button"  />

</FORM>


	<!--========================== 功能放這邊 ↑↑↑↑↑↑↑=======================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />
	
	
</body>	
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/js/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/js/datetimepicker/build/jquery.datetimepicker.full.min.js"></script>


<script>
function inPutInfo(){
	document.getElementsByName("notice_up_date")[0].value= '2017-12-02 11:00' ;
	document.getElementsByName("notice_down_date")[0].value= '2017-12-15 19:00' ;
	document.getElementsByName("exp_date")[0].value= '2018-3-15 00:00' ;
	document.getElementsByName("up_date")[0].value= '2017-12-05 11:00' ;
	document.getElementsByName("down_date")[0].value= '2017-12-20 19:00' ;
	
	document.getElementsByName("coupon_cash")[0].value= '20' ;
	document.getElementsByName("total")[0].value= '100' ;
	document.getElementsByName("notice_desc")[0].value= '大苑子慶開幕，將發出100張 20元折價券千萬別錯過 !!' ;
	document.getElementsByName("coupon_desc")[0].value= '每張訂單結帳，只能使用一張折價券。 \r折價券一經使用無法恢復且不找零。 \r折價券有使用效期，逾期後則無法使用，且無法恢復。';

	
	
}

//活動開始日
$.datetimepicker.setLocale('zh');
$('#f_date1').datetimepicker({
    theme: '',              //theme: 'dark',
   timepicker:true,       //timepicker:true,
   format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
   step:1,
   value: 'up', // value:   new Date(),
   //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
   //startDate:	            '2017/07/10',  // 起始日
   //minDate:               '-1970-01-01', // 去除今日(不含)之前
   //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
});


//活動結束日不得在開始日前
$('#f_date2').datetimepicker({
	 theme: '',              //theme: 'dark',
	   timepicker:true,       //timepicker:true,
	   format:'Y-m-d H:i', 
  beforeShowDay: function(date) {
	  somedate1 = new Date(document.getElementById("f_date1").value);
	  if (  date.getYear() <  somedate1.getYear() || 
           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
          (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
      ) {
           return [false, ""]
     }
      return [true, ""];
}

});


//預告開始日不得在活動開始日之後


$('#f_date3').datetimepicker({
	 theme: '',              //theme: 'dark',
	   timepicker:true,       //timepicker:true,
	   format:'Y-m-d H:i', 
  beforeShowDay: function(date) {
	  somedate2 = new Date(document.getElementById("f_date1").value);
	  if (  date.getYear() >  somedate2.getYear() || 
           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
      ) {
           return [false, ""]
      }
      return [true, ""];
}});



//預告結束日需在 活動開始日之前  預告開始日後

 $('#f_date4').datetimepicker({
	 theme: '',              //theme: 'dark',
	   timepicker:true,       //timepicker:true,
	   format:'Y-m-d H:i', 
	 beforeShowDay: function(date) {
        	  somedate1 = new Date(document.getElementById("f_date3").value);
        	  somedate2 = new Date(document.getElementById("f_date1").value);
        	  if (  date.getYear() <  somedate1.getYear() || 
		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
		             ||
		            date.getYear() >  somedate2.getYear() || 
		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
              ) {
                   return [false, ""]
              }
              return [true, ""];
     }});

//折價券到期日無法選擇發送日之前

$('#f_date5').datetimepicker({
	 theme: '',              //theme: 'dark',
	   timepicker:true,       //timepicker:true,
	   format:'Y-m-d H:i', 
	beforeShowDay: function(date) {
	  somedate1 = new Date(document.getElementById("f_date1").value);
	  if (  date.getYear() <  somedate1.getYear() || 
           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
          (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
      ) {
           return [false, ""]
     }
      return [true, ""];
}});



// ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

//      1.以下為某一天之前的日期無法選擇
//      var somedate1 = new Date('2017-06-15');
//      $('#f_date1').datetimepicker({
//          beforeShowDay: function(date) {
//        	  if (  date.getYear() <  somedate1.getYear() || 
//		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
//		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
//              ) {
//                   return [false, ""]
//              }
//              return [true, ""];
//      }});


//      2.以下為某一天之後的日期無法選擇
//      var somedate2 = new Date('2017-06-15');
//      $('#f_date1').datetimepicker({
//          beforeShowDay: function(date) {
//        	  if (  date.getYear() >  somedate2.getYear() || 
//		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
//		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
//              ) {
//                   return [false, ""]
//              }
//              return [true, ""];
//      }});


//      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
//      var somedate1 = new Date('2017-06-15');
//      var somedate2 = new Date('2017-06-25');
//      $('#f_date1').datetimepicker({
//          beforeShowDay: function(date) {
//        	  if (  date.getYear() <  somedate1.getYear() || 
//		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
//		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
//		             ||
//		            date.getYear() >  somedate2.getYear() || 
//		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
//		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
//              ) {
//                   return [false, ""]
//              }
//              return [true, ""];
//      }});



function onFileSelected(event,get){
     
     var selectFile = event.target.files[0];
     var reader = new FileReader();
     
        var imgtag =  document.getElementById(get+"1");
        imgtag.title = selectFile.name;

        reader.onload = function(event){
            imgtag.src = event.target.result;
        };
        reader.readAsDataURL(selectFile);
        
    }
    

</script>
</html>