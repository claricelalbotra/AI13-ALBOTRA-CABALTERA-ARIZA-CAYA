import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Items {
    private int ID;
    private String name;
    private String description;
    private String date;
    private String location;
    private String status;
    
    //initialize an item
    Items(int ID, String name, String description, String date, String location, String status) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
        this.status = status;
    }
    //Get the ID
    public int getID() {
        return ID;
    }
    //Get the Name
    public String getName() {
        return name;
    }
    //Get the Description
    public String getDescription() {
        return description;
    }
    //Get the Date
    public String getDate() {
        return date;
    }
    //Get the Location
    public String getLocation() {
        return location;
    }
    //Get the Status
    public String getStatus() {
        return status;
    }
    //To Update Status
    public void setStatus(String status) {
        this.status = status;
    }
    //To Update Other Details
    public void updateDetails(String name, String description, String date, String location) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
    }
    //Show Details as Item
    public String toString() {
        return "ID: " + ID + " | Name: " + name + " | Description: " + description + " | Date: " + date + " | Location: " + location + " | Status: " + status;
    }
}

class G2 {
    public static void main(String[] args) {
        List<Items> clar = new ArrayList<>();
        Scanner scn = new Scanner(System.in);
        int menu;
        int id1 = 202401;

        //Menu Option
        do {
            System.out.println("--- LNULostFound System ---");
            System.out.println("1. VIEW ITEMS");
            System.out.println("2. ADD ITEMS");
            System.out.println("3. UPDATE ITEMS");
            System.out.println("4. DELETE ITEMS");
            System.out.println("5. SEARCH ITEMS");
            System.out.println("6. STATISTICS");
            System.out.println("7. EXIT");
            System.out.print("Enter Menu Option: ");
            while (!scn.hasNextInt()) {
                System.out.println("Invalid input! Enter a number between 1 and 7.");
                scn.next();
            }
            menu = scn.nextInt();
            System.out.println();

            switch (menu) {
                case 1: // View items with pagination
                    viewItems(clar, scn);
                    break;

                case 2: // Add item with validation
                    addItem(clar, scn, id1++);
                    break;

                case 3: // Update item
                    updateItem(clar, scn);
                    break;

                case 4: // Delete item
                    deleteItem(clar, scn);
                    break;

                case 5: // Search item
                    searchItem(clar, scn);
                    break;

                case 6: // Statistics with optional date filtering
                    showStatistics(clar, scn);
                    break;

                case 7: //Exit
                    System.out.println("THANK YOU AND GOODBYE!");
                    break;

                default:
                    System.out.println("INVALID MENU OPTION");
            }
        } while (menu != 7);
    }

    // View items with by pages, maximum of 5 items
    private static void viewItems(List<Items> clar, Scanner scn) {
        int itemsPerPage = 5, page = 0;
        while (true) {
            //Calculate The Range of Items
            int start = page * itemsPerPage;
            int end = Math.min(start + itemsPerPage, clar.size());
            if (clar.isEmpty()) { //Checking If List is Empty
                System.out.println("NO ITEMS UPLOADED");
                break; 
            } 
            // Items Display in Current Page
            for (int j = start; j < end; j++) { 
                System.out.println(clar.get(j));
            }
            if (end == clar.size()) break; //End if no items
            System.out.print("Next page? (y/n): ");
            scn.nextLine();  
            if (!scn.nextLine().equalsIgnoreCase("y")) break; //Stop, If User Enters "n"(No)
            page++; //Move To The Next Page
        }
        System.out.println();
    }

