package com.antonjohansson.game.client.app.rendering;

/**
 * Defines hints to the graphics library about how the data should be stored in a buffer, for various types of performance increases.
 *
 * @see https://www.khronos.org/opengl/wiki/Buffer_Object#Buffer_Object_Usage
 */
public enum StorageHint
{
    /**
     * The caller is only reading the buffer from the graphics library. This is useful when the graphics library renders data into a buffer, that the
     * caller can use for various operations. The caller can never write the buffer to the graphics library.
     */
    READ,

    /**
     * The caller is only writing the buffer to the graphics library. This is the most common hint, as it's generally used to upload data to the GPU
     * and render it. The caller can never read the buffer from the graphics library.
     */
    WRITE,

    /**
     * The caller can neither write to or read from the buffer in the graphics library. It's used when the buffer is used as a destination from one
     * location in the graphics library to another location in the graphics library.
     */
    COPY;
}
