package com.fdays.tsms.transaction._entity;



/**
 * Platform generated by hbm2java
 */


public class _Platform 

  extends org.apache.struts.action.ActionForm implements Cloneable
 {
	private static final long serialVersionUID = 1L;

    // Fields    

     protected long id;
     protected String name;
     protected Long type;
     protected Long status;
     protected java.util.Set platLoginAccounts = new java.util.HashSet(0);
     protected java.util.Set platComAccounts = new java.util.HashSet(0);

     // Constructors
   
    // Property accessors


    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    


    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    


    public Long getType() {
        return this.type;
    }
    
    public void setType(Long type) {
        this.type = type;
    }
    


    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    


    public java.util.Set getPlatLoginAccounts() {
        return this.platLoginAccounts;
    }
    
    public void setPlatLoginAccounts(java.util.Set platLoginAccounts) {
        this.platLoginAccounts = platLoginAccounts;
    }
    


    public java.util.Set getPlatComAccounts() {
        return this.platComAccounts;
    }
    
    public void setPlatComAccounts(java.util.Set platComAccounts) {
        this.platComAccounts = platComAccounts;
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


