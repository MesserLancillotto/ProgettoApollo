package User;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class UserMenu 
{
    private static final String ERROR_INVALID_INPUT = "\nErrore valore inserito non valido\n";
    private static final String GET_DATA_MENU_DECISION = "Cosa vuoi fare (Inserisci il numero dell'opzione)";

    private static final int MIN_MENU_DECISION = 0;

    HashMap <Integer, Runnable> menuSelection = new HashMap <>();
    ArrayList <String> menuOptionList = new ArrayList<>();

    abstract void initialize_menu_selection ();
    
    public void visualize_options ()
	{
        int optionCount = 1;
        for (String options : menuOptionList)
        {
            System.out.println (optionCount+"."+options);
            optionCount++;
        }
        int userDecision;
        boolean validMenuDecision;
        do 
        {
            userDecision = UserTui.getInteger(GET_DATA_MENU_DECISION, MIN_MENU_DECISION, optionCount);
            validMenuDecision = menuSelection.containsKey(userDecision);
            if (!validMenuDecision)
                System.out.println (ERROR_INVALID_INPUT);
        }while (!validMenuDecision);
        menuSelection.get(userDecision).run();
	}
    
    public void manage_options (String whichMenu)
	{
        boolean keepUsingConfiguratorMenu;
        do
        {
            UserTui.stampSeparator();
            visualize_options();
            StringBuilder msgKeepUsingMenu  = new StringBuilder();
            msgKeepUsingMenu.append("\nVuoi fare altro nel ");
            msgKeepUsingMenu.append(whichMenu);
            msgKeepUsingMenu.append(" ");
            keepUsingConfiguratorMenu = UserTui.getYesNoAnswer(msgKeepUsingMenu.toString());
            UserTui.stampSeparator();
        }while (keepUsingConfiguratorMenu);
	}

}
