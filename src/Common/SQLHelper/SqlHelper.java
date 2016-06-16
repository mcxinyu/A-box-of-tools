package Common.SQLHelper;
/**
 * Created by 跃峰 on 2015/12/26.
 * 连接、操作数据库
 * 调用存储过程？
 */
import java.sql.*;

public class SqlHelper {
    //定义需要的对象
    PreparedStatement preparedStatement =null;
    ResultSet resultSet =null;
    Connection connection=null;
    boolean isConnected = false;

    String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String ConnectionUrl="jdbc:sqlserver://localhost:1433;databaseName=WYZJ;user=sa;password=0897Mssql;";

    //构造函数，初始化connection
    public SqlHelper() {
        try {
            //加载驱动
            Class.forName(driverName);
            connection = DriverManager.getConnection(ConnectionUrl);
            isConnected = true;
            System.out.println("数据库连接成功");
            System.out.println(connection);
        } catch (SQLException se){
            isConnected = false;
            System.out.println("数据库连接失败");
            System.out.println(connection);
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //数据库的查询方法
    public ResultSet query(String sqlStatement, String []paras) {
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            //给sql语句中的?号赋值
            for(int i=0; i<paras.length; i++) {
                preparedStatement.setString(i+1,paras[i]);
            }
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //数据库的增，删，改方法
    public boolean modify(String sqlStatement, String []paras) {
        boolean b=true;
        try {
            preparedStatement =connection.prepareStatement(sqlStatement);
            //给sql语句中的?号赋值
            for(int i=0; i<paras.length; i++) {
                preparedStatement.setString(i+1,paras[i]);
            }
            preparedStatement.executeUpdate();   //更新数据
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    //关闭资源的方法
    public void close() {
        try {
            if(resultSet !=null) resultSet.close();
            if(preparedStatement !=null) preparedStatement.close();
            if(connection !=null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return isConnected;
    }
}
