package shape;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GRectangle extends GShape implements Cloneable {
	private static final long serialVersionUID = 1L;

	public GRectangle() {
		this.eDrawingStyle = EDrawingStyle.e2Points;		
		this.shape = new Rectangle();
	}

	@Override
	public void setOrigin(int x, int y) {
		Rectangle rectangle = (Rectangle)this.shape;
		rectangle.setLocation(x, y);
		rectangle.setSize(0, 0);
	}

	@Override
	public void setPoint(int x, int y) {
		Rectangle rectangle = (Rectangle)this.shape;
		int newWidth = x - rectangle.x;
		int newHeight = y - rectangle.y;
		rectangle.setSize(newWidth, newHeight);	
	}

	@Override
	public void addPoint(int x, int y) {
	}
	public void keepMoving(Graphics2D graphics2d, int x, int y) {
		int dw =  x - tX;
		int dh = y - tY;
		
		affineTransform.setToTranslation(dw, dh);
		this.shape = affineTransform.createTransformedShape(this.shape);
		
		this.tX = x;
		this.tY = y;
	}

	@Override
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
		this.tX = x;
		this.tY = y;
	}
	
}
