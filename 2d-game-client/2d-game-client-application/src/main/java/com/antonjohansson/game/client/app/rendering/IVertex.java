package com.antonjohansson.game.client.app.rendering;

import java.nio.FloatBuffer;

/**
 * Defines a vertex.
 */
public interface IVertex
{
    /**
     * Gets the number of floats required to store this vertex type.
     *
     * @return Returns the number of floats.
     */
    int getNumberOfFloats();

    /**
     * Gets the attributes used for this vertex.
     *
     * @return Returns the attributes.
     */
    VertexAttribute[] getAttributes();

    /**
     * Feeds this vertex into the given buffer.
     *
     * @param buffer The buffer to feed.
     */
    void feed(FloatBuffer buffer);
}
