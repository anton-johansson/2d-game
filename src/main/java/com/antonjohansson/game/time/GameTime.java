package com.antonjohansson.game.time;

import java.util.OptionalInt;

import com.antonjohansson.game.config.Configuration;

/**
 * Controls the game time.
 */
public class GameTime
{
    private static final float NANO_TO_SECONDS_DIVIDER = 1_000_000_000;
    private static final float NANO_TO_MILLISECONDS_DIVIDER = 1_000_000;
    private static final int ONE_SECOND_IN_MILLISECONDS = 1_000;

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

        delta = (newUpdate - lastUpdate) / NANO_TO_SECONDS_DIVIDER;

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

            long estimatedTimeAfterFrame = (long) (lastUpdate / NANO_TO_MILLISECONDS_DIVIDER) + (long) (secondsPerFrame * ONE_SECOND_IN_MILLISECONDS);
            long now = (long) (time() / NANO_TO_MILLISECONDS_DIVIDER);
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

    public float getDelta()
    {
        return delta;
    }

    public int getUpdatesPerSecond()
    {
        return updatesPerSecond;
    }
}
