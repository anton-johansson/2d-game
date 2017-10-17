package com.antonjohansson.game.client.app.rendering;

import java.nio.FloatBuffer;

import com.antonjohansson.game.client.math.Vector2;

/**
 * Defines a textured vertex.
 */
public class TexturedVertex implements IVertex
{
    // Vertex data
    private Vector2 position = Vector2.ZERO;
    private Vector2 textureCoordinate = Vector2.ZERO;

    private static final int POSITION_NUMBER_OF_FLOATS = Vector2.NUMBER_OF_FLOATS;
    private static final int TEXTURECOORDINATE_NUMBER_OF_FLOATS = Vector2.NUMBER_OF_FLOATS;
    private static final int NUMBER_OF_FLOATS = POSITION_NUMBER_OF_FLOATS + TEXTURECOORDINATE_NUMBER_OF_FLOATS;
    private static final VertexAttribute[] ATTRIBUTES = new VertexAttribute[] {
            new VertexAttribute(2, 0), // position
            new VertexAttribute(2, 8)  // textureCoordinate
    };

    /**
     * Constructs a new, empty {@link TexturedVertex}.
     */
    public TexturedVertex()
    {
    }

    /**
     * Constructs a new {@link TexturedVertex} with the given data.
     *
     * @param position The position of the vertex.
     * @param textureCoordinate The texture coordinate of the vertex.
     */
    public TexturedVertex(Vector2 position, Vector2 textureCoordinate)
    {
        this.position = position;
        this.textureCoordinate = textureCoordinate;
    }

    /**
     * Constructs a new {@link TexturedVertex} with the given data.
     *
     * @param positionX The X-coordinate of the position of the vertex.
     * @param positionY The Y-coordinate of the position of the vertex.
     * @param textureCoordinateX The X-coordinate of the texture coordinate of the vertex.
     * @param textureCoordinateX The Y-coordinate of the texture coordinate of the vertex.
     */
    public TexturedVertex(float positionX, float positionY, float textureCoordinateX, float textureCoordinateY)
    {
        this(new Vector2(positionX, positionY), new Vector2(textureCoordinateX, textureCoordinateY));
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public Vector2 getTextureCoordinate()
    {
        return textureCoordinate;
    }

    public void setTextureCoordinate(Vector2 textureCoordinate)
    {
        this.textureCoordinate = textureCoordinate;
    }

    @Override
    public int getNumberOfFloats()
    {
        return NUMBER_OF_FLOATS;
    }

    @Override
    public VertexAttribute[] getAttributes()
    {
        return ATTRIBUTES;
    }

    /**
     * Feeds this vertex into the given buffer.
     *
     * @param buffer The buffer to feed.
     */
    @Override
    public void feed(FloatBuffer buffer)
    {
        position.feed(buffer);
        textureCoordinate.feed(buffer);
    }
}
