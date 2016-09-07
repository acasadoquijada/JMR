/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

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
