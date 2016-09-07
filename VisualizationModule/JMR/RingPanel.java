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

        // Mejorar radios
        slice = 2 * Math.PI /  ringValues.get(0).size();
        tam = ringValues.get(0).size();
        
        radio = tam;
        
        for(int i = 0; i < anillos; i++){
            
            tam = ringValues.get(i).size();
          
            radio +=3;
         
            // Coger la coordenada del Array de ringValues
            for(int j = 0; j < tam; j++){
                
                double angle = slice * j;
                double newX = (0 + radio * Math.cos(angle));
                double newY = (0 + radio * Math.sin(angle));


                puntoX = getVector(images).coordinate(0)*50;
                puntoY = getVector(images).coordinate(1)*150;

                vector.x = newX;

                vector.y = newY;

                //vector.z = puntoY;    

            //    System.out.println("Vector: " + vector);
            
                drawImage(getResultMetada(images), vector);
                drawPosition(vector, images);
                
               /// System.out.println("Indice: " + images);
                
               System.out.println("Indice: " + images);
               images++;
            
            }
            
            System.out.println("");
         
        }


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


    protected void mouseControl() {
        BoundingSphere bounds =
        new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100000000.0);
     
        m_orbit = new OrbitBehavior(canvas3d, 
        OrbitBehavior.REVERSE_ALL);

        m_orbit.setRotationCenter(new Point3d(1.0,0,0));
        m_orbit.setSchedulingBounds(bounds);
        m_orbit.setZoomFactor(-1d);
        //m_orbit.setRotYFactor(0);
        m_orbit.setRotXFactor(0);
                    
        simpleU.getViewingPlatform().setViewPlatformBehavior(m_orbit);
        
        viewplatform.setViewAttachPolicy(View.NOMINAL_HEAD);
    }

    @Override
    protected void drawPosition(Vector3d pos, int index) {
    
        Font font = new Font("Verdana", Font.PLAIN, 1);
    
        Font3D f3d = new Font3D(font.deriveFont(0.6f),new FontExtrusion());
        
        
        Text3D text = new Text3D(f3d, "Java3D.org", new Point3f(0.0f,
				0.0f, 0.0f));
              
        String st = new String();
        
        st = String.valueOf(index);
        
        text.setString(st);
        
        text.setCharacterSpacing(0.1f);
        
        text.setAlignment(Text3D.ALIGN_CENTER);

        Shape3D sh = new Shape3D();
        
        sh.setGeometry(text);
        
        Appearance aprnc = new Appearance();
        
        PointAttributes pa = new PointAttributes();
        
        pa.setPointAntialiasingEnable(true);

        aprnc.setPointAttributes(pa);
   
        sh.setAppearance(aprnc);
 
        TransformGroup tg = new TransformGroup();
        
        Transform3D t3d = new Transform3D();
        
        Vector3d v = new Vector3d(pos);      
        
        System.out.println("ENTRO");
        
        
        if(index != 0){
            
            int compX = Double.compare(pos.x, 0.0);
            int compZ = Double.compare(pos.y, 0.0);

            if(compX != 0){
                v.x +=1.0f;
            }
            
            if(compZ != 0){
                v.y += 1.0f;
            }
        }
        
        t3d.setTranslation(v);
        
        t3d.setScale(0.7);
        
        tg.setTransform(t3d);
        
        tg.addChild(sh);
        
        position.addChild(tg);
    
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

    @Override
    protected void sceneControl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
