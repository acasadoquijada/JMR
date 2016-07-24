/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package displaymodule;

import java.util.ArrayList;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

/**
 *
 * @author alejandro
 */

public abstract class AbstractVisualization {
    
    protected ArrayList<Transform3D> transformadas;
    protected ArrayList<TransformGroup> grupo_transformadas;
    
    protected int num_imagenes;
    protected BranchGroup objRoot;
    protected ImageLoader ci;
    
    
    protected AbstractVisualization(){
        this.num_imagenes=5;
        this.objRoot = new BranchGroup();
        this.inicializarGrupoTransformadas(num_imagenes);
        this.inicializarTransformadas(num_imagenes);
        this.ci = new ImageLoader(num_imagenes);

    }
    
    protected void inicializarTransformadas(int num_imagenes){
        Transform3D t;
        transformadas = new ArrayList();
        
        for(int i = 0;i < num_imagenes; i++){
            t = new Transform3D();    
            transformadas.add(t);
        }
    }

    protected void inicializarGrupoTransformadas(int num_imagenes){
        
        TransformGroup tg;
        grupo_transformadas = new ArrayList();

        for(int i = 0;i < num_imagenes; i++){
            tg = new TransformGroup();    
            grupo_transformadas.add(tg);
        }
    
    }
    abstract BranchGroup crearEscena();
    abstract void dibujarImagen(Texture tex, Vector3f pos, int indice);
    
}
