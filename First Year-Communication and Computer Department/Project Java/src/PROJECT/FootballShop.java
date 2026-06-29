package PROJECT;

public abstract class FootballShop {
 

    /* this is  the data member of FootBallShop Astract Super calss  */
     private String brand ;
     private double price;
     private int quantity;
 

     /* public Constructor with no arguments that will do No thing */
     public FootballShop()
     {
     

     }

     /* public constructor with arguments that will intialize all the data items   */
     public FootballShop(String brand, double price ,int quantity)
     {
       
      
        this.brand=brand;
        this.price= price;
        this.quantity=quantity;
     }

     /* the setter function or mutator function to set the price data item */
     public void setPrice(double price)
     {
        this.price=price;
     }

     /*the getter function or accessor method to get the brand name item */
     public String getBrand()
     {
        return this.brand;
     }

     /* the getter method of price item */
     public double getPrice()
     {
        return this.price;
     }
     
     /* the getter method of quantity item */
     public int getQuantity()
     {
        return this.quantity;
     }
     
     /*Abstact methods for sub classes */
     public abstract void display() throws NotBallTypeException  ;
     public abstract double calcPrice();

}
