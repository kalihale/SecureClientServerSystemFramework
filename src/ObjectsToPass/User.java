package ObjectsToPass;

public class User
{
    int action;
    private String username;
    private String password;
    private String oldPassword;

    public User()
    {
        action = -1;
        username = "";
        password = "";
        oldPassword = "";
    }

    public void setAction(int action)
    {
        this.action = action;
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

    public int getAction()
    {
        return action;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getOldPassword()
    {
        return oldPassword;
    }
}
