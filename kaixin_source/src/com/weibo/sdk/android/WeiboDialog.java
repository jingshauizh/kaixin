package com.weibo.sdk.android;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.weibo.sdk.android.util.Utility;

public class WeiboDialog extends Dialog
{
  static FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(-1, -1);
  private static final String TAG = "Weibo-WebView";
  private static int bottom_margin;
  private static int left_margin;
  private static int right_margin;
  private static int theme = 16973840;
  private static int top_margin;
  private RelativeLayout mContent;
  private WeiboAuthListener mListener;
  private ProgressDialog mSpinner;
  private String mUrl;
  private WebView mWebView;
  private RelativeLayout webViewContainer;

  static
  {
    left_margin = 0;
    top_margin = 0;
    right_margin = 0;
    bottom_margin = 0;
  }

  public WeiboDialog(Context paramContext, String paramString, WeiboAuthListener paramWeiboAuthListener)
  {
    super(paramContext, theme);
    this.mUrl = paramString;
    this.mListener = paramWeiboAuthListener;
  }

  private void handleRedirectUrl(WebView paramWebView, String paramString)
  {
    Bundle localBundle = Utility.parseUrl(paramString);
    String str1 = localBundle.getString("error");
    String str2 = localBundle.getString("error_code");
    if ((str1 == null) && (str2 == null))
    {
      this.mListener.onComplete(localBundle);
      return;
    }
    if (str1.equals("access_denied"))
    {
      this.mListener.onCancel();
      return;
    }
    if (str2 == null)
    {
      this.mListener.onWeiboException(new WeiboException(str1, 0));
      return;
    }
    this.mListener.onWeiboException(new WeiboException(str1, Integer.parseInt(str2)));
  }

