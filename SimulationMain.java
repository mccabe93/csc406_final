import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

/** Simulation Main is our main class in our program and it is where we initially extend 
 * PApplet and implement our ApplicationConstants. In SimulationMain we define a perspective,
 * as well as some controls for that perspective. We also initialize our surface and ball.
 * Simulation Main contains multiple helper methods for calculating partial derivatives and 
 * normal vectors. There also multiple helper draw methods for showing references of dimensions
 * and orientation.*/
public class SimulationMain extends PApplet implements ApplicationConstants
{
	/** Defining our variables */
	private static final long serialVersionUID = 1L;
	
	private PImage backgroundImage, earthImage;
		
	private float fov, cameraZ;
	private float perspectiveX,perspectiveY;
	
	//user perspective controls for rotation - i.e. the modifier to default rotation
	private float zRotMod,xRotMod;
	
	//and this is for controlling the user rotation.
	//this value basically calls the width/height of the window 2PI
	//such that a mouse drag from left to right would be two full rotations
	private float piScaler;
	
	private float maxZ;
	
	//Define our surface, partial derivatives for each point, and our ball
	private float[][] surface;
	private float[][] partialXs;
	private float[][] partialYs;
	
	private Ball ball;
	
	/** Standard settings method for processing file. Also, initialize piScaler, because
	 * it is based on the width and height of the window.*/
	public void settings() {
		width = WINDOW_WIDTH;
		height = WINDOW_HEIGHT;
		
		piScaler =((width+height)/2)/(2*PI);
		
		//Initial Scene configuration
		size(width,height,P3D);
		
	}
	/** Our setup method initializes textures, perspective tools, the surface,
	 * partial derivatives, and the ball. */
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
		
		maxZ = 0;
		
		surface = new float[WORLD_WIDTH+1][WORLD_HEIGHT+1];
		partialXs = new float[WORLD_WIDTH+1][WORLD_HEIGHT+1];
		partialYs = new float[WORLD_WIDTH+1][WORLD_HEIGHT+1];
		
		surface = createSurface();
		
		//Initialize ball..currently this is hardcoded to place ball at 20,20
		//which is currently the last point we calculate for our surface. It also attempts
		//to use the normal vector at that point to place the ball exactly on the surface.
		float ball_x = 20 - (5*partialXs[40][40]);
		float ball_y = 20 - (5*partialYs[40][40]);
		float ball_z = surface[40][40]+.6f;
		ball = new Ball(this,earthImage,ball_x,ball_y,
				ball_z,5);
	}
	
	/** A function of x and y that gives us the z value for our heightMap*/
	private float zFunction(int x, int y){
		float xf = ((sq(x)*x) - (3*x));
		float yf = ((sq(y)*y) - (3*y));

		return (float)(xf+yf);	
	}
	/** Calculates dx, the partial derivative of x for the zFunction */
	private float partialX(float x, float y){
		float dx = ((3*sq(x)) - 3);
		return dx;
	}
	/** Calculates dy, the partial derivative of y for the zFunction */
	private float partialY(float x, float y){
		float dy = ((3*sq(y)) - 3);
		return dy;
	}
	/** Calculates the partialX as a unit value by dividing by the squares of
	 * the partialX and partialY at a specific point */
	private float unitNormalX(float x, float y){
		return partialX(x,y)/sqrt(sq(partialX(x,y))+sq(partialY(x,y)));
	}
	/** Calculates the partialY as a unit value by dividing by the squares of
	 * the partialX and partialY at a specific point */
	private float unitNormalY(float x, float y){
		return partialY(x,y)/sqrt(sq(partialX(x,y))+sq(partialY(x,y)));
	}
	
	/** Possible helper method..not currently being used */
	private float gradient(){
		float[] coords = ball.getCoords();

		int x = (int) (coords[0]+WORLD_WIDTH/2), y = (int) (coords[1]+WORLD_HEIGHT/2);
		ball.update();
		if(x <= WORLD_WIDTH &&
				y  <= WORLD_HEIGHT && 
				x >= 0 && y >= 0) {
			ball.setZ(surface[x][y]);
		}
		
		return 0;
	}
	/** Creates the surface in pixel units and determines if the z values produced by the
	 * zFunction need to be scaled down to fit within our 'world' box*/
	private float[][] createSurface(){
		float z = 0;
		//rows
		for (int i=WORLD_Y_MIN;i<=WORLD_Y_MAX;i++){
			//cols
			for (int j=WORLD_X_MIN;j<=WORLD_X_MAX;j++){
				z = zFunction(j,i);
				if(abs(z) > WORLD_Z_MAX){
					maxZ = abs(z);
				}else if(z < 0){
					maxZ = abs(z);
				}
				surface[j+WORLD_HEIGHT/2][i+WORLD_WIDTH/2] = zFunction(j,i);
			}
		}
		//scales all values down..without it, the surface would be huge for exponential functions
		for (int i=WORLD_Y_MIN;i<=WORLD_Y_MAX;i++){			
			for (int j=WORLD_X_MIN;j<=WORLD_X_MAX;j++){
				surface[j+WORLD_HEIGHT/2][i+WORLD_WIDTH/2] *= WORLD_HEIGHT/maxZ;
				//also calculates partial derivatives and stores them
				//as unit values
				partialXs[j+WORLD_HEIGHT/2][i+WORLD_WIDTH/2] = unitNormalX(j,i);
				partialYs[j+WORLD_HEIGHT/2][i+WORLD_WIDTH/2] = unitNormalY(j,i);
			}
		}
		
		return surface;
	}
	
	/** Our draw function first moves to the perspective that we are in and then moves
	 * to world units to draw the surface, ball, and any helper drawings such as the normal
	 * vectors, the world box, and any reference drawings. */
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
		pushMatrix();
		//------------------//
		
		//---------SURFACE------------//
		pushMatrix();
		strokeWeight(.1f);
		drawSurface();
		drawNormVectors();
		popMatrix();
		//-----------------------//
		
		//--------WORLD BOX & CENTER REFERENCES--------//
		drawBoxRef();
		drawRef();
		//--------------------------------------//
		
		popMatrix();
		//gradient();
