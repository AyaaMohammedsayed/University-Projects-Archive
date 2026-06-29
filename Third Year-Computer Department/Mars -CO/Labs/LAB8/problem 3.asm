.data
array1: .float 5.6e+20, -5.6e+20, 1.2
array2: .float 1.2, 5.6e+20, -5.6e+20
result_msg: .asciiz "Sum of array1 elements: "
result_msg2: .asciiz "Sum of array2 elements: "

.text
.globl main

main:
    la $t0, array1
    lwc1 $f0, 0($t0)
    lwc1 $f1, 4($t0)
    lwc1 $f2, 8($t0)
    add.s $f3, $f0, $f1
    add.s $f4, $f2, $f3

    la $t1, array2
    lwc1 $f5, 0($t1)
    lwc1 $f6, 4($t1)
    lwc1 $f7, 8($t1)
    add.s $f8, $f5, $f6
    add.s $f9, $f7, $f8

    li $v0, 4
    la $a0, result_msg
    syscall

    li $v0, 2
    mov.s $f12, $f4
    syscall

    li $v0, 4
    la $a0, result_msg2
    syscall

    li $v0, 2
    mov.s $f12, $f4
    syscall

    li $v0, 10
    syscall