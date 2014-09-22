Ext.define('DEMO.view.docShared.DocSharedList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.DocSharedList',

    store: 'DocSharedListStore',
   
    initComponent: function() {
        this.columns = [
                        { text: '指标项名称',  dataIndex: 'zbxmc' },
                        { text: '资源文档名称', dataIndex: 'docName', flex: 1 },
                        { text: '操作',
                        	xtype:'actioncolumn',
                        	items:[{
                        		iconCls: 'icon-save',
	                            tooltip: '下载',
	                            handler: function(grid, rowIndex, colIndex) {
	                                var rec = grid.getStore().getAt(rowIndex);
	                                alert("Edit " + rec.get('firstname'));
	                            	}
                        	}] 
                        }
                        	
                        
                        ];

        this.callParent(arguments);
    }
});