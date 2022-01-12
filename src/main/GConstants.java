package main;

import shape.GShape;
import shape.GCursor;
import shape.GRectangle;
import shape.GOval;
import shape.GPencil;
//import shape.GGroup;

import menus.GMenu;
import menus.GFileMenu;
import menus.GEditMenu;

import java.awt.Cursor;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import menus.GColorMenu;
import shape.GLine;
import shape.GPolygon;

public class GConstants {

	public static final long serialVersionUID = 1L;
	
	public GConstants() {
	}
	
	public enum EMainFrame {
		eWidth(600),
		eHeight(800);
		
		private int value;		
		private EMainFrame(int value) {
			this.value = value;
		}	
		public int getValue() {
			return this.value;
		}
	}
	
	public enum EMenubar {
		eFile(new GFileMenu("����")),
		eEdit(new GEditMenu("����")),
		eColor(new GColorMenu("�÷�"));
		
		private GMenu menu;
		private EMenubar(GMenu menu) {
			this.menu = menu;
		}		
		public GMenu getMenu() {
			return this.menu;
		}
	}
	
	public enum EFileMenu {
		eNew("New", "nnew"),
		eOpen("����", "open"),
//		eImageOpen("�̹��� ����", "imageOpen"),
		eSave("����", "save"),
		eSaveAs("�ٸ��̸�����", "saveAs"),
		ePrint("����Ʈ", "print"),
		eQuit("����", "exit");
		
		private String title;		
		private String actionCommand;
		private EFileMenu(String title, String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}		
		public String getTitle() {
			return this.title;
		}
		public String getActionCommand() {
			return this.actionCommand;
		}
	}
	
	public enum EEditMenu {
		eUndo("�ǵ�����", "undo"),
		eRedo("�ٽý���", "redo"),
		eCopy("����", "copy"),
		eCut("�ڸ���", "cut"),
		ePaste("�ٿ��ֱ�", "paste"),
		eDelete("�����", "delete"),
		eGroup("�׷�", "group"),
		eUnGroup("�׷� ����", "unGroup");
		
		private String title;		
		private String actionCommand;
		private EEditMenu(String title, String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}		
		public String getTitle() {
			return this.title;
		}
		public String getActionCommand() {
			return this.actionCommand;
		}
	}
	
	public enum EColorMenu {
		eLineColor("���� ��", "line"),
		eFillColor("ä��� ��", "fill");	

		private String title;		
		private String actionCommand;

		private EColorMenu(String title, String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}		
		public String getTitle() {
			return this.title;
		}
		public String getActionCommand() {
		return this.actionCommand;
	}
}	
	
	public enum EToolbar {
		eRectangle("�׸�", new GRectangle(), "data/Rectangle.png", "data/RectangleSelected.png"),
		eOval("��", new GOval(), "data/Oval.png", "data/OvalSelected.png"),
		eLine("����", new GLine(), "data/Line.png", "data/LineSelected.png"),
		ePolygon("�ٰ���", new GPolygon(), "data/Polygon.png", "data/PolygonSelected.png"),
		ePencil("����", new GPencil(), "data/pencil.png", "data/pencilSelected.png");
		
		private String title;
		private GShape tool;
		private String image;
		private String sltImage;
		
		private EToolbar(String title, GShape tool, String image, String sltImage) {
			this.title = title;
			this.tool = tool;
			this.image = image;
			this.sltImage = sltImage;
		}		
		public String getTitle() {
			return this.title;
		}
		public GShape getTool() {
			return this.tool;
		}
		public String getImage() {
			return this.image;
		}
		public String getSltImage() {
			return this.sltImage;
		}
	}
	
	public final static int MAXPOINTS = 100;

	public enum ECursor {
		eDefault(new Cursor(Cursor.DEFAULT_CURSOR)),
		eMove(new Cursor(Cursor.MOVE_CURSOR)),
		eRotate(new Cursor(Cursor.HAND_CURSOR));

		private Cursor cursor;
		
		private ECursor(Cursor cursor) {
			this.cursor = cursor;
		}		
		public Cursor getCursor() {
			return this.cursor;
		}
	}

	public enum EAnchors{
		NW(GCursor.NW_RESIZE()), 
		NN(GCursor.NN_RESIZE()), 
		NE(GCursor.NE_RESIZE()), 
		SW(GCursor.SW_RESIZE()), 
		SS(GCursor.SS_RESIZE()), 
		SE(GCursor.SE_RESIZE()), 
		WW(GCursor.WW_RESIZE()), 
		EE(GCursor.EE_RESIZE()), 
		RR(GCursor.RR_CURSOR()), 
		MM(GCursor.MOVE_CURSOR());
		
		private Cursor cursor;	
		private EAnchors(Cursor cursor) {
			this.cursor = cursor;
		}		
		public Cursor getCursor() {
			return this.cursor;
		}
	}
	

	public enum EFile{
		eDefaultDirectory("./data"),
		eMessage("���� ������ �����Ͻðڽ��ϳ�?"),
		eMessageTitle("���� Ȯ��"),
		eDialogOption(JOptionPane.YES_NO_CANCEL_OPTION),
		eYES(JOptionPane.YES_OPTION),
		eNO(JOptionPane.NO_OPTION),
		eCANCEL(JOptionPane.CANCEL_OPTION),
		eApprove(JFileChooser.APPROVE_OPTION),
		eOpen("����"), 
		eSave("����");
		private String title;		
		private int option;
		private EFile(String title) {
			this.title = title;
		}		
		private EFile(int option) {
			this.option = option;
		}
		public int getOption() {
			return this.option;
		}
		public String getTitle() {
			return this.title;
		}
	}
}
