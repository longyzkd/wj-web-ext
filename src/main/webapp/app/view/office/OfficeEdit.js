/**
 * 新增 修改 查看窗口
 */
Ext.define('DEMO.view.office.OfficeEdit', {
    extend: 'Ext.window.Window',
    alias : 'widget.OfficeEdit',

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
                        fieldLabel: '部门名称',
                        maxLength :100,
                        id:'name',
                        vtype:'checkunique',
                        beanClazz:'me.entity.Office',
                        property:'name',
                    },
                    {
                        xtype: 'textfield',
                        name : 'officeCode',
                        fieldLabel: '部门编码',
                        maxLength :50
                    },
                    {
            			xtype: 'treepicker',
            			fieldLabel: '上一级部门',
            			displayField: 'text',
            			valueField:'id',
            			name:'parentId',
            			minPickerHeight: 200,
            			store:Ext.create("Ext.data.TreeStore", {
            				rootVisible:false,
            				root:{id:0,text:'政府'},
//        					defaultRootId : 0, // 默认的根节点id
        					fields : ['id',{name:'text',mapping:'name'},{name : "leaf",type : "boolean"}],
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
                    	name : 'spellCode',
                    	fieldLabel: '拼音码',
                    	maxLength :50,
                    	vtype :'alpha' 
                    },
                    {
                        xtype: 'textfield',
                        name : 'address',
                        fieldLabel: '地址',
                        allowBlank: true,
                        maxLength :255
                    },
                    {
                        xtype: 'textfield',
                        name : 'phone',
                        fieldLabel: '电话',
                        allowBlank: true,
                        maxLength :200,
                        vtype: 'alphanum'
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
