package User;

import java.io.*;
import java.util.*;

public class UserTui 
{
    static BufferedReader consoleIn = new BufferedReader (new InputStreamReader (System.in));

    public static String getPasswordFromUser (String thingToSayToUser)
    {
        stampSeparator();
        System.out.println("CAMBIO PASSWORD");
        stampSeparator();
        while (true)
        {
            try
            {
                System.out.println ("La password necessita di almeno una maiuscola e di un numero");
                System.out.printf ("\n%s: ", thingToSayToUser);
                String value = consoleIn.readLine();
                boolean passwIsSolid = passwordIsSolid(value);
                
                if (value != null && !value.isEmpty() && passwIsSolid)
                    return value.trim();

                System.out.println ("Valore inserito non valido, riprova!\n");
            }
            catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
    }

    private static boolean passwordIsSolid(String input) 
    {
        if (input == null || input.isEmpty()) 
        {
            return false;
        }
        
        boolean haMaiuscola = false;
        boolean haNumero = false;
        
        for (char c : input.toCharArray()) 
        {
            if (Character.isUpperCase(c)) 
            {
                haMaiuscola = true;
            }
            if (Character.isDigit(c)) 
            {
                haNumero = true;
            }
            
            // Ottimizzazione: esci appena trovi entrambi
            if (haMaiuscola && haNumero) 
            {
                return true;
            }
        }
        
        return haMaiuscola && haNumero;
    }

    public static String getString (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String value = consoleIn.readLine();
                
                if (value != null && !value.isEmpty())
                    return value.trim();
                
                System.out.println ("Valore inserito non valido, riprova!\n");
            }
           catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
    }

    //acquisizione array di stringhe
    public static ArrayList <String> getStringArray (String thingToSayToUser, String cycleConfirmation)
   {
        ArrayList<String> values = new ArrayList<>();
        boolean addMoreValues = true;
        
        while (addMoreValues)
        {
            try
            {
                System.out.printf("\n%s: ", thingToSayToUser);
                String value = consoleIn.readLine();
                
                if (value != null && !value.isEmpty())
                {
                    values.add(value);
                    addMoreValues = getYesNoAnswer(cycleConfirmation);
                }
                else
                {
                    System.out.println("Valore inserito non valido, riprova!\n");
                }
            }
           catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
            
        }

        return values;
    }

