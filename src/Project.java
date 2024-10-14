import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Project implements ActionListener {
    JFrame frame;
    JTextField textField;
    JButton[] numbers = new JButton[10];

    JButton[] mathSigns = new JButton[10];
    JButton plusSign, minusSign, multiplicationSign, divisionSign,
            dotSign, equalsSign, deleteSign, clearSign, squareRootSign, squareSign;
    JPanel panel;
    Font font1 = new Font("SansSerif", Font.BOLD, 50), font2 = new Font("SansSerif", Font.BOLD, 20);
    double num1 = 0, num2 = 0, finalValue = 0;
    char operation;
    Project(){
        frame = new JFrame("KalKULAtor");
        frame.setSize(500,460);
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

        for(int i = 0; i < 10; i++){
            mathSigns[i].addActionListener(this);
            mathSigns[i].setFocusable(false);
            mathSigns[i].setFont(font2);
        }

        for(int i = 0; i < 7; i++){
            mathSigns[i].setBackground(Color.LIGHT_GRAY);
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
        panel.setLayout(new GridLayout(6,4,10,10));


        panel.add(squareRootSign);
        panel.add(squareSign);
        panel.add(clearSign);
        panel.add(deleteSign);
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

        frame.add(textField, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        for(int i = 0; i < 10; i++){
            if(e.getSource() == numbers[i]){
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }

        if(e.getSource() == plusSign){
            num1 = Double.parseDouble(textField.getText());
            operation = '+';
            textField.setText("");
        }

        else if(e.getSource() == minusSign){
            num1 = Double.parseDouble(textField.getText());
            operation = '-';
            textField.setText("");
        }

        else if(e.getSource() == multiplicationSign){
            num1 = Double.parseDouble(textField.getText());
            operation = '*';
            textField.setText("");
        }

        else if(e.getSource() == divisionSign){
            num1 = Double.parseDouble(textField.getText());
            operation = '/';
            textField.setText("");
        }

        else if(e.getSource() == squareSign){
            num1 = Double.parseDouble(textField.getText());
            finalValue = Math.pow(num1, 2);
            textField.setText(String.valueOf(finalValue));
        }

        else if(e.getSource() == squareRootSign){
            num1 = Double.parseDouble(textField.getText());
            finalValue = Math.sqrt(num1);
            textField.setText(String.valueOf(finalValue));
        }

        else if(e.getSource() == dotSign){
            textField.setText(textField.getText().concat("."));
        }

        else if(e.getSource() == equalsSign){
            num2 = Double.parseDouble(textField.getText());

            switch(operation) {
                case'+':
                    finalValue = num1 + num2;
                    break;
                case'-':
                    finalValue = num1 - num2;
                    break;
                case'*':
                    finalValue = num1 * num2;
                    break;
                case'/':
                    finalValue = num1 / num2;
                    break;
            }
            textField.setText(String.valueOf(finalValue));
            num1 = finalValue;
        }

        else if(e.getSource() == clearSign) {
            textField.setText("");
        }

        else if(e.getSource() == deleteSign) {
            String presentValue = textField.getText();
            if (!presentValue.isEmpty()) {
                textField.setText(presentValue.substring(0, presentValue.length() - 1));
            }
        }

    }

    public static void main(String[] args) {
        new Project();
    }
}