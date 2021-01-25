/* Auto-generated code from Toy Compiler */
#include<stdio.h>
#include<stdlib.h>

char* nome = "Michele";
int multAddDiff_1;
int multAddDiff_2;


int multAddDiff();
void writeNewLines(int n);

int main(){ 
int a, b, c, d = 0;
a = multAddDiff();
b = multAddDiff_1;
c =  multAddDiff_2;
printf("Ciao %s", nome);
writeNewLines(2); 
printf("I tuoi valori sono:\n%d per la moltiplicazione\n%d per la somma, e \n%d per la differenza", a, b, c);
return 0;
}
int multAddDiff(){ 
int primo, secondo, mul, add, diff;
printf("Inserire il primo argomento:\n");
scanf("%d",&primo);
printf("Inserire il secondo argomento:\n");
scanf("%d",&secondo);
mul = primo * secondo;
add = primo + secondo;
diff = primo - secondo;
multAddDiff_1 = add;
multAddDiff_2 = diff;
return mul;
}
void writeNewLines(int n){ 
while(n > 0){
	printf("\n");
	n = n - 1;
}

}

