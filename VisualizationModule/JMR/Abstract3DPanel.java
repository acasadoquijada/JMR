/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.AWTEvent;
import java.awt.Color;
import static java.awt.Component.TOP_ALIGNMENT;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.CapabilityNotSetException;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.ExponentialFog;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import jmr.result.ResultList;
import jmr.result.ResultMetadata;
import jmr.result.Vector;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;
import javax.vecmath.Vector3f;


/**
 *
 * @author alejandro
 */
public abstract class Abstract3DPanel extends javax.swing.JPanel {
    
    protected SimpleUniverse simpleU;

    protected Canvas3D canvas3d;
        
    protected Transform3D transforms[];

    ViewPlatform viewplatform;
    
    protected TransformGroup transformsGroup[],tgBehaviour;
        
    protected ResultList results;
    
    protected BranchGroup backgroundScene, scene, fogBranch,axis,position;
    
    private Background background;
       
    protected OrbitBehavior m_orbit;
    
    protected ExponentialFog myFog;
    
    boolean fogActive,positionActive, planeActive,axisActive;
    
    protected int TYPE;
    
    protected static final int SECUENCIAL = 0;
    
    protected static final int SPIRAL = 1;
    
    protected static final int PATH = 2;
    
    protected int SHAPE;
    
    protected static final int BOX = 3;
    
    protected static final int SPHERE = 4;
    
    protected int CONTROL;
      
    protected static final int MOUSE = 5;
    
    protected static final int KEY = 6;  

    protected final static Dimension DEFAULT_COMMON_SIZE = new Dimension(100,100);

    TransformGroup camara;
    
    private Transform3D tcamara;
    private Vector3d vcamara;
    
    private int spiralIndex;

    
    /**
     * Creates new form AbstractVisualizationPanel
     */
    
    public Abstract3DPanel(){
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        initComponents();
        
        SHAPE = BOX;

        this.scene = new BranchGroup();
        this.fogBranch = new BranchGroup();
        this.position = new BranchGroup();

        fogBranch.setCapability(BranchGroup.ALLOW_DETACH);

        scene.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        scene.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        scene.setCapability(BranchGroup.ALLOW_DETACH);
        
        position.setCapability(BranchGroup.ALLOW_DETACH);
        

        spiralIndex = 0;
    
        this.vcamara    = new Vector3d();
        this.tcamara    = new Transform3D();
        this.tgBehaviour = new TransformGroup();

        
        GraphicsConfiguration config =
        SimpleUniverse.getPreferredConfiguration();
        
        canvas3d = new Canvas3D(config);
        
        this.add(canvas3d, java.awt.BorderLayout.CENTER);
        
        this.setPreferredSize(
                new Dimension
        (DEFAULT_COMMON_SIZE.width*5,DEFAULT_COMMON_SIZE.height*5));

        simpleU = new SimpleUniverse(canvas3d);

        this.createBackground();

        //this.axis();
        
     //   this.pickPointBehaviour();
        
        //this.simpleU.addBranchGraph(backgroundScene);
        
        //this.simpleU.getViewingPlatform().setNominalViewingTransform();

        this.camara = simpleU.getViewingPlatform().getViewPlatformTransform();

        cameraPos();
        
        //ViewPlatform
        viewplatform = simpleU.getViewingPlatform().getViewPlatform();
        
        simpleU.getViewer().getView().setBackClipDistance(100000);

        // To avoid problems between Java3D and Swing

        ToolTipManager ttManager = ToolTipManager.sharedInstance();
        ttManager.setEnabled(true);
        ttManager.setLightWeightPopupEnabled(false);
        
        this.createFog();
        
        fogActive = false;
        planeActive = false;
        positionActive = true;
        axisActive = false;
        
        
        this.sceneControl();
        this.mouseBehaviour();

    }
  

    public Abstract3DPanel(ResultList list){
        this();
        if (list!=null){          
            add(list);
        }    

    }
    
