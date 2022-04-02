

import java.util.ArrayList;
import java.util.Scanner;


public class DC {

    private ArrayList<Aids> aids_array = new ArrayList<>(); 
    private ArrayList<Request> req_array = new ArrayList<>();
    private Scanner scan = new Scanner(System.in);

    // no arg constructor
    public DC (){}
    

    // NEW FUNCTION
    public void add_aids(Donor the_donor, String item_name, int quantity)
    {
        aids_array.add(new Aids(the_donor, item_name, quantity));
    }

    // NEW FUNCTION
    public void add_req(NGO the_NGO, String item_name, int quantity)
    {
        req_array.add(new Request(the_NGO, item_name, quantity));
    }

    public void input_by_donor(Donor the_donor)
    {
        String item_name;
        int quantity;

        System.out.println(">>> Donor input <<< \n");

        System.out.print("Enter item name: ");
        item_name = scan.next();

        quantity = get_int("Enter item quantity: ");

        aids_array.add(new Aids(the_donor, item_name, quantity));
    }

    public void input_by_NGO(NGO the_NGO)
    {
        String item_name;
        int quantity;

        System.out.println(">>> NGO input <<< \n");

        System.out.print("Enter item name: ");
        item_name = scan.next();

        quantity = get_int("Enter item quantity: ");

        req_array.add(new Request(the_NGO, item_name, quantity));
    }

    public void clear_all()
    {
        aids_array.clear();
        req_array.clear();
        System.out.println("\n-> All aids and requests cleared\n");
    }

    public void print_view_all()
    {
        for(String i : get_view_all())
            System.out.println(i);
    }

    public void print_one_to_one()
    {
        for(String i : match_one_to_one())
            System.out.println(i);
    }

    public void print_one_to_many()
    {
        for(String i : match_one_to_many())
            System.out.println(i);
    }

    public void print_many_to_one()
    {
        for(String i : match_many_to_one())
            System.out.println(i);
    }

    public void print_many_to_many()
    {
        for(String i : match_many_to_many())
        {
            System.out.println(i);
        }
    }

    public void print_donor_view(String donor_id)
    {
        for (String i: get_donor_view(donor_id))
            System.out.println(i);
    }

    public void print_NGO_view(String NGO_id)
    {
        for(String i : get_NGO_view(NGO_id))
            System.out.println(i);
    }

    public ArrayList<String> get_view_all()
    {
        ArrayList<String> output = new ArrayList<>();

        output.add("-> All donors and their respective donations:");

        // print column name
        output.add("------------------------------------------");
        output.add(String.format("%-10s%-14s%-10s%-10s", "Donor", "Phone", "Aids", "Quantity"));
        output.add("------------------------------------------");
      
        // print all donor and aids donated
        for (int i = 0; i < aids_array.size(); i++) 
            output.add(aids_array.get(i).toString());
        output.add("------------------------------------------");

        
        output.add(""); // empty newline


        output.add("-> All NGOs and their respective requests:");

        // print column name
        output.add("------------------------------------------");
        output.add(String.format("%-10s%-14s%-10s%-10s", "NGO", "Manpower", "Request" , "Quantity"));
        output.add("------------------------------------------");

        // print all NGOs and their request
        for (int i = 0; i < req_array.size(); i++) 
            output.add(req_array.get(i).toString());
        output.add("------------------------------------------");

        return output;
    }
    

    public ArrayList<String> match_one_to_one ()
    {
        ArrayList<String> output = new ArrayList<>();
        output.add(get_title());

        ArrayList<Aids> aids_array_2 = copy_aids_array(aids_array); // get a separate copy of aids_array
        ArrayList<Request> req_array_2 = copy_req_array(req_array); // get a separate copy of req_array
       
        // match pairs where aids.quantity == req.quantity
        for(Aids aids : aids_array_2)
        {
            for(Request req : req_array_2)
            {
                if( aids.get_quantity() == 0 ) break;

                if( req.get_quantity() != 0 && 
                    aids.get_item().equals(req.get_item()) && 
                    aids.get_quantity() == req.get_quantity())
                { 
                    output.add(get_row(aids.get_donor().get_id(), aids.get_donor().get_phone(), aids.get_item(), req.get_quantity(), req.get_NGO().get_id(), req.get_NGO().get_power()));
                    output.add(""); // empty line

                    aids.set_quantity(0);
                    req.set_quantity(0);
                    break;
                }    
            }    
        }
        output.add("---------------------------------------------------------");

        return output;
    }

