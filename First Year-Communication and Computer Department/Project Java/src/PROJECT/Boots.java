package PROJECT;

import java.text.NumberFormat;

public class Boots extends FootballShop implements DiscConsiderable
{
  
/* this is  the  special data member of Boots class  */

    private int size;
     /*for special purpose */
       public static int n=0;
 /* public Constructor with no arguments that will do No thing */
    public Boots()
    {
     n++;
    }

 /* public constructor with arguments that will intialize all the data items   */
 public Boots(String brand, double price ,int quantity, int size)
 {
    /*calling the Super class Constructor */
    super(brand,price,quantity);
    this.size=size;
    n++;

 }

 /* implemntation of interface  class DiscConsiderable  */
 public double calcDisc()
 {
    double Disc_value =this.getPrice()*RATE*getQuantity();
    double New_Price= this.getPrice()*getQuantity()-Disc_value;
    return New_Price;
 }  

  /* implemntation of Super  class FootballShop  Abstract method display */
 public void display()
 {
    NumberFormat fmt=   NumberFormat.getCurrencyInstance();
    System.out.println("SOCCER BOOTS\n"+"Brand : " + this.getBrand() + " \n"  + "Price: " + fmt.format(this.getPrice())
                          +" for Size: " + this.size + "\n" + "Discount: " + RATE*100+"%"+ "\n" +"Quantity: "+ this.getQuantity()+
                          "\n" + "SubTotal: "+fmt.format(this.calcPrice())+ "\n" );
   


 }
  /* implemntation of Super  class FootballShop  Abstract method calcPrice */
 public  double calcPrice()
 {
    return this.calcDisc();

 }





}


