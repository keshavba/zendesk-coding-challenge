package request;

import org.json.JSONObject;

public class Ticket
{
    String subject;
    long submitterID;
    String status;

    //Creates a Ticket object from its subject, submmitter_id, and status
    public Ticket(JSONObject json)
    {
        try
        {
            subject = (String)json.get("subject");
            submitterID = (Long)json.get("submitter_id");
            status = (String)json.get("status");
        }
        catch(Exception e)
        {
            subject = "None";
            submitterID = 0;
            status = "new";
        }
    }

    //Displays the subject, submitter_id, and status of the ticket
    public String display()
    {
        return "Ticket with subject \"" + subject + "\" opened by " + submitterID + " has status \"" + status + "\"";
    }

    public String getSubject()
    {
        return subject;
    }
    public long getSubmitterId()
    {
        return submitterID;
    }
    public String getStatus()
    {
        return status;
    }
}
