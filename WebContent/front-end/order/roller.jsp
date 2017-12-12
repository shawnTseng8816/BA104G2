<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Examples</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.all.min.js"></script>
<script src="<%=request.getContextPath()%>/js/dist/sweetalert2.min.js"></script>
<link href="<%=request.getContextPath()%>/css/game/animator.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/game/dmaku.css" />
<style>
.item1 .clipped-box {
    display: none;
    position: absolute;
    bottom: 130px;
    left: 140px;
    height: 540px;
    width: 980px;
}

</style>

</head>
<jsp:include page="/front-end/member_top.jsp" flush="true" />
<jsp:include page="/front-end/coupon_notify.jsp" />
<body>
<div class="main" >
	
	<div style="margin-left:350px" class="clickme"><img  src="<%=request.getContextPath()%>/img/game/right.gif" /></div>
	<div class="item1 ">

		<div class="main" style="margin-top:300px;"></div>
		<div class="kodai">
			<img  src="<%=request.getContextPath()%>/img/game/cupfull.png"  class="getGame full" />		
			<input type="hidden" class="or_num" value="${or_num}">
			<img src="<%=request.getContextPath()%>/img/game/cupempty.png" class="empty" />
		</div>
		<div class="clipped-box">

		</div>

	</div>
	<p id="html"></p>
</div>
	<jsp:include page="/front-end/Footer.jsp" flush="true" />
</body>
<script type="text/javascript">


$(function(){
	$('.getGame').on('click', function(){
		var getPoint = 'getPoint';
		var or_num = $('.or_num').val();
		$.ajax({
			 type: "POST",
			 url: "<%=request.getContextPath()%>/ValueRecordServlet",
			 data: {"action":getPoint,"or_num":or_num},
			 dataType: "json",
			 async: false, // true預設是非同步，也就是"不會等待"; false是同步，"會等待"
			 success: function (data){
				if(data.canPass === "Yes"){
					$('.empty').addClass("Shake");
					show2();
					message = data.message;		
					console.log(data.message);
				}else{
					alert("已抽過獎囉 !!")
				}	
		     },
            error: function(){alert("AJAX-class發生錯誤囉!")}
        })

	});
});

function sh(){
	swal({
		  position: 'center',
		  type: 'success',
		  title: message,
		  showConfirmButton: true,
		  timer: 4000
		})
		
	setTimeout('callback()',4000);	

}

function callback(){
	
	window.location = "<%=request.getContextPath()%>/front-end/index.jsp";
}



  function show2(){
        $t = $('.item1');
        //顯示的數量
   
      		
        var amount = 10;
        var width = $t.width() / amount;
        var height = $t.height() / amount;
        var totalSquares = Math.pow(amount, 2);
        var y = 0;
        var index = 1;
        for (var z = 0; z <= (amount * width) ; z = z + width) {
            $('<img class="clipped" width="30px" src="<%=request.getContextPath()%>/img/game/coin' + index + '.png" />').appendTo($('.item1 .clipped-box'));
          ;
            if (z === (amount * width) - width) {
                y = y + height;
                z = -width;
            }
            if (index >= 6) {
                index = 1;
            }
            index++;
            if (y === (amount * height)) {
                z = 9999999;
            }
           
        }
    }

    function rand(min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }
    var first = false,
        clicked = false;
    // On click
    $('.item1 div.kodai').on('click', function () {

        if (clicked === false) {
        	$('.clickme').css({
        		'visibility': 'hidden'
        	});
            $('.full').css({
                'display': 'none'
            });
            $('.empty').css({
                'display': 'block'
            });
            clicked = true;

            $('.item1 .clipped-box').css({
                'display': 'block'
            });
            // Apply to each clipped-box div.
            $('.clipped-box img').each(function () {
            	
                var v = rand(120, 90),
                    angle = rand(80, 89), 
                    theta = (angle * Math.PI) / 180, 
                    g = -9.8; 

                // $(this) as self
                var self = $(this);
                var t = 0,
                    z, r, nx, ny,
                    totalt =10;
                var negate = [1, -1, 0],
                    direction = negate[Math.floor(Math.random() * negate.length)];

                var randDeg = rand(-5, 10),
                    randScale = rand(0.9, 1.1),
                    randDeg2 = rand(30, 5);

                // And apply those
                $(this).css({
                    'transform': 'scale(' + randScale + ') skew(' + randDeg + 'deg) rotateZ(' + randDeg2 + 'deg) '
                });

                // Set an interval
                z = setInterval(function () {
                    var ux = (Math.cos(theta) * v) * direction;
                    var uy = (Math.sin(theta) * v) - ((-g) * t);
                    nx = (ux * t);
                    ny = (uy * t) + (0.35 * (g) * Math.pow(t, 2));
                    if (ny < -120) {
                        ny = -120;
                    }
                    //$("#html").html("g:" + g + "bottom:" + ny + "left:" + nx + "direction:" + direction);
                    $(self).css({
                        'bottom': (ny) + 'px',
                        'left': (nx) + 'px'
                    });
                    // Increase the time by 0.10
                    t = t + 0.10;

                  
                    if (t > totalt) {
                        clicked = false;
                        first = true;
                        clearInterval(z);
                    }
                }, 20);
            });
        }
    });
    r = setInterval(function () {
        if (first === true) {  
            first = false;
            setTimeout('sh()',1000);
        }
    }, 200);


</script>

</html>