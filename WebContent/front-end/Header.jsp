<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="navbars navbar-fixed-top">
	<div class="container">
		<div class="row ">
			<ul class="nav navbar-nav navbar-right ">
				<li><a class="item-color" href="#!"> <span
						class="glyphicon glyphicon-log-in icons"></span> 登入
				</a></li>
				<li><a class="item-color" href="#"> <span
						class="glyphicon glyphicon-pencil icons"></span> 註冊
				</a></li>
				<li class="dropdown"><a href="#"
					class="dropdown-toggle item-color" data-toggle="dropdown"> <span
						class="glyphicon glyphicon-user icons"></span> 會員專區

				</a>
					<ul class="dropdown-menu">
						<li><a href="#">訂單管理</a></li>
						<li><a href="#">寄杯</a></li>
						<li><a href="#">點數</a></li>
						<li><a href="#">集點卡</a></li>
						<li><a href="#">最愛店家</a></li>
						<li><a href="#">評論</a></li>
						<li><a href="#">好友</a></li>
						<li><a href="#">個人資料</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</div>



<!-- LOGO + Serch Bar + hotserch + 訂單管理 + 附近店家 + 折價券 ============ -->
<div class="container">
	<div class="row">

		<!-- LOGO -->
		<div class="col-xs-12 col-sm-3 ">
			<img src="/BA104G2/img/LOGO_w_285x150.png">
		</div>

		<!-- serch bar + hot key-->
		<div class="col-xs-12 col-sm-5 ">


			<!-- search bar -->
			<div class="input-group area70">
				<input type="text" class="form-control input-lg"
					placeholder="請輸入關鍵字"> <span class="input-group-btn">
					<button class="btn btn-lg" type="button">
						<span class=" glyphicon glyphicon-search"></span>
					</button>
				</span>
			</div>


			<!-- hot keys 熱門字 24個中文字滿-->

			<div class="hotkey">
				<ul class="list-inline">
					<li>熱門字</li>
					<li>熱門字</li>
					<li>熱門字</li>
					<li>熱門字</li>
					<li>熱門字</li>
					<li>熱門字</li>
					<li>熱門字</li>

				</ul>
			</div>
		</div>



		<!-- 訂單管理 btn+ 附近店家 btn + 折價券btn -->
		<div class="col-xs-12 col-sm-4 ">

			<button type="button" class="btn btn-green area70 btn-lg ">訂單管理</button>
			<button type="button" class="btn btn-green area70 btn-lg">附近店家</button>
			<button type="button" class="btn btn-org area70 btn-lg">瘋折價券</button>

		</div>
	</div>
</div>

