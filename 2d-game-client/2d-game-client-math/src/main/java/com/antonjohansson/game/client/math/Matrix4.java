package com.antonjohansson.game.client.math;

import java.nio.FloatBuffer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Defines a four times for sized matrix.
 */
public class Matrix4
{
    /** Defines a read-only matrix that represents identity. */
    public static final Matrix4 IDENTITY = new UnmodifiableMatrix4(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);

    /** Defines a read-only matrix that represents zero. */
    public static final Matrix4 ZERO = new UnmodifiableMatrix4(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    /** The number of floats in a single matrix. */
    public static final int NUMBER_OF_FLOATS = 16;

    // CSOFF
    private float m11, m12, m13, m14;
    private float m21, m22, m23, m24;
    private float m31, m32, m33, m34;
    private float m41, m42, m43, m44;
    // CSON

    /**
     * Creates a new {@link Matrix4 matrix} at identity.
     */
    public Matrix4()
    {
        m11 = 1.0F;
        m22 = 1.0F;
        m33 = 1.0F;
        m44 = 1.0F;
    }

    /**
     * Creates a new {@link Matrix4 matrix} with the given data.
     */
    // CSOFF
    public Matrix4(
            float m11, float m12, float m13, float m14,
            float m21, float m22, float m23, float m24,
            float m31, float m32, float m33, float m34,
            float m41, float m42, float m43, float m44)
    // CSON
    {
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m14 = m14;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m24 = m24;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
        this.m34 = m34;
        this.m41 = m41;
        this.m42 = m42;
        this.m43 = m43;
        this.m44 = m44;
    }

    public float getM11()
    {
        return m11;
    }

    public void setM11(float m11)
    {
        this.m11 = m11;
    }

    public float getM12()
    {
        return m12;
    }

    public void setM12(float m12)
    {
        this.m12 = m12;
    }

    public float getM13()
    {
        return m13;
    }

    public void setM13(float m13)
    {
        this.m13 = m13;
    }

    public float getM14()
    {
        return m14;
    }

    public void setM14(float m14)
    {
        this.m14 = m14;
    }

    public float getM21()
    {
        return m21;
    }

    public void setM21(float m21)
    {
        this.m21 = m21;
    }

    public float getM22()
    {
        return m22;
    }

    public void setM22(float m22)
    {
        this.m22 = m22;
    }

    public float getM23()
    {
        return m23;
    }

    public void setM23(float m23)
    {
        this.m23 = m23;
    }

    public float getM24()
    {
        return m24;
    }

    public void setM24(float m24)
    {
        this.m24 = m24;
    }

    public float getM31()
    {
        return m31;
    }

    public void setM31(float m31)
    {
        this.m31 = m31;
    }

    public float getM32()
    {
        return m32;
    }

    public void setM32(float m32)
    {
        this.m32 = m32;
    }

    public float getM33()
    {
        return m33;
    }

    public void setM33(float m33)
    {
        this.m33 = m33;
    }

    public float getM34()
    {
        return m34;
    }

    public void setM34(float m34)
    {
        this.m34 = m34;
    }

    public float getM41()
    {
        return m41;
    }

    public void setM41(float m41)
    {
        this.m41 = m41;
    }

    public float getM42()
    {
        return m42;
    }

    public void setM42(float m42)
    {
        this.m42 = m42;
    }

    public float getM43()
    {
        return m43;
    }

    public void setM43(float m43)
    {
        this.m43 = m43;
    }

    public float getM44()
    {
        return m44;
    }

    public void setM44(float m44)
    {
        this.m44 = m44;
    }

    /**
     * Sets the matrix to identity.
     *
     * @return Returns this instance (<b>not a copy</b>), used for chaining.
     */
    public Matrix4 setIdentity()
    {
        this.m11 = 1.0F;
        this.m12 = 0.0F;
        this.m13 = 0.0F;
        this.m14 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = 1.0F;
        this.m23 = 0.0F;
        this.m24 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
        this.m34 = 0.0F;
        this.m41 = 0.0F;
        this.m42 = 0.0F;
        this.m43 = 0.0F;
        this.m44 = 1.0F;
        return this;
    }

    /**
     * Sets the matrix to zero.
     *
     * @return Returns this instance (<b>not a copy</b>), used for chaining.
     */
    public Matrix4 setZero()
    {
        this.m11 = 0.0F;
        this.m12 = 0.0F;
        this.m13 = 0.0F;
        this.m14 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = 0.0F;
        this.m23 = 0.0F;
        this.m24 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 0.0F;
        this.m34 = 0.0F;
        this.m41 = 0.0F;
        this.m42 = 0.0F;
        this.m43 = 0.0F;
        this.m44 = 0.0F;
        return this;
    }

    /**
     * Feeds this matrix into the given {@link FloatBuffer buffer of floating points}.
     *
     * @param buffer The buffer to feed.
     */
    public void feed(FloatBuffer buffer)
    {
        buffer.put(m11).put(m12).put(m13).put(m14);
        buffer.put(m21).put(m22).put(m23).put(m24);
        buffer.put(m31).put(m32).put(m33).put(m34);
        buffer.put(m41).put(m42).put(m43).put(m44);
    }

    /**
     * Creates a new matrix that produces parallel projection.
     *
     * @param left The left frustum plane.
     * @param right The right frustum plane.
     * @param bottom The bottom frustum plane.
     * @param top The top frustum plane.
     * @param near The near frustum plane.
     * @param far The far frustum plane.
     * @return Returns the orthographic matrix.
     */
    public static Matrix4 ortho(float left, float right, float bottom, float top, float near, float far)
    {
        // CSOFF
        return new Matrix4(
                2.0F / (right - left),
                0.0F,
                0.0F,
                0.0F,
                0.0F,
                2.0F / (top - bottom),
                0.0F,
                0.0F,
                0.0F,
                0.0F,
                -2.0F / (far - near),
                0.0F,
                -(right + left) / (right - left),
                -(top + bottom) / (top - bottom),
                -(far + near) / (far - near),
                1.0F);
        // CSON
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .append(m11)
                .append(m12)
                .append(m13)
                .append(m14)
                .append(m21)
                .append(m22)
                .append(m23)
                .append(m24)
                .append(m31)
                .append(m32)
                .append(m33)
                .append(m34)
                .append(m41)
                .append(m42)
                .append(m43)
                .append(m44)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Matrix4)
        {
            Matrix4 that = (Matrix4) obj;
            return new EqualsBuilder()
                    .append(this.m11, that.m11)
                    .append(this.m12, that.m12)
                    .append(this.m13, that.m13)
                    .append(this.m14, that.m14)
                    .append(this.m21, that.m21)
                    .append(this.m22, that.m22)
                    .append(this.m23, that.m23)
                    .append(this.m24, that.m24)
                    .append(this.m31, that.m31)
                    .append(this.m32, that.m32)
                    .append(this.m33, that.m33)
                    .append(this.m34, that.m34)
                    .append(this.m41, that.m41)
                    .append(this.m42, that.m42)
                    .append(this.m43, that.m43)
                    .append(this.m44, that.m44)
                    .isEquals();
        }
        return false;
    }
}
