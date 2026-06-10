/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "family")
public class Family {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "family_id")
     private int familyId;
     
    @Column(name = "household_name")
    private String householdName;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "family_size")
    private int familySize;
    
    @Column(name = "national_id")  
    private String nationalId;
    
   @Column(name = "vulnerability_level")
    private String vulnerabilityLevel;
    
    @Column(name = "registration_date")
    private  Date registrationDate;
    
    @Column(name = "last_aid_date")
    private Date lastAidDate;

    public Family() {
    }
    
    

    public Family(int familyId, String householdName, String phone, String location, int familySize, String nationalId, String vulnerabilityLevel, Date registrationDate,Date lastAidDate) {
        this.familyId = familyId;
        this.householdName = householdName;
        this.phone = phone;
        this.location = location;
        this.familySize = familySize;
        this.nationalId = nationalId;
        this.vulnerabilityLevel = vulnerabilityLevel;
        this.registrationDate = registrationDate;
        this.lastAidDate = lastAidDate;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFamilySize() {
        return familySize;
    }

    public void setFamilySize(int familySize) {
        this.familySize = familySize;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getVulnerabilityLevel() {
        return vulnerabilityLevel;
    }

    public void setVulnerabilityLevel(String vulnerabilityLevel) {
        this.vulnerabilityLevel = vulnerabilityLevel;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastAidDate() {
        return lastAidDate;
    }

    public void setLastAidDate(Date lastAidDate) {
        this.lastAidDate = lastAidDate;
    }

   /* @Override
    public String toString() {
        return "Family{" + "familyId=" + familyId + ", householdName=" + householdName +
                ", phone=" + phone + ", location=" + location + ", familySize=" + familySize +
                ", nationalId=" + nationalId + ", vulnerabilityLevel=" + vulnerabilityLevel +
                ", registrationDate=" + registrationDate + ", lastAidDate=" + lastAidDate + '}';
    }
    */

    @Override
    public String toString() {
        return String.valueOf(familyId);
   
    }
    
    }
    
    
    

