package com.tencent.open;

import android.os.Handler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

class TaskGuide$GiftResultListener extends TaskGuide.BaseRequestListener
{
  int index = -1;

  public TaskGuide$GiftResultListener(TaskGuide paramTaskGuide, int paramInt)
  {
    super(paramTaskGuide, null);
    this.index = paramInt;
  }

  protected void handleException(Exception paramException)
  {
    if (paramException != null)
      paramException.printStackTrace();
    this.this$0.mListener.onError(new UiError(101, "error ", "金券领取时出现异常"));
    if (TaskGuide.access$3200(this.this$0) != null)
      TaskGuide.access$3200(this.this$0).post(new TaskGuide.GiftResultListener.1(this, paramException));
  }

  public void onComplete(JSONObject paramJSONObject, Object paramObject)
  {
    String str = null;
    while (true)
    {
      JSONObject localJSONObject2;
      try
      {
        int i = paramJSONObject.getInt("code");
        str = paramJSONObject.getString("message");
        if (i != 0)
          continue;
        TaskGuide.access$3600(this.this$0, this.index, TaskGuide.TaskState.REWARD_SUCCESS);
        JSONObject localJSONObject1 = new JSONObject();
        try
        {
          localJSONObject1.put("result", "金券领取成功");
          this.this$0.mListener.onComplete(localJSONObject1);
          TaskGuide.access$600(this.this$0, this.index);
          TaskGuide.access$2400(this.this$0, 2000);
          return;
        }
        catch (JSONException localJSONException2)
        {
          localJSONException2.printStackTrace();
          continue;
        }
      }
      catch (JSONException localJSONException1)
      {
        TaskGuide.access$3600(this.this$0, this.index, TaskGuide.TaskState.NORAML);
        TaskGuide.access$3800(this.this$0, str);
        localJSONException1.printStackTrace();
        continue;
        TaskGuide.access$3600(this.this$0, this.index, TaskGuide.TaskState.NORAML);
        TaskGuide.access$3800(this.this$0, str);
        localJSONObject2 = new JSONObject();
      }
      try
      {
        localJSONObject2.put("result", "金券领取失败");
        this.this$0.mListener.onComplete(localJSONObject2);
      }
      catch (JSONException localJSONException3)
      {
        while (true)
          localJSONException3.printStackTrace();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide.GiftResultListener
 * JD-Core Version:    0.6.0
 */