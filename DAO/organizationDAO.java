/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Config.JPAUtil;
import Models.Organization;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hp
 */
public class organizationDAO {
  public List<Organization> ShowAllOrganizations() {
        EntityManager em = null;
        try{
        em=JPAUtil.getEntityManager();
        return em.createQuery("SELECT o FROM Organization o", Organization.class).getResultList();
             }finally{
                  em.close();
        }
    }
    //------------------------------------------------------------------------------
     
     public boolean AddOrganization(Organization O) {
        EntityManager em = null;
        try {
            em=JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(O);
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

    public boolean UpdateOrganization(Organization O) {
         EntityManager em=null;
     try{
      em=JPAUtil.getEntityManager();
      em.getTransaction().begin();
      em.merge(O);
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
    public Boolean DeleteOrganization(int org_id) {
        EntityManager em=null;
     try{
      em=JPAUtil.getEntityManager();
      em.getTransaction().begin();
        Organization manageOrgan = em.find(Organization.class, org_id);
        if (manageOrgan != null) {
            em.remove(manageOrgan);
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
    
    //----------------------------------------------------
    public static Long NumOfOrganizations() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return (Long) em.createQuery("SELECT COUNT(o) FROM Organization o").getSingleResult();
        } finally {
            em.close();
        }
}
    //-------------------------------
    public Organization searchOrgName(String name) {
    EntityManager em = null;
    Organization org = null;
    try {
        em = JPAUtil.getEntityManager();
        org = em.createQuery("SELECT o FROM Organization o WHERE o.name = :name", Organization.class)
                .setParameter("name", name)
                .getSingleResult();
    } catch (Exception e) {
    } finally {
        if (em != null)
            em.close();
    }
    return org;
}
}
