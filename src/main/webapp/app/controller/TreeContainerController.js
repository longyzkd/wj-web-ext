Ext.define('DEMO.controller.DocSharedListController', {
    extend	: 'Ext.app.Controller',

    stores	: ['DocSharedListStore'],
    models	: ['DocSharedListModel'],

    views	: [
		'docShared.DocSharedList'
    ],

    init	: function() {
        this.control({
			'DocSharedList' : {
				show		: this.loadList
				
			}
        });
    },

	// Reload the list every time it is shown
    loadList	: function(list) {
    	list.getStore().load();
    },

});