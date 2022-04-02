

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Program {

    private ArrayList<Donor> donor_array = new ArrayList<>();
    private ArrayList<NGO> NGO_array = new ArrayList<>();
    private DC the_DC = new DC();

    private Scanner scan = new Scanner(System.in);

    private boolean is_donor = true;// if false then is NGO

    private boolean logged_in = false;

    private Donor current_donor = null;
    private NGO current_NGO = null;

    // default contructor
    public Program() {
        load_donor_file(donor_array);
        load_NGO_file(NGO_array);
    }

    // executable function
    public void execute() {
        loginPage();
    }

     // login
    // ---------------------------------------------------------------------------------------
    public void loginPage() {
        int option = 0;

        while (option != 6) {

            loginMenu();

            option = get_int("Enter your option : ");

            switch (option) {
                case 1:registerDonor();break;

                case 2:registerNGO();break;

                case 3:loginDonor();break;

                case 4:loginNGO();break;

                case 5:
                    if(logged_in)main_menu();
                    else System.out.println("\n-> Please log in first\n");
                    break;

                case 6: 
                    save_donor_data();
                    save_NGO_data();
                    break; // exit loop // save user data??

                default:System.out.println("Invalid input, Please try again");break;
            }

            System.out.println("\n-> press ENTER to continue \n");
            try {System.in.read();} catch (Exception e) {}
        }
    }

    public void loginMenu() {
        System.out.println("\n********* WELCOME *********\n");

        if(logged_in)
            if(is_donor)System.out.println(" >>> Logged in as: " + current_donor.get_id() + " <<<\n");
            else System.out.println(" >>> Logged in as: " + current_NGO.get_id() + " <<<\n");
        else System.out.println(" >>> Please log in to proceed <<<\n");

        System.out.println("1. - Register as Donor");
        System.out.println("2. - Register as Ngo\n");
        System.out.println("3. - Donor Login");
        System.out.println("4. - NGO Login\n");
        System.out.println("5. - Enter main menu\n");
        System.out.println("6. - System Exit\n");
    }

    public int get_int(String prompt) // get integer with try catch
    {
        int output;
        while (true) {
            try {
                System.out.print(prompt);
                output = scan.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("\n-> Invalid input, please enter an integer.\n");
                scan.nextLine();
            }
        }
        return output;
    }

    public void loginDonor() {
        String id;
        String password;
        int findDonorMatch;

        System.out.println("\n********* Donor Login *********\n");

        System.out.print("Enter your UserId : ");
        id = scan.next();

        System.out.print("Enter your password : ");
        password = scan.next();

        if (DonorContains(id)) {
            findDonorMatch = locateDonor(id);
            if (donor_array.get(findDonorMatch).get_password().equals(password)) {
                System.out.println("\n***** Login Successful *****\n");
                System.out.println("UserId : " + id);
                System.out.println("Password : " + password);
                System.out.println("ContactNo: " + donor_array.get(findDonorMatch).get_phone());

                current_donor = donor_array.get(findDonorMatch);
                logged_in = true;
                is_donor = true;
            } else
                System.out.println("\n-> Password entered is Invalid. Please try again");
        } else
            System.out.println("\n-> User does not exist");

    }

    public void loginNGO() {
        String id;
        String password;
        int findNgoMatch; // findMatch declared to find the input by user matches the one in array

        System.out.println("\n********* NGO Login *********\n");

        System.out.print("Enter your UserId : ");
        id = scan.next();

        System.out.print("Enter your password : ");
        password = scan.next();

        // Retrieve data's from Array to check on if the user is Already existed or if
        // the password given is a match to the registered user
        if (NgoContains(id)) {
            // getting the user input and search for the match in array and validate them if
            // true
            findNgoMatch = locateNgo(id);

            if (NGO_array.get(findNgoMatch).get_password().equals(password)) {// password validity checking
                System.out.println("\n-> Login Successful!\n ");
                System.out.println("UserId : " + id);
                System.out.println("Password : " + password);
                System.out.println("Manpower needed : " + NGO_array.get(findNgoMatch).get_power());
                System.out.println("===============================");

                current_NGO = NGO_array.get(findNgoMatch);
                logged_in = true;
                is_donor = false;

            } else
                System.out.println("\n-> Password entered is Invalid. Please try again");

        } else
            System.out.println("\n-> User does not exist!");
    }

    public void registerDonor() {
        String id;
        String password;
        String contactNo;

        System.out.println("\n********* Donor Registration *********\n");

        System.out.print("Enter your UserId : ");
        id = scan.next();

        System.out.print("Enter your password : ");
        password = scan.next();

        System.out.print("Enter your Contact No. : ");
        contactNo = scan.next();

        if (DonorContains(id))
            System.out.println("\n-> User already exist, please continue to Login page");
        else {
            System.out.println("Registration Successful! Welcome " + id);
            donor_array.add(new Donor(id, password, contactNo));
        }

    }

    public void registerNGO() {
        String id;
        String password;
        int manpower;

        System.out.println("\n********* NGO Registration *********\n");

        System.out.print("Enter your UserId : ");
        id = scan.next();

        System.out.print("Enter your password : ");
        password = scan.next();

        manpower = get_int("Enter number of manpower needed : ");

        if (NgoContains(id)) {
            System.out.println("\n-> User already exist, please continue to Login page");
        } else {
            System.out.println("Registration Successful! Welcome " + id);
            NGO_array.add(new NGO(id, password, manpower));
        }
    }

    public int locateDonor(String DonorId) {
        // To locate and return the index of Donor in Arraylist, which to be used during
        // the login
        for (int i = 0; i < donor_array.size(); i++)
            if (donor_array.get(i).get_id().equals(DonorId))
                return i;

        return -1;
    }

    public int locateNgo(String NgoId) {
        // To locate and return the index of Ngo in Arraylist, which to be used during
        // the login
        for (int i = 0; i < NGO_array.size(); i++)
            if (NGO_array.get(i).get_id().equals(NgoId))
                return i;

        return -1;
    }

    public boolean DonorContains(String DonorId) {
        // to check if Donor as user exist
        for (Donor i : donor_array)
            if (i.get_id().equals(DonorId)) // if the Id is a match to the list returns User exist or the id has been
                                            // taken
                return true;

        return false;// may proceed to add new user's registration
    }

    public boolean NgoContains(String NgoId) {
        // to check if the Ngo as user exist
        for (NGO i : NGO_array)
            if (i.get_id().equals(NgoId))
                return true;

        return false;
    }

     // main menu
    // ------------------------------------------------------------------------
    public void main_menu() {
        int choice = 0;

        while (choice != 11) {
            print_main_menu();

            choice = get_int("-> Enter your choice: ");

            System.out.println("\n=======================================================================");

            switch (choice) {
                case 1: 
                    if(is_donor)the_DC.input_by_donor(current_donor);
                    else System.out.println("\n-> You are not a donor\n");
                    break;

                case 2: 
                    if(is_donor){
                        the_DC.print_donor_view(current_donor.get_id());
                    }else System.out.println("\n-> You are not a donor\n");
                    break;

                case 3: 
                    if(!is_donor)the_DC.input_by_NGO(current_NGO);
                    else System.out.println("\n-> You are not a NGO");
                    break;

                case 4:
                    if(!is_donor){
                        the_DC.print_NGO_view(current_NGO.get_id());
                    }else System.out.println("\n-> You are not a NGO");
                    break;

                case 5: the_DC.print_view_all();break;

                case 6: the_DC.print_one_to_one();break;

                case 7: the_DC.print_one_to_many();break;

                case 8: the_DC.print_many_to_one();break;

                case 9: the_DC.print_many_to_many();break;

                case 10: the_DC.clear_all(); break;

                case 11: logged_in = false; break; // exit loop

                default:    System.out.println("\n-> choice unavailable, try again.\n");break;
            }
            System.out.println("\n-> press ENTER to continue \n");
            try {System.in.read();} catch (Exception e) {}
        }
    }

    public void print_main_menu() {

        System.out.println(); // empty newline
        System.out.println("+-----------------------------------+");
        System.out.println("|             MAIN MENU             |");
        System.out.println("+-----------------------------------+\n");
      
        if(is_donor)System.out.println("  >>> Logged in as: " + current_donor.get_id() + " <<<\n");
        else System.out.println("  >>> Logged in as: " + current_NGO.get_id() + " <<<\n");
    

        System.out.println("============ Donor ============\n");
        System.out.println("1. - Donate aids");
        System.out.println("2. - View aids donated\n\n");

        System.out.println("============= NGO =============\n");
        System.out.println("3. - Request aids");
        System.out.println("4. - View aids requested\n\n");

        System.out.println("============= DC ==============\n");
        System.out.println("5. - View all aids donated by donors, and all NGOs\n");

        System.out.println("6. - Match aids one-to-one");
        System.out.println("7. - Match aids one-to-many");
        System.out.println("8. - Match aids many-to-one");
        System.out.println("9. - Match aids many-to-many\n");

        System.out.println("10.- Clear all aids and request\n");

        System.out.println("11.- Log out\n");
    }

   

    // load / save file
    // ------------------------------------------------------------------------
    public void load_donor_file(ArrayList<Donor> donor_array) {
        String path = "Donor_Data.csv";
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                donor_array.add(new Donor(values[0], values[1], values[2]));
            }

            br.close();

        } catch (Exception e) {
            System.out.println("-> Failed to load file.");
        }
    }

    public void load_NGO_file(ArrayList<NGO> NGO_array) {
        String path = "NGO_Data.csv";
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                NGO_array.add(new NGO(values[0], values[1], Integer.parseInt(values[2])));
            }

            br.close();

        } catch (Exception e) {
            System.out.println("-> Failed to load file.");
        }
    }

    public void save_donor_data()
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Donor_Data.csv"));

            for (Donor i : donor_array) {
                writer.write(i.get_id() + "," + i.get_password() + "," + i.get_phone() + "\n");
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save_NGO_data()
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("NGO_Data.csv"));

            for (NGO i : NGO_array) {
                writer.write(i.get_id() + "," + i.get_password() + "," + i.get_power() + "\n");
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}