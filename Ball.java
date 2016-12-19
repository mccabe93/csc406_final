import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;

/** Our Ball class is implemented by processing using triangle strips, which allows us to put
 * our earth texture on the ball. We are still in the process of implementing its location and
 * physics based movement, however some of the variables are already defined. */
public class Ball extends ApplicationMath implements ApplicationConstants
{
	/** Define instance variables */
	private float radius;
	private float x,y,z;
	private float zScale;
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
	
	Ball(PApplet inApplet,PImage in_skin,float x_, float y_, float z_, float radius_, float zScale_){
		x = x_;
		y = y_;
		z = z_;
		radius = radius_;
		zScale = zScale_;
		
		mySkin=in_skin;
		
		refApplet = inApplet;
		vtx = 1.2f*-unitPartialX(x,y);
		vty = 1.2f*-unitPartialY(x,y);
		mass = 5.0f;
		inertia = (2/5f) * mass * (radius*radius);
		ball = refApplet.createShape(PConstants.SPHERE, new float[]{radius});
		ball.setTexture(mySkin);
	}
	
	public float getRadius() {
		return radius;
	}
	
	/** Our update function is unfinished, but shows some of steps we have begun to take 
	 * to model the state equation of the ball.*/
	void update(float dt) {//float gradX, float gradY) {

		//System.out.println("dx,dy,dz: " + dx + ", " + dy + ", " + dz);
		
		float px = x, py = y, pz = z;
		
		x += vtx*dt;
		y += vty*dt;
		
		vtx += atx*dt;
		vty += aty*dt;
		
		float dz = zScale* (zFunction(x,y) - zFunction(px,py));
		
		atx = (float) ((2/3f)*GRAVITY*-unitPartialX(px,py));
		aty = (float) ((2/3f)*GRAVITY*-unitPartialY(px,py));
		
		z += dz;

		// friction 
		vtx *= 0.98f;
		vty *= 0.98f;
		
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
