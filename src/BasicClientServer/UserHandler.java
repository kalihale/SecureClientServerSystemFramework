package BasicClientServer;

import ObjectsToPass.User;

import java.sql.*;
import java.util.Stack;

// -- Download JavaMail API from here: http://www.oracle.com/technetwork/java/javamail/index.html
// -- Download JavaBeans Activation Framework from here: http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-java-plat-419418.html#jaf-1.1.1-fcs-oth-JPR
//    Activation Framework is only needed for earlier versions of Java
// -- Your gmail account must be set to allow "less secure apps" to access it
//    Information is found here: https://devanswers.co/allow-less-secure-apps-access-gmail-account/
// -- javax.mail.jar moved here: https://javaee.github.io/javamail/?xd_co_f=c9a74338-3e62-4b48-9e18-489fa570fd5b
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

public class UserHandler
{
    private String username;
    private String reply;

    // -- objects to be used for database access
    private static Connection conn = null;
    private static ResultSet rset = null;
    private static Stack<String>[] results = null;
    private static CallableStatement statement;
    private static boolean hadResults = false;

    // -- root/admin
    // -- connect to the world database
    // -- this is the connector to the database, default port is 3306
    //    <<Your schema name here>> is the schema (database) you created using the workbench
    private static final String userdatabaseURL = "jdbc:mysql://localhost:3306/userData?useSSL=false";

    // -- this is the username/password, created during installation and in MySQL Workbench
    //    When you add a user make sure you give them the appropriate Administrative Roles
    //    (DBA sets all which works fine)
    private static final String user = "root";
    private static final String pw = "rosegarden";

    public String process(User process) throws SQLException
    {
        // -- make the connection to the database
        //    performs functionality of SQL: use <<your schema>>;
        conn = DriverManager.getConnection(userdatabaseURL, user, pw);

        this.username = process.getUsername();
        System.out.println(process.getUsername() + " " + process.getAction());
        try
        {
            switch (process.getAction())
            {
                case 0:
                    //  <@  Logout with stored procedure
                    System.out.println("Logged out " + this.username);
                    return "logoutSuccess";
                case 1:
                    //  <@  Login with stored procedure
                    System.out.println("Logged in " + this.username);
                    return "loginSuccess";
                case 2:
                    //  <@  Change password
                    System.out.println("Changed password for " + this.username);
                    return "passwordChangeSuccess";
                case 3:
                    //  <@  Forgot password
                    System.out.println("Forgot password " + process.getEmail());
                    return "forgotPasswordSuccessful";
                case 4:
                    //  <@  Registration
                    statement = conn.prepareCall("{call register(?, ?, ?, ?, ?)}");
                    statement.setString("id", process.getUserID());
                    statement.setString("un", process.getUsername());
                    statement.setString("pass", process.getPassword());
                    statement.setString("uRole", process.getUserRole());
                    statement.setString("createdBy", "css");
                    statement.execute();
                    statement.close();

                    return "RegistrationSuccessful";
                default:
                    break;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return "SQLerror";
    }

    public static Stack<String>[] getLoggedInUsers() throws SQLException
    {
        // -- make the connection to the database
        //    performs functionality of SQL: use <<your schema>>;
        conn = DriverManager.getConnection(userdatabaseURL, user, pw);

        results = new Stack[2];
        results[0] = new Stack<>();
        results[1] = new Stack<>();
        statement = conn.prepareCall("{call loggedInUsers()}");
        hadResults = statement.execute();
        while(hadResults)
        {
            rset = statement.getResultSet();
            while(rset.next())
            {
                results[0].add(rset.getString("userID"));
                results[1].add(rset.getString("username"));
            }
            hadResults = statement.getMoreResults();
        }
        return results;
    }

    public static Stack<String>[] getLockedOutUsers() throws SQLException
    {
        // -- make the connection to the database
        //    performs functionality of SQL: use <<your schema>>;
        conn = DriverManager.getConnection(userdatabaseURL, user, pw);

        results = new Stack[2];
        results[0] = new Stack<>();
        results[1] = new Stack<>();
        statement = conn.prepareCall("{call lockedOut()}");
        hadResults = statement.execute();
        while(hadResults)
        {
            rset = statement.getResultSet();
            while(rset.next())
            {
                results[0].add(rset.getString("userID"));
                results[1].add(rset.getString("username"));
            }
            hadResults = statement.getMoreResults();
        }
        return results;
    }

    public static String getLoggedInNum() throws SQLException
    {
        // -- make the connection to the database
        //    performs functionality of SQL: use <<your schema>>;
        conn = DriverManager.getConnection(userdatabaseURL, user, pw);

        String result = "0";
        statement = conn.prepareCall("{call loggedInNum()}");
        hadResults = statement.execute();
        while(hadResults)
        {
            rset = statement.getResultSet();
            while(rset.next())
            {
                result = rset.getString("count(username)");
            }
            hadResults = statement.getMoreResults();
        }
        return result;
    }

    public static String getRegisteredNum() throws SQLException
    {
        // -- make the connection to the database
        //    performs functionality of SQL: use <<your schema>>;
        conn = DriverManager.getConnection(userdatabaseURL, user, pw);

        String result = "0";
        statement = conn.prepareCall("{call registeredNum()}");
        hadResults = statement.execute();
        while(hadResults)
        {
            rset = statement.getResultSet();
            while(rset.next())
            {
                result = rset.getString("count(userID)");
            }
            hadResults = statement.getMoreResults();
        }
        return result;
    }
}
