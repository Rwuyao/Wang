(function (){
var $table = $('#menutable');
$(document).ready(function(){
	//绑定菜单tbale 
	$("#addmenu").bind("click",function(){
    	$table.bootstrapTable('insertRow', {
	        index: 0,
	        row: {
	        	id: '',
	        	text: '',
	        	description: '',
	        	url:'',
	        	parentid:'',
	        	enable:''
	        }
	      })
    });
	//初始化菜单树
	menuTreeInit();
});

	//注册右键菜单的项与动作
	$('#treeview').contextMenu({
	    selector: 'li', // 选择器，为某一类元素绑定右键菜单
	    callback: function(key, options) {
	        if(key=="add"){
	        	//通过当前右键的节点的nodeid获取对应的节点，不必选择后才能添加
	        	 var parentNode=$('#treeview').treeview('getNode', $(this).attr('data-nodeid'));	        	 
	             ajaxEdit({ text: '子节点',parentid: parentNode.id}) ;	        	        	 
	        }else if(key=="delete"){
	        	//获取选中的节点
	        	var node=$('#treeview').treeview('getNode', $(this).attr('data-nodeid'));		        	
	        	ajaxDelete({ id: node.id});        	
	        }    	
	    },
	    items: {
	        "add": {name: "Add", icon: "add"},
	        "delete": {name: "Delete", icon: "delete" },
	        "refresh":{name: "Refresh", icon: "loading"},
	        "sep1": "---------",
	        "quit": {name: "Quit", icon: function($element, key, item){ return 'context-menu-icon context-menu-icon-quit'; }}//自定义方法来设置图标
	    }
	});
	 
	//在treeview的li之外添加根节点，只需添加这一功能即可 
	$('#treediv').contextMenu({
	    selector: '.treeview', // 选择器，为某一类元素绑定右键菜单
	    callback: function(key, options) {
	    	if(key=="add"){	    		 	
	    		 ajaxEdit({ text: '根节点',parentid: 0}) ;  
	    	}
	    },
	    items: {
		    "add": {name: "Add", icon: "add"},
	        "refresh":{name: "Refresh", icon: "loading"},
	    }
	});
	function menuTreeInit(){
		$.ajax({
		    type: "get",
		    url: '/admin/getMenuTree'  ,
		//  data: "para="+para,  此处data可以为 a=1&b=2类型的字符串 或 json数据。			 
		    cache: false,
		    async : false,
		    dataType: "json",		    
		    success: function (data ,textStatus, jqXHR)
		    {
		    	if(data.code==200){			    		
		    		$('#treeview').treeview({
		    			 data: data.rows,
		    			 levels:1,
		    	         onNodeSelected: function(event, node) {
		    	        	//点击以后查询该节点下的所有菜单 	    	        		    	           
		    	        	 if(node.id){
		    	        		 //设置参数
			    	        	 $("#treeViewInput").val(node.id);
			    	        	 //刷新菜单		    	        	 
			    	        	 $table.bootstrapTable('refresh');  
		    	        	 }	    	        	
		    	         },
		    	          onNodeUnselected: function (event, node) {	
		    	        	  //清空参数
			    	         $("#treeViewInput").val("");
		    	          }
		    		});		                
		    	}			        
		    },
		    error:function (XMLHttpRequest, textStatus, errorThrown) {      
		    	 params.error(); 
		    }
		 });
	}
	
	$table.bootstrapTable({
	method: 'get',
    url: "/admin/getMenuTable",//请求路径
	toolbar:'#menutoolbar',              //工具栏
    toolbarAlign:'left',                 //工具栏的位置
     pagination: true,                   //是否显示分页（*）
     sortable: true,                     //是否启用排序
     sortOrder: "asc",                   //排序方式
     sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
     pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
     pageSize: 5,  					  //每页的记录行数（*）
     pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）          
    clickEdit: true, 
    showRefresh: true,
    //height: 500,                         //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度	    
    queryParams : function (params) {
          //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
          var temp = {   
              limit: params.limit,                         //页面大小
              page: (params.offset / params.limit) + 1,   //页码
              sort: params.sort,      //排序列名  
              sortOrder: params.order //排位命令（desc，asc） 
          };
          if($("#treeViewInput").val()!=""){
           	 temp.parentid= $("#treeViewInput").val();
            }
          return temp;
      },
    columns: [	
		    	{
			        field: 'id',
			        title: 'ID'
			    },
				{
			        field: 'text',
			        title: '菜单名'
			    }, {
			        field: 'description',
			        title: '描述'
			    },
			    {
			        field: 'url',
			        title: '链接'
			    },
			    {
			        field: 'parentid',
			        title: '父ID'
			    },
			    {
			        field: 'enable',
			        title: '是否启用'
			    },			    
			    {
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
				        	  ajaxDelete(row);
				          }
				      },
				      'click button[title=Save]': function (e, value, row, index) {
				    	  ajaxEdit(row);
				      },					    
			      }
			    }
			    ] ,
	    onClickCell: function(field, value, row, $element) {
		       	if(field=='id' || field=='operate') return;
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
	function ajaxDelete(data) {
		 $.ajax({
			    type: "delete",
			    url: '/admin/deleteMenu/'+ data.id ,
			//  data: "para="+para,  此处data可以为 a=1&b=2类型的字符串 或 json数据。
			    data: JSON.stringify(data),
			    cache: false,
			    async : false,
			    dataType: "json",
			    contentType:"application/json",
			    success: function (data ,textStatus, jqXHR)
			    {
			    	if(data.code==200){
			    		$table.bootstrapTable('refresh');
			    		//初始化菜单树
			    		menuTreeInit();
			    	}else{
			    		alert(data.msg);
			    	}			        
			    },
			    error:function (XMLHttpRequest, textStatus, errorThrown) {      
			    	//errorTips("error-tip",data.msg);
			    }
			 });
	 }
	
	 function ajaxEdit(data) {
		 $.ajax({
			    type: "put",
			    url: '/admin/EditMenu'  ,
			    data: JSON.stringify(data),
			    cache: false,
			    async : false,
			    dataType: "json",
			    contentType:"application/json",
			    success: function (data ,textStatus, jqXHR)
			    {
			    	if(data.code==200){
			    		$table.bootstrapTable('refresh');
			    		//初始化菜单树
			    		menuTreeInit();
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
	
	
})()