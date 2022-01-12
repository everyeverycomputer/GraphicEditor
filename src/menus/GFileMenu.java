package menus;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import file.GFile;
import main.GConstants;
import main.GConstants.EFile;

public class GFileMenu extends GMenu{
   // attributes
   private static final long serialVersionUID = GConstants.serialVersionUID;

   //working variables
   private File file, directory;
   private boolean cancel;
   
   public GFileMenu(String name) {
      super(name);
      for (GConstants.EFileMenu eFileMenu: GConstants.EFileMenu.values()) {
         JMenuItem menuItem = new JMenuItem(eFileMenu.getTitle());
         menuItem.setActionCommand(eFileMenu.getActionCommand());
         menuItem.addActionListener(this.actionHandler);
         this.menuItems.add(menuItem);
         this.add(menuItem);
      }
      //currentDirectory = defaultDirectory
      this.directory = new File(GConstants.EFile.eDefaultDirectory.getTitle());
      this.file = null;
   }
   public void initialize() {}
   public boolean checkSave() {
	   cancel = false; // cancel 버튼 눌림 여부
	   int reply = GConstants.EFile.eNO.getOption();
	   if(this.drawingPanel.isUpdated()) {
		   reply =JOptionPane.showConfirmDialog(this.drawingPanel, 
				   GConstants.EFile.eMessage.getTitle(), 
				   GConstants.EFile.eMessageTitle.getTitle(), 
				   GConstants.EFile.eDialogOption.getOption() );
		   if(reply == GConstants.EFile.eCANCEL.getOption()) {
			   cancel = true;
		   }
	   }
	   //cancel버튼이 눌리지 않았을 때
		   if(cancel == false) {
			   if(reply == GConstants.EFile.eYES.getOption()) {
				   this.save();
			   }
		   }
	   return cancel;
   }
   public void checkOut() {
	   if(this.drawingPanel.isUpdated()) {
		   int reply =JOptionPane.showConfirmDialog(this.drawingPanel, 
				   GConstants.EFile.eMessage.getTitle(), 
				   GConstants.EFile.eMessageTitle.getTitle(), 
				   GConstants.EFile.eDialogOption.getOption() );
		   			if(reply == EFile.eYES.getOption()) {
		   				this.save();
		   				System.exit(0);
		   			}
		   			else if(reply == EFile.eNO.getOption()) {
		   				System.exit(0);
		   			}
	   }
	   else {
		   System.exit(0);
	   }
   }
   //그림 모두 지우기
   public void nnew() {
	   cancel = this.checkSave();
	   if(cancel == false)
		   this.drawingPanel.clearShapes();
	       this.file = null;
   }  
   public void open() {
	   cancel = this.checkSave();
	   if(cancel == false) {
		   JFileChooser fc = new JFileChooser(this.directory);
		   fc.setDialogTitle(EFile.eOpen.getTitle());
		   int returnVal = fc.showOpenDialog(this.drawingPanel);
		   if(returnVal == EFile.eApprove.getOption()) {
			   this.drawingPanel.clearShapes();
			   this.directory = fc.getCurrentDirectory();
			   this.file = fc.getSelectedFile();
			   GFile gFile = new GFile();
			   Object shapes = gFile.readObject(this.file);
			   this.drawingPanel.setShapes(shapes);
		   }
	   }
   }

   public void saveAs() {
	   JFileChooser fc = new JFileChooser();
	   fc.setDialogTitle(EFile.eSave.getTitle());
	   int returnVal = fc.showSaveDialog(this.drawingPanel);
	   if(returnVal == EFile.eApprove.getOption()) {
		   this.directory = fc.getCurrentDirectory();
		   this.file = fc.getSelectedFile();
		   this.save();
	   }
   }
   public void save() {
	   if(this.file == null) {
		   this.saveAs();
	   }
	   else {
		   GFile gFile = new GFile();
		   gFile.saveObject(drawingPanel.getShapes(), file);   
		   this.drawingPanel.setUpdated(false);
	   }
   }
   public void print() {
	   this.drawingPanel.print();
   } 
   public int exit() {
	   checkOut();
	   return 0;
   }
}