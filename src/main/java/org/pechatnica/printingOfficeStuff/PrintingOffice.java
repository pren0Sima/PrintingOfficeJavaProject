package org.pechatnica.printingOfficeStuff;

import org.pechatnica.employees.Employee;
import org.pechatnica.employees.Manager;
import org.pechatnica.enumerations.PageSize;
import org.pechatnica.enumerations.PaperType;
import org.pechatnica.interfaces.*;
import org.pechatnica.printingStuff.Page;
import org.pechatnica.printingStuff.Printer;
import org.pechatnica.prints.Print;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PrintingOffice extends PageInventory implements EmployeeServices, PrintingServices, PageServices, Accounting, ReportServices {
    private BigDecimal expenses;
    private BigDecimal income;
    private Set<Employee> employeesSet;
    private BigDecimal minimalIncomeForBonuses;
    private Set<Printer> printersSet;
    private BigDecimal percentDiscount;
    private int numberOfPrintedPrints;
    private int numbersOfPrintedPrintsForDiscount;
    private BigDecimal percentOverchargePerPrint;
    private Report printedPrintsReport;
    private Report printingOfficeReport;
    public PrintingOffice(Set<Employee> employeeSet, Set<Printer> printersSet,
                          BigDecimal minimalIncomeForBonuses, BigDecimal percentDiscount,
                          int numbersOfPrintedPrintsForDiscount, BigDecimal percentOverchargePerPrint) {
        this.numbersOfPrintedPrintsForDiscount = numbersOfPrintedPrintsForDiscount;
        this.percentOverchargePerPrint = percentOverchargePerPrint;
        this.expenses = BigDecimal.ZERO;
        this.income = BigDecimal.ZERO;
        this.employeesSet = employeeSet;
        this.minimalIncomeForBonuses = minimalIncomeForBonuses;
        this.printersSet = printersSet;
        purchasedPagesInit();
        this.percentDiscount = percentDiscount;
        this.printedPrintsReport = null;
    }

    public PrintingOffice() {
        this.expenses = BigDecimal.ZERO;
        this.income = BigDecimal.ZERO;
        this.employeesSet = new HashSet<>();
        this.printersSet = new HashSet<>();
        this.minimalIncomeForBonuses = BigDecimal.ZERO;
        purchasedPagesInit();
        this.percentDiscount = BigDecimal.ZERO;
    }

    public BigDecimal getExpenses() {
        return expenses;
    }

    public void setExpenses(BigDecimal expenses) {
        this.expenses = expenses;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Set<Employee> getEmployeesSet() {
        return employeesSet;
    }
    public void setEmployeesSet(Set<Employee> employeesSet) {
        this.employeesSet = employeesSet;
    }

    public BigDecimal getMinimalIncomeForBonuses() {
        return minimalIncomeForBonuses;
    }

    public void setMinimalIncomeForBonuses(BigDecimal minimalIncomeForBonuses) {
        this.minimalIncomeForBonuses = minimalIncomeForBonuses;
    }

    public Set<Printer> getPrintersSet() {
        return printersSet;
    }

    public void setPrintersSet(Set<Printer> printersSet) {
        this.printersSet = printersSet;
    }

    public BigDecimal getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(BigDecimal percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public int getNumberOfPrintedPrints() {
        return numberOfPrintedPrints;
    }

    public void setNumberOfPrintedPrints(int numberOfPrintedPrints) {
        this.numberOfPrintedPrints = numberOfPrintedPrints;
    }

    public int getNumbersOfPrintedPrintsForDiscount() {
        return numbersOfPrintedPrintsForDiscount;
    }

    public void setNumbersOfPrintedPrintsForDiscount(int numbersOfPrintedPrintsForDiscount) {
        this.numbersOfPrintedPrintsForDiscount = numbersOfPrintedPrintsForDiscount;
    }

    public BigDecimal getPercentOverchargePerPrint() {
        return percentOverchargePerPrint;
    }

    public void setPercentOverchargePerPrint(BigDecimal percentOverchargePerPrint) {
        this.percentOverchargePerPrint = percentOverchargePerPrint;
    }
    public Report getPrintedPrintsReport() {
        return printedPrintsReport;
    }

    public void setPrintedPrintsReport(Report printedPrintsReport) {
        this.printedPrintsReport = printedPrintsReport;
    }

    public Report getPrintingOfficeReport() {
        return printingOfficeReport;
    }

    public void setPrintingOfficeReport(Report report) {
        this.printingOfficeReport = report;
    }

    // from PageServices interface
    @Override
    public void purchasedPagesInit() {
        HashMap<Page, Integer> map = new HashMap<>(15);
        Page[] pages = {
                // Standard
                new Page(PageSize.A1, PaperType.STANDARD_PAPER),
                new Page(PageSize.A2, PaperType.STANDARD_PAPER),
                new Page(PageSize.A3, PaperType.STANDARD_PAPER),
                new Page(PageSize.A4, PaperType.STANDARD_PAPER),
                new Page(PageSize.A5, PaperType.STANDARD_PAPER),
                // Glossy
                new Page(PageSize.A1, PaperType.GLOSSY_PAPER),
                new Page(PageSize.A2, PaperType.GLOSSY_PAPER),
                new Page(PageSize.A3, PaperType.GLOSSY_PAPER),
                new Page(PageSize.A4, PaperType.GLOSSY_PAPER),
                new Page(PageSize.A5, PaperType.GLOSSY_PAPER),
                // Newspaper
                new Page(PageSize.A1, PaperType.NEWSPAPER_PAPER),
                new Page(PageSize.A2, PaperType.NEWSPAPER_PAPER),
                new Page(PageSize.A3, PaperType.NEWSPAPER_PAPER),
                new Page(PageSize.A4, PaperType.NEWSPAPER_PAPER),
                new Page(PageSize.A5, PaperType.NEWSPAPER_PAPER)
        };
        // adding all pages to the map and setting the keys(amount) to 0
        Arrays.stream(pages).forEach(page -> map.put(page, 0));
        this.purchasedPages = map;
    }
    @Override
    public void purchasePages(Page page, int quantity) {
        if (purchasedPages.isEmpty()){
            purchasedPagesInit();
        }
        // we get the current quantity of pages
        int currentQuantity = this.purchasedPages.get(page);
        // we change the value associated with the key=page
        this.purchasedPages.replace(page, (currentQuantity + quantity));
        // add to expenses
        this.addToExpenses(page.getPagePrice().multiply(BigDecimal.valueOf(quantity)));
    }

    @Override
    public void takePagesFromInventory(Page page, int quantity) {
        if(purchasedPages.isEmpty()){
            purchasedPagesInit();
        }
        int currentQuantity = this.purchasedPages.get(page);
        // not enough pages in inventory
        if (currentQuantity < quantity) {
            // purchase more pages
            purchasePages(page, quantity - currentQuantity);
            this.purchasedPages.replace(page, 0);
        } else {
            // take the needed pages
            this.purchasedPages.replace(page, currentQuantity - quantity);
        }
    }

    @Override
    public void returnPagesToInventory(Page page, int quantity) {
        if (purchasedPages.isEmpty()){
            purchasedPagesInit();
        }
        int currentQuantity = this.purchasedPages.get(page);
        this.purchasedPages.replace(page, (currentQuantity + quantity));
    }

    // from the EmployeeServices interface:
    @Override
    public boolean hireEmployee(Employee employee) {
        return employeesSet.add(employee);
    }
    @Override
    public boolean fireEmployee(Employee employee) {
        return employeesSet.remove(employee);
    }

    @Override
    public void payEmployee(Employee employee) {
        if (employee instanceof Manager && income.compareTo(minimalIncomeForBonuses) >= 0) {
            expenses = expenses.add(employee.getSalary())
                    .add(employee.getSalary()
                            .multiply(((Manager) employee)
                                    .getPercentageAdditionalPay()
                                    .divide(BigDecimal.valueOf(100))));
        }
        else {
            expenses = expenses.add(employee.getSalary());
        }
    }

    // from the PrintingServices interface:
    @Override
    public boolean addPrinter(Printer printer) {
        boolean helper = printersSet.add(printer);
        if(helper) {
            printer.setPrintingOffice(this);
        }
        return helper;
    }

    @Override
    public boolean removePrinter(Printer printer) {
        boolean helper = printersSet.remove(printer);
        if(helper) {
            printer.setPrintingOffice(null);
        }
        return helper;
    }

    // form Accounting interface:
    @Override
    public BigDecimal addToExpenses(BigDecimal amount) {
        return this.expenses = this.expenses.add(amount);
    }

    @Override
    public BigDecimal addToIncome(BigDecimal amount) {
        return this.income = this.income.add(amount);
    }

    @Override
    public BigDecimal calculateProfit() {
        return this.income.subtract(this.expenses);
    }

    // from here:
    public void payAllEmployees(){
        employeesSet.forEach(employee -> this.payEmployee(employee));
    }

    public BigDecimal calculatePrintPriceWithOvercharge(Print print, Page pageVariation){
                        // pages*PageSizePrice
        return print.calculatePriceByPagesAndPageSize()
                        // +
                .add(BigDecimal.valueOf(print.getNumberOfPages())
                        // pages*PaperTypePrice
                .multiply(pageVariation.getPaperType().getPaperTypePrice()))
                        // * (x + 100)%
                .multiply(getPercentOverchargePerPrint().add(BigDecimal.valueOf(100)))
                .divide(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }
    public BigDecimal calculatePrintPriceWithOverchargeAndDiscount(Print print, Page pageVariation){
       if(this.numbersOfPrintedPrintsForDiscount == this.numberOfPrintedPrints){
            this.numberOfPrintedPrints = 0;
            return calculatePrintPriceWithOvercharge(print, pageVariation)
                    .subtract(calculatePrintPriceWithOvercharge(print, pageVariation)
                            .multiply(getPercentDiscount()).divide(BigDecimal.valueOf(100)))
                    .setScale(2, RoundingMode.HALF_UP);

       } else {
           this.numberOfPrintedPrints++;
           return calculatePrintPriceWithOvercharge(print, pageVariation)
                   .setScale(2, RoundingMode.HALF_UP);
        }
    }
    // from ReportServices
    @Override
    public void addPrintToPrintedPrintsReport(Print print, Page pageVariation) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(printedPrintsReport.getReportName(), true));
        writer.write("\n" + print.getTitle() + " - " + this.calculatePrintPriceWithOverchargeAndDiscount(print, pageVariation));
        // if we don't close it, the file won't have anything in it
        writer.close();
    }
    @Override
    public void writeReportTitle(String title) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(getPrintingOfficeReport().getReportName(), true));
        writer.write(title);
        writer.close();
    }

    @Override
    public void addPrintedPrintsReportToReport() throws IOException {
        // adding a title
        writeReportTitle("\nPrinted prints: ");
        // creating the objects for the I/O streams
        // source
        BufferedReader reader = new BufferedReader(new FileReader(getPrintedPrintsReport().getReportName()));
        // destination (the content will be appended)
        BufferedWriter writer = new BufferedWriter(new FileWriter(getPrintingOfficeReport().getReportName(), true));
        // copying the contents from the source to the destination
        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.newLine();
        }
        // closing the streams
        reader.close();
        writer.close();
    }

    @Override
    public void addExpensesIncomeAndProfitToReport() throws IOException {
        // opening the stream
        BufferedWriter writer = new BufferedWriter(new FileWriter(getPrintingOfficeReport().getReportName(), true));
        // expenses
        writer.write("\nExpenses: " + this.expenses);
        // income
        writer.write("\nIncome: " + this.income);
        // profit
        writer.write("\nProfit: " + this.calculateProfit());
        // closing the stream
        writer.close();
    }

    @Override
    public void deleteReport(String reportFileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(reportFileName));
        writer.write("");
        writer.close();
    }

    @Override
    public void createReport(String reportTitle) throws IOException {
        // if we have anything in the report file, delete it.
        deleteReport(getPrintingOfficeReport().getReportName());
        // adding the report title
        writeReportTitle(reportTitle);
        // adding the printed prints
        addPrintedPrintsReportToReport();
        // adding the expenses and income
        addExpensesIncomeAndProfitToReport();
    }

    @Override
    public void printReport(String reportFileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(reportFileName));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    @Override
    public String toString() {
        return "PrintingOffice{" +
                "expenses=" + expenses +
                ", income=" + income +
                ", employeesSet=" + employeesSet +
                ", minimalIncomeForBonuses=" + minimalIncomeForBonuses +
                 ", printersSet=" + printersSet + "} ";
    }
}
