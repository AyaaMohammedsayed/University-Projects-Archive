#Team Members: Aya Mohamed ,Bosina Saad , Habiba Ahmed & Nadia Kamel

#Shop Cart Using Vector as DS 

#Team 1
######################################################## Data segment############################################################
.data
# messages for Interaction with User

    prompt1: .asciiz "\n\n\t\t****Welcome to the E-commerce Website****\n\n"
    prompt2: .asciiz "1) Signup\n2) Login\n3) Exit\n\nPlease Enter Your choice : "
    prompt3: .asciiz "\n\nThanks for Visiting!!\n\n "
    prompt4: .asciiz "Please Enter a valid choice\n\n"
    prompt5: .asciiz "\n\n\t******************Welcome to Signup Page*********************\n\n"
    prompt6: .asciiz "\tEnter Your name : "
    prompt7: .asciiz "\tEnter Your Email : "
    prompt8: .asciiz "\tConfirm Password : "
    prompt9: .asciiz "\tEnter Your Mobile Number : "
    prompt10: .asciiz "\tEnter Your Age : "
    prompt11: .asciiz "\n\n\t******************Welcome to Login Page********************\n\n"
    prompt12: .asciiz "\n\nLOGIN\n\n"
    prompt13: .asciiz "\tEnter Your Password: "
    prompt16: .asciiz "\n\nWelcome "
    prompt17: .asciiz ", You are successfully logged in\n\n "
    prompt18: .asciiz "\n\nAccount doesn't exist. Please login again!!\n\n "
    prompt19: .asciiz "\n\nThanks! Your item is canceled!! Exiting..\n\n"
    prompt20: .asciiz "\n\nDo You wish to order (y=1/n=0) : "
    prompt21 :.asciiz "\n\nThank You "
    prompt22: .asciiz  "Please Enter the Count of "
    
cancel_or_exit_options: .asciiz "\nTo cancel Your Order = 1\nTo Exit & just Save = 0\nSelect option: "
order_confirmation_message: .asciiz " for your Order\nYour item is on the way. Welcome again \n\n"
error_invalid_name:    .asciiz "\nPlease enter a valid name!\n"   
pop_back_error_str: .asciiz  "Vector is empty"
index_error_msg: .asciiz "Index out of range\n"
email_invalid_str:         .asciiz "\nPlease Enter a Valid E-Mail\n\n"
password_strong_str:       .asciiz "\n\nPlease Enter a strong password. Your password must contain at least one uppercase, lowercase, number, and special character.\n\n"
password_length_str:       .asciiz "\nYour Password's Length must be between 8 to 12\n\n"
password_mismatch_str:     .asciiz "\nPassword Mismatch\n\n"
age_invalid_str:           .asciiz "\n\nPlease Enter a valid age\n\n"
mobile_number_invalid_str: .asciiz "\n\nPlease Enter an 11-digit mobile number\n\n"
account_exists_str:        .asciiz "\n\nAccount already exists. Please login!\n\n"
account_created_str:       .asciiz "\n\n\nAccount successfully created!\n\n"
account_limit_str:         .asciiz "\n\nCannot create more accounts. Limit reached!\n\n"
search_option_str:       .asciiz "We provide two ways of search: \n "
search_by_shop_str:      .asciiz "1) Search by Shop\n "
search_by_item_str:      .asciiz "2) Search by Item\n "
exit_str:                .asciiz "3) Exit\n\n"
choice_str:              .asciiz "Please Enter your choice : "
choose_shop_str:        .asciiz "\n\nPlease Choose the Shop \n\n1) "
shop_exit_str:          .asciiz "\n4) Exit\n\nPlease select the shop\t"
item_list_str:          .asciiz "\n\nList of items available in "
cart_exit_st:          .asciiz "\n4) Cart\n5) Exit\n\n"
choose_item_str:        .asciiz "\n\nPlease choose the item\n\n"
cart_header_str:        .asciiz "\n\n\n\n\t*********************************Cart*********************************\n\t\t\t "
cart_amount_str:        .asciiz "\nElement \t\t\tprice\t\t\tAmount\n"
total_order_str:        .asciiz "\n\nYour Total Order Amount is : "
remove_item_str:        .asciiz "\n\nDo you want to remove the last item from the cart? (y=1/n=0) : "
cart_exit_str: .asciiz "10) Cart\n11) Exit\n"
Space : .asciiz " \t--> " 
newline:   .asciiz "\n"
Lastisremoved: .asciiz "\n\nLast item removed from the cart.\n\n"

