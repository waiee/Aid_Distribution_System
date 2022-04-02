
public class Request extends Submission
{
    private NGO the_NGO;

    public Request(){}
    public Request(NGO the_NGO, String item, int quantity)
    {
       super(item, quantity);
       this.the_NGO = the_NGO;
    }

    public NGO get_NGO(){return the_NGO;}

    public String toString()
    {
        return String.format("%-10s%-14s%-10s%-10s", the_NGO.get_id(), the_NGO.get_power(), get_item() , get_quantity());
    }
}