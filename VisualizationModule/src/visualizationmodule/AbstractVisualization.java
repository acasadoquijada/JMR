/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationmodule;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import java.util.ArrayList;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
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
    protected int TYPE;
    
    protected static final int SECUENCIAL = 0;
    
    protected static final int SPIRAL = 1;


    
    
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
    protected void drawImage(Texture tex, Vector3f pos, int index){
        
        Transform3D trans = this.transforms.get(index);
        TransformGroup tg = this.transformsGroup.get(index);
                
        Appearance ap = new Appearance();
        
        tex.setBoundaryModeS(Texture.WRAP);

        tex.setBoundaryModeT(Texture.WRAP);
        
        TextureAttributes texAttr = new TextureAttributes();
        
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        ap.setTexture(tex);

        ap.setTextureAttributes(texAttr);    
        
        int primflags = Primitive.GENERATE_NORMALS +

        Primitive.GENERATE_TEXTURE_COORDS; 
         
        float tam;

        if(TYPE == SPIRAL){
            tam = 0.5f - (index/225f); 
        }
        
        else{
             tam = 0.5f;
        }

    
        Box b = new Box(tam,tam,0f,primflags,ap);
        
        trans.setTranslation(pos);
       
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        tg.setTransform(trans);
        
        tg.addChild(b);
        
        this.objRoot.addChild(tg);   
        
    };
    
}
