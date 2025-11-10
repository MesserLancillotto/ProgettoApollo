package User;

import java.util.*;
import org.json.*;
import Client.Client;

public class ConfMenuVoluntaryMenu extends UserMenu
{
    private static final String MENU_TITLE = "MENU GESTIONE VOLONTARI";
    private static final String MSG_SHOW_VOLUNTARY_LIST = "Ecco l'elenco dei volontari iscritti:";
    private static final String ERROR_VOLUNTARY_LIST_EMPTY = "\nERRORE! Lista volontari vuota";

    private String organization;
    
    public void initialize_menu_selection ()
    {
        menuSelection.put(1, () -> view_voluntary_list());
    
        menuOptionList.add("Visualizza l'elenco volontari");
    }

    public ConfMenuVoluntaryMenu (String organization)
    {
        UserTui.printCenteredTitle(MENU_TITLE);
        this.organization = organization;
        initialize_menu_selection();
        UserTui.stampSeparator();
        manage_options(MENU_TITLE);  
    }

    public void view_voluntary_list()   
    {
        Set<String> voluntaryList =  get_voluntary_list();
        if (voluntaryList != null && !voluntaryList.isEmpty())
        {
            UserTui.stamp_list(MSG_SHOW_VOLUNTARY_LIST, voluntaryList);
        }
        else
            System.out.println (ERROR_VOLUNTARY_LIST_EMPTY);
    }

    

    // metodi per GESTIONE INTERNA

    private Set<String> get_voluntary_list()   
    {
        Set <String> voluntaryList = new HashSet<>();

        Client.getInstance().get_voluntaries(new HashMap<>());
        String getVoluntariesResponse = Client.getInstance().make_server_request();
        if (!getVoluntariesResponse.trim().isEmpty() && JSONObjectMethod.isValidJSONArray(getVoluntariesResponse))
        {
            JSONArray voluntariesArray = new JSONArray(getVoluntariesResponse);
            for (int i = 0; i < voluntariesArray.length(); i++)
            {
                JSONObject voluntary = voluntariesArray.getJSONObject(i);
                String voluntaryID = voluntary.getString("userName");
                voluntaryList.add(voluntaryID);
            }
            return voluntaryList;
        }
        else
            return null;
    }
}
