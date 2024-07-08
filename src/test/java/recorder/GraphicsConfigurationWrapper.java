package recorder;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;

public class GraphicsConfigurationWrapper {

    private GraphicsConfiguration gc;

    public GraphicsConfigurationWrapper() {
        this.gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    public GraphicsConfiguration getGraphicsConfiguration() {
        return gc;
    }
}