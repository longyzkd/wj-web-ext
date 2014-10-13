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
    	var selectedTree = Ext.getCmp('officeTree').getSelectionModel( ). getSelection( );
    	var selectedDocs = Ext.getCmp('DocList').getSelectionModel( ). getSelection( );
    	console.log(selectedTree);
    	console.log(selectedDocs);
    	
    	if(selectedTree && selectedTree.length==1){
    		
    		if(selectedDocs){
    			var selectedTreeData = selectedTree[0].getData();
    			var selectedDocsData = [];
    			Ext.each(selectedDocs,function(cur){
    				selectedDocsData.push(cur.getData());
    			});
    			 Ext.Ajax.request({
    		   			url : 'dataPerm/edit',
    		   			params: Ext.encode({officeId:selectedTreeData.id,docsJson:Ext.encode(selectedDocsData)}),
//    		   			params: Ext.JSON.encode({selectedTreeData:selectedTreeData,selectedDocsData:selectedDocsData}),
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
    		}else{
    			 Ext.MessageBox.show({
                     title: '系统提示',
                     msg: '请至少选择一个文档',
                     icon: Ext.MessageBox.INFO,
                     buttons: Ext.Msg.OK
                 });
    		}
    	}else{
    		 Ext.MessageBox.show({
                 title: '系统提示',
                 msg: '请选择一个部门',
                 icon: Ext.MessageBox.INFO,
                 buttons: Ext.Msg.OK
             });
    	}
    	
    	
    }

});

