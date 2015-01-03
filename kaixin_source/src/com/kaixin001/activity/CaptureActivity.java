package com.kaixin001.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.ResultParser;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.zxing.BeepManager;
import com.kaixin001.zxing.CaptureActivityHandler;
import com.kaixin001.zxing.FinishListener;
import com.kaixin001.zxing.InactivityTimer;
import com.kaixin001.zxing.ViewfinderView;
import com.kaixin001.zxing.camera.CameraManager;
import com.kaixin001.zxing.result.ResultHandler;
import com.kaixin001.zxing.result.TextResultHandler;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

public final class CaptureActivity extends Activity
  implements SurfaceHolder.Callback, View.OnClickListener
{
  public static final int CAPTURE_RESULT_CODE = 11;
  private static final Set<ResultMetadataType> DISPLAYABLE_METADATA_TYPES;
  public static final String LOCATION = "location";
  public static final String POI_ID = "poiid";
  public static final String POI_NAME = "poiname";
  private static final String TAG = CaptureActivity.class.getSimpleName();
  private BeepManager beepManager;
  private CaptureActivityHandler handler;
  private boolean hasSurface;
  private InactivityTimer inactivityTimer;
  private Result lastResult;
  private View resultView;
  private Source source;
  private TextView statusView;
  private ViewfinderView viewfinderView;

  static
  {
    DISPLAYABLE_METADATA_TYPES = new HashSet(5);
    DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.ISSUE_NUMBER);
    DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.SUGGESTED_PRICE);
    DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.ERROR_CORRECTION_LEVEL);
    DISPLAYABLE_METADATA_TYPES.add(ResultMetadataType.POSSIBLE_COUNTRY);
  }

  private void displayFrameworkBugMessageAndExit()
  {
    DialogUtil.showMsgDlgStd(this, 2131427328, 2131428322, new FinishListener(this));
  }

  private static void drawLine(Canvas paramCanvas, Paint paramPaint, ResultPoint paramResultPoint1, ResultPoint paramResultPoint2)
  {
    paramCanvas.drawLine(paramResultPoint1.getX(), paramResultPoint1.getY(), paramResultPoint2.getX(), paramResultPoint2.getY(), paramPaint);
  }

  private void drawResultPoints(Bitmap paramBitmap, Result paramResult)
  {
    int i = 0;
    ResultPoint[] arrayOfResultPoint = paramResult.getResultPoints();
    Canvas localCanvas;
    Paint localPaint;
    if ((arrayOfResultPoint != null) && (arrayOfResultPoint.length > 0))
    {
      localCanvas = new Canvas(paramBitmap);
      localPaint = new Paint();
      localPaint.setColor(-1);
      localPaint.setStrokeWidth(3.0F);
      localPaint.setStyle(Paint.Style.STROKE);
      localCanvas.drawRect(new Rect(2, 2, -2 + paramBitmap.getWidth(), -2 + paramBitmap.getHeight()), localPaint);
      localPaint.setColor(getResources().getColor(2131165194));
      if (arrayOfResultPoint.length != 2)
        break label133;
      localPaint.setStrokeWidth(4.0F);
      drawLine(localCanvas, localPaint, arrayOfResultPoint[0], arrayOfResultPoint[1]);
    }
    while (true)
    {
      return;
      label133: if ((arrayOfResultPoint.length == 4) && ((paramResult.getBarcodeFormat().equals(BarcodeFormat.UPC_A)) || (paramResult.getBarcodeFormat().equals(BarcodeFormat.EAN_13))))
      {
        drawLine(localCanvas, localPaint, arrayOfResultPoint[0], arrayOfResultPoint[1]);
        drawLine(localCanvas, localPaint, arrayOfResultPoint[2], arrayOfResultPoint[3]);
        return;
      }
      localPaint.setStrokeWidth(10.0F);
      int j = arrayOfResultPoint.length;
      while (i < j)
      {
        ResultPoint localResultPoint = arrayOfResultPoint[i];
        localCanvas.drawPoint(localResultPoint.getX(), localResultPoint.getY(), localPaint);
        i++;
      }
    }
  }

  private void handleDecodeInternally(Result paramResult, ResultHandler paramResultHandler, Bitmap paramBitmap)
  {
    TwoDimensionalCodeObject localTwoDimensionalCodeObject = new TwoDimensionalCodeObject(null);
    String str = paramResult.toString();
    int i = 0;
    if (str != null);
    try
    {
      JSONObject localJSONObject = new JSONObject(str);
      localTwoDimensionalCodeObject.poiid = localJSONObject.getString("poiid");
      localTwoDimensionalCodeObject.poiname = localJSONObject.getString("poiname");
      localTwoDimensionalCodeObject.location = localJSONObject.getString("location");
      i = 1;
      boolean bool1 = TextUtils.isEmpty(str);
      int j = 0;
      if (!bool1)
      {
        boolean bool2 = str.startsWith("http://");
        j = 0;
        if (bool2)
          j = 1;
      }
      if (j != 0)
      {
        DialogUtil.showMsgDlg(this, getString(2131428635), str, getString(2131428636), getString(2131427587), new DialogInterface.OnClickListener(str)
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            Intent localIntent = new Intent(CaptureActivity.this, WebPageActivity.class);
            localIntent.putExtra("url", this.val$urlStr);
            CaptureActivity.this.startActivity(localIntent);
          }
        }
        , new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            CaptureActivity.this.restart();
          }
        });
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e(TAG, "The content is not from kaixin, content:" + str);
        i = 0;
      }
      if (i != 0)
      {
        DialogUtil.showMsgDlg(this, getString(2131428635), str, getString(2131427381), getString(2131427587), new DialogInterface.OnClickListener(localTwoDimensionalCodeObject)
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            Intent localIntent = CaptureActivity.this.getIntent();
            Bundle localBundle = new Bundle();
            localBundle.putString("poiid", this.val$twoDimensionalCodeObject.poiid);
            localBundle.putString("poiname", this.val$twoDimensionalCodeObject.poiname);
            localBundle.putString("location", this.val$twoDimensionalCodeObject.location);
            localIntent.putExtras(localBundle);
            CaptureActivity.this.setResult(-1, localIntent);
            CaptureActivity.this.finish();
          }
        }
        , new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            CaptureActivity.this.restart();
          }
        });
        return;
      }
      DialogUtil.showMsgDlg(this, getString(2131428635), str, getString(2131428637), getString(2131427597), new DialogInterface.OnClickListener(str)
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          try
          {
            CaptureActivity.this.restart();
            ((ClipboardManager)CaptureActivity.this.getSystemService("clipboard")).setText(this.val$copyStr);
            return;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      }
      , new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          CaptureActivity.this.restart();
        }
      });
    }
  }

  private void handleDecodeInternally2(Result paramResult, ResultHandler paramResultHandler, Bitmap paramBitmap)
  {
    this.statusView.setVisibility(8);
    this.viewfinderView.setVisibility(8);
    this.resultView.setVisibility(0);
    ImageView localImageView = (ImageView)findViewById(2131361949);
    if (paramBitmap == null)
      localImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), 2130838465));
    while (true)
    {
      ((TextView)findViewById(2131361951)).setText(paramResult.getBarcodeFormat().toString());
      ((TextView)findViewById(2131361953)).setText(paramResultHandler.getType().toString());
      String str1 = DateFormat.getDateTimeInstance(3, 3).format(new Date(paramResult.getTimestamp()));
      ((TextView)findViewById(2131361955)).setText(str1);
      TextView localTextView1 = (TextView)findViewById(2131361957);
      View localView = findViewById(2131361956);
      localTextView1.setVisibility(8);
      localView.setVisibility(8);
      Hashtable localHashtable = paramResult.getResultMetadata();
      StringBuilder localStringBuilder;
      Iterator localIterator;
      if (localHashtable != null)
      {
        localStringBuilder = new StringBuilder(20);
        localIterator = localHashtable.entrySet().iterator();
        label204: if (localIterator.hasNext())
          break label495;
        if (localStringBuilder.length() > 0)
        {
          localStringBuilder.setLength(-1 + localStringBuilder.length());
          localTextView1.setText(localStringBuilder);
          localTextView1.setVisibility(0);
          localView.setVisibility(0);
        }
      }
      TextView localTextView2 = (TextView)findViewById(2131361958);
      CharSequence localCharSequence = paramResultHandler.getDisplayContents();
      TwoDimensionalCodeObject localTwoDimensionalCodeObject = new TwoDimensionalCodeObject(null);
      String str2 = localCharSequence.toString();
      int i = 0;
      if (str2 != null);
      try
      {
        JSONObject localJSONObject = new JSONObject(str2);
        localTwoDimensionalCodeObject.poiid = localJSONObject.getString("poiid");
        localTwoDimensionalCodeObject.poiname = localJSONObject.getString("poiname");
        localTwoDimensionalCodeObject.location = localJSONObject.getString("location");
        i = 1;
        if (i != 0)
        {
          localTextView2.setText(localTwoDimensionalCodeObject.poiname);
          localTextView2.setTextSize(2, Math.max(22, 32 - localCharSequence.length() / 4));
          ((ViewGroup)findViewById(2131361959)).requestFocus();
          Button localButton1 = (Button)findViewById(2131361960);
          localButton1.setVisibility(0);
          7 local7 = new View.OnClickListener()
          {
            public void onClick(View paramView)
            {
              CaptureActivity.this.restart();
            }
          };
          localButton1.setOnClickListener(local7);
          localButton2 = (Button)findViewById(2131361961);
          localButton2.setVisibility(0);
          if (i == 0)
            break label599;
          localButton2.setEnabled(true);
          8 local8 = new View.OnClickListener(localTwoDimensionalCodeObject)
          {
            public void onClick(View paramView)
            {
              Intent localIntent = CaptureActivity.this.getIntent();
              Bundle localBundle = new Bundle();
              localBundle.putString("poiid", this.val$twoDimensionalCodeObject.poiid);
              localBundle.putString("poiname", this.val$twoDimensionalCodeObject.poiname);
              localBundle.putString("location", this.val$twoDimensionalCodeObject.location);
              localIntent.putExtras(localBundle);
              CaptureActivity.this.setResult(-1, localIntent);
              CaptureActivity.this.finish();
            }
          };
          localButton2.setOnClickListener(local8);
          return;
          localImageView.setImageBitmap(paramBitmap);
          continue;
          label495: Map.Entry localEntry = (Map.Entry)localIterator.next();
          if (!DISPLAYABLE_METADATA_TYPES.contains(localEntry.getKey()))
            break label204;
          localStringBuilder.append(localEntry.getValue()).append('\n');
        }
      }
      catch (Exception localException)
      {
        Button localButton2;
        while (true)
        {
          KXLog.e(TAG, "The content is not from kaixin, content:" + str2);
          i = 0;
          continue;
          localTextView2.setText(str2);
          Toast.makeText(this, 2131428328, 0).show();
        }
        label599: localButton2.setEnabled(false);
      }
    }
  }

  private void initCamera(SurfaceHolder paramSurfaceHolder)
  {
    try
    {
      CameraManager.get().openDriver(paramSurfaceHolder);
      if (this.handler == null)
        this.handler = new CaptureActivityHandler(this, null, null);
      return;
    }
    catch (IOException localIOException)
    {
      KXLog.e(TAG, "initCamera", localIOException);
      displayFrameworkBugMessageAndExit();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      KXLog.e(TAG, "Unexpected error initializating camera", localRuntimeException);
      displayFrameworkBugMessageAndExit();
    }
  }

  private void resetStatusView()
  {
    this.resultView.setVisibility(8);
    this.statusView.setText(2131428316);
    this.statusView.setVisibility(0);
    this.viewfinderView.setVisibility(0);
    this.lastResult = null;
  }

  private void restart()
  {
    resetStatusView();
    if (this.handler != null)
      this.handler.sendEmptyMessage(2131361798);
  }

  public void drawViewfinder()
  {
    this.viewfinderView.drawViewfinder();
  }

  public Handler getHandler()
  {
    return this.handler;
  }

  public ViewfinderView getViewfinderView()
  {
    return this.viewfinderView;
  }

  public void handleDecode(Result paramResult, Bitmap paramBitmap)
  {
    this.inactivityTimer.onActivity();
    this.lastResult = paramResult;
    TextResultHandler localTextResultHandler = new TextResultHandler(ResultParser.parseResult(paramResult));
    if (paramBitmap == null)
    {
      handleDecodeInternally(paramResult, localTextResultHandler, null);
      return;
    }
    this.beepManager.playBeepSoundAndVibrate();
    drawResultPoints(paramBitmap, paramResult);
    switch ($SWITCH_TABLE$com$kaixin001$activity$CaptureActivity$Source()[this.source.ordinal()])
    {
    default:
      KXLog.d(TAG, "unknown source type");
      return;
    case 4:
    }
    handleDecodeInternally(paramResult, localTextResultHandler, paramBitmap);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
    }
    finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getWindow().addFlags(128);
    setContentView(2130903061);
    CameraManager.init(getApplication());
    this.viewfinderView = ((ViewfinderView)findViewById(2131361947));
    this.resultView = findViewById(2131361948);
    this.statusView = ((TextView)findViewById(2131361962));
    this.handler = null;
    this.lastResult = null;
    this.hasSurface = false;
    this.inactivityTimer = new InactivityTimer(this);
    this.beepManager = new BeepManager(this);
    setTitleBar();
  }

  protected void onDestroy()
  {
    this.inactivityTimer.shutdown();
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      if ((this.source != Source.NONE) || (this.lastResult == null))
        break label58;
      resetStatusView();
      if (this.handler != null)
        this.handler.sendEmptyMessage(2131361798);
    }
    do
      return true;
    while ((paramInt == 80) || (paramInt == 27));
    label58: return super.onKeyDown(paramInt, paramKeyEvent);
  }

  protected void onPause()
  {
    super.onPause();
    if (this.handler != null)
    {
      this.handler.quitSynchronously();
      this.handler = null;
    }
    this.inactivityTimer.onPause();
    CameraManager.get().closeDriver();
  }

  protected void onResume()
  {
    super.onResume();
    resetStatusView();
    SurfaceHolder localSurfaceHolder = ((SurfaceView)findViewById(2131361946)).getHolder();
    if (this.hasSurface)
      initCamera(localSurfaceHolder);
    while (true)
    {
      this.source = Source.NONE;
      this.beepManager.updatePrefs();
      this.inactivityTimer.onResume();
      return;
      localSurfaceHolder.addCallback(this);
      localSurfaceHolder.setType(3);
    }
  }

  protected void setTitleBar()
  {
    ((ImageView)findViewById(2131362914)).setOnClickListener(this);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428634);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    findViewById(2131362928).setVisibility(8);
  }

  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    if (!this.hasSurface)
    {
      this.hasSurface = true;
      initCamera(paramSurfaceHolder);
    }
  }

  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    this.hasSurface = false;
  }

  private static enum Source
  {
    static
    {
      NONE = new Source("NONE", 3);
      Source[] arrayOfSource = new Source[4];
      arrayOfSource[0] = NATIVE_APP_INTENT;
      arrayOfSource[1] = PRODUCT_SEARCH_LINK;
      arrayOfSource[2] = ZXING_LINK;
      arrayOfSource[3] = NONE;
      ENUM$VALUES = arrayOfSource;
    }
  }

  private class TwoDimensionalCodeObject
  {
    String location;
    String poiid;
    String poiname;

    private TwoDimensionalCodeObject()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.CaptureActivity
 * JD-Core Version:    0.6.0
 */