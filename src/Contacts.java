import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Contacts extends IOMethods{


    public static void main(String[] args) {

        Contacts benny = new Contacts();
        benny.createContactStorage();

        Path contactFile = Paths.get("src/contactList", "contacts.txt");

        ArrayList<String> contact = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        String addContact = "";
        while(!userInput.equals("5") ) {

            System.out.println("Welcome to your Contacts Menu.");
            System.out.println("1. View contacts");
            System.out.println("2. Add a new contact.");
            System.out.println("3. Search a contact by name.");
            System.out.println("4. Delete an existing contact.");
            System.out.println("5. Exit.");
            System.out.print("Enter a number: ");

            userInput = scanner.nextLine();

            if (userInput.equals("1")) {
                benny.readFileAndOutput(contactFile);
            } else if (userInput.equals("2")) {
                //call to add number method
                addContact = benny.addContact(scanner);
                contact.add(addContact);

                benny.addNewContact(contactFile, contact, addContact);
                contact.clear();

            } else if (userInput.equals("3")) {
                System.out.println("Enter the name of the contact you would like to search.");
                Scanner search = new Scanner(System.in);
                String searchContact = search.nextLine();

                try {
                    benny.searchFile(contactFile, searchContact);
                }catch (IOException ioException) {
                    System.out.println("Error at #3 section");
                    ioException.printStackTrace();
                }
            } else if (userInput.equals("4")) {
                System.out.println("Enter the name of the contact you would like to delete.");
                Scanner search = new Scanner(System.in);
                String searchContact = search.nextLine();
                try {
                    benny.deleteContact(contactFile, searchContact);
                } catch (IOException ioException) {
                    System.out.println("Error at section #4");
                    ioException.printStackTrace();
                }

            } else if (userInput.equals("5")) {
                System.out.println("Thank you, goodbye!");
            } else {
                System.out.println("Sorry please try again.");
            }
        }
    }
}
