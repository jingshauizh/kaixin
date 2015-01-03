package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.KXDialogNoticeModel;
import com.kaixin001.model.KXDialogNoticeModel.ButtonData;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

public class KXDialogNoticeEngine extends KXEngine
{
  private static final String TAG = "KXPushEngine";
  private static KXDialogNoticeEngine instance = null;

  public static KXDialogNoticeEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new KXDialogNoticeEngine();
      KXDialogNoticeEngine localKXDialogNoticeEngine = instance;
      return localKXDialogNoticeEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private KXDialogNoticeModel.ButtonData loadButtonData(JSONObject paramJSONObject)
    throws Exception
  {
    if (paramJSONObject != null)
    {
      KXDialogNoticeModel.ButtonData localButtonData = new KXDialogNoticeModel.ButtonData();
      localButtonData.mName = paramJSONObject.optString("btnName", "");
      localButtonData.mPageId = paramJSONObject.optString("pageId", "");
      localButtonData.mUrl = paramJSONObject.optString("url", "");
      JSONObject localJSONObject = paramJSONObject.optJSONObject("pagePara");
      Iterator localIterator;
      if (localJSONObject != null)
      {
        localButtonData.mParams = new HashMap();
        localIterator = localJSONObject.keys();
      }
      while (true)
      {
        if (!localIterator.hasNext())
          return localButtonData;
        String str1 = (String)localIterator.next();
        String str2 = localJSONObject.optString(str1, "");
        localButtonData.mParams.put(str1, str2);
      }
    }
    return null;
  }

  private KXDialogNoticeModel parseJSON(JSONObject paramJSONObject)
  {
    KXDialogNoticeModel localKXDialogNoticeModel = KXDialogNoticeModel.getInstance();
    JSONObject localJSONObject;
    if ((paramJSONObject != null) && (this.ret == 1))
      localJSONObject = paramJSONObject.optJSONObject("data");
    try
    {
      localKXDialogNoticeModel.setTitle(localJSONObject.optString("tipTitle", ""));
      localKXDialogNoticeModel.setContent(localJSONObject.optString("tipContent", ""));
      localKXDialogNoticeModel.setButtonData1(loadButtonData(localJSONObject.optJSONObject("btn1")));
      localKXDialogNoticeModel.setButtonData2(loadButtonData(localJSONObject.optJSONObject("btn2")));
      return localKXDialogNoticeModel;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localException.toString();
      KXLog.e("KXPushEngine", "parseJSON error", arrayOfObject);
    }
    return localKXDialogNoticeModel;
  }

  public boolean getDialogNotice(Context paramContext)
  {
    KXDialogNoticeModel.getInstance().clear();
    String str1 = Protocol.getInstance().makeGetDialogNotice();
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      boolean bool = TextUtils.isEmpty(str2);
      localObject = null;
      if (bool);
    }
    catch (Exception localException1)
    {
      try
      {
        JSONObject localJSONObject = super.parseJSON(paramContext, false, str2);
        localObject = localJSONObject;
        KXDialogNoticeModel localKXDialogNoticeModel = parseJSON(localObject);
        int i = 0;
        if (localKXDialogNoticeModel != null)
          i = 1;
        return i;
        localException1 = localException1;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localException1.toString();
        KXLog.e("KXPushEngine", "getDialogNotice error", arrayOfObject);
        String str2 = null;
      }
      catch (Exception localException2)
      {
        while (true)
        {
          localException2.printStackTrace();
          Object localObject = null;
        }
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.KXDialogNoticeEngine
 * JD-Core Version:    0.6.0
 */