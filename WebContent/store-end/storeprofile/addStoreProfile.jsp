<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.store_profile.model.*"%>

<%
  StoreProfileVO storeprofileVO = (StoreProfileVO) request.getAttribute("storeprofileVO");
%>


<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

<title>�ө����U - ������</title>

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
		margin-top:15px;
		width:0px;
	}
	
/* 		.table2 td{  */
/*  		vertical-align:top;  */
/*  	}  */

	.caption {
		padding-left:85px;
		font-size: 36px;
		color:#3C9682;
		font-weight:bold;
		text-align:center;
 		margin-left:1.5em; 
	}
	
	table{
		/* border:1px solid #3C9682;  */
		table-layout:fie
		border-collapse: collapse;
		font-family:Microsoft JhengHei;
		word-break: keep-all;
		/* word-wrap:break-word;  */
		/* word-break:break-all;  */
		/* word-break: normal;  */
	}
	.table1{
		width:0px;
		padding-right:5px;
	}
	
	td{
		font-size:18px;
	}
	
	tr{
		text-align:left;
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
		padding-left: 6px;
	}
	
	input[type="password" i]:focus{
		/* http://www.w3big.com/zh-TW/css/css-outline.html */
		outline:none;
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
		padding-left: 6px;
	}
	
	input[type="text" i]:focus{
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
	#output1{
		max-width:588px;
		max-height:588px;
	}
</style>

</head>
<body bgcolor='white'>

<jsp:include page="/store-end/store_top.jsp" flush="true" />

