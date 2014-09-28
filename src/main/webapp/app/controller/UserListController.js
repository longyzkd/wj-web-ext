Ext.define('DEMO.controller.UserListController', {
    extend	: 'Ext.app.Controller',

    stores	: ['UserListStore'],
    models	: ['UserListModel'],

    views	: [
		'user.UserList','user.UserEdit'
    ],
    refs: [{
	        ref: 'UserList',
	        selector: 'UserList'
	    }
    ],
    init	: function() {
        this.control({
			'UserList' : {
				show		: this.loadList
				
			},
			'UserList button[action=query]': {
				click: this.query
            },
            'UserList button[action=add]': {
            	click: this.add
            },
            'UserList button[action=del]': {
            	click: this.del
            },
            'UserList actioncolumn ': {
            	click: this.onAction
            },
            'UserEdit button[action=save]':{
            	click:this.save
            }
            
        });
    },

	// Reload the list every time it is shown
    loadList	: function(list) {
    	list.getStore().load();
    },
    query:function( e, eOpts){
    	var name = e.up('toolbar').down('[name=name]').getValue();
    	var loginName = e.up('toolbar').down('[name=loginName]').getValue();
    	 this.getUserListStoreStore().load({
    		 params:{
    			 	name: name,
    			 	loginName:loginName
    			  } 
    	 });
    },
    del:function(){
    	var grid = this.getUserList(),
    	record = grid.getSelectionModel().getSelection(), 
        store = this.getUserListStoreStore();

    	if(record && record.length){
    		 store.remove(record);
    		 store.sync({
                 success: function(batch) {
                	 console.log(batch);
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
        var editWin = Ext.widget('UserEdit') .show();
        editWin.setTitle('新增用户');
        
    },
    //真正的保存
    save: function(button) {
        var win    = button.up('window'),
            form   = win.down('form'),
            record = form.getRecord(),
            values = form.getValues();
        
        var UserListStore = this.getUserListStoreStore();
		if (values.id > 0){//edit
			record.set(values);
		} else{
			record = Ext.create('DEMO.model.UserListModel');
			record.set(values);
//			record.setId(0);
			UserListStore.add(record);
			
		}
		 if(form.isValid( )){
			 UserListStore.sync({
	                success: function(batch)   {
		               	 Ext.MessageBox.show({
		                        title: '提示',
		                        msg: Ext.decode(batch.operations[0].response.responseText).message,
		                        icon: Ext.MessageBox.INFO,
		                        buttons: Ext.Msg.OK,
		                        fn: function(buttonId) {
		                            if (buttonId === "ok") {
		                            	win.close();
		                            	UserListStore.reload();
		                            }
		                        }
		                    });
		               	
	                }
	   		 	});
	        	
	        }
    
    },
    onAction: function(view,cell,row,col,e){
        var m = e.getTarget().className.match(/\bicon-(\w+)\b/)
        if(m){
            //选择该列
            this.getUserList().getView().getSelectionModel().select(row,false)
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
               default:
            	   break;
                    
            }
        }
    }

});

