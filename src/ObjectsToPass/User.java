package ObjectsToPass;

public class User extends QueryObject
{
    private String userID;
    private String username;
    private String password;
    private String oldPassword;
    private String email;
    private String userRole;

    public User()
    {
        super(-1);
        userID = "";
        username = "";
        password = "";
        oldPassword = "";
        email = "";
        userRole = "";
    }

    public User(int action, String userID, String username, String password, String oldPassword,
                String email, String userRole)
    {
        super(action);
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.oldPassword = oldPassword;
        this.email = email;
        this.userRole = userRole;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setUserRole(String userRole)
    {
        this.userRole = userRole;
    }

    public String getUserID()
    {
        return userID;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getOldPassword()
    {
        return this.oldPassword;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getUserRole()
    {
        return userRole;
    }
}
