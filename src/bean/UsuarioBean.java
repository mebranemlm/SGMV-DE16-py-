/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import model.Usuario;
import util.uCn;
import util.uError;
import util.uPass;

/**
 *
 * @author Luis
 */
public class UsuarioBean {
    uCn cn = new uCn();
    uError er=new uError();
    
    public Usuario validar(Usuario u)throws Exception{
        cn.open();
        Usuario o = new Usuario(0);
        List<Usuario> list=new ArrayList<Usuario>();
        String jpql= "select u from Usuario u where upper(u.userUser)= upper(:user) and u.userPass = :pass";
        Query q=cn.em.createQuery(jpql);
        q.setParameter("user", u.getUserUser());
        q.setParameter("pass", new uPass().MD5(u.getUserPass()));
        
      
        
        list= q.getResultList();
        for(Usuario t :list){
            o=t;break;
        }
        return o;
    }
}
