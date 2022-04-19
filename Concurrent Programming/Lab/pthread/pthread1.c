#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <unistd.h> //for sleep
// gcc pthread1.c -pthread && ./a.out

void* routine() {
    printf("Hello from threads\n");
    sleep(3);
    printf("Ending thread\n");
}

int main(int argc, char* argv[]) {
    pthread_t p1, p2;
    //arg1=data about thread
    //arg2=attribute, if NULL use default values
    //arg3=routine [şablon, sıradan iş] that thread executes
    //arg4=argument of routine; if none use NULL
    if (pthread_create(&p1, NULL, &routine, NULL) != 0) 
        return 1;
    if (pthread_create(&p2, NULL, &routine, NULL) != 0) 
        return 2;
    if (pthread_join(p1, NULL) != 0)
        return 3;
    if (pthread_join(p2, NULL) != 0)
        return 4;
    return 0;
}