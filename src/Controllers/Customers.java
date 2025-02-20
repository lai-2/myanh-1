/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Tools.Acceptable;
import Tools.Inputter;
import Model.Customer;
import Model.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author myanh
 */
public class Customers {

    private ArrayList<Customer> customlist;
    private Inputter input;
    private final String HEADER_TABLE
            = "--------------------------------------------------------------------------------\n"
            + "   Code      |   Customer Name        |   Phone      |    Email     \n"
            + "--------------------------------------------------------------------------------\n";
    private final String FOOTER_TABLE
            = "--------------------------------------------------------------------------------\n";
    private String pathFile = "src/customers.dat";
    private String orderFile = "src/feast_order_service.dat";
    private String fileName = "C:\\Ky 3\\LAB211\\Set13_SP25\\FeastMenu.csv";
    private boolean isSaved;
    Scanner scanner = new Scanner(System.in);
    private int orderIdCounter = 1;
    private boolean isUpdating;
    private List<Order> orderList = new ArrayList<>();
    private final String ORDER_TABLE
            = "----------------------------------------------------------------------------------------------------------------------------\n"
            + "   ID      |   Event date       |   Customer ID      |    Set menu      |     Price       |     Tables       |     Cost     \n"
            + "----------------------------------------------------------------------------------------------------------------------------\n";

    public Customers() {
        this.input = new Inputter();
        this.customlist = new ArrayList<>();
    }

    public boolean isSaved() {
        return this.isSaved;
    }

