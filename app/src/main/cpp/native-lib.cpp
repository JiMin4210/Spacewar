#include <jni.h>
#include <string>
#include <android/log.h>
#include <fcntl.h>
#include <unistd.h> // write(), close()

#define FND_DEVICE "/dev/fpga_joystick"

int fpga_jostick()
{
    int stats[5];
    int count = 0;
    int dev;
    dev = open(FND_DEVICE,O_RDWR);
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
Java_com_example_final_1shooting_DrawFrame_getJoystick(
        JNIEnv* env,
        jobject /* this */) {
    jint result = fpga_jostick();
    return result;
}