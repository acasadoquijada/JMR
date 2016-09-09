/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import javax.vecmath.Vector3d;

/**
 * Clase que representa la visualización con coordenadas cartesianas 2D.
 * @since version 1.00
 */
public class Cartesian2DPanel extends Abstract3DPanel {

    
    /**
     * Double que representa la coordenada x
     * @since version 1.00
     */
    protected double x;

    /**
     * Double que representa la coordenada y
     * @since version 1.00
     */
    
    protected double y;
    
    
    /**
     * Double que representa el valor por el cual se multiplicarán las coordenadas
     * para estas se distribuyan por el mundo en lugar de concentrarse
     * en un único lugar
     * @since version 1.00
     */
    
    protected double value;
    
    
    /**
     * Objeto Vector3d que representa un punto en el espacio tridimensional
     * @since version 1.00
     */
    
    
    protected Vector3d v;
    
    /**
     * Constructor por defecto.
     * Indica que se desea un plano que simule el suelo y el valor de value.
     * @since version 1.00
     */
    
    public Cartesian2DPanel() {

        super();
        planeActive = true;
        v = new Vector3d();
        
        value = 75;
        
    }

    /**
     * Método que se encarga de dibujar las distintas imágenes en el mundo.
     * @since version 1.00
     */
    
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

    /**
     * Método en el que se indica el tipo de interacción 
     * asi como de modificacones sobre ella.
     * @since version 1.00
     */
    @Override
    protected void sceneControl() {
  
        keyControl();
        
        
    }
    
    /**
     * Método que calcula el punto en el espacio y lo multiplica por value
     * para evitar que los puntos aparezcan en un rango muy pequeño.     
     * @param x indica la posición en el eje x
     * @param y indica la posición en el eje y
     * @param index índice de la imagen.
     * @since version 1.00
     */
    
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
