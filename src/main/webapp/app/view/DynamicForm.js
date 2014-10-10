/**
* 不能做到动态啊，值在初始化的时候已经赋了
* Source From SO 
* http://stackoverflow.com/questions/13095695/extjs-4-0-mvc-add-components-to-a-form-based-on-store
*
* Create Form Dynamics
**/


var myitems = Ext.create('Ext.data.Store', {
//    fields: ['name', 'prompt', 'type'],
//    data : 
//    [
//        {"name":"name", "prompt":"Your name" , "type":"textfield"},
//        {"name":"addr", "prompt":"Your street address", "type":"textfield"},
//        {"name":"city", "prompt":"Your city" , "type":"textfield"}
//    ]
	fields: ['code', 'name'],
	storeId:'myitems',
	proxy: {
		type: 'ajax',
		url: 'x.json',
		reader: {
			type: 'json'
//             root: 'users'
		}
	}
});


function genwidget(name, prompt, type)
{
    console.log("In genwidget with name=" + name + ", prompt=" + prompt + ", type=" + type + ".");

    var widget = null;
    if (type == "textfield")
    {
        widget = Ext.create('Ext.form.TextField', 
            {
                xtype     : 'textfield',
                name      : name,
                fieldLabel: prompt
//                labelWidth: 150,
//                width     : 400,  // includes labelWidth
//                allowBlank: false
            });
    }
    else
    {
        // will code others later
        console.log("Unrecongized type [" + type + '] in function mywidget');
    }
    return widget;
};


function genItems(myitems,form)
{
    console.log("Begin genItems.");

    console.log(form);
    console.log(form.items);
    // var items = new Ext.util.MixedCollection();
//    var items = [];
    var items = form.items;

    var howMany = myitems.count();
    console.log("There are " + howMany + " items.");

    for (var i = 0; i < myitems.count(); i++)
    {
        var name   = myitems.getAt(i).get('name');
        var prompt = myitems.getAt(i).get('prompt');
        var type   = myitems.getAt(i).get('type');
        var widget = genwidget(name, prompt, type) ; 
        items.push(widget);
        console.log("items.toString(): " + items.toString());
    }

    return items;
};


Ext.define('DEMO.view.DynamicForm' ,{
    extend: 'Ext.form.Panel',
    alias : 'widget.dynamicform',

    // no store

    id: 'dynamicform',


    dockedItems: [ ],

//    items: genItems(myitems,this),

    // Reset and Submit buttons


    initComponent: function() 
    {
        console.log("--> in dynamicform init");

        var me = this,
            items = genItems(myitems,me);
        console.log(me);
        Ext.apply(me, {
            items:items
        });

        this.callParent(arguments);

        console.log("--> leaving dynamicform init");
    }


});  // Ext.define