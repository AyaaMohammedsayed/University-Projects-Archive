#Title: lab1 intro to mars
#Author : Aya Mohamed sec:1 B.No :9
##Title: lab1 intro to mars
#Author : Aya Mohamed sec:1 B.No :9



####################################### Data segment ############################
.data
hello : .asciiz "Hello in Mars "
####################################### Code segment ############################
.text
.globl main       #main function entry 
main:
li $v0 4
la $a0,hello
syscall
li $v0 10
syscall    #System call to exit the program 

