package com.antonjohansson.game.asset.map;

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
}