# Add variables here
t_name:         .space  64          
name:         .space  64          
t_email:        .space  64          
t_password1:    .space  12          
pass:    .space  12          
t_mobile:       .space  12          
user_name:      .space  64        
t_age:          .word   0           
# Structure: Shops
.Puma:
    .asciiz "Puma"
.puma_sneaker_V2:
    .asciiz "1)puma sneaker V2"
.mens_dryflex:
    .asciiz "2)mens dryflex"
.one8x_puma:
    .asciiz "3)one8x puma"
.USA:
    .asciiz "\n2)USA"
.US_polo_Tshirt:
    .asciiz "4)US polo Tshirt"
.Shoes:
    .asciiz "5)Shoes"
.shirt:
    .asciiz "6)shirt"
.WROGN:
    .asciiz "\n3)WROGN"
.Mens_Watch:
    .asciiz "7)Mens Watch"
.mens_solid_jacket:
    .asciiz "8)mens solid jacket"
.casual_Tshirt:
    .asciiz "9)casual T-shirt"

# Structure: purchases
purchases_size: .word 16    
purchases_item: .space 32  
purchases_price: .word 0    
purchases_n_item: .word 0   


# Shops vector
 Mole:           .space 160  
 p:     .space 1
 # Text segment
 .text
.globl main
main:

jal shop_initialize
jal purchese

  
    li $v0, 4
    la $a0, prompt1
    syscall
 
    li $v0, 4
    la $a0,  prompt2
    syscall
  
    li $v0, 5
    syscall
    move $t1, $v0

    switch:
    beq $t1, 1, signup
    beq $t1, 2, login
    beq $t1, 3, exit_program
    li $v0, 4
    la $a0, prompt4
    syscall
    j main
   exit_program:
  li $v0, 4
  la $a0, prompt3
    syscall
  li $v0 10
  syscall

signup:
    li $v0, 4
    la $a0, prompt5
    syscall
     
    li $v0, 4
    la $a0, prompt6
    syscall
    li $v0, 8
    la $a0, t_name
    li $a1, 64
    syscall
    
    la $t3, t_name
    li $v0, 4
    la $a0, prompt7
    syscall
    li $v0, 8
    la $a0, t_email
    li $a1, 64
    syscall

    li $v0, 4
    la $a0, prompt13
    syscall
    li $v0, 8
    la $a0, t_password1
    li $a1, 12
    syscall
    la $t4, t_password1

    li $v0, 4
    la $a0, prompt9
    syscall
    li $v0, 8
    la $a0, t_mobile
    li $a1, 12
    syscall

    li $v0, 4
    la $a0, prompt10
    syscall
    li $v0, 5
    syscall
    move $t5, $v0 
    
    li $v0, 4
    la $a0, account_created_str
    syscall
    j login

login:
    li $v0, 4
    la $a0, prompt11
    syscall
    
    li $v0, 4
    la $a0, prompt6
    syscall
    li $v0, 8
    la $a0, name
    li $a1, 64
    syscall
    
    li $v0, 4
    la $a0, prompt13
    syscall
    li $v0, 8
    la $a0, pass
    li $a1, 12
    syscall
    
    la $t0, name
    la $t1, t_name

    
    compare_loop:
        lb $t2, 0($t0)  
        lb $t3, 0($t1)  

       
        beq $t2, $zero, strings_equal  
        beq $t3, $zero, strings_not_equal  
        bne $t2, $t3, strings_not_equal  
        addi $t0, $t0, 1 
        addi $t1, $t1, 1  
        j compare_loop 

    
    strings_equal:
        j pass_check

   
    strings_not_equal:
        li $v0, 4
        la $a0, prompt18
        syscall
        j main

