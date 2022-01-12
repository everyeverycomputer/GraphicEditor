package transformer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import shape.GShape;

public class GMover extends GTransformer{

	public GMover(GShape shape) {
		super(shape);
	}

	@Override
	public void initTransforming(Graphics2D graphics2D, int x, int y) {
		this.shape.initMoving(graphics2D, x, y);
	}

	@Override
	public void keepTransforming(Graphics2D graphics2D, int x, int y) {
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setXORMode(graphics2D.getBackground());
		this.shape.draw(graphics2D);
		this.shape.keepMoving(graphics2D, x, y);
		this.shape.draw(graphics2D);
	}

	@Override
	public void finishTransforming(Graphics2D graphics2D, int x, int y) {
		this.shape.finishMoving(graphics2D, x, y);
	}

	@Override
	public void continueTransforming(Graphics2D graphics2D, int x, int y) {
	}
	public void pasteLocation() {
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(20,20);
		this.shape.transformShape(affineTransform);
	}
}