
public class ApplicationMath {
	/**Helper function for calculating float x to the power 2 */
	static float sq(float x) {
		return x*x;
	}
	
	/**Helper function for calculating square root of float x */
	static float sqrt(float x) {
		return (float) Math.sqrt(x);
	}
		
	/** Normalize helper function. This function takes two constants, presumably
	 * constants in a vector, such that the output is the square root of the constants
	 */
	static float magnitude(float c0, float c1){
		return sqrt(sq(c0) + sq(c1));
	}
	
	/** A function of x and y that gives us the z value for our heightMap*/
	static float zFunction(float x, float y){
		return (float)(sq(x)+sq(y));	
	}
	
	/** Calculates dx, the partial derivative of x for the zFunction at a point
	 * (x0,y0) */
	static float partialX(float x0, float y0){
		float fx = 2*x0;
		return -fx;
	}
	/** Calculates dy, the partial derivative of y for the zFunction at a point
	 * (x0,y0) */
	static float partialY(float x0, float y0){
		float fy = 2*y0;
		return -fy;
	}
	
	/**Finds the second partial derivative Fxx */
	static float secondPartialX(float x0, float y0){
		float fxx = 2;
		return -fxx;
	}
	
	/**Finds the second partial derivative Fyy */
	static float secondPartialY(float x0, float y0){
		float fyy = 2;
		return -fyy;
	}
	
	/**Finds and calculates the x component of the unit velocity vector at a point (x0,y0)
	 * in the steepest rate of change.
	 */
	static float unitPartialX(float x0, float y0){
		return partialX(x0,y0)/magnitude(partialX(x0,y0),partialY(x0,y0));
	}
	
	/**Finds and calculates the x component of the unit velocity vector at a point (x0,y0)
	 * in the steepest rate of change.
	 */
	static float unitPartialY(float x0, float y0){
		return partialY(x0,y0)/magnitude(partialX(x0,y0),partialY(x0,y0));
	}
	
	/** Functions in same way as unitVelocityX */
	static float unitAccelerationX(float x0, float y0){
		return secondPartialX(x0,y0)/magnitude(secondPartialX(x0,y0),secondPartialY(x0,y0));
	}
	
	/** Functions in same way as unitVelocityY */
	static float unitAccelerationY(float x0, float y0){
		return secondPartialY(x0,y0)/magnitude(secondPartialX(x0,y0),secondPartialY(x0,y0));
	}
	
}
