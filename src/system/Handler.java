package system;

import objects.tetrominos.Tetromino_I;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
	LinkedList<GameObject> objects = new LinkedList<>();
	private GameObject current_tetromino;
	private int timer = 0;

	public Handler() {}

	public void tick() {
		if(current_tetromino != null) {
			if (timer >= 60) {
				timer = 0;
				System.out.println("move check");
				if (canMove(0,1)) {
					current_tetromino.setY(current_tetromino.getY() + Game.TILESIZE);
				} else {
					System.out.println("plant tetromino");
					plantTetromino();
				}
			}
			timer++;
		}
		for(int i=0; i<objects.size(); i++) {
			objects.get(i).tick();
		}
		if(current_tetromino != null) current_tetromino.tick();
	}

	public void render(Graphics g) {
		for(int i=0; i<objects.size(); i++) {
			objects.get(i).render(g);
		}
		if(current_tetromino != null) current_tetromino.render(g);
	}

	public void addObject(GameObject object) {
		this.objects.add(object);
	}

	public void removeObject(GameObject object) {
		this.objects.remove(object);
	}

	public void setCurrent_tetromino(GameObject tetromino) {
		this.current_tetromino = tetromino;
	}

	public GameObject getCurrent_tetromino() {
		return this.current_tetromino;
	}

	private boolean canMove(int x_offset, int y_offset) {
		for(GameObject cube : ((Tetromino)current_tetromino).getCubes()) {
			for(GameObject object : objects) {
				if(object.getId() == ID.wall || object.getId() == ID.tetromino_cube) {
					if(object.getY() == cube.getY() + (y_offset * Game.TILESIZE) && object.getX() == cube.getX() + (x_offset * Game.TILESIZE)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public void moveTetromino(int x_offset, int y_offset) {
		if(canMove(x_offset, y_offset)) {
			current_tetromino.setY(current_tetromino.getY() + y_offset*Game.TILESIZE);
			current_tetromino.setX(current_tetromino.getX() + x_offset*Game.TILESIZE);
		}
	}

	private void plantTetromino() {
		objects.addAll(((Tetromino)current_tetromino).getCubes());
		current_tetromino = new Tetromino_I(64, 64);
	}
}
