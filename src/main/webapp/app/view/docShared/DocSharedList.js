Ext.define('DEMO.view.docShared.DocSharedList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.DocSharedList',

    store: 'DocSharedListStore',
    title: '文档共享中心',
    initComponent: function() {
        this.columns = [];

        this.callParent(arguments);
    }
});