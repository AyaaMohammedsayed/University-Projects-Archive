package PROJECT;
public class Invoice {
    public static void main(String[] args)   {
        FootballShop p1 = new SoccerBall("Addidas",0,3,2);
        FootballShop p2 = new Boots ("PUMA",49.00,3,42);
        FootballShop p3 = new Boots ("NiKe",50.00,5,46);
     
        FootballShop p4 = new SoccerBall("PUMA",0,4,5);
         Customer c = new Customer("Jemmy Adams");
        System.out.println(c);
         c.buy(p1);
        c.buy(p2);
        c.buy(p3);
        c.buy(p4);



       /*  special purpose*/ 
   
       /* 
         FootballShop p5 = new Boots ("NiKe",50.00,5,46); c.buy(p5);
        c.buy(p4);
        c.buy(p2);
        c.buy(p1);*/
        c.print();


    }
}
