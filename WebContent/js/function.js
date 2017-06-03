
var HOST = 'http://hener.me';
var HOSTIMG = 'http://hener.me/img';
var HOSTAVATAR = 'http://hener.me/img/avatar';
var HOSTGOODIMG = 'http://hener.me/img/goodimg';
var HOSTTBL = 'http://hener.me/img/tbl';
/**
 * 回车绑定
 */
$('#key').keydown(function(event){
	var code = event.keyCode || event.which;
	if(code == 13){
		$('#search').click();
	}
});
$('#input_sign_pwd').keydown(function(event){
	var code = event.keyCode || event.which;
	if(code == 13){
		$('#btn_sign').click();
	}
});
$('#input_regist_pwd').keydown(function(event){
	var code = event.keyCode || event.which;
	if(code == 13){
		$('#btn_regist').click();
	}
});
$('#input_findpwd_pwd').keydown(function(event){
	var code = event.keyCode || event.which;
	if(code == 13){
		$('#btn_findpwd').click();
	}
});
$('#input_setpwd_new').keydown(function(event){
	var code = event.keyCode || event.which;
	if(code == 13){
		$('#btn_setpwd').click();
	}
});
$('.closebtn').click(function(){
	$('.contactbox').hide();
});

/**
 * 点击登陆按钮
 */
$('#btn_sign').click(function(){
	var phone = $('#input_sign_phone').val();
	var pwd = $('#input_sign_pwd').val();
	if(!checkPhone(phone)){
		warnIpt($('#input_sign_phone'));
		return;
	}
	if(!checkPwd(pwd)){
		warnIpt($('#input_sign_pwd'));
		return;
	}else{
		sign(phone,pwd);
	}
});
$('#input_sign_phone').keyup(function(){
	if(checkPhone($(this).val())){
		removeWarnIpt($(this));
	}
});
$('#input_sign_pwd').keyup(function(){
	if(checkPwd($(this))){
		removeWarnIpt($(this));
	}
});
/**
 * 点击注册按钮
 */
$('#btn_regist').click(function(){
	var phone = $('#input_regist_phone').val();
	var pwd = $('#input_regist_pwd').val();
	if(!checkPhone(phone)){
		warnIpt($('#input_regist_phone'));
		return;
	}
	if(!checkPwd(pwd)){
		warnIpt($('#input_regist_pwd'));
		return;
	}else{
		regist(phone,pwd);
	}
});
$('#input_regist_phone').keyup(function(){
	if(checkPhone($(this).val())){
		removeWarnIpt($(this));
	}
});
$('#input_regist_pwd').keyup(function(){
	if(checkPwd($(this).val())){
		removeWarnIpt($(this));
	}
});
/**
 * 切换登录注册
 */
$('#toregist').click(function(){
	closeSignBox();
	showRegistBox();
});
$('#tosign').click(function(){
	closeRegistBox();
	showSignBox();
});

/***切换找回密码*****/
$('.link-findpwd').click(function(){
	closeRegistBox();
	closeSignBox();
	$('#findpwdBox').show();
});
/***找回密码*****/
 
document.getElementById("input_findpwd_codebtn").onclick=function(){getCode(this);} 
$('#btn_findpwd').click(function(){
	setPwdByCode();
});

/**上货链接*****/
$('.a-upgood').click(function(){
	if(isSigned()){
		window.open("member.html#up-good");
	}else{
		showSignBox();
	}
});
$('.a-upneed').click(function(){
	if(isSigned()){
		window.open("member.html#up-need");
	}else{
		showSignBox();
	}
});

/**关闭发送消息框******/
$('#sendmsgbtn-nook').click(function(){
	$('.sendmsgbox').hide();
});

/****搜索链接****/
$('body').on('click','.search-category',function(){
	window.location.href=HOST+'#category-'+$(this).html();
	
	
});
$('body').on('click','.search-key',function(){
	window.location.href=HOST+'#key-'+$(this).html();
});
$('#search').click(function(){
	//window.location.href=HOST+'#key-'+$('#key').val();
	window.open(HOST+'#key-'+$('#key').val(),'_self');
	
});




/***********/


