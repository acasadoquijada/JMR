/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import javax.media.j3d.Appearance;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import jmr.result.ResultList;

/**
 *
 * @author alejandro
 */
public class StatisticPanel extends Abstract3DPanel {

    /**
     * Creates new form StatisticPanel
     */
    public StatisticPanel() {
        super();
        planeActive=true;
        
    }
    
    public StatisticPanel(ResultList list){
        super(list);
        
    }
    
    @Override
    protected void createScene() {
        Vector3d v = new Vector3d();
        
        //Decidir 3 medidas estadisticas y usarlas
        
        drawImage(getResultMetada(0), v);
        drawPosition(v, 0);
        
        // Para el eje x uso la media
        // Depende de la distancia de la media
        
        double mean = calculateMean(0); // Primer descriptor
        double meanAux = mean;
        double mean1 = calculateMean(1);
        double standarDeviation = calculateStandardDeviation(1,mean1);

        
        
        System.out.println("Media: " + mean + " - Desviacion: " + standarDeviation);
        
        updateInfo(mean,standarDeviation);

        
        ArrayList<Vector3d> vArray;
        vArray = new ArrayList
                (Collections.nCopies(results.size(), new Vector3d()));
        
        
        for(int i = 1; i < results.size(); i++){
            
            double x = getVector(i).coordinate(0);
            
            double y = getVector(i).coordinate(1);
            
            System.out.println("Valor de x: " + x + " y: " + y );
  /*       
            // Problemas si se pasa de la media
           
            
            if(x != 1.0){
                
                // SI se pasa de la media, la aumentamos lo que se pase              
                if(Double.compare(mean, x) < 0){
                    
                    double aux = x-mean;
                    mean +=aux;
                    
                }
                
                
                x = Math.abs(mean - getVector(i).coordinate(0)); 

                System.out.println("X: " + x + " 1-x: " + (1-x));
                x = 1-x;
            }
            
            if(y != 1.0){
                y = Math.abs(standarDeviation - getVector(i).coordinate(1));
                y = 1-y;

            }
            
            System.out.println("Valor post de x: " + x + " y: " + y );
*/
                        
  
            x+=mean;
            y+=standarDeviation;
            v.x = x*50;
            v.y = y*50;
            
            
            boolean sigo = true;
            
            double newDist = 2;
            
            Vector3d auxV;
            
            mean = meanAux;
            
            for(int j = 1; j <= i && sigo; j++) {
                
                // Comparar x y z, si el punto nuevo
                // está en el mismo x y aumentarle z
                // Puede ser que esten en distinto x y
                // con z similar, lo que provocaria que
                // aumentara la z sin necesidad
                // Problema: z algunas veces aumenta demasiado
                
                // Cojo el vector

                auxV = vArray.get(j-1);
                
                // Calculo si el nuevo punto esta demasiado cerca
                
                double xDif = Math.abs(v.x - auxV.x);
                double yDif = Math.abs(v.y - auxV.y);
                double zDif = Math.abs(v.z - auxV.z);
                
                // Si no hay suficiente distancia, la aumento
                if(xDif < 0.7000 && yDif < 0.7000 && zDif < 0.7000){
                                        
                    v.z -= newDist;
                    
                    // Asi nos aseguramos de que todas
                    // las imagenes se vean correctamente
                    //newDist+=2.0;
                    
                    sigo = false;
                   
                }
            }

            
            System.out.println("Punto: "+ v);
            System.out.println("");
            

            vArray.set(i, v);
            
            drawImage(getResultMetada(i), v);
            drawPosition(v, i);
            
        }

    }
    
    private double calculateMean(int coor){
        double mean = 0;
                
        for(int i = 1; i < results.size(); i++)
            mean += getVector(i).coordinate(coor);
      
        mean = mean/results.size();
     
        return mean;
        
    }
    
    private double calculateStandardDeviation(int coor,double mean){
        double standarDeviation = 0;
        
        double sum = 0;
        double variance = 0;
        
        int tam = results.size();
        
        for(int i = 1; i < tam; i++){
            
            double n = getVector(i).coordinate(coor);
            
            sum = Math.pow(n - mean , 2);
            
            variance +=sum;

        }
        
        variance = variance /(tam-1);
        
        standarDeviation = Math.sqrt(variance);
        
        
        return standarDeviation;
        
    }

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