pass_check:
    la $t0, pass
    la $t1, t_password1

   
    Compare_loop:
        lb $t2, 0($t0)  
        lb $t3, 0($t1)  

       
        beq $t2, $zero, Strings_equal  
        beq $t3, $zero, Strings_not_equal  
        bne $t2, $t3, Strings_not_equal  
        addi $t0, $t0, 1  
        addi $t1, $t1, 1 
        j Compare_loop 

  
    Strings_equal:
        li $v0, 4
        la $a0, prompt16
        syscall
        li $v0, 4
        la $a0, t_name
        syscall
        li $v0, 4
        la $a0, prompt17
        syscall
        j StartShopping

  
    Strings_not_equal:
        li $v0, 4
        la $a0, prompt18
        syscall
        j main

  
shop_initialize:
    addi $sp, $sp, -8      
    sw $ra, 4($sp)         
   
    li $a0, 5
    jal Vector_constructor_int_n
   
    la $t0, Mole       
    li $t1, 1          

   
    la $t2, .Puma
    sw $t2, 0($t0)

    
    la $t2, .puma_sneaker_V2
    sw $t2, 4($t0)

   
    la $t2, .mens_dryflex
    sw $t2, 8($t0)

   
    la $t2, .one8x_puma
    sw $t2, 12($t0)

    
    li $t2, 3499
    sw $t2, 16($t0)

   
    li $t2, 2999
    sw $t2, 20($t0)

    
    li $t2, 5999
    sw $t2, 24($t0)

   
    addi $t0, $t0, 32  
    li $t1, 2          

    
    la $t2, .USA
    sw $t2, 0($t0)

    
    la $t2, .US_polo_Tshirt
    sw $t2, 4($t0)

    
    la $t2, .Shoes
    sw $t2, 8($t0)

   
    la $t2, .shirt
    sw $t2, 12($t0)

  
    li $t2, 999
    sw $t2, 16($t0)

   
    li $t2, 2000
    sw $t2, 20($t0)

    
    li $t2, 1499
    sw $t2, 24($t0)

   
    addi $t0, $t0, 32  
    li $t1, 3          

   
    la $t2, .WROGN
    sw $t2, 0($t0)

    
    la $t2, .Mens_Watch
    sw $t2, 4($t0)

    
    la $t2, .mens_solid_jacket
    sw $t2, 8($t0)

    
    la $t2, .casual_Tshirt
    sw $t2, 12($t0)

    
    li $t2, 2000
    sw $t2, 16($t0)

  
    li $t2, 2239
    sw $t2, 20($t0)

   
    li $t2, 799
    sw $t2, 24($t0)
    lw $ra, 4($sp)         
    addi $sp, $sp, 8      
    jr $ra                 
  
StartShopping:
    # Print the menu options
    li $v0, 4
    la $a0,search_option_str
    syscall

    li $v0, 4
    la $a0, search_by_shop_str
    syscall

    li $v0, 4
    la $a0, search_by_item_str
    syscall

    li $v0, 4
    la $a0, exit_str
    syscall

    li $v0, 4
    la $a0, choice_str
    syscall

    # Read the user's choice
    li $v0, 5
    syscall
    move $t0, $v0

    # Perform the switch case
    beq $t0, 1, searchByShop
    beq $t0, 2, searchByItem
    beq $t0,3, exit_program

    li $v0, 4
    la $a0,   error_invalid_name
    syscall
    j StartShopping

searchByShop:

    li $v0, 4
    la $a0,choose_shop_str
    syscall
   
print_shop_data:
    la $t0, Mole       
    li $t1, 1          

