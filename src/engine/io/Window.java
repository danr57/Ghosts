package engine.io;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.nio.DoubleBuffer;

//Window class is responsible for drawing the game window and the user's interaction with it
public class Window {
    private int width, height;
    private String title;
    private long window;
    private Vector3f backgroundColour;
    private double fps_cap, time, processedTime = 0;
    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    public Window(int width, int height, int fps, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        fps_cap = fps;
        time = getTime();
        backgroundColour = new Vector3f(0.0f, 0.0f, 0.0f);

    }

    public void create() {

        if (!GLFW.glfwInit()) {
            System.err.println("Error! GLFW failed to initialise.");
            System.exit(-1);
        }

        // GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) {
            System.err.println("Error! Couldn't create window.");
            System.exit(-1);
        }

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);

        GLFW.glfwShowWindow(window);
    }

    public boolean closed() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void stop() {
        GLFW.glfwTerminate();
    }

    public void update() {
        for (int i = 0; i < GLFW.GLFW_KEY_LAST; i++) {
            keys[i] = isKeyDown(i);
        }

        for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) {
            mouseButtons[i] = isMouseDown(i);
        }
        GL11.glClearColor(backgroundColour.x, backgroundColour.y, backgroundColour.z, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GLFW.glfwPollEvents();
    }

    // Gives us how much time has passed
    public boolean isUpdating() {
        double nextTime = getTime();
        double passedTime = nextTime - time;
        processedTime += passedTime;
        time = nextTime;

        while (processedTime > 1.0 / fps_cap) {
            processedTime -= 1.0 / fps_cap;
            return true;
        }
        return false;
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public double getTime() {
        return (double) System.nanoTime() / (double) 1000000000;
    }

    public boolean isKeyDown(int keyCode) {
        return GLFW.glfwGetKey(window, keyCode) == 1;

    }

    public boolean isKeyPressed(int keyCode) {
        return isKeyDown(keyCode) && !keys[keyCode];
    }

    public boolean isKeyReleased(int keyCode) {
        return !isKeyDown(keyCode) && keys[keyCode];
    }

    public boolean isMouseDown(int mouseButton) {
        return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
    }

    public boolean isMousePressed(int mouseButton) {
        return isMouseDown(mouseButton) && !mouseButtons[mouseButton];
    }

    public boolean isMouseReleased(int mouseButton) {
        return !isMouseDown(mouseButton) && mouseButtons[mouseButton];
    }


    /*
    Getters and Setters
    */
    public double getMouseX() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window, buffer, null);
        return buffer.get(0);
    }

    public double getMouseY() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window, null, buffer);
        return buffer.get(0);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }

    public double getFps_cap() {
        return fps_cap;
    }

    public void setFps_cap(double fps_cap) {
        this.fps_cap = fps_cap;
    }

    public void setBackgroundColour(float r, float g, float b) {
        backgroundColour = new Vector3f(r, g, b);
    }
}
