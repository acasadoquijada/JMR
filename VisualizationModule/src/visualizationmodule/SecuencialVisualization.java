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
public class SecuencialVisualization extends AbstractVisualization{
    
    public SecuencialVisualization(int num){
        
        super(num);

        this.TYPE = SecuencialVisualization.SECUENCIAL;
    
    }
    
    @Override
    public BranchGroup createScene() {
      
        Vector3f vector = new Vector3f(0.0f,0.0f,0.0f);
        
        float leftPos = 0.0f;
        float rightPos = 0.0f;
        float yPos = 0.0f;
        
        
        drawImage(ci.getImages().get(0),vector,0);
        
        
        for(int i = 1; i < this.imagesNumber; i++){
            
            if(i%2 != 0){
                rightPos += 1.5;
                vector.x = rightPos;
                
            }
            
            else{
                leftPos -= 1.5;

                vector.x = leftPos;
               
      
            }
            
            yPos -= 0.03;
            vector.y = yPos;
            
            drawImage(ci.getImages().get(i),vector,i);
            System.out.println(vector);
            
        }
        
        return this.objRoot;
    }

}
