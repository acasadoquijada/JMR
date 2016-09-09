/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import javax.vecmath.Vector3d;

/**
 * Clase que representa la visualización camino.
 * @since version 1.00
 */
public class PathPanel extends Abstract3DPanel {

    /**
     * Constructor por defecto.
     * Indica que se desea un plano que simule el suelo.
     * @since version 1.00
     */
    
    public PathPanel() {
        super();        
        TYPE = PATH;
        planeActive = true;
    }
    
   /**
     * Método que se encarga de dibujar las distintas imágenes en el mundo.
     * @since version 1.00
     */
    @Override
    public void createScene() {
        Vector3d vector = new Vector3d(0.0f,0.0f,0.0f);
            
        drawPosition(vector,0);
        drawImage(getResultMetada(0),vector);
        
        double puntoAnt = 0.0f;

        double punto = 0.0f;

        double puntoSig = 0.0f;
        
        boolean entro = false;
        

        for(int i = 1; i < results.size()-1; i++){
            
            punto = getVector(i).magnitude()*150;
            
            if(entro){
                punto=puntoAnt;
            }
            
            
            while(Math.abs(puntoAnt - punto) <= 4.000000000){
                punto += 0.1;
                entro = true;
            }

            
            vector.z = -punto;
          
           // vector.z -= 5.0;
            
            
            drawImage(getResultMetada(i),vector);
            drawPosition(vector, i);
           /* System.out.println(
                    "\nMagnitud: " + getVector(i).magnitude() +
                    "\nPunto modificado: " + punto +                    
                    "\nPunto siguiente: " + puntoAnt
                                
            );
            */
            puntoAnt = punto;
    
        }
        
      //  pathEnd(vector);
            
    }
    
    
        /**
     * Método en el que se indica el tipo de interacción 
     * asi como de modificacones sobre ella.
     * @since version 1.00
     */
    @Override
    protected void sceneControl() {

        keyControl();
    
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
