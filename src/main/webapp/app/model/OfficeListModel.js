Ext.define('DEMO.model.OfficeListModel', {
    extend	: 'Ext.data.Model',
    fields	: [
		'id','parentId','name','type','grade','address','zipCode','master','phone','fax','email','officeCode',
		'spellCode'
		//dataEntity内容略过
	],

	proxy	: {
		type			: 'ajax',
//		url				: 'sys/office/list',
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
        	read : 'sys/office/list',
            create : 'sys/office/create',
            update: 'sys/office/edit',
            destroy: 'sys/office/delete'
        },
		
		//封装后台Page对象
		pageParam : "pageNo",
        limitParam: "pageSize",
       // sortParam: "sort",
        //directionParam: "dir"
//        simpleSortMode	: true,
        extraParams :{//用来排序
        	orderBy:'officeCode ,name'
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