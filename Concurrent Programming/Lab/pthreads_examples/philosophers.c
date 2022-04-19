/* primer uporabe semaforjev v primeru 5-ih filozofov

    usage:
     philosophers 

*/
 
#include <pthread.h>
#include <stdio.h>
#include <semaphore.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>

#define SHARED 0

#define P 5
#define NUM_ITER 2


void *Philosopher(void *);  /* nit filozofov */
sem_t *m_fork[P];             /* semaforji za vilice */



/* main() */

int main(int argc, char *argv[]) {
	
  long i;
  char ime_semaforja[10];
  
  /* thread ids and attributes */ 
  pthread_t pid[P]; 
 
  // inicializiramo vse semaforje
  // ker MAC OSX uporablja named semforje, moramo tako komplicirati,
  // sicer bi uporabili sem_init(m_fork[i], SHARED, 1)
  for (i=0; i<P; i++) {
  	  sprintf(ime_semaforja, "mm_fork_%ld", i);
  	  m_fork[i] = sem_open(ime_semaforja,O_CREAT,0644,1);
  }
 
  
  printf("main started\n"); fflush(stdout);
  
  /* 5 filozofov */
  for (i=0; i<P; i++) {
    pthread_create(&pid[i], NULL, Philosopher, (void*)i);
  }
  
  /*  pocakamo vse filozofe */
  for (i=0; i<P; i++) {
    pthread_join(pid[i], NULL);
  }
  
  for (i=0; i<P; i++) {
    sem_close(m_fork[i]);
  }
  
  
  
  
  printf("main done\n"); fflush(stdout);
}


/* Filozof */
void *Philosopher(void *id) {
  int iter;
  long myid;
  
  myid = (long)id;
  
  //printf("Folozof %ld narejen ...\n", myid); fflush(stdout);
  
  for (iter = 0 ; iter < NUM_ITER; iter++) {
  	  if (myid %2 == 0) {
  	  	  sem_wait(m_fork[(myid+1)%P]);
  	  	  sem_wait(m_fork[myid]);
  	  }
  	  else {
  	  	  sem_wait(m_fork[myid]);
  	  	  sem_wait(m_fork[(myid+1)%P]);
  	  }
  	  printf("Filozof %ld sedaj je...\n", myid); fflush(stdout);
  	  
  	  sem_post(m_fork[myid]);
  	  sem_post(m_fork[(myid+1)%P]);
  	  
  	  printf("Filozof %ld sedaj razmislja...\n", myid); fflush(stdout);
  	  sleep(1);
  }
}


