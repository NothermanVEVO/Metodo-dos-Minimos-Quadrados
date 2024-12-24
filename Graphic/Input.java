package Graphic;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Input extends JPanel {

    static JLabel textPoints = new JLabel("Pontos:");

    static JLabel adjustText = new JLabel("Ajuste:");
    static JComboBox<String> adjustComboBox = new JComboBox<>();

    static JButton continueButton = new JButton("Prosseguir");

    static JPanel pointsPanel = new JPanel();
    static JScrollPane pointsScrollPane;

    static JButton newPoint = new JButton("Novo Ponto");
    static JButton removePoint = new JButton("Remover Ponto");
    static JButton clearPoints = new JButton("Limpar Pontos");

    static ArrayList<Points> pointsList = new ArrayList<>();

    public Input(){

        setBackground(Color.DARK_GRAY);

        setBounds(0, 0, 800, 600);
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);

        pointsPanel.setPreferredSize(new Dimension(400, 400));
        pointsPanel.setBackground(Color.BLACK);
        pointsPanel.setLayout(null);

        // point.setLocation(0, 0);
        // pointsPanel.add(point);

        pointsScrollPane = new JScrollPane(pointsPanel);
        pointsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pointsScrollPane.setBounds(25, 50, 400, 400);
        add(pointsScrollPane);

        newPoint.setBounds(pointsScrollPane.getX() + pointsScrollPane.getWidth() + 15, pointsScrollPane.getY() + 10, 150, 35);
        newPoint.setFont(new Font("", Font.PLAIN, 20));
        newPoint.setFocusPainted(false);
        newPoint.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newPoint.addActionListener(l -> actionListener(l));
        add(newPoint);

        removePoint.setBounds(newPoint.getX(), newPoint.getY() + newPoint.getHeight() + 10, 200, 35);
        removePoint.setFont(new Font("", Font.PLAIN, 20));
        removePoint.setFocusPainted(false);
        removePoint.setCursor(new Cursor(Cursor.HAND_CURSOR));
        removePoint.addActionListener(l -> actionListener(l));
        add(removePoint);

        clearPoints.setBounds(newPoint.getX(), removePoint.getY() + removePoint.getHeight() + 10, 175, 35);
        clearPoints.setFont(new Font("", Font.PLAIN, 20));
        clearPoints.setFocusPainted(false);
        clearPoints.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearPoints.addActionListener(l -> actionListener(l));
        add(clearPoints);

        textPoints.setForeground(Color.WHITE);
        textPoints.setFont(new Font("", Font.PLAIN, 40));
        textPoints.setBounds(pointsScrollPane.getX() + 2, pointsScrollPane.getY() - 41 , 150, 40);
        add(textPoints);

        adjustText.setForeground(Color.WHITE);
        adjustText.setFont(new Font("", Font.PLAIN, 40));
        adjustText.setBounds(pointsScrollPane.getX() + 2, pointsScrollPane.getY() + pointsScrollPane.getHeight() + 11 , 150, 40);
        add(adjustText);

        adjustComboBox.setFont(new Font("", Font.PLAIN, 40));
        adjustComboBox.setBounds(adjustText.getX() - 2, adjustText.getY() + adjustText.getHeight() + 1, 300, 40);
        adjustComboBox.addItem("Linear");
        adjustComboBox.addItem("Quadrático");
        adjustComboBox.addItem("Cúbico");
        add(adjustComboBox);

        continueButton.setBounds(800 - 180, 600 - 40, 175, 35);
        continueButton.setFont(new Font("", Font.PLAIN, 20));
        continueButton.setFocusPainted(false);
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        continueButton.addActionListener(l -> actionListener(l));
        add(continueButton);

    }

    public void actionListener(ActionEvent e){
        if (e.getSource() == newPoint) {
            Points nPoint = new Points();
            pointsList.add(nPoint);
            if(pointsList.size() == 1){
                nPoint.setLocation(0, 0);
            } else{
                Points lastPoint = pointsList.get(pointsList.size() - 2);
                nPoint.setLocation(0, lastPoint.getY() + lastPoint.getHeight() + 5);
            }
            pointsPanel.add(nPoint);
            repaint();
        } else if (e.getSource() == removePoint) {
            if (pointsList.isEmpty()) {
                return;
            }
            String result = JOptionPane.showInputDialog(null, "Você deseja remover o ponto de qual posição? Entre 0 até " + (pointsList.size() - 1) + ".", "Remover Ponto", JOptionPane.QUESTION_MESSAGE);
            if (result == null) {
                return;
            }
            if (result.matches("[+-]?[0-9]+")) {
                int index = Integer.parseInt(result);
                if(index < 0 || index >= pointsList.size()){
                    JOptionPane.showMessageDialog(null, "Você deve entrar com uma posição válida!", "ERRO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (Points points : pointsList) {
                    pointsPanel.remove(points);
                }
                pointsList.remove(index);
                for (int i = 0; i < pointsList.size(); i++) {
                    if(i == 0){
                        pointsList.get(0).setLocation(0, 0);
                    } else{
                        Points lastPoint = pointsList.get(i - 1);
                        pointsList.get(i).setLocation(0, lastPoint.getY() + lastPoint.getHeight() + 5);
                    }
                    pointsPanel.add(pointsList.get(i));
                }
                repaint();
            } else{
                JOptionPane.showMessageDialog(null, "Você deve entrar com um valor do tipo INTEIRO", "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == clearPoints) {
            if (pointsList.isEmpty()){
                return;
            }
            for (Points points : pointsList) {
                pointsPanel.remove(points);
            }
            pointsList.clear();
            repaint();
        } else if (e.getSource() == continueButton){
            if (pointsList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "A lista de pontos está VAZIA", "ERRO", JOptionPane.ERROR_MESSAGE);
            }
            ArrayList<Double> xs = new ArrayList<>();
            ArrayList<Double> ys = new ArrayList<>();
            for (Points point : pointsList) {
                xs.add(point.getXValue());
                ys.add(point.getYValue());
            }
            new Output(listToArray(xs), listToArray(ys), Output.ajustarPolinomio(listToArray(xs), listToArray(ys), adjustComboBox.getSelectedItem().equals("Linear") ? 1 : adjustComboBox.getSelectedItem().equals("Quadrático") ? 2 : 3));
        }
    }

    public static double[] listToArray(ArrayList<Double> list){
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

}

class Points extends JPanel{

    public static final int FONT_SIZE = 35;

    public static final Font FONT = new Font("", Font.PLAIN, FONT_SIZE);

    JLabel xText = new JLabel("X: ");
    JTextField xField = new JTextField();

    JLabel yText = new JLabel("Y: ");
    JTextField yField = new JTextField();

    Points(){
        xText.setFont(FONT);
        xText.setBounds(0, 0, FONT_SIZE + 10, FONT_SIZE + 1);
        add(xText);

        xField.setFont(FONT);
        xField.setBounds(xText.getX() + xText.getWidth() + 2, 0, FONT_SIZE * 3, FONT_SIZE + 1);
        add(xField);

        yText.setFont(FONT);
        yText.setBounds(xField.getX() + xField.getWidth() + 2, 0, FONT_SIZE + 10, FONT_SIZE + 1);
        add(yText);

        yField.setFont(FONT);
        yField.setBounds(yText.getX() + yText.getWidth() + 2, 0, FONT_SIZE * 3, FONT_SIZE + 1);
        add(yField);

        setSize(yField.getX() + yField.getWidth(), FONT_SIZE + 1);
        setBackground(Color.WHITE);
        setLayout(null);
    }

    public double getXValue(){
        return Double.parseDouble(xField.getText());
    }

    public double getYValue(){
        return Double.parseDouble(yField.getText());
    }

}
