package com.antonjohansson.game.client.app.asset.input;

/**
 * Provides operations for working with input.
 */
public interface IInputManager
{
    /**
     * Gets whether or not a {@link Key key} is held down.
     * 
     * @param key The key to check.
     * @return Returns {@code true} if the key is held down; otherwise, {@code false}.
     */
    boolean isKeyDown(Key key);
}
