import jade.core.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.GUI, "true");
        ContainerController cc = rt.createAgentContainer(p);

        try {
            Object[] agentArgs = new Object[] {"Water", 1.0};
            AgentController ac = cc.createNewAgent("WaterAgent", "agents.ProductAgent", agentArgs);
            ac.start();
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }

    }
}