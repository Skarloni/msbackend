# Получить список товаров

**URL** : `/storages/:storageName/products`

**URL Parameters** : `storageName=[unicode 64 chars max]`

**Method** : `GET`

## Success Responses

**Code** : `200 OK`

**Content** :

```json
[
    {
        "code": 1,
        "name": "Product name 1",
        "price": 1.0,
        "sellingPrice": 1.0,
        "count": 10
    },
    {
        "code": 2,
        "name": "Product name 2",
        "price": 1.0,
        "sellingPrice": 1.0,
        "count": 10
    },
    {
        "code": 3,
        "name": "Product name 3",
        "price": 1.0,
        "sellingPrice": 1.0,
        "count": 10
    }
]
```

## Error Responses

**Code** : `500 Internal Server Error`
