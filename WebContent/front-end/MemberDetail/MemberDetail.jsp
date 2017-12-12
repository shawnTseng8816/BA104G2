<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_profile.model.*"%>
<%@ page import="com.friend_list.model.*"%>
<%
	MemberProfileVO memberProfileVO = new MemberProfileService().getMemberProfileByMem_num((String)pageContext.getAttribute("memid"));	
// 	MemberProfileVO memberProfileVO = (MemberProfileVO) request.getAttribute("memberProfileVO");
	FriendListVO friendListVO = (FriendListVO) request.getAttribute("friendListVO");
	
	pageContext.setAttribute("memberProfileVO", memberProfileVO);
	
%>


	<style type="text/css">
			.table-user-information > tbody > tr {
			    border-top: 1px solid rgb(221, 221, 221);
			}

			.table-user-information > tbody > tr:first-child {
			    border-top: 0;
			}


			.table-user-information > tbody > tr > td {
			    border-top: 0;
			}
			.toppad{
				margin-top:20px;
			}
			.friends{
				margin-top:100px;
			}
			
			.btn3d {
		          position:relative;
		          top: -6px;
		          border:0;
		           transition: all 40ms linear;
		           margin-top:10px;
		           margin-bottom:10px;
		           margin-left:2px;
		           margin-right:2px;
		      }
		      .btn3d:active:focus,
		      .btn3d:focus:hover,
		      .btn3d:focus {
		          -moz-outline-style:none;
		               outline:medium none;
		      }
		      .btn3d:active, .btn3d.active {
		          top:2px;
		      }

		      .btn3d.btn-success {
		          box-shadow:0 0 0 1px #31c300 inset, 0 0 0 2px rgba(255,255,255,0.15) inset, 0 8px 0 0 #5eb924, 0 8px 8px 1px rgba(0,0,0,0.5);
		          background-color:#78d739;
		      }

		      .btn3d.btn-success:active, .btn3d.btn-success.active {
		          box-shadow:0 0 0 1px #30cd00 inset, 0 0 0 1px rgba(255,255,255,0.15) inset, 0 1px 3px 1px rgba(0,0,0,0.3);
		          background-color: #78d739;
		      }

		      .btn3d.btn-danger {
		          box-shadow:0 0 0 1px #b93802 inset, 0 0 0 2px rgba(255,255,255,0.15) inset, 0 8px 0 0 #AA0000, 0 8px 8px 1px rgba(0,0,0,0.5);
		          background-color:#D73814;
		      }

		      .btn3d.btn-danger:active, .btn3d.btn-danger.active {
		          box-shadow:0 0 0 1px #b93802 inset, 0 0 0 1px rgba(255,255,255,0.15) inset, 0 1px 3px 1px rgba(0,0,0,0.3);
		          background-color: #D73814;
		      }
		      .btn3d.btn-info {
	          	box-shadow:0 0 0 1px #1aa3ff inset, 0 0 0 2px rgba(255,255,255,0.15) inset, 0 8px 0 0 #0099ff, 0 8px 8px 1px rgba(0,0,0,0.5);
	          	background-color:#4db8ff;
	      	}
	
	      	.btn3d.btn-info:active, .btn3d.btn-success.active {
	          	box-shadow:0 0 0 1px #1aa3ff inset, 0 0 0 1px rgba(255,255,255,0.15) inset, 0 1px 3px 1px rgba(0,0,0,0.3);
	          	background-color:#4db8ff;
	      	}

		      .btn.active {                
		        display: none;    
		      }
		      
		      #head{
		      	padding:20px;
		      }


			  #head{
				width: 100%;
			  }
			  td{
			   	font-size: 15px;
			  }
	</style>

		

		<div class="modal fade" id="memberProfile${pageScope.memid}">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="row">
						<div class="col-xs-12 col-sm-5">
							<img id="head" src="/BA104G2/GetPicShawn?mem_num=${memberProfileVO.getMem_num()}&getType=memberProfile" class="img-circle img-responsive">
							
						</div>
						<div class="col-xs-12 col-sm-7">
						<br>
						
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<table class="table-user-information">
	                    		<tbody>
	                    			<c:if test='${sessionScope.session_mem_num==mem_num || mem_bn_no!=null}' var="myself">
		                     			
		                      			<tr>
		                        			<td><h4>姓名:</h4></td>
		                       				<td>${memberProfileVO.getMem_name()}</td>
		                      			</tr>
						                <tr>
						                    <td><h4>帳號:</h4></td>
						                    <td>${memberProfileVO.getMem_acc()}</td>
						                </tr>
					                    <tr>
					                     	<td><h4>性別:</h4></td>
					                       	<td>${memberProfileVO.getSex()}</td>
					                   	</tr>
			                      		<tr>
			                        		<td><h4>年齡:</h4></td>
			                        		<td>${memberProfileVO.getAge()}</td>
			                      		</tr>
			                      		
		                      		</c:if>
		                      		<c:if test ='${!myself}'>
			                      		<c:if test ='${isfriend.equals("Y")}' var="friend">
					                      	<tr>
					                        	<td><h4>姓名:</h4></td>
					                       		<td>${memberProfileVO.getMem_name()}</td>
					                      	</tr>
									        <tr>
									            <td><h4>帳號:</h4></td>
									            <td>${memberProfileVO.getMem_acc()}</td>
									        </tr>
								            <tr>
								                <td><h4>性別:</h4></td>
								                <td>${memberProfileVO.getSex()}</td>
								            </tr>
						                  	<tr>
						                      	<td><h4>年齡:</h4></td>
						                       	<td>${memberProfileVO.getAge()}</td>
						                   	</tr>
						                   	
				                   		</c:if>
			                   			<c:if test="${!friend}">
			                   				<tr>
					                        	<td><h4>姓名:</h4></td>
					                       		<td>${memberProfileVO.getMem_name()}</td>
					                      	</tr>
									        <tr>
									            <td><h4>帳號:</h4></td>
									            <td>${memberProfileVO.getMem_acc()}</td>
									        </tr>
								            <tr>
								                <td><h4>性別:</h4></td>
								                <td>${memberProfileVO.getSex()}</td>
								            </tr>
						                  	<tr>
						                      	<td><h4>年齡:</h4></td>
						                       	<td>${memberProfileVO.getAge()}</td>
						                   	</tr>
			                   			</c:if>
			                   		</c:if>
	                    		</tbody>
	                  		</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		