package shape;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class GOval extends GShape implements Cloneable {
	private static final long serialVersionUID = 1L;

	public GOval() {
		this.eDrawingStyle = EDrawingStyle.e2Points;		
		this.shape = new Ellipse2D.Float();
	}
	
	@Override
	public void setOrigin(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D)this.shape;
		ellipse.setFrame(x, y, 0, 0);
	}

	@Override
	public void setPoint(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D)this.shape;
		int newWidth = (int) (x - ellipse.getX());
		int newHeight = (int) (y - ellipse.getY());
		ellipse.setFrame(ellipse.getX(), ellipse.getY(), newWidth, newHeight);
	}
	
	@Override
	public void addPoint(int x, int y) {
	}

	@Override
	public void keepMoving(Graphics2D graphics2d, int x, int y) {
		int dw = x - tX;
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
