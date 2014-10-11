Ext.define('DEMO.view.permissionDocOffice.DocList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.DocList',

    store: 'PermDataDocListStore',
    selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
//    selModel:Ext.create('Ext.selection.RowModel',{mode:"SIMPLE"}),
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
                        { text: '文档名称', dataIndex: 'wdMc', flex: 1 },
                        { text: '上传人', dataIndex: 'createBy', flex: 1 },
                        { text: '上传时间', dataIndex: 'createDate', flex: 1 },
                        { text: '状态', dataIndex: 'status', flex: 1 }
                        
                        	
                        
                        ];
        
        
     // Add the top toolbar
        this.dockedItems=[{
    		dock: 'top',
    		 xtype: 'toolbar',
    		items:[ {
                text: '保存',
                action:'save',
                iconCls:'icon-save'
            },
		     
		       '->',
    		'指标项名称',{
                xtype    : 'textfield',
                name     : 'zbxMc',
                emptyText: '请输入'
            },'-','文档名称',{
                xtype    : 'textfield',
                name     : 'wdMc',
                emptyText: '请输入'
            },{
                text: '查询',
                action:'query',
                iconCls:'icon-search'
            }
		     ]
    	},{
    		dock: 'bottom',
            xtype: 'pagingtoolbar',
            store: 'PermDataDocListStore',   // same store GridPanel is using
            displayInfo: true,
            displayMsg: '显示  {0} - {1} of {2}',
            afterPageText :'总共 {0} 页',
            beforePageText :'页',
            emptyMsg: "没有记录"
        }];

        this.callParent(arguments);
    }
});