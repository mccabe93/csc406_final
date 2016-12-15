import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

public class SimulationMain extends PApplet implements ApplicationConstants
{
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
	
	private float[][] surface;
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
		
		maxZ = 0;
		
		surface = new float[WORLD_WIDTH+1][WORLD_HEIGHT+1];
		surface = createSurface();
		
		//Initialize ball
		int ball_x = 0,
				ball_y = 0;
		float ball_z = surface[ball_x+WORLD_WIDTH/2][ball_y+WORLD_HEIGHT/2];
		ball = new Ball(this,earthImage,ball_x,ball_y,
				ball_z,5);
	}
	
	/** A function of x and y that gives us the z value for our heightMap*/
	private float zFunction(int x, int y){
		float xf = ((sq(x)*x) - (3*x));
		float yf = ((sq(y)*y) - (3*y));

		return (float)(xf+yf);	
	}
	
	private float gradient(){
		float[] coords = ball.getCoords();
		// normal vector = 1st deriv of zFunction
		
		// partial deriv of our zFunction respect to x
		// plug in x location of ball
		
		// partial deriv of our zFunction respect to y
		// plug in y location of ball
		
		// just set z to z value stored in heightmap
		//System.out.println(coords[0] + ", " + coords[1] + ", " + coords[2]);
		int x = (int) (coords[0]+WORLD_WIDTH/2), y = (int) (coords[1]+WORLD_HEIGHT/2);
		ball.update();
		if(x <= WORLD_WIDTH &&
				y  <= WORLD_HEIGHT && 
				x >= 0 && y >= 0) {
			ball.setZ(surface[x][y]);
		}
		
		return 0;
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
		pushMatrix();
		//------------------//
		
		//---------SURFACE------------//
		pushMatrix();
		strokeWeight(.1f);
		drawSurface();
		popMatrix();
		//-----------------------//
		
		//--------WORLD BOX & CENTER REFERENCES--------//
		drawBoxRef();
		drawRef();	
		//--------------------------------------//
		
		popMatrix();
		gradient();
//		ball.update();
		ball.draw();
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
	
	private float[][] createSurface(){
		float z = 0;
		//rows
		for (int i=-(WORLD_HEIGHT/2);i<(WORLD_HEIGHT/2);i++){
			//cols
			for (int j=-(WORLD_WIDTH/2);j<(WORLD_WIDTH/2);j++){
				z = zFunction(j,i);
				if(abs(z) > WORLD_Z_MAX/2){
					maxZ = abs(z);
				}else if(z < 0){
					maxZ = abs(z);
				}
				surface[j+WORLD_HEIGHT/2][i+WORLD_WIDTH/2] = zFunction(j,i);
			}
		}
		//scales all values down..without it, the surface would be huge for exponential functions
		for (int i=-(WORLD_HEIGHT/2);i<(WORLD_HEIGHT/2);i++){			
			for (int j=-(WORLD_WIDTH/2);j<(WORLD_WIDTH/2);j++){
				surface[j+WORLD_HEIGHT/2][i+WORLD_WIDTH/2] *= WORLD_HEIGHT/maxZ;
			}
		}
		return surface;
	}
	
	private void drawSurface(){
		texture(backgroundImage);
		
		for (int i=-(WORLD_HEIGHT/2);i<(WORLD_HEIGHT/2);i++){

			beginShape(QUAD_STRIP);
			texture(backgroundImage);
			
			for (int j=-(WORLD_WIDTH/2);j<(WORLD_WIDTH/2)+1;j++){

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
