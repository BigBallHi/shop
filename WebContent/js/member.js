
$(document).ready(function(){
	$.getJSON('getMemberInfo.UserServlet',function(json){
		fillCount(json);
		fillMember(json);
		getSignedInfo();
	});
	var hash = window.location.hash;
	updateRight(hash);
});
window.onhashchange = function(){
	var hash = window.location.hash;
	updateRight(hash);
}




$('.desc1').click(function(){
	var t = $('#member-info').html();
	$('.desc1').hide();
	$('#member-infoinput').show();
	$('#member-infoinput').val(t);
	$('#member-infoinput').focus();
	$('#member-infoinput').select();
});
$('#member-infoinput').blur(function(){	
	$('.desc1').show();
	$('#member-infoinput').hide();
	if($('#member-info').html()!=$('#member-infoinput').val()){
		$('#member-info').html($('#member-infoinput').val());
		$.getJSON('modifiUserInfo.UserServlet?info='+$('#member-infoinput').val(),function(json){
			if(json.success){
				showAlert('更新签名成功');
			}
		});
	}
	
});