    public void registered(Customer x) {
        String id;
        do {
            id = input.inputAndLoop("Enter customer id (C,G,K + 4 numbers): ", Acceptable.CUSTOMER_VALID);

            boolean isDuplicate = false;
            for (Customer custom : customlist) {
                if (custom.getid().equalsIgnoreCase(id)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate) {
                System.out.println("Error: The ID \"" + id + "\" already exists. Please enter a unique ID.");
            } else {
                break;
            }
        } while (true);
        String name = input.inputAndLoop("Enter name: ", Acceptable.NAME_VALID);
        String phone = getUniquePhone();
        String email = getUniqueEmail();
        x.setid(id);
        x.setName(name);
        x.setPhone(phone);
        x.setEmail(email);
        customlist.add(x);
        System.out.println("Customer registered successfully!");
        this.isSaved = false;
    }

    private String getUniquePhone() {
        String phone;
        do {
            phone = input.inputAndLoop("Enter phone number: ", Acceptable.PHONE_VALID);

            boolean isDuplicate = false;
            for (Customer customer : customlist) {
                if (customer.getPhone().equalsIgnoreCase(phone)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate) {
                System.out.println("The phone \"" + phone + "\" already exists. Please enter a unique phone");
            } else {
                break;
            }
        } while (true);
        return phone;
    }

    private String getUniqueEmail() {
        String email;
        do {
            email = input.inputAndLoop("Enter email: ", Acceptable.EMAIL_VALID);

            boolean isDuplicate = false;
            for (Customer customer : customlist) {
                if (customer.getEmail().equalsIgnoreCase(email)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate) {
                System.out.println("The email \"" + email + "\" already exists. Please enter a unique email.");
            } else {
                break;
            }
        } while (true);
        return email;
    }

    public Customer getCustom(String code) {
        if (code.trim().isEmpty()) {
            return null;
        }
        for (Customer custom : customlist) {
            if (custom.getid().equalsIgnoreCase(code)) {
                return custom;
            }
        }
        return null;
    }

    private boolean isPhoneDuplicate(String phone) {
        for (Customer custom : customlist) {
            if (custom.getPhone().equalsIgnoreCase(phone)) {
                return true; // Số điện thoại đã tồn tại
            }
        }
        return false; // Số điện thoại chưa tồn tại
    }

    private boolean isEmailDuplicate(String email) {
        for (Customer custom : customlist) {
            if (custom.getEmail().equalsIgnoreCase(email)) {
                return true; // Số điện thoại đã tồn tại
            }
        }
        return false; // Số điện thoại chưa tồn tại
    }

    public void update() {
        isUpdating = true;
        if (customlist.isEmpty()) {
            System.out.println("No customer registered!");
            return;
        }
        String id;
        Customer customToUpdate;
        do {
            id = input.inputAndLoop("Enter customer id to update (C,G,K + 4 numbers): ", Acceptable.CUSTOMER_VALID);
            customToUpdate = getCustom(id);

            if (customToUpdate == null) {
                System.out.println("Customer ID does not exist. Please enter a valid ID.");
            }
        } while (customToUpdate == null);
        System.out.println("Do you want to update?(Y/N): ");
        while (true) {
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("y")) {
                break;
            } else if (choice.equalsIgnoreCase("N")) {
                System.out.println("Update canceled");
                return;
            } else {
                System.out.print("Please enter again Y or N: ");
            }
        }
        while (isUpdating) {
            System.out.print("Please select update: \n");
            System.out.print("1. Update name\n"
                    + "2. Update phone\n"
                    + "3. Update email\n"
                    + "4. Finish\n");
            System.out.print("Enter select: ");
            int select;
            try {
                select = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number between 1-4.");
                continue;
            }
            switch (select) {
                case 1:
                    String name = input.inputAndLoop("Enter updated name: ", Acceptable.NAME_VALID);
                    if (!name.isEmpty()) {
                        customToUpdate.setName(name);
                    }
                    break;
                case 2:
                    String phone = input.inputAndLoop("Enter updated phone number: ", Acceptable.PHONE_VALID);
                    if (!phone.isEmpty() && !isPhoneDuplicate(phone)) {
                        customToUpdate.setPhone(phone);
                    } else {
                        System.out.println("Phone number already exists or invalid input.");
                    }
                    break;
                case 3:
                    String email = input.inputAndLoop("Enter updated email: ", Acceptable.EMAIL_VALID);
                    if (!email.isEmpty() && !isEmailDuplicate(email)) {
                        customToUpdate.setEmail(email);
                    } else {
                        System.out.println("Email already exists or invalid input.");
                    }
                    break;
                case 4:
                    System.out.println("Update completed.");
                    isUpdating = false;
                    break;
                default:
                    System.out.println("Enter again select 1-4: ");
                    break;
            }
        }
    }

    public void searchByName(String name) {
        String Name;
        // Lặp cho đến khi người dùng nhập vào tên hợp lệ
        while (true) {
            System.out.println("Enter name: ");
            Name = scanner.nextLine().toLowerCase();
            // Nếu người dùng nhập chuỗi rỗng, yêu cầu nhập lại
            if (Name.isEmpty()) {
                System.out.println("You must enter a name to search. Please try again.");
            } else {
                break; // Thoát khỏi vòng lặp khi nhập tên hợp lệ
            }
        }
        if (customlist.isEmpty()) {
            System.out.println("No customer have registered yet.");
            return;
        }
        List<Customer> matchcus = new ArrayList<>();
        for (Customer custom : customlist) {
            if (custom.getName().toLowerCase().contains(Name)) {
                matchcus.add(custom);
            }
        }
        if (matchcus.isEmpty()) {
            System.out.println("No one matches the search criteria!");
        } else {
            System.out.println(HEADER_TABLE);
            for (Customer cus : matchcus) {
                System.out.println(cus);
            }
            System.out.println(FOOTER_TABLE);
        }
    }

    public List<String[]> readMenuFromFile() {
        List<String[]> menuList = new ArrayList<>();
        File file = new File(fileName);
        // Đọc dữ liệu từ file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // Bỏ qua dòng tiêu đề
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", 4);
                if (data.length == 4) {
                    menuList.add(data);
                }
            }
        } catch (IOException e) {
        }
        return menuList;
    }

