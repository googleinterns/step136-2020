
package com.google.sps.util;

import com.google.gson.Gson;

/**
 * A class with static utility methods which can be useful across all classes.
 */
public class Utils {
  /**
   * Simple Implementation to reduce need to type print statements in debugging.
   **/
  public static void SOP(Object thing) {
    System.out.println(thing);
  }

  /**
   * Converts an object into JSON using Gson, but abstracts the need to make
   * a Gson object in different parts of code.
   **/
  public static String convertToJson(Object target) {
    Gson gson = new Gson();
    return gson.toJson(target);
  }
}