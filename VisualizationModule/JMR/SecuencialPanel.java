/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import java.awt.Font;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import static jmr.iu.Abstract3DPanel.SECUENCIAL;
import jmr.result.ResultList;

/**
 *
 * @author alejandro
 */
public class SecuencialPanel extends Abstract3DPanel {

    /**
     * Creates new form SecuencialPanel
     */
    
    
    public SecuencialPanel() {
 
        super();
    
        TYPE = SECUENCIAL;
        
        planeActive = true;
        
    }
    
    
    
    public SecuencialPanel(ResultList list){
        super(list);        
    }
    
    @Override
    public void add(ResultList list){
        super.add(list);
        
     /*   
        BranchGroup p = new BranchGroup();
      
        t = new Transform3D();
        
        tg = new TransformGroup();
        
        
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        
        tg.addChild(scene);

        p.addChild(tg);
        
        MouseRotate myMouseRotate = new MouseRotate();
        myMouseRotate.setTransformGroup(tg);
        myMouseRotate.setSchedulingBounds(new BoundingSphere());

        p.addChild(myMouseRotate);
        
        MouseTranslate myMouseTranslate = new MouseTranslate();
        myMouseTranslate.setTransformGroup(tg);
        myMouseTranslate.setSchedulingBounds(new BoundingSphere());
        
        p.addChild(myMouseTranslate);
        
        MouseWheelZoom myMouseZoom = new MouseWheelZoom();
        myMouseZoom.setTransformGroup(tg);
        myMouseZoom.setSchedulingBounds(new BoundingSphere());

        p.addChild(myMouseZoom);
        
        simpleU.addBranchGraph(p);
       
        
/*
        objTransform.addChild(new FuzzyColorShape());
        objRoot.addChild(new Axis());

        MouseRotate myMouseRotate = new MouseRotate();
        myMouseRotate.setTransformGroup(objTransform);
        myMouseRotate.setSchedulingBounds(new BoundingSphere());
        objRoot.addChild(myMouseRotate);

        MouseTranslate myMouseTranslate = new MouseTranslate();
        myMouseTranslate.setTransformGroup(objTransform);
        myMouseTranslate.setSchedulingBounds(new BoundingSphere());
        objRoot.addChild(myMouseTranslate);

        MouseZoom myMouseZoom = new MouseZoom();
        myMouseZoom.setTransformGroup(objTransform);
        myMouseZoom.setSchedulingBounds(new BoundingSphere());
        objRoot.addChild(myMouseZoom);*/
        
    }
    
    @Override
    public void createScene() {
   
        Vector3d vector = new Vector3d(0.0f,0.0f,0.0f);
        
        float leftPos = 0.0f;
        float rightPos = 0.0f;
                
        drawImage(getResultMetada(0),vector);

        drawPosition(vector,0);

        
        for(int i = 1; i < results.size(); i++){
            
            if(i%2 != 0){
                rightPos += 1.5;
                vector.x = rightPos;
                
            }
            
            else{
                leftPos -= 1.5;

                vector.x = leftPos;
               
      
            }
     
            drawImage(getResultMetada(i),vector);
            drawPosition(vector,i);


        }
        
        viewplatform.setViewAttachPolicy(View.NOMINAL_HEAD);
        
    }
    
    

    
    
    @Override
    public void mouseControl() {
        BoundingSphere bounds =
        new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100000000.0);
     
        m_orbit = new OrbitBehavior(canvas3d, 
        OrbitBehavior.REVERSE_ALL | OrbitBehavior.STOP_ZOOM);

        m_orbit.setRotationCenter(new Point3d(1.0,0,0));
        m_orbit.setSchedulingBounds(bounds);
        m_orbit.setZoomFactor(-1d);
        m_orbit.setRotYFactor(0);
        m_orbit.setRotXFactor(0);
        m_orbit.setMinRadius(3.0);
                    
        simpleU.getViewingPlatform().setViewPlatformBehavior(m_orbit);
        this.simpleU.getViewingPlatform().setNominalViewingTransform();
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
