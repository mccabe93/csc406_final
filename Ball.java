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
	private float x,y,z;
	private float vx, vy, ax, ay;
	
	private float mass, radius;
	
	private float zScale;
	
	private PShape ball;
	private PApplet refApplet;
	private PImage mySkin;
	
	Ball(PApplet inApplet_,PImage in_skin,float x_, float y_, float z_,
			float mass_, float radius_, float zScale_){
		
		refApplet = inApplet_;
		mySkin=in_skin;	
		
		x = x_;
		y = y_;
		z = z_;
		
		mass = mass_;
		radius = radius_;
		
		vx = unitVelocityX(x,y);
		vy = unitVelocityY(x,y);
		
		ax = unitAccelerationX(x,y);
		ay = unitAccelerationY(x,y);
		
		zScale = zScale_;
		
		ball = refApplet.createShape(PConstants.SPHERE, new float[]{radius});
		ball.setTexture(mySkin);
	}
	
	/** Our update function is unfinished, but shows some of steps we have begun to take 
	 * to model the state equation of the ball.*/
	void update(float dt){
		//Store old vx and vy for calculations
		float pvx = vx, pvy = vy;
		
		//First we need to update our x,y position based on our velocity
		x += vx * dt;
		y += vy * dt;
		
		//Then we can map that to a z value on our surface
		z = zScale*zFunction(x,y);
			
		//Next, let's update our velocity based on our acceleration
		vx += ax * dt;
		vy += ay * dt;
		
		//Now we can update our acceleration
		ax = unitVelocityX(x,y) * GRAVITY * pvx;
		ay = unitVelocityY(x,y) * GRAVITY * pvy;		
	}
	
	/** Getter for ball radius */
	public float getRadius() {
		return radius;
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
		//refApplet.translate(-unitVelocityX(x,y)*radius,-unitVelocityY(x,y)*radius,radius);
		refApplet.shape(ball);
		
		refApplet.popMatrix();
	}
}
