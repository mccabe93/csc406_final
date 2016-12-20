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
	
	private float drag = 0.1f;
	
	/** resistance vector <x,y> **/
	private float resistanceX,
					resistanceY;

	/** gravity vector <x,y> **/
	private float gravityX, 
					gravityY;
	
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
		
		vx = 30f*unitPartialX(x,y);
		vy = 30f*unitPartialX(x,y);
		
		ax = -unitAccelerationX(x,y) * unitPartialX(x,y);// * dt; // * pvx;
		ay = -unitAccelerationY(x,y) * unitPartialY(x,y);
		
		zScale = zScale_;
		
		ball = refApplet.createShape(PConstants.SPHERE, new float[]{radius});
		ball.setTexture(mySkin);
	}
	
	/** Our update function is unfinished, but shows some of steps we have begun to take 
	 * to model the state equation of the ball.*/
	void update(float dt){
		
		//First we need to update our x,y position based on our velocity
		x += vx * dt;// + unitPartialX(x,y) * radius;
		y += vy * dt;// + unitPartialY(x,y) * radius;
		
		//Then we can map that to a z value on our surface
		z = zScale*zFunction(x,y) + radius;

		physics(dt);
		
//		System.out.println(magnitude(vx,vy));
		
		//System.out.println("x,y: " + x + ", " + y + "\nvx,vy: " + vx + ", " + vy + "\nax,ay: " + ax +", " + ay);
	}
	
	void physics(float dt) {
		//Store old vx and vy for calculations
		float pvx = vx, pvy = vy;
		
		//Now we can update our acceleration
		ax = -unitAccelerationX(x,y) * unitPartialX(x,y);// * dt; // * pvx;
		ay = -unitAccelerationY(x,y) * unitPartialY(x,y);// * dt; // * pvy;	
		
		// recalculate the friction vector
		resistanceX = (2/3f) * unitPartialX(x,y)*GRAVITY;
		resistanceY = (2/3f) * unitPartialY(x,y)*GRAVITY;
		
		// recalculate the gravity vector
		gravityX = -unitPartialX(x,y) * GRAVITY;
		gravityY = -unitPartialY(x,y) * GRAVITY;
		
		//Next, let's update our velocity based on our acceleration
		vx = ((pvx + (ax + gravityX - resistanceX)*dt) * (1-drag*dt));
		vy = ((pvy + (ay + gravityY - resistanceY)*dt) * (1-drag*dt));
		
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
