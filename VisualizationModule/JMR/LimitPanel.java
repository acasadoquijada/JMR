/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmr.iu;

import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Geometry;
import javax.media.j3d.Shape3D;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

/**
 *
 * @author alejandro
 */
public class LimitPanel extends Abstract3DPanel {

    /**
     * Creates new form AveragePanel
     */
    
    private double limit;
    private JMenu limitMenu;
    private JMenuItem limitMenuItemValue;
    
    public LimitPanel() {
        super();

        createLimitPlanes(0.5);

        limit = 0.5;
        
        limitMenu = new JMenu();
        limitMenu.setText("Limit");
        
        limitMenuItemValue = new JCheckBoxMenuItem();
        limitMenuItemValue.setText("Limit value");
        
        limitMenu.add(limitMenuItemValue);

        popMenuExpansion(limitMenu);

        planeActive = true;
        
        eventControl();

    }
    
    private void updateText(String s){
        
        limitMenu.setText("A");
        
    }
    
    private void createLimitPlanes(double lim){

        Appearance ap = new Appearance();

        ColoringAttributes ca = new ColoringAttributes();
        Color3f c = new Color3f(0.43f, 0.502f, 0.565f);
        
        ca.setColor(c);
        
        ap.setColoringAttributes(ca);
    
        Geometry geo = createGeometry(new Point3f(-130.0f, 10000.5f, -10000.0f),
        new Point3f(-130.0f, 10000.5f, 10000.0f),
        new Point3f(-130.0f, -1000000.f, 10000.0f), new Point3f(-130.0f, -100000.0f, -10000.0f));
      
        
        Shape3D plane = new Shape3D(geo, ap);
        
        backgroundScene.addChild(plane);
        
        Geometry geo2 = createGeometry(new Point3f(10.0f, 100.5f, -10000.0f),
        new Point3f(10.0f, 100.5f, 10000.0f),
        new Point3f(10.0f, -1000000.f, 10000.0f), new Point3f(10.0f, -100000.0f, -10000.0f));
      

        Shape3D plane2 = new Shape3D(geo2, ap);
         
       // backgroundScene.addChild(plane2);


        
    }  

    
    @Override
    protected void createScene() {
        
        Vector3d v = new Vector3d();
       
        drawImage(getResultMetada(0), v);
        drawPosition(v, 0);
      
        Vector3d[] vArray = new Vector3d[results.size()];
        
        for(int i = 0; i < vArray.length; i++){
            vArray[i] = new Vector3d(0,0,0);
        }
        
        double newDist = 2.0;
        
        double leftZ = 2.0;
        double rightZ = 2.0;
        
        
        
        for(int i = 1; i < results.size(); i++){
            
        
            //obtengo el valor del primer descriptor
            
            double x = getVector(i).coordinate(0);
            
            // Lo trunco para saber si va a la izquierda o derecha
            
            double xTrunc = Math.floor(x * 10) / 10;
   
            int compX = Double.compare(xTrunc, limit);

                      
            // Obtengo y          
            double y = getVector(i).coordinate(1);  
            
            // Calculo z
            double z = ((x+y)/2) * 25;
            
            y =  getVector(i).coordinate(1) *120;
            
            v.y = y; 
            
            //negativo
            if(compX < 0){
                x *= 120;
                v.x = - x;
                z -= leftZ;
                leftZ += 2.0;
                
            }
            //positivo o igual
            else{
                x *= 60;
                v.x = x; 
                z -= rightZ;
                rightZ += 2.0;
            }

            //Salvo el valor de z
            v.z = z;
            
            
            /*
            boolean sigo = true;
            
            Vector3d auxV = new Vector3d();
            
            for(int j = 1; j <= i && sigo; j++) {
                
                // Comparar x y z, si el punto nuevo
                // está en el mismo x y aumentarle z
                // Puede ser que esten en distinto x y
                // con z similar, lo que provocaria que
                // aumentara la z sin necesidad
                
                // Cojo el vector

                auxV = vArray[j-1];
                
                // Calculo si el nuevo punto esta demasiado cerca
                
                double xDif = Math.abs(v.x - auxV.x);
                double yDif = Math.abs(v.y - auxV.y);
                double zDif = Math.abs(v.z - auxV.z);
                
                // Si no hay suficiente distancia, la aumento
                if(xDif < 0.7000 || yDif < 0.7000 || zDif < 0.7000){
                    
                    
                    v.z -= newDist;
                    
                    // Asi nos aseguramos de que todas
                    // las imagenes se vean correctamente
                    newDist+=2.0;
                    
                    sigo = false;
                   
                }
                
            
            }

            //Para volver a entrar en el bucle
            sigo = true;
            
            vArray[i]=v;
            
            System.out.println("Vector: " + v);*/
            drawImage(getResultMetada(i), v);
            drawPosition(v, i);

            
        }       
        
    }

    @Override
    protected void sceneControl() {

        keyControl();
        
    }
    
    
    private void eventControl(){
        
        limitMenuItemValue.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                limitMenuItemValueMousePressed(evt);
            }
        });
  
    }
    
    private void limitMenuItemValueMousePressed(MouseEvent evt){
            try{
                String b = JOptionPane.showInputDialog(null,
                        "Values in range: 0 - 1\nActual value: " 
                                + String.valueOf(limit),
                "Set new limit", JOptionPane.INFORMATION_MESSAGE  );

                double value = Double.parseDouble(b);

                double xTrunc = Math.floor(value * 10) / 10;

                if(0.1 <= xTrunc && xTrunc <= 1.0){
                    
                    limit = value;
                    
                    rePaint();  
                }

                else{
                JOptionPane.showMessageDialog(null,"Values must be in range 0 - 1",
                        "Error",JOptionPane.ERROR_MESSAGE); 
                }

        }catch(HeadlessException | NumberFormatException e){

            System.out.println(e);
            JOptionPane.showMessageDialog(null,"Values must be in range 0 - 1",
                        "Error",JOptionPane.ERROR_MESSAGE); 

        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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
