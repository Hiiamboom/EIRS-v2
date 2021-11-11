import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class UserClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Boolean loop = true;
        String userName = "";
        String type = "";
        LocalDateTime currentDate = null;
        String phoneNo = "";
        String shortDescription = "";
        String status = "Pending";
        int priority = 0;
        int ticketId = 0;

        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1234);
            TicketInterface iTicket = (TicketInterface) reg.lookup("ticket");
            System.out.println("User client is connected to server..\n");

            while (loop) {
                printSelectOption();
                if (scanner.hasNextInt()) {
                    switch (scanner.nextInt()) {
                    case 1:
                        userAddsTicket(userName, type, currentDate, shortDescription, phoneNo, priority, scanner,
                                iTicket);
                        break;
                    case 2:
                        userRemovesTicket(ticketId, scanner, iTicket);
                        break;
                    case 3:
                        showAllUserTickets(userName, scanner, iTicket);
                        break;
                    case 4:
                        getTicketInformation(ticketId, scanner, iTicket);
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
        System.out.println("1. Create Incident Report");
        System.out.println("2. Delete Previous Incident Report");
        System.out.println("3. View All My Submitted Reports");
        System.out.println("4. Get My Report Information");
        System.out.println("5. Exit\n");
        System.out.print("Please select an option: ");
    }

    public static void userAddsTicket(String userName, String type, LocalDateTime currentDate, String shortDescription,
            String phoneNo, int priority, Scanner scanner, TicketInterface iTicket) {

        try {
            System.out.print("\nEnter User ID: ");
            userName = scanner.next();

            System.out.print("Enter report type (enquiry/incident): ");
            type = scanner.next();

            currentDate = LocalDateTime.now();

            System.out.print("Enter phone number: ");
            phoneNo = scanner.next();

            System.out.print("Enter the short description: ");
            shortDescription = scanner.next();

            System.out.print("Enter report priority 1-4 (4 being the most critical): ");
            priority = scanner.nextInt();
            scanner.nextLine();

            int newId = iTicket.addTicket(currentDate, shortDescription, priority, userName, phoneNo, type);
            System.out.println("\nReport #" + newId + " has been created..");
            System.out.println("Returning to main menu..\n");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void userRemovesTicket(int ticketId, Scanner scanner, TicketInterface iTicket) {
        System.out.print("Enter report ID to be removed: ");
        ticketId = scanner.nextInt();

        try {
            if (iTicket.removeTicket(ticketId)) {
                System.out.println("\nReport has been removed..");
                System.out.println("Returning to main menu..\n");
            } else {
                System.out.println("\nReport does not exist..");
                System.out.println("Returning to main menu..\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void showAllUserTickets(String userName, Scanner scanner, TicketInterface iTicket) {
        try {
            System.out.print("Enter ID: ");
            userName = scanner.next();

            // System.out.println("\n" + String.format("%-15s %-15s %-15s %-15s %-15s %-15s
            // %-15s %n", "Ticket ID", "Status",
            // "Issued Date", "Type", "Name", "Phone", "Priority"));

            List<TicketInterface> tickets = iTicket.getAllUserTickets(userName);
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
}