print_loop:


    lw $a0, 0($t0)     
    li $v0, 4
    syscall
# Move to next Mole element
    addi $t0, $t0, 32
    addi $t1, $t1, 1
    blt $t1, 4, print_loop 

     li $v0, 4
    la $a0,shop_exit_str
    syscall
    li $v0, 5
    syscall
li $t0, 0
ble $v0, $t0, invalid_choice 
li $t0, 4
bgt $v0, $t0, invalid_choice 


beq $v0, $t0, StartShopping 
move $a0,$v0
j Shop_selected
j exit_program 
invalid_choice:

li $v0, 4
la $a0, prompt4
syscall
j searchByShop

Shop_selected:
addi $s1,$a0,0
    la $s0, Mole       
     addi $t0,$s0,0
    li $t1, 1          # Index for Mole[1]

  li $t6,1
  beq $s1,$t6,puma
  li $t6,2
  beq $s1,$t6,usa
   addi $t0, $t0, 64
    addi $t1, $t1, 2
    printw:
    lw $a0, 4($t0)     # Load item1
    li $v0, 4
    syscall
    li $v0, 4
    la $a0, Space
    syscall

    lw $a0, 16($t0)    # Load first price
    li $v0, 1
    syscall
    li $v0, 4
    la $a0, newline
    syscall


    lw $a0, 8($t0)     # Load item2
    li $v0, 4
    syscall    
    
    li $v0, 4
    la $a0, Space
    syscall
    
     lw $a0, 20($t0)    # Load second price
    li $v0, 1
    syscall    
    li $v0, 4
    la $a0, newline
    syscall

    lw $a0, 12($t0)    # Load item3
    li $v0, 4
    syscall   
     li $v0, 4
    la $a0, Space
    syscall

    lw $a0, 24($t0)    # Load third price
    li $v0, 1
    syscall
    li $v0, 4
    la $a0, newline
    syscall
    
    la $a0,cart_exit_st
    li $v0,4
    syscall
     li $v0, 4
    la $a0, newline
    syscall
 
    la $a0,choice_str
    li $v0,4
    syscall
    li $v0, 4
    la $a0, newline
    syscall
    li $v0, 5
    syscall
    addi $t7,$v0,0#item
    blt $v0,1,invw
    bgt $v0,5,invw
    beq $v0,4,cart
    beq $v0,5,searchByShop
    la $a0,prompt22
    li $v0,4
    syscall
    li $v0,5
    syscall
    addi $t6,$v0,0#n
    beq $t7, 1, calculate_item1w
    beq $t7, 2, calculate_item2w
    j calculate_item3w

calculate_item1w:
      lw $t5, 16($t0)    # Load first price      # load Mole[shop_choice].first into $t1
    mul $t5, $t5, $t6      # multiply n with Mole[shop_choice].first
    add $s4,$s4,$t5
    lw $a0, 4($t0) #item
    lw $a1, 16($t0) #price
    move $a2, $t6   # n
 jal push_back
    j printw
calculate_item2w:
     lw $t5, 20($t0)    # Load second price
    mul $t5, $t5, $t6      # multiply n with Mole[shop_choice].first
    add $s4,$s4,$t5
    lw $a0, 8($t0) #item
    lw $a1, 20($t0) #price
    move $a2, $t6   # n
 jal push_back
    j printw
calculate_item3w:
    lw $t5, 24($t0)    # Load third price
    mul $t5, $t5, $t6      # multiply n with Mole[shop_choice].first
    add $s4,$s4,$t5
    lw $a0, 12($t0) #item
    lw $a1, 24($t0) #price
    move $a2, $t6   # n
    jal push_back
    j printw
    invw:
    la $a0,prompt4
    li $v0,4
    syscall
    j printw
  
  puma:
  
    lw $a0, 4($t0)     # Load item1
    li $v0, 4
    syscall
    li $v0, 4
    la $a0, Space
    syscall
        

    lw $a0, 16($t0)    # Load first price
    li $v0, 1
    syscall
    li $v0, 4
    la $a0, newline
    syscall


    lw $a0, 8($t0)     # Load item2
    li $v0, 4
    syscall
    li $v0, 4
    la $a0, Space
    syscall
    
     lw $a0, 20($t0)    # Load second price
    li $v0, 1
    syscall
