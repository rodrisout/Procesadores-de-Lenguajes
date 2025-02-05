
package lexer.tokens;

public abstract class Token {
	TokenType type;
	String content;

	Token(TokenType t, String c) {
		this.type = t;
		this.content = c;
	}

	public String toString() {
		return this.content;
	}
}
