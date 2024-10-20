import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.lang.Math;

public class Project implements ActionListener {
    JFrame frame;
    JTextField textField;
    JButton[] numbers = new JButton[10];

    JButton[] mathSigns = new JButton[14];
    JButton plusSign, minusSign, multiplicationSign, divisionSign,
            dotSign, equalsSign, deleteSign, clearSign, squareRootSign, squareSign,
            sineSign, cosineSign, tangentSign, percentageSign;
    JPanel panel;
    ChartPanel chartPanel;
    ArrayList<Double> presentNumbers = new ArrayList<>();
    ArrayList<Character> presentOperators = new ArrayList<>();
    Font font1 = new Font("SansSerif", Font.BOLD, 50), font2 = new Font("SansSerif", Font.BOLD, 20);
    double num1 = 0, finalValue = 0;
    String function = "";
    Dimension defaultFrameSize;
    JLabel coordinateLabel;
    Project(){
        frame = new JFrame("KalKULAtor");
        frame.setSize(600,600);
        defaultFrameSize = frame.getSize();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setFont(font1);
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setEditable(false);

        plusSign = new JButton("+");
        minusSign = new JButton("-");
        multiplicationSign = new JButton("X");
        divisionSign = new JButton("/");
        dotSign = new JButton(".");
        equalsSign = new JButton("=");
        deleteSign = new JButton("del");
        clearSign = new JButton("C");
        squareSign = new JButton("x²");
        squareRootSign = new JButton("√");
        percentageSign = new JButton("%");
        sineSign = new JButton("sin()");
        cosineSign = new JButton("cos()");
        tangentSign = new JButton("tan()");


        mathSigns[0] = plusSign;
        mathSigns[1] = minusSign;
        mathSigns[2] = multiplicationSign;
        mathSigns[3] = divisionSign;
        mathSigns[4] = dotSign;
        mathSigns[5] = squareRootSign;
        mathSigns[6] = squareSign;
        mathSigns[7] = percentageSign;
        mathSigns[8] = clearSign;
        mathSigns[9] = deleteSign;
        mathSigns[10] = equalsSign;
        mathSigns[11] = sineSign;
        mathSigns[12] = cosineSign;
        mathSigns[13] = tangentSign;


        for(int i = 0; i < 14; i++){
            mathSigns[i].addActionListener(this);
            mathSigns[i].setFocusable(false);
            mathSigns[i].setFont(font2);
        }

        for(int i = 0; i < 8; i++){                                         //kolorowanie przycisków
            mathSigns[i].setBackground(Color.LIGHT_GRAY);
        }

        for(int i = 11; i < 14; i++){
            mathSigns[i].setBackground(Color.PINK);
        }

        clearSign.setBackground(Color.gray);
        deleteSign.setBackground(Color.gray);
        equalsSign.setBackground(Color.cyan);

        for(int i = 0; i < 10; i++){
            numbers[i] = new JButton(String.valueOf(i));
            numbers[i].addActionListener(this);
            numbers[i].setFocusable(false);
            numbers[i].setFont(font2);
        }

        panel = new JPanel();                                               //panel z przyciskami
        panel.setLayout(new GridLayout(7,4,10,10));

        panel.add(sineSign);
        panel.add(cosineSign);
        panel.add(clearSign);
        panel.add(deleteSign);
        panel.add(tangentSign);
        panel.add(percentageSign);
        panel.add(squareRootSign);
        panel.add(squareSign);
        panel.add(numbers[1]);
        panel.add(numbers[2]);
        panel.add(numbers[3]);
        panel.add(multiplicationSign);
        panel.add(numbers[4]);
        panel.add(numbers[5]);
        panel.add(numbers[6]);
        panel.add(divisionSign);
        panel.add(numbers[7]);
        panel.add(numbers[8]);
        panel.add(numbers[9]);
        panel.add(plusSign);

        panel.add(dotSign);
        panel.add(numbers[0]);
        panel.add(equalsSign);
        panel.add(minusSign);
        panel.add(new JLabel(""));

        chartPanel = new ChartPanel(null);
        chartPanel.setLayout(new BorderLayout());
        chartPanel.setVisible(false);

        frame.add(textField, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(chartPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }

    public void createChart(double usersNumber, double calculatedPoint, String function){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries(function + "(x)");

        for(double x = usersNumber -2 * Math.PI; x <= usersNumber + 2 * Math.PI; x += 0.01){
            switch(function) {
                case "sin":
                    series.add(x, Math.sin(x));
                    break;
                case "cos":
                    series.add(x, Math.cos(x));
                    break;
                case "tan":
                    if (Math.abs(Math.cos(x)) > 0.0001) {
                        series.add(x, Math.tan(x));
                    }
                    else{
                        dataset.addSeries(series);
                        series = new XYSeries(function + "(x)");
                    }
                    break;
            }
        }

        XYSeries highlightedPointSeries = new XYSeries("Obliczony punkt");
        highlightedPointSeries.add(usersNumber, calculatedPoint);
        dataset.addSeries(series);
        dataset.addSeries(highlightedPointSeries);

        if (coordinateLabel != null) {
            chartPanel.remove(coordinateLabel);
        }
        coordinateLabel = new JLabel("");
        coordinateLabel.setText("(" + String.format("%.2f", usersNumber) + ", " +
                String.format("%.2f", calculatedPoint) + ")");

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Wykres funkcji " + function + "(x)",
                "x", "y",
                dataset
        );

        XYPlot plot = chart.getXYPlot();
        plot.getDomainAxis().setRange(usersNumber - 2 * Math.PI, usersNumber + 2 * Math.PI);
        if(function.equals("tan")) {
            plot.getRangeAxis().setRange(-10, 10);
        }

        ValueMarker yMarker = new ValueMarker(0);
        yMarker.setPaint(Color.blue);
        ValueMarker xMarker = new ValueMarker(0);
        xMarker.setPaint(Color.blue);

        ValueMarker yResultMarker = new ValueMarker(calculatedPoint);
        ValueMarker xResultMarker = new ValueMarker(usersNumber);

        plot.addRangeMarker(yMarker);
        plot.addDomainMarker(xMarker);
        plot.addRangeMarker(yResultMarker);
        plot.addDomainMarker(xResultMarker);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesLinesVisible(0,true);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesLinesVisible(1,false);
        renderer.setSeriesShapesVisible(1, true);

        plot.setRenderer(renderer);

        chartPanel.add(coordinateLabel, BorderLayout.SOUTH);
        chartPanel.setChart(chart);
        chartPanel.setVisible(true);
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }

    public void actionPerformed(ActionEvent e){
        for(int i = 0; i < 10; i++){                                     //obsluga przyciskow z cyframi
            if(e.getSource() == numbers[i]){
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }

        if(e.getSource() == plusSign){                                  //obsluga przysiskow z operacjami
            presentNumbers.add(Double.parseDouble(textField.getText()));
            presentOperators.add('+');
            textField.setText("");
        }

        else if(e.getSource() == minusSign){
            presentNumbers.add(Double.parseDouble(textField.getText()));
            presentOperators.add('-');
            textField.setText("");
        }

        else if(e.getSource() == multiplicationSign){
            presentNumbers.add(Double.parseDouble(textField.getText()));
            presentOperators.add('*');
            textField.setText("");
        }

        else if(e.getSource() == divisionSign){
            presentNumbers.add(Double.parseDouble(textField.getText()));
            presentOperators.add('/');
            textField.setText("");
        }

        else if(e.getSource() == squareSign){
            num1 = Double.parseDouble(textField.getText());
            finalValue = Math.pow(num1, 2);
            textField.setText(String.valueOf(finalValue));
            finalValue = num1;
        }

        else if(e.getSource() == squareRootSign){
            num1 = Double.parseDouble(textField.getText());
            finalValue = Math.sqrt(num1);
            textField.setText(String.valueOf(finalValue));
            finalValue = num1;
        }

        else if(e.getSource() == sineSign){
            function = "sin";
            num1 = Double.parseDouble(textField.getText());
            num1 = Math.toRadians(num1);
            finalValue = Math.sin(num1);
            textField.setText(String.valueOf(finalValue));
            createChart(num1, finalValue,function);
            finalValue = num1;
        }

        else if(e.getSource() == cosineSign){
            function = "cos";
            num1 = Double.parseDouble(textField.getText());
            num1 = Math.toRadians(num1);
            finalValue = Math.cos(num1);
            textField.setText(String.valueOf(finalValue));
            createChart(num1, finalValue,function);
            finalValue = num1;
        }
        else if(e.getSource() == tangentSign){
            function = "tan";
            num1 = Double.parseDouble(textField.getText());
            num1 = Math.toRadians(num1);
            finalValue = Math.tan(num1);
            textField.setText(String.valueOf(finalValue));
            createChart(num1, finalValue,function);
            finalValue = num1;
        }
        else if(e.getSource() == percentageSign){
            if(!presentNumbers.isEmpty()){
                num1 = Double.parseDouble(textField.getText());
                double num2 = presentNumbers.getLast();
                finalValue = num1 * num2 / 100;
                presentNumbers.add(finalValue);
                textField.setText(String.valueOf(finalValue));
                finalValue = num1;
            }
        }

        else if(e.getSource() == dotSign){                          //obsluga kropki
            String scanner = textField.getText();

            if(scanner.contains(".")){
                return;
            }

            textField.setText(textField.getText().concat("."));
        }

        else if(e.getSource() == equalsSign){                       //obsluga wyniku
            if(presentNumbers.isEmpty()){
                textField.setText("ERROR");
                return;
            }

            chartPanel.setVisible(false);
            frame.setSize(defaultFrameSize);
            frame.revalidate();
            frame.repaint();

            presentNumbers.add(Double.parseDouble(textField.getText()));

            for (int i = 0; i < presentOperators.size(); i++) {
                char localOperation = presentOperators.get(i);
                if (localOperation == '*' || localOperation == '/') {
                    double localResult;
                    if (localOperation == '*') {
                        localResult = presentNumbers.get(i) * presentNumbers.get(i + 1);
                    } else {
                        localResult = presentNumbers.get(i) / presentNumbers.get(i + 1);
                    }
                    presentNumbers.set(i, localResult);
                    presentNumbers.remove(i + 1);
                    presentOperators.remove(i);
                    i--;
                }
            }

            finalValue = presentNumbers.getFirst();
            for(int i = 0; i < presentOperators.size(); i++){
                char localOperation = presentOperators.get(i);
                if(localOperation == '+'){
                    finalValue += presentNumbers.get(i + 1);
                }
                else if (localOperation == '-') {
                    finalValue -= presentNumbers.get(i + 1);
                }
            }

            textField.setText(String.valueOf(finalValue));

            presentNumbers.clear();
            presentOperators.clear();
        }

        else if(e.getSource() == clearSign) {               //clear i delete
            textField.setText("");
            finalValue = 0;
        }

        else if(e.getSource() == deleteSign) {
            String presentValue = textField.getText();
            if (!presentValue.isEmpty()) {
                textField.setText(presentValue.substring(0, presentValue.length() - 1));
            }
        }
    }
}