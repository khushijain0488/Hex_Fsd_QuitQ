package ecom;

import ecom.config.AppConfig;
import ecom.controller.CartController;
import ecom.enums.UserMembership;
import ecom.model.CartItem;
import ecom.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        CartController cartController = context.getBean(CartController.class);
        Scanner sc = new Scanner(System.in);

        int choice = -1;

        while (choice != 0) {

            System.out.println("\n===== CART MENU =====");
            System.out.println("1. Add Item + User");
            System.out.println("2. Fetch All Items");
            System.out.println("3. Fetch Items by Username");
            System.out.println("4. Delete an Item");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter User Name: ");
                    String userName = sc.nextLine();

                    System.out.println("Select Membership: 1. Normal  2. Premium");
                    int mem = sc.nextInt();
                    sc.nextLine();
                    UserMembership membership = (mem == 1) ? UserMembership.Normal : UserMembership.Premium;

                    User user = new User(userId, userName, membership);

                    System.out.print("Enter Item ID: ");
                    int itemId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Item Name: ");
                    String itemName = sc.nextLine();

                    System.out.print("Enter Price: ");
                    BigDecimal price = sc.nextBigDecimal();
                    sc.nextLine();

                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();

                    CartItem item = new CartItem(itemId, itemName, price, qty, user);
                    cartController.addItemWithUser(user, item);
                    break;

                case 2:
                    cartController.fetchAllItems();
                    break;

                case 3:
                    System.out.print("Enter Username to search: ");
                    String name = sc.nextLine();
                    cartController.fetchItemsByUsername(name);
                    break;

                case 4:
                    System.out.print("Enter Item ID to delete: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();
                    cartController.deleteItem(deleteId);
                    break;

                case 0:
                    System.out.println("Exiting... Bye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter 0-4.");
            }
        }

        sc.close();
        context.close();
    }
}