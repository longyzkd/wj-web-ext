Ext.define('DEMO.store.DocSharedListStore', {
    extend	: 'Ext.data.Store',
	model	: 'DEMO.model.DocSharedListModel',

	pageSize	: 50,
	autoLoad	: true,

	remoteSort	: true,
	sorters		: {
		property: 'zywdmc',
		direction: 'ASC'
	}
});