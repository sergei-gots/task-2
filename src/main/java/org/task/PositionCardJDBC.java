package org.task;

import org.apache.commons.dbutils.DbUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PositionCardJDBC implements PositionCardDAO {

        static private final AppProperties appProperties = AppProperties.loadProperties();

        public PositionCardJDBC() throws ClassNotFoundException {

            try {
                //load driver communication of postgresql.
                Class.forName(appProperties.getDriver());
            } catch (ClassNotFoundException e) {
                System.out.println("Could not load class " + appProperties.getDriver());
                e.printStackTrace();
                throw e;
            }

        }

        private Connection getConnection () throws SQLException{
            //open the connection
            return DriverManager.getConnection(
                    appProperties.getUrl(),
                    appProperties.getUsername(),
                    appProperties.getPassword()
            );
        }

        @Override
        public Optional<String> getPositionNameByGroupId(String groupId)  {

            String sqlFilename = "scripts/get-position-name-by-group-id.sql";
            String sql;
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(sqlFilename)) {
                assert is != null;
                sql =  new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    sql = sql.replaceAll("\r\n", " ");
            }
            catch (Exception e) {
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
            } catch(SQLException e) {
                e.printStackTrace();
                DbUtils.closeQuietly(sqlStatement);
                DbUtils.closeQuietly(resultSet);
                DbUtils.closeQuietly(connection);
            }

            return Optional.empty();
        }

    }


