package org.pechatnica.interfaces;

import java.math.BigDecimal;

public interface Accounting {
    public BigDecimal addToExpenses(BigDecimal amount);
    public BigDecimal addToIncome(BigDecimal amount);
    public BigDecimal calculateProfit();
}
