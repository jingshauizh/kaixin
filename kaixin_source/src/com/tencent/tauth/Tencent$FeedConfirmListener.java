package com.tencent.tauth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.sdkutil.HttpUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import org.json.JSONException;
import org.json.JSONObject;

class Tencent$FeedConfirmListener
  implements IUiListener
{
  private String KEY_SHOWFEED = "sendinstall";
  private String KEY_WORDING = "installwording";
  private String SEND_INSTALL_APP_FEED_CGI = "http://appsupport.qq.com/cgi-bin/qzapps/mapp_addapp.cgi";
  IUiListener userListener;

  public Tencent$FeedConfirmListener(Tencent paramTencent, IUiListener paramIUiListener)
  {
    this.userListener = paramIUiListener;
  }

  private View createContentView(Context paramContext, Drawable paramDrawable, String paramString, View.OnClickListener paramOnClickListener1, View.OnClickListener paramOnClickListener2)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
    float f = localDisplayMetrics.density;
    RelativeLayout localRelativeLayout = new RelativeLayout(paramContext);
    ImageView localImageView = new ImageView(paramContext);
    localImageView.setImageDrawable(paramDrawable);
    localImageView.setScaleType(ImageView.ScaleType.FIT_XY);
    localImageView.setId(1);
    int i = (int)(60.0F * f);
    int j = (int)(60.0F * f);
    (int)(14.0F * f);
    int k = (int)(18.0F * f);
    int m = (int)(6.0F * f);
    int n = (int)(18.0F * f);
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(i, j);
    localLayoutParams1.addRule(9);
    localLayoutParams1.setMargins(0, k, m, n);
    localRelativeLayout.addView(localImageView, localLayoutParams1);
    TextView localTextView = new TextView(paramContext);
    localTextView.setText(paramString);
    localTextView.setTextSize(14.0F);
    localTextView.setGravity(3);
    localTextView.setIncludeFontPadding(false);
    localTextView.setPadding(0, 0, 0, 0);
    localTextView.setLines(2);
    localTextView.setId(5);
    localTextView.setMinWidth((int)(185.0F * f));
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams2.addRule(1, 1);
    localLayoutParams2.addRule(6, 1);
    (int)(10.0F * f);
    localLayoutParams2.setMargins(0, 0, (int)(5.0F * f), 0);
    localRelativeLayout.addView(localTextView, localLayoutParams2);
    View localView = new View(paramContext);
    localView.setBackgroundColor(Color.rgb(214, 214, 214));
    localView.setId(3);
    RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(-2, 2);
    localLayoutParams3.addRule(3, 1);
    localLayoutParams3.addRule(5, 1);
    localLayoutParams3.addRule(7, 5);
    localLayoutParams3.setMargins(0, 0, 0, (int)(12.0F * f));
    localRelativeLayout.addView(localView, localLayoutParams3);
    LinearLayout localLinearLayout = new LinearLayout(paramContext);
    RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams4.addRule(5, 1);
    localLayoutParams4.addRule(7, 5);
    localLayoutParams4.addRule(3, 3);
    Button localButton1 = new Button(paramContext);
    localButton1.setText("跳过");
    localButton1.setBackgroundDrawable(getDrawable("buttonNegt.png", paramContext));
    localButton1.setTextColor(Color.rgb(36, 97, 131));
    localButton1.setTextSize(20.0F);
    localButton1.setOnClickListener(paramOnClickListener2);
    localButton1.setId(4);
    LinearLayout.LayoutParams localLayoutParams5 = new LinearLayout.LayoutParams(0, (int)(45.0F * f));
    localLayoutParams5.rightMargin = (int)(14.0F * f);
    localLayoutParams5.leftMargin = (int)(4.0F * f);
    localLayoutParams5.weight = 1.0F;
    localLinearLayout.addView(localButton1, localLayoutParams5);
    Button localButton2 = new Button(paramContext);
    localButton2.setText("确定");
    localButton2.setTextSize(20.0F);
    localButton2.setTextColor(Color.rgb(255, 255, 255));
    localButton2.setBackgroundDrawable(getDrawable("buttonPost.png", paramContext));
    localButton2.setOnClickListener(paramOnClickListener1);
    LinearLayout.LayoutParams localLayoutParams6 = new LinearLayout.LayoutParams(0, (int)(45.0F * f));
    localLayoutParams6.weight = 1.0F;
    localLayoutParams6.rightMargin = (int)(4.0F * f);
    localLinearLayout.addView(localButton2, localLayoutParams6);
    localRelativeLayout.addView(localLinearLayout, localLayoutParams4);
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams((int)(279.0F * f), (int)(163.0F * f));
    localRelativeLayout.setPadding((int)(14.0F * f), 0, (int)(12.0F * f), (int)(12.0F * f));
    localRelativeLayout.setLayoutParams(localLayoutParams);
    localRelativeLayout.setBackgroundColor(Color.rgb(247, 251, 247));
    PaintDrawable localPaintDrawable = new PaintDrawable(Color.rgb(247, 251, 247));
    localPaintDrawable.setCornerRadius(f * 5.0F);
    localRelativeLayout.setBackgroundDrawable(localPaintDrawable);
    return localRelativeLayout;
  }

  private Drawable getDrawable(String paramString, Context paramContext)
  {
    AssetManager localAssetManager = paramContext.getApplicationContext().getAssets();
    InputStream localInputStream;
    Object localObject;
    IOException localIOException2;
    try
    {
      localInputStream = localAssetManager.open(paramString);
      if (localInputStream == null)
        return null;
      if (paramString.endsWith(".9.png"))
      {
        Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream);
        if (localBitmap != null)
        {
          byte[] arrayOfByte = localBitmap.getNinePatchChunk();
          NinePatch.isNinePatchChunk(arrayOfByte);
          NinePatchDrawable localNinePatchDrawable = new NinePatchDrawable(localBitmap, arrayOfByte, new Rect(), null);
          return localNinePatchDrawable;
        }
      }
    }
    catch (IOException localIOException1)
    {
      localObject = null;
      localIOException2 = localIOException1;
    }
    while (true)
    {
      localIOException2.printStackTrace();
      return localObject;
      return null;
      Drawable localDrawable = Drawable.createFromStream(localInputStream, paramString);
      localObject = localDrawable;
      try
      {
        localInputStream.close();
        return localObject;
      }
      catch (IOException localIOException3)
      {
      }
    }
  }

  private void showFeedConfrimDialog(String paramString, IUiListener paramIUiListener, JSONObject paramJSONObject)
  {
    Dialog localDialog = new Dialog(Tencent.access$100(this.this$0));
    localDialog.requestWindowFeature(1);
    PackageManager localPackageManager = Tencent.access$100(this.this$0).getPackageManager();
    try
    {
      PackageInfo localPackageInfo2 = localPackageManager.getPackageInfo(Tencent.access$100(this.this$0).getPackageName(), 0);
      localPackageInfo1 = localPackageInfo2;
      Drawable localDrawable = null;
      if (localPackageInfo1 != null)
        localDrawable = localPackageInfo1.applicationInfo.loadIcon(localPackageManager);
      Tencent.FeedConfirmListener.1 local1 = new Tencent.FeedConfirmListener.1(this, localDialog, paramIUiListener, paramJSONObject);
      Tencent.FeedConfirmListener.2 local2 = new Tencent.FeedConfirmListener.2(this, localDialog, paramIUiListener, paramJSONObject);
      ColorDrawable localColorDrawable = new ColorDrawable();
      localColorDrawable.setAlpha(0);
      localDialog.getWindow().setBackgroundDrawable(localColorDrawable);
      localDialog.setContentView(createContentView(Tencent.access$100(this.this$0), localDrawable, paramString, local1, local2));
      localDialog.setOnCancelListener(new Tencent.FeedConfirmListener.3(this, paramIUiListener, paramJSONObject));
      if ((Tencent.access$100(this.this$0) != null) && (!Tencent.access$100(this.this$0).isFinishing()))
        localDialog.show();
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
      {
        localNameNotFoundException.printStackTrace();
        PackageInfo localPackageInfo1 = null;
      }
    }
  }

  public void onCancel()
  {
    if (this.userListener != null)
      this.userListener.onCancel();
  }

  public void onComplete(JSONObject paramJSONObject)
  {
    int i = 0;
    Object localObject;
    if (paramJSONObject != null)
      if (paramJSONObject != null)
        localObject = "";
    try
    {
      int j = paramJSONObject.getInt(this.KEY_SHOWFEED);
      i = 0;
      if (j == 1)
        i = 1;
      String str2 = paramJSONObject.getString(this.KEY_WORDING);
      localObject = str2;
      label47: String str1 = URLDecoder.decode((String)localObject);
      Log.d("TAG", " WORDING = " + str1 + "xx");
      if ((i != 0) && (!TextUtils.isEmpty(str1)))
        showFeedConfrimDialog(str1, this.userListener, paramJSONObject);
      do
      {
        do
          return;
        while (this.userListener == null);
        this.userListener.onComplete(paramJSONObject);
        return;
      }
      while (this.userListener == null);
      this.userListener.onComplete(null);
      return;
    }
    catch (JSONException localJSONException)
    {
      break label47;
    }
  }

  public void onError(UiError paramUiError)
  {
    if (this.userListener != null)
      this.userListener.onError(paramUiError);
  }

  protected void sendFeedConfirmCgi()
  {
    Bundle localBundle = this.this$0.composeActivityParams();
    HttpUtils.requestAsync(Tencent.access$200(this.this$0), Tencent.access$100(this.this$0), this.SEND_INSTALL_APP_FEED_CGI, localBundle, "POST", null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.Tencent.FeedConfirmListener
 * JD-Core Version:    0.6.0
 */