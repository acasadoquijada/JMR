/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationmodule;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Texture;
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
    
    private final float Zdecrement = 0.025f;
    
    public SpiralVisualization(int num){
        
        super(num);
        this.TYPE = AbstractVisualization.SPIRAL;
       

    }
    
    @Override
    public BranchGroup createScene() {

        this.vector = new Vector3f(0.0f,0.0f,0.0f);

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
                    drawImage(ci.getImages().get(image),this.vector,image);
                }
                
                else{
                    this.vector.x +=1.1;
                    this.vector.z -=Zdecrement;
                    drawImage(ci.getImages().get(image),this.vector,image);

                }
                
                image++;
                i++;
                
                if(image >= this.imagesNumber)
                    cont=false;
            }
                        
            // Second loop
            
            i=1;
            while(i <=maxLoop2 && cont){
                
                this.vector.y -= 1.1;
                this.vector.z -=Zdecrement;
                drawImage(ci.getImages().get(image),this.vector,image);
                
                image++;
                i++;
                
                if(image >= this.imagesNumber)
                    cont=false;
  
            }
            
            // Third loop
            
            i=1;
            while(i <= maxLoop3 && cont){
                
                this.vector.x -= 1.1;
                this.vector.z -=Zdecrement;
                drawImage(ci.getImages().get(image),this.vector,image);
                
                image++;
                i++;
                
                if(image >= this.imagesNumber)
                    cont=false;
            }
            
            // Fourth loop
            
            i=1;
            while(i <= maxLoop4 && cont){
                
                this.vector.y += 1.1;
                this.vector.z -=Zdecrement;
                drawImage(ci.getImages().get(image),this.vector,image);
                
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

}
