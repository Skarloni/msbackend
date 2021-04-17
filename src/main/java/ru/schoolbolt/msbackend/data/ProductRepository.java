package ru.schoolbolt.msbackend.data;

import ru.schoolbolt.msbackend.data.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final Database database;

    ProductRepository(final Database database) {
        this.database = database;
    }

    private static final String GET_PRODUCTS_SQL = "" +
            "SELECT * FROM Products%s";

    public List<Product> getProducts(final String name) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps;
            if (name != null && name.length() > 0) {
                ps = c.prepareStatement(String.format(
                        GET_PRODUCTS_SQL,
                        " WHERE name = ?"
                ));
                ps.setString(
                        1,
                        name
                );
            } else {
                ps = c.prepareStatement(String.format(
                        GET_PRODUCTS_SQL,
                        ""
                ));
            }

            final ResultSet rs = ps.executeQuery();

            c.commit();

            final List<Product> products = new ArrayList<>();
            while (rs.next()) {
                products.add(toProduct(rs));
            }
            return products;
        }
    }

    private static final String GET_PRODUCTS_IN_STORAGE_SQL = "" +
            "SELECT p.*, SUM(sp.product_count) AS product_count FROM Products p " +
            "INNER JOIN StorageProduct sp " +
            "ON p.code = sp.product_code%s " +
            "WHERE sp.product_count > 0 " +
            "GROUP BY p.code";

    public List<Product> getProductsInStorage(final String storageName) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps;
            if (storageName != null && storageName.length() > 0) {
                ps = c.prepareStatement(String.format(
                        GET_PRODUCTS_IN_STORAGE_SQL,
                        " AND sp.storage_name = ?"
                ));
                ps.setString(
                        1,
                        storageName
                );
            } else {
                ps = c.prepareStatement(String.format(
                        GET_PRODUCTS_IN_STORAGE_SQL,
                        ""
                ));
            }

            System.out.println(ps);

            final ResultSet rs = ps.executeQuery();

            c.commit();

            final List<Product> products = new ArrayList<>();
            while (rs.next()) {
                final Product product = toProduct(rs);
                product.count = rs.getInt("product_count");
                products.add(product);
            }
            return products;
        }
    }

    private static final String DELETE_PRODUCT_SQL = "" +
            "DELETE FROM Products WHERE code = ?";

    public void deleteProduct(final int code) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps = c.prepareStatement(DELETE_PRODUCT_SQL);
            ps.setInt(
                    1,
                    code
            );

            ps.executeUpdate();

            c.commit();
        }
    }

    private static final String POST_PRODUCT_SQL = "" +
            "INSERT INTO Products VALUES (?, ?, ?, ?) ON CONFLICT (code) DO UPDATE SET " +
            "name = COALESCE(EXCLUDED.name, Products.name), " +
            "price = COALESCE(NULLIF(EXCLUDED.price, 0), Products.price), " +
            "selling_price = COALESCE(NULLIF(EXCLUDED.selling_price, 0), Products.selling_price)";

    public void postProduct(final Product product) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps = c.prepareStatement(POST_PRODUCT_SQL);
            ps.setInt(
                    1,
                    product.code
            );
            ps.setString(
                    2,
                    product.name
            );
            ps.setFloat(
                    3,
                    product.price
            );
            ps.setFloat(
                    4,
                    product.sellingPrice
            );

            ps.executeUpdate();

            c.commit();
        }
    }

    private static final String POST_PRODUCT_IN_STORAGE_SQL = "" +
            "INSERT INTO StorageProduct VALUES (?, ?, ?) " +
            "ON CONFLICT (storage_name, product_code) " +
            "DO UPDATE SET product_count = StorageProduct.product_count + EXCLUDED.product_count";

    public void postProductInStorage(
            final String storageName,
            final int productCode,
            final int productCount
    ) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps = c.prepareStatement(POST_PRODUCT_IN_STORAGE_SQL);
            ps.setString(
                    1,
                    storageName
            );
            ps.setInt(
                    2,
                    productCode
            );
            ps.setInt(
                    3,
                    productCount
            );

            ps.executeUpdate();

            c.commit();
        }
    }

    private static final String PATCH_PRODUCT_SQL = "" +
            "UPDATE Products SET " +
            "name = COALESCE(?, name), " +
            "price = COALESCE(NULLIF(?, 0), price), " +
            "selling_price = COALESCE(NULLIF(?, 0), selling_price) " +
            "WHERE code = ?";

    public void patchProduct(final Product product) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps = c.prepareStatement(PATCH_PRODUCT_SQL);
            ps.setString(
                    1,
                    product.name
            );
            ps.setFloat(
                    2,
                    product.price
            );
            ps.setFloat(
                    3,
                    product.sellingPrice
            );
            ps.setInt(
                    4,
                    product.code
            );

            ps.executeUpdate();

            c.commit();
        }
    }

    private static final String PATCH_PRODUCT_COUNT_SQL = "" +
            "UPDATE StorageProduct SET product_count = product_count + ? " +
            "WHERE storage_name = ? AND product_code = ?";

    public void patchProductCount(
            final String storageName,
            final int productCode,
            final int productCount
    ) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps = c.prepareStatement(PATCH_PRODUCT_COUNT_SQL);
            ps.setInt(
                    1,
                    productCount
            );
            ps.setString(
                    2,
                    storageName
            );
            ps.setInt(
                    3,
                    productCode
            );

            ps.executeUpdate();

            c.commit();
        }
    }

    private Product toProduct(final ResultSet rs) throws SQLException {
        final Product product = new Product();
        product.code = rs.getInt("code");
        product.name = rs.getString("name");
        product.price = rs.getFloat("price");
        product.sellingPrice = rs.getFloat("selling_price");
        return product;
    }
}
