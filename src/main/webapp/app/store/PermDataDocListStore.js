Ext.define('DEMO.store.PermDataDocListStore', {
    extend	: 'Ext.data.Store',
	model	: 'DEMO.model.UploadDocLookupModel',

	pageSize	: pageSize,
	autoLoad	: true,//这里必须
	
	proxy : {
		type : 'ajax',
		reader : {
			type : 'json',
			root : 'root',
			totalProperty : 'totalCount',
			successProperty : 'success'
		},
		writer : {
			type : 'json',
			writeAllFields : true,
			encode : false
		//        root: 'root'
		},
		api : {
			read : 'dataPerm/list',
			create : 'dataPerm/create',
			update : 'dataPerm/edit',
			destroy : 'dataPerm/delete'
		},

		//封装后台Page对象
		pageParam : "pageNo",
		limitParam : "pageSize",
		// sortParam: "sort",
		//directionParam: "dir"
		//    simpleSortMode	: true,
		extraParams : {//用来排序,sql用法
			orderBy : 'zbxMc'
		},
		listeners : {
			exception : function(proxy, response, operation) {
				Ext.MessageBox.show({
					title : '系统错误',
					msg : Ext.decode(response.responseText).message,
					icon : Ext.MessageBox.ERROR,
					buttons : Ext.Msg.OK
				});
			}
		}

	}


});