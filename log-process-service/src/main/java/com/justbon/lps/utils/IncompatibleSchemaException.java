package com.justbon.lps.utils;

public class IncompatibleSchemaException extends ValidationException {

	  public IncompatibleSchemaException(String message) {
	    super(message);
	  }
	  
	  public IncompatibleSchemaException(String message, Throwable cause) {
	    super(message, cause);
	  }
	  
	  public IncompatibleSchemaException(Throwable cause) {
	    super(cause);
	  }

	  /**
	   * Precondition-style validation that throws a {@link ValidationException}.
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
	      throw new IncompatibleSchemaException(
	          String.format(String.valueOf(message), (Object[]) argStrings));
	    }
	  }
	}
