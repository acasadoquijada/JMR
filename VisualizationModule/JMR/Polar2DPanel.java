/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import javax.vecmath.Vector3d;

/**
 * Clase que representa la visualización con coordenadas polares 2D.
 * @since version 1.00
 */
public class Polar2DPanel extends Abstract3DPanel {

    
    
    /**
    * Double que representa la distancia al centro de coordenadas.
    * @since version 1.00
    */

    protected double r;
    
    /**
    * Double que representa el ángulo phita sobre el eje x
    * @since version 1.00
    */
    
    protected double theta;
    
        /**
     * Double que representa el valor por el cual se multiplicarán las coordenadas
     * para estas se distribuyan por el mundo en lugar de concentrarse
     * en un único lugar
     * @since version 1.00
     */
    
    protected final double value;
    
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
    
    
    public Polar2DPanel() {

        super();
        planeActive = true;
        v = new Vector3d();
        value=10;
        
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
            
            double x = getVector(i).coordinate(0);
            double y = getVector(i).coordinate(1);
            
            calculateCoor(x, y);
            
            
            drawImage(getResultMetada(i), v);
            //drawPosition(v, i);
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
     * Dados x e y se calcula el valor del radio, del ángulo phita y se almacenan
     * sus valores en el vector de posición.
     * 
     * @param x indica la posición en el eje x en coordenadas cartesianas
     * @param y indica la posición en el eje y en coordenadas cartesianas
     * @since version 1.00
     */

    protected void calculateCoor(double x, double y){
            
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
