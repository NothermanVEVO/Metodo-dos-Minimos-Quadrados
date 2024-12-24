package Graphic;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Output {
    
    public Output(double[] x, double[] y, double[] ajuste){
        XYSeries pontos = new XYSeries("Pontos Originais");
        for (int i = 0; i < x.length; i++) {
            pontos.add(x[i], y[i]);
        }

        XYSeries curva = new XYSeries("Ajuste " + Input.adjustComboBox.getSelectedItem());

        for (double xi = x[0]; xi <= x[x.length - 1]; xi += 0.1) {
            curva.add(xi, calcularPolinomio(ajuste, xi));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(pontos);
        dataset.addSeries(curva);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Ajuste por Mínimos Quadrados",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        JFrame frame = new JFrame("Gráfico de Ajuste");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static double[] ajustarPolinomio(double[] x, double[] y, int grau) {
        int n = x.length;
        int m = grau + 1;

        double[][] A = new double[m][m];
        double[] B = new double[m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                A[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    A[i][j] += Math.pow(x[k], i + j);
                }
            }

            B[i] = 0;
            for (int k = 0; k < n; k++) {
                B[i] += y[k] * Math.pow(x[k], i);
            }
        }

        return resolverSistemaLinear(A, B);
    }

    public static double[] resolverSistemaLinear(double[][] A, double[] B) {
        int n = B.length;
        double[] coef = new double[n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double fator = A[j][i] / A[i][i];
                for (int k = i; k < n; k++) {
                    A[j][k] -= fator * A[i][k];
                }
                B[j] -= fator * B[i];
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            coef[i] = B[i];
            for (int j = i + 1; j < n; j++) {
                coef[i] -= A[i][j] * coef[j];
            }
            coef[i] /= A[i][i];
        }

        return coef;
    }

    public static double calcularPolinomio(double[] ajuste, double x) {
            double resultado = 0;
            for (int i = 0; i < ajuste.length; i++) {
                resultado += ajuste[i] * Math.pow(x, i);
        }
        return resultado;
    }

}
