/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Luis
 */
public class uDate {
    public String now(){
         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        uDate date = new uDate();
       return dateFormat.format(date);
    }
    
    public String dateTable(Date d){
       DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
       return dateFormat.format(d);
    }
    
    public Date tableToDate(String s) {
        Date d= null;
         DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
         try {
            d=dateFormat.parse(s);
        } catch (Exception e) {
             new uError().printError(e);
        }
         return d;
    }
//    public String dateTable(Date d){
//       DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//       return dateFormat.format(d);
//    }
      
    public Date getDate(String str){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;

	try {

		date = formatter.parse(str);
//		System.out.println(date);
//		System.out.println(formatter.format(date));

	} catch (Exception e) {
		e.printStackTrace();
	}
        
        return date;
    }

    
}