li $v0, 4
    la $a0, newline
    syscall
    lw $a0, 12($t0)    # Load item3
    li $v0, 4
    syscall
        li $v0, 4
    la $a0, Space
    syscall

    lw $a0, 24($t0)    # Load third price
    li $v0, 1
    syscall
  li $v0, 4
    la $a0, newline
    syscall
    
    la $a0,cart_exit_st
    li $v0,4
    syscall
  li $v0, 4
    la $a0, newline
    syscall
 
    la $a0,choice_str
    li $v0,4
    syscall
    li $v0, 4
    la $a0, newline
    syscall
    li $v0, 5
    syscall
    addi $t7,$v0,0#item
    blt $v0,1,invp
    bgt $v0,5,invp
    
    beq $v0,4,cart
    beq $v0,5,searchByShop
    la $a0,prompt22
    li $v0,4
    syscall
    li $v0,5
    syscall
    addi $t6,$v0,0#n
    beq $t7, 1, calculate_item1p
    beq $t7, 2, calculate_item2p
    j calculate_item3p

calculate_item1p:
      lw $t5, 16($t0)    # Load first price      # load Mole[shop_choice].first into $t1
    mul $t5, $t5, $t6      # multiply n with Mole[shop_choice].first
    add $s4,$s4,$t5
lw $a0, 4($t0) #item
    lw $a1, 16($t0) #price
    move $a2, $t6   # n
   jal push_back
    j puma
calculate_item2p:
     lw $t5, 20($t0)    # Load second price
    mul $t5, $t5, $t6      # multiply n with Mole[shop_choice].first
    add $s4,$s4,$t5
lw $a0, 8($t0) #item
    lw $a1, 20($t0) #price
    move $a2, $t6   # n
   jal push_back
    j puma
calculate_item3p:
    lw $t5, 24($t0)    # Load third price
    mul $t5, $t5, $t6      # multiply n with Mole[shop_choice].first
    add $s4,$s4,$t5
    lw $a0, 12($t0) #item
    lw $a1, 24($t0) #price
    move $a2, $t6   # n
   jal push_back
    j puma
    invp:
    la $a0,prompt4
    li $v0,4
    syscall
    j puma
  usa:
  addi $t0, $t0, 32
    addi $t1, $t1, 1
    printu:
   lw $a0, 4($t0)     # Load item1
    li $v0, 4
    syscall
       li $v0, 4
    la $a0, Space
    syscall

    lw $a0, 16($t0)    # Load first price
    li $v0, 1
    syscall
    li $v0, 4
    la $a0, newline
    syscall


    lw $a0, 8($t0)     # Load item2
    li $v0, 4
    syscall
     li $v0, 4
    la $a0, Space
    syscall
    
     lw $a0, 20($t0)    # Load second price
    li $v0, 1
    syscall
     li $v0, 4
    la $a0, newline
    syscall

    lw $a0, 12($t0)    # Load item3
    li $v0, 4
    syscall
    li $v0, 4
    la $a0, Space
    syscall

    lw $a0, 24($t0)    # Load third price
    li $v0, 1
    syscall
     li $v0, 4
    la $a0, newline
    syscall
    
    la $a0,cart_exit_st
    li $v0,4
    syscall
     li $v0, 4
    la $a0, newline
    syscall
 
    la $a0,choice_str
    li $v0,4
    syscall
    
     li $v0, 4
    la $a0, newline
    syscall
    li $v0, 5
    syscall
    addi $t7,$v0,0#item
    blt $v0,1,inv
    bgt $v0,5,inv
    beq $v0,4,cart
    beq $v0,5,searchByShop
    la $a0,prompt22
    li $v0,4
    syscall
    li $v0,5
    syscall
    addi $t6,$v0,0#n
    beq $t7, 1, calculate_item1
    beq $t7, 2, calculate_item2
    j calculate_item3

