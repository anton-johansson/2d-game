package com.antonjohansson.game.client.app.config;

/**
 * Loads the stored configuration.
 */
public class ConfigurationLoader
{
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private static final int DEFAULT_FRAME_CAP = 5;

    /**
     * Loads the configuration.
     */
    public Configuration load()
    {
        Configuration configuration = new Configuration();
        configuration.getGraphics().setWidth(WIDTH);
        configuration.getGraphics().setHeight(HEIGHT);
        configuration.getGraphics().setVerticalSyncEnabled(false);
        configuration.getGraphics().setFrameCap(getFrameCap());
        return configuration;
    }

    private int getFrameCap()
    {
        String value = System.getProperty("frameCap");
        try
        {
            return Integer.parseInt(value);
        }
        catch (Exception e)
        {
            return DEFAULT_FRAME_CAP;
        }
    }
}
