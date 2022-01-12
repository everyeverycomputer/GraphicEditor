package menus;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;

import main.GConstants;
import main.GConstants.EColorMenu;

public class GColorMenu extends GMenu {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;
	// components

	public GColorMenu(String name) {
		super(name);
		for (GConstants.EColorMenu eColorMenu: GConstants.EColorMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eColorMenu.getTitle());
			menuItem.setActionCommand(eColorMenu.getActionCommand());
			menuItem.addActionListener(this.actionHandler);
			this.menuItems.add(menuItem);
			this.add(menuItem);
		}	
	}
	public void initialize() {
	}
	public void line() {
		Color selectedColor = JColorChooser.showDialog(this.drawingPanel, 
				EColorMenu.eLineColor.getTitle(), this.drawingPanel.getForeground());
		if(selectedColor != null){
			this.drawingPanel.setLineColor(selectedColor);
		}
	}
	public void fill() {
		Color selectedColor = JColorChooser.showDialog(this.drawingPanel,
				EColorMenu.eFillColor.getTitle(), this.drawingPanel.getForeground());
		if(selectedColor != null){
			this.drawingPanel.setFillColor(selectedColor);
		}
	}

}
