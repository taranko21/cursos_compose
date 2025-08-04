#include <WiFi.h>
#include <WebServer.h>

// Pines del sensor ultrasónico
const int trigPin = 19;
const int echoPin = 18;

// Pin del relé
const int relay = 14;

// Variable de modo de operación
bool modoAutomatico = true;

// Variables del sensor
long duration;
int distance;

WebServer server(80);

// ---------------------- SETUP ---------------------- //
void setup() {
  Serial.begin(9600);

  // Configura el modo Access Point
  WiFi.softAP("Esp32_Ultrasonic_sensor", "sensor329");

  // Endpoints del servidor
  server.on("/ultrasonic_sensor", ultrasonic_sensor);
  server.on("/modo_manual", setModoManual);
  server.on("/modo_manual/relay_on", handleRelayOn);
  server.on("/modo_manual/relay_off", handleRelayOff);
  server.on("/modo_auto", setModoAuto); // NUEVO

  server.begin();

  // Configuración de pines
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(relay, OUTPUT);
}

// ---------------------- LOOP ---------------------- //
void loop() {
  server.handleClient();
}

// ---------- SENSOR ULTRASÓNICO (solo en modo automático) ---------- //
void ultrasonic_sensor() {
  if (!modoAutomatico) {
    server.send(200, "text/plain", "Modo manual activado");
    return;
  }

  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  duration = pulseIn(echoPin, HIGH);
  distance = duration * 0.034 / 2;

  if (distance < 20) {
    digitalWrite(relay, HIGH);
  } else {
    digitalWrite(relay, LOW);
  }

  server.send(200, "text/plain", String(distance));
  delay(500);
}

// ---------------- CONTROL MANUAL DEL RELÉ ---------------- //
void handleRelayOn() {
  digitalWrite(relay, LOW);
  server.send(200, "text/plain", "Relé encendido manualmente");
}

void handleRelayOff() {
  digitalWrite(relay, HIGH);
  server.send(200, "text/plain", "Relé apagado manualmente");
}

// ---------------- CAMBIO DE MODO ---------------- //
void setModoManual() {
  modoAutomatico = false;
  server.send(200, "text/plain", "Modo manual activado");
}

void setModoAuto() {
  modoAutomatico = true;
  server.send(200, "text/plain", "Modo automático activado");
}


