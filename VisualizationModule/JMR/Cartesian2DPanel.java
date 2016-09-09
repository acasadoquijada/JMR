/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import javax.vecmath.Vector3d;

/**
 *
 * @author alejandro
 */
public class Cartesian2DPanel extends Abstract3DPanel {

    /**
     * Creates new form Cartesian2DPanel
     */
    
    protected double x;
    protected double y;
    
    protected double value;
    
    protected Vector3d v;
    
    public Cartesian2DPanel() {

        super();
        planeActive = true;
        v = new Vector3d();
        
        value = 75;
        
    }

    
    @Override
    protected void createScene() {
                
        drawImage(getResultMetada(0), v);
        drawPosition(v, 0);
        
        for(int i = 1; i < results.size(); i++){
            
            calculatePosition(x,y,i);
            
            drawImage(getResultMetada(i), v);
            drawPosition(v, i);
        }
        
    }

    @Override
    protected void sceneControl() {
  
        keyControl();
        
        
    }
    
    protected void calculatePosition(double x, double y,int index){
            x = getVector(index).coordinate(0) * value;
            y = getVector(index).coordinate(1) * value;
            
            v.x = x;
            v.y = y;
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


}
