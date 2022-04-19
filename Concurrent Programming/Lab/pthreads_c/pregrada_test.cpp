#include <pthread.h>
#include <iostream>
#include "pregrada.h"

using namespace std;

void* func(void *arg);

main()
{
   int n = 4;
   pregrada_init(n);

   pthread_attr_t attr;
   pthread_attr_init(&attr);
   pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);
   
   for (int i = 0; i < n; i++) {
      pthread_t tid;
      pthread_create(&tid, &attr, func, (void*)i);
   }

   pthread_attr_destroy(&attr);
   pthread_exit(NULL);
}


void* func(void *arg)
{
   cout << "nit " << (int)arg << " grem v pregrado" << endl;
   pregrada_await();
   cout << "nit " << (int)arg << " zapustila sem pregrado" << endl;
}
