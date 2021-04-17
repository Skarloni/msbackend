package ru.schoolbolt.msbackend.data;

import ru.schoolbolt.msbackend.data.model.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StorageRepository {
    private final Database database;

    StorageRepository(final Database database) {
        this.database = database;
    }

    private static final String GET_STORAGES_SQL = "" +
            "SELECT * FROM Storages";

    public List<Storage> getStorages() throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps = c.prepareStatement(GET_STORAGES_SQL);

            final ResultSet rs = ps.executeQuery();

            c.commit();

            final List<Storage> storages = new ArrayList<>();
            while (rs.next()) {
                storages.add(toStorage(rs));
            }
            return storages;
        }
    }

    private static final String DELETE_STORAGE_SQL = "" +
            "DELETE FROM Storages WHERE name = ?";

    public void deleteStorage(final String name) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps = c.prepareStatement(DELETE_STORAGE_SQL);
            ps.setString(
                    1,
                    name
            );

            ps.executeUpdate();

            c.commit();
        }
    }

    private static final String POST_STORAGE_SQL = "" +
            "INSERT INTO Storages VALUES (?) ON CONFLICT DO NOTHING";

    public void postStorage(final Storage storage) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps = c.prepareStatement(POST_STORAGE_SQL);
            ps.setString(
                    1,
                    storage.name
            );

            ps.executeUpdate();

            c.commit();
        }
    }

    private static final String PATCH_STORAGE_SQL = "" +
            "UPDATE Storages SET name = ? WHERE name = ?";

    public void patchStorage(
            final String name,
            final Storage storage
    ) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps = c.prepareStatement(PATCH_STORAGE_SQL);
            ps.setString(
                    1,
                    storage.name
            );
            ps.setString(
                    2,
                    name
            );

            ps.executeUpdate();

            c.commit();
        }
    }

    private Storage toStorage(final ResultSet rs) throws SQLException {
        final Storage storage = new Storage();
        storage.name = rs.getString("name");
        return storage;
    }
}
