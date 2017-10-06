package com.antonjohansson.game.asset.input;

import org.lwjgl.glfw.GLFW;

/**
 * Defines keys of the keyboard.
 */
public enum Key
{
    LEFT(GLFW.GLFW_KEY_LEFT),
    RIGHT(GLFW.GLFW_KEY_RIGHT),
    UP(GLFW.GLFW_KEY_UP),
    DOWN(GLFW.GLFW_KEY_DOWN);

    private final int value;

    Key(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}
