package space_fighter_test_3d.global.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import space_fighter_test_3d.global.Application;
import space_fighter_test_3d.global.events.GlobalEvents;
import space_fighter_test_3d.global.events.GlobalEventListener;
import space_fighter_test_3d.logging.ErrorLogger;
import space_fighter_test_3d.logging.EventLogger;
import space_fighter_test_3d.logging.MessageLogger;
/**
 * <p>
 * The GraphicsModule is responsible for rendering the games graphics frame by
 * frame.</p>
 *
 * @author Dynisious 05/10/2015
 * @version 0.0.1
 */
public final class GraphicsModule extends Thread implements GlobalEventListener {
    private final GraphicsThread[] graphicsThreads;
    public int getGraphicsThreadsCount() {
        return graphicsThreads.length;
    }
    private final Semaphore GraphicsThreadsClosed = new Semaphore(0);
    private BufferStrategy strategy;
    public void setStrategy(final BufferStrategy strategy) {
        this.strategy = strategy;
    }
    //<editor-fold defaultstate="collapsed" desc="Getting and Adding new Renderables">
    private final Semaphore renderablesQueueSemaphore = new Semaphore(0);
    private ArrayDeque<Renderable> renderableQueue;
    private final ArrayList<Renderable> activeRenderables;
    /**
     * <p>
     * Returns an array of Objects: {Graphics2D, Renderable}.</p>
     *
     * @return The {Graphics2D, Renderable}.
     *
     * @throws InterruptedException if the thread is interrupted while waiting
     *                              for a new Renderable.
     */
    private Object[] dequeueRenderable() throws InterruptedException {
        renderablesQueueSemaphore.acquire();
        synchronized (renderableQueue) {
            synchronized (activeRenderables) {
                synchronized (strategy) {
                    final Renderable toRender = renderableQueue.pop();
                    activeRenderables.add(toRender);
                    activeRenderables.sort(toRender);
                    return new Object[]{strategy.getDrawGraphics(), toRender};
                }
            }
        }
    }
    /**
     * <p>
     * Adds a renderable to the queue of Renderables to be rendered.</p>
     *
     * @param renderable The renderable to queue.
     */
    public void queueRenderable(final Renderable renderable) {
        synchronized (renderableQueue) {
            renderableQueue.add(renderable);
            renderablesQueueSemaphore.release();
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Manage rendered Renderables">
    private ArrayList<Renderable> renderedRenderables;
    private final Semaphore renderedRenderablesSemaphore = new Semaphore(0);
    /**
     * <p>
     * Signals the rendering thread that a new Graphics is ready to display.</p>
     *
     * @param toRender The Renderable now ready to render.
     */
    private void renderableReady(final Renderable toRender) {
        synchronized (renderedRenderables) {
            renderedRenderables.add(toRender);
            renderedRenderables.sort(toRender);
            renderedRenderablesSemaphore.release();
        }
    }
    //</editor-fold>

    /**
     * <p>
     * Creates a new GraphicsModule with the passed values.</p>
     *
     * @param graphicsThreadCount The number of GraphicThreads to create.
     */
    public GraphicsModule(final int graphicsThreadCount) {
        this.graphicsThreads = new GraphicsThread[graphicsThreadCount];
        this.activeRenderables = new ArrayList<>(graphicsThreadCount);
        this.renderedRenderables = new ArrayList<>(graphicsThreadCount);
        for (int i = 0; i < graphicsThreads.length; i++) {
            graphicsThreads[i] = new GraphicsThread(i);
        }
        setName("GraphicsModule");
    }

    @Override
    public synchronized void start() {
        for (final GraphicsThread gt : graphicsThreads) {
            gt.start();
        }
        String message = "Graphics Module now executing.";
        MessageLogger.write(message, 2, true);
        EventLogger.write(message, 2, false,
                "Graphics Threads=" + graphicsThreads.length);
        super.start();
    }

    @Override
    public void run() {
        while (Application.applicationAlive) {
            try {
                renderedRenderablesSemaphore.acquire(); //A new Renderable is ready to show.
                synchronized (renderedRenderables) {
                    synchronized (activeRenderables) {
                        while (renderedRenderables.get(0).equals(
                                activeRenderables.get(0))) { //The proper frame is ready to render.
                            synchronized (strategy) {
                                strategy.show();
                            }
                            activeRenderables.remove(0);
                            renderedRenderables.remove(0);
                        }
                    }
                }
            } catch (final InterruptedException ex) {
                //Nothing to do here.
            } catch (final IndexOutOfBoundsException | ClassCastException ex) {
                final String message = "ERROR : There was a catastrophic failiure in the Threads execution.";
                ErrorLogger.write(message, 1, ex, false);
                MessageLogger.write(message, 1, true);
                GlobalEvents.fireApplicationClosingEvent(
                        GlobalEvents.AppClose_Main_Graphics_Death);
            } finally {
                final String message = "The GraphicsModule has now closed.";
                EventLogger.write(message, 4, false);
                MessageLogger.write(message, 4, false);
            }
        }
    }

    @Override
    public synchronized void handleApplicationClosingEvent(int reason) {
        interrupt();
        synchronized (graphicsThreads) {
            for (final GraphicsThread graphicsThread : graphicsThreads) {
                graphicsThread.CloseGraphicsThread(reason);
            }
        }
        for (int i = 0; i < graphicsThreads.length; i++) {
            try {
                GraphicsThreadsClosed.acquire();
            } catch (final InterruptedException ex) {
                ErrorLogger.write(
                        "ERROR : The Thread was interrupted while waiting for GraphicsThread"
                        + i + " to close.", 4, ex, false);
            }
        }
        MessageLogger.write("Graphics Module now closed", 1, true);
    }

    private final class GraphicsThread extends Thread {
        private boolean alive = true;
        private boolean getAlive() {
            return alive;
        }
        public GraphicsThread(final int ID) {
            super("GraphicsThread" + ID);
        }

        @Override
        public void run() {
            Renderable toRender = null; //The renderable to be rendered.
            try {
                while (Application.applicationAlive) {
                    try {
                        final Graphics2D g; //The Graphics2D to render on.
                        {
                            final Object[] obj = GraphicsModule.this
                                    .dequeueRenderable();
                            g = (Graphics2D) obj[0];
                            toRender = (Renderable) obj[1];
                        }
                        toRender.render(g);
                        renderableReady(toRender);
                    } catch (final InterruptedException ex) {
                        final String message = "ERROR : The Thread was interupted while waiting for a new frame.";
                        MessageLogger.write(message, 5, false);
                        EventLogger.write(message, 5, false);
                    } finally {
                        toRender = null;
                    }
                }
            } catch (final Exception ex) {
                alive = false;
                final String message = "ERROR : There was a catastrophic failiure in the Threads execution.";
                MessageLogger.write(message, 1, true);
                ErrorLogger.write(message, 1, ex, false);
                boolean aThreadAlive = false;
                for (final GraphicsThread gt : graphicsThreads) {
                    if (gt.getAlive()) {
                        aThreadAlive = true;
                        break;
                    }
                }
                if (!aThreadAlive) {
                    GraphicsThreadsClosed.release();
                    GlobalEvents.fireApplicationClosingEvent(
                            GlobalEvents.AppClose_Critical_Graphics_Death);
                }
            } finally {
                if (toRender != null) {
                    synchronized (renderedRenderables) {
                        synchronized (activeRenderables) {
                            activeRenderables.remove(toRender);
                            renderedRenderables.remove(toRender);
                        }
                    }
                }
                final String message = "The Thread has now closed.";
                EventLogger.write(message, 5, false);
                MessageLogger.write(message, 5, false);
                GraphicsThreadsClosed.release(); //Signal that a GraphicsThread has closed.
            }
        }

        public synchronized void CloseGraphicsThread(int reason) {
            synchronized (this) {
                if (!this.isInterrupted()) {
                    this.interrupt();
                }
            }
        }

    }

}
