Ext.define('DEMO.model.DocSharedListModel', {
    extend	: 'Ext.data.Model',
    fields	: [
		'zbxmc',
		{
			name		: 'uploadTime',
			type		:'date',
			dateFormat	: 'Y-m-d h:m:s'
		}
		, 'docName','docOfferer','status','downloadCounts'
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