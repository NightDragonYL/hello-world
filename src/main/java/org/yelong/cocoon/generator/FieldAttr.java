package org.yelong.cocoon.generator;

public class FieldAttr
{
  private String fieldType;
  private String fieldName;
  private String fieldDisplayName;
  private String fieldMapping;
  private boolean hidden = false;
  private boolean ignore = false;
  private boolean editable = true;
  private boolean allowBlank = true;
  private String blankText;
  private String emptyText;
  private int maxLength = -1;
  private String maxLengthText;
  private String inputType = "text";
  private boolean searchable = false;
  private boolean readOnly = false;
  private int minLength = -1;
  private String minLengthText;
  private int maxValue = Integer.MAX_VALUE;
  private int minValue = Integer.MIN_VALUE;
  private boolean isComboBoxLocal;
  private boolean isComboBoxTree;
  private boolean isComboBoxDict;
  private boolean isCheckBoxGroup;
  private boolean isRadioGroup;
  private String dictType;
  private String comboTreeId;
  private String comboTreeUrl;
  private String defaultValue;
  private String defaultText;
  
  public String getFieldName()
  {
    return this.fieldName;
  }
  
  public void setFieldName(String fieldName)
  {
    this.fieldName = fieldName;
  }
  
  public String getFieldDisplayName()
  {
    return this.fieldDisplayName;
  }
  
  public void setFieldDisplayName(String fieldDisplayName)
  {
    this.fieldDisplayName = fieldDisplayName;
  }
  
  public boolean isHidden()
  {
    return this.hidden;
  }
  
  public void setHidden(boolean hidden)
  {
    this.hidden = hidden;
  }
  
  public boolean isIgnore()
  {
    return this.ignore;
  }
  
  public void setIgnore(boolean ignore)
  {
    this.ignore = ignore;
  }
  
  public boolean isEditable()
  {
    return this.editable;
  }
  
  public void setEditable(boolean editable)
  {
    this.editable = editable;
  }
  
  public boolean isAllowBlank()
  {
    return this.allowBlank;
  }
  
  public void setAllowBlank(boolean allowBlank)
  {
    this.allowBlank = allowBlank;
  }
  
  public String getBlankText()
  {
    return this.blankText;
  }
  
  public void setBlankText(String blankText)
  {
    this.blankText = blankText;
  }
  
  public int getMaxLength()
  {
    return this.maxLength;
  }
  
  public void setMaxLength(int maxLength)
  {
    this.maxLength = maxLength;
  }
  
  public String getMaxLengthText()
  {
    return this.maxLengthText;
  }
  
  public void setMaxLengthText(String maxLengthText)
  {
    this.maxLengthText = maxLengthText;
  }
  
  public String getInputType()
  {
    return this.inputType;
  }
  
  public void setInputType(String inputType)
  {
    this.inputType = inputType;
  }
  
  public void setFieldType(String fieldType)
  {
    this.fieldType = fieldType;
  }
  
  public String getFieldType()
  {
    return this.fieldType;
  }
  
  public void setSearchable(boolean searchable)
  {
    this.searchable = searchable;
  }
  
  public boolean isSearchable()
  {
    return this.searchable;
  }
  
  public void setReadOnly(boolean readOnly)
  {
    this.readOnly = readOnly;
  }
  
  public boolean isReadOnly()
  {
    return this.readOnly;
  }
  
  public void setMinLength(int minLength)
  {
    this.minLength = minLength;
  }
  
  public int getMinLength()
  {
    return this.minLength;
  }
  
  public void setMinLengthText(String minLengthText)
  {
    this.minLengthText = minLengthText;
  }
  
  public String getMinLengthText()
  {
    return this.minLengthText;
  }
  
  public void setComboBoxLocal(boolean isComboBoxLocal)
  {
    this.isComboBoxLocal = isComboBoxLocal;
  }
  
  public boolean isComboBoxLocal()
  {
    return this.isComboBoxLocal;
  }
  
  public void setComboBoxTree(boolean isComboBoxTree)
  {
    this.isComboBoxTree = isComboBoxTree;
  }
  
  public boolean isComboBoxTree()
  {
    return this.isComboBoxTree;
  }
  
  public void setCheckBoxGroup(boolean isCheckBoxGroup)
  {
    this.isCheckBoxGroup = isCheckBoxGroup;
  }
  
  public boolean isCheckBoxGroup()
  {
    return this.isCheckBoxGroup;
  }
  
  public void setRadioGroup(boolean isRadioGroup)
  {
    this.isRadioGroup = isRadioGroup;
  }
  
  public boolean isRadioGroup()
  {
    return this.isRadioGroup;
  }
  
  public void setFieldMapping(String fieldMapping)
  {
    this.fieldMapping = fieldMapping;
  }
  
  public String getFieldMapping()
  {
    return this.fieldMapping;
  }
  
  public void setComboBoxDict(boolean isComboBoxDict)
  {
    this.isComboBoxDict = isComboBoxDict;
  }
  
  public boolean isComboBoxDict()
  {
    return this.isComboBoxDict;
  }
  
  public void setDictType(String dictType)
  {
    this.dictType = dictType;
  }
  
  public String getDictType()
  {
    return this.dictType;
  }
  
  public void setMaxValue(int maxValue)
  {
    this.maxValue = maxValue;
  }
  
  public int getMaxValue()
  {
    return this.maxValue;
  }
  
  public void setMinValue(int minValue)
  {
    this.minValue = minValue;
  }
  
  public int getMinValue()
  {
    return this.minValue;
  }
  
  public void setComboTreeId(String comboTreeId)
  {
    this.comboTreeId = comboTreeId;
  }
  
  public String getComboTreeId()
  {
    return this.comboTreeId;
  }
  
  public void setComboTreeUrl(String comboTreeUrl)
  {
    this.comboTreeUrl = comboTreeUrl;
  }
  
  public String getComboTreeUrl()
  {
    return this.comboTreeUrl;
  }
  
  public void setEmptyText(String emptyText)
  {
    this.emptyText = emptyText;
  }
  
  public String getEmptyText()
  {
    return this.emptyText;
  }
  
  public void setDefaultText(String defaultText)
  {
    this.defaultText = defaultText;
  }
  
  public String getDefaultText()
  {
    return this.defaultText;
  }
  
  public void setDefaultValue(String defaultValue)
  {
    this.defaultValue = defaultValue;
  }
  
  public String getDefaultValue()
  {
    return this.defaultValue;
  }
}
