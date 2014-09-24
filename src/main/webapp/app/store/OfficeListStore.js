Ext.define('DEMO.store.OfficeListStore', {
    extend	: 'Ext.data.Store',
	model	: 'DEMO.model.OfficeListModel',

	pageSize	: pageSize,
	autoLoad	: true,
	
    listeners: {
        exception: function(proxy, response, operation){
            Ext.MessageBox.show({
                title: 'REMOTE EXCEPTION',
                msg: operation.getError(),
                icon: Ext.MessageBox.ERROR,
                buttons: Ext.Msg.OK
            });
        }
    }

//	remoteSort	: true,
//	sorters		: {
//		property: 'officeCode',
//		direction: 'ASC'
//	}
});