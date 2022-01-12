package menus;
import javax.swing.JMenuItem;

import main.GConstants;

public class GEditMenu extends GMenu {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	public GEditMenu(String name) {
		super(name);

		for (GConstants.EEditMenu eEditMenu: GConstants.EEditMenu.values()) {
		     JMenuItem menuItem = new JMenuItem(eEditMenu.getTitle());
	         menuItem.setActionCommand(eEditMenu.getActionCommand());
	         menuItem.addActionListener(this.actionHandler);
	         this.menuItems.add(menuItem);
	         this.add(menuItem);
		}
	}
	public void initialize() {
	
	}

	public void copy() {
		this.drawingPanel.copy();
	}
	public void paste() {
		this.drawingPanel.paste();
	}
	public void delete() {
		this.drawingPanel.delete();
	}
	public void cut() {
		this.drawingPanel.cut();
	}
	public void group() {
		this.drawingPanel.group();	
		}
	public void unGroup( ) {
		this.drawingPanel.unGroup();
	}
	public void undo() {
		this.drawingPanel.undo();
	}
	public void redo() {
		this.drawingPanel.redo();
	}
}
