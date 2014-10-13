Ext.define('DEMO.view.permissionDocOffice.PermDataPanel' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.PermDataPanel',

//    layout: {
//        type: 'vbox',
//        align: 'center'
//    },
    layout:'border',
    items: [{
        id:'officeTree',
        region:'west',
        split:true,
        width:150,
        minWidth: 100,
        maxWidth: 250,
        layout:'fit',
        margin: '5 0 5 5',
        xtype:'treepanel',
        rootVisible:false,
		store: Ext.create('Ext.data.TreeStore',{
			fields: ['id','text'],
			root: {
				expanded: true
			},
			proxy: {
				type: 'ajax',
				url: "sys/user/getAllOfficeNodes",
				reader: {
					type: 'json'
				}
			}
		}),
		listeners: {
			 itemclick: function(s,r) {
				 var nodeId = r.data.id;
	                
	                Ext.Ajax.request({
    		   			url : 'dataPerm/getPermited',
    		   			params: {nodeId:nodeId},
//    		   			params: Ext.JSON.encode({selectedTreeData:selectedTreeData,selectedDocsData:selectedDocsData}),
    		   			async : false,//同步执行,为了争取返回exist，必须同步
    		   			method:'post',
//    		 			headers: {
//    		 	              'Content-Type': 'application/json;charset=utf-8'
//    		 	          },
    		              success: function(response){
    		               	var requestSuccess = Ext.JSON.decode(response.responseText).success;
    		               	if(requestSuccess){
    		               		var permitedDocs =Ext.JSON.decode(response.responseText).data;
    		               		Ext.getCmp('DocList').getSelectionModel().deselectAll();
    		               		Ext.each(permitedDocs,function(cur){
    		               			Ext.getCmp('DocList').getSelectionModel().select(new DEMO.model.UploadDocLookupModel(cur),true);
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
   },{
       region:'center',
       margin: '5 5 5 0',
       xtype:'DocList'
   }],
    
    
    initComponent: function() {
        

        this.callParent(arguments);
    }
});