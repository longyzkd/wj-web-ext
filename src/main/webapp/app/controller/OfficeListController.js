Ext.define('DEMO.controller.OfficeListController', {
    extend	: 'Ext.app.Controller',

    stores	: ['OfficeListStore'],
    models	: ['OfficeListModel'],

    views	: [
		'office.OfficeList'
    ],

    init	: function() {
        this.control({
			'OfficeList' : {
				show		: this.loadList
				
			}
        });
    },

	// Reload the list every time it is shown
    loadList	: function(list) {
    	list.getStore().load();
    },

});