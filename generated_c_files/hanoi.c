/* Auto-generated code from Toy Compiler */
#include<stdio.h>
#include<stdlib.h>


void printPeg(int peg);
void hanoi(int n, int fromPeg, int usingPeg, int toPeg);


void printPeg(int peg){ 
if(peg == 1){
	printf("left");
}
	else if(peg == 2){
	printf("center");
}
else{
	printf("right");
}

}
void hanoi(int n, int fromPeg, int usingPeg, int toPeg){ 
if(n != 0){
	hanoi(n - 1, fromPeg, toPeg, usingPeg); 
	printf("Move disk from ");
	printPeg(fromPeg); 
	printf(" peg to ");
	printPeg(toPeg); 
	printf(" peg.\n");
	hanoi(n - 1, usingPeg, fromPeg, toPeg); 
}

}
int main(){ 
int n = 0;
printf("How many pegs? ");
scanf("%d",&n);
hanoi(n, 1, 2, 3); 
return 0;
}

