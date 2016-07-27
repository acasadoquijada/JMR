/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationmodule;

import java.util.ArrayList;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

/**
 *
 * @author alejandro
 */

public abstract class AbstractVisualization {
    
    protected ArrayList<Transform3D> transforms;
    protected ArrayList<TransformGroup> transformsGroup;
    
    protected int imagesNumber;
    protected BranchGroup objRoot;
    protected ImageLoader ci;
    
    
    protected AbstractVisualization(int num){
        this.imagesNumber = num;
        this.objRoot = new BranchGroup();
        this.initializeTransformsGroup(imagesNumber);
        this.initializeTransforms3D(imagesNumber);
        this.ci = new ImageLoader(imagesNumber);

    }
    
    protected void initializeTransforms3D(int imagesNumber){
        Transform3D t;
        transforms = new ArrayList();
        
        for(int i = 0;i < imagesNumber; i++){
            t = new Transform3D();    
            transforms.add(t);
        }
    }

    protected void initializeTransformsGroup(int imagesNumber){
        
        TransformGroup tg;
        transformsGroup = new ArrayList();

        for(int i = 0;i < imagesNumber; i++){
            tg = new TransformGroup();    
            transformsGroup.add(tg);
        }
    
    }
    abstract BranchGroup createScene();
    abstract void drawImage(Texture tex, Vector3f pos, int index);
    
}
