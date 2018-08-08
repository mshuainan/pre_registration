package com.elementspeed.mapper.system.entity;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.elementspeed.framework.base.dao.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @ClassName: SysPreRegister
 * @Description: TODO(报名注册POJO)
 * @author masn
 * @date 2018年8月3日 下午10:07:39
 */
@SuppressWarnings("serial")
public class SysPreRegister extends BaseEntity {
	/** 报名单号 */
	private String preOrderNo;
	/** 学生姓名 */
	private String studentName;
	/** 学生性别ps:1男、2：女 */
	private Integer studentGender;
	/** 学生身份证 */
	private String studentIdentity;
	/** 学生出生日期 */
	private Date birthDate;
	/** 学生父亲姓名 */
	private String fatherName;
	/** 父亲联系方式 */
	private String fatherContactInfo;
	/** 父亲身份证号 */
	private String fatherIdentity;
	/** 母亲姓名 */
	private String matherName;
	/** 母亲联系方式 */
	private String matherContactInfo;
	/** 母亲身份证 */
	private String matherIdentity;
	/** 居住地ID(户口本地段) */
	private String domicileId;
	/** 居住地编码 */
	private String domicileCode;
	/** 居住地名称 */
	private String domicileName;
	/** 居住地详细地址 */
	private String domicileAddress;
	/** 房产所有人 */
	private Integer propertyOwner;
	/** 房产证签发日期 */
	private Date propertySignDate;
	/** 房产所有人姓名 */
	private String propertyOwnerName;
	/** 房产证编号 */
	private String propertyCode;
	/** 学校编码 */
	private String schoolCode;
	/** 学校名称 */
	private String schoolName;
	/** 区域编码 */
	private String districtCode;
	/** 区域名称 */
	private String districtName;
	/** 学生头像 */
	private MultipartFile studentFace;
	/** 学生头像Url */
	private String studentFaceUrl;	
	/** 学生头像名称 */
	private String studentFaceName;
	/** 户口本或房屋合同附件 */
	private MultipartFile propertyFile;	
	/** 户口本或房屋合同附件Url */
	private String propertyFileUrl;	
	/** 户口本或房屋合同附件name */
	private String propertyFileName;
	/** 1:户口本地段,2:购置商品房 */
	private Integer domicileType;
	/******************** 非持久化字段 ********************/
	/** 居住地ID(户口本地段) */
	private String domicileId1;
	/** 居住地ID(商品房) */
	private String domicileId2;
	private String studentGenderStr;
	private String propertyOwnerStr;
	/** 1:户口本地段,2:购置商品房 */
	private String domicileTypeStr;

	public String getPreOrderNo() {
		return preOrderNo;
	}

	public void setPreOrderNo(String preOrderNo) {
		this.preOrderNo = preOrderNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Integer getStudentGender() {
		return studentGender;
	}

	public void setStudentGender(Integer studentGender) {
		this.studentGender = studentGender;
	}

	public String getStudentIdentity() {
		return studentIdentity;
	}

	public void setStudentIdentity(String studentIdentity) {
		this.studentIdentity = studentIdentity;
	}

	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFatherContactInfo() {
		return fatherContactInfo;
	}

	public void setFatherContactInfo(String fatherContactInfo) {
		this.fatherContactInfo = fatherContactInfo;
	}

	public String getFatherIdentity() {
		return fatherIdentity;
	}

	public void setFatherIdentity(String fatherIdentity) {
		this.fatherIdentity = fatherIdentity;
	}

	public String getMatherName() {
		return matherName;
	}

	public void setMatherName(String matherName) {
		this.matherName = matherName;
	}

	public String getMatherContactInfo() {
		return matherContactInfo;
	}

	public void setMatherContactInfo(String matherContactInfo) {
		this.matherContactInfo = matherContactInfo;
	}

	public String getMatherIdentity() {
		return matherIdentity;
	}

	public void setMatherIdentity(String matherIdentity) {
		this.matherIdentity = matherIdentity;
	}

	public String getDomicileId() {
		return domicileId;
	}

	public void setDomicileId(String domicileId) {
		this.domicileId = domicileId;
	}
	
	public String getDomicileId1() {
		return domicileId1;
	}

	public void setDomicileId1(String domicileId1) {
		this.domicileId1 = domicileId1;
	}

	public String getDomicileId2() {
		return domicileId2;
	}

	public void setDomicileId2(String domicileId2) {
		this.domicileId2 = domicileId2;
	}

	public String getDomicileCode() {
		return domicileCode;
	}

	public void setDomicileCode(String domicileCode) {
		this.domicileCode = domicileCode;
	}

	public String getDomicileName() {
		return domicileName;
	}

	public void setDomicileName(String domicileName) {
		this.domicileName = domicileName;
	}

	public String getDomicileAddress() {
		return domicileAddress;
	}

	public void setDomicileAddress(String domicileAddress) {
		this.domicileAddress = domicileAddress;
	}

	public Integer getPropertyOwner() {
		return propertyOwner;
	}

	public void setPropertyOwner(Integer propertyOwner) {
		this.propertyOwner = propertyOwner;
	}

	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	public Date getPropertySignDate() {
		return propertySignDate;
	}

	public void setPropertySignDate(Date propertySignDate) {
		this.propertySignDate = propertySignDate;
	}

	public String getPropertyOwnerName() {
		return propertyOwnerName;
	}

	public void setPropertyOwnerName(String propertyOwnerName) {
		this.propertyOwnerName = propertyOwnerName;
	}

	public String getPropertyCode() {
		return propertyCode;
	}

	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}
	
	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	public MultipartFile getStudentFace() {
		return studentFace;
	}

