
/** Our world is bounded by the window width and height. The z values,depth, are scaled
 * to fit within the default perspective. The world is cubic and increasing the values of
 * the world xmin,xmax,ymin,etc. increases the detail of the world by increasing grid
 * density in SimulationMain.java.
 * 
 * For our physics relation, we will say that the world width, height, and depth all
 * represent 2 meters, such that our world is a 2 meters cubed area where each quadrant is a
 * 1 meter cubed area. Depending on the value for world width, height, and depth, the world will
 * be more or less detailed.
 * 
 * @author Cameron
 *
 */
public interface ApplicationConstants {

	int WINDOW_WIDTH = 600, WINDOW_HEIGHT = 600,
			WINDOW_DEPTH = (WINDOW_WIDTH + WINDOW_HEIGHT)/2;
	
	int WORLD_X_MIN = -20;
	int WORLD_X_MAX = 20; 
	int WORLD_Y_MIN = -20;
	int WORLD_Y_MAX = 20;
	int WORLD_Z_MIN = -20;
	int WORLD_Z_MAX = 20;
	
	//All same, our world is cubed
	int WORLD_WIDTH = (int) (WORLD_X_MAX - WORLD_X_MIN);
	int WORLD_HEIGHT = (int) (WORLD_Y_MAX - WORLD_Y_MIN);
	int WORLD_DEPTH = (int) (WORLD_Z_MAX - WORLD_Z_MIN);
	
	float WORLD_TO_PIXEL_X = WINDOW_HEIGHT/WORLD_WIDTH;
	float WORLD_TO_PIXEL_Y = WINDOW_WIDTH/WORLD_HEIGHT;
	float WORLD_TO_PIXEL_Z = WINDOW_DEPTH/WORLD_DEPTH;
	
	float GRAVITY = 9.8f;
}