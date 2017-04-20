package com.chai.mockit.server.database;

import java.sql.*;
import java.util.*;

/**
 * Created by chaishipeng on 2017/4/19.
 */
public class SqliteDB {

    public static String dbPath;

    private static Connection conn;

    private static Object lock = new Object();

    public static void execute(String sql) {
        initCon();
        Statement stat = null;
        try {
            stat = conn.createStatement();
            stat.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Result queryForList(String sql) {
        initCon();
        Result result = new Result();
        List list = new ArrayList();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData resultSetMetaData = preparedStatement.getMetaData();
            int column = resultSetMetaData.getColumnCount();
            for (int index = 1 ; index <= column ; index ++){
                MetaData metaData = new MetaData();
                String name = resultSetMetaData.getColumnLabel(index);
                metaData.setName(name);
                metaData.setMaxLength(name.length());
                result.addMeta(name, metaData);
            }

            while(resultSet.next()) {
                Map<String, Object> data = new LinkedHashMap<String, Object>();
                // 每循环一条将列名和列值存入Map
                for (int i = 1; i <= column; i++) {
                    String columnName = resultSetMetaData.getColumnLabel(i);
                    Object obj = resultSet.getObject(columnName);
                    String dataStr = obj == null? "" : obj.toString();
                    data.put(columnName, dataStr);
                    result.addMetaLength(columnName, dataStr.length());
                }
                // 将整条数据的Map存入到List中
                list.add(data);
            }

            result.setDatas(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void initCon() {
        if (conn != null) {
            return;
        }
        synchronized (lock) {
            if (conn != null) {
                return;
            }
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Result {

        private Map<String, MetaData> metaDataMap = new LinkedHashMap<String, MetaData>();

        private List<Map<String, String>> datas;

        public void addMeta(String name, MetaData meta){
            metaDataMap.put(name, meta);
        }

        public void addMetaLength(String name, int length){
            MetaData metaData = metaDataMap.get(name);
            if (length > metaData.getMaxLength()){
                metaData.setMaxLength(length);
            }
        }

        public Map<String, MetaData> getMetaDataMap(){
            return metaDataMap;
        }

        public List<Map<String, String>> getDatas() {
            return datas;
        }

        public void setDatas(List<Map<String, String>> datas) {
            this.datas = datas;
        }
    }

    public static class MetaData{

        private String name;

        private int maxLength;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(int maxLength) {
            this.maxLength = maxLength;
        }
    }

}
