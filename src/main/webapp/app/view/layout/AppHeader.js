Ext.define('DEMO.view.layout.AppHeader' ,{
    extend: 'Ext.panel.Panel',
//    extend: 'Ext.container.Container',
    alias : 'widget.AppHeader',

	id: 'app-header',

	region: 'north',
//	height: 35,

//	html: '扬州市社会综合治税共享平台 - <span class="subtitle">文档采集</span>',
	title: '扬州市社会综合治税共享平台 - <span class="subtitle">文档采集</span>',
	
	 autoHeight: true, 
     border: false,
     bbar:[
           '->',
           curUser+',欢迎您!',
           '当前时间:'+Ext.Date.format(new Date(),'Y-m-d'),
           {
               text:'修改密码'            
           },
           {
               text:'退出',
               handler: function() {
//            	   Ext.Ajax.request({
//            		    url: ctx+'/logout',
//            		    success: function(response){
//            		        var text = response.responseText;
//            		        console.log(text);
//            		    }
//            		});
            	   window.location.href=ctx+'/logout'
               }
           },
           {
               xtype:'displayfield',
               width:50
           }
           
       ],
    initComponent: function() {
        this.callParent(arguments);
    }
});