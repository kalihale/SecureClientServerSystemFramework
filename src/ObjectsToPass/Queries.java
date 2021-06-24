package ObjectsToPass;

public class Queries extends QueryObject
{
    String firstName;
    String lastName;
    String birthdate;
    String clubName;
    String clubSubject;
    String clubDescription;

    public Queries()
    {
        super(-1);
        firstName = "";
        lastName = "";
        birthdate = "";
        clubName = "";
        clubSubject = "";
        clubDescription = "";
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
