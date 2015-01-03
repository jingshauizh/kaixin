package com.tencent.open;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONObject;

class TaskGuide$TaskInfo
{
  private static final String STEP_INFO_KEY = "step_info";
  private static final String TASK_DESC_KEY = "task_desc";
  private static final String TASK_ID_KEY = "task_id";
  TaskGuide.StepInfo[] stepInfoArray;
  String taskDesc;
  String taskId;

  static TaskInfo generateFromJSONObject(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return null;
    TaskInfo localTaskInfo = new TaskInfo();
    JSONObject localJSONObject1 = paramJSONObject.getJSONObject("task_info");
    localTaskInfo.taskId = localJSONObject1.getString("task_id");
    localTaskInfo.taskDesc = localJSONObject1.getString("task_desc");
    JSONArray localJSONArray = localJSONObject1.getJSONArray("step_info");
    int i = localJSONArray.length();
    if (i > 0)
      localTaskInfo.stepInfoArray = new TaskGuide.StepInfo[i];
    for (int j = 0; j < i; j++)
    {
      JSONObject localJSONObject2 = localJSONArray.getJSONObject(j);
      int k = localJSONObject2.getInt("step_no");
      int m = localJSONObject2.getInt("status");
      TaskGuide.StepInfo localStepInfo = new TaskGuide.StepInfo(k, localJSONObject2.getString("step_desc"), localJSONObject2.getString("step_gift"), localJSONObject2.getLong("end_time"), m);
      localTaskInfo.stepInfoArray[j] = localStepInfo;
    }
    return localTaskInfo;
  }

  static TaskInfo manualGenerateTaskInfo()
  {
    TaskInfo localTaskInfo = new TaskInfo();
    localTaskInfo.taskId = "1111133333";
    localTaskInfo.taskDesc = "xxxxx";
    localTaskInfo.stepInfoArray = new TaskGuide.StepInfo[2];
    TaskGuide.StepInfo localStepInfo1 = new TaskGuide.StepInfo(0, "一走了之你好", "4金劵", 0L, 2);
    localTaskInfo.stepInfoArray[0] = localStepInfo1;
    TaskGuide.StepInfo localStepInfo2 = new TaskGuide.StepInfo(0, "电脑推送QQ泡泡毛你好", "500金劵", 0L, 1);
    localTaskInfo.stepInfoArray[1] = localStepInfo2;
    return localTaskInfo;
  }

  public boolean isValidTask()
  {
    return (!TextUtils.isEmpty(this.taskId)) && (this.stepInfoArray != null) && (this.stepInfoArray.length > 0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide.TaskInfo
 * JD-Core Version:    0.6.0
 */