package com.kaixin001.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FaceModel extends KXModel
{
  private static final int[] EMOJI_CODES;
  private static final String FACE_DESCRIPTION_FILE = "face_book.xml";
  private static final String TAG = "FaceModel";
  public static final int TYPE_EMOJI = 1;
  public static final int TYPE_KAIXIN = 0;
  private static final String XML_FACECODE_ELE = "face";
  private static final String XML_STATEFACE_ELE = "state";
  private static FaceModel instance = null;
  private HashMap<String, Bitmap> mAllFaceInfoMap = null;
  private ArrayList<String> mAllFaceStringsSortByLength = null;
  private ArrayList<String> mEmojiCodes = new ArrayList();
  private ArrayList<Bitmap> mEmojiIcons = new ArrayList();
  private boolean mInitialized = false;
  private ArrayList<Bitmap> mStateFaceIcons = new ArrayList();
  private HashMap<String, Bitmap> mStateMap = new HashMap();
  private HashMap<String, String> mStateResMap = new HashMap();
  private ArrayList<String> mStateStrings = new ArrayList();

  static
  {
    EMOJI_CODES = new int[] { 58369, 58370, 58371, 58372, 58373, 58374, 58375, 58377, 58378, 58379, 58380, 58381, 58382, 58384, 58385, 58386, 58387, 58388, 58389, 58390, 58391, 58392, 57431, 57432, 57433, 57605, 57606, 57608, 58678, 57345, 57346, 57347, 57348, 57349, 57356, 57358, 57375, 57394, 57395, 57396, 57397, 57407, 57423, 57426, 57611, 57616, 57617, 57618, 57619, 57622, 57626, 57627, 57628, 57629, 57662, 57668, 57676, 57682, 57689, 57690, 58113, 58117, 58120, 58122, 58124, 58128, 58132, 58136, 58137, 58138, 58139, 58141, 58142, 58143, 58146, 58147, 58151, 58170, 58171, 58181, 58408, 58410, 58412, 58416, 58417, 58423, 58437, 58441, 58648, 58649, 58650, 58655 };
  }

  private FaceModel()
  {
    Context localContext = KXApplication.getInstance().getApplicationContext();
    if (localContext != null)
    {
      init(localContext);
      return;
    }
    throw new RuntimeException("context is null, we can't init this model with null context");
  }

  public static FaceModel getInstance()
  {
    
   
      if (instance == null)
      {
        KXLog.d("FaceModel", " new intance ");
        instance = new FaceModel();
      }
      FaceModel localFaceModel = instance;
      return localFaceModel;
   
  }

  // ERROR //
  private void init(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 145	com/kaixin001/model/FaceModel:mInitialized	Z
    //   6: istore_3
    //   7: iload_3
    //   8: ifeq +6 -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_1
    //   15: invokevirtual 206	android/content/Context:getAssets	()Landroid/content/res/AssetManager;
    //   18: astore 4
    //   20: aload_0
    //   21: invokestatic 212	javax/xml/parsers/DocumentBuilderFactory:newInstance	()Ljavax/xml/parsers/DocumentBuilderFactory;
    //   24: invokevirtual 216	javax/xml/parsers/DocumentBuilderFactory:newDocumentBuilder	()Ljavax/xml/parsers/DocumentBuilder;
    //   27: aload 4
    //   29: ldc 10
    //   31: invokevirtual 222	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   34: invokevirtual 228	javax/xml/parsers/DocumentBuilder:parse	(Ljava/io/InputStream;)Lorg/w3c/dom/Document;
    //   37: invokeinterface 234 1 0
    //   42: ldc 24
    //   44: invokeinterface 240 2 0
    //   49: iconst_0
    //   50: invokeinterface 246 2 0
    //   55: checkcast 236	org/w3c/dom/Element
    //   58: aload_1
    //   59: invokevirtual 250	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   62: invokespecial 254	com/kaixin001/model/FaceModel:initFaces	(Lorg/w3c/dom/Element;Landroid/content/res/Resources;)V
    //   65: aload_0
    //   66: aload_1
    //   67: invokevirtual 250	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   70: invokespecial 258	com/kaixin001/model/FaceModel:initEmojis	(Landroid/content/res/Resources;)V
    //   73: aload_0
    //   74: iconst_1
    //   75: putfield 145	com/kaixin001/model/FaceModel:mInitialized	Z
    //   78: goto -67 -> 11
    //   81: astore_2
    //   82: aload_0
    //   83: monitorexit
    //   84: aload_2
    //   85: athrow
    //   86: astore 6
    //   88: aload 6
    //   90: invokevirtual 261	java/lang/Exception:printStackTrace	()V
    //   93: goto -28 -> 65
    //   96: astore 5
    //   98: aload 5
    //   100: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   2	7	81	finally
    //   14	20	81	finally
    //   65	78	81	finally
    //   98	101	81	finally
    //   20	65	86	java/lang/Exception
    //   20	65	96	finally
    //   88	93	96	finally
  }

  private void initEmojis(Resources paramResources)
  {
    for (int i = 0; ; i++)
    {
      if (i >= EMOJI_CODES.length)
        return;
      int j = EMOJI_CODES[i];
      Bitmap localBitmap = BitmapFactory.decodeResource(paramResources, paramResources.getIdentifier("emoji_" + j, "drawable", "com.kaixin001.activity"));
      this.mEmojiIcons.add(localBitmap);
      this.mEmojiCodes.add(String.valueOf(j));
    }
  }

  private void initFaces(Element paramElement, Resources paramResources)
  {
    HashMap localHashMap1 = this.mStateMap;
    ArrayList localArrayList1 = this.mStateStrings;
    ArrayList localArrayList2 = this.mStateFaceIcons;
    HashMap localHashMap2 = this.mStateResMap;
    localHashMap1.clear();
    localArrayList1.clear();
    localArrayList2.clear();
    localHashMap2.clear();
    NodeList localNodeList = paramElement.getElementsByTagName("face");
    int i = localNodeList.getLength();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      Element localElement = (Element)localNodeList.item(j);
      String str1 = localElement.getAttribute("code");
      String str2 = localElement.getFirstChild().getNodeValue();
      KXLog.d("FaceModel", "parse face: " + str1 + "           <---> " + str2);
      Bitmap localBitmap = BitmapFactory.decodeResource(paramResources, paramResources.getIdentifier(str2, "drawable", "com.kaixin001.activity"));
      localArrayList1.add(str1);
      localHashMap1.put(str1, localBitmap);
      localArrayList2.add(localBitmap);
      localHashMap2.put(str1, str2);
    }
  }

  public void clear()
  {
    this.mInitialized = false;
    this.mStateMap.clear();
    this.mStateResMap.clear();
    this.mStateStrings.clear();
  }

  public HashMap<String, Bitmap> getAllFaceInfo()
  {
    int j;
    int k;
    if (this.mAllFaceInfoMap == null)
    {
      this.mAllFaceInfoMap = new HashMap();
      int i = this.mStateFaceIcons.size();
      j = 0;
      if (j < i)
    	  this.mAllFaceInfoMap.put((String)this.mStateStrings.get(j), (Bitmap)this.mStateFaceIcons.get(j));
      
      
    } 
    return this.mAllFaceInfoMap;
   
  }

  public ArrayList<String> getAllFaceStringSortByLength()
  {
    return null;
    
  }

  public ArrayList<String> getFaceCodes(int paramInt)
  {
    if (paramInt == 0)
      return this.mStateStrings;
    return this.mEmojiCodes;
  }

  public Bitmap getFaceIcon(String paramString)
  {
    Bitmap localBitmap = (Bitmap)this.mStateMap.get(paramString);
    if (localBitmap == null);
    for (int i = 0; ; i++)
    {
      if (i >= this.mEmojiCodes.size())
        return localBitmap;
      if (((String)this.mEmojiCodes.get(i)).equals(paramString))
        return (Bitmap)this.mEmojiIcons.get(i);
    }
  }

  public ArrayList<Bitmap> getFaceIcons(int paramInt)
  {
    if (paramInt == 0)
      return this.mStateFaceIcons;
    return this.mEmojiIcons;
  }

  public ArrayList<Bitmap> getStateFaceIcons()
  {
    return this.mStateFaceIcons;
  }

  public ArrayList<String> getStateFaceStrings()
  {
    return this.mStateStrings;
  }

  public SpannableString processTextForFace(String paramString)
  {
	  SpannableString localSpannableString = new SpannableString(paramString);
    if (paramString.length() == 0)
    {
    	 localSpannableString = null;
         
    }
    return localSpannableString;
    /*
    int i = 0;
   
    while (true)
    {
      int j = paramString.indexOf("(#", i);
      if (j < 0)
        break;
      int k = paramString.indexOf(")", i);
      if ((k < 0) || (j >= k))
        break;
      Bitmap localBitmap = getFaceIcon(paramString.substring(j, k + 1));
      if (localBitmap != null)
        localSpannableString.setSpan(new ImageSpan(localBitmap), j, k + 1, 33);
      i = k + 1;
    }*/
  }
  
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.FaceModel
 * JD-Core Version:    0.6.0
 */