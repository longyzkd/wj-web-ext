Ext.define('DEMO.view.office.OfficeEdit', {
    extend: 'Ext.window.Window',
    alias : 'widget.OfficeEdit',

    requires: ['Ext.form.Panel','Ext.form.field.Text'],

    layout: 'fit',
    autoShow: true,
    width: 280,
    
    iconCls: 'icon-user',

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
                        fieldLabel: '部门名称'
                    },
                    {
                        xtype: 'textfield',
                        name : 'officeCode',
                        fieldLabel: '部门编码'
                    },
                    {
                        xtype: 'textfield',
                        name : 'spellCode',
                        fieldLabel: '拼音码'
                    },
                    {
                        xtype: 'textfield',
                        name : 'address',
                        fieldLabel: '地址',
                        allowBlank: true
                    },
                    {
                        xtype: 'textfield',
                        name : 'phone',
                        fieldLabel: '电话',
                        allowBlank: true
                    },
                    {
                        xtype: 'textfield',
                        name : 'email',
                        fieldLabel: 'email',
                        allowBlank: true
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
                text: 'Save',
                action: 'save'
            },{
                iconCls: 'icon-reset',
                text: 'Cancel',
                scope: this,
                handler: this.close
            }]
        }];

        this.callParent(arguments);
    }
});
