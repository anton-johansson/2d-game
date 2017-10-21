package com.antonjohansson.game.client.app.rendering;

import static java.util.Objects.requireNonNull;

import org.lwjgl.opengl.GL15;

/**
 * Defines hints to the graphics library about how often data is updated in a buffer.
 *
 * @see https://www.khronos.org/opengl/wiki/Buffer_Object#Buffer_Object_Usage
 */
public enum FrequencyHint
{
    /**
     * The data in the buffer is updated in the GPU only once. Typically useful for rendering static meshes.
     */
    STATIC(GL15.GL_STATIC_DRAW, GL15.GL_STATIC_READ, GL15.GL_STATIC_COPY),

    /**
     * The data in the buffer is updated in the GPU occasionally. It's not very clear when to use this hint.
     */
    DYNAMIC(GL15.GL_DYNAMIC_DRAW, GL15.GL_DYNAMIC_READ, GL15.GL_DYNAMIC_COPY),

    /**
     * The data in the buffer is updated in the GPU at almost every frame. This is common for meshes that change shape in one way or another.
     */
    STREAM(GL15.GL_STREAM_DRAW, GL15.GL_STREAM_READ, GL15.GL_STREAM_COPY);

    private final int internalForWrite;
    private final int internalForRead;
    private final int internalForCopy;

    FrequencyHint(int internalForWrite, int internalForRead, int internalForCopy)
    {
        this.internalForWrite = internalForWrite;
        this.internalForRead = internalForRead;
        this.internalForCopy = internalForCopy;
    }

    int forStorage(StorageHint hint)
    {
        switch (requireNonNull(hint, "The given 'hint' cannot be null"))
        {
            case WRITE: return internalForWrite;
            case READ: return internalForRead;
            case COPY: return internalForCopy;

            default:
                throw new IllegalArgumentException("The given hint isn't a legal hint: " + hint);
        }
    }
}
