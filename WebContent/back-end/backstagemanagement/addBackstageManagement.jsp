<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.backstage_management.model.*"%>

<%
BackstageManagementVO backstagemanagementVO = (BackstageManagementVO) request.getAttribute("backstagemanagementVO");
%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>��������u�s�W - �D���x</title>

<style>
	.body{
		font-family:Microsoft JhengHei;
	}
	.caption {
		font-size: 36px;
		color:#3C9682;
		font-weight:bold;
	}
	table{
/* 		border:1px solid #3C9682;  */
		table-layout:automatic;
		border-collapse: collapse;
		width: 700px;
		word-break: keep-all;
/*  		word-wrap:break-word;  */
/*   		word-break:break-all;  */
/*   		word-break: normal;  */
	}
	tr{
		text-align:left;
	}
	
	input[type="text" i] {
		height:50px;
/*  	border: none; */
		background: transparent;
		font-family: 'Roboto', sans-serif;
		font-size: 24px;
		color: rgba(0, 0, 0, 0.8);
		font-weight: 300;
	}
	input[type="submit" i]{
 		width:100%;
		-webkit-appearance:button;
		cursor:pointer;
		line-height:64px;
		left:0px;
		border-color:#3C9682;
	/* 	color:#AD974F; */
		background-color:#ffffff;
/* 		border: 3px solid rgba(0, 0, 0, 0.1); */
		border-style:solid;
		font-family:Microsoft JhengHei;
		font-weight:900;
		font-size:24px;
/* 		color: rgba(0, 0, 0, 0.2); */
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
	} // ���FIE10��xx   https://www.w3cways.com/1940.html
	input::-ms-reveal{
	  display: none; 
	} // ���FIE10��xx   https://www.w3cways.com/1940.html
	
	
</style>

</head>
<body bgcolor='white'>

	<table class="table" align="center">
		<caption>
			<div class="caption">��x���u�s�W</div>
		 	<h4><a href="login.jsp"><img src="<%=request.getContextPath()%>/img/back1.gif" width="100" height="32" border="0" title="�^����"></a></h4>
		</caption>
	</table>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<ul>
			<img src="<%= request.getContextPath() %>/img/xx.png" height="30" width="30">
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/backstagemanagement/BackstageManagementServletChi" name="form1" enctype="multipart/form-data">
		<table align="center">
			<tr>
				<td>��x�H���W��:</td>
				<td><input type="TEXT" name="bm_name" size="45" 
					 value="<%= (backstagemanagementVO==null)? "�����" : backstagemanagementVO.getBm_name()%>" /></td>
			</tr>
			
			<tr>
				<td>������X:</td>
				<td><input type="TEXT" name="bm_number" size="45"
					 value="<%= (backstagemanagementVO==null)? "0987-468532" : backstagemanagementVO.getBm_number()%>" /></td>
			</tr>
			
			<tr>
				<td>�q�l�H�c:</td>
				<td><input type="TEXT" name="bm_mail" size="45"
					 value="<%= (backstagemanagementVO==null)? "sashimi666@hotmail.com" : backstagemanagementVO.getBm_mail()%>" /></td>
			</tr>
			
			<tr>
				<td>�Ȧ�b��:</td>
				<td><input type="TEXT" name="bm_banknum" size="45"
					 value="<%= (backstagemanagementVO==null)? "7685321975" : backstagemanagementVO.getBm_banknum()%>" /></td>
			</tr>
			
			<tr>
				<td>�j�Y�K:</td>
				<td> <img id="output1"  height="365" width="588" ><br>
					 <input type="file" name="upfile" id="output" size="45" onchange="onFileSelected(event,this.id)">
					<!--  <%--  value="<%= (backstagemanagementVO==null)? "bm_img" : backstagemanagementVO.getbm_img()%>" /></td>  --%>   -->
				</td>
			</tr>
			
			<tr>
				<td>��x�H���b��:</td>
				<td><input type="TEXT" name="bm_num" size="45"
					 value="<%= (backstagemanagementVO==null)? "bm008" : backstagemanagementVO.getBm_num()%>" /></td>
			</tr>
			
			<tr>
				<td>��x�H���K�X:</td>
				<td><input type="TEXT" name="bm_pwd" size="45"
					 value="<%= (backstagemanagementVO==null)? "bm008" : backstagemanagementVO.getBm_pwd()%>" /></td>
			</tr>
			
			<tr>
				<td>��x�H���b¾���A:</td>
				<td><input type="TEXT" name="bm_jstatus" size="45"
					 value="<%= (backstagemanagementVO==null)? "�b¾" : backstagemanagementVO.getBm_jstatus()%>" /></td>
					                                <!-- �b¾�B��¾ -->
			</tr>
			
			<tr>
				<td><input type="hidden" name="action" value="insert"></td>
				<td><input type="submit" value="�e�X�s�W"></td>
			</tr>
		</table>
	</FORM>
</body>

 
<%--  <%   --%>
//    java.sql.Date set_time = null;
//    try {
// 	   set_time = storeprofileVO.getSet_time();
//     } catch (Exception e) {
//     	set_time = new java.sql.Date(System.currentTimeMillis());
//     }
<%--  %>  --%>

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

//          $.datetimepicker.setLocale('zh');
//          $('#f_date1').datetimepicker({
//  	       theme: '',              //theme: 'dark',
//  	       timepicker:false,       //timepicker:true,
//  	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
<%--  		   //value: '<%=set_time%>', // value:   new Date(),    --%>
//             disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // �h���S�w���t
//             startDate:	            '2017/07/10',  // �_�l��
//             minDate:               '-1970-01-01', // �h������(���t)���e
//             maxDate:               '+1970-01-01'  // �h������(���t)����
//   �@       });
        
        
   
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
	
	//����TR

	//document.getElementById( "cekmobileSide ").style.visibility= "hidden"; 
	//var result_style = document.getElementById('rempointSide').style;
	//var result_style = document.getElementById('statusSide').style;

        
</script>
</html>