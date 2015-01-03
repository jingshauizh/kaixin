package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.VoteItem;
import com.kaixin001.model.VoteListModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VoteListEngine extends KXEngine
{
  private static final String LOGTAG = "VoteListEngine";
  public static final int NUM = 20;
  private static VoteListEngine instance = null;

  public static VoteListEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new VoteListEngine();
      VoteListEngine localVoteListEngine = instance;
      return localVoteListEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private boolean parseVoteList(JSONObject paramJSONObject, VoteListModel paramVoteListModel)
  {
    if ((paramJSONObject == null) || (paramVoteListModel == null));
    JSONArray localJSONArray;
    do
    {
      return false;
      localJSONArray = paramJSONObject.optJSONArray("data");
    }
    while (localJSONArray == null);
    int i = localJSONArray.length();
    ArrayList localArrayList = paramVoteListModel.getVoteList();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return true;
      try
      {
        JSONObject localJSONObject = localJSONArray.getJSONObject(j);
        if (localJSONObject == null)
          continue;
        VoteItem localVoteItem = new VoteItem();
        localVoteItem.mType = localJSONObject.optString("type");
        localVoteItem.mUid = localJSONObject.optString("uid");
        localVoteItem.mToUid = localJSONObject.optString("touid");
        localVoteItem.mCtime = localJSONObject.optString("ctime");
        localVoteItem.mId = localJSONObject.optString("id");
        localVoteItem.mTitle = localJSONObject.optString("title");
        localArrayList.add(localVoteItem);
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
  }

  public boolean getVoteList(Context paramContext, String paramString1, String paramString2, int paramInt)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetVoteListRequest(paramString1, paramString2, paramInt);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    JSONObject localJSONObject;
    VoteListModel localVoteListModel;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return false;
    }
    catch (Exception localException)
    {
      do
      {
        String str2;
        while (true)
        {
          KXLog.e("VoteListEngine", "getVoteList error", localException);
          str2 = null;
        }
        localJSONObject = super.parseJSON(paramContext, str2);
      }
      while (this.ret != 1);
      localVoteListModel = VoteListModel.getInstance();
      localVoteListModel.getVoteList().clear();
    }
    return parseVoteList(localJSONObject, localVoteListModel);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.VoteListEngine
 * JD-Core Version:    0.6.0
 */