//		ball.update();
		noStroke();
		ball.draw();
		popMatrix();
	}
	/** Moves into world units by scaling by all the world to pixel values defined in
	 * ApplicationConstants*/
	private void moveToWorldUnits(){
		//scale to world units
		scale(WORLD_TO_PIXEL_X, WORLD_TO_PIXEL_Y,WORLD_TO_PIXEL_Z);
	}
	
	/** Draws the box of the world that we want our surface to fit within*/
	private void drawBoxRef(){
		//fill(0);
		box(WORLD_HEIGHT,WORLD_WIDTH,WORLD_DEPTH);
		//noFill();
	}
	
	/** Draws the x,y,z axises */
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
	
	/** Draws the unit normal vector at all points in the surface */
	private void drawNormVectors(){
		stroke(0);
		//rows
		for (int i=WORLD_Y_MIN;i<=WORLD_Y_MAX;i++){
			//cols
			for (int j=WORLD_X_MIN;j<=WORLD_X_MAX;j++){
				line(j,i,surface[j+WORLD_HEIGHT/2][i+WORLD_WIDTH/2],
						j-partialXs[j+WORLD_HEIGHT/2][i+WORLD_WIDTH/2],
						i-partialYs[j+WORLD_HEIGHT/2][i+WORLD_WIDTH/2],
						surface[j+WORLD_HEIGHT/2][i+WORLD_WIDTH/2]+1);
			}
		}
	}
	/** Draws the surface as a quad strip, referencing the 2d array surface for the z values */
	private void drawSurface(){
		texture(backgroundImage);
		
		for (int i=-(WORLD_HEIGHT/2);i<=(WORLD_HEIGHT/2)-1;i++){

			beginShape(QUAD_STRIP);
			texture(backgroundImage);
			
			for (int j=-(WORLD_WIDTH/2);j<=(WORLD_WIDTH/2);j++){

				vertex(j,i,surface[j+WORLD_HEIGHT/2][i+WORLD_WIDTH/2],
						(float)j/(float)WORLD_WIDTH,
						(float)i/(float)WORLD_HEIGHT);
							
				vertex(j,i+1,surface[j+WORLD_HEIGHT/2][i+1+WORLD_WIDTH/2],
						(float)(j)/(float)WORLD_WIDTH,
						(float)(i+1)/(float)WORLD_HEIGHT);

			}
			endShape(CLOSE);   

		}
	}
	
	/** Various tools for the perspective and some beginner controls for ball movement */
	public void keyPressed() {
		switch(key){
		//reset perspective
		case 'r':
			perspectiveX = width/2;
			perspectiveY = height/2;
			
			zRotMod = 0;
			xRotMod = 0;
			break;
		case ' ':
			ball.accelerate(0.7f);
			break;
		case '[':
			ball.rotate(0.2f);
			break;
		case ']':
			ball.rotate(-0.2f);
			break;
		}
	}
	
	/** The mouseWheel event is used for zooming in and out (i.e. changing the fov of the
	 * perspective)
	 */
	public void mouseWheel(MouseEvent event) {
		  float e = event.getCount();
		  if (e>0){
			  fov *= 1.1;
		  }else if(e<0){
			  fov *= .9;
		  }
	}
	
	/** The mouseDragged event is used for moving the location of the perspective and for
	 * rotating the perspective 
	 */
	public void mouseDragged(MouseEvent event){
		//translation
		if (mouseButton == LEFT){
			perspectiveX += mouseX-pmouseX;
			perspectiveY += mouseY-pmouseY;
		}else{ //rotation
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
