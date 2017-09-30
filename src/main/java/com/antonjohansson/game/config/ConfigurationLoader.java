package com.antonjohansson.game.config;

/**
 * Loads the stored configuration.
 */
public class ConfigurationLoader
{
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private static final int FRAME_CAP = 5;

    /**
     * Loads the configuration.
     */
    public Configuration load()
    {
        Configuration configuration = new Configuration();
        configuration.getGraphics().setWidth(WIDTH);
        configuration.getGraphics().setHeight(HEIGHT);
        configuration.getGraphics().setVerticalSyncEnabled(false);
        configuration.getGraphics().setFrameCap(FRAME_CAP);
        return configuration;
    }
}
