package parsing.model;

import essentials.contract.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Creator: Patrick
 * Created: 26.03.2019
 * TODO: On an error, you should also get the class that was responsible.
 * TODO: Delegate the error related code into a separate "Parse Error" class.
 */
public final class ParseResult {
    private final int _cursorPosition;
    private final String _message;
    private final boolean _isValid;
    private final List<ParseResult> _innerErrors;
    private final Class<? extends ParseNode> _errorSource;

    public ParseResult(int cursorPosition, String message, boolean isValid) {
        this(cursorPosition, message, isValid, new ArrayList<>(), null);
    }

    public ParseResult(
        int cursorPosition,
        String message,
        boolean isValid, Class<? extends ParseNode> errorSource
    ) {
        this(cursorPosition, message, isValid, new ArrayList<>(), errorSource);
    }

    public ParseResult(int cursorPosition,
                       String message,
                       boolean isValid,
                       Collection<ParseResult> innerErrors,
                       Class<? extends ParseNode> errorSource
    ) {
        _cursorPosition = cursorPosition;
        _message = message;
        _isValid = isValid;
        _innerErrors = new ArrayList<>(innerErrors);
        _errorSource = errorSource;
    }

    public List<ParseResult> innerErrors() {
        return _innerErrors;
    }

    public void ifValid(Runnable runnable) {
        if (isValid()) runnable.run();
    }

    public boolean isValid() {
        return _isValid;
    }

    public boolean isInvalid() {
        return !_isValid;
    }

    public String getMessage() {
        if (_isValid) {
            throw new IllegalStateException("Cannot instance message because cursor is at a valid position.");
        }

        return _message;
    }

    public int index() {
        if (!_isValid) {
            throw new IllegalStateException("Cannot instance position of an invalid cursor.");
        }

        return _cursorPosition;
    }

    public static ParseResult invalid(int index, @NotNull String message, ParseNode source) {
        return invalid(index, message, Collections.emptyList(), source);
    }

    public static ParseResult invalid(
        int index, @NotNull String message, Collection<ParseResult> innerExceptions, ParseNode errorSource
    ) {
        Contract.checkNull(message, "message");

        return new ParseResult(index, message, false, innerExceptions, errorSource.getClass());
    }

    public static ParseResult invalid(
        int index, @NotNull String message,
        ParseNode errorSource, ParseResult... innerExceptions
    ) {
        return invalid(index, message, Arrays.asList(innerExceptions), errorSource);
    }

    public Class<? extends ParseNode> getErrorSource() {
        return _errorSource;
    }

    public static ParseResult notMatch(int index, char expected, char actual, ParseNode errorSource) {
        return notMatch(index, Character.toString(expected), Character.toString(actual), errorSource);
    }

    public static ParseResult notMatch(int index, String expected, String actual, ParseNode errorSource) {
        // If this ever throws, consider turning input nulls into string nulls "null".
        Contract.checkNull(expected, actual);
        String message = "Failed to match expected \"" + expected + "\" with \"" + actual + "\"";

        return new ParseResult(index, message, false, errorSource.getClass());
    }

    public static ParseResult at(int cursor) {
        Contract.checkNegative(cursor);
        return new ParseResult(cursor, null, true);
    }

    @Override
    public String toString() {
        return isValid() ? "Index: " + _cursorPosition : "Invalid for reason: " + _message;
    }
}
