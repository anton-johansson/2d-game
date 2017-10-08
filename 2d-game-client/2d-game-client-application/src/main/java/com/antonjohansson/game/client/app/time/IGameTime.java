package com.antonjohansson.game.client.app.time;

/**
 * Provides operations for monitoring elapsed time.
 */
public interface IGameTime
{
    /**
     * Gets the elapsed time since last update, in seconds.
     *
     * @return Returns the elapsed time since last update.
     */
    float getDelta();

    /**
     * Gets the number of updates per second, during the previous second.
     *
     * @return Returns the number of updates per second.
     */
    int getUpdatesPerSecond();
}
