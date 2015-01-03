package com.tencent.open;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashMap<Ljava.lang.String;Ljava.lang.Object;>;
import java.util.Locale;

public class VoiceHelper extends AsyncTask<Bitmap, Void, HashMap<String, Object>>
{
  private static final String EXT_SDCARD_PATH = "/mnt/sdcard-ext";
  private static final String RESULT_TYPE = "ResultType";
  private static final int RESULT_TYPE_EXCEPTION = 0;
  private static final int RESULT_TYPE_NORMAL = 1;
  private static final String RESULT_VALUE = "ResultValue";
  private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd-HHmmss", Locale.CHINA);
  private VoiceHelper.ImageCallback mCallBack;

  public VoiceHelper(VoiceHelper.ImageCallback paramImageCallback)
  {
    this.mCallBack = paramImageCallback;
  }

  private int calculateInSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    int i = paramOptions.outHeight;
    int j = paramOptions.outWidth;
    int k = 1;
    int m;
    if ((i > paramInt2) || (j > paramInt1))
    {
      k = Math.round(i / paramInt2);
      m = Math.round(j / paramInt1);
      if (k >= m);
    }
    else
    {
      return k;
    }
    return m;
  }

  public static void clearImageCacheFile(String paramString)
  {
    if (!TextUtils.isEmpty(paramString));
    try
    {
      File localFile = new File(paramString);
      if (localFile.exists())
        localFile.delete();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private Bitmap comp(Bitmap paramBitmap)
  {
    int i = 1;
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
    if (localByteArrayOutputStream.toByteArray().length / 1024 > 1024)
    {
      localByteArrayOutputStream.reset();
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 50, localByteArrayOutputStream);
    }
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localByteArrayOutputStream.toByteArray());
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inJustDecodeBounds = i;
    BitmapFactory.decodeStream(localByteArrayInputStream, null, localOptions);
    localOptions.inJustDecodeBounds = false;
    int j = calculateInSampleSize(localOptions, 320, 320);
    if (j <= 0);
    while (true)
    {
      Log.i("comp", "comp be=" + i);
      localOptions.inSampleSize = i;
      return BitmapFactory.decodeStream(new ByteArrayInputStream(localByteArrayOutputStream.toByteArray()), null, localOptions);
      i = j;
    }
  }

  private String getDateStringWithoutSpace(long paramLong)
  {
    Date localDate = new Date(paramLong);
    return sdf2.format(localDate);
  }

  private String getSDCardPath()
  {
    String str = ".";
    if (Environment.getExternalStorageState().equals("mounted"))
      str = Environment.getExternalStorageDirectory().getAbsolutePath();
    do
      return str;
    while (!new File("/mnt/sdcard-ext").isDirectory());
    return "/mnt/sdcard-ext";
  }

  public static boolean hasSDCard()
  {
    if (Environment.getExternalStorageState().equals("mounted"));
    do
      return true;
    while (new File("/mnt/sdcard-ext").isDirectory());
    return false;
  }

  // ERROR //
  private String saveImageToSDCard(Bitmap paramBitmap)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 129	java/lang/StringBuilder
    //   5: dup
    //   6: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   9: aload_0
    //   10: invokestatic 200	java/lang/System:currentTimeMillis	()J
    //   13: invokespecial 202	com/tencent/open/VoiceHelper:getDateStringWithoutSpace	(J)Ljava/lang/String;
    //   16: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   19: ldc 204
    //   21: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: invokevirtual 143	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   27: astore 8
    //   29: new 129	java/lang/StringBuilder
    //   32: dup
    //   33: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   36: aload_0
    //   37: invokespecial 206	com/tencent/open/VoiceHelper:getSDCardPath	()Ljava/lang/String;
    //   40: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: getstatic 209	java/io/File:separator	Ljava/lang/String;
    //   46: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: ldc 211
    //   51: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: invokevirtual 143	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   57: astore 9
    //   59: new 129	java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   66: aload 9
    //   68: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: getstatic 209	java/io/File:separator	Ljava/lang/String;
    //   74: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: aload 8
    //   79: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: invokevirtual 143	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   85: astore 6
    //   87: new 75	java/io/File
    //   90: dup
    //   91: aload 9
    //   93: invokespecial 77	java/io/File:<init>	(Ljava/lang/String;)V
    //   96: astore 10
    //   98: aload 10
    //   100: invokevirtual 81	java/io/File:exists	()Z
    //   103: ifne +11 -> 114
    //   106: aload 10
    //   108: invokevirtual 214	java/io/File:mkdirs	()Z
    //   111: ifne +3 -> 114
    //   114: new 75	java/io/File
    //   117: dup
    //   118: aload 6
    //   120: invokespecial 77	java/io/File:<init>	(Ljava/lang/String;)V
    //   123: astore 11
    //   125: aload 11
    //   127: invokevirtual 81	java/io/File:exists	()Z
    //   130: ifeq +9 -> 139
    //   133: aload 11
    //   135: invokevirtual 84	java/io/File:delete	()Z
    //   138: pop
    //   139: aload 11
    //   141: invokevirtual 217	java/io/File:createNewFile	()Z
    //   144: pop
    //   145: new 219	java/io/FileOutputStream
    //   148: dup
    //   149: aload 11
    //   151: invokespecial 222	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   154: astore 13
    //   156: aload_1
    //   157: getstatic 225	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
    //   160: bipush 100
    //   162: aload 13
    //   164: invokevirtual 101	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   167: pop
    //   168: aload 13
    //   170: invokevirtual 228	java/io/FileOutputStream:flush	()V
    //   173: aload 13
    //   175: ifnull +8 -> 183
    //   178: aload 13
    //   180: invokevirtual 231	java/io/FileOutputStream:close	()V
    //   183: aload 6
    //   185: areturn
    //   186: astore 5
    //   188: ldc 233
    //   190: astore 6
    //   192: aload_2
    //   193: ifnull -10 -> 183
    //   196: aload_2
    //   197: invokevirtual 231	java/io/FileOutputStream:close	()V
    //   200: aload 6
    //   202: areturn
    //   203: astore 7
    //   205: aload 6
    //   207: areturn
    //   208: astore_3
    //   209: aload_2
    //   210: ifnull +7 -> 217
    //   213: aload_2
    //   214: invokevirtual 231	java/io/FileOutputStream:close	()V
    //   217: aload_3
    //   218: athrow
    //   219: astore 16
    //   221: aload 6
    //   223: areturn
    //   224: astore 4
    //   226: goto -9 -> 217
    //   229: astore_3
    //   230: aload 13
    //   232: astore_2
    //   233: goto -24 -> 209
    //   236: astore 14
    //   238: aload 13
    //   240: astore_2
    //   241: goto -53 -> 188
    //
    // Exception table:
    //   from	to	target	type
    //   2	114	186	java/lang/Exception
    //   114	139	186	java/lang/Exception
    //   139	156	186	java/lang/Exception
    //   196	200	203	java/io/IOException
    //   2	114	208	finally
    //   114	139	208	finally
    //   139	156	208	finally
    //   178	183	219	java/io/IOException
    //   213	217	224	java/io/IOException
    //   156	173	229	finally
    //   156	173	236	java/lang/Exception
  }

  protected HashMap<String, Object> doInBackground(Bitmap[] paramArrayOfBitmap)
  {
    HashMap localHashMap = new HashMap();
    try
    {
      Bitmap localBitmap1 = paramArrayOfBitmap[0];
      if (localBitmap1 != null)
      {
        Object localObject;
        if ((localBitmap1.getWidth() > 320) || (localBitmap1.getHeight() > 320))
        {
          Bitmap localBitmap2 = comp(localBitmap1);
          localObject = saveImageToSDCard(localBitmap2);
          localBitmap2.recycle();
        }
        while (true)
        {
          localBitmap1.recycle();
          localHashMap.put("ResultType", Integer.valueOf(1));
          localHashMap.put("ResultValue", localObject);
          return localHashMap;
          String str = saveImageToSDCard(localBitmap1);
          localObject = str;
        }
      }
    }
    catch (Exception localException)
    {
      localHashMap.put("ResultType", Integer.valueOf(0));
      localHashMap.put("ResultValue", localException.getMessage());
    }
    return (HashMap<String, Object>)localHashMap;
  }

  protected void onPostExecute(HashMap<String, Object> paramHashMap)
  {
    if (((Integer)paramHashMap.get("ResultType")).intValue() == 1)
      this.mCallBack.onSuccess((String)paramHashMap.get("ResultValue"));
    while (true)
    {
      super.onPostExecute(paramHashMap);
      return;
      this.mCallBack.onFailed((String)paramHashMap.get("ResultValue"));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.VoiceHelper
 * JD-Core Version:    0.6.0
 */