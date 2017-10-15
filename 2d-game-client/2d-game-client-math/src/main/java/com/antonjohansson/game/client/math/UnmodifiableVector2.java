package com.antonjohansson.game.client.math;

/**
 * Extension of {@link Vector2} that cannot be modified after being created.
 */
class UnmodifiableVector2 extends Vector2
{
    /**
     * Creates a new {@link UnmodifiableVector2} with the given coordinates.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     */
    UnmodifiableVector2(float x, float y)
    {
        super(x, y);
    }

    @Override
    public void setX(float x)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setY(float y)
    {
        throw unsupportedOperationException();
    }

    @Override
    public Vector2 negate()
    {
        throw unsupportedOperationException();
    }

    private UnsupportedOperationException unsupportedOperationException()
    {
        return new UnsupportedOperationException("Cannot set values");
    }
}
