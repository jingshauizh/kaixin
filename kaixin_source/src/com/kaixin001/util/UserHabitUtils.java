package com.kaixin001.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.engine.UserhabitEngine;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class UserHabitUtils
  implements UserHabitList.onAddListerner
{
  private static final boolean ISDEBUG = true;
  private static final String TAG = "UserHabitUtils";
  private static Context app = KXApplication.getInstance();
  private static final int habitSize = 5;
  private static UserHabitUtils instance;
  private UserHabitList<String> mUserHabitsList;

  private UserHabitUtils()
  {
    recoverUserHabitList();
  }

  public static UserHabitUtils getInstance(Context paramContext)
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new UserHabitUtils();
      UserHabitUtils localUserHabitUtils = instance;
      return localUserHabitUtils;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void recoverUserHabitList()
  {
    Log.i("UserHabitUtils", "recoverUserHabitList");
    new UserHabitList().setOnAddListerner(this);
  }

  private void sendHabitList(UserHabitList paramUserHabitList)
  {
    FutureTask localFutureTask = new FutureTask(new SendUserHabitRun(paramUserHabitList));
    Log.i("UserHabitUtils", "preSend:" + localFutureTask);
    recoverUserHabitList();
    TreadPoolUtils.execute(localFutureTask);
  }

  public void addUserHabit(String paramString)
  {
    if (this.mUserHabitsList == null)
    {
      Log.i("UserHabitUtils", "addUserHabit need new new UserHabitList<String>()");
      this.mUserHabitsList = new UserHabitList();
      this.mUserHabitsList.setOnAddListerner(this);
    }
    Log.i("UserHabitUtils", "recordUserHabit:" + paramString);
    this.mUserHabitsList.add(paramString);
  }

  public void addUserHabitAndSendAtOnce(String paramString)
  {
    addUserHabit(paramString);
    if ((this.mUserHabitsList != null) && (this.mUserHabitsList.size() > 0))
      sendHabitList(this.mUserHabitsList);
  }

  public void onAdd(UserHabitList paramUserHabitList)
  {
    if (paramUserHabitList == null);
    do
      return;
    while (paramUserHabitList.size() < 5);
    sendHabitList(paramUserHabitList);
  }

  class SendUserHabitRun
    implements Callable<Void>
  {
    private UserHabitList<String> mUserHabitsListTemp;

    public SendUserHabitRun()
    {
      Object localObject;
      this.mUserHabitsListTemp = localObject;
    }

    private String buildHabitString(UserHabitList<String> paramUserHabitList)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = paramUserHabitList.iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return localStringBuilder.toString();
        String str = (String)localIterator.next();
        if (TextUtils.isEmpty(str))
          continue;
        localStringBuilder.append(str).append(";");
      }
    }

    private int sendUserHabit(String paramString)
    {
      Log.i("UserHabitUtils", "sendUserHabit:" + paramString);
      return UserhabitEngine.getInstance().uploadUserHabitMulti(UserHabitUtils.app, paramString);
    }

    public Void call()
      throws Exception
    {
      if ((this.mUserHabitsListTemp == null) || (this.mUserHabitsListTemp.size() <= 0));
      String str;
      do
      {
        return null;
        str = buildHabitString(this.mUserHabitsListTemp);
        this.mUserHabitsListTemp.clear();
      }
      while (TextUtils.isEmpty(str));
      int i = sendUserHabit(str);
      Log.i("UserHabitUtils", "response:" + i);
      return null;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.UserHabitUtils
 * JD-Core Version:    0.6.0
 */