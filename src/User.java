public class User {
    private String name;
    private String email;
    private String password;
    private float balance;
    User(String name, String email, String password, float balance){
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    User(){}

    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }

    public Float getBalance() {
        return balance;
    }
    public void setBalance(float newBalance){
        balance = newBalance;
    }
}
