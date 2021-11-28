import request.*;
import io.github.cdimascio.dotenv.*;

public class Main
{
    public static void main(String[] args)
    {
        Dotenv dotenv = Dotenv.configure()
                .directory("./assets")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        if(dotenv.get("FULL_TOKEN") == null || dotenv.get("FULL_TOKEN").isEmpty() || dotenv.get("SUBDOMAIN") == null || dotenv.get("SUBDOMAIN").isEmpty() )
        {
            System.out.println("\nPlease make sure to add your OAuth access token and subdomain to the .env file in order to use the ticket viewer!");
            return;
        }

        TicketRequester requester = new TicketRequester();
        Display display = new Display();

        display.mainDriver(requester);
    }
}
