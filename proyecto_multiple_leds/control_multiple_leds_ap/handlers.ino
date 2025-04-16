#include "handles.h"

extern WebServer server;

int ledPins[6] = {2,4,5,18,19};

///Tunrn on leds///

void handleRoot(){
  server.send(200, "text/plain", "ESP32 encendido");
}

void handleRedOn(){
  digitalWrite(ledPins[0],HIGH);
  server.send(200, "text/plain", "LED Red");
}

void handleGreenOn(){
  digitalWrite(ledPins[1],HIGH);
   server.send(200, "text/plain", "LED Green");  
}

void handleBluenOn(){
  digitalWrite(ledPins[2],HIGH);
   server.send(200, "text/plain", "LED Blue");  
}

void handleWhiteOn(){
  digitalWrite(ledPins[3],HIGH);
   server.send(200, "text/plain", "LED White");  
}

void handleYellowOn(){
  digitalWrite(ledPins[4],HIGH);
   server.send(200, "text/plain", "LED Yellow");  
}


///Turn OFF leds///

void handleRedOff(){
  digitalWrite(ledPins[0],LOW);
  server.send(200, "text/plain", "LED Red");
}

void handleGreenOff(){
  digitalWrite(ledPins[1],LOW);
   server.send(200, "text/plain", "LED Green");  
}

void handleBlueOff(){
  digitalWrite(ledPins[2],LOW);
   server.send(200, "text/plain", "LED Blue");  
}

void handleWhiteOff(){
  digitalWrite(ledPins[3],LOW);
   server.send(200, "text/plain", "LED White");  
}

void handleYellowOff(){
  digitalWrite(ledPins[4],LOW);
   server.send(200, "text/plain", "LED Yellow");  
}