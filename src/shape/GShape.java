package shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import main.GConstants;
import main.GConstants.EAnchors;

public abstract class GShape implements Cloneable, Serializable {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	public enum EDrawingStyle {
		e2Points, eNPoints
	}
	
	protected EDrawingStyle eDrawingStyle;
	protected Shape shape;
	protected EAnchors eSelectedAnchor;
	protected AffineTransform affineTransform;
	protected int lineThickness;
	// components
	protected GAnchor anchors;
	// working variables
	protected boolean bSelected; 
	private Color lineColor, fillColor;
	protected int tX, tY;
	
	public GShape() {
		this.lineColor = null;
		this.fillColor = null;
		this.anchors = new GAnchor();
		this.affineTransform = new AffineTransform();
		this.bSelected = false;
		this.eSelectedAnchor = null;
		this.lineThickness = 1;
	}
	
	public Shape getShape() {
		return this.shape;
	}
	
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public EDrawingStyle getEDrawingStyle() { 
		return this.eDrawingStyle; 
	}
	
	public GConstants.EAnchors getCurrentEAnchor(){
		return eSelectedAnchor;
	}
	
	public boolean isSelected() { 
		return bSelected;
	}
	
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
		if(this.bSelected) {
			this.anchors.setBounds(this.shape.getBounds());
		}
	}
	
	public void draw(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D)graphics;
		Stroke savedStroke = graphics2D.getStroke();
		if(this.fillColor != null) {
			graphics2D.setColor(this.fillColor);
			graphics2D.fill(this.shape);
		}
		if(this.lineColor != null) {
			graphics2D.setColor(this.lineColor);
			graphics2D.draw(this.shape);
		}
		if(this.bSelected == true) {
			this.anchors.draw(graphics2D);
			graphics2D.setStroke(savedStroke);

		}
		graphics2D.setStroke(new BasicStroke(this.lineThickness));
		graphics2D.draw(this.shape);
		graphics2D.setStroke(savedStroke);

	}
	
	public GShape clone() {
		try {
			return this.getClass().getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
		
	}
	
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	public void makeLineThick() {
		this.lineThickness +=1;
		
	}
	
	public void makeLineThin() {
		if(this.lineThickness >= 1) {
			this.lineThickness -=1;
		}
	}
	public GConstants.EAnchors contains(int x, int y) {
		this.eSelectedAnchor = null;
		if(this.bSelected) {
			this.eSelectedAnchor = this.anchors.contains(x, y); // EAnchor Сп return
			if(this.eSelectedAnchor != null) {
				return this.eSelectedAnchor;
			}
		}
		if(this.eSelectedAnchor == null) {
			if(this.shape.contains(x,y)) { // move
				this.eSelectedAnchor = GConstants.EAnchors.MM;
			}	
		}
		return this.eSelectedAnchor;
	}
	
	public Rectangle getBounds() {
		return this.shape.getBounds();
	}

	public void initMoving(Graphics2D graphics2D,int x, int y) {
		this.tX = x;
		this.tY = y;	
	}
	abstract public void keepMoving(Graphics2D graphics2D, int x, int y);	
	abstract public void finishMoving(Graphics2D graphics2D, int x, int y);

	public abstract void setOrigin(int x, int y);	
	public abstract void setPoint(int x, int y);	
	public abstract void addPoint(int x, int y);
	public void moveReverse(Point resizeAnchor) {
		this.affineTransform.setToTranslation(-resizeAnchor.getX(), -resizeAnchor.getY());
		this.shape = this.affineTransform.createTransformedShape(this.shape);
	}
	public void transformShape(AffineTransform at) {
		this.shape = at.createTransformedShape(this.shape);
	}

}