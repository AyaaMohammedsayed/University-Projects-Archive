<?php

include "accounts.php"; 
  
$t=false;

if(isset($_POST["submit"]))
{
   
    //    check if the user eamil is register before so follo to website
        foreach ($accounts as $x => $y) {
          
            if($x == $_POST["email"] &&$y==$_POST["password"])
            {
               
                $t=true;
                header("Location:../Home/home-out.html");
                break;
                

            }
    
        }
    //  else he must register first
        if($t== false)
        {

        header("Location:../Register/register.html");
        }

        

        
    

    
}

?>
