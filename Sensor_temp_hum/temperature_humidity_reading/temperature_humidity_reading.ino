#include <WiFi.h>
#include <WebServer.h>
#include "DHT.h"

#define DHTPIN 2
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);
WebServer server(80);

void setup() {
  Serial.begin(9600);
  dht.begin();

  // ESP32 Access Point setup
  WiFi.softAP("ESP32_AP", "clave12345");

  server.on("/sensor", sensor);
  server.begin();
}

void loop() {
  server.handleClient();
}

void sensor() {
  float humidity = dht.readHumidity();
  float temperature = dht.readTemperature();

  String response = "Temperature: " + String(temperature) + " °C\nHumidity: " + String(humidity) + " %";
  server.send(200, "text/plain", response);
}
