/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

/**
 *
 * @author Luis
 */
public class uGUI {
    
    public void hideTableColumn(TableColumn tc){
        tc.setPreferredWidth(0);
        tc.setMinWidth(0);
        tc.setMaxWidth(0);
    }
    
     public void clearTextFields (Container container){

  for(Component c : container.getComponents()){
   if(c instanceof JTextField){
     JTextField f = (JTextField) c;
     f.setText("");
 } 
  else if (c instanceof Container)
     clearTextFields((Container)c);
}
}
    
}
