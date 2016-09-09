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
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.swing.JColorChooser;
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

/**
 * Clase abstracta que incorpora los métodos y configuraciones
 * necesarias para que las clases hijas puedan mostrar las imágenes
 * en el mundo tridimensional.
 * @since version 1.00
 */

public abstract class Abstract3DPanel extends javax.swing.JPanel {
    
    /**
     * Objeto SimpleUniverse que contiene todo lo relacionado con el mundo 3D.
     * 
     * @since version 1.00
     */
    protected SimpleUniverse simpleU;

    /**
     * Objeto Canvas3D usado para la visualización del mundo tridimensional.
     * 
     * @since version 1.00
     */
    
    protected Canvas3D canvas3d;
    
    /**
     * Objeto ResultList de la JMR en el que 
     * se almacena la información obtenida de los descriptores.
     * 
     * @since version 1.00
     */            
    protected ResultList results;
    
    /**
     * Objeto BranchGroup en el que se distribuyen las diferentes imágenes.
     * 
     * @since version 1.00
     */
    
    protected BranchGroup scene;
    
    /**
     * Objeto BranchGroup en el cual se dibuja el fondo del entorno
     * así como del suelo.
     * 
     * @since version 1.00
     */
   
    protected BranchGroup backgroundScene;
    
    /**
     * Objeto BranchGroup encargado de almacenar la niebla exponencial.
     * 
     * @since version 1.00
     */
    
    protected BranchGroup fogBranch;
    
    /**
     * Objeto BranchGroup cuya función es almacenar 
     * la visualización de los ejes de coordenadas.
     * 
     * @since version 1.00
     */
    
    protected BranchGroup axis;
    
    /**
     * Objeto BranchGroup en el que se pintan los 
     * indicadores de posición de las imágenes.
     * 
     * @since version 1.00
     */
    
    protected BranchGroup position;
       
    /**
     * Objeto Brackground que representa el fondo del entorno tridimensional.
     * 
     * @since version 1.00
     */
    private Background background;
       
    /**
     * Objeto OrbitBehavior encargado de la interacción con el ratón.
     * 
     * @since version 1.00
     */
    
    private OrbitBehavior m_orbit;
    
    /**
     * Objeto ExponentialFog que representa la niebla exponencial.
     * 
     * @since version 1.00
     */
    
    private ExponentialFog myFog;
    
    
    /**
     * Objeto booleano para indicar si la niebla se encuentra activa o no.
     * 
     * @since version 1.00
     */
    protected boolean fogActive;

    /**
     * Objeto booleano para indicar si se quiere 
     * pintar los indicadores de posición de las imágenes.
     * 
     * @since version 1.00
     */
    
    protected boolean positionActive;

    /**
     * Objeto booleano para indicar si se desea 
     * dibujar el plano que simula al suelo o no.
     * 
     * @since version 1.00
     */
    
    protected boolean planeActive;
    
    /**
     * Objeto booleano para indicar si se desea 
     * dibujar los ejes de coordenadas.
     * 
     * @since version 1.00
     */    
    
    protected boolean axisActive;
    
    /**
     * Entero que indica el tipo de visualización.
     * Se usa para tratar con el tamaño de las imágenes
     * 
     * @since version 1.00
     */
    
    protected int TYPE;
    
    /**
     * Entero que indica que el tipo de visualización es secuencial. 
     * @since version 1.00
     */
    
    protected static final int SECUENCIAL = 0;
    
    /**
     * Entero que indica que el tipo de visualización es espiral.
     * @since version 1.00
     */
    
    protected static final int SPIRAL = 1;
    
    /**
     * Entero que indica que el tipo de visualización es camino.
     * @since version 1.00
     */
    
    protected static final int PATH = 2;
    
    /**
     * Entero que indica la primitiva en la que se van a dibujar las imágenes.
     * Esta primitiva puede ser un cubo o una esfera
     * @since version 1.00
     */
    
