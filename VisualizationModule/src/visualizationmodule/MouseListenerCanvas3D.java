/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationmodule;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author Alejandro
 */
public class MouseListenerCanvas3D implements MouseListener{

    public void InterracionRaton(){
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("AAA");
    }

    @Override
    public void mousePressed(MouseEvent e) {
                System.out.println("ddd");

    }

    @Override
    public void mouseReleased(MouseEvent e) {
                System.out.println("fff");

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void addMouseWheelListener(MouseWheelListener mouseWheelListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
