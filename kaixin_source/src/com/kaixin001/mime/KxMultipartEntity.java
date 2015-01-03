package com.kaixin001.mime;

import com.kaixin001.network.HttpProgressListener;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class KxMultipartEntity extends MultipartEntity
{
  private OutputStream mLastOutputStream;
  private HttpProgressListener mListener;
  private CountingOutputStream mOutputStream;

  public KxMultipartEntity(HttpProgressListener paramHttpProgressListener)
  {
    super(HttpMultipartMode.BROWSER_COMPATIBLE);
    this.mListener = paramHttpProgressListener;
  }

  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    if ((this.mLastOutputStream == null) || (this.mLastOutputStream != paramOutputStream))
    {
      this.mLastOutputStream = paramOutputStream;
      this.mOutputStream = new CountingOutputStream(paramOutputStream, super.getContentLength());
    }
    super.writeTo(this.mOutputStream);
  }

  private class CountingOutputStream extends FilterOutputStream
  {
    private long current = 0L;
    private long total = 0L;
    private OutputStream wrappedOutputStream_;

    public CountingOutputStream(OutputStream paramLong, long arg3)
    {
      super();
      this.wrappedOutputStream_ = paramLong;
      Object localObject;
      this.total = localObject;
    }

    public void write(int paramInt)
      throws IOException
    {
      super.write(paramInt);
    }

    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      this.wrappedOutputStream_.write(paramArrayOfByte, paramInt1, paramInt2);
      this.current += paramInt2;
      if (KxMultipartEntity.this.mListener != null)
        KxMultipartEntity.this.mListener.transferred(this.current, this.total);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.mime.KxMultipartEntity
 * JD-Core Version:    0.6.0
 */