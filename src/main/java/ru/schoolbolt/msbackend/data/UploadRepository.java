package ru.schoolbolt.msbackend.data;

import ru.schoolbolt.msbackend.data.document.EDocumentType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UploadRepository {
    private final Database database;

    UploadRepository(final Database database) {
        this.database = database;
    }

    private static final String GET_NUMBERS_SQL = "" +
            "SELECT \"number\" FROM Uploads WHERE \"type\" = ?";

    public List<Integer> getNumbers(final EDocumentType type) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps = c.prepareStatement(GET_NUMBERS_SQL);
            ps.setString(
                    1,
                    String.valueOf(type)
            );

            final ResultSet rs = ps.executeQuery();

            c.commit();

            final List<Integer> numbers = new ArrayList<>();
            while (rs.next()) {
                numbers.add(rs.getInt("number"));
            }
            return numbers;
        }
    }

    private static final String GET_SQL = "" +
            "SELECT uuid FROM Uploads WHERE \"number\" = ?";

    public String get(final int number) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps = c.prepareStatement(GET_SQL);
            ps.setInt(
                    1,
                    number
            );

            final ResultSet rs = ps.executeQuery();

            c.commit();

            if (rs.next()) {
                return rs.getObject("uuid").toString();
            }
            return null;
        }
    }

    private static final String UPLOAD_SQL = "" +
            "INSERT INTO Uploads VALUES (?, ?, ?)";

    public void upload(
            final int number,
            final UUID uuid,
            final EDocumentType type
    ) throws SQLException {
        try (final Connection c = database.getConnection()) {
            final PreparedStatement ps = c.prepareStatement(UPLOAD_SQL);
            ps.setInt(
                    1,
                    number
            );
            ps.setObject(
                    2,
                    uuid
            );
            ps.setString(
                    3,
                    String.valueOf(type)
            );

            ps.executeUpdate();

            c.commit();
        }
    }
}
