package space_fighter_test_3d.global;

import space_fighter_test_3d.global.events.GlobalEvents;
import space_fighter_test_3d.global.events.ApplicationEventListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import softEngine3D.matrixes.FPoint3D;
import space_fighter_test_3d.exceptions.NoPropertiesFieldException;
import space_fighter_test_3d.global.graphics.GraphicsModule;
import space_fighter_test_3d.gameWorld.GameModule;
import space_fighter_test_3d.gameWorld.entities.EntityObject;
import space_fighter_test_3d.gameWorld.entities.builders.EntityObjectBuilder;
import space_fighter_test_3d.gameWorld.entities.ships.builders.*;
import space_fighter_test_3d.gameWorld.entities.ships.events.PlayerControllerEvents;
import space_fighter_test_3d.gameWorld.entities.ships.shipControls.PlayerController;
import space_fighter_test_3d.gameWorld.gameStates.FreeFlightStateModule;
import space_fighter_test_3d.gameWorld.gameStates.GameStateModule;
import dynutils.events.EventObject;
import space_fighter_test_3d.global.events.GlobalEventListener;
import space_fighter_test_3d.logging.ErrorLogger;
import space_fighter_test_3d.logging.EventLogger;
import space_fighter_test_3d.logging.MessageLogger;
import dynutils.linkedlist.sorted.LinkedIDList;
import dynutils.linkedlist.sorted.StrongLinkedIDListNode;
import java.util.EventListener;
import softEngine3D.matrixes.Point3D;
/**
 * <p>
 * Being ambitious and going to try and get a space dogfight game running in a
 * 3D space.</p>
 *
 * @author Dynisious 05/10/2015
 * @version 0.0.1
 */
