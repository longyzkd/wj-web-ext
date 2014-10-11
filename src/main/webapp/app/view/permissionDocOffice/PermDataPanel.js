Ext.define('DEMO.view.permissionDocOffice.PermDataPanel' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.PermDataPanel',

//    layout: {
//        type: 'vbox',
//        align: 'center'
//    },
    layout:'border',
    items: [{
        id:'btns',
        region:'west',
        split:true,
        width:150,
        minWidth: 100,
        maxWidth: 250,
        layout:'fit',
        margin: '5 0 5 5',
        xtype:'treepanel',
        rootVisible:false,
		store: Ext.create('Ext.data.TreeStore',{
			fields: ['id','text'],
			root: {
				expanded: true
			},
			proxy: {
				type: 'ajax',
				url: "sys/user/getAllOfficeNodes",
				reader: {
					type: 'json'
				}
			}
		})
   },{
       region:'center',
       margin: '5 5 5 0',
       xtype:'DocList'
   }],
    
    
    initComponent: function() {
        

        this.callParent(arguments);
    }
});