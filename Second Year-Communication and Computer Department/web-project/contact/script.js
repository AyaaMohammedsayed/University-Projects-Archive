const form =document.querySelector("form");
const fullName=document.getElementById("name");
const mail=document.getElementById("email");
const Phone=document.getElementById("phone");
const Subject=document.getElementById("subject");
const Message=document.getElementById("Message");
// sending email
function sendEmail()
{
    const bodyMessage="The Fullname: "+fullName.value+"<br>" +"The Email: "+mail.value+"<br>"+ "The Phone: "+Phone.value+"<br>"+"The Subject: "+Subject.value+"<br>"+"The Message: "+Message.value+"<br>";
    Email.send({
        // scurity section
        SecureToken : "46d5afa1-66df-4900-900f-2082ed22e521",

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