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
public class Polar2DPanel extends Abstract3DPanel {

    /**
     * Creates new form Cartesian2DPanel
     */
    
    protected double r;
    protected double theta;
    
    protected final double value;
    
    protected Vector3d v;
    
    public Polar2DPanel() {

        super();
        planeActive = true;
        v = new Vector3d();
        value=10;
        
    }

    
    @Override
    protected void createScene() {
        
        
        drawImage(getResultMetada(0), v);
        drawPosition(v, 0);
        
        for(int i = 1; i < results.size(); i++){
            
            double x = getVector(i).coordinate(0);
            double y = getVector(i).coordinate(1);
            
            calculateCoor(x, y, i);
            
            
            drawImage(getResultMetada(i), v);
            //drawPosition(v, i);
        }
        
    }

    @Override
    protected void sceneControl() {
  
        keyControl();

    }

    protected void calculateCoor(double x, double y, int index){
            
            r = Math.sqrt((x*x) + (y*y));
            
            theta = Math.atan2(x, y);
     
            System.out.println("r: " + r + " theta: " + theta);

            v.x = r * value;
            v.y = theta * value;
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
