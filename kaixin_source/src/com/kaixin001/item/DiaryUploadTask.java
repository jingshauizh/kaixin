package com.kaixin001.item;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.db.UpLoadTaskListDBAdapter.TaskParameters;
import com.kaixin001.engine.DiaryEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.NewsModel;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UploadFile;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class DiaryUploadTask extends KXUploadTask
{
  public static final String SEPERATOR = "\t";
  private static final String TAG = "DiaryUploadTask";
  public static final String UPLOAD_IMAGE_FILENAME = "upload_image_";
  private String mContent = "";
  private String mDiaryId = "";
  private HashMap<String, String> mInsertedPic = null;

  public DiaryUploadTask(Context paramContext)
  {
    super(paramContext);
    setTaskType(3);
  }

  public static String convertPicMapToString(HashMap<String, String> paramHashMap)
  {
    if ((paramHashMap == null) || (paramHashMap.size() == 0))
      return null;
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = paramHashMap.entrySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localStringBuilder.toString();
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localStringBuilder.append((String)localEntry.getKey()).append("\t").append((String)localEntry.getValue());
    }
  }

  public static HashMap<String, String> convertStringToMap(String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    HashMap localHashMap = null;
    if (bool);
    while (true)
    {
      return localHashMap;
      String[] arrayOfString = paramString.split("\t");
      localHashMap = null;
      if (arrayOfString == null)
        continue;
      int i = arrayOfString.length;
      localHashMap = null;
      if (i == 0)
        continue;
      localHashMap = new HashMap();
      int j = arrayOfString.length / 2;
      for (int k = 0; k < j; k++)
        localHashMap.put(arrayOfString[(k * 2)], arrayOfString[(1 + k * 2)]);
    }
  }

  public void deleteDraft()
  {
    Iterator localIterator;
    if ((this.mInsertedPic != null) && (this.mInsertedPic.size() > 0))
      localIterator = this.mInsertedPic.keySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      String str = (String)localIterator.next();
      FileUtil.deleteFileWithoutCheckReturnValue(new File((String)this.mInsertedPic.get(str)));
    }
  }

  public void doCancel()
  {
    DiaryEngine.getInstance().cancel();
  }

  public boolean doUpload()
  {
    boolean bool = true;
    try
    {
      HashMap localHashMap = this.mInsertedPic;
      UploadFile[] arrayOfUploadFile = null;
      int j;
      Iterator localIterator;
      if (localHashMap != null)
      {
        int i = this.mInsertedPic.size();
        arrayOfUploadFile = null;
        if (i > 0)
        {
          arrayOfUploadFile = new UploadFile[this.mInsertedPic.size()];
          Set localSet = this.mInsertedPic.entrySet();
          j = 0;
          localIterator = localSet.iterator();
        }
      }
      while (true)
      {
        if (!localIterator.hasNext())
        {
          DiaryEngine localDiaryEngine = DiaryEngine.getInstance();
          bool = localDiaryEngine.doPostDiary(getContext(), getTitle(), this.mContent, getLocationName(), getLatitude(), getLongitude(), arrayOfUploadFile, this);
          if (!bool)
            break;
          this.mDiaryId = localDiaryEngine.getLastPostDiaryDid();
          deleteDraft();
          NewsModel.getMyHomeModel().setFirstRefresh(true);
          return bool;
        }
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        arrayOfUploadFile[j] = new UploadFile((String)localEntry.getValue(), "upload_image_" + (String)localEntry.getKey(), "image/jpeg");
        j++;
      }
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
      KXLog.e("DiaryUploadTask", "doUpload", localSecurityErrorException);
    }
    return bool;
  }

  public String getContent()
  {
    return this.mContent;
  }

  public String getDiaryId()
  {
    return this.mDiaryId;
  }

  public final HashMap<String, String> getInsertedPic()
  {
    return this.mInsertedPic;
  }

  public void initUploadTask(UpLoadTaskListDBAdapter.TaskParameters paramTaskParameters)
  {
    setTaskType(3);
    super.initUploadTask(paramTaskParameters);
    if (paramTaskParameters == null);
    do
    {
      return;
      setTitle(paramTaskParameters.mData1);
      this.mContent = paramTaskParameters.mData2;
      this.mDiaryId = paramTaskParameters.mData3;
    }
    while (TextUtils.isEmpty(paramTaskParameters.mData4));
    this.mInsertedPic = convertStringToMap(paramTaskParameters.mData4);
  }

  public void setContent(String paramString)
  {
    this.mContent = paramString;
  }

  public void setDiaryId(String paramString)
  {
    this.mDiaryId = paramString;
  }

  public void setInsertedPic(String paramString)
  {
    this.mInsertedPic = convertStringToMap(paramString);
  }

  public void setInsertedPic(HashMap<String, String> paramHashMap)
  {
    this.mInsertedPic = paramHashMap;
  }

  public ContentValues toContentValues()
  {
    ContentValues localContentValues = super.toContentValues();
    if (localContentValues == null);
    do
    {
      return localContentValues;
      if (getTitle() != null)
        localContentValues.put("DATA1", getTitle());
      if (this.mContent != null)
        localContentValues.put("DATA2", this.mContent);
      if (this.mDiaryId == null)
        continue;
      localContentValues.put("DATA3", this.mDiaryId);
    }
    while ((this.mInsertedPic == null) || (this.mInsertedPic.size() <= 0));
    localContentValues.put("DATA4", convertPicMapToString(this.mInsertedPic));
    return localContentValues;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.DiaryUploadTask
 * JD-Core Version:    0.6.0
 */