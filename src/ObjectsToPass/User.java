package ObjectsToPass;

import java.io.Serializable;

public class User implements Serializable
{
    int action;
    private String username;
    private String password;
    private String oldPassword;
    private String email;

    public User()
    {
        action = -1;
        username = "";
        password = "";
        oldPassword = "";
        email = "";
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

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getAction()
    {
        return this.action;
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
}
