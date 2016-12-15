
/** For our application constants we will initialize window size and various constants relating
 * to our world and how we handle our perspective. Our world will be defined as a (toggleably) visible
 * cube. We will say that this cube is 2 meters cubed. We will translate to the center of this cube
 * when drawing in our world.
 * 
 * Note that there are 3 different scaling factors (x,y,z)
 * the max z is average of max x and y
 * 1 meter = 
 * @author Cameron
 *
 */
public interface ApplicationConstants {

	int WINDOW_WIDTH = 1280, WINDOW_HEIGHT = 720,
			WINDOW_DEPTH = (WINDOW_WIDTH + WINDOW_HEIGHT)/4;
		
	//x-> cols ..... y-> rows ....z -> hard-coded
	int DELTA_X = 20, DELTA_Y = 20, Z_RATIO = (DELTA_X+DELTA_Y);
	
	float WORLD_X_MIN = 0;
	float WORLD_X_MAX = 40; 
	float WORLD_Y_MIN = 0;
	float WORLD_Y_MAX = 40;
	float WORLD_Z_MIN = 0;
	float WORLD_Z_MAX = 40;
	
	//All same, our world is cubed
	float WORLD_WIDTH = WORLD_X_MAX - WORLD_X_MIN;
	float WORLD_HEIGHT = WORLD_Y_MAX - WORLD_Y_MIN;
	float WORLD_DEPTH = WORLD_Z_MAX - WORLD_Z_MIN;
	
	float WORLD_CENTER_X = (WORLD_X_MIN + WORLD_X_MAX)/2;
	float WORLD_CENTER_Y = (WORLD_Y_MIN + WORLD_Y_MAX)/2;
	float WORLD_CENTER_Z = (WORLD_Z_MIN + WORLD_Z_MAX)/2;
	
	float WORLD_TO_PIXEL_X = WINDOW_HEIGHT/WORLD_WIDTH;
	float WORLD_TO_PIXEL_Y = WINDOW_WIDTH/WORLD_HEIGHT;
	float WORLD_TO_PIXEL_Z = WINDOW_DEPTH/WORLD_DEPTH;
	
}