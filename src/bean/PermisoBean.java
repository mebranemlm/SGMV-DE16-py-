/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import model.Permiso;
import util.uCn;
import util.uError;

/**
 *
 * @author Luis
 */
public class PermisoBean {
    
    uCn cn = new uCn();
    uError er=new uError();
    
    public Permiso create(Permiso obj) throws Exception{
        cn.open();
        Permiso o= new Permiso();
        try {
            cn.em.getTransaction().begin();
            cn.em.persist(obj);
            cn.em.getTransaction().commit();
            o=obj;
        } catch (Exception e) {
            cn.em.getTransaction().rollback();
            er.printError(e);
        }
        
        return o;
    }
    
     public void edit(Permiso obj) throws Exception{
        cn.open();
        try {
            cn.em.getTransaction().begin();
            cn.em.merge(obj);
            cn.em.getTransaction().commit();
        } catch (Exception e) {
            cn.em.getTransaction().rollback();
            er.printError(e);
        }
        

    }
    
    
}
