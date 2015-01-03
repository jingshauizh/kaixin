package com.tencent.connect.a;

import android.content.Context;
import com.tencent.jsutil.JsConfig;
import com.tencent.sdkutil.HttpUtils;
import com.tencent.sdkutil.Security;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class b
{
  private static String b = "0";
  JsConfig a;
  private Context c;

  public b(Context paramContext)
  {
    this.c = paramContext;
    this.a = JsConfig.getInstance(this.c);
  }

  public void a()
  {
    JSONObject localJSONObject = this.a.getConfig();
    try
    {
      long l = localJSONObject.getLong("frequency");
      if ((System.currentTimeMillis() - this.a.getLastUpdateTime()) / 60000L < l)
        return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    b = this.a.getJsVersion();
    if (b == null)
      b = "0";
    b();
  }

  public boolean a(File paramFile)
  {
    a.a(paramFile, new File(this.a.getDirZipTemp()));
    return Security.verify(this.a.getDirZipTemp());
  }

  public void b()
  {
    String str1 = HttpUtils.getFromUrl("http://s.p.qq.com/pub/check_bizup?qver=" + "2.2" + "&hver=" + b + "&pf=3" + "&biz=" + "107" + "&uin=" + 0 + "&t=" + System.currentTimeMillis());
    if (str1 == null);
    while (true)
    {
      return;
      try
      {
        JSONObject localJSONObject1 = new JSONObject(str1);
        if ((localJSONObject1.getInt("r") != 0) || (localJSONObject1.getInt("type") <= 0))
          continue;
        String str2 = localJSONObject1.getString("url");
        if ((str2 == null) || (a.a(str2, this.a.getDirZipTemp(), "js.zip") != true))
          continue;
        this.a.setLastUpdateTime(System.currentTimeMillis());
        JSONObject localJSONObject2 = this.a.readConfigFromZip(new File(this.a.getDirZipTemp() + File.separator + "js.zip"));
        if (localJSONObject2 == null)
          continue;
        this.a.setJsVersion(localJSONObject2.getString("version"));
        this.a.setFrequency(localJSONObject2.getLong("frequency"));
        return;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
  }

  public boolean c()
  {
    File localFile = new File(this.a.getDirZipTemp() + File.separator + "js.zip");
    if (!localFile.exists());
    do
      return false;
    while (!a(localFile));
    a.a(localFile, new File(this.a.getDirJsRoot()));
    a.a(this.a.getDirZipTemp());
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.connect.a.b
 * JD-Core Version:    0.6.0
 */