    public ArrayList<String> match_one_to_many ()
    {
        ArrayList<String> output = new ArrayList<>();
        output.add(get_title());

        ArrayList<Aids> aids_array_2 = copy_aids_array(aids_array); 
        ArrayList<Request> req_array_2 = copy_req_array(req_array);

        boolean newline = false;

        for (Aids aids : aids_array_2)
        {
            for (Request req : req_array_2)
            {
                if (aids.get_quantity() == 0) break;
                
                if (req.get_quantity() != 0 && 
                    aids.get_item().equals(req.get_item()) && 
                    aids.get_quantity() >= req.get_quantity())
                {
                    output.add(get_row(aids.get_donor().get_id(), aids.get_donor().get_phone(), aids.get_item(), req.get_quantity(), req.get_NGO().get_id(), req.get_NGO().get_power()));

                    aids.set_quantity(aids.get_quantity() - req.get_quantity());
                    req.set_quantity(0);

                    newline = true;
                }    
            }   
            if(newline)
            {
                output.add("");
                newline = false;
            }        
        }
        // print remaining aids
        for(Aids i : aids_array_2)
            if(i.get_quantity() != 0)
                output.add(get_row(i.get_donor().get_id(), i.get_donor().get_phone(), i.get_item(), i.get_quantity(), "-", "-"));
        
        output.add("---------------------------------------------------------");

        return output;
    }

    public ArrayList<String> match_many_to_one ()
    {
        ArrayList<String> output = new ArrayList<>();
        output.add(get_title());

        ArrayList<Aids> aids_array_2 = copy_aids_array(aids_array); 
        ArrayList<Request> req_array_2 = copy_req_array(req_array);

        boolean newline = false;
        
        for(Request req : req_array_2)
        {
            for (Aids aids : aids_array_2) 
            {
                if (req.get_quantity() == 0)break;

                if( aids.get_quantity() != 0 &&
                    aids.get_item().equals(req.get_item()) &&
                    req.get_quantity() >= aids.get_quantity())
                {
                    output.add(get_row(aids.get_donor().get_id(), aids.get_donor().get_phone(), aids.get_item(), aids.get_quantity(), req.get_NGO().get_id(), req.get_NGO().get_power()));

                    req.set_quantity(req.get_quantity() - aids.get_quantity());
                    aids.set_quantity(0);

                    newline = true;
                }  
            }
            if(newline)
            {
                output.add("");
                newline = false;
            }
        }   

        // print remaining req
        for(Request i : req_array_2 )
            if(i.get_quantity() != 0) 
                output.add(get_row("-", "-", i.get_item(), i.get_quantity(), i.get_NGO().get_id(), i.get_NGO().get_power()));
        
        output.add("---------------------------------------------------------");

        return output;
    }

    public ArrayList<String> match_many_to_many ()
    {
        ArrayList<String> output = new ArrayList<>();
        output.add(get_title());

        ArrayList<Aids> aids_array_2 = copy_aids_array(aids_array); 
        ArrayList<Request> req_array_2 = copy_req_array(req_array);

        boolean newline = false;
        
        for (Aids aids : aids_array_2)
        {
            for (Request req : req_array_2)
            {
                if(aids.get_quantity() == 0) // if current aids_quantity == 0
                    break; // move on to next aid
            
                if(req.get_quantity() == 0 || !(aids.get_item().equals(req.get_item()))) // if req_quantity == 0 || item type doesnt match 
                    continue; // skip this req

                
                if(aids.get_quantity() >= req.get_quantity()) // if aid > req
                {
                    output.add(get_row(aids.get_donor().get_id(), aids.get_donor().get_phone(), aids.get_item(), req.get_quantity(), req.get_NGO().get_id(), req.get_NGO().get_power()));
                    
                    aids.set_quantity(aids.get_quantity() - req.get_quantity());
                    req.set_quantity(0);

                    newline = true;
                }
                else // else req > aid
                {
                    output.add(get_row(aids.get_donor().get_id(), aids.get_donor().get_phone(), aids.get_item(), aids.get_quantity(), req.get_NGO().get_id(), req.get_NGO().get_power()));

                    req.set_quantity(req.get_quantity() - aids.get_quantity());
                    aids.set_quantity(0);

                    newline = true;
                }
            }
            if(newline)
            {
                output.add("");
                newline = false;
            }
        }   

        // print remaining aids
        for(Aids i : aids_array_2)
            if(i.get_quantity() != 0)
                output.add(get_row(i.get_donor().get_id(), i.get_donor().get_phone(), i.get_item(), i.get_quantity(), "-", "-"));
               
        // print remaining req
        for(Request i : req_array_2 )
            if(i.get_quantity() != 0) 
                output.add(get_row("-", "-", i.get_item(), i.get_quantity(), i.get_NGO().get_id(), i.get_NGO().get_power()));
    
        output.add("---------------------------------------------------------");

        return output;
    }

