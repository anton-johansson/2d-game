package com.antonjohansson.game.client.app;

import com.antonjohansson.game.client.app.config.Configuration;
import com.antonjohansson.game.client.app.config.ConfigurationLoader;

/**
 * Contains the applications main entry point.
 */
public class EntryPoint
{
    /**
     * The application main entry point.
     */
    public static void main(String[] args)
    {
        ConfigurationLoader configurationLoader = new ConfigurationLoader();
        Configuration configuration = configurationLoader.load();

        Game game = new Game(configuration);
        game.run();
    }
}
