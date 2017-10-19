package com.antonjohansson.game.client.app.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests of {@link Utility}.
 */
public class UtilityTest extends Assert
{
    private static final float DELTA = 0.000000000001F;

    @Test
    public void test_toMeters()
    {
        assertEquals(2.5F, Utility.toMeters(160), DELTA);
        assertEquals(12.5F, Utility.toMeters(800), DELTA);
        assertEquals(10.484375F, Utility.toMeters(671), DELTA);
    }

    @Test
    public void test_toPixels()
    {
        assertEquals(160, Utility.toPixels(2.5F), DELTA);
        assertEquals(800, Utility.toPixels(12.5F), DELTA);
        assertEquals(671, Utility.toPixels(10.484375F), DELTA);
    }
}
