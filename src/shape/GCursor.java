package shape;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

public class GCursor {
	
	public GCursor() {	
	}
	public static Cursor NN_RESIZE() {
		return new Cursor(Cursor.N_RESIZE_CURSOR);
	}
	public static Cursor NE_RESIZE() {
		return new Cursor(Cursor.NE_RESIZE_CURSOR);
	}
	public static Cursor NW_RESIZE() {
		return new Cursor(Cursor.NW_RESIZE_CURSOR);
	}
	public static Cursor SS_RESIZE() {
		return new Cursor(Cursor.S_RESIZE_CURSOR);
	}
	public static Cursor SW_RESIZE() {
		return new Cursor(Cursor.SW_RESIZE_CURSOR);
	} 
	public static Cursor SE_RESIZE() {
		return new Cursor(Cursor.SE_RESIZE_CURSOR);
	}
	public static Cursor EE_RESIZE() {
		return new Cursor(Cursor.E_RESIZE_CURSOR);
	}
	public static Cursor WW_RESIZE() {
		return new Cursor(Cursor.W_RESIZE_CURSOR);
	}
	public static Cursor RR_CURSOR() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image rotateCursor = toolkit.getImage("data/rotate.png");
		return toolkit.createCustomCursor(rotateCursor, new Point(10, 10), "Rotate");
	}
	public static Cursor MOVE_CURSOR() {
		return new Cursor(Cursor.MOVE_CURSOR);
	}
}