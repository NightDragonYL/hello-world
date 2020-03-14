package org.yelong.cocoon.generator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public final class ClassUtils
{
  private static Map<String, Clazz> CLASS_MAP = new HashMap();
  
  public static Clazz getClazz(String className)
  {
    Clazz clazz = (Clazz)CLASS_MAP.get(className);
    if (clazz == null) {
      clazz = addClazz(className);
    }
    return clazz;
  }
  
  public static Clazz getClazz(Object obj)
  {
    Clazz clazz = null;
    if (obj != null)
    {
      String className = obj.getClass().getName();
      clazz = (Clazz)CLASS_MAP.get(className);
      if (clazz == null) {
        clazz = addClazz(className);
      }
    }
    return clazz;
  }
  
  public static Clazz addClazz(String className)
  {
    Clazz clazz = null;
    if (StringUtils.isNotBlank(className)) {
      try
      {
        Class classType = Class.forName(className);
        
        clazz = new Clazz();
        clazz.setClazz(classType);
        clazz.setClassName(classType.getName());
        clazz.setClassSimpleName(classType.getSimpleName());
        clazz.setLowerCasePrefixClassName(lowerCasePrefixString(clazz.getClassSimpleName()));
        Field[] fields = classType.getDeclaredFields();
        if ((fields != null) && (fields.length > 0))
        {
          Field[] arrayOfField1;
          int j = (arrayOfField1 = fields).length;
          for (int i = 0; i < j; i++)
          {
            Field field = arrayOfField1[i];
            clazz.addClassField(field);
            if (field.getName().equals(clazz.getLowerCasePrefixClassName() + "ID")) {
              clazz.setPrimaryKey(field.getName());
            }
          }
        }
        CLASS_MAP.put(clazz.getClassName(), clazz);
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
        return null;
      }
    }
    return clazz;
  }
  
  public static Clazz addClazz(String className, String[] excludeFields)
  {
    Clazz clazz = null;
    if (StringUtils.isNotBlank(className)) {
      try
      {
        Class classType = Class.forName(className);
        
        clazz = new Clazz();
        clazz.setClazz(classType);
        clazz.setClassName(classType.getName());
        clazz.setClassSimpleName(classType.getSimpleName());
        clazz.setLowerCasePrefixClassName(lowerCasePrefixString(clazz.getClassSimpleName()));
        Field[] fields = classType.getDeclaredFields();
        if ((fields != null) && (fields.length > 0))
        {
          Field[] arrayOfField1;
          int j = (arrayOfField1 = fields).length;
          for (int i = 0; i < j; i++)
          {
            Field field = arrayOfField1[i];
            if (excludeFields != null)
            {
              String[] arrayOfString;
              int m = (arrayOfString = excludeFields).length;
              for (int k = 0; k < m; k++)
              {
                String fieldName = arrayOfString[k];
                if (!field.getName().equals(fieldName)) {
                  clazz.addClassField(field);
                }
              }
            }
          }
        }
        CLASS_MAP.put(clazz.getClassName(), clazz);
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
        return null;
      }
    }
    return clazz;
  }
  
  public static String getPrimaryKey(String className)
  {
    String clientID = "";
    try
    {
      Class classType = Class.forName(className);
      String classSimpleName = classType.getSimpleName();
      
      clientID = lowerCasePrefixString(classSimpleName) + "ID";
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return clientID;
  }
  
  public static Class getFieldType(String className, String fieldName)
  {
    Class fieldClass = null;
    try
    {
      Class classType = Class.forName(className);
      
      fieldClass = getFieldType(classType, fieldName);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (SecurityException e)
    {
      e.printStackTrace();
    }
    return fieldClass;
  }
  
  public static Class getFieldType(Class clazz, String fieldName)
  {
    Class fieldClass = null;
    try
    {
      Field field = clazz.getDeclaredField(fieldName);
      if (field != null) {
        fieldClass = field.getType();
      }
    }
    catch (SecurityException e)
    {
      e.printStackTrace();
    }
    catch (NoSuchFieldException e)
    {
      e.printStackTrace();
    }
    return fieldClass;
  }
  
  private static String lowerCasePrefixString(String str)
  {
    if (StringUtils.isNotBlank(str)) {
      str = str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
    }
    return str;
  }
}