    protected int SHAPE;
   
    /**
     * Entero que indica que la primitiva es un cubo.
     * @since version 1.00
     */
    
    protected static final int BOX = 3;
    
    /**
     * Entero que indica que la primitiva es una esfera.
     * @since version 1.00
     */
    
    protected static final int SPHERE = 4;
    
    /**
     * Objeto Dimension para indicar el tamaño del panel.
     * @since version 1.00
     */
    
    protected final static Dimension DEFAULT_COMMON_SIZE = new Dimension(100,100);

    
    /**
     * Objeto TransformGroup asociado a la cámara del entorno tridimensional.
     * @since version 1.00
     */
        
    private TransformGroup camara;
    
    /**
     * Objeto Transform3D asociado al control de la cámara del entorno tridimensional.
     * @since version 1.00
     */
    
    private Transform3D tcamara;
    
    /**
     * Objeto Vector3d asociado a la posición de la cámara del entorno tridimensional.
     * @since version 1.00
     */
    
    private Vector3d vcamara;
    
    /**
     * Entero asociado al tamaño de las imágenes en la visualización espiral.
     * @since version 1.00
     */
    
    private int spiralIndex;
    

    /**
     * Constructor por defecto.
     * Se encarga de instanciar todo lo necesario para la creación e interacción
     * del mundo tridimensional
     * 
     * @since version 1.00
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
  

    /**
     * Constructor con parámetros.
     * Este constructor se encarga de obtener la información necesaria de los
     * descriptores y almacenarla llamando al método add
     * 
     * @param list Objeto ResultList con la información sobre los descriptores.
     * @since version 1.00
     */
    
    public Abstract3DPanel(ResultList list){
        this();
        if (list!=null){          
            add(list);
        }    

    }
    
    /**
     * Almacena la información de los descriptores.
     * Este método almacena la información obtenida en el parámetro list.
     * También llama a una serie de métodos para crear la escena. 
     * @param list Objeto ResultList con la información sobre los descriptores.
     * @since version 1.00
     */
    
    
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
    
    /**
     * Añade la interacción por ratón.
     * Se encarga de añadir la interacción por ratón a la vista que asi lo desee.
     * 
     * Rotación: Arrastrar el ratón con el botón izquierdo pulsado.
     * Desplazamiento: Arrastrar el ratón con el botón derecho pulsado.
     * Zoom: Utilizar la rueda del ratón.
     *
     * @param rotX booleano que si indica si se desea rotar sobre el eje x.
     * @param rotY booleano que si indica si se desea rotar sobre el eje y.
     * @since version 1.00
     */
    
    
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
    
    /**
     * Añade la interacción por teclado.
     * Se encarga de añadir la interacción por teclado a la vista que asi lo desee.
     * Rotación izquierda: Flecha izquierda.
     * Rotación derecha: Flecha derecha.
     * Acercar zoom: Flecha arriba.
     * Alejar zoom: Flecha abajo.
     * Rotación arriba: PgUp.
     * Rotación abajo: PgDn.
     * Desplazamiento izquierda: Alt + flecha izquierda.
     * Desplazamiento derecha: Alt + flecha derecha.
     * Desplazamiento arriba: Alt + PgUp.
     * Desplazamiento abajo: Alt + PgDn.
     * @since version 1.00
     */
    
    protected void keyControl(){

        KeyNavigatorBehavior navegacion = new KeyNavigatorBehavior(camara);
        BoundingSphere bounds =
        new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 12000.0);
        
        navegacion.setSchedulingBounds(bounds);
        
