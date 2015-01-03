package org.apache.sanselan.formats.jpeg.segments;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.util.Debug;

public class SOSSegment extends Segment
{
  public SOSSegment(int paramInt1, int paramInt2, InputStream paramInputStream)
    throws ImageReadException, IOException
  {
    super(paramInt1, paramInt2);
    if (getDebug())
      System.out.println("SOSSegment marker_length: " + paramInt2);
    Debug.debug("SOS", paramInt2);
    int i = readByte("number_of_components_in_scan", paramInputStream, "Not a Valid JPEG File");
    Debug.debug("number_of_components_in_scan", i);
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        Debug.debug("start_of_spectral_selection", readByte("start_of_spectral_selection", paramInputStream, "Not a Valid JPEG File"));
        Debug.debug("end_of_spectral_selection", readByte("end_of_spectral_selection", paramInputStream, "Not a Valid JPEG File"));
        Debug.debug("successive_approximation_bit_position", readByte("successive_approximation_bit_position", paramInputStream, "Not a Valid JPEG File"));
        if (getDebug())
          System.out.println("");
        return;
      }
      Debug.debug("scan_component_selector", readByte("scan_component_selector", paramInputStream, "Not a Valid JPEG File"));
      Debug.debug("ac_dc_entrooy_coding_table_selector", readByte("ac_dc_entrooy_coding_table_selector", paramInputStream, "Not a Valid JPEG File"));
    }
  }

  public String getDescription()
  {
    return "SOS (" + getSegmentType() + ")";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.segments.SOSSegment
 * JD-Core Version:    0.6.0
 */