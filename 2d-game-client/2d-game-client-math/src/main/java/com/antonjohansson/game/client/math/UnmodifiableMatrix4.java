package com.antonjohansson.game.client.math;

/**
 * Extension of {@link Matrix4} that cannot be modified after being created.
 */
class UnmodifiableMatrix4 extends Matrix4
{
    /**
     * Creates a new {@link Matrix4 matrix} with the given data.
     */
    // CSOFF
    UnmodifiableMatrix4(
            float m11, float m12, float m13, float m14,
            float m21, float m22, float m23, float m24,
            float m31, float m32, float m33, float m34,
            float m41, float m42, float m43, float m44)
    // CSON
    {
        super(
                m11, m12, m13, m14,
                m21, m22, m23, m24,
                m31, m32, m33, m34,
                m41, m42, m43, m44);
    }

    @Override
    public void setM11(float m11)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM12(float m12)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM13(float m13)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM14(float m14)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM21(float m21)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM22(float m22)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM23(float m23)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM24(float m24)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM31(float m31)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM32(float m32)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM33(float m33)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM34(float m34)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM41(float m41)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM42(float m42)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM43(float m43)
    {
        throw unsupportedOperationException();
    }

    @Override
    public void setM44(float m44)
    {
        throw unsupportedOperationException();
    }

    @Override
    public Matrix4 setIdentity()
    {
        throw unsupportedOperationException();
    }

    @Override
    public Matrix4 setZero()
    {
        throw unsupportedOperationException();
    }

    private UnsupportedOperationException unsupportedOperationException()
    {
        return new UnsupportedOperationException("Cannot set values");
    }
}
