package User;

public class BeneficiaryModel extends UserModel
{
    public  BeneficiaryModel (boolean isFirstAccess)
    {
        this.isFirstAccess = isFirstAccess;
    }

    public BeneficiaryModel
            (String username, String cityOfResidence, int yearOfBirth, UserType roleTitle, String organization, boolean pswdNeedsToBeChanged)
    {
        this.username = username;
        this.cityOfResidence = cityOfResidence;
        this.yearOfBirth = yearOfBirth;
        this.organization = organization;
        this.pswdNeedsToBeChanged = pswdNeedsToBeChanged;
        this.roleTitle = roleTitle;
    }

}
