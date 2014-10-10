//var model = Ext.define("TreeModel", { // 定义树节点数据模型
//	extend : "Ext.data.Model",
//	fields : [{name : "id",type : "string"},
//			{name : "text",type : "string"},
//			{name : "iconCls",type : "string"},
//			{name : "leaf",type : "boolean"},
//			{name : 'type'},
//			{name : 'component'}]
//});

/**
 * 新增 修改 查看窗口
 */
Ext.define('DEMO.view.zbx.ZbxEdit', {
    extend: 'Ext.window.Window',
    alias : 'widget.ZbxEdit',

    requires: ['Ext.form.Panel','Ext.form.field.Text','DEMO.view.DynamicForm'],

    layout: 'fit',
    autoShow: true,
    width: 500,
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
            {
                xtype: 'form',
                id:'ZbxEditForm',
//                xtype: 'dynamicform',
                padding: '5 5 0 5',
                border: false,
                style: 'background-color: #fff;',
                
                fieldDefaults: {
                    anchor: '100%',
                    labelAlign: 'left',
                    allowBlank: false,
                    combineErrors: true,
                    msgTarget: 'side'
                },

                items: [
					{
					    xtype: 'textfield',
					    name : 'id',
					    fieldLabel: 'id',
					    hidden:true,
					    allowBlank: true
					},    
                    {
                        xtype: 'textfield',
                        name : 'zbxMc',
                        id:'zbxMc',
                        fieldLabel: '指标项名称',
                        maxLength :50,
                        vtype:'checkunique',
                        beanClazz:'Zbx',
                        property:'zbxMc'
                       
                    },
//                    {
//                    	xtype: 'combo',
//                    	name : 'entityName',
//                    	id:'entityName',
//                    	fieldLabel: '表名',
//                	    queryMode: 'remote',
//                	    displayField: 'entityName',
//                	    valueField: 'entityName',
//                	    store:  Ext.create('Ext.data.Store', {
//                	    	fields: ['entityName'],
//                	    	proxy: {
//                	    		type: 'ajax',
//                	    		url: 'lookup/zbx/getEntitys',
//                	    		reader: {
//                	    			type: 'json',
//                    	             root: 'data'
//                	    		}
//                	    	}
//                	    }),
//                	    listeners : {   
//                	        select : function(combo,records,options){   
////                	        	var items = this.up('form').items;
////    	                 		console.log(items);
//                	        	 Ext.Ajax.request({
//                	     			url : 'lookup/zbx/getProperty',// 获取面板的地址
//                	     			params : {
//                	     				entityName:combo.getValue()
//                	     			},
//                	     			async : true,
//                	     			method:'post',
//                	                success: function(response){
//                	                 	var requestSuccess = Ext.JSON.decode(response.responseText).success;
//                	                 	if(requestSuccess){
//                	                 		data = Ext.JSON.decode(response.responseText).data;
////                	                 		var items = this.down('form').items;
////                	                 		var items = Ext.getCmp('ZbxEditForm').items;
////                	                 		var items = [];
//                	                 		var fieldSet = Ext.getCmp('field_container');
//                	                 		fieldSet.removeAll();
//                	                 		Ext.each(data,function(cur){
//                	                 			var item =  Ext.create('Ext.form.TextField', {
//		            	                 	                xtype     : 'textfield',
//		            	                 	                name      : cur.propertyName,
//		            	                 	                fieldLabel: cur.propertyName,
//		            	                 	                value:cur.propertyText,
//		            	                 	                anchor: '100%',
//		            	                                    labelAlign: 'left',
//		            	                                    allowBlank: false,
//		            	                                    combineErrors: true,
//		            	                                    msgTarget: 'side'
//		            	                 	            });
//                	                 			
//                	                 			
//                	                 			fieldSet.add(item);
////                	                 			items.push(item);
////                	                 			items.add(item);
//                	                 			
//                	                 		});
////                	                 		Ext.apply(Ext.getCmp('ZbxEditForm'), {
////                	                 		    items:items
////                	                 		});
//                	                 		
//                	                 	}else{
//                	                 		 Ext.MessageBox.show({
//                	                              title: '系统错误',
//                	                              msg: Ext.decode(response.responseText).message,
//                	                              icon: Ext.MessageBox.ERROR,
//                	                              buttons: Ext.Msg.OK
//                	                          });
//                	                 	}
//                	                 	
//                	                 },
//                	                failure: function(response){}
//                	             });
//                	        }   
//                	    }  
//                	
//                    },
                    
            		{
                    	xtype:'displayfield',
                    	name : 'entityName',
                    	id:'entityName',
                    	fieldLabel: '表名'
            		},
                    {
                        xtype: 'combo',
                        name : 'uploadCycle',
                        id:'uploadCycle',
                        fieldLabel: '上传周期',
                        queryMode: 'remote',
                	    displayField: 'name',
                	    valueField: 'code',
                	    store:  Ext.create('Ext.data.Store', {
                	    	fields: ['code', 'name'],
                	    	proxy: {
                	    		type: 'ajax',
                	    		url: 'cycle.json',
                	    		reader: {
                	    			type: 'json'
//                    	             root: 'users'
                	    		}
                	    	},
                	    	autoLoad: true,
                	    	listeners : { 
                	    		load : function(){ 
//	                    	           subjectField.setValue(record.get("drug.subjectCode")); 
                	    		} 
                	    	} 
                	    })
                    },
                   { xtype: 'form',
                	 id:'fieldsetForm',
                     items:[{
                       id:'field_container',
                       xtype: 'fieldset',
                       layout: 'anchor',
                       border: 0,
                       style: { padding: '0' },
                       fieldDefaults: {
                           // field defaults
                       },
                       defaultType: 'textfield'
                   }]} 
                    
                ]
            }
        ];
        
        this.dockedItems = [{
            xtype: 'toolbar',
            dock: 'bottom',
            id:'buttons',
            ui: 'footer',
            items: ['->', {
//            	 formBind: true, //only enabled once the form is valid
//                 disabled: true,
                iconCls: 'icon-save',
                itemId: 'save',
                text: '保存',
                action: 'save'
            },{
                iconCls: 'icon-reset',
                text: '重置',
                scope: this,//window
                handler: function(){
                	this.down('form').getForm().reset();
                }
            }]
        }];

        this.callParent(arguments);
    }
});
