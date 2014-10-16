Ext.define('DEMO.store.UploadDocListStore', {
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
			read : 'upload/list',
			create : 'upload/create',
			update : 'upload/edit',
			destroy : 'upload/delete'
		},

		//封装后台Page对象
		pageParam : "pageNo",
		limitParam : "pageSize",
		// sortParam: "sort",
		//directionParam: "dir"
		//    simpleSortMode	: true,
//		extraParams : {//用来排序,sql用法
//			orderBy : 'zbxMc'
//		},
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

	},
	listeners:{
		beforeload:function(store, operation, eOpts){//保持分页参数
			var officeId = Ext.getCmp('officeId')?Ext.getCmp('officeId').getValue():'';
	    	var zbxMc = Ext.getCmp('zbxMc')? Ext.getCmp('zbxMc').getValue():'';
	    	var status = Ext.getCmp('status')?Ext.getCmp('status').getValue():'';
	    	var from = Ext.getCmp('from_date')?Ext.getCmp('from_date').getValue():'';
	    	var to = Ext.getCmp('to_date')?Ext.getCmp('to_date').getValue():'';
			store.proxy.extraParams={
					orderBy:'zbxMc',
					
					officeId:officeId,
	    			zbxMc: zbxMc,
	    			status:status,
	    			from:Ext.Date.format(from,'Y-m-d '),
	    			to:Ext.Date.format(to,'Y-m-d ')
	    			
	    		}
		}
	}


});