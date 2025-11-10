package User;

import Client.Client;
import org.json.*;

public class ConfMenuGenericOptions extends UserMenu
{
    private static final String MENU_TITLE = "MENU CONFIGURATORE: OPZIONI GENERICHE";
    private static final String GET_DATA_MAX_NUMBER_PER_SUBSCRIPTION = "\nDefinire il nuovo numero massimo di persone che un fruitore può iscrivere in una volta sola";
    private static final String GET_DATA_CLOSED_DAY = "Inserire il giorno precluso alle visite";
    private static final String GET_DATA_CLOSED_DAY_DURATION = "Di quanti giorni è la chiusura";
    private static final String GET_DATA_CLOSED_DAY_LOOP = "Vuoi inserire un'altra data preclusa alle visite";
    private String organization;

    public void initialize_menu_selection ()
    {
        menuSelection.put(1, () -> modify_max_number_per_subscription());
        menuSelection.put(2, () -> manage_disponibilty_dates());  

        menuOptionList.add("Modifica il numero massimo di persone iscrivibili a un'iniziativa da parte di un fruitore");
        menuOptionList.add("Segna date precluse alle visite");
    }
    
    public ConfMenuGenericOptions (String organization)
    {
        UserTui.printCenteredTitle(MENU_TITLE);        //titolo temporaneo
        this.organization = organization;
        initialize_menu_selection();
        UserTui.stampSeparator();
        manage_options(MENU_TITLE);  
    }

    public void modify_max_number_per_subscription()
    {
        int newMaxNumber = UserTui.getInteger(GET_DATA_MAX_NUMBER_PER_SUBSCRIPTION, 0, 100);
        JSONObjectCreator.setMaxPeopleForSubscription(newMaxNumber);
    }

     public void manage_disponibilty_dates ()
    {
        boolean addAnotherDate;
        DataManagerDisponibility date = new DataManagerDisponibility(3);
        do
        {
            int unaviableDay = date.getReferenceDay(GET_DATA_CLOSED_DAY, GET_DATA_CLOSED_DAY_DURATION);
            if (unaviableDay > 0)
            {
                int unixDate = date.getUnixDate(unaviableDay);
                Client.getInstance().set_closed_days(unixDate, date.getEndDayOfClosure(), organization);  // definisci ASSOCIAZIONE
                String closedDaysReply = Client.getInstance().make_server_request();
                if (!closedDaysReply.trim().isEmpty() && JSONObjectMethod.isValidJSONObject(closedDaysReply))
                {
                    JSONObject dictionary = new JSONObject(closedDaysReply);
                    UserTui.operationIsSuccessful(dictionary.getBoolean("querySuccesful"));
                }
            }
            addAnotherDate = UserTui.getYesNoAnswer(GET_DATA_CLOSED_DAY_LOOP);
        }while (addAnotherDate);
    }

}
