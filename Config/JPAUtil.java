/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author hp
 */
public class JPAUtil {
      private static EntityManagerFactory emf;
    private JPAUtil(){}
    
  private static EntityManagerFactory getEMF() {
    if (emf == null) {
        emf = Persistence.createEntityManagerFactory("GHADSPU");
    }
    return emf;
}
    
    public static EntityManager getEntityManager(){
        return getEMF().createEntityManager();
    }
    
    public static  void CloseEMF(){
        if(emf != null)
            emf.close();
    }
    
    
   
    } 
    
    

