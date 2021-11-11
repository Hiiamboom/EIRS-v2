import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketInterface extends Remote {
    public int getId() throws RemoteException;

    public String getStatus() throws RemoteException;

    public void setStatus(String _status) throws RemoteException;

    public LocalDateTime getDateIssued() throws RemoteException;

    public String getShortDescription() throws RemoteException;

    public int getPriority() throws RemoteException;

    public String getUserName() throws RemoteException;

    public String getPhoneNo() throws RemoteException;

    public String getType() throws RemoteException;

    public int addTicket(LocalDateTime _dateIssued, String _shortDescription, int _priority, String _userName,
            String _phoneNo, String _type) throws RemoteException;

    public Boolean removeTicket(int _id) throws RemoteException;

    public TicketInterface getTicketById(int _id) throws RemoteException;

    public List<TicketInterface> getAllTickets() throws RemoteException;

    public List<TicketInterface> getAllUserTickets(String _userName) throws RemoteException;
}
