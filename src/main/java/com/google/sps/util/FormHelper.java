package com.google.sps.util;

import com.google.appengine.api.datastore.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FormHelper {
  // input: String of values separated by commas
  // returns: values split by commas, trimmed and in lowercase and without any empty/null members
  public static ArrayList<String> separateByCommas(String input) {
    ArrayList<String> list = new ArrayList<String>(Arrays.asList(Arrays.stream(input.split(",")).map(String::trim).map(String::toLowerCase).toArray(String[]::new)));
    list.removeAll(Arrays.asList("", null, "\n", "\r\n", "\r"));
    return list;
  }

  // input: String of values separated by newlines
  // returns: values split by newlines, trimmed and without any empty/null/newline members
  public static ArrayList<String> separateByNewlines(String input) {
    ArrayList<String> list = new ArrayList<String>(Arrays.asList(Arrays.stream(input.split("\n")).map(String::trim).toArray(String[]::new)));
    list.removeAll(Arrays.asList("", null, "\n", "\r\n", "\r"));
    return list;
  }
}