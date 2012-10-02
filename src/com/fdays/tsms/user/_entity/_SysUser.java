package com.fdays.tsms.user._entity;



/**
 * SysUser generated by hbm2java
 */


public class _SysUser 

  extends org.apache.struts.action.ActionForm implements Cloneable
 {
	private static final long serialVersionUID = 1L;

    // Fields    

     protected long userId;
     protected String userName;
     protected String userNo;
     protected String userPassword;
     protected Long userStatus;
     protected Long userType;
     protected String userEmail;
     protected java.util.Set sysLogs = new java.util.HashSet(0);
     protected java.util.Set ticketLogs = new java.util.HashSet(0);
     protected java.util.Set statements = new java.util.HashSet(0);

     // Constructors
   
    // Property accessors


    public long getUserId() {
        return this.userId;
    }
    
    public void setUserId(long userId) {
        this.userId = userId;
    }
    


    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    


    public String getUserNo() {
        return this.userNo;
    }
    
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
    


    public String getUserPassword() {
        return this.userPassword;
    }
    
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    


    public Long getUserStatus() {
        return this.userStatus;
    }
    
    public void setUserStatus(Long userStatus) {
        this.userStatus = userStatus;
    }
    


    public Long getUserType() {
        return this.userType;
    }
    
    public void setUserType(Long userType) {
        this.userType = userType;
    }
    


    public String getUserEmail() {
        return this.userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    


    public java.util.Set getSysLogs() {
        return this.sysLogs;
    }
    
    public void setSysLogs(java.util.Set sysLogs) {
        this.sysLogs = sysLogs;
    }
    


    public java.util.Set getTicketLogs() {
        return this.ticketLogs;
    }
    
    public void setTicketLogs(java.util.Set ticketLogs) {
        this.ticketLogs = ticketLogs;
    }
    


    public java.util.Set getStatements() {
        return this.statements;
    }
    
    public void setStatements(java.util.Set statements) {
        this.statements = statements;
    }
    




  // The following is extra code specified in the hbm.xml files

  public Object clone()
  {
    Object o = null;
    try
    {
      o = super.clone();
    }
    catch (CloneNotSupportedException e)
    {
      e.printStackTrace();
    }
    return o;
}

private String thisAction="";
 public String getThisAction()
 {
     return thisAction;
 }


public void setThisAction(String thisAction)
 {
     this.thisAction=thisAction;
 }

private int index=0;
 public int getIndex()
 {
     return index;
 }


public void setIndex(int index)
 {
     this.index=index;
 }
 





  // end of extra code specified in the hbm.xml files


}


