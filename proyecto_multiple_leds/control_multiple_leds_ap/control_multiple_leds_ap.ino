#include <WiFi.h>
#include <WebServer.h>
#include "handles.h"

const char* ssid = "ESP32_Motor";
const char* password = "192098";

WebServer server(80);

///////////VOID SETUP/////////////
int ledsPin[5] = {2,4,5,18,19};

void setup() {
  Serial.begin(115200);
  for(int n=0;n<5;n++){
    pinMode(ledsPin[n],OUTPUT);
  }

  WiFi.softAP(ssid,password);
  Serial.println("AP creado");
  Serial.println("IP: ");
  Serial.println(WiFi.softAPIP());

  server.on("/", handleRoot);
  server.on("/red_led/on", handleRedOn);
  server.on("/red_led/off",handleRedOff);
  server.on("/green_led/on", handleGreenOn);
  server.on("/green_led/off",handleGreenOff);
  server.on("/blue_led/on", handleBluenOn);
  server.on("/blue_led/off",handleBlueOff);
  server.on("/white_led/on", handleWhiteOn);
  server.on("/white_led/off",handleWhiteOff);
  server.on("/yellow_led/on", handleYellowOn);
  server.on("/yellow_led/off",handleYellowOff);
  server.begin();

}

//////VOID LOOP////////////

void loop() {
  // put your main code here, to run repeatedly:
  server.handleClient();
}
