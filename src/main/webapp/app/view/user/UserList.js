Ext.define('DEMO.view.user.UserList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.UserList',

    store: 'UserListStore',
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
                        { text: '用户名称', dataIndex: 'name', flex: 1 },
                        { text: '所属部门', dataIndex: 'officeName', flex: 1 },
//                        { text: '所属部门', dataIndex: 'officeId', flex: 1 },
                        { text: '电话', dataIndex: 'phone', flex: 1 },
                        { text: 'email', dataIndex: 'email', flex: 1 },
                        
                        { text: '操作',
                        	xtype:'actioncolumn',
                        	items:[{
                        		iconCls: 'icon-edit',
                        		tooltip: '编辑'
                        	},'-',{
                        		iconCls: 'icon-updatePwd',
                        		tooltip: '修改密码'
                        	}] 
                        }
                        	
                        
                        ];
        
        
     // Add the top toolbar
        this.dockedItems=[{
    		dock: 'top',
    		 xtype: 'toolbar',
    		items:[
    		       {text:'新增用户',iconCls:'icon-add',action:'add'},
    		       '-',
    		       {text:'删除所选',iconCls:'icon-del',action:'del'},
    		     
    		       '->',
		       '用户名称',{
                xtype    : 'textfield',
                name     : 'name',
                emptyText: '请输入'
            },'登陆名',{
                xtype    : 'textfield',
                name     : 'loginName',
                emptyText: '请输入'
            },{
                text: '查询',
                action:'query',
                iconCls:'icon-search'
            }]
    	},{
    		dock: 'bottom',
            xtype: 'pagingtoolbar',
            store: 'UserListStore',   // same store GridPanel is using
            displayInfo: true,
            displayMsg: '显示  {0} - {1} of {2}',
            afterPageText :'总共 {0} 页',
            beforePageText :'页',
            emptyMsg: "没有记录"
        }];

        this.callParent(arguments);
    }
});