/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Config.JPAUtil;
import Models.User;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hp
 */
public class UserDAO {
  public List<User> ShowAllUsers() {
        EntityManager em = null;
        try{
        em=JPAUtil.getEntityManager();
        return em.createQuery("SELECT U FROM User U", User.class).getResultList();
             }finally{
                  em.close();
        }
    }
    //------------------------------------------------------------------------------
     
     public boolean AddUser(User U) {
        EntityManager em = null;
        try {
            em=JPAUtil.getEntityManager();
            em.getTransaction().begin();
            em.persist(U);
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

    public boolean UpdateUser(User U) {
         EntityManager em=null;
     try{
      em=JPAUtil.getEntityManager();
      em.getTransaction().begin();
      em.merge(U);
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
    public Boolean DeleteUser(int userId) {
        EntityManager em=null;
     try{
      em=JPAUtil.getEntityManager();
      em.getTransaction().begin();
        User manageUser = em.find(User.class,userId );
        if (manageUser != null) {
            em.remove(manageUser);
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
    //---------------------------------------------------------------------------------
    public User searchByUsername(String username) {
        EntityManager em = null;
        User user = null;
        try {
            em=JPAUtil.getEntityManager();
            user = em.createQuery("SELECT U FROM User U WHERE U.username = :username", User.class)
                     .setParameter("username", username)
                     .getSingleResult();
        } catch (Exception e) {
        }
        em.close();
        return user;
    }
    //------------------------------------------------------------------
     public User checkPassandUsername(String username, String password) {
          EntityManager em = null;
          User user = null;
    try {
        em = JPAUtil.getEntityManager();
        List<User> result = em.createQuery(
            "SELECT U FROM User U WHERE U.username = :username AND U.password = :password", User.class)
            .setParameter("username", username)
            .setParameter("password", password)
            .getResultList();
     if (!result.isEmpty()) {
            user = result.get(0);
        }
    } catch (Exception e) {
  e.printStackTrace();
    return null;
    } finally {
        if (em != null) em.close();
    }
    return user;
}
     //---------------------------------------------------------------------
     
     public static Long NumOfCoordinators() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return (Long) em.createQuery("SELECT COUNT(u) FROM User u WHERE u.role = 'Coordinator'").getSingleResult();
        } finally {
            em.close();
        }
}
     //--------------------------------------
     public boolean checkPassword(int userId, String currentPass) {
    EntityManager em = null;
    try {
        em = JPAUtil.getEntityManager();
        User user = em.find(User.class, userId);
        return user != null && user.getPassword().equals(currentPass);
    } finally {
        if (em != null) em.close();
    }
}
     //--------------------------------------------------
     public boolean newPassword(int userId, String newPass) {
    EntityManager em = null;
    try {
        em = JPAUtil.getEntityManager();
        User user = em.find(User.class, userId);
        if (user != null) {
            em.getTransaction().begin();
            user.setPassword(newPass);
            em.merge(user);
            em.getTransaction().commit();
            return true;
        }
        return false;
    } catch (Exception e) {
        return false;
    } finally {
        if (em != null) em.close();
    }
}
     //----------------------------------
     public User getUserById(int userId) {
    EntityManager em = null;
    try {
        em = JPAUtil.getEntityManager();
        return em.find(User.class, userId); // يرجع المستخدم مباشرة من قاعدة البيانات
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        if (em != null) {
            em.close();
        }
    }
}


}     

