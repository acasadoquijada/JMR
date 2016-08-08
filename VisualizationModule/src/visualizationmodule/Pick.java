/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationmodule;

import com.sun.j3d.utils.geometry.Primitive;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Vector3f;

/**
 *
 * @author alejandro
 */
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;

import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Vector3d;

public class Pick extends Behavior {

    private WakeupOnAWTEvent condition;
    private PickCanvas pickCanvas;

    public Pick(Canvas3D aCanvas, BranchGroup bg) {
        condition = new WakeupOnAWTEvent(MouseEvent.MOUSE_CLICKED);
        pickCanvas = new PickCanvas(aCanvas, bg);
        setEnable(true);
    }

    @Override
    public void initialize() {
        setEnable(true);
        wakeupOn(condition);
    }

    @Override
    public void processStimulus(Enumeration cond) {
        WakeupOnAWTEvent c = (WakeupOnAWTEvent) cond.nextElement();
        AWTEvent[] e = c.getAWTEvent();
        MouseEvent mouse = (MouseEvent) e[0];
        pickCanvas.setShapeLocation(mouse);
        PickResult result = pickCanvas.pickClosest();
        
        Vector3f v = new Vector3f(0.0f , 0.0f , 5.0f);
        BranchGroup bg;
        if (result != null) {
            
            Transform3D t = result.getLocalToVworld();
            Primitive p = (Primitive)result.getNode(PickResult.PRIMITIVE); 
            
            Shape3D shape = (Shape3D)result.getNode(PickResult.SHAPE3D);
            if(p != null){       
                t.set(v);
                
                // abrir otra ventana

              //  tg.setTransform(t);
                System.out.println( shape.getClass().getName());
            }
        }
        
        else
            System.out.println("ADIOS");

        setEnable(true);
        wakeupOn(condition);
    }

}