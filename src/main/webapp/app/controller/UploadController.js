Ext.define('DEMO.controller.UploadController', {
    extend	: 'Ext.app.Controller',

    stores	: ['UploadDocListStore'],
    models	: ['UploadDocLookupModel'],

    views	: [
		'upload.UploadDocList','upload.UploadPanel','upload.DocView'
    ],
    refs: [{
	        ref: 'UploadDocList',
	        selector: 'UploadDocList'
	    }
    ],
    init	: function() {
        this.control({
			'UploadDocList' : {
				show		: this.loadList
				
			},
			'UploadDocList button[action=query]': {
				click: this.query
            },
			'UploadPanel form button[action=upload]': {
				click: this.upload
            },
            'UploadDocList actioncolumn': {
				click: this.onAction
            }
            
        });
    },

	// Reload the list every time it is shown
    loadList	: function(list) {
    	//不执行alert(1);
    	list.getStore().load();
    },
    upload:function( e, eOpts){
     var form = e.up('form').getForm(),
         store =  this.getUploadDocListStoreStore();
     if(form.isValid()){
         form.submit({
             url: 'upload/upload',
             waitMsg: '上传中...',
             success: function(form, action) {
            		 Ext.MessageBox.show({
                         title: '提示',
                         msg: action.result.message,
                         icon: Ext.MessageBox.INFO,
                         buttons: Ext.Msg.OK,
                         fn: function(buttonId) {
                             if (buttonId === "ok") {
                            	store.reload();
                             }
                         }
                     });
            	 
             },
             failure: function(form, action) {//form(success:false) 和 ajax(通信故障) 区别
            	 console.log(action);
            	 Ext.MessageBox.show({
                     title: '提示',
                     msg: action.result.message,
                     icon: Ext.MessageBox.ERROR,
                     buttons: Ext.Msg.OK
                 });
             }
         });
     }
   },
   query:function( e, eOpts){
	   this.getUploadDocListStoreStore().load();
    },
    onAction: function(view,cell,row,col,e){
        var m = e.getTarget().className.match(/\bicon-(\w+)\b/)
        if(m){
            //选择该列
            this.getUploadDocList().getView().getSelectionModel().select(row,false)
            switch(m[1]){
                case 'edit':
                	var record = this.getUserListStoreStore().getAt(row);
                	var editWin = Ext.widget('UserEdit').show();
                	editWin.setTitle('修改用户');
                	if(record){
//                		editWin.down('form').getForm().findField('loginName').set({action:'edit'});
                		Ext.apply(Ext.getCmp('loginName'), {action:'edit'}, {});
                		Ext.apply(Ext.getCmp('loginName'), {myrawValue:record.get('loginName')}, {});
                		editWin.down('form').loadRecord(record);
                	}
                    break;
                case 'del':
                	var store = this.getUploadDocListStoreStore(),
                	   record = store.getAt(row);
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
           		 
                    break;
                case 'view':
                	var record = this.getUploadDocListStoreStore().getAt(row);
                	var editWin = Ext.widget('DocView').show();
                	editWin.setTitle('文档详情');
                	if(record){
                		var dynamicStore = Ext.data.StoreManager.lookup('dynamicStore');
                		dynamicStore.load();
//                		dynamicStore.load({
//                			params:{
//                				id : record.get('id'),
//                				zbxMc : record.get('zbxMc')
//                			} 
//                		});
//                		dynamicStore.proxy.extraParams={upload : Ext.encode(record.getData())};
//                		dynamicStore.loadPage(1);
                	}
                    break;
               default:
            	   break;
                    
            }
        }
    }

});

