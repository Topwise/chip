package com.saucepls.chip.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.saucepls.chip.Chip;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Chip";
		config.useGL30 = false;
		config.width = 800;
		config.height = 800;
		new LwjglApplication(new Chip(), config);
	}
}
