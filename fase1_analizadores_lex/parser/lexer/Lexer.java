
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
			boolean discard = false;
			switch (st) {
			case LexerState.START:
				sb = new StringBuilder();
				if (Character.isAlphabetic(cur) || cur == '_')
					st = LexerState.REC_IDENTIFIER;
				else if (cur == '0')
					st = LexerState.REC_INTEGER;
				else if (Character.isDigit(cur))
					st = LexerState.REC_INTEGER;
				else if (cur == ' ' || cur == '\n' ||
					cur == '\t' || cur == '\b' ||
					cur == '\r')
					st = LexerState.START;
				else if (cur == -1)
					throw new LexerEOFException();
				else if (cur == '+')
					st = LexerState.REC_PLUS;
				else if (cur == '-')
					st = LexerState.REC_MINUS;
				else if (cur == '#')
					st = LexerState.REC_ALMOADILLA;
				else if (cur == '=')
					st = LexerState.REC_EQUAL;
				else if (cur == '>')
					st = LexerState.REC_GREATER;
				else if (cur == '<')
					st = LexerState.REC_LESS;
				else if (cur == '!')
					st = LexerState.REC_EXCLAMATION;
				else if (cur == '&')
					st = LexerState.REC_ET;
				else if (cur == '*')
					st = LexerState.REC_MULTIPLY;
				else if (cur == '/')
					st = LexerState.REC_FORWARD_SLASH;
				else if (cur == '(')
					st = LexerState.REC_PAREN_OPEN;
				else if (cur == ')')
					st = LexerState.REC_PAREN_CLOSE;
				else if (cur == '{')
					st = LexerState.REC_BRACE_OPEN;
				else if (cur == '}')
					st = LexerState.REC_BRACE_CLOSE;
				else if (cur == ';')
					st = LexerState.REC_SEMICOLON;
				else if (cur == '@')
					st = LexerState.REC_ARROBA;
				else
					throw new LexerParseException();
				break;
			case LexerState.REC_EQUAL:
				if (cur == '=')
					st = LexerState.REC_EQUAL_EQUAL;
				else
					return new UniToken(TokenType.ASSIGN);
				break;
			case LexerState.REC_GREATER:
				if (cur == '=')
					st = LexerState.REC_GREATER_EQ;
				else
					return new UniToken(TokenType.GREATER);
				break;
			case LexerState.REC_LESS:
				if (cur == '=')
					st = LexerState.REC_LESS_EQ;
				else
					return new UniToken(TokenType.LESS);
				break;
			case LexerState.REC_EXCLAMATION:
				if (cur == '=')
					st = LexerState.REC_NOT_EQ;
				else
					throw new LexerParseException();
				break;
			case LexerState.REC_ET:
				if (cur == '=')
					st = LexerState.REC_ET_ET;
				else
					throw new LexerParseException();
				break;
			case LexerState.REC_ALMOADILLA:
				if (cur == '#')
					st = LexerState.REC_COMMENT;
				else
					throw new LexerParseException();
				break;
			case LexerState.REC_COMMENT:
				if (cur == '\n') {
					st = LexerState.START;
					discard = true;
				} else if (cur == -1)
					throw new LexerEOFException();
				else
					st = LexerState.REC_COMMENT;
				break;
			case LexerState.REC_IDENTIFIER:
				if (Character.isLetterOrDigit(cur) || cur == '_')
					st = LexerState.REC_IDENTIFIER;
				else
					return this.solveIdentifier(sb.toString());
				break;
			case LexerState.REC_EQ_EQ:
				return new UniToken(TokenType.EQUALS);
			case LexerState.REC_GREATER_EQ:
				return new UniToken(TokenType.GREATER_EQ);
			case LexerState.REC_LESS_EQ:
				return new UniToken(TokenType.LESS_EQ);
			case LexerState.REC_NOT_EQ:
				return new UniToken(TokenType.NOT_EQ);
			case LexerState.REC_ET_ET:
				return new UniToken(TokenType.ENDDECL);
			case LexerState.REC_INTEGER:
				if (Character.isDigit(cur))
					st = LexerState.REC_INTEGER;
				else if (cur == '.')
					st  = LexerState.REC_DOT;
				else if (cur == 'e' || cur == 'E')
					st = LexerState.REC_E;
				else
					return new MultiToken(TokenType.INTEGER, sb.toString());
				break;
			case LexerState.REC_MINUS:
				if (cur == '0')
					st = LexerState.REC_ZERO;
				else if (Character.isDigit(cur))
					st = LexerState.REC_INTEGER;
				else
					return new UniToken(TokenType.MINUS);
				break;
			case LexerState.REC_PLUS:
				if (cur == '0')
					st = LexerState.REC_ZERO;
				else if (Character.isDigit(cur))
					st = LexerState.REC_INTEGER;
				else
					return new UniToken(TokenType.PLUS);
				break;
			case LexerState.REC_ZERO:
				if (cur == 'e' || cur == 'E')
					st = LexerState.REC_E;
				else
					return new MultiToken(TokenType.INTEGER, sb.toString());
				break;
			case LexerState.REC_DOT:
				if (Character.isDigit(cur))
					st = LexerState.REC_DECIMAL;
				else
					throw new LexerParseException();
				break;
			case LexerState.REC_DECIMAL:
				if (cur == '0')
					st = LexerState.REC_DECIMAL_ZERO;
				else if (Character.isDigit(cur))
					st = LexerState.REC_DECIMAL;
				else if (cur == 'e' || cur == 'E')
					st = LexerState.REC_E;
				else
					return new MultiToken(TokenType.REAL, sb.toString());
				break;
			case LexerState.REC_DECIMAL_ZERO:
				if (cur == '0')
					st = LexerState.REC_DECIMAL_ZERO;
				else if (Character.isDigit(cur))
					st = LexerState.REC_DECIMAL;
				else
					return new MultiToken(TokenType.REAL, sb.toString());
				break;
			case LexerState.REC_E:
				if (cur == '0')
					st = LexerState.REC_E_ZERO;
				else if (Character.isDigit(cur))
					st = LexerState.REC_E_POST;
				else if (cur == '-' || cur == '+')
					st = LexerState.REC_E_ANT;
				else
					throw new LexerParseException();
				break;
			case LexerState.REC_E_ZERO:
				return new MultiToken(TokenType.REAL, sb.toString());
			case LexerState.REC_E_ANT:
				if (cur == '0')
					st = LexerState.REC_E_ZERO;
				else if (Character.isDigit(cur))
					st = LexerState.REC_E_POST;
				else
					throw new LexerParseException();
				break;
			case LexerState.REC_E_POST:
				if (Character.isDigit(cur))
					st = LexerState.REC_E_POST;
				else
					return new MultiToken(TokenType.REAL, sb.toString());
				break;
			default:	/* UNREACHABLE */
				throw new RuntimeException();
			}

			if (discard)
				sb = new StringBuilder();
			else
				sb.append((char)cur);

			cur = this.rd.read();
		}
	}

	private Token solveIdentifier(String str) {
		switch (str.toLowerCase()) {
		case "bool":
			return new UniToken(TokenType.BOOL);
		case "int":
			return new UniToken(TokenType.INT);
		case "real":
			return new UniToken(TokenType.REAL);
		case "and":
			return new UniToken(TokenType.AND);
		case "or":
			return new UniToken(TokenType.OR);
		case "not":
			return new UniToken(TokenType.NOT);
		case "true":
			return new UniToken(TokenType.TRUE);
		case "false":
			return new UniToken(TokenType.FALSE);
		default:
			return new MultiToken(TokenType.IDENTIFIER, str);
		}
	}
}