    public void add(ResultList list){
        
        this.results = new ResultList();
        this.results = list;
     
        createScene();
        
        mouseOver();
        
        
        simpleU.addBranchGraph(scene);
        
        if(position.numChildren() > 0){
            simpleU.addBranchGraph(position);        
        }
        

        
        if(planeActive){
            Shape3D plane = createPlane();

            backgroundScene.addChild(plane);  //Add plane to content branch. 
        }
        
        this.simpleU.addBranchGraph(backgroundScene);
        
        
        if(axisActive){
            this.axis();
        }
        
        
                

    }
    
    protected void mouseControl(boolean rotX, boolean rotY){
        
        BoundingSphere bounds =
        new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000000000000.0);
     
        m_orbit = new OrbitBehavior(canvas3d, 
        OrbitBehavior.REVERSE_ALL );
      
        if(!rotX){
            m_orbit.setRotXFactor(0);
        }
        
        if(!rotY){
            m_orbit.setRotYFactor(0);
        }
        
        m_orbit.setZoomFactor(100);
        m_orbit.setTransFactors(3, 3);
        
        

        m_orbit.setRotationCenter(new Point3d(1.0,0,0));
        m_orbit.setSchedulingBounds(bounds);
        m_orbit.setZoomFactor(-1d);
                
