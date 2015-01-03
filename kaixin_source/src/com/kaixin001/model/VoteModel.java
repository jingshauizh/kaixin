package com.kaixin001.model;

import java.util.ArrayList;

public class VoteModel extends KXModel
{
  private static VoteModel instance = null;
  private ArrayList<Answer> answerlist = new ArrayList();
  private String ctime = null;
  private String endtime = null;
  private String fname = null;
  private String intro = null;
  private String[] limitlist = null;
  private String mVid = null;
  private ArrayList<Result> resultlist = new ArrayList();
  private String summary = null;
  private String title = null;
  private String type = null;
  private String votenum = null;

  public static VoteModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new VoteModel();
      VoteModel localVoteModel = instance;
      return localVoteModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.mVid = null;
    this.type = null;
    this.fname = null;
    this.ctime = null;
    this.endtime = null;
    this.title = null;
    this.intro = null;
    this.votenum = null;
    this.limitlist = null;
    this.summary = null;
    if (this.answerlist != null)
      this.answerlist.clear();
    if (this.resultlist != null)
      this.resultlist.clear();
  }

  public ArrayList<Answer> getAnswerlist()
  {
    return this.answerlist;
  }

  public String getCtime()
  {
    return this.ctime;
  }

  public String getEndtime()
  {
    return this.endtime;
  }

  public String getFname()
  {
    return this.fname;
  }

  public String getIntro()
  {
    return this.intro;
  }

  public String[] getLimitlist()
  {
    return this.limitlist;
  }

  public ArrayList<Result> getResultlist()
  {
    return this.resultlist;
  }

  public String getSummary()
  {
    return this.summary;
  }

  public String getTitle()
  {
    return this.title;
  }

  public String getType()
  {
    return this.type;
  }

  public String getVoteId()
  {
    return this.mVid;
  }

  public String getVotenum()
  {
    return this.votenum;
  }

  public void setAnswerlist(ArrayList<Answer> paramArrayList)
  {
    this.answerlist = paramArrayList;
  }

  public void setCtime(String paramString)
  {
    this.ctime = paramString;
  }

  public void setEndtime(String paramString)
  {
    this.endtime = paramString;
  }

  public void setFname(String paramString)
  {
    this.fname = paramString;
  }

  public void setIntro(String paramString)
  {
    this.intro = paramString;
  }

  public void setLimitlist(String[] paramArrayOfString)
  {
    this.limitlist = paramArrayOfString;
  }

  public void setResultlist(ArrayList<Result> paramArrayList)
  {
    this.resultlist = paramArrayList;
  }

  public void setSummary(String paramString)
  {
    this.summary = paramString;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public void setVoteId(String paramString)
  {
    if (paramString != null)
      this.mVid = paramString;
  }

  public void setVotenum(String paramString)
  {
    this.votenum = paramString;
  }

  public static class Answer
  {
    private String answer = null;
    private String votenum = null;
    private String votepercent = null;

    public String getAnswer()
    {
      return this.answer;
    }

    public String getVotenum()
    {
      return this.votenum;
    }

    public String getVotepercent()
    {
      return this.votepercent;
    }

    public void setAnswer(String paramString)
    {
      this.answer = paramString;
    }

    public void setVotenum(String paramString)
    {
      this.votenum = paramString;
    }

    public void setVotepercent(String paramString)
    {
      this.votepercent = paramString;
    }
  }

  public static class Result
  {
    private String answer = null;
    private String ctime = null;
    private String fname = null;
    private String uid = null;

    public String getAnswer()
    {
      return this.answer;
    }

    public String getCtime()
    {
      return this.ctime;
    }

    public String getFname()
    {
      return this.fname;
    }

    public String getUid()
    {
      return this.uid;
    }

    public void setAnswer(String paramString)
    {
      this.answer = paramString;
    }

    public void setCtime(String paramString)
    {
      this.ctime = paramString;
    }

    public void setFname(String paramString)
    {
      this.fname = paramString;
    }

    public void setUid(String paramString)
    {
      this.uid = paramString;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.VoteModel
 * JD-Core Version:    0.6.0
 */