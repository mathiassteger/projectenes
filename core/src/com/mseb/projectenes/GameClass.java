package com.mseb.projectenes;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mseb.projectenes.logging.CustomLogger;
import com.mseb.projectenes.model.Model;

public class GameClass extends Game {

	
	@Override
	public void create () {
		Gdx.app.setApplicationLogger(new CustomLogger());
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Model.libgdxInit();
		setScreen(Model.screen);
	}

	@Override
	public void render () {

	    screen.render(Gdx.graphics.getDeltaTime());

	}
	
	@Override
	public void dispose () {
	    screen.dispose();
	}
}
