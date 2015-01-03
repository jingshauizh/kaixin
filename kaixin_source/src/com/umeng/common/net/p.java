package com.umeng.common.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.animation.Animation;
import android.widget.ImageView;
import com.umeng.common.Log;
import com.umeng.common.b;
import com.umeng.common.b.g;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Stack;

public class p
{
  public static boolean a = false;
  private static final String b = p.class.getName();
  private static final long c = 104857600L;
  private static final long d = 10485760L;

  static
  {
    a = false;
  }

  private static long a(File paramFile)
  {
    long l1;
    if ((paramFile == null) || (!paramFile.exists()) || (!paramFile.isDirectory()))
      l1 = 0L;
    while (true)
    {
      return l1;
      Stack localStack = new Stack();
      localStack.clear();
      localStack.push(paramFile);
      l1 = 0L;
      while (!localStack.isEmpty())
      {
        File[] arrayOfFile = ((File)localStack.pop()).listFiles();
        long l2 = l1;
        int i = 0;
        if (i < arrayOfFile.length)
        {
          long l3;
          if (arrayOfFile[i].isDirectory())
          {
            localStack.push(arrayOfFile[i]);
            l3 = l2;
          }
          while (true)
          {
            i++;
            l2 = l3;
            break;
            l3 = l2 + arrayOfFile[i].length();
          }
        }
        l1 = l2;
      }
    }
  }

  private static Bitmap a(Bitmap paramBitmap)
  {
    try
    {
      Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      Paint localPaint = new Paint();
      Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
      RectF localRectF = new RectF(localRect);
      localPaint.setAntiAlias(true);
      localCanvas.drawARGB(0, 0, 0, 0);
      localPaint.setColor(-12434878);
      localCanvas.drawRoundRect(localRectF, paramBitmap.getWidth() / 6, paramBitmap.getHeight() / 6, localPaint);
      localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
      paramBitmap.recycle();
      return localBitmap;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      Log.e(b, "Cant`t create round corner bitmap. [OutOfMemoryError] ");
    }
    return null;
  }

  public static String a(Context paramContext, String paramString)
  {
    if (g.c(paramString))
      return null;
    label265: String str3;
    try
    {
      String str1 = b(paramString) + ".tmp";
      String str2;
      long l;
      File localFile2;
      if (b.b())
      {
        str2 = Environment.getExternalStorageDirectory().getCanonicalPath();
        l = 104857600L;
        localFile2 = new File(str2 + "/download/.um");
        if (!localFile2.exists())
          break label265;
        if (a(localFile2.getCanonicalFile()) > l)
          b(localFile2);
      }
      while (true)
      {
        localFile1 = new File(localFile2, str1);
        try
        {
          localFile1.createNewFile();
          localFileOutputStream = new FileOutputStream(localFile1);
          localInputStream = (InputStream)new URL(paramString).openConnection().getContent();
          byte[] arrayOfByte = new byte[4096];
          while (true)
          {
            int i = localInputStream.read(arrayOfByte);
            if (i == -1)
              break;
            localFileOutputStream.write(arrayOfByte, 0, i);
          }
        }
        catch (Exception localException1)
        {
        }
        Log.a(b, localException1.getStackTrace().toString() + "\t url:\t" + g.a + paramString);
        if ((localFile1 != null) && (localFile1.exists()))
          localFile1.deleteOnExit();
        return null;
        str2 = paramContext.getCacheDir().getCanonicalPath();
        l = 10485760L;
        break;
        if (localFile2.mkdirs())
          continue;
        Log.b(b, "Failed to create directory" + localFile2.getAbsolutePath() + ". Check permission. Make sure WRITE_EXTERNAL_STORAGE is added in your Manifest.xml");
      }
    }
    catch (Exception localException2)
    {
      File localFile1;
      FileOutputStream localFileOutputStream;
      InputStream localInputStream;
      while (true)
        localFile1 = null;
      localFileOutputStream.flush();
      localInputStream.close();
      localFileOutputStream.close();
      File localFile3 = new File(localFile1.getParent(), localFile1.getName().replace(".tmp", ""));
      localFile1.renameTo(localFile3);
      Log.a(b, "download img[" + paramString + "]  to " + localFile3.getCanonicalPath());
      str3 = localFile3.getCanonicalPath();
    }
    return str3;
  }

  public static void a(Context paramContext, ImageView paramImageView, String paramString, boolean paramBoolean)
  {
    a(paramContext, paramImageView, paramString, paramBoolean, null, null, false);
  }

  public static void a(Context paramContext, ImageView paramImageView, String paramString, boolean paramBoolean, a parama)
  {
    a(paramContext, paramImageView, paramString, paramBoolean, parama, null, false);
  }

  public static void a(Context paramContext, ImageView paramImageView, String paramString, boolean paramBoolean, a parama, Animation paramAnimation)
  {
    a(paramContext, paramImageView, paramString, paramBoolean, parama, null, false);
  }

  public static void a(Context paramContext, ImageView paramImageView, String paramString, boolean paramBoolean1, a parama, Animation paramAnimation, boolean paramBoolean2)
  {
    if (paramImageView == null);
    while (true)
    {
      return;
      try
      {
        File localFile = b(paramContext, paramString);
        if ((localFile == null) || (!localFile.exists()) || (a))
          break;
        if (parama != null)
          parama.a(b.a);
        Drawable localDrawable = c(localFile.getAbsolutePath());
        if (localDrawable == null)
          localFile.delete();
        b(paramContext, paramImageView, localDrawable, paramBoolean1, parama, paramAnimation, paramBoolean2);
        return;
      }
      catch (Exception localException)
      {
        Log.b(b, "", localException);
      }
      if (parama == null)
        continue;
      parama.a(o.a.b);
      return;
    }
    new c(paramContext, paramImageView, paramString, b.b, paramBoolean1, parama, paramAnimation, paramBoolean2).execute(new Object[0]);
  }

