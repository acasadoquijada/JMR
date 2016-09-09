/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import javax.vecmath.Vector3d;
import static jmr.iu.Abstract3DPanel.SECUENCIAL;

/**
 * Clase que representa la visualización secuencial.
 * @since version 1.00
 */
public class SecuencialPanel extends Abstract3DPanel {

    /**
     * Constructor por defecto.
     * Indica que se desea un plano que simule el suelo.
     * @since version 1.00
     */
    
    public SecuencialPanel() {
 
        super();
    
        TYPE = SECUENCIAL;
        
        planeActive = true;
        
    }
    
    /**
     * Método que se encarga de dibujar las distintas imágenes en el mundo.
     * La imagen original se posiciona en el centro
     * Se eligen las dos imágenes mas similares a ella, 
     * la mas similar se coloca a su izquierda, la restante a su derecha, 
     * tras esto se vuelven a coger las siguientes dos imágenes mas 
     * parecidas a la usada en la consulta y se repite el proceso hasta 
     * usar todas las imágenes.
     * @since version 1.00
     */
    
    @Override
    public void createScene() {
   
        Vector3d vector = new Vector3d(0.0f,0.0f,0.0f);
        
        float leftPos = 0.0f;
        float rightPos = 0.0f;
                
        drawImage(getResultMetada(0),vector);

        drawPosition(vector,0);

        double separator = 4.5;
        
        
        for(int i = 1; i < results.size(); i++){
            
            if(i%2 != 0){
                rightPos += separator;
                vector.x = rightPos;
                
            }
            
            else{
                leftPos -= separator;

                vector.x = leftPos;
            }
     
            drawImage(getResultMetada(i),vector);
            drawPosition(vector,i);


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