    public void readFromFileMenu() {
        List<String[]> menuList = readMenuFromFile();
        if (menuList.isEmpty()) {
            System.out.println("No menu data found.");
            return;
        }
        // Sắp xếp Bubble Sort
        for (int i = 0; i < menuList.size() - 1; i++) {
            for (int j = i + 1; j < menuList.size(); j++) {
                double price1 = Double.parseDouble(menuList.get(i)[2].trim());
                double price2 = Double.parseDouble(menuList.get(j)[2].trim());
                if (price1 > price2) {
                    String[] temp = menuList.get(i);
                    menuList.set(i, menuList.get(j));
                    menuList.set(j, temp);
                }
            }
        }
        System.out.println("------------------------------------------------");
        System.out.println("     List of Set Menus for ordering party:      ");
        System.out.println("------------------------------------------------");
        for (String[] item : menuList) {
            System.out.println("Code       : " + item[0]);
            System.out.println("Name       : " + item[1]);
            System.out.println("Price      : " + item[2] + " Vnd");
            System.out.println("Ingredients:");
            // Loại bỏ dấu ngoặc kép trước khi in ra
            String ingredients = item[3].replace("\"", "");
            System.out.println(ingredients.replace("#", "\n"));
            System.out.println("----------------------------------------------------------------------");
        }
    }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print("Event date                   : ");
            String date = scanner.nextLine().trim();
            try {
                LocalDate eventDate = LocalDate.parse(date, formatter);
                if (eventDate.isAfter(LocalDate.now())) { // Kiểm tra ngày có ở tương lai không
                    return date;
                } else {
                    System.out.println("Date must be in the future.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Please use dd/MM/yyyy.");
            }
        }
    }

    public int getNumberOfTable() {
        while (true) {
            System.out.print("Number of tables             : ");
            try {
                int tables = Integer.parseInt(scanner.nextLine().trim());
                if (tables > 0) {
                    return tables;
                }
                System.out.println("Invalid! Number must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private int generateOrderId() {
        return orderIdCounter;
    }

    private int getValidOrderId(String customerId, String setMenuCode, String eventDate) {
        if (isDuplicateOrder(customerId, setMenuCode, eventDate) >= 0) {
            System.out.println("Duplicate order detected! Order ID will not change.");
            return orderIdCounter; // Không tăng nếu bị trùng
        }
        return orderIdCounter++; // Chỉ tăng nếu không bị trùng
    }

    private int isDuplicateOrder(String customerId, String setMenuCode, String eventDate) {
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            if (order.getCustomerId().equalsIgnoreCase(customerId)
                    && order.getMenuCode().equalsIgnoreCase(setMenuCode)
                    && order.getEventDate().equals(eventDate)) {
                return i; // ✅ Trả về vị trí của đơn hàng trùng lặp
            }
        }
        return -1; // ✅ Không tìm thấy => trả về -1
    }

    public void placeAFeastOrder() {
        int orderId = generateOrderId(); // Tạo Order ID tự động (số tăng dần)
        String id = input.inputAndLoop("Code(C-K-G): ", Acceptable.CUSTOMER_VALID);
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Invalid Customer ID");
        }
        Customer customUpdate = getCustom(id);
        if (customUpdate == null) {
            System.out.println("No one matches the search criteria!");
            return;
        }
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Customer order information [Order ID: " + orderId + "]");
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Code         : " + customUpdate.getid());
        System.out.println("Customer name: " + customUpdate.getName());
        System.out.println("Phone        : " + customUpdate.getPhone());
        System.out.println("Email        : " + customUpdate.getEmail());
        System.out.println("--------------------------------------------------------------------");
        List<String[]> menuList = readMenuFromFile();
        // Nhập mã Set Menu cần tìm
        System.out.print("Code of Set Menu (PW001-PW006)       : ");
        String inputCode = scanner.nextLine().trim();
        boolean found = false;
        for (String[] item : menuList) {
            if (item[0].equalsIgnoreCase(inputCode)) { // So sánh mã món ăn
                found = true;
                String menuName = item[1];
                String priceStr = item[2];
                double price = 0;
                System.out.println("Set menu name                : " + menuName);
                String eventDate = getDate();
                orderId = getValidOrderId(id, menuName, eventDate);
                int tables = 0;
                double totalCost = 0;
                int index = isDuplicateOrder(id, menuName, eventDate);
                if (index > -1) {
                    System.out.println("❌ Duplicate data! The same customer has already ordered this menu on the same date.");
                    return;
                }else{
                tables = getNumberOfTable(); // Nhập số bàn một lần và sử dụng lại
                System.out.println("Price                        : " + priceStr + " Vnd");
                System.out.println("Ingredients                  : ");
                // Loại bỏ dấu ngoặc kép trước khi in ra
                String ingredients = item[3].replace("\"", "");
                System.out.println(ingredients.replace("#", "\n")); // Hiển thị đúng định dạng
                System.out.println("--------------------------------------------------------------------");
                try {
                    price = Double.parseDouble(item[2].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format in file.");
                    return;
                }
                totalCost = price * tables;
                String formattedTotalCost = NumberFormat.getInstance(Locale.US).format(totalCost);
                System.out.println("Total cost: " + formattedTotalCost + " VND");
                System.out.println("--------------------------------------------------------------------");
                Order newOrder = new Order(orderId, id, inputCode, price, tables, totalCost, eventDate);
                orderList.add(newOrder);
                System.out.println("Order saved successfully!");
                }
            }
        }
        if (!found) {
            System.out.println("No menu found with code: " + inputCode);
        }
    }

    public Order findOrderById(int orderId) {
        for (Order order : orderList) {
            if (order.getOrderId() == orderId) {
                return order; // Tìm thấy, trả về đơn hàng
            }
        }
        return null; // Không tìm thấy
    }

    public boolean isMenuCodeValid(String code) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Menu file not found!");
            return false;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0 && data[0].equalsIgnoreCase(code)) {
                    return true; // Tìm thấy mã hợp lệ
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading menu file: " + e.getMessage());
        }
        return false;
    }

    public void updateOrderInformation(int orderId) {
        isUpdating = true;
        if (orderList.isEmpty()) {
            System.out.println("No order found");
            return;
        }
        Order order = findOrderById(orderId);
        if (order == null) {
            System.out.println("This Order does not exist.");
            return;
        }
        System.out.println("Do you want to update order?(Y/N): ");
        while (true) {
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("y")) {
                break;
            } else if (choice.equalsIgnoreCase("N")) {
                System.out.println("Update canceled");
                return;
            } else {
                System.out.print("Please enter again Y or N: ");
            }
        }
        while (isUpdating) {
            System.out.print("Please select update: \n");
            System.out.print("1. Code of set menu\n"
                    + "2. Number of tables\n"
                    + "3. Preferred event date\n"
                    + "4. Finish\n");
            System.out.print("Enter choose: ");
            int choose;
            try {
                choose = Integer.parseInt(scanner.nextLine().trim());  // ✅ Xử lý lỗi nhập số
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1-4.");
                continue;
            }
            switch (choose) {
                case 1:
                    System.out.print("Enter new Set Menu Code (PW001 - PW006): ");
                    String newMenuCode;
                    while (true) {
                        newMenuCode = scanner.nextLine().trim();
                        if (newMenuCode.length() == 5 && isMenuCodeValid(newMenuCode)) {
                            break;
                        }
                        System.out.print("Enter again menu code (PW001 - PW006): ");
                    }
                    order.setMenuCode(newMenuCode);
                    break;
                case 2:
                    // Cập nhật số bàn
                    int tables = getNumberOfTable();
                    order.setTables(tables);

                    // Đọc menu từ file
                    List<String[]> menuList = readMenuFromFile();

                    // Kiểm tra xem món có trong menu không
                    double price = 0;
                    boolean found = false;

                    for (String[] menuItem : menuList) {
                        if (menuItem.length >= 3 && menuItem[0].trim().equalsIgnoreCase(order.getMenuCode().trim())) {
                            try {
                                price = Double.parseDouble(menuItem[2].trim());
                                found = true;
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid price format in file.");
                                return;
                            }
                        }
                    }
                    // Cập nhật tổng tiền ngay trong case 2
                    double totalCost = price * tables;
                    order.setTotalCost(totalCost);

                    break;
                case 3:
                    order.setEventDate(getDate());
                    break;
                case 4:
                    System.out.println("Update order successfully!");
                    this.isSaved = false;
                    isUpdating = false;
                    break;
                default:
                    System.out.println("Enter again select 1-4: ");
            }
        }
    }

    public void displayCustomersFromFile() {
        File file = new File(pathFile);
        if (!file.exists()) {
            System.out.println("No previous customer data found.");
            return;
        }
        try (FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            customlist = (ArrayList<Customer>) ois.readObject();
            isSaved = true;
            System.out.println("Customer data loaded successfully.");

            if (customlist.isEmpty()) {
                System.out.println("Does not have any customer information.");
                return;
            }
            System.out.println(HEADER_TABLE);
            for (Customer customer : customlist) {
                System.out.println(customer);
            }
            System.out.println(FOOTER_TABLE);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Customers.class
                    .getName()).log(Level.SEVERE, "Error reading customer file", ex);
        }
    }

    public void displayOrdersFromFile() {
        File file = new File(orderFile);
        if (!file.exists()) {
            System.out.println("No previous order data found.");
            return;
        }
        try (FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            orderList = (ArrayList<Order>) ois.readObject();
            isSaved = true;
            System.out.println("Order data loaded successfully.");
            if (orderList.isEmpty()) {
                System.out.println("Does not have any customer information.");
                return;
            }
            System.out.println(ORDER_TABLE);
            for (Order order : orderList) {
                System.out.println(order);
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------\n");

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Customers.class
                    .getName()).log(Level.SEVERE, "Error reading order file", ex);
        }
    }

    public void saveCustomersToFile() {
        if (isSaved) {
            return;
        }
        try (FileOutputStream fos = new FileOutputStream(pathFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(customlist);
            isSaved = true;
            System.out.println("Customer data has been successfully saved to " + pathFile);

        } catch (IOException ex) {
            Logger.getLogger(Customers.class
                    .getName()).log(Level.SEVERE, "Error saving customer file", ex);
        }
    }

    public void saveOrdersToFile() {
        if (orderList == null || orderList.isEmpty()) {
            System.out.println("No orders to save.");
            return;
        }
        try (FileOutputStream fos = new FileOutputStream(orderFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(orderList);
            isSaved = true;
            System.out.println("Order data has been successfully saved to " + orderFile);

        } catch (IOException ex) {
            Logger.getLogger(Customers.class
                    .getName()).log(Level.SEVERE, "Error saving order file", ex);
        }
    }

    public void saveDataToFile() {
        int chon;
        System.out.println("Select the file to save data:");
        System.out.println("1. Save Customer Data (customers.dat)");
        System.out.println("2. Save Order Data (feast_order_service.dat)");
        do {
            System.out.print("Enter choice (1-2): ");
            while (!scanner.hasNextInt()) { // Kiểm tra nếu nhập không phải số
                System.out.println("Please enter a number (1-2): ");
                scanner.next(); // Xóa giá trị không hợp lệ
            }
            chon = scanner.nextInt();
            scanner.nextLine(); // Xóa bộ nhớ đệm
            if (chon == 1) {
                saveCustomersToFile();
                break;
            } else if (chon == 2) {
                saveOrdersToFile();
                break;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        } while (true);
    }

    public void displayData() {
        int choice;
        System.out.println("Select the list to display:");
        System.out.println("1. Display Customer List");
        System.out.println("2. Display Order List");
        do {
            System.out.print("Enter choice (1-2): ");
            while (!scanner.hasNextInt()) { // Kiểm tra nếu nhập không phải số
                System.out.println("Please enter a number (1-2): ");
                scanner.next(); // Xóa giá trị không hợp lệ
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Xóa bộ nhớ đệm
            if (choice == 1) {
                if (customlist.isEmpty()) {
                    System.out.println("No customer data in the system.");
                } else {
                    displayCustomersFromFile();
                }
                break;
            } else if (choice == 2) {
                if (orderList.isEmpty()) {
                    System.out.println("No order data in the system.");
                } else {
                    displayOrdersFromFile();
                }
                break;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        } while (true);
    }
}
