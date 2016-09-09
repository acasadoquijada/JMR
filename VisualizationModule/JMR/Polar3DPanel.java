/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

/**
 * Clase que representa la visualización con coordenadas polares 3D.
 * @since version 1.00
 */
public class Polar3DPanel extends Polar2DPanel {

    /**
    * Double que representa el 
    * @since version 1.00
    */    
    double azimut;
    

    /**
     * Constructor por defecto.
     * @since version 1.00
     */
    public Polar3DPanel() {
        super();

        
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
            double z = getVector(i).coordinate(2);
            
            calculateCoor(x, y, z);
            
            drawImage(getResultMetada(i), v);
            //drawPosition(v, i);
        }
        
    }
    
        /**
     * Método que calcula el punto en el espacio y lo multiplica por value
     * para evitar que los puntos aparezcan en un rango muy pequeño.
     * Dados x, y e z, se calcula el valor del radio, del ángulo phita, del azimut
     * y se almacenan sus valores en el vector de posición.
     * 
     * @param x indica la posición en el eje x en coordenadas cartesianas
     * @param y indica la posición en el eje y en coordenadas cartesianas
     * @param z indica la posición en el eje z en coordenadas cartesianas
     * @since version 1.00
     */

    private void calculateCoor(double x, double y, double z){
            
            r = Math.sqrt((x*x) + (y*y) + (z*z));
            
            theta = Math.acos(z/r);
            
            azimut = Math.atan2(y,x);
            
            v.x = r * value;
            v.y = theta * value;
            v.z = azimut * value; 
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
