package test;

import request.Ticket;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TicketTest
{
    //Tests if an empty ticket object is made if the passed JSON is null
    @Test
    public void shouldMakeEmptyTicket()
    {
        Ticket ticket = new Ticket(null);
        Assert.assertEquals("None", ticket.getSubject());
        Assert.assertEquals(0, ticket.getSubmitterId());
        Assert.assertEquals("new", ticket.getStatus());
    }

    @Test
    public void shouldMakeRequestedTicket()
    {
        String subj = "What a subject!";
        String status = "open";
        long submitterId = 187278920;

        JSONObject json = new JSONObject();
        json.put("subject", subj);
        json.put("status", status);
        json.put("submitter_id", submitterId);

        Ticket ticket = new Ticket(json);

        Assert.assertEquals(subj, ticket.getSubject());
        Assert.assertEquals(submitterId, ticket.getSubmitterId());
        Assert.assertEquals(status, ticket.getStatus());
    }

    @Test
    public void shouldPrintTicketCorrectly()
    {
        String subj = "What a subject!";
        String status = "open";
        long submitterId = 187278920;

        String ticketDisplay = "Ticket with subject \"" + subj + "\" opened by " + submitterId + " has status \"" + status + "\"";

        JSONObject json = new JSONObject();
        json.put("subject", subj);
        json.put("status", status);
        json.put("submitter_id", submitterId);

        Ticket ticket = new Ticket(json);
        Assert.assertEquals(ticketDisplay, ticket.display());
    }
}
