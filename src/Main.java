import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public class Main {
    private static final String USER_DATA_FILE = "userData.txt";
    private static User LOGGED_IN_USER = new User();
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        System.out.println("======= System Specs Banking System ======");
        Scanner scanner = new Scanner(System.in);

        ArrayList<User> users = loadUsers();
        String response = getUserResponse(scanner);

        if (response.equals("q")) {
            System.exit(1);
        }

        if (response.equals("yes")) {
            scanner.nextLine();
            System.out.println("Create your account by filling in the correct details below.");
            createUserAccount(users, scanner);
            System.out.println();
            System.out.println("Account Created Successfully...Login with the details");
            loginUser(scanner, users);
            bankingSystem(scanner);
        } else if (response.equals("no")) {
            scanner.nextLine();
            System.out.println("Welcome Back!");
            System.out.println("Log in with your details.");
            int TooManyTry = loginUser(scanner, users);
            while(TooManyTry == 3){
                TooManyTry = loginUser(scanner, users);
            }
        } else
            System.out.println("Invalid Input");
            System.exit(1);
    }
    private static void bankingSystem(Scanner scanner) throws InterruptedException, FileNotFoundException {
        System.out.println("What would you like to do today ? \nClick 1 to View Balance\nClick 2 to withdraw\nClick 3 to Deposit Money\nQ to quit");
        String response = scanner.next().toLowerCase();
        scanner.nextLine();
        if(!response.equals("q")){
            switch (response) {
                case "1" -> {
                    System.out.println("Loading Balance...\n");
                    TimeUnit.SECONDS.sleep(3);
                    viewBalance();
                    TimeUnit.SECONDS.sleep(1);
                    bankingSystem(scanner);
                }
                case "2" -> {
                    System.out.println("Loading ...\n");
                    TimeUnit.SECONDS.sleep(2);
                    withdraw(scanner);
                    TimeUnit.SECONDS.sleep(1);
                    bankingSystem(scanner);
                }
                case "3" -> {
                    System.out.println("Loading ...\n");
                    TimeUnit.SECONDS.sleep(2);
                    deposit(scanner);
                    TimeUnit.SECONDS.sleep(1);
                    bankingSystem(scanner);
                }
                default -> {
                    System.out.println("Invalid Input");
                    System.out.println();
                    bankingSystem(scanner);
                }
            }
        }else{
            System.out.println("Thank you for banking with us;");
            System.exit(1);
        }
    }
    private static void viewBalance(){
        float balance = LOGGED_IN_USER.getBalance();
        System.out.println("Your current Balance is " + balance + "\n");
    }
    private static void updateData(float newBalance, File file) throws FileNotFoundException {
        LOGGED_IN_USER.setBalance(newBalance);
        ArrayList<String> updatedData = new ArrayList<>();
        try {
            Scanner output = new Scanner(file);
            while(output.hasNextLine()) {
                String data = output.nextLine();
                String[] userData = data.split(",");

                if (userData[1].equals(LOGGED_IN_USER.getEmail())) {
                    userData[3] = String.valueOf(newBalance);
                    data = String.join(",", userData);
                }
                updatedData.add(data);
            }
        }catch(IOException e){
            System.out.println("Error in updating balance");
        }

        try(PrintWriter output = new PrintWriter(USER_DATA_FILE)){
            for (String data : updatedData) {
                output.println(data);
            }
        }
    }
    private static void deposit(Scanner scanner) throws  FileNotFoundException{
        File file = new File(USER_DATA_FILE);
        float balance = LOGGED_IN_USER.getBalance();
        System.out.println("How much do you wish to Deposit: ");
        float amountToDeposit = scanner.nextFloat();
        float newBalance = balance + amountToDeposit;
        updateData(newBalance, file);
        System.out.println("Deposit Successful");
        viewBalance();
        System.out.println();
    }
    private static void withdraw(Scanner scanner) throws FileNotFoundException {
        File file = new File(USER_DATA_FILE);
        String message;
        float balance = LOGGED_IN_USER.getBalance();
        System.out.println("How much do you wish to Withdraw: ");
        float amountToWithdraw = scanner.nextFloat();
        if(amountToWithdraw > balance){
            message = "Insufficient Funds in the Account";
            System.out.println(message);
        }else{
            float newBalance = balance - amountToWithdraw;
            updateData(newBalance, file);
            System.out.println("Withdrawal Successful");
            viewBalance();
            System.out.println();
        }
    }
   private static String getUserResponse(Scanner scanner) {
      System.out.print("Are you a new User (Yes or No) (Press Q to quit): ");
      return scanner.next().toLowerCase().trim();
   }
    private static String getUserInput(Scanner scanner, String prompt) {
        String input;
        int emptyPrompt = 0;
        do {
            System.out.print(prompt + ": ");
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid Prompt");
                emptyPrompt++;
                if(emptyPrompt == 3){
                    System.out.println("Multiple Invalid Prompt..Do you wish to quit (yes / no)");
                    String response = scanner.next().toLowerCase();
                    scanner.nextLine();
                    if(response.equals("yes")){
                        System.exit(1);
                    }else if(response.equals("no"))
                        emptyPrompt = 0;
                }
            }
        } while (input.isEmpty());
        return input;
    }
   private static void createUserAccount(ArrayList<User> users, Scanner scanner ) {
       String name = getUserInput(scanner, "Full Name");
       String email = getUserInput(scanner, "Email Address");
       String password = getUserInput(scanner, "Password");

       User user = new User(name, email, password, 10000.00f);
       users.add(user);
       saveUsers(users);
   }
   private static int loginUser(Scanner scanner, ArrayList<User> users) throws InterruptedException, FileNotFoundException {
        int wrongTry = 0;
       while(wrongTry < 3){
           String email = getUserInput(scanner, "Email Address");
           String password = getUserInput(scanner, "Password");

           User loggedInUser = login(users, email, password);
           if (loggedInUser != null) {
               System.out.println("Login successful! Welcome, " + loggedInUser.getName().split(" ")[0]);
               System.out.println("Available Balance: " + loggedInUser.getBalance());
               LOGGED_IN_USER = loggedInUser;
               System.out.println();
               bankingSystem(scanner);
               break;
           } else {
               if(wrongTry == 2){
                   System.out.println("Invalid email or password. Try Again in 20 Seconds / Q to Quit / W to wait");
                   String response = scanner.next().toLowerCase().trim();
                   scanner.nextLine();
                   if(response.equals("q")){
                       System.exit(1);
                   }else if (response.equals("w"))
                       System.out.println("Waiting...");
                       TimeUnit.SECONDS.sleep(3);
                       wrongTry = 0;
               }else{
                   System.out.println("Invalid email or password. Try Again.");
               }
               wrongTry++;
           }
       }
       return wrongTry;
   }
    public static User login(ArrayList<User> users, String email, String password){
        for(User user: users){
            if(user.getEmail().equals(email) && user.getPassword().equals(password) ){
                return user;
            }
        }
        return null;
    }
    private static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        File file = new File(USER_DATA_FILE);
        try{
            Scanner output = new Scanner(file);
            if(output.hasNext()){
                while(output.hasNextLine()){
                    String data = output.nextLine();
                    String[] userData = data.split(",");
                    String name = userData[0];
                    String email = userData[1];
                    String password = userData[2];
                    float balance = Float.parseFloat(userData[3]);
                    User user = new User(name, email, password, balance);
                    users.add(user);
                }
            }else{
                return users;
            }
            output.close();
        } catch (IOException e) {
            System.out.println("Error Loading Users into the file...Error Message: \n" + e.getMessage());
        }
        return users;
    }
    private static void saveUsers(ArrayList<User> users) {
        try {
            PrintWriter input = new PrintWriter(USER_DATA_FILE);
            for (User user : users) {
                input.println(user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getBalance());
            }
            input.close();
        } catch (IOException e) {
            System.out.println("Error Saving Users into the file...Error Message: \n" + e.getMessage());
        }
    }
}