    public ArrayList<String> get_donor_view (String donor_id)
    {
        ArrayList<String> output = new ArrayList<>();
        output.add(get_title());

        ArrayList<Aids> aids_array_2 = copy_aids_array(aids_array); 
        ArrayList<Request> req_array_2 = copy_req_array(req_array);
        

        for (Aids aids : aids_array_2)
        {
            for (Request req : req_array_2)
            {
                if(aids.get_quantity() == 0)
                    break;
                
                if(req.get_quantity() == 0 ||  !(aids.get_item().equals(req.get_item()))  )
                    continue;
                
              
                if(aids.get_quantity() >= req.get_quantity())
                {
                    if(aids.get_donor().get_id().equals(donor_id)) // new rule
                        output.add(get_row(aids.get_donor().get_id(), aids.get_donor().get_phone(), aids.get_item(), req.get_quantity(), req.get_NGO().get_id(), req.get_NGO().get_power()));
                    
                    aids.set_quantity(aids.get_quantity() - req.get_quantity());
                    req.set_quantity(0);
                }
                else
                {
                    if(aids.get_donor().get_id().equals(donor_id)) // new rule
                        output.add(get_row(aids.get_donor().get_id(), aids.get_donor().get_phone(), aids.get_item(), aids.get_quantity(), req.get_NGO().get_id(), req.get_NGO().get_power()));

                    req.set_quantity(req.get_quantity() - aids.get_quantity());
                    aids.set_quantity(0);
                }
            }    
        }

        // print remaining aids
        for(Aids i : aids_array_2)
            if(i.get_quantity() != 0 && i.get_donor().get_id().equals(donor_id)) // new rule
                output.add(get_row(i.get_donor().get_id(), i.get_donor().get_phone(), i.get_item(), i.get_quantity(), "-", "-"));
            
        output.add("---------------------------------------------------------");

        return output;
    }   

    public ArrayList<String> get_NGO_view (String NGO_id)
    {
        ArrayList<String> output = new ArrayList<>();
        output.add(get_title());

        ArrayList<Aids> aids_array_2 = copy_aids_array(aids_array); 
        ArrayList<Request> req_array_2 = copy_req_array(req_array);

        for (Aids aids : aids_array_2)
        {
            for (Request req : req_array_2)
            {
                if(aids.get_quantity() == 0)
                    break;
                
                if(req.get_quantity() == 0 ||  !(aids.get_item().equals(req.get_item()))  )
                    continue;
                
              
                if(aids.get_quantity() >= req.get_quantity())
                {
                    if(req.get_NGO().get_id().equals(NGO_id)) // new rule
                        output.add(get_row(aids.get_donor().get_id(), aids.get_donor().get_phone(), aids.get_item(), req.get_quantity(), req.get_NGO().get_id(), req.get_NGO().get_power()));
                    
                    aids.set_quantity(aids.get_quantity() - req.get_quantity());
                    req.set_quantity(0);
                }
                else
                {
                    if(req.get_NGO().get_id().equals(NGO_id)) // new rule
                        output.add(get_row(aids.get_donor().get_id(), aids.get_donor().get_phone(), aids.get_item(), aids.get_quantity(), req.get_NGO().get_id(), req.get_NGO().get_power()));

                    req.set_quantity(req.get_quantity() - aids.get_quantity());
                    aids.set_quantity(0);
                }
            }    
        }
   
        // print remaining req
        for(Request i : req_array_2 )
            if(i.get_quantity() != 0 && i.get_NGO().get_id().equals(NGO_id)) 
                output.add(get_row("-", "-", i.get_item(), i.get_quantity(), i.get_NGO().get_id(), i.get_NGO().get_power()));
    
        output.add("---------------------------------------------------------");

        return output;
    } 

    

    // utility functions----------------------------------------------------------------------------------------------------------------

    // UPDATED
    private <T,V> String get_row(String donor, String phone, String aids, T quantity, String NGO, V manpower)
    {
        return String.format("%-10s%-14s%-10s%-10s%-5s%-1s", donor, phone, aids, quantity, NGO, manpower);
    }

    // UPDATED
    private String get_title()
    {
        String output;

        output = get_row("Donor", "Phone", "Aids", "Quantity", "NGO", "Manpower");
        output += "\n---------------------------------------------------------";

        return output;
    }

    private Aids clone_aids (Aids original) // returns new aid obj with the same values
    {
        return new Aids(original.get_donor(), original.get_item(), original.get_quantity());
    }
    
    private Request clone_req (Request original) // returns new req obj with the same values
    {
        return new Request(original.get_NGO(), original.get_item(), original.get_quantity());
    }

    private ArrayList<Aids> copy_aids_array (ArrayList<Aids> original) // returns a new copy of ArrayList<aids>
    {
        ArrayList<Aids> copy = new ArrayList<>();

        for (Aids i : original) 
            copy.add(clone_aids(i));    
        
        return copy;
    }

    private ArrayList<Request> copy_req_array (ArrayList<Request> original) // returns a new copy of ArrayList<Request>
    {
        ArrayList<Request> copy = new ArrayList<>();

        for (Request i : original) 
            copy.add(clone_req(i));    

        return copy;
    }

    private int get_int(String prompt) // get integer with try catch
    {
        int output;
        while (true) {
            try {
                System.out.print(prompt);
                output = scan.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("\n-> Invalid input, please enter an integer.\n");
                scan.nextLine();
            }
        }
        return output;
    }
}
