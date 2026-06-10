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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "aid_distribution")
public class AidDistribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distribution_id")
    private int distributionId;
    
    //private int familyId;
    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;
    
    
    //private int orgId;
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    
    //private int distributedBy;
     @ManyToOne
    @JoinColumn(name = "distributed_by")
    private User distributedBy;
     
    @Column(name = "distribution_date")
    private Date distributionDate;

    public AidDistribution() {
    }

    public AidDistribution(int distributionId, Family family, Organization organization, User distributedBy, Date distributionDate) {
        this.distributionId = distributionId;
        this.family = family;
        this.organization = organization;
        this.distributedBy = distributedBy;
        this.distributionDate = distributionDate;
    }
   
    public int getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(int distributionId) {
        this.distributionId = distributionId;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public User getDistributedBy() {
        return distributedBy;
    }

    public void setDistributedBy(User distributedBy) {
        this.distributedBy = distributedBy;
    }

    public Date getDistributionDate() {
        return distributionDate;
    }

    public void setDistributionDate(Date distributionDate) {
        this.distributionDate = distributionDate;
    }

   /* @Override
    public String toString() {
      //  return "AidDistribution{" + "distributionId=" + distributionId + ", family=" + family + ", organization=" + organization + ", distributedBy=" + distributedBy + ", distributionDate=" + distributionDate + '}';
    return "AidDistribution{" +
           "distributionId=" + distributionId +
           ", familyId=" + (family != null ? family.getFamilyId() : "null") +
           ", orgId=" + (organization != null ? organization.getOrgId() : "null") +
           ", distributedBy=" + (distributedBy != null ? distributedBy.getUserId() : "null") +
           ", distributionDate=" + distributionDate +
           '}';*/

    @Override
    public String toString() {
        return "AidDistribution{" + "organization=" + organization + '}';
    }
    
}
   
  
    
