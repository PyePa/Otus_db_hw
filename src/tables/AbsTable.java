package tables;

import data.ITableData;
import db.IDBExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbsTable {

    private String tableName;
    private IDBExecutor dbExecutor;

    public AbsTable(String tableName, IDBExecutor dbExecutor) {
        this.tableName = tableName;
        this.dbExecutor = dbExecutor;
    }

    public void create(List<String> columns) {
        delete();
        dbExecutor.execute(String.format("CREATE TABLE %s (%s);", tableName, String.join(",", columns)));
    }

    public void delete() {
        dbExecutor.execute(String.format("DROP TABLE IF EXISTS %s;", tableName));
    }

    public void insert(List<String> columns, ITableData tableData) {
        dbExecutor.execute(String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, String.join(",", columns), tableData.toString()));
    }


    public ResultSet selectSimple(String columnCondition) {
        return dbExecutor.executeQuery(String.format("SELECT %s FROM %s;", columnCondition, tableName));
    }

    public ResultSet count() {
        return dbExecutor.executeQuery(String.format("SELECT COUNT(*) FROM %s;", tableName));
    }

    public ResultSet selectWithWhere(String columnResult, String columnCondition, String valueCondition) {
        return dbExecutor.executeQuery(String.format("SELECT %s FROM %s WHERE %s = '%s';", columnResult, tableName, columnCondition, valueCondition));
    }

    public void update(String columnUpdate, String valueUpdate, String columnCondition, String valueCondition) {
        dbExecutor.execute(String.format("UPDATE %s SET %s=%s WHERE %s=%s;", tableName, columnUpdate, valueUpdate, columnCondition, valueCondition));
    }

    public ResultSet selectWithJoin(String columnResult, String tableColumn, String tableName2, String tableColumn2) {
        return dbExecutor.executeQuery(String.format("SELECT %s FROM %s JOIN %s ON %s.%s = %s.%s;", columnResult, tableName, tableName2, tableName, tableColumn, tableName2, tableColumn2));
    }

    public ResultSet selectWithSubRequest(String columnResult, String tableColumn, String columnResult2, String tableName2, String tableColumn2, String valueCondition) {
        return dbExecutor.executeQuery(String.format("SELECT %s FROM %s WHERE %s = (SELECT %s FROM %s where %s = '%s');", columnResult, tableName, tableColumn, columnResult2, tableName2, tableColumn2, valueCondition));
    }

    public void resultDisplay(ResultSet resultSet) {
        try {
            int countColumns = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i <= countColumns; i++) {
                    stringBuilder.append(resultSet.getString(i)).append(" ");
                    if (i == countColumns) {
                        System.out.println(stringBuilder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}

