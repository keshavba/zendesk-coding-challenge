package request;

import org.json.*;
import java.io.*;
import io.github.cdimascio.dotenv.*;
import java.util.Scanner;

public class TicketRequester
{   
    final int numPerPage = 25;
    String token;
    String subdomain;

    public TicketRequester()
    {
        Dotenv dotenv = Dotenv.configure()
                .directory("./assets")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        token = "Authorization: Bearer " + dotenv.get("FULL_TOKEN");            
        subdomain = dotenv.get("SUBDOMAIN");
    }

    //Fetches and prints out all the tickets
    public void getTickets()
    {
        String command = "curl -H \"" + token + "\" https://" + subdomain + ".zendesk.com/api/v2/tickets.json?per_page=" + numPerPage;
        JSONObject json = connectAPI(command);

        if(json == null)
        {
            System.out.println("~~ERROR: Unable to obtain tickets!");
            return;
        }

        int page = 1;
        String nextUrl = (String)json.get("next_page");

        Scanner input = new Scanner(System.in);

        if(nextUrl == null)
        {
            System.out.println("\nTotal tickets: " + json.get("count") + "\n");

            JSONArray tickets = (JSONArray)json.get("tickets");
            displayTicketsPerPage(page, tickets);
        }
        else
        {
            System.out.println("\nTotal tickets: " + json.get("count"));
            System.out.println("Press Enter to move to the next page or press any other key to exit!\n");

            while(nextUrl != null)
            {
                JSONArray tickets = (JSONArray)json.get("tickets");
                displayTicketsPerPage(page, tickets);

                String userInput = input.nextLine();
                while(true)
                {
                    if(!userInput.isEmpty())
                    {
                        return;
                    }

                    if(userInput.isEmpty())
                    {
                        page++;
                        String nextPage = "curl -H \"" + token + "\" " + nextUrl;
                        json = connectAPI(nextPage);

                        if(json == null)
                        {
                            System.out.println("~~ERROR: Unable to obtain tickets!");
                            return;
                        }

                        if(json.get("next_page") == JSONObject.NULL)
                            return;
                        else
                            nextUrl = (String)json.get("next_page");

                        break;
                    }

                    if(input.hasNextLine())
                    {
                        userInput = input.nextLine();
                    }
                }
            }
        }

        input.close();
        return;
    }

    //Fetches and prints out a ticket based on its ID
    public void getIndividualTicket(int ticketID)
    {
        String command = "curl -H \"" + token + "\" https://" + subdomain + ".zendesk.com/api/v2/tickets/" + ticketID + ".json";
        JSONObject json = connectAPI(command);

        if(json != null)
        {
            json = (JSONObject)json.get("ticket");

            System.out.println("\n");
            Ticket ticket = new Ticket(json);
            System.out.println(ticket.display());
        }
        else
        {
            System.out.println("\nERROR: Unable to obtain ticket!");
        }
    }

    //Gets the total number of tickets
    public int getNumTickets()
    {
        String command = "curl -H \"" + token + "\" https://" + subdomain + ".zendesk.com/api/v2/tickets.json";
        JSONObject json = connectAPI(command);

        if(json != null)
        {
            return (int)json.get("count");
        }
        else
        {
            return 0;
        }
    }

    //Displays the tickets for each page (helper method for getTickets)
    public void displayTicketsPerPage(int page, JSONArray tickets)
    {
        System.out.println("Page: " + page + "\n");

        for(int i = 0; i < tickets.length(); i++)
        {
            JSONObject currentTicketJSON = (JSONObject)tickets.get(i);
            
            Ticket ticket = new Ticket(currentTicketJSON);
            System.out.println(ticket.display());
        }
    }

    //Connects to the Zendesk API with the passed command argument
    public JSONObject connectAPI(String command)
    {
        JSONObject json = null;
        Process process = null;

        try
        {
            process = Runtime.getRuntime().exec(command);

            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            JSONTokener tokener = new JSONTokener(bufferedReader);
            json = new JSONObject(tokener);
        }
        catch(Exception e)
        {
            if(process != null)
            {
                process.destroy();
                if(process.exitValue() != 0)
                {
                    switch(process.exitValue())
                    {
                        case 401: System.out.printf("\nERROR: Couldn't authenticate you!");
                                  break;
                        case 404: System.out.printf("\nERROR: Couldn't find ticket(s)!");
                                  break;
                        case 400: System.out.printf("\nERROR: Invalid ticket number!");
                                  break;
                        default:  System.out.printf("\nERROR: Couldn't obtain ticket(s)!");
                                  break;
                    }
                }
            }
        }

        return json;
    }
}