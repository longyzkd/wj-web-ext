Ext.define('DEMO.view.upload.UploadDocList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.UploadDocList',

    id:'UploadDocList',
    store: 'UploadDocListStore',
//    selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
    selModel:Ext.create('Ext.selection.RowModel',{mode:"SINGLE"}),
    initComponent: function() {
        this.columns = [
                        { text: '序号', 
                	      renderer:function(value,metadata,record,rowIndex, colIndex, store) {
                          
                                 var start = store.lastOptions.start;
                                 if (isNaN(start)) 
                                 {
                                    start = 0;
                                 }
                                 return start + rowIndex + 1;   
//                    		   return rowIndex + 1; 
                           }
                        },
                        { text: '指标项名称', dataIndex: 'zbxMc', flex: 1 },
                        { text: '上传时间', dataIndex: 'createDate', flex: 1 },
                        { text: '文档名称', dataIndex: 'wdMc', flex: 1 },
                        { text: '文档提供者', dataIndex: 'createBy', flex: 1 },
                        { text: '状态', dataIndex: 'status', flex: 1 },
                        {   text: '操作',
                        	xtype:'actioncolumn',
                        	items:[{
                        		iconCls: 'icon-edit',
                        		tooltip: '补报'
                        	},'-',{
                        		iconCls: 'icon-del',
                        		tooltip: '删除'
                        	},'-',{
                        		iconCls: 'icon-view',
                        		tooltip: '详情'
                        	}] 
                        }
                        
                        	
                        
                        ];
        
        
     // Add the top toolbar
        this.dockedItems=[{
    		dock: 'top',
    		 xtype: 'toolbar',
    		items:[ 
		     
		       '->',
		       '部门',{
       			xtype: 'treepicker',
       			emptyText: '请选择',
//    			fieldLabel: '所属部门',
    			displayField: 'text',
    			valueField:'id',
    			name:'officeId',
    			id:'officeId',
    			minPickerHeight: 200,
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
    				},
    				autoLoad: false
    			})
    		},
    		'指标项名称',{
                xtype    : 'textfield',
                name     : 'zbxMc',
                id:'zbxMc',
                emptyText: '请输入'
            },'-','状态',{
                xtype    : 'combo',
                name     : 'status',
                id     : 'status',
                emptyText: '请选择',
		        store:  Ext.create('Ext.data.Store', {
		        	fields: ['code', 'name'],
        	    	proxy: {
        	    		type: 'ajax',
        	    		url: 'status.json',
        	    		reader: {
        	    			type: 'json'
        	    		}
        	    	},
        	    	autoLoad: false
		        }),
//		        queryMode: 'remote',
		        displayField: 'name',
		        valueField: 'code'
            },'-','上传时间',{
                xtype: 'datefield',
//                anchor: '100%',
                name: 'from_date',
                id: 'from_date',
                format :'Y-m-d',
                maxValue: new Date()  // limited to the current date or prior
            },'至',{
                xtype: 'datefield',
//                anchor: '100%',
                name: 'to_date',
                id: 'to_date',
                format :'Y-m-d',
                value: new Date()  // limited to the current date or prior
            },{
                text: '查询',
                action:'query',
                iconCls:'icon-search'
            }
           
		     ]
    	},{
    		dock: 'bottom',
            xtype: 'pagingtoolbar',
            store: 'UploadDocListStore',   // same store GridPanel is using
            displayInfo: true,
            displayMsg: '显示  {0} - {1} of {2}',
            afterPageText :'总共 {0} 页',
            beforePageText :'页',
            emptyMsg: "没有记录"
        }];

        this.callParent(arguments);
    }
});