Ext.define('DEMO.controller.OfficeListController', {
    extend	: 'Ext.app.Controller',

    stores	: ['OfficeListStore'],
    models	: ['OfficeListModel'],

    views	: [
		'office.OfficeList','office.OfficeEdit'
    ],
    refs: [{
	        ref: 'OfficeList',
	        selector: 'OfficeList'
	    }
    ],
    init	: function() {
        this.control({
			'OfficeList' : {
				show		: this.loadList
				
			},
			'OfficeList button[action=query]': {
				click: this.query
            },
            'OfficeList button[action=add]': {
            	click: this.add
            },
            'OfficeList button[action=del]': {
            	click: this.del
            },
            'OfficeList actioncolumn ': {
            	click: this.onAction
            },
            'OfficeEdit button[action=save]':{
            	click:this.save
            }
            
        });
    },

	// Reload the list every time it is shown
    loadList	: function(list) {
    	list.getStore().load();
    },
    query:function( e, eOpts){
    	var name = e.up('toolbar').down('[name=name]').getValue();
    	var officeCode =  e.up('toolbar').down('[name=officeCode]').getValue();
    	 this.getOfficeListStoreStore().load({
    		 params:{
    			 	name: name,
    			 	officeCode: officeCode
    			  } 
    	 });
    },
    del:function(){
    	var grid = this.getOfficeList(),
    	record = grid.getSelectionModel().getSelection(), 
        store = this.getOfficeListStoreStore();

    	if(record && record.length){
    		 store.remove(record);
    		 store.sync({
                 success: function(batch) {
                	 console.log(batch);
                	 Ext.MessageBox.show({
                         title: '提示',
                         msg: Ext.decode(batch.operations[0].response.responseText).message,
                         icon: Ext.MessageBox.INFO,
                         buttons: Ext.Msg.OK,
                         fn: function(buttonId) {
                             if (buttonId === "ok") {
                            	 store.reload();
                             }
                         }
                     });
            		 
                 },
	             failure: function(batch){
                        Ext.MessageBox.show({
	                         title: "错误",
	                         msg:Ext.decode(batch.operations[0].response.responseText).message,
	                         icon: Ext.MessageBox.ERROR,
	                         buttons: Ext.MessageBox.OK
                        });  
	
	             }
    		 });
    		 
    		
    	}else{
    		 Ext.MessageBox.show({
                 title: '提示',
                 msg: '请至少选择一条记录',
                 icon: Ext.MessageBox.INFO,
                 buttons: Ext.Msg.OK
             });
    	}
	   
    },
    //窗口
    add: function(button) {
        var editWin = Ext.widget('OfficeEdit').show();
//        editWin.params= {ss:1};
//        editWin.on('show',function(){
//        	editWin.title='ss';
//        });
//        editWin.show();
        
    },
    //真正的保存
    save: function(button) {
        var win    = button.up('window'),
            form   = win.down('form'),
            record = form.getRecord(),
            values = form.getValues();
        
		if (values.id > 0){//edit
			record.set(values);
		} else{
			record = Ext.create('DEMO.model.OfficeListModel');
			record.set(values);
//			record.setId(0);
			
		    if(form.isValid( )){
		    	var OfficeListStore = this.getOfficeListStoreStore();
		    	OfficeListStore.add(record);
		    	OfficeListStore.sync({
	                success: function(batch)   {
		               	 Ext.MessageBox.show({
		                        title: '提示',
		                        msg: Ext.decode(batch.operations[0].response.responseText).message,
		                        icon: Ext.MessageBox.INFO,
		                        buttons: Ext.Msg.OK,
		                        fn: function(buttonId) {
		                            if (buttonId === "ok") {
		                            	win.close();
		                            	OfficeListStore.reload();
		                            }
		                        }
		                    });
		               	
	                }
	   		 	});
	        	
	        }
			
		}
    
    },
    onAction: function(view,cell,row,col,e){
        //console.log(this.getActioncolumn(),arguments, e.getTarget())
        var m = e.getTarget().className.match(/\bicon-(\w+)\b/)
        if(m){
            //选择该列
            this.getOfficeList().getView().getSelectionModel().select(row,false)
            switch(m[1]){
                case 'edit':
//                    this.getGrid().getPlugin('rowediting').startEdit({colIdx:col,rowIdx:row})
                	edit(this.getOfficeList(),this.getOfficeListStoreStore().getAt(row));
                    break;
                case 'delete':
//                    var record = this.getGrid().store.getAt(row)
//                    this.deleteRecord([record])
                    break;
            }
        }
    }

});

 function edit (record) {
	var editWin = Ext.widget('OfficeEdit').show();
//  editWin.params= {ss:1};
//  editWin.on('show',function(){
//  	editWin.title='ss';
//  });
//  editWin.show();
	
	if(record){
		editWin.down('form').loadRecord(record);
	}
}