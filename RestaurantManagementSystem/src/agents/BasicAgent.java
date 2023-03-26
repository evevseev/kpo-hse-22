package agents;

import jade.core.Agent;

public class BasicAgent extends Agent {
    protected Object[] startupArgs;

    @Override
    protected void setup() {
        super.setup();

        startupArgs = getArguments();
        // TODO: use logging
        System.out.println("Agent " + getAID().getName() + " created");
    }

    @Override
    protected void takeDown() {
        super.takeDown();

        System.out.println("Agent " + getAID().getName() + " terminating");
    }
}
