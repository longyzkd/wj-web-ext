Ext.define('DEMO.controller.UploadController', {
    extend	: 'Ext.app.Controller',

    stores	: ['UploadDocListStore'],
    models	: ['UploadDocLookupModel'],

    views	: [
		'upload.UploadDocList','upload.UploadPanel'
    ],
    refs: [{
	        ref: 'UploadDocList',
	        selector: 'UploadDocList'
	    }
    ],
    init	: function() {
        this.control({
			'UploadDocList' : {
				show		: this.loadList
				
			},
			'UploadDocList button[action=query]': {
				click: this.query
            }
            
        });
    },

	// Reload the list every time it is shown
    loadList	: function(list) {
    	//不执行alert(1);
    	list.getStore().load();
    },
    query:function( e, eOpts){
   	 var officeId = e.up('toolbar').down('[name=officeId]').getValue();
   	 var zbxMc = e.up('toolbar').down('[name=zbxMc]').getValue();
   	 var status = e.up('toolbar').down('[name=status]').getValue();
   	 var from = e.up('toolbar').down('[name=from_date]').getValue();
   	 console.log();
   	 var to = e.up('toolbar').down('[name=to_date]').getValue();
   	 this.getUploadDocListStoreStore().load({
   		 params:{
	   			officeId:officeId,
			 	zbxMc: zbxMc,
	   			status:status,
	   			from:Ext.Date.format(from,'Y-m-d'),
	   			to:Ext.Date.format(to,'Y-m-d')
   			  } 
   	 });
   }

});

