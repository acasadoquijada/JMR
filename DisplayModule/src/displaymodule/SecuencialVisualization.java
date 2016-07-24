/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package displaymodule;

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
        //this.num_imagenes= 5;
        
    }
    
    
    @Override
    public void inicializarTransformadas(int num_imagenes) {
        super.inicializarTransformadas(num_imagenes);
    }

    @Override
    public void inicializarGrupoTransformadas(int num_imagenes) {
        super.inicializarGrupoTransformadas(num_imagenes);
    }

    @Override
    public BranchGroup crearEscena() {
      
        Vector3f vector = new Vector3f(0.0f,0.0f,0.0f);
        
        for(int i = 0; i < this.num_imagenes; i++){
            dibujarImagen(ci.getImagenes().get(i),vector,i);
            vector.x += 1.5f;
        }
        
        return this.objRoot;
    }

    @Override
    public void dibujarImagen(Texture tex, Vector3f pos, int indice) {
        
        Transform3D trans = this.transformadas.get(indice);
        TransformGroup tg = this.grupo_transformadas.get(indice);
                
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
