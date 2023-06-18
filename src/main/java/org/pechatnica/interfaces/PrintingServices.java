package org.pechatnica.interfaces;

import org.pechatnica.printingStuff.Printer;

public interface PrintingServices {
    public boolean addPrinter(Printer printer);
    public boolean removePrinter(Printer printer);
}
