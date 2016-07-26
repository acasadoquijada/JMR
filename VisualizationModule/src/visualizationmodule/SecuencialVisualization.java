/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationmodule;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
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
public class SecuencialVisualization extends AbstractVisualization{
    
    public SecuencialVisualization(){
        
        super();
        
    }
    
    
    @Override
    public void initializeTransforms3D(int imagesNumber) {
        super.initializeTransforms3D(imagesNumber);
    }

    @Override
    public void initializeTransformsGroup(int imagesNumber) {
        super.initializeTransformsGroup(imagesNumber);
    }

    @Override
    public BranchGroup createScene() {
      
        Vector3f rightVector = new Vector3f(0.0f,0.0f,0.0f);
        Vector3f leftVector = new Vector3f(0.0f,0.0f,0.0f);
        
        drawImage(ci.getImages().get(0),rightVector,0);
        
        for(int i = 1; i < this.imagesNumber; i++){
            
            System.out.println(ci.getWeights().get(i));
            
            if(ci.getWeights().get(i) >= 0.5f){
                rightVector.x += 1.5;
                drawImage(ci.getImages().get(i),rightVector,i);
                
            }
            
            else{
                leftVector.x += -1.5;
                drawImage(ci.getImages().get(i),leftVector,i);
            } 
        }
        
        return this.objRoot;
    }

    @Override
    public void drawImage(Texture tex, Vector3f pos, int index) {
        
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
           
        Box b = new Box(0.5f,0.5f,0f,primflags,ap);
        
        trans.setTranslation(pos);
       
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        tg.setTransform(trans);
        
        tg.addChild(b);
        
        this.objRoot.addChild(tg);
    }
   


}
