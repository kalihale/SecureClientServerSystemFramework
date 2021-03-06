package Server;

import java.sql.*;

public class ServerDataBase
{
    // -- objects to be used for ExamplesAndReferences.database access
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rset = null;
    private static String sqlcmd = null;
    private static String result = null;


    // -- root/admin
    // -- connect to the world ExamplesAndReferences.database
    // -- this is the connector to the ExamplesAndReferences.database, default port is 3306
    //    <<Your schema name here>> is the schema (ExamplesAndReferences.database) you created using the workbench
   //  ／(•ㅅ•)＼  "jdbc:mysql://<<IP_address_here>>:<<port_here>>/<<schema_name_here>>?useSSL=false"
    private static String userdatabaseURL = "jdbc:mysql://localhost:3306/serverdata?useSSL=false";

    // -- this is the username/password, created during installation and in MySQL Workbench
    //    When you add a user make sure you give them the appropriate Administrative Roles
    //    (DBA sets all which works fine)
    private static String user = "root";
    private static String pw = "root";

    //  ／(•ㅅ•)＼  https://www.codejava.net/java-se/jdbc/jdbc-examples-for-calling-stored-procedures-mysql
    //      How to call a procedure using Java

    public static String getInfo(String name) throws SQLException
    {
        String charInfo = "";

        // -- make the connection to the ExamplesAndReferences.database
        //    performs functionality of SQL: use <<your schema>>;
        conn = DriverManager.getConnection(userdatabaseURL, user, pw);

        // -- These will be used to send queries to the ExamplesAndReferences.database
        stmt = conn.createStatement();

        //        SELECT * FROM users WHERE username = 'ccrdoc'
        sqlcmd = "SELECT * FROM mariodata WHERE charname = '" + name + "'";
        rset = stmt.executeQuery(sqlcmd);
        try
        {
            ResultSetMetaData rsmd = rset.getMetaData();
            // -- loop through the ResultSet one row at a time
            //    Note that the ResultSet starts at index 1
            while (rset.next())
            {
                // -- loop through the columns of the ResultSet
                for (int i = 1; i <= 4; i++)
                {
                    charInfo = charInfo.concat(rsmd.getColumnLabel(i));
                }
                for (int i = 1; i <= 4; ++i)
                {
                    charInfo = charInfo.concat(rset.getString(i) + "/");
//                userInfo[i-1] = rset.getString(i);
//                System.out.println(userInfo[i-1]);
                }
//            System.out.println();
            }
            return charInfo;
        }
        catch(Exception e)
        {
            return "No character data for " + name;
        }

    }

}
