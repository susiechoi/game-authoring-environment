package engine.sprites.properties;
public class Vector2 {
	
	public final static Vector2 UP = new Vector2(0,1);
	public final static Vector2 DOWN = new Vector2(0,-1);
	public final static Vector2 LEFT = new Vector2(-1,0);
	public final static Vector2 RIGHT = new Vector2(1,0);

	
	private double x;
	private double y;
	
	/**
	 * Empty constructor if only needed for functions
	 */
	public Vector2() {}
	
	public Vector2(double x, double y)
	{
		this.x = x;
		this.y = y;
		
	}

	public double getX() 
	{
		return x;
	}

	public void setX(double x) 
	{
		this.x = x;
	}

	public double getY() 
	{
		return y;
	}

	public void setY(double y) 
	{
		this.y = y;
	}
	
	/**
	 * 
	 * @return
	 * get magnitude of the vector
	 */
	public double getMagnitude()
	{
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	/**
	 * 
	 * @return
	 * Returns the square of the magnitude. Can be used for optimization purposes since it avoid the costly
	 * Math.sqrt in Java. Can be used as e.g. while(position.getSquaredMagnitude() < maxDistance * maxDistance)
	 */
	public double getSquaredMagnitude()
	{
		return Math.pow(x, 2) + Math.pow(y, 2);
	}
	
	/**
	 * 
	 * @return
	 * Returns a normalized version of the vector 
	 */
	public Vector2 getNormalized()
	{
		return new Vector2(x / this.getMagnitude(), y / this.getMagnitude());
	}
	
	/**
	 * 
	 * @param b
	 * @return
	 * to Calculate the difference of two vectors, 
	 * returns this - a
	 */
	public Vector2 subtractVector(Vector2 b)
	{
		return new Vector2(x - b.getX(), y - b.getY());
	}
	
	/**
	 * 
	 * @param b
	 * @return
	 * To calculate the sum of two vectors
	 * returns this + b
	 */
	public Vector2 addVector(Vector2 b)
	{
		return new Vector2(x + b.getX(), y + b.getY());
	}
	
	/**
	 * 
	 * @param divisor
	 * @return
	 * Divides a vector by a scalar
	 * returns this / divisor
	 */
	public Vector2 divideVector(double divisor)
	{
		return new Vector2(x / divisor,  y / divisor);
	}
	
	/**
	 * 
	 * @param multiple
	 * @return
	 * Multiplies a vector by a scalar
	 * return this * multiple
	 */
	public Vector2 multiplyVector(double multiple)
	{
		return new Vector2(x * multiple, y * multiple);
		
	}
	
	/**
	 * 
	 * @param b
	 * @return
	 * Get dot product with another vector.
	 */
	public double getDotProduct(Vector2 b)
	{
		return (x * b.getX() + y * b.getY());
	}
	
	
}

