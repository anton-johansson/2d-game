package com.antonjohansson.game.asset.input;

import org.lwjgl.glfw.GLFW;

/**
 * Default implementation of {@link IInputManager}.
 */
public class InputManager implements IInputManager
{
    private long window;

    public void setWindow(long window)
    {
        this.window = window;
    }

    @Override
    public boolean isKeyDown(Key key)
    {
        return GLFW.glfwGetKey(window, key.getValue()) == GLFW.GLFW_TRUE;
    }
}
