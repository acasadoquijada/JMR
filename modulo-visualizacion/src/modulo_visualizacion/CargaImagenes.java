 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_visualizacion;

import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.media.j3d.Texture;



/**
 *
 * @author Alejandro
 */
public class CargaImagenes {
    
    private ArrayList<Float> pesos;
    private ArrayList<Texture> imagenes;
    
    public CargaImagenes(int n){
        
        pesos = new ArrayList();
        imagenes = new ArrayList();
        
        Random num_aleatorio = new Random();
        
                TextureLoader loader = new TextureLoader
        ("C:\\Users\\Alejandro\\Desktop\\pug.png",
                new Container());
        
        Texture texture = loader.getTexture();
        
        
        for(int i = 1; i <= n; i++) {
            pesos.add(num_aleatorio.nextFloat());
            imagenes.add(texture);
            
        }
    }
        
        public ArrayList<Float> getPesos(){
            return pesos;
        }
        
        public ArrayList<Texture> getImagenes(){
            return imagenes;
    }   
}
