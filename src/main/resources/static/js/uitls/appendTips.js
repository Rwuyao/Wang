function errorTips(id,msg){
	$("#"+id).empty() ;
	var str='<div class="sufee-alert alert with-close alert-danger alert-dismissible fade show">';
	str+=msg;
	str+='<button type="button" class="close" data-dismiss="alert" aria-label="Close">';
	str+='<span aria-hidden="true">&times;</span>';
	str+='</button>';
	$("#"+id).append(str);										
}