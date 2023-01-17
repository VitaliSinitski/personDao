package vitali.dao.dao;

import vitali.dao.annotation.MyColumn;
import vitali.dao.annotation.MyTable;
import vitali.dao.util.DaoUtils;
import vitali.dao.vo.Person;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

public class DaoPersonImpl implements DaoPerson {

    public static final String TABLE_NAME = Person.class.getDeclaredAnnotation(MyTable.class).name();
    public static final List<String> COLUMNS = DaoUtils.tableColumns(Person.class);


    @Override
    public Person save(Person person) throws SQLException {
        String sql = "insert into " + TABLE_NAME + " (" + COLUMNS.get(1) + ", " + COLUMNS.get(2) + " ) "
                     + " values ( '?', '*' )";
        sql = sql.replace("?", person.getName()).replace("*", person.getSurname());
//        sql = sql.replace("*", person.getSurname());
        String sqlSelect = "select * from " + TABLE_NAME + " order by id";

        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementSelect = null;
        ResultSet resultSet = null;
        connection.setAutoCommit(false);
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int row = resultSet.getInt(1);
            person.setId(row);

            preparedStatementSelect = connection.prepareStatement(sqlSelect);
            resultSet = preparedStatementSelect.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2)
                                   + " " + resultSet.getString(3));
            }
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            DaoUtils.close(resultSet);
            DaoUtils.close(preparedStatement);
            DaoUtils.close(preparedStatementSelect);
            DaoUtils.close(connection);
        }
        return person;
    }

    @Override
    public Person get(int id) throws SQLException {
        String sqlSelect = "select * from " + TABLE_NAME + " where "
                           + COLUMNS.get(0) + " = '?'" + "order by id";
        sqlSelect = sqlSelect.replace("?", Integer.toString(id));
        Person person = null;

        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementSelect = null;
        ResultSet resultSet = null;
        connection.setAutoCommit(false);
        try {
            preparedStatementSelect = connection.prepareStatement(sqlSelect);
            resultSet = preparedStatementSelect.executeQuery();
            while (resultSet.next()) {
                person = Person.builder().id(resultSet.getInt(1)).name(resultSet.getString(2))
                        .surname(resultSet.getString(3)).build();
            }
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            DaoUtils.close(resultSet);
            DaoUtils.close(preparedStatement);
            DaoUtils.close(preparedStatementSelect);
            DaoUtils.close(connection);
        }
        return person;
    }

    @Override
    public void update(Person person) throws SQLException {
        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementSelect = null;
        ResultSet resultSet = null;
        connection.setAutoCommit(false);
        try {
            String sql = "update " + TABLE_NAME +
                         " set " + COLUMNS.get(1) + " = '?'" +
                         " where " + COLUMNS.get(0) + " = '!'";
            sql = sql.replace("?", person.getName());
            sql = sql.replace("!", Integer.toString(person.getId()));
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            sql = "update " + TABLE_NAME +
                  " set " + COLUMNS.get(2) + " = '*'" +
                  " where " + COLUMNS.get(0) + " = '!'";
            sql = sql.replace("*", person.getSurname());
            sql = sql.replace("!", Integer.toString(person.getId()));
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            String sqlSelect = "select * from " + TABLE_NAME +
                               " order by " + COLUMNS.get(0) + " desc";

            preparedStatementSelect = connection.prepareStatement(sqlSelect);
            resultSet = preparedStatementSelect.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) +
                                   " " + resultSet.getString(3));

            }
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            DaoUtils.close(resultSet);
            DaoUtils.close(preparedStatement);
            DaoUtils.close(preparedStatementSelect);
            DaoUtils.close(connection);
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        String sqlDelete = "delete " + TABLE_NAME + " from " + TABLE_NAME + " where " + COLUMNS.get(0) + " = '?'";
        sqlDelete = sqlDelete.replace("?", Integer.toString(id));
        int rows = 0;

        Connection connection = DaoUtils.getConnection();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementSelect = null;
        ResultSet resultSet = null;
        connection.setAutoCommit(false);
        try {
            preparedStatementSelect = connection.prepareStatement(sqlDelete);
            rows = preparedStatementSelect.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            DaoUtils.close(resultSet);
            DaoUtils.close(preparedStatement);
            DaoUtils.close(preparedStatementSelect);
            DaoUtils.close(connection);
        }
        return rows;
    }
}
