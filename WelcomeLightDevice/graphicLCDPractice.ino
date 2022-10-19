#include <Adafruit_GFX.h>
#include <Adafruit_PCD8544.h>

/*  아두이노 HC-06 예시코드 - Arduino bluetooth controller 깔아서 테스트하기 */
#include <SoftwareSerial.h>

#define BT_RXD 2
#define BT_TXD 3
SoftwareSerial hc06(BT_RXD,BT_TXD)

void setup()
{
	Serial.begin(9600);
    hc06.begin(9600);
}

void loop()
{
	if(hc06.available())
        Serial.Write(hc06.read());
    if(Serial.available())
        hc06.write(Serial.read());
}
/*  아두이노 HC-06 예시코드 */