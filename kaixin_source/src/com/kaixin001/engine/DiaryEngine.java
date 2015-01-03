package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProgressListener;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UploadFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class DiaryEngine extends KXEngine
{
  private static final String LOGTAG = "DiaryEngine";
  private static DiaryEngine instance = null;
  private int mLastErrorCode = -1;
  private String mLastPostDiaryDid = "";
  private String mLastUploadedDiaryPictureUrl = "";

  public static DiaryEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new DiaryEngine();
      DiaryEngine localDiaryEngine = instance;
      return localDiaryEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  // ERROR //
  public boolean doGetDiaryDetail(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    // Byte code:
    //   0: invokestatic 45	com/kaixin001/network/Protocol:getInstance	()Lcom/kaixin001/network/Protocol;
    //   3: aload_2
    //   4: aload_3
    //   5: invokestatic 50	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   8: invokevirtual 54	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   11: invokevirtual 58	com/kaixin001/network/Protocol:makeGetDiaryDetailRequest	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   14: astore 4
    //   16: new 60	com/kaixin001/network/HttpProxy
    //   19: dup
    //   20: aload_1
    //   21: invokespecial 63	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   24: astore 5
    //   26: aload 5
    //   28: aload 4
    //   30: aconst_null
    //   31: aconst_null
    //   32: invokevirtual 67	com/kaixin001/network/HttpProxy:httpGet	(Ljava/lang/String;Lcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Ljava/lang/String;
    //   35: astore 16
    //   37: aload 16
    //   39: astore 7
    //   41: aload 7
    //   43: ifnonnull +22 -> 65
    //   46: iconst_0
    //   47: ireturn
    //   48: astore 6
    //   50: ldc 8
    //   52: ldc 69
    //   54: aload 6
    //   56: invokestatic 75	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   59: aconst_null
    //   60: astore 7
    //   62: goto -21 -> 41
    //   65: aload_0
    //   66: aload_1
    //   67: aload 7
    //   69: invokespecial 79	com/kaixin001/engine/KXEngine:parseJSON	(Landroid/content/Context;Ljava/lang/String;)Lorg/json/JSONObject;
    //   72: astore 8
    //   74: aload 8
    //   76: ifnonnull +5 -> 81
    //   79: iconst_0
    //   80: ireturn
    //   81: aload_0
    //   82: getfield 82	com/kaixin001/engine/KXEngine:ret	I
    //   85: istore 10
    //   87: iload 10
    //   89: iconst_1
    //   90: if_icmpne +108 -> 198
    //   93: invokestatic 87	com/kaixin001/model/DiaryModel:getInstance	()Lcom/kaixin001/model/DiaryModel;
    //   96: aload_2
    //   97: invokevirtual 91	com/kaixin001/model/DiaryModel:setDiaryId	(Ljava/lang/String;)V
    //   100: invokestatic 87	com/kaixin001/model/DiaryModel:getInstance	()Lcom/kaixin001/model/DiaryModel;
    //   103: aload_3
    //   104: invokevirtual 94	com/kaixin001/model/DiaryModel:setDiaryFuid	(Ljava/lang/String;)V
    //   107: aload 8
    //   109: ldc 96
    //   111: invokevirtual 102	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   114: astore 11
    //   116: aload 8
    //   118: ldc 104
    //   120: invokevirtual 108	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   123: istore 15
    //   125: iload 15
    //   127: istore 13
    //   129: aload 8
    //   131: ldc 110
    //   133: invokevirtual 108	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   136: istore 14
    //   138: invokestatic 87	com/kaixin001/model/DiaryModel:getInstance	()Lcom/kaixin001/model/DiaryModel;
    //   141: aload 11
    //   143: invokevirtual 113	com/kaixin001/model/DiaryModel:setDiaryContent	(Ljava/lang/String;)V
    //   146: invokestatic 87	com/kaixin001/model/DiaryModel:getInstance	()Lcom/kaixin001/model/DiaryModel;
    //   149: iload 13
    //   151: invokevirtual 117	com/kaixin001/model/DiaryModel:setDiaryRepFlag	(I)V
    //   154: invokestatic 87	com/kaixin001/model/DiaryModel:getInstance	()Lcom/kaixin001/model/DiaryModel;
    //   157: iload 14
    //   159: invokevirtual 120	com/kaixin001/model/DiaryModel:setCNum	(I)V
    //   162: iconst_1
    //   163: ireturn
    //   164: invokestatic 87	com/kaixin001/model/DiaryModel:getInstance	()Lcom/kaixin001/model/DiaryModel;
    //   167: aload 8
    //   169: ldc 122
    //   171: invokevirtual 125	org/json/JSONObject:optInt	(Ljava/lang/String;)I
    //   174: invokevirtual 128	com/kaixin001/model/DiaryModel:setErrorNum	(I)V
    //   177: iconst_0
    //   178: ireturn
    //   179: iconst_0
    //   180: ireturn
    //   181: astore 9
    //   183: aload 9
    //   185: invokevirtual 131	org/json/JSONException:printStackTrace	()V
    //   188: iconst_0
    //   189: ireturn
    //   190: astore 12
    //   192: iconst_0
    //   193: istore 13
    //   195: goto -66 -> 129
    //   198: iload 10
    //   200: bipush 100
    //   202: if_icmpeq -38 -> 164
    //   205: iload 10
    //   207: sipush 4001
    //   210: if_icmpne -31 -> 179
    //   213: goto -49 -> 164
    //
    // Exception table:
    //   from	to	target	type
    //   26	37	48	java/lang/Exception
    //   81	87	181	org/json/JSONException
    //   93	116	181	org/json/JSONException
    //   129	162	181	org/json/JSONException
    //   164	177	181	org/json/JSONException
    //   116	125	190	org/json/JSONException
  }

  public boolean doPostDiary(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makePostDiaryRequest(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
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
        KXLog.e("DiaryEngine", "doPostDiary error", localException);
        str2 = null;
      }
      JSONObject localJSONObject = super.parseJSON(paramContext, str2);
      if (localJSONObject == null)
        return false;
      try
      {
        int i = this.ret;
        if (i == 1)
        {
          this.mLastPostDiaryDid = localJSONObject.getString("did");
          return true;
        }
        this.mLastErrorCode = i;
        return false;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return false;
  }

  public boolean doPostDiary(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, UploadFile[] paramArrayOfUploadFile, HttpProgressListener paramHttpProgressListener)
    throws SecurityErrorException
  {
    if (paramString3 == null)
      paramString3 = "";
    if (paramString4 == null)
      paramString4 = "";
    if (paramString5 == null)
      paramString5 = "";
    String str1 = User.getInstance().getUID();
    String str2 = Protocol.getInstance().makePostDiaryRequest(str1, paramString1, paramString3, paramString4, paramString5);
    HashMap localHashMap = new HashMap();
    localHashMap.put("uid", str1);
    localHashMap.put("content", paramString2);
    localHashMap.put("location", paramString3);
    localHashMap.put("lat", paramString4);
    localHashMap.put("lon", paramString5);
    int k;
    int m;
    if (paramArrayOfUploadFile != null)
    {
      k = paramArrayOfUploadFile.length;
      m = 0;
    }
    JSONObject localJSONObject;
    while (true)
    {
      HttpProxy localHttpProxy;
      if (m >= k)
        localHttpProxy = new HttpProxy(paramContext);
      try
      {
        String str4 = localHttpProxy.httpPost(str2, localHashMap, null, paramHttpProgressListener);
        str3 = str4;
        if (TextUtils.isEmpty(str3))
        {
          return false;
          UploadFile localUploadFile = paramArrayOfUploadFile[m];
          localHashMap.put(localUploadFile.getFormname(), new File(localUploadFile.getFilname()));
          m++;
        }
      }
      catch (Exception localException)
      {
        String str3;
        while (true)
        {
          KXLog.e("DiaryEngine", "doPostDiary error", localException);
          str3 = null;
        }
        localJSONObject = super.parseJSON(paramContext, str3);
        if (localJSONObject != null)
          break label253;
      }
    }
    int i = 0;
    while (true)
    {
      return i;
      try
      {
        label253: int j = this.ret;
        if (j == 1)
        {
          this.mLastPostDiaryDid = localJSONObject.getString("did");
          i = 1;
          continue;
        }
        this.mLastErrorCode = j;
        i = 0;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        i = 0;
      }
    }
  }

  public boolean doUploadDiaryPicture(Context paramContext, String paramString1, String paramString2)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeUploadDiaryPictureRequest(paramString1, User.getInstance().getUID());
    Object localObject = null;
    HashMap localHashMap;
    HttpProxy localHttpProxy;
    if (paramString2 != null)
    {
      File localFile = new File(paramString2);
      boolean bool = localFile.exists();
      localObject = null;
      if (bool)
      {
        localHashMap = new HashMap();
        localHashMap.put("upload_pic", localFile);
        localHttpProxy = new HttpProxy(paramContext);
      }
    }
    try
    {
      String str2 = localHttpProxy.httpPost(str1, localHashMap, null, null);
      localObject = str2;
      if (localObject == null)
        return false;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("DiaryEngine", "doUploadDiaryPicture error", localException);
        localObject = null;
      }
      JSONObject localJSONObject = super.parseJSON(paramContext, localObject);
      if (localJSONObject == null)
        return false;
      if (this.ret == 1)
        try
        {
          this.mLastUploadedDiaryPictureUrl = localJSONObject.getString("imgurl");
          return true;
        }
        catch (JSONException localJSONException)
        {
          while (true)
            localJSONException.printStackTrace();
        }
    }
    return false;
  }

  public int getLastErrorCode()
  {
    return this.mLastErrorCode;
  }

  public String getLastPostDiaryDid()
  {
    return this.mLastPostDiaryDid;
  }

  public String getLastUploadedDiaryPictureUrl()
  {
    return this.mLastUploadedDiaryPictureUrl;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.DiaryEngine
 * JD-Core Version:    0.6.0
 */