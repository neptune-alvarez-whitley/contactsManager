import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Contacts{

    public void addNewContact(Path location, ArrayList<String> contactList, String userContact){
        ArrayList<String> currentList = new ArrayList<>();
        try {
            currentList = (ArrayList<String>) Files.readAllLines(location);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        try {
            //Create a reader that will test every line of our text file against the new String the user wants to add
            //if the string IS found inside our file, it gets thrown out and nothing is added.
            //If it is NOT found then it gets added to the list
            BufferedReader reader = new BufferedReader(new FileReader(new File(String.valueOf(location))));
            String line;
            while((line = reader.readLine()) != null){
                if(currentList.contains(userContact)){
                    System.out.println("This contact already exists.");
                    break;
                }
                write(location, contactList, StandardOpenOption.APPEND);
                break;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Hey there all you cool cats and kittens");
    }

    public void write(Path location, ArrayList<String> information, StandardOpenOption option){
        try{
            //2 arguments for File.write
            // the path to the file, the data we want to write
            Files.write(location, information, option);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void readFileAndOutput(Path pathToFile){

        //lets grab the information we stored on our file...
        //first we must create a place to store that information:
        List<String> currentList = new ArrayList<>();

        //again... we must wrap everything in a try catch statement
        try {
            currentList = Files.readAllLines(pathToFile);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        //we can use the readAllLines() method to get every line of information from the txt file
        //Now we will use a for loop(enhanced) to print out our array list to the console
        for(String line: currentList){
            System.out.println(line);
        }
    }

    public void createContactStorage(){
        //create a new OBJECT that holds a specific file location
        Path storageFile = Paths.get("src/contactList");
        try{
            //create a new directory using our file path object
            if(Files.notExists(storageFile)){
                Files.createDirectory(storageFile);
            }else{
                System.out.println("That file already exists...");
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        //(same as above) : create a new text file that will store Users contact list
        Path contactFile = Paths.get(String.valueOf(storageFile), "contacts.txt");
        try{
            if(Files.notExists(contactFile)){
                Files.createFile(contactFile);
            }else{
                System.out.println("This already exists...");
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    //for #3 search contact.
    public void searchFile(Path pathToFile, String searchContact) throws IOException{
        Scanner scan = new Scanner(pathToFile);
        while(scan.hasNext()){
            String line = scan.nextLine().toLowerCase();
            if(line.contains(searchContact)){
                System.out.println(line);
            }
        }
    }

    public void deleteContact(Path pathToFile, String searchContact) throws IOException{
        Scanner scanner = new Scanner(System.in);
        Scanner scan = new Scanner(pathToFile);
        int i = 0;
        while(scan.hasNext()){
            String contact = scan.nextLine().toLowerCase();
            i++;
            if(contact.contains(searchContact)){

                System.out.println("======================================");
                System.out.println("\n" + contact);
                System.out.println("======================================");
                System.out.println("Would you like to delete this contact?");
                System.out.print("(Y/N) : ");

                String userResponse = scanner.next();
                if(userResponse.equalsIgnoreCase("yes") || userResponse.equalsIgnoreCase("y")){
                    //delete the line that our contact was found on
                    List<String> currentList = new ArrayList<>();
                    try{
                        currentList = Files.readAllLines(pathToFile);
                    }catch(IOException ioe){
                        ioe.printStackTrace();
                    }
                    //remove the line at that index
                    currentList.remove(i - 1);

                    try{
                        Files.write(pathToFile, currentList);
                    }catch(IOException ioe){
                        ioe.printStackTrace();
                    }
                }else{
                    System.out.println("\nOkay, we wont delete that contact\n");
                }
            }
        }
    }

    public static void main(String[] args) {

        Contacts benny = new Contacts();
        benny.createContactStorage();

        Path contactFile = Paths.get("src/contactList", "contacts.txt");
//        benny.readFileAndOutput(contactFile);

        ArrayList<String> contact = new ArrayList<>();
        //call the write method to add information to the contacts list
//        benny.write(contactFile, contact, StandardOpenOption.APPEND);

//        benny.readFileAndOutput(contactFile);

        Scanner scanner = new Scanner(System.in);
        int userInput = 0;
        String addContact = "";
        while(userInput != 5) {

            System.out.println("Welcome to your Contacts Menu.");
            System.out.println("1. View contacts");
            System.out.println("2. Add a new contact.");
            System.out.println("3. Search a contact by name.");
            System.out.println("4. Delete an existing contact.");
            System.out.println("5. Exit.");
            System.out.print("Enter a number: ");

            userInput = scanner.nextInt();

            if (userInput == 1) {
                benny.readFileAndOutput(contactFile);
            } else if (userInput == 2) {
                System.out.print("\nFirst name: ");
                String firstName = scanner.nextLine();
                firstName = scanner.nextLine();

                System.out.print("\nLast name: ");
                String lastName = scanner.nextLine();

                System.out.print("\nPhone number: ");
                String phoneNumber = scanner.nextLine();


               phoneNumber = phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");




                addContact = firstName + " " + lastName + ", " + phoneNumber;

                contact.add(addContact);

//                benny.write(contactFile, contact, StandardOpenOption.APPEND);
//                contact.clear();

                benny.addNewContact(contactFile, contact, addContact);
                contact.clear();

            } else if (userInput == 3) {
                System.out.println("Enter the name of the contact you would like to search.");
                Scanner search = new Scanner(System.in);
                String searchContact = search.nextLine();

                try {
                    benny.searchFile(contactFile, searchContact);
                }catch (IOException ioException) {
                    System.out.println("Error at #3 section");
                    ioException.printStackTrace();
                }
            } else if (userInput == 4) {
                System.out.println("Enter the name of the contact you would like to delete.");
                Scanner search = new Scanner(System.in);
                String searchContact = search.nextLine();
                try {
                    benny.deleteContact(contactFile, searchContact);
                } catch (IOException ioException) {
                    System.out.println("Error at section #4");
                    ioException.printStackTrace();
                }

            } else if (userInput == 5) {
                System.out.println("Thank you, goodbye!");
            }
        }
    }
}
