/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Luis
 */
public class uError {
    
    public String printError(Exception e){
        String error="ERROR STRING: "+ e.toString();
        error=error+"\nERROR MENSAJE: "+ e.getMessage();
        error=error+"\nERROR LOCALIZED: "+ e.getLocalizedMessage();
         System.out.println(error);
        return error;
    }
    
}
