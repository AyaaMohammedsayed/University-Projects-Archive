#include <stdio.h>
#include <stdlib.h>
#include<string.h>



void home ();
long long start ();
void level1();
void level2();
void level3();
void GOOD();
void bad();
void help();
void quit();
int q=0;int numbers_player=0;
int index2=0;
char  name[4][50];
double score_final;
int score=0;
int scoree=0;
long long edit_score_R(int);
long long edit_score_F(int);
void show_score();
void show_score_high( score_final);
void home2();
double arr[5];
char namee[4][100];
int y=0;
int main()
{
home();
return 0;
}



void home()
{
    printf("\t\tWelcome to Educational game\t\n");
    printf("\t\t--------------------------\t\n");
    printf("\t\tWelcome to EDU game \t\n");
    printf("\t\t--------------------------\t\n");
    printf("\t\t--------------------------\t\n");
    printf("\t\t Do you want to learn multiplication ? \t\n");
    printf("1) Press S to start the game\n");
    printf("2) Press V to View the highest score\n");
    printf("3) Press H for help\n");
    printf("4) Press Q to finish the game\n");
    printf("\t\t--------------------------\t\n");
    char choice;
    printf("Please enter your choice here: ");
    scanf(" %c",&choice);
    if(choice =='S'|| choice =='s')
    {
       system("cls");
       printf("%ll",start()) ;
    }
   else if(choice =='V'|| choice =='v')
    {
      show_score_high();
    }
     else if(choice =='H'||choice =='h')
    {
        system("cls");
        help();
    }
     else if(choice =='Q'||choice =='q')
    {
       system("cls");
       quit();
    }
    else
    {
       printf("please enter valid choice\n");
       system("cls");
       home();
    }
}



long long start ()
{
char temp;
int choice,counter =0,counterloop=1;
printf("\t\t***********************************\t\n");
printf("Please enter numbers of players\n");
scanf("%d",&numbers_player);
printf("\t\t***********************************\t\n");
system("cls");
arr[numbers_player];
for(double i=0;i<numbers_player;i++)
{
printf("\t\t***********************************\t\n");
printf(" please enter your name : ");
scanf("%c",&temp);
//scanf("%[^\n]",name);
scanf("%s",name);
q=0;
score=0;
scoree=0;
strcpy(namee[y],name);
/*for(int j=0;j<sizeof(name);j++)
{
    namee[y][j]=name[y][j];
}*/
y++;




system("cls");

printf("\t\t ***********************************\t\n");

printf("\t\t Hello in level 1 in our game :)\t\n");
level1();
printf(" Finally the final score: ");
show_score();
index2++;


}
show_score_high();
home2();

}



void level1()
{
int choice,counter =0,counterloop=1,score1;char x='A';

do
{
time_t t;
srand((unsigned) time(&t));
int n1 = rand() % 11;
int n2 = (rand() % 10)+1;
int index =rand()%4;
int arr[4]={(n1+1)*(n2+2),(n1+1)*(n2+4),(n1+1)*(n2+3),(n1+1)*(n2-2)};
printf(" how much is %d times %d ?\n",n1,n2);
arr[index]=n1*n2;
for(int i=0;i<4;i++)
{
    if(arr[index]==arr[i])
    {
          printf(" %c :%d \n",x+i,arr[index]);
    }
    else
    {
          printf(" %c:%d  \n",x+i,arr[i]);

    }
}
    printf("*************************************\n");
    printf(" Please enter your choice here: ");
    scanf(" %d",&choice);
    if (choice == (n1*n2))
    {
        system("cls");
        GOOD();
        counter++;
        q++;
        int x=5;
         int score_r=edit_score_R(x);

    }
    else
    {
        system("cls");
        bad();
        counter=0;
        q++;
           int x=5;
         int score_f=edit_score_F(x);
    }
    if(q==20)
{
    return 0;
}
} while (counter<3);


if(counter==3)
{
    printf("\t\t Hello in level 2 in our game :)\t\n");
    level2();
}
}



void level2()
{
int choice,counter =0,counterloop=1;char x='A';
do
{
time_t t;
srand((unsigned) time(&t));
int n1 = rand() %(100+1-10)+(10) ;
int n2 = rand() %(100+1-10)+(10) ;
int index =rand()%4;
int arr[4]={(n1+1)*(n2+2),(n1+1)*(n2+4),(n1+1)*(n2+3),(n1+1)*(n2-2)};
printf (" how much is %d times %d ?\n",n1,n2);
arr[index]=n1*n2;
for(int i=0;i<4;i++)
{
    if(arr[index]==arr[i])
    {
         printf(" %c :%d \n",x+i,arr[index]);
    }
    else
    {
          printf(" %c:%d  \n",x+i,arr[i]);
    }
}
    printf("*************************************\n");
    printf(" Please enter your choice here: ");
    scanf(" %d",&choice);
    if (choice == (n1*n2))
    {
        system("cls");
        GOOD();
        counter++;
        q++;
          int x=10;
         int score_r=edit_score_R(x);
    }
    else
    {
        system("cls");
        bad();
        counter=0;
        q++;
          int x=10;
         int score_f=edit_score_F(x);
    }
    if(q==20)
    {
    return 0;
    }
} while (counter<3);

  if(counter==3)
{
    printf("\t\t Hello in level 3 in our game :)\t\n");
    level3();
}
}