<div class="reg">
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<table class="table">
					<caption>
						<div class="caption">�ө����U</div>
					</caption>
				</table>
			</div>

			<%-- ���~��C --%>
			<c:if test="${not empty errorMsgs}">
				<ul>
					<img src="<%= request.getContextPath() %>/img/xx.png" height="30" width="30">
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color:red; list-style-type:none;">${message}</li>
					</c:forEach>
				</ul>
			</c:if>

			<form METHOD="post" ACTION="sto.do" name="form1" enctype="multipart/form-data">
				<div class="col-xs-12 col-sm-6">
					<table class="table1">
						<tr>
							<td>�ө��b��&nbsp;</td>
							<td><input type="TEXT" name="sto_acc" size="45" 
								 value="<%= (storeprofileVO==null)? " " : storeprofileVO.getSto_acc()%>" /></td>
						</tr>
						
						<tr>
							<td>�ө��K�X&nbsp;</td>
							<td><input type="PASSWORD" name="sto_pwd" size="45"
								 value="<%= (storeprofileVO==null)? "" : storeprofileVO.getSto_pwd()%>" /></td>
						</tr>
						
						<tr>
							<td>�ө��W��&nbsp;</td>
							<td><input type="TEXT" name="sto_name" size="45"
								 value="<%= (storeprofileVO==null)? " " : storeprofileVO.getSto_name()%>" /></td>
						</tr>
						
						<tr>
							<td>�ө��t�d�H&nbsp;</td>
							<td><input type="TEXT" name="guy" size="45"
								 value="<%= (storeprofileVO==null)? " " : storeprofileVO.getGuy()%>" /></td>
						</tr>
						
						<tr>
							<td>�Τ@�s��&nbsp;</td>
							<td><input type="TEXT" name="uni_num" size="45"
								 value="<%= (storeprofileVO==null)? " " : storeprofileVO.getUni_num()%>" /></td>
						</tr>
						
						<tr>
							<td>�ө��q�ܸ��X&nbsp;</td>
							<td><input type="TEXT" name="mobile" size="45"
								 value="<%= (storeprofileVO==null)? " " : storeprofileVO.getMobile()%>" /></td>
						</tr>
						
						<tr>
							<td>�ө��Ҧb�a��&nbsp;</td>
							<td><input type="TEXT" name="area" size="45"
								 value="<%= (storeprofileVO==null)? " " : storeprofileVO.getArea()%>" /></td>
						</tr>
						
						<tr>
							<td>�ө��a�}&nbsp;</td>
							<td><input type="TEXT" name="address" size="45"
								 value="<%= (storeprofileVO==null)? " " : storeprofileVO.getAddress()%>" /></td>
						</tr>
						
						<tr>
							<td>�ө��H�c&nbsp;</td>
							<td><input type="TEXT" name="email" size="45"
								 value="<%= (storeprofileVO==null)? " " : storeprofileVO.getEmail()%>" /></td>
						</tr>
						
						<tr>
							<td>�ө��]�߮ɶ�&nbsp;</td>
							<td><input type="TEXT" name="set_time" size="45" id="f_date1"></td> <!-- 2017-11-13 -->
					<!-- 	<div class="input-group">
					  			<span class="input-group-addon" id="basic-addon1">  ���ʶ}�l��     </span> 
					 			<input type="text" id="f_date1" name="up_date" class="form-control" aria-describedby="basic-addon1">
							</div>	 
					-->
						</tr>
					</table>
				</div>
				
				<div class="col-xs-12 col-sm-6">
					<table class="table2">
						<tr>
							<td>�ө�LOGO&nbsp;</td>
							<td> <img id="output1"  ><br>
								 <input type="file" name="upfile" id="output" size="45" onchange="onFileSelected(event,this.id)">
								<!--  <%--  value="<%= (storeprofileVO==null)? "Sto_logo" : storeprofileVO.getSto_logo()%>" /></td>  --%>   -->
							</td>
						</tr>
						
						<tr>
							<td>�ө�����&nbsp;</td>
							<td>
								  <textarea class="form-control" rows="5" id="comment" name="sto_introduce" size="45"></textarea>					
							</td>
						</tr>
						
						<tr name="pointSide" style="display:none;">
							<td>�ө��Ѿl�I��&nbsp;</td>
							<td><input type="TEXT" name="rem_point" size="45"/></td>
						</tr>
						
						<tr name="statusSide" style="display:none;">
							<td>�ө��W�U�[���A&nbsp;</td>
							<td><input type="TEXT" name="sto_status" size="45"/></td>
								                         <!-- �ӽФ��B���W�[�B�w�W�[�B��ݤU�[�B���v -->
						</tr>
						<tr>
							<td><input type="hidden" name="action" value="insertsto"></td>
							<td><br><input type="submit" value="�e�X�s�W"></td>
						</tr>
					</table>
				</div>
			</form>
			<div class="magicbutton">
				<button class="magic2" onClick="inPutInfo1()" type="button" style="margin-left:100px;">shop8</button>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/store-end/store_foot.jsp" flush="true" />	

</body>

 
 <%  
 
 
 	java.util.Date util_time =null;
 
 	try{
 		util_time = storeprofileVO.getSet_time();
 	} catch(Exception e){
 		util_time = new java.util.Date(System.currentTimeMillis());
 	}
 
 	
   java.sql.Date set_time = new java.sql.Date(util_time.getTime());

    
 %> 

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

         $.datetimepicker.setLocale('zh');
         $('#f_date1').datetimepicker({
 	       theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   //value: '<%=set_time%>', // value:   new Date(),   
            disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // �h���S�w���t
            startDate:	            '2017/07/10',  // �_�l��
            minDate:               '-1970-01-01', // �h������(���t)���e
            maxDate:               '+1970-01-01'  // �h������(���t)����
  �@       });
        
        
   
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
	
	
	
    function inPutInfo1(){
		document.getElementsByName("sto_acc")[0].value= 'shop8';
		document.getElementsByName("sto_pwd")[0].value= 'store8';
		document.getElementsByName("sto_name")[0].value= '�j�b�l';
		document.getElementsByName("guy")[0].value= '����';
		document.getElementsByName("uni_num")[0].value= '76452389';
		document.getElementsByName("mobile")[0].value= '05-7658473';
		document.getElementsByName("area")[0].value= '��饫����';
		document.getElementsByName("address")[0].value= '�s�ͥ_��80��';
		document.getElementsByName("email")[0].value= 'maxchensw428@gmail.com';
		document.getElementsByName("set_time")[0].value= '2017-11-13';
		document.getElementsByName("sto_introduce")[0].value= '100%�s�A�A�c�A����u���z�̦n�B�̤ѵM���s�A�G��!! �u�|�A.�`�A�v�O�ڭ̪��g��z���A���F�ë��~��B�s�A�B�ˤ��B�M�~���Ĥ@�n�D�A��ɱ`���X�s���~�C';
		//�|���j�Y�K
	}
	
	//����TR

	//document.getElementById( "cekmobileSide ").style.visibility= "hidden"; 
	//var result_style = document.getElementById('rempointSide').style;
	//var result_style = document.getElementById('statusSide').style;

        
</script>
</html>