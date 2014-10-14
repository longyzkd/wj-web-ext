Ext.define('DEMO.controller.ZbxListController', {
    extend	: 'Ext.app.Controller',

    stores	: ['ZbxListStore'],
    models	: ['ZbxListModel'],

    views	: [
		'zbx.ZbxList','zbx.ZbxEdit'
    ],
    refs: [{
	        ref: 'ZbxList',
	        selector: 'ZbxList'
	    }
    ],
    init	: function() {
        this.control({
			'ZbxList' : {
				show		: this.loadList
				
			},
			'ZbxList button[action=query]': {
				click: this.query
            },
            'ZbxList button[action=add]': {
            	click: this.add
            },
            'ZbxList button[action=del]': {
            	click: this.del
            },
            'ZbxList actioncolumn ': {
            	click: this.onAction
            },
            'ZbxEdit button[action=save]':{
            	click:this.save
            }
            
        });
    },

	// Reload the list every time it is shown
    loadList	: function(list) {
    	list.getStore().load();
    },
    query:function( e, eOpts){
    	var zbxMc = e.up('toolbar').down('[name=zbxMc]').getValue();
    	 this.getZbxListStoreStore().load({
    		 params:{
    			 	zbxMc: zbxMc
    			  } 
    	 });
    },
    del:function(){
    	var grid = this.getZbxList(),
    	record = grid.getSelectionModel().getSelection(), 
        store = this.getZbxListStoreStore();

    	if(record && record.length){
    		 store.remove(record);
    		 store.sync({
                 success: function(batch) {
                	 Ext.MessageBox.show({
                         title: '提示',
                         msg: Ext.decode(batch.operations[0].response.responseText).message,
                         icon: Ext.MessageBox.INFO,
                         buttons: Ext.Msg.OK,
                         fn: function(buttonId) {
                             if (buttonId === "ok") {
                            	 store.reload();
                             }
                         }
                     });
            		 
                 },
	             failure: function(batch){
                        Ext.MessageBox.show({
	                         title: "错误",
	                         msg:Ext.decode(batch.operations[0].response.responseText).message,
	                         icon: Ext.MessageBox.ERROR,
	                         buttons: Ext.MessageBox.OK
                        });  
	
	             }
    		 });
    		 
    		
    	}else{
    		 Ext.MessageBox.show({
                 title: '提示',
                 msg: '请至少选择一条记录',
                 icon: Ext.MessageBox.INFO,
                 buttons: Ext.Msg.OK
             });
    	}
	   
    },
    //窗口
    add: function(button) {
        var editWin = Ext.widget('ZbxEdit') .show();
        editWin.setTitle('新增指标项');
        
    },
    //真正的保存
    save: function(button) {
        var win    = button.up('window'),
            form   = win.down('form'),
            fieldsetForm = form.down('#fieldsetForm');
            values = form.getValues();
        
        var formJson = Ext.JSON.encode(values);
        var fieldSetJson = Ext.JSON.encode(fieldsetForm.getValues());
        
        var ZbxListStore = this.getZbxListStoreStore();
        
			var zbxMc = Ext.getCmp('zbxMc').getValue();
			var entityName = Ext.getCmp('entityName').getValue();
			var uploadCycle = Ext.getCmp('uploadCycle').getValue();
			var officeId = Ext.getCmp('officeId').getValue();
		
	 if(form.isValid( )){
				 
		 Ext.Ajax.request({
  			url : 'lookup/zbx/edit',
//  			params: {propertyJson:fieldSetJson,zbxMc:zbxMc,entityName:entityName,uploadCycle:uploadCycle},
  			params: Ext.JSON.encode({propertyJson:fieldSetJson,zbxMc:zbxMc,entityName:entityName,uploadCycle:uploadCycle,officeId:officeId}),
  			async : false,//同步执行,为了争取返回exist，必须同步
  			method:'post',
			headers: {
	              'Content-Type': 'application/json;charset=utf-8'
	          },
             success: function(response){
              	var requestSuccess = Ext.JSON.decode(response.responseText).success;
              	if(requestSuccess){
              		 Ext.MessageBox.show({
	                        title: '提示',
	                        msg:  Ext.JSON.decode(response.responseText).message,
	                        icon: Ext.MessageBox.INFO,
	                        buttons: Ext.Msg.OK,
	                        fn: function(buttonId) {
	                            if (buttonId === "ok") {
	                            	win.close();
	                            	ZbxListStore.reload();
	                            }
	                        }
	                    });
              	}else{
              		 Ext.MessageBox.show({
                           title: '系统错误',
                           msg: Ext.decode(response.responseText).message,
                           icon: Ext.MessageBox.ERROR,
                           buttons: Ext.Msg.OK
                       });
              	}
              	
              },
             failure: function(response){}
          });
			 }
    
    },
    onAction: function(view,cell,row,col,e){
        var m = e.getTarget().className.match(/\bicon-(\w+)\b/)
        if(m){
            //选择该列
            this.getZbxList().getView().getSelectionModel().select(row,false)
            switch(m[1]){
                case 'edit':
                	var record = this.getZbxListStoreStore().getAt(row);
                	var editWin = Ext.widget('ZbxEdit').show();
                	editWin.setTitle('修改指标项');
                	if(record){
//                		editWin.down('form').getForm().findField('loginName').set({action:'edit'});
                		Ext.apply(Ext.getCmp('zbxMc'), {action:'edit'}, {});
                		Ext.apply(Ext.getCmp('zbxMc'), {myrawValue:record.get('zbxMc')}, {});
                		editWin.down('form').loadRecord(record);
//                		editWin.down('combo').select('Wshtba');
//                		editWin.down('combo').fireEvent('select');
                		
                		
                		 Ext.Ajax.request({
         	     			url : 'lookup/zbx/getProperty',// 获取面板的地址
         	     			params : {
         	     				entityName:record.get('entityName')
         	     			},
         	     			async : true,
         	     			method:'post',
         	                success: function(response){
         	                 	var requestSuccess = Ext.JSON.decode(response.responseText).success;
         	                 	if(requestSuccess){
         	                 		data = Ext.JSON.decode(response.responseText).data;
         	                 		var fieldSet = Ext.getCmp('field_container');
         	                 		fieldSet.removeAll();
         	                 		Ext.each(data,function(cur){
         	                 			var item =  Ext.create('Ext.form.TextField', {
	            	                 	                xtype     : 'textfield',
	            	                 	                name      : cur.propertyName,
	            	                 	                fieldLabel: cur.propertyName,
	            	                 	                value:cur.propertyText,
	            	                 	                anchor: '100%',
	            	                                    labelAlign: 'left',
	            	                                    allowBlank: false,
	            	                                    combineErrors: true,
	            	                                    msgTarget: 'side'
	            	                 	            });
         	                 			
         	                 			
         	                 			fieldSet.add(item);
         	                 			
         	                 		});
         	                 	}else{
         	                 		 Ext.MessageBox.show({
         	                              title: '系统错误',
         	                              msg: Ext.decode(response.responseText).message,
         	                              icon: Ext.MessageBox.ERROR,
         	                              buttons: Ext.Msg.OK
         	                          });
         	                 	}
         	                 	
         	                 },
         	                failure: function(response){}
         	             });
                	}
                    break;
               default:
            	   break;
                    
            }
        }
    }

});

