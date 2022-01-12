package transformer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import shape.GShape;

public class GDrawer extends GTransformer{

	public GDrawer(GShape shape) {
		super(shape);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initTransforming(Graphics2D g2d, int x, int y) {
		this.shape.setOrigin(x, y);
	}

	@Override
	public void keepTransforming(Graphics2D g2d, int x, int y) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setXORMode(g2d.getBackground());
		this.shape.draw(g2d);
		this.shape.setPoint(x, y);
		this.shape.draw(g2d);
	}
	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {}
	@Override
	public void continueTransforming(Graphics2D graphics2d, int x, int y) {
		this.shape.addPoint(x, y);
	}
	
}
