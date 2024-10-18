import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Project implements ActionListener {
    JFrame frame;
    JTextField textField;
    JButton[] numbers = new JButton[10];

    JButton[] mathSigns = new JButton[14];
    JButton plusSign, minusSign, multiplicationSign, divisionSign,
            dotSign, equalsSign, deleteSign, clearSign, squareRootSign, squareSign,
            sineSign, cosineSign, tangentSign, cotangentSign;
    JPanel panel;
    ArrayList<Double> presentNumbers = new ArrayList<>();
    ArrayList<Character> presentOperators = new ArrayList<>();
    Font font1 = new Font("SansSerif", Font.BOLD, 50), font2 = new Font("SansSerif", Font.BOLD, 20);
    double num1 = 0, finalValue = 0;
    Project(){
        frame = new JFrame("KalKULAtor");
        frame.setSize(600,460);
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
        sineSign = new JButton("sin()");
        cosineSign = new JButton("cos()");
        tangentSign = new JButton("tan()");
        cotangentSign = new JButton("ctg()");

        mathSigns[0] = plusSign;
        mathSigns[1] = minusSign;
        mathSigns[2] = multiplicationSign;
        mathSigns[3] = divisionSign;
        mathSigns[4] = dotSign;
        mathSigns[5] = squareRootSign;
        mathSigns[6] = squareSign;
        mathSigns[7] = clearSign;
        mathSigns[8] = deleteSign;
        mathSigns[9] = equalsSign;
        mathSigns[10] = sineSign;
        mathSigns[11] = cosineSign;
        mathSigns[12] = tangentSign;
        mathSigns[13] = cotangentSign;

        for(int i = 0; i < 14; i++){
            mathSigns[i].addActionListener(this);
            mathSigns[i].setFocusable(false);
            mathSigns[i].setFont(font2);
        }

        for(int i = 0; i < 7; i++){
            mathSigns[i].setBackground(Color.LIGHT_GRAY);
        }

        for(int i = 10; i < 14; i++){
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

        panel = new JPanel();
        panel.setLayout(new GridLayout(7,4,10,10));

        panel.add(sineSign);
        panel.add(cosineSign);
        panel.add(clearSign);
        panel.add(deleteSign);
        panel.add(tangentSign);
        panel.add(cotangentSign);
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

        frame.add(textField, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
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

        else if(e.getSource() == dotSign){                          //obsluga kropki
            String scanner = textField.getText();
            for(int i = 0; i < scanner.length(); i++) {
                if(scanner.contains(".")){
                    return;
                }
            }
            textField.setText(textField.getText().concat("."));
        }

        else if(e.getSource() == equalsSign){                       //obsluga wyniku
            if(presentNumbers.isEmpty()){
                textField.setText("ERROR");
                return;
            }

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