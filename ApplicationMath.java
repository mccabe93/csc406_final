
public class ApplicationMath {
	
	static int equation = 1;
	final static int EQUATIONS = 3;
	
	static void changeEquation(int mod) {
		equation = mod;
	}
	
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
		switch(equation) {
		case 1:
			return zFunction1(x,y);
		case 2:
			return zFunction2(x,y);
		case 3:
			return zFunction3(x,y);
		}
		return zFunction1(x,y);	
	}
	
	/** Calculates dx, the partial derivative of x for the zFunction at a point
	 * (x0,y0) */
	static float partialX(float x0, float y0){
		switch(equation) {
		case 1:
			return partialX1(x0,y0);
		case 2:
			return partialX2(x0,y0);
		case 3:
			return partialX3(x0,y0);
		}
		return partialX1(x0,y0);	
	}
	/** Calculates dy, the partial derivative of y for the zFunction at a point
	 * (x0,y0) */
	static float partialY(float x0, float y0){
		switch(equation) {
		case 1:
			return partialY1(x0,y0);
		case 2:
			return partialY2(x0,y0);
		case 3:
			return partialY3(x0,y0);
		}
		return partialY1(x0,y0);	
	}
	
	/**Finds the second partial derivative Fxx */
	static float secondPartialX(float x0, float y0){
		switch(equation) {
		case 1:
			return secondPartialX1(x0,y0);
		case 2:
			return secondPartialX2(x0,y0);
		case 3:
			return secondPartialX3(x0,y0);
		}
		return secondPartialX1(x0,y0);	
	}
	
	/**Finds the second partial derivative Fyy */
	static float secondPartialY(float x0, float y0){
		switch(equation) {
		case 1:
			return secondPartialY1(x0,y0);
		case 2:
			return secondPartialY2(x0,y0);
		case 3:
			return secondPartialY3(x0,y0);
		}
		return secondPartialY1(x0,y0);	
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
	
	/* * * * *
	 * Defining our 3 functions.
	 * 
	 * Each can be swapped to by keys ',' and '.'
	 * 
	 * * * * */
	
	/***
	 * zFunction 1
	 * z = x^2 + y^2
	 */
	
	/** A function of x and y that gives us the z value for our heightMap*/
	static float zFunction1(float x, float y){
		return (float)(sq(x)+sq(y));	
	}
	
	/** Calculates dx, the partial derivative of x for the zFunction at a point
	 * (x0,y0) */
	static float partialX1(float x0, float y0){
		float fx = 2*x0;
		return -fx;
	}
	/** Calculates dy, the partial derivative of y for the zFunction at a point
	 * (x0,y0) */
	static float partialY1(float x0, float y0){
		float fy = 2*y0;
		return -fy;
	}
	
	/**Finds the second partial derivative Fxx */
	static float secondPartialX1(float x0, float y0){
		return -2;
	}
	
	/**Finds the second partial derivative Fyy */
	static float secondPartialY1(float x0, float y0){
		return -2;
	}
	
	/***
	 * zFunction 2
	 * z = x^3 - 3x + y^3 - 3y
	 */
	
	/** A function of x and y that gives us the z value for our heightMap*/
	static float zFunction2(float x, float y){
		float px = sq(x)*x - 3*x;
		float py = sq(y)*y - 3*y;
		return (float)(px+py);	
	}
	
	/** Calculates dx, the partial derivative of x for the zFunction at a point
	 * (x0,y0) */
	static float partialX2(float x0, float y0){
		float fx = 3*sq(x0) - 3;
		return -fx;
	}
	/** Calculates dy, the partial derivative of y for the zFunction at a point
	 * (x0,y0) */
	static float partialY2(float x0, float y0){
		float fy = 3*sq(y0) - 3;
		return -fy;
	}
	
	/**Finds the second partial derivative Fxx */
	static float secondPartialX2(float x0, float y0){
		return -3*x0;
	}
	
	/**Finds the second partial derivative Fyy */
	static float secondPartialY2(float x0, float y0){
		return -3*y0;
	}
	
	/***
	 * zFunction 3
	 * z = x + y
	 */
	
	/** A function of x and y that gives us the z value for our heightMap*/
	static float zFunction3(float x, float y){
		return (float)(x+y);	
	}
	
	/** Calculates dx, the partial derivative of x for the zFunction at a point
	 * (x0,y0) */
	static float partialX3(float x0, float y0){
		return -1;
	}
	/** Calculates dy, the partial derivative of y for the zFunction at a point
	 * (x0,y0) */
	static float partialY3(float x0, float y0){
		return -1;
	}
	
	/**Finds the second partial derivative Fxx */
	static float secondPartialX3(float x0, float y0){
		return 1;
	}
	
	/**Finds the second partial derivative Fyy */
	static float secondPartialY3(float x0, float y0){
		return 1;
	}
	
}
