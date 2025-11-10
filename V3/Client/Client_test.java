package Client;

import java.util.*;

public class Client_test
{
    public static void main(String [] args)
    {
        // Client client = new Client("CONFIGURATOR.Carlo.Goldoni.97", "samuele");
        
        // ORGANIZZAZIONE DIVERSA
        // Map<String, Object> fields = new HashMap<String, Object>();
        // fields.put("state", "CONFIRMED"); 
        // client.edit_event("Le quattro stagioni al parco", fields);

        // TUTTO CORRETTO
        // Map<String, Object> fields = new HashMap<String, Object>();
        // fields.put("state", "CONFIRMED"); 
        // client.edit_event("Cinema in Castello: la corazzata Potiomkin", fields);

        // client.edit_password("BaruffeChiozzotte");

        // get_event
        // Map<String, Object> filters = new HashMap<String, Object>();
        // filters.put("city", "Bergamo");
        // client.get_event(filters);

        // client.get_user_data("VOLUNTARY.Arlecchino.Valcalepio.81");

        // client.get_user_data("CONFIGURATOR.Carlo.Goldoni.97");

        // CON IL NOME
        // client.get_voluntaries_for_visit("Cinema in Castello: la corazzata Potiomkin", null);

        // CON LA CITTA'
        // Map<String, Object> filter = new HashMap<String, Object>();
        // filter.put("city", "Bergamo");
        // client.get_voluntaries_for_visit(null, filter);

        // GIORNI CHIUSURA ORGANIZZAZIONE INESISTENTE
        // client.set_closed_days(0, 1, "San Carlo Acutis");

        // GIORNI CHIUSURA ORGANIZZAZIONE ALTRUI
        // client.set_closed_days(0, 1, "Santa Cecilia");

        // GIORNI CHIUSURA ORGANIZZAZIONE PROPRIA
        // client.set_closed_days(0, 10, "San Genesio");

        // NUOVO EVENTO ORGANIZZAZIONE NON PROPRIA
        // client.set_new_event("Evento truffaldino", "Partecipa, non ti rubiamo i schei!", "Bologna", "Via i tuoi schei", 10, 100, "San Carlo Acutis", 10, 100, 20, "Rubatina generale");
        
        // NUOVO EVENTO TERRIRORIO NON DI COMPETENZA
        // client.set_new_event("Evento su Ulisse", "Anche tu ti sei perso?", "Ivrea", "Via Tomtom", 10, 100, "San Genesio", 10, 100, 20, "Cartografia 101");
        
        // NUOVO EVENTO GIORNO DI CHIUSURA
        // client.set_new_event("Spettacolo il 31 dicembre", "Magari avete di meglio da fare ...", "Bergamo", "Scantinato di Michele", 1766534350, 1767743840, "San Genesio", 10, 100, 20, "Organizzazione degli appuntamenti 101");
        
        // TUTTO GIUSTO
        // client.set_new_event("Il tartufo di Molière", "Battute sulla mia ex a parte ...", "Bergamo", "Teatro dei poveretti", 1767744000, 1767745000 , "San Genesio", 10, 100, 20, "Spettacolo teatrale");
        
        // CON UTENTE GIA' IMPEGNATO
        // ArrayList<String> territoriesOfCompetence = new ArrayList<String>();
        // territoriesOfCompetence.add("Torino");
        // territoriesOfCompetence.add("Ivrea");
        // territoriesOfCompetence.add("Alessandria");
    
        // CON NUOVO UTENTE
        // Client client = new Client("CONFIGURATOR.Michelangelo.Merisi.71", "maddalena");
        // ArrayList<String> territoriesOfCompetence = new ArrayList<String>();
        // territoriesOfCompetence.add("Torino");
        // territoriesOfCompetence.add("Ivrea");
        // territoriesOfCompetence.add("Alessandria");
        // client.set_new_organization("San Luca", territoriesOfCompetence);

        // System.out.println(client.make_server_request());

    }
}