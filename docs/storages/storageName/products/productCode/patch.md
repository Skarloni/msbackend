# Обновить товар

**URL** : `/storages/:storageName/products/:productCode`

**URL Parameters** : `storageName=[unicode 64 chars max]`, `productCode=[integer]`

**Method** : `PATCH`

## Data Params

```json
{
    "name": "Product name",
    "price": 1.0,
    "sellingPrice": 1.0
}
```

## Success Responses

**Code** : `200 OK`

## Error Responses

**Code** : `500 Internal Server Error`
