// enable and configure loader
Ext.Loader.setConfig({
     enabled:true,

     paths:{
//          Ext:'ext/src'
//          My:'app'
    	 'Ext.ux' : 'ext/ux',
    	 'Ext.app' : 'ext/app'
     }
});

Ext.application({
    name: 'DEMO', // Global namespace

    appFolder: 'app',

    controllers: [
		'LayoutController',
		'DocSharedListController',
		'OfficeListController',
		'UserListController'
    ],
    autoCreateViewport	: false,

    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'border',
			items: [
					{ xtype: 'ManagementArea' },
					{ xtype: 'TreeContainer' },
//					{ xtype: 'ListsContainer' },
					{ xtype: 'AppHeader' }
				],
				listeners : {
					afterrender : function(){
						Ext.getBody().mask('正在加载系统菜单....');
						ajax({
							url : 'menu/list',// 获取面板的地址
							params : {
								view : "list"
							},
							method:'get',
							callback : addTree
						});
					}
				}
        });
        
    }
});


function addTree(data) {
	Ext.getBody().unmask();
	var  tree = Ext.getCmp('treeContainer');
	var  tab = Ext.getCmp('managementArea');
	for (var i = 0; i < data.length; i++) {
		tree.add(Ext.create("Ext.tree.Panel", {
					title : data[i].text,
					iconCls : data[i].iconCls,
					//useArrows: true,
					autoScroll : true,
					rootVisible : false,
					viewConfig : {
						loadingText : "正在加载..."
					},
					store : createStore(data[i].id),
					listeners : {
						afterlayout : function() {
							if (this.getView().el) {
								var el = this.getView().el;
								var table = el
										.down("table.x-grid-table");
								if (table) {
									table.setWidth(el.getWidth());
								}
							}
						},
						itemclick : function(view,node){
							if (node.isLeaf()) { //判断是否是根节点
								if(node.raw.type === 'URL'){ //判断资源类型
									var panel = Ext.getCmp(node.raw.id);
									if(panel){
										tab.setActiveTab(panel);
									}else{
										 panel = Ext.create('Ext.panel.Panel',{
											title : node.raw.text,
											closable : true,
											id:node.raw.id,
											iconCls : 'icon-activity',
											html : '欢迎'
//												html : '<iframe width="100%" height="100%" frameborder="0" src="http://www.baidu.com"></iframe>'
										});
										tab.add(panel);
										tab.setActiveTab(panel);
										
									}
									
								}else if(node.raw.type === 'COMPONENT'){
									
									var panel = Ext.getCmp(node.raw.id);
									if(panel){
										tab.setActiveTab(panel);
									}else{
										var panel = Ext.widget(node.raw.component,{
											title : node.raw.text,
											id:node.raw.id,
											closable : true,
											iconCls : 'icon-activity'
										});
										tab.add(panel);
										tab.setActiveTab(panel);
										
									}
									
									
								}
							}
						}
					}
				}));
		tree.doLayout();

	}
	
	var model = Ext.define("TreeModel", { // 定义树节点数据模型
		extend : "Ext.data.Model",
		fields : [{name : "id",type : "string"},
				{name : "text",type : "string"},
				{name : "iconCls",type : "string"},
				{name : "leaf",type : "boolean"},
				{name : 'type'},
				{name : 'component'}]
	});
	function createStore (id) { // 创建树面板数据源
		var me = this;
		return Ext.create("Ext.data.TreeStore", {
					defaultRootId : id, // 默认的根节点id
					model : model,
					proxy : {
						type : "ajax", // 获取方式
						url : "menu/list?view=node", // 获取树节点的地址
						reader			: {
							type			: 'json',
							root			: 'root',
							successProperty	: 'success'
						},
					},
					clearOnLoad : true,
					nodeParam : "id"// 设置传递给后台的参数名,值是树节点的id属性
				});
	};
}