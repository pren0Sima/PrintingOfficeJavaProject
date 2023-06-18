package org.pechatnica.exceptions;

public class PrinterLoadedWithWrongPaper extends Exception {
    public PrinterLoadedWithWrongPaper(String message) {
        super(message);
    }
}
