package com.antonjohansson.game.client.app.asset.font;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.stb.STBTruetype;

import com.antonjohansson.game.client.app.asset.common.IAsset;

/**
 * Defines a renderable bitmap font.
 */
public class Font implements IAsset
{
    private final FontKey key;
    private final STBTTPackedchar.Buffer characterData;
    private final int textureId;
    private final int textureWidth;
    private final int textureHeight;

    Font(FontKey key, STBTTPackedchar.Buffer characterData, int textureId, int textureWidth, int textureHeight)
    {
        this.key = key;
        this.characterData = characterData;
        this.textureId = textureId;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public Object getIdentifier()
    {
        return key;
    }

    int getTextureId()
    {
        return textureId;
    }

    STBTTPackedchar.Buffer getCharacterData()
    {
        return characterData;
    }

    private void print(float x, float y, String text)
    {
        FloatBuffer xb = BufferUtils.createFloatBuffer(1);
        FloatBuffer yb = BufferUtils.createFloatBuffer(1);

        STBTTAlignedQuad q = STBTTAlignedQuad.malloc();

        xb.put(0, x);
        yb.put(0, -y);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        GL11.glBegin(GL11.GL_QUADS);

        for (int i = 0; i < text.length(); i++)
        {
            int character = text.charAt(i);
            STBTruetype.stbtt_GetPackedQuad(characterData, textureWidth, textureHeight, character, xb, yb, q, false);
            drawBoxTC(
                    q.x0() + 1, -q.y0() - 1, q.x1() + 1, -q.y1() - 1,
                    q.s0(), q.t0(), q.s1(), q.t1(), true);
            drawBoxTC(
                    q.x0(), -q.y0(), q.x1(), -q.y1(),
                    q.s0(), q.t0(), q.s1(), q.t1(), false);
        }
        GL11.glEnd();

        q.free();
    }

    private static void drawBoxTC(float x0, float y0, float x1, float y1, float s0, float t0, float s1, float t1, boolean black)
    {
        float c = black ? 0 : 1;

        GL11.glTexCoord2f(s0, t0);
        GL11.glColor4f(c, c, c, 1);
        GL11.glVertex2f(x0, y0);
        GL11.glTexCoord2f(s1, t0);
        GL11.glColor4f(c, c, c, 1);
        GL11.glVertex2f(x1, y0);
        GL11.glTexCoord2f(s1, t1);
        GL11.glColor4f(c, c, c, 1);
        GL11.glVertex2f(x1, y1);
        GL11.glTexCoord2f(s0, t1);
        GL11.glColor4f(c, c, c, 1);
        GL11.glVertex2f(x0, y1);
    }

    /**
     * Prints a text.
     * 
     * @param text The text to print.
     * @param x The X-location.F
     * @param y The Y-location.
     */
    public void print(String text, float x, float y)
    {
        print(x, y, text);
    }
}
