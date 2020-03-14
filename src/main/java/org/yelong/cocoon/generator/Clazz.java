/**
 * 
 */
package org.yelong.cocoon.generator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Clazz
{
  private Class clazz;
  private String className;
  private String classSimpleName;
  private String lowerCasePrefixClassName;
  private String primaryKey;
  private List<Field> classFields = new ArrayList();
  
  public String getClassName()
  {
    return this.className;
  }
  
  public void setClassName(String className)
  {
    this.className = className;
  }
  
  public String getClassSimpleName()
  {
    return this.classSimpleName;
  }
  
  public void setClassSimpleName(String classSimpleName)
  {
    this.classSimpleName = classSimpleName;
  }
  
  public String getLowerCasePrefixClassName()
  {
    return this.lowerCasePrefixClassName;
  }
  
  public void setLowerCasePrefixClassName(String lowerCasePrefixClassName)
  {
    this.lowerCasePrefixClassName = lowerCasePrefixClassName;
  }
  
  public List<Field> getClassFields()
  {
    return this.classFields;
  }
  
  public void addClassField(Field field)
  {
    this.classFields.add(field);
  }
  
  public void setClazz(Class clazz)
  {
    this.clazz = clazz;
  }
  
  public Class getClazz()
  {
    return this.clazz;
  }
  
  public void setPrimaryKey(String primaryKey)
  {
    this.primaryKey = primaryKey;
  }
  
  public String getPrimaryKey()
  {
    return this.primaryKey;
  }
}