        simpleU.getViewingPlatform().setViewPlatformBehavior(m_orbit);
        
        
    }
    
    protected void keyControl(){

        KeyNavigatorBehavior navegacion = new KeyNavigatorBehavior(camara);
        BoundingSphere bounds =
        new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 12000.0);
        
        navegacion.setSchedulingBounds(bounds);
        
        backgroundScene.addChild(navegacion);
        
        
    }
    
    
    private void mouseOver(){
        
    MouseOverBehavior mouseOver = 
                new MouseOverBehavior(canvas3d, scene);
              
        mouseOver.setSchedulingBounds (new BoundingSphere (new Point3d (),
        1000000));
        
        scene.addChild(mouseOver);
    }
    
    private void createFog(){
        myFog = new ExponentialFog();
        myFog.setColor( new Color3f( 0.8f, 0.8f, 0.8f ) );
        myFog.setDensity(0.1f);
        
        myFog.setCapability(ExponentialFog.ALLOW_COLOR_WRITE);
        
        BoundingSphere myBounds = new BoundingSphere(new Point3d( ), 10000.0 );
        myFog.setInfluencingBounds(myBounds);
        
        fogBranch.addChild(myFog);
        
    }
    
 
    
    public BufferedImage getImage(int index){
        
        ResultMetadata r = (ResultMetadata)this.results.get(index);
        
        BufferedImage img = (BufferedImage) r.getMetadata();
        
        return img;
        
    }
    
    public BufferedImage getImage(ResultMetadata r){
             
        BufferedImage img = (BufferedImage) r.getMetadata();
                
        return img;
    }
    
    public Vector getVector(int index){
        
        ResultMetadata r = (ResultMetadata)this.results.get(index);
        
        Vector vec = (Vector)r.getResult();
        
        return vec;
    } 
    
    public ResultList getResultList(int coor){
        
        ResultList l = new ResultList();

        int tam = results.size();
        
        int numDes = getVector(0).dimension();
        
        
        
        for(int i = 1; i < tam; i++){
            
            Vector v = new Vector(1);
            

            v.setCoordinate(0, getVector(i).coordinate(coor));
     
            l.add(new ResultMetadata(v, getImage(i)));
            
        }
        
        l.sort();

        return l;
    }
    
    public ResultMetadata getResultMetada(int index){
       
        ResultMetadata r = (ResultMetadata)this.results.get(index);
   
        return r;
 
    }
    
    protected void resetView(){
        cameraPos();
    }
    
    protected void updateInfo(double info1,double info2){
        
        try{
            String m;
            m = "Mean: " + String.valueOf(info1)+
                    " - Standar deviation: " + String.valueOf(info2);
            
            
            meanLabel.setText(m);    
        }catch(Error e){
            
        }

        
    }
    
    protected abstract void createScene();
    
    protected abstract void sceneControl();
    
    protected void drawPosition(Vector3d pos, int index){
        
        Font font = new Font("Verdana", Font.PLAIN, 1);
    
        Font3D f3d = new Font3D(font.deriveFont(0.6f),new FontExtrusion());
        
        
        Text3D text = new Text3D(f3d, "Java3D.org", new Point3f(0.0f,
				0.0f, 0.0f));
              
        String st;
        
        st = String.valueOf(index);

        text.setString(st);
        
        Shape3D sh = new Shape3D();
        
        sh.setGeometry(text);
        
        Appearance aprnc = new Appearance();
        
        PointAttributes pa = new PointAttributes();
        
        pa.setPointAntialiasingEnable(true);

        aprnc.setPointAttributes(pa);
   
        sh.setAppearance(aprnc);
 
        TransformGroup tg = new TransformGroup();
        
        Transform3D t3d = new Transform3D();
        
        Vector3d v = new Vector3d(pos);
              
        v.y += 2.1;
        
        if(index == 0){
            v.x -= 0.6;
        }

        else{
            int dig = (int)(Math.log10(index)+1);
            v.x -= (0.2 *dig );   
        }


        t3d.setTranslation(v);
        
        t3d.setScale(2.0);
        
        tg.setTransform(t3d);
        
        tg.addChild(sh);
        
        position.addChild(tg);

    }
    
    protected void fog(boolean activate){
                                
        if(activate){
            scene.addChild(fogBranch);
            fogActive = true;
           
        }
        
        else{
           fogBranch.detach();
           fogActive = false;
        }
    }
    
    protected boolean fogActive(){
        
        return fogActive;
        
    }
    
    protected boolean positionActive(){
        return positionActive;
    }
    
    private void clean(){
                
        fogBranch.detach();
    }
    
    protected void deteachScene(){

        

    }
    
    protected void rePaint(){
        scene.detach();
        position.detach();

        scene.removeAllChildren();
        position.removeAllChildren();
        
        clean();
                
        if(TYPE == SPIRAL)
            spiralIndex = 0;
        
        createScene();
        mouseOver();
        simpleU.addBranchGraph(scene);
        simpleU.addBranchGraph(position);
        
        if(fogActive()){
            fog(true);
        }

        
        
    }
    

    
    protected void drawImage(BufferedImage img, Vector3d pos){
        
        
        TextureLoader loader = new TextureLoader(img,new Container());
        Texture tex = loader.getTexture();

        Transform3D trans = new Transform3D();
        TransformGroup tg1 = new TransformGroup();
        
        Appearance ap = new Appearance();
        
        tex.setBoundaryModeS(Texture.CLAMP_TO_EDGE );

        tex.setBoundaryModeT(Texture.CLAMP_TO_EDGE );
        
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
        
        TransparencyAttributes tAttr = new TransparencyAttributes();
        tAttr.setTransparencyMode(TransparencyAttributes.NICEST);
        
       // ap.setTransparencyAttributes(tAttr);
        
        int primflags = Primitive.GENERATE_NORMALS +

        Primitive.GENERATE_TEXTURE_COORDS; 
         
        float tam;

        if(TYPE == SPIRAL){
            tam = 0.5f - (spiralIndex/1500f);
            spiralIndex++;
           // tam = -0.0688889f;
        }
        
        else{
             tam = 0.5f;
        }

       // System.out.println("Imagen: " + index + " tamaño" + tam);
    
       Primitive b = null;
       
       
       switch(SHAPE){
           
           case BOX:
                b = new Box(tam,tam,tam,primflags,ap);

                break;
            
           case SPHERE:
                b = new Sphere(tam,primflags,64,ap);
   
                break;
       }

       
        trans.setTranslation(pos);
       
    //    tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        tg1.setTransform(trans);
        
        tg1.addChild(b);
        
        this.scene.addChild(tg1);   
        
    };
    
   protected void drawImage(ResultMetadata rm, Vector3d pos){
        
        BufferedImage img = (BufferedImage) rm.getMetadata();
        
        Vector v = (Vector) rm.getResult();  


        TextureLoader loader = new TextureLoader(img,new Container());
        Texture tex = loader.getTexture();

        Transform3D trans = new Transform3D();
        TransformGroup tg1 = new TransformGroup();
        
        Appearance ap = new Appearance();
        
        tex.setBoundaryModeS(Texture.CLAMP_TO_EDGE );

        tex.setBoundaryModeT(Texture.CLAMP_TO_EDGE );
        
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
        
        TransparencyAttributes tAttr = new TransparencyAttributes();
        tAttr.setTransparencyMode(TransparencyAttributes.NICEST);
        
        ap.setTransparencyAttributes(tAttr);
        
        int primflags = Primitive.GENERATE_NORMALS +

        Primitive.GENERATE_TEXTURE_COORDS; 
         
        float tam;

        if(TYPE == SPIRAL){
            tam = 1.5f - ( spiralIndex / (float)((results.size()*3)));
            spiralIndex++;

        }
        
        else{
             tam = 1.5f;
        }

    
       Primitive b = null;
       
 
       
       switch(SHAPE){
           
           case BOX:
                b = new Box(tam,tam,tam,primflags,ap);
                break;
            
           case SPHERE:
                b = new Sphere(tam,primflags,64,ap);
   
                break;
       }
       
        String s = v.toString();
                b.setUserData(s);
      

       
        trans.setTranslation(pos);
       
    //    tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        tg1.setTransform(trans);
        
        tg1.addChild(b);
        
        this.scene.addChild(tg1);   
        
    };
   

   protected void popMenuExpansion(javax.swing.JMenu menu){
       
       jPopupMenu.add(menu);
       
   }
    
    
    
    protected void axis(){

        // Create X axis
        
        axis = new BranchGroup();
        
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
        
        this.axis.addChild(shapeLineX);
        
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
        
        this.axis.addChild(shapeLineY);

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
        
        this.axis.addChild(shapeLineZ);

        axisZLines.setCoordinate(0,z1);
        axisZLines.setCoordinate(1,z2);
        
        simpleU.addBranchGraph(axis);
     
    }
    
    protected void backgroundColor(Color3f c){
        
        this.background.setColor(c);
        
    }
    
    
    private Shape3D createPlane(){
     
        Appearance ap = new Appearance();

        ColoringAttributes ca = new ColoringAttributes();
        Color3f c = new Color3f(0.502f, 0.502f, 0.502f);
        
        ca.setColor(c);
        
        ap.setColoringAttributes(ca);
    
        Geometry geo = createGeometry(new Point3f(-100000.0f, -1.5f, -100000.0f),
        new Point3f(-100000.0f, -1.5f, 100000.0f),
        new Point3f(100000.0f, -1.5f, 100000.0f), new Point3f(100000.0f, -1.5f, -100000f));
        
        
        Shape3D plane = new Shape3D(geo, ap);
        
        return plane;
        
        
    }
    
    
    protected void cameraPos(){
        
        camara.getTransform(tcamara);
        
        tcamara.get(vcamara);
        
        vcamara.x = 0.0;
        vcamara.y = 0.0;
        vcamara.z = 25.0;
        
        
        tcamara.set(vcamara);
        
        camara.setTransform(tcamara);
        
    }
    
    
    
    protected void createBackground(){
        
        this.backgroundScene = new BranchGroup();
        
        this.backgroundScene.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        
        this.background = new Background(new Color3f(0.4f, 0.6f, 1.0f));
        
        background.setCapability(Background.ALLOW_COLOR_WRITE);
            
        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 10000000);
        background.setApplicationBounds(sphere);
        
        this.backgroundScene.addChild(background);  


    }
    
    protected Geometry createGeometry(Point3f A, Point3f B, Point3f C, Point3f D) {

      QuadArray plane = new QuadArray(4, GeometryArray.COORDINATES
          | GeometryArray.NORMALS);

      plane.setCoordinate(0, A);
      plane.setCoordinate(1, B);
      plane.setCoordinate(2, C);
      plane.setCoordinate(3, D);

      Vector3f a = new Vector3f(A.x - B.x, A.y - B.y, A.z - B.z);
      Vector3f b = new Vector3f(C.x - B.x, C.y - B.y, C.z - B.z);
      Vector3f n = new Vector3f();
      n.cross(b, a);

      n.normalize();

      plane.setNormal(0, n);
      plane.setNormal(1, n);
      plane.setNormal(2, n);
      plane.setNormal(3, n);

      return plane;
    }
    
    private void mouseBehaviour(){

        this.canvas3d.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        
        this.canvas3d.addMouseListener(new java.awt.event.MouseAdapter() {
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt){    
                if(evt.getClickCount() >= 2){
                    popMenu(evt);
                }
                
                else if(evt.getButton() == MouseEvent.BUTTON1){

                }
   
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                if(evt.getClickCount() >= 2){
                   popMenu(evt);
                }
            }
            
        });

    }
    
    private void popMenu(java.awt.event.MouseEvent evt){

        if (evt.isPopupTrigger()) {
            jPopupMenu.show(evt.getComponent(),
                    evt.getX(), evt.getY());
        }
    
    }
    
    private Point3d getIntersection(Point3d line1, Point3d line2, 
			Point3d plane1, Point3d plane2, Point3d plane3) {
        
        Vector3d p1 = new Vector3d(plane1);
        Vector3d p2 = new Vector3d(plane2);
        Vector3d p3 = new Vector3d(plane3);
        Vector3d p2minusp1 = new Vector3d(p2);
        p2minusp1.sub(p1);
        Vector3d p3minusp1 = new Vector3d(p3);
        p3minusp1.sub(p1);
        Vector3d normal = new Vector3d();
        normal.cross(p2minusp1, p3minusp1);
        // The plane can be defined by p1, n + d = 0
        double d = -p1.dot(normal);
        Vector3d i1 = new Vector3d(line1);
        Vector3d direction = new Vector3d(line1);
        direction.sub(line2);
        double dot = direction.dot(normal);
        if (dot == 0) return null;
        double t = (-d - i1.dot(normal)) / (dot);
        Vector3d intersection = new Vector3d(line1);
        Vector3d scaledDirection = new Vector3d(direction);
        scaledDirection.scale(t);
        intersection.add(scaledDirection);
        Point3d intersectionPoint = new Point3d(intersection);
        return intersectionPoint;
    }
	
    
    
    private Point3d get3DPoint(MouseEvent event){
        Point3d eyePos = new Point3d();
        Point3d mousePos = new Point3d();
        canvas3d.getCenterEyeInImagePlate(eyePos);
        canvas3d.getPixelLocationInImagePlate(event.getX(), event.getY(), mousePos);
        Transform3D transform = new Transform3D();
        canvas3d.getImagePlateToVworld(transform);
        transform.transform(eyePos);
        transform.transform(mousePos);
        Vector3d direction = new Vector3d(eyePos);
        direction.sub(mousePos);
        // three points on the plane
        Point3d p1 = new Point3d(.5, -.5, .5);
        Point3d p2 = new Point3d(.5, .5, .5);
        Point3d p3 = new Point3d(-.5, .5, .5);
        Transform3D currentTransform = new Transform3D();	
        Point3d intersection = getIntersection(eyePos, mousePos, p1, p2, p3);

     
      //  currentTransform.invert();
        //currentTransform.transform(intersection);
        return intersection;	             
    }

    
    private void canvas3dmousePressed(java.awt.event.MouseEvent evt){

        
        if(evt.getButton() == java.awt.event.MouseEvent.BUTTON1){
        
            get3DPoint(evt);
            
         /*   
            
            
            
            
            PickInfo pickInfo = null;

            PickCanvas pickCanvas = new PickCanvas(canvas3d, scene);
            pickCanvas.setMode(PickTool.GEOMETRY_INTERSECT_INFO);

            Point3d eyePos = pickCanvas.getStartPosition();
            // get the viewer's eye location

            pickInfo = pickCanvas.pickClosest();
            // get the intersected shape closest to the viewer

            if (pickInfo != null) {
                pickInfo.getClosestIntersectionPoint();
                eyePos = pickInfo.getClosestIntersectionPoint();
                // get the closest intersect to the eyePos point
                //Point3d intercept = pi.getPointCoordinatesVW();
                System.out.println(eyePos);
                // extract the intersection pt in scene coords space
                // use the intersection pt in some way...
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
          /*  
            
            int x = evt.getX();
            int y = evt.getY();
            
            Point3d eye_pos = new Point3d();
            Point3d mouse_pos = new Point3d(); 
            
            Transform3D motion = new Transform3D();
            canvas3d.getImagePlateToVworld(motion); 
            canvas3d.getCenterEyeInImagePlate(eye_pos);
            canvas3d.getPixelLocationInImagePlate(x, y, mouse_pos);
            
            motion.transform(eye_pos);
            motion.transform(mouse_pos); 
            
            Vector3d direction = new Vector3d(mouse_pos); 
            
            direction.sub(eye_pos); 
            
            Vector3d straightDown = new Vector3d(0, 0, -1.0f); 
            
            straightDown.normalize(); 
            
            direction.normalize();
            
            double rayLength = direction.dot(straightDown);
            double rayamount = mouse_pos.z / rayLength; 
            
            double test = direction.length();
            direction.scale(rayamount);
            mouse_pos.add(direction);
            
            Vector3d v = new Vector3d();
            
            tg =  m_orbit.getViewingPlatform().getViewPlatformTransform();

            Transform3D tra = new Transform3D();
            tg.getTransform(tra);
            tra.get(v);

            Point3d pntd = new Point3d(v.x,v.y,0);
            
            camara.getTransform(tcamara);
            tcamara.get(vcamara); 
            
            System.out.println("Mouse pos" + mouse_pos + "\n" + "Camara" + vcamara
            +"\n" + "View:" + pntd +"\n\n");
            
            
            
            
            
            */
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            /*   Transform3D t = new Transform3D();
            Transform3D t2 = new Transform3D();

            Vector3d v = new Vector3d();
            
            Vector3d v2= new Vector3d();
            Point3d p = new Point3d();
            
            canvas3d.getCenterEyeInImagePlate(p);
         
            t2.get(v2);
    
            canvas3d.getView().getUserHeadToVworld(t2);
            tg =  m_orbit.getViewingPlatform().getViewPlatformTransform();

            tg.getTransform(t);
            t.get(v);
            
            camara.getTransform(tcamara);
            tcamara.get(vcamara);  
            
            System.out.println("V2:" + vcamara +"\n" + " P: " + p + "\n" + "V:" + v +"\n\n");
            /*
            
            
            
            tg =  m_orbit.getViewingPlatform().getViewPlatformTransform();

            tg.getTransform(t);
            t.get(v);

            Point3d pntd = new Point3d(v.x,v.y,0);

            System.out.println("Vector: " + v +"  Punto: " + pntd);
            m_orbit.setRotationCenter(pntd);
            
            ViewPlatform vp = new ViewPlatform();
          
            this.simpleU.getViewingPlatform().setViewPlatform(vp);*/
        }
    }
    
    
    
    private void formKeyPressed(java.awt.event.KeyEvent evt) { 
        
        ViewPlatform vp = canvas3d.getView().getViewPlatform();
        
        /*
        
        // Si el valor de x es 0 NO dejarlo pasar
    
        double xLimit = 0.17918508094160188;
        camara.getTransform(tcamara);
        
        tcamara.get(vcamara);
        
        System.out.println("Antes: " + vcamara);

        if(vcamara.x < xLimit){
            vcamara.x = xLimit;
        }
        
        tcamara.set(vcamara);
        
        camara.setTransform(tcamara);
        
        
        System.out.println("Despues: " + vcamara);
        
        */
    }
    
    