calculate_item1:
      lw $t5, 16($t0)    # Load first price      # load Mole[shop_choice].first into $t1
    mul $t5, $t5, $t6      # multiply n with Mole[shop_choice].first
    add $s4,$s4,$t5
  lw $a0, 4($t0) #item
    lw $a1, 16($t0) #price
    move $a2, $t6   # n
   jal push_back
    j printu
calculate_item2:
     lw $t5, 20($t0)    # Load second price
    mul $t5, $t5, $t6      # multiply n with Mole[shop_choice].first
    add $s4,$s4,$t5
    lw $a0, 8($t0)
    lw $a1, 20($t0)    # Load second price
    move $a2, $t6  
   jal push_back
    j printu
calculate_item3:
    lw $t5, 24($t0)    # Load third price
    mul $t5, $t5, $t6      # multiply n with Mole[shop_choice].first
    add $s4,$s4,$t5
lw $a0, 12($t0)
    lw $a1,24($t0)    # Load second price
    move $a2, $t6   
   jal push_back
    j printu
    inv:
    la $a0,prompt4
    li $v0,4
    syscall
    j printu
    jr $ra
    

searchByItem:
    # Print the item choices
    li $v0, 4
    la $a0, choose_item_str
    syscall
print_shop:
    la $t0, Mole       # Load address of Mole
    li $t1, 1          # Index for Mole[1]

Print_loop:




    lw $a0, 4($t0)     # Load item1
    li $v0, 4
    syscall
    li $v0, 4
    la $a0, Space
    syscall
    lw $a0, 16($t0)
    li $v0, 1
    syscall
    
    li $v0, 4
    la $a0, newline
    syscall



    lw $a0, 8($t0)     # Load item2
    li $v0, 4
    syscall
    li $v0, 4
    la $a0, Space
    syscall
    lw $a0, 20($t0)
    li $v0, 1
    syscall
    li $v0, 4
    la $a0, newline
    syscall

    lw $a0, 12($t0)    # Load item3
    li $v0, 4
    syscall
    li $v0, 4
    la $a0, Space
    syscall
   lw $a0, 24($t0)
    li $v0, 1
    syscall
    li $v0, 4
    la $a0, newline
    syscall





    # Print newline
    li $v0, 4
    la $a0, newline
    syscall

    # Move to next Mole element
    addi $t0, $t0, 32
    addi $t1, $t1, 1
    blt $t1, 4, Print_loop # Print data for Mole[1], Mole[2], and Mole[3]

    

    li $v0, 4
    la $a0, cart_exit_str
    syscall
    li $v0, 4
    la $a0, choice_str
    syscall
    
    # Read the user's choice
    li $v0, 5
    syscall
    move $t0, $v0
    # Handle the user's choice
    blt $t0, 1, invalid_choice
    bgt $t0, 11, invalid_choice
    beq $t0, 10, cart
    beq $t0, 11, StartShopping
   move $a0,$t0
    jal item_selected
    j searchByItem



item_selected:
    la $s0, Mole       # Load address of Mole
    addi $t0, $s0, 0

    # Check if item is in range 1-3
    bge $a0, 1, check_shop1
    j invalid_item

check_shop1:
    ble $a0, 3, set_shop1
    bge $a0, 4, check_shop2
    j invalid_item

check_shop2:
    ble $a0, 6, set_shop2
    j set_shop3

set_shop1:
    li $s7, 1
    j process_item

set_shop2:
    li $s7, 2
    j process_item

set_shop3:
    li $s7, 3
    j process_item

process_item:
    rem $t1, $a0, 3  # Calculate item type (1, 2, or 0)
    beq $t1, $zero, item3
    beq $t1, 2, item2
    # If item type is 1
