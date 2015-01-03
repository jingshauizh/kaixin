package com.kaixin001.util;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.db.CircleDBAdapter;
import com.kaixin001.model.User;
import java.util.ArrayList;

public class BlockedCircleDBManager
  implements BlockedCircleManagerInterface
{
  private static final String TAG = "BlockedCircleDBManager";
  private Context mContext = null;

  private BlockedCircleDBManager()
  {
  }

  public BlockedCircleDBManager(Context paramContext)
    throws Exception
  {
    if (paramContext == null)
      throw new Exception("context should not be null");
    this.mContext = paramContext;
  }

  public long add(String paramString)
  {
    long l1 = 0L;
    CircleDBAdapter localCircleDBAdapter;
    if ((!TextUtils.isEmpty(User.getInstance().getUID())) && (!TextUtils.isEmpty(paramString)))
      localCircleDBAdapter = new CircleDBAdapter(this.mContext);
    try
    {
      long l2 = localCircleDBAdapter.addBlockedCircle(User.getInstance().getUID(), paramString, 1, null);
      l1 = l2;
      return l1;
    }
    finally
    {
      localCircleDBAdapter.close();
    }
    throw localObject;
  }

  public int del(String paramString)
  {
    int i = -1;
    CircleDBAdapter localCircleDBAdapter;
    if ((!TextUtils.isEmpty(User.getInstance().getUID())) && (!TextUtils.isEmpty(paramString)))
      localCircleDBAdapter = new CircleDBAdapter(this.mContext);
    try
    {
      int j = localCircleDBAdapter.delBlockedCircle(User.getInstance().getUID(), paramString, 0, null);
      i = j;
      return i;
    }
    finally
    {
      localCircleDBAdapter.close();
    }
    throw localObject;
  }

  public ArrayList<String> load()
  {
    CircleDBAdapter localCircleDBAdapter = new CircleDBAdapter(this.mContext);
    try
    {
      ArrayList localArrayList = localCircleDBAdapter.loadBlockedCircles(User.getInstance().getUID());
      return localArrayList;
    }
    finally
    {
      localCircleDBAdapter.close();
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.BlockedCircleDBManager
 * JD-Core Version:    0.6.0
 */