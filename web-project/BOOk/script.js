const form =document.querySelector("form");
const fullName=document.getElementById("name");
const mail=document.getElementById("email");
const Phone=document.getElementById("phonenum");
const Age=document.getElementById("age");
const Departure=document.getElementById("Departure");
const Return=document.getElementById("Return");
const Subject=" ";
var Gender="male";
var Destination=" ";
var package=" ";

function sendEmail()
{
    // Gender determine
    if(document.getElementById('Gendermale').checked) {   

        var Gender="Male";
     }   
     else if(document.getElementById('Genderfemale').checked) {   
      
         var Gender="Female";
     } 
    //  Destination determine
     if(document.getElementById('Pyramids').checked) {   

         var Destination ="Pyramids";
    }   
    else if(document.getElementById('DAHAB').checked) {   
    
         var Destination ="DAHAB";
    }  
    else if(document.getElementById('Sinaa').checked) {   
    
        var  Destination ="Sinaa";
    }  
    else if(document.getElementById('Luxor').checked) {   
    
       var  Destination ="Luxor";
    }  
    else if(document.getElementById('Alexandria').checked) {   
    
      var   Destination ="Alexandria";
    }  
    else if(document.getElementById('NOBA').checked) {   
    
         var Destination ="NOBA";
    }  
    else if(document.getElementById(' Old Egypt ').checked) {   
    
       var Destination =" Old Egypt ";
    }  
    else if(document.getElementById('Siwaa').checked) {   
    
       var Destination =" Siwaa ";
    } 
    // package determine  
    if(document.getElementById('Bronze').checked) {   

       var package ="Bronze";
    }   
    else if(document.getElementById('Silver').checked) {   
    
       var package ="Silver";
    }  
    else if(document.getElementById('Gold').checked) {   
    
         var package ="Gold";
    }  
    else if(document.getElementById('Platinum').checked) {   
    
         var package ="Platinum";
    }  
    // body of message
    const bodyMessage="The Fullname: "+fullName.value+"<br>" +"The Email: "+mail.value+"<br>"+ "The Phone: "+Phone.value+"<br>"+"The Age: "+Age.value+"<br>"+
     "The Departure: "+Departure.value+"<br>"+
    "The Return: "+Return.value+"<br>"+"The Gender: "+Gender+"<br>"+"The Destination: "+Destination+"<br>"+"The package: "+package+"<br>";
    Email.send({
        // security
        SecureToken :"46d5afa1-66df-4900-900f-2082ed22e521",

        To : "ayahame99@gmail.com",
        From : document.getElementById("email").value,
        Subject : Subject,
        Body : bodyMessage
    }).then(
      message =>
      {
        if(message=='OK')
        {
            Swal.fire({
                title: "Success!",
                text: "Message sent successfully!",
                icon: "success"
              });
        }
      }
    );
}
function checkInputs()
{
    const items =document.querySelectorAll(".item");
    for(const item of items)
    {
        if(item.value=="")
        {
            item.classList.add("error");
            item.parentElement.classList.add("error");
        }
        item.addEventListener("keyup",()=>
        {
            if(item.value!="")
            {
                item.classList.remove("error");
                item.parentElement.classList.remove("error");

            }

        })
    }
}

// calling methods
form.addEventListener("submit",(e)=>
{
    e.preventDefault();
    checkInputs();
    sendEmail();
  
}
);