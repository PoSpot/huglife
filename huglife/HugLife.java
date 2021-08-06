package huglife;

import creatures.Plip;

import java.util.logging.Logger;

/**
 * World facing class for HugLife simulator.
 *
 * @author Josh Hug
 */
public class HugLife {

    // TODO stop when stuck
    // TODO some git mess maybe? (visible on push window)

    /**
     * Size of the world. Probably best to keep this under 100
     * or so.
     */
    public static final int WORLD_SIZE = 15;// 15;

    /**
     * Maximum number of cycles to simulate by default.
     */
    public static final int MAX_CYCLES = 1000;

    /**
     * Time in milliseconds between simulation steps.
     * Reduce to make things run faster.
     */
    public static final int PAUSE_TIME_PER_SIMSTEP = 100;
    /**
     * By default, the simulator simulates by cycle, i.e.
     * allows every creature to move before drawing.
     * If you set this to false, then the world will be drawn
     * between moves (much slower).
     */
    public static final boolean SIMULATE_BY_CYCLE = true;
    /**
     * Maximum number of tics to simulate by default if using.
     */
    public static final int MAX_TICS = 100000;
    /**
     * Number of tics to simulate between draw ops.
     */
    public static final int TICS_BETWEEN_DRAW = 10;
    private static final Logger logger = Logger.getLogger(HugLife.class.getName());
    /**
     * Grid for holding all the creatures.
     */
    private final Grid g;

    /**
     * Creates a new world grid of size N for this HugLife simulation.
     */
    public HugLife(int n) {
        g = new Grid(n);
    }

    /**
     * Reads the world from file with worldName and intialized
     * a HugLife with the contents of the file
     * NOTE: DON'T USE THIS; KEPT FOR TESTING PURPOSES
     *
     * @param worldName name of the file to read from
     * @return a newly initialized HugLife
     */
    public static HugLife readWorld(String worldName) {
        var in = new In("huglife/" + worldName + ".world");
        var h = new HugLife(WORLD_SIZE);
        while (!in.isEmpty()) {
            var creature = in.readString();
            var x = in.readInt();
            var y = in.readInt();
            switch (creature) {
                //Uncomment this when you're ready to test out your clorus class
                // case "clorus" -> h.addCreature(x, y, new Clorus(1));
                case "plip" -> h.addCreature(x, y, new Plip());
                case "samplecreature" -> h.addCreature(x, y, new SampleCreature());
            }
        }
        return h;
    }

    /**
     * Runs world name specified by ARGS[0].
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            logger.info("Usage: java huglife.HugLife [worldname]");
            return;
        }
        HugLife h = readWorld(args[0]);
        // HugLife h = new HugLife(WORLD_SIZE);
        // h.initialize(args[0]); DON'T USE ME
        if (SIMULATE_BY_CYCLE) {
            h.simulate(MAX_CYCLES);
        } else {
            h.simulate(MAX_TICS, TICS_BETWEEN_DRAW);
        }
    }

    /**
     * Adds a creature C to the HugLife universe at X, Y.
     */
    public void addCreature(int x, int y, Creature c) {
        g.createCreature(x, y, c);
    }

    /**
     * Simulates the world for CYCLES cycles, simulation
     * one entire cycle between
     */
    public void simulate(int cycles) {
        var cycleCount = 0;
        while (cycleCount < cycles) {
            boolean cycleCompleted = g.tic();
            if (cycleCompleted) {
                g.drawWorld();
                StdDraw.show(PAUSE_TIME_PER_SIMSTEP);
                cycleCount += 1;
            }
        }
    }

    /**
     * Simulates the world for TICS tics, simulating
     * TICSBETWEENDRAW in between world drawing events.
     */
    public void simulate(int tics, int ticsBetweenDraw) {
        for (int i = 0; i < tics; i++) {
            g.tic();
            if ((i % ticsBetweenDraw) == 0) {
                g.drawWorld();
                StdDraw.show(PAUSE_TIME_PER_SIMSTEP);
            }
        }
    }

    /**
     * A set of precanned hard-coded worlds. This is terrible
     * style, but hey it's very easy to write this way.
     */
    public void initialize(String worldName) { // Stefan
        switch (worldName) {
            case "samplesolo" -> addCreature(11, 1, new SampleCreature());
            case "sampleplip" -> {
                addCreature(11, 1, new SampleCreature());
                addCreature(12, 12, new Plip());
                addCreature(4, 3, new Plip());
            }
            case "strugggz" -> {
                logger.info("You need to uncomment the strugggz test!");
                /*addCreature(11, 1, new SampleCreature());
                addCreature(12, 12, new Plip());
                addCreature(3, 3, new Plip());
                addCreature(4, 3, new Plip());
                addCreature(2, 2, new Clorus(1));*/
            }
            default -> logger.severe("World name not recognized!");
        }
    }

}
