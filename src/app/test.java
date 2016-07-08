/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import controller.PermisoJpaController;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Permiso;
import model.Usuario;
import util.uError;

/**
 *
 * @author Luis
 */
public class test {

    static EntityManagerFactory emf=Persistence.createEntityManagerFactory("pySerpentin0PU");
    static PermisoJpaController cPermiso= new PermisoJpaController(emf);
    
    private static void permisoModificar(int cod, String desc){
        Permiso p= new Permiso(cod, desc, 'A', new Date(), new Date());
        //p.setUserId(new Usuario());
        try {
            cPermiso.edit(p);   
            System.out.println("Ok");
        } catch (Exception e) {
            new uError().printError(e);
        }
    }
    
    
        private static void permisoCrear(String usuario){
        Permiso p= new Permiso(0, usuario, 'A', new Date(), new Date());
        //p.setUserId(new Usuario(null));
        try {
            cPermiso.create(p);   
            System.out.println("Ok");
        } catch (Exception e) {
            new uError().printError(e);
        }
    }
    
    public static void main(String[] args) {
      //permisoModificar(1,"ACCION");

//        permisoCrear("Cargo");
//        permisoCrear("Cliente");
//        permisoCrear("Cliente Tipo");
//        permisoCrear("CodigoElemento");
//        permisoCrear("Contrato");
//        permisoCrear("ControlActividad");

        permisoModificar(10,"Elemento");
        permisoModificar(11,"Personal");
        permisoModificar(12,"Sector");
        permisoModificar(13,"Tipo Actividad");
        permisoModificar(14,"Tramo");
        // TODO code application logic here
    }
    
}
