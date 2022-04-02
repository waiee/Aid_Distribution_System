

public class Submission
{
    private String item;
    private int quantity;

    public Submission(){}

    public Submission(String item, int quantity)
    {
        this.item = item;
        this.quantity = quantity;
    }

    public String get_item(){return item;}

    public int get_quantity(){return quantity;}

    public void set_quantity(int quantity){this.quantity = quantity;}
}