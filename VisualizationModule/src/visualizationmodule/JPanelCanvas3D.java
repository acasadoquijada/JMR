/**
 * 
 */
package visualizationmodule;

/**
 * @author Alejandro
 *
 */
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.applet.MainFrame; 
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;
import static java.awt.Color.RED;
import static java.awt.Color.red;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.media.j3d.*;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

//   HelloJava3Dc renders a single, rotating cube.  

@SuppressWarnings("serial")

public class JPanelCanvas3D extends JPanel {

    
    private ArrayList<Transform3D> transformadas;
    private ArrayList<TransformGroup> grupo_transformadas;
    private Transform3D rotate;
    private Transform3D tempRotate;
    private Transform3D transformada; 
    private Transform3D t_esfera;
    private TransformGroup objSpin;
    private TransformGroup camara;
   // private TransformGroup tg;
    private TransformGroup tg_esfera;
    
    private Canvas3D canvas3D;
    
    private BranchGroup objRoot;

    private float x,y;
    
    private Transform3D tcamara;
    private Vector3d vcamara;
    
    private static final double TURNSPEED = 0.005;
    private static final double MOVESPEED  = 0.5;
    private int lastX = 0;
    private int lastY = 0;
    
    public BranchGroup createSceneGraph() {
     
        comportamientoRaton();

        /*inicializarArrayTransformadas(2);
        
        inicializarArrayGrupoTransformadas(2);
        
	// Create the root of the branch graph
	objRoot = new BranchGroup();
        
        Vector3f vector = new Vector3f(0.0f,0.0f,0.0f);

        CargaImagenes ci = new CargaImagenes(2);
  
        for(int i = 0; i < 2; i++){
            dibujarImagen(ci.getImagenes().get(1),vector,i);
            vector.x += 1.5f;
        }
        
        
        
        */
        
        //SecuencialVisualization vs = new SecuencialVisualization();
        
        SpiralVisualization sv = new SpiralVisualization();
        
        objRoot = sv.createScene();
        // objRoot = vs.crearEscena(null);
                
        crearFondo();
        
        
	return objRoot;
    } // end of CreateSceneGraph method of JPanelCanvas3D

    private void inicializarArrayTransformadas(int tam){
        
        Transform3D t;
        transformadas = new ArrayList();
        
        for(int i = 0;i < tam; i++){
            t = new Transform3D();    
            transformadas.add(t);
        }
        
    }
    
    private void inicializarArrayGrupoTransformadas(int tam){
        
        TransformGroup tg;
        grupo_transformadas = new ArrayList();

        for(int i = 0;i < tam; i++){
            tg = new TransformGroup();    
            grupo_transformadas.add(tg);
        }
        
    }
    
    private void dibujarImagen(Texture tex, Vector3f pos, int indice){
        
        Transform3D trans = transformadas.get(indice);
        TransformGroup tg = grupo_transformadas.get(indice);
                
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
        //tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        tg.setTransform(trans);
        
        tg.addChild(b);
        
        objRoot.addChild(tg);

    }
    /*
    private void crearSoporteTextura(){
        
        Appearance ap = new Appearance();
        
        Color3f red = new Color3f(0.7f, .15f, .15f);
        ColoringAttributes unColorPlano = new ColoringAttributes
        ( red , ColoringAttributes.SHADE_GOURAUD) ;
        
        //ap.setColoringAttributes(unColorPlano);
        
            //Cargamos la textura a usar
        
        TextureLoader loader = new TextureLoader
        ("C:\\Users\\Alejandro\\Desktop\\pug.png",
                new Container());
        
        Texture texture = loader.getTexture();
        
        texture.setBoundaryModeS(Texture.WRAP);

        texture.setBoundaryModeT(Texture.WRAP);
        
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        ap.setTexture(texture);

        ap.setTextureAttributes(texAttr);    
        
        // Transparencia loco
        
       TransparencyAttributes ta = new TransparencyAttributes();
        ta.setTransparencyMode( TransparencyAttributes.NICEST );
        ta.setTransparency(.5f);
        
        ap.setTransparencyAttributes(ta);

        
        int primflags = Primitive.GENERATE_NORMALS +

        Primitive.GENERATE_TEXTURE_COORDS; 
           
        Box b = new Box(0.5f,0.5f,0f,primflags,ap);

        
        t_esfera = new Transform3D();
        
        tg_esfera = new TransformGroup();
        
        tg_esfera.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        tg_esfera.addChild(b);
        
        objRoot.addChild(tg_esfera);
    }
     */
    
    private void crearMasImagenes(){
        
        Appearance ap = new Appearance();
        
        Color3f red = new Color3f(0.7f, .15f, .15f);
        ColoringAttributes unColorPlano = new ColoringAttributes
        ( red , ColoringAttributes.SHADE_GOURAUD) ;
        
        TextureLoader loader = new TextureLoader
        ("C:\\Users\\Alejandro\\Desktop\\pug.png",
                new Container());
        
        Texture texture = loader.getTexture();
        
        texture.setBoundaryModeS(Texture.WRAP);

        texture.setBoundaryModeT(Texture.WRAP);
        
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        ap.setTexture(texture);

        ap.setTextureAttributes(texAttr);    
        
        int primflags = Primitive.GENERATE_NORMALS +

        Primitive.GENERATE_TEXTURE_COORDS; 
           
        Box b = new Box(0.5f,0.5f,0f,primflags,ap);

        
        Transform3D t3d = new Transform3D();
        TransformGroup tg3d = new TransformGroup();
        /*
        t_esfera = new Transform3D();
        
        tg_esfera = new TransformGroup();*/
        
        Vector3f vector = new Vector3f( 2.5f, 1.0f, -8.8f);
        
        t3d.setTranslation(vector);
        
        tg3d.setTransform(t3d);
        
        tg3d.addChild(b);

        objRoot.addChild(tg3d);
        
        // Crear la caja
        // Ponerle la textura
        // Posicionarla
        // Agerarla al grafo de la escena
        
    }
    
