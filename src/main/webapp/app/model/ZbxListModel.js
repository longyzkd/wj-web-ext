Ext.define('DEMO.model.ZbxListModel', {
    extend	: 'Ext.data.Model',
    fields	: [
		'id','zbxMc','uploadCycle','propertyText','propertyName','entityName',
		 'remarks',	
		 'createBy',	
		 {
			  name:'createDate'	
		  },
		 'updateBy',	
		 {
			  name:'updateDate'	
			  
		  }
	],

	proxy	: {
		type			: 'ajax',
		reader			: {
			type			: 'json',
			root			: 'root',
			totalProperty	: 'totalCount',
			successProperty	: 'success'
		},
		writer: {
            type: 'json',
            writeAllFields: true,
            encode: false
//            root: 'root'
        },
        api: {
        	read : 'lookup/zbx/list',
            create : 'lookup/zbx/create',
            update: 'lookup/zbx/edit',
            destroy: 'lookup/zbx/delete'
        },
		
		//封装后台Page对象
		pageParam : "pageNo",
        limitParam: "pageSize",
       // sortParam: "sort",
        //directionParam: "dir"
//        simpleSortMode	: true,
        extraParams :{//用来排序,sql用法
        	orderBy:'zbxMc'
        },
        listeners: {
            exception: function(proxy, response, operation){
                Ext.MessageBox.show({
                    title: '系统错误',
                    msg: Ext.decode(response.responseText).message,
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        }

	}	
	
});