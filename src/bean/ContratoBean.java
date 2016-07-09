/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import model.Contrato;
import util.uCn;
import util.uError;

/**
 *
 * @author Luis
 */
public class ContratoBean {
    
    uCn cn = new uCn();
    uError er=new uError();
    
    
    public List<Contrato> readAll()throws Exception{
        cn.open();
        List<Contrato> lista = new ArrayList<>();
        Query q=cn.em.createQuery("select co from Contrato co");
        lista=q.getResultList();
        return lista;
    }
    
}
