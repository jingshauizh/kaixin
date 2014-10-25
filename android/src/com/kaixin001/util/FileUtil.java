package com.kaixin001.util;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import com.kaixin001.item.CloudAlbumPicItem;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Date;

public class FileUtil
{
  private static final String TAG = "FileUtil";

  private static boolean checkDataDirs(String paramString1, String paramString2)
  {
    String str = paramString1 + "/data";
    File localFile1 = new File(str);
    if ((!localFile1.exists()) && (!localFile1.mkdirs()))
    {
      KXLog.d("checkDataDirs", "!dataCache.mkdir()");
      return false;
    }
    File[] arrayOfFile = localFile1.listFiles(new FileFilter()
    {
      public boolean accept(File paramFile)
      {
        return paramFile.isDirectory();
      }
    });
    if (arrayOfFile.length == 10)
      delOldestFile(arrayOfFile);
    File localFile2 = new File(str + "/" + paramString2);
    if ((!localFile2.exists()) && (!localFile2.mkdirs()))
    {
      KXLog.d("checkDataDirs", "!userCache.mkdir()");
      return false;
    }
    return true;
  }

  // ERROR //
  public static boolean copyFile(String paramString1, String paramString2, String paramString3, String paramString4)
  {
   return false;
  }

  public static void delOldestFile(File[] paramArrayOfFile)
  {
    int i = paramArrayOfFile.length;
    long l1 = 0L;
    int j = 0;
    for (int k = 0; ; k++)
    {
      if (k >= i)
      {
        if (!deleteDirectory(paramArrayOfFile[j]))
          KXLog.d("delOldestFile", "deleteDirectory failed");
        return;
      }
      long l2 = paramArrayOfFile[k].lastModified();
      if (k == 0)
        l1 = l2;
      if (l1 <= l2)
        continue;
      l1 = l2;
      j = k;
    }
  }

  public static boolean deleteCacheFile(String paramString)
  {
    File localFile = new File(paramString);
    if (localFile.exists())
      return localFile.delete();
    return true;
  }

  public static boolean deleteCacheFile(String paramString1, String paramString2)
  {
    File localFile = new File(paramString1, paramString2);
    if (localFile.exists())
      return localFile.delete();
    return true;
  }

  public static boolean deleteCacheFile(String paramString1, String paramString2, String paramString3)
  {
    File localFile = new File(paramString1 + "/data/" + paramString2 + "/" + paramString3);
    if (localFile.exists())
      return localFile.delete();
    return true;
  }

