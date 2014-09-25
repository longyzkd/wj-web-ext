Ext.define('DEMO.view.office.OfficeList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.OfficeList',

    store: 'OfficeListStore',
//    selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
    selModel:Ext.create('Ext.selection.RowModel',{mode:"SIMPLE"}),
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
                        { text: '部门名称', dataIndex: 'name', flex: 1 },
                        { text: '部门编码', dataIndex: 'officeCode', flex: 1 },
                        { text: '拼音码', dataIndex: 'spellCode', flex: 1 },
                        
                        { text: '操作',
                        	xtype:'actioncolumn',
                        	items:[{
                        		iconCls: 'icon-edit',
                        		tooltip: '编辑',
                        		action:'edit'/*,
                        		handler: function(grid, rowIndex, colIndex) {
                        			var rec = grid.getStore().getAt(rowIndex);
                        			alert("Edit " + rec.get('firstname'));
                        		}*/
                        	},'-',{
                        		iconCls: 'icon-del',
                        		tooltip: '删除',
                        		handler: function(grid, rowIndex, colIndex) {
                        			var rec = grid.getStore().getAt(rowIndex);
                        			alert("Edit " + rec.get('firstname'));
                        		}
                        	},'-',{
                        		iconCls: 'icon-save',
                        		tooltip: '查看',
                        		handler: function(grid, rowIndex, colIndex) {
                        			var rec = grid.getStore().getAt(rowIndex);
                        			alert("Edit " + rec.get('firstname'));
                        		}
                        	}] 
                        }
                        	
                        
                        ];
        
        
     // Add the top toolbar
        this.dockedItems=[{
    		dock: 'top',
    		 xtype: 'toolbar',
    		items:[
    		       {text:'新增部门',iconCls:'icon-add',action:'add'},
    		       '-',
    		       {text:'删除所选',iconCls:'icon-del',action:'del'},
    		       '->',
    		       '部门名称',{
                xtype    : 'textfield',
                name     : 'name',
                emptyText: '请输入'
            },'部门编码',{
                xtype    : 'textfield',
                name     : 'officeCode',
                emptyText: '请输入'
            },{
                text: '查询',
                action:'query',
                iconCls:'icon-search'
            }]
    	},{
    		dock: 'bottom',
            xtype: 'pagingtoolbar',
            store: 'OfficeListStore',   // same store GridPanel is using
            displayInfo: true,
            displayMsg: '显示  {0} - {1} of {2}',
            afterPageText :'总共 {0} 页',
            beforePageText :'页',
            emptyMsg: "没有记录"
        }];

        this.callParent(arguments);
    }
});