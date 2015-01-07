package com.thekillerbyte.game.Tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.thekillerbyte.game.objekte.FractalTerrain;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class HeightmapTester implements ApplicationListener {

   public Environment environment;
   public PerspectiveCamera cam;
   public CameraInputController camController;
   public ModelBatch modelBatch;
   public Model model;
   public ModelInstance instance;
   public Mesh test;
   ShaderProgram shader;
   public static final String VERT_SHADER
	     = "attribute vec2 a_position;\n"
	     + "attribute vec4 a_color;\n"
	     + "uniform mat4 u_projTrans;\n"
	     + "varying vec4 vColor;\n"
	     + "void main() {\n"
	     + "	vColor = a_color;\n"
	     + "	gl_Position =  u_projTrans * vec4(a_position.xy, 0.0, 1.0);\n"
	     + "}";

   public static final String FRAG_SHADER
	     = "#ifdef GL_ES\n"
	     + "precision mediump float;\n"
	     + "#endif\n"
	     + "varying vec4 vColor;\n"
	     + "void main() {\n"
	     + "	gl_FragColor = vColor;\n"
	     + "}";

   @Override
   public void create() {

	environment = new Environment();
	environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
	environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

	modelBatch = new ModelBatch();

	cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	cam.position.set(10f, 10f, 10f);
	cam.lookAt(0, 0, 0);
	cam.near = 1f;
	cam.far = 300f;
	cam.update();

	/*
	 * @Override
	 * public void create() {
	 * mesh = new Mesh(true, 3, 3,
	 * new VertexAttribute(Usage.Position, 3, "a_position")); *
	 * mesh.setVertices(new float[] { -0.5f, -0.5f, 0, //bottom-left
	 * 0.5f, -0.5f, 0, //bottom-right
	 * 0, 0.5f, 0 }); //top
	 * mesh.setIndices(new short[] { 0, 1, 2 });
	 * }
	 *
	 *
	 *
	 *
	 */
	FractalTerrain heightMap = new FractalTerrain(10, 1);
	int xMax = 10;
	int yMax = 10;
	List<Float> vektoren = new ArrayList<Float>();
	List<Float> colors = new ArrayList<Float>();
	List<Integer> indicies = new ArrayList<Integer>();
	for (int x = 0; x < xMax; x++) {
	   for (int y = 0; y < yMax; y++) {
		vektoren.add(-0.5f + x / ((float) xMax));
		vektoren.add(-0.5f + y / ((float) yMax));
		vektoren.add(0f);

		float colorcode = (float) heightMap.getAltitude(x / 16384., y / 16384.);

		colors.add(colorcode);
		colors.add(colorcode);
		colors.add(colorcode);
		if (x + (y - 1) * xMax > Short.MAX_VALUE || x + (y + 1) * xMax > Short.MAX_VALUE) {
		   System.out.println("fehler");
		}
		if (x > 0) {
		   if (y > 0) {

			indicies.add(x - 1 + y * xMax);
			indicies.add(x + y * xMax);
			indicies.add(x + (y - 1) * xMax);
		   }
		}
		if (x < xMax - 1) {
		   if (y < yMax - 1) {
			indicies.add(x + 1 + y * xMax);
			indicies.add(x + (y + 1) * xMax);
			indicies.add(x + y * xMax);
		   }
		}
	   }
	}
	heightMap = null;

	float[] vertices = new float[vektoren.size()];
	for (int i = 0; i < vertices.length; i++) {
	   vertices[i] = vektoren.get(i);
	}
	short[] inidicies = new short[indicies.size()];
	for (int i = 0; i < inidicies.length; i++) {
	   inidicies[i] = indicies.get(i).shortValue();
	}

	test = new Mesh(true, true, vektoren.size(), indicies.size(), new VertexAttributes(new VertexAttribute(Usage.Position, 3, "")));

	vektoren.clear();
	indicies.clear();
	test.setVertices(vertices);
	test.setIndices(inidicies);

	ModelBuilder modelBuilder = new ModelBuilder();

	model = modelBuilder.createBox(5f, 5f, 5f,
		  new Material(ColorAttribute.createDiffuse(Color.GREEN)),
		  Usage.Position | Usage.Normal);

	instance = new ModelInstance(model);

	camController = new CameraInputController(cam);
	Gdx.input.setInputProcessor(camController);

	shader =createMeshShader();
   }

   @Override
   public void render() {
	camController.update();

	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

	modelBatch.begin(cam);
	test.render(shader, 0);
//modelBatch.render(instance, environment);
	modelBatch.end();
   }

   @Override
   public void dispose() {
	modelBatch.dispose();
	model.dispose();
   }

   @Override
   public void resize(int width, int height) {
   }

   @Override
   public void pause() {
   }

   @Override
   public void resume() {
   }

   protected static ShaderProgram createMeshShader() {
	ShaderProgram.pedantic = false;
	ShaderProgram shader = new ShaderProgram(VERT_SHADER, FRAG_SHADER);
	String log = shader.getLog();
	if (!shader.isCompiled()) {
	   throw new GdxRuntimeException(log);
	}
	if (log != null && log.length() != 0) {
	   System.out.println("Shader Log: " + log);
	}
	return shader;
   }

}
