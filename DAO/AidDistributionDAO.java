/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Config.JPAUtil;
import Models.AidDistribution;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hp
 */
public class AidDistributionDAO {
 public List<AidDistribution> ShowAidDistributions() {
        EntityManager em = null;
        try{
        em=JPAUtil.getEntityManager();
        return em.createQuery("SELECT A FROM AidDistribution A", AidDistribution.class).getResultList();
             }finally{
             em.close();
        }
    }
    //------------------------------------------------------------------------------
     
     public boolean AddAids(AidDistribution A) {
        EntityManager em = null;
        try {
            em=JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(A);
            em.getTransaction().commit();
             return true;
            }catch(Exception c){
             return false;
            }
            finally{
           em.close();
     }
       
    }
//----------------------------------------------------------

    public boolean UpdateAids(AidDistribution A) {
         EntityManager em=null;
     try{
      em=JPAUtil.getEntityManager();
      em.getTransaction().begin();
      em.merge(A);
      em.getTransaction().commit();
         return true;
     }catch(Exception c){
         return false;
     }
     finally{
         em.close();
     } 
    }
//---------------------------------------------------------------------------------
    public Boolean DeleteAids(int distribution_id) {
        EntityManager em=null;
     try{
      em=JPAUtil.getEntityManager();
      em.getTransaction().begin();
        AidDistribution manageAid = em.find(AidDistribution.class,distribution_id);
        if (manageAid != null) {
            em.remove(manageAid);
        }
        em.getTransaction().commit();
        return true;
    } catch (Exception c) {
        return false;
    } finally {
        if (em != null) {
            em.close();
        }
    }
     
    }
    
    //--------------------------------------------------------------------------------
    public boolean duplicationChecking(int familyId, Date date) {
    EntityManager em = JPAUtil.getEntityManager();
    Long count = em.createQuery(
        "SELECT COUNT(A) FROM AidDistribution A " +
        "WHERE A.family.familyId = :f_id AND A.distributionDate = :D", Long.class)
        .setParameter("f_id", familyId)   
        .setParameter("D", date)         
        .getSingleResult();
    em.close();
    return count > 0;
}
    //-----------------------------------
    public static Long NumOfServedFamilies() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return (Long) em.createQuery("SELECT COUNT(DISTINCT d.family) FROM AidDistribution d").getSingleResult();
        } finally {
            em.close();
        }
    }
//---------------------------------------------------
    public static Long NumOfUnservedFamilies() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
        return (long) em.createQuery(
            "SELECT COUNT(f) FROM Family f " +
            "WHERE f.familyId NOT IN (SELECT DISTINCT d.family.familyId FROM AidDistribution d)"
       ).getSingleResult();
    } finally {
        em.close();
    }
    }
    //---------------------------
    public List<AidDistribution> searchByOrganization(int orgId) {
    EntityManager em = null;
    try {
        em = JPAUtil.getEntityManager();
        return em.createQuery(
            "SELECT a FROM AidDistribution a WHERE a.organization.orgId = :orgId", AidDistribution.class)
            .setParameter("orgId", orgId)
            .getResultList();
    } finally {
        if (em != null) em.close();
    }
    
}
    //-------------------------------------
public int NumOfServedFamiliesByOrg(int orgId) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        Long count = em.createQuery(
            "SELECT COUNT(DISTINCT d.family.familyId) " +
            "FROM AidDistribution d WHERE d.organization.orgId = :orgId", Long.class)
            .setParameter("orgId", orgId)
            .getSingleResult();
        return count.intValue();
    } finally {
        em.close();
    }
}
//------------------------------------------------------------------------------
public AidDistribution getLastDistributionForFamily(int familyId) {
    EntityManager em = null;
    try {
        em = JPAUtil.getEntityManager();
        List<AidDistribution> results = em.createQuery(
            "SELECT a FROM AidDistribution a WHERE a.family.familyId = :fid " +
            "ORDER BY a.distributionDate DESC", AidDistribution.class)
            .setParameter("fid", familyId)
            .setMaxResults(1)
            .getResultList();
        
        return results.isEmpty() ? null : results.get(0);
        
    } finally {
        if (em != null) em.close();
    }
}

// حفظ توزيع جديد
public boolean save(AidDistribution distribution) {
    EntityManager em = null;
    try {
        em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(distribution);
        em.getTransaction().commit();
        return true;
    } catch (Exception e) {
        return false;
    } finally {
        if (em != null) em.close();
    }
}

}
 



   

