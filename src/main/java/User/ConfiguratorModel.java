package User;

public class ConfiguratorModel extends UserModel
{
    public ConfiguratorModel
            (String username, String cityOfResidence, int yearOfBirth, UserType roleTitle, String organization, boolean pswdNeedsToBeChanged)
    {
        this.username = username;
        this.cityOfResidence = cityOfResidence;
        this.yearOfBirth = yearOfBirth;
        this.organization = organization;
        this.pswdNeedsToBeChanged = pswdNeedsToBeChanged;
        this.roleTitle = roleTitle;
    }

    public ConfiguratorModel (String userName, String roleTitle)
    {

    }

    public String getUserID ()
    {
        return name+" "+surname;
    }
}
