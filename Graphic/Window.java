package Graphic;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Window extends JFrame {

    public static Input input = new Input();

    public Window(){
        super("Minimos Quadrados");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());
        add(input);
        pack();
        
        setLocationRelativeTo(null);

        setVisible(true);
    }

}
