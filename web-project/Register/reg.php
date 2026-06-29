<?php
$nameError=$emailError=$pass1error=$pass2error="";
$account=array(""=>"");

if(isset($_POST['submit']))
{
    $name=$_POST['username'];
    $email=$_POST['email'];
    $pass1=$_POST['password'];
    $pass2=$_POST['configpassword'];
    if(preg_match('/[^a-zA-Z ]/ ',$name))
    {
        $nameError="Only letters are allowed";
       echo "Only letters are allowed";
       

    }
    $lowr=preg_match("/[a-z]/ ",$pass1);
    $upper=preg_match("/[A-Z]/ ",$pass1);
    $special=preg_match("/[^a-zA-Z]/ ",$pass1);
    $numb=preg_match("/[0-9]/ ",$pass1);
    if(strlen($pass1 )<8)
    {
        $pass1error="Follow rules please";
        echo $pass1error;
    }
    if($pass1!=$pass2)
    {
        $pass2error= "passesd password not matched";
        echo $pass2error;

    }
    if(empty($nameError)&&empty($emailError)&&empty($pass1error) &&empty($pass2error))
    {
        
            $account[$email]=$pass1;
       
          
          

            header("Location:../Home/home-login.html");
            

            exit();
    }
    header('Refresh:5');
}


?>

