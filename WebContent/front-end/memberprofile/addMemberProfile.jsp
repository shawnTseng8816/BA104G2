<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member_profile.model.*"%>

<%
  MemberProfileVO memberprofileVO = (MemberProfileVO) request.getAttribute("memberprofileVO");
%>


<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/member_base.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<title>�|�����U - ������</title>

<style>

	.reg{
		padding-top:10px;
		padding-right:140px;
		padding-bottom:10px;
		/* ��relative�A��+�|����� */
	}


	.body{
		font-family:Microsoft JhengHei;
	}
	
	.table2{
		margin-top:10px;
	}
	
/* 	.table2 td{ */
/* 		vertical-align:top; */
/* 	} */
	
	.caption {
		padding-left:85px;
		font-size: 36px;
		color:#3C9682;
		font-weight:bold;
		text-align:center;
/* 		margin-left:1.5em; */
	}
	
	table{
		/* border:1px solid #3C9682;  */
		border-collapse: collapse;
		font-family:Microsoft JhengHei;
		word-break: keep-all;
		/* word-wrap:break-word;  */
		/* word-break:break-all;  */
		/* word-break: normal;  */
	}
	
	td{
		font-size:18px;
	}
	
	tr{
		text-align:left;
	}
	
	input[type="text" i] {
		/* border: none; */
 		border-bottom-style: solid; 
 		border-top-style: none; 
 		border-left-style:none; 
 		border-right-style:none; 
		height:50px;
		background: transparent;
		font-family: 'Roboto', sans-serif;
		font-size: 18px;
		color: rgba(0, 0, 0, 0.8);
		font-weight: 300;
		border-color:#3C9682;
		padding: 6px;
	}
	
	input[type="text" i]:focus{
		/* http://www.w3big.com/zh-TW/css/css-outline.html */
		outline:none;
	}
	
	input[type="password" i] {
		/* border: none; */
 		border-bottom-style: solid; 
 		border-top-style: none; 
 		border-left-style:none; 
 		border-right-style:none; 
		height:50px;
		background: transparent;
		font-family: 'Roboto', sans-serif;
		font-size: 18px;
		color: rgba(0, 0, 0, 0.8);
		font-weight: 300;
		border-color:#3C9682;
		padding: 6px;
	}
	
	input[type="password" i]:focus{
		/* http://www.w3big.com/zh-TW/css/css-outline.html */
		outline:none;
	}
	
	input[type="submit" i]{
 		width:100%;
		-webkit-appearance:button;
		cursor:pointer;
		line-height:64px;
		left:0px;
		border-color:#3C9682;
		/* color:#AD974F; */
		background-color:#ffffff;
		/* border: 3px solid rgba(0, 0, 0, 0.1); */
		border-style:solid;
		font-family:Microsoft JhengHei;
		font-weight:900;
		font-size:24px;
		/* color: rgba(0, 0, 0, 0.2); */
		color:#3C9682;
	}
	
	#output{
	    cursor: pointer;
	    line-height: 64px;
	    border-color: #3C9682;
	    background-color: #ffffff;
	    /* border: 3px solid rgba(0, 0, 0, 0.1); */
	    font-family: Microsoft JhengHei;
	    font-size: 18px;
	    /* color: rgba(0, 0, 0, 0.2); */
	    color: #3C9682;
		
	}
	
	input::-ms-clear{
	    display: none; 
	}  /* ���FIE10��xx   https://www.w3cways.com/1940.html */
	
	input::-ms-reveal{
	    display: none; 
 	}  /* ���FIE10��xx   https://www.w3cways.com/1940.html */
	
	.magic2 {
		padding:2px;
	    background-color: #FFFFFF;
	    color: #ffd280;
	    border:1px solid #ffffff;
	    border-radius: 300px;
	}
	
	.magic2:hover {
		padding:2px;
	    background-color: #ffd280;
	    color: #ffffff;
	    border:1px solid #ffd280;
	    border-radius: 300px;
	}
	
	.magic2:focus {
	    outline:none;
	}
	
	.magicbutton{
		display:inline-table;
		margin-left:20px;
		/* border:1px solid #fff; */
	}
	
	#output{
		max-width:588px;
		max-height:588px;
	}
	
