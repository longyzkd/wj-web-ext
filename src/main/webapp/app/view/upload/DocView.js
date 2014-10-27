

/**
 * 动态grid
 * @author wj
 * @date 2014-10-27
 */
Ext.define('DEMO.view.upload.DocView', {
    extend: 'Ext.window.Window',
    alias : 'widget.DocView',

    id:'DocView',
    requires: ['Ext.form.Panel','Ext.form.field.Text','Ext.ux.TreePicker'],

    layout: 'fit',
    autoShow: true,
    width: 1050,
    modal: true,
    iconCls: 'icon-user',
    keys: {
        key: Ext.EventObject.ESC,
        fn: function() {
                    this.close();
                },
        scope: this
    },
    initComponent: function() {
        this.items = [
            
            	Ext.create('Ext.grid.Panel', {
//                    title: 'Grid',
                    id:'DocViewGrid',
                    store: Ext.data.StoreManager.lookup('dynamicStore'),
                    columns: [ ],
                    height: 200,
                    width: 400,
                    renderTo: Ext.getBody(),
                    dockedItems:[ {
                		dock: 'bottom',
                        xtype: 'pagingtoolbar',
                        store:  Ext.data.StoreManager.lookup('dynamicStore'),   // same store GridPanel is using
                        displayInfo: true,
                        displayMsg: '显示  {0} - {1} of {2}',
                        afterPageText :'总共 {0} 页',
                        beforePageText :'页',
                        emptyMsg: "没有记录"
                    }]
                })
            	
            
        ];
        
        this.dockedItems = [{}];

        this.callParent(arguments);
    }
});

var store = Ext.create('Ext.data.Store', {
    storeId:'dynamicStore',
    autoLoad: false,
    pageSize	: pageSize,
    proxy: {
        type: 'ajax',
//        url: 'data.json',
        url: 'upload/view',
        pageParam : "pageNo",
		limitParam : "pageSize",
		actionMethods : {
	        create  : 'POST',
	        read    : 'POST',
	        update  : 'PUT',
	        destroy : 'DELETE'
	    },
//		headers: {
//            'Content-Type': 'application/json;charset=utf-8'
//        },
//		writer: {
//            type: 'json',
//            writeAllFields: true,
//            encode: false
////            root: 'root'
//        },
	   
        reader: {
            type: 'json',
            root: 'data',
            totalProperty: "total",
            successProperty: "success"
            
        }
    },
    listeners: {
    	metachange: function(store, meta){
    		var grid = Ext.getCmp('DocViewGrid');
    	    grid.reconfigure(store, meta.columns);
    	    console.log(store);
    	    console.log(meta);
    	},
    	beforeload:function(store, operation, eOpts){//保持参数
    		var record = Ext.getCmp('UploadDocList').getSelectionModel().getSelection()[0];
			var id =record?record.get('id'):'';
	    	var zbxMc = record?record.get('zbxMc'):'';
			store.proxy.extraParams={
					orderBy:'xh',
					id:id,
	    			zbxMc: zbxMc
	    		}
		}
    }
});
//store.on('metachange',function(store, meta){
//    grid.reconfigure(store, meta.columns);
//    console.log(store);
//    console.log(meta);
//});
