package BasicClientServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDataBase
{
    // -- objects to be used for database access
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rset = null;
    private static String sqlcmd = null;
    private static String result = null;


    // -- root/admin
    // -- connect to the world database
    // -- this is the connector to the database, default port is 3306
    //    <<Your schema name here>> is the schema (database) you created using the workbench
    private static final String userdatabaseURL = "jdbc:mysql://localhost:3306/userdata?useSSL=false";

    // -- this is the username/password, created during installation and in MySQL Workbench
    //    When you add a user make sure you give them the appropriate Administrative Roles
    //    (DBA sets all which works fine)
    private static String user = "root";
    private static String pw = "root";

    private static String[] getInfo(String username) throws SQLException
    {
        String[] userInfo = new String[5];

        // -- make the connection to the database
        //    performs functionality of SQL: use <<your schema>>;
        conn = DriverManager.getConnection(userdatabaseURL, user, pw);

        // -- These will be used to send queries to the database
        stmt = conn.createStatement();


        //        SELECT * FROM users WHERE username = 'ccrdoc'
        sqlcmd = "SELECT * FROM users WHERE username = '" + username + "'";
        rset = stmt.executeQuery(sqlcmd);
        // -- loop through the ResultSet one row at a time
        //    Note that the ResultSet starts at index 1
        while (rset.next()) {
            // -- loop through the columns of the ResultSet
            for (int i = 1; i <= 5; ++i) {
                userInfo[i-1] = rset.getString(i);
                System.out.println(userInfo[i-1]);
            }
            System.out.println();
        }
        return userInfo;

    }

    public static String login(String username, String password)
    {

        int lockout = 0;
        boolean loggedIn = false;
        String[] userInfo = new String[5];

        try
        {
            userInfo = getInfo(username);
        }
        catch(SQLException e)
        {
            System.out.println("UserDataBase.getInfo returned SQL Exception " + e.getMessage());
            return "loginFailure";
        }

        if(userInfo[1] == null)
        {
            return "loginFailure";
        }
        else if(userInfo[2].equals(password) && Integer.parseInt(userInfo[4]) == 0 && Integer.parseInt(userInfo[0]) < 3)
        {
            try {
                sqlcmd = "UPDATE users SET loggedin = '1' WHERE (username = '" + username + "')";
                stmt.executeUpdate(sqlcmd);
                sqlcmd = "UPDATE users SET lockout = '0' WHERE (username = '" + username + "')";
                stmt.executeUpdate(sqlcmd);
                userInfo = getInfo(username);
                System.out.println(userInfo[0]);
                return "loginSuccess";
            }
            catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                return "loginFailure";
            }
        }
        else if(userInfo[2].equals(password) && Integer.parseInt(userInfo[0]) >= 3)
        {
            return "lockedOut";
        }
        else
        {
            try
            {
                sqlcmd = "UPDATE users SET lockout = '" + (Integer.parseInt(userInfo[0]) + 1) + "' WHERE (username = '" + username + "')";
                stmt.executeUpdate(sqlcmd);
            }
            catch (SQLException ex)
            {
                System.out.println("SQLException: " + ex.getMessage());
            }
            return "loginFailure";
        }

    }

    public static String logout(String username)
    {
        String[] userInfo;
        try
        {
            sqlcmd = "UPDATE users SET loggedin = '0' WHERE (username = '" + username + "')";
            stmt.executeUpdate(sqlcmd);
            userInfo = getInfo(username);
            System.out.println(userInfo[0]);
            return "logoutSuccess";
        }
        catch(SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            return "logoutFailure";
        }
    }

    public static String registration(String username, String password, String email)
    {
        String[] userInfo;
        try
        {
            userInfo = getInfo(username);
        }
        catch(SQLException e)
        {
            System.out.println("UserDataBase.getInfo returned SQL Exception " + e.getMessage());
            return "RegistrationFailure";
        }
        if (userInfo[1] != null){
            System.out.println("You already exist Dummy");
            return "RegistrationFailure";
        }
        else {
            try {
                sqlcmd = "INSERT INTO users VALUES('0'," + "'" + username + "', '" + password + "', '" + email + "', '0')";
                stmt.executeUpdate(sqlcmd);
                return "RegistrationSuccessful";
            }
            catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                return "RegistrationFailure";
            }
        }

    }

    public static String changePassword(String username, String oldPassword, String newPassword)
    {
        String[] userInfo;
        try
        {
            userInfo = getInfo(username);
        }
        catch(SQLException e)
        {
            System.out.println("UserDataBase.getInfo returned SQL Exception " + e.getMessage());
            return "PasswordChangeFailure";
        }
        if(oldPassword.equals(userInfo[2]))
        {
            try
            {
                sqlcmd = "UPDATE users SET userpassword = '" + newPassword + "' WHERE (username = '" + username + "')";
                stmt.executeUpdate(sqlcmd);
                userInfo = getInfo(username);
                System.out.println(userInfo[1] + " " + userInfo[2]);
                return "passwordChangeSuccess";
            }
            catch(SQLException ex)
            {
                System.out.println("SQLException: " + ex.getMessage());
                return "PasswordChangeFailure";
            }
        }
        else
        {
            System.out.println("Wrong password");
            return "PasswordChangeFailure:WrongPassword";
        }
    }

    public static boolean changePassword(String email, String newPassword)
    {
        String[] userInfo;
        try
        {
            sqlcmd = "UPDATE users SET password = '" + newPassword + "' WHERE (email = '" + email + "')";
            stmt.executeUpdate(sqlcmd);
            return true;
        }
        catch(SQLException e)
        {
            System.out.println("UserDataBase.changePassword(email): " + e.getMessage());
            return false;
        }
    }

    public static int getRegisteredUsers() {
        int count = 0;
        sqlcmd = "SELECT username FROM users";
        try {
            conn = DriverManager.getConnection(userdatabaseURL, user, pw);
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sqlcmd);

            while (rset.next()){
                count++;
            }
            return count;
        }
        catch(SQLException e){
            return -1;
        }
    }

    public static int getLoggedInNum() {
        int count = 0;
        sqlcmd = "SELECT username FROM users WHERE (loggedin = '1')";
        try {
            conn = DriverManager.getConnection(userdatabaseURL, user, pw);
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sqlcmd);

            while (rset.next()){
                count++;
            }
            return count;
        }
        catch(SQLException e){
            return -1;
        }
    }

    public static String getLoggedInUsers() {
        String stringy = "";

        sqlcmd = "SELECT username FROM users WHERE (loggedin = '1')";
        try {
            conn = DriverManager.getConnection(userdatabaseURL, user, pw);
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sqlcmd);

            while (rset.next()){
                stringy = stringy.concat(rset.getString("username"));
                stringy = stringy.concat("/o/");
            }
            return stringy;
        }
        catch(SQLException e){
            return "ErR0r";
        }
    }

    public static String getLockedUsers() {
        String stringy = "";

        sqlcmd = "SELECT username FROM users WHERE (lockout >= '3')";
        try {
            conn = DriverManager.getConnection(userdatabaseURL, user, pw);
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sqlcmd);

            while (rset.next()){
                stringy = stringy.concat(rset.getString("username"));
                stringy = stringy.concat("/o/");
            }
            return stringy;
        }
        catch(SQLException e){
            return "ErR0r";
        }
    }

    public static void main(String[] args) {
        System.out.println(getLockedUsers());
    }
}
