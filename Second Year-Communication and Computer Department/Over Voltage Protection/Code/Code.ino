
#include "EmonLib.h"   // Include Emon Library
EnergyMonitor emon1;   // Create an instance

// include the library code: 
#include <LiquidCrystal.h> //library for LCD 

// initialize the library with the numbers of the interface pins 
LiquidCrystal lcd(13, 12, 11, 10, 9, 8); 

const int Relay_PIN = 7;
const int LED_PIN = 6;

void setup()
{
  pinMode(Relay_PIN, OUTPUT);  // Set the pin as OUTPUT 
  pinMode(LED_PIN, OUTPUT);  // Set the LED pin as OUTPUT 
  
  emon1.voltage(A0,187, 1.7); // Voltage: input pin, calibration, phase_shift

  lcd.begin(20, 4); // set up the LCD's number of columns and rows:
  lcd.setCursor(0,0);
  lcd.print("  THE BRIGHT LIGHT ");
  lcd.setCursor(0,1);
  lcd.print(" VOLTAGE PROTECTION ");
}

void loop()
{
  emon1.calcVI(20,2000); // Calculate all. No.of half wavelengths (crossings), time-out
  int Voltage   = emon1.Vrms;  //extract Vrms into Variable
  
  lcd.setCursor(0,2);
  lcd.print("   VOLTAGE = ");
  lcd.print(Voltage);
  lcd.print("V         ");

    if (Voltage >= 180 && Voltage <= 250)  //Normal Voltage Condition
  {
    digitalWrite(Relay_PIN, HIGH);
    digitalWrite(LED_PIN, LOW);
    lcd.setCursor(0, 3);
    lcd.print("   NORMAL VOLTAGE   ");
  }
  if (Voltage > 250)  //Over Voltage Condition
  {
    digitalWrite(Relay_PIN, LOW);
    digitalWrite(LED_PIN, HIGH);
    lcd.setCursor(0, 3);
    lcd.print("    OVER VOLTAGE   ");
  }
  if (Voltage < 180)  //Under Voltage Condition
  {
    digitalWrite(Relay_PIN, LOW);
    digitalWrite(LED_PIN, HIGH);
    lcd.setCursor(0, 3);
    lcd.print("    UNDER VOLTAGE   ");
  }
  delay(200);
}
