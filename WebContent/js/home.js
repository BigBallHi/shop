
$('.needlist').on('click','.msg',function(){
	$('#ensurebox-msg-ok').attr('userid',$(this).attr('userid'));
	$('#ensurebox-msg-ok').attr('needid',$(this).attr('needid'));
	$('#ensurebox-msg').show();
	$('#curtain').show();
});

$('#ensurebox-msg-ok').click(function(){
	var userid = $(this).attr('userid');
	var needid = $(this).attr('needid');
	getNeedContact(userid,needid);
});
$('#ensurebox-msg-nook').click(function(){
	closeEnsurebox();
});
$('#sendmsgbtn-ok').click(function(){
	var taruserid = $(this).attr('userid');
	var needid = $(this).attr('needid');
	var msgcontent = $('.sendmsg-ipt').val();
	sendNeedMsg(taruserid,needid,msgcontent);
});


/**
 * 元素加载完成时
 */
$(document).ready(function(){
	var phone = getCookie('phone');
	var pwd = getCookie('pwd');
	if (phone != '' && pwd != '') {
		sign(phone, pwd);
	}
	/**查询商品**/
	/**如果是首次访问**/
	var hash = window.location.hash;
	if(hash==''){
		var json = createHomeQueryJson();
		json.enter = 'default';
		postHomeQuery(json);
	}else{
		var hash1 = hash.split('#')[1].split('-')[0];
		var hash2 = hash.split('#')[1].split('-')[1];
		if(hash1=='key'){
			$('#key').val(hash2);
			$('.category-item.active').removeClass('active');
			$('#all').addClass('active');
			$('#nokey').removeClass('active');
			var json = createHomeQueryJson();
			json.key = hash2;
			json.enter = 'key';
			json.sort = "";
			postHomeQuery(json);
		}else if(hash1=='category'){
			$('#'+hash2).addClass('active');
			var json = createHomeQueryJson();
			json.enter = 'category';
			postHomeQuery(json);
		}
	}
		/**查询需求**/
	$.getJSON('getNeeds.NeedServlet?pageSize=14&&pageNo=1',function(json){
		fillHomeNeedList(json);
		fillHomeNeedPageModel(json);
	});

});
/**
 * 查询相关
 */
$('.category-item').click(function() {
	$(this).addClass('active');
	$('.category-item').not($(this)).removeClass('active');
	var json = createHomeQueryJson();
	json.enter = 'category';
	postHomeQuery(json);
});
$('#search').click(function() {
	$('.category-item.active').removeClass('active');
	$('#all').addClass('active');
	$('#nokey').removeClass('active');
	var json = createHomeQueryJson();
	json.enter = 'key';
	json.sort = "";
	postHomeQuery(json);
});
$('.order').click(function() {
	$(this).addClass('active');
	$('.order').not($(this)).removeClass('active');
	var json = createHomeQueryJson();
	json.enter = 'order';

	postHomeQuery(json);
});
$('#nokey').click(function() {
	if ($(this).hasClass('active')) {
		$(this).removeClass('active');
	} else {
		$(this).addClass('active');
	}
	var json = createHomeQueryJson();
	json.enter = 'filter';
	postHomeQuery(json);
});
$('#nolocked').click(function() {
	if ($(this).hasClass('active')) {
		$(this).removeClass('active');
	} else {
		$(this).addClass('active');
	}
	var json = createHomeQueryJson();
	json.enter = 'filter';

	postHomeQuery(json);
});
$('.page-good').on("click",'.goodpageitem',function(){
	var json = createHomeQueryJson();
	json.enter = 'pageitem';
	var pageNo = $(this).attr('pageNo');
	json.pageNo = pageNo;
	postHomeQuery(json);
});

/////////////////////
$('.page-need').on("click",'.needpageitem',function(){
	var pageNo = $(this).attr('pageNo');
	$.getJSON('getNeeds.NeedServlet?pageSize=14&&pageNo='+pageNo,function(json){
		fillHomeNeedList(json);
		fillHomeNeedPageModel(json);
	});
});





