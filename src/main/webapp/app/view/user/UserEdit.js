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
Ext.define('DEMO.view.user.UserEdit', {
    extend: 'Ext.window.Window',
    alias : 'widget.UserEdit',

    requires: ['Ext.form.Panel','Ext.form.field.Text','Ext.ux.TreePicker'],

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
                        xtype: 'textfield',
                        name : 'name',
                        fieldLabel: '用户名称',
                        maxLength :100
                       
                    },
                    
                    {
            			xtype: 'treepicker',
            			fieldLabel: '所属部门',
            			displayField: 'text',
            			valueField:'id',
            			name:'officeId',
            			minPickerHeight: 200,
            			store:Ext.create("Ext.data.TreeStore", {
        					defaultRootId : null, // 默认的根节点id
        					fields : ['id',{name:'text',mapping:'name'},{name : "leaf",type : "boolean"}],
        					root: {
        						text: '政府',
        						expanded: true
        					},
        					proxy : {
        						type : "ajax", // 获取方式
        						url : "sys/user/getOfficeNodesOf", // 获取树节点的地址
        						reader			: {
        							type			: 'json',
        							root			: 'root',
        							successProperty	: 'success'
        						},
        					},
        					clearOnLoad : true,
        					nodeParam : "id"// 设置传递给后台的参数名,值是树节点的id属性
        				})
            		},
            		
                    {
                        xtype: 'textfield',
                        name : 'loginName',
                        id:'loginName',
                        fieldLabel: '登陆名',
                        maxLength :50,
                        vtype:'checkunique',
                        beanClazz:'me.entity.User',
                        property:'loginName'
                      
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
                        
                    },
                    {
                        xtype: 'textfield',
                        name : 'phone',
                        fieldLabel: '电话',
                        allowBlank: true,
                        maxLength :200,
                        vtype: 'mobile'
                    },
                    {
                        xtype: 'textfield',
                        name : 'email',
                        fieldLabel: 'email',
                        allowBlank: true,
                        maxLength :200,
                        vtype: 'email'
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
