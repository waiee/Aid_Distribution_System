

public class NGO extends User
{
    private int manpower;

    public NGO(){}
    public NGO(String id, String password, int manpower)
    {
        super(id, password);
        this.manpower = manpower;
    }

    public int get_power(){return manpower;}
}
