package de.guerda.plapperbot.controller;

import java.io.File;
import java.util.Collection;

import de.guerda.plapperbot.view.MainWindow;

public class PlapperBotController {
  
  private MainWindow view;
  private Collection<File> fileList;

  public MainWindow getView() {
    return view;
  }

  public void setView(MainWindow aView) {
    view = aView;
  }

  public PlapperBotController() {
    super();
  }

  public void processFiles() {
    // TODO Auto-generated method stub
    
  }

  public Collection<File> getFileList() {
    return fileList;
  }

  public void setFileList(Collection<File> aFileList) {
    fileList = aFileList;
  }
  
  

}
