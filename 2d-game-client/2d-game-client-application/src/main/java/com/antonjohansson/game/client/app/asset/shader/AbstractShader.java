package com.antonjohansson.game.client.app.asset.shader;

import com.antonjohansson.game.client.app.asset.common.IAsset;

/**
 * Abstract skeleton for shaders.
 */
abstract class AbstractShader implements IAsset
{
    private final int handle;
    private final String name;

    AbstractShader(int handle, String name)
    {
        this.handle = handle;
        this.name = name;
    }

    // TODO: reduce to package privacy
    public int getHandle()
    {
        return handle;
    }

    @Override
    public Object getIdentifier()
    {
        return name;
    }
}
