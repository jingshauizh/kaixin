package com.kaixin001.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import com.kaixin001.engine.GetTouchListEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.TouchListModel;
import com.kaixin001.util.KXLog;

public class GetTouchListTask extends AsyncTask<Object, Void, Integer>
{
  public static final int RESULT_GET_GIFT_LIST = 222;
  private static final String TAG = "GetTouchListTask";
  private Context context;
  private Handler handle;

  public GetTouchListTask(Context paramContext)
  {
    this.context = paramContext;
  }

  protected Integer doInBackground(Object[] paramArrayOfObject)
  {
    this.handle = ((Handler)paramArrayOfObject[0]);
    TouchListModel localTouchListModel = TouchListModel.getInstance();
    if (localTouchListModel.isNeedGetLatest())
      try
      {
        Integer localInteger = Integer.valueOf(GetTouchListEngine.getInstance().getTouchList(this.context, localTouchListModel.version));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        KXLog.e("GetTouchListTask", "SecurityErrorException", localSecurityErrorException);
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
 * Qualified Name:     com.kaixin001.task.GetTouchListTask
 * JD-Core Version:    0.6.0
 */