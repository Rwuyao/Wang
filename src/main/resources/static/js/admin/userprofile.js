
$(document).ready(function(){	   
		$("#addrole").bind("click",function(){
			$('#roletable').bootstrapTable('insertRow', {
		        index: 0,
		        row: {
		        	role: '',
		        	description: ''
		        }
		      })
	    });	   
	});

$('#roletable').bootstrapTable({
		 toolbar:'#roletoolbar',//工具栏
	     toolbarAlign:'left',//工具栏的位置
	     clickEdit: true, 
	     showRefresh: true,  
	     columns: [		
					{
				        field: 'role',
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
					        	  ajaxroleDelete(row);
					          }
					      },
					      'click button[title=Save]': function (e, value, row, index) {
					    	  row.username=$("input[name='username']").val();
					    	  ajaxroleEdit(row);
					      },					    
				      }
				    }
				    ] ,
				    onClickCell: function(field, value, row, $element) {
			        	if(field=='operate' &&value!="") return;
			        	$element.attr('contenteditable', true);//设置属性为可编辑
			        	$element.blur(function(){
			        		  let index = $element.parent().data('index');
			                  let tdValue = $element.html();
			                  saveRoleData(index, field, tdValue);
			        	});
			        }, 
	 });

function ajaxroleEdit(data) {
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
	                $('#roletable').bootstrapTable('refresh');
		    	}else{
		    		alert(data.msg);
		    	}			        
		    },
		    error:function (XMLHttpRequest, textStatus, errorThrown) {      
		    	//errorTips("error-tip",data.msg);
		    }
		 });
}

function ajaxroleDelete(data) {
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
	                $('#roletable').bootstrapTable('refresh');
		    	}else{
		    		alert(data.msg);
		    	}			        
		    },
		    error:function (XMLHttpRequest, textStatus, errorThrown) {      
		    	//errorTips("error-tip",data.msg);
		    }
		 });
}
function saveRoleData(index, field, value) {
	$('#roletable').bootstrapTable('updateCell', {
        index: index,       //行索引
        field: field,       //列名
        value: value        //cell值
    })
}
