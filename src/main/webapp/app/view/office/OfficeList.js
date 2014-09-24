Ext.define('DEMO.view.office.OfficeList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.OfficeList',

    store: 'OfficeListStore',
   
    initComponent: function() {
        this.columns = [
                        { text: '序号',  dataIndex: 'zbxmc' },
                        { text: '部门名称', dataIndex: 'name', flex: 1 },
                        { text: '部门编码', dataIndex: 'officeCode', flex: 1 },
                        { text: '拼音码', dataIndex: 'spellCode', flex: 1 },
                        
                        { text: '操作',
                        	xtype:'actioncolumn',
                        	items:[{
                        		iconCls: 'icon-save',
                        		tooltip: '编辑',
                        		handler: function(grid, rowIndex, colIndex) {
                        			var rec = grid.getStore().getAt(rowIndex);
                        			alert("Edit " + rec.get('firstname'));
                        		}
                        	},{
                        		iconCls: 'icon-save',
                        		tooltip: '删除',
                        		handler: function(grid, rowIndex, colIndex) {
                        			var rec = grid.getStore().getAt(rowIndex);
                        			alert("Edit " + rec.get('firstname'));
                        		}
                        	},{
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
    		items:[ '->','部门名称',{
                xtype    : 'textfield',
                name     : 'field1',
                emptyText: '请输入'
            },'部门编码',{
                xtype    : 'textfield',
                name     : 'field1',
                emptyText: '请输入'
            },{
                text: '查询'
            }]
    	},{
    		dock: 'bottom',
            xtype: 'pagingtoolbar',
            store: 'OfficeListStore',   // same store GridPanel is using
            displayInfo: true
        }];

        this.callParent(arguments);
    }
});