public class MouseOverBehavior extends Behavior {

    private com.sun.j3d.utils.picking.PickCanvas pickCanvas;
    private PickResult pickResult;

    private boolean isObjectSelectedBefore = false;
    private Shape3D oldPickedNode = new Shape3D ();
    private BranchGroup dataBranchGroup;

    private Primitive p;
    private Canvas3D canvas;
    private boolean overAnObject = false;

    private BranchGroup shapeLabelBG = new BranchGroup ();

    public MouseOverBehavior (Canvas3D canvas, BranchGroup dataBranchGroup) {
        this.canvas = canvas;
        this.dataBranchGroup = dataBranchGroup;

        pickCanvas = new com.sun.j3d.utils.picking.PickCanvas (canvas, dataBranchGroup);
        pickCanvas.setTolerance (1.0f);
        pickCanvas.setMode (com.sun.j3d.utils.picking.PickCanvas.GEOMETRY_INTERSECT_INFO);


    }

    @Override
    public void initialize () {
        wakeupOn (new WakeupOnAWTEvent (MouseEvent.MOUSE_MOVED));
    }

    @Override
    public void processStimulus (Enumeration criteria) {
        WakeupCriterion wakeup;
        AWTEvent[] event;
        int eventId;

        while (criteria.hasMoreElements ()) {
            wakeup = (WakeupCriterion) criteria.nextElement ();

            if (wakeup instanceof WakeupOnAWTEvent) {
                event = ((WakeupOnAWTEvent) wakeup).getAWTEvent ();

                for (int i = 0; i < event.length; i++) {
                    eventId = event[i].getID ();

                    if (eventId == MouseEvent.MOUSE_MOVED) {
                        int x = ((MouseEvent) event[i]).getX ();
                        int y = ((MouseEvent) event[i]).getY ();

                        pickCanvas.setShapeLocation (x, y);

                        try {
                            pickResult = pickCanvas.pickClosest ();

                            if (pickResult != null) {
                                Primitive p = 
                                        (Primitive)pickResult.getNode
        (PickResult.PRIMITIVE);
                                
                                
                                if(p.getUserData() != null){
                                    String s = (String)p.getUserData();
                                    if(!s.equals(infoLabel.getText()))
                                        infoLabel.setText((String)p.getUserData());
                                }
          

                            }
                        } catch (CapabilityNotSetException e) {
                            System.err.println(e);
                        }
                    }
                }
            }
        }
        wakeupOn (new WakeupOnAWTEvent (MouseEvent.MOUSE_MOVED));
    }
}
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu = new javax.swing.JPopupMenu();
        jMenuView = new javax.swing.JMenu();
        jCheckBoxMenuItemDefault = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jMenuShape = new javax.swing.JMenu();
        jCheckBoxMenuItemBox = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemSphere = new javax.swing.JCheckBoxMenuItem();
        jMenuFog = new javax.swing.JMenu();
        jCheckBoxMenuItemFog = new javax.swing.JCheckBoxMenuItem();
        jMenuItemFogColor = new javax.swing.JMenuItem();
        jMenuOptions = new javax.swing.JMenu();
        jMenuItemResetView = new javax.swing.JMenuItem();
        jMenuItemBackgroundColor = new javax.swing.JMenuItem();
        buttonGroupView = new javax.swing.ButtonGroup();
        buttonGroupShape = new javax.swing.ButtonGroup();
        infoPanel = new javax.swing.JPanel();
        infoLabel = new javax.swing.JLabel();
        meanLabel = new javax.swing.JLabel();

        jMenuView.setText("View");

        buttonGroupView.add(jCheckBoxMenuItemDefault);
        jCheckBoxMenuItemDefault.setSelected(true);
        jCheckBoxMenuItemDefault.setText("Default");
        jMenuView.add(jCheckBoxMenuItemDefault);

        buttonGroupView.add(jCheckBoxMenuItem2);
        jCheckBoxMenuItem2.setText("Vector");
        jMenuView.add(jCheckBoxMenuItem2);

        jPopupMenu.add(jMenuView);

        jMenuShape.setText("Shape");

        buttonGroupShape.add(jCheckBoxMenuItemBox);
        jCheckBoxMenuItemBox.setSelected(true);
        jCheckBoxMenuItemBox.setText("Box");
        jCheckBoxMenuItemBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItemBoxActionPerformed(evt);
            }
        });
        jMenuShape.add(jCheckBoxMenuItemBox);

        buttonGroupShape.add(jCheckBoxMenuItemSphere);
        jCheckBoxMenuItemSphere.setText("Sphere");
        jCheckBoxMenuItemSphere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItemSphereActionPerformed(evt);
            }
        });
        jMenuShape.add(jCheckBoxMenuItemSphere);

        jPopupMenu.add(jMenuShape);

        jMenuFog.setText("Fog");
        jMenuFog.setToolTipText("");

        jCheckBoxMenuItemFog.setText("Active");
        jCheckBoxMenuItemFog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItemFogActionPerformed(evt);
            }
        });
        jMenuFog.add(jCheckBoxMenuItemFog);

        jMenuItemFogColor.setText("Color");
        jMenuItemFogColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFogColorActionPerformed(evt);
            }
        });
        jMenuFog.add(jMenuItemFogColor);

        jPopupMenu.add(jMenuFog);

        jMenuOptions.setText("Options");

        jMenuItemResetView.setText("Reset view");
        jMenuItemResetView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemResetViewActionPerformed(evt);
            }
        });
        jMenuOptions.add(jMenuItemResetView);

        jMenuItemBackgroundColor.setText("Background color");
        jMenuItemBackgroundColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBackgroundColorActionPerformed(evt);
            }
        });
        jMenuOptions.add(jMenuItemBackgroundColor);

        jPopupMenu.add(jMenuOptions);

        setMaximumSize(new java.awt.Dimension(400, 400));
        setMinimumSize(new java.awt.Dimension(400, 400));
        setPreferredSize(new java.awt.Dimension(400, 400));
        setLayout(new java.awt.BorderLayout());

        infoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        infoPanel.setPreferredSize(new java.awt.Dimension(25, 25));
        infoPanel.setLayout(new java.awt.BorderLayout());
        infoPanel.add(infoLabel, java.awt.BorderLayout.LINE_START);
        infoPanel.add(meanLabel, java.awt.BorderLayout.LINE_END);

        add(infoPanel, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemResetViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemResetViewActionPerformed

        resetView();
        
    }//GEN-LAST:event_jMenuItemResetViewActionPerformed

    private void jCheckBoxMenuItemBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemBoxActionPerformed


        if(jCheckBoxMenuItemBox.isSelected()){
            SHAPE = Abstract3DPanel.BOX;
            
            rePaint();
        }


    }//GEN-LAST:event_jCheckBoxMenuItemBoxActionPerformed

    private void jCheckBoxMenuItemSphereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemSphereActionPerformed

        if(jCheckBoxMenuItemSphere.isSelected()){
            SHAPE = Abstract3DPanel.SPHERE;

            rePaint();

        }
        
    }//GEN-LAST:event_jCheckBoxMenuItemSphereActionPerformed

    private void jMenuItemBackgroundColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBackgroundColorActionPerformed
       
        Color auxColor;
        Color3f color;

        auxColor = JColorChooser.showDialog(this,
                    "Choose a color", null);

            if(auxColor != null){

             color = new Color3f(auxColor);

             this.backgroundColor(color);
            }
             

    }//GEN-LAST:event_jMenuItemBackgroundColorActionPerformed

    private void jMenuItemFogColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFogColorActionPerformed

        Color auxColor;
        Color3f color;

        auxColor = JColorChooser.showDialog(this,
            "Choose a color", null);

        if(auxColor != null){

            color = new Color3f(auxColor);

            this.myFog.setColor(color);


        }

        
    }//GEN-LAST:event_jMenuItemFogColorActionPerformed

    private void jCheckBoxMenuItemFogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemFogActionPerformed
        
        if(jCheckBoxMenuItemFog.isSelected()){
            
            this.fog(true);
            
        }
        
        else{
            
            this.fog(false);
            
        }
    }//GEN-LAST:event_jCheckBoxMenuItemFogActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupShape;
    private javax.swing.ButtonGroup buttonGroupView;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemBox;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemDefault;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemFog;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemSphere;
    private javax.swing.JMenu jMenuFog;
    private javax.swing.JMenuItem jMenuItemBackgroundColor;
    private javax.swing.JMenuItem jMenuItemFogColor;
    private javax.swing.JMenuItem jMenuItemResetView;
    private javax.swing.JMenu jMenuOptions;
    private javax.swing.JMenu jMenuShape;
    private javax.swing.JMenu jMenuView;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JLabel meanLabel;
    // End of variables declaration//GEN-END:variables
}
