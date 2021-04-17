# Загрузить документ перемещения

**URL** : `/uploads/transfer`

**Method** : `POST`

## Data Params

```json
{
    "number": 1,
    "from": "From storage",
    "to": "To storage",
    "products": [
        {
            "code": 1,
            "name": "Product name 1",
            "count": 10
        },
        {
            "code": 2,
            "name": "Product name 2",
            "count": 10
        },
        {
            "code": 3,
            "name": "Product name 3",
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
