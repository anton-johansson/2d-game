package com.antonjohansson.game.world;

import static com.antonjohansson.game.asset.input.Key.DOWN;
import static com.antonjohansson.game.asset.input.Key.LEFT;
import static com.antonjohansson.game.asset.input.Key.RIGHT;
import static com.antonjohansson.game.asset.input.Key.UP;

import com.antonjohansson.game.asset.input.InputManager;
import com.antonjohansson.game.time.IGameTime;

/**
 * Contains information about the playable character.
 */
public class Player
{
    private float x;
    private float y;

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    /**
     * Updates the player.
     *
     * @param gameTime The current game time.
     * @param inputManager The input manager.
     */
    public void update(IGameTime gameTime, InputManager inputManager)
    {
        float deltaX = 0.0F;
        float deltaY = 0.0F;

        if (inputManager.isKeyDown(LEFT))
        {
            deltaX -= 1.0F;
        }
        if (inputManager.isKeyDown(RIGHT))
        {
            deltaX += 1.0F;
        }
        if (inputManager.isKeyDown(DOWN))
        {
            deltaY -= 1.0F;
        }
        if (inputManager.isKeyDown(UP))
        {
            deltaY += 1.0F;
        }

        deltaX = deltaX * gameTime.getDelta() * 0.1F;
        deltaY = deltaY * gameTime.getDelta() * 0.1F;

        x += deltaX;
        y += deltaY;
    }
}
