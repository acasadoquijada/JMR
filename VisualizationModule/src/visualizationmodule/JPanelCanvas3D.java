/**
 * 
 */
package visualizationmodule;

/**
 * @author Alejandro
 *
 */
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
    
    private static final double TURNSPEED = 0.005;
    private static final double MOVESPEED  = 0.5;
    
    private int lastX = 0;
    private int lastY = 0;
    
    private Point point;
    
    private Point initialPoint;
    
    private final double zoomLimitMin = 0.91;
    private final double zoomLimitMax = 31.91;
    private final double defaultZoom = 2.414213562373095 ;
    
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

        this.vcamara.z = this.defaultZoom;
        
    }
        
    
    
    public JPanelCanvas3D() {
        
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        initializeVisualizations();
        
        canvas3D = new Canvas3D(config);
        add("Center", canvas3D);
        
        simpleU = new SimpleUniverse(canvas3D);
        
        this.camara = simpleU.getViewingPlatform().getViewPlatformTransform();
        
        simpleU.getViewingPlatform().setNominalViewingTransform();

        currentVisualization = 1;
        
        simpleU.addBranchGraph(spiralVisualization);
        
        
        mouseBehaviour();

    }

    private void mouseBehaviour(){
        
        
        this.canvas3D.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            
                        
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
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
        
        point = new Point();
        
        initialPoint = new Point();
        
        initialPoint = evt.getPoint();
     
    }
    
    private void formMouseReleased(java.awt.event.MouseEvent evt){
        
     /*   if(this.tcamara == null){
            this.vcamara    = new Vector3d();
            this.tcamara    = new Transform3D();
            //Making it the same as used
            camara.getTransform(tcamara);
            tcamara.get(vcamara);
          
        }
        
        
         vcamara.x   += ( evt.getX() - initialPoint.getX()) * JPanelCanvas3D.TURNSPEED;
        // this.lastY  =   evt.getY();
        tcamara.set(vcamara);
        camara.setTransform(tcamara);
        
        
        point = evt.getPoint();*/
        
        
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
        vcamara.x   += ( evt.getX() - initialPoint.getX()) * JPanelCanvas3D.TURNSPEED ;
        
        vcamara.y   += ( evt.getY() - initialPoint.getY()) * JPanelCanvas3D.TURNSPEED ;
        
        
        initialPoint =   evt.getPoint();
        
        tcamara.set(vcamara);
        camara.setTransform(tcamara);
        
	}
    
        
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {                                     
        if(this.tcamara == null){
            this.vcamara    = new Vector3d();
            this.tcamara    = new Transform3D();
            //Making it the same as used
            camara.getTransform(tcamara);
            tcamara.get(vcamara);
          
        }
        
        
        int rotation = evt.getWheelRotation();

        double truncatedVcamaraZ = (int)(vcamara.z * 100) / 100.0;
   
        if(rotation > 0 && truncatedVcamaraZ != zoomLimitMin){

            vcamara.z -= JPanelCanvas3D.MOVESPEED;
        }
        
        if(rotation < 0 && truncatedVcamaraZ != zoomLimitMax){
            vcamara.z += JPanelCanvas3D.MOVESPEED;     
        }

        tcamara.set(vcamara);

        camara.setTransform(tcamara);
    }
    // Parte de eventos
   
} // end of class JPanelCanvas3D

