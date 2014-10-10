Ext.define('DEMO.view.zbx.ZbxList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.ZbxList',

    store: 'ZbxListStore',
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
                        { text: '上传周期', dataIndex: 'uploadCycle', flex: 1 },
                        { text: '表名', dataIndex: 'entityName', flex: 1 },
                        
                        { text: '操作',
                        	xtype:'actioncolumn',
                        	items:[{
                        		iconCls: 'icon-edit',
                        		tooltip: '编辑'
                        	}] 
                        }
                        	
                        
                        ];
        
        
     // Add the top toolbar
        this.dockedItems=[{
    		dock: 'top',
    		 xtype: 'toolbar',
    		items:[
//    		       {text:'新增',iconCls:'icon-add',action:'add'},
//    		       '-',
//    		       {text:'删除所选',iconCls:'icon-del',action:'del'},
    		     
    		       '->',
		       '指标项名称',{
                xtype    : 'textfield',
                name     : 'zbxMc',
                emptyText: '请输入'
            },{
                text: '查询',
                action:'query',
                iconCls:'icon-search'
            }]
    	},{
    		dock: 'bottom',
            xtype: 'pagingtoolbar',
            store: 'ZbxListStore',   // same store GridPanel is using
            displayInfo: true,
            displayMsg: '显示  {0} - {1} of {2}',
            afterPageText :'总共 {0} 页',
            beforePageText :'页',
            emptyMsg: "没有记录"
        }];

        this.callParent(arguments);
    }
});