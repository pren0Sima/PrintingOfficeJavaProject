package org.pechatnica;

import org.pechatnica.employees.Employee;
import org.pechatnica.employees.Manager;
import org.pechatnica.employees.Operator;
import org.pechatnica.enumerations.PageSize;
import org.pechatnica.enumerations.PaperType;
import org.pechatnica.enumerations.PrintType;
import org.pechatnica.exceptions.PrinterLoadedWithWrongPaper;
import org.pechatnica.exceptions.PrinterNotCompatibleByColor;
import org.pechatnica.exceptions.PrinterNotLoadedWithEnoughPaperException;
import org.pechatnica.exceptions.TryingToLoadPrinterWithTooManyPagesException;
import org.pechatnica.printingOfficeStuff.PrintingOffice;
import org.pechatnica.printingOfficeStuff.Report;
import org.pechatnica.printingStuff.Page;
import org.pechatnica.printingStuff.Printer;
import org.pechatnica.prints.Print;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // 1. Create a set of employees ======================================================================
            // 1.1. Create the employees
        Manager manager1 = new Manager("Stevie the first", BigDecimal.valueOf(10));
        Manager manager2 = new Manager("Samantha", BigDecimal.valueOf(15));
        Operator operator1 = new Operator("Jeremy");
        Operator operator2 = new Operator("Joji the singer");
        Operator operator3 = new Operator("Daria the Ukrainian");

            // 1.2. Create a set
        Set<Employee> employees = new HashSet<>();
            // 1.3. Put the employees in the set
        employees.add(manager1);
        employees.add(manager2);
        employees.add(operator1);
        employees.add(operator2);
        employees.add(operator3);

            // 1.4. Set the salary
        Employee.setSalary(BigDecimal.valueOf(1000));
            // 1.5. Display all employees
        System.out.println("All employees:");
        employees.forEach(System.out::println);

        // 2. Create prints for printing =====================================================================
        Print book = new Print("Alice in Wonderland", 200, PageSize.A5, PrintType.BOOK);
        Print poster = new Print("Freddie Mercury", 1, PageSize.A2, PrintType.POSTER);
        Print newspaper = new Print("The New York Times", 18, PageSize.A1, PrintType.NEWSPAPER);

        // 3. Create printers ================================================================================
        Printer bookPrinter = new Printer(1000, true, 20);
        Printer posterPrinter = new Printer(500, true, 10);
        Printer newspaperPrinter = new Printer(500, false, 15);
            // 3.1. Add them to a set
        Set<Printer> printers = new HashSet<>();
        printers.add(bookPrinter);
        printers.add(posterPrinter);
        printers.add(newspaperPrinter);
        System.out.println("\nAll printers:");
        printers.forEach(System.out::println);

        // 4. Create a Printing office =======================================================================
        PrintingOffice printingOffice = new PrintingOffice(employees, printers,
                BigDecimal.valueOf(20000), BigDecimal.valueOf(10),
                10, BigDecimal.valueOf(20));
        // we specify that the printers are in this printing office
        printers.forEach(printer -> printer.setPrintingOffice(printingOffice));
        System.out.println("\nPrinting office:\n" + printingOffice);
        System.out.println("\nPage inventory: \n" + printingOffice.getPurchasedPages());

        // 5. Prepare the files for the reports ==============================================================
        printingOffice.setPrintingOfficeReport(new Report("report.txt"));
        printingOffice.setPrintedPrintsReport(new Report("printedPrints.txt"));
        // if the printedPrints report already exists, delete it
        try {
            printingOffice.deleteReport("printedPrints.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 6. Purchase pages =================================================================================
        // for the book
        printingOffice.purchasePages(new Page(PageSize.A5, PaperType.STANDARD_PAPER), 200);
        // for the poster
        printingOffice.purchasePages(new Page(PageSize.A2, PaperType.GLOSSY_PAPER), 1);
        // for the newspaper
        printingOffice.purchasePages(new Page(PageSize.A1, PaperType.NEWSPAPER_PAPER), 10);
        System.out.println("\nPage inventory after purchasing pages: \n" +printingOffice.getPurchasedPages());

        // 7. Loading printers with paper ====================================================================
            // 7.1. Setting the page variations we will use for printing
                // for the book:
        Page bookPageVariation = new Page(PageSize.A5, PaperType.STANDARD_PAPER);
                // for the poster:
        Page posterPageVariation = new Page(PageSize.A2, PaperType.GLOSSY_PAPER);
                // for the newspaper:
        Page newspaperPageVariation = new Page(PageSize.A1, PaperType.NEWSPAPER_PAPER);
            // 7.2. Setting the page variations for each printer
        bookPrinter.setPageVariationLoaded(bookPageVariation);
        posterPrinter.setPageVariationLoaded(posterPageVariation);
        newspaperPrinter.setPageVariationLoaded(newspaperPageVariation);
            // 7.3. The actual loading
        try {
            bookPrinter.loadPrinterWithPages(bookPageVariation, 200);
            posterPrinter.loadPrinterWithPages(posterPageVariation, 1);
            newspaperPrinter.loadPrinterWithPages(newspaperPageVariation, 18);
        } catch (TryingToLoadPrinterWithTooManyPagesException e) {
            System.err.println("An exception was thrown while trying to load the printer with pages.");
        }

        // 8. Print the prints ===============================================================================
        try {
            bookPrinter.letPrinterPrint(book, bookPageVariation, true);
            posterPrinter.letPrinterPrint(poster, posterPageVariation, true);
            newspaperPrinter.letPrinterPrint(newspaper, newspaperPageVariation, false);
        } catch (PrinterNotLoadedWithEnoughPaperException | PrinterLoadedWithWrongPaper
                 | PrinterNotCompatibleByColor | IOException e) {
            System.err.println("An error occurred during printing: " + e);
        }

        // 9. Printing the income of the printing office =====================================================
        System.out.println("Price for PageSize.A5 + PaperType.STANDARD_PAPER : "
                + PageSize.A5.getSheetPrice().add(PaperType.STANDARD_PAPER.getPaperTypePrice()));
        System.out.println("Price for the book without overcharge and discount : "
                + PageSize.A5.getSheetPrice().add(PaperType.STANDARD_PAPER.getPaperTypePrice())
                .multiply(BigDecimal.valueOf(book.getNumberOfPages())));
        System.out.println("Price for the printed book including overcharge and discount : "
                + printingOffice.calculatePrintPriceWithOverchargeAndDiscount(book, bookPageVariation));
        System.out.println("Price for the printed poster including overcharge and discount : "
                + printingOffice.calculatePrintPriceWithOverchargeAndDiscount(poster, posterPageVariation));
        System.out.println("Price for the printed newspaper including overcharge and discount : "
                + printingOffice.calculatePrintPriceWithOverchargeAndDiscount(newspaper, newspaperPageVariation));
        System.out.println("Income (including the unwanted remained from customers :) : "
                + printingOffice.getIncome());
        System.out.println();

        // 10. Making the report =============================================================================
        try {
            printingOffice.createReport("Printing Office report: \n");
        } catch (IOException e) {
            System.err.println("An I/O exception because of the printedPrintsReport.");
        }
        // printing the report onto the console
        try {
            printingOffice.printReport("report.txt");
        } catch (IOException e) {
            System.err.println("An I/O exception because of the final report.");
        }
    }
}