
$(document).ready(function(){
	
	var hash = window.location.hash;
	hash = hash.split('#')[1];
	$.getJSON('getGoodDetail.GoodServlet?goodid='+hash,function(json){
		$('#category').html(json.category);
		$('#maintag').html(json.tags[0]);
		$('.taglist').html('');
		$('.taglist').append('<a class="tag active" href='+HOST+'>全部</a>');
		for(var o in json.tags){
			$('.taglist').append('<a class="tag search-key">'+json.tags[o]+'</a>');
		}
		$('.piclist').html('');
		for(var i in json.pics){
			$('.piclist').append('<a href="#"><img src="'+HOSTGOODIMG+'/'+json.userid+'/'+json.pics[i]+'"></a>');
		}
		$($('.piclist').children($('a')).get(0)).addClass('active');
		$('#bigimg').attr('src',HOSTGOODIMG+'/'+json.userid+'/'+json.pics[0]);
		$('.dd-detail').html(json.goodinfo);
		$('.dd-time').html(json.time);
		$('.dd-price').html(json.price);
		$('.dd-loc').html(json.location);
		$('.dd-times').html(json.clicktimes);
		if(json.name == '二站新人'){
			$('.name').html(json.name);
		}else{
			$('.name').html(json.name);
		}
		if(json.avatar == 'avatar.jpg'){
			$('.userinfoava').attr('src',HOSTAVATAR+'/avatar.jpg');
		}else{
			$('.userinfoava').attr('src',HOSTAVATAR+'/'+json.userid+'.jpg');
		}
		$('.sig').html(json.userinfo);
		$('.sbtn-msg').attr('userid',json.userid);
		$('.sbtn-msg').attr('goodid',json.goodid);
		$('.sbtn-msg').show();
		getOtherGoods();
		
	});
	getSignedInfo();
	
});
$('.sbtn-msg').click(function(){
	$('#ensurebox-msg-ok').attr('userid',$(this).attr('userid'));
	$('#ensurebox-msg-ok').attr('goodid',$(this).attr('goodid'));
	$('#ensurebox-msg').show();
	$('#curtain').show();

});
$('#ensurebox-msg-ok').click(function(){
	var userid = $(this).attr('userid');
	var goodid = $(this).attr('goodid');
	getGoodContact(userid,goodid);
});
$('#ensurebox-msg-nook').click(function(){
	closeEnsurebox();
});
$('#sendmsgbtn-ok').click(function(){
	var taruserid = $(this).attr('userid');
	var goodid = $(this).attr('goodid');
	var msgcontent = $('.sendmsg-ipt').val();
	sendGoodMsg(taruserid,goodid,msgcontent);
});
$('.piclist').on('click','img',function(){
	$('#bigimg').attr('src',$(this).attr('src'));
	$('.piclist a').removeClass('active');
	$(this).parent().addClass('active');
	
});
