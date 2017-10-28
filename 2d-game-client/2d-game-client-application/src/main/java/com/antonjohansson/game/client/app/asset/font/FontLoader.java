package com.antonjohansson.game.client.app.asset.font;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.antonjohansson.game.client.app.asset.IAssetLoader;
import com.antonjohansson.game.client.app.asset.IAssetManager;

/**
 * Loads {@link Font fonts}.
 */
public class FontLoader implements IAssetLoader<Font, FontKey>
{
    private static final int CHARACTERS = 256;

    private String fontLocation;

    @Override
    public void setAssetLocation(String assetLocation)
    {
        this.fontLocation = assetLocation + "fonts/";
    }

    @Override
    public Class<FontKey> getIdentifierType()
    {
        return FontKey.class;
    }

    @Override
    public Font load(FontKey key, IAssetManager manager)
    {
        final int textureWidth = 512;
        final int textureHeight = 512;

        String file = fontLocation + key.getName() + ".ttf";

        try (MemoryStack stack = MemoryStack.stackPush(); STBTTPackContext context = STBTTPackContext.malloc())
        {
            STBTTPackedchar.Buffer characterData = STBTTPackedchar.mallocStack(CHARACTERS, stack);
            ByteBuffer fontData = getByteBuffer(file);
            ByteBuffer bitmap = BufferUtils.createByteBuffer(textureWidth * textureHeight);

            STBTruetype.stbtt_PackBegin(context, bitmap, textureWidth, textureHeight, 0, 1, MemoryUtil.NULL);
            STBTruetype.stbtt_PackSetOversampling(context, 1, 1);
            STBTruetype.stbtt_PackFontRange(context, fontData, 0, key.getSize(), 0, characterData);
            STBTruetype.stbtt_PackEnd(context);

            int textureId = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_ALPHA, textureWidth, textureHeight, 0, GL11.GL_ALPHA, GL11.GL_UNSIGNED_BYTE, bitmap);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

            return new Font(key, characterData, textureId, textureWidth, textureHeight);
        }
    }

    private ByteBuffer getByteBuffer(String file)
    {
        Path path = Paths.get(file);
        try (SeekableByteChannel channel = Files.newByteChannel(path))
        {
            ByteBuffer buffer = BufferUtils.createByteBuffer((int) channel.size() + 1);

            while (channel.read(buffer) != -1)
            // CSOFF
            {
            }
            // CSON

            buffer.flip();
            return buffer;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Could not read font file", e);
        }
    }

    @Override
    public void dispose(Font asset, IAssetManager manager)
    {
        GL11.glDeleteTextures(asset.getTextureId());
    }
}
