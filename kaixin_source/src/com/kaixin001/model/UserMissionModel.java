package com.kaixin001.model;

import java.util.ArrayList;
import java.util.HashMap;

public class UserMissionModel
{
  private static UserMissionModel instance;
  private HashMap<String, ArrayList<LevelActivityModel>> activityListMap = new HashMap();
  private HashMap<String, ArrayList<MissionItem>> missionListMap = new HashMap();

  public static UserMissionModel getInstance()
  {
    if (instance == null)
      instance = new UserMissionModel();
    return instance;
  }

  public void addLevelActivity(String paramString, ArrayList<LevelActivityModel> paramArrayList)
  {
    this.activityListMap.put(paramString, paramArrayList);
  }

  public void addMissionList(String paramString, ArrayList<MissionItem> paramArrayList)
  {
    this.missionListMap.put(paramString, paramArrayList);
  }

  public void clear()
  {
    this.missionListMap.clear();
    this.activityListMap.clear();
    instance = null;
  }

  public ArrayList<LevelActivityModel> getActivityList(String paramString)
  {
    return (ArrayList)this.activityListMap.get(paramString);
  }

  public ArrayList<MissionItem> getMissionList(String paramString)
  {
    return (ArrayList)this.missionListMap.get(paramString);
  }

  public class LevelActivityModel
  {
    public String activityId;
    public String content;
    public String endTime;
    public String startTime;
    public String title;

    public LevelActivityModel()
    {
    }
  }

  public class MissionItem
  {
    public static final int TYPE_GRADE = 2001;
    public static final int TYPE_MISSION = 1001;
    public String days = "";
    public String done = "";
    public String exp = "";
    public boolean isEnd = false;
    public String large = "";
    public String level = "";
    public String limitValue = "";
    public String middle = "";
    public String small = "";
    public String title = "";
    public int type;
    public String typeTitle = "";

    public MissionItem()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.UserMissionModel
 * JD-Core Version:    0.6.0
 */