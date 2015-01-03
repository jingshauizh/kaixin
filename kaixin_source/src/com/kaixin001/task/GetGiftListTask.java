package com.kaixin001.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import com.kaixin001.engine.GetGiftListEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.GiftListModel;
import com.kaixin001.util.KXLog;

public class GetGiftListTask extends AsyncTask<Object, Void, Integer>
{
  public static final int RESULT_GET_GIFT_LIST = 222;
  private static final String TAG = "GetGiftListTask";
  private Context context;
  private Handler handle;

  public GetGiftListTask(Context paramContext)
  {
    this.context = paramContext;
  }

  protected Integer doInBackground(Object[] paramArrayOfObject)
  {
    this.handle = ((Handler)paramArrayOfObject[0]);
    if (GiftListModel.getInstance().isNeedGetLatest())
      try
      {
        Integer localInteger = Integer.valueOf(GetGiftListEngine.getInstance().getGiftList(this.context, (String)paramArrayOfObject[1]));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        KXLog.e("GetGiftListTask", "SecurityErrorException", localSecurityErrorException);
        return Integer.valueOf(-1003);
      }
    return Integer.valueOf(1);
  }

  protected void onPostExecute(Integer paramInteger)
  {
    Message localMessage = this.handle.obtainMessage();
    localMessage.what = 222;
    localMessage.arg1 = paramInteger.intValue();
    this.handle.sendMessage(localMessage);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.task.GetGiftListTask
 * JD-Core Version:    0.6.0
 */