
package lexer;

import java.io.*;
import lexer.tokens.*;
import lexer.exceptions.*;

public class Lexer {
	private InputStreamReader rd; 

	private int cur;

	public Lexer(InputStream is) {
		this.rd = new InputStreamReader(is);

		this.cur = -1;
	}

	public Token getToken() throws	LexerEOFException,
	      				LexerParseException,
					IOException {
		LexerState st = LexerState.START;
		StringBuilder sb = null;

		if (this.cur == -1)
			this.cur = this.rd.read();

		while (true) {
			if (this.cur == -1)
				throw new LexerEOFException();

			switch (st) {
			case LexerState.START:
				sb = new StringBuilder();
				if (Character.isAlphabetic(cur) || cur == '_')
					st = LexerState.IDENTIFIER;
				else if (cur == ' ' || cur == '\n' ||
					cur == '\t' || cur == '\b' ||
					cur == '\r')
					st = LexerState.START;
				else
					throw new LexerParseException();
				break;
			case LexerState.IDENTIFIER:
				if (	Character.isLetterOrDigit(cur)
					|| cur == '_')
					st = LexerState.IDENTIFIER;
				else
					return this.solveIdentifier(sb.toString());
				break;
			default:	/* UNREACHABLE */
				throw new RuntimeException();
			}

			sb.append((char)cur);
			cur = this.rd.read();
		}
	}

	private Token solveIdentifier(String str) {
		switch (str) {
		case "if":
			return new UniToken(TokenType.IF);
		case "else":
			return new UniToken(TokenType.ELSE);
		default:
			return new MultiToken(TokenType.IDENTIFIER, str);
		}
	}
}
