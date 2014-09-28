Ext.apply(Ext.form.VTypes, { 
	
	 mobile:function (value, field) {
         return /^(((13[0-9]{1})|159|153)+\d{8})$/.test(value);
     },
     mobileText:'手机格式不正确',
    password : function(val, field) {// val指这里的文本框值，field指这个文本框组件，大家要明白这个意思  
        if (field.confirmTo) {// confirmTo是我们自定义的配置参数，一般用来保存另外的组件的id值  
            var pwd = Ext.getCmp(field.confirmTo);// 取得confirmTo的那个id的值  
            return (val == pwd.getValue());  
        }  
        return true;  
    } ,
    passwordText:'密码不一致',
    checkunique : function(val, field) {
    	console.log(field);
        if (!val) {
            return true;
        }
        var exist = false;
        Ext.Ajax.request({
			url : 'validate/checkunique',// 获取面板的地址
			params : {
				beanClazz:field.beanClazz,
				property:field.property,
				val : val,
				rawValue:field.myrawValue,
				action:field.action
			},
			async : false,//同步执行,为了争取返回exist，必须同步
			method:'post',
            success: function(response){
            	var requestSuccess = Ext.JSON.decode(response.responseText).success;
            	if(requestSuccess){
            		exist = Ext.JSON.decode(response.responseText).root;
            	}else{
            		 Ext.MessageBox.show({
                         title: '系统错误',
                         msg: Ext.decode(response.responseText).message,
                         icon: Ext.MessageBox.ERROR,
                         buttons: Ext.Msg.OK
                     });
            	}
            	
            },
            failure: function(response){}
        });
        return !exist;
    },
    checkuniqueText:'已经存在'  
});  


/**
 * 把simple json数组转成复杂数组
 * @param array
 * @returns
 */
function convert(array){
    var map = {};
    for(var i = 0; i < array.length; i++){
        var obj = array[i];
        obj.children= [];

        map[obj.id] = obj;

        var parent = obj.parentId || '-';
        if(!map[parent]){
            map[parent] = {
            		children: []
            };
        }
        map[parent].children.push(obj);
    }

    return map['-'].children;

}




/**
 * Enable any previously disabled button when any button is clicked.
 *
 * @param Object node
 * @param String selector
 *
 * @returns void
 */
function enableSiblings(node, selector){

	var sibling = node.previousSibling(selector);
	while( sibling != null) {
		sibling.setDisabled(false);
		var sibling = sibling.previousSibling(selector);
	}


	var sibling = node.nextSibling(selector);
	while( sibling != null) {
		sibling.setDisabled(false);
		var sibling = sibling.nextSibling(selector);
	}
}


/**
 * Enable all the buttons in a toolbar.
 * 
 * @param Object toolbar - The alias of an existing toolbar
 *
 * @returns void
 */
function enableToolbarButtons(toolbar)
{
    // Need to get all siblings of the add operator, it is only button that
    // is enabled by default on page load.
    var btns = Ext.ComponentQuery.query(toolbar + ' > button');
    
    Ext.Array.each(btns, function(button) {
    	button.enable();
    });
    
}

/**
 * Enable or disable all elements within the received array.
 * 
 * Elements of string can be Ext components or HTML ids
 *
 * @param Array items (array of strings or objects)
 * @param String action (enable or disable)
 * 
 * @returns void
 */
function toggleEnabled(items, action)
{
    items.forEach( function(item) {
    	
        if( ! Ext.isObject(item) )
        {
            // Query by alias
            var el = Ext.ComponentQuery.query(item);
            
            if( ! Ext.isEmpty(el) )
            {
            	toggleEnabled(el, action);
            } else
                {
                    // Query by id
                    var el = Ext.ComponentQuery.query('#' + item);

                    if( ! Ext.isEmpty(el) )
                    {
		            	toggleEnabled(el, action);
                    } else
                        {
                            console.log('Failed to query ' + item + ' in redisableItems');
                        }
                }
        } else
            {
                if( action == 'enable' )
                {
                	item.enable();	
                } else
                	{
                		item.disable();
                	}
            }
    });
}



   






//wjs.prototype.showTab = function (id,name) {
//	 
////	HO.log("HO.Class.CRUD.showTab " + id + " " + name);
// 
//	var tabID = this.ComponentName + id;
//	var tab = this.TabPanel.items.find(function(i){
//		return i.id === tabID;
//	})
//	if (!tab) { 
//		 showObject = 	this.getShowObject(id,tabID, name);
// 
//		this.TabPanel.add( showObject ).show();
//		// x = {title: "dupa", layout: 'fit', items: [showObject]
//		// ,autoScroll		: 	true
//		// ,bodyStyle		: 	'position:relative'
//		// 
//		// }
//		// 	
//		// this.TabPanel.add( x ).show();
// 
// 
//	} else {
////		HO.log('tab exists');
//		this.TabPanel.setActiveTab(tabID);
//	}
//}
