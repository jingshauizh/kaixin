package com.kaixin001.activity;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class MessageHandlerHolder
{
  protected static MessageHandlerHolder inst = new MessageHandlerHolder();
  protected ArrayList<WeakReference<Handler>> handlers = new ArrayList();

  public static MessageHandlerHolder getInstance()
  {
    return inst;
  }

  public boolean addHandler(Handler paramHandler)
  {
    synchronized (this.handlers)
    {
      boolean bool = this.handlers.add(new WeakReference(paramHandler));
      return bool;
    }
  }

  public void fireMessage(Message paramMessage)
  {
    synchronized (this.handlers)
    {
      Iterator localIterator = this.handlers.iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return;
        WeakReference localWeakReference = (WeakReference)localIterator.next();
        if (localWeakReference == null)
          continue;
        try
        {
          Handler localHandler = (Handler)localWeakReference.get();
          if (localHandler == null)
            continue;
          Message localMessage = Message.obtain();
          localMessage.copyFrom(paramMessage);
          localHandler.sendMessage(localMessage);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    }
  }

  public boolean removeHandler(Handler paramHandler)
  {
    while (true)
    {
      int i;
      synchronized (this.handlers)
      {
        i = -1 + this.handlers.size();
        if (i <= -1)
          return true;
        WeakReference localWeakReference = (WeakReference)this.handlers.get(i);
        if ((localWeakReference == null) || (localWeakReference.get() == null) || (localWeakReference.get() == paramHandler))
          this.handlers.remove(i);
      }
      i--;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.MessageHandlerHolder
 * JD-Core Version:    0.6.0
 */