item1:
    li $v0, 4
    la $a0, prompt22
    syscall
    li $v0, 5
    syscall
    move $t2, $v0  # Store quantity in $t2

    # Load appropriate prices based on shop type
    beq $s7, 1, Price1
    beq $s7, 2, Price2

Price2:
    addi $t0, $t0, 32
Price1:
     
    lw $t3, 16($t0)  # Load first price
    mul $t4, $t2, $t3  # Calculate total for item 1
    add $s4, $s4, $t4  # Update total

    # Push back calculated price
    lw $a0, 4($t0) 
    lw $a1, 16($t0) # Load second price
    move $a2, $t2
    jal push_back
    j searchByItem

    # If item type is 2
item2:
    li $v0, 4
    la $a0, prompt22
    syscall
    li $v0, 5
    syscall
    move $t2, $v0  # Store quantity in $t2

    # Load appropriate prices based on shop type
    beq $s7, 1, Price12
    beq $s7, 2, Price22

Price22:
    addi $t0, $t0, 32
Price12:
 
    lw $t3, 20($t0)  # Load first price
    mul $t4, $t2, $t3  # Calculate total for item 2
    add $s4, $s4, $t4  # Update total

    # Push back calculated price
    lw $a0, 4($t0)
    lw $a1, 20($t0) # Load second price
    move $a2, $t2
    jal push_back
    j searchByItem

    # If item type is 3
item3:
    li $v0, 4
    la $a0, prompt22
    syscall
    li $v0, 5
    syscall
    move $t2, $v0  # Store quantity in $t2

    # Load appropriate prices based on shop type
    beq $s7, 1, Price13
    beq $s7, 2, Price23

Price23:
    addi $t0, $t0, 32
Price13:
  
    lw $t3, 24($t0)  # Load first price
    mul $t4, $t2, $t3  # Calculate total for item 3
    add $s4, $s4, $t4  # Update total

    # Push back calculated price
    lw $a0,  4($t0)
    lw $a1, 24($t0)# Load second price
    move $a2, $t2
    jal push_back
    j searchByItem

invalid_item:
    li $v0, 4
    la $a0, prompt4
    syscall
    j item_selected

   
cart:

    li $v0, 4
    la $a0, cart_header_str
    syscall
        li $v0, 4
    la $a0, prompt16
    syscall
    la $a0, t_name
    syscall

jal print_push
    li $v0, 4
    la $a0, remove_item_str
    syscall
    li $v0, 5
    syscall
    move $t0, $v0
  
      beq $t0, 1, pop_back
      END_Cart:
     
      
    li $v0, 4
    la $a0, total_order_str
    syscall
    li $v0, 1
    move $a0, $s4
    syscall
    
 Order:
  
    li $v0, 4
    la $a0, prompt20
    syscall

    
    li $v0, 5
    syscall
    move $t0, $v0  


    
    beq $t0, 1, order_confirmed

   
    beq $t0, 0, cancel_order

   
    li $v0, 4
    la $a0, prompt4
    syscall
    j Order

order_confirmed:
   
    li $v0, 4
    la $a0, prompt21
    syscall

    
    li $v0, 4
    la $a0, t_name
    syscall

    
    li $v0, 4
    la $a0, order_confirmation_message
    syscall

 j exit_program


cancel_order:

   
    li $s4, 0

   
    li $v0, 4
    la $a0, cancel_or_exit_options
    syscall

   
    li $v0, 5
    syscall
    move $t3, $v0  
    
    li $t4, 1
    beq $t3, $t4, Cancel_order
    bne $t3, $zero, exit_cancel_or_exit  

Cancel_order:
   
    li $v0, 4
    la $a0, prompt21
    syscall
    
    jal Vector_Clear

   jal print_push


exit_cancel_or_exit:
   
        li $s4, 0
    j exit_program




