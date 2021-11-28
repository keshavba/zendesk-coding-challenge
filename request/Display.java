package request;

import java.util.Scanner;

public class Display
{
    Scanner scanner = new Scanner(System.in);
    
    //Driver for ticket viewer CLI
    public void mainDriver(TicketRequester requester)
    {
        String userInput;

        System.out.println("\n~~Welcome to the Zendesk API ticket viewer!\n");
        System.out.println("~~Enter \"menu\" to show options or \"quit\" to exit the ticket viewer");
        userInput = scanner.nextLine();

        if(userInput.toLowerCase().equals("quit"))
        {
            System.out.println("\n~~Thank you for using the ticket viewer!\n");
            return;
        }
        else if(userInput.toLowerCase().equals("menu"))
        {
            menuDriver(requester);
        }
        else
        {
            System.out.println("\n~~ERROR: Invalid command!");
            menuDriver(requester);
        }
    }

    //CLI driver that shows the menu and gets tickets based on user input
    public void menuDriver(TicketRequester requester)
    {
        String userInput;

        while(true)
        {
            userInput = showMenu();

            if(userInput.toLowerCase().equals("quit"))
            {
                System.out.println("\n~~Thank you for using the ticket viewer! See you!\n");
                return;
            }
            else if(userInput.toLowerCase().equals("1"))
            {
                System.out.println("\n~~All tickets:");
                requester.getTickets();
            }
            else if(userInput.toLowerCase().equals("2"))
            {
                int numTickets = requester.getNumTickets();
                if(numTickets != 0)
                    System.out.println("\n~~Enter ticket number (number of tickets - " + numTickets + "): ");
                
                userInput = scanner.nextLine();

                try
                {
                    int ticketId = Integer.parseInt(userInput);
                    requester.getIndividualTicket(ticketId);
                }
                catch(Exception e)
                {
                    System.out.println("\n~~ERROR: Invalid ticket number!");
                }
            }
            else
            {
                System.out.println("\n~~ERROR: Invalid command!");
            }
        }
    }

    //Prints out the menu for the CLI
    public String showMenu() 
    {
        System.out.println("\n\tSelect from following options:\n\t- Press 1 to view all tickets\n\t- Press 2 to view a ticket\n\t- Enter \"quit\" to exit the ticket viewer");
        return scanner.nextLine();
    }
}
