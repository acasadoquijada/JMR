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
public class SpiralVisualization extends AbstractVisualization {

    
    private Vector3f vector;
    private int order;
    
    public SpiralVisualization(){
        
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

        vector = new Vector3f(0.0f,0.0f,0.0f);
        
        drawImage(ci.getImages().get(0),vector,0);

        order=1;
        
        while((order*order) <= this.imagesNumber){
            order++;
        }

        
        for(int i = 1; i < this.imagesNumber; i++){
            
            interiorCube(i);
      

            
            System.out.println("i: " + i + " " + vector);

            
            drawImage(ci.getImages().get(i),vector,i);
            

        }


    
        return this.objRoot;

    }
    
    
    private void interiorCube(int index){
        
        int n = index%9;
       
        switch(n){
            case 0: // Es 9
                vector.y += 1.1f;
                break;
            case 1: // Es 1
               vector.y += 1.1f;  
                break;

            case 2: // Es 2
               vector.x += 1.1f; 
                break;

            case 3: // Es 3
               vector.y -=1.1f;
                break;

            case 4: // Es 4
               vector.y -=1.1f;
                break;                    

            case 5: // Es 5
               vector.x -=1.1f;
                break;

            case 6: // Es 6
               vector.x -=1.1f;
                break;  

            case 7: // Es 7
               vector.y +=1.1f;
                break;
                
            case 8:
                vector.y +=1.1;
                break;
                    
            }
    }
    
    private void exteriorCube(int index){
        
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
