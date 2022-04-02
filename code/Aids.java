


public class Aids extends Submission 
{
    private Donor the_donor;

    public Aids(){}
    public Aids(Donor the_donor, String item, int quantity)
    {
        super(item, quantity);
        this.the_donor = the_donor;
    }

    public Donor get_donor(){return the_donor;}

    public String toString()
    {
        return  String.format("%-10s%-14s%-10s%-10s", the_donor.get_id(), the_donor.get_phone() , get_item(), get_quantity());
    }
}
