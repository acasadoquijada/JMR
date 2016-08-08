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
      
        Vector3f rightVector = new Vector3f(0.0f,0.0f,0.0f);
        Vector3f leftVector = new Vector3f(0.0f,0.0f,0.0f);
        
        drawImage(ci.getImages().get(0),rightVector,0);
        
        
        for(int i = 1; i < this.imagesNumber; i++){
            
            if(i%2 != 0){
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

}
