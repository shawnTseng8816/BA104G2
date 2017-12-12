/**
 * Plugin ipushs_countdown counts down to specific dates in the future.
 *
 * @example
 * $(".come-class").ipushs_countdown();
 *
 * @type jQuery
 *
 * @name ipushs-countdown
 * @author ipushs
 * @version 1.0
 * 
 * Documentation: http://blog.ipushs.com/642/
 * 
 */

(function($){
    $.fn.ipushs_countdown = function(options) {
        	
            var defaults = {
                daysText		: 	'days ',
                hoursText		: 	':',
                minutesText		:	':',
                secondsText		:	'',
                textAfterCount	: 	'---',
                oneDayClass		:	false,
                displayDays		: 	true,
                displayZeroDays	:	true,
                addClass		: 	false,
                callback		: 	false
            };
                 
            var options =  $.extend(defaults, options);
 
            this.each(function(){
            	var _this = $(this);
            	
		        var box = $(document.createElement('span')).addClass('ipushs-countdown-box');
		        var boxDni = $(document.createElement('span')).addClass('ipushs-dni');
		        var boxGodz = $(document.createElement('span')).addClass('ipushs-godz');
		        var boxMin = $(document.createElement('span')).addClass('ipushs-min');
		        var boxSec = $(document.createElement('span')).addClass('ipushs-sec');
				var boxSecs = $(document.createElement('span')).addClass('ipushs-secs');
		        var boxDniText = $(document.createElement('span')).addClass('ipushs-dni-text');
		        var boxGodzText = $(document.createElement('span')).addClass('ipushs-godz-text');
		        var boxMinText = $(document.createElement('span')).addClass('ipushs-min-text');
		        var boxSecText = $(document.createElement('span')).addClass('ipushs-sec-text');
		        var boxSecsText = $(document.createElement('span')).addClass('ipushs-secs-text');
		        if(options.addClass != false){
		        	box.addClass(options.addClass);
		        }
		        
		        boxGodzText.html(options.hoursText);
	            boxMinText.html(options.minutesText);
	            boxSecText.html(options.secondsText);
		        boxSecsText.html(options.secondssText);
		        box.append(boxDni).append(boxDniText).append(boxGodz).append(boxGodzText).append(boxMin).append(boxMinText).append(boxSec).append(boxSecText).append(boxSecs).append(boxSecsText);
		        _this.append(box);

				
				var nows = datech(_this.attr('now'));
		        nows = Math.floor(nows.getTime() / 100);
				
				var local_now = new Date();
		        local_now = Math.floor(local_now.getTime() / 100);
				
				var now_error = nows - local_now; //時間誤差植


            	ipushs_CountdownInit(_this,now_error);
				
				
            	
            });
            function datech(toDate)
			{
				if(!(toDate instanceof Date))
				{
					if(String(toDate).match(/^[0-9]*$/))
					{
					  toDate = new Date(toDate);
					} 
					else if( toDate.match(/([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{2,4})\s([0-9]{1,2})\:([0-9]{2})\:([0-9]{2})/) ||
						toDate.match(/([0-9]{2,4})\/([0-9]{1,2})\/([0-9]{1,2})\s([0-9]{1,2})\:([0-9]{2})\:([0-9]{2})/)
						)
					{
					  toDate = new Date(toDate);
					} 
					else if(toDate.match(/([0-9]{1,2})\/([0-9]{1,2})\/([0-9]{2,4})/) || 
							  toDate.match(/([0-9]{2,4})\/([0-9]{1,2})\/([0-9]{1,2})/)
							  )
					{
					  toDate = new Date(toDate)
					}
				} 
				else 
				{
				  throw new Error("Doesn't seen to be a valid date object or string")
				}
				return toDate;
			}

            function ipushs_CountdownInit(_this,now_error){
				
            	var now = new Date();
		        now = (Math.floor(now.getTime() / 100)) + now_error;

				
				

				var todate = datech(_this.attr('time'));	
							
		        var event =  todate.valueOf() / 100;

		        var count = (event - now);
		      
		        if(count <= 0){
		            _this.html(options.textAfterCount);
		            if(options.callback){
		            	options.callback();
		            }
		        }else if(count <= 24*60*60){
		        	setTimeout(function(){
						ipushs_CountDown(true, _this, count);
						ipushs_CountdownInit(_this,now_error);
					}, 100);
		        }else{
		        	setTimeout(function(){
						//alert('ss');
		            	ipushs_CountDown(false, _this, count);
		            	ipushs_CountdownInit(_this,now_error);
		            }, 100);
		        }
            }
            
            function ipushs_CountDown(oneDay, obj, count){
				var sekundys = count % 10
				count = Math.floor(count/10);
            	var sekundy = naprawaCzasu(count % 60);
	            count = Math.floor(count/60);
	            var minuty = naprawaCzasu(count % 60);
	            count = Math.floor(count/60);
	            var godziny = naprawaCzasu(count % 24);
	            count = Math.floor(count/24);
	            var dni = count;
	            
				if(oneDay && options.oneDayClass != false){
		            obj.addClass(options.oneDayClass);
				}
				
				if(dni == 0 && !options.displayZeroDays){
					
	            }else{
	            	obj.find('.ipushs-dni').html(dni);
	                obj.find('.ipushs-dni-text').html(options.daysText);
	            }
	            
	            obj.find('.ipushs-godz').html(godziny);
	            obj.find('.ipushs-min').html(minuty);
	            obj.find('.ipushs-sec').html(sekundy);
				obj.find('.ipushs-secs').html(sekundys);
            }
            
            function naprawaCzasu(obj){
			    s = '';
			    if(obj < 10){
			        obj = '0' + obj;
			    }
			    return obj;
			}
      }
})(jQuery);