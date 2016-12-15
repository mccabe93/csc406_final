import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

public class SimulationMain extends PApplet implements ApplicationConstants
{
	private static final long serialVersionUID = 1L;
	
	private PImage backgroundImage, earthImage;
	
	//define our height map as 2d array
	private float[][] heightMap;
	//hMapRows and hMapCols specifies the number of rows and columns
	private int hMapCols = DELTA_X;
	private int hMapRows = DELTA_Y;
		
	private float fov, cameraZ;
	private float perspectiveX,perspectiveY;
	
	//user perspective controls for rotation - i.e. the modifier to default rotation
	private float zRotMod,xRotMod;
	//and this is for controlling the user rotation.
	//this value basically calls the width/height of the window 2PI
	//such that a mouse drag from left to right would be two full rotations
	private float piScaler;
	
	private Ball ball;
	
	public void settings() {
		width = WINDOW_WIDTH;
		height = WINDOW_HEIGHT;
		
		piScaler =((width+height)/2)/(2*PI);
		
		//Initial Scene configuration
		size(width,height,P3D);
		
	}
	
	public void setup() {
		
		textureMode(NORMAL);
		
		backgroundImage=loadImage("test.png");
		earthImage = loadImage("earthTexture.jpg");
		
		fov = PI/2.0f;
		cameraZ = (height/2.0f)/tan((fov)/2.0f);
		
		zRotMod = 0;
		xRotMod = 0;
		
		//where we place our perspective -- center of world
		perspectiveX = width/2;
		perspectiveY = height/2;
		
		heightMap = new float[hMapCols][hMapRows];
		
		for (int i = 0; i < hMapRows; i++){
			for (int j = 0; j < hMapCols; j++){
				heightMap[j][i] = zFunction(j,i);
			}
		}
		
		//Initialize ball
		//ball = new Ball(this,earthImage,250,250,100,50);
	}
	
	/** A function of x and y that gives us the z value for our heightMap*/
	private float zFunction(int x, int y){
		float xf = ((sq(x)*x) - (3*x));
		float yf = ((sq(y)*y) - (3*y));
		return (float)(xf+yf);
	}
	
	public void draw() {
		//first we clear background
		background(100);
		//and we don't want to fill
		noFill();
		//reset stroke color and weight
		stroke(0);
		strokeWeight(1);
		
		//--------PERSPECTIVE--------//
		perspective(fov, ((float)width)/((float)height), 
		            cameraZ/10.0f, cameraZ*10.0f);
		
		//center and rotate to give perspective where Z points up, x and y slightly rotated
		//creating depths on diagonals
		translate(perspectiveX,perspectiveY);
		
		rotateX(PI/1.5f + xRotMod);
		rotateZ(-PI/1.5f + zRotMod);
		rotateY(-PI/4);	
		//---------------------------//
		
		//--------WORLD--------//
		pushMatrix();
		moveToWorldUnits();
		//------------------//
		
		//---------SURFACE------------//
		pushMatrix();
		strokeWeight(.1f);
		drawHeightMap();
		popMatrix();
		//-----------------------//
		
		//--------WORLD BOX & CENTER REFERENCES--------//
		drawBoxRef();
		drawRef();	
		//--------------------------------------//
		
		popMatrix();
	}
	
	private void moveToWorldUnits(){
		//scale to world units
		scale(WORLD_TO_PIXEL_X, WORLD_TO_PIXEL_Y,WORLD_TO_PIXEL_Z);
	}
	
	private void drawBoxRef(){
		//fill(0);
		box(WORLD_HEIGHT,WORLD_WIDTH,WORLD_DEPTH);
		//noFill();
	}
	
	private void drawRef(){
//		draws reference axises
		//red is x axis
		stroke(255, 0, 0);
		line(-2, 0, 6, 0);
		//green is y axis
		stroke(0, 255, 0);
		line(0, -2, 0, 6);
		//blue is z axis
		stroke(0,0,255);
		line(0,0,-2,0,0,6);
	}
	
	private void drawHeightMap(){
		texture(backgroundImage);
		
		int num_rows = hMapRows;
		int num_col = hMapCols;
		
		for (int i=-num_col;i<num_rows;i++){

			beginShape(QUAD_STRIP);
			texture(backgroundImage);
			for (int j=-num_col;j<num_col+1;j++){

				vertex(j,
						i,
						zFunction(j,i)/Z_RATIO,
						(float)j/(float)2*num_col,
						(float)i/(float)2*num_rows);
				
				
				vertex(j,
						(i+1),
						zFunction(j,i+1)/Z_RATIO,
						(float)(j)/(float)2*num_col,
						(float)(i+1)/(float)2*num_rows);

			}
			endShape(CLOSE);   

		}
	}
	
	
	public void keyPressed() {
		switch(key){
		//reset perspective
		case 'r':
			perspectiveX = width/2;
			perspectiveY = height/2;
			
			zRotMod = 0;
			xRotMod = 0;
			break;
		}
	}
	
	//mouse wheel zooming!
	public void mouseWheel(MouseEvent event) {
		  float e = event.getCount();
		  if (e>0){
			  fov *= 1.1;
		  }else if(e<0){
			  fov *= .9;
		  }
	}
	
	public void mouseDragged(MouseEvent event){
		if (mouseButton == LEFT){
			perspectiveX += mouseX-pmouseX;
			perspectiveY += mouseY-pmouseY;
		}else{
			//left and right mouse position will rotate z axis
			//up and down mouse movement will rotate x axis
			zRotMod += (mouseX-pmouseX)/piScaler;
			xRotMod += (mouseY-pmouseY)/piScaler;
		}
	}
		
	public static void main(String _args[]) {
		PApplet.main(new String[] { SimulationMain.class.getName() });
	}
}
