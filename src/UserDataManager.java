import java.util.ArrayList;

public interface UserDataManager {
    ArrayList<User> loadUsers();
    void saveUsers(ArrayList<User> users);
}
