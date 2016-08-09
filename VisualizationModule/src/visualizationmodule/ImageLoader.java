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
import javax.swing.plaf.TextUI;



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
        TextureLoader loader2 = new TextureLoader
        ("C:\\Users\\Alejandro\\Desktop\\pastor-aleman.jpg",
                new Container());
        
        TextureLoader loader3 = new TextureLoader
        ("C:\\Users\\Alejandro\\Desktop\\corgi.jpg",
                new Container());
       /*  TextureLoader loader4 = new TextureLoader
        ("C:\\Users\\Alejandro\\Desktop\\chema.jpg",
                new Container()); */
        
        Texture texture = loader.getTexture();
        Texture texture2 = loader2.getTexture();
        Texture texture3 = loader3.getTexture();
      // Texture texture4 = loader4.getTexture();

        
        
        for(int i = 1; i <= n; i++) {
            
            weights.add(random_number.nextFloat());
            
            //images.add(texture4);
            
            if(i%3 == 0)
                images.add(texture); 
            else if(i%3 == 1)
                images.add(texture2);
            else
                images.add(texture3);
            
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

    private void Contanier() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
