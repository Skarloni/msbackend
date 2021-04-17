# Загрузить документ поступления

**URL** : `/uploads/receipt`

**Method** : `POST`

## Data Params

```json
{
    "number": 1,
    "storage": "Storage name",
    "products": [
        {
            "code": 1,
            "name": "Product name 1",
            "price": 1.0,
            "count": 10
        },
        {
            "code": 2,
            "name": "Product name 2",
            "price": 1.0,
            "count": 10
        },
        {
            "code": 3,
            "name": "Product name 3",
            "price": 1.0,
            "count": 10
        }
    ]
}
```

## Success Responses

**Code** : `200 OK`

## Error Responses

**Code** : `500 Internal Server Error`

### OR

**Code** : `400 BAD REQUEST`

**Content** : Number is undefined

### OR

**Code** : `400 BAD REQUEST`

**Content** : Storage is undefined

### OR

**Code** : `400 BAD REQUEST`

**Content** : Product is undefined
