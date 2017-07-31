package SQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Created by Twj on 2017/5/28.
 */

public class ToSQL  {

    private static Connection getSQLConnection(String ip, String user, String pwd, String db)
    {
        Connection con = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + ":1433/" + db + ";charset=utf8", user, pwd);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return con;
    }

    public static String testSQL()
    {
        String result = "字段1  -  字段2\n";
        try
        {
            Connection conn = getSQLConnection("TWJ-PC\\MYSQLSERVER", "sa", "123", "WebServer");
            String sql = "select top 10 * from T_SYS_Tree";
            Statement stmt = conn.createStatement();// 閸掓稑缂揝tatement
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {// <code>ResultSet</code>閺堬拷鍨甸幐鍥ф倻缁楊兛绔寸悰锟�
                String s1 = rs.getString("ID");
                String s2 = rs.getString("Name");
                result += s1 + "  -  " + s2 + "\n";
                System.out.println(s1 + "  -  " + s2);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            result += "查询数据异常!" + e.getMessage();
        }
        return result;
    }

    public static void main(String[] args)
    {
        testSQL();
    }



}
