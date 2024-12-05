import javax.swing.*;
import java.awt.*;

public class LoginSystem {
    public static void main(String[] args) {
        // Criação da interface de login
        JFrame frame = new JFrame("Sistema de Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Tamanho ajustado para teste
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela

        // Painel com imagem de fundo
        BackgroundPanel panel = new BackgroundPanel("background.jpg");
        panel.setLayout(null); // Definido como null para usar coordenadas absolutas
        frame.add(panel);

        // Dimensões dos componentes
        int labelWidth = 100, labelHeight = 30;
        int fieldWidth = 200, fieldHeight = 30;
        int buttonWidth = 100, buttonHeight = 30;

        // Coordenadas iniciais
        int xCenter = (frame.getWidth() - fieldWidth) / 2;
        int yStart = 200; // Define a posição inicial para centralizar os componentes verticalmente

        // Rótulo do usuário
        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setBounds(xCenter - 110, yStart, labelWidth, labelHeight);
        userLabel.setForeground(Color.WHITE);
        panel.add(userLabel);

        // Campo de texto para o usuário
        JTextField userText = new JTextField();
        userText.setBounds(xCenter, yStart, fieldWidth, fieldHeight);
        panel.add(userText);

        // Rótulo da senha
        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(xCenter - 110, yStart + 50, labelWidth, labelHeight);
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel);

        // Campo de senha
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(xCenter, yStart + 50, fieldWidth, fieldHeight);
        panel.add(passwordText);

        // Botão de login
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(xCenter + 50, yStart + 100, buttonWidth, buttonHeight);
        panel.add(loginButton);

        // Mensagem de resultado
        JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(xCenter - 50, yStart + 150, 300, labelHeight);
        resultLabel.setForeground(Color.YELLOW);
        panel.add(resultLabel);

        // Lógica do botão de login
        loginButton.addActionListener(_ -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            if ("admin".equals(username) && "123".equals(password)) {
                resultLabel.setText("Login bem-sucedido!");
                resultLabel.setForeground(Color.GREEN);
                
                // Fecha a janela de login
                frame.dispose();
                
                // Abre o menu principal
                MainMenu.main(new String[0]);
            } else {
                resultLabel.setText("Usuário ou senha inválidos!");
                resultLabel.setForeground(Color.RED);
            }
        });

        frame.setVisible(true);
    }
}

// Classe para desenhar a imagem de fundo
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String filePath) {
        try {
            backgroundImage = new ImageIcon(filePath).getImage();
            if (backgroundImage == null) {
                throw new Exception("Imagem não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar a imagem: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.RED);
            g.drawString("Erro: Imagem de fundo não encontrada!", 10, 20);
        }
    }
}