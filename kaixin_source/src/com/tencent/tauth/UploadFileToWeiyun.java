package com.tencent.tauth;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UploadFileToWeiyun
{
  private String bmd5;
  private int body_len;
  private int cmd = 1000;
  private byte[] data;
  private int data_len;
  private String file_id;
  private byte[] file_key;
  private int file_key_len = 20;
  private int file_size;
  private String filepath;
  private String host;
  private String mAppid;
  private Handler mHandler = new UploadFileToWeiyun.1(this, Looper.getMainLooper());
  private IUploadFileToWeiyunStatus mListener;
  private String mOpenid;
  private Tencent mTencent;
  private String mToken;
  private int magic_num = -1412589450;
  private int offset;
  private int port;
  private byte[] postbody;
  private String requestUrl;
  private int reserved = 0;
  String str_file_key;
  private String sum;
  private byte[] ukey;
  private int ukey_len = 304;

  public UploadFileToWeiyun(Tencent paramTencent, String paramString1, String paramString2, IUploadFileToWeiyunStatus paramIUploadFileToWeiyunStatus)
  {
    this.mTencent = paramTencent;
    this.filepath = paramString1;
    this.mListener = paramIUploadFileToWeiyunStatus;
    this.requestUrl = paramString2;
  }

  private void doUpload()
  {
    this.mListener.onUploadStart();
    new UploadFileToWeiyun.3(this).start();
  }

  private void getUploadPermission()
  {
    new UploadFileToWeiyun.2(this).start();
  }

  private byte[] packPostBody(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte, 0, paramInt1);
      this.bmd5 = DataConvert.toHexString(localMessageDigest.digest());
      localMessageDigest.reset();
      Log.i("weiyun_test", "uploadFileToWeiyun prepareFileInfo  md5:" + this.bmd5);
      int i = paramInt1 + 340;
      byte[] arrayOfByte = new byte[4 + (4 + (4 + (i + 4)))];
      int j = 0 + DataConvert.putInt2Bytes(-1412589450, arrayOfByte, 0);
      int k = j + DataConvert.putInt2Bytes(1000, arrayOfByte, j);
      int m = k + DataConvert.putInt2Bytes(0, arrayOfByte, k);
      int n = m + DataConvert.putInt2Bytes(i, arrayOfByte, m);
      int i1 = n + DataConvert.putShort2Bytes(304, arrayOfByte, n);
      int i2 = i1 + DataConvert.putBytes2Bytes(this.ukey, arrayOfByte, i1);
      int i3 = i2 + DataConvert.putShort2Bytes(20, arrayOfByte, i2);
      int i4 = i3 + DataConvert.putString2Bytes(this.str_file_key, arrayOfByte, i3);
      int i5 = i4 + DataConvert.putInt2Bytes(this.file_size, arrayOfByte, i4);
      int i6 = i5 + DataConvert.putInt2Bytes(paramInt2, arrayOfByte, i5);
      int i7 = i6 + DataConvert.putInt2Bytes(paramInt1, arrayOfByte, i6);
      (i7 + DataConvert.putBytes2Bytes(paramArrayOfByte, paramInt1, arrayOfByte, i7));
      return arrayOfByte;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      Log.e("weiyun_test", "uploadFileToWeiyun prepareFileInfo  error:" + localNoSuchAlgorithmException.getLocalizedMessage());
      localNoSuchAlgorithmException.printStackTrace();
      this.mListener.onError("prepareFileInfo error: get bmd5 failed");
    }
    return null;
  }

  // ERROR //
  private void prepareFileInfo()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 72	com/tencent/tauth/UploadFileToWeiyun:mListener	Lcom/tencent/tauth/IUploadFileToWeiyunStatus;
    //   4: invokeinterface 239 1 0
    //   9: aload_0
    //   10: new 241	java/io/File
    //   13: dup
    //   14: aload_0
    //   15: getfield 70	com/tencent/tauth/UploadFileToWeiyun:filepath	Ljava/lang/String;
    //   18: invokespecial 243	java/io/File:<init>	(Ljava/lang/String;)V
    //   21: invokevirtual 247	java/io/File:length	()J
    //   24: l2i
    //   25: putfield 80	com/tencent/tauth/UploadFileToWeiyun:file_size	I
    //   28: ldc 249
    //   30: invokestatic 158	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   33: astore_2
    //   34: new 251	java/io/FileInputStream
    //   37: dup
    //   38: aload_0
    //   39: getfield 70	com/tencent/tauth/UploadFileToWeiyun:filepath	Ljava/lang/String;
    //   42: invokespecial 252	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   45: astore_3
    //   46: new 254	java/security/DigestInputStream
    //   49: dup
    //   50: aload_3
    //   51: aload_2
    //   52: invokespecial 257	java/security/DigestInputStream:<init>	(Ljava/io/InputStream;Ljava/security/MessageDigest;)V
    //   55: astore 4
    //   57: ldc_w 258
    //   60: newarray byte
    //   62: astore 5
    //   64: aload 4
    //   66: aload 5
    //   68: invokevirtual 262	java/security/DigestInputStream:read	([B)I
    //   71: ifgt -7 -> 64
    //   74: aload 4
    //   76: invokevirtual 266	java/security/DigestInputStream:getMessageDigest	()Ljava/security/MessageDigest;
    //   79: astore 6
    //   81: aload_0
    //   82: aload 6
    //   84: invokevirtual 166	java/security/MessageDigest:digest	()[B
    //   87: putfield 268	com/tencent/tauth/UploadFileToWeiyun:file_key	[B
    //   90: aload_0
    //   91: aload_0
    //   92: getfield 268	com/tencent/tauth/UploadFileToWeiyun:file_key	[B
    //   95: invokestatic 172	com/tencent/tauth/DataConvert:toHexString	([B)Ljava/lang/String;
    //   98: putfield 209	com/tencent/tauth/UploadFileToWeiyun:str_file_key	Ljava/lang/String;
    //   101: aload 6
    //   103: invokevirtual 175	java/security/MessageDigest:reset	()V
    //   106: aload_3
    //   107: invokevirtual 271	java/io/FileInputStream:close	()V
    //   110: aload 4
    //   112: invokevirtual 272	java/security/DigestInputStream:close	()V
    //   115: ldc 152
    //   117: invokestatic 158	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
    //   120: astore 8
    //   122: new 251	java/io/FileInputStream
    //   125: dup
    //   126: aload_0
    //   127: getfield 70	com/tencent/tauth/UploadFileToWeiyun:filepath	Ljava/lang/String;
    //   130: invokespecial 252	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   133: astore 9
    //   135: new 254	java/security/DigestInputStream
    //   138: dup
    //   139: aload 9
    //   141: aload 8
    //   143: invokespecial 257	java/security/DigestInputStream:<init>	(Ljava/io/InputStream;Ljava/security/MessageDigest;)V
    //   146: astore 10
    //   148: ldc_w 258
    //   151: newarray byte
    //   153: astore 11
    //   155: aload 10
    //   157: aload 11
    //   159: invokevirtual 262	java/security/DigestInputStream:read	([B)I
    //   162: ifgt -7 -> 155
    //   165: aload 10
    //   167: invokevirtual 266	java/security/DigestInputStream:getMessageDigest	()Ljava/security/MessageDigest;
    //   170: astore 12
    //   172: aload_0
    //   173: aload 12
    //   175: invokevirtual 166	java/security/MessageDigest:digest	()[B
    //   178: invokestatic 172	com/tencent/tauth/DataConvert:toHexString	([B)Ljava/lang/String;
    //   181: putfield 131	com/tencent/tauth/UploadFileToWeiyun:bmd5	Ljava/lang/String;
    //   184: aload 12
    //   186: invokevirtual 175	java/security/MessageDigest:reset	()V
    //   189: aload 9
    //   191: invokevirtual 271	java/io/FileInputStream:close	()V
    //   194: aload 10
    //   196: invokevirtual 272	java/security/DigestInputStream:close	()V
    //   199: return
    //   200: astore_1
    //   201: aload_1
    //   202: invokevirtual 273	java/lang/Exception:printStackTrace	()V
    //   205: aload_0
    //   206: getfield 72	com/tencent/tauth/UploadFileToWeiyun:mListener	Lcom/tencent/tauth/IUploadFileToWeiyunStatus;
    //   209: ldc_w 275
    //   212: invokeinterface 233 2 0
    //   217: return
    //   218: astore 7
    //   220: aload 7
    //   222: invokevirtual 273	java/lang/Exception:printStackTrace	()V
    //   225: aload_0
    //   226: getfield 72	com/tencent/tauth/UploadFileToWeiyun:mListener	Lcom/tencent/tauth/IUploadFileToWeiyunStatus;
    //   229: ldc_w 275
    //   232: invokeinterface 233 2 0
    //   237: return
    //
    // Exception table:
    //   from	to	target	type
    //   28	64	200	java/lang/Exception
    //   64	115	200	java/lang/Exception
    //   115	155	218	java/lang/Exception
    //   155	199	218	java/lang/Exception
  }

  public void start()
  {
    prepareFileInfo();
    getUploadPermission();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.UploadFileToWeiyun
 * JD-Core Version:    0.6.0
 */