function setCookie(cname, cvalue, exdays) {
	var d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	var expires = "expires=" + d.toUTCString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}
function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ')
			c = c.substring(1);
		if (c.indexOf(name) != -1)
			return c.substring(name.length, c.length);
	}
	return "";
}
function isSigned() {
	a = $.ajax({
		url : "isSigned.UserServlet",
		async : false
	});
	b = $.parseJSON(a.responseText);
	if (b.success) {
		return true;
	}
	return false;
}
function clearCookie(name) {
	setCookie(name, "", -1);
}
function showSignBox() {
	$('#signBox').css('display', 'block');
	var phone = getCookie('phone');
	var pwd = getCookie('pwd');
	$('#input_sign_phone').val(phone);
	$('#input_sign_pwd').val(pwd);

}
function closeSignBox() {
	$('#signBox').css('display', 'none');
}
function showRegistBox() {
	$('#registBox').css('display', 'block');
}
function closeRegistBox() {
	$('#registBox').css('display', 'none');
}
function closeFindpwdBox(){
	$('#findpwdBox').css('display', 'none');
}
/**
 * 检查电话号码,只要十一位的以1开头的纯数字即可
 * @returns 返回true or false
 */
function checkPhone(phone){
	
	var reg = /^1\d{10}$/;
	if(reg.test(phone)){
		return true;
	}
	return false;
}
function checkPwd(pwd){
	if(pwd.length>=1&&pwd.length<16){
		return true;
	}
	return false;
}
function warnIpt(element){
	element.css('border-color','red');
}
function removeWarnIpt(element){
	element.css('border-color','#999');
}
//function alert(){}
function getSignedInfo(){
	$.getJSON("getSignedInfo.UserServlet",function(json){
		if(!json.success){
			showAlert("未登陆");
		}else{
			showSignedUser(json);
			showMsgCount((json.msgCount)+(json.sysMsgCount));
			showMsgList(json);
		}
	});
}
function signOut() {
	a = $.ajax({
		url : "signOut.UserServlet",
		async : false
	});
}
function hint(){}
function showSignedUser(json){
	$('#user').css('display', 'none');
	$('#signed').css('display', 'inline-block');
	$('#signed-user').css('display', 'inline-block');
	if(json.name == '二站新人'){
		$('#signed-name').html(json.name);
	}else{
		$('#signed-name').html(json.name);
	}
	if(json.avatar == 'avatar.jpg'){
		$('#signed-avatar').attr('src',HOSTAVATAR+'/avatar.jpg');
	}else{
		
		$('#signed-avatar').attr('src',HOSTAVATAR+'/'+json.avatar);
	}
	
}
function showMsgCount(msgCount){
	if(msgCount>0){
		$('#msgcount').html(msgCount);
		$('#msgcount').show();
		
	}else{
		$('.msglist').html('你还没有新的消息哦');
	}
}
function showMsgList(json){
	$('#span-msgcount').html((json.msgCount)+(json.sysMsgCount));
}
function sign(phone, pwd) {
	$.getJSON("sign.UserServlet?phone=" + phone + "&&pwd=" + pwd,function(json){
		if(!json.success){
			showAlert('登陆失败');
		}else{
			closeSignBox();
			showAlert('登陆成功');
			showSignedUser(json);
			showMsgCount((json.msgCount)+(json.sysMsgCount));
			showMsgList(json);
		}
	});
}
function regist(phone,pwd){
	$.getJSON("registed.UserServlet?phone=" + phone,function(json){
		if(json.registed){
			showAlert('该电话已经被注册，您可以通过短信找回');
		}else{
			$.getJSON("regist.UserServlet?phone=" + phone + "&&pwd=" + pwd,function(json){
				if(!json.success){
					showAlert('注册失败');
				}else{
					closeRegistBox();
					showAlert('注册成功');
					sign(phone,pwd);
				}
			});
		}
	});
	
}

