Ext.define('DEMO.model.OfficeListModel', {
    extend	: 'Ext.data.Model',
    fields	: [
		'id','parentId','name','type','grade','address','zipCode','master','phone','fax','email','officeCode',
		'spellCode'
		//dataEntity内容略过
	],

	proxy	: {
		type			: 'ajax',
		url				: 'sys/office/list',

		reader			: {
			type			: 'json',
			root			: 'root',
			totalProperty	: 'totalCount',
			successProperty	: 'success'
		},
	
		simpleSortMode	: true
	}	
	
});