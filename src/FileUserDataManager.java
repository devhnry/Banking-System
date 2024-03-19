import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUserDataManager{

    private static String USER_DATA_FILE;

    public FileUserDataManager(String fileName) {
        USER_DATA_FILE = fileName;
    }

    public ArrayList<User> loadUsers(){
        ArrayList<User> users = new ArrayList<>();

        if(Files.exists(Paths.get(USER_DATA_FILE)) && !Files.isDirectory(Paths.get(USER_DATA_FILE))){
            try(Scanner output = new Scanner(Paths.get(USER_DATA_FILE))){
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
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return users;
    }

    public void saveUsers(ArrayList<User> users) {
        try {
            PrintWriter input = new PrintWriter(FileUserDataManager.USER_DATA_FILE);
            for (User user : users) {
                input.println(user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getBalance());
            }
            input.close();
        }catch (FileNotFoundException e){
            System.out.println("Error Saving Users into the file...Error Message: \n" + e.getMessage());
        }
    }
}
