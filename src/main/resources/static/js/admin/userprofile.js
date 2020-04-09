(function (){
var $table = $('#roletable');
var addflag=true;//添加角色的falg，一次只能加一行

$(document).ready(function(){	   
		$("#addrole").bind("click",function(){
			if(addflag){
				addflag=false;
				$table.bootstrapTable('insertRow', {
			        index: 0,
			        row: {
			        	rolename: '',
			        	description: ''
			        }
			      })	
			}			
	    });	
		//点击头像触发事件
		$("#editheadSculpture").bind("click",function(){
			$("input[name='headSculpture']").click();
	    });
		//切换头像触发
		$("input[name='headSculpture']").bind("change",function(){
			 // 获取上传文件对象
		    var file = $(this)[0].files[0];
		    // 读取文件URL
		    var reader = new FileReader();
		    reader.readAsDataURL(file);
		    // 阅读文件完成后触发的事件
		    reader.onload = function () {
		        // 读取的URL结果：this.result
		    	$("img[name='avatar']").attr("src",this.result);
		    }
		});
	});

	$table.bootstrapTable({
		 toolbar:'#roletoolbar',              //工具栏
	     toolbarAlign:'left',                 //工具栏的位置
	      pagination: true,                   //是否显示分页（*）
	      sortable: true,                     //是否启用排序
	      sortOrder: "asc",                   //排序方式
	      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	      pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
	      pageSize: 4,  					  //每页的记录行数（*）
	      pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）          
	     clickEdit: true, 
	     showRefresh: true,
	     //height: 150,                         //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度	    
	     queryParams : function (params) {
	           //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
	           var temp = {   
	               limit: params.limit,                         //页面大小
	               page: (params.offset / params.limit) + 1,   //页码
	               sort: params.sort,      //排序列名  
	               sortOrder: params.order //排位命令（desc，asc） 
	           };
	           temp.username= $("input[name='username']").val();
	           return temp;
	       },
	     columns: [		
					{
				        field: 'rolename',
				        title: '角色'
				    }, {
				        field: 'description',
				        title: '描述'
				    },{
				    	field: 'operate',
				        title: '操作',
				        align: 'center',
				        valign: 'middle',
				        width: '10%',
				    	formatter: function (value, row, index) {	    
					      	var str='<div class="table-data-feature">' +		        			       							      	       
					      			'<button class="item" data-toggle="tooltip" data-placement="top" title="Save">' + 
							            '<i class="fa fa-floppy-o"></i>' + 
							        '</button>' +  
							        '<button class="item" data-toggle="tooltip" data-placement="top" title="Delete">' + 
							            '<i class="zmdi zmdi-delete"></i>' + 
							        '</button>' + 
							    	'</div>';
					        return str;
				    	},
				       events: {
					      'click button[title=Delete]': function (e, value, row, index) {
					          if(confirm('此操作不可逆，请确认是否删除？')){
					        	  row.username=$("input[name='username']").val();
					        	  ajaxDelete(row);
					          }
					      },
					      'click button[title=Save]': function (e, value, row, index) {
					    	  row.username=$("input[name='username']").val();
					    	  ajaxEdit(row);
					      },					    
				      }
				    }
				    ] ,
		    onClickCell: function(field, value, row, $element) {
	        	if((field=='rolename' &&value!="")||field=='operate') return;
	        	if($element.attr('contenteditable')) return;
	        	$element.attr('contenteditable', true);//设置属性为可编辑
	        	$element.unbind("blur");
	        	$element.blur(function(){
	        		  let index = $element.parent().data('index');
	                  let tdValue = $element.html();
	                  saveData(index, field, tdValue);
	        	});
	        }, 
	 });

function ajaxEdit(data) {
	 $.ajax({
		    type: "put",
		    url: '/admin/addUserRole'  ,
		    data: JSON.stringify(data),
		    cache: false,
		    async : false,
		    dataType: "json",
		    contentType:"application/json",
		    success: function (data ,textStatus, jqXHR)
		    {
		    	if(data.code==200){
		    		addflag=true;
		    		$table.bootstrapTable('refresh',{ url:'/admin/getUserRole'});
		    	}else{
		    		alert(data.msg);
		    	}			        
		    },
		    error:function (XMLHttpRequest, textStatus, errorThrown) {      
		    	//errorTips("error-tip",data.msg);
		    }
		 });
}

function ajaxDelete(data) {
	 $.ajax({
		    type: "delete",
		    url: '/admin/deleteUserRole/' ,
		//  data: "para="+para,  此处data可以为 a=1&b=2类型的字符串 或 json数据。
		    data: JSON.stringify(data),
		    cache: false,
		    async : false,
		    dataType: "json",
		    contentType:"application/json",
		    success: function (data ,textStatus, jqXHR)
		    {
		    	if(data.code==200){ 
		    		addflag=true;
		    		$table.bootstrapTable('refresh',{ url:'/admin/getUserRole'});
		    	}else{
		    		alert(data.msg);
		    	}			        
		    },
		    error:function (XMLHttpRequest, textStatus, errorThrown) {      
		    	//errorTips("error-tip",data.msg);
		    }
		 });
}
function saveData(index, field, value) {
	$table.bootstrapTable('updateCell', {
        index: index,       //行索引
        field: field,       //列名
        value: value        //cell值
    })
}

function editUserProfile(){
	var url="/admin/edituserprofile";
	var formData = new FormData();
	// 获取要上传的文件file
	var files = $("#file")[0].files;
	if(files.length >0 ){
		var file = files[0];
		// 将文件添加到formData对象中
		formData.append("files",file);
	}
	//添加用户信息
	formData.append("username",$("input[name='username']").val());
	formData.append("password",$("input[name='password']").val());
	formData.append("nickname",$("input[name='nickname']").val());
	formData.append("sex",$("input[name='sex']:checked").val());
	formData.append("telphone",$("input[name='telphone']").val());
	formData.append("email",$("input[name='email']").val());
	formData.append("age",$("input[name='age']").val());
	// ajax 异步上传
	$.ajax({
	　　type:"post",
	　　url:url,// 上传请求url
	　　data:formData,// 上传参数
	　　processData : false,// 必须，设置不转换为string，默认为true
	　　contentType : false,
	　　success:function(data){
		addflag=true;
		 //$('#myModal').modal('hide');
	　　},
	　　error:function(e){
	　　　　alert("上传失败");
	　　}
	});
	
	
}
})()