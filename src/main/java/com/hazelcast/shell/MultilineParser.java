package com.hazelcast.shell;

import org.jline.reader.EOFError;
import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.SyntaxError;
import org.jline.reader.impl.DefaultParser;

/** A jline extension for SQL-like inputs. Commands are terminated with a semicolon. */
public final class MultilineParser implements Parser {

  private static final Parser DEFAULT_PARSER = new DefaultParser();

  @Override
  public ParsedLine parse(String line, int cursor, Parser.ParseContext context) throws SyntaxError {
    if ((Parser.ParseContext.UNSPECIFIED.equals(context) || Parser.ParseContext.ACCEPT_LINE.equals(context)) 
        && !line.trim().endsWith(";")) {
      throw new EOFError(-1, cursor, "Missing semicolon (;)");
    }

    return DEFAULT_PARSER.parse(line, cursor, context);
  }

  public MultilineParser() {}
}