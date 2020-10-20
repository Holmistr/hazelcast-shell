package com.hazelcast.shell;

import org.jline.reader.EOFError;
import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.SyntaxError;
import org.jline.reader.impl.DefaultParser;

/** A jline extension for SQL-like inputs. Commands are terminated with a semicolon. */
public final class MyParser implements Parser {

  private static final Parser DEFAULT_PARSER = new DefaultParser();

  private boolean printHi = false;

  @Override
  public ParsedLine parse(String line, int cursor, ParseContext context) throws SyntaxError {
    if (printHi)
      System.out.println("Printing hi");
    else
      System.out.println("Not printing hi");

    return DEFAULT_PARSER.parse(line, cursor, context);
  }

  public MyParser() {}

  public void setPrintHi(boolean printHi) {
    this.printHi = printHi;
  }
}