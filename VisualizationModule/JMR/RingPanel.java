/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import java.util.ArrayList;
import java.util.Arrays;
import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
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

        
        drawImage(getResultMetada(0),vector);
        drawPosition(vector, 0);
        
     
        for(int i = 1; i < results.size(); i++){
       

            
            double x = getVector(i).coordinate(0);
            double xTrunc = Math.floor(x * 10) / 10;


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
        

        
        for(int i = 1; i < results.size(); i++){
            
            double x = getVector(i).coordinate(0);
            double xTrunc = Math.floor(x * 10) / 10;
            
            if(arrAux.contains(xTrunc)){
                
                int n = arrAux.indexOf(xTrunc);
                
                ringValues.get(n).add(x);
                
            }
            
            
        }
        
        ArrayList<Double> arrayAux = new ArrayList<>();
        

        ringValues.removeAll(Arrays.asList(arrayAux));


        int images = 1;
                
        int tam = 0;
        
        int radioAnt=0;

        tam = ringValues.get(0).size();
        
        radio = tam;
        
        for(int i = 0; i < ringValues.size(); i++){
            slice = 2 * Math.PI /  ringValues.get(i).size();

            tam = ringValues.get(i).size();
          
            radio = tam;
            

            while( (radio <= radioAnt) ){                
         
                radio += tam;
                
            }
            
            
            while((radio - radioAnt) <= 4){
                radio += tam;
            }
           
            System.out.println("Radio: " + radio + " radio anterior: " + radioAnt);

            double zAux = 0;
            // Coger la coordenada del Array de ringValues
            for(int j = 0; j < tam; j++){
                
                double angle = slice * j;
                double newX = (0 + radio * Math.cos(angle));
                double newY = (0 + radio * Math.sin(angle));

                puntoY = getVector(images).coordinate(1)*350;

                vector.x = newX;

                vector.y = newY;

                vector.z = puntoY; 
                
                if(j == 0){
                    zAux = puntoY;
                }

                drawImage(getResultMetada(images), vector);
                drawPosition(vector, images);
                
                
               
               images++;
            
            }
            drawRadio(radio,zAux);
            radioAnt = radio;
            
         
        }


    }

    
    private void drawRadio(int radio,double z){
        

        int coorNumber =  radio * 40;
        Transform3D t = new Transform3D();
        TransformGroup tg = new TransformGroup();
        
        Vector3d v = new Vector3d();
        
        Appearance ap = new Appearance();
        
        LineAttributes la = new LineAttributes();
        
        la.setLineAntialiasingEnable(true);
        
        la.setLineWidth(2.5f);
        
        ap.setLineAttributes(la);
        
        ColoringAttributes ca = new ColoringAttributes();
        
        ca.setColor(new Color3f(0.545f, 0.271f, 0.075f));
        
        ap.setColoringAttributes(ca);
        
        LineArray ls = new LineArray(coorNumber,LineArray.COORDINATES);
        
        double slice = 2 * Math.PI / coorNumber;

        
        for(int j = 0; j < coorNumber; j++){
                
            double angle = slice * j;
            double newX = (0 + radio * Math.cos(angle));
            double newY = (0 + radio * Math.sin(angle));
            
            Point3f p = new Point3f();
            
            p.x = (float) newX;
            p.y = (float) newY;
            p.z = (float) z;
                
            ls.setCoordinate(j, p);
            
        }
        
        
        t.setTranslation(v);
        
        tg.setTransform(t);
        
        tg.addChild(new Shape3D(ls, ap));
        
        
        position.addChild(tg);
                
        
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
