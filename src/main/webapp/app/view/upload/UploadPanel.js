Ext.define('DEMO.view.upload.UploadPanel' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.UploadPanel',

//    layout: {
//        type: 'vbox',
//        align: 'center'
//    },
    layout:'border',
    items: [{
        id:'uploadForm',
        region:'north',
        split:true,
        heigth:150,
        minHeigth: 100,
        maxHeigth: 250,
        layout:'fit',
        margin: '5 5 0 5',

        items:[{
				xtype: 'form',
				padding: '5 5 0 5',
				border: false,
				style: 'background-color: #fff;',
				 width: 350,
				fieldDefaults: {
				    anchor: '50%',
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
				        fieldLabel: '文档上传模式',
				        xtype      : 'fieldcontainer',
			            defaultType: 'radiofield',
//			            defaults: {
//			                flex: 1
//			            },
			            layout: 'hbox',
			            items: [
			                {
			                    boxLabel  : '指标上传',
			                    name      : 'size',
			                    inputValue: 'm',
			                    id        : 'radio1',
			                    checked   : true
			                },{
			                    boxLabel  : '多文件上传',
			                    name      : 'size',
			                    inputValue: 'l',
			                    id        : 'radio2'
			                }
			            ]
				       
				    },
					 {
				        xtype: 'combo',
				        name : 'zbxMc',
				        fieldLabel: '选择指标项',
				        emptyText:'请选择',
				        store:  Ext.create('Ext.data.Store', {
				        	fields: ['code', 'name'],
                	    	proxy: {
                	    		type: 'ajax',
                	    		url: 'upload/getZbx',
                	    		reader: {
                	    			type: 'json',
                    	             root: 'data'
                	    		}
                	    	},
                	    	autoLoad: false
				        }),
				        queryMode: 'remote',
				        displayField: 'name',
				        valueField: 'code'
				       
				    }, {
				        xtype: 'filefield',
				        name : 'zbxMc',
				        fieldLabel: '选择上传文档',
				        buttonText: '浏览',
				        emptyText:'请选择'
				       
				    }],
				    buttonAlign: 'center',
				    buttons:[{
		                iconCls: 'icon-upload',
		                itemId: 'upload',
		                text: '上传',
		                action: 'upload'
				    }]
			
		      }]
    
   },{
	   region:'center',
	   margin: '0 5 5 5',
	   xtype:'UploadDocList'
   }],
    
    
    initComponent: function() {
        

        this.callParent(arguments);
    }
});