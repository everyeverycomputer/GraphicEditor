package shape;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import main.GConstants;

public class GLine extends GShape implements Cloneable {
	private static final long serialVersionUID = GConstants.serialVersionUID;
	public GLine() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		this.shape = new Line2D.Float();
	}
	
	@Override
	public void setOrigin(int x, int y) {
		Line2D line = (Line2D)this.shape;
		line.setLine(x, y, x, y);
	}

	@Override
	public void setPoint(int x, int y) {
		Line2D line = (Line2D)this.shape;
		line.setLine(line.getX1(), line.getY1(), x, y);	
	}
	
	@Override
	public void addPoint(int x, int y) {
	}
	

	public GConstants.EAnchors contains(int x, int y) {
		eSelectedAnchor = null;
		if(this.bSelected) {
			this.eSelectedAnchor = this.anchors.contains(x, y);
			if(this.eSelectedAnchor != null) {
				return this.eSelectedAnchor;
			}
		}
		if(eSelectedAnchor == null) {
			if(this.shape.intersects(new Rectangle(x, y, 8, 8))){
				this.eSelectedAnchor = GConstants.EAnchors.MM;
			}
		}
		return this.eSelectedAnchor;
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
