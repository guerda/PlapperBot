package de.guerda.plapperbot.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.filechooser.FileFilter;

import org.ciscavate.cjwizard.PageFactory;
import org.ciscavate.cjwizard.WizardPage;
import org.ciscavate.cjwizard.WizardSettings;

import de.guerda.plapperbot.controller.PlapperBotController;

public class PlapperBotPageFactory implements PageFactory {

  ArrayList<WizardPage> pages;
  private JList<File> fileList;
  private JFileChooser inputFileChooser;
  private DefaultListModel<File> fileListData;
  
  private PlapperBotController controller;

  public PlapperBotPageFactory() {
    pages = new ArrayList<>();

    WizardPage tmpInputFilesPage = new WizardPage("Choose Files", "Choose input files") {
      
      
    };
    inputFileChooser = new JFileChooser();
    inputFileChooser.setMultiSelectionEnabled(true);
    inputFileChooser.setFileFilter(new FileFilter() {

      @Override
      public String getDescription() {
        return "Text files (*.txt)";
      }

      @Override
      public boolean accept(File aF) {
        return aF.isFile();
      }
    });

    fileListData = new DefaultListModel<>();
    fileList = new JList<>(fileListData);
    tmpInputFilesPage.add(fileList, "wrap");

    JButton tmpAddFilesButton = new JButton("Add Files");
    tmpAddFilesButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent anEvent) {
        handleAddFilesButtonClick(anEvent);
      }
    });
    tmpInputFilesPage.add(tmpAddFilesButton);

    pages.add(tmpInputFilesPage);

    WizardPage tmpProgressPage = new WizardPage("Progress", "Progressing input files") {
      @Override
      public void rendering(List<WizardPage> aPath, WizardSettings aSettings) {
        super.rendering(aPath, aSettings);
        getController().processFiles();
      }
    };
    pages.add(tmpProgressPage);

    WizardPage tmpOutputPage = new WizardPage("Output", "Output sentences") {
      @Override
      public void rendering(List<WizardPage> aPath, WizardSettings aSettings) {
        super.rendering(aPath, aSettings);
        setFinishEnabled(true);
        setNextEnabled(false);
      }
    };
    pages.add(tmpOutputPage);
  }

  private void handleAddFilesButtonClick(ActionEvent aEvent) {
    inputFileChooser.setVisible(true);
    int tmpShowDialog = inputFileChooser.showOpenDialog(null);
    if (tmpShowDialog == JFileChooser.APPROVE_OPTION) {
      File[] tmpSelectedFiles = inputFileChooser.getSelectedFiles();
      for (File tmpFile : tmpSelectedFiles) {
        fileListData.addElement(tmpFile);
      }
    }
  }

  @Override
  public WizardPage createPage(List<WizardPage> aPath, WizardSettings aSettings) {
    return pages.get(aPath.size());
  }

  public PlapperBotController getController() {
    return controller;
  }

  public void setController(PlapperBotController aController) {
    controller = aController;
  }

}
