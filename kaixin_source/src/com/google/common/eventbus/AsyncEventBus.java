package com.google.common.eventbus;

import com.google.common.annotations.Beta;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

@Beta
public class AsyncEventBus extends EventBus
{
  private final ConcurrentLinkedQueue<EventBus.EventWithHandler> eventsToDispatch = new ConcurrentLinkedQueue();
  private final Executor executor;

  public AsyncEventBus(String paramString, Executor paramExecutor)
  {
    super(paramString);
    this.executor = paramExecutor;
  }

  public AsyncEventBus(Executor paramExecutor)
  {
    this.executor = paramExecutor;
  }

  protected void dispatch(Object paramObject, EventHandler paramEventHandler)
  {
    this.executor.execute(new Runnable(paramObject, paramEventHandler)
    {
      public void run()
      {
        AsyncEventBus.this.dispatch(this.val$event, this.val$handler);
      }
    });
  }

  protected void dispatchQueuedEvents()
  {
    while (true)
    {
      EventBus.EventWithHandler localEventWithHandler = (EventBus.EventWithHandler)this.eventsToDispatch.poll();
      if (localEventWithHandler == null)
        return;
      dispatch(localEventWithHandler.event, localEventWithHandler.handler);
    }
  }

  protected void enqueueEvent(Object paramObject, EventHandler paramEventHandler)
  {
    this.eventsToDispatch.offer(new EventBus.EventWithHandler(paramObject, paramEventHandler));
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.eventbus.AsyncEventBus
 * JD-Core Version:    0.6.0
 */