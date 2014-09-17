Ext.define('DEMO.controller.LayoutController', {
    extend	: 'Ext.app.Controller',
    views	: [
		'layout.AppHeader',
		'layout.TreeContainer',
		'layout.ManagementArea'
    ],

    init	: function() {

        this.control({});
    },



});