import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Contacts{

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

    public static void main(String[] args) {

        Contacts benny = new Contacts();
        benny.createContactStorage();

        Path contactFile = Paths.get("src/contactList", "contacts.txt");
//        benny.readFileAndOutput(contactFile);

        ArrayList<String> contact = new ArrayList<>();
//        contact.add("Naysa, 1234567890");
//        contact.add("Benny, 2106307421");
//        contact.add("Austin, 8178465508");

        //call the write method to add information to the contacts list
//        benny.write(contactFile, contact, StandardOpenOption.APPEND);

        benny.readFileAndOutput(contactFile);


    }
}
