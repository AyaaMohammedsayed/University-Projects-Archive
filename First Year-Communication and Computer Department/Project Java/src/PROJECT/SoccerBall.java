package PROJECT;

import java.text.NumberFormat;

public class SoccerBall extends FootballShop {
    
 /* this is  the special  data member of   SoccerBall class  */
    private  int ballType;

 /* public Constructor with no arguments that will do No thing */
     public SoccerBall()
     {
      /*for special purpose */
      Boots.n++;
 
     }

 /* public constructor with arguments that will intialize all the data items   */
 public SoccerBall(String brand, double price ,int quantity, int ballType)
 {
    /*calling the Super class Constructor */
    super(brand,price,quantity);
    this. ballType=ballType;
    /*for special purpose */
    Boots.n++;
   

 }

/* implemntation of Super  class FootballShop  Abstract method display */
 public void display() throws NotBallTypeException 
 {
    
    NumberFormat fmt=   NumberFormat.getCurrencyInstance();
   switch(this.ballType)
   {
       case 1:
       this.setPrice(200);
       System.out.println(  " SOCCER BALL"+"\n"+"Brand: " + this.getBrand() + "\n" +
                           "Type: Professional  Match" + "\n" +"Price:" +  fmt.format(this.getPrice())  + "\n"+" No Discount !"+"\n"+"Quantity: "+ 
                           this.getQuantity() + "\n"+"subtotal: "+fmt.format(this.calcPrice())+"\n" );
       break;
       case 2:
       this.setPrice(80);
       
       System.out.println("SOCCER BALL"+"\n"+ "Brand: " + this.getBrand() + "\n"+
                          "Type:  Match" + "\n" +"Price: " +  fmt.format(this.getPrice())  + "\n"+"No Discount !"+"\n"+"Quantity: "+ 
                           this.getQuantity() + "\n"+"subtotal: "+fmt.format(this.calcPrice())+"\n" );
       break;
       case 3:
       this.setPrice(50);
       System.out.println("SOCCER BALL"+"\n"+ "Brand: " + this.getBrand() + "\n"+ 
                          "Type: Training" + "\n" +"Price: " +  fmt.format(this.getPrice())  + "\n"+" No Discount !"+"\n"+"Quantity: "+ 
                           this.getQuantity() + "\n"+"subtotal: "+fmt.format(this.calcPrice())+"\n" );
       break;
       case 4:
       this.setPrice(20);
       System.out.println("SOCCER BALL"+"\n"+ "Brand: " + this.getBrand() + "\n"+ 
                          "Type: Recreational " + "\n" +"Price: " +  fmt.format(this.getPrice())  + "\n"+" No Discount !"+"\n"+"Quantity: "+ 
                           this.getQuantity() + "\n"+"subtotal: "+fmt.format(this.calcPrice())+"\n" );
       break;
       default:
       System.out.println("SOCCER BALL"+"\n"+ "Brand: " + this.getBrand() + "\n");
       
       throw new NotBallTypeException( this.ballType+ "  Not a valid soccer ball type , Changing the soccer ball type to training ball \n");
         
       
      
    
       
      }
      
   
   


 }

 
 
/* implemntation of Super  class FootballShop  Abstract method calcPrice */
 public  double calcPrice()
 {
    double totalPrice =this.getPrice()*this.getQuantity();
    return totalPrice;

 }

   
    
}
