/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationmodule;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import java.awt.Color;
import static java.awt.Component.TOP_ALIGNMENT;
import java.util.ArrayList;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 *
 * @author alejandro
 */

public abstract class AbstractVisualization {
    
    protected ArrayList<Transform3D> transforms;
    protected ArrayList<TransformGroup> transformsGroup;
    
    protected int imagesNumber;
    protected BranchGroup objRoot;
    protected ImageLoader ci;
    protected int TYPE;
    
    protected static final int SECUENCIAL = 0;
    
    protected static final int SPIRAL = 1;
    
    protected static final int ROAD = 2;


    
    
    protected AbstractVisualization(int num){
        this.imagesNumber = num;
        this.objRoot = new BranchGroup();
        this.initializeTransformsGroup(imagesNumber);
        this.initializeTransforms3D(imagesNumber);
        this.ci = new ImageLoader(imagesNumber);
        this.axis();
        this.createBackground();
        
        
    }
    
    public TransformGroup getTransformGroup(){
        
        
        return transformsGroup.get(0);
        
    }
    private void initializeTransforms3D(int imagesNumber){
        Transform3D t;
        transforms = new ArrayList();
        
        for(int i = 0;i < imagesNumber; i++){
            t = new Transform3D();    
            transforms.add(t);
        }
    }

    private void initializeTransformsGroup(int imagesNumber){
        
        TransformGroup tg;
        transformsGroup = new ArrayList();

        for(int i = 0;i < imagesNumber; i++){
            tg = new TransformGroup();    
            transformsGroup.add(tg);
        }
    
    }
    abstract BranchGroup createScene();
    
    protected void drawImage(Texture tex, Vector3f pos, int index){
        
        Transform3D trans = this.transforms.get(index);
        TransformGroup tg = this.transformsGroup.get(index);
                
        Appearance ap = new Appearance();
        
        tex.setBoundaryModeS(Texture.WRAP);

        tex.setBoundaryModeT(Texture.WRAP);
        
        TextureAttributes texAttr = new TextureAttributes();
        
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        ap.setTexture(tex);

        ap.setTextureAttributes(texAttr);   
        
        PointAttributes pa = new PointAttributes();
        //pa.setPointSize(5f);
        pa.setPointAntialiasingEnable(true);
        
        LineAttributes la=new LineAttributes();
        la.setLineAntialiasingEnable(true);
    
        ap.setLineAttributes(la);     
        ap.setPointAttributes(pa);
        
        int primflags = Primitive.GENERATE_NORMALS +

        Primitive.GENERATE_TEXTURE_COORDS; 
         
        float tam;

        if(TYPE == SPIRAL){
            tam = 0.5f - (index/225f); 
        }
        
        else{
             tam = 0.5f;
        }

    
        Box b = new Box(tam,tam,tam,primflags,ap);

        
        //Cylinder b = new Cylinder(tam,tam*2,primflags,64,64,ap);
        
        //Sphere b = new Sphere(tam,primflags,64,ap);
        
        
        trans.setTranslation(pos);
       
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        tg.setTransform(trans);
        
        tg.addChild(b);
        
        this.objRoot.addChild(tg);   
        
    };
    
    private void pick(){
        
    }
    
    private void axis(){
        this.objRoot = new BranchGroup();

        // Create X axis
        
        LineArray axisXLines=new LineArray(2,LineArray.COORDINATES);

        axisXLines.setCoordinate(0,new Point3f(-99999.0f,0.0f,0.0f));
        axisXLines.setCoordinate(1,new Point3f(99999.0f,0.0f,0.0f));   
        
        Appearance appearanceX = new Appearance();
        
        LineAttributes la = new LineAttributes();
        
        la.setLineAntialiasingEnable(true);
        la.setLineWidth(TOP_ALIGNMENT);
        
        appearanceX.setLineAttributes(la);
        ColoringAttributes coloringAttributesX = new ColoringAttributes();
        coloringAttributesX.setColor(new Color3f(Color.RED));
        appearanceX.setColoringAttributes(coloringAttributesX);
        
        Shape3D shapeLineX = new Shape3D(axisXLines, appearanceX);
        
        this.objRoot.addChild(shapeLineX);
        
        // Create Y axis  
        
        Appearance appearanceY = new Appearance();

        appearanceY.setLineAttributes(la);
        ColoringAttributes coloringAttributesY = new ColoringAttributes();
        coloringAttributesY.setColor(new Color3f(Color.green));
        appearanceY.setColoringAttributes(coloringAttributesY);
        
        LineArray axisYLines=new LineArray(2,LineArray.COORDINATES);
       
        axisYLines.setCoordinate(0,new Point3f(0.0f,-99999.0f,0.0f));
        axisYLines.setCoordinate(1,new Point3f(0.0f,99999.0f,0.0f));

        Shape3D shapeLineY = new Shape3D(axisYLines, appearanceY);
        
        this.objRoot.addChild(shapeLineY);

        // Create Z axis 
        
        Appearance appearanceZ = new Appearance();

        appearanceZ.setLineAttributes(la);
        ColoringAttributes coloringAttributesZ = new ColoringAttributes();
        coloringAttributesZ.setColor(new Color3f(Color.blue));
        appearanceZ.setColoringAttributes(coloringAttributesZ);
        Point3f z1=new Point3f(0.0f,0.0f,-99999.0f);
        Point3f z2=new Point3f(0.0f,0.0f,99999.0f);

        LineArray axisZLines=new LineArray(2,LineArray.COORDINATES);
        
        Shape3D shapeLineZ = new Shape3D(axisZLines, appearanceZ);
        
        this.objRoot.addChild(shapeLineZ);

        axisZLines.setCoordinate(0,z1);
        axisZLines.setCoordinate(1,z2);

        
    }
    
      private void createBackground(){
          
        Background background = new Background(new Color3f(1.000f, 0.980f, 0.980f));

        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 10000000);
        background.setApplicationBounds(sphere);
        
        this.objRoot.addChild(background);
    }
    
    
}
