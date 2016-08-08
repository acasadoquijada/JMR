 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationmodule;

import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.media.j3d.Texture;



/**
 *
 * @author Alejandro
 */
public class ImageLoader {
    
    private ArrayList<Float> weights;
    private ArrayList<Texture> images;
    
    public ImageLoader(int n){
        
        weights = new ArrayList();
        images = new ArrayList();
        
        Random random_number = new Random();
        
        TextureLoader loader = new TextureLoader
        ("C:\\Users\\Alejandro\\Desktop\\pug.png",
                new Container());
        
        Texture texture = loader.getTexture();
        
        
        for(int i = 1; i <= n; i++) {
            
            weights.add(random_number.nextFloat());
            images.add(texture); 
            
        }
        
        Collections.sort(weights, Collections.reverseOrder());

        for(int i = 1; i < weights.size(); i++){
        }
        

        
    }
        
        public ArrayList<Float> getWeights(){
            return weights;
        }
        
        public ArrayList<Texture> getImages(){
            return images;
    }   
}
