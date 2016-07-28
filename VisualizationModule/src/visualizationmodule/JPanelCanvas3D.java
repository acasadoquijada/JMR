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

    private TransformGroup camara;
    
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
     
        mouseBehaviour();
        
        SpiralVisualization sv = new SpiralVisualization(75);
        
        objRoot = sv.createScene();
                
        createBackground();
        
	return objRoot;
    } 
     
    private void createBackground(){
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

    //

    private void mouseBehaviour(){
        
        
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

