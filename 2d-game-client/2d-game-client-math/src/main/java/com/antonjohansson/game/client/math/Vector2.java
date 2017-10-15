package com.antonjohansson.game.client.math;

import java.nio.FloatBuffer;

/**
 * Defines a 2-dimensional vector, usually used for storing an X-coordinate and a Y-coordinate.
 */
public class Vector2
{
    /** The number of floats contained in this vector. */
    public static final int NUMBER_OF_FLOATS = 2;

    /** The size in bytes of an instance of this type. */
    public static final int SIZE_IN_BYTES = 4 * NUMBER_OF_FLOATS;

    /** Defines the vector at origin (0, 0). */
    public static final Vector2 ZERO = new UnmodifiableVector2(0.0F, 0.0F);

    /** Defines the vector at position one (1, 1). */
    public static final Vector2 ONE = new UnmodifiableVector2(1.0F, 1.0F);

    private float x;
    private float y;

    /**
     * Creates a new, empty {@link Vector2}.
     */
    public Vector2()
    {
    }

    /**
     * Creates a new {@link Vector2} with the given coordinates.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     */
    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    /**
     * Feeds this vector into a {@link FloatBuffer buffer of floating points}.
     *
     * @param buffer The buffer to feed.
     */
    public void feed(FloatBuffer buffer)
    {
        buffer.put(x).put(y);
    }

    /**
     * Negates the vector by changing the sign of the coordinates.
     *
     * @return Returns the current instance (<b>not a copy</b>), used for chaining.
     */
    public Vector2 negate()
    {
        y *= -1;
        x *= -1;
        return this;
    }
}
