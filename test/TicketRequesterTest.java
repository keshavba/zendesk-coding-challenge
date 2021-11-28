package test;

import request.TicketRequester;
import request.Ticket;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TicketRequesterTest
{
    @Test
    public void shouldReturnNullJSON()
    {
        TicketRequester req = new TicketRequester();
        JSONObject json = req.connectAPI("curl -H \"" + "\" https://.zendesk.com/api/v2/tickets.json");

        Assert.assertEquals(null, json);
    }
}
