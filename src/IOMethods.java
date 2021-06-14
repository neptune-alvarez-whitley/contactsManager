import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class IOMethods {

    public void addNewContact(Path location, ArrayList<String> contactList, String userContact){
        List<String> currentList = new ArrayList<>();
        try {
            currentList = Files.readAllLines(location);
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
        System.out.println("\nHey there all you cool cats and kittens\n");
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
        }catch(Exception e){
            System.out.println("Your contacts list is empty");
        }
        //we can use the readAllLines() method to get every line of information from the txt file
        //Now we will use a for loop(enhanced) to print out our array list to the console
        String format = "|| %1$-15s| %2$-16s||\n";
        System.out.println("||==================================||");
        System.out.format(format, "Name ", " Phone Number ");
        System.out.println("||----------------------------------||");
        String[] information;
        for(String line: currentList){
            information = line.split(",");
            try {
                System.out.format(format, information[0], information[1]);
            } catch(ArrayIndexOutOfBoundsException aio){
                System.out.println("");
            }
        }
        System.out.println("||==================================||");
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
        System.out.println("==============================");
        while(scan.hasNext()){
            String line = scan.nextLine().toLowerCase();
            if(line.contains(searchContact.toLowerCase(Locale.ROOT))){
                System.out.println(line);
            }
        }
        System.out.println("==============================");
    }

    public String addContact(Scanner sc){
        System.out.println("===============================");
        System.out.print("\nFirst name: ");
        String firstName = sc.nextLine();


        System.out.print("\nLast name: ");
        String lastName = sc.nextLine();

        System.out.print("\nPhone number: ");
        String phoneNumber = sc.nextLine();
        System.out.println("\n===============================");
        String[] numberTest = phoneNumber.split("");
        //format phone numbers based on length
        if(numberTest.length == 10) {
            phoneNumber = phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)$2-$3");
        }else if(numberTest.length == 7){
            phoneNumber = phoneNumber.replaceFirst("(\\d{3})(\\d+)", "$1-$2");
        }
        else{
            System.out.println("That number is not the right length");
            System.out.println("Please enter your contact information again:");

            return addContact(sc);
        }
        return firstName + " " + lastName + ", " + phoneNumber;
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
                System.out.println(contact);
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

}