</style>

</head>
<body bgcolor='white'>

<jsp:include page="/front-end/member_top.jsp" flush="true" />

<div class="reg">
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<table class="table">
					<caption>
						<div class="caption">�|�����U</div>	
					</caption>
				</table>
			</div>
<!-- 		</div> -->
	
			<%-- ���~��C --%>
			<c:if test="${not empty errorMsgs}">
				<ul>
					<img src="<%= request.getContextPath() %>/img/xx.png" height="30" width="30">
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color:red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			
			<form METHOD="post" ACTION="mem.do" name="form1" enctype="multipart/form-data">
				<div class="col-xs-12 col-sm-6">
					<table align="center">
						<tr>
							<td>�|���b��&nbsp;</td>
							<td><input type="TEXT" name="mem_acc" size="45" 
								 value="<%= (memberprofileVO==null)? "" : memberprofileVO.getMem_acc()%>" /></td>
						</tr>
						
						<tr>
							<td>�|���K�X&nbsp;</td>
							<td><input type="password" name="mem_pwd" size="45"
								 value="<%= (memberprofileVO==null)? "" : memberprofileVO.getMem_pwd()%>" /></td>
						</tr>
						
						<tr>
							<td>�|���W��&nbsp;</td>
							<td><input type="TEXT" name="mem_name" size="45"
								 value="<%= (memberprofileVO==null)? "" : memberprofileVO.getMem_name()%>" /></td>
						</tr>
						
						<tr class="sex">
							<td>�ʧO&nbsp;</td>
							<td>
								<br>
								<input type="radio" name="sex" size="45" value="�k"> <img src="<%=request.getContextPath()%>/img/gender/male.png" width="50" height="50" title="�k">
								<input type="radio" name="sex" size="45" value="�k"> <img src="<%=request.getContextPath()%>/img/gender/female.png" width="50" height="50" title="�k">
								<input type="radio" name="sex" size="45" value="����"> <img src="<%=request.getContextPath()%>/img/gender/Heterosexuality.png" width="40" height="50" title="����">
								<input type="radio" name="sex" size="45" value="���K"> <img src="<%=request.getContextPath()%>/img/gender/secret.png" width="50" height="50" title="���Q��">
							 <%-- 	 value="<%= (memberprofileVO==null)? "male" : memberprofileVO.getSex()%>" />  --%>
								<br>
							</td>  
						</tr>
						
						<tr>
							<td>�~��&nbsp;</td>
							<td><input type="TEXT" name="age" size="45"
								 value="<%= (memberprofileVO==null)?"" : memberprofileVO.getAge()%>" /></td>
						</tr>
						
						<tr>
							<td>�q�ܸ��X&nbsp;</td>
							<td><input type="TEXT" name="mobile" size="45"
								 value="<%= (memberprofileVO==null)? "" : memberprofileVO.getMobile()%>" /></td>
						</tr>
						
						<tr>
							<td>�q�l�H�c&nbsp;</td>
							<td><input type="TEXT" name="email" size="45"
								 value="<%= (memberprofileVO==null)? "" : memberprofileVO.getEmail()%>" /></td>
						</tr>
						
						<tr>
							<td>��}&nbsp;</td>
							<td><input type="TEXT" name="address" size="45"
								 value="<%= (memberprofileVO==null)? "" : memberprofileVO.getAddress()%>" /></td>
						</tr>
		
					</table>
				</div>
						
				<div class="col-xs-12 col-sm-6">
					<table class="table2" align="center">
						<tr>
							<td>�|���j�Y�K&nbsp;</td>
							<td> 
								 <img name="upfile" id="output" ><br>
								 <input type="file" name="upfile" id="output" size="45" onchange="previewFile()">
								<!--  <%--  value="<%= (memberprofileVO==null)? "mem_img" : memberprofileVO.getMem_img()%>" /></td>  --%>   -->
							</td>
						</tr>
						
						<tr id="cekmobileSide" style="display:none;">
							<td>�O�_�q�L�q������&nbsp;</td>
							<td><input type="TEXT" name="cek_mobile" size="45" readonly="readonly" style="border:0px;"
								 value="<%= (memberprofileVO==null)? "�_" : memberprofileVO.getCek_mobile()%>" /></td>    <!-- �O�B�_�B���Ҥ� -->
						</tr>
						
						<tr name="rempointSide" style="display:none;">
							<td>�Ѿl�I��&nbsp;</td>
							<td><input type="TEXT" name="rem_point" size="45" readonly="readonly" style="border:0px;"
								 value="<%= (memberprofileVO==null)? "0" : memberprofileVO.getRem_point()%>" /></td>
								                                 <!-- 0 -->
						</tr>
						
						<tr name="statusSide" style="display:none;">
							<td>���A&nbsp;</td>
							<td><input type="TEXT" name="status" size="45" readonly="readonly" style="border:0px;"
								 value="<%= (memberprofileVO==null)? "���`" : memberprofileVO.getStatus()%>" readonly="true" /></td>          <!-- ���v -->
								                                <!-- ���` -->
						</tr>
						<tr>
							<td><input type="hidden" name="action" value="insert"></td>
							<td><br><input type="submit" value="�e�X�s�W"></td>
						</tr>
					</table>
				</div>
			</form>

			<div class="magicbutton">
				<button class="magic2" onClick="inPutInfo1()" type="button">rulein123</button>
			</div>	
			
		</div>
	</div>
