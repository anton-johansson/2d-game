package com.antonjohansson.game.client.math;

import java.nio.FloatBuffer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests of {@link Vector2}.
 */
public class Vector2Test extends Assert
{
    private static final float DELTA = 0.000000001F;

    @Test
    public void test_constants()
    {
        assertEquals(0.0F, Vector2.ZERO.getX(), DELTA);
        assertEquals(0.0F, Vector2.ZERO.getY(), DELTA);
        assertEquals(1.0F, Vector2.ONE.getX(), DELTA);
        assertEquals(1.0F, Vector2.ONE.getY(), DELTA);

        assertOperationNotSupported(Vector2.ZERO::setX);
        assertOperationNotSupported(Vector2.ZERO::setY);
        assertOperationNotSupported(Vector2.ZERO::negate);
        assertOperationNotSupported(Vector2.ONE::setX);
        assertOperationNotSupported(Vector2.ONE::setY);
        assertOperationNotSupported(Vector2.ONE::negate);
    }

    @Test
    public void test_empty_constructor()
    {
        Vector2 vector = new Vector2();
        assertEquals(0.0F, vector.getX(), DELTA);
        assertEquals(0.0F, vector.getY(), DELTA);
    }

    @Test
    public void test_xy_constructor()
    {
        Vector2 vector = new Vector2(12.34F, -66.66F);
        assertEquals(12.34F, vector.getX(), DELTA);
        assertEquals(-66.66F, vector.getY(), DELTA);
    }

    @Test
    public void test_setters()
    {
        Vector2 vector = new Vector2();
        vector.setX(-13.37F);
        vector.setY(1.23F);
        assertEquals(-13.37F, vector.getX(), DELTA);
        assertEquals(1.23F, vector.getY(), DELTA);
    }

    @Test
    public void test_feed()
    {
        Vector2 vector1 = new Vector2(60.29F, 54.91F);
        Vector2 vector2 = new Vector2(-9.35F, 2.85F);

        FloatBuffer buffer = FloatBuffer.allocate(4);
        vector1.feed(buffer);
        vector2.feed(buffer);
        buffer.rewind();

        assertEquals(60.29F, buffer.get(), DELTA);
        assertEquals(54.91F, buffer.get(), DELTA);
        assertEquals(-9.35F, buffer.get(), DELTA);
        assertEquals(2.85F, buffer.get(), DELTA);
    }

    @Test
    public void test_negate()
    {
        Vector2 vector = new Vector2(91.51F, -51.94F).negate();
        assertEquals(-91.51F, vector.getX(), DELTA);
        assertEquals(51.94F, vector.getY(), DELTA);
    }

    private void assertOperationNotSupported(Consumer<Float> method)
    {
        try
        {
            method.accept(123.45F);
            fail("Expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
        }
    }

    private void assertOperationNotSupported(Supplier<?> method)
    {
        try
        {
            method.get();
            fail("Expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
        }
    }
}
