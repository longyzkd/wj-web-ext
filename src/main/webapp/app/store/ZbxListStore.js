Ext.define('DEMO.store.ZbxListStore', {
    extend	: 'Ext.data.Store',
	model	: 'DEMO.model.ZbxListModel',

	pageSize	: pageSize,
	autoLoad	: false
	
    

//	remoteSort	: true,
//	sorters		: {
//		property: 'officeCode',
//		direction: 'ASC'
//	}
});