  protected static File b(Context paramContext, String paramString)
    throws IOException
  {
    String str1 = b(paramString);
    if (b.b());
    for (String str2 = Environment.getExternalStorageDirectory().getCanonicalPath(); ; str2 = paramContext.getCacheDir().getCanonicalPath())
    {
      File localFile = new File(new File(str2 + "/download/.um"), str1);
      if (!localFile.exists())
        break;
      return localFile;
    }
    return null;
  }

  private static String b(String paramString)
  {
    int i = paramString.lastIndexOf(".");
    String str = "";
    if (i >= 0)
      str = paramString.substring(i);
    return g.a(paramString) + str;
  }

  private static void b(Context paramContext, ImageView paramImageView, Drawable paramDrawable, boolean paramBoolean1, a parama, Animation paramAnimation, boolean paramBoolean2)
  {
    monitorenter;
    if ((paramBoolean2) && (paramDrawable != null));
    while (true)
    {
      try
      {
        paramDrawable = new BitmapDrawable(a(((BitmapDrawable)paramDrawable).getBitmap()));
        break label180;
        if (parama == null)
          continue;
        parama.a(o.a.b);
        Log.e(b, "bind drawable failed. drawable [" + paramDrawable + "]  imageView[+" + paramImageView + "+]");
        return;
        if (paramBoolean1 == true)
        {
          paramImageView.setBackgroundDrawable(paramDrawable);
          if (paramAnimation == null)
            continue;
          paramImageView.startAnimation(paramAnimation);
          if (parama == null)
            continue;
          parama.a(o.a.a);
          continue;
        }
      }
      catch (Exception localException)
      {
        Log.b(b, "bind failed", localException);
        if (parama == null)
          continue;
        parama.a(o.a.b);
        continue;
      }
      finally
      {
        monitorexit;
      }
      paramImageView.setImageDrawable(paramDrawable);
      continue;
      label180: if (paramDrawable == null)
        continue;
      if (paramImageView != null)
        continue;
    }
  }

  private static void b(File paramFile)
  {
    if ((paramFile == null) || (!paramFile.exists()) || (!paramFile.canWrite()) || (!paramFile.isDirectory()))
      return;
    File[] arrayOfFile = paramFile.listFiles();
    int i = 0;
    label33: if (i < arrayOfFile.length)
    {
      if (!arrayOfFile[i].isDirectory())
        break label60;
      b(arrayOfFile[i]);
    }
    while (true)
    {
      i++;
      break label33;
      break;
      label60: if (new Date().getTime() - arrayOfFile[i].lastModified() <= 1800L)
        continue;
      arrayOfFile[i].delete();
    }
  }

  private static Drawable c(String paramString)
  {
    try
    {
      Drawable localDrawable = Drawable.createFromPath(paramString);
      return localDrawable;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      Log.e(b, "Resutil fetchImage OutOfMemoryError:" + localOutOfMemoryError.toString());
    }
    return null;
  }

  public static abstract interface a
  {
    public abstract void a(o.a parama);

    public abstract void a(p.b paramb);
  }

  public static enum b
  {
    static
    {
      b[] arrayOfb = new b[2];
      arrayOfb[0] = a;
      arrayOfb[1] = b;
      c = arrayOfb;
    }
  }

  static class c extends AsyncTask<Object, Integer, Drawable>
  {
    private Context a;
    private String b;
    private ImageView c;
    private p.b d;
    private boolean e;
    private p.a f;
    private Animation g;
    private boolean h;

    public c(Context paramContext, ImageView paramImageView, String paramString, p.b paramb, boolean paramBoolean1, p.a parama, Animation paramAnimation, boolean paramBoolean2)
    {
      this.a = paramContext;
      this.b = paramString;
      this.f = parama;
      this.d = paramb;
      this.e = paramBoolean1;
      this.g = paramAnimation;
      this.c = paramImageView;
      this.h = paramBoolean2;
    }

    protected Drawable a(Object[] paramArrayOfObject)
    {
      if (p.a);
      try
      {
        Thread.sleep(3000L);
      }
      catch (InterruptedException localInterruptedException)
      {
        try
        {
          while (true)
          {
            p.a(this.a, this.b);
            File localFile = p.b(this.a, this.b);
            Object localObject = null;
            if (localFile != null)
            {
              boolean bool = localFile.exists();
              localObject = null;
              if (bool)
              {
                Drawable localDrawable = p.a(localFile.getAbsolutePath());
                localObject = localDrawable;
              }
            }
            return localObject;
            localInterruptedException = localInterruptedException;
            localInterruptedException.printStackTrace();
          }
        }
        catch (Exception localException)
        {
          Log.e(p.a(), localException.toString(), localException);
        }
      }
      return null;
    }

    protected void a(Drawable paramDrawable)
    {
      p.a(this.a, this.c, paramDrawable, this.e, this.f, this.g, this.h);
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      if (this.f != null)
        this.f.a(this.d);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.p
 * JD-Core Version:    0.6.0
 */