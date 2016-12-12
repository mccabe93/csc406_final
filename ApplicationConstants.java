
/** Taken from Professor Herve's CSC 406 Course. However, I did add two new constants,
 * WORLD_CENTER_X and WORLD_CENTER_Y which are used for placing the spaceship in the
 * middle of the window. */
public interface ApplicationConstants {

	int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 500;
	
	//	world that gets mapped into the window
	float WORLD_X_MIN = -10;
	float WORLD_X_MAX = 70; 
	float WORLD_Y_MIN = 0;
	float WORLD_Y_MAX = 60;
	
	float WORLD_CENTER_X = (WORLD_X_MIN + WORLD_X_MAX)/2;
	float WORLD_CENTER_Y = (WORLD_Y_MIN + WORLD_Y_MAX)/2;
	
	float WORLD_WIDTH = WORLD_X_MAX - WORLD_X_MIN;
	float WORLD_HEIGHT = WORLD_Y_MAX - WORLD_Y_MIN;
	
	//	Here, I hope that whoever picked the world and window dimensions 
	//	managed to select values that give the same scaling horizontally 
	//	and vertically.  I could apply separate scalings, but that has a 
	//	bad effect on rotation (results in skewing). 
	float WORLD_TO_PIXEL_SCALE = WINDOW_WIDTH / WORLD_WIDTH;
	float PIXEL_WORLD_SCALE = 1 / WORLD_TO_PIXEL_SCALE;
	
	//	This gives the location of the world's origin in the window,
	//	in pixel coordinates.  Note that this point could well be 
	//	outside of the window.
	float ORIGIN_X = -WORLD_TO_PIXEL_SCALE*WORLD_X_MIN;
	float ORIGIN_Y = WORLD_TO_PIXEL_SCALE*WORLD_Y_MAX;
}