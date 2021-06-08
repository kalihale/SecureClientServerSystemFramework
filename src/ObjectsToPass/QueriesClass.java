package ObjectsToPass;

import java.io.Serializable;

public class QueriesClass implements Serializable
{
    int queryNum;
    String firstName;
    String lastName;
    String birthdate;
    String clubName;
    String clubSubject;
    String clubDescription;

    public QueriesClass()
    {
        queryNum = -1;
        firstName = "";
        lastName = "";
        birthdate = "";
        clubName = "";
        clubSubject = "";
        clubDescription = "";
    }

    public void setQueryNum(int queryNum)
    {
        this.queryNum = queryNum;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setBirthdate(String birthdate)
    {
        this.birthdate = birthdate;
    }

    public void setClubName(String clubName)
    {
        this.clubName = clubName;
    }

    public void setClubSubject(String clubSubject)
    {
        this.clubSubject = clubSubject;
    }

    public void setClubDescription(String clubDescription)
    {
        this.clubDescription = clubDescription;
    }

    public int getQueryNum()
    {
        return queryNum;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getBirthdate()
    {
        return birthdate;
    }

    public String getClubName()
    {
        return clubName;
    }

    public String getClubSubject()
    {
        return clubSubject;
    }

    public String getClubDescription()
    {
        return clubDescription;
    }
}
