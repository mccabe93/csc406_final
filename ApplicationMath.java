
public class ApplicationMath {
	
	static float sq(float x) {
		return x*x;
	}
	
	static float sqrt(float x) {
		return (float) Math.sqrt(x);
	}
	
	/** A function of x and y that gives us the z value for our heightMap*/
	static float zFunction(float x, float y){
		float xf = sq(x)/5;//((sq(x)*x) - (3*x));
		float yf = sq(y)/5;//((sq(y)*y) - (3*y));
		return (float)(xf+yf);	
	}
	/** Calculates dx, the partial derivative of x for the zFunction */
	static float partialX(float x, float y){
		float dx = 2*x/5;//((3*sq(x)) - 3);
		return dx;
	}
	/** Calculates dy, the partial derivative of y for the zFunction */
	static float partialY(float x, float y){
		float dy = 2*y/5;//((3*sq(y)) - 3);
		return dy;
	}
	/** Calculates the partialX as a unit value by dividing by the squares of
	 * the partialX and partialY at a specific point */
	static float unitPartialX(float x, float y){
		return partialX(x,y)/sqrt(sq(partialX(x,y))+sq(partialY(x,y)));
	}
	/** Calculates the partialY as a unit value by dividing by the squares of
	 * the partialX and partialY at a specific point */
	static float unitPartialY(float x, float y){
		return partialY(x,y)/sqrt(sq(partialX(x,y))+sq(partialY(x,y)));
	}
}
