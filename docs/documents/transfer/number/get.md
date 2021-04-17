# Получить документ перемещения

**URL** : `GET /documents/transfer/:number`

**URL Parameters** : `number=[integer]`

**Method** : `GET`

## Success Responses

**Code** : `200 OK`

**Content** :

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

## Error Responses

**Code** : `500 Internal Server Error`
