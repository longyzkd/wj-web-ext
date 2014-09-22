//Ext.define('DEMO.view.layout.ManagementArea' ,{
//    extend	: 'Ext.panel.Panel',
//    alias	: 'widget.ManagementArea',
//
//	region	: 'center',
//
//	layout:'fit',
//    items: [{
//        region: 'center',
//        margin: '35 5 5 0',
//        layout: 'column',
//        autoScroll: true,
//        defaultType: 'container',
//        
//        items: [{
//            columnWidth: 1/2,
//            padding: 5,
//            items:[{
//            	height:300,
//                title: '上传文档',
//                html: '<p>Lorem ipsum</p>'
//            },{
//            	height:300,
//                margin: '5 0 0 0',
//                title: '模板下载中心',
//                html: '<p>Lorem ipsum</p>'
//            }]
//        },{
//            columnWidth: 1/2,
//            padding: 5,
//            items:[{
//            	height:300,
//                title: '最新交换情况',
//                html: '<p>Lorem ipsum</p>'
//            },{
//            	 margin: '5 0 0 0',
//            	height:300,
//                xtype:'DocSharedList'           
//                
//            }]
//        }]
//    }],
//
//    initComponent: function() {
//        this.callParent(arguments);
//    }
//});
//Ext.define('DEMO.view.layout.ManagementArea' ,{
//    extend	: 'Ext.tab.Panel',
//    alias	: 'widget.ManagementArea',
//
//	region	: 'center',
//
//	layout:'fit',
//    items: [{
//        region: 'center',
//        margin: '35 5 5 0',
//        layout: 'column',
//        autoScroll: true,
//        defaultType: 'container',
//        
//        items: [{
//            columnWidth: 1/2,
//            padding: 5,
//            items:[{
//            	height:300,
//                title: '上传文档',
//                html: '<p>Lorem ipsum</p>'
//            },{
//            	height:300,
//                margin: '5 0 0 0',
//                title: '模板下载中心',
//                html: '<p>Lorem ipsum</p>'
//            }]
//        },{
//            columnWidth: 1/2,
//            padding: 5,
//            items:[{
//            	height:300,
//                title: '最新交换情况',
//                html: '<p>Lorem ipsum</p>'
//            },{
//            	 margin: '5 0 0 0',
//            	height:300,
//                xtype:'DocSharedList'           
//                
//            }]
//        }]
//    }],
//
//    initComponent: function() {
//        this.callParent(arguments);
//    }
//});

Ext.require(['Ext.app.Portlet', 'Ext.app.PortalColumn', 'Ext.app.PortalPanel',
		'Ext.app.PortalDropZone', 'Ext.ux.TabReorderer',
		'Ext.ux.TabCloseMenu']);


Ext.define('DEMO.view.layout.ManagementArea', {
	extend : 'Ext.tab.Panel',
	alias : 'widget.ManagementArea',

	id:'managementArea',
	region : 'center',

	layout : 'fit',

	activeTab : 0,
	enableTabScroll : true,
	animScroll : true,
	border : true,
	autoScroll : true,
	split : true,
	items : [ {
		iconCls : 'icon-activity',
		title : '平台首页',
		xtype : 'portalpanel',
		layout : 'column',
		items : [ {
			xtype : 'portalcolumn',
			columnWidth : 0.7,
			items : [ {
				title : '上传文档',
				height:300,
				iconCls : 'icon-upload'
			}, {
				title : '模板下载中心',
				height:300,
				iconCls : 'icon-file'
			}]
		}, {
			xtype : 'portalcolumn',
			columnWidth : 0.3,
			items : [ {
				title : '最新交换情况',
				height:300,
				iconCls : 'icon-link'
			},{
				 title: '文档共享中心',
				height:300,
				iconCls : 'icon-share', 
				items:[{
	            	 margin: '5 0 0 0',
	             	 height:300,
	                 xtype:'DocSharedList'           
	                 
	             }]
			}]
		} ]
	} ],
	plugins : [Ext.create('Ext.ux.TabReorderer'),
     		  Ext.create('Ext.ux.TabCloseMenu',{
      		  	closeTabText: '关闭面板',
      		  	closeOthersTabsText: '关闭其他',
      		  	closeAllTabsText: '关闭所有'
      		  })],

	initComponent : function() {
		this.callParent(arguments);
	}
});


