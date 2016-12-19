import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;

/** Our Ball class is implemented by processing using triangle strips, which allows us to put
 * our earth texture on the ball. We are still in the process of implementing its location and
 * physics based movement, however some of the variables are already defined. */
public class Ball implements ApplicationConstants
{
	/** Define instance variables */
	private float radius;
	private float x,y,z;
	private float mass, inertia, 
					vtx, vty, 		// translational velocities
					atx, aty,		// translational accelerations
					potential, kinetic;
	private float angle_x, // angle along x,y axes (direction the ball is moving along the surface)
					angle_y; // angle the ball is moving relative to z axis (rotation of the ball)
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
		vtx = vty = atx = aty = 0.0f;
		mass = 50.0f;
		inertia = (2/5) * mass * (radius*radius);
		ball = refApplet.createShape(PConstants.SPHERE, new float[]{radius});
		ball.setTexture(mySkin);
	}
	
	public float getRadius() {
		return radius;
	}
	
	/** Our update function is unfinished, but shows some of steps we have begun to take 
	 * to model the state equation of the ball.*/
	void update(float dx, float dy, float dz, float dt) {//float gradX, float gradY) {
		x += atx;
		y += aty; 

		System.out.println("dx,dy,dz: " + dx + ", " + dy + ", " + dz);
		
		float thetaX = (float)Math.asin(dz/dx);
		System.out.println("thetaX: " + thetaX);
		float atx = (float)((2/3)*GRAVITY*Math.sin(thetaX));
		
		System.out.println(atx);
		
		float thetaY = (float)Math.asin(dz/dy);
		float aty = (float)((2/3)*GRAVITY*Math.sin(thetaY));

		System.out.println(aty);
		
		float nx = x + atx;
		System.out.println("nx: " + nx);
		Float new_angle_x = (float) Math.acos(nx/x);
		if(!new_angle_x.isNaN())
			angle_x = new_angle_x;
		x=nx;
		
		float ny = y+aty;
		System.out.println("ny: " + ny);
		Float new_angle_y = (float) Math.acos(ny/y);
		if(!new_angle_y.isNaN())
			angle_y = new_angle_y;
		y=ny;
	}
	/** Helper method for rotating the ball*/
	void rotate(float amt) {
//		angle_planar += amt;
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
	
	public void setCoords(float x_, float y_, float z_) {
		x = x_;
		y = y_;
		z = z_;
	}
	
	/** Our draw function gets called in world units and draws the ball with its texture */
	void draw(){
		refApplet.pushMatrix();

		refApplet.translate(x,y,z);
		refApplet.rotateX(angle_x);
		refApplet.rotateY(angle_y);
		//This noStroke has no effect..don't know why yet
		refApplet.noStroke();
		refApplet.shape(ball);
		//reference line for ball's intended movement
//		refApplet.line(0, 0, 0, 
//				(float)(2000*Math.cos(angle_planar)), (float)(200*Math.sin(angle_planar)), 0);
		refApplet.popMatrix();
	}
}
