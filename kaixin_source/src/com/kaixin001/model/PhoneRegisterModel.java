package com.kaixin001.model;

public class PhoneRegisterModel
{
  public static final String ERROR_PHONE_NUMBER = "0";
  public static final String ERROR_REGISTER = "4";
  public static final String ERROR_SEND_CODE = "2";
  public static final String ERROR_USER_EXISTS = "1";
  public static final String ERROR_VERIY_CODE = "3";
  private static PhoneRegisterModel instance;
  private String code;
  private String error;
  private RegisterInfo info;
  private String ret;
  private String uid;

  public static PhoneRegisterModel getInstance()
  {
    if (instance == null)
      instance = new PhoneRegisterModel();
    return instance;
  }

  public void clear()
  {
    this.ret = null;
    this.code = null;
    this.error = null;
    this.uid = null;
  }

  public String getCode()
  {
    return this.code;
  }

  public String getError()
  {
    return this.error;
  }

  public RegisterInfo getInfo()
  {
    if (this.info == null)
      this.info = new RegisterInfo();
    return this.info;
  }

  public String getRet()
  {
    return this.ret;
  }

  public String getUid()
  {
    return this.uid;
  }

  public void setCode(String paramString)
  {
    this.code = paramString;
  }

  public void setError(String paramString)
  {
    this.error = paramString;
  }

  public void setInstance(PhoneRegisterModel paramPhoneRegisterModel)
  {
    instance = paramPhoneRegisterModel;
  }

  public void setRet(String paramString)
  {
    this.ret = paramString;
  }

  public void setUid(String paramString)
  {
    this.uid = paramString;
  }

  public class RegisterInfo
  {
    private String account;
    private String code;
    private String fromuid;
    private String gender;
    private String ip;
    private String landpage;
    private String nick;
    private String outside;
    private String password;
    private String refer;
    private String regflag;
    private String regfrom;
    private String regpage;
    private String regposition;
    private String source;
    private String src;
    private String uid;
    private String useragent;

    protected RegisterInfo()
    {
    }

    public String getAccount()
    {
      return this.account;
    }

    public String getCode()
    {
      return this.code;
    }

    public String getFromuid()
    {
      return this.fromuid;
    }

    public String getGender()
    {
      return this.gender;
    }

    public String getIp()
    {
      return this.ip;
    }

    public String getLandpage()
    {
      return this.landpage;
    }

    public String getNick()
    {
      return this.nick;
    }

    public String getOutside()
    {
      return this.outside;
    }

    public String getPassword()
    {
      return this.password;
    }

    public String getRefer()
    {
      return this.refer;
    }

    public String getRegflag()
    {
      return this.regflag;
    }

    public String getRegfrom()
    {
      return this.regfrom;
    }

    public String getRegpage()
    {
      return this.regpage;
    }

    public String getRegposition()
    {
      return this.regposition;
    }

    public String getSource()
    {
      return this.source;
    }

    public String getSrc()
    {
      return this.src;
    }

    public String getUid()
    {
      return this.uid;
    }

    public String getUseragent()
    {
      return this.useragent;
    }

    public void setAccount(String paramString)
    {
      this.account = paramString;
    }

    public void setCode(String paramString)
    {
      this.code = paramString;
    }

    public void setFromuid(String paramString)
    {
      this.fromuid = paramString;
    }

    public void setGender(String paramString)
    {
      this.gender = paramString;
    }

    public void setIp(String paramString)
    {
      this.ip = paramString;
    }

    public void setLandpage(String paramString)
    {
      this.landpage = paramString;
    }

    public void setNick(String paramString)
    {
      this.nick = paramString;
    }

    public void setOutside(String paramString)
    {
      this.outside = paramString;
    }

    public void setPassword(String paramString)
    {
      this.password = paramString;
    }

    public void setRefer(String paramString)
    {
      this.refer = paramString;
    }

    public void setRegflag(String paramString)
    {
      this.regflag = paramString;
    }

    public void setRegfrom(String paramString)
    {
      this.regfrom = paramString;
    }

    public void setRegpage(String paramString)
    {
      this.regpage = paramString;
    }

    public void setRegposition(String paramString)
    {
      this.regposition = paramString;
    }

    public void setSource(String paramString)
    {
      this.source = paramString;
    }

    public void setSrc(String paramString)
    {
      this.src = paramString;
    }

    public void setUid(String paramString)
    {
      this.uid = paramString;
    }

    public void setUseragent(String paramString)
    {
      this.useragent = paramString;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.PhoneRegisterModel
 * JD-Core Version:    0.6.0
 */