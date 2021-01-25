/* Auto-generated code from Toy Compiler */
#include<stdio.h>
#include<stdlib.h>

int LINE_COUNT, STATUS_LINE, MESSAGE_COLUMN, PROMPT_COLUMN, MAXIMUM_COLUMN, SCALE, HORIZONTAL_SPEED;
int Seed, Score, GunColumn, TargetColumn;
int MessageShown;

int randomShoot(int rang);
void clearScreen();
void disableCursor();
void enableCursor();
void moveTo(int row, int col);
void setSpecial2();
void setSpecial3();
void setNormal();
void writeScore();
void drawGround();
void drawGun();
void clearMessage();
void drawTarget();
void missed();
int hitTarget(int speed);
int getSpeed();


int randomShoot(int rang){ 
Seed = Seed * 2137 + 173;
if(Seed < 0){
	Seed = -Seed;
}
return Seed - Seed / rang * rang;
}
void clearScreen(){ 
printf("\(12)");

}
void disableCursor(){ 
printf("\(155)0 p");

}
void enableCursor(){ 
printf("\(27)[ p");

}
void moveTo(int row, int col){ 
printf("\(155)%d;%dH", row, col);

}
void setSpecial2(){ 
printf("\(155)1;32m");

}
void setSpecial3(){ 
printf("\(155)1;33m");

}
void setNormal(){ 
printf("\(155)0m");

}
void writeScore(){ 
moveTo(STATUS_LINE, 8); 
printf("%d", Score);

}
void drawGround(){ 
int i;
moveTo(STATUS_LINE - 1, 1); 
i = 1;
while(i < MAXIMUM_COLUMN){
	i = i + 1;
	printf("-");
}

}
void drawGun(){ 
moveTo(STATUS_LINE - 4, GunColumn - 2); 
printf("+-I-+");
moveTo(STATUS_LINE - 3, GunColumn - 2); 
printf("|   |");
moveTo(STATUS_LINE - 2, GunColumn - 2); 
printf("|   |");
moveTo(STATUS_LINE - 1, GunColumn - 2); 
printf("$$$$$");

}
void clearMessage(){ 
if(MessageShown){
	MessageShown = 0;
	moveTo(STATUS_LINE, MESSAGE_COLUMN); 
	printf("                   ");
}

}
void drawTarget(){ 
moveTo(STATUS_LINE - 4, TargetColumn); 
printf("^");
moveTo(STATUS_LINE - 3, TargetColumn - 1); 
printf("< >");
moveTo(STATUS_LINE - 2, TargetColumn); 
printf("V");

}
void missed(){ 
moveTo(STATUS_LINE, MESSAGE_COLUMN); 
setSpecial3(); 
printf("Missed!");
setNormal(); 
MessageShown = 1;

}
int hitTarget(int speed){ 
int row, col, oldRow, oldCol;
int notDone, hit;
setSpecial2(); 
row = STATUS_LINE - 5 * SCALE;
col = GunColumn * SCALE;
hit = 0;
notDone = 1;
while(notDone){
	if(row / SCALE > 0){
	moveTo(row / SCALE, col / SCALE); 
	printf("*");
}
	oldRow = row;
	oldCol = col;
	col = col + HORIZONTAL_SPEED;
	row = row - speed;
	speed = speed - 1;
	if(oldRow / SCALE > 0){
	moveTo(oldRow / SCALE, oldCol / SCALE); 
}
else{
	moveTo(0, 0); 
}
	printf(" ");
	if(row / SCALE > 0){
	if(row / SCALE < STATUS_LINE - 1){
	moveTo(row / SCALE, col / SCALE); 
	printf("*");
}
else{
	moveTo(0, 0); 
	printf(" ");
}
}
	if(row / SCALE >= STATUS_LINE - 1){
	moveTo(STATUS_LINE - 2, col / SCALE - 2); 
	printf("BOOOM");
	missed(); 
	notDone = 0;
}
	else if(col / SCALE >= MAXIMUM_COLUMN - 1){
	moveTo(row / SCALE, col / SCALE); 
	printf(" ");
	missed(); 
	notDone = 0;
}
	else if(col / SCALE == TargetColumn){
	if(row / SCALE == STATUS_LINE - 3){
	hit = 1;
	drawTarget(); 
	moveTo(STATUS_LINE, MESSAGE_COLUMN); 
	setSpecial3(); 
	printf("Hit!");
	setNormal(); 
	MessageShown = 1;
	notDone = 0;
}
}
}
return hit;
}
int getSpeed(){ 
int speed;
moveTo(STATUS_LINE, PROMPT_COLUMN); 
printf("speed (1-99): ");
enableCursor(); 
scanf("%d",&speed);
disableCursor(); 
moveTo(STATUS_LINE, PROMPT_COLUMN); 
printf("                  ");
clearMessage(); 
return speed;
}
int main(){ 
int speed;
int notDone;
MESSAGE_COLUMN = 15;
PROMPT_COLUMN = 58;
MAXIMUM_COLUMN = 76;
SCALE = 100;
HORIZONTAL_SPEED = 40;
MessageShown = 0;
	printf("How many text lines on your screen? ");
	scanf("%d",&LINE_COUNT);
	notDone = LINE_COUNT < 10;
	if(LINE_COUNT > 50){
	notDone = 1;
}

while(notDone){
	printf("Value out of range (10 - 50), try again.\n");
	printf("How many text lines on your screen? ");
	scanf("%d",&LINE_COUNT);
	notDone = LINE_COUNT < 10;
	if(LINE_COUNT > 50){
	notDone = 1;
}

}
STATUS_LINE = LINE_COUNT - 1;
printf("Enter random number seed: ");
scanf("%d",&Seed);
disableCursor(); 
clearScreen(); 
GunColumn = 4 + randomShoot(10);
TargetColumn = 30 + randomShoot(40);
setSpecial3(); 
drawGround(); 
drawGun(); 
drawTarget(); 
setNormal(); 
Score = 0;
moveTo(STATUS_LINE, 0); 
printf("Score: ");
writeScore(); 
notDone = 1;
while(notDone){
	speed = getSpeed();
	if(speed > 0){
	if(speed >= 100){
	moveTo(STATUS_LINE, MESSAGE_COLUMN); 
	setSpecial3(); 
	printf("Too large.");
	setNormal(); 
	MessageShown = 1;
}
else{
	if(hitTarget(speed) ){
	notDone = 0;
}
else{
	setSpecial3(); 
	drawTarget(); 
	setNormal(); 
}
	Score = Score + 1;
	writeScore(); 
}
}
else{
	notDone = 0;
}
}
enableCursor(); 
moveTo(STATUS_LINE + 1, 1); 
return 0;
}

