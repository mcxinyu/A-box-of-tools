package Database;
/**
 * Created by 跃峰 on 2015/12/26.
 * 连接、操作数据库
 * 调用存储过程？
 */
import java.sql.*;

public class SqlHelper {
    //定义需要的对象
    PreparedStatement ps=null;
    ResultSet rs=null;
    Connection ct=null;

    String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String ConnectionUrl="jdbc:sqlserver://localhost:1433;databaseName=ABoxOfTools;integratedSecurity=true;";

    //构造函数，初始化ct
    public SqlHelper() {
        try {
            //加载驱动
            Class.forName(driverName);
            ct=DriverManager.getConnection(ConnectionUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //数据库的增，删，改方法
    public boolean exeUpdate(String sql, String []paras) {
        boolean b=true;
        try {
            ps=ct.prepareStatement(sql);
            //给sql语句中的?号赋值
            for(int i=0; i<paras.length; i++) {
                ps.setString(i+1,paras[i]);
            }
            ps.executeUpdate();   //更新数据
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    //数据库的查询方法
    public ResultSet query(String sql, String []paras) {
        try {
            ps=ct.prepareStatement(sql);
            //给sql语句中的?号赋值
            for(int i=0; i<paras.length; i++) {
                ps.setString(i+1,paras[i]);
            }
            rs=ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    //关闭资源的方法
    public void close() {
        try {
            if(rs!=null) rs.close();
            if(ps!=null) ps.close();
            if(ct!=null) ct.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
