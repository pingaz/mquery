# Search API

## Database

### JSON

```json
{
    "id"     : "database id",
    "type"   : "mysql/oracle/hive/es/...",
    "params" : {
        ...
    }
}
```

## Finder

### JSON

```json
{
    "id"   : "finder id",
    "ql"   : "SELECT * FROM t_user",
    "opts" :{
        "db"     : "database id",
        "name"   : "finder name",
        "title"  : "finder title",
        "desc"   : "finder description",
    },
    "fields" :[
        {
            "name"      : "field name",
            "type"      : "string/number/time/price/view, default is string.",
            "view"    : {
                "type"      : "options, button/checkbox/radio/select/custom",
                "value"     : "button/checkbox/radio's value or select's values",
                "text"      : "button/checkbox/radio's value or select's texts",
                "options"   :{
                },
            },
            "condition" :{ //single condition
                "name"      : "single",
                "key"       : "field key in db",
                "oper"      : "like/eq/neq/ls/leq/gt/geq/in/between/...",
                "value"     : "default values which are one or array",
                "valid"     : [
                    {
                    	"name"     : "length",
                        "value"    : ""
                	},
                    ...,
                ],
                "select"    :{
                    
                }
            }
        },
        {
            "name"      : "field name",
            "condition" :{ //complex condition
                "name"      : "complex",
                "value"     :[
                    {
                        //TODO
                    },
                    ...
                ]
            }
        },
        ...
    ]
}
```

## Query

### JSON

```json
{
    "id"     : "finder id",
    "fields" :[
        {
            "name"  : "field name",
            "value" : "one or array"
        },
        ...
    ]
}
```

## Dictionary

### JSON

```json
{
    "resource A key" : "resource A value",
    "resource B key" : "resource B value",
    "resource C key" : "resource C value",
    "finder A ID" :{
    		"resource C key" : "resource C value",
     },
    "finder B ID" :{
    		"resource C key" : "resource C value",
    		"resource D key" : "resource D value",
     },
}
```

