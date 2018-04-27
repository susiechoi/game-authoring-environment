package engine.sprites.properties;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Player {

	private Transform transform;
	private Rectangle rect;
	
	double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    
    private double speed;
	
	public Player(Vector2 position, double size, Color color, double speed)
	{
		this.transform = new Transform(position);
		this.rect = new Rectangle(size, size);
		this.rect.setFill(color);
		this.rect.setX(position.getX());
		this.rect.setY(position.getY());
		this.speed = speed;
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
	
	/**
	 * Updates the position of the rectangle to be equal to the position of the object's transform position
	 */
	public void UpdateRectangle()
	{
		rect.setX(transform.getPosition().getX());
		rect.setY(transform.getPosition().getY());
	}
	
	/**
	 * 
	 * @param other
	 * @param stepDistance
	 * 
	 * Calls moveobject in the transform class to move one object towards another
	 */
	public void moveTowards(Player other, double stepDistance)
	{
		transform.moveTowards(other.getTransform(), stepDistance);
	}
	
	public void moveInDirection(Vector2 direction, double stepDistance)
	{
		transform.move(direction, stepDistance);
	}
	
	public void pingpong(Vector2 original, Vector2 target, double stepDistance)
	{
		transform.pingPongObject(new Transform(original), new Transform(target), stepDistance);
	}
	
	public void moveTowardsDamped(Player other, double stepDistance)
	{
		transform.acceleratedMoveTowards(other.getTransform(), stepDistance);
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
}
