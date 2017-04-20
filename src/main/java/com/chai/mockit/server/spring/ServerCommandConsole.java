package com.chai.mockit.server.spring;

import com.chai.mockit.server.common.Configs;
import com.chai.mockit.server.database.SqliteDB;
import com.chai.simconsole.api.ConsoleHandler;
import com.chai.simconsole.impl.CommandConsole;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/**
 * Created by chaishipeng on 2017/4/19.
 */
public class ServerCommandConsole extends CommandConsole implements ConsoleHandler {

    public void start(){
        super.setPrefix("MockItServer:");
        super.addConsoleHandler(this);
        super.start();
    }

    public String getHelp() {
        return "";
    }

    private void queryAndPrint(String sql, PrintStream ps){
        SqliteDB.Result result = SqliteDB.queryForList(sql);
        Map<String, SqliteDB.MetaData> metaMap = result.getMetaDataMap();
        int sumLength = 0;
        for (String key : metaMap.keySet()){
            SqliteDB.MetaData metaData = metaMap.get(key);
            int formatLength = metaData.getMaxLength() + 2;
            ps.printf("%-"+ formatLength +"s", "|" + metaMap.get(key).getName());
            sumLength = sumLength + formatLength;
        }
        ps.println();
        printLine(sumLength, ps);
        List<Map<String, String>> datas = result.getDatas();
        if (datas != null){
            for (Map<String, String> item : datas){
                for (String key : metaMap.keySet()){
                    SqliteDB.MetaData metaData = metaMap.get(key);
                    int formatLength = metaData.getMaxLength() + 2;
                    ps.printf("%-"+ formatLength +"s", "|" + item.get(key));
                }
                ps.println();
            }
        }
    }

    public void _sqa(String args, PrintStream ps){
        String sql = "select * from " + Configs.mockItDB;
        queryAndPrint(sql, ps);
    }

    private void printLine(int max, PrintStream ps){
        for (int index  = 0 ;index < max ; index ++) {
            ps.print("-");
        }
        ps.println();
    }

    public void _si(String args , PrintStream ps){
        String[] values = args.split(" ");
        String sqlTemplate = "INSERT INTO %s (id,classname,methodname,content)values(null,'%s','%s','%s')";
        String sql = String.format(sqlTemplate, new String[]{Configs.mockItDB, values[0], values[1], values[2]});
        _se(sql, ps);
    }

    public void _sqc(String sql , PrintStream ps){
        sql = "select * from " + Configs.mockItDB + " where " + sql;
        queryAndPrint(sql, ps);
    }

    public void _sqci(String id , PrintStream ps){
        String sql = "select * from " + Configs.mockItDB + " where id = '" + id + "'";
        queryAndPrint(sql, ps);
    }

    public void _su(String args, PrintStream ps) {
        String[] sqls = args.split(" ");
        String sql = "update " + Configs.mockItDB + " set " + sqls[1] + " where id='" + sqls[0] + "'";
        _se(sql, ps);
    }

    public void _se(String sql, PrintStream ps){
        SqliteDB.execute(sql);
    }
}
