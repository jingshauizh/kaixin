package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.model.VoteModel;
import com.kaixin001.model.VoteModel.Answer;
import com.kaixin001.model.VoteModel.Result;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VoteEngine extends KXEngine
{
  public static final int ERR_CODE_ACTION = -6;
  public static final int ERR_CODE_CGI = -4;
  public static final int ERR_CODE_DATA = -3;
  public static final int ERR_CODE_NOINSTALL = -7;
  public static final int ERR_CODE_RIGHT = -5;
  private static final String LOGTAG = "VoteEngine";
  private static final String TAG = "VoteEngine";
  private static VoteEngine instance = null;
  public String mLastError = "";

  public static VoteEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new VoteEngine();
      VoteEngine localVoteEngine = instance;
      return localVoteEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void parseVoteJson(JSONObject paramJSONObject)
  {
    if ((paramJSONObject == null) || (paramJSONObject.length() <= 0))
      return;
    VoteModel localVoteModel = VoteModel.getInstance();
    while (true)
    {
      int k;
      int m;
      try
      {
        localVoteModel.setSummary(paramJSONObject.getString("summary"));
        localVoteModel.setEndtime(paramJSONObject.getString("endtime"));
        localVoteModel.setVotenum(paramJSONObject.getString("votenum"));
        localVoteModel.setType(paramJSONObject.getString("type"));
        localVoteModel.setCtime(paramJSONObject.getString("ctime"));
        localVoteModel.setIntro(paramJSONObject.getString("intro"));
        localVoteModel.setTitle(paramJSONObject.getString("title"));
        localVoteModel.setFname(paramJSONObject.getString("fname"));
        JSONArray localJSONArray1 = paramJSONObject.optJSONArray("limitlist");
        if (localJSONArray1 != null)
          continue;
        int i = 0;
        if (i <= 0)
          continue;
        String[] arrayOfString = new String[i];
        int j = 0;
        if (j < i)
          continue;
        localVoteModel.setLimitlist(arrayOfString);
        JSONArray localJSONArray2 = paramJSONObject.optJSONArray("answerlist");
        if (localJSONArray2 != null)
          continue;
        k = 0;
        if (k <= 0)
          continue;
        ArrayList localArrayList1 = localVoteModel.getAnswerlist();
        m = 0;
        break label420;
        JSONArray localJSONArray3 = paramJSONObject.optJSONArray("resultlist");
        if (localJSONArray3 != null)
          continue;
        int i1 = 0;
        if (i1 <= 0)
          break;
        ArrayList localArrayList2 = localVoteModel.getResultlist();
        int i2 = 0;
        if (i2 >= i1)
          break;
        JSONObject localJSONObject1 = localJSONArray3.getJSONObject(i2);
        VoteModel.Result localResult = new VoteModel.Result();
        localResult.setUid(localJSONObject1.getString("uid"));
        localResult.setFname(localJSONObject1.getString("fname"));
        localResult.setCtime(localJSONObject1.getString("ctime"));
        localResult.setAnswer(localJSONObject1.getString("answer"));
        localArrayList2.add(localResult);
        i2++;
        continue;
        i = localJSONArray1.length();
        continue;
        arrayOfString[j] = localJSONArray1.getString(j);
        j++;
        continue;
        k = localJSONArray2.length();
        continue;
        JSONObject localJSONObject2 = localJSONArray2.getJSONObject(m);
        VoteModel.Answer localAnswer = new VoteModel.Answer();
        localAnswer.setAnswer(localJSONObject2.getString("answer"));
        localAnswer.setVotenum(localJSONObject2.getString("votenum"));
        localAnswer.setVotepercent(localJSONObject2.getString("votepercent"));
        localArrayList1.add(localAnswer);
        m++;
        break label420;
        int n = localJSONArray3.length();
        i1 = n;
        continue;
      }
      catch (JSONException localJSONException)
      {
        KXLog.e("VoteEngine", "parseVoteJson JSONException:", localJSONException);
        return;
      }
      label420: if (m < k)
        continue;
    }
  }

  public int doGetVoteDetail(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetVoteRequest(paramString, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return -1002;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("VoteEngine", "doGetVoteDetail error", localException);
        str2 = null;
      }
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return -1001;
      if (this.ret == 1)
      {
        VoteModel.getInstance().clear();
        VoteModel.getInstance().setVoteId(paramString);
        parseVoteJson(localJSONObject);
      }
    }
    return this.ret;
  }

  public boolean doVoteSubmit(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeVoteRequest(paramString1, paramString2, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    JSONObject localJSONObject;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (str2 == null)
        return false;
    }
    catch (Exception localException)
    {
      String str2;
      while (true)
      {
        KXLog.e("VoteEngine", "doVoteSubmit error", localException);
        str2 = null;
      }
      localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return false;
      if (this.ret == 1)
        return true;
    }
    try
    {
      this.mLastError = localJSONObject.getString("error");
      return false;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public String getLastError()
  {
    return this.mLastError;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.VoteEngine
 * JD-Core Version:    0.6.0
 */