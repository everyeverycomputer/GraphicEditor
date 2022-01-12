package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import shape.GShape;

public class GRotator extends GTransformer {
   
   public GRotator(GShape shape) {
      super(shape);
      // TODO Auto-generated constructor stub
   }

   @Override
   public void initTransforming(Graphics2D graphics2D, int x, int y) {
      this.center.setLocation((int)this.shape.getBounds().getCenterX(), (int)this.shape.getBounds().getCenterY());
      this.previous.setLocation(x, y);
   }
   
   private double computeRotateAngle(Point center, Point previous, Point end) {
      double startAngle = Math.toDegrees(Math.atan2(previous.getY() - center.getY(), previous.getX() - center.getX()));
      double endAngle = Math.toDegrees(Math.atan2(end.getY() - center.getY(), end.getX() - center.getX()));
      double angle =  endAngle - startAngle;
      if(angle<0) angle += 360;
      return angle;
   }
   
   @Override
   public void keepTransforming(Graphics2D graphics2D, int x, int y) {
      graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics2D.setXORMode(graphics2D.getBackground());
      
      this.shape.draw(graphics2D);
      
      double rotateAngle = Math.toRadians(computeRotateAngle(center, previous, new Point(x,y)));
      this.affineTransform.rotate(rotateAngle, this.center.getX(), this.center.getY());
      this.shape.setShape(affineTransform.createTransformedShape(this.shape.getShape()));
      this.affineTransform.setToRotation(Math.toRadians(rotateAngle), center.getX(), center.getY());
      this.shape.setShape(this.affineTransform.createTransformedShape(this.shape.getShape()));
      this.shape.draw(graphics2D);
      this.previous.setLocation(x, y);
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