Ext.define('DEMO.model.UserListModel', {
    extend	: 'Ext.data.Model',
    fields	: [
		'id','loginName','name','plainPassword','password','officeId','phone','email'
		//dataEntity内容略过
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
        	read : 'sys/user/list',
            create : 'sys/user/create',
            update: 'sys/user/edit',
            destroy: 'sys/user/delete'
        },
		
		//封装后台Page对象
		pageParam : "pageNo",
        limitParam: "pageSize",
       // sortParam: "sort",
        //directionParam: "dir"
//        simpleSortMode	: true,
        extraParams :{//用来排序
        	orderBy:'name'
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