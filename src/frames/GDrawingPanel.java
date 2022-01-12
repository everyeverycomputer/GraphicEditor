package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.GConstants;
import main.GConstants.EToolbar;
import shape.GShape;
import shape.GShape.EDrawingStyle;
import tool.DeepCloner;
import transformer.GDrawer;
import transformer.GMover;
import transformer.GResizer;
import transformer.GRotator;
import transformer.GTransformer;

public class GDrawingPanel extends JPanel implements Printable {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	private enum EDrawingState {
		eIdle, eDrawing, eTransforming
	}
	private EDrawingState eDrawingState;	
	
	// components
	private MouseHandler mouseHandler;
	private Vector<GShape> shapes;
	private GShape currentShape;
//	private Stack<Vector<GShape>> undo, redo; //되돌리기, 다시실행
	private GTransformer transformer;
	// association components
	private DeepCloner deepCloner;
	private GShape currentTool;
	private Color lineColor, fillColor;
	// constructors and initializers
	public GDrawingPanel() {
		// attributes
		this.setBackground(Color.white);
		this.eDrawingState = EDrawingState.eIdle;
		
		// components
		this.mouseHandler = new MouseHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);
		this.deepCloner = new DeepCloner();	
		this.shapes = new Vector<GShape>();
		
		// working variables
		this.bUpdated = false;
		this.currentShape = null;
		this.currentTool = null;
		this.lineColor = null; 
		this.fillColor = null;
		this.bDrawing = false;
		this.transformer = null;
	}
	// working variables
	private GShape copyShape;
	private boolean bUpdated;
	private boolean bDrawing;
	
	public void initialize() {
		// set associations
		// set associative attributes
		// initialize components
		this.lineColor = this.getForeground();
	}
	
	// setters & getters
	public boolean isUpdated() {
		return this.bUpdated;
	}
	public void setUpdated(boolean bUpdated) {
		this.bUpdated = bUpdated;
	}
	
	public Vector<GShape> getShapes() {
		return this.shapes;
	}
	
	public void setShapes(Object shapes){
		this.shapes = (Vector<GShape>)shapes; // 고치면 점수
		this.repaint();
	}

	public void clearShapes() {
		this.shapes.clear();
		this.repaint();
	}
	
	public void setLineColor(Color lineColor) {
		if(this.currentShape != null) {
			this.currentShape.setLineColor(lineColor);
			this.repaint();
		}
		this.lineColor = lineColor; // color 저장
	}
	
	public void setFillColor(Color fillColor) {
		if(this.currentShape != null) {
			this.currentShape.setFillColor(fillColor);
			this.repaint();
		}
		this.fillColor = fillColor;
	}

	public void setCurrentTool(EToolbar eToolBar) {
		this.currentTool = eToolBar.getTool();
	}
	
	// methods
	public void paint(Graphics graphics) {
		super.paint(graphics);
		Graphics2D graphics2D = (Graphics2D)graphics;
	
		// user defined drawing
		for (GShape shape: this.shapes) {
			shape.draw(graphics2D);
		}		
	}
	
	private void changeCursorPoint(GShape shape) { 
		if(shape == null) {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		else {
			this.setCursor(shape.getCurrentEAnchor().getCursor());
		}
	}
	
	
	private GShape onShape(int x, int y) {
		for(GShape shape : this.shapes) {
			GConstants.EAnchors eAnchors = shape.contains(x,y);
			if(eAnchors != null) {
				return shape;
			}
		}
		return null;
	}
	
	private void resetSelected() {
		for(GShape shape : this.shapes) {
			shape.setSelected(false);
		}
		this.repaint();
	}
	
	private void setSelected(GShape selectedShape) {
		for(GShape shape : this.shapes) {
			shape.setSelected(false); // 기존에 selected된 것을 false 로 바꾼후
		}
		selectedShape.setSelected(true);
		this.repaint();
	}
	
	private void initTransforming(GShape shape, int x, int y) {
		this.resetSelected();
		if(shape == null) {
			this.bDrawing = true;
			this.currentShape = this.currentTool.clone();
			this.currentShape.setOrigin(x, y);
			this.currentShape.setLineColor(this.lineColor);
			this.currentShape.setFillColor(this.fillColor);
			this.transformer = new GDrawer(this.currentShape);
		}
		else {
			this.bDrawing = false;
			this.currentShape = shape;
			// transformation
			switch(shape.getCurrentEAnchor()) {
			case MM :
				this.transformer = new GMover(this.currentShape);
				break;
			case RR :
				this.transformer = new GRotator(this.currentShape);
				break;
			default :
				this.transformer = new GResizer(this.currentShape);
			}
		}
		Graphics graphics = this.getGraphics();
		Graphics2D graphics2D = (Graphics2D) graphics;
		this.transformer.initTransforming(graphics2D, x, y);
	}
	
	private void keepTransforming(int x, int y) {
		Graphics graphics = this.getGraphics();
		Graphics2D graphics2D = (Graphics2D) graphics;
		this.transformer.keepTransforming(graphics2D, x, y);
	}
	
	private void finishTransforming(int x, int y) {
		Graphics graphics = this.getGraphics();
		Graphics2D graphics2D = (Graphics2D) graphics;
		this.transformer.finishTransforming(graphics2D, x, y);
		this.bUpdated = true;
		this.setSelected(this.currentShape);
		if(this.bDrawing) {
			this.shapes.add(this.currentShape);
		}
	}
	
	void continueTransforming(int x, int y) {
		Graphics graphics = this.getGraphics();
		Graphics2D graphics2D = (Graphics2D) graphics;
		this.transformer.continueTransforming(graphics2D, x, y);
	}
	
	public void copy() {
		this.copyShape = (GShape) this.deepCloner.clone(this.currentShape);
	}
	public void paste() {
		resetSelected();
		this.transformer = new GMover(this.copyShape);	
		this.transformer.pasteLocation();
		this.setSelected(this.copyShape);
		GShape pasteShape =  ((GShape) this.deepCloner.clone(this.copyShape));
		this.shapes.add(pasteShape);
		this.currentShape = pasteShape;
		this.repaint();

	}
	
	public void delete() {
		this.shapes.remove(this.currentShape);
		this.repaint();
	}
	public void cut() {
		this.copyShape = this.currentShape;
		this.delete();
	}
	public void group() {
	}
	public void unGroup() {
	}
	public void undo() {	
	}
	public void redo()  {
	}
	public void makeLineThin() {
		if(this.currentShape != null) {
			this.currentShape.makeLineThin();
			this.repaint();
		}
	}
	public void makeLineThick() {
		if(this.currentShape != null) {
			this.currentShape.makeLineThick();
			this.repaint();
		}
	}
	public void print() {
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);
		boolean isPrinted = printJob.printDialog();
		if(isPrinted) {
			try {
				printJob.print();
			}catch(PrinterException e) {
				JOptionPane.showMessageDialog(this, "can't print");
			}
		}
	}
	
	// inner class
	class MouseHandler implements MouseMotionListener, MouseListener {
		@Override
		public void mouseClicked(MouseEvent event) {
			if (event.getClickCount() == 1) {
				this.mouse1Clicked(event);
			} 
			else if (event.getClickCount() == 2) {
				this.mouse2Clicked(event);
			}
		}
		
		// n point drawing
		private void mouse1Clicked(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			GShape shape = onShape(x,y);
			if (shape == null) {
				if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eDrawingState ==EDrawingState.eIdle) {
					initTransforming(null, x, y);
					eDrawingState = EDrawingState.eDrawing;
				}
			}else {
				setSelected(shape);
			}
			if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eDrawingState ==EDrawingState.eDrawing) {
				continueTransforming(x, y);
			}
		}
		private void mouse2Clicked(MouseEvent event) {
			if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eDrawingState==EDrawingState.eDrawing) {
				int x = event.getX();
				int y = event.getY();
				finishTransforming(x, y);
				eDrawingState = EDrawingState.eIdle;
			}
		}
		@Override
		public void mouseMoved(MouseEvent event) {
			if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eDrawingState==EDrawingState.eDrawing) {
				int x = event.getX();
				int y = event.getY();
				keepTransforming(x, y);
			}
			else if(currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints) {
				//changeCusorPoint(event.getX(), event.getY());
				GShape shape = onShape(event.getX(), event.getY());
				changeCursorPoint(shape);
			}
			else if(currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
				//changeCusorPoint(event.getX(), event.getY());
				GShape shape = onShape(event.getX(), event.getY());
				changeCursorPoint(shape);
			}
		}
		
		// 2 point drawing
		@Override
		public void mousePressed(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			
			if (eDrawingState ==EDrawingState.eIdle) {
				GShape shape = onShape(x, y);
				if (shape == null) {
					if(currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
						initTransforming(null, x, y);
						eDrawingState = EDrawingState.eDrawing;
					}
				}
				else {
					initTransforming(shape, x, y);
					eDrawingState = EDrawingState.eTransforming;
				}
			}
		}
			
		@Override
		public void mouseDragged(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			if (eDrawingState==EDrawingState.eTransforming) {
				keepTransforming(x, y);
			}
			else if(eDrawingState == EDrawingState.eDrawing) {
				if (currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
					keepTransforming(x, y);
				}
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			if (eDrawingState==EDrawingState.eTransforming) {
				finishTransforming(x, y);
				eDrawingState = EDrawingState.eIdle;
			}
			else if (eDrawingState==EDrawingState.eDrawing) {
				if(currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
					finishTransforming(x, y);
					eDrawingState = EDrawingState.eIdle;
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent event) {
			// TODO Auto-generated method stub
			
		}
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if(pageIndex>0) {
			return NO_SUCH_PAGE;
		}
		Graphics2D graphics2d = (Graphics2D)graphics;
		graphics2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		for(GShape shape : this.shapes) {
			shape.draw(graphics2d);
		}
		return PAGE_EXISTS;
	}
}