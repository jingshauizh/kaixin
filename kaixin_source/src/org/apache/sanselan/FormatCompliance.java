package org.apache.sanselan;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class FormatCompliance
{
  private final ArrayList comments = new ArrayList();
  private final String description;
  private final boolean failOnError;

  public FormatCompliance(String paramString)
  {
    this.description = paramString;
    this.failOnError = false;
  }

  public FormatCompliance(String paramString, boolean paramBoolean)
  {
    this.description = paramString;
    this.failOnError = paramBoolean;
  }

  public static final FormatCompliance getDefault()
  {
    return new FormatCompliance("ignore", false);
  }

  private String getValueDescription(int paramInt)
  {
    return paramInt + " (" + Integer.toHexString(paramInt) + ")";
  }

  public void addComment(String paramString)
    throws ImageReadException
  {
    this.comments.add(paramString);
    if (this.failOnError)
      throw new ImageReadException(paramString);
  }

  public void addComment(String paramString, int paramInt)
    throws ImageReadException
  {
    addComment(paramString + ": " + getValueDescription(paramInt));
  }

  public boolean checkBounds(String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws ImageReadException
  {
    if ((paramInt3 < paramInt1) || (paramInt3 > paramInt2))
    {
      addComment(paramString + ": " + "bounds check: " + paramInt1 + " <= " + paramInt3 + " <= " + paramInt2 + ": false");
      return false;
    }
    return true;
  }

  public boolean compare(String paramString, int paramInt1, int paramInt2)
    throws ImageReadException
  {
    return compare(paramString, new int[] { paramInt1 }, paramInt2);
  }

  public boolean compare(String paramString, int[] paramArrayOfInt, int paramInt)
    throws ImageReadException
  {
    int i = 1;
    int j = 0;
    StringBuffer localStringBuffer;
    if (j >= paramArrayOfInt.length)
    {
      localStringBuffer = new StringBuffer();
      localStringBuffer.append(paramString + ": " + "Unexpected value: (valid: ");
      if (paramArrayOfInt.length > i)
        localStringBuffer.append("{");
    }
    for (int k = 0; ; k++)
    {
      if (k >= paramArrayOfInt.length)
      {
        if (paramArrayOfInt.length > i)
          localStringBuffer.append("}");
        localStringBuffer.append(", actual: " + getValueDescription(paramInt) + ")");
        addComment(localStringBuffer.toString());
        i = 0;
        do
          return i;
        while (paramInt == paramArrayOfInt[j]);
        j++;
        break;
      }
      if (k > 0)
        localStringBuffer.append(", ");
      localStringBuffer.append(getValueDescription(paramArrayOfInt[k]));
    }
  }

  public boolean compare_bytes(String paramString, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws ImageReadException
  {
    if (paramArrayOfByte1.length != paramArrayOfByte2.length)
    {
      addComment(paramString + ": " + "Unexpected length: (expected: " + paramArrayOfByte1.length + ", actual: " + paramArrayOfByte2.length + ")");
      return false;
    }
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfByte1.length)
        return true;
      if (paramArrayOfByte1[i] == paramArrayOfByte2[i])
        continue;
      addComment(paramString + ": " + "Unexpected value: (expected: " + getValueDescription(paramArrayOfByte1[i]) + ", actual: " + getValueDescription(paramArrayOfByte2[i]) + ")");
      return false;
    }
  }

  public void dump()
  {
    dump(new PrintWriter(new OutputStreamWriter(System.out)));
  }

  public void dump(PrintWriter paramPrintWriter)
  {
    paramPrintWriter.println("Format Compliance: " + this.description);
    if (this.comments.size() == 0)
      paramPrintWriter.println("\tNo comments.");
    while (true)
    {
      paramPrintWriter.println("");
      paramPrintWriter.flush();
      return;
      for (int i = 0; i < this.comments.size(); i++)
        paramPrintWriter.println("\t" + (i + 1) + ": " + this.comments.get(i));
    }
  }

  public String toString()
  {
    StringWriter localStringWriter = new StringWriter();
    dump(new PrintWriter(localStringWriter));
    return localStringWriter.getBuffer().toString();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.FormatCompliance
 * JD-Core Version:    0.6.0
 */