public class Application extends EventObject<ApplicationEventListener>
        implements GlobalEventListener {
    private static Application app;
    public static boolean applicationAlive = true;
    public static boolean debug = false;
    public GameModule gameModule;
    public GraphicsModule graphicsModule;
    public MainForm mainForm;
    private Timer tick;

    public Application(final int graphicsThreadCount) {
        MessageLogger.write("Initialising Game Module...", 1, true);
        gameModule = new GameModule();
        GlobalEvents.instance.addListener(gameModule);
        this.addListener(gameModule);
        {
            MessageLogger.write("Initialising FreeFlightModule...", 7, true);
            final FreeFlightStateModule freeFlightStateModule = new FreeFlightStateModule(
                    new LinkedIDList<>());
            GameStateModule.addNewGameStateModule(freeFlightStateModule);
            freeFlightStateModule.entityList.add(new StrongLinkedIDListNode<>(
                    (EntityObject) EntityObjectBuilder.getPhysicsObjectBuildersByTypeName(
                            "dynisious")[0].build(
                            PlayerController.initInstance())));
        }

        MessageLogger.write("Initialising Graphics Module...", 1, true);
        graphicsModule = new GraphicsModule(graphicsThreadCount);
        GlobalEvents.instance.addListener(graphicsModule);

        MessageLogger.write("Initialising Main Form...", 1, true);
        mainForm = new MainForm(graphicsThreadCount, graphicsModule);
        GlobalEvents.instance.addListener(mainForm);
        PlayerControllerEvents.setMainForm(mainForm);

        GlobalEvents.instance.addListener(MessageLogger.getInstance());
        GlobalEvents.instance.addListener(EventLogger.getInstance());
        GlobalEvents.instance.addListener(ErrorLogger.getInstance());
    }

    /**
     * <p>
     * Starts the tick object.</p>
     *
     * @param period The milliseconds between each tick.
     */
    public void startTick(final long period) {
        tick = new Timer("Timer Tick");
        tick.schedule(new TimerTask() {

            @Override
            public void run() {
                fireGameTickEvent();
            }

        }, 0, period);
    }

    /**
     * <p>
     * Initialise all game Objects.</p>
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        try {
            final Properties properties = new Properties();
            System.out.println("Setting working directory...");
            /*<editor-fold defaultstate="collapsed" desc="Load Properties.">*/ {
                properties.setProperty("app",
                        "C:\\Users\\danie_000\\Desktop\\working\\");
                properties.setProperty("app.properties", properties.getProperty(
                        "app") + "application.properties");
                try (final InputStream in = new FileInputStream(
                        properties.getProperty("app.properties"))) {
                    properties.load(in);
                } catch (final IOException ex) { //The properties file does not exist.
                    System.out.println("Creating working directory...");
                    new File(properties.getProperty("app")).mkdirs(); //Make
                    //sure the rest of the directories exist.
                } catch (final SecurityException ex) {
                    System.out.print(ErrorLogger.formatMessage(
                            "ERROR : There was an error creating the working directory for the application.",
                            1, ex));
                    GlobalEvents.fireApplicationClosingEvent(
                            GlobalEvents.AppClose_Initialisation_Error);
                }
                System.out.println("Loading Key bindings...");
                //<editor-fold defaultstate="collapsed" desc="Get key bindings">
                try {
                    PlayerController.keys[0][0] = Integer.valueOf(
                            properties.getProperty("forwardThrustKey"));
                } catch (final NullPointerException | NumberFormatException ex) {
                    System.out.println(
                            "ERROR : There was an error getting the forwardThrustKey value.");
                }
                try {
                    PlayerController.keys[1][0] = Integer.valueOf(
                            properties.getProperty("reverseThrustKey"));
                } catch (final NullPointerException | NumberFormatException ex) {
                    System.out.println(
                            "ERROR : There was an error getting the reverseThrustKey value.");
                }
                try {
                    PlayerController.keys[2][0] = Integer.valueOf(
                            properties.getProperty("rightThrustKey"));
                } catch (final NullPointerException | NumberFormatException ex) {
                    System.out.println(
                            "ERROR : There was an error getting the rightThrustKey value.");
                }
                try {
                    PlayerController.keys[3][0] = Integer.valueOf(
                            properties.getProperty("leftThrustKey"));
                } catch (final NullPointerException | NumberFormatException ex) {
                    System.out.println(
                            "ERROR : There was an error getting the leftThrustKey value.");
                }
                try {
                    PlayerController.keys[4][0] = Integer.valueOf(
                            properties.getProperty("ascendThrustKey"));
                } catch (final NullPointerException | NumberFormatException ex) {
                    System.out.println(
                            "ERROR : There was an error getting the ascendThrustKey value.");
                }
                try {
                    PlayerController.keys[5][0] = Integer.valueOf(
                            properties.getProperty("descendThrustKey"));
                } catch (final NullPointerException | NumberFormatException ex) {
                    System.out.println(
                            "ERROR : There was an error getting the descendThrustKey value.");
                }
                try {
                    PlayerController.keys[6][0] = Integer.valueOf(
                            properties.getProperty("rightYawKey"));
                } catch (final NullPointerException | NumberFormatException ex) {
                    System.out.println(
                            "ERROR : There was an error getting the rightYawKey value.");
                }
                try {
                    PlayerController.keys[7][0] = Integer.valueOf(
                            properties.getProperty("leftYawKey"));
                } catch (final NullPointerException | NumberFormatException ex) {
                    System.out.println(
                            "ERROR : There was an error getting the leftYawKey value.");
                }
                try {
                    PlayerController.keys[8][0] = Integer.valueOf(
                            properties.getProperty("downPitchKey"));
                } catch (final NullPointerException | NumberFormatException ex) {
                    System.out.println(
                            "ERROR : There was an error getting the downPitchKey value.");
                }
                try {
                    PlayerController.keys[9][0] = Integer.valueOf(
                            properties.getProperty("upPitchKey"));
                } catch (final NullPointerException | NumberFormatException ex) {
                    System.out.println(
                            "ERROR : There was an error getting the upPitchKey value.");
                }
                try {
                    PlayerController.keys[10][0] = Integer.valueOf(
                            properties.getProperty("rightRollKey"));
                } catch (final NullPointerException | NumberFormatException ex) {
                    System.out.println(
                            "ERROR : There was an error getting the rightRollKey value.");
                }
                try {
                    PlayerController.keys[11][0] = Integer.valueOf(
                            properties.getProperty("leftRollKey"));
                } catch (final NullPointerException | NumberFormatException ex) {
                    System.out.println(
                            "ERROR : There was an error getting the leftRollKey value.");
                }
                //</editor-fold>
            }//</editor-fold>

            System.out.println("Checking logs directory...");
            /*<editor-fold defaultstate="collapsed" desc="Logs directory.">*/ {
                if (properties.putIfAbsent("app.logs", properties.getProperty(
                        "app") + "Logs\\") == null) { //The properties did not exist.
                    System.out.println("Creating Logs directory...");
                    try {
                        new File(properties.getProperty("app.logs")).mkdirs();
                    } catch (final SecurityException ex) {
                        System.out.println(
                                "ERROR : There was an error creating the logs directory for the application.");
                        GlobalEvents.fireApplicationClosingEvent(
                                GlobalEvents.AppClose_Initialisation_Error);
                    }
                }

                properties.putIfAbsent("app.logs.errors",
                        properties.getProperty("app.logs") + "Errors.log");

                properties.putIfAbsent("app.logs.messages",
                        properties.getProperty("app.logs") + "Messages.log");

                properties.putIfAbsent("app.logs.events",
                        properties.getProperty("app.logs") + "Events.log");

            }//</editor-fold>

            System.out.println("Checking Entities directory...");
            /*<editor-fold defaultstate="collapsed" desc="Entities directory.">*/ {
                if (properties.putIfAbsent("app.entities",
                        properties.get("app") + "Entities\\") == null) {
                    System.out.println("Creating Entities directory...");
                    try {
                        new File(properties.getProperty("app.entities")).mkdirs();
                    } catch (final SecurityException ex) {
                        System.out.println(
                                "ERROR : There was an error creating the Entities directory for the application.");
                        GlobalEvents.fireApplicationClosingEvent(
                                GlobalEvents.AppClose_Initialisation_Error);
                    }
                }

                System.out.println("Checking Ships directory...");
                //<editor-fold defaultstate="collapsed" desc="Ships directory.">
                if (properties.putIfAbsent("app.entities.ships",
                        properties.get("app.entities") + "Ships\\") == null) {
                    System.out.println("Creating Ships directory...");
                    try {
                        new File(properties.getProperty("app.entities.ships")).mkdirs();
                    } catch (final SecurityException ex) {
                        System.out.println(
                                "ERROR : There was an error creating the Ships directory for the application.");
                        GlobalEvents.fireApplicationClosingEvent(
                                GlobalEvents.AppClose_Initialisation_Error);
                    }
                } else {
                    System.out.println("Loading Ships...");
                    int shipsLoaded = 0;
                    int shipsToLoad = 0;
                    for (final String ship : new File(properties.getProperty(
                            "app.entities.ships")).list()) {
                        if (!ship.equalsIgnoreCase("COPY ME-Template.ship")) {
                            shipsToLoad++;
                            final Properties shipFile = new Properties();
                            try {
                                shipFile.load(new FileInputStream(
                                        properties.getProperty(
                                                "app.entities.ships") + ship));
                                //<editor-fold defaultstate="collapsed" desc="Set Builder settings.">
                                //<editor-fold defaultstate="collapsed" desc="Get Builder values.">
                                String cls = shipFile.getProperty("type.class");
                                if (cls == null) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the type.class value.",
                                            null);
                                }
                                String typeName = shipFile.getProperty(
                                        "type.name");
                                if (typeName == null) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the type.name value.",
                                            null);
                                }
                                double mass;
                                try {
                                    mass = Double.valueOf(shipFile.getProperty(
                                            "mass"));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the mass value.",
                                            ex);
                                }
                                int vertexCount;
                                try {
                                    vertexCount = Integer.valueOf(
                                            shipFile.getProperty(
                                                    "vertex.count"));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the vertex.count value.",
                                            ex);
                                }
                                final Point3D[] vertexes = new Point3D[vertexCount];
                                for (int vertex = 0; vertex < vertexCount; vertex++) {
                                    try {
                                        vertexes[vertex] = new Point3D(
                                                Integer.valueOf(
                                                        shipFile.getProperty(
                                                                "vertex" + vertex + ".x")),
                                                Integer.valueOf(
                                                        shipFile.getProperty(
                                                                "vertex" + vertex + ".y")),
                                                Integer.valueOf(
                                                        shipFile.getProperty(
                                                                "vertex" + vertex + ".z")));
                                    } catch (final NullPointerException | NumberFormatException ex) {
                                        throw new NoPropertiesFieldException(
                                                "ERROR : There was an error getting the vertex"
                                                + vertex + " value.", ex);
                                    }
                                }
                                FPoint3D linearForces = null;
                                try {
                                    linearForces = new FPoint3D(
                                            Double.valueOf(shipFile.getProperty(
                                                            "initLinearForces.x")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initLinearForces.y")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initLinearForces.z")));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the initLinearForces value.",
                                            ex);
                                }
                                FPoint3D velocity = null;
                                try {
                                    velocity = new FPoint3D(
                                            Double.valueOf(shipFile.getProperty(
                                                            "initVelocity.x")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initVelocity.y")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initVelocity.z")));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the initVelocity value.",
                                            ex);
                                }
                                FPoint3D maxMagnituidTorques = null;
                                try {
                                    maxMagnituidTorques = new FPoint3D(
                                            Double.valueOf(shipFile.getProperty(
                                                            "initMaxTorques.x")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initMaxTorques.y")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initMaxTorques.z")));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the initMaxTorques value.",
                                            ex);
                                }
                                FPoint3D torques = null;
                                try {
                                    torques = new FPoint3D(
                                            Double.valueOf(shipFile.getProperty(
                                                            "initTorques.x")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initTorques.y")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initTorques.z")));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the initTorques value.",
                                            ex);
                                }
                                FPoint3D maxMagnituidLinearForces = null;
                                try {
                                    maxMagnituidLinearForces = new FPoint3D(
                                            Double.valueOf(shipFile.getProperty(
                                                            "initMaxLinearForces.x")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initMaxLinearForces.y")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initMaxLinearForces.z")));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the initMaxLinearForces value.",
                                            ex);
                                }
                                FPoint3D rotation = null;
                                try {
                                    rotation = new FPoint3D(
                                            Double.valueOf(shipFile.getProperty(
                                                            "initRotation.x")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initRotation.y")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initRotation.z")));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the initRotation value.",
                                            ex);
                                }
                                FPoint3D location = null;
                                try {
                                    location = new FPoint3D(
                                            Double.valueOf(shipFile.getProperty(
                                                            "initLocation.x")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initLocation.y")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initLocation.z")));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the initLocation value.",
                                            ex);
                                }
                                FPoint3D linearForcesIncrement = null;
                                try {
                                    linearForcesIncrement = new FPoint3D(
                                            Double.valueOf(shipFile.getProperty(
                                                            "initLinearForcesIncrement.x")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initLinearForcesIncrement.y")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initLinearForcesIncrement.z")));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the initLinearForcesIncrement value.",
                                            ex);
                                }
                                FPoint3D maxLinearForcesIncrement = null;
                                try {
                                    maxLinearForcesIncrement = new FPoint3D(
                                            Double.valueOf(shipFile.getProperty(
                                                            "initMaxLinearForcesIncrement.x")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initMaxLinearForcesIncrement.y")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initMaxLinearForcesIncrement.z")));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the initMaxLinearForcesIncrement value.",
                                            ex);
                                }
                                FPoint3D torquesIncrement = null;
                                try {
                                    torquesIncrement = new FPoint3D(
                                            Double.valueOf(shipFile.getProperty(
                                                            "initTorquesIncrement.x")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initTorquesIncrement.y")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initTorquesIncrement.z")));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the initTorquesIncrement value.",
                                            ex);
                                }
                                FPoint3D maxTorquesIncrement = null;
                                try {
                                    maxTorquesIncrement = new FPoint3D(
                                            Double.valueOf(shipFile.getProperty(
                                                            "initMaxTorquesIncrement.x")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initMaxTorquesIncrement.y")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initMaxTorquesIncrement.z")));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the initMaxTorquesIncrement value.",
                                            ex);
                                }
                                FPoint3D rotationalSpeed = null;
                                try {
                                    rotationalSpeed = new FPoint3D(
                                            Double.valueOf(shipFile.getProperty(
                                                            "initRotationalSpeed.x")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initRotationalSpeed.y")),
                                            Double.valueOf(shipFile.getProperty(
                                                            "initRotationalSpeed.z")));
                                } catch (final NullPointerException | NumberFormatException ex) {
                                    throw new NoPropertiesFieldException(
                                            "ERROR : There was an error getting the initRotationalSpeed value.",
                                            ex);
                                }
                                //</editor-fold>
                                if (cls.equalsIgnoreCase("civilian")) {
                                    if (!typeName.isEmpty()) {
                                        EntityObjectBuilder.addNewBuilder(
                                                new CivilianShipBuilder(typeName,
                                                        mass, vertexes, location,
                                                        rotation, velocity,
                                                        rotationalSpeed,
                                                        linearForces,
                                                        maxMagnituidLinearForces,
                                                        torques,
                                                        maxMagnituidTorques,
                                                        linearForcesIncrement,
                                                        maxLinearForcesIncrement,
                                                        torquesIncrement,
                                                        maxTorquesIncrement));
                                    } else {
                                        throw new NoSuchFieldException(
                                                "ERROR : No invalid type.name value in file '" + ship + "'");
                                    }
                                } else {
                                    throw new NoSuchFieldException(
                                            "ERROR : No invalid type.class value in file '" + ship + "'");
                                }
                                //</editor-fold>
                                shipsLoaded++;
                            } catch (final IOException | NumberFormatException | NullPointerException | NoSuchFieldException | NoPropertiesFieldException ex) {
                                System.out.print(ErrorLogger.formatMessage(
                                        "ERROR : There was an error loading the ship '"
                                        + ship + "'; it will not be used in instance of the game.",
                                        4, ex));
                            }
                        }
                    }
                    System.out.println(
                            "Ships loaded " + shipsLoaded + " of "
                            + shipsToLoad + ". WARNING! Unloaded ships may cause instability.");
                }
                //</editor-fold>
            } //</editor-fold>

            int graphicsThreadCount = 1; //The number of Threads that render frames in the game.
            long period = 50; //The number of milliseconds between each game tick.
            /*<editor-fold defaultstate="collapsed" desc="Process commandline args.">*/ {
                for (final String line : args) {
                    final String[] command = line.split("=");
                    try {
                        if (command[0].equalsIgnoreCase("graphics")) {
                            graphicsThreadCount = Integer.valueOf(
                                    command[1]);
                        } else if (command[0].equalsIgnoreCase(
                                "tick")) {
                            period = Long.valueOf(command[1]);
                        } else if (command[0].equalsIgnoreCase("debug")) {
                            debug = true;
                        }
                    } catch (Exception ex) {
                        //Don't bother.
                    }
                }
            } //</editor-fold>
            System.getProperties().putAll(properties);
            properties.store(new FileOutputStream(properties.getProperty(
                    "app.properties")),
                    "Editing this properties file will produce unexpected results");

            MessageLogger.write("Initialising application...", 1, true);
            /*<editor-fold defaultstate="collapsed" desc="Initialise application.">*/ {
                app = new Application(graphicsThreadCount);
                app.startTick(period);
                MessageLogger.write("Application initialised.", 1, true);
            } //</editor-fold>
        } catch (final Exception ex) {
            System.out.print(ErrorLogger.formatMessage(
                    "ERROR : There was an error during the applications initialisation."
                    + ex.getMessage(), 1, ex));
            System.exit(GlobalEvents.AppClose_Initialisation_Error);
        }
        app.graphicsModule.start();

        MessageLogger.write("Initialising Console input...", 1, true);
        //<editor-fold defaultstate="collapsed" desc="Console loop.">
        final Scanner scan = new Scanner(System.in);
        while (applicationAlive) {
            final String[] line = scan.nextLine().split(" ");
            if ("help".equalsIgnoreCase(line[0])) {
                System.out.println(
                        "help               : Displays all commands.\r\n"
                        + "stop             : Closes the application.\r\n"
                        + "set tick <value> : Sets the milliseconds between update ticks.");
            } else if (line[0].equalsIgnoreCase("stop")) {
                GlobalEvents.fireApplicationClosingEvent(
                        GlobalEvents.AppClose_Standard_Operation);
            } else if (line[0].equalsIgnoreCase("set")) {
                if (line[1].equalsIgnoreCase("tick")) {
                    try {
                        app.startTick(Long.valueOf(line[2]));
                    } catch (final NumberFormatException ex) {
                        System.out.println("value is not a number.");
                    }
                }
            } else {
                System.out.println("Unrecoginsed command.");
            }
        }
        //</editor-fold>
        GlobalEvents.fireApplicationClosingEvent(
                GlobalEvents.AppClose_Main_Thread_Death);
    }

    private void fireGameTickEvent() {
        for (final EventListener l : getListeners()) {
            ((ApplicationEventListener) l).handleGameTickEvent();
        }
    }

    @Override
    public void handleApplicationClosingEvent(int reason) {
        tick.cancel();
    }

}
