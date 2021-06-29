package Server;

import ObjectsToPass.User;
import Security.AES256;
import Security.SymmetricEncrypt;

import java.io.Serializable;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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

    // -- objects to be used for ExamplesAndReferences.database access
    private static Connection conn = null;
    private static ResultSet rset = null;
    private static Stack<String>[] results = null;
    private static CallableStatement statement;
    private static boolean hadResults = false;

    // -- root/admin
    // -- connect to the world ExamplesAndReferences.database
    // -- this is the connector to the ExamplesAndReferences.database, default port is 3306
    //    <<Your schema name here>> is the schema (ExamplesAndReferences.database) you created using the workbench
    private static final String userdatabaseURL = "jdbc:mysql://localhost:3306/userData?useSSL=false";

    // -- this is the username/password, created during installation and in MySQL Workbench
    //    When you add a user make sure you give them the appropriate Administrative Roles
    //    (DBA sets all which works fine)
    private static final String USERNAME = "root";
    private static final String PW = "rosegarden";

    // ／(•ㅅ•)＼ Declare encryption module
    private SymmetricEncrypt<Serializable> encrypt;

    /** ／(•ㅅ•)＼
     * process will do all functions possible with a User object in relation to the database
     * @param user: the User object sent from the ClientGUI
     * @return String (indication of success or failure)
     * @throws SQLException if SQL statement is wrong
     * @throws NoSuchAlgorithmException if SymmetricEncrypt is wrong
     * @throws InvalidKeySpecException if encrypt.getKeyFromPassword throws an exception
     */
    public String process(User user) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        // -- make the connection to the ExamplesAndReferences.database
        //    performs functionality of SQL: use <<your schema>>;
        conn = DriverManager.getConnection(userdatabaseURL, USERNAME, PW);

        // ／(•ㅅ•)＼ Initialize encryption module
        encrypt = new AES256<>();
        Key key = encrypt.getKeyFromPassword(PW, "saltsaltsalt");

        this.username = user.getUsername();
        System.out.println(user.getUsername() + " " + user.getAction());
        try
        {
            switch (user.getAction())
            {
                case 0:
                    //  ／(•ㅅ•)＼  Logout with stored procedure
                    System.out.println("Logged out " + this.username);
                    statement = conn.prepareCall("{call userData.logout(?)}");
                    statement.setString(1, encrypt.bytesToString(encrypt.encryptString(user.getUsername(), key)));
                    statement.execute();
                    statement.close();
                    return "logoutSuccess";
                case 1:
                    //  ／(•ㅅ•)＼  Login with stored procedure
                    statement = conn.prepareCall("{? = call login(?,?)}");
                    statement.registerOutParameter(1, Types.BOOLEAN);
                    statement.setString(2, encrypt.bytesToString(encrypt.encryptString(user.getUsername(), key)));
                    statement.setString(3, encrypt.bytesToString(encrypt.encryptString(user.getPassword(), key)));
                    statement.execute();
                    boolean exists = statement.getBoolean(1);
                    statement.close();
                    if(exists)
                    {
                        return "loginSuccess";
                    }
                    else
                    {
                        return "loginFailure";
                    }
                case 2:
                    //  ／(•ㅅ•)＼  Change password
                    System.out.println("Changed password for " + this.username);
                    statement = conn.prepareCall("{? = call changePassword(?, ?, ?)}");
                    statement.registerOutParameter(1, Types.BOOLEAN);
                    statement.setString("un", encrypt.bytesToString(encrypt.encryptString(user.getUsername(), key)));
                    statement.setString("oldpass", encrypt.bytesToString(encrypt.encryptString(user.getOldPassword(), key)));
                    statement.setString("newpass", encrypt.bytesToString(encrypt.encryptString(user.getPassword(), key)));
                    statement.execute();
                    boolean success = statement.getBoolean(1);
                    statement.close();
                    if(success)
                    {
                        return "passwordChangeSuccess";
                    }
                    else
                    {
                        return "password change failure";
                    }
                case 3:
                    //  ／(•ㅅ•)＼  Forgot password
                    System.out.println("Forgot password " + user.getEmail());
                    return "forgotPasswordSuccessful";
                case 4:
                    //  ／(•ㅅ•)＼  Registration
                    statement = conn.prepareCall("{call register(?, ?, ?, ?, ?, ?)}");
                    statement.setString("id", encrypt.bytesToString(encrypt.encryptString(user.getUserID(), key)));
                    statement.setString("un", encrypt.bytesToString(encrypt.encryptString(user.getUsername(), key)));
                    statement.setString("pass", encrypt.bytesToString(encrypt.encryptString(user.getPassword(), key)));
                    statement.setString("email", encrypt.bytesToString(encrypt.encryptString(user.getEmail(), key)));
                    statement.setString("uRole", encrypt.bytesToString(encrypt.encryptString(user.getUserRole(), key)));
                    statement.setString("createdBy", encrypt.bytesToString(encrypt.encryptString("css", key)));
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
        return "SQLError";
    }

    public static Stack<String>[] getLoggedInUsers(SymmetricEncrypt<Serializable> encrypt, Key key) throws SQLException
    {
        // -- make the connection to the ExamplesAndReferences.database
        //    performs functionality of SQL: use <<your schema>>;
        conn = DriverManager.getConnection(userdatabaseURL, USERNAME, PW);

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
                results[0].add(encrypt.decryptString(encrypt.stringToBytes(rset.getString("userID")), key));
                results[1].add(encrypt.decryptString(encrypt.stringToBytes(rset.getString("username")), key));
            }
            hadResults = statement.getMoreResults();
        }
        return results;
    }

    public static Stack<String>[] getLockedOutUsers(SymmetricEncrypt<Serializable> encrypt, Key key) throws SQLException
    {
        // -- make the connection to the ExamplesAndReferences.database
        //    performs functionality of SQL: use <<your schema>>;
        conn = DriverManager.getConnection(userdatabaseURL, USERNAME, PW);

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
                results[0].add(encrypt.decryptString(encrypt.stringToBytes(rset.getString("userID")), key));
                results[1].add(encrypt.decryptString(encrypt.stringToBytes(rset.getString("username")), key));
            }
            hadResults = statement.getMoreResults();
        }
        return results;
    }

    public static String getLoggedInNum() throws SQLException
    {
        // -- make the connection to the ExamplesAndReferences.database
        //    performs functionality of SQL: use <<your schema>>;
        conn = DriverManager.getConnection(userdatabaseURL, USERNAME, PW);

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

    public static String getRegisteredNum(SymmetricEncrypt<Serializable> encrypt, Key key) throws SQLException
    {
        // -- make the connection to the ExamplesAndReferences.database
        //    performs functionality of SQL: use <<your schema>>;
        conn = DriverManager.getConnection(userdatabaseURL, USERNAME, PW);

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

    public static void main(String[] args)
    {
        SymmetricEncrypt<Serializable> encrypt = new AES256<>();
        Key key = null;
        try
        {
            key = encrypt.getKeyFromPassword("rosegarden", "saltsaltsalt");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            e.printStackTrace();
        }
        String[] roles = {"manager", "estimator", "office staff"};
        try
        {
            conn = DriverManager.getConnection(userdatabaseURL, USERNAME, PW);
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        try
        {
            PreparedStatement statement = conn.prepareStatement("insert into userData.userRoles(userRoleName) values (?)");
            for(String S : roles)
            {
                statement.setString(1, encrypt.bytesToString(encrypt.encryptString(S, key)));
                statement.execute();
            }
            statement.close();
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }
}
