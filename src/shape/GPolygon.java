package shape;

import java.awt.Graphics2D;
import java.awt.Polygon;

import main.GConstants;

public class GPolygon extends GShape implements Cloneable {
	private static final long serialVersionUID = 1L;
	public final static int nMaxPoints = GConstants.MAXPOINTS;
	public GPolygon() {
		this.eDrawingStyle = EDrawingStyle.eNPoints;
		this.shape = new Polygon();
	}
	
	@Override
	public void setOrigin(int x, int y) {
		Polygon polygon = (Polygon)this.shape;
		polygon.addPoint(x, y);
		polygon.addPoint(x, y);
	}
	@Override
	public void setPoint(int x, int y) {
		Polygon polygon = (Polygon)this.shape;
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;
	}
	@Override
	public void addPoint(int x, int y) {
		Polygon polygon = (Polygon)this.shape;
		polygon.addPoint(x, y);
	}

	@Override
	public void keepMoving(Graphics2D graphics2D, int x, int y) {
		int dw = x - tX;
		int dh = y - tY;
		
		affineTransform.setToTranslation(dw, dh);
		this.shape = affineTransform.createTransformedShape(this.shape);
		
		this.tX = x;
		this.tY = y;
		
	}
	@Override
	public void finishMoving(Graphics2D graphics2D, int x, int y) {
		this.tX = x;
		this.tY = y;
	}
}
