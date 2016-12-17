import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;

/** Our Ball class is implemented by processing using triangle strips, which allows us to put
 * our earth texture on the ball. We are still in the process of implementing its location and
 * physics based movement, however some of the variables are already defined. */
public class Ball {
	/** Define instance variables */
	private float radius;
	private float x,y,z;
	private float vx, vy, acceleration;
	private float angle_planar, // angle along x,y axes (direction the ball is moving along the surface)
					angle_rotational; // angle the ball is moving relative to z axis (rotation of the ball)
	//Our ball is a PShape object
	private PShape ball;
	
	/**
	 * Reference to the parent papplet for drawing
	 */
	PApplet refApplet;
	//PImage texture variable
	PImage mySkin;
	
	Ball(PApplet inApplet,PImage in_skin,float x_, float y_, float z_, float radius_){
		x = x_;
		y = y_;
		z = z_;
		radius = radius_;
		
		mySkin=in_skin;
		
		refApplet = inApplet;
		acceleration = 0f;
		ball = refApplet.createShape(PConstants.SPHERE, new float[]{radius});
		ball.setTexture(mySkin);
	}
	
	/** Our update function is unfinished, but shows some of steps we have begun to take 
	 * to model the state equation of the ball.*/
	void update() {//float gradX, float gradY) {
		x += vx;
		y += vy; 
		vx = (float) (acceleration*Math.cos(angle_planar));
		vy = (float) (acceleration*Math.sin(angle_planar));
		if(acceleration > 0.03f)
			acceleration -= 0.05f;
		else
			acceleration = 0;
	}
	/** Helper method for rotating the ball*/
	void rotate(float amt) {
		angle_planar += amt;
	}
	/** Helper method for accelerating the ball*/
	void accelerate(float amt) {
		acceleration += amt;
	}
	/** Helper method that sets the z value of the ball. This will probably be helpful
	 * for the reset method in SimulationMain*/
	void setZ(float z_) {
		z = z_;
	}
	/** Getter method for getting the location of the ball */
	float[] getCoords() {
		return new float[]{x,y,z};
	}
	
	/** Our draw function gets called in world units and draws the ball with its texture */
	void draw(){
		refApplet.pushMatrix();

		refApplet.translate(x,y,z);
		//This noStroke has no effect..don't know why yet
		refApplet.noStroke();
		refApplet.shape(ball);
		//reference line for ball's intended movement
		refApplet.line(0, 0, 0, 
				(float)(2000*Math.cos(angle_planar)), (float)(200*Math.sin(angle_planar)), 0);
		refApplet.popMatrix();
	}
}
