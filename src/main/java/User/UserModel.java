package User;

public abstract class UserModel
{
    String name;
    String surname;
    String username;
    String cityOfResidence;
    String organization;
    int yearOfBirth;
    boolean pswdNeedsToBeChanged;
    UserType roleTitle;

    boolean isFirstAccess;

    public void setUsername(String username)
    {
        this.username = username;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setSurname (String surname)
    {
        this.surname = surname;
    }
    public void setOrganization(String organization)
    {
        this.organization = organization;
    }
    public void setYearOfBirth(int yearOfBirth)
    {
        this.yearOfBirth = yearOfBirth;
    }
    public void setCityOfResidence(String cityOfResidence)
    {
        this.cityOfResidence = cityOfResidence;
    }

    public String  getUsername()
    {
        return username;
    }
    public String getSurname()
    {
        return surname;
    }
    public String getName()
    {
        return name;
    }
    public UserType getRoleTitle ()
    {
        return roleTitle;
    }
    public String getCityOfResidence ()
    {
        return cityOfResidence;
    }
    public String getOrganization ()
    {
        return organization;
    }
    public int getYearOfBirth ()
    {
        return yearOfBirth;
    }
    public boolean getPasswordNeedsToBeChanged ()
    {
        return pswdNeedsToBeChanged;
    }
    public boolean getIsFirstAccess () { return isFirstAccess; }

    public void setIsFirstAccess(boolean firstAccess)
    {
        isFirstAccess = firstAccess;
    }
    public boolean valuesAreValid ()
    {
        if (username == null || username.trim().isEmpty())
            return false;
        if (name == null || name.trim().isEmpty())
            return false;
        if (surname == null || surname.trim().isEmpty())
            return false;
        if (cityOfResidence == null || cityOfResidence.trim().isEmpty())
            return false;
        if (organization == null || organization.trim().isEmpty())
            return false;
        return true;
    }


}
