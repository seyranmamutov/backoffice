package org.seyran.backoffice.utils;

public class TextUtil {
  public static final String BOM_SYMBOL = "\uFEFF";

  public static String trimDoubleQuotes(String input) {
    return input.replaceAll("\"(.+)\"", "$1");
  }
}
