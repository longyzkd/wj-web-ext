Ext.define('DEMO.store.OfficeListStore', {
    extend	: 'Ext.data.Store',
	model	: 'DEMO.model.OfficeListModel',

	pageSize	: pageSize,
	autoLoad	: false
	
    

//	remoteSort	: true,
//	sorters		: {
//		property: 'officeCode',
//		direction: 'ASC'
//	}
});