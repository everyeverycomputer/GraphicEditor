package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.plaf.basic.BasicArrowButton;

import main.GConstants;
import main.GConstants.EToolbar;

public class GToolBar extends JToolBar {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	// components
	private ActionHadler actionHadler;
	private ButtonGroup buttonGroup;
	// associations
	private GDrawingPanel drawingPanel;
	private JPanel strokeTransPanel;
	public GToolBar() {
		super();
		// create components
		this.actionHadler = new ActionHadler();
		this.buttonGroup = new ButtonGroup();	
		this.strokeTransPanel = new JPanel();		
		for (GConstants.EToolbar eTool: GConstants.EToolbar.values()) {
			JRadioButton button = new JRadioButton(new ImageIcon(eTool.getImage()));
			button.setRolloverIcon(new ImageIcon(eTool.getSltImage()));
			button.setSelectedIcon(new ImageIcon(eTool.getSltImage()));
			button.setBorderPainted(true);
			button.setActionCommand(eTool.toString());
			button.addActionListener(this.actionHadler);
			this.add(button);
			buttonGroup.add(button);
		}
		this.strokeTransPanel.setLayout(new BoxLayout(this.strokeTransPanel, BoxLayout.X_AXIS) );
		this.strokeTransPanel.setBorder(BorderFactory.createTitledBorder("LineThickness"));
		this.add(this.strokeTransPanel);
		
		BasicArrowButton upButton = new BasicArrowButton(BasicArrowButton.NORTH);
		upButton.setActionCommand("MakeLineThick");
		upButton.addActionListener(actionHadler);
		this.strokeTransPanel.add(upButton);
		BasicArrowButton downButton = new BasicArrowButton(BasicArrowButton.SOUTH);
		downButton.setActionCommand("MakeLineThin");
		downButton.addActionListener(actionHadler);
		this.strokeTransPanel.add(downButton);

		this.add(Box.createHorizontalStrut(200));
	}
	
	public void setAssociation(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;		
	}
	public void initialize() {
		// set associations
		
		// set associative attributes
		((JRadioButton)this.getComponentAtIndex(EToolbar.eRectangle.ordinal())).doClick();
		// initialize components
	}
	
	class ActionHadler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getActionCommand().equals("MakeLineThick")) {
				drawingPanel.makeLineThick();
			}
			else if(event.getActionCommand().equals("MakeLineThin")) {
				drawingPanel.makeLineThin();
			}
			else {
			drawingPanel.setCurrentTool(GConstants.EToolbar.valueOf(event.getActionCommand()));		
			}
		}
	}
}

