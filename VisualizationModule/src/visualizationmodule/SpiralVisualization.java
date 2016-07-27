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

/*

http://javaconceptoftheday.com/how-to-create-spiral-of-numbers-matrix-in-java/

*/
public class SpiralVisualization extends AbstractVisualization {

    
    private Vector3f vector;
    private int order;
    
    public SpiralVisualization(int num){
        
        super(num);
        
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

        /*     
        Primer loop 3 imágenes
        Segundo loop 1 imágenes
        Tercer loop 3 imágenes
        Cuarto loop 2 imágenes
        */
        
        int image = 0;
        
        boolean cont = true;
        
        int maxLoop1 = 3; // +1
         
        int maxLoop2 = 1; // +2
         
        int maxLoop3 = 3; // +2
          
        int maxLoop4 = 2; // +2 
         
        while ((cont))
        {
            
            //First loop
            
            int i=1;
            while(i <=maxLoop1 && cont){

                if(image == 0){
                    drawImage(ci.getImages().get(image),vector,image);
                }
                
                else{
                    vector.x +=1.1;
                    drawImage(ci.getImages().get(image),vector,image);

                }
                
                image++;
                i++;
                
                if(image >= this.imagesNumber)
                    cont=false;
            }
                        
            // Second loop
            
            i=1;
            while(i <=maxLoop2 && cont){
                
                vector.y -= 1.1;
                drawImage(ci.getImages().get(image),vector,image);
                
                image++;
                i++;
                
                if(image >= this.imagesNumber)
                    cont=false;
  
            }
            
            // Third loop
            
            i=1;
            while(i <= maxLoop3 && cont){
                
                vector.x -= 1.1;
                drawImage(ci.getImages().get(image),vector,image);
                
                image++;
                i++;
                
                if(image >= this.imagesNumber)
                    cont=false;
            }
            
            // Fourth loop
            
            i=1;
            while(i <= maxLoop4 && cont){
                
                vector.y += 1.1;
                drawImage(ci.getImages().get(image),vector,image);
                
                image++;
                i++;
                
                if(image >= this.imagesNumber)
                    cont=false;
            }
            
            
            if(maxLoop1 == 3){
                maxLoop1++;
            }
            
            else{
                maxLoop1+=2;
            }
            
            maxLoop2+=2;
            maxLoop3+=2;
            maxLoop4+=2;

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
