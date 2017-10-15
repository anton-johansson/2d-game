package com.antonjohansson.game.client.math;

import static com.antonjohansson.game.client.math.Matrix4.IDENTITY;
import static com.antonjohansson.game.client.math.Matrix4.NUMBER_OF_FLOATS;
import static com.antonjohansson.game.client.math.Matrix4.ZERO;

import java.nio.FloatBuffer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests of {@link Matrix4}.
 */
public class Matrix4Test extends Assert
{
    private static final float DELTA = 0.000000000001F;

    @Test
    public void test_IDENTITY_constant()
    {
        assertEquals(1.0F, Matrix4.IDENTITY.getM11(), DELTA);
        assertEquals(0.0F, Matrix4.IDENTITY.getM12(), DELTA);
        assertEquals(0.0F, Matrix4.IDENTITY.getM13(), DELTA);
        assertEquals(0.0F, Matrix4.IDENTITY.getM14(), DELTA);
        assertEquals(0.0F, Matrix4.IDENTITY.getM21(), DELTA);
        assertEquals(1.0F, Matrix4.IDENTITY.getM22(), DELTA);
        assertEquals(0.0F, Matrix4.IDENTITY.getM23(), DELTA);
        assertEquals(0.0F, Matrix4.IDENTITY.getM24(), DELTA);
        assertEquals(0.0F, Matrix4.IDENTITY.getM31(), DELTA);
        assertEquals(0.0F, Matrix4.IDENTITY.getM32(), DELTA);
        assertEquals(1.0F, Matrix4.IDENTITY.getM33(), DELTA);
        assertEquals(0.0F, Matrix4.IDENTITY.getM34(), DELTA);
        assertEquals(0.0F, Matrix4.IDENTITY.getM41(), DELTA);
        assertEquals(0.0F, Matrix4.IDENTITY.getM42(), DELTA);
        assertEquals(0.0F, Matrix4.IDENTITY.getM43(), DELTA);
        assertEquals(1.0F, Matrix4.IDENTITY.getM44(), DELTA);

        assertOperationNotSupported(Matrix4.IDENTITY::setM11);
        assertOperationNotSupported(Matrix4.IDENTITY::setM12);
        assertOperationNotSupported(Matrix4.IDENTITY::setM13);
        assertOperationNotSupported(Matrix4.IDENTITY::setM14);
        assertOperationNotSupported(Matrix4.IDENTITY::setM21);
        assertOperationNotSupported(Matrix4.IDENTITY::setM22);
        assertOperationNotSupported(Matrix4.IDENTITY::setM23);
        assertOperationNotSupported(Matrix4.IDENTITY::setM24);
        assertOperationNotSupported(Matrix4.IDENTITY::setM31);
        assertOperationNotSupported(Matrix4.IDENTITY::setM32);
        assertOperationNotSupported(Matrix4.IDENTITY::setM33);
        assertOperationNotSupported(Matrix4.IDENTITY::setM34);
        assertOperationNotSupported(Matrix4.IDENTITY::setM41);
        assertOperationNotSupported(Matrix4.IDENTITY::setM42);
        assertOperationNotSupported(Matrix4.IDENTITY::setM43);
        assertOperationNotSupported(Matrix4.IDENTITY::setM44);
        assertOperationNotSupported(Matrix4.IDENTITY::setIdentity);
        assertOperationNotSupported(Matrix4.IDENTITY::setZero);
    }

