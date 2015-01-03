package com.kaixin001.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.kaixin001.activity.CaptureActivity;
import com.kaixin001.util.KXLog;
import com.kaixin001.zxing.camera.CameraManager;
import java.util.Vector;

public final class CaptureActivityHandler extends Handler
{
  private static final String TAG = CaptureActivityHandler.class.getSimpleName();
  private final CaptureActivity activity;
  private final DecodeThread decodeThread;
  private State state;

  public CaptureActivityHandler(CaptureActivity paramCaptureActivity, Vector<BarcodeFormat> paramVector, String paramString)
  {
    this.activity = paramCaptureActivity;
    this.decodeThread = new DecodeThread(paramCaptureActivity, paramVector, paramString, new ViewfinderResultPointCallback(paramCaptureActivity.getViewfinderView()));
    this.decodeThread.start();
    this.state = State.SUCCESS;
    CameraManager.get().startPreview();
    restartPreviewAndDecode();
  }

  private void restartPreviewAndDecode()
  {
    if (this.state == State.SUCCESS)
    {
      this.state = State.PREVIEW;
      CameraManager.get().requestPreviewFrame(this.decodeThread.getHandler(), 2131361793);
      CameraManager.get().requestAutoFocus(this, 2131361792);
      this.activity.drawViewfinder();
    }
  }

  public void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    case 2131361793:
    case 2131361797:
    default:
    case 2131361792:
      do
        return;
      while (this.state != State.PREVIEW);
      CameraManager.get().requestAutoFocus(this, 2131361792);
      return;
    case 2131361798:
      KXLog.d(TAG, "Got restart preview message");
      restartPreviewAndDecode();
      return;
    case 2131361795:
      KXLog.d(TAG, "Got decode succeeded message");
      this.state = State.SUCCESS;
      Bundle localBundle = paramMessage.getData();
      if (localBundle == null);
      for (Bitmap localBitmap = null; ; localBitmap = (Bitmap)localBundle.getParcelable("barcode_bitmap"))
      {
        this.activity.handleDecode((Result)paramMessage.obj, localBitmap);
        return;
      }
    case 2131361794:
      this.state = State.PREVIEW;
      CameraManager.get().requestPreviewFrame(this.decodeThread.getHandler(), 2131361793);
      return;
    case 2131361799:
      KXLog.d(TAG, "Got return scan result message");
      this.activity.setResult(-1, (Intent)paramMessage.obj);
      this.activity.finish();
      return;
    case 2131361796:
    }
    KXLog.d(TAG, "Got product query message");
    Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse((String)paramMessage.obj));
    localIntent.addFlags(524288);
    this.activity.startActivity(localIntent);
  }

  public void quitSynchronously()
  {
    this.state = State.DONE;
    CameraManager.get().stopPreview();
    Message.obtain(this.decodeThread.getHandler(), 2131361797).sendToTarget();
    try
    {
      this.decodeThread.join();
      label35: removeMessages(2131361795);
      removeMessages(2131361794);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label35;
    }
  }

  private static enum State
  {
    static
    {
      DONE = new State("DONE", 2);
      State[] arrayOfState = new State[3];
      arrayOfState[0] = PREVIEW;
      arrayOfState[1] = SUCCESS;
      arrayOfState[2] = DONE;
      ENUM$VALUES = arrayOfState;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.zxing.CaptureActivityHandler
 * JD-Core Version:    0.6.0
 */