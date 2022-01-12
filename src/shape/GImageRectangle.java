//package shape;
//
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//
//public class GImageRectangle extends GShape implements Cloneable{
//	private static final long serialVersionUID = 1L;
//	// attribute
//	private BufferedImage image;
//	//constructor
//	public GImageRectangle(File imageFile) {
//		this.eDrawingStyle = EDrawingStyle.e2Points;
//		try{
//			this.image = ImageIO.read(imageFile);} 
//		catch (IOException e) {e.printStackTrace();}
//		
//		//create component
//		this.shape = new Rectangle();
//		
//		//set Rectangle 
//		this.setOrigin(0, 0);
//		this.setPoint(this.image.getWidth(), this.image.getHeight());
//		}
//			
//	public void draw(Graphics graphics) {
//		//draw Image
//		Rectangle bound = this.shape.getBounds();
//		graphics.drawImage(this.image, bound.x, bound.y, bound.width, bound.height, null);
//	
//		//draw anchors
//		super.draw(graphics);
//	}
//
//	@Override
//	public void setOrigin(int x, int y) {
//		Rectangle r = (Rectangle) this.shape;
//		r.setLocation(x, y);
//		r.setSize(0,0);
//	}
//	@Override
//	public void setPoint(int x, int y) {
//		Rectangle r = (Rectangle ) this.shape;
//		int w = x-r.x;
//		int h = y - r.y;
//		r.setSize(w, h);
//	}
//	@Override
//	public void addPoint(int x, int y) {}
//	@Override
//	public void keepMoving(Graphics2D graphics2d, int x, int y) {
//		int dw = x - tX;
//		int dh = y - tY;
//		
//		affineTransform.setToTranslation(dw, dh);
//		this.shape = affineTransform.createTransformedShape(this.shape);
//		
//		this.tX = x;
//		this.tY = y;
//	}
//
//	@Override
//	public void finishMoving(Graphics2D graphics2d, int x, int y) {
//		this.tX = x;
//		this.tY = y;
//	}
//
//}
