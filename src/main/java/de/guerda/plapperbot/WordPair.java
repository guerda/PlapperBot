package de.guerda.plapperbot;

public final class WordPair implements Comparable<WordPair> {

  private String firstWord;
  private String secondWord;

  public WordPair(String aFirstWord, String aSecondWord) {
    super();
    firstWord = aFirstWord.toLowerCase();
    secondWord = aSecondWord.toLowerCase();
  }

  public String getFirstWord() {
    return firstWord;
  }

  public void setFirstWord(String aFirstWord) {
    firstWord = aFirstWord;
  }

  public String getSecondWord() {
    return secondWord;
  }

  public void setSecondWord(String aSecondWord) {
    secondWord = aSecondWord;
  }

  @Override
  public int compareTo(WordPair aO) {
    if (getFirstWord() == null || getFirstWord().trim().equals("")) {
      return -1;
    }
    if (getSecondWord() == null || getSecondWord().trim().equals("")) {
      return -1;
    }
    if (aO.getFirstWord() == null || aO.getFirstWord().trim().equals("")) {
      return 1;
    }
    if (aO.getSecondWord() == null || aO.getSecondWord().trim().equals("")) {
      return 1;
    }

    if (getFirstWord().compareTo(aO.getFirstWord()) < 0) {
      return -1;
    }
    if (getFirstWord().compareTo(aO.getFirstWord()) > 0) {
      return 1;
    }
    if (getSecondWord().compareTo(aO.getSecondWord()) < 0) {
      return -1;
    }
    if (getSecondWord().compareTo(aO.getSecondWord()) > 0) {
      return 1;
    }
    return 0;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((firstWord == null) ? 0 : firstWord.hashCode());
    result = prime * result + ((secondWord == null) ? 0 : secondWord.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    WordPair other = (WordPair) obj;
    if (firstWord == null) {
      if (other.firstWord != null)
        return false;
    } else if (!firstWord.equals(other.firstWord))
      return false;
    if (secondWord == null) {
      if (other.secondWord != null)
        return false;
    } else if (!secondWord.equals(other.secondWord))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return getFirstWord() + " " + getSecondWord();
  }

}
