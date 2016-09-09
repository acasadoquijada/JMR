/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import javax.vecmath.Vector3d;
import jmr.result.ResultList;

/**
 * Clase que representa la visualización en espiral.
 * @since version 1.00
 */
public class SpiralPanel extends Abstract3DPanel {


    /**
    * Objeto Vector3d que representa una posición en el espacio tridimensional
    * @since version 1.00
    */
    private Vector3d vector;

    /**
    * Float que indica el decremento de la posición de las imágenes en el eje z.
    * @since version 1.00
    */
    
    
    private final float Zdecrement = 0.025f;
    
        /**
     * Constructor por defecto.
     * Indica que el tipo de visualización es espiral.
     * @since version 1.00
     */
    
    public SpiralPanel() {  

        super();
        
        TYPE = SPIRAL;


    }
    /**
     * Método que se encarga de dibujar las distintas imágenes en el mundo.
     * La imagen original se posiciona en el centro
     * El resto se colocan a su alrededor siguiendo una espiral en sentido 
     * antihorario.
     * @since version 1.00
     */
    @Override
    public void createScene() {
        
        this.vector = new Vector3d(0.0f,0.0f,0.0f);

        int image = 0;
        
        boolean cont = true;
        
        int maxLoop1 = 3; // +1
         
        int maxLoop2 = 1; // +2
         
        int maxLoop3 = 3; // +2
          
        int maxLoop4 = 2; // +2 
         
        while ((cont))
        {
            
            //First loop
            
            int i=1;
            while(i <=maxLoop1 && cont){

                if(image == 0){
                    drawImage(getResultMetada(image),this.vector);
                    drawPosition(vector,image);
                }
                
                else{
                    this.vector.x +=2.1;
                    this.vector.z -=Zdecrement;
                    drawImage(getResultMetada(image),this.vector);
                    drawPosition(vector,image);

                    
                 //   System.out.println("Imagen numero : " + image + " Valor de z: " + this.vector.z);

                }
                
                image++;
                i++;
                
                if(image >= this.results.size())
                    cont=false;
            }
                        
            // Second loop
            
            i=1;
            while(i <=maxLoop2 && cont){
                
                this.vector.y -= 2.1;
                this.vector.z -=Zdecrement;
                drawImage(getResultMetada(image),this.vector);
                drawPosition(vector,image);

                image++;
                i++;
               // System.out.println("Imagen numero : " + image + " Valor de z: " + this.vector.z);
                
                if(image >= this.results.size())
                    cont=false;
  
            }
            
            // Third loop
            
            i=1;
            while(i <= maxLoop3 && cont){
                
                this.vector.x -= 2.1;
                this.vector.z -=Zdecrement;
                drawImage(getResultMetada(image),this.vector);
                drawPosition(vector,image);

                image++;
                i++;
                // System.out.println("Imagen numero : " + image + " Valor de z: " + this.vector.z);
                if(image >= this.results.size())
                    cont=false;
            }
            
            // Fourth loop
            
            i=1;
            while(i <= maxLoop4 && cont){
                
                this.vector.y += 2.1;
                this.vector.z -=Zdecrement;
                drawImage(getResultMetada(image),this.vector);
                drawPosition(vector,image);

                
                image++;
                i++;
              //   System.out.println("Imagen numero : " + image + " Valor de z: " + this.vector.z);
                if(image >= this.results.size())
                    cont=false;
            }
            
            
            if(maxLoop1 == 3){
                maxLoop1++;
            }
            
            else{
                maxLoop1+=2;
            }
            
            maxLoop2+=2;
            maxLoop3+=2;
            maxLoop4+=2;

        }
    
    }
            /**
     * Método en el que se indica el tipo de interacción 
     * asi como de modificacones sobre ella.
     * @since version 1.00
     */

    @Override
    protected void sceneControl() {

        mouseControl(true,false);
        
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


