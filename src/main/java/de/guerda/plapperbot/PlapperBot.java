/**
 * 
 */
package de.guerda.plapperbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import de.guerda.ApplicationException;
import de.guerda.plapperbot.controller.PlapperBotController;
import de.guerda.plapperbot.view.MainWindow;

/**
 * @author philip
 * 
 */
public class PlapperBot {

  // TODO wrapper class
  private HashMap<WordPair, List<String>> dictionary;
  private Random random;

  public static void main(String[] args) {
    PlapperBot tmpPlapperBot = new PlapperBot();
    tmpPlapperBot.start();
    // tmpPlapperBot.initializeDictionary();

    // Generate text
    // for (int i = 0; i <= 10; i++) {
    // String tmpText = tmpPlapperBot.generateChatLine(tmpPlapperBot,
    // tmpRandom);
    // System.out.println(tmpText);
    // }
  }

  private void start() {
    MainWindow tmpMainWindow = new MainWindow();
    PlapperBotController tmpController = new PlapperBotController();
    tmpController.setView(tmpMainWindow);
    tmpMainWindow.setController(tmpController);
    
    tmpMainWindow.setVisible(true);
  }

  public PlapperBot() {
    random = new Random();
  }

  /**
   * @param tmpPlapperBot
   * @param tmpRandom
   * @return
   */
  public String generateChatLine(PlapperBot tmpPlapperBot) {
    StringBuffer tmpText = new StringBuffer();
    WordPair tmpStartWords = tmpPlapperBot.getRandomInitialWordPair();// new
                                                                      // WordPair("tach",
                                                                      // "");
    String tmpLastWord = tmpStartWords.getSecondWord();
    tmpText.append(tmpStartWords.getFirstWord() + " " + tmpStartWords.getSecondWord());
    List<String> tmpPossibleWords = null;
    while ((tmpPossibleWords = tmpPlapperBot.getPossibleWords(tmpStartWords)) != null) {
      int tmpLength = tmpPossibleWords.size();
      int tmpIndex = random.nextInt(tmpLength);
      String tmpNextWord = tmpPossibleWords.get(tmpIndex);
      tmpText.append(" " + tmpNextWord);

      tmpStartWords = new WordPair(tmpLastWord, tmpNextWord);
      tmpLastWord = tmpNextWord;
    }
    return tmpText.toString();
  }

  private WordPair getRandomInitialWordPair() {
    Set<WordPair> tmpKeySet = dictionary.keySet();
    int tmpSize = tmpKeySet.size();
    Random tmpRandom = new Random();
    int tmpIndex = tmpRandom.nextInt(tmpSize);
    WordPair[] tmpArray = tmpKeySet.toArray(new WordPair[0]);
    WordPair tmpWordPair = tmpArray[tmpIndex];
    return tmpWordPair;
  }

  private List<String> getPossibleWords(WordPair aStartWords) {
    return dictionary.get(aStartWords);
  }

  /**
   * Initializes the dictionary with a great example sentence:
   * 
   * <pre>
   * The brown fox jumps over the lazy dog.
   * </pre>
   */
  private void initializeDictionary() {
    dictionary = new HashMap<>();

    String tmpInput = "Eine MarkowKette engl. Markov chain, auch MarkowProzess, nach Andrei Andrejewitsch Markow, andere Schreibweisen:"
        + " MarkovKette, MarkoffKette, MarkofKette ist ein spezieller stochastischer Prozess. Man unterscheidet eine MarkowKette "
        + "in diskreter und in stetiger Zeit. Der Unterschied in der Bezeichnung „MarkowKetten“ in stetiger/diskreter Zeit zu "
        + "MarkowProzessen besteht darin, dass der Zustandsraum bei letzteren im Allgemeinen stetig ist, während bei diskretem "
        + "Zustandsraum von MarkowKetten gesprochen wird. Ziel ist es, Wahrscheinlichkeiten für das Eintreten zukünftiger Ereignisse"
        + " anzugeben. Das Spezielle einer MarkowKette ist die Eigenschaft, dass durch Kenntnis einer begrenzten Vorgeschichte ebenso "
        + "gute Prognosen über die zukünftige Entwicklung möglich sind wie bei Kenntnis der gesamten Vorgeschichte des Prozesses. "
        + "Im Falle einer MarkowKette erster Ordnung heißt das: Die Zukunft des Systems hängt nur von der Gegenwart dem aktuellen Zustand "
        + "und nicht von der Vergangenheit ab. Die mathematische Formulierung im Falle einer endlichen Zustandsmenge benötigt lediglich den "
        + "Begriff der diskreten Verteilung sowie der bedingten Wahrscheinlichkeit, während im zeitstetigen Falle die Konzepte der Filtration"
        + " sowie der bedingten Erwartung benötigt werden.";
    extractDictionaryFromText(tmpInput);

    try {
      readOutIcqLogs();
    } catch (ApplicationException e) {
      getLogger().error("Could not read ICQ log files!", e);
    }

    Set<WordPair> tmpKeySet = dictionary.keySet();
    for (WordPair tmpWordPair : tmpKeySet) {
      StringBuffer tmpText = new StringBuffer(tmpWordPair + ": ");
      List<String> tmpList = dictionary.get(tmpWordPair);
      for (String tmpString : tmpList) {
        tmpText.append(tmpString + ", ");
      }
      getLogger().info(tmpText.toString());
    }
  }

