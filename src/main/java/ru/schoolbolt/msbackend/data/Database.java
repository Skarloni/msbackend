package ru.schoolbolt.msbackend.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private final HikariDataSource dataSource;

    public Database() {
        final Properties properties = new Properties();
        properties.setProperty(
                "dataSourceClassName",
                "org.postgresql.ds.PGSimpleDataSource"
        );
        properties.setProperty(
                "dataSource.user",
                "msbackend"
        );
        properties.setProperty(
                "dataSource.password",
                "msbackend"
        );
        properties.setProperty(
                "dataSource.databaseName",
                "msbackend"
        );
        properties.setProperty(
                "dataSource.portNumber",
                "5432"
        );
        properties.setProperty(
                "dataSource.serverName",
                "localhost"
        );

        final HikariConfig config = new HikariConfig(properties);
        config.setAutoCommit(false);

        dataSource = new HikariDataSource(config);
    }

    Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private ProductRepository productRepository;
    private StorageRepository storageRepository;
    private UploadRepository uploadRepository;

    public ProductRepository getProductRepository() {
        if (productRepository == null) {
            productRepository = new ProductRepository(this);
        }
        return productRepository;
    }

    public StorageRepository getStorageRepository() {
        if (storageRepository == null) {
            storageRepository = new StorageRepository(this);
        }
        return storageRepository;
    }

    public UploadRepository getUploadRepository() {
        if (uploadRepository == null) {
            uploadRepository = new UploadRepository(this);
        }
        return uploadRepository;
    }
}
