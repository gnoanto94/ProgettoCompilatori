/* Auto-generated code from Toy Compiler */
#include<stdio.h>
#include<stdlib.h>

char* nome = "Michele";

void main(){ 
int a, b, c = 0;
a = multAddDiff(),;
b = multAddDiff_1;
c =  multAddDiff_2;
printf("Ciao %s ", nome);
writeNewLines(2); 
printf("I tuoi valori sono:\n%d  per la moltiplicazione\n%d  per la somma, e \n%d  per la differenza", a, b, c);

}

int multAddDiff(){ 
int primo, secondo, mul, add, diff;
printf("Inserire il primo argomento:\n");
scanf("%d ",&primo);
printf("Inserire il secondo argomento:\n");
scanf("%d ",&secondo);
mul = primo * secondo;
add = primo + secondo;
diff = primo - secondo;
multAddDiff1 = add;
multAddDiff2 = diff;
	return mul
}

void writeNewLines(int n){ 
while(n > 0){
	printf("\n");
	n = n - 1;
}

}

