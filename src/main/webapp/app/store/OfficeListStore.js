Ext.define('DEMO.store.OfficeListStore', {
    extend	: 'Ext.data.Store',
	model	: 'DEMO.model.OfficeListModel',

	pageSize	: pageSize,
	autoLoad	: true,

	remoteSort	: true,
	sorters		: {
		property: 'officeCode',
		direction: 'ASC'
	}
});