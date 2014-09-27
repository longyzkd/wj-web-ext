Ext.define('DEMO.store.UserListStore', {
    extend	: 'Ext.data.Store',
	model	: 'DEMO.model.UserListModel',

	pageSize	: pageSize,
	autoLoad	: false
	
    

//	remoteSort	: true,
//	sorters		: {
//		property: 'officeCode',
//		direction: 'ASC'
//	}
});