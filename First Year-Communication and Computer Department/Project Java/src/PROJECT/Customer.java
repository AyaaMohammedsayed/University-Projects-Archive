package PROJECT;

import java.text.NumberFormat;

public class Customer {
    /* this class make Aggregation  relation with FootBallShop Class */

    /* this is  the   data member of Customer class  */
    private String custName;
    private FootballShop[]itemList=new FootballShop[Boots.n];
    private int numOfitems;
    private double totalPay;
    /*for special purpose */
    int []ar=new int[Boots.n];
    
    static int i=0;

    
    /* public Constructor with String  argument that will intial Name Customer */
    public Customer(String Name)
    {
        this.custName=Name;

    }
    /*the getter function or accessor method to get the Customer name item */
        public String getName()
        {
           return this.custName;
        }
        
     /* the buy method of Customer class */
    
    public void buy(FootballShop product)
    {
        NumberFormat fmt=   NumberFormat.getCurrencyInstance();
     /*if the same item is printed again  the special purpose*/  
        if(this.numOfitems<Boots.n)
        {
            ++numOfitems;
            ar[i]=numOfitems;
        
            System.out.println("Item "+this.getNumOfitems());
            try
            {
                itemList[i]=product;
             
             
                this.itemList[i].display();
             

               totalPay+= this. itemList[i].calcPrice();// total intially =0
           
            }
            catch(NotBallTypeException e1)
            {
                System.out.println(e1.getMessage());
                itemList[i].setPrice(50);
                System.out.println("SOCCER BALL"+"\n"+ "Brand:" +   itemList[i].getBrand() + "\n"+ "Type: Training" + "\n" +"Price:" +  
                           fmt.format(  itemList[i].getPrice())  + "\n"+" No Discount !"+"\n"+"Quantity:"+ 
                           itemList[i].getQuantity() + "\n"+"subtotal "+fmt.format(  itemList[i].calcPrice())+"\n" );
                         
                totalPay+=  itemList[i].calcPrice();
            
            }
         i++;

          

           
           

        }
        else
        {
            for(int j=0;j<Boots.n;j++)
            {
              
               
                if(itemList[j]==product)
                {
                    System.out.println("Item "+ar[j]);
                    try
                    {
                        
                     
                     
                        this.itemList[j].display();
                     
        
                       totalPay+= this. itemList[j].calcPrice();// total intially =0
                      
                    }
                    catch(NotBallTypeException e1)
                    {
                        System.out.println(e1.getMessage());
                        itemList[j].setPrice(50);
                        System.out.println("SOCCER BALL"+"\n"+ "Brand:" +  itemList[j].getBrand() + "\n"+ "Type: Training" + "\n" +"Price:" +  
                                   fmt.format( itemList[j].getPrice())  + "\n"+" No Discount !"+"\n"+"Quantity:"+ 
                                   itemList[j].getQuantity() + "\n"+"subtotal "+fmt.format(  itemList[j].calcPrice())+"\n" );
                                 
                        totalPay+= itemList[j].calcPrice();
                     
                    }
        

                }
            
                   
                
            }
            
        }
    
        
          
          
     
         
        
       
    

          
       
          
            
        
      
    }
      /*the getter function or accessor method to get thegetNumOfitems */
    public int getNumOfitems()
    {
        return this.numOfitems;
    }
    /* welcome method for Customer */
    public String toString()
    {
        return ("Welcome "+ this.getName()+"\n\n"+"List of FootBall Items Bought \n");
    }
     /*the getter function or accessor method to get totalPay item */
    public double getTotalPay()
    {
        return this.totalPay;
    }
    /* print method for print total price */
    public void print()
    {
        NumberFormat fmt=   NumberFormat.getCurrencyInstance();
        System.out.println("TOTAL: "+fmt.format(this.getTotalPay()));
    }

}
