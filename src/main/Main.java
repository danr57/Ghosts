package main;

import engine.io.Loader;
import engine.io.Model;
import engine.io.Renderer;
import engine.io.Window;
import org.lwjgl.glfw.GLFW;

public class Main {
    public static final int WIDTH = 1260, HEIGHT = 720, FPS = 60;

    public static void main(String[] args) {
        Window window = new Window(WIDTH, HEIGHT, FPS, "Game Window");
        window.create();
        //window.setBackgroundColour(1.0f, 0.0f, 0.0f);

        int x = 0;

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
                -0.5f, 0.5f, 0f,    // v0
                -0.5f, -0.5f, 0f,   // v1
                0.5f, -0.5f, 0f,    // v2
                0.5f, 0.5f, 0f,     // v3
        };

        int[] indices = {
                0, 1, 3,    //Top left triangle
                3, 1, 2     //Bottom Right triangle
        };

        Model model = loader.loadToVAO(vertices, indices);


        while (!window.closed()) {
            if (window.isUpdating()) {
                window.update();
                renderer.prepare();
                renderer.render(model);

                if (window.isKeyPressed(GLFW.GLFW_KEY_A)) System.out.println("You pressed A");
                if (window.isKeyReleased(GLFW.GLFW_KEY_A)) System.out.println("You released A");
                if (window.isMousePressed(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("You pressed LMB at "
                        + window.getMouseX()
                        + ", "
                        + window.getMouseY());
                if (window.isMouseReleased(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("You released LMB");
                window.swapBuffers();
                x++;
            }


        }

        loader.cleanUp();
        window.stop();

        System.out.println(x);
    }
}
