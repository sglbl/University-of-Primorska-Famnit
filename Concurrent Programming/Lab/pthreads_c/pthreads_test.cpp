#include <pthread.h>
#include <iostream>

using namespace std;

void* func(void *arg);

main()
{
   pthread_attr_t attr;
   pthread_attr_init(&attr);
   pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);
   
   pthread_t tid;
   pthread_create(&tid, &attr, func, (void*)9);

   pthread_attr_destroy(&attr);
//   pthread_exit(NULL);
   pthread_join(tid, NULL);
}


void* func(void *arg)
{
   cout << "nit " << (int)arg << endl;
}
