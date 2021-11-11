import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.*;

public class Ticket extends UnicastRemoteObject implements TicketInterface, Serializable {
    private int id;
    private LocalDateTime dateIssued;
    private String status;
    private String shortDescription;
    private int priority;
    private String userName;
    private String phoneNo;
    private String type;
    // photo?
    // location?

    private List<TicketInterface> tickets;

    public Ticket() throws RemoteException {
        tickets = new ArrayList<TicketInterface>();
        addTicket(LocalDateTime.now(), "Pothole at Jalan Tutong", 3, "Syazwan", "7150000", "Enquiry");
        addTicket(LocalDateTime.now(), "Landslide at Jalan Kilanas", 4, "Ghas", "7151111", "Enquiry");
        addTicket(LocalDateTime.now(), "OH NO, OUR FIRE HYDRANT, ITS BROKEN.", 4, "Waiz", "7152222", "Enquiry");
    }

    public Ticket(int _id, LocalDateTime _dateIssued, String _shortDescription, int _priority, String _userName,
            String _phoneNo, String _type) throws RemoteException {
        id = _id;
        dateIssued = _dateIssued;
        status = "PENDING";
        shortDescription = _shortDescription;
        priority = _priority;
        userName = _userName;
        phoneNo = _phoneNo;
        type = _type;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String _status) {
        status = _status;
    }

    public LocalDateTime getDateIssued() {
        return dateIssued;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public int getPriority() {
        return priority;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getType() {
        return type;
    }

    public int addTicket(LocalDateTime _dateIssued, String _shortDescription, int _priority, String _userName,
            String _phoneNo, String _type) throws RemoteException {
        int newId = 0;
        if (tickets.size() == 0)
            newId = 1;
        else
            newId = tickets.size() + 1;

        TicketInterface newTicket = new Ticket(newId, _dateIssued, _shortDescription, _priority, _userName, _phoneNo,
                _type);
        tickets.add(newTicket);
        return newId;
    }

    public Boolean removeTicket(int _id) throws RemoteException {
        Boolean found = false;
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getId() == _id) {
                tickets.remove(i);
                found = true;
            }
        }
        return found;
    }

    public TicketInterface getTicketById(int _id) throws RemoteException {
        for (TicketInterface ticket : tickets) {
            if (ticket.getId() == _id)
                return ticket;
        }
        return new Ticket();
    }

    public List<TicketInterface> getAllTickets() throws RemoteException {
        return tickets;
    }

    public List<TicketInterface> getAllUserTickets(String _userName) throws RemoteException {
        List<TicketInterface> userTickets = new ArrayList<TicketInterface>();
        for (TicketInterface ticket : tickets) {
            if (ticket.getUserName().equals(_userName)) {
                userTickets.add(ticket);
            }
        }
        if (userTickets.size() != 0)
            return userTickets;
        else
            return Collections.emptyList();
    }
}
