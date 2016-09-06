/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import java.awt.Font;
import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Geometry;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

/**
 *
 * @author alejandro
 */
public class AveragePanel extends Abstract3DPanel {

    /**
     * Creates new form AveragePanel
     */
    public AveragePanel() {
        super();
        createLimitPlanes(0.5);
        //aquí4
        planeActive = true;
       // axisActive = true;

    }
    
    private void createLimitPlanes(double lim){



        Appearance ap = new Appearance();

        ColoringAttributes ca = new ColoringAttributes();
        Color3f c = new Color3f(0.43f, 0.502f, 0.565f);
        
        ca.setColor(c);
        
        ap.setColoringAttributes(ca);
    
        Geometry geo = createGeometry(new Point3f(-130.0f, 10000.5f, -10000.0f),
        new Point3f(-130.0f, 10000.5f, 10000.0f),
        new Point3f(-130.0f, -1000000.f, 10000.0f), new Point3f(-130.0f, -100000.0f, -10000.0f));
      
        
        Shape3D plane = new Shape3D(geo, ap);
        
        backgroundScene.addChild(plane);
        
        Geometry geo2 = createGeometry(new Point3f(10.0f, 100.5f, -10000.0f),
        new Point3f(10.0f, 100.5f, 10000.0f),
        new Point3f(10.0f, -1000000.f, 10000.0f), new Point3f(10.0f, -100000.0f, -10000.0f));
      

        Shape3D plane2 = new Shape3D(geo2, ap);
         
       // backgroundScene.addChild(plane2);


        
    }  

    
    @Override
    protected void createScene() {
        
        Vector3d v = new Vector3d();
       
        drawImage(getResultMetada(0), v);
        
        Vector3d[] vArray = new Vector3d[results.size()];
        
        for(int i = 0; i < vArray.length; i++){
            vArray[i] = new Vector3d(0,0,0);
        }
        
        double newDist = 2.0;
        
        for(int i = 1; i < results.size(); i++){
            
        
            //obtengo el valor del primer descriptor
            
            double x = getVector(i).coordinate(0);
            
            // Lo trunco para saber si va a la izquierda o derecha
            
            double xTrunc = Math.floor(x * 10) / 10;
            
            
            int compX = Double.compare(xTrunc, 0.5);
            
    
            
            //negativo
            if(compX < 0){
                x *= 120;
                v.x = - x;
                
            }
            //positivo o igual
            else{
                x *= 120;
                v.x = x;       
            }
            
            // Obtengo y
            double y =getVector(i).coordinate(1);
            
            // "Reinicio" el valor de x
            x = getVector(i).coordinate(0);
            
            // Calculo z
            double z = ((x+y)/2) * 25;
            
            y =  getVector(i).coordinate(1) *120;
            
            v.y = y;
            
            v.z = z;
            
            boolean sigo = true;
            
            Vector3d auxV = new Vector3d();
            
            for(int j = 1; j <= i && sigo; j++) {
                
                // Comparar x y z, si el punto nuevo
                // está en el mismo x y aumentarle z
                // Puede ser que esten en distinto x y
                // con z similar, lo que provocaria que
                // aumentara la z sin necesidad
                
                // Cojo el vector

                auxV = vArray[j-1];
                
                // Calculo si el nuevo punto esta demasiado cerca
                
                double xDif = Math.abs(v.x - auxV.x);
                double yDif = Math.abs(v.y - auxV.y);
                double zDif = Math.abs(v.z - auxV.z);
                
                // Si no hay suficiente distancia, la aumento
                if(xDif < 0.7000 || yDif < 0.7000 || zDif < 0.7000){
                    
                    
                    v.z += newDist;
                    
                    // Asi nos aseguramos de que todas
                    // las imagenes se vean correctamente
                    newDist+=2.0;
                    
                    sigo = false;
                   
                }
                
            
            }

            //Para volver a entrar en el bucle
            sigo = true;
            
            vArray[i]=v;
            
            System.out.println("Vector: " + v);
            drawImage(getResultMetada(i), v);
            
        }       
        
    }

    @Override
    protected void mouseControl() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void drawPosition(Vector3d pos, int index) {
      Font font = new Font("Verdana", Font.PLAIN, 1);
    
        Font3D f3d = new Font3D(font.deriveFont(0.6f),new FontExtrusion());
        
        
        Text3D text = new Text3D(f3d, new String("Java3D.org"), new Point3f(0.0f,
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
        
       
        v.z += 0.5f; 
        v.x += 0.5f;
             
        System.out.println("Punto numero: " + v);
        

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
            .addGap(0, 400, Short.MAX_VALUE)
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
