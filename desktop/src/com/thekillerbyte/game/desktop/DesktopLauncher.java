package com.thekillerbyte.game.desktop;

import comer.badlogic.gdx.ai.tests.PathFinderTests;
import comer.badlogic.gdx.ai.tests.pfa.tests.tiled.DungeonUtils;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.thekillerbyte.game.TestStarter;
import com.thekillerbyte.game.Tests.HeightmapTester;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        config.vSyncEnabled = false;
        
        config.title = "Game - Dev";
        //new LwjglApplication(new TestStarter(), config);
	  new LwjglApplication(new HeightmapTester(),config);
        //DungeonUtils.main(arg);
        //PathFinderTests.main(arg);
    }
}
