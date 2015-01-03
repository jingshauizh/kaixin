package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.task.GetLoginUserRealNameTask;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UploadFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class InfoCompletedEngine extends KXEngine
{
  private static final String LOGTAG = "InfoCompletedEngine";
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_SUCCESS = 1;
  private static InfoCompletedEngine instance;
  public String msg;

  public static InfoCompletedEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new InfoCompletedEngine();
      InfoCompletedEngine localInfoCompletedEngine = instance;
      return localInfoCompletedEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private Object parseCheckNameJson(Context paramContext, String paramString)
  {
    HashMap localHashMap;
    try
    {
      JSONObject localJSONObject2 = super.parseJSON(paramContext, paramString);
      localJSONObject1 = localJSONObject2;
      if (localJSONObject1 == null)
        return null;
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
      JSONObject localJSONObject1;
      while (true)
      {
        localSecurityErrorException.printStackTrace();
        localJSONObject1 = null;
      }
      localJSONObject1.optString("reason");
      localHashMap = new HashMap();
      localHashMap.put("ret", Integer.valueOf(localJSONObject1.optInt("ret", 0)));
      localHashMap.put("reason", localJSONObject1.optString("reason"));
    }
    return localHashMap;
  }

  public int getInfoUpdatedRequest(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeInfoUpdatedRequest(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, "", User.getInstance().getUID(), paramString9);
    HashMap localHashMap = new HashMap();
    if (!TextUtils.isEmpty(paramString7))
    {
      File localFile = new File(paramString7);
      if (!localFile.exists())
        return -1;
      localHashMap.put("upload_img", localFile);
    }
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpPost(str1, localHashMap, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("InfoCompletedEngine", "postInfoCompletedRequest error", localException);
        str2 = null;
      }
    }
    return parseFeedbackJSON(paramContext, str2, paramString8);
  }

  public int parseFeedbackJSON(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    JSONObject localJSONObject = super.parseJSON(paramContext, paramString1);
    if (localJSONObject == null)
      return 0;
    if (Setting.getInstance().isDebug())
      KXLog.d("parseFeedbackJSON", "strContent=" + localJSONObject.toString());
    int i = localJSONObject.optInt("ret", 0);
    String str = localJSONObject.optString("iconsrc");
    if (i == 0)
      this.msg = localJSONObject.optString("msg", null);
    while (i == 0)
    {
      this.msg = localJSONObject.optString("msg", null);
      return i;
      this.msg = null;
      if (TextUtils.isEmpty(str))
        continue;
      User localUser = User.getInstance();
      if (localUser.getUID().compareTo(paramString2) == 0)
      {
        localUser.setLogo120(str);
        localUser.setLogo(str);
        NewsModel.getMyHomeModel().setLogo120(str);
      }
      while (true)
      {
        new Thread(str)
        {
          public void run()
          {
            int i = 0;
            while (true)
            {
              if (i >= 10)
                label8: return;
              try
              {
                sleep(5000L);
                if (GetLoginUserRealNameTask.getMyNameAndLogo(null, this.val$logo120).intValue() == 1)
                  break label8;
                i++;
              }
              catch (InterruptedException localInterruptedException)
              {
                while (true)
                  localInterruptedException.printStackTrace();
              }
            }
          }
        }
        .start();
        break;
        NewsModel.getHomeModel().setLogo120(str);
      }
    }
    this.msg = null;
    return i;
  }

  public int postInfoCompletedRequest(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeInfoCompletedRequest(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, User.getInstance().getUID());
    UploadFile[] arrayOfUploadFile = null;
    if (paramString9 != null)
    {
      arrayOfUploadFile = new UploadFile[1];
      arrayOfUploadFile[0] = new UploadFile(paramString9, "upload_img", "image/jpeg");
    }
    HashMap localHashMap = new HashMap();
    int i;
    int j;
    if (arrayOfUploadFile != null)
    {
      i = arrayOfUploadFile.length;
      j = 0;
    }
    String str2;
    while (true)
    {
      HttpProxy localHttpProxy;
      if (j >= i)
        localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str3 = localHttpProxy.httpPost(str1, localHashMap, null, null);
        str2 = str3;
        if (TextUtils.isEmpty(str2))
        {
          return 0;
          UploadFile localUploadFile = arrayOfUploadFile[j];
          localHashMap.put(localUploadFile.getFormname(), new File(localUploadFile.getFilname()));
          j++;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("InfoCompletedEngine", "postInfoCompletedRequest error", localException);
          str2 = null;
        }
      }
    }
    return parseFeedbackJSON(paramContext, str2, paramString10);
  }

  public Object verifyNameLegal(Context paramContext, String paramString)
  {
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str1;
    try
    {
      String str2 = Protocol.getInstance().makeNameVerify(paramString);
      HashMap localHashMap = new HashMap();
      if (!TextUtils.isEmpty(paramString))
        localHashMap.put("name", paramString);
      String str3 = localHttpProxy.httpPost(str2, localHashMap, null, null);
      str1 = str3;
      if (TextUtils.isEmpty(str1))
        return null;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        str1 = null;
      }
    }
    return parseCheckNameJson(paramContext, str1);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.InfoCompletedEngine
 * JD-Core Version:    0.6.0
 */