function createQueryJson() {
	var key = $('#key').val();
	if (key == '') {
		key = 'all'
	}
	var category = $('.active.category-item').attr('id');
	
	var nokey = false;
	if ($('#nokey').hasClass('active')) {
		nokey = true;
	}
	var nolocked = false;
	if ($('#nolocked').hasClass('active')) {
		nolocked = true;
	}
	var order = $('.active.order').attr('id');
	var sort = 'desc';
	if(order == 'price'){
		sort = 'asc';
	}
	var json = {
		'key' : key,
		'category' : category,
		'pageNo' : 1,
		'nokey' : nokey,
		'nolocked' : nolocked,
		'order' : order,
		'sort' : sort
	};
	return json;
}
function postQuery(json) {
	json = JSON.stringify(json);
	$.post('getGoods.GoodServlet',{json:json},function(data){
		
	});
}


////////////////////////////////////////////member/////////////////////
function fillCount(json){
	$('#goodcount').html(json.goodCount);
	$('#needcount').html(json.needCount);
	$('#selledcount').html(json.selledCount);
	$('#lockedcount').html(json.lockedCount);
	$('#belockedcount').html(json.belockedCount);
	$('#historycount').html(json.historyCount);
}
function fillMember(json){
	if(json.name == '二站新人'){
		$('#member-name').html(json.name);
	}else{
		$('#member-name').html(json.name);
	}
	if(json.info == '这是系统自带的签名'){
		$('#member-info').html(json.info);
	}else{
		$('#member-info').html(json.info);
	}
	if(json.avatar == 'avatar.jpg'){
		$('#member-avatar').attr('src',HOSTAVATAR+'/avatar.jpg');
	}else{
		$('#member-avatar').attr('src',HOSTAVATAR+'/'+json.userid+'.jpg');
	}
	
}
function updateRight(hash){
	hash = hash.split('#')[1];
	$('.item').removeClass('active');
	$('#'+hash).addClass('active');
	$.ajax({
		url:hash+'.html',
		success:function(data){

			$('.mainer-r').html(data);
		}
	});
}///////////////////////////////////////////填充/////////////////////////////
function fillHomeGoodList(data) {
	$('.goodlist').html('');
	var json = $.parseJSON(data);
	var goodlist = json.list;
	var html = [];
	for (i = 0; i < goodlist.length; i++) {
		var goodurl = HOST+"/detail.html#" + goodlist[i].goodid;
		var tblurl = '"' + HOSTTBL+'/' + goodlist[i].userid+ "/" + goodlist[i].tbl + '"';
		var detail = goodlist[i].info;
		var userurl = '"' + HOST+"/user/" + goodlist[i].userid+ ".html" + '"';
		var avatarurl = '"' + HOSTAVATAR+"/"+ goodlist[i].avatar + '"';
		var price = goodlist[i].price;
		var time = goodlist[i].gmt_modified.substr(0,goodlist[i].gmt_modified.length-5);
		var name = goodlist[i].name;
		var times = goodlist[i].clicktimes;
		html.push('<div class="goodbox">');
		html.push('<a target="_blank"  href=' + goodurl + '>');
		html.push('<div class="goodpic">');
		html.push('<img src=' + tblurl + '>');
		html.push('<div class="cov">');
		html.push('<p class="detail">' + detail + '</p>');
		html.push('<p class="times">浏览<span>+'+times+'</span>次</p>');
		html.push('</div></div></a>');
		html.push('<div class="info">');
		html.push('<a href="">');
		html.push('<div class="user"><img src=' + avatarurl + '><span>' + name + '</span></div>');
		html.push('</a>');
		html.push('<div class="price"><span>￥<em>' + price+ '</em></span></div></div>');
		html.push('<div class="time">' + time + '</div></div>');
		$('.goodlist').append(html.join(''));
		html = [];
	}
}
function fillHomeGoodPageModel(data){
	$('.page-good').html('');
	var json = $.parseJSON(data);
	var pageSize = json.pageSize;
	var pageNo = json.pageNo;
	var allCount = json.allcount;
	var pageCount = Math.ceil(allCount/pageSize);
	var html = [];
	if(allCount<=pageSize){
		return;
	}	
	if(pageCount<=5){
		for(i=1;i<=pageCount;i++){
			html.push('<a href="#" pageNo=' + "'" +i+"'" + 'class="goodpageitem page-item">'+ i + '</a>');
		}
		$('.page-good').append(html.join(''));
		$('.goodpageitem[pageNo="'+pageNo+'"]').addClass('active');
		html = [];
	}else if(pageCount>5&&pageNo<=3){
		for(i=1;i<=5;i++){
			html.push('<a href="#" pageNo=' + "'" +i+"'" + 'class="goodpageitem page-item">'+ i + '</a>');
		}
		html.push('<a href="#" id="next" class="goodpageitem" pageNo="'+(pageNo+1)+'">&gt;</a>');
		html.push('<a href="#" id="end" pageNo='+"'"+pageCount+"'"+ 'class="goodpageitem">&gt;&gt;</a>');
		$('.page-good').append(html.join(''));
		$('.goodpageitem[pageNo="'+pageNo+'"]').addClass('active');
		html = [];
	}else if(pageCount>5&&pageNo>=pageCount-2){
		html.push('<a href="#" id="start" class="goodpageitem goodpageitem" pageNo="1">&lt;&lt;</a>');
		html.push('<a href="#" id="last" class="goodpageitem" pageNo="'+(pageNo-1)+'">&lt;</a>');
		for(i=pageCount-2;i<=pageCount;i++){
			html.push('<a href="#" pageNo=' + "'" +i+"'" + 'class="goodpageitem page-item">'+ i + '</a>');
		}
		$('.page-good').append(html.join(''));
		$('.goodpageitem[pageNo="'+pageNo+'"]').addClass('active');
		html = [];
	}else{
		html.push('<a href="#" id="start" class="goodpageitem goodpageitem" pageNo="1">&lt;&lt;</a>');
		html.push('<a href="#" id="last" class="goodpageitem" pageNo="'+(pageNo-1)+'">&lt;</a>');
		html.push('<a href="#" pageNo=' + "'"+(pageNo-2)+"'" + 'class="goodpageitem page-item">'+ (pageNo-2) + '</a>');
		html.push('<a href="#" pageNo=' + "'"+(pageNo-1)+"'" + 'class="goodpageitem page-item">'+ (pageNo-1) + '</a>');
		html.push('<a href="#" pageNo=' + "'"+pageNo+"'" + 'class="goodpageitem page-item active">'+ pageNo + '</a>');
		html.push('<a href="#" pageNo=' + "'"+(pageNo+1)+"'" + 'class="goodpageitem page-item">'+ (pageNo+1) + '</a>');
		html.push('<a href="#" pageNo=' + "'"+(pageNo+2)+"'" + 'class="goodpageitem page-item">'+ (pageNo+2) + '</a>');
		html.push('<a href="#" id="next" class="goodpageitem" pageNo="'+(pageNo+1)+'">&gt;</a>');
		html.push('<a href="#" id="end" pageNo='+"'"+pageCount+"'"+ 'class="goodpageitem">&gt;&gt;</a>');
		$('.page-good').append(html.join(''));
		$('.goodpageitem[pageNo="'+pageNo+'"]').addClass('active');
		html = [];
	}
}
function createHomeQueryJson() {
	var key = $('#key').val();
	if (key == '') {
		key = 'all'
	}
	var category = $('.active.category-item').attr('id');
	
	var nokey = false;
	if ($('#nokey').hasClass('active')) {
		nokey = true;
	}
	var nolocked = false;
	if ($('#nolocked').hasClass('active')) {
		nolocked = true;
	}
	var order = $('.active.order').attr('id');
	var sort = 'desc';
	if(order == 'price'){
		sort = 'asc';
	}
	var json = {
		'key' : key,
		'category' : category,
		'pageNo' : 1,
		'nokey' : nokey,
		'nolocked' : nolocked,
		'order' : order,
		'sort' : sort,
		'pageSize' : 12
	};
	return json;
}
function postHomeQuery(json) {

	json = JSON.stringify(json);
	$.post('getHomeGoods.GoodServlet',{json:json},function(data){
		
		fillHomeGoodList(data);
		fillHomeGoodPageModel(data);
	});
}

