package org.pechatnica.interfaces;

import org.pechatnica.printingStuff.Page;
import org.pechatnica.prints.Print;

import java.io.IOException;

public interface ReportServices {
    // for giving a title to the report
    public void writeReportTitle(String title) throws IOException;
    // for adding prints to the printedPrintsReport
    public void addPrintToPrintedPrintsReport(Print print, Page pageVariation) throws IOException;
    // for taking the contents of a file and putting them in another
    public void addPrintedPrintsReportToReport() throws IOException;
    // for adding the expenses and income to the file
    public void addExpensesIncomeAndProfitToReport() throws IOException;
    // constructing the report
    public void createReport(String reportTitle) throws IOException;
    // deleting the contents of a file
    public void deleteReport(String reportFileName) throws IOException;
    // reading the report file and printing it in the console
    public void printReport(String reportFileName) throws IOException;
}
