package org.apache.sanselan.formats.jpeg.segments;

import java.io.PrintWriter;
import org.apache.sanselan.common.BinaryFileParser;

public abstract class Segment extends BinaryFileParser
{
  public final int length;
  public final int marker;

  public Segment(int paramInt1, int paramInt2)
  {
    this.marker = paramInt1;
    this.length = paramInt2;
  }

  public void dump(PrintWriter paramPrintWriter)
  {
  }

  public abstract String getDescription();

  public String getSegmentType()
  {
    switch (this.marker)
    {
    default:
      if ((this.marker < 65282) || (this.marker > 65471))
        break;
      return "Reserved";
    case 65472:
      return "Start Of Frame, Baseline DCT, Huffman coding";
    case 65473:
      return "Start Of Frame, Extended sequential DCT, Huffman coding";
    case 65474:
      return "Start Of Frame, Progressive DCT, Huffman coding";
    case 65475:
      return "Start Of Frame, Lossless (sequential), Huffman coding";
    case 65477:
      return "Start Of Frame, Differential sequential DCT, Huffman coding";
    case 65478:
      return "Start Of Frame, Differential progressive DCT, Huffman coding";
    case 65479:
      return "Start Of Frame, Differential lossless (sequential), Huffman coding";
    case 65480:
      return "Start Of Frame, Reserved for JPEG extensions, arithmetic coding";
    case 65481:
      return "Start Of Frame, Extended sequential DCT, arithmetic coding";
    case 65482:
      return "Start Of Frame, Progressive DCT, arithmetic coding";
    case 65483:
      return "Start Of Frame, Lossless (sequential), arithmetic coding";
    case 65485:
      return "Start Of Frame, Differential sequential DCT, arithmetic coding";
    case 65486:
      return "Start Of Frame, Differential progressive DCT, arithmetic coding";
    case 65487:
      return "Start Of Frame, Differential lossless (sequential), arithmetic coding";
    case 65476:
      return "Define Huffman table(s)";
    case 65484:
      return "Define arithmetic coding conditioning(s)";
    case 65488:
      return "Restart with modulo 8 count 0";
    case 65489:
      return "Restart with modulo 8 count 1";
    case 65490:
      return "Restart with modulo 8 count 2";
    case 65491:
      return "Restart with modulo 8 count 3";
    case 65492:
      return "Restart with modulo 8 count 4";
    case 65493:
      return "Restart with modulo 8 count 5";
    case 65494:
      return "Restart with modulo 8 count 6";
    case 65495:
      return "Restart with modulo 8 count 7";
    case 65496:
      return "Start of image";
    case 65497:
      return "End of image";
    case 65498:
      return "Start of scan";
    case 65499:
      return "Define quantization table(s)";
    case 65500:
      return "Define number of lines";
    case 65501:
      return "Define restart interval";
    case 65502:
      return "Define hierarchical progression";
    case 65503:
      return "Expand reference component(s)";
    case 65534:
      return "Comment";
    case 65281:
      return "For temporary private use in arithmetic coding";
    }
    if ((this.marker >= 65504) && (this.marker <= 65519))
      return "APP" + (this.marker - 65504);
    if ((this.marker >= 65520) && (this.marker <= 65533))
      return "JPG" + (this.marker - 65504);
    return "Unknown";
  }

  public String toString()
  {
    return "[Segment: " + getDescription() + "]";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.segments.Segment
 * JD-Core Version:    0.6.0
 */