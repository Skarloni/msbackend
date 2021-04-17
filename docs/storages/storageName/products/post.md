# Создать товар

**URL** : `/storages/:storageName/products`

**URL Parameters** : `storageName=[unicode 64 chars max]`

**Method** : `POST`

## Data Params

```json
{
    "code": 1,
    "name": "Product name",
    "price": 1.0,
    "sellingPrice": 1.0
}
```

## Success Responses

**Code** : `200 OK`

## Error Responses

**Code** : `500 Internal Server Error`