    // Add new item to the list
private static void addItem(List<Items> clar, Scanner scn, int id) {
    scn.nextLine();  // To clear any leftover newline character in the scanner buffer
    System.out.print("Enter Name of Item: ");
    String name = scn.nextLine();
    System.out.print("Description: ");
    String description = scn.nextLine(); 

    String date;
    while (true) {
        System.out.print("Date (YYYY-MM-DD): ");
        date = scn.nextLine();
        if (isValidDate(date)) break; //Checking If Valid Input
        System.out.println("Invalid date format! Please try again."); //Error Message
    }

    System.out.print("Location: ");
    String location = scn.nextLine();

    String status;
    while (true) {
        System.out.print("Status (Lost/Found): ");
        status = scn.nextLine();
        if (status.equalsIgnoreCase("Lost") || status.equalsIgnoreCase("Found")) break; //Checking If Valid Input
        System.out.println("Invalid status! Enter 'Lost' or 'Found'."); //Error Message
    }

    
    boolean match = false;  // Check if item match in the list
    for (Items item : clar) {
        if (item.getName().equalsIgnoreCase(name) && item.getDescription().equalsIgnoreCase(description) && item.getLocation().equalsIgnoreCase(location)) {
            match = true; 
            System.out.println("GOOD THING! IT MATCHED IN THE LIST!");
            System.out.println("----------------------");
            System.out.println(item); //Display The Match Item
            System.out.println("----------------------");
            break; 
        }
    }
    // If no match, add the new item to the list
    if (!match) {
        clar.add(new Items(id, name, description, date, location, status));
        System.out.println("ITEM ADDED SUCCESSFULLY!");
    } 

    System.out.println();
}
    // Update item in the list
private static void updateItem(List<Items> clar, Scanner scn) {
        System.out.print("Enter ID of Item to Update: ");
        int idUpdate = scn.nextInt();
        scn.nextLine();
        boolean updated = false;

    for (Items item : clar) {
        if (item.getID() == idUpdate) {
            System.out.println("Leave a field blank to retain the current value.");

            System.out.print("ENTER NEW NAME (Current: " + item.getName() + "): ");
            String newName = scn.nextLine();
            if (!newName.trim().isEmpty()) {  //If User Want To Change The Name
                item.updateDetails(newName, item.getDescription(), item.getDate(), item.getLocation());
            }

            System.out.print("ENTER NEW DESCRIPTION (Current: " + item.getDescription() + "): ");
            String newDescription = scn.nextLine();
            if (!newDescription.trim().isEmpty()) { //If User Want To Change The Description
                item.updateDetails(item.getName(), newDescription, item.getDate(), item.getLocation());
            }

            String newDate;
            while (true) {
                System.out.print("ENTER NEW DATE (YYYY-MM-DD, Current: " + item.getDate() + "): ");
                newDate = scn.nextLine();
                if (newDate.trim().isEmpty() || isValidDate(newDate)) {
                    if (!newDate.trim().isEmpty()) { //If User Want To Change The Name
                        item.updateDetails(item.getName(), item.getDescription(), newDate, item.getLocation());
                    }
                    break;
                }
                System.out.println("Invalid date format! Please try again.");
            }

            System.out.print("ENTER NEW LOCATION (Current: " + item.getLocation() + "): ");
            String newLocation = scn.nextLine();
            if (!newLocation.trim().isEmpty()) { //If User Want To Change The Location
                item.updateDetails(item.getName(), item.getDescription(), item.getDate(), newLocation);
            }

            System.out.print("ENTER NEW STATUS (Lost/Found, Current: " + item.getStatus() + "): ");
            String newStatus = scn.nextLine();
            if (!newStatus.trim().isEmpty()) { //If User Want To Change The Status
                if (newStatus.equalsIgnoreCase("Lost") || newStatus.equalsIgnoreCase("Found")) {
                    item.setStatus(newStatus); //Update If Valid
                } else {
                    System.out.println("Invalid status! Skipping status update.");
                }
            }

            System.out.println("ITEM UPDATED SUCCESSFULLY!");
            updated = true;
            break;
        }
    }
    if (!updated) {
        System.out.println("ITEM NOT FOUND!");
    }
    System.out.println();
}

    // Delete item on the list
    private static void deleteItem(List<Items> clar, Scanner scn) {
        System.out.print("Enter ID of Item to Delete: ");
        int idDelete = scn.nextInt(); //Input ID Item To Delete
        boolean removed = clar.removeIf(item -> item.getID() == idDelete); //To Remove In The List

        if (removed) {
            System.out.println("ITEM DELETED SUCCESSFULLY!"); //Message If Item Deleted
        } else {
            System.out.println("ITEM NOT FOUND!"); //Message If ID Item Not Found in the List
        }
        System.out.println();
    }

    // Search for items on the list
    private static void searchItem(List<Items> clar, Scanner scn) { 
        System.out.print("Enter keyword to search (Name/Description/Location): ");
        scn.nextLine();
        String keyword = scn.nextLine().toLowerCase(); //Input Convert To Lower Case
        boolean found = false;

        for (Items item : clar) {
            if (item.getName().toLowerCase().contains(keyword) || //Checking If Keyword Match
                    item.getDescription().toLowerCase().contains(keyword) ||
                    item.getLocation().toLowerCase().contains(keyword)) {
                System.out.println(item);
                found = true;
            }
        }
        if (!found) System.out.println("NO MATCHING ITEMS FOUND!");
        System.out.println();
    }

    // Show statistics
    private static void showStatistics(List<Items> clar, Scanner scn) {
        System.out.print("Filter by date range? (y/n): ");
        scn.nextLine();
        String filter = scn.nextLine();
        int lostCount = 0, foundCount = 0;

        if (filter.equalsIgnoreCase("y")) { //If User Applies Date Filter
            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDate = scn.nextLine(); 
            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDate = scn.nextLine();

            for (Items item : clar) { //Count Items Within The Date Range
                if (isWithinDateRange(item.getDate(), startDate, endDate)) {
                    if (item.getStatus().equalsIgnoreCase("Lost")) lostCount++;
                    else if (item.getStatus().equalsIgnoreCase("Found")) foundCount++;
                }
            }
        } else { //If No Date Filtering
            for (Items item : clar) {
                if (item.getStatus().equalsIgnoreCase("Lost")) lostCount++;
                else if (item.getStatus().equalsIgnoreCase("Found")) foundCount++;
            }
        }
        //Print Number of Lost and Found Items
        System.out.println("STATISTICS");
        System.out.println("Number of Lost Items: " + lostCount);
        System.out.println("Number of Found Items: " + foundCount);
        System.out.println();
    }

    //Check If Date is Valid
    private static boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); //Disable Lenient Mode
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    //Check If The Date is Withing The Given Range
    private static boolean isWithinDateRange(String date, String startDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Date d = sdf.parse(date);
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            return d.compareTo(start) >= 0 && d.compareTo(end) <= 0; //Checking If Within Range
        } catch (ParseException e) {
            return false;
        }
    }
}