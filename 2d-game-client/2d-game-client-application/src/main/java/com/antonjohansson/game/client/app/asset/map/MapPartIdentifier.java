package com.antonjohansson.game.client.app.asset.map;

import java.util.Objects;

/**
 * Defines an identifier for loading {@link MapPart map parts}.
 */
public class MapPartIdentifier
{
    private final int x;
    private final int y;

    private MapPartIdentifier(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets a new identifier of the given coordinates.
     *
     * @param x The x-coodrinate.
     * @param y The y-coordinate.
     * @return Returns the identifier.
     */
    public static MapPartIdentifier of(int x, int y)
    {
        return new MapPartIdentifier(x, y);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || obj.getClass() != getClass())
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }

        MapPartIdentifier that = (MapPartIdentifier) obj;
        return this.x == that.x
            && this.y == that.y;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
