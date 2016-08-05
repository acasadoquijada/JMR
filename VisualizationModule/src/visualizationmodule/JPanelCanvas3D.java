/**
 * 
 */
package visualizationmodule;

/**
 * @author Alejandro
 *
 */
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.pickfast.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.universe.*;
import java.awt.Point;
import java.util.ArrayList;
import javax.media.j3d.*;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


@SuppressWarnings("serial")

public class JPanelCanvas3D extends JPanel {

    private SimpleUniverse simpleU;
    
    private TransformGroup camara;
    
    private Canvas3D canvas3D;
    
    private Transform3D tcamara;
    private Vector3d vcamara;
    
    private static final double TURNSPEED = 0.01;
    private static final double MOVESPEED  = 0.5;
    
    private Point point;
    
    private Point initialPoint;
    
    private final double zoomLimitMin = 0.91;
    private final double zoomLimitMax = 31.91;
    private final double defaultZoom = 2.414213562373095 ;
    
    
    private PickCanvas pickCanvas; 
    
    public BranchGroup spiralVisualization;
    public BranchGroup secuencialVisualization;
 
    private ArrayList <BranchGroup> visualizationArray;
    
    public int currentVisualization;
    
    public static final int SECUENCIAL = 0;

    public static final int SPIRAL = 1;
    

    private void initializeVisualizations(){
        
        // Secuencial visualization
        SecuencialVisualization sv = new SecuencialVisualization(6);
        
        secuencialVisualization = sv.createScene();
        secuencialVisualization.setCapability(BranchGroup.ALLOW_DETACH);

        
        createBackground(secuencialVisualization);
        
        
        // Spiral visualization
        SpiralVisualization vs = new SpiralVisualization(75);
        
        spiralVisualization = vs.createScene();
        spiralVisualization.setCapability(BranchGroup.ALLOW_DETACH);

        createBackground(spiralVisualization);
        
        visualizationArray = new ArrayList<>();
                
        visualizationArray.add(secuencialVisualization);
        visualizationArray.add(spiralVisualization);
  
        
    }

    private void createBackground(BranchGroup bg){
        Background background = new Background(new Color3f(1.000f, 0.980f, 0.980f));
        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000);
        background.setApplicationBounds(sphere);
        bg.addChild(background);
    }
    
    public void setVisualization(int v){
        
        
        switch(currentVisualization){
            
            case SECUENCIAL:
                
                simpleU.getLocale().replaceBranchGraph(secuencialVisualization, visualizationArray.get(v));
                
                currentVisualization = v;
                
                break;
            
            case SPIRAL:
                
                simpleU.getLocale().replaceBranchGraph(spiralVisualization, visualizationArray.get(v));
                
                currentVisualization = v;   
                
                break;
                
                
        }
     
    }
    
    public void resetCameraPosition(){
        
        this.simpleU.getViewingPlatform().setNominalViewingTransform();

        this.camara = simpleU.getViewingPlatform().getViewPlatformTransform();

        this.vcamara = new Vector3d();
        this.vcamara.z = this.defaultZoom;
        
    }
        
    
    
    public JPanelCanvas3D() {
        
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        initializeVisualizations();
        
        this.vcamara    = new Vector3d();
        this.tcamara    = new Transform3D();
        
        
        canvas3D = new Canvas3D(config);
        add("Center", canvas3D);
        
        simpleU = new SimpleUniverse(canvas3D);
        
        //this.camara = simpleU.getViewingPlatform().getViewPlatformTransform();
        
        //simpleU.getViewingPlatform().setNominalViewingTransform();

        currentVisualization = 1;
        
        //simpleU.addBranchGraph(spiralVisualization);
        
        
       OrbitBehavior m_orbit = new OrbitBehavior(canvas3D, 
        OrbitBehavior.REVERSE_ALL | OrbitBehavior.STOP_ZOOM);
       
       
        BoundingSphere bounds =
        new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 200.0);
  
        m_orbit.setSchedulingBounds(bounds);
        m_orbit.setZoomFactor(-1d);
        this.simpleU.getViewingPlatform().setViewPlatformBehavior(m_orbit);
        this.camara = this.simpleU.getViewingPlatform().getViewPlatformTransform();
        
        simpleU.addBranchGraph(spiralVisualization);
       
       
       
       
       
       
       
       
       
     mouseBehaviour();

    }

    private void mouseBehaviour(){
        
        
        this.canvas3D.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        
        
        
    }
    
    private void formKeyPressed(java.awt.event.KeyEvent evt) {                                

        camara.getTransform(tcamara);
        tcamara.get(vcamara);  
        
        char key = evt.getKeyChar();
        
        switch(key){
            
            case 'w':
                
                vcamara.y += JPanelCanvas3D.MOVESPEED/2;
                                
                break;
                
            case 's':
                
                vcamara.y -= JPanelCanvas3D.MOVESPEED/2;
                break;
 
            case 'a':
                
                vcamara.x -= JPanelCanvas3D.MOVESPEED/2;
                break;
                
            case 'd':
                
                vcamara.x += JPanelCanvas3D.MOVESPEED/2;
                break;
        }
        
        
        tcamara.set(vcamara);
        camara.setTransform(tcamara);
        
        
    } 

    // Parte de eventos
   
} // end of class JPanelCanvas3D

