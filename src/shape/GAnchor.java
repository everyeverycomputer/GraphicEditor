package shape;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.util.Vector;

import main.GConstants;
import main.GConstants.EAnchors;


public class GAnchor implements Serializable{
	
	private static final long serialVersionUID = GConstants.serialVersionUID;
	private final static int w = 8;
	private final static int h = 8;
	
	private Vector<Ellipse2D> anchors;
	
	public GAnchor() { 
		this.anchors = new Vector<Ellipse2D>(); // ���� ����
		for(int i=0; i<EAnchors.values().length-1; i++) { // EAnchor�� ������ŭ ���׶�� ����
			Ellipse2D anchor = new Ellipse2D.Double();
			this.anchors.add(anchor);
		}
	}
	public void drawObjectOnRect(EAnchors eAnchor, Ellipse2D anchor, Rectangle bounds) {
		int x = 0;
		int y = 0;
		switch(eAnchor) {
		case NW : // �ϼ�
			x = bounds.x;
			y = bounds.y;
			break;
		case NN : // �Ϻ�
			x = bounds.x + bounds.width/2;
			y = bounds.y;
			break;
		case NE : // �ϵ�
			x = bounds.x + bounds.width;
			y = bounds.y;
			break;
		case SW : // ����
			x = bounds.x;
			y = bounds.y + bounds.height;
			break;
		case SS : // ����
			x = bounds.x + bounds.width/2;
			y = bounds.y + bounds.height;
			break;
		case SE : // ����
			x = bounds.x + bounds.width;
			y = bounds.y + bounds.height;
			break;
		case WW : // ����
			x = bounds.x;
			y = bounds.y + bounds.height/2;
			break;
		case EE : // ����
			x = bounds.x + bounds.width;
			y = bounds.y + bounds.height/2;
			break;
		case RR : // rotate
			x = bounds.x + bounds.width/2;
			y = bounds.y - 30;
			break;
		default :
			break;
		}
		x = x - w/2;
		y = y - h/2;
		anchor.setFrame(x, y, w, h);
	}
	
	public void setBounds(Rectangle bounds) {
		for(int i=0; i<EAnchors.values().length-1; i++) {
			Ellipse2D anchor = this.anchors.get(i);
			this.drawObjectOnRect(EAnchors.values()[i], anchor, bounds);	
		}
	}
	
	public EAnchors contains(int x, int y) {
		for(int i=0; i<EAnchors.values().length-1; i++) {
			if(this.anchors.get(i).contains(x, y)) {
				return EAnchors.values()[i];
			}
		}
		return null;
	}

	public void draw(Graphics2D graphics2D) {
		for(Ellipse2D anchor : this.anchors) {
			graphics2D.draw(anchor);
		}
	}
}