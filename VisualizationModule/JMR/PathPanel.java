/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Color;
import java.awt.Container;
import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import jmr.result.ResultList;

/**
 *
 * @author alejandro
 */
public class PathPanel extends Abstract3DPanel {

    /**
     * Creates new form PathPanel
     */
    public PathPanel() {
        super();        
        TYPE = PATH;
        planeActive = true;
    }
    
    public PathPanel(ResultList list){
        super(list);        
    }
    
    
    private void pathEnd(Vector3d pos){
        
        Transform3D tra = new Transform3D();
        TransformGroup tg = new TransformGroup();
        
        ColorCube b = new ColorCube(0.5f);
      
        
        Appearance ap = new Appearance();
        
       /* TextureLoader loader = new TextureLoader(
                "H:\\Dropbox\\Universidad\\Cuarto_año\\2 cuatrimestre\\TFG\\stop.png"
                ,new Container());
        */
        
        ///home/alejandro/Dropbox/Universidad/Cuarto_año/2 cuatrimestre/TFG

        TextureLoader loader = new TextureLoader(
        "/home/alejandro/Dropbox/Universidad/Cuarto_año/2 cuatrimestre/TFG/stop",
                new Container());
        
      
        
        Texture tex = loader.getTexture();
        
        
        tex.setBoundaryModeS(Texture.WRAP);

        tex.setBoundaryModeT(Texture.WRAP);
        
        TextureAttributes texAttr = new TextureAttributes();
        
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        
        ColoringAttributes coloringAttributes = new ColoringAttributes();
        coloringAttributes.setColor(new Color3f(Color.RED));
        
        TransparencyAttributes tAttr = new TransparencyAttributes();
        tAttr.setTransparencyMode(TransparencyAttributes.NICEST);
        
        ap.setTransparencyAttributes(tAttr);
        
        ap.setTexture(tex);
        
        ap.setTextureAttributes(texAttr);   
        
        int primflags = Primitive.GENERATE_NORMALS +

        Primitive.GENERATE_TEXTURE_COORDS; 
       
        Box stop = new Box(0.5f,0.5f,0.5f,primflags,ap);

        pos.z -= 2.0;

        tra.setTranslation(pos);
        
        tg.setTransform(tra);
        
        tg.addChild(stop);
        
        this.scene.addChild(tg);   
        
    }
    
    @Override
    public void createScene() {
        Vector3d vector = new Vector3d(0.0f,0.0f,0.0f);
            
        drawPosition(vector,0);
        drawImage(getResultMetada(0),vector);
        
        double puntoAnt = 0.0f;

        double punto = 0.0f;

        double puntoSig = 0.0f;
        
        boolean entro = false;
        

        for(int i = 1; i < results.size()-1; i++){
            
            punto = getVector(i).magnitude()*150;
            
            if(entro){
                punto=puntoAnt;
            }
            
            
            while(Math.abs(puntoAnt - punto) <= 4.000000000){
                punto += 0.1;
                entro = true;
            }

            
            vector.z = -punto;
          
           // vector.z -= 5.0;
            
            
            drawImage(getResultMetada(i),vector);
            drawPosition(vector, i);
           /* System.out.println(
                    "\nMagnitud: " + getVector(i).magnitude() +
                    "\nPunto modificado: " + punto +                    
                    "\nPunto siguiente: " + puntoAnt
                                
            );
            */
            puntoAnt = punto;
    
        }
        
      //  pathEnd(vector);
            
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
