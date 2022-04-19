#include <pthread.h>
#include <iostream>
#include "buffer.h"

using namespace std;

void* func(void *arg);
double racunaj();

main()
{
   buffer_init();

   pthread_attr_t attr;
   pthread_attr_init(&attr);
   pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);
   
   pthread_t tid;
   pthread_create(&tid, &attr, func, (void*)1);

   double sum = racunaj();
   cout << "vsota = " << sum << endl;

   pthread_attr_destroy(&attr);
//   pthread_exit(NULL);
}

double racunaj()
{
   double sum = 0.0;
   for (int i = 0; i < 5; i++) {
      double x = buffer_fetch();
      sum += x;
   }
   return sum;
}

void* func(void *arg)
{
   cout << "nit " << (int)arg << endl;
   for (;;) {
      buffer_deposit(1.0);
   }
}
