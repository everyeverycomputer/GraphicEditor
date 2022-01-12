package shape;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import main.GConstants;

public class GPencil extends GShape implements Cloneable{
   private static final long serialVersionUID = GConstants.serialVersionUID;
   private GeneralPath generalPath;
   
   public GPencil() {
      this.eDrawingStyle = EDrawingStyle.e2Points;
      this.shape = new GeneralPath();
      this.generalPath = (GeneralPath) this.shape;
   }
   @Override
   public void setOrigin(int x, int y) {
      this.generalPath.moveTo(x, y);
   }

   @Override
   public void setPoint(int x, int y) {
      this.generalPath.lineTo(x, y);
   }

   @Override
   public void addPoint(int x, int y) {
      // TODO Auto-generated method stub
      
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