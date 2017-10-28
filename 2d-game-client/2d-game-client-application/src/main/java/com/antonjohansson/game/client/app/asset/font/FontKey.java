package com.antonjohansson.game.client.app.asset.font;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Defines the key of a font.
 */
public final class FontKey
{
    private final String name;
    private final int size;

    private FontKey(String name, int size)
    {
        this.name = name;
        this.size = size;
    }

    /**
     * Gets a font key of the given values.
     *
     * @param name The name of the font.
     * @param size The size of the font.
     * @return Returns the key of the font.
     */
    public static FontKey of(String name, int size)
    {
        return new FontKey(name, size);
    }

    String getName()
    {
        return name;
    }

    int getSize()
    {
        return size;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, size);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof FontKey)
        {
            FontKey that = (FontKey) obj;
            return new EqualsBuilder()
                    .append(this.name, that.name)
                    .append(this.size, that.size)
                    .isEquals();
        }
        return false;
    }

    @Override
    public String toString()
    {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
