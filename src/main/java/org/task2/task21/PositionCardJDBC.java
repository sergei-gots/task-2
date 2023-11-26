package org.task2.task21;

import org.apache.commons.dbutils.DbUtils;
import org.task2.util.DbProperties;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PositionCardJDBC implements PositionCardDAO {

    static private final DbProperties dbProperties
            = DbProperties.loadProperties("liquibase.properties");

    public PositionCardJDBC() throws ClassNotFoundException {

        dbProperties.loadDriver();
    }

    /**
     * Opens the connection
     *
     * @throws SQLException in case of something wrong
     */
    private Connection getConnection() throws SQLException {
        return dbProperties.getConnection();
    }

    @Override
    public Optional<String> getPositionNameByGroupId(String groupId) {

        String sqlFilename = "scripts/get-position-name-by-group-id.sql";
        String sql;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(sqlFilename)) {
            assert is != null;
            sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            sql = sql.replaceAll("\r\n", " ");
        } catch (Exception e) {
            throw new RuntimeException("Could not read the file which contains the sql request body " + sqlFilename, e);
        }

        PreparedStatement sqlStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;

        try {
            connection = getConnection();
            sqlStatement = connection.prepareStatement(sql);
            sqlStatement.setString(1, groupId);
            resultSet = sqlStatement.executeQuery();
            return (resultSet.next()) ?
                    Optional.of(resultSet.getString(3)) :
                    Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            DbUtils.closeQuietly(sqlStatement);
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(connection);
        }

        return Optional.empty();
    }

}


