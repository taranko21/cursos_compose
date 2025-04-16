#ifdef HANDLERS_H
#define HANDLERS_H

#include <WebServer.h>

extern WebServer server;

void handleRoot();
void handleRedOn();
void handleGreenOn();
void handleBlueOn();
void handleWhiteOn();
void handleYellowOn();
void handleRedOff();
void handleGreenOff();
void handleBlueOff();
void handleWhiteOff();
void handleYellowOff();

#endif