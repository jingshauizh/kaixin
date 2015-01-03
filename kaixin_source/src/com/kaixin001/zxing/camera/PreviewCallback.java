package com.kaixin001.zxing.camera;

import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Handler;
import android.os.Message;
import com.kaixin001.util.KXLog;

final class PreviewCallback
  implements Camera.PreviewCallback
{
  private static final String TAG = PreviewCallback.class.getSimpleName();
  private final CameraConfigurationManager configManager;
  private Handler previewHandler;
  private int previewMessage;
  private final boolean useOneShotPreviewCallback;

  PreviewCallback(CameraConfigurationManager paramCameraConfigurationManager, boolean paramBoolean)
  {
    this.configManager = paramCameraConfigurationManager;
    this.useOneShotPreviewCallback = paramBoolean;
  }

  public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera)
  {
    Point localPoint = this.configManager.getCameraResolution();
    if (!this.useOneShotPreviewCallback)
      paramCamera.setPreviewCallback(null);
    if (this.previewHandler != null)
    {
      this.previewHandler.obtainMessage(this.previewMessage, localPoint.x, localPoint.y, paramArrayOfByte).sendToTarget();
      this.previewHandler = null;
      return;
    }
    KXLog.d(TAG, "Got preview callback, but no handler for it");
  }

  void setHandler(Handler paramHandler, int paramInt)
  {
    this.previewHandler = paramHandler;
    this.previewMessage = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.zxing.camera.PreviewCallback
 * JD-Core Version:    0.6.0
 */