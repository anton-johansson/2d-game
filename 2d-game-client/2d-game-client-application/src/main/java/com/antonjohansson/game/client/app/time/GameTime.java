package com.antonjohansson.game.client.app.time;

import java.util.OptionalInt;

import com.antonjohansson.game.client.app.config.Configuration;

/**
 * Controls the game time.
 */
public class GameTime implements IGameTime
{
    private static final int SECOND_IN_MILLISECONDS = 1_000;
    private static final int SECOND_IN_NANOSECONDS = 1_000_000_000;
    private static final int MILLISECOND_IN_NANOSECONDS = 1_000_000;

    private final Configuration configuration;
    private long lastUpdate;
    private float delta;
    private float updatesPerSecondTime;
    private int updatesPerSecondCounter;
    private int updatesPerSecond;

    public GameTime(Configuration configuration)
    {
        this.configuration = configuration;
    }

    /**
     * Initializes the clock.
     */
    public void initialize()
    {
        lastUpdate = time();
    }

    private long time()
    {
        return System.nanoTime();
    }

    /**
     * Updates the clock.
     */
    public void update()
    {
        long newUpdate = time();

        delta = (newUpdate - lastUpdate) / (float) SECOND_IN_NANOSECONDS;

        updatesPerSecondTime += delta;
        if (updatesPerSecondTime >= 1.0F)
        {
            updatesPerSecondTime -= 1.0F;
            updatesPerSecond = updatesPerSecondCounter;
            updatesPerSecondCounter = 1;

            String message = "Updates per second: " + updatesPerSecond;
            if (frameCap().isPresent())
            {
                message += " (requested: " + frameCap().getAsInt() + ")";
            }
            System.out.println(message);
        }
        else
        {
            updatesPerSecondCounter++;
        }

        lastUpdate = newUpdate;
    }

    /**
     * Restrains the frame count if necessary.
     */
    public void restrainIfNecessary()
    {
        if (frameCap().isPresent())
        {
            float secondsPerFrame = 1.0F / frameCap().getAsInt();

            long estimatedTimeAfterFrame = (long) (lastUpdate / (float) MILLISECOND_IN_NANOSECONDS) + (long) (secondsPerFrame * SECOND_IN_MILLISECONDS);
            long now = (long) (time() / (float) MILLISECOND_IN_NANOSECONDS);
            long millisecondsToSleep = estimatedTimeAfterFrame - now;

            if (millisecondsToSleep > 0)
            {
                sleepQuietly(millisecondsToSleep);
            }
        }
    }

    private void sleepQuietly(long millisecondsToSleep)
    {
        try
        {
            Thread.sleep(millisecondsToSleep);
        }
        catch (InterruptedException e)
        {
        }
    }

    private OptionalInt frameCap()
    {
        return configuration.getGraphics().getFrameCap();
    }

    @Override
    public float getDelta()
    {
        return delta;
    }

    @Override
    public int getUpdatesPerSecond()
    {
        return updatesPerSecond;
    }
}
