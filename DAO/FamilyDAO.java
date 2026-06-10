/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Config.JPAUtil;
import Models.Family;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hp
 */
public class FamilyDAO {
  public List<Family> ShowAllFamilies() {
        EntityManager em = null;
        try{
        em=JPAUtil.getEntityManager();
        return em.createQuery("SELECT f FROM Family f", Family.class).getResultList();
             }finally{
                  em.close();
        }
    }
    //------------------------------------------------------------------------------
     
     public boolean AddFamily(Family F) {
        EntityManager em = null;
        try {
            em=JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(F);
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

    public boolean UpdateFamily(Family F) {
         EntityManager em=null;
     try{
      em=JPAUtil.getEntityManager();
      em.getTransaction().begin();
      em.merge(F);
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
    public Boolean DeleteFamily(int familyId) {
        EntityManager em=null;
     try{
      em=JPAUtil.getEntityManager();
      em.getTransaction().begin();
        Family family = em.find(Family.class, familyId);
            if (family != null) {
                em.remove(family);
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
    //------------------------------------------------------------
    public Family SearchByNationalId(String nationalId) {
        EntityManager em = null;
        Family family = null;
        try {
            em=JPAUtil.getEntityManager();

            family = em.createQuery("SELECT F FROM Family F WHERE F.nationalId = :n_id", Family.class)
                       .setParameter("n_id", nationalId)
                       .getSingleResult();
        } catch (Exception e) {
          
        }
        em.close();
        return family;
    }
    //---------------------------------------------------
     public static Long NumOfFamilies() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return (Long) em.createQuery("SELECT COUNT(f) FROM Family f").getSingleResult();
        } finally {
            em.close();
        }
    }
     
     //---------------------------------------------------------
     
    public LocalDate getRegistrationDate(int familyId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT f.registrationDate FROM Family f WHERE f.familyId = :id", LocalDate.class)
                .setParameter("id", familyId)
                .getSingleResult();
        } finally {
            em.close();
        }
    }
    //-----------------------------------------

    public LocalDate getLastAidDate(int familyId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                "SELECT f.lastAidDate FROM Family f WHERE f.familyId = :id", LocalDate.class)
                .setParameter("id", familyId)
                .getSingleResult();
        } finally {
            em.close();
        }
       
    }
    
    //---------------------------------------------
    public Family getFamilyById(int familyId) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        return em.find(Family.class, familyId);
    } finally {
        em.close();
    }
}
//------------------------------------------------------------------------------
        public List<Family> getFamiliesByVulnerability(String Vulnerability) {
            EntityManager em = JPAUtil.getEntityManager();
    try {
        return em.createQuery("SELECT f FROM Family f WHERE f.vulnerabilityLevel = :Vulnerability ORDER BY f.vulnerabilityLevel DESC", Family.class)
                 .setParameter("Vulnerability", Vulnerability)
                 .getResultList();
    } finally {
        em.close();
    }
}
        //--------------------------------------
       public List<Family> getUnservedFamilies() {
    EntityManager em = null;
    try {
        em = JPAUtil.getEntityManager();
        return em.createQuery(
            "SELECT f FROM Family f WHERE f.familyId NOT IN " +
            "(SELECT DISTINCT d.family.familyId FROM AidDistribution d) " +
            "ORDER BY f.registrationDate ASC",
            Family.class)
            .getResultList();
    } finally {
        if (em != null) em.close();
    }
}
        //------------------------------------------------
    public LocalDate findRegistrationDate(int familyId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Family fam = em.find(Family.class, familyId);
            if (fam != null && fam.getRegistrationDate() != null) {
                return fam.getRegistrationDate().toLocalDate();
            }
            return null;
        } finally {
            em.close();
        }
    }
    ///--------------------------------------------------

    public LocalDate findLastAidDate(int familyId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Family fam = em.find(Family.class, familyId);
            if (fam != null && fam.getLastAidDate() != null) {
                return fam.getLastAidDate().toLocalDate();
            }
            return null;
        } finally {
            em.close();
        }
    }

        

    

}

     

