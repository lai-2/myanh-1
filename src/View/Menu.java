package View;

import java.util.Scanner;
import Model.Customer;
import Controllers.Customers;
/**
 *
 * @author myanh
 */
public class Menu {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Customers custom = new Customers();
        String name = new String();
        int choice = 0;
        do {
            System.out.println("----------Menu----------");
            System.out.println(
                    "1. Register customers.\n"
                    + "2. Update customer information.\n"
                    + "3. Search for customer information by name.\n"
                    + "4. Display feast menus.\n"
                    + "5. Place a feast order.\n"
                    + "6. Update order information.\n"
                    + "7. Save data to file.\n"
                    + "8. Display Customer or Order lists.\n"
            );
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            if (choice < 1 || choice > 8) {
                System.out.println("Quit");
                break;
            }
            switch (choice) {
                case 1:
                    custom.registered(new Customer());
                    break;
                case 2:
                    custom.update();
                    break;
                case 3:
                    custom.searchByName(name);
                    break;
                case 4:
                    custom.readFromFileMenu();
                    break;
                case 5:
                    custom.placeAFeastOrder();
                    break;
                case 6:
                    System.out.print("Order ID   : ");
                    int orderId = sc.nextInt();
                    sc.nextLine();
                    custom.updateOrderInformation(orderId);
                    break;
                case 7:
                    custom.saveDataToFile();
                    break;
                case 8:
                    custom.displayData();
                    break;
                default:
                    System.out.println("Exiting the program...");
                    break;
            }
        } while (true);
        sc.close();
    }
}
