#include "pregrada.h"
#include <semaphore.h>

int stProc;
int count = 0;
sem_t arrive, go;

void pregrada_init(int n)
{
   stProc = n;
   sem_init(&arrive, 0, 1);
   sem_init(&go, 0, 0);
}

void pregrada_await()
{
   sem_wait(&arrive);
   count = count + 1;
   if (count < stProc) {
      sem_post(&arrive); 
      sem_wait(&go);
   }
   count = count - 1;
   if (count > 0)
      sem_post(&go);
   else
      sem_post(&arrive);
}

void pregrada_destroy()
{
   sem_destroy(&arrive);
   sem_destroy(&go);
}
