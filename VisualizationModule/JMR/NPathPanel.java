/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
import jmr.result.ResultMetadata;

/**
 *
 * @author alejandro
 */
public class NPathPanel extends Abstract3DPanel {

    /**
     * Creates new form NPathPanel
     */
    
    private ArrayList<ResultList> descValues;
    
    public NPathPanel() {
        super();
        
        planeActive = true;
        
        descValues = new ArrayList<>();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    
    @Override
    protected void createScene() {

        
        Vector3d v = new Vector3d();
        
        drawImage(getResultMetada(0), v);
        drawPosition(v,0);
  
        int descNum = getVector(0).dimension();

        int tam = results.size();
                
        int radio = descNum*2;
       
        double slice = 2 * Math.PI / descNum;
        double angle = 0.0;
        double newX = 0.0;
        double newZ = 0.0;

        
        ResultList rl;

        for(int i = 0; i < descNum; i++){
 
            rl = getResultList(i);
            
            descValues.add(rl);

        }
        
        
        for(int i = 0; i < descNum; i++){
            angle = slice * i;
            newX = (double)(0 + radio * Math.cos(angle));
            newZ = (double)(0 + radio * Math.sin(angle));
                
            v.x = newX;
            v.z = newZ;
            
            rl = descValues.get(i);
            
            drawDescNumb(v,i);
            
             for(int j = 1; j < tam-1; j++){
                 
                 int compX = Double.compare(newX, 0.0);
                 int compZ = Double.compare(newZ, 0.0);

                 if(compX < 0){                
                    double d = newX - 0;
                    
                    v.x += d;                  
                     
                 }
                 
                 else if(compX > 0){
                     double d = newX + 0;
                     
                     v.x += d;
                 }
                 
                if(compZ < 0){                
                    double d = newZ - 0;
                    
                    v.z += d;                  
                     
                 }
                 
                 else if(compZ > 0){
                     double d = newZ + 0;
                     
                     v.z += d;
                 }

                ResultMetadata rm = (ResultMetadata)rl.get(j);

                //BufferedImage img = getImage(rm);
                //

                drawImage(rm, v);
                drawPosition(v,j);
 
             }
        }

    }

    @Override
    protected void mouseControl() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        
        v.y += 1.1;
        v.x -= 0.3;

        t3d.setTranslation(v);
        
        t3d.setScale(0.7);
        
        tg.setTransform(t3d);
        
        tg.addChild(sh);
        
        position.addChild(tg);
    }
    
    private void drawDescNumb(Vector3d pos, int num){
        
        Font font = new Font("Verdana", Font.PLAIN, 1);
    
        Font3D f3d = new Font3D(font.deriveFont(0.6f),new FontExtrusion());
        
        Text3D text = new Text3D(f3d, "Java3D.org", new Point3f(0.0f,
				0.0f, 0.0f));      
        String st;
        
        num = num +1 ;
        st = String.valueOf(num);

        
        text.setString(st);
        
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
        
        //v.y += 1.1;
        //v.x -= 0.3;

        t3d.setTranslation(v);
        
        t3d.setScale(0.7);
        
        tg.setTransform(t3d);
        
        tg.addChild(sh);
        
        position.addChild(tg);
        
        
    }

    @Override
    protected void sceneControl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}