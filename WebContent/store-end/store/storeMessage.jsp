<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store_profile.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<jsp:useBean id="memproSvc" class="com.member_profile.model.MemberProfileService"/>


<%	

	StoreProfileService StoreProSvc  = new StoreProfileService ();
		List list = new ArrayList();
		String sto_num =(String)session.getAttribute("sto_num");
		list =  StoreProSvc.getStoComment(sto_num);
		request.setAttribute("StoreProSvc", list);

%>

<html lang="">
	<head>

		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>Title Page</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/3dbtn.css" />
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.all.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.min.js"></script>
<style type="text/css">
	textarea{/* Text Area 固定大小*/
 max-width:500px;
 max-height:200px;
 min-width:500px;
 min-height:200px;


}

			
/*評價星星*/	
.star-vote{		
                width:170px;
                height:28px;
                position:relative;
                overflow:hidden;
            }
            
            .star-vote>span{
                position:absolute;
                width:170px;
                height:28px;
                background-size:cover;
            }
            
            .add-star{
                background-image:url("<%=request.getContextPath()%>/img/stars.png");
            }
            
            .del-star{
                background-image:url("<%=request.getContextPath()%>/img/stars1.png");

	}	
   
</style>
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

         
		<div class="container">
  <div class="row">
    <div class="col-md-8">
      <h2 class="page-header">店家評論</h2>
        <section class="comment-list">
          <!-- First Comment -->
        
 <%@ include file="pages/page1.file" %> 	
 
          <c:forEach var="storeCommentVO" items="${StoreProSvc}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
          
          <article class="row">
          
<!--           大頭貼 -->
            <div class="col-md-2 col-sm-2 hidden-xs">
            <a href='#memberProfile${storeCommentVO.mem_num}' data-toggle="modal" >
              <figure class="thumbnail"  >
                <img class="img-responsive " src="<%=request.getContextPath()%>/GetPicEric?mem_num=${storeCommentVO.mem_num}" />
                <figcaption class="text-center">${memproSvc.getOneMemInfo(storeCommentVO.mem_num).mem_name}</figcaption>
              </figure>
              </a>
            </div>
            <div class="col-md-10 col-sm-10">
              <div class="panel panel-default arrow left">
               <div class="panel-body">
               
                        	      	
   <h4 class="media-heading " style="font-weight:bold;" >${storeCommentVO.com_title}</h4>
         		  
          <p style="margin-top: 30px;" >${storeCommentVO.commentt}</p>
          <ul class="list-inline list-unstyled" style="margin-top: 30px;" >
  			<li><i class="glyphicon glyphicon-calendar"></i>${storeCommentVO.com_time}</li>
            <li>|</li>
            <li>
            <div id="star_con" class="star-vote">
            <span id="add_star" class="add-star"></span>
            <span id="del_star" class="del-star"></span>
        	</div>
            </li>
            <li>|</li>
             <li>  
           <p class="text-right"><a href='#${storeCommentVO.com_num}' data-toggle="modal" class="btn btn-danger  ">檢舉</a></p>
           </li>
			</ul>  
                </div>
              </div>
            </div>
          </article>
          
          <!--   			檢舉燈箱 -->
          	<form action="" method=post  >
          	<div class="modal fade" id="${storeCommentVO.com_num}">  
			<div class="modal-dialog ">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 class="modal-title text-center">檢舉原因</h3>
					</div>
					<div class="modal-body">
					<p ><h4>被檢舉的會員名稱   : ${memproSvc.getOneMemInfo(storeCommentVO.mem_num).mem_name}</h4></p>		
					<textarea class="form-control" id="content" name="content"></textarea>		
					</div>
					<div class="modal-footer">			
					<input type="button" value="送出" class="btn stoinsertReport btn-green" >
					<input name="mem_num" type="hidden" value="${storeCommentVO.mem_num}"> 
					<input name="com_num" type="hidden" value="${storeCommentVO.com_num}"> 
					<input name="action" type="hidden" value="insertReport"> 
						<button type="button" class="btn btn-danger" data-dismiss="modal">關閉</button>
					</div>
				</div>
			</div>
		</div>
		</form>
  
  
  		<c:set var="memid" value="${storeCommentVO.mem_num}"/>
											
		<%@ include file="/front-end/MemberDetail/MemberDetail.jsp" %>
  
		</c:forEach>
		 <c:if test="${StoreProSvc.isEmpty()}">
       	<div class="text-center" style="color:red; font-size:48px;">暫無評論!!</div>
       </c:if>   
<!-- Sixth Comment Reply -->
      
        
          <%@ include file="pages/page2.file" %>
        
        </section>
    </div>
  </div>
    </div>


	<!--========================== 功能放這邊 ↑↑↑↑↑↑↑=======================================-->			
				</div><!-- class="block-center panelheight" -->			
			</div><!-- class="col-xs-12 col-sm-8 col-sm-offset-3" -->
		</div><!-- div class="row" -->
	</div><!-- div class="container-fluid" -->
	
	<jsp:include page="/store-end/store_foot.jsp" />
  
  
  
 </body>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">

// 檢舉用************************
$(function(){
	$('.stoinsertReport').on('click', function(){

		var content = $(this).parent().prev().children('textarea').val();
		var mem_num = $(this).next().val();
		var com_num = $(this).next().next().val();
		var action =  $(this).next().next().next().val();
		$.ajax({
			 type: "POST",
			 url: "<%=request.getContextPath()%>/StoreProfileServletEric",
			 data: {"mem_num": mem_num, "com_num":com_num, "action":action, "content":content },
			 dataType: "json",
			 async: false, // true預設是非同步，也就是"不會等待"; false是同步，"會等待"
			 success: function(data){
				  console.log(data.message);
				  if(data.message != null){
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



function comment(){
	<c:forEach var="storeCommentVO" items="${StoreProSvc}" varStatus = "s">
		stars(${storeCommentVO.stars} ,${s.index});
	</c:forEach>
}

function stars(n,v){
	var con_wid = document.getElementsByClassName("add-star")[v].offsetWidth;
	var del_star= document.getElementsByClassName("del-star")[v];
             var del_move=(n*con_wid)/5;
             del_star.style.backgroundPosition=-del_move+"px 0px";
             del_star.style.left=del_move+"px";
}
window.onload=comment;
</script>
	

</html>