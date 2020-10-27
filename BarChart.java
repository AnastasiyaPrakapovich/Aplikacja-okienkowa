package app;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

/**
 * Program <code>app.MyWindow</code>
 * The class <code>app.BarChart</code> describing
 * the creation of a graph.
 * @author Anastasiya Prakapovich
 * @version 1.0	1/05/2020
 */
public class BarChart extends JDialog {
    private IntegerTableModel tableModel;
    private Dimension histogramSize = null;
    private JFreeChart chart;
    private DefaultCategoryDataset dataset;

    /**
     * Constructor with 1 param of Histogram class.
     * @param h model from Center Panel table.
     */
    public BarChart(IntegerTableModel h) {
        tableModel = h;
        this.setTitle("Histogram pionowy");
        this.setModal(true);
        this.setSize(520, 340);
        this.setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setBackground(Color.white);
        contentPane.setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            // obsluga wcisniecia przycisku zamkniecia okna
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });
        setStartLocation();
        createHistogram();
    }

    /**
     * The method that creates Bar chart.
     */
    private void createHistogram() {
        chart = createChartGUI();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 200));
        setContentPane(chartPanel);
        chartPanel.setVisible(true);
    }

    /**
     * The method that creates GUI of Bar chart and returns it.
     * @return JFreeChart object.
     */
    private JFreeChart createChartGUI() {
        setData();
        chart = ChartFactory.createBarChart(
                "Histogram  pionowy", "Position", "Value",
                dataset, PlotOrientation.VERTICAL, true, false, false);
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.DARK_GRAY);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.15);
        return chart;
    }

    /**
     * Method that takes data from table model and seeds to dataset.
     */
    private void setData() {
        dataset = new DefaultCategoryDataset();
        for (int i = 0; i < tableModel.getRowCount(); i++)
            for (int j = 0; j < tableModel.getColumnCount(); j++)
                dataset.addValue((Number) tableModel.getValueAt(i, j), "Row " + i, "Column " + j);
    }

    /**
     * Method that sets start location of the application to the center of screen.
     */
    private void setStartLocation() {
        // pobranie rozmiarow aplikacji
        histogramSize = getSize();
        // pobranie rozdzielczosci pulpitu
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (histogramSize.height > screenSize.height)
            histogramSize.height = screenSize.height;
        if (histogramSize.width > screenSize.width)
            histogramSize.height = screenSize.width;
        // rozmieszczenie aplikacji na srodku ekranu
        setLocation((screenSize.width - histogramSize.width) / 2,
                (screenSize.height - histogramSize.height) / 2);
    }

    /**
     * Method that creates image from the graph.
     * @throws IOException if there are some errors with IO.
     */
    public void saveChartAsJPEG() throws IOException {
        File pieChart = new File( "grafika/PieChart.jpeg" );
        ChartUtilities.saveChartAsJPEG( pieChart , chart , 300 , 300 );
    }
}