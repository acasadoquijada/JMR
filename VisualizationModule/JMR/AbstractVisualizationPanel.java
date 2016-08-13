/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
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
import jmr.result.ResultList;
import jmr.result.ResultMetadata;
import jmr.result.Vector;

/**
 *
 * @author alejandro
 */
public abstract class AbstractVisualizationPanel extends javax.swing.JPanel {
    
    protected SimpleUniverse simpleU;
    
    protected Canvas3D canvas3D;
    
    protected Transform3D transforms[];
    
    protected TransformGroup transformsGroup[];
    
    protected ResultList results;
    
    protected BranchGroup objRoot;
    
    protected int TYPE;
    
    protected static final int SECUENCIAL = 0;
    
    protected static final int SPIRAL = 1;
    
    protected static final int ROAD = 2;

    protected final static Dimension DEFAULT_COMMON_SIZE = new Dimension(100,100);

    
    /**
     * Creates new form AbstractVisualizationPanel
     */
    
    public AbstractVisualizationPanel(){
        initComponents();
        
    }
    
    public void initialize(Canvas3D canvas){
        
        this.objRoot = new BranchGroup();
        
        this.axis();
        this.createBackground();
        
        this.setPreferredSize(new Dimension(DEFAULT_COMMON_SIZE.width*5,DEFAULT_COMMON_SIZE.height*5));

        this.setLayout(new BorderLayout());
        
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        canvas = new Canvas3D(config);
        add("Center", canvas);
        
        simpleU = new SimpleUniverse(canvas);
        
        
        BoundingSphere bounds =
        new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 20000.0);
     
        OrbitBehavior m_orbit = new OrbitBehavior(canvas, 
        OrbitBehavior.REVERSE_ALL);

        m_orbit.setRotationCenter(new Point3d(1.0,0,0));
        m_orbit.setSchedulingBounds(bounds);
        m_orbit.setZoomFactor(-1d);
        simpleU.getViewingPlatform().setViewPlatformBehavior(m_orbit);
      //  simpleU.addBranchGraph(objRoot);
        
    }

    public AbstractVisualizationPanel(ResultList list){
        this(); 
        if (list!=null) add(list);    

    }
    
    public void add(ResultList list){

        int tam = list.size();
        
        this.results = new ResultList();
        this.results = list;
        this.transforms = new Transform3D[tam];
        this.transformsGroup = new TransformGroup[tam];
        for(int i = 0; i < tam; i++){
            this.transforms[i] = new Transform3D();
            this.transformsGroup[i] = new TransformGroup();
        }
               
    }
    
    public BufferedImage getImage(int index){
        
        ResultMetadata r = (ResultMetadata)this.results.get(index);
        
        BufferedImage img = (BufferedImage) r.getMetadata();
        
        return img;
        
    }
    
    public Vector getVector(int index){
        
        ResultMetadata r = (ResultMetadata)this.results.get(index);
        
        Vector vec = (Vector)r.getResult();
        
        return vec;
    } 

    public void setBranchGroup(BranchGroup bg){
        simpleU.addBranchGraph(bg);
    }
    
    abstract BranchGroup createScene();
    
    protected void drawImage(BufferedImage img, Vector3f pos, int index){
        
        
        TextureLoader loader = new TextureLoader(img,new Container());
        Texture tex = loader.getTexture();
        
        Transform3D trans = this.transforms[index];
        TransformGroup tg1 = this.transformsGroup[index];
                
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
       
        tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        tg1.setTransform(trans);
        
        tg1.addChild(b);
        
        this.objRoot.addChild(tg1);   
        
    };
    
    
    
    protected void axis(){

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
    
    protected void createBackground(){
          //0.941, 0.973, 1.000
        Background background = new Background(new Color3f(0.941f, 0.973f, 1.000f));

       // Background background = new Background(new Color3f(1.0f, 1.0f, 0.000f));

          
        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 10000000);
        background.setApplicationBounds(sphere);
        
        this.objRoot.addChild(background);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