        backgroundScene.addChild(navegacion);
        
        
    }
    
    /**
     * Posibilita la obtención de información en el mundo tridimensional.
     * Este método añade al mundo 3D un comportamiento necesario para obtener
     * información de cada una de las imágenes mostradas usando el ratón
     * @since version 1.00
     */
    
    
    
    private void mouseOver(){
        
    MouseOverBehavior mouseOver = 
                new MouseOverBehavior(canvas3d, scene);
              
        mouseOver.setSchedulingBounds (new BoundingSphere (new Point3d (),
        1000000));
        
        scene.addChild(mouseOver);
    }
    
    /**
     * Crea la niebla exponencial.
     * @since version 1.00
     */
    
    private void createFog(){
        myFog = new ExponentialFog();
        myFog.setColor( new Color3f( 0.8f, 0.8f, 0.8f ) );
        myFog.setDensity(0.1f);
        
        myFog.setCapability(ExponentialFog.ALLOW_COLOR_WRITE);
        
        BoundingSphere myBounds = new BoundingSphere(new Point3d( ), 10000.0 );
        myFog.setInfluencingBounds(myBounds);
        
        fogBranch.addChild(myFog);
        
    }
    
 
    /**
     * Devuelve un BufferedImage dado un índice.
     * @param index entero que indica la imagen.
     * @return La imagen asociada a ese índice.
     * @since version 1.00
     */
    public BufferedImage getImage(int index){
        
        ResultMetadata r = (ResultMetadata)this.results.get(index);
        
        BufferedImage img = (BufferedImage) r.getMetadata();
        
        return img;
        
    }

    /**
     * Devuelve un BufferedImage dado un ResultMetada.
     * @param r objeto ResultMetada del cual se quiere obtener la imagen.
     * @return La imagen asociada al ResultMetada.
     * @since version 1.00
     */
    
    
    
    public BufferedImage getImage(ResultMetadata r){
             
        BufferedImage img = (BufferedImage) r.getMetadata();
                
        return img;
    }
    
    
    /**
     * Devuelve un Vector de la JMR dado un índice.
     * @param index entero que indica el vector.
     * @return El vector asociado a ese índice.
     * @since version 1.00
     */
    public Vector getVector(int index){
        
        ResultMetadata r = (ResultMetadata)this.results.get(index);
        
        Vector vec = (Vector)r.getResult();
        
        return vec;
    } 
    
    
    /**
     * Devuelve un ResultList dada una coordenada.
     * Método que devuelve un ResultList cuyos vectores poseen
     * el valor del descriptor indicado por el parámetro coordenada.
     * 
     * @param coor entero que indica la coordenada.
     * @return Objeto ResultList.
     * @since version 1.00
     */
    
    
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
    
    /**
     * Devuelve un ResultMetadata dado un índice.
     * @param index entero que indica el ResultMetadata.
     * @return ResultMetadata asociado a ese índice.
     * @since version 1.00
     */
    
    public ResultMetadata getResultMetada(int index){
       
        ResultMetadata r = (ResultMetadata)this.results.get(index);
   
        return r;
 
    }
    
    /**
     * Reinicia la posición de la cámara
     * @since version 1.00
     */
    
    protected void resetView(){
        cameraPos();
    }
    
    
    /**
     * Método abstracto que indica la manera en la que las diferentes imágenes
     * se distribuyen a lo largo del entorno.
     * @since version 1.00
     */
    
    protected abstract void createScene();
    
    
    /**
     * Método abstracto en el cual se debe indicar la interacción
     * deseada así como posibles modificaciones sobre esta
     * @since version 1.00
     */
    
    protected abstract void sceneControl();
    
    
    /**
     * Dibuja el indicador de posición para una imagen.
     * @param pos Posición de la imagen
     * @param index Número de la imagen
     * @since version 1.00
     */
    
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

    
    /**
     * Activa o desactiva la niebla exponencial.
     * @param activate booleano que indica la activación/desactivación de la niebla
     * @since version 1.00
     */
    
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
    
    /**
     * Devuelve el estado de la niebla.
     * @return booleano que indica si la niebla esta activa o no.
     * @since version 1.00
     */
    
    protected boolean fogActive(){
        
        return fogActive;
        
    }
    
    /**
     * Devuelve el estado de los indicadores de posición.
     * 
     * @return booleano que indica si los indicadores de posición
     * de las imágenes estan activados
     * @since version 1.00
     */
    
    protected boolean positionActive(){
        return positionActive;
    }
    
    /**
     * Método encargado de volver a dibujar los objetos del mundo tridimensional.
     * Es llamado cuando se cambia el tipo de primitiva
     * @since version 1.00
     */
    
    private void rePaint(){
        scene.detach();
        position.detach();

        scene.removeAllChildren();
        position.removeAllChildren();
        
        fogBranch.detach();
                
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
    

    /**
     * Dibuja la imagen asociada a rm en la posición indica por pos.
     * Del ResultMetada obtenido como parámetro se obtiene a parte de la propia
     * imagen, información sobre ella, necesaria para poder recuperarla cuando se
     * use el ratón en la visualización.
     * @param rm Objeto ResultMetada que contiene una imagen e información sobre ella
     * @param pos Objeto Vector3d que indica la posición de la imagen en el mundo 3D
     * @since version 1.00
     */
    
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
       
   /**
     * Crea un objeto BranchGroup en el que se dibujan los ejes de coordenadas.
     * @since version 1.00
     */ 
    
    
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

    /**
     * Crea un objeto Shape3D que representa el plano que simula el suelo.
     * @since version 1.00
     */ 
    
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
    
    /**
     * Establece la posición de la cámara.
     * @since version 1.00
     */ 
    
    protected void cameraPos(){
        
        camara.getTransform(tcamara);
        
        tcamara.get(vcamara);
        
        vcamara.x = 0.0;
        vcamara.y = 0.0;
        vcamara.z = 25.0;
        
        
        tcamara.set(vcamara);
        
        camara.setTransform(tcamara);
        
    }
    
    /**
     * Crea un objeto BranchGroup que representa el fondo del entorno 3D.
     * @since version 1.00
     */ 
    
    protected void createBackground(){
        
        this.backgroundScene = new BranchGroup();
        
        this.backgroundScene.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        
        this.background = new Background(new Color3f(0.4f, 0.6f, 1.0f));
        
        background.setCapability(Background.ALLOW_COLOR_WRITE);
            
        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 10000000);
        background.setApplicationBounds(sphere);
        
        this.backgroundScene.addChild(background);  


    }
    
    /**
     * Crea un objeto Geometry necesario para dibujar el plano.
     * @param A primer punto del plano.
     * @param B segundo punto del plano.
     * @param C tercer punto del plano.
     * @param D cuarto punto del plano.
     * @return Objeto geometría necesario para dibujar el plano.
     * @since version 1.00
     */ 
    
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
    
    /**
     * Método en el que se establecen los distintos listener para el objeto Canvas3D.
     * @since version 1.00
     */ 
    private void mouseBehaviour(){

        
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
    
    /**
     * Método encargado de mostrar el popMenu por pantalla.
     * @since version 1.00
     */ 
    
    private void popMenu(java.awt.event.MouseEvent evt){

        if (evt.isPopupTrigger()) {
            jPopupMenu.show(evt.getComponent(),
                    evt.getX(), evt.getY());
        }
    
    }

    /**
     * Clase que representa el comportamiento del 
     * ratón cuando se coloca sobre una imagen.
     * @param evt evento desencadenado al pulsar el menu item
     * @since version 1.00
     */

    public class MouseOverBehavior extends Behavior {

    /**
     * Objeto PickCanvas usado para la selección de primitivas mediante ratón
     * @since version 1.00
     */ 
    private com.sun.j3d.utils.picking.PickCanvas pickCanvas;

    /**
     * Objeto PickResult usado para la selección de primitivas mediante ratón
     * @since version 1.00
     */ 
    
    private PickResult pickResult;

    /**
     * Objeto BranchGroup sobre el que va a actuar este behavior
     * @since version 1.00
     */ 
    
    private BranchGroup dataBranchGroup;

    /**
     * Objeto Primitiva que representa la imagen seleccionada mediante el ratón
     * @since version 1.00
     */ 
    
    private Primitive p;
    
    /**
     * Objeto Canvas3D sobre el que va a actuar este behavior
     * @since version 1.00
     */ 
    
    
    private Canvas3D canvas;

        /**
         * Constructor que establece el objeto Canvas3D y 
         * BranchGroup sobre los que van a actuar este behavior
         * @param canvas Objeto Canvas3D
         * @param dataBranchGroup Objeto BranchGroup
         * @since version 1.00
         */
    
    public MouseOverBehavior (Canvas3D canvas, BranchGroup dataBranchGroup) {
        this.canvas = canvas;
        this.dataBranchGroup = dataBranchGroup;

        pickCanvas = new com.sun.j3d.utils.picking.PickCanvas (canvas, dataBranchGroup);
        pickCanvas.setTolerance (1.0f);
        pickCanvas.setMode (com.sun.j3d.utils.picking.PickCanvas.GEOMETRY_INTERSECT_INFO);


    }

    /**
     * Método initialize.
     * Se indica que condición provoca que se active este behavior.
     * En este caso se activa al mover el ratón
     * @since version 1.00
     */ 
    
    
    @Override
    public void initialize () {
        wakeupOn (new WakeupOnAWTEvent (MouseEvent.MOUSE_MOVED));
    }
    
    /**
     * Método que se activa cuando el behavior se despierta.
     * Obtiene la información de la imagen y la muestra en la esquina inferior
     * izquierda del panel
     * 
     * @param criteria Criterios que han provocado la activación del behavior
     * @since version 1.00
     */ 

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

    
    /**
     * Se encarga de reiniciar la vista.
     * @param evt evento desencadenado al pulsar el menu item
     * @since version 1.00
     */ 
    private void jMenuItemResetViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemResetViewActionPerformed

        resetView();
        
    }//GEN-LAST:event_jMenuItemResetViewActionPerformed

    
    /**
     * Establece como primitiva el cubo y vuelve a pintar la visualización.
     * @param evt evento desencadenado al pulsar el menu item
     * @since version 1.00
     */
    private void jCheckBoxMenuItemBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemBoxActionPerformed


        if(jCheckBoxMenuItemBox.isSelected()){
            SHAPE = Abstract3DPanel.BOX;
            
            rePaint();
        }


    }//GEN-LAST:event_jCheckBoxMenuItemBoxActionPerformed

    /**
     * Establece como primitiva la esfera y vuelve a pintar la visualización.
     * @param evt evento desencadenado al pulsar el menu item
     * @since version 1.00
     */
    
    private void jCheckBoxMenuItemSphereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemSphereActionPerformed

        if(jCheckBoxMenuItemSphere.isSelected()){
            SHAPE = Abstract3DPanel.SPHERE;

            rePaint();

        }
        
    }//GEN-LAST:event_jCheckBoxMenuItemSphereActionPerformed

    /**
     * Establece el color del fondo del entorno.
     * @param evt evento desencadenado al pulsar el menu item
     * @since version 1.00
     */
    
    private void jMenuItemBackgroundColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBackgroundColorActionPerformed
       
        Color auxColor;
        Color3f color;

        auxColor = JColorChooser.showDialog(this,
                    "Choose a color", null);

            if(auxColor != null){

             color = new Color3f(auxColor);

             this.background.setColor(color);

            }
             

    }//GEN-LAST:event_jMenuItemBackgroundColorActionPerformed

        /**
     * Establece el color de la niebla.
     * @param evt evento desencadenado al pulsar el menu item
     * @since version 1.00
     */
    
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

    
    /**
     * Activa/desactiva la niebla.
     * @param evt evento desencadenado al pulsar el menu item
     * @since version 1.00
     */
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
