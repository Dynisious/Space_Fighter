package space_fighter_test_3d.global;

import space_fighter_test_3d.global.events.GlobalEvents;
import java.awt.AWTException;
import java.awt.BufferCapabilities;
import java.awt.ImageCapabilities;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import space_fighter_test_3d.global.events.GlobalEventListener;
import space_fighter_test_3d.global.graphics.GraphicsModule;
import space_fighter_test_3d.logging.ErrorLogger;
import space_fighter_test_3d.logging.EventLogger;
import space_fighter_test_3d.logging.MessageLogger;
/**
 * <p>
 * This is the main form throughout the execution of the game.</p>
 *
 * @author Dynisious 05/10/2015
 * @version 0.0.1
 */
public class MainForm extends JFrame implements GlobalEventListener {
    private boolean denyKeyStrokes = false; //While true the JFrame consumes all keystroke.
    /**
     * <p>
     * When set to true the JFrame will consume all KeyEvents.</p>
     *
     * @param denyKeyStrokes The boolean to set denyKeyStrokes as.
     */
    public void setDenyKeyStrokes(boolean denyKeyStrokes) {
        this.denyKeyStrokes = denyKeyStrokes;
    }

    public MainForm(final int graphicsThreads, final GraphicsModule g) {
        setFocusable(true);
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (denyKeyStrokes) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (denyKeyStrokes) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (denyKeyStrokes) {
                    e.consume();
                }
            }

        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addFocusListener(new FocusListener() {

            @Override
            public void focusGained(final FocusEvent e) {
                setState(NORMAL);
            }

            @Override
            public void focusLost(final FocusEvent e) {
                setState(ICONIFIED);
            }

        });
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(final WindowEvent e) {
                GlobalEvents.fireApplicationClosingEvent(
                        GlobalEvents.AppClose_Standard_Operation);
            }

        });
        setUndecorated(true);
        setResizable(false);
        setVisible(true);
        try {
            createBufferStrategy(graphicsThreads, new BufferCapabilities(
                    new ImageCapabilities(true), new ImageCapabilities(true),
                    BufferCapabilities.FlipContents.BACKGROUND));
            g.setStrategy(getBufferStrategy());
        } catch (final AWTException ex) {
            ErrorLogger.write(
                    "ERROR : There was an exception while creating the BufferStrategy for the main form",
                    1, ex, true);
            GlobalEvents.fireApplicationClosingEvent(
                    GlobalEvents.AppClose_Critical_Graphics_Death);
        }
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLayout(null);
        setLocationRelativeTo(null);
    }

    @Override
    public void handleApplicationClosingEvent(int reason) {
        setFocusable(false);
        setVisible(false);
        final String message = "Main form has now closed.";
        MessageLogger.write(message, 1, true);
        EventLogger.write(message, 1, false);
    }

}