  // ERROR //
  private boolean parseDimens()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: aload_0
    //   3: invokevirtual 129	com/weibo/sdk/android/WeiboDialog:getContext	()Landroid/content/Context;
    //   6: invokevirtual 135	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   9: astore_2
    //   10: aload_0
    //   11: invokevirtual 129	com/weibo/sdk/android/WeiboDialog:getContext	()Landroid/content/Context;
    //   14: invokevirtual 139	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   17: invokevirtual 145	android/content/res/Resources:getDisplayMetrics	()Landroid/util/DisplayMetrics;
    //   20: getfield 151	android/util/DisplayMetrics:density	F
    //   23: fstore_3
    //   24: aconst_null
    //   25: astore 4
    //   27: aload_2
    //   28: ldc 153
    //   30: invokevirtual 159	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   33: astore 4
    //   35: invokestatic 165	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   38: astore 9
    //   40: aload 9
    //   42: aload 4
    //   44: ldc 167
    //   46: invokeinterface 173 3 0
    //   51: aload 9
    //   53: invokeinterface 177 1 0
    //   58: istore 12
    //   60: iload 12
    //   62: istore 13
    //   64: iconst_1
    //   65: istore_1
    //   66: iload 13
    //   68: iconst_1
    //   69: if_icmpne +15 -> 84
    //   72: aload 4
    //   74: ifnull +8 -> 82
    //   77: aload 4
    //   79: invokevirtual 182	java/io/InputStream:close	()V
    //   82: iload_1
    //   83: ireturn
    //   84: iload 13
    //   86: tableswitch	default:+18 -> 104, 2:+30->116
    //   105: lconst_0
    //   106: invokeinterface 185 1 0
    //   111: istore 13
    //   113: goto -47 -> 66
    //   116: aload 9
    //   118: invokeinterface 189 1 0
    //   123: ldc 191
    //   125: invokevirtual 101	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   128: ifeq -24 -> 104
    //   131: aload 9
    //   133: aconst_null
    //   134: ldc 193
    //   136: invokeinterface 197 3 0
    //   141: astore 14
    //   143: ldc 199
    //   145: aload 14
    //   147: invokevirtual 101	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   150: ifeq +61 -> 211
    //   153: fload_3
    //   154: aload 9
    //   156: invokeinterface 202 1 0
    //   161: invokestatic 119	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   164: i2f
    //   165: fmul
    //   166: f2i
    //   167: putstatic 41	com/weibo/sdk/android/WeiboDialog:left_margin	I
    //   170: goto -66 -> 104
    //   173: astore 10
    //   175: aload 10
    //   177: invokevirtual 205	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   180: goto -108 -> 72
    //   183: astore 7
    //   185: aload 7
    //   187: invokevirtual 206	java/io/IOException:printStackTrace	()V
    //   190: aload 4
    //   192: ifnull -110 -> 82
    //   195: aload 4
    //   197: invokevirtual 182	java/io/InputStream:close	()V
    //   200: iload_1
    //   201: ireturn
    //   202: astore 8
    //   204: aload 8
    //   206: invokevirtual 206	java/io/IOException:printStackTrace	()V
    //   209: iload_1
    //   210: ireturn
    //   211: ldc 208
    //   213: aload 14
    //   215: invokevirtual 101	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   218: ifeq +38 -> 256
    //   221: fload_3
    //   222: aload 9
    //   224: invokeinterface 202 1 0
    //   229: invokestatic 119	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   232: i2f
    //   233: fmul
    //   234: f2i
    //   235: putstatic 43	com/weibo/sdk/android/WeiboDialog:top_margin	I
    //   238: goto -134 -> 104
    //   241: astore 5
    //   243: aload 4
    //   245: ifnull +8 -> 253
    //   248: aload 4
    //   250: invokevirtual 182	java/io/InputStream:close	()V
    //   253: aload 5
    //   255: athrow
    //   256: ldc 210
    //   258: aload 14
    //   260: invokevirtual 101	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   263: ifeq +23 -> 286
    //   266: fload_3
    //   267: aload 9
    //   269: invokeinterface 202 1 0
    //   274: invokestatic 119	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   277: i2f
    //   278: fmul
    //   279: f2i
    //   280: putstatic 45	com/weibo/sdk/android/WeiboDialog:right_margin	I
    //   283: goto -179 -> 104
    //   286: ldc 212
    //   288: aload 14
    //   290: invokevirtual 101	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   293: ifeq -189 -> 104
    //   296: fload_3
    //   297: aload 9
    //   299: invokeinterface 202 1 0
    //   304: invokestatic 119	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   307: i2f
    //   308: fmul
    //   309: f2i
    //   310: putstatic 47	com/weibo/sdk/android/WeiboDialog:bottom_margin	I
    //   313: goto -209 -> 104
    //   316: astore 6
    //   318: aload 6
    //   320: invokevirtual 206	java/io/IOException:printStackTrace	()V
    //   323: goto -70 -> 253
    //   326: astore 11
    //   328: aload 11
    //   330: invokevirtual 206	java/io/IOException:printStackTrace	()V
    //   333: iload_1
    //   334: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   40	60	173	org/xmlpull/v1/XmlPullParserException
    //   104	113	173	org/xmlpull/v1/XmlPullParserException
    //   116	170	173	org/xmlpull/v1/XmlPullParserException
    //   211	238	173	org/xmlpull/v1/XmlPullParserException
    //   256	283	173	org/xmlpull/v1/XmlPullParserException
    //   286	313	173	org/xmlpull/v1/XmlPullParserException
    //   27	40	183	java/io/IOException
    //   40	60	183	java/io/IOException
    //   104	113	183	java/io/IOException
    //   116	170	183	java/io/IOException
    //   175	180	183	java/io/IOException
    //   211	238	183	java/io/IOException
    //   256	283	183	java/io/IOException
    //   286	313	183	java/io/IOException
    //   195	200	202	java/io/IOException
    //   27	40	241	finally
    //   40	60	241	finally
    //   104	113	241	finally
    //   116	170	241	finally
    //   175	180	241	finally
    //   185	190	241	finally
    //   211	238	241	finally
    //   256	283	241	finally
    //   286	313	241	finally
    //   248	253	316	java/io/IOException
    //   77	82	326	java/io/IOException
  }

  // ERROR //
  private void setUpWebView()
  {
    // Byte code:
    //   0: aload_0
    //   1: new 217	android/widget/RelativeLayout
    //   4: dup
    //   5: aload_0
    //   6: invokevirtual 129	com/weibo/sdk/android/WeiboDialog:getContext	()Landroid/content/Context;
    //   9: invokespecial 220	android/widget/RelativeLayout:<init>	(Landroid/content/Context;)V
    //   12: putfield 222	com/weibo/sdk/android/WeiboDialog:webViewContainer	Landroid/widget/RelativeLayout;
    //   15: aload_0
    //   16: new 224	android/webkit/WebView
    //   19: dup
    //   20: aload_0
    //   21: invokevirtual 129	com/weibo/sdk/android/WeiboDialog:getContext	()Landroid/content/Context;
    //   24: invokespecial 225	android/webkit/WebView:<init>	(Landroid/content/Context;)V
    //   27: putfield 71	com/weibo/sdk/android/WeiboDialog:mWebView	Landroid/webkit/WebView;
    //   30: aload_0
    //   31: getfield 71	com/weibo/sdk/android/WeiboDialog:mWebView	Landroid/webkit/WebView;
    //   34: iconst_0
    //   35: invokevirtual 229	android/webkit/WebView:setVerticalScrollBarEnabled	(Z)V
    //   38: aload_0
    //   39: getfield 71	com/weibo/sdk/android/WeiboDialog:mWebView	Landroid/webkit/WebView;
    //   42: iconst_0
    //   43: invokevirtual 232	android/webkit/WebView:setHorizontalScrollBarEnabled	(Z)V
    //   46: aload_0
    //   47: getfield 71	com/weibo/sdk/android/WeiboDialog:mWebView	Landroid/webkit/WebView;
    //   50: invokevirtual 236	android/webkit/WebView:getSettings	()Landroid/webkit/WebSettings;
    //   53: iconst_1
    //   54: invokevirtual 241	android/webkit/WebSettings:setJavaScriptEnabled	(Z)V
    //   57: aload_0
    //   58: getfield 71	com/weibo/sdk/android/WeiboDialog:mWebView	Landroid/webkit/WebView;
    //   61: new 243	com/weibo/sdk/android/WeiboDialog$WeiboWebViewClient
    //   64: dup
    //   65: aload_0
    //   66: aconst_null
    //   67: invokespecial 246	com/weibo/sdk/android/WeiboDialog$WeiboWebViewClient:<init>	(Lcom/weibo/sdk/android/WeiboDialog;Lcom/weibo/sdk/android/WeiboDialog$WeiboWebViewClient;)V
    //   70: invokevirtual 250	android/webkit/WebView:setWebViewClient	(Landroid/webkit/WebViewClient;)V
    //   73: aload_0
    //   74: getfield 71	com/weibo/sdk/android/WeiboDialog:mWebView	Landroid/webkit/WebView;
    //   77: aload_0
    //   78: getfield 53	com/weibo/sdk/android/WeiboDialog:mUrl	Ljava/lang/String;
    //   81: invokevirtual 254	android/webkit/WebView:loadUrl	(Ljava/lang/String;)V
    //   84: aload_0
    //   85: getfield 71	com/weibo/sdk/android/WeiboDialog:mWebView	Landroid/webkit/WebView;
    //   88: getstatic 36	com/weibo/sdk/android/WeiboDialog:FILL	Landroid/widget/FrameLayout$LayoutParams;
    //   91: invokevirtual 258	android/webkit/WebView:setLayoutParams	(Landroid/view/ViewGroup$LayoutParams;)V
    //   94: aload_0
    //   95: getfield 71	com/weibo/sdk/android/WeiboDialog:mWebView	Landroid/webkit/WebView;
    //   98: iconst_4
    //   99: invokevirtual 262	android/webkit/WebView:setVisibility	(I)V
    //   102: new 264	android/widget/RelativeLayout$LayoutParams
    //   105: dup
    //   106: iconst_m1
    //   107: iconst_m1
    //   108: invokespecial 265	android/widget/RelativeLayout$LayoutParams:<init>	(II)V
    //   111: astore_1
    //   112: new 264	android/widget/RelativeLayout$LayoutParams
    //   115: dup
    //   116: iconst_m1
    //   117: iconst_m1
    //   118: invokespecial 265	android/widget/RelativeLayout$LayoutParams:<init>	(II)V
    //   121: astore_2
    //   122: aload_0
    //   123: getfield 267	com/weibo/sdk/android/WeiboDialog:mContent	Landroid/widget/RelativeLayout;
    //   126: iconst_0
    //   127: invokevirtual 270	android/widget/RelativeLayout:setBackgroundColor	(I)V
    //   130: aload_0
    //   131: invokevirtual 129	com/weibo/sdk/android/WeiboDialog:getContext	()Landroid/content/Context;
    //   134: invokevirtual 135	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   137: astore_3
    //   138: aconst_null
    //   139: astore 4
    //   141: aload_3
    //   142: ldc_w 272
    //   145: invokevirtual 159	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   148: astore 4
    //   150: aload_0
    //   151: invokevirtual 129	com/weibo/sdk/android/WeiboDialog:getContext	()Landroid/content/Context;
    //   154: invokevirtual 139	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   157: invokevirtual 145	android/content/res/Resources:getDisplayMetrics	()Landroid/util/DisplayMetrics;
    //   160: getfield 151	android/util/DisplayMetrics:density	F
    //   163: fstore 14
    //   165: aload_2
    //   166: ldc_w 273
    //   169: fload 14
    //   171: fmul
    //   172: f2i
    //   173: putfield 276	android/widget/RelativeLayout$LayoutParams:leftMargin	I
    //   176: aload_2
    //   177: ldc_w 273
    //   180: fload 14
    //   182: fmul
    //   183: f2i
    //   184: putfield 279	android/widget/RelativeLayout$LayoutParams:topMargin	I
    //   187: aload_2
    //   188: ldc_w 273
    //   191: fload 14
    //   193: fmul
    //   194: f2i
    //   195: putfield 282	android/widget/RelativeLayout$LayoutParams:rightMargin	I
    //   198: aload_2
    //   199: ldc_w 273
    //   202: fload 14
    //   204: fmul
    //   205: f2i
    //   206: putfield 285	android/widget/RelativeLayout$LayoutParams:bottomMargin	I
    //   209: aload 4
    //   211: ifnonnull +132 -> 343
    //   214: aload_0
    //   215: getfield 222	com/weibo/sdk/android/WeiboDialog:webViewContainer	Landroid/widget/RelativeLayout;
    //   218: ldc_w 286
    //   221: invokevirtual 289	android/widget/RelativeLayout:setBackgroundResource	(I)V
    //   224: aload 4
    //   226: ifnull +8 -> 234
    //   229: aload 4
    //   231: invokevirtual 182	java/io/InputStream:close	()V
    //   234: aload_0
    //   235: getfield 222	com/weibo/sdk/android/WeiboDialog:webViewContainer	Landroid/widget/RelativeLayout;
    //   238: aload_0
    //   239: getfield 71	com/weibo/sdk/android/WeiboDialog:mWebView	Landroid/webkit/WebView;
    //   242: aload_2
    //   243: invokevirtual 293	android/widget/RelativeLayout:addView	(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    //   246: aload_0
    //   247: getfield 222	com/weibo/sdk/android/WeiboDialog:webViewContainer	Landroid/widget/RelativeLayout;
    //   250: bipush 17
    //   252: invokevirtual 296	android/widget/RelativeLayout:setGravity	(I)V
    //   255: aload_0
    //   256: invokespecial 298	com/weibo/sdk/android/WeiboDialog:parseDimens	()Z
    //   259: ifeq +166 -> 425
    //   262: aload_1
    //   263: getstatic 41	com/weibo/sdk/android/WeiboDialog:left_margin	I
    //   266: putfield 276	android/widget/RelativeLayout$LayoutParams:leftMargin	I
    //   269: aload_1
    //   270: getstatic 43	com/weibo/sdk/android/WeiboDialog:top_margin	I
    //   273: putfield 279	android/widget/RelativeLayout$LayoutParams:topMargin	I
    //   276: aload_1
    //   277: getstatic 45	com/weibo/sdk/android/WeiboDialog:right_margin	I
    //   280: putfield 282	android/widget/RelativeLayout$LayoutParams:rightMargin	I
    //   283: aload_1
    //   284: getstatic 47	com/weibo/sdk/android/WeiboDialog:bottom_margin	I
    //   287: putfield 285	android/widget/RelativeLayout$LayoutParams:bottomMargin	I
    //   290: aload_0
    //   291: getfield 267	com/weibo/sdk/android/WeiboDialog:mContent	Landroid/widget/RelativeLayout;
    //   294: aload_0
    //   295: getfield 222	com/weibo/sdk/android/WeiboDialog:webViewContainer	Landroid/widget/RelativeLayout;
    //   298: aload_1
    //   299: invokevirtual 293	android/widget/RelativeLayout:addView	(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    //   302: return
    //   303: astore 7
    //   305: aload 7
    //   307: invokevirtual 299	java/lang/Exception:printStackTrace	()V
    //   310: goto -101 -> 209
    //   313: astore 8
    //   315: aload 8
    //   317: invokevirtual 299	java/lang/Exception:printStackTrace	()V
    //   320: aload 4
    //   322: ifnull -88 -> 234
    //   325: aload 4
    //   327: invokevirtual 182	java/io/InputStream:close	()V
    //   330: goto -96 -> 234
    //   333: astore 9
    //   335: aload 9
    //   337: invokevirtual 206	java/io/IOException:printStackTrace	()V
    //   340: goto -106 -> 234
    //   343: aload 4
    //   345: invokestatic 305	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   348: astore 11
    //   350: new 307	android/graphics/drawable/NinePatchDrawable
    //   353: dup
    //   354: aload 11
    //   356: aload 11
    //   358: invokevirtual 313	android/graphics/Bitmap:getNinePatchChunk	()[B
    //   361: new 315	android/graphics/Rect
    //   364: dup
    //   365: iconst_0
    //   366: iconst_0
    //   367: iconst_0
    //   368: iconst_0
    //   369: invokespecial 318	android/graphics/Rect:<init>	(IIII)V
    //   372: aconst_null
    //   373: invokespecial 321	android/graphics/drawable/NinePatchDrawable:<init>	(Landroid/graphics/Bitmap;[BLandroid/graphics/Rect;Ljava/lang/String;)V
    //   376: astore 12
    //   378: aload_0
    //   379: getfield 222	com/weibo/sdk/android/WeiboDialog:webViewContainer	Landroid/widget/RelativeLayout;
    //   382: aload 12
    //   384: invokevirtual 325	android/widget/RelativeLayout:setBackgroundDrawable	(Landroid/graphics/drawable/Drawable;)V
    //   387: goto -163 -> 224
    //   390: astore 5
    //   392: aload 4
    //   394: ifnull +8 -> 402
    //   397: aload 4
    //   399: invokevirtual 182	java/io/InputStream:close	()V
    //   402: aload 5
    //   404: athrow
    //   405: astore 6
    //   407: aload 6
    //   409: invokevirtual 206	java/io/IOException:printStackTrace	()V
    //   412: goto -10 -> 402
    //   415: astore 13
    //   417: aload 13
    //   419: invokevirtual 206	java/io/IOException:printStackTrace	()V
    //   422: goto -188 -> 234
    //   425: aload_0
    //   426: invokevirtual 129	com/weibo/sdk/android/WeiboDialog:getContext	()Landroid/content/Context;
    //   429: invokevirtual 139	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   432: astore 10
    //   434: aload_1
    //   435: aload 10
    //   437: ldc_w 326
    //   440: invokevirtual 330	android/content/res/Resources:getDimensionPixelSize	(I)I
    //   443: putfield 276	android/widget/RelativeLayout$LayoutParams:leftMargin	I
    //   446: aload_1
    //   447: aload 10
    //   449: ldc_w 331
    //   452: invokevirtual 330	android/content/res/Resources:getDimensionPixelSize	(I)I
    //   455: putfield 282	android/widget/RelativeLayout$LayoutParams:rightMargin	I
    //   458: aload_1
    //   459: aload 10
    //   461: ldc_w 332
    //   464: invokevirtual 330	android/content/res/Resources:getDimensionPixelSize	(I)I
    //   467: putfield 279	android/widget/RelativeLayout$LayoutParams:topMargin	I
    //   470: aload_1
    //   471: aload 10
    //   473: ldc_w 333
    //   476: invokevirtual 330	android/content/res/Resources:getDimensionPixelSize	(I)I
    //   479: putfield 285	android/widget/RelativeLayout$LayoutParams:bottomMargin	I
    //   482: goto -192 -> 290
    //
    // Exception table:
    //   from	to	target	type
    //   141	209	303	java/lang/Exception
    //   214	224	313	java/lang/Exception
    //   305	310	313	java/lang/Exception
    //   343	387	313	java/lang/Exception
    //   325	330	333	java/io/IOException
    //   141	209	390	finally
    //   214	224	390	finally
    //   305	310	390	finally
    //   315	320	390	finally
    //   343	387	390	finally
    //   397	402	405	java/io/IOException
    //   229	234	415	java/io/IOException
  }

  protected void onBack()
  {
    try
    {
      this.mSpinner.dismiss();
      if (this.mWebView != null)
      {
        this.mWebView.stopLoading();
        this.mWebView.destroy();
      }
      label28: dismiss();
      return;
    }
    catch (Exception localException)
    {
      break label28;
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mSpinner = new ProgressDialog(getContext());
    this.mSpinner.requestWindowFeature(1);
    this.mSpinner.setMessage("Loading...");
    this.mSpinner.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramDialogInterface, int paramInt, KeyEvent paramKeyEvent)
      {
        WeiboDialog.this.onBack();
        return false;
      }
    });
    requestWindowFeature(1);
    getWindow().setFeatureDrawableAlpha(0, 0);
    this.mContent = new RelativeLayout(getContext());
    setUpWebView();
    addContentView(this.mContent, new ViewGroup.LayoutParams(-1, -1));
  }

  private class WeiboWebViewClient extends WebViewClient
  {
    private WeiboWebViewClient()
    {
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      Log.d("Weibo-WebView", "onPageFinished URL: " + paramString);
      super.onPageFinished(paramWebView, paramString);
      if (WeiboDialog.this.mSpinner.isShowing())
        WeiboDialog.this.mSpinner.dismiss();
      WeiboDialog.this.mWebView.setVisibility(0);
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      Log.d("Weibo-WebView", "onPageStarted URL: " + paramString);
      if (paramString.startsWith(Weibo.redirecturl))
      {
        WeiboDialog.this.handleRedirectUrl(paramWebView, paramString);
        paramWebView.stopLoading();
        WeiboDialog.this.dismiss();
        return;
      }
      super.onPageStarted(paramWebView, paramString, paramBitmap);
      WeiboDialog.this.mSpinner.show();
    }

    public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
    {
      super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
      WeiboDialog.this.mListener.onError(new WeiboDialogError(paramString1, paramInt, paramString2));
      WeiboDialog.this.dismiss();
    }

    public void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError)
    {
      paramSslErrorHandler.proceed();
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      Log.d("Weibo-WebView", "Redirect URL: " + paramString);
      if (paramString.startsWith("sms:"))
      {
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.putExtra("address", paramString.replace("sms:", ""));
        localIntent.setType("vnd.android-dir/mms-sms");
        WeiboDialog.this.getContext().startActivity(localIntent);
        return true;
      }
      return super.shouldOverrideUrlLoading(paramWebView, paramString);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.weibo.sdk.android.WeiboDialog
 * JD-Core Version:    0.6.0
 */