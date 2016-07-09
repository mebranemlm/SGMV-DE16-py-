/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import bean.PermisoBean;
import controller.PermisoJpaController;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Permiso;
import model.Usuario;
import util.uError;
import util.uPass;
import view.PrincipalGUI;

/**
 *
 * @author Luis
 */
public class test {

    static EntityManagerFactory emf=Persistence.createEntityManagerFactory("pySerpentin0PU");
    static PermisoJpaController cPermiso= new PermisoJpaController(emf);
    static PermisoBean bPermiso=new PermisoBean();
    static uError er=new uError();
    
    private static void permisoModificar(int cod, String desc){
        Permiso p= new Permiso(cod, desc, 'A', new Date(), new Date());
        //p.setUserId(new Usuario());
        try {
            bPermiso.edit(p);   
            System.out.println("Ok");
        } catch (Exception e) {
            new uError().printError(e);
        }
    }
    
    
        private static void permisoCrear(String str){
        Permiso p= new Permiso(0, str, 'A', new Date(), new Date());
        Permiso op=new Permiso();
            try {
              op=bPermiso.create(p);   
            } catch (Exception e) {
                er.printError(e);
            }
            
            System.out.println(op.getPermId());
                  
      
    }
        
        public static void md5(String str){
            String p=new uPass().MD5(str);
            System.out.println(p);
        }
    
    public static void main(String[] args) {
        //permisoCrear("Permiso3");
         //permisoModificar(15,"Permiso02");
         
         //new JFPrincipalGUI().setVisible(true);
         //md5("admin");
    }
    
}
