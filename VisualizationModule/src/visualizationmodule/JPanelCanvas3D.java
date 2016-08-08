/**
 * 
 */
package visualizationmodule;

/**
 * @author Alejandro
 *
 */
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.pickfast.PickCanvas;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.universe.*;
import java.awt.Color;
import java.util.ArrayList;
import javax.media.j3d.*;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;


@SuppressWarnings("serial")

public class JPanelCanvas3D extends JPanel {

    private SimpleUniverse simpleU;
    
    private TransformGroup camara;
    
    private Canvas3D canvas3D;
    
    private Transform3D tcamara;
    private Vector3d vcamara;
    
    private static final double MOVESPEED  = 0.5;
    
   // private final double defaultZoom = 2.414213562373095 ;
    
    private final double defaultZoom = 3.65418546;
    
    private PickCanvas pickCanvas; 
    
    public BranchGroup spiralVisualization;
    public BranchGroup secuencialVisualization;
    public BranchGroup roadVisualization;
    
    private ArrayList <BranchGroup> visualizationArray;
    
    public int currentVisualization;
    
    public static final int SECUENCIAL = 0;

    public static final int SPIRAL = 1;
    
    public static final int ROAD = 2;
    

    private void initializeVisualizations(){
        
        // Secuencial visualization
        SecuencialVisualization sv = new SecuencialVisualization(6);
        
        secuencialVisualization = sv.createScene();
        secuencialVisualization.setCapability(BranchGroup.ALLOW_DETACH);
 
        
        // Spiral visualization
        SpiralVisualization vs = new SpiralVisualization(75);
        
        spiralVisualization = vs.createScene();
        spiralVisualization.setCapability(BranchGroup.ALLOW_DETACH);

        
        // Road visualization
        RoadVisualization rv = new RoadVisualization(9);
        
        roadVisualization = rv.createScene();
        roadVisualization.setCapability(BranchGroup.ALLOW_DETACH);
        roadVisualization.setCapability(TransformGroup.ENABLE_PICK_REPORTING); 
        roadVisualization.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        roadVisualization.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); 

        
        // Visualization array
        visualizationArray = new ArrayList<>();
                
        visualizationArray.add(secuencialVisualization);
        visualizationArray.add(spiralVisualization);
        visualizationArray.add(roadVisualization);
        
        
        
  
        
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

///        this.camara = simpleU.getViewingPlatform().getViewPlatformTransform();

      //  this.vcamara = new Vector3d();
       // this.vcamara.z = this.defaultZoom;
        
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

        currentVisualization = SPIRAL;
        
        //simpleU.addBranchGraph(spiralVisualization);
        
        //
        
        
        
        
        
        
        
        //
            BoundingSphere bounds =
        new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 200.0);
     
      OrbitBehavior m_orbit = new OrbitBehavior(canvas3D, 
        OrbitBehavior.REVERSE_ALL);
       
       
 
        m_orbit.setSchedulingBounds(bounds);
        m_orbit.setZoomFactor(-1d);
        this.simpleU.getViewingPlatform().setViewPlatformBehavior(m_orbit);
        
        resetCameraPosition();
    //    PickRotateBehavior behavior = new PickRotateBehavior(roadVisualization, canvas3D, bounds);
       // roadVisualization.addChild(behavior);

       // this.camara = this.simpleU.getViewingPlatform().get;
        

        
        
        
      
    /*    MouseRotate behavior1 = new MouseRotate(canvas3D); 
        roadVisualization.addChild(behavior1); 
        behavior1.setSchedulingBounds(bounds); 
 
        // Create the zoom behavior node 
        MouseZoom behavior2 = new MouseZoom(canvas3D); 
        roadVisualization.addChild(behavior2); 
        behavior2.setSchedulingBounds(bounds); 
 
        // Create the translate behavior node 
        MouseTranslate behavior3 = new MouseTranslate(canvas3D); 
        roadVisualization.addChild(behavior3); 
        behavior3.setSchedulingBounds(bounds); */
        
  
        
        simpleU.addBranchGraph(spiralVisualization);

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

