package org.pechatnica.printingOfficeStuff;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pechatnica.enumerations.PageSize;
import org.pechatnica.enumerations.PaperType;
import org.pechatnica.enumerations.PrintType;
import org.pechatnica.printingStuff.Page;
import org.pechatnica.prints.Print;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PrintingOfficeTest {
    private PrintingOffice printingOffice;
    @BeforeEach
    void init(){
        // it initializes the storage as well; every value in it is 0.
        printingOffice = new PrintingOffice();
    }
    @Test
    @DisplayName("purchasePages supply test 0 pages")
    void purchaseZeroPagesTest() {
        Page page = new Page(PageSize.A1, PaperType.GLOSSY_PAPER);
        printingOffice.purchasePages(page, 0);
        int expected = 0;
        int actual = printingOffice.getPurchasedPages().get(page);
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("purchasePages supply test 5 pages from 1 type")
    void purchaseMorePagesTest() {
        Page page = new Page(PageSize.A1, PaperType.GLOSSY_PAPER);
        printingOffice.purchasePages(page, 5);
        int expected = 5;
        int actual = printingOffice.getPurchasedPages().get(page);
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("purchasePages supply test 5 pages from 2 types")
    void purchaseMorePageTypesTest() {
        Page page1 = new Page(PageSize.A1, PaperType.GLOSSY_PAPER);
        Page page2 = new Page(PageSize.A5, PaperType.STANDARD_PAPER);
        printingOffice.purchasePages(page1, 3);
        printingOffice.purchasePages(page2, 2);
        int expected = 5;
        int actual = printingOffice.getPurchasedPages().get(page1) + printingOffice.getPurchasedPages().get(page2);
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("purchasePages supply test 5 pages from 2 types")
    void purchaseMorePagesExpensesTest() {
        Page page = new Page(PageSize.A1, PaperType.GLOSSY_PAPER);
        int quantity = 3;
        printingOffice.purchasePages(page, quantity);
        BigDecimal expectedExpenses = page.getPagePrice().multiply(BigDecimal.valueOf(quantity));
        BigDecimal actualExpenses = printingOffice.getExpenses();
        assertEquals(expectedExpenses, actualExpenses);
    }

    @Test
    @DisplayName("takePagesFromInventory if pages in supply are enough")
    void takePagesFromInventoryEnoughInSupplyTest() {
        // purchase
        Page page = new Page(PageSize.A1, PaperType.GLOSSY_PAPER);
        int quantity = 3;
        printingOffice.purchasePages(page, quantity);
        // take 2 of them from inventory
        printingOffice.takePagesFromInventory(page, 2);
        // 1 left.
        int expected = 1;
        int actual = printingOffice.getPurchasedPages().get(page);
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("takePagesFromInventory if pages in supply are not enough")
    void takePagesFromInventoryNotEnoughInSupplyTest() {
        // purchase
        Page page = new Page(PageSize.A1, PaperType.GLOSSY_PAPER);
        int quantity = 3;
        printingOffice.purchasePages(page, quantity);
        // take 2 of them from inventory
        printingOffice.takePagesFromInventory(page, quantity + 1);
        // purchasing 1.
        int expected = 0;
        int actual = printingOffice.getPurchasedPages().get(page);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("returnPagesToInventory checking supply")
    void returnPagesToInventoryTest() {
        Page page = new Page(PageSize.A1, PaperType.GLOSSY_PAPER);
        int quantity = 5;
        printingOffice.returnPagesToInventory(page, quantity);
        int expectedNumberOfPages = 5;
        int actualNumberOfPages = printingOffice.getPurchasedPages().get(page);
        assertEquals(expectedNumberOfPages, actualNumberOfPages);
    }
    @Test
    @DisplayName("calculatePrintPriceWithOvercharge")
    void calculatePrintPriceWithOverchargeTest(){
        Print print = new Print("The New York Times", 14, PageSize.A5, PrintType.NEWSPAPER);
        Page pageVariation = new Page(PageSize.A5, PaperType.NEWSPAPER_PAPER);
        // we need to get the overcharge per print from PrintingOffice (10)
        printingOffice.setPercentOverchargePerPrint(BigDecimal.valueOf(10));
        // pages*(size+type)*(overcharge+100)/100 = 14*(0.01+0.3)*110/100 = 4,774
        BigDecimal expectedPriceWithOvercharge = BigDecimal.valueOf(4.774);
        BigDecimal actualPriceWithOvercharge = printingOffice.calculatePrintPriceWithOvercharge(print, pageVariation);
        assertEquals(expectedPriceWithOvercharge, actualPriceWithOvercharge);
    }
}