package alibaba.po;

import java.io.Serializable;
import java.util.Date;

import alibaba.po.e.BloodType;
import alibaba.po.e.EducatType;
import alibaba.po.e.IdentityType;
import alibaba.po.e.IncomeType;
import alibaba.po.e.ReligionType;
import alibaba.po.e.Sex;

/**
 * 会员信息
 * 
 * @author toc
 *
 */
public class Member implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;				// 会员ID
	
	private String alibabaId;		// 阿里巴巴会员ID
	
	/* 基本信息 */
	private String name;		// 姓名
	
	private Sex sex;			// 性别
	
	private IdentityType identity;	// 会员身份
	
	private String email;		// 业务联系邮箱
	
	private String phone;		// 业务联系手机
	
	private String telephone;	// 固定电话(国家代码guodai + 区号quhao + 电话号码telephone)
	
	private String fax;			// 传真(国家代码guodai + 区号quhao + 传真号码fax)
	
	private String liveProvince;// 省份
	
	private String liveCity;	// 城市
	
	private String address;		// 联系地址
	
	private String pcode;		// 邮编

	/* 公司信息 */
	private String position;		// 职位
	
	private String companyName;		// 公司名称
	
	private String companyUrlInAlibaba;		// 公司URL
	
	private Integer creditYear;			// 城信通年份

	// 个人头像
	private String headImageUrl;	// 头像信息URL

	/* 个人信息 */
	private Date birthday;	// 生日
	
	private BloodType bloodType;	// 血型
	
	private String hometown;	// 籍贯
	
	private IncomeType income;		// 收入水平
	
	private EducatType educat;		// 学历
	
	private ReligionType religion;	// 宗教信仰
	
	private String profile;		// 个人主页
	
	private String interests;	// 兴趣爱好
	
	private String selfIntr;	// 自我介绍
	
	/* 账户信息 */
	private String memberAcc;		// 会员账号
	
	private Date registTime;		// 注册时间
	
	private Date lastLogin;			// 最近登录
	
	private Date createTime;		// 创建时间
	
	private Date updateTime;		// 更新时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public IdentityType getIdentity() {
		return identity;
	}

	public void setIdentity(IdentityType identity) {
		this.identity = identity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLiveProvince() {
		return liveProvince;
	}

	public void setLiveProvince(String liveProvince) {
		this.liveProvince = liveProvince;
	}

	public String getLiveCity() {
		return liveCity;
	}

	public void setLiveCity(String liveCity) {
		this.liveCity = liveCity;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyUrlInAlibaba() {
		return companyUrlInAlibaba;
	}

	public void setCompanyUrlInAlibaba(String companyUrlInAlibaba) {
		this.companyUrlInAlibaba = companyUrlInAlibaba;
	}

	public Integer getCreditYear() {
		return creditYear;
	}

	public void setCreditYear(Integer creditYear) {
		this.creditYear = creditYear;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public IncomeType getIncome() {
		return income;
	}

	public void setIncome(IncomeType income) {
		this.income = income;
	}

	public EducatType getEducat() {
		return educat;
	}

	public void setEducat(EducatType educat) {
		this.educat = educat;
	}

	public ReligionType getReligion() {
		return religion;
	}

	public void setReligion(ReligionType religion) {
		this.religion = religion;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getSelfIntr() {
		return selfIntr;
	}

	public void setSelfIntr(String selfIntr) {
		this.selfIntr = selfIntr;
	}

	public String getMemberAcc() {
		return memberAcc;
	}

	public void setMemberAcc(String memberAcc) {
		this.memberAcc = memberAcc;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getAlibabaId() {
		return alibabaId;
	}

	public void setAlibabaId(String alibabaId) {
		this.alibabaId = alibabaId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
