package com.tencent.open;

 enum TaskGuide$TaskState
{
  static
  {
    WAITTING_BACK_REWARD = new TaskState("WAITTING_BACK_REWARD", 2);
    NORAML = new TaskState("NORAML", 3);
    REWARD_SUCCESS = new TaskState("REWARD_SUCCESS", 4);
    REWARD_FAIL = new TaskState("REWARD_FAIL", 5);
    TaskState[] arrayOfTaskState = new TaskState[6];
    arrayOfTaskState[0] = INIT;
    arrayOfTaskState[1] = WAITTING_BACK_TASKINFO;
    arrayOfTaskState[2] = WAITTING_BACK_REWARD;
    arrayOfTaskState[3] = NORAML;
    arrayOfTaskState[4] = REWARD_SUCCESS;
    arrayOfTaskState[5] = REWARD_FAIL;
    $VALUES = arrayOfTaskState;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide.TaskState
 * JD-Core Version:    0.6.0
 */