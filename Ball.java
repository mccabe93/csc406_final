import processing.core.PApplet;
import processing.core.PImage;


public class Ball {
	private float radius;
	private float origin_x,origin_y,origin_z;
	
	/**
	 * Reference to the parent papplet for drawing
	 */
	PApplet refApplet;
	PImage mySkin;
	
	Ball(PApplet inApplet,PImage in_skin,float x_, float y_, float z_, float radius_){
		origin_x = x_;
		origin_y = y_;
		origin_z = z_;
		radius = radius_;
		
		mySkin=in_skin;
		
		refApplet = inApplet;
	}
	
	void draw(){
		refApplet.pushMatrix();
		
		refApplet.texture(mySkin);
		
		refApplet.translate(origin_x,origin_y,origin_z);
//		refApplet.noStroke();
		refApplet.sphere(radius);
		
		refApplet.popMatrix();
	}
}
