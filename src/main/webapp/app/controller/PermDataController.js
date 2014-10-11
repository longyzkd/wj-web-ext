Ext.define('DEMO.controller.PermDataController', {
    extend	: 'Ext.app.Controller',

    stores	: ['PermDataDocListStore'],
    models	: ['UploadDocLookupModel'],

    views	: [
		'permissionDocOffice.PermDataPanel','permissionDocOffice.DocList'
    ],
    refs: [{
	        ref: 'DocList',
	        selector: 'DocList'
	    }
    ],
    init	: function() {
        this.control({
			'DocList' : {
				show		: this.loadList
				
			},
			'DocList button[action=query]': {
				click: this.query
            },
            'DocList button[action=save]':{
            	click:this.save
            }
            
        });
    },

	// Reload the list every time it is shown
    loadList	: function(list) {
    	//不执行alert(1);
    	list.getStore().load();
    },
    query:function( e, eOpts){
   	 var zbxMc = e.up('toolbar').down('[name=zbxMc]').getValue();
   	 var wdMc = e.up('toolbar').down('[name=wdMc]').getValue();
   	 this.getPermDataDocListStoreStore().load({
   		 params:{
   			 	zbxMc: zbxMc,
   			 	wdMc:wdMc
   			  } 
   	 });
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
		
	 if(form.isValid( )){
				 
		 Ext.Ajax.request({
  			url : 'lookup/zbx/edit',
//  			params: {propertyJson:fieldSetJson,zbxMc:zbxMc,entityName:entityName,uploadCycle:uploadCycle},
  			params: Ext.JSON.encode({propertyJson:fieldSetJson,zbxMc:zbxMc,entityName:entityName,uploadCycle:uploadCycle}),
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
    
    }

});

