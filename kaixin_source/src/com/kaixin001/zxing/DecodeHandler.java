package com.kaixin001.zxing;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.kaixin001.activity.CaptureActivity;
import java.util.Hashtable;

final class DecodeHandler extends Handler
{
  private static final String TAG = DecodeHandler.class.getSimpleName();
  private final CaptureActivity activity;
  private final MultiFormatReader multiFormatReader = new MultiFormatReader();
  private boolean running = true;

  DecodeHandler(CaptureActivity paramCaptureActivity, Hashtable<DecodeHintType, Object> paramHashtable)
  {
    this.multiFormatReader.setHints(paramHashtable);
    this.activity = paramCaptureActivity;
  }

  // ERROR //
  private void decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: invokestatic 49	java/lang/System:currentTimeMillis	()J
    //   3: lstore 4
    //   5: aload_1
    //   6: arraylength
    //   7: newarray byte
    //   9: astore 6
    //   11: iconst_0
    //   12: istore 7
    //   14: iload 7
    //   16: iload_3
    //   17: if_icmplt +150 -> 167
    //   20: invokestatic 55	com/kaixin001/zxing/camera/CameraManager:get	()Lcom/kaixin001/zxing/camera/CameraManager;
    //   23: aload 6
    //   25: iload_3
    //   26: iload_2
    //   27: invokevirtual 59	com/kaixin001/zxing/camera/CameraManager:buildLuminanceSource	([BII)Lcom/kaixin001/zxing/PlanarYUVLuminanceSource;
    //   30: astore 9
    //   32: new 61	com/google/zxing/common/HybridBinarizer
    //   35: dup
    //   36: aload 9
    //   38: invokespecial 64	com/google/zxing/common/HybridBinarizer:<init>	(Lcom/google/zxing/LuminanceSource;)V
    //   41: astore 10
    //   43: new 66	com/google/zxing/BinaryBitmap
    //   46: dup
    //   47: aload 10
    //   49: invokespecial 69	com/google/zxing/BinaryBitmap:<init>	(Lcom/google/zxing/Binarizer;)V
    //   52: astore 11
    //   54: aload_0
    //   55: getfield 33	com/kaixin001/zxing/DecodeHandler:multiFormatReader	Lcom/google/zxing/MultiFormatReader;
    //   58: aload 11
    //   60: invokevirtual 73	com/google/zxing/MultiFormatReader:decodeWithState	(Lcom/google/zxing/BinaryBitmap;)Lcom/google/zxing/Result;
    //   63: astore 19
    //   65: aload 19
    //   67: astore 14
    //   69: aload_0
    //   70: getfield 33	com/kaixin001/zxing/DecodeHandler:multiFormatReader	Lcom/google/zxing/MultiFormatReader;
    //   73: invokevirtual 76	com/google/zxing/MultiFormatReader:reset	()V
    //   76: aload 14
    //   78: ifnull +160 -> 238
    //   81: invokestatic 49	java/lang/System:currentTimeMillis	()J
    //   84: lstore 15
    //   86: getstatic 22	com/kaixin001/zxing/DecodeHandler:TAG	Ljava/lang/String;
    //   89: new 78	java/lang/StringBuilder
    //   92: dup
    //   93: ldc 80
    //   95: invokespecial 83	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   98: lload 15
    //   100: lload 4
    //   102: lsub
    //   103: invokevirtual 87	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   106: ldc 89
    //   108: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   114: invokestatic 101	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   117: aload_0
    //   118: getfield 39	com/kaixin001/zxing/DecodeHandler:activity	Lcom/kaixin001/activity/CaptureActivity;
    //   121: invokevirtual 107	com/kaixin001/activity/CaptureActivity:getHandler	()Landroid/os/Handler;
    //   124: ldc 108
    //   126: aload 14
    //   128: invokestatic 114	android/os/Message:obtain	(Landroid/os/Handler;ILjava/lang/Object;)Landroid/os/Message;
    //   131: astore 17
    //   133: new 116	android/os/Bundle
    //   136: dup
    //   137: invokespecial 117	android/os/Bundle:<init>	()V
    //   140: astore 18
    //   142: aload 18
    //   144: ldc 119
    //   146: aload 9
    //   148: invokevirtual 125	com/kaixin001/zxing/PlanarYUVLuminanceSource:renderCroppedGreyscaleBitmap	()Landroid/graphics/Bitmap;
    //   151: invokevirtual 129	android/os/Bundle:putParcelable	(Ljava/lang/String;Landroid/os/Parcelable;)V
    //   154: aload 17
    //   156: aload 18
    //   158: invokevirtual 133	android/os/Message:setData	(Landroid/os/Bundle;)V
    //   161: aload 17
    //   163: invokevirtual 136	android/os/Message:sendToTarget	()V
    //   166: return
    //   167: iconst_0
    //   168: istore 8
    //   170: iload 8
    //   172: iload_2
    //   173: if_icmplt +9 -> 182
    //   176: iinc 7 1
    //   179: goto -165 -> 14
    //   182: aload 6
    //   184: iconst_m1
    //   185: iload_3
    //   186: iload 8
    //   188: iload_3
    //   189: imul
    //   190: iadd
    //   191: iload 7
    //   193: isub
    //   194: iadd
    //   195: aload_1
    //   196: iload 8
    //   198: iload 7
    //   200: iload_2
    //   201: imul
    //   202: iadd
    //   203: baload
    //   204: bastore
    //   205: iinc 8 1
    //   208: goto -38 -> 170
    //   211: astore 13
    //   213: aload_0
    //   214: getfield 33	com/kaixin001/zxing/DecodeHandler:multiFormatReader	Lcom/google/zxing/MultiFormatReader;
    //   217: invokevirtual 76	com/google/zxing/MultiFormatReader:reset	()V
    //   220: aconst_null
    //   221: astore 14
    //   223: goto -147 -> 76
    //   226: astore 12
    //   228: aload_0
    //   229: getfield 33	com/kaixin001/zxing/DecodeHandler:multiFormatReader	Lcom/google/zxing/MultiFormatReader;
    //   232: invokevirtual 76	com/google/zxing/MultiFormatReader:reset	()V
    //   235: aload 12
    //   237: athrow
    //   238: aload_0
    //   239: getfield 39	com/kaixin001/zxing/DecodeHandler:activity	Lcom/kaixin001/activity/CaptureActivity;
    //   242: invokevirtual 107	com/kaixin001/activity/CaptureActivity:getHandler	()Landroid/os/Handler;
    //   245: ldc 137
    //   247: invokestatic 140	android/os/Message:obtain	(Landroid/os/Handler;I)Landroid/os/Message;
    //   250: invokevirtual 136	android/os/Message:sendToTarget	()V
    //   253: return
    //
    // Exception table:
    //   from	to	target	type
    //   54	65	211	com/google/zxing/ReaderException
    //   54	65	226	finally
  }

  public void handleMessage(Message paramMessage)
  {
    if (!this.running)
      return;
    switch (paramMessage.what)
    {
    default:
      return;
    case 2131361793:
      decode((byte[])paramMessage.obj, paramMessage.arg1, paramMessage.arg2);
      return;
    case 2131361797:
    }
    this.running = false;
    Looper.myLooper().quit();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.zxing.DecodeHandler
 * JD-Core Version:    0.6.0
 */