void level3()
{
int choice,counter =0,counterloop=1;char x='A';
do
{
time_t t;
srand((unsigned) time(&t));
int n1 = rand() %(1000+1-100)+(100) ;
int n2 = rand() %(1000+1-100)+(100) ;
int index =rand()%4;
int arr[4]={(n1+1)*(n2+2),(n1+1)*(n2+4),(n1+1)*(n2+3),(n1+1)*(n2-2)};
printf (" how much is %d times %d ?\n",n1,n2);
arr[index]=n1*n2;
for(int i=0;i<4;i++)
{
    if(arr[index]==arr[i])
    {
         printf(" %c :%d \n",x+i,arr[index]);
    }
    else
    {
          printf(" %c:%d  \n",x+i,arr[i]);
    }
}
printf("*************************************\n");
printf(" Please enter your choice here: ");
scanf(" %d",&choice);
if (choice == (n1*n2))
    {
        system("cls");
        GOOD();
        counter++;
        q++;
          int x=15;
         int score_r=edit_score_R(x);
    }
    else
    {
        system("cls");
        bad();
        counter=0;
        q++;
          int x=15;
         int score_f=edit_score_F(x);
    }
if(q==20)
{
    return 0;
}
} while (counter<3);

}



void GOOD()
{
time_t t;
srand((unsigned) time(&t));
int n1 = rand() % 4;
switch(n1)
{
case 0:
    printf(" Very good!\n\n");
    break;
case 1:
    printf(" Excellent!\n\n");
    break;
case 2:
    printf(" Nice work!\n\n");
    break;
case 3:
    printf(" Keep up the good work!\n\n");
    break;
}



}
void bad()
{
time_t t;
srand((unsigned) time(&t));
int n1 = rand() % 4;
switch(n1)
{
case 0:
    printf(" No. Please try again. \n\n");
    break;
case 1:
    printf(" Wrong. Try once more.\n\n");
    break;
case 2:
    printf(" Don't give up!\n\n");
    break;
case 3:
    printf(" No. Keep trying.\n\n");
    break;
}
}



void quit ()
{
    printf("\n");
    printf("\t\t************************************\t\n");
    printf("\t\t Thanks for using our game :)\t\n");
    printf("\t\t************************************\t\n");

    return 0 ;
}



 void help()
 {
     printf("\t\t \t \t Hello in the EDU game \n -->summary and rules for help \t\n");
     printf(" First this game for  help an elementary school student learn multiplication\n");
     printf("\t\t******************************the rules of the game is :******************************\t\n ");
     printf("\n");
     printf(" 1-the first level is to learn student multiplicate 2 numbers of 1 digit\n ");
     printf("2-the student will try questions from the same level repeatedly until the student finally gets it right 3 time in arow\n");
     printf("\n");
     printf("** After the student types 20 answers our  program, the percentage is calculated.\n");
     printf("1-if the  correct the percentage is lower than 75% the program will display for you \" Please ask your teacher for help \" \n2- if  the program for reset to another trial \n");
     printf("3-If the percentage is 75 or higher, display \"Congratulations,you are ready to go to the next level!\",the program resets\n");
     printf(" the user will go to next level after level 1 with more difficult level ant the  The difficulty level should move to the medium level when the student manages to answer 3 questions correctly in aROW.\n");
     printf("Finally, the student moves to the hard level when she/he answers 3 questions correctly in a row. \n");
     printf("\n");
     printf(" NOTE:: the first level help the student to learn  multiplicate 2 numbers of 1 digit\n but the SECOND LEVEL is to learn the student how to  multiplicate 2 numbers of 2 digit\n AND  hard level learn the student how to multiplicate 2 numbers of 3 digit\n");
     printf("\n");
     printf(" \t \t******************************HOPING THAT HELP YOU IN YOUR LEARNING LIFE******************************\t\n");

     home2();

 }




long long edit_score_F(int x_level)
{
     scoree+=x_level;

     return scoree;

}
long long edit_score_R(int x_level)
{
    score+=x_level;

    return score;

}
void show_score()
{


     score_final=((double)score/(score + scoree))*100;
    if(score_final<75)
    {
        arr[index2]=score_final;
          printf("%0.0f",score_final);
          printf("\n");
        printf("\t\t Please ask your teacher for extra help\n");


    }
    else
    {
         arr[index2]=score_final;
          printf("%0.0f",score_final);
          printf("\n");
        printf("\t\tCongratulations, you are ready to go to the next level!\t\n");
    }







}
void home2()
{

    char y;
    printf("Do you want to play a game enter (y or n):");
scanf(" %c",&y);
  system("cls");

if(y=='y'||y=='Y')
{
    home();
    system("cls");
}
else if(y=='n'||y=='N')
{
    quit();
}
else
{
printf(" Please enter valid choose: ");


}

}
void show_score_high()
{
    double max =arr[0];

    for(int i=0;i<numbers_player-1;i++)
    {

        if(max<arr[i+1])
        {
            max=arr[i+1];
        }


    }
     printf("\t\t******************************************\t\n");

    for(int i=0;i<numbers_player;i++)
    {
        if (max==arr[i])
        {
            for(int j=0;j<sizeof(namee[i]);j++)
            {
               printf("%c",namee[i][j]);



            }

        }





    }
     printf(" has the highest score : %0.0f\n ",max);










}