function fillHomeNeedList(json){
	$('.needlist').html('');
	var list = json.list;
	var html = [];
	for (i = 0; i < list.length; i++) {
		var detail = list[i].needinfo;
		var userid = list[i].userid;
		var avatarurl = '"' + HOSTAVATAR+"/"+ list[i].avatar + '"';
		var price = list[i].price;
		
		var time = list[i].time.substr(0,list[i].time.length-5);
		var name = list[i].name;
		var needid = list[i].needid;
		html.push('<div class="unit-need"><div class="r">');
		html.push('<div class="alert">');
		html.push('<div class="needdetail">'+detail+'</div>');
		html.push('<div class=br></div>');
		
		html.push('<div class="needinfo"><span class="tit">发布时间</span><span class="needtime">'+time+'</span>');
		html.push('<span class="tit">价格</span><span class="needprice">'+price+'</span></div>');
		html.push('<div class="userarea"><div class="ava">');
		html.push('<img src=' + avatarurl + '></div>');
		html.push('<div class="username tit">'+name+'</div>');
		html.push('<button class="msg" userid="' + userid + '" needid='+needid + ' >联系他</button>');
		html.push('</div></div></div></div>');
		
		$('.needlist').append(html.join(''));
		html = [];
	}
}
function fillHomeNeedPageModel(json){
	$('.page-need').html('');
	var pageSize = json.pageSize;
	var pageNo = json.pageNo;
	var allCount = json.allcount;
	var pageCount = Math.ceil(allCount/pageSize);
	var html = [];
	if(allCount<=pageSize){
		return;
	}
	if(pageNo==1){
		
		html.push('<a href="#" id="last" class="needpageitem" pageno="2">></a>');
	}else if(pageNo==pageCount){
		html.push('<a href="#" id="last" class="needpageitem" pageno="'+(pageNo-1)+'"><</a>');
		
	}else{
		html.push('<a href="#" id="last" class="needpageitem" pageno="'+(pageNo-1)+'"><</a>');
		html.push('<a href="#" id="last" class="needpageitem" pageno="'+(pageNo+1)+'">></a>');
		
	}
	$('.page-need').append(html.join(''));
	html = [];
}