	public void setStudentFace(MultipartFile studentFace) {
		this.studentFace = studentFace;
	}

	public String getStudentFaceUrl() {
		return studentFaceUrl;
	}

	public void setStudentFaceUrl(String studentFaceUrl) {
		this.studentFaceUrl = studentFaceUrl;
	}

	public String getStudentFaceName() {
		return studentFaceName;
	}

	public void setStudentFaceName(String studentFaceName) {
		this.studentFaceName = studentFaceName;
	}

	public MultipartFile getPropertyFile() {
		return propertyFile;
	}

	public void setPropertyFile(MultipartFile propertyFile) {
		this.propertyFile = propertyFile;
	}

	public String getPropertyFileUrl() {
		return propertyFileUrl;
	}

	public void setPropertyFileUrl(String propertyFileUrl) {
		this.propertyFileUrl = propertyFileUrl;
	}

	public String getPropertyFileName() {
		return propertyFileName;
	}

	public void setPropertyFileName(String propertyFileName) {
		this.propertyFileName = propertyFileName;
	}
	
	public Integer getDomicileType() {
		return domicileType;
	}

	public void setDomicileType(Integer domicileType) {
		this.domicileType = domicileType;
	}

	public String getStudentGenderStr() {
		String studentGenderStr = "未知";
	    if (this.studentGender != null && this.studentGender.intValue() == 1) {
	    	studentGenderStr = "男";
		} else if (this.studentGender != null && this.studentGender.intValue() == 2) {
			studentGenderStr = "女";
		}
		return studentGenderStr;
	}

	public void setStudentGenderStr(String studentGenderStr) {
		this.studentGenderStr = studentGenderStr;
	}

	public String getPropertyOwnerStr() {
		String propertyOwnerStr = "";
	    if (this.propertyOwner != null && this.propertyOwner.intValue() == 1) {
	    	propertyOwnerStr = "父亲";
		} else if (this.propertyOwner != null && this.propertyOwner.intValue() == 2) {
			propertyOwnerStr = "母亲";
		}
		return propertyOwnerStr;
	}

	public void setPropertyOwnerStr(String propertyOwnerStr) {
		this.propertyOwnerStr = propertyOwnerStr;
	}
	
	public String getDomicileTypeStr() {
		String domicileTypeStr = "";
	    if (this.domicileType != null && this.domicileType.intValue() == 1) {
	    	domicileTypeStr = "户口本地段";
		} else if (this.domicileType != null && this.domicileType.intValue() == 2) {
			domicileTypeStr = "购置商品房";
		}
		return domicileTypeStr;
	}

	public void setDomicileTypeStr(String domicileTypeStr) {
		this.domicileTypeStr = domicileTypeStr;
	}

	@Override
	public String toString() {
		return "SysPreRegister [studentName=" + studentName
				+ ", studentGender=" + studentGender + ", studentIdentity="
				+ studentIdentity + ", birthDate=" + birthDate
				+ ", fatherName=" + fatherName + ", fatherContactInfo="
				+ fatherContactInfo + ", fatherIdentity=" + fatherIdentity
				+ ", matherName=" + matherName + ", matherContactInfo="
				+ matherContactInfo + ", matherIdentity=" + matherIdentity
				+ ", domicileId=" + domicileId + ", domicileCode="
				+ domicileCode + ", domicileName=" + domicileName
				+ ", domicileAddress=" + domicileAddress + ", propertyOwner="
				+ propertyOwner + ", propertySignDate=" + propertySignDate
				+ ", propertyOwnerName=" + propertyOwnerName
				+ ", propertyCode=" + propertyCode + "]";
	}
	
}