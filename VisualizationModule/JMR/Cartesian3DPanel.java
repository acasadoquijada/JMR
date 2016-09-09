/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

/**
 * Clase que representa la visualización con coordenadas cartesianas 3D.
 * @since version 1.00
 */
public class Cartesian3DPanel extends Cartesian2DPanel {

    /**
     * Double que representa la coordenada y
     * @since version 1.00
     */
    double z;
    
    /**
     * Constructor por defecto
     * @since version 1.00
     */
    public Cartesian3DPanel() {
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

            calculatePosition(x, y, i);
            
            try{
            v.z = - getVector(i).coordinate(2)*value;
            
            }catch(Exception e){
                System.err.println("Falta un descriptor");
                
            }
            System.out.println(v);
          
            drawImage(getResultMetada(i), v);
            drawPosition(v, i);
        }
        
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