    /*
    private void crearEsfera(){

        Sphere esfera = new Sphere(0.5f,64,64);
        
        Appearance ap = new Appearance(); 
        
        Color3f col = new Color3f(0.0f, 0.0f, 1.0f);
        
        PolygonAttributes polyAttribs = new PolygonAttributes
        ( PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 10 );
        
        Color3f red = new Color3f(0.7f, .15f, .15f);
        ColoringAttributes unColorPlano = new ColoringAttributes
        ( red , ColoringAttributes.SHADE_GOURAUD) ;
        
        
        TransparencyAttributes t_attr =

        new TransparencyAttributes(TransparencyAttributes.BLENDED,0.5f,

        TransparencyAttributes.BLEND_SRC_ALPHA,

        TransparencyAttributes.BLEND_ONE);

        ap.setTransparencyAttributes( t_attr );
        
        ap.setColoringAttributes(ca);
        
        
        ap.setColoringAttributes(unColorPlano);
        ap.setPolygonAttributes(polyAttribs);
        
        esfera.setAppearance(ap);
        
    
        t_esfera = new Transform3D();
        
        tg_esfera = new TransformGroup();
        
        Vector3f pos_esfera = new Vector3f(-.2f,.1f , -.4f);
        
        t_esfera.setTranslation(pos_esfera);
        
        tg_esfera.setTransform(t_esfera);
        
        tg_esfera.addChild(esfera);
        
        objRoot.addChild(tg_esfera);

    }
    */
    private void crearLuz(){
        Color3f color_luz = new Color3f(.1f, 1.4f, .1f); // green light

        BoundingSphere bounds = 
          new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);

        DirectionalLight light1 = new DirectionalLight(color_luz, light1Direction);

        light1.setInfluencingBounds(bounds);

        objRoot.addChild(light1);  
        
    }
     
    private void crearFondo(){
        Background background = new Background(new Color3f(1.000f, 0.980f, 0.980f));
        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000);
        background.setApplicationBounds(sphere);
        objRoot.addChild(background);
    }
    
    public JPanelCanvas3D() {
        x = y = 1f;
        
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        canvas3D = new Canvas3D(config);
        add("Center", canvas3D);

        BranchGroup scene = createSceneGraph();
	scene.compile();

        // SimpleUniverse is a Convenience Utility class
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

	// This will move the ViewPlatform back a bit so the
	// objects in the scene can be viewed.
        
        this.camara = simpleU.getViewingPlatform().getViewPlatformTransform();
        
        simpleU.getViewingPlatform().setNominalViewingTransform();

        //Axis axis = new Axis();
        
        simpleU.addBranchGraph(scene);
        //simpleU.addBranchGraph(axis.ejes());

        
//        canvas3D.addKeyListener(this);
        //mouseListener();
        
        //MouseListenerCanvas3D ir = new MouseListenerCanvas3D();
        
        //canvas3D.addMouseListener(ir);
    }
    

    
    public static void main(String[] args) {
       // Frame frame = new MainFrame(new JPanelCanvas3D(), 256, 256);
    } // end of main (method of HelloJava3D)

    //

    private void comportamientoRaton(){
        
        
        this.canvas3D.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        
        this.canvas3D.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        
        
        this.canvas3D.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            @Override
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
    }
    
    private void formMousePressed(java.awt.event.MouseEvent evt){
     
    }
    
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {                                     
        if(this.tcamara == null){
            this.vcamara    = new Vector3d();
            this.tcamara    = new Transform3D();
            //Making it the same as used
            camara.getTransform(tcamara);
            tcamara.get(vcamara);
        }
        
        int rotacion = evt.getWheelRotation();
        
        if(rotacion > 0){
            vcamara.z -= JPanelCanvas3D.MOVESPEED;
        }
        
        else{
            vcamara.z += JPanelCanvas3D.MOVESPEED;
        }
        

        tcamara.set(vcamara);

        camara.setTransform(tcamara);
    }
    
    private void formMouseDragged(java.awt.event.MouseEvent evt){
        
        if(this.tcamara == null){
 
            //Creating the object we need
            this.tcamara = new Transform3D();
            this.vcamara    = new Vector3d();

            //Making it the same as used
            camara.getTransform(tcamara);
            tcamara.get(vcamara);
            }
 
        //Increasing or decreasing the x value of the Vector3d
        vcamara.x   += ( evt.getX() - this.lastX) * JPanelCanvas3D.TURNSPEED;
        this.lastX  =   evt.getX();
        
        vcamara.y   += ( evt.getY() - this.lastY) * JPanelCanvas3D.TURNSPEED;
        this.lastY  =   evt.getY();
        tcamara.set(vcamara);
        camara.setTransform(tcamara);
        
	}
    // Parte de eventos
   
} // end of class JPanelCanvas3D

