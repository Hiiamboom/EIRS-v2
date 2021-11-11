import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class AdminClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Boolean loop = true;
        String status = "Pending";
        int ticketId = 0;

        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1234);
            TicketInterface iTicket = (TicketInterface) reg.lookup("ticket");
            System.out.println("Admin client is connected to server..\n");

            while (loop) {
                printSelectOption();
                if (scanner.hasNextInt()) {
                    switch (scanner.nextInt()) {
                    case 1:
                        showAllTickets(iTicket);
                        break;
                    case 2:
                        getTicketInformation(ticketId, scanner, iTicket);
                        break;
                    case 3:
                        setTicketStatus(ticketId, status, scanner, iTicket);
                        break;
                    default:
                        System.out.println("\nProgram exited..\n");
                        loop = false;
                        break;
                    }
                } else {
                    scanner.next();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void printSelectOption() {
        System.out.println("------------Welcome to Environmental Incident Report System------------\n");
        System.out.println("1. View Report Database");
        System.out.println("2. Get Report Information by ID");
        System.out.println("3. Set Report Status");
        System.out.println("4. Exit\n");
        System.out.print("Please select an option: ");
    }

    public static void showAllTickets(TicketInterface iTicket) {
        try {
            // System.out.println("\n" + String.format("%-15s %-15s %-15s %-15s %-15s %-15s
            // %-15s %n", "Ticket ID", "Status",
            // "Issued Date", "Type", "Name", "Phone", "Priority"));

            List<TicketInterface> tickets = iTicket.getAllTickets();
            printAllTickets(tickets);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void printAllTickets(List<TicketInterface> tickets) {
        try {
            for (TicketInterface ticket : tickets)
                printTicket(ticket);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void printTicket(TicketInterface ticket) {
        try {
            System.out.println("\nReport id: " + ticket.getId());
            System.out.println("Status: " + ticket.getStatus());
            System.out.println("Issued date: " + ticket.getDateIssued());
            System.out.println("Type: " + ticket.getType());
            System.out.println("User name: " + ticket.getUserName());
            System.out.println("Phone no: " + ticket.getPhoneNo());
            System.out.println("Priority: " + ticket.getPriority());
            System.out.println("Description: " + ticket.getShortDescription());
            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getTicketInformation(int ticketId, Scanner scanner, TicketInterface iTicket) {
        try {
            System.out.print("\nEnter Report ID: ");
            ticketId = scanner.nextInt();

            TicketInterface ticket = iTicket.getTicketById(ticketId);
            printTicket(ticket);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void setTicketStatus(int ticketId, String status, Scanner scanner, TicketInterface iTicket) {
        try {
            System.out.println("\nSetting Report status..");
            System.out.print("Enter Report ID: ");
            ticketId = scanner.nextInt();

            System.out.print("Enter status: ");
            status = scanner.next();

            TicketInterface ticket = iTicket.getTicketById(ticketId);
            ticket.setStatus(status);

            // if ticket exists
            System.out.println("\nReport status has been updated..\n");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
