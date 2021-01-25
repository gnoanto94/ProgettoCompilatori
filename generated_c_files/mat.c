/* Auto-generated code from Toy Compiler */
#include<stdio.h>
#include<stdlib.h>



float sum();
int multiply();
int divide();
int my_pow();
void fib(int n);

int main(){ 
int scelta = 1, n;
float flt_result;
int int_result;
	printf("1 - Somma di due numeri \n");
	printf("2 - Moltiplicazione di due numeri utilizzando la somma\n");
	printf("3 - Divisione intera fra due numeri positivi\n");
	printf("4 - Elevamento a potenza\n");
	printf("5 - Successione di Fibonacci\n");
	printf("0 - Uscita");
	printf("\n");
	printf("Scelta: ");
	scanf("%d",&scelta);

while(scelta != 0){
	if(scelta == 1){
	flt_result = sum();
	printf("La somma dei due numeri è: %f\n", flt_result);
}
	else if(scelta == 2){
	int_result = multiply();
	printf("La moltiplicazione di due numeri usando la somma è: %d\n", int_result);
}
	else if(scelta == 3){
	int_result = divide();
	printf("La divisione intera fra i due numeri è: %d\n", int_result);
}
	else if(scelta == 4){
	int_result = my_pow();
	printf("L'elevamento a potenza è: %d\n", int_result);
}
else{
	printf("SUCCESSIONE di FIBONACCI\n");
	printf("Fino a che punto della successione? ");
	scanf("%d",&n);
	fib(n); 
	printf("\n");
}
	printf("1 - Somma di due numeri \n");
	printf("2 - Moltiplicazione di due numeri utilizzando la somma\n");
	printf("3 - Divisione intera fra due numeri positivi\n");
	printf("4 - Elevamento a potenza\n");
	printf("5 - Successione di Fibonacci\n");
	printf("0 - Uscita");
	printf("\n");
	printf("Scelta: ");
	scanf("%d",&scelta);

}
return 0;
}
float sum(){ 
float a, b, result;
printf("SOMMA\n");
printf("Inserire il primo operando: ");
scanf("%f",&a);
printf("Inserire il secondo operando: ");
scanf("%f",&b);
result = a + b;
return result;
}
int multiply(){ 
int a, b, result = 0, i = 0;
printf("MOLTIPLICAZIONE tramite SOMMA\n");
printf("Inserire il primo operando: ");
scanf("%d",&a);
printf("Inserire il secondo operando: ");
scanf("%d",&b);
while(i < b){
	result = result + a;
	i = i + 1;
}
return result;
}
int divide(){ 
int a, b, result = 0, i = 0;
printf("DIVISIONE tra INTERI POSITIVI\n");
	printf("Inserire il primo operando: ");
	scanf("%d",&a);

while(a < 0){
	printf("Il valore inserito è negativo. Riprovare\n");
	printf("Inserire il primo operando: ");
	scanf("%d",&a);

}
	printf("Inserire il secondo operando: ");
	scanf("%d",&b);

while(b < 0){
	printf("Il valore inserito è negativo. Riprovare\n");
	printf("Inserire il secondo operando: ");
	scanf("%d",&b);

}
result = a / b;
return result;
}
int my_pow(){ 
int a, b, result = 1, i = 0;
printf("ELEVAMENTO a POTENZA\n");
printf("Inserire la base: ");
scanf("%d",&a);
printf("Inserire l'esponente: ");
scanf("%d",&b);
while(i < b){
	result = result * a;
	i = i + 1;
}
return result;
}
void fib(int n){ 
int first = 0, second = 1, next, c = 0;
while(c < n){
	if(c <= 1){
	next = c;
}
else{
	next = first + second;
	first = second;
	second = next;
}
	printf("%d ", next);
	c = c + 1;
}

}

