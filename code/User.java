

public class User {
    private String id;
    private String password;

    public User(){};
    public User(String id, String password)
    {
        this.id = id;
        this.password = password;
    }

    public String get_id(){return id;}
    public String get_password(){return password;}
}
