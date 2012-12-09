package de.guerda.plapperbot.view;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;
import org.ciscavate.cjwizard.PageFactory;
import org.ciscavate.cjwizard.WizardContainer;
import org.ciscavate.cjwizard.WizardListener;
import org.ciscavate.cjwizard.WizardPage;
import org.ciscavate.cjwizard.WizardSettings;
import org.ciscavate.cjwizard.pagetemplates.TitledPageTemplate;

import de.guerda.plapperbot.controller.PlapperBotController;

public class MainWindow extends JDialog {

  PlapperBotController controller;

  /**
   * Default serial version UID.
   */
  private static final long serialVersionUID = 1L;

  private ResourceBundle bundle;

  public MainWindow() {
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(500, 400);
    initI18N();
    initComponents();
  }

  private void initI18N() {
    Locale tmpLocale = Locale.getDefault();
    bundle = ResourceBundle.getBundle("MessagesBundle", tmpLocale);
  }

  private String getTranslatedText(String aKey) {
    return getBundle().getString(aKey);
  }

  public void initComponents() {
    PageFactory tmpFactory = new PlapperBotPageFactory();
    WizardContainer tmpContainer = new WizardContainer(tmpFactory, new TitledPageTemplate());
    getContentPane().add(tmpContainer);

    tmpContainer.addWizardListener(new WizardListener() {
      @Override
      public void onCanceled(List<WizardPage> path, WizardSettings settings) {
        MainWindow.this.dispose();
      }

      @Override
      public void onFinished(List<WizardPage> path, WizardSettings settings) {
        MainWindow.this.dispose();
      }

      @Override
      public void onPageChanged(WizardPage aNewPage, List<WizardPage> aPath) {
      }

    });

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      getLogger().warn("Could not set Look And Feel.", e);
    }

  }

  private Logger getLogger() {
    return Logger.getLogger(getClass());
  }

  public PlapperBotController getController() {
    return controller;
  }

  public void setController(PlapperBotController aController) {
    controller = aController;
  }

  public ResourceBundle getBundle() {
    return bundle;
  }

  public void setBundle(ResourceBundle aBundle) {
    bundle = aBundle;
  }

}