  public static boolean deleteDirectory(File paramFile)
  {
    if (paramFile == null)
      return false;
    if (paramFile.isFile())
      return paramFile.delete();
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile != null);
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfFile.length)
        return paramFile.delete();
      deleteDirectory(arrayOfFile[i]);
    }
  }

  public static void deleteFileWithoutCheckReturnValue(File paramFile)
  {
    deleteDirectory(paramFile);
  }

  public static void deleteFileWithoutCheckReturnValue(String paramString)
  {
    deleteDirectory(new File(paramString));
  }

  public static boolean ensureEmptyFile(File paramFile)
  {
    File localFile = paramFile.getParentFile();
    if ((localFile != null) && (localFile.exists()))
    {
      if (paramFile.exists())
        return paramFile.delete();
    }
    else if ((localFile != null) && (!localFile.mkdirs()))
      return false;
    return true;
  }

  public static boolean existCacheFile(String paramString)
  {
    return new File(paramString).exists();
  }

  public static boolean existCacheFile(String paramString1, String paramString2)
  {
    return new File(paramString1, paramString2).exists();
  }

  // ERROR //
  public static Bitmap getBmpFromFile(Context paramContext, String paramString)
  {
	  return null;
  }

  public static String getCacheDir(String paramString1, String paramString2)
  {
    return paramString1 + "/data/" + paramString2;
  }

  public static String getDynamicFileName(String paramString1, String paramString2)
  {
    String str1 = getCacheDir(paramString1, User.getInstance().getUID());
    long l = new Date().getTime();
    int i = paramString2.indexOf(".jpg", -5 + paramString2.length());
    String str2 = null;
    if (i != -1)
      str2 = paramString2.substring(0, i) + Long.toString(l);
    int k;
    for (int j = 0; ; j = k)
    {
      if (!existCacheFile(str1, str2 + ".jpg"))
      {
        
        str2 = paramString2 + Long.toString(l);
        break;
      }
      StringBuilder localStringBuilder = new StringBuilder(String.valueOf(str2));
      k = j + 1;
      str2 = Integer.toString(j);
    }
    return str2 + ".jpg";
  }

  public static String getDynamicFilePath(String paramString)
  {
    int i = 0;
    KXLog.d("sOriFilePath", "sOriFilePath=" + paramString);
    long l = new Date().getTime();
    int j = paramString.indexOf(".jpg");
    String str = "";
    if (j != -1)
      str = paramString.substring(0, j) + Long.toString(l);
   
      if (!existCacheFile(str + ".jpg"))
      {
        
        str = paramString + Long.toString(l);
        i = 0;
        
      }
      StringBuilder localStringBuilder = new StringBuilder(String.valueOf(str));
      int k = i + 1;
      str = Integer.toString(i);
      i = k;
  
    return str + ".jpg";
  }

  public static String getKXCacheDir(Context paramContext)
  {
    if (paramContext == null)
      return null;
    if (Environment.getExternalStorageState().equals("mounted"))
      return Environment.getExternalStorageDirectory().getAbsolutePath() + "/kaixin001/cache";
    return paramContext.getCacheDir().getAbsolutePath();
  }

  public static int getUploadPicMaxWid(Context paramContext)
  {
    int i = (int)Math.sqrt((((ActivityManager)paramContext.getSystemService("activity")).getMemoryClass() << 20) / 4 / 4);
    if (i >= 1000)
      i = 1000;
   
    return i;
  }

  public static boolean isPathExist(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      File localFile = new File(paramString);
      if ((localFile != null) && (localFile.exists()))
        return true;
    }
    return false;
  }

  public static boolean isPicExist(Context paramContext, String paramString)
  {
    Cursor localCursor = null;
    while (true)
    {
      try
      {
        String[] arrayOfString1 = { "_data", "date_added", "_id", "mini_thumb_magic", "_size" };
        String[] arrayOfString2 = { paramString };
        localCursor = paramContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOfString1, "_id = ?", arrayOfString2, "date_added");
        if (localCursor.moveToFirst())
        {
          String str = localCursor.getString(0);
          if (!TextUtils.isEmpty(str))
          {
            File localFile = new File(str);
            if ((localFile != null) && (localFile.exists()))
            {
              long l = localFile.length();
              if (l > 100L)
                return true;
            }
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return false;
      }
      finally
      {
        if (localCursor == null)
          continue;
        localCursor.close();
      }
      if (localCursor == null)
        continue;
      localCursor.close();
    }
  }

  public static boolean isScreenShot(Context paramContext, String paramString)
  {
    WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
    int i = localWindowManager.getDefaultDisplay().getWidth();
    int j = localWindowManager.getDefaultDisplay().getHeight();
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    try
    {
      localOptions.inJustDecodeBounds = true;
      Bitmap localBitmap = BitmapFactory.decodeFile(paramString, localOptions);
      if ((localBitmap != null) && (!localBitmap.isRecycled()))
      {
        localBitmap.recycle();
        System.gc();
      }
      int k = localOptions.outWidth;
      int m = localOptions.outHeight;
      int n = 0;
      if (k == i)
      {
        n = 0;
        if (m == j)
          n = 1;
      }
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return false;
    }
    
  }

  // ERROR //
  public static Bitmap loadBitmapFromFile(String paramString, int paramInt)
  {
 return null;
  }

  // ERROR //
  public static Bitmap loadBitmapFromFile(String paramString, int paramInt1, int paramInt2)
  {
   return null;
  }

  public static String loadLatestPic(Context paramContext)
  {
    Cursor localCursor = null;
    while (true)
    {
      try
      {
        String[] arrayOfString = { "_data", "date_added", "_id" };
        localCursor = paramContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOfString, null, null, "date_added");
        if (localCursor.moveToLast())
        {
          String str = localCursor.getString(0);
          return str;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      finally
      {
        if (localCursor == null)
          continue;
        localCursor.close();
      }
      if (localCursor == null)
        continue;
      localCursor.close();
    }
  }

  public static ArrayList<CloudAlbumPicItem> loadLocalPictures(Context paramContext, String paramString, int paramInt, boolean paramBoolean)
  {
    KXLog.d("CloudAlbumManager", " + loadLocalPictures(" + paramString + ")");
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = null;
    while (true)
    {
      boolean bool2;
      try
      {
        String[] arrayOfString1 = { "_data", "date_added", "_id" };
        boolean bool1 = TextUtils.isEmpty(paramString);
        String str1 = null;
        String[] arrayOfString2 = null;
        if (bool1)
          continue;
        if (!paramBoolean)
          continue;
        str1 = "date_added < ?";
        arrayOfString2 = new String[] { paramString };
        localCursor = paramContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOfString1, str1, arrayOfString2, "date_added");
        KXLog.d("CloudAlbumManager", "cursor.getCount():" + localCursor.getCount());
        bool2 = localCursor.moveToLast();
       
        KXLog.d("CloudAlbumManager", "load availabe pic num:" + localArrayList.size());
       
        str1 = "date_added > ?";
       
        String str2 = localCursor.getString(0);
        long l = Long.parseLong(localCursor.getString(1));
        String str3 = localCursor.getString(2);
        if (skipPicture(str2))
          continue;
        CloudAlbumPicItem localCloudAlbumPicItem = new CloudAlbumPicItem();
        localCloudAlbumPicItem.mUrl = str2;
        localCloudAlbumPicItem.mMD5 = null;
        localCloudAlbumPicItem.mThumbUrl = str3;
        localCloudAlbumPicItem.mLastModfiedTime = l;
        localArrayList.add(localCloudAlbumPicItem);
        KXLog.d("CloudAlbumManager", "Load IMG:" + localCloudAlbumPicItem.mUrl + ", " + KXTextUtil.formatTimestamp(1000L * localCloudAlbumPicItem.mLastModfiedTime) + ", " + localCloudAlbumPicItem.mLastModfiedTime);
        if ((paramInt > 0) && (localArrayList.size() >= paramInt))
          continue;
        boolean bool3 = localCursor.moveToPrevious();
        bool2 = bool3;
        return localArrayList;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return localArrayList;
      }
      finally
      {
        if (localCursor == null)
          continue;
        localCursor.close();
      }
     
    }
  }

  public static boolean makeDirExist(File paramFile)
  {
    if (paramFile.exists())
    	return true;
    paramFile.mkdirs();
    return false;
  }

  public static boolean makeDirExist(String paramString)
  {
    return makeDirExist(new File(paramString));
  }

  public static boolean makeEmptyFile(File paramFile)
  {
    File localFile = paramFile.getParentFile();
    if ((localFile != null) && (localFile.exists()))
    {
      if (paramFile.exists())
        return paramFile.delete();
    }
    else if ((localFile != null) && (!localFile.mkdirs()))
      return false;
    return true;
  }

  public static boolean makeEmptyFile(String paramString)
  {
    return makeEmptyFile(new File(paramString));
  }

  public static boolean makeEmptyFile(String paramString1, String paramString2)
  {
    return makeEmptyFile(new File(paramString1, paramString2));
  }

  // ERROR //
  public static byte[] md5(File paramFile)
  {
   return null;
  }

  public static boolean renameCacheFile(String paramString1, String paramString2, String paramString3)
  {
    String str = getCacheDir(paramString1, User.getInstance().getUID());
    File localFile = new File(str, paramString3);
    if (!makeEmptyFile(localFile))
      return false;
    return new File(str, paramString2).renameTo(localFile);
  }

  public static boolean renameCachePath(String paramString1, String paramString2)
  {
    File localFile = new File(paramString2);
    if (!makeEmptyFile(localFile))
      return false;
    return new File(paramString1).renameTo(localFile);
  }

  // ERROR //
  public static String savePicture(String paramString)
  {
    return null;
  }

  // ERROR //
  public static boolean setCacheData(String paramString1, String paramString2, String paramString3, String paramString4)
  {
     return false;
  }

  public static boolean skipPicture(String paramString)
  {
    String str1 = Setting.getInstance().getDeviceName();
    String str2 = Setting.getInstance().getManufacturerName();

    return false;
  }

public static String getCacheData(String paramString1, String paramString2,
		String paramString3) {
	// TODO Auto-generated method stub
	return null;
}
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.FileUtil
 * JD-Core Version:    0.6.0
 */