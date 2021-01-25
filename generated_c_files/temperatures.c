/* Auto-generated code from Toy Compiler */
#include<stdio.h>
#include<stdlib.h>




int main(){ 
float zeroAssolutoCelsius = -273.15;
float celsius, fahrenheit, kelvin;
printf("Inserire temperatura in °C (-273.15 = fine)");
scanf("%f",&celsius);
if(celsius >= zeroAssolutoCelsius){
	fahrenheit = 9.0 / 5.0 * celsius + 32;
	kelvin = celsius - zeroAssolutoCelsius;
	printf("Fahrenheit: %fKelvin: %f\n", fahrenheit, kelvin);
}
else{
	printf("Errore!");
}
return 0;
}

