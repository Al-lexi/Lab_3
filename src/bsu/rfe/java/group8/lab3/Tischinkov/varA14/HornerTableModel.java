package bsu.rfe.java.group8.lab3.Tischinkov.varA14;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
class HornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public HornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.setCoefficients(coefficients);
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

     public int getColumnCount() { // В данной модели два столбца
        return 3;
    }

    public int getRowCount() { // Вычислить количество точек между началом и концом отрезка исходя из шага табулирования
        return (int) Math.ceil((to - from) / step) + 1;
    }

    private boolean hasOnlyEvenDigitsInIntegerPart(double x) {
        long n = (long) Math.floor(x);
        if (n == 0) return true; //Handle 0 separately
        if (n < 0) n = -n; //Handle negative numbers

        while (n > 0) {
            if ((n % 10) % 2 != 0) return false; //Odd digit found
            n /= 10;
        }
        return true; //All digits are even
    }

    @Override
    public Object getValueAt(int row, int col) {
        double x = from + step * row;
        double polynomialValue = calculateHorner(x);
        return switch (col) {
            case 0 -> x;
            case 1 -> polynomialValue;
            case 2 -> hasOnlyEvenDigitsInIntegerPart(polynomialValue); // Use the new method
            default -> 0;
        };
    }

    public Class<?> getColumnClass(int col) {
        return switch (col) {
            case (2) -> Boolean.class;
            case (0), (1) -> Double.class;
            default -> Double.class;
        };
    }

    private double calculateHorner(double x) {
        Double b = coefficients[coefficients.length - 1];
        for (int i = coefficients.length - 2; i >= 0; i--) {
            b = b * x + coefficients[i];
        }
        return b;
    }

    public Double[] getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(Double[] coefficients) {
        this.coefficients = coefficients;
    }
}

