package com.antonjohansson.game.client.app.rendering;

/**
 * Defines a vertex attribute.
 */
class VertexAttribute
{
    private final int size;
    private final int offset;

    VertexAttribute(int size, int offset)
    {
        this.size = size;
        this.offset = offset;
    }

    public int getSize()
    {
        return size;
    }

    public int getOffset()
    {
        return offset;
    }
}
