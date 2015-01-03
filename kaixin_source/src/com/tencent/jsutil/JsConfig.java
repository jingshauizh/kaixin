package com.tencent.jsutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.tencent.connect.a.a;
import com.tencent.javascript.RawFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class JsConfig
{
  public static final String CHECK_UP_URL = "http://s.p.qq.com/pub/check_bizup";
  public static final String CONFIG_FILE = "config.json";
  public static final String DIR_ASSERT_ROOT = "tencent/js";
  public static final String DIR_ASSERT_ZIP = "tencent/zip";
  public static final int FREQUENCY_TIME = 30;
  private static final String TAG = "JsConfig";
  public static final String TENCENT_FILE_PATH = "file:///android_asset/tencent/js/tencent.html";
  public static final String ZIP_FILE_NAME = "js.zip";
  private static JsConfig config;
  private static Context mContext = null;
  private static String mTencentFilePath;
  public static String mTencentFileProtocolPath;
  private String mDirApp;
  private String mDirJsRoot;
  private String mDirZipTemp;
  SharedPreferences.Editor mEditor;
  SharedPreferences mPreferences;

  static
  {
    config = null;
  }

  private JsConfig(Context paramContext)
  {
    this.mPreferences = paramContext.getSharedPreferences("js_config", 0);
    mContext = paramContext;
    initEnv();
  }

  public static JsConfig getInstance(Context paramContext)
  {
    if (config == null)
      config = new JsConfig(paramContext);
    return config;
  }

  private void initEnv()
  {
    this.mDirApp = (mContext.getFilesDir() + "/");
    Log.d("JsConfig", this.mDirApp);
    this.mDirJsRoot = (this.mDirApp + "tencent/js");
    mTencentFilePath = this.mDirJsRoot + File.separator + "tencent.html";
    mTencentFileProtocolPath = "file://" + this.mDirJsRoot + File.separator + "tencent.html";
    File localFile1 = new File(this.mDirJsRoot);
    if (!localFile1.exists())
      localFile1.mkdirs();
    Log.d("JsConfig", this.mDirJsRoot);
    this.mDirZipTemp = (this.mDirApp + "tencent/temp");
    Log.d("JsConfig", this.mDirZipTemp);
    File localFile2 = new File(this.mDirZipTemp);
    if (!localFile2.exists())
      localFile2.mkdirs();
  }

  public JSONObject getConfig()
  {
    File localFile = new File(this.mDirJsRoot + "/config.json");
    URL localURL;
    if (!localFile.exists())
      localURL = RawFile.class.getResource("js.zip");
    Object localObject;
    while (true)
    {
      try
      {
        InputStream localInputStream = localURL.openConnection().getInputStream();
        localObject = localInputStream;
        if (localObject != null)
          break;
        return null;
      }
      catch (IOException localIOException2)
      {
        localIOException2.printStackTrace();
        localObject = null;
        continue;
      }
      try
      {
        localObject = new FileInputStream(localFile);
      }
      catch (IOException localIOException1)
      {
        localIOException1.printStackTrace();
        localObject = null;
      }
    }
    String str = a.a((InputStream)localObject);
    try
    {
      JSONObject localJSONObject = new JSONObject(str);
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return (JSONObject)null;
  }

  public String getDirJsRoot()
  {
    return this.mDirJsRoot;
  }

  public String getDirZipTemp()
  {
    return this.mDirZipTemp;
  }

  public long getFrequency()
  {
    return this.mPreferences.getLong("frequency", 30L);
  }

  public String getJsVersion()
  {
    return this.mPreferences.getString("js_version", "0");
  }

  public long getLastUpdateTime()
  {
    return this.mPreferences.getLong("last_update", 0L);
  }

  public String getTencentFilePath()
  {
    return mTencentFilePath;
  }

  public String getTencentFileProtocolPath()
  {
    return mTencentFileProtocolPath;
  }

  public JSONObject readConfigFromZip(File paramFile)
  {
    Log.d("JsConfig", "file exists:" + paramFile.exists());
    try
    {
      localZipInputStream = new ZipInputStream(new FileInputStream(paramFile));
      ZipEntry localZipEntry = localZipInputStream.getNextEntry();
      if (localZipEntry != null)
        if (localZipEntry.getName().equals("config.json"))
        {
          localStringBuffer = new StringBuffer();
          localScanner = new Scanner(localZipInputStream);
          while (localScanner.hasNextLine())
            localStringBuffer.append(localScanner.nextLine()).append("\n");
        }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      StringBuffer localStringBuffer;
      Scanner localScanner;
      localFileNotFoundException.printStackTrace();
      return null;
      JSONObject localJSONObject = new JSONObject(localStringBuffer.toString());
      localZipInputStream.closeEntry();
      localScanner.close();
      localZipInputStream.close();
      return localJSONObject;
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        localIOException.printStackTrace();
        continue;
        localZipInputStream.closeEntry();
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        ZipInputStream localZipInputStream;
        localJSONException.printStackTrace();
        continue;
        localZipInputStream.close();
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void setFrequency(long paramLong)
  {
    this.mEditor = this.mPreferences.edit();
    this.mEditor.putLong("frequency", paramLong);
    this.mEditor.commit();
  }

  public void setJsVersion(String paramString)
  {
    this.mEditor = this.mPreferences.edit();
    this.mEditor.putString("js_version", paramString);
    this.mEditor.commit();
  }

  public void setLastUpdateTime(long paramLong)
  {
    this.mEditor = this.mPreferences.edit();
    this.mEditor.putLong("last_update", paramLong);
    this.mEditor.commit();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JsConfig
 * JD-Core Version:    0.6.0
 */