package User.Configurator;

public class ConfiguratorGUI extends AbstractConfiguratorGUI {

    public ConfiguratorGUI() {
        configMap.put("btn_0", new ActionConfig("Elimina posto", "Elimina il posto selezionato", this::foo_0));
        configMap.put("btn_1", new ActionConfig("Elimina tipo di visita", "Elimina il tipo di visita selezionato da un posto specifico", this::foo_1));
        configMap.put("btn_2", new ActionConfig("Elimina utente", "Elimina l'utente selezionato dal pool di volontari", this::foo_2));
        configMap.put("btn_3", new ActionConfig("Modifica posti visitabili", "Modifica le informazioni del posto selezionato", this::foo_3));
    }

    private void foo_0() {
        System.out.println("Esecuzione foo_0()");
    }

    private void foo_1() {
        System.out.println("Esecuzione foo_1()");
    }

    private void foo_2() {
        System.out.println("Esecuzione foo_2()");
    }

    private void foo_3() {
        System.out.println("Esecuzione foo_3()");
    }
}