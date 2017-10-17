package com.antonjohansson.game.client.app.rendering;

import static org.lwjgl.BufferUtils.createIntBuffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Defines a graphic buffer, containing all required elements of rendering buffers.
 * <p>
 * To create a new buffer and utilize it:
 * <pre>
 * GraphicBuffer<TexturedVertex> buffer = GraphicBuffer.of(TexturedVertex.class);
 * buffer.setVertexData(...);
 * buffer.setIndexData(...);
 * buffer.draw();
 * </pre>
 * </p>
 *
 * @param <V> The type of the vertices of this buffer.
 */
public class GraphicBuffer<V extends IVertex>
{
    private final int numberOfFloatsPerVertex;
    private final int vertexArrayId;
    private final int vertexBufferId;
    private final int indexBufferId;
    private final int numberOfVertexAttributes;
    private int numberOfIndices;

    private GraphicBuffer(int numberOfFloatsPerVertex, int vertexArrayId, int vertexBufferId, int indexBufferId, int numberOfVertexAttributes)
    {
        this.numberOfFloatsPerVertex = numberOfFloatsPerVertex;
        this.vertexArrayId = vertexArrayId;
        this.vertexBufferId = vertexBufferId;
        this.indexBufferId = indexBufferId;
        this.numberOfVertexAttributes = numberOfVertexAttributes;
    }

    private static <V extends IVertex> V vertex(Class<V> vertexClass)
    {
        try
        {
            return vertexClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new {@link GraphicBuffer} of the given {@link IVertex vertex type}.
     *
     * @param vertexClass The type of the vertices of the buffer.
     * @return Returns the created buffer.
     */
    public static <V extends IVertex> GraphicBuffer<V> of(Class<V> vertexClass)
    {
        V vertex = vertex(vertexClass);
        int numberOfFloatsPerVertex = vertex.getNumberOfFloats();
        int strideSize = numberOfFloatsPerVertex * Float.BYTES;

        int vertexArrayId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexArrayId);

        int vertexBufferId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferId);

        VertexAttribute[] attributes = vertex.getAttributes();
        for (int i = 0; i < attributes.length; i++)
        {
            VertexAttribute attribute = attributes[i];
            GL20.glVertexAttribPointer(i, attribute.getSize(), GL11.GL_FLOAT, false, strideSize, attribute.getOffset());
        }

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        int indexBufferId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        return new GraphicBuffer<>(numberOfFloatsPerVertex, vertexArrayId, vertexBufferId, indexBufferId, 2/*attributes.length*/);
    }

    /**
     * Sets the vertex data and uploads it into the vertex buffer stored in the GPU memory.
     *
     * @param vertices The vertices to upload.
     */
    public void setVertexData(V[] vertices)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * numberOfFloatsPerVertex);
        for (V vertex : vertices)
        {
            vertex.feed(buffer);
        }
        buffer.flip();

        GL30.glBindVertexArray(vertexArrayId);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    /**
     * Sets the index data and uploads it into the index buffer stored in the GPU memory.
     *
     * @param indices The indices to upload.
     */
    public void setIndexData(int[] indices)
    {
        if (indices.length % 3 != 0)
        {
            throw new IllegalArgumentException("The input indices must be dividable by three");
        }
        numberOfIndices = indices.length;

        IntBuffer buffer = createIntBuffer(numberOfIndices);
        buffer.put(indices);
        buffer.flip();

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    /**
     * Renders the current buffer.
     */
    public void draw()
    {
        GL30.glBindVertexArray(vertexArrayId);
        for (int i = 0; i < numberOfVertexAttributes; i++)
        {
            GL20.glEnableVertexAttribArray(i);
        }
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
        GL11.glDrawElements(GL11.GL_TRIANGLES, numberOfIndices, GL11.GL_UNSIGNED_INT, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        for (int i = 0; i < numberOfVertexAttributes; i++)
        {
            GL20.glDisableVertexAttribArray(i);
        }
        GL30.glBindVertexArray(0);
    }

    /**
     * Disposes any resources created by this buffer.
     */
    public void dispose()
    {
        GL15.glDeleteBuffers(indexBufferId);
        GL15.glDeleteBuffers(vertexBufferId);
        GL30.glDeleteVertexArrays(vertexArrayId);
    }
}