    //acquisizione lasciando gli spazi
    public static String getStringNoTrim (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String value = consoleIn.readLine();
                
                if (value != null && !value.isEmpty())
                    return value;
                else
                    System.out.println ("Valore inserito non valido, riprova!\n");
            }
            catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
    }

    public static String getStringNoTrimWithConfirm (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String value = consoleIn.readLine();
                
                if (value != null && !value.isEmpty())
                {
                    if (getYesNoAnswer("Hai inserito "+value+" confermi"))
                        return value;
                }
                else
                    System.out.println ("Valore inserito non valido, riprova!\n");
            }
            catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
    }

    //con un num. max di caratteri
    public static String getString (String thingToSayToUser, int maxCharacters)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s(Max: %d caratteri): ", thingToSayToUser, maxCharacters);
                String value = consoleIn.readLine();
                
                if (value != null && !value.isEmpty())
                    return value.trim();
                else if (value.length() > maxCharacters)
                    System.out.println ("\nStringa non valida, hai superato il numero massimo di caratteri consentiti");
                System.out.println ("\nValore inserito non valido, riprova!\n");
            }
            catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
    }

    public static float getFloat (String thingToSayToUser , float lowerBound, float upperBound)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String input = consoleIn.readLine();
                if (input == null || input.isEmpty())
                    System.out.println ("Errore, valore inserito non valido");
                else
                {
                    input = input.replace(",", "."); // gestisce il caso in cui l'utente usa la virgola come separatore decimale
                    float tmpValue = Float.parseFloat(input);
                    String confirmInput = "Hai inserito "+input+" confermi ";
                    if (getYesNoAnswer(confirmInput))
                    {
                        if (tmpValue >= lowerBound && tmpValue <= upperBound)
                            return tmpValue;
                        else
                            System.out.println ("Errore, valore inserito non valido");
                    }
                }
             } 
            catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
    }

    public static int getInteger (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String input = consoleIn.readLine();
                if (input == null || input.isEmpty())
                    System.out.println ("Errore, valore inserito non valido");
                else
                {
                    String confirmInput = "Hai inserito "+input+" confermi";
                    if (getYesNoAnswer(confirmInput))
                        return Integer.parseInt(input);
                }
             } 
            catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
    }

    public static int getIntegerNoCheck (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String input = consoleIn.readLine();
                if (input == null || input.isEmpty())
                    System.out.println ("Errore, valore inserito non valido");
                else
                    return Integer.parseInt(input);
                
             } 
            catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
    }

    //metodo di acquisizione int con con range di validità
    public static int getInteger (String thingToSayToUser, int lowerBound, int upperBound)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String input = consoleIn.readLine();
                int tmpValue = Integer.parseInt(input);

                if (input == null || input.isEmpty() || tmpValue <= lowerBound || tmpValue >= upperBound)
                    System.out.println ("Errore, valore inserito non valido");
                else
                    return tmpValue;
             } 
            catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
    }

    //metodo di acquisizione int con con range di validità
    public static int getInteger (String thingToSayToUser, String thingToSayToUserToConfirm, int lowerBound, int upperBound)
    {
        int loopCount = 0;
        while (true)
        {
            try
            {
                if (loopCount >= 3)
                {
                    System.out.println ("\nHai sbagliato troppe volte!");
                    return -1;
                }
                System.out.printf ("\n%s: ", thingToSayToUser);
                String input = consoleIn.readLine();
                int tmpValue = Integer.parseInt(input);

                if (input == null || input.isEmpty() || tmpValue <= lowerBound || tmpValue >= upperBound)
                    System.out.println ("Errore, valore inserito non valido");
                else
                {
                    StringBuilder confirmInput = new StringBuilder();
                    confirmInput.append(thingToSayToUserToConfirm);
                    confirmInput.append(input);
                    confirmInput.append(" confermi");
                    boolean isConfirmed = getYesNoAnswer(confirmInput.toString());
                    if (isConfirmed)
                        return tmpValue;
                }
                loopCount++;
             } 
            catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
    }

    public static boolean getYesNoAnswer (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s(SI/NO): ", thingToSayToUser);
                String answer = consoleIn.readLine();

                if (answer.toLowerCase().equals("si"))
                {
                    return true;
                }
                if (answer.toLowerCase().equals("no"))
                {
                    return false;
                }
                System.out.println ("\nErrore, valore inserito non valido");
            }
            catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
    }

    public static String getDateFromUser (String thingToSayToUser)
    {
        while (true)
        {
            try
            {
                System.out.printf ("\n%s: ", thingToSayToUser);
                String value = consoleIn.readLine();
                if (value != null && !value.isEmpty())
                    return value;

                System.out.println ("Valore inserito non valido, riprova!\n");
            }
            catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
        
    }

    public static void stamp_list (String title, Collection <String>listToStamp)
	{
		System.out.println ("\n"+title);
		for (String elementToStamp : listToStamp)
		{
			System.out.println (elementToStamp);
		}
	}

    public static void stamp_integer_list (String title, Collection <Integer>listToStamp)
	{
		System.out.println ("\n"+title);
        boolean firstElement = true;
		for (int elementToStamp : listToStamp)
		{
            if (firstElement)
			{
                System.out.printf ("%d", elementToStamp);
                firstElement = false;
            }
            else
                System.out.printf (", %d", elementToStamp);
		}
	}

    public static void stamp_list (String title, Map <String, Place> placeList)
    {
        System.out.println ("\n"+title);
        int cycleCount = 1;
        for (Map.Entry<String, Place> entry : placeList.entrySet())
        {
            Place place = entry.getValue();
            System.out.printf ("%d.A %s ci sono questi tipi di visite\n", cycleCount, place.getPlaceName());
            place.stampTypeVisit();
            cycleCount++;
        }
    }

    public static boolean operationIsSuccessful (boolean result)
    {
        if (result)
        {
            System.out.println ("\nL'operazione ha avuto successo\n");
            return true;
        }
        else
        {
            System.out.println ("\nERRORE! l'operazione non ha avuto successo\n");
            return false;
        }
    }

    public static HashMap <Integer, String> fromListToMap (Collection <String> listToConvert)
    {
        HashMap <Integer, String> convertedMap = new HashMap<>();
        int cycleCount = 1;
        for (String element : listToConvert)
        {
            convertedMap.put(cycleCount, element);
            cycleCount++;
        }

        return convertedMap;
    }

    public static String getChoiceFromMap (String thingToSayToUser, Map <Integer, String> choices)
    {
        int loopCount = 0;
        while (true)
        {
            try
            {
                if (loopCount >= 3)
                {
                    System.out.println ("\nHai sbagliato troppe volte!");
                    return "";
                }
                System.out.printf ("\n%s\n", thingToSayToUser);
                for (Map.Entry<Integer, String> entry : choices.entrySet())
                {
                    System.out.printf ("%d. %s\n", entry.getKey(), entry.getValue());
                }
                System.out.print("Inserisci il numero della tua scelta: ");
                String input = consoleIn.readLine();
                int chosenKey = Integer.parseInt(input);
                if (choices.containsKey(chosenKey))
                {
                    return choices.get(chosenKey);
                }
                else
                {
                    System.out.println ("\nErrore, valore inserito non valido");
                }
             } 
            catch (Exception ex)
            {
                System.out.println ("\nErrore durante la digitazione");
                System.out.println ("Riprova");
            }
        }
    }

    public static Set<String> getSetForComparison (Set<String> originalSet)
    {
        Set<String> returnSet = new HashSet<>();
        for (String element : originalSet)
        {
            returnSet.add(element.toUpperCase().trim());
        }
        return returnSet;
    }

    // METODI PER IMPAGINAZIONE

    public static void stampSeparator ()
    {
        System.out.println("\n==================================================\n");
    }

    public static void printExitMessage() 
    {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("CHIUSURA APPLICAZIONE");
        System.out.println("Grazie per aver utilizzato il nostro servizio!");
        System.out.println("Alla prossima! ");
        System.out.println("=".repeat(50));
    }

    public static void printWelcomeMessage() 
    {
        System.out.println("==========================================");
        System.out.println("|          BENVENUTO NELL'APP            |");
        System.out.println("==========================================");
        System.out.println("| L'applicazione è pronta per l'uso!     |");
        System.out.println("|                                        |");
        System.out.println("| Funzionalità disponibili:              |");
        System.out.println("| • Accesso                              |");
        System.out.println("| • Creazione nuovo account              |");
        System.out.println("|                                        |");
        System.out.println("==========================================");
    }

    public static void stampTitle (String title)
    {
        stampSeparator();
        System.out.println (title);
        stampSeparator();
    }
    
    public static void printCenteredTitle(String title) 
    {
        int totalWidth = 50;
        int padding = (totalWidth - title.length() - 2) / 2;
        
        String border = "✽".repeat(totalWidth);
        String paddingStr = " ".repeat(padding);
        
        System.out.println("\n" + border);
        System.out.println("✽" + paddingStr + " " + title + " " + paddingStr + "✽");
        System.out.println(border + "\n");
    }
    
    public static void stampEventInfo (int index, String eventName, String eventDescription, String eventCity, String eventAddress, String formattedDate, StateOfVisit visitState) 
    {
        // Mappa gli stati a icone e colori
        String stateIcon = UserTui.getStateIcon(visitState);
        System.out.println("\n");
        UserTui.stampSeparator();
        System.out.printf("🏷️  EVENTO #%d\n", index);
        System.out.printf("📌 %s\n", eventName);
        System.out.printf("📝 %s\n", eventDescription);
        System.out.printf("📍 %s - %s\n", eventCity, eventAddress);
        System.out.printf("📅 %s\n", formattedDate);
        System.out.printf("%s %s\n", stateIcon, UserTui.getStateDescription(visitState));
        UserTui.stampSeparator();
    }
    
    public static String getStateIcon(StateOfVisit state) 
    {
        return switch (state) {
            case PROPOSTA -> "⏳";
            case CONFERMATA -> "✅";
            case CANCELLATA -> "❌";
            case COMPLETA -> "📋";
            case EFFETTUATA -> "🎯";
            default -> "📄";
        };
    }

    public static String getStateColor(StateOfVisit state) 
    {
        // Per terminali che supportano colori ANSI
        return switch (state) 
        {
            case PROPOSTA -> "\u001B[33m"; // Giallo
            case CONFERMATA -> "\u001B[32m"; // Verde
            case CANCELLATA -> "\u001B[31m"; // Rosso
            case COMPLETA -> "\u001B[36m"; // Ciano
            case EFFETTUATA -> "\u001B[35m"; // Magenta
            default -> "\u001B[0m"; // Reset
        };
    }

    public static String getStateDescription(StateOfVisit state) 
    {
        return switch (state) 
        {
            case PROPOSTA -> "In attesa di conferma";
            case CONFERMATA -> "Confermata - In programma";
            case CANCELLATA -> "Cancellata";
            case COMPLETA -> "Prenotazione al completo";
            case EFFETTUATA -> "Effettuata";
            default -> "Stato sconosciuto";
        };
    }

    public static void stampAllEventInfo(
        String city,
        String address,
        String eventName,
        String eventDescription,
        String visitType,
        String meetingPoint,
        ArrayList<String> visitDays,
        ArrayList<Integer> startHours,
        ArrayList<Integer> duration,
        int startDate,
        int endDate,
        int minPartecipants,
        int maxPartecipants,
        int maxPeopleForSubscription,
        float price) 
    {
        System.out.println("=============================================");
        System.out.println("          RIEPILOGO DATI EVENTO");
        System.out.println("=============================================");
        System.out.println("NOME EVENTO: " + eventName);
        System.out.println("DESCRIZIONE: " + eventDescription);
        System.out.println("CITTA: " + city);
        System.out.println("INDRIZZO: " + address);
        System.out.println("TIPO DI VISITA: " + visitType);
        System.out.println("MEETING POINT: " + meetingPoint);
        System.out.println("PERIODO: dal " + DataManager.fromUnixToNormal(startDate) + " al " + DataManager.fromUnixToNormal(endDate));
        System.out.println("PARTECIPANTI: min " + minPartecipants + " - max " + maxPartecipants);
        System.out.println("MASSIMO ISCRIZIONI: " + maxPeopleForSubscription);
        System.out.println("PREZZO: " + (price > 0 ? price + " €" : "Gratuito"));
        
        System.out.println("\n--- GIORNI E ORARI DELLE VISITE ---");
        for (int i = 0; i < visitDays.size(); i++) 
        {
            String tmpHour = Integer.toString(startHours.get(i));
            StringBuilder hourToStamp = new StringBuilder();
            if (tmpHour.length() == 3)
            {
                hourToStamp.append(tmpHour.substring(0, 1));
                hourToStamp.append(":");
                hourToStamp.append(tmpHour.substring(1));
            }
            else if (tmpHour.length() == 4)
            {
                hourToStamp.append(tmpHour.substring(0, 2));
                hourToStamp.append(":");
                hourToStamp.append(tmpHour.substring(2));
            }
            else
                hourToStamp.append("ERRORE");
            System.out.println("Visita " + (i + 1) + ":");
            System.out.println("  • Giorno: " + visitDays.get(i));
            System.out.println("  • Orario inizio: " + hourToStamp.toString());
            System.out.println("  • Durata: " + duration.get(i) + " minuti");
        }
        
        System.out.println("=============================================");
    }
}

