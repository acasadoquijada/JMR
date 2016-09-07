/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import java.awt.Font;
import java.util.ArrayList;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineStripArray;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import jmr.result.ResultList;

/**
 *
 * @author alejandro
 */
public class RingPanel extends Abstract3DPanel {

    /**
     * Creates new form NewJPanel
     */
    
    private ArrayList<ArrayList<Double>> ringValues;
    
    public RingPanel() {
        super();

        TYPE = SECUENCIAL;
        
        ringValues = new ArrayList<>();        
        
        
    }
    
    public RingPanel(ResultList list){
          super(list);        
    }
    
    @Override
    public void add(ResultList list){
        super.add(list);
    }

    @Override
    protected void createScene() {
        
        /* 
        
        Organizar los vectores
       
        11 en total.
        
        Hacer el radio segun el numero de elementos

        Calcular el numero de elementos
        
        */
 
        
        Vector3d vector = new Vector3d(0.0f,0.0f,0.0f);
        

        double puntoAnt = 0.0f;

        double puntoX = 0.0f;
        
        double puntoY = 0.0f;

        double puntoSig = 0.0f;
        
        boolean entro = false;
       
        int radio = 0;
        
        double Zpos = 50.0;
        
        double slice = 2 * Math.PI / results.size();
        
        int anillos = 0;
        
        int values = 0;
        
        double aux=0.0;
        
        ArrayList<Double> arrAux = new ArrayList<>();
        
        /*
 
        Dos bucles, uno para obtener datos
        Otro para introducirlos
 
        */
        
        //List<Integer> arr = Arrays.asList(new Integer[10]);
        
        drawImage(getResultMetada(0),vector);
        drawPosition(vector, 0);
        
        
        for(int i = 1; i < results.size(); i++){
       
            
            // Obtengo el numero de anillos
            
            double x = getVector(i).coordinate(0);
            double xTrunc = Math.floor(x * 10) / 10;
            
            /* System.out.println("X: "+ x );
            
            System.out.println("Xtrun: " + xTrunc);
            */
            ArrayList<Double> arr = new ArrayList<>();
            
            int comp = Double.compare(xTrunc, aux);
            
            if(comp != 0){
                
                arr = new ArrayList<>();
                ringValues.add(arr);
                anillos++;
             
                arrAux.add(xTrunc);
                
                aux = xTrunc;
            }

        }
        
/*
        
        for(int i = 1; i < results.size(); i++){
            
            double x = getVector(i).coordinate(0);
            double xTrunc = Math.floor(x * 10) / 10;
            
            if(arrAux.contains(xTrunc)){
                
                int n = arrAux.indexOf(xTrunc);
                
                ringValues.get(n).add(x);
                
            }
            
            
        }
        
        int images = 1;
                
        int tam = 0;
        
        int radioAnt=0;

        tam = ringValues.get(0).size();
        
        radio = tam;
        
        for(int i = 0; i < anillos; i++){
            slice = 2 * Math.PI /  ringValues.get(i).size();
            tam = ringValues.get(i).size();
          
            radio = tam;
            
            while( (radio <= radioAnt) ){
                
                radio += tam;
                
            }
           
                
            
           // System.out.println("Radio: " + radio + " radio ant:" + radioAnt);
            
            //radio +=3;
         
            // Coger la coordenada del Array de ringValues
            for(int j = 0; j < tam; j++){
                
                double angle = slice * j;
                double newX = (0 + radio * Math.cos(angle));
                double newY = (0 + radio * Math.sin(angle));

                puntoY = getVector(images).coordinate(1)*150;

                vector.x = newX;

                vector.y = newY;

                vector.z = puntoY;    

            //    System.out.println("Vector: " + vector);
            
                drawImage(getResultMetada(images), vector);
                drawPosition(vector, images);
                
               /// System.out.println("Indice: " + images);
                
               
               images++;
            
            }
            
            radioAnt = radio;
            
          //  System.out.println("");
         
        }

*/
    }
    
    
    // Mirar http://stackoverflow.com/questions/4864541/draw-line-in-java3d
    
    private void drawRadio(int radio,float[] points){
        
        
        Transform3D t = new Transform3D();
        TransformGroup tg = new TransformGroup();
        
        Vector3d v = new Vector3d();
        
        
        Appearance ap = new Appearance();
        
        PolygonAttributes polyAttribs = 
                new PolygonAttributes( PolygonAttributes.POLYGON_LINE, 
                        PolygonAttributes.CULL_NONE, 0 );
        
        int[] cadena = {points.length};
        
        for(int i = 0; i < points.length; i++)
        System.out.println("Valor de: " + i + "  - " +points[i]);
        System.out.println("");

        LineStripArray ls = new LineStripArray(points.length,
                GeometryArray.COORDINATES, cadena);
        
        ls.setCoordinate(0, points);
        
        t.setTranslation(v);
        
        tg.setTransform(t);
        
        tg.addChild(new Shape3D(ls));
        
        
        scene.addChild(tg);
                
        
    }

    @Override
    protected void sceneControl() {

       // mouseControl(true, false);
        keyControl();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
