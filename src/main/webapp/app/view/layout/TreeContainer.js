//Ext.define('DEMO.view.layout.TreeContainer' ,{
//	extend: 'Ext.panel.Panel',
//	alias : 'widget.TreeContainer',
//	
//	title : '菜单',
//	region: 'west',
//	width: 300,
//	
//	split: true,
//	collapsible: true,
//	
//	layout: 'accordion',
//	items: [{
//		
//		title: '我的上传文档',
//		iconCls: 'nav' // see the HEAD section for style used
//	}, {
//		title: '模板下载中心',
//		html: '<p>Some settings in here.</p>',
//		iconCls: 'nav'
//	}, {
//		title: '文档共享中心',
//		html: '<p>Some info in here.</p>',
//		iconCls: 'nav'
//	}, {
//		title: '系统管理',
//		html: '<p>Some info in here.</p>',
//		iconCls: 'nav'
//	}],
//	
//	initComponent: function() {
//		this.callParent(arguments);
//	}
//});
Ext.define('DEMO.view.layout.TreeContainer' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.TreeContainer',
    id:'treeContainer',
    title : '菜单',
	region: 'west',
    width: 300,

    split: true,
    collapsible: true,

    layout: 'accordion',

    initComponent: function() {
        this.callParent(arguments);
    }
});