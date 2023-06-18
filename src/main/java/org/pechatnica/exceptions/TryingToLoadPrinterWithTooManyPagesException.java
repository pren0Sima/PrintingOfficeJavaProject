package org.pechatnica.exceptions;

public class TryingToLoadPrinterWithTooManyPagesException extends Exception {
    public TryingToLoadPrinterWithTooManyPagesException(String message) {
        super(message);
    }
}