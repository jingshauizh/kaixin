package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.item.GiftNewsItem;
import com.kaixin001.model.GiftNewsModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class GiftNewsEngine extends KXEngine
{
  private static final String LOGTAG = "GiftNewsEngine";
  public static final String NEWS_GIFT_FILE = "news_gift.kx";
  private static GiftNewsEngine instance;

  public static GiftNewsEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new GiftNewsEngine();
      GiftNewsEngine localGiftNewsEngine = instance;
      return localGiftNewsEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int doGetGiftNews(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeGetGiftNewsRequest(paramString);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
      {
        i = -1002;
        return i;
      }
    }
    catch (Exception localException)
    {
      String str2;
      int i;
      do
      {
        while (true)
        {
          KXLog.e("GiftNewsEngine", "getGiftList error", localException);
          str2 = null;
        }
        i = parseGiftNewsJSON(paramContext, str2, paramString);
      }
      while (i != 1);
      if (((TextUtils.isEmpty(paramString)) || (paramString.compareTo("0") == 0)) && (!setGiftNewsData(paramContext, str2)))
        return -1001;
    }
    return 1;
  }

  public boolean loadGiftNewsCache(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)));
    String str;
    do
    {
      return false;
      str = FileUtil.getCacheData(FileUtil.getKXCacheDir(paramContext), paramString, "news_gift.kx");
    }
    while ((TextUtils.isEmpty(str)) || (parseGiftNewsJSON(paramContext, str, "0") != 1));
    return true;
  }

  public int parseGiftNewsJSON(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    JSONObject localJSONObject1 = super.parseJSON(paramContext, paramString1);
    if (localJSONObject1 == null)
      return -1001;
    if (Setting.getInstance().isDebug())
      KXLog.d("parseGiftListJSON", "strContent=" + localJSONObject1.toString());
    while (true)
    {
      int i;
      int m;
      try
      {
        GiftNewsModel localGiftNewsModel = GiftNewsModel.getInstance();
        if (paramString2.equals(Integer.toString(localGiftNewsModel.giftNewsList.size())))
          continue;
        localGiftNewsModel.giftNewsList.clear();
        this.ret = localJSONObject1.optInt("ret", 0);
        if (this.ret == 1)
        {
          localGiftNewsModel.total = localJSONObject1.optInt("total", 0);
          JSONArray localJSONArray1 = localJSONObject1.getJSONArray("list");
          if (localJSONArray1 != null)
            continue;
          i = 0;
          if (i >= 20)
            break label391;
          localGiftNewsModel.isHasMore = false;
          break label391;
          i = localJSONArray1.length();
          continue;
          GiftNewsItem localGiftNewsItem = new GiftNewsItem();
          JSONObject localJSONObject2 = localJSONArray1.getJSONObject(j);
          localGiftNewsItem.mIntro = localJSONObject2.getString("intro");
          JSONObject localJSONObject3 = localJSONObject2.optJSONObject("receiver");
          localGiftNewsItem.mRuid = localJSONObject3.getString("ruid");
          localGiftNewsItem.mRname = localJSONObject3.getString("name");
          localGiftNewsItem.mRlogo = localJSONObject3.getString("logo");
          localGiftNewsItem.mRgender = localJSONObject3.getString("gender");
          JSONArray localJSONArray2 = localJSONObject2.optJSONObject("senders").getJSONArray("sender_list");
          if (localJSONArray2 != null)
            continue;
          int k = 0;
          break label403;
          if (m < k)
            continue;
          localGiftNewsModel.giftNewsList.add(localGiftNewsItem);
          j++;
          break label394;
          k = localJSONArray2.length();
          break label403;
          JSONObject localJSONObject4 = localJSONArray2.getJSONObject(m).getJSONObject("sender");
          if (m != 0)
            continue;
          localGiftNewsItem.mStime = localJSONObject4.getString("stime");
          String str = localJSONArray2.getJSONObject(m).getJSONObject("gift").getString("icon");
          localGiftNewsItem.mSgiftLogoList.add(str);
          m++;
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("parseGiftNewsJSON", "parseGiftNewsJSON", localException);
        return -1001;
      }
      return this.ret;
      label391: int j = 0;
      label394: if (j >= i)
      {
        return 1;
        label403: m = 0;
      }
    }
  }

  public boolean setGiftNewsData(Context paramContext, String paramString)
  {
    try
    {
      FileUtil.setCacheData(FileUtil.getKXCacheDir(paramContext), User.getInstance().getUID(), "news_gift.kx", paramString);
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.GiftNewsEngine
 * JD-Core Version:    0.6.0
 */