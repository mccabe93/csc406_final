import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;


public class Ball {
	private float radius;
	private float x,y,z;
	private float vx, vy, acceleration;
	private float angle_planar, // angle along x,y axes (direction the ball is moving along the surface)
					angle_rotational; // angle the ball is moving relative to z axis (rotation of the ball)
	private PShape ball;
	/**
	 * Reference to the parent papplet for drawing
	 */
	PApplet refApplet;
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
	
	void rotate(float amt) {
		angle_planar += amt;
	}
	
	void accelerate(float amt) {
		acceleration += amt;
	}
	
	void setZ(float z_) {
		z = z_;
	}
	
	float[] getCoords() {
		return new float[]{x,y,z};
	}
	
	void draw(){
		refApplet.pushMatrix();

//		refApplet.rotateZ(angle_planar);
//		refApplet.rotateY(angle_rotational);
		refApplet.translate(x,y,z);
		refApplet.noStroke();
//		refApplet.sphere(radius);
		refApplet.shape(ball);
		refApplet.line(0, 0, 0, 
				(float)(2000*Math.cos(angle_planar)), (float)(200*Math.sin(angle_planar)), 0);
		refApplet.popMatrix();
	}
}
