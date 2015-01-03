package com.tencent.open;

import android.os.Handler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

class TaskGuide$TaskRequestListener extends TaskGuide.BaseRequestListener
{
  private TaskGuide$TaskRequestListener(TaskGuide paramTaskGuide)
  {
    super(paramTaskGuide, null);
  }

  protected void handleException(Exception paramException)
  {
    if (paramException != null)
      paramException.printStackTrace();
    JSONObject localJSONObject;
    if (paramException == null)
      localJSONObject = new JSONObject();
    while (true)
    {
      try
      {
        localJSONObject.put("result", "暂无任务");
        this.this$0.mListener.onComplete(localJSONObject);
        TaskGuide.access$3200(this.this$0).post(new TaskGuide.TaskRequestListener.1(this));
        return;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        continue;
      }
      this.this$0.mListener.onError(new UiError(100, "error ", "获取任务失败"));
    }
  }

  public void onComplete(JSONObject paramJSONObject, Object paramObject)
  {
    try
    {
      TaskGuide.access$3502(this.this$0, TaskGuide.TaskInfo.generateFromJSONObject(paramJSONObject));
      if ((TaskGuide.access$3500(this.this$0) != null) && (TaskGuide.access$3500(this.this$0).isValidTask()))
      {
        this.this$0.showWindow();
        TaskGuide.access$3600(this.this$0, 2, TaskGuide.TaskState.NORAML);
        localJSONObject = new JSONObject();
      }
    }
    catch (JSONException localJSONException1)
    {
      try
      {
        JSONObject localJSONObject;
        localJSONObject.put("result", "获取成功");
        this.this$0.mListener.onComplete(localJSONObject);
        return;
        localJSONException1 = localJSONException1;
        this.this$0.mListener.onError(new UiError(100, "error ", "获取任务失败"));
        localJSONException1.printStackTrace();
      }
      catch (JSONException localJSONException2)
      {
        while (true)
          localJSONException2.printStackTrace();
      }
      handleException(null);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide.TaskRequestListener
 * JD-Core Version:    0.6.0
 */