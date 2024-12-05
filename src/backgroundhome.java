import javax.swing.*;
import java.awt.*;

public class backgroundhome extends JFrame {
    public backgroundhome(String title) {
        super(title);
        setSize(1920, 1080); // Tamanho da tela (resolução)
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Você abriu " + title + "!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(label, BorderLayout.CENTER);

        add(panel);
    }
}
