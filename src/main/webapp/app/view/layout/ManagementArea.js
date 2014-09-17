Ext.define('DEMO.view.layout.ManagementArea' ,{
    extend	: 'Ext.panel.Panel',
    alias	: 'widget.ManagementArea',

	region	: 'center',

    items: [{
        region: 'center',
        margin: '35 5 5 0',
        layout: 'column',
        autoScroll: true,
        defaultType: 'container',
        items: [{
            columnWidth: 1/2,
            padding: 5,
            items:[{
                title: '上传文档',
                html: '<p>Lorem ipsum</p>'
            },{
                margin: '5 0 0 0',
                title: '模板下载中心',
                html: '<p>Lorem ipsum</p>'
            }]
        },{
            columnWidth: 1/2,
            padding: 5,
            items:[{
                title: '最新交换情况',
                html: '<p>Lorem ipsum</p>'
            },{
                xtype:'DocSharedList'           
                
            }]
        }]
    }],

    initComponent: function() {
        this.callParent(arguments);
    }
});