#########################################################  Vector Class #########################################################
Vector:
Vector_constructor_int_n:
   
    addi $sp, $sp, -8      
    sw $ra, 4($sp)         

  
    sll $t0, $a0, 2        
    li $v0, 9             
    move $a0, $t0        
    syscall                
    move $t1, $v0          
   
    sw $t1, 0($t1)         
    sw $a0, 4($t1)         

    lw $ra, 4($sp)        
    addi $sp, $sp, 8       
    jr $ra                
Vector_constructor:
    
    li $t0, 0         
    sw $t0, 0($a0)    
    li $t0, 0        
    sw $t0, 4($a0)    
    jr $ra             

Vector_destructor:
   
    lw $t0, 0($a0)     
    beq $t0, $zero, end_destructor 
    lw $t1, 4($a0)     
    sll $t1, $t1, 2    
    add $t0, $t0, $t1  
    sub $t0, $t0, 4   
    end_destructor:
    jr $ra






Vector_Clear:
addi $sp, $sp, -8      # Adjust stack pointer
    sw $ra, 4($sp) 

  beq $t8, $zero, endC
 li $t1,0


 item_clear:
 beq $t1,$t8,endC

 sw $0,0($v1)

 sw $0, 4($v1)

  sw $0, 8($v1)

  addi $v1,$v1,-12
   addi $t1,$t1,1
   j item_clear
 endC:
 move $t8,$0
     lw $ra, 4($sp)         # Restore return address
    addi $sp, $sp, 8  
 jr $ra
 
pop_back:
 
addi $v1,$v1,-12
lw $t0, 4($v1)
lw $t1, 8($v1)
mul $t1,$t1,$t0
sub $s4,$s4,$t1
sw $0,0($v1)
sw $0, 4($v1)
sw $0, 8($v1)
addi $v1,$v1,-12
addi $t8,$t8,-1

li $v0, 4
la $a0,Lastisremoved
syscall 

 j END_Cart
 
purchese:
addi $sp, $sp, -8      # Adjust stack pointer
    sw $ra, 4($sp)         # Save return address
    # Initialize the vector
    li $a0, 0
    jal Vector_constructor_int_n
     la $t9, p       # Load address of p
         move $v1,$t9
    li $t8, 0         # Index for p[0]
    lw $ra, 4($sp)         # Restore return address
    addi $sp, $sp, 8  
         # Restore stack pointer
    jr $ra 
                    # Return from constructor
push_back:
addi $sp, $sp, -8      # Adjust stack pointer
    sw $ra, 4($sp)         # Save return address
    move $s5,$a0 #item
    move $s6,$a1 #price
    move $s7,$a2 # N

    sw $s5, 0($t9)
    sw $s6, 4($t9)
    sw $s7, 8($t9)
    addi $t9,$t9,12
   addi $t8,$t8,1

    lw $ra, 4($sp)         # Restore return address
    addi $sp, $sp, 8       # Restore stack pointer
        jr $ra

 print_push:
 addi $sp, $sp, -8      # Adjust stack pointer
    sw $ra, 4($sp)  
  beq $t8, $zero, end
 li $t1,0


        li $v0, 4
        la $a0, cart_amount_str
        syscall

 item_print:
 beq $t1,$t8,end

 lw $a0,0($v1)
 li $v0,4
 syscall
   li $v0, 4
    la $a0, Space
    syscall
 
 lw $t2, 4($v1)
 move $a0,$t2
 li $v0,1
 syscall
   li $v0, 4
    la $a0, Space
    syscall
 
  lw $t2, 8($v1)
 move $a0,$t2
 li $v0,1
 syscall
         
    li $v0, 4
    la $a0, newline
    syscall
  addi $v1,$v1,12
   addi $t1,$t1,1
   j item_print
 
end:
    li $v0, 4
    la $a0, total_order_str
    syscall
    li $v0, 1
    move $a0, $s4
    syscall
      lw $ra, 4($sp)         # Restore return address
    addi $sp, $sp, 8       # Restore stack pointer
        jr $ra
