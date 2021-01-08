package com.justbon.lps.utils;

public class DatasetRecordException extends DatasetException{
	/**   
	 * @Fields serialVersionUID : TODO  
	 */ 
	private static final long serialVersionUID = -6006018614178972244L;

	public DatasetRecordException(String message) {
	    super(message);
	  }

	  public DatasetRecordException(String message, Throwable t) {
	    super(message, t);
	  }

	  /**
	   * Precondition-style validation that throws a {@link DatasetRecordException}.
	   *
	   * @param isValid
	   *          {@code true} if valid, {@code false} if an exception should be
	   *          thrown
	   * @param message
	   *          A String message for the exception.
	   */
	  public static void check(boolean isValid, String message, Object... args) {
	    if (!isValid) {
	      String[] argStrings = new String[args.length];
	      for (int i = 0; i < args.length; i += 1) {
	        argStrings[i] = String.valueOf(args[i]);
	      }
	      throw new DatasetRecordException(
	          String.format(String.valueOf(message), (Object[]) argStrings));
	    }
	  }
}
