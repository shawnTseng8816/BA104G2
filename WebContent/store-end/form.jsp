<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

</HEAD>
<BODY>


  
      

<FORM METHOD="get" ACTION="<%= request.getContextPath() %>/fakeLogin">
會員編號
	<select size="1" name="mem_num">
    	<option value="MB0000000001" >MB0000000001
    	<option value="MB0000000002" >MB0000000002
    	<option value="MB0000000003" >MB0000000003
    </select>
    <input type="hidden" name="action" value="loginm">
    <INPUT TYPE="SUBMIT" value="送出">
</form>    
    
    
    <FORM METHOD="get" ACTION="<%= request.getContextPath() %>/fakeLogin">
店家編號
    <select size="1" name="sto_num">
    	<option value="ST0000000001" >ST0000000001
    	<option value="ST0000000002" >ST0000000002
    	<option value="ST0000000003" >ST0000000003
    	<option value="ST0000000004" >ST0000000004
    	<option value="ST0000000005" >ST0000000005
    	<option value="ST0000000006" >ST0000000006
    </select>
    
    <input type="hidden" name="action" value="loginin">
    <INPUT TYPE="SUBMIT" value="送出">
  </FORM>
  
      <FORM METHOD="get" ACTION="<%= request.getContextPath() %>/fakeLogin">
後臺編號
    <select size="1" name="bm_no">
    	<option value="BM0000000001" >BM0000000001
    	<option value="BM0000000002" >BM0000000002
    	<option value="BM0000000003" >BM0000000003
    	<option value="BM0000000004" >BM0000000004
    	<option value="BM0000000005" >BM0000000005
    	<option value="BM0000000006" >BM0000000006
    </select>
    
    <input type="hidden" name="action" value="loginbak">
    <INPUT TYPE="SUBMIT" value="送出">
  </FORM>


<!-- 	<FORM METHOD="get" ACTION="/BA104G2/store_detail/store_detail.do"> -->

<!--     <INPUT TYPE="TEXT" NAME="mem_num" VALUE="MB0000000001"><p> -->

<!--     <INPUT TYPE="TEXT" NAME="sto_num" VALUE="ST0000000001"><p> -->
<!--     <INPUT TYPE="SUBMIT"> -->
<!--   </FORM> -->


</BODY>
</HTML>