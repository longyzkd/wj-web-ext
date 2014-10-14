Ext.define('DEMO.model.UploadDocLookupModel', {
	extend : 'Ext.data.Model',
	fields : [ 'id', 'zbxMc', 'wdMc', 'createBy', 'createBy', 
	           {
				name : 'createDate'
	           },
	           'status','officeId' 
	         ]

	//autoLoad	: true,
	
});