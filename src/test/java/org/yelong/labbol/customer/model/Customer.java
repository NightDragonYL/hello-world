package org.yelong.labbol.customer.model;

import java.util.Date;


import org.yelong.core.model.annotation.Column;
import org.yelong.core.model.annotation.Table;
import org.yelong.core.model.Model;

/**
 * tb_sm_customer_info 客户基本信息
 * @author 14308
 *
 */
@Table(value="tb_sm_customer_info",alias="customer",desc="客户基本信息")
public class Customer extends Model {
	
	
	@Column(column = "id",maxLength = 32,allowNull = false,jdbcType = "VARCHAR",desc = "主键")
	private String id;
	
	@Column(column = "customerNo",maxLength = 50,allowNull = true,jdbcType = "VARCHAR",desc = "客户编号")
	private String customerNo;
	
	@Column(column = "isFormal",maxLength = 2,allowNull = true,jdbcType = "VARCHAR",desc = "是否正式客户")
	private String isFormal;
	
	@Column(column = "customerClass",maxLength = 2,allowNull = true,jdbcType = "VARCHAR",desc = "客户类别")
	private String customerClass;
	
	@Column(column = "customerSource",maxLength = 2,allowNull = true,jdbcType = "VARCHAR",desc = "客户来源")
	private String customerSource;
	
	@Column(column = "customerName",maxLength = 200,allowNull = true,jdbcType = "VARCHAR",desc = "客户名称")
	private String customerName;
	
	@Column(column = "fullNameE",maxLength = 300,allowNull = true,jdbcType = "VARCHAR",desc = "英文名称")
	private String fullNameE;
	
	@Column(column = "customerLevel",allowNull = true,jdbcType = "INTEGER",desc = "客户级别")
	private Integer customerLevel;
	
	@Column(column = "IDType",maxLength = 2,allowNull = true,jdbcType = "VARCHAR",desc = "证件类型")
	private String IDType;
	
	@Column(column = "IDCard",maxLength = 50,allowNull = true,jdbcType = "VARCHAR",desc = "证件号码")
	private String IDCard;
	
	@Column(column = "companyName",maxLength = 200,allowNull = true,jdbcType = "VARCHAR",desc = "公司名称")
	private String companyName;
	
	@Column(column = "website",maxLength = 200,allowNull = true,jdbcType = "VARCHAR",desc = "公司网址")
	private String website;
	
	@Column(column = "department",maxLength = 50,allowNull = true,jdbcType = "VARCHAR",desc = "部门名称")
	private String department;
	
	@Column(column = "tel",maxLength = 50,allowNull = true,jdbcType = "VARCHAR",desc = "电话号码")
	private String tel;
	
	@Column(column = "email",maxLength = 100,allowNull = true,jdbcType = "VARCHAR",desc = "电子邮箱")
	private String email;
	
	@Column(column = "fax",maxLength = 50,allowNull = true,jdbcType = "VARCHAR",desc = "传真号码")
	private String fax;
	
	@Column(column = "addr",maxLength = 200,allowNull = true,jdbcType = "VARCHAR",desc = "联系地址")
	private String addr;
	
	@Column(column = "postcode",maxLength = 6,allowNull = true,jdbcType = "VARCHAR",desc = "邮政编码")
	private String postcode;
	
	@Column(column = "region",maxLength = 20,allowNull = true,jdbcType = "VARCHAR",desc = "地区")
	private String region;
	
	@Column(column = "city",maxLength = 20,allowNull = true,jdbcType = "VARCHAR",desc = "城市")
	private String city;
	
	@Column(column = "remark",maxLength = 200,allowNull = true,jdbcType = "VARCHAR",desc = "备注")
	private String remark;
	
	@Column(column = "researchLab",maxLength = 200,allowNull = true,jdbcType = "VARCHAR",desc = "所属研究所")
	private String researchLab;
	
	@Column(column = "groupId",maxLength = 32,allowNull = true,jdbcType = "VARCHAR",desc = "所属部门")
	private String groupId;
	
	@Column(column = "labId",maxLength = 32,allowNull = true,jdbcType = "VARCHAR",desc = "所属实验室")
	private String labId;
	
	@Column(column = "tenantId",maxLength = 32,allowNull = true,jdbcType = "VARCHAR",desc = "租户ID")
	private String tenantId;
	
	@Column(column = "customerLogo",allowNull = true,jdbcType = "VARCHAR",desc = "客户头像")
	private String customerLogo;
	
	@Column(column = "creator",maxLength = 32,allowNull = true,jdbcType = "VARCHAR",desc = "创建人")
	private String creator;
	
	@Column(column = "createTime",allowNull = true,jdbcType = "TIMESTAMP",desc = "创建时间")
	private Date createTime;
	
	@Column(column = "updator",maxLength = 32,allowNull = true,jdbcType = "VARCHAR",desc = "修改人")
	private String updator;
	
	@Column(column = "updateTime",allowNull = true,jdbcType = "TIMESTAMP",desc = "修改时间")
	private Date updateTime;
	
	@Column(column = "state",maxLength = 1,allowNull = true,jdbcType = "VARCHAR",desc = "状态")
	private String state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getIsFormal() {
		return isFormal;
	}

	public void setIsFormal(String isFormal) {
		this.isFormal = isFormal;
	}
	public String getCustomerClass() {
		return customerClass;
	}

	public void setCustomerClass(String customerClass) {
		this.customerClass = customerClass;
	}
	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getFullNameE() {
		return fullNameE;
	}

	public void setFullNameE(String fullNameE) {
		this.fullNameE = fullNameE;
	}
	public Integer getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(Integer customerLevel) {
		this.customerLevel = customerLevel;
	}
	public String getIDType() {
		return IDType;
	}

	public void setIDType(String IDType) {
		this.IDType = IDType;
	}
	public String getIDCard() {
		return IDCard;
	}

	public void setIDCard(String IDCard) {
		this.IDCard = IDCard;
	}
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getResearchLab() {
		return researchLab;
	}

	public void setResearchLab(String researchLab) {
		this.researchLab = researchLab;
	}
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getLabId() {
		return labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getCustomerLogo() {
		return customerLogo;
	}

	public void setCustomerLogo(String customerLogo) {
		this.customerLogo = customerLogo;
	}
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
