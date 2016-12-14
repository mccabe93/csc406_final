
/** Taken from Professor Herve's CSC 406 Course. However, I did add two new constants,
 * WORLD_CENTER_X and WORLD_CENTER_Y which are used for placing the spaceship in the
 * middle of the window. */
public interface ApplicationConstants {

	int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 500;
	
	float WORLD_UNIT = 3.33f;
	
	float WORLD_WIDTH = WINDOW_WIDTH/WORLD_UNIT;
	float WORLD_HEIGHT = WINDOW_WIDTH * WORLD_UNIT;
	
	//	Here, I hope that whoever picked the world and window dimensions 
	//	managed to select values that give the same scaling horizontally 
	//	and vertically.  I could apply separate scalings, but that has a 
	//	bad effect on rotation (results in skewing). 
	float WORLD_TO_PIXEL_SCALE = WINDOW_WIDTH / WORLD_WIDTH;
	float PIXEL_WORLD_SCALE = 1 / WORLD_TO_PIXEL_SCALE;

}