function fillMyGood(json){
	$('.form').html('');
	var list = json.list;
	
	if(list.length<=0||json.success==false){
		$('.form').html('<h1>您还没上传过商品哦，</h1><a href="#up-good">点击上传</a>');
	}else{
		for(i=0;i<list.length;i++){
			list[i].uptime = list[i].uptime.substr(0,list[i].uptime.length-5);
			list[i].lockedtime = list[i].lockedtime.substr(0,list[i].lockedtime.length-5);
			var html = [];
			html.push('<div class="unit-good"> <div class="l"> <a href="#" class="goodpic"> ');
			html.push('<img src='+HOSTTBL+"/"+list[i].userid+'/'+list[i].tbl+' class="pre">');
			html.push('</a></div><div class="r">');
				if(list[i].locked){
					html.push('<div class="locked"><span class="lockeduser">');
					html.push('<img src='+HOSTAVATAR+'/'+list[i].lockedavatar+' class="lockedava">');
					html.push('<span class="lockedname">'+list[i].lockedname+'</span>');
					html.push('<br>于<span class="time">'+list[i].lockedtime+'</span>');
					html.push('<br>锁定了这件商品<br>');
					html.push('<button class="mana mana-contact" goodid='+list[i].goodid+' lockeduserid='+list[i].lockeduserid+'>联系方式</button>');
					html.push('<button class="mana mana-clearlock" goodid='+list[i].goodid+' lockeduserid='+list[i].lockeduserid+'>解除锁定</button>');
					html.push('</div>');
				}
				html.push('<a href="javascript:;" class="category search-category">'+list[i].category+'</a>');
				html.push('<div class="detail">'+list[i].goodinfo+'</div>');
				html.push('<div class="info"><span>上传时间</span><span class="time">'+list[i].uptime+'</span> ');
				html.push(' <span>访问次数</span><span class="times">'+list[i].clicktimes+'</span> ');
				html.push(' <span>价格</span><span class="price">'+list[i].price+'</span></div> ');
				html.push(' <div class="divider"></div><div class="down"><div class="taglist"> ');
				for(var o in list[i].tags){
					html.push('<span class="t">'+list[i].tags[o]+'</span>');
				}
				html.push('</div><div class="manage">');
				html.push('<button class="mana mana-remove" goodid='+list[i].goodid+'><i class="icon icon-close"></i>删除</button>');
				html.push('<button class="mana mana-modify" goodid='+list[i].goodid+'><i class="icon icon-close"></i>修改</button>');
				html.push('</div></div></div><span class="clearfix"></span></div>');
				$('.form').append(html.join(''));
				html = [];
		}
	}
}
function fillMyNeed(json){
	$('.form').html('');
	var list = json.list;
	if(list.length<=0||json.success==false){
		$('.form').html('<h1>您还没发布过需求哦，</h1><a href="#up-need">点击上传</a>');
	}else{
		
		for(i=0;i<list.length;i++){
			list[i].uptime = list[i].uptime.substr(0,list[i].uptime.length-5);
			list[i].lockedtime = list[i].lockedtime.substr(0,list[i].lockedtime.length-5);
			var html = [];
				html.push('<div class="unit-good">');
				html.push('<div class="r myneed">');
				if(list[i].locked){
					html.push('<div class="locked"><span class="lockeduser">');
					html.push('<img src='+HOSTAVATAR+'/'+list[i].lockedavatar+' class="lockedava">');
					html.push('<span class="lockedname">'+list[i].lockedname+'</span>');
					html.push('<br>于<span class="time">'+list[i].lockedtime+'</span>');
					html.push('<br>锁定了这条信息<br>');
					html.push('<button class="mana mana-contact" needid='+list[i].needid+' lockeduserid='+list[i].lockeduserid+'>联系方式</button>');
					html.push('<button class="mana mana-clearlock" needid='+list[i].needid+' lockeduserid='+list[i].lockeduserid+'>解除锁定</button>');
					html.push('</div>');
				}
				html.push('<a href="javascript:;" class="search-category category">'+list[i].category+'</a>');
				html.push('<div class="detail">'+list[i].needinfo+'</div>');
				html.push('<div class="info"><span>上传时间</span><span class="time">'+list[i].uptime+'</span> ');
				
				html.push(' <span>价格</span><span class="price">'+list[i].price+'</span></div> ');
				html.push(' <div class="divider"></div>');
				
				html.push('<div class="down"> <div class="manage">');
				html.push('<button class="mana mana-remove" needid='+list[i].needid+'><i class="icon icon-close"></i>删除</button>');
				html.push('<button class="mana mana-modify" needid='+list[i].needid+'><i class="icon icon-close"></i>修改</button>');
				
				html.push('</div></div></div></div><span class="clearfix"></span></div>');
				$('.form').append(html.join(''));
				html = [];
		}
	}
}
function getGoodContact(userid,goodid){
	
	closeEnsurebox();
	if(!isSigned()){
		showSignBox();
		showAlert("请先登陆");
		return;
	}
	$.getJSON('getGoodContact.GoodServlet?userid='+userid+'&&goodid='+goodid,function(json){
		if(json.ban){
			showAlert("禁止查看");
			return;
		}
		if(json.ilocked){
			if(json.seeQq==false&&json.seePhone==false){
				if(json.avatar=='avatar.jpg'){
					$('.sendava').attr('src',HOSTAVATAR+"/avatar.jpg");
				}else{
					$('.sendava').attr('src',HOSTAVATAR+"/"+json.userid+".jpg");
				}
				$('.sendname').html(json.name);
				$('#sendmsgbtn-ok').attr('userid',json.userid);
				$('#sendmsgbtn-ok').attr('goodid',json.goodid);
				$('.sendmsg-goodimg').attr('src',HOSTTBL+'/'+json.userid+'/'+json.tbl);
				$('.sendmsgbox').show();
			}else{
				if(json.seeQq==true){
					if(json.qq!==""){
						$('.contactqq').html(json.qq);
					}else{
						$('.contactqq').html('未公布');
					}
					
				}else{
					$('.contactqq').html('未公布');
				}
				if(json.seePhone){
					$('.contactphone').html(json.phone);
				}else{
					$('.contactphone').html('未公布');
				}
				$('.contactbox').show();
			}
			return;
		}
		if(json.otherlocked){
			showAlert('该商品已经被锁定');
			return;
		}
		if(json.seeQq==false&&json.seePhone==false){
			if(json.avatar=='avatar.jpg'){
				$('.sendava').attr('src',HOSTAVATAR+"/avatar.jpg");
			}else{
				$('.sendava').attr('src',HOSTAVATAR+"/"+json.userid+".jpg");
			}
			$('.sendname').html(json.name);
			$('#sendmsgbtn-ok').attr('userid',json.userid);
			$('#sendmsgbtn-ok').attr('goodid',json.goodid);
			$('.sendmsg-goodimg').attr('src',HOSTTBL+'/'+json.userid+'/'+json.tbl);
			$('.sendmsgbox').show();
		}else{
			if(json.seeQq==true){
				if(json.qq!==""){
					$('.contactqq').html(json.qq);
				}else{
					$('.contactqq').html('未公布');
				}
				
			}else{
				$('.contactqq').html('未公布');
			}
			if(json.seePhone){
				$('.contactphone').html(json.phone);
			}
			$('.contactbox').show();
		}
	});
	
} 

