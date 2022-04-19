/* primer uporabe semaforjev v primeru preprostega P/C problema

    usage:
     pc.sems numIters

*/
 
#include <pthread.h>
#include <stdio.h>
#include <semaphore.h>
#define SHARED 0

void *Producer(void *);  /* the two threads */
void *Consumer(void *);

sem_t *empty, *full;    /* the global semaphores */
int buf;              /* shared buffer         */
int numIters;

/* main() -- read command line and create threads, then
             print result when the threads have quit */

int main(int argc, char *argv[]) {
  /* thread ids and attributes */
  pthread_t pid, cid;  
  pthread_attr_t attr;
  pthread_attr_init(&attr);
  pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);

  numIters = atoi(argv[1]);
  //sem_init(&empty, SHARED, 1);  /* sem empty = 1 */
  empty = sem_open("m_empty",O_CREAT,0644,1);  /* to je isto kot zgoraj, le zaradi OSX mora biti tako */
  //sem_init(&full, SHARED, 0);   /* sem full = 0  */
  full = sem_open("m_full",O_CREAT,0644,0); /* to je isto kot zgoraj, le zaradi OSX mora biti tako */

  printf("main started\n");
  pthread_create(&pid, &attr, Producer, NULL);
  pthread_create(&cid, &attr, Consumer, NULL);
  pthread_join(pid, NULL);
  pthread_join(cid, NULL);
  
  sem_close(empty);
  sem_close(full);
  
  printf("main done\n");
  
  
  return 0;
}


/* deposit 1, ..., numIters into the data buffer */
void *Producer(void *arg) {
  int produced;
  printf("Producer created\n");
  for (produced = 0; produced < numIters; produced++) {
    sem_wait(empty);
    buf = produced;
    printf("P: vstavil sem %d\n", buf); fflush(stdout);
    sem_post(full);
  }
}

/* fetch numIters items from the buffer and sum them */
void *Consumer(void *arg) {
  int total = 0, consumed;
  printf("Consumer created\n");
  for (consumed = 0; consumed < numIters; consumed++) {
    sem_wait(full);
    total = total+buf;
    printf("C: vzel sem %d\n", buf); fflush(stdout);
    sem_post(empty);
  }
  printf("for %d iterations, the total is %d\n", numIters, total); fflush(stdout);
}
