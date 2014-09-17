Ext.define('DEMO.view.layout.AppHeader' ,{
    extend: 'Ext.container.Container',
    alias : 'widget.AppHeader',

	id: 'app-header',

	region: 'north',
	height: 35,

	html	: '扬州市社会综合治税共享平台 - <span class="subtitle">文档采集</span>',

    initComponent: function() {
        this.callParent(arguments);
    }
});