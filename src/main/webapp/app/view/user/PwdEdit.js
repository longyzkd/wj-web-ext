//var model = Ext.define("TreeModel", { // 定义树节点数据模型
//	extend : "Ext.data.Model",
//	fields : [{name : "id",type : "string"},
//			{name : "text",type : "string"},
//			{name : "iconCls",type : "string"},
//			{name : "leaf",type : "boolean"},
//			{name : 'type'},
//			{name : 'component'}]
//});

/**
 * 新增 修改 查看窗口
 */
Ext.define('DEMO.view.user.PwdEdit', {
    extend: 'Ext.window.Window',
    alias : 'widget.PwdEdit',

    requires: ['Ext.form.Panel','Ext.form.field.Text'],

    layout: 'fit',
    autoShow: true,
    width: 500,
    modal: true,
    iconCls: 'icon-user',
    keys: {
        key: Ext.EventObject.ESC,
        fn: function() {
                    this.close();
                },
        scope: this
    },
    initComponent: function() {
        this.items = [
            {
                xtype: 'form',
                padding: '5 5 0 5',
                border: false,
                style: 'background-color: #fff;',
                
                fieldDefaults: {
                    anchor: '100%',
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
                        xtype: 'displayfield',
                        name : 'name',
                        fieldLabel: '用户名称'
                       
                    },
                    {
                        xtype: 'textfield',
                        inputType: 'password',
                        name : 'plainPassword',
                        fieldLabel: '密码',
                        maxLength :10,
                        id:'plainPassword'
                    },
                    {
                        xtype: 'textfield',
                        inputType: 'password',
                        fieldLabel: '确认密码',
                        maxLength :10,
                        vtype:'password',
                        confirmTo:'plainPassword'
                        
                    }
                ]
            }
        ];
        
        this.dockedItems = [{
            xtype: 'toolbar',
            dock: 'bottom',
            id:'buttons',
            ui: 'footer',
            items: ['->', {
                iconCls: 'icon-save',
                itemId: 'save',
                text: '保存',
                action: 'save'
            },{
                iconCls: 'icon-reset',
                text: '重置',
                scope: this,//window
                handler: function(){
                	this.down('form').getForm().reset();
                }
            }]
        }];

        this.callParent(arguments);
    }
});
