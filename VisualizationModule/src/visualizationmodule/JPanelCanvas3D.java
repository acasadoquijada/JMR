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
import java.util.ArrayList;
import javax.media.j3d.*;
import javax.swing.JPanel;
import javax.vecmath.Point3d;
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
    
    //private final double defaultZoom = 3.65418546;
    
    private PickCanvas pickCanvas; 
    
    public BranchGroup spiralVisualization;
    public BranchGroup secuencialVisualization;
    public BranchGroup roadVisualization;
    
    private ArrayList <BranchGroup> visualizationArray;
    
    public int currentVisualization;
    
    public static final int SECUENCIAL = 0;

    public static final int SPIRAL = 1;
    
    public static final int ROAD = 2;
    
    public TransformGroup tg ;
    
    
    
    // GUI
    
    private javax.swing.JPopupMenu popmenu;
    private javax.swing.JMenu menuVisualization;
    private javax.swing.JMenu menuOption;
    private javax.swing.JCheckBoxMenuItem itemSecuencial;
    private javax.swing.JCheckBoxMenuItem itemSpiral;
    private javax.swing.JCheckBoxMenuItem itemRoad;
    private javax.swing.JMenuItem itemResetPosition;
    private javax.swing.ButtonGroup buttonGroupVisualization;

    private void initializeVisualizations(){
        
        // Secuencial visualization
        SecuencialVisualization sv = new SecuencialVisualization(20);
        
        secuencialVisualization = sv.createScene();
        secuencialVisualization.setCapability(BranchGroup.ALLOW_DETACH);
 
        
        // Spiral visualization
        
        tg = new TransformGroup();
        
        SpiralVisualization vs = new SpiralVisualization(75);
        
        spiralVisualization = vs.createScene();
        spiralVisualization.setCapability(BranchGroup.ALLOW_DETACH);

        tg = vs.getTransformGroup();
        
        
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
                
            case ROAD:
                
                simpleU.getLocale().replaceBranchGraph(roadVisualization, visualizationArray.get(v));
                
                currentVisualization = v; 
                
                
                
        }
     
    }
    
    public void resetCameraPosition(){
        
        this.simpleU.getViewingPlatform().setNominalViewingTransform();

///        this.camara = simpleU.getViewingPlatform().getViewPlatformTransform();

      //  this.vcamara = new Vector3d();
       // this.vcamara.z = this.defaultZoom;
        
    }
        
    
    private void initializeGUI(){
        
        popmenu = new javax.swing.JPopupMenu();
        menuVisualization = new javax.swing.JMenu();   
        menuOption = new javax.swing.JMenu();   
        
        buttonGroupVisualization = new javax.swing.ButtonGroup();

        itemSecuencial = new javax.swing.JCheckBoxMenuItem();
        itemSpiral = new javax.swing.JCheckBoxMenuItem();
        itemRoad = new javax.swing.JCheckBoxMenuItem();
        itemResetPosition = new javax.swing.JMenuItem();

        buttonGroupVisualization.add(itemSecuencial);
        buttonGroupVisualization.add(itemSpiral);
        buttonGroupVisualization.add(itemRoad);
        
        itemSpiral.setSelected(true);
        
        
        menuVisualization.setText("Visualizations");
        menuOption.setText("Options");
        
        itemSecuencial.setText("Secuencial");
        itemSpiral.setText("Spiral");
        itemRoad.setText("Road");
        itemResetPosition.setText("Reset position");
        
        menuVisualization.add(itemSecuencial);
        menuVisualization.add(itemSpiral);
        menuVisualization.add(itemRoad);
        
        menuOption.add(itemResetPosition);
        
        popmenu.add(menuVisualization);
        popmenu.add(menuOption);
        
        
        
        
    }
    
    public JPanelCanvas3D() {
        
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        initializeVisualizations();
        initializeGUI();
        
        this.vcamara    = new Vector3d();
        this.tcamara    = new Transform3D();
        
       // 
        canvas3D = new Canvas3D(config);
        add("Center", canvas3D);
        
        simpleU = new SimpleUniverse(canvas3D);
            
        this.camara = simpleU.getViewingPlatform().getViewPlatformTransform();
        
        //simpleU.getViewingPlatform().setNominalViewingTransform();

        currentVisualization = SPIRAL;
        
        //simpleU.addBranchGraph(spiralVisualization);
        
        //
        
        
        
        
        
        
        
        //
            BoundingSphere bounds =
        new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 20000.0);
     
      OrbitBehavior m_orbit = new OrbitBehavior(canvas3D, 
        OrbitBehavior.REVERSE_ALL);
      
      
       
       
        m_orbit.setRotationCenter(new Point3d(1.0,0,0));
        m_orbit.setSchedulingBounds(bounds);
        m_orbit.setZoomFactor(-1d);
        this.simpleU.getViewingPlatform().setViewPlatformBehavior(m_orbit);
        
        resetCameraPosition();
    //    PickRotateBehavior behavior = new PickRotateBehavior(roadVisualization, canvas3D, bounds);
       // roadVisualization.addChild(behavior);

       // this.camara = this.simpleU.getViewingPlatform().get;
        

        
            Pick p= new Pick(canvas3D,spiralVisualization);
            p.setSchedulingBounds ( new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 50000000f) ) ;
            spiralVisualization.addChild(p);

     /*  
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);        
   
        
        ProcesaRaton pc = new ProcesaRaton(tg);
        
        
        pc.setSchedulingBounds(bounds);
        
        
        tg.addChild(pc);
       */ 
        //ProcesaRaton pc = new ProcesaRaton
        
        /*
        MouseRotate behavior1 = new MouseRotate(); 
        behavior1.setTransformGroup(tg);
        behavior1.setSchedulingBounds(bounds); 
        visualizationArray.get(currentVisualization).addChild(behavior1);        
        
 
        // Create the zoom behavior node 
        MouseZoom behavior2 = new MouseZoom(); 
         behavior2.setTransformGroup(tg);
        visualizationArray.get(currentVisualization).addChild(behavior2); 
        behavior2.setSchedulingBounds(bounds); 
 
        // Create the translate behavior node 
        MouseTranslate behavior3 = new MouseTranslate(); 
         behavior3.setTransformGroup(tg);
        visualizationArray.get(currentVisualization).addChild(behavior3); 
        behavior3.setSchedulingBounds(bounds); 
        */
  
        
        simpleU.addBranchGraph(visualizationArray.get(currentVisualization));
        mouseBehaviour();
        popmenuBehaviour();
    }

    private void mouseBehaviour(){
        
        
        this.canvas3D.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        
        this.canvas3D.addMouseListener(new java.awt.event.MouseAdapter() {
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt){
                if(evt.getClickCount() >= 2){
                    showPopup(evt);
                }
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                if(evt.getClickCount() >= 2){
                    showPopup(evt);
                }
            }
            
        });

    }
    
    private void popmenuBehaviour(){
        
        itemSecuencial.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                itemSecuencialMousePressed(evt);
            }
        });
        
         itemSpiral.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                itemSpiralMousePressed(evt);
            }
        });    
         
        itemRoad.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                itemRoadMousePressed(evt);
            }
        });  
        
        itemResetPosition.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                itemResetPositionMousePressed(evt);
            }
        });
    
    }

    private void itemSecuencialMousePressed(java.awt.event.MouseEvent evt){
        
        this.setVisualization(SECUENCIAL);
        
    }
    
    private void itemSpiralMousePressed(java.awt.event.MouseEvent evt){
        
        this.setVisualization(SPIRAL);
    }
    
    private void itemRoadMousePressed(java.awt.event.MouseEvent evt){
        
        this.setVisualization(ROAD);
    }
    
    private void itemResetPositionMousePressed(java.awt.event.MouseEvent evt){
        
        this.resetCameraPosition();
    }
    
    private void showPopup(java.awt.event.MouseEvent evt){
        if (evt.isPopupTrigger()) {
            popmenu.show(evt.getComponent(),
                    evt.getX(), evt.getY());
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void formKeyPressed(java.awt.event.KeyEvent evt) {                                

        camara.getTransform(tcamara);
        tcamara.get(vcamara);  
        
        char key = evt.getKeyChar();
        
        float ang=0.0f;
        
        System.out.println(vcamara);
        switch(key){
            
            case 'w':
                ang += 0.1;
                
                tcamara.rotY(Math.toRadians(ang));
                
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
                
            case 'q':
                
                vcamara.x -= JPanelCanvas3D.MOVESPEED/2;
                vcamara.y -= JPanelCanvas3D.MOVESPEED/2;
                break;
                
            case 'e':
                
                vcamara.x += JPanelCanvas3D.MOVESPEED/2;
                vcamara.y += JPanelCanvas3D.MOVESPEED/2;
                break;                
                
        }
        
        
    //   tcamara.set(vcamara);
        camara.setTransform(tcamara);
        
        
    } 

    // Parte de eventos
   
} // end of class JPanelCanvas3D

