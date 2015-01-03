package org.apache.sanselan.formats.tiff;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.formats.tiff.constants.TiffDirectoryConstants.ExifDirectoryType;
import org.apache.sanselan.formats.tiff.fieldtypes.FieldType;

public class TiffField
  implements TiffConstants
{
  private static final Map ALL_TAG_MAP;
  public static final String Attribute_Tag = "Tag";
  private static final Map EXIF_TAG_MAP;
  private static final Map GPS_TAG_MAP = makeTagMap(ALL_GPS_TAGS, false, "GPS");
  private static final Map TIFF_TAG_MAP = makeTagMap(ALL_TIFF_TAGS, false, "TIFF");
  public final int byteOrder;
  public final int directoryType;
  public final FieldType fieldType;
  public final int length;
  public byte[] oversizeValue = null;
  private int sortHint = -1;
  public final int tag;
  public final TagInfo tagInfo;
  public final int type;
  public final int valueOffset;
  public final byte[] valueOffsetBytes;

  static
  {
    EXIF_TAG_MAP = makeTagMap(ALL_EXIF_TAGS, true, "EXIF");
    ALL_TAG_MAP = makeTagMap(ALL_TAGS, true, "All");
  }

  public TiffField(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfByte, int paramInt6)
  {
    this.tag = paramInt1;
    this.directoryType = paramInt2;
    this.type = paramInt3;
    this.length = paramInt4;
    this.valueOffset = paramInt5;
    this.valueOffsetBytes = paramArrayOfByte;
    this.byteOrder = paramInt6;
    this.fieldType = getFieldType(paramInt3);
    this.tagInfo = getTag(paramInt2, paramInt1);
  }

  private static FieldType getFieldType(int paramInt)
  {
    for (int i = 0; ; i++)
    {
      FieldType localFieldType;
      if (i >= FIELD_TYPES.length)
        localFieldType = FIELD_TYPE_UNKNOWN;
      do
      {
        return localFieldType;
        localFieldType = FIELD_TYPES[i];
      }
      while (localFieldType.type == paramInt);
    }
  }

  private static TagInfo getTag(int paramInt1, int paramInt2)
  {
    Integer localInteger = new Integer(paramInt2);
    List localList = (List)EXIF_TAG_MAP.get(localInteger);
    if (localList == null)
      return TIFF_TAG_UNKNOWN;
    return getTag(paramInt1, paramInt2, localList);
  }

  private static TagInfo getTag(int paramInt1, int paramInt2, List paramList)
  {
    TagInfo localTagInfo;
    if (paramList.size() < 1)
    {
      localTagInfo = null;
      return localTagInfo;
    }
    int i = 0;
    label18: int j;
    if (i >= paramList.size())
    {
      j = 0;
      label31: if (j < paramList.size())
        break label223;
    }
    for (int k = 0; ; k++)
    {
      if (k >= paramList.size())
      {
        return TIFF_TAG_UNKNOWN;
        localTagInfo = (TagInfo)paramList.get(i);
        if (localTagInfo.directoryType == EXIF_DIRECTORY_UNKNOWN);
        do
        {
          i++;
          break label18;
          if (((paramInt1 == -2) && (localTagInfo.directoryType == EXIF_DIRECTORY_EXIF_IFD)) || ((paramInt1 == -4) && (localTagInfo.directoryType == EXIF_DIRECTORY_INTEROP_IFD)) || ((paramInt1 == -3) && (localTagInfo.directoryType == EXIF_DIRECTORY_GPS)) || ((paramInt1 == -5) && (localTagInfo.directoryType == EXIF_DIRECTORY_MAKER_NOTES)) || ((paramInt1 == 0) && (localTagInfo.directoryType == TIFF_DIRECTORY_IFD0)) || ((paramInt1 == 1) && (localTagInfo.directoryType == TIFF_DIRECTORY_IFD1)) || ((paramInt1 == 2) && (localTagInfo.directoryType == TIFF_DIRECTORY_IFD2)))
            break;
        }
        while ((paramInt1 != 3) || (localTagInfo.directoryType != TIFF_DIRECTORY_IFD3));
        return localTagInfo;
        label223: localTagInfo = (TagInfo)paramList.get(j);
        if (localTagInfo.directoryType == EXIF_DIRECTORY_UNKNOWN);
        do
        {
          j++;
          break label31;
          if ((paramInt1 >= 0) && (localTagInfo.directoryType.isImageDirectory()))
            break;
        }
        while ((paramInt1 >= 0) || (localTagInfo.directoryType.isImageDirectory()));
        return localTagInfo;
      }
      localTagInfo = (TagInfo)paramList.get(k);
      if (localTagInfo.directoryType == EXIF_DIRECTORY_UNKNOWN)
        break;
    }
  }

  private String getValueDescription(Object paramObject)
  {
    if (paramObject == null)
      return null;
    if ((paramObject instanceof Number))
      return paramObject.toString();
    if ((paramObject instanceof String))
      return "'" + paramObject.toString().trim() + "'";
    if ((paramObject instanceof Date))
      return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format((Date)paramObject);
    if ((paramObject instanceof Object[]))
    {
      Object[] arrayOfObject = (Object[])paramObject;
      StringBuffer localStringBuffer7 = new StringBuffer();
      for (int i4 = 0; ; i4++)
      {
        if (i4 >= arrayOfObject.length);
        Object localObject;
        while (true)
        {
          return localStringBuffer7.toString();
          localObject = arrayOfObject[i4];
          if (i4 <= 50)
            break;
          localStringBuffer7.append("... (" + arrayOfObject.length + ")");
        }
        if (i4 > 0)
          localStringBuffer7.append(", ");
        localStringBuffer7.append(localObject);
      }
    }
    if ((paramObject instanceof int[]))
    {
      int[] arrayOfInt = (int[])paramObject;
      StringBuffer localStringBuffer6 = new StringBuffer();
      for (int i2 = 0; ; i2++)
      {
        if (i2 >= arrayOfInt.length);
        int i3;
        while (true)
        {
          return localStringBuffer6.toString();
          i3 = arrayOfInt[i2];
          if (i2 <= 50)
            break;
          localStringBuffer6.append("... (" + arrayOfInt.length + ")");
        }
        if (i2 > 0)
          localStringBuffer6.append(", ");
        localStringBuffer6.append(i3);
      }
    }
    if ((paramObject instanceof long[]))
    {
      long[] arrayOfLong = (long[])paramObject;
      StringBuffer localStringBuffer5 = new StringBuffer();
      for (int i1 = 0; ; i1++)
      {
        if (i1 >= arrayOfLong.length);
        long l;
        while (true)
        {
          return localStringBuffer5.toString();
          l = arrayOfLong[i1];
          if (i1 <= 50)
            break;
          localStringBuffer5.append("... (" + arrayOfLong.length + ")");
        }
        if (i1 > 0)
          localStringBuffer5.append(", ");
        localStringBuffer5.append(l);
      }
    }
    if ((paramObject instanceof double[]))
    {
      double[] arrayOfDouble = (double[])paramObject;
      StringBuffer localStringBuffer4 = new StringBuffer();
      for (int n = 0; ; n++)
      {
        if (n >= arrayOfDouble.length);
        double d;
        while (true)
        {
          return localStringBuffer4.toString();
          d = arrayOfDouble[n];
          if (n <= 50)
            break;
          localStringBuffer4.append("... (" + arrayOfDouble.length + ")");
        }
        if (n > 0)
          localStringBuffer4.append(", ");
        localStringBuffer4.append(d);
      }
    }
    if ((paramObject instanceof byte[]))
    {
      byte[] arrayOfByte = (byte[])paramObject;
      StringBuffer localStringBuffer3 = new StringBuffer();
      for (int k = 0; ; k++)
      {
        if (k >= arrayOfByte.length);
        int m;
        while (true)
        {
          return localStringBuffer3.toString();
          m = arrayOfByte[k];
          if (k <= 50)
            break;
          localStringBuffer3.append("... (" + arrayOfByte.length + ")");
        }
        if (k > 0)
          localStringBuffer3.append(", ");
        localStringBuffer3.append(m);
      }
    }
    if ((paramObject instanceof char[]))
    {
      char[] arrayOfChar = (char[])paramObject;
      StringBuffer localStringBuffer2 = new StringBuffer();
      for (int j = 0; ; j++)
      {
        if (j >= arrayOfChar.length);
        char c;
        while (true)
        {
          return localStringBuffer2.toString();
          c = arrayOfChar[j];
          if (j <= 50)
            break;
          localStringBuffer2.append("... (" + arrayOfChar.length + ")");
        }
        if (j > 0)
          localStringBuffer2.append(", ");
        localStringBuffer2.append(c);
      }
    }
    if ((paramObject instanceof float[]))
    {
      float[] arrayOfFloat = (float[])paramObject;
      StringBuffer localStringBuffer1 = new StringBuffer();
      for (int i = 0; ; i++)
      {
        if (i >= arrayOfFloat.length);
        float f;
        while (true)
        {
          return localStringBuffer1.toString();
          f = arrayOfFloat[i];
          if (i <= 50)
            break;
          localStringBuffer1.append("... (" + arrayOfFloat.length + ")");
        }
        if (i > 0)
          localStringBuffer1.append(", ");
        localStringBuffer1.append(f);
      }
    }
    return "Unknown: " + paramObject.getClass().getName();
  }

  private int getValueLengthInBytes()
  {
    return this.fieldType.length * this.length;
  }

  private static final Map makeTagMap(TagInfo[] paramArrayOfTagInfo, boolean paramBoolean, String paramString)
  {
    Hashtable localHashtable = new Hashtable();
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfTagInfo.length)
        return localHashtable;
      TagInfo localTagInfo = paramArrayOfTagInfo[i];
      Integer localInteger = new Integer(localTagInfo.tag);
      Object localObject = (List)localHashtable.get(localInteger);
      if (localObject == null)
      {
        localObject = new ArrayList();
        localHashtable.put(localInteger, localObject);
      }
      ((List)localObject).add(localTagInfo);
    }
  }

  public void dump()
  {
    PrintWriter localPrintWriter = new PrintWriter(System.out);
    dump(localPrintWriter);
    localPrintWriter.flush();
  }

  public void dump(PrintWriter paramPrintWriter)
  {
    dump(paramPrintWriter, null);
  }

  public void dump(PrintWriter paramPrintWriter, String paramString)
  {
    if (paramString != null)
      paramPrintWriter.print(paramString + ": ");
    paramPrintWriter.println(toString());
    paramPrintWriter.flush();
  }

  public void fillInValue(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    if (this.fieldType.isLocalValue(this))
      return;
    int i = getValueLengthInBytes();
    setOversizeValue(paramByteSource.getBlock(this.valueOffset, i));
  }

  public byte[] getByteArrayValue()
    throws ImageReadException
  {
    return this.fieldType.getRawBytes(this);
  }

  public int getBytesLength()
    throws ImageReadException
  {
    return this.fieldType.getBytesLength(this);
  }

  public String getDescriptionWithoutValue()
  {
    return this.tag + " (0x" + Integer.toHexString(this.tag) + ": " + this.tagInfo.name + "): ";
  }

  public double[] getDoubleArrayValue()
    throws ImageReadException
  {
    Object localObject = getValue();
    double[] arrayOfDouble2;
    if ((localObject instanceof Number))
    {
      arrayOfDouble2 = new double[1];
      arrayOfDouble2[0] = ((Number)localObject).doubleValue();
    }
    while (true)
    {
      return arrayOfDouble2;
      if ((localObject instanceof Number[]))
      {
        Number[] arrayOfNumber = (Number[])localObject;
        arrayOfDouble2 = new double[arrayOfNumber.length];
        for (int m = 0; m < arrayOfNumber.length; m++)
          arrayOfDouble2[m] = arrayOfNumber[m].doubleValue();
        continue;
      }
      if ((localObject instanceof int[]))
      {
        int[] arrayOfInt = (int[])localObject;
        arrayOfDouble2 = new double[arrayOfInt.length];
        for (int k = 0; k < arrayOfInt.length; k++)
          arrayOfDouble2[k] = arrayOfInt[k];
        continue;
      }
      if ((localObject instanceof float[]))
      {
        float[] arrayOfFloat = (float[])localObject;
        arrayOfDouble2 = new double[arrayOfFloat.length];
        for (int j = 0; j < arrayOfFloat.length; j++)
          arrayOfDouble2[j] = arrayOfFloat[j];
        continue;
      }
      if (!(localObject instanceof double[]))
        break;
      double[] arrayOfDouble1 = (double[])localObject;
      arrayOfDouble2 = new double[arrayOfDouble1.length];
      for (int i = 0; i < arrayOfDouble1.length; i++)
        arrayOfDouble2[i] = arrayOfDouble1[i];
    }
    throw new ImageReadException("Unknown value: " + localObject + " for: " + this.tagInfo.getDescription());
  }

  public double getDoubleValue()
    throws ImageReadException
  {
    Object localObject = getValue();
    if (localObject == null)
      throw new ImageReadException("Missing value: " + this.tagInfo.getDescription());
    return ((Number)localObject).doubleValue();
  }

  public String getFieldTypeName()
  {
    return this.fieldType.name;
  }

  public int[] getIntArrayValue()
    throws ImageReadException
  {
    Object localObject = getValue();
    int[] arrayOfInt2;
    if ((localObject instanceof Number))
    {
      arrayOfInt2 = new int[1];
      arrayOfInt2[0] = ((Number)localObject).intValue();
    }
    while (true)
    {
      return arrayOfInt2;
      if ((localObject instanceof Number[]))
      {
        Number[] arrayOfNumber = (Number[])localObject;
        arrayOfInt2 = new int[arrayOfNumber.length];
        for (int j = 0; j < arrayOfNumber.length; j++)
          arrayOfInt2[j] = arrayOfNumber[j].intValue();
        continue;
      }
      if (!(localObject instanceof int[]))
        break;
      int[] arrayOfInt1 = (int[])localObject;
      arrayOfInt2 = new int[arrayOfInt1.length];
      for (int i = 0; i < arrayOfInt1.length; i++)
        arrayOfInt2[i] = arrayOfInt1[i];
    }
    throw new ImageReadException("Unknown value: " + localObject + " for: " + this.tagInfo.getDescription());
  }

  public int getIntValue()
    throws ImageReadException
  {
    Object localObject = getValue();
    if (localObject == null)
      throw new ImageReadException("Missing value: " + this.tagInfo.getDescription());
    return ((Number)localObject).intValue();
  }

  public int getIntValueOrArraySum()
    throws ImageReadException
  {
    Object localObject = getValue();
    int i;
    if ((localObject instanceof Number))
      i = ((Number)localObject).intValue();
    while (true)
    {
      return i;
      if ((localObject instanceof Number[]))
      {
        Number[] arrayOfNumber = (Number[])localObject;
        i = 0;
        for (int k = 0; k < arrayOfNumber.length; k++)
          i += arrayOfNumber[k].intValue();
        continue;
      }
      if (!(localObject instanceof int[]))
        break;
      int[] arrayOfInt = (int[])localObject;
      i = 0;
      for (int j = 0; j < arrayOfInt.length; j++)
        i += arrayOfInt[j];
    }
    throw new ImageReadException("Unknown value: " + localObject + " for: " + this.tagInfo.getDescription());
  }

  public TiffElement getOversizeValueElement()
  {
    if (this.fieldType.isLocalValue(this))
      return null;
    return new OversizeValueElement(this.valueOffset, this.oversizeValue.length);
  }

  public int getSortHint()
  {
    return this.sortHint;
  }

  public String getStringValue()
    throws ImageReadException
  {
    Object localObject = getValue();
    if (localObject == null)
      return null;
    if (!(localObject instanceof String))
      throw new ImageReadException("Expected String value(" + this.tagInfo.getDescription() + "): " + localObject);
    return (String)localObject;
  }

  public String getTagName()
  {
    if (this.tagInfo == TIFF_TAG_UNKNOWN)
      return this.tagInfo.name + " (0x" + Integer.toHexString(this.tag) + ")";
    return this.tagInfo.name;
  }

  public Object getValue()
    throws ImageReadException
  {
    return this.tagInfo.getValue(this);
  }

  public String getValueDescription()
  {
    try
    {
      String str = getValueDescription(getValue());
      return str;
    }
    catch (ImageReadException localImageReadException)
    {
    }
    return "Invalid value: " + localImageReadException.getMessage();
  }

  public boolean isLocalValue()
  {
    return this.fieldType.isLocalValue(this);
  }

  public void setOversizeValue(byte[] paramArrayOfByte)
  {
    this.oversizeValue = paramArrayOfByte;
  }

  public void setSortHint(int paramInt)
  {
    this.sortHint = paramInt;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(this.tag + " (0x" + Integer.toHexString(this.tag) + ": " + this.tagInfo.name + "): ");
    localStringBuffer.append(getValueDescription() + " (" + this.length + " " + this.fieldType.name + ")");
    return localStringBuffer.toString();
  }

  public final class OversizeValueElement extends TiffElement
  {
    public OversizeValueElement(int paramInt1, int arg3)
    {
      super(i);
    }

    public String getElementDescription(boolean paramBoolean)
    {
      if (paramBoolean)
        return null;
      return "OversizeValueElement, tag: " + TiffField.this.tagInfo.name + ", fieldType: " + TiffField.this.fieldType.name;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.TiffField
 * JD-Core Version:    0.6.0
 */