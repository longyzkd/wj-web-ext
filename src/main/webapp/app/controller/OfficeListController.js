Ext.define('DEMO.controller.OfficeListController', {
    extend	: 'Ext.app.Controller',

    stores	: ['OfficeListStore'],
    models	: ['OfficeListModel'],

    views	: [
		'office.OfficeList'
    ],
    refs: [{
	        ref: 'OfficeList',
	        selector: 'OfficeList'
	    }
    ],
    init	: function() {
        this.control({
			'OfficeList' : {
				show		: this.loadList
				
			},
			'OfficeList button[action=query]': {
				click: this.query
            },
            'OfficeList button[action=add]': {
            	click: this.add
            },
            'OfficeList button[action=del]': {
            	click: this.del
            }
        });
    },

	// Reload the list every time it is shown
    loadList	: function(list) {
    	list.getStore().load();
    },
    query:function( e, eOpts){
    	var name = e.up('toolbar').down('[name=name]').getValue();
    	var officeCode =  e.up('toolbar').down('[name=officeCode]').getValue();
    	 this.getOfficeListStoreStore().load({
    		 params:{
    			 	name: name,
    			 	officeCode: officeCode
    			  } 
    	 });
    },
    del:function(){
    	var grid = this.getOfficeList(),
    	record = grid.getSelectionModel().getSelection(), 
        store = this.getOfficeListStoreStore();

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
    add:function(){
    	
    }

});