  /**
   * @param tmpInput
   */
  public void extractDictionaryFromText(String tmpInput) {
    StringTokenizer tmpTokenizer = new StringTokenizer(tmpInput, " ");
    String tmpFirstWord = null;
    String tmpSecondWord = null;
    while (tmpTokenizer.hasMoreTokens()) {
      String tmpThirdWord = tmpTokenizer.nextToken().toLowerCase().trim();

      if (tmpFirstWord != null && tmpSecondWord != null) {
        WordPair tmpWordPair = new WordPair(tmpFirstWord, tmpSecondWord);
        List<String> tmpList = dictionary.get(tmpWordPair);
        if (tmpList == null) {
          tmpList = new ArrayList<>();
          dictionary.put(tmpWordPair, tmpList);
        }
        try {
          tmpList.add(tmpThirdWord);
        } catch (UnsupportedOperationException e) {
          getLogger().error("Could not add word '" + tmpThirdWord + "'!", e);
        }
      }
      tmpFirstWord = tmpSecondWord;
      tmpSecondWord = tmpThirdWord;
    }
  }

  public void readOutIcqLogs() throws ApplicationException {
    try (InputStream tmpResourceAsStream = getClass().getResourceAsStream("../../../filelist.txt");
        InputStreamReader tmpInputStreamReader = new InputStreamReader(tmpResourceAsStream);
        BufferedReader tmpBufferedReader = new BufferedReader(tmpInputStreamReader);) {
      String tmpFilename = null;
      while ((tmpFilename = tmpBufferedReader.readLine()) != null) {
        getLogger().info("Read line: " + tmpFilename);
        extractFromLogFile(tmpFilename);
      }
    } catch (IOException e) {
      throw new ApplicationException("Could not read File!", e);
    }
  }

  private void extractFromLogFile(String aFilename) throws ApplicationException {
    boolean isHtml = (aFilename.endsWith(".html"));
    try (InputStream tmpResourceAsStream = new FileInputStream(new File(aFilename));
        InputStreamReader tmpInputStreamReader = new InputStreamReader(tmpResourceAsStream);
        BufferedReader tmpBufferedReader = new BufferedReader(tmpInputStreamReader);) {
      String tmpChatLine = null;
      int i = 0;
      while ((tmpChatLine = tmpBufferedReader.readLine()) != null) {
        if (i == 0) {
          i++;
          continue;
        }

        if (isHtml) {
          tmpChatLine = Jsoup.parseBodyFragment(tmpChatLine).text();
        }
        if (tmpChatLine.length() > 10) {
          tmpChatLine = tmpChatLine.substring(9);
          int tmpPosition = tmpChatLine.indexOf(":");
          if (tmpChatLine.length() > tmpPosition + 1) {
            tmpChatLine = tmpChatLine.substring(tmpPosition + 2);
          }
        }
        getLogger().info("Read line: " + tmpChatLine);
        extractDictionaryFromText(tmpChatLine);
      }
    } catch (IOException e) {
      throw new ApplicationException("Could not read File!", e);
    }
  }

  private Logger getLogger() {
    return Logger.getLogger(getClass());
  }
}
