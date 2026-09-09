package User;

public class ViewFactory implements IViewFactory {
    private static IViewFactory instance = new ViewFactory();

    public static IViewFactory getInstance() {
        return instance;
    }

    public static void setInstance(IViewFactory factory) {
        instance = factory;
    }

    @Override
    public ILoginView createLoginView() {
        return new UserLoginView();
    }

    @Override
    public IConfiguratorView createConfiguratorView() {
        return new ConfiguratorView();
    }

    @Override
    public IVoluntaryView createVoluntaryView() {
        return new VoluntaryView();
    }

    @Override
    public IBeneficiaryView createBeneficiaryView() {
        return new BeneficiaryView();
    }

    @Override
    public IFirstAccessView createFirstAccessView(String type) {
        return new FirstAccessView(type);
    }

    @Override
    public IChangePswdView createChangePswdView() {
        return new ChangePswdView();
    }

    @Override
    public ISetBasicAppInfoView createSetBasicAppInfoView() {
        return new SetBasicAppInfoView();
    }
}
