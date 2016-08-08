/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationmodule;

import javax.media.j3d.BranchGroup;
import javax.vecmath.Vector3f;

/**
 *
 * @author alejandro
 */
public class RoadVisualization extends AbstractVisualization {

    public RoadVisualization(int num) {
        super(num);
        
        this.TYPE = ROAD;
        
        
    }

    @Override
    public BranchGroup createScene() {
      
        Vector3f vector = new Vector3f(0.0f,0.0f,0.0f);
        
        drawImage(ci.getImages().get(0),vector ,0);
        
        
        for(int i = 1; i < this.imagesNumber; i++){
            
            vector.z -= (2/ci.getWeights().get(1));
            
           //    System.out.println("Imagen " + i + " valor de z " + vector.z);
            
            drawImage(ci.getImages().get(i), vector, i);
            
        }
        
        return this.objRoot;
    }

    
}
