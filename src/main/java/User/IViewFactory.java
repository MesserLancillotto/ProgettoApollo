package User;

public interface IViewFactory {
    ILoginView createLoginView();
    IConfiguratorView createConfiguratorView();
    IVoluntaryView createVoluntaryView();
    IBeneficiaryView createBeneficiaryView();
    IFirstAccessView createFirstAccessView(String type);
    IChangePswdView createChangePswdView();
    ISetBasicAppInfoView createSetBasicAppInfoView();
}