    @Test
    public void test_ZERO_constant()
    {
        assertEquals(0.0F, Matrix4.ZERO.getM11(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM12(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM13(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM14(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM21(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM22(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM23(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM24(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM31(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM32(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM33(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM34(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM41(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM42(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM43(), DELTA);
        assertEquals(0.0F, Matrix4.ZERO.getM44(), DELTA);

        assertOperationNotSupported(Matrix4.ZERO::setM11);
        assertOperationNotSupported(Matrix4.ZERO::setM12);
        assertOperationNotSupported(Matrix4.ZERO::setM13);
        assertOperationNotSupported(Matrix4.ZERO::setM14);
        assertOperationNotSupported(Matrix4.ZERO::setM21);
        assertOperationNotSupported(Matrix4.ZERO::setM22);
        assertOperationNotSupported(Matrix4.ZERO::setM23);
        assertOperationNotSupported(Matrix4.ZERO::setM24);
        assertOperationNotSupported(Matrix4.ZERO::setM31);
        assertOperationNotSupported(Matrix4.ZERO::setM32);
        assertOperationNotSupported(Matrix4.ZERO::setM33);
        assertOperationNotSupported(Matrix4.ZERO::setM34);
        assertOperationNotSupported(Matrix4.ZERO::setM41);
        assertOperationNotSupported(Matrix4.ZERO::setM42);
        assertOperationNotSupported(Matrix4.ZERO::setM43);
        assertOperationNotSupported(Matrix4.ZERO::setM44);
        assertOperationNotSupported(Matrix4.ZERO::setIdentity);
        assertOperationNotSupported(Matrix4.ZERO::setZero);
    }

    @Test
    public void test_empty_constructor()
    {
        Matrix4 matrix = new Matrix4();
        assertEquals(IDENTITY, matrix);
    }

    @Test
    public void test_constructor()
    {
        Matrix4 matrix = new Matrix4(1.1F, 1.2F, 1.3F, 1.4F, 2.1F, 2.2F, 2.3F, 2.4F, 3.1F, 3.2F, 3.3F, 3.4F, 4.1F, 4.2F, 4.3F, 4.4F);
        assertEquals(1.1F, matrix.getM11(), DELTA);
        assertEquals(1.2F, matrix.getM12(), DELTA);
        assertEquals(1.3F, matrix.getM13(), DELTA);
        assertEquals(1.4F, matrix.getM14(), DELTA);
        assertEquals(2.1F, matrix.getM21(), DELTA);
        assertEquals(2.2F, matrix.getM22(), DELTA);
        assertEquals(2.3F, matrix.getM23(), DELTA);
        assertEquals(2.4F, matrix.getM24(), DELTA);
        assertEquals(3.1F, matrix.getM31(), DELTA);
        assertEquals(3.2F, matrix.getM32(), DELTA);
        assertEquals(3.3F, matrix.getM33(), DELTA);
        assertEquals(3.4F, matrix.getM34(), DELTA);
        assertEquals(4.1F, matrix.getM41(), DELTA);
        assertEquals(4.2F, matrix.getM42(), DELTA);
        assertEquals(4.3F, matrix.getM43(), DELTA);
        assertEquals(4.4F, matrix.getM44(), DELTA);
    }

    @Test
    public void test_setters()
    {
        Matrix4 matrix = new Matrix4();
        matrix.setM11(1.1F);
        matrix.setM12(1.2F);
        matrix.setM13(1.3F);
        matrix.setM14(1.4F);
        matrix.setM21(2.1F);
        matrix.setM22(2.2F);
        matrix.setM23(2.3F);
        matrix.setM24(2.4F);
        matrix.setM31(3.1F);
        matrix.setM32(3.2F);
        matrix.setM33(3.3F);
        matrix.setM34(3.4F);
        matrix.setM41(4.1F);
        matrix.setM42(4.2F);
        matrix.setM43(4.3F);
        matrix.setM44(4.4F);
        assertEquals(1.1F, matrix.getM11(), DELTA);
        assertEquals(1.2F, matrix.getM12(), DELTA);
        assertEquals(1.3F, matrix.getM13(), DELTA);
        assertEquals(1.4F, matrix.getM14(), DELTA);
        assertEquals(2.1F, matrix.getM21(), DELTA);
        assertEquals(2.2F, matrix.getM22(), DELTA);
        assertEquals(2.3F, matrix.getM23(), DELTA);
        assertEquals(2.4F, matrix.getM24(), DELTA);
        assertEquals(3.1F, matrix.getM31(), DELTA);
        assertEquals(3.2F, matrix.getM32(), DELTA);
        assertEquals(3.3F, matrix.getM33(), DELTA);
        assertEquals(3.4F, matrix.getM34(), DELTA);
        assertEquals(4.1F, matrix.getM41(), DELTA);
        assertEquals(4.2F, matrix.getM42(), DELTA);
        assertEquals(4.3F, matrix.getM43(), DELTA);
        assertEquals(4.4F, matrix.getM44(), DELTA);
    }

    @Test
    public void test_setIdentity()
    {
        Matrix4 matrix = new Matrix4().setIdentity();
        assertEquals(IDENTITY, matrix);
    }

    @Test
    public void test_setZero()
    {
        Matrix4 matrix = new Matrix4().setZero();
        assertEquals(ZERO, matrix);
    }

    @Test
    public void test_feed()
    {
        Matrix4 matrix1 = Matrix4.IDENTITY;
        Matrix4 matrix2 = new Matrix4(1.1F, 1.2F, 1.3F, 1.4F, 2.1F, 2.2F, 2.3F, 2.4F, 3.1F, 3.2F, 3.3F, 3.4F, 4.1F, 4.2F, 4.3F, 4.4F);

        FloatBuffer buffer = FloatBuffer.allocate(NUMBER_OF_FLOATS * 2);
        matrix1.feed(buffer);
        matrix2.feed(buffer);
        buffer.rewind();

        // matrix1
        assertEquals(1.0F, buffer.get(), DELTA);
        assertEquals(0.0F, buffer.get(), DELTA);
        assertEquals(0.0F, buffer.get(), DELTA);
        assertEquals(0.0F, buffer.get(), DELTA);
        assertEquals(0.0F, buffer.get(), DELTA);
        assertEquals(1.0F, buffer.get(), DELTA);
        assertEquals(0.0F, buffer.get(), DELTA);
        assertEquals(0.0F, buffer.get(), DELTA);
        assertEquals(0.0F, buffer.get(), DELTA);
        assertEquals(0.0F, buffer.get(), DELTA);
        assertEquals(1.0F, buffer.get(), DELTA);
        assertEquals(0.0F, buffer.get(), DELTA);
        assertEquals(0.0F, buffer.get(), DELTA);
        assertEquals(0.0F, buffer.get(), DELTA);
        assertEquals(0.0F, buffer.get(), DELTA);
        assertEquals(1.0F, buffer.get(), DELTA);

        // matrix2
        assertEquals(1.1F, buffer.get(), DELTA);
        assertEquals(1.2F, buffer.get(), DELTA);
        assertEquals(1.3F, buffer.get(), DELTA);
        assertEquals(1.4F, buffer.get(), DELTA);
        assertEquals(2.1F, buffer.get(), DELTA);
        assertEquals(2.2F, buffer.get(), DELTA);
        assertEquals(2.3F, buffer.get(), DELTA);
        assertEquals(2.4F, buffer.get(), DELTA);
        assertEquals(3.1F, buffer.get(), DELTA);
        assertEquals(3.2F, buffer.get(), DELTA);
        assertEquals(3.3F, buffer.get(), DELTA);
        assertEquals(3.4F, buffer.get(), DELTA);
        assertEquals(4.1F, buffer.get(), DELTA);
        assertEquals(4.2F, buffer.get(), DELTA);
        assertEquals(4.3F, buffer.get(), DELTA);
        assertEquals(4.4F, buffer.get(), DELTA);
    }

    @Test
    public void test_ortho()
    {
        Matrix4 value = Matrix4.ortho(0.0F, 800.0F, 0.0F, 600.0F, 1.0F, -1.0F);

        assertEquals(0.0025F, value.getM11(), DELTA);
        assertEquals(0.0F, value.getM12(), DELTA);
        assertEquals(0.0F, value.getM13(), DELTA);
        assertEquals(0.0F, value.getM14(), DELTA);
        assertEquals(0.0F, value.getM21(), DELTA);
        assertEquals(0.0033333334F, value.getM22(), DELTA);
        assertEquals(0.0F, value.getM23(), DELTA);
        assertEquals(0.0F, value.getM24(), DELTA);
        assertEquals(0.0F, value.getM31(), DELTA);
        assertEquals(0.0F, value.getM32(), DELTA);
        assertEquals(1.0F, value.getM33(), DELTA);
        assertEquals(0.0F, value.getM34(), DELTA);
        assertEquals(-1.0F, value.getM41(), DELTA);
        assertEquals(-1.0F, value.getM42(), DELTA);
        assertEquals(0.0F, value.getM43(), DELTA);
        assertEquals(1.0F, value.getM44(), DELTA);
    }

    private void assertOperationNotSupported(Consumer<Float> method)
    {
        try
        {
            method.accept(123.45F);
            fail("Expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
        }
    }

    private void assertOperationNotSupported(Supplier<?> method)
    {
        try
        {
            method.get();
            fail("Expected UnsupportedOperationException");
        }
        catch (UnsupportedOperationException e)
        {
        }
    }
}
