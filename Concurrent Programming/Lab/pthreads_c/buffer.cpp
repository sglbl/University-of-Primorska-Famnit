#include "buffer.h"
#include <pthread.h>

pthread_mutex_t lock;
pthread_cond_t nfull, nempty;
const int n = 5;
double buf[5];
int front = 0, rear = 0, count = 0;

void buffer_init()
{
   pthread_mutex_init(&lock, NULL);
   pthread_cond_init(&nfull, NULL);
   pthread_cond_init(&nempty, NULL);
}

void buffer_deposit(double x)
{
   pthread_mutex_lock(&lock);
   while (count == n)
      pthread_cond_wait(&nfull, &lock);
   buf[rear] = x;
   rear = (rear + 1) % n;
   count++;
   pthread_cond_broadcast(&nempty);
   pthread_mutex_unlock(&lock);
}

double buffer_fetch()
{
   pthread_mutex_lock(&lock);
   while (count == 0)
      pthread_cond_wait(&nempty, &lock);
   double x = buf[front];
   front = (front + 1) % n;
   count--;
   pthread_cond_broadcast(&nfull);
   pthread_mutex_unlock(&lock);
   return x;
}

void buffer_destroy()
{
   pthread_mutex_destroy(&lock);
   pthread_cond_destroy(&nfull);
   pthread_cond_destroy(&nempty);
}
