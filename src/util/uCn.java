/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Luis
 */
public class uCn {
    EntityManagerFactory emf;
    public EntityManager em;
    
    public void open(){
        emf= Persistence.createEntityManagerFactory("pySerpentin0PU");
        em=emf.createEntityManager();
    }
    
}
