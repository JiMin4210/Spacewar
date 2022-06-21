#include <jni.h>
#include <string>
#include <android/log.h>
#include <fcntl.h>
#include <unistd.h> // write(), close()
#include "fpga_dot_font.h"

#define JOY_DEVICE "/dev/fpga_joystick"
#define BUZZER_DEVICE "/dev/fpga_buzzer"
#define FPGA_TEXT_LCD_DEVICE "/dev/fpga_text_lcd"
#define PUSH_SWITCH_DEVICE "/dev/fpga_push_switch"
#define STEP_DEVICE "/dev/fpga_step_motor"
#define LED_DEVICE "/dev/fpga_led"
#define FND_DEVICE "/dev/fpga_fnd"
#define DOT_DEVICE "/dev/fpga_dot"

#define MAX_DIGIT 4
#define LINE_BUFF 16
#define MAX_BUFF 32
#define MAX_BUTTON 9


int fpga_dot(int value) // value = value mode 1,2
{
    int i;
    int dev;
    int str_size;

    str_size = sizeof(face[value]);

    dev = open(DOT_DEVICE, O_RDWR);

    if (dev<0)
    {
        __android_log_print(ANDROID_LOG_INFO, "Device Open Error", "Driver = %d", value);
    }
    else {
        __android_log_print(ANDROID_LOG_INFO, "Device Open Success", "Driver = %d", value);

        write(dev, face[value], str_size); //얼굴 테스트

        close(dev);
    }
    return 0;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_example_final_1shooting_Character_ReceiveDotValue(JNIEnv* env, jobject, jint val)
{
    __android_log_print(ANDROID_LOG_INFO, "FpgaDotExample", "dot value = %d", val);
    fpga_dot(val);
    return NULL;
}

// ------------------------------------ FND
int fpga_fnd(const char* str)
{
    int dev;
    unsigned char data[4];
    unsigned char retval;
    int i;
    int str_size;

    memset(data,0,sizeof(data));

    str_size = (strlen(str));
    if(str_size>MAX_DIGIT)
    {
        str_size=MAX_DIGIT;
    }
    for(i = 0; i<str_size; i++)
    {
        if((str[i]<0x30)||(str[i])>0x39){
            return 1;
        }
        data[3-i] = str[str_size - 1 - i] - 0x30;
    }
    dev = open(FND_DEVICE, O_RDWR);
    if(dev<0){
        __android_log_print(ANDROID_LOG_INFO,"Device Open Error", "Driver = %s",str);
        return -1;
    }
    else{
        __android_log_print(ANDROID_LOG_INFO,"Device Open Success", "Driver = %s",str);
        write(dev,&data,4);
        close(dev);
        return 0;
    }
}

extern "C" JNIEXPORT jint JNICALL
Java_com_example_final_1shooting_DrawFrame_ReceiveFndValue(
        JNIEnv* env,
        jobject thiz, jstring val) {
    jint result;
    const char * str = env->GetStringUTFChars(val,NULL);
    __android_log_print(ANDROID_LOG_INFO,"FpgaFndExample", "value = %s",str);
    result=fpga_fnd(str);
    env->ReleaseStringUTFChars(val, str);
    return result;
}
// ------------------------------------ LED
int fpga_led(int x)
{
    int dev;
    unsigned char data;
    unsigned char retval;

    unsigned char val = 0;
    for(int i = 0; i<x; i++)
    {
        val = val | (0x01 << (7-i)); // 목숨의 개수만큼 쉬프트해준다 (위에서부터 표시)
    }
    dev = open(LED_DEVICE, O_RDWR);
    if (dev<0){
        __android_log_print(ANDROID_LOG_INFO, "Device Open Error","Driver = %d",x);
    }
    else{
        __android_log_print(ANDROID_LOG_INFO, "Device Open Success","Driver = %d",x);
        write(dev, &val, sizeof(unsigned  char));
        close(dev);
    }
    return 0;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_final_1shooting_Character_ReciveLedValue(JNIEnv *env, jobject thiz, jint val) {
    // TODO: implement ReciveLedValue()
    __android_log_print(ANDROID_LOG_INFO, "FpgaLedJniExample","led value = %d",val);
    fpga_led(val);
    return NULL;
}

// ------------------------------------ 스탭모터

int fpga_step_motor(int action, int direction, int speed)
{
    int i;
    int dev;
    int str_size;

    unsigned char motor_state[3];

    if(speed>250)
        speed = 250;
    else if (speed<5)
        speed = 5;

    memset(motor_state,0,sizeof(motor_state));

    motor_state[0] = (unsigned char)action;
    motor_state[1] = (unsigned char)direction;
    motor_state[2] = (unsigned char)speed;

    dev = open(STEP_DEVICE,O_RDWR);
    if(dev<0){
        __android_log_print(ANDROID_LOG_INFO,"Device Open Error", "Driver = %d",dev);
    }
    else{
        __android_log_print(ANDROID_LOG_INFO,"Device Open Success", "Driver = %d",dev);
        write(dev,motor_state,sizeof(motor_state));

        __android_log_print(ANDROID_LOG_INFO,"debug 1", "Driver = %d",dev);
        close(dev);
    }
    return 0;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_final_1shooting_DrawFrame_SetMotorState(
        JNIEnv* env,
        jobject /* this */, jint act, jint dir, jint spd) {
    __android_log_print(ANDROID_LOG_INFO,"FpgaStepMotorExample","SetMotor");
    fpga_step_motor(act, dir, spd);
    return 0;
}
// ------------------------------------ 스위치
int gFd= -1;

int fpga_push_open(void)
{
    int dev;

    dev = open(PUSH_SWITCH_DEVICE, O_RDWR);
    if (dev<0) {
        __android_log_print(ANDROID_LOG_INFO, "Device Open Error", "Driver = %d", dev);
        return -1;
    } else {
        gFd = dev;
    }
    return 0;
}

int fpga_push_close(void)
{
    if (gFd<0) {
        __android_log_print(ANDROID_LOG_INFO, "Device not Opened", "Driver = %d", gFd);
        return 0;
    } else {
        close(gFd);
        __android_log_print(ANDROID_LOG_INFO, "Device Close", "Driver = %d", gFd);
        return -1;
    }
}

int fpga_push_switch(void)
{
    int i;
    int dev;
    size_t buff_size;
    int retval;

    unsigned char push_sw_buff[MAX_BUTTON];

    if (gFd<0) {
        __android_log_print(ANDROID_LOG_INFO, "Device Open Error Opened", "Driver = %d", gFd);
        return -1;
    } else {
        close(gFd);
        __android_log_print(ANDROID_LOG_INFO, "Device Open Success", "Driver = %d", gFd);

        buff_size = sizeof(push_sw_buff);

        __android_log_print(ANDROID_LOG_INFO, "debug 1", "Driver = %d", gFd);

        read(gFd, &push_sw_buff, buff_size);

        __android_log_print(ANDROID_LOG_INFO, "debug 2", "Driver = %d", gFd);

        retval =0;

        for(i=0; i<MAX_BUTTON; i++)
        {
            if(push_sw_buff[i] != 0)
            {
                retval |= 0x01 << i;
            }
        }
    }
    return retval;
}
extern "C" JNIEXPORT jint JNICALL
Java_com_example_final_1shooting_DrawFrame_ReceivePushSwitchValue(
        JNIEnv* env,
        jobject thiz)
{
    int retval;
    retval = fpga_push_switch();
    return retval;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_example_final_1shooting_DrawFrame_DeviceOpen(
        JNIEnv* env,
        jobject thiz)
{
    int retval;
    retval = fpga_push_open();
}

extern "C" JNIEXPORT jint JNICALL
Java_com_example_final_1shooting_DrawFrame_DeviceClose(
        JNIEnv* env,
        jobject thiz)
{
    int retval;
    retval = fpga_push_close();
}

// ------------------------------------ LCD
int fpga_text_lcd(const char* str1, const char*str2)
{
    int i;
    int dev;
    size_t str_size;

    char string[32];
    memset(string,0,sizeof(string));

    dev = open(FPGA_TEXT_LCD_DEVICE, O_RDWR);
    if (dev<0){
        __android_log_print(ANDROID_LOG_INFO, "Device Open Error", "Driver = %d", dev);
        return -1;
    } else {
        str_size = strlen(str1);
        if(str_size>0)
        {
            strncat(reinterpret_cast<char *>(string), str1, str_size);
            memset(string + str_size, ' ',LINE_BUFF-str_size);
        }
        str_size = strlen(str2);
        if(str_size>0)
        {
            strncat(reinterpret_cast<char *>(string), str2, str_size);
            memset(string+LINE_BUFF+str_size, ' ',LINE_BUFF-str_size);
        }
        write(dev, string, MAX_BUFF - 1);

        close(dev);
        return 0;
    }
}

extern "C" JNIEXPORT jint JNICALL
Java_com_example_final_1shooting_DrawFrame_ReceiveTextLcdValue(
        JNIEnv* env, jobject thiz, jstring val1, jstring val2)
{
    jint result = 0;
    const char * pstr1 = (*env).GetStringUTFChars(val1,NULL);
    __android_log_print(ANDROID_LOG_INFO, "FpgaFndExample", "value = %s", pstr1);

    const char * pstr2 = (*env).GetStringUTFChars(val2,NULL);
    __android_log_print(ANDROID_LOG_INFO, "FpgaFndExample", "value = %s", pstr2);

    fpga_text_lcd(pstr1, pstr2);

    (*env).ReleaseStringUTFChars(val1, pstr1);
    (*env).ReleaseStringUTFChars(val2, pstr2);

    return result;
}
// ------------------------------------ 부저
int fpga_buzzer(int x)
{
    int dev;
    unsigned char data;
    unsigned char retval;

    data = (char)x;

    dev = open(BUZZER_DEVICE, O_RDWR);
    if (dev < 0) {
        __android_log_print(ANDROID_LOG_INFO, "Device Open Error", "Driver = %d", dev);
        return -1;
    } else {
        __android_log_print(ANDROID_LOG_INFO, "Device Open Success", "Driver = %d", dev);
        write(dev, &data, 1);
        close(dev);

        return 0;
    }
}

extern "C" JNIEXPORT jint JNICALL
Java_com_example_final_1shooting_DrawFrame_ReceiveBuzzerValue(
        JNIEnv* env,
        jobject thiz,
        jint val)
{
    jint result;
    __android_log_print(ANDROID_LOG_INFO, "FpgaBuzzerExample", "value = %d", val);
    result = fpga_buzzer(val);

    return result;
}
// ------------------------------------ 조이스틱
int fpga_jostick()
{
    int stats[5];
    int count = 0;
    int dev;
    dev = open(JOY_DEVICE,O_RDWR);
    if(dev<0){
        __android_log_print(ANDROID_LOG_INFO,"Device Open Error", "Driver = %s","joy");
        return -1;
    }
    memset(stats,0,sizeof(stats));
    read(dev,stats,sizeof(stats));
    close(dev);
    return stats[0] + stats[1]*10 + stats[2]*100 + stats[3]*1000 + stats[4]*10000;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_example_final_1shooting_Hero_getJoystick(
        JNIEnv* env,
        jobject /* this */) {
    jint result = fpga_jostick();
    return result;
}