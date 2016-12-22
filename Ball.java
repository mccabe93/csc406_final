import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PMatrix;
import processing.core.PShape;

/** Our Ball class is implemented by processing using triangle strips, which allows us to put
 * our earth texture on the ball. We are still in the process of implementing its location and
 * physics based movement, however some of the variables are already defined. */
public class Ball extends ApplicationMath implements ApplicationConstants
{
	/** Define instance variables */
	private float x,y,z;
	private float vx, vy, ax, ay;
	
	private float drag = 0.3f;
	
	/** resistance vector <x,y> **/
	private float resistanceX,
					resistanceY;

	/** gravity vector <x,y> **/
	private float gravityX, 
					gravityY;
	
	private double thetaX, thetaY, thetaZ;
	
	private double radius,diameter,circumference;
	
	private float zScale;
	
	private PShape ball;
	private PApplet refApplet;
	private PImage mySkin;
	
	Ball(PApplet inApplet_,PImage in_skin,float x_, float y_, float z_, float radius_,
			float initialVelocity_, float drag_, float zScale_){
		
		refApplet = inApplet_;
		mySkin=in_skin;	
		
		x = x_;
		y = y_;
		z = z_;
		
		radius = radius_;
		diameter = radius*2;
		circumference = 2*Math.PI*radius;
		
		drag = drag_;
		
		vx = initialVelocity_*unitPartialX(x,y);
		vy = initialVelocity_*unitPartialX(x,y);
		
		ax = -unitAccelerationX(x,y) * unitPartialX(x,y);// * dt; // * pvx;
		ay = -unitAccelerationY(x,y) * unitPartialY(x,y);
		
		zScale = zScale_;
		
		ball = refApplet.createShape(PConstants.SPHERE, new float[]{(float) radius});
		ball.setTexture(mySkin);
		
		thetaX = 0;
		thetaY = 0;
		thetaZ = 0;
	}
	
	/** Our update function is unfinished, but shows some of steps we have begun to take 
	 * to model the state equation of the ball.*/
	void update(float dt){
		float px = x, py = y, pz = z,pvx = vx, pvy = vy;

		//First we need to update our x,y position based on our velocity
		x += vx * dt;
		y += vy * dt;
		
		//Then we can map that to a z value on our surface
		z = (float) (zScale*zFunction(x,y) + radius);
		
		float dx = x-px, dy = y-py, dz = z-pz;
		
		//Now we can update our acceleration
		ax = -unitAccelerationX(x,y) * unitPartialX(x,y);// * dt; // * pvx;
		ay = -unitAccelerationY(x,y) * unitPartialY(x,y);// * dt; // * pvy;	
		
		// recalculate the resistance vector
		resistanceX = (2/3f) * unitPartialX(x,y)*GRAVITY;
		resistanceY = (2/3f) * unitPartialY(x,y)*GRAVITY;
		
		// recalculate the gravity vector
		gravityX = -unitPartialX(x,y) * GRAVITY;
		gravityY = -unitPartialY(x,y) * GRAVITY;
		
		//Next, let's update our velocity based on our acceleration
		vx = ((pvx + (ax + gravityX - resistanceX)*dt) * (1-drag*dt));
		vy = ((pvy + (ay + gravityY - resistanceY)*dt) * (1-drag*dt));
		
		//if the direction of our last velocity is same as our new velocity
		if(Math.signum(pvy) == Math.signum(vy)){
			//then we can continue rotation in this direction
			thetaY -= Math.signum(pvy)*magnitude(dy,dz)/radius;
		}else{
			thetaY += Math.signum(vy)*magnitude(dy,dz)/radius; 
		}
		
		if(Math.signum(pvx) == Math.signum(vx)){
			//then we can continue rotation in this direction
			thetaX -= Math.signum(pvx)*magnitude(dx,dz)/radius;
		}else{
			thetaX += Math.signum(vx)*magnitude(dx,dz)/radius; 
		}	
		
	}

	/** Getter for ball radius */
	public double getRadius() {
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
		
		refApplet.rotateX((float) thetaY);
		refApplet.rotateY((float) thetaX);
		
		refApplet.shape(ball);
		
		refApplet.popMatrix();
	}
	
	/** Draws the x,y,z axises */
	private void drawRef(){
//		draws reference axises
		//red is x axis
		refApplet.stroke(255, 0, 0);
		refApplet.line(-2, 0, 6, 0);
		//green is y axis
		refApplet.stroke(0, 255, 0);
		refApplet.line(0, -2, 0, 6);
		//blue is z axis
		refApplet.stroke(0,0,255);
		refApplet.line(0,0,-2,0,0,6);
	}
}
