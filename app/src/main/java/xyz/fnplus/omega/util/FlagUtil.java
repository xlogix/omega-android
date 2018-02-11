package xyz.fnplus.omega.util;

public class FlagUtil {

  /**
   * Returns true if "flags" contains "flag"
   */
  public static boolean hasFlag(int flags, int flag) {
    return (flags & flag) == flag;
  }
}
