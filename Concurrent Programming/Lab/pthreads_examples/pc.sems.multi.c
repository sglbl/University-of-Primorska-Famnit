/* primer uporabe semaforjev v primeru preprostega P/C: vec P in C 

    usage:
     pc.sems.multi 

*/
 
#include <pthread.h>
#include <stdio.h>
#include <semaphore.h>
#define SHARED 0

#define P 3
#define C 2


void *Producer(void *);  /* the two threads */
void *Consumer(void *);

sem_t *empty, *full;    /* the global semaphores */
long buf;             /* shared buffer         */
int numIters_P;
int numIters_C;

/* main() -- read command line and create threads, then
             print result when the threads have quit */

int main(int argc, char *argv[]) {
	
  long i;
  
  /* thread ids and attributes */ 
  pthread_t pid[P]; 
  pthread_t cid[C];  
  
  pthread_attr_t attr;
  pthread_attr_init(&attr);
  pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);

  numIters_P =  2;   /* da se nam izide */
  numIters_C =  3;
  
  //sem_init(&empty, SHARED, 1);  /* sem empty = 1 */
  empty = sem_open("m_empty",O_CREAT,0644,1);  /* to je isto kot zgoraj, le zaradi OSX mora biti tako */
  //sem_init(&full, SHARED, 0);   /* sem full = 0  */
  full = sem_open("m_full",O_CREAT,0644,0); /* to je isto kot zgoraj, le zaradi OSX mora biti tako */
  
  printf("main started\n"); fflush(stdout);
  
  /* tri proizvajalci */
  for (i=0; i<P; i++) {
    pthread_create(&pid[i], &attr, Producer, (void*)i);
  }
  
  /* dva porabnika */
  for (i=0; i<C; i++) {
    pthread_create(&cid[i], &attr, Consumer, (void*)i);
  }
  
  /*  pocakamo vse proizvajalce */
  for (i=0; i<P; i++) {
    pthread_join(pid[i], NULL);
  }
  
  /* pocakamo vse porabnike */
  for (i=0; i<C; i++) {
    pthread_join(cid[i], NULL);
  }
  
  sem_close(empty);
  sem_close(full);
  
  printf("main done\n"); fflush(stdout);
}


/* deposit 1, ..., numIters into the data buffer */
void *Producer(void *id) {
  int produced;
  
  printf("Producer %ld created\n", (long)id ); fflush(stdout);
  for (produced = 0 ; produced < numIters_P; produced++) {
    sem_wait(empty);
    buf = (long)id * 10 + produced;
    printf("P %ld: vstavil sem %ld\n", (long) id, buf); fflush(stdout);
    sem_post(full);
  }
}

/* fetch numIters items from the buffer */
void *Consumer(void *id) {
  long result = -1;
  int consumed;
  printf("Consumer %ld created\n", (long)id ); fflush(stdout);
  for (consumed = 0; consumed < numIters_C; consumed++) {
    sem_wait(full);
    result = buf;
    printf("C %ld: vzel sem %ld\n", (long) id, result); fflush(stdout);
    sem_post(empty);
    
  }
}