function getNeedContact(userid,needid){
	closeEnsurebox();
	if(!isSigned()){
		showSignBox();
		showAlert("请先登陆");
		return;
	}
	$.getJSON('getNeedContact.NeedServlet?userid='+userid+'&&needid='+needid,function(json){
		if(json.ban){
			showAlert("禁止查看");
			return;
		}
		if(json.ilocked){
			if(json.seeQq==false&&json.seePhone==false){
				if(json.avatar=='avatar.jpg'){
					$('.sendava').attr('src',HOSTAVATAR+"/avatar.jpg");
				}else{
					$('.sendava').attr('src',HOSTAVATAR+"/"+json.userid+".jpg");
				}
				$('.sendname').html(json.name);
				$('#sendmsgbtn-ok').attr('userid',json.userid);
				$('#sendmsgbtn-ok').attr('needid',json.needid);
				$('.sendmsgbox').show();
			}else{
				if(json.seeQq==true){
					if(json.qq!==""){
						$('.contactqq').html(json.qq);
					}else{
						$('.contactqq').html('未公布');
					}
				}else{
					$('.contactqq').html('未公布');
				}
				if(json.seePhone){
					$('.contactphone').html(json.phone);
				}else{
					$('.contactphone').html('未公布');
				}
				$('.contactbox').show();
			}
			return;
		}
		if(json.otherlocked){
			showAlert('该信息已经被锁定');
			return;
		}
		if(json.seeQq==false&&json.seePhone==false){
			if(json.avatar=='avatar.jpg'){
				$('.sendava').attr('src',HOSTAVATAR+"/avatar.jpg");
			}else{
				$('.sendava').attr('src',HOSTAVATAR+"/"+json.userid+".jpg");
			}
			$('.sendname').html(json.name);
			$('#sendmsgbtn-ok').attr('userid',json.userid);
			$('#sendmsgbtn-ok').attr('needid',json.needid);
			$('.sendmsgbox').show();
		}else{
			if(json.seeQq==true){
				if(json.qq!==""){
					$('.contactqq').html(json.qq);
				}else{
					$('.contactqq').html('未公布');
				}
				
			}else{
				$('.contactqq').html('未公布');
			}
			if(json.seePhone){
				$('.contactphone').html(json.phone);
			}
			$('.contactbox').show();
		}
	});
	
} 
function closeEnsurebox(){
	$('#ensurebox-msg').hide();
	$('#curtain').hide();
}
function showAlert(text){
	$('.alertbox').show();
	$('.alertbox').html(text);
	$('.alertbox').css('transform','translate(0px,0px)');
	var a = document.getElementById("alertbox");
	a.addEventListener('webkitTransitionEnd',function(){
		
		setTimeout(function () { 
			$('.alertbox').delay(2000).css('transform','translate(30px,-40px)');
			$('.alertbox').css('display','none');
		}, 3000);
	});
}
function removeAlert(){
	$('.alert').css("display",'none');
}
function sendGoodMsg(taruserid,goodid,msgcontent){
	if(msgcontent==''){
		showAlert('消息内容不能为空');
		return;
	}else if(msgcontent.length>100){
		showAlert('消息内容不能超过100个字符');
		return;
	}
	$.getJSON('sendMsg.GoodServlet?taruserid='+taruserid+'&&goodid='+goodid+'&&msgcontent='+msgcontent,function(){
		showAlert('发送成功');
		$('.sendmsgbox').hide();
	});
}
function sendNeedMsg(taruserid,needid,msgcontent){
	if(msgcontent==''){
		showAlert('消息内容不能为空');
		return;
	}else if(msgcontent.length>100){
		showAlert('消息内容不能超过100个字符');
		return;
	}
	$.getJSON('sendMsg.NeedServlet?taruserid='+taruserid+'&&needid='+needid+'&&msgcontent='+msgcontent,function(){
		showAlert('发送成功');
		$('.sendmsgbox').hide();
	});
}
function getOtherGoods(){
	var category = $('#category').html();
	var userid = $('.sbtn-msg').attr('userid');
	$.getJSON('getOtherGoods.GoodServlet?userid='+userid+'&&category='+category,function(json){
		fillOtherGoods(json);
	});
}
function fillOtherGoods(json){
	var hash = window.location.hash;
	nowgoodid = hash.split('#')[1];
	var type = json.type;
	var list = json.list;
	if(type == "userOthers"){
		$('#otherstype').html("他的更多");
	}else if(type == "categoryOthers"){
		$('#otherstype').html("同类推荐");
	}
	var html=[];
	$('.goodlist').html='';
	for(var i in list){
		if(nowgoodid==list[i].goodid){
			continue;
		}
		html.push('<div class="goodbox">')
		html.push('<a target="_blank" href='+HOST+'/detail.html#'+list[i].goodid+'>');
		html.push('<div class="goodpic">');
		html.push('<img src='+HOSTTBL+'/'+list[i].userid+'/'+list[i].tbl+'>');
		html.push('<div class="cov">');
		html.push('<p class="detail">'+list[i].info+'</p>');
		html.push('<p class="times">浏览<span>'+list[i].clicktimes+'</span>次</p>');
		html.push('<p class="price"><em>￥</em>'+list[i].price+'</p>');
		html.push('</div></div></a></div>');
		$('.goodlist').append(html.join(''));
		html = [];
	}
}

