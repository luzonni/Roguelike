package com.retronova.engine;

import com.retronova.engine.graphics.SpriteSheet;
import com.retronova.engine.inputs.keyboard.KeyBoard;
import com.retronova.engine.inputs.mouse.Mouse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serial;
import java.net.URL;

public class Window extends Canvas {

    @Serial
    private static final long serialVersionUID = 36752349087L;

    public static final BufferedImage defalutCursor;

    static {
        defalutCursor = new SpriteSheet("ui","cursor", 3).getSHEET();
    }

    private final String name;
    private Thread thread;
    private JFrame frame;
    private final Toolkit toolkit;
    private int C_W, C_H;
    boolean oglEnabled = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration()
            .getImageCapabilities()
            .isAccelerated();


    public Window(String name) {
        this.name = name;
        this.toolkit = Toolkit.getDefaultToolkit();
        createOpenGl(true);
        initFrame();
        Mouse m = new Mouse();
        KeyBoard k = new KeyBoard();
        addMouseListener(m);
        addMouseMotionListener(m);
        addMouseWheelListener(m);
        addKeyListener(k);
    }

    private void createOpenGl(boolean bool) {
        if(bool)
            try {
                System.setProperty("sun.java2d.opengl", "True");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                System.out.println("OpenGL Pipeline enabled: " + oglEnabled);
            } catch (Exception ignore) { }
    }

    public void initFrame(){
        this.frame = new JFrame(this.name);
        frame.add(this);
        frame.setUndecorated(false);
        frame.setResizable(true);
        frame.setAlwaysOnTop(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(Configs.Fullscreen()) {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            if (gd.isFullScreenSupported()) {
                gd.setFullScreenWindow(frame);
            }
        }else {
            setPreferredSize(new Dimension(Engine.getResolution()[0], Engine.getResolution()[1]));
            frame.setMinimumSize(new Dimension(Engine.getResolution()[0], Engine.getResolution()[1]));
        }
        frame.pack();
        try {
            SpriteSheet icon = new SpriteSheet("ui","icon", 3);
            setCursor(defalutCursor);
            frame.setIconImage(icon.getSHEET());
        }catch(Exception ignore) { }
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        createBufferStrategy(3);
        Engine.BUFFER = getBufferStrategy();
        createOpenGl(true);
        System.out.println("OpenGL: " + System.getProperty("sun.java2d.opengl")); // "true" se OpenGL estiver ativado
        System.out.println("DirectX: " + System.getProperty("sun.java2d.d3d"));   // "true" se Direct3D estiver ativado
    }

    private void closeFrame() {
        frame.setVisible(false);
        frame.dispose();
        frame = null;
    }

    public void setResolution() {
        closeFrame();
        initFrame();
        requestFocus();
    }

    public synchronized void setCursor(BufferedImage cursor) {
        Cursor c = toolkit.createCustomCursor(cursor, new Point(0,0), "cursor");
        frame.setCursor(c);
    }

    public synchronized void setCursor(BufferedImage cursor, Point pointClick) {
        Cursor c = toolkit.createCustomCursor(cursor, pointClick, "cursor");
        frame.setCursor(c);
    }

    public synchronized void resetCursor() {
        Cursor c = toolkit.createCustomCursor(defalutCursor, new Point(0,0), "cursor");
        frame.setCursor(c);
    }

    //Getter's and Setter's

    public JFrame getFrame() {
        return this.frame;
    }

    public int getWidth() {
        Component c = frame.getComponent(0);
        return c.getWidth();
    }

    public int getHeight() {
        Component c = frame.getComponent(0);
        return c.getHeight();
    }

    public boolean isResizing() {
        int W = getWidth();
        int H = getHeight();
        if(W != C_W || H != C_H) {
            return true;
        }
        return false;
    }

    public Dimension getScreenSize() {
        return toolkit.getScreenSize();
    }



}
