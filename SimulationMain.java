import processing.core.PApplet;
import processing.core.PImage;

public class SimulationMain extends PApplet implements ApplicationConstants
{
	private static final long serialVersionUID = 1L;
	
	private float[][] heightMap;
	
	//hMapRows and hMapCols specifies the number of rows and columns "point" wise  
	private int hMapRows;
	private int hMapCols;
	private PImage _backgroundImage, earthImage;
	
	private Ball ball;
	
	public void settings() {

		//Initial Scene configuration
		size(WINDOW_WIDTH,WINDOW_HEIGHT,P3D);
		
	}
	
	public void setup() {
		
		textureMode(NORMAL);

		_backgroundImage=loadImage("low_res_gravel.png");
		earthImage = loadImage("earthTexture.jpg");
		
		hMapRows = 15;
		hMapCols = 15;
		
		heightMap = new float[hMapCols][hMapRows];
		
		for (int i = 0; i < hMapRows; i++){
			for (int j = 0; j < hMapCols; j++){
				heightMap[j][i] = zFunction(j,i);
			}
		}
		
		//Initialize ball
		ball = new Ball(this,earthImage,250,250,100,50);
	}
	
	/** A function of x and y that gives us the z value for our heightMap*/
	private float zFunction(int x, int y){
		return (float)(x*y);
	}
	
	public void draw() {

		//Background world setup - etc
		background(100);
		drawHeightMap();
		ball.draw();
	}
	
	private void drawHeightMap(){
//		pushMatrix();
//		
//		// translate to the origin
//		translate(ORIGIN_X, ORIGIN_Y);
//		
//		// change to world coordinates with y pointing up
//		scale(WORLD_TO_PIXEL_SCALE, -WORLD_TO_PIXEL_SCALE);
		
		texture(_backgroundImage);
		
		int num_rows = hMapRows - 1;
		int num_col = hMapCols - 1;
		
		for (int i=0;i<num_rows;i++){

			beginShape(QUAD_STRIP);
			texture(_backgroundImage);
			for (int j=0;j<num_col+1;j++){

				vertex(j*width/num_col,
						i*height/num_rows,
						heightMap[j][i],
						(float)j/(float)num_col,
						(float)i/(float)num_rows);
				
				
				vertex(j*width/num_col,
						(i+1)*height/num_rows,
						heightMap[j][i+1],
						(float)(j)/(float)num_col,
						(float)(i+1)/(float)num_rows);

			}
			endShape(CLOSE);   

		}
	}
	
	/**
	 * This function switches between camera angles
	 * based on the key that was pressed.
	 * 1: Corresponds to the default camera
	 * 2: Corresponds to a rotated camera
	 * 3: Corresponds to a further out rotated camera
	 */
	public void keyReleased(){
		
		if (key=='1'){
			camera(width/2.0f, height/2.0f, (height/2.0f) / tan(PI*30.0f / 180.0f), width/2.0f, height/2.0f, 0, 0, 1, 0); 
			
		} else if (key=='2'){
			camera(width/2.0f, height/1.0f, (height/1.5f) / tan(PI*30.0f / 180.0f), width/2.0f, height/2.0f, 0, 0, 1, 0);
		
		} else if (key=='3'){
			camera(width/2.0f, height*2.5f, (height/3.0f) / tan(PI*30.0f / 180.0f), width/2.0f, height/2.0f, 0, 0, 1, 0); 
			
		}	
	}
		
	public static void main(String _args[]) {
		PApplet.main(new String[] { SimulationMain.class.getName() });
	}
}
