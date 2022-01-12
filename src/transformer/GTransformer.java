package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import shape.GShape;

abstract public class GTransformer {
	protected GShape shape;
	protected AffineTransform affineTransform;
	protected Point previous, center;
	
	public GTransformer(GShape shape) {
		this.shape = shape;
		this.previous = new Point(0, 0);
		this.center = new Point(0,0);
		affineTransform = new AffineTransform();
	}
	
	public void setOldPoint(int x, int y) {
		this.previous.x = x;
		this.previous.y = y;
	}
	public GShape getShape() {
		return shape;
	}
	public void setShape(GShape shape) {
		this.shape = shape;
	}
	public void pasteLocation() {
	}
	abstract public void initTransforming(Graphics2D graphics2D, int x, int y);
	abstract public void keepTransforming(Graphics2D graphics2D, int x, int y);
	abstract public void finishTransforming(Graphics2D graphics2D, int x, int y);
	abstract public void continueTransforming(Graphics2D graphics2D, int x, int y);
		


}