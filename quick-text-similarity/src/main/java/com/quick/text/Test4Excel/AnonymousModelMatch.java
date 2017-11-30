package com.quick.text.Test4Excel;

import com.quick.text.Test4Excel.util.ExcelCell;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/11/29
 * Time: 16:09
 * Description:
 */
public class AnonymousModelMatch {
    @ExcelCell(index = 0)
    private String key;
    @ExcelCell(index = 1)
    private String talentId;
    @ExcelCell(index = 2)
    private String id;
    @ExcelCell(index = 3)
    private String realName;
    @ExcelCell(index = 4)
    private String birthday;
    @ExcelCell(index = 5)
    private String gender;
    @ExcelCell(index = 6)
    private String huKou;
    @ExcelCell(index = 7)
    private String degree;
    @ExcelCell(index = 8)
    private String schoolName;
    @ExcelCell(index = 9)
    private String majorName;
    @ExcelCell(index = 10)
    private String eduStartDate;
    @ExcelCell(index = 11)
    private String endDate;
    @ExcelCell(index = 12)
    private String companyName;
    @ExcelCell(index = 13)
    private String title;
    @ExcelCell(index = 14)
    private String workStartDate;
    @ExcelCell(index = 15)
    private Double result;

    @Override
    public String toString() {
        return "AnonymousModelMatch{" +
                "key='" + key + '\'' +
                ", talentId='" + talentId + '\'' +
                ", id='" + id + '\'' +
                ", realName='" + realName + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", huKou=" + huKou +
                ", degree=" + degree +
                ", schoolName='" + schoolName + '\'' +
                ", majorName='" + majorName + '\'' +
                ", eduStartDate=" + eduStartDate +
                ", endDate=" + endDate +
                ", companyName='" + companyName + '\'' +
                ", title='" + title + '\'' +
                ", workStartDate=" + workStartDate +
                ", result=" + result +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHuKou() {
        return huKou;
    }

    public void setHuKou(String huKou) {
        this.huKou = huKou;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
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

    public String getEduStartDate() {
        return eduStartDate;
    }

    public void setEduStartDate(String eduStartDate) {
        this.eduStartDate = eduStartDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    public String getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(String workStartDate) {
        this.workStartDate = workStartDate;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
