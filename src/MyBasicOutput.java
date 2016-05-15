import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.util.Objects;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

/**
 * Created by Mike on 8/8/2015.
 */
public class MyBasicOutput {
    private static int BUFFER_NUMBER=2;
    private static int FRAME_TIME_MS=8;

    private static Frame InitGraph (){
        Frame myFrame;
        try {
            GraphicsEnvironment env= GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice dev= env.getDefaultScreenDevice();
            GraphicsConfiguration conf= dev.getDefaultConfiguration();
            myFrame = new Frame (conf);
            myFrame.setUndecorated(true);
            myFrame.setIgnoreRepaint(true);
            return myFrame;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void OutLoop (Frame window){
        window.setLocation(10,10);
        window.setSize(500,300);
        window.setVisible(true);
        window.createBufferStrategy(BUFFER_NUMBER);
        BufferStrategy buffers = window.getBufferStrategy();
        Rectangle bounds = window.getBounds();
        //gets a pointer to new buffer
        for (int i = 0; i <1600 ; i++) {
            Graphics g = buffers.getDrawGraphics();
            if (!buffers.contentsLost()) {
                render(g, bounds, i);
                buffers.show();
                g.dispose();
            }
            try {
                //2DO implement time scale 2 get exactly 60 fps
                Thread.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    //2REFACTOR - change to point2D probably? to get rid of Float
    static Point.Float[] startPointSet = {new Point.Float(200,100), new Point.Float(200,250), new Point.Float (50,175)};
    static ActorExample actor= new ActorExample(startPointSet);
    static Point.Float[] rotatedPointSet = new Point.Float[startPointSet.length];
    public static Polygon testPolygon = new Polygon();

    //This is a small step for mankind, but a giant leap for one man - me. )
    protected static int angle=1;
    static int dx=0;
    static int dy=0;
    static int fired=0;
    private static void render (Graphics g, Rectangle bounds,int framecount){

            g.setColor(Color.white);
            g.fill3DRect(0,0,bounds.width,bounds.height,true);
            g.setColor(Color.black);
            g.drawLine(10,10,framecount+10,150);
            //now MOVE
            actor.move();
            //now for ROTATION
            startPointSet=actor.GetCoords();
            rotateMatrix(startPointSet, (double) angle, rotatedPointSet);
            polySetShape(rotatedPointSet,testPolygon);
            g.drawPolygon(testPolygon);
            angle++;
        }

    private  static class ActorExample {
        private Point.Float[] PointSet;
        private int dx,dy,fire;

        public  ActorExample (Point.Float[] initCoords){
            this.PointSet=new Point.Float[initCoords.length];
            this.PointSet=initCoords;

            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    System.out.println("PRESSED!!");
                    this.keyPressed(e);
                }
            });


        }

        public void move(){
            for (Point.Float i : PointSet){
                i.setLocation(i.getX()+dx,i.getY()+dy);
            }
            dx=0;
            dy=0;
        }

        public void keyPressed (KeyEvent e){
            int key = e.getKeyCode();

            int STEP=10;
            System.out.println("KEYPRESSED");
            if (key == KeyEvent.VK_LEFT) {
                dx = -1*STEP;
            }

            if (key == KeyEvent.VK_RIGHT) {
                dx = 1*STEP;
            }

            if (key == KeyEvent.VK_UP) {
                dy = -1*STEP;
            }

            if (key == KeyEvent.VK_DOWN) {
                dy = 1*STEP;
            }
        }

        public Point.Float[] GetCoords(){
            return this.PointSet;
        }
    }

        private static void rotateMatrix (Point2D[] original, double angle, Point2D[] rotated){
            //System.out.println(angle);
            AffineTransform rotation=AffineTransform.getRotateInstance(Math.toRadians(angle),120,120);
            //Point2D[] init = {new Point2D.Float(200,100), new Point2D.Float(200,250), new Point2D.Float (50,175)};
            //Point2D[] result = new Point.Float[3];
            //rotation.transform(init,0,result,0,init.length);
            rotation.transform(original,0,rotated,0,3);
        }

        private static void polySetShape(Point2D[] vertices, Polygon poly){
            poly.reset();
            for (Point2D i : vertices) {
                poly.addPoint((int)i.getX(),(int)i.getY());
            }
        }




    public static void main (String[] Args){
        try {

            Frame window = InitGraph();
           // KeyboardExample keyboardExample= new KeyboardExample();
            //window.add(keyboardExample);
            OutLoop(window);
            System.exit(0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
