package transformer;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import main.GConstants.EAnchors;
import shape.GShape;

public class GResizer extends GTransformer {
	private EAnchors anchors;

	public GResizer(GShape shape) {
		super(shape);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initTransforming(Graphics2D graphics2D, int x, int y) {
		this.setOldPoint(x, y);
		this.anchors = this.shape.getCurrentEAnchor();
	}
	
	private double getDX(double x, double width) {
		return(x - this.previous.getX()) / width;
	}
	
	private double getDY(double y, double height) {
		return(y - this.previous.getY()) / height;
	}

	@Override
	public void keepTransforming(Graphics2D graphics2D, int x, int y) {
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setXORMode(graphics2D.getBackground());
		this.shape.draw(graphics2D);
		Rectangle bound = this.shape.getShape().getBounds();
		double dx = this.getDX(x, bound.getWidth());
		double dy = this.getDY(y, bound.getHeight());
		//scale�� ���� Ȯ�����ְ� translate�� �����̵�
		switch(this.anchors) {
		case NW : // �ϼ�
			affineTransform.setToTranslation(bound.getMinX() + bound.getWidth(), bound.getMinY() + bound.getHeight());//�ݴ��Ŀ
			affineTransform.scale(1 - dx, 1 - dy);
			affineTransform.translate(-(bound.getMinX() + bound.getWidth()), -(bound.getMinY() + bound.getHeight()));
			break;
		case NN : // ��
			affineTransform.setToTranslation(0, bound.getMinY() + bound.getHeight());
			affineTransform.scale(1 , 1 - dy);
			affineTransform.translate(0, -(bound.getMinY() + bound.getHeight()));
			break; 
		case NE : //�ϵ�
			affineTransform.setToTranslation(bound.getMinX(), bound.getMinY() + bound.getHeight());
			affineTransform.scale(1 + dx, 1 - dy);
			affineTransform.translate(-(bound.getMinX()), -(bound.getMinY() + bound.getHeight()));
			break;
		case WW : // ��
			affineTransform.setToTranslation(bound.getMinX() + bound.getWidth(), 0);
			affineTransform.scale(1 - dx, 1);
			affineTransform.translate(-(bound.getMinX() + bound.getWidth()), 0);
			break;
		case EE : // ��
			affineTransform.setToTranslation(bound.getMinX(), 0);
			affineTransform.scale(1 + dx, 1);
			affineTransform.translate(-(bound.getMinX()), 0);
			break;
		case SW : // ����
			affineTransform.setToTranslation(bound.getMinX() + bound.getWidth(), bound.getMinY());
			affineTransform.scale(1 - dx, 1 + dy);
			affineTransform.translate(-(bound.getMinX() + bound.getWidth()), -(bound.getMinY()));
			break;
		case SS : // ��
			affineTransform.setToTranslation(0, bound.getMinY());
			affineTransform.scale(1, 1 + dy);
			affineTransform.translate(0, -(bound.getMinY()));
			break;
		case SE : // ����
			affineTransform.setToTranslation(bound.getMinX(), bound.getMinY());
			affineTransform.scale(1 + dx, 1 + dy);
			affineTransform.translate(-(bound.getMinX()), -(bound.getMinY()));
			break;
		default:
			break;
		}
		this.shape.setShape(affineTransform.createTransformedShape(this.shape.getShape()));
		this.setOldPoint(x, y);
		this.shape.draw(graphics2D);
	}

	@Override
	public void finishTransforming(Graphics2D graphics2D, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void continueTransforming(Graphics2D graphics2D, int x, int y) {
		// TODO Auto-generated method stub

	}
}