(function( $ ){

  var signMethods = {
    BtHide : function (id) {
		var Div = document.getElementById(id);
		if (Div) {
			Div.style.display = "none"
		}
	},
   BtShow : function (id) {
		var Div = document.getElementById(id);
		if (Div) {
			Div.style.display = "block"
		}
	},
    BtPopload : function (showId) {
		// 高度减去 4px，避免在页面无滚动条时显示遮罩后出现流动条
		var h = (Math.max(document.documentElement.scrollHeight,
				document.documentElement.clientHeight) - 4)
				+ 'px';
		var w = document.documentElement.scrollWidth + 'px';
		var popCss = "background:#000;opacity:0.3;filter:alpha(opacity=30);position:absolute;left:0;top:0;overflow:hidden;border:0"// 遮罩背景
		var rePosition_mask = function() {
			pop_Box.style.height = h;
			pop_Box.style.width = w;
			pop_Iframe.style.height = h;
			pop_Iframe.style.width = w;
			if (document.documentElement.offsetWidth < 950) {
				// 防止正常宽度下点击时 在 ff 下出现页面滚动到顶部
				document.documentElement.style.overflowX = "hidden";
			}
		}
		var exsit = document.getElementById("popBox");
		if (!exsit) {
			var pop_Box = document.createElement("div");
			pop_Box.id = "popBox";
			document.getElementsByTagName("body")[0].appendChild(pop_Box);
			pop_Box.style.cssText = popCss;
			pop_Box.style.zIndex = "10";
			var pop_Iframe = document.createElement("iframe"); // 这里如果用 div
																// 的话，在 ie6 不能把
																// <select> 遮住
			pop_Iframe.id = "popIframe";
			document.getElementsByTagName("body")[0].appendChild(pop_Iframe);
			pop_Iframe.style.cssText = popCss;
			pop_Iframe.style.zIndex = "9";
			rePosition_mask();
		}
		signMethods.BtShow("popIframe");
		signMethods.BtShow("popBox");
		signMethods.BtShow(showId);
		var pop_Win = document.getElementById(showId);
		pop_Win.style.position = "absolute";
		pop_Win.style.zIndex = "11";
		var rePosition_pop = function() {
			pop_Win.style.top = document.documentElement.scrollTop
					+ document.body.scrollTop
					+ document.documentElement.clientHeight / 2
					- pop_Win.offsetHeight / 2 + 'px';
			pop_Win.style.left = document.documentElement.scrollLeft
					+ document.body.scrollLeft
					+ document.documentElement.clientWidth / 2
					- pop_Win.offsetWidth / 2 + 'px';
		}
		rePosition_pop();
		window.onresize = function() {
			w = document.documentElement.offsetWidth + 'px'; // 使用
																// scrollWidth
																// 不能改变宽度
			rePosition_mask();
			rePosition_pop();
		}
		window.onscroll = function() {
			rePosition_pop();
		}
	},
    SignShow : function (sign_user) {
		
		$("body").append("<div id='sign_box' style='display: none; z-index: 999; text-align: center; background: #FFF; padding: 20px; border: 5px solid #95B8E7;'><input type='hidden'   name='sign_user' id='sign_user'/>	请输入签名密码：<input type='password' name='sign_pwd'  id='sign_pwd' maxlength='18' /> <input type='button' value='确定' onclick=\"$(this).sign('sign_submit_base64')\"/> <input type='button' value='取消' onclick='SignHide()' />	<div id='sign_mes' style='padding-top: 10px;font: red;'></div></div>");
		
		$('#sign_user').val(sign_user);		
		signMethods.BtPopload("sign_box");
	},
    SignHide : function () {
		signMethods.BtHide("sign_box");
		signMethods.BtHide("popBox");
		signMethods.BtHide("popIframe");
	},
    sign_submit : function (url){		
		$('#sign_mes').html('');
		if(!$('#sign_user').val()){
			$('#sign_mes').html('签名用户为空，请检查！');
			return false;
		}
		
		if($('#sign_pwd').val()){
			$.post("UserSign!sign.action", {sign_user: $('#sign_user').val(), sign_pwd: $('#sign_pwd').val()},
					   function(data){
					     if(data.success=='1'){					    	 
					    	 $('#sign_img').html(data.sign_img);
					    	
					    	 signMethods.SignHide();
					     }else{
					    	 $('#sign_mes').html(data.message);
					     }
						});
		}else{
			$('#sign_mes').html('密码不能为空，请重新输入！');
			return false;
		}
	},
    sign_submit_base64 : function (url){		
		$('#sign_mes').html('');
		if(!$('#sign_user').val()){
			$('#sign_mes').html('签名用户为空，请检查！');
			return false;
		}
		
		if($('#sign_pwd').val()){
			$.post("UserSign!signBase64.action", {sign_user: $('#sign_user').val(), sign_pwd: $('#sign_pwd').val()},
					   function(data){
					     if(data.success=='1'){					    	 
					    	 $('#sign_img').html(data.sign_img);
					    	 alert($('#sign_img').html());
					    	 signMethods.SignHide();
					     }else{
					    	 $('#sign_mes').html(data.message);
					     }
						});
		}else{
			$('#sign_mes').html('密码不能为空，请重新输入！');
			return false;
		}
	}
  };

  $.fn.sign = function( method ) {	
    if ( signMethods[method] ) {
      return signMethods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    } else if ( typeof method === 'object' || ! method ) {
      return signMethods.init.apply( this, arguments );
    } else {
      $.error( 'Method ' +  method + ' 不存在！' );
    }    

  };

})( jQuery );