</div>
<jsp:include page="/front-end/member_foot.jsp" flush="true" />	

</body>

<%-- 
 <%  
//   java.sql.Date hiredate = null;
//   try {
// 	    hiredate = memberprofileVO.getHiredate();
//    } catch (Exception e) {
// 	    hiredate = new java.sql.Date(System.currentTimeMillis());
//    }
 %> 
--%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/build/jquery.datetimepicker.full.min.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>

//        $.datetimepicker.setLocale('zh');
//        $('#f_date1').datetimepicker({
//	       theme: '',              //theme: 'dark',
//	       timepicker:false,       //timepicker:true,
//	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
<%--		   value: '<%=hiredate%>', // value:   new Date(),  --%>
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // �h���S�w���t
           //startDate:	            '2017/07/10',  // �_�l��
           //minDate:               '-1970-01-01', // �h������(���t)���e
           //maxDate:               '+1970-01-01'  // �h������(���t)����
// �@       });
        
        
   
        // ----------------------------------------------------------�H�U�ΨӱƩw�L�k��ܪ����-----------------------------------------------------------

        //      1.�H�U���Y�@�Ѥ��e������L�k���
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

        
        //      2.�H�U���Y�@�Ѥ��᪺����L�k���
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


        //      3.�H�U����Ӥ�����~������L�k��� (�]�i���ݭn������L���)
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

	function previewFile() {
		  var preview = document.querySelector('img[name=upfile]');
		  var file    = document.querySelector('input[type=file]').files[0];
		  var reader  = new FileReader();
		
		  reader.onloadend = function () {
		    preview.src = reader.result;
		  }
		
		  if (file) {
		    reader.readAsDataURL(file);
		  } else {
		    preview.src = "";
		  }
		}
	
    function inPutInfo1(){
		document.getElementsByName("mem_acc")[0].value= 'rulein123';
		document.getElementsByName("mem_pwd")[0].value= 'rulein456';
		document.getElementsByName("mem_name")[0].value= '�����v';
		document.getElementsByName("age")[0].value= '20';
		document.getElementsByName("mobile")[0].value= '0946-768453';
		document.getElementsByName("email")[0].value= 'rulein@hotmail.com';
		document.getElementsByName("address")[0].value= '�������';
	}
	
	//����TR

	//document.getElementById( "cekmobileSide ").style.visibility= "hidden"; 
	//var result_style = document.getElementById('rempointSide').style;
	//var result_style = document.getElementById('statusSide').style;

        
</script>
</html>