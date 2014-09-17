Ext.define('DEMO.model.DocSharedListModel', {
    extend	: 'Ext.data.Model',
    fields	: [
		'zbxmc', 'zywdmc'
	],

	proxy	: {
		type			: 'ajax',
		url				: 'docShared/list',

		reader			: {
			type			: 'json',
			root			: 'root',
//			totalProperty	: 'totalCount',
			successProperty	: 'success'
		},
	
		simpleSortMode	: true
	}	
	
});