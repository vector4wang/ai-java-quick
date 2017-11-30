package com.quick.text.Test4Excel;

import com.quick.text.Test4Excel.util.ExcelCell;

import java.util.Date;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/10/20
 * Time: 17:50
 * Description:
 */
public class AnonymousModel {
    @ExcelCell(index = 0)
    private String talentId;
    @ExcelCell(index = 1)
    private String id;
    @ExcelCell(index = 2)
    private String realName;
    @ExcelCell(index = 3)
    private Date birthday;
    @ExcelCell(index = 4)
    private Double gender;
    @ExcelCell(index = 5)
    private Double huKou;
    @ExcelCell(index = 6)
    private Double degree;
    @ExcelCell(index = 7)
    private String schoolName;
    @ExcelCell(index = 8)
    private String majorName;
    @ExcelCell(index = 9)
    private Date eduStartDate;
    @ExcelCell(index = 10)
    private Date endDate;
    @ExcelCell(index = 11)
    private String companyName;
    @ExcelCell(index = 12)
    private String title;
    @ExcelCell(index = 13)
    private Date workStartDate;

    public String getTalentId() {
        return talentId;
    }

    public void setTalentId(String talentId) {
        this.talentId = talentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public double getGender() {
        return gender;
    }

    public void setGender(double gender) {
        this.gender = gender;
    }

    public double getHuKou() {
        return huKou;
    }

    public void setHuKou(double huKou) {
        this.huKou = huKou;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Date getEduStartDate() {
        return eduStartDate;
    }

    public void setEduStartDate(Date eduStartDate) {
        this.eduStartDate = eduStartDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(Date workStartDate) {
        this.workStartDate = workStartDate;
    }

    @Override
    public String toString() {
        return "AnonymousModel{" +
                "talentId='" + talentId + '\'' +
                ", id='" + id + '\'' +
                ", realName='" + realName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", huKou='" + huKou + '\'' +
                ", degree='" + degree + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", majorName='" + majorName + '\'' +
                ", eduStartDate='" + eduStartDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", companyName='" + companyName + '\'' +
                ", title='" + title + '\'' +
                ", workStartDate='" + workStartDate + '\'' +
                '}';
    }
}