//定时器
var wait=60;  
function time(o) {  
        if (wait == 0) {  
        	$(o).removeClass('disabled');
        	o.removeAttribute("disabled");           
            o.value="获取验证码";  
            wait = 60;  
        } else {  
        	$(o).addClass('disabled');
        	o.setAttribute("disabled", true);   
            o.value="重新发送(" + wait + ")";  
            wait--;  
            setTimeout(function() {  
                time(o)  
            },  
            1000)  
        }  
 } 
function getCode(o){
	var phone = $('#input_findpwd_phone').val();
	if (checkPhone(phone)){
		$.getJSON('getCode.UserServlet?phone='+phone,function(json){
			if(json.registed){
				if(json.success){
					showAlert('验证码以发送，请注意你的短息');
					time(o);
					return;
				}
				if(!json.success){
					showAlert('发送失败');
					return
				}
			}
			showAlert('该账号还没被注册，您不必找回密码');
			
			
		});
	}else{
		showAlert('请检查您的手机号码');
	}
}
function setPwdByCode(){
	var pwd = $('#input_findpwd_pwd').val();
	var code = $('#input_findpwd_code').val();
	if(checkPwd(pwd)&&code.length==4){
		$.getJSON('setPwdByCode.UserServlet?code='+code+'&&pwd='+pwd,function(json){
			if(json.success){
				showAlert('修改成功');
			}else if(!json.success){
				showAlert('验证码错误');
			}else{
				showAlert('异常！')
			}
		});
	}else{
		showAlert('请检查验证码和密码');
	}
}

function closeSetPwdBox(){
	$('#setpwdBox').hide();
}

function updatePwd(oldpwd,newpwd){
	$.getJSON('updatePwd.UserServlet?oldpwd='+oldpwd+'&&newpwd='+newpwd,function(json){
		if(json.success){
			closeSetPwdBox();
			showAlert('新密码设置成功');
		}else{
			showAlert('设置失败');
		}
	});
}
