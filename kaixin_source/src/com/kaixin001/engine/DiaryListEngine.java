package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.DiaryInfo;
import com.kaixin001.model.DiaryListModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class DiaryListEngine extends KXEngine
{
  private static final String LOGTAG = "DiaryListEngine";
  public static final int NUM = 10;
  private static DiaryListEngine instance;

  public static DiaryListEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new DiaryListEngine();
      DiaryListEngine localDiaryListEngine = instance;
      return localDiaryListEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private ArrayList<DiaryInfo> parseDiaryList(JSONArray paramJSONArray)
  {
    ArrayList localArrayList;
    if (paramJSONArray == null)
      localArrayList = null;
    while (true)
    {
      return localArrayList;
      localArrayList = new ArrayList();
      try
      {
        int i = paramJSONArray.length();
        for (int j = 0; j < i; j++)
        {
          JSONObject localJSONObject1 = paramJSONArray.getJSONObject(j);
          DiaryInfo localDiaryInfo = new DiaryInfo();
          localDiaryInfo.diaryFeedContent = localJSONObject1.getString("content");
          localDiaryInfo.diaryFeedCtime = localJSONObject1.getString("ctime");
          localDiaryInfo.diaryFeedDid = localJSONObject1.getString("did");
          localDiaryInfo.diaryFeedPrivacy = localJSONObject1.getString("privacy");
          localDiaryInfo.diaryFeedStrctime = localJSONObject1.getString("strctime");
          localDiaryInfo.diaryFeedTitle = localJSONObject1.getString("title");
          JSONArray localJSONArray = localJSONObject1.optJSONArray("imglist");
          if ((localJSONArray != null) && (localJSONArray.length() > 0))
          {
            localDiaryInfo.diaryFeedImages = new String[2];
            JSONObject localJSONObject2 = localJSONArray.getJSONObject(0);
            localDiaryInfo.diaryFeedImages[0] = localJSONObject2.optString("thumbnail", "");
            localDiaryInfo.diaryFeedImages[1] = localJSONObject2.optString("large", "");
          }
          localArrayList.add(localDiaryInfo);
        }
      }
      catch (Exception localException)
      {
        KXLog.e("DiaryListEngine", "parseDiaryList", localException);
      }
    }
    return localArrayList;
  }

  public boolean getDiaryList(Context paramContext, String paramString, int paramInt)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeDiaryListRequest(User.getInstance().getUID(), paramString, paramInt, 10);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("DiaryListEngine", "getDiaryList error", localException);
        str2 = null;
      }
    }
    return parseDiaryListJSON(paramContext, str2);
  }

  public boolean parseDiaryListJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString);
    if (localJSONObject == null)
      return false;
    if (this.ret == 1)
      try
      {
        JSONArray localJSONArray = localJSONObject.getJSONArray("diarylist");
        int i = localJSONObject.getInt("total");
        DiaryListModel.getInstance().setTotal(i);
        ArrayList localArrayList = parseDiaryList(localJSONArray);
        DiaryListModel.getInstance().setDiaryList(localArrayList);
        return true;
      }
      catch (Exception localException)
      {
        KXLog.e("DiaryListEngine", "parseDiaryListJSON", localException);
        return false;
      }
    KXLog.d("DiaryListEngine failed", paramString);
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.DiaryListEngine
 * JD-Core Version:    0.6.0
 */