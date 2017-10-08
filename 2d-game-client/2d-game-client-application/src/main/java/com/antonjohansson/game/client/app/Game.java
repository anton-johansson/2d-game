package com.antonjohansson.game.client.app;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.antonjohansson.game.client.app.asset.AssetManager;
import com.antonjohansson.game.client.app.asset.IAssetManagerController;
import com.antonjohansson.game.client.app.asset.input.InputManager;
import com.antonjohansson.game.client.app.config.Configuration;
import com.antonjohansson.game.client.app.time.GameTime;
import com.antonjohansson.game.client.app.world.World;

/**
 * Defines the game.
 */
public class Game
{
    private final Configuration configuration;
    private final GameTime gameTime;
    private final IAssetManagerController assetManager;
    private final InputManager inputManager;
    private final World world;
    private long window;

    public Game(Configuration configuration)
    {
        this.configuration = configuration;
        this.gameTime = new GameTime(configuration);
        this.assetManager = new AssetManager();
        this.inputManager = new InputManager();
        this.world = new World();
    }

    /**
     * Runs the game.
     */
    public void run()
    {
        System.out.println("Running LWJGL version " + Version.getVersion());

        initialize();
        loop();
    }

    private void initialize()
    {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit())
        {
            throw new IllegalStateException("Could not initialie GLWF");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

        window = GLFW.glfwCreateWindow(configuration.getGraphics().getWidth(), configuration.getGraphics().getHeight(), "Game 2D", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL)
        {
            throw new IllegalStateException("Could not create GLFW window");
        }

        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) ->
        {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
            {
                GLFW.glfwSetWindowShouldClose(window, true);
            }
        });

        try (MemoryStack stack = MemoryStack.stackPush())
        {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2);
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(configuration.getGraphics().isVerticalSyncEnabled() ? 1 : 0);
        GLFW.glfwShowWindow(window);
    }

    private void loop()
    {
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        assetManager.initialize();
        inputManager.setWindow(window);
        world.initialize(assetManager);
        gameTime.initialize();

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, configuration.getGraphics().getWidth(), 0, configuration.getGraphics().getHeight(), 1, -1);

        while (!GLFW.glfwWindowShouldClose(window))
        {
            GLFW.glfwPollEvents();
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            gameTime.update();
            world.update(gameTime, inputManager);
            world.render();
            gameTime.restrainIfNecessary();

            GLFW.glfwSwapBuffers(window);
        }

        world.dispose(assetManager);
        assetManager.dispose();
        GLFW.glfwTerminate();
    }
}
