Ext.define('DEMO.view.office.OfficeEdit', {
    extend: 'Ext.window.Window',
    alias : 'widget.OfficeEdit',

    requires: ['Ext.form.Panel','Ext.form.field.Text'],

    layout: 'fit',
    autoShow: true,
    width: 280,
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
