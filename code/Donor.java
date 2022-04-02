

public class Donor extends User
{
    private String phone_no;

    public Donor(){};
    public Donor(String id, String password, String phone_no)
    {
        super(id, password);
        this.phone_no = phone_no;
    }

    public String get_phone(){return phone_no;}   
}