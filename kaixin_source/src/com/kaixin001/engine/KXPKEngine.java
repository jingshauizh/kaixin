package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.PKInfoItem;
import com.kaixin001.item.PKRecordListItem;
import com.kaixin001.model.PKModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KXPKEngine extends KXEngine
{
  private static KXPKEngine instance = new KXPKEngine();

  public static KXPKEngine getInstance()
  {
    return instance;
  }

  private void parsePKInfoJSON(JSONObject paramJSONObject)
  {
    if (paramJSONObject.optInt("ret") == 1)
    {
      JSONObject localJSONObject = paramJSONObject.optJSONObject("data");
      PKInfoItem localPKInfoItem = new PKInfoItem();
      localPKInfoItem.setBluetitle(localJSONObject.optString("bluetitle"));
      localPKInfoItem.setRedtitle(localJSONObject.optString("redtitle"));
      localPKInfoItem.setRedtotal(localJSONObject.optString("redtotal"));
      localPKInfoItem.setBluetotal(localJSONObject.optString("bluetotal"));
      localPKInfoItem.setTitle(localJSONObject.optString("title").trim());
      PKModel.getInstance().setPkInfoItem(localPKInfoItem);
    }
  }

  private void parseRecordsListJson(int paramInt, JSONObject paramJSONObject)
    throws JSONException
  {
    int i = paramJSONObject.optInt("ret");
    ArrayList localArrayList = new ArrayList();
    JSONArray localJSONArray;
    int j;
    if (i == 1)
    {
      localJSONArray = paramJSONObject.optJSONObject("data").optJSONArray("recordlist");
      j = 0;
      if (j < localJSONArray.length())
        break label60;
      if (paramInt != 0)
        break label122;
      PKModel.getInstance().setRedList(localArrayList);
    }
    label60: label122: 
    do
    {
      return;
      JSONObject localJSONObject = (JSONObject)localJSONArray.get(j);
      PKRecordListItem localPKRecordListItem = new PKRecordListItem(paramInt);
      localPKRecordListItem.setContent(localJSONObject.optString("content"));
      localPKRecordListItem.setImgURL(localJSONObject.optString("icon90"));
      localArrayList.add(localPKRecordListItem);
      j++;
      break;
    }
    while (paramInt != 1);
    PKModel.getInstance().setBlueList(localArrayList);
  }

  public void getRecordsList(Context paramContext, int paramInt1, String paramString, int paramInt2, int paramInt3)
  {
    String str1 = Protocol.getInstance().makePKRecordListRequest(paramInt1, paramString, paramInt2, paramInt3);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str2 = localHttpProxy.httpGet(str1, null, null);
      if (!TextUtils.isEmpty(str2))
        parseRecordsListJson(paramInt1, super.parseJSON(paramContext, str2));
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void getTopicInfo(Context paramContext, String paramString)
  {
    String str1 = Protocol.getInstance().makePKInfoRequest(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str2 = localHttpProxy.httpGet(str1, null, null);
      if (!TextUtils.isEmpty(str2))
        parsePKInfoJSON(super.parseJSON(paramContext, str2));
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public int pkInfoByTitle(Context paramContext, String paramString)
  {
    String str = Protocol.getInstance().makePKInfoByTitleRequest(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      JSONObject localJSONObject1 = super.parseJSON(paramContext, localHttpProxy.httpGet(str, null, null));
      this.ret = localJSONObject1.optInt("ret");
      if (this.ret == 1)
      {
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("data");
        if (localJSONObject2 == null)
          break label97;
        PKModel.getInstance().setPkid(localJSONObject2.optString("id"));
        PKModel.getInstance().setPkType(localJSONObject2.optString("type"));
      }
      while (true)
      {
        return this.ret;
        label97: PKModel.getInstance().setPkid(null);
        PKModel.getInstance().setPkType(null);
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public int pkVote(Context paramContext, String paramString, int paramInt)
  {
    String str1 = Protocol.getInstance().makePKVoteRequest(paramString, paramInt);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      JSONObject localJSONObject = super.parseJSON(paramContext, localHttpProxy.httpGet(str1, null, null));
      int i = localJSONObject.optInt("ret");
      String str2 = localJSONObject.optString("word");
      PKModel.getInstance().setWord(str2);
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.KXPKEngine
 * JD-Core Version:    0.6.0
 */