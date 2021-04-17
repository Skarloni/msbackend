# Backend

Сборка проекта `mvn clean package`

Запуск проекта `java -jar target/MS_Backend-0.1-jar-with-dependencies.jar`

## Endpoints

### Склады

* [Получить список складов](docs/storages/get.md) : `GET /storages`

* [Создать склад](docs/storages/post.md) : `POST /storages`

* [Удалить склад](docs/storages/storageName/delete.md) : `DELETE /storages/:storageName`

* [Обновить склад](docs/storages/storageName/patch.md) : `PATCH /storages/:storageName`

### Товары

* [Получить список товаров](docs/storages/storageName/products/get.md) : `GET /storages/:storageName/products`

* [Создать товар](docs/storages/storageName/products/post.md) : `POST /storages/:storageName/products`

* [Удалить товар](docs/storages/storageName/products/productCode/delete.md) : `DELETE /storages/:storageName/products/:productCode`

* [Обновить товар](docs/storages/storageName/products/productCode/patch.md) : `PATCH /storages/:storageName/products/:productCode`

### Документы

* [Получить список документов поступления](docs/documents/receipt/get.md) : `GET /documents/receipt`

* [Получить список документов продажи](docs/documents/sale/get.md) : `GET /documents/sale`

* [Получить список документов перемещения](docs/documents/transfer/get.md) : `GET /documents/transfer`

* [Получить документ поступления](docs/documents/receipt/number/get.md) : `GET /documents/receipt/:number`

* [Получить документов продажи](docs/documents/sale/number/get.md) : `GET /documents/sale/:number`

* [Получить документ перемещения](docs/documents/transfer/number/get.md) : `GET /documents/transfer/:number`

### Отчеты

* [Получить отчет по общему списоку товаров](docs/reports/full/get.md) : `GET /reports/full`

* [Получить отчет по остатках товаров на складах](docs/reports/storage/get.md) : `GET /reports/storage`

### Загрузка

* [Загрузить документ поступления](docs/uploads/receipt/post.md) : `POST /uploads/receipt`

* [Загрузить документ продажи](docs/uploads/sale/post.md) : `POST /uploads/sale`

* [Загрузить документ перемещения](docs/uploads/transfer/post.md) : `POST /uploads/transfer`

## Database PostgreSQL

Database: msbackend

User: msbackend

Password: msbackend

```sql
CREATE TABLE Products (
	code INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(64) NOT NULL,
	price DECIMAL(8, 2) NOT NULL DEFAULT 0.0,
	selling_price DECIMAL(8, 2) NOT NULL DEFAULT 0.0
);

CREATE TABLE Storages (
	name VARCHAR(64) NOT NULL PRIMARY KEY
);

CREATE TABLE StorageProduct (
	storage_name VARCHAR(64) NOT NULL REFERENCES storages (name) ON DELETE CASCADE ON UPDATE CASCADE,
	product_code INTEGER NOT NULL REFERENCES products (code) ON DELETE CASCADE ON UPDATE CASCADE,
	product_count INTEGER NOT NULL DEFAULT 0 CHECK (product_count >= 0),
	UNIQUE (storage_name, product_code)
);

CREATE TABLE Uploads (
	"number" INTEGER NOT NULL UNIQUE,
	"uuid" UUID NOT NULL,
	"type" VARCHAR(64) NOT NULL
);
```
