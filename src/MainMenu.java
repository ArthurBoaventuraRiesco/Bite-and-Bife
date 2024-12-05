import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainMenu {
    public static void main(String[] args) {
        // Criação da janela principal
        JFrame frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080); // Define a resolução da janela para 1920x1080
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela

        // Painel com imagem de fundo
        BackgroundPanel panel1 = new BackgroundPanel("background1.jpg"); // Caminho relativo da imagem
        panel1.setLayout(new GridBagLayout()); // Usando GridBagLayout para o alinhamento dos botões
        frame.add(panel1);

        // Criação de 10 botões (incluindo os novos botões)
        String[] buttonLabels = {
            "Criar Receita", "Pesquisar Receita", "Bolo de Cenoura", "Focaccia", "Frango frito",
            "Spaghetti Aglio e Olio", "Panqueca Americana", "Guacamole", "Ceviche", "Bife Acebolado"
        };

        // Posição inicial e dimensões dos botões (Aumentados)
        int buttonWidth = 800;  // Largura maior dos botões
        int buttonHeight = 240; // Altura maior dos botões
        int spacing = 35; // Espaçamento entre os botões

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre os botões
        gbc.gridwidth = 1;  // Definindo 1 coluna por botão

        // Adicionando os botões
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);

            // Configurações do botão
            button.setContentAreaFilled(false); // Sem fundo
            button.setFocusPainted(false); // Sem foco
            button.setOpaque(false); // Transparente
            button.setForeground(Color.WHITE); // Texto branco
            button.setFont(new Font("Arial", Font.BOLD, 24)); // Aumentando o tamanho da fonte

            int row = i / 2; // Distribui os botões nas linhas
            int col = i % 2; // Distribui os botões nas colunas

            gbc.gridx = col;
            gbc.gridy = row;

            // Captura a variável i dentro do lambda de forma efetiva final
            final int index = i; // A variável "i" precisa ser final ou efetivamente final para ser usada no lambda
            button.addActionListener(e -> {
                if (index == 0) { // Se o botão "Criar Receita" for clicado
                    new CreateRecipe().setVisible(true);
                } else if (index == 1) { // Se o botão "Pesquisar Receita" for clicado
                    new SearchRecipe().setVisible(true);
                } else {
                    // Define os arquivos que devem ser abertos
                    String fileName = "";
                    switch (index) {
                        case 2: fileName = "bolo_de_cenoura.txt"; break;
                        case 3: fileName = "Focaccia.txt"; break;
                        case 4: fileName = "frango_frito.txt"; break;
                        case 5: fileName = "Spaghetti_Aglio_e_Olio.txt"; break;
                        case 6: fileName = "Panqueca_Americana.txt"; break;
                        case 7: fileName = "Guacamole.txt"; break;
                        case 8: fileName = "Ceviche_.txt"; break;
                        case 9: fileName = "Bife_Acebolado.txt"; break;
                    }

                    openFile(fileName);
                }
            });

            button.setPreferredSize(new Dimension(buttonWidth, buttonHeight)); // Definindo o tamanho preferido dos botões

            panel1.add(button, gbc);
        }

        frame.setVisible(true);
    }

    // Método para abrir o arquivo e exibir o conteúdo
    private static void openFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Arquivo não encontrado: " + fileName, "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Lê o conteúdo do arquivo
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            // Exibe o conteúdo em uma nova janela
            JFrame fileFrame = new JFrame("Visualizando: " + file.getName());
            fileFrame.setSize(600, 400);
            fileFrame.setLocationRelativeTo(null);

            JTextArea textArea = new JTextArea(content.toString());
            textArea.setFont(new Font("Arial", Font.PLAIN, 14));
            textArea.setEditable(false);

            JScrollPane textScrollPane = new JScrollPane(textArea);
            fileFrame.add(textScrollPane);
            fileFrame.setVisible(true);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// Painel para exibir a imagem de fundo
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String filePath) {
        try {
            // Verifica se a imagem existe no caminho correto
            backgroundImage = new ImageIcon(getClass().getResource(filePath)).getImage();
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

// Classe para criar receitas
class CreateRecipe extends JFrame {
    public CreateRecipe() {
        setTitle("Criar Receita");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        JTextField recipeNameField = new JTextField();
        JTextArea ingredientsArea = new JTextArea();
        JTextArea preparationArea = new JTextArea();
        JButton saveButton = new JButton("Salvar Receita");

        add(new JLabel("Nome da Receita:"));
        add(recipeNameField);
        add(new JLabel("Ingredientes:"));
        add(new JScrollPane(ingredientsArea));
        add(new JLabel("Modo de Preparo:"));
        add(new JScrollPane(preparationArea));
        add(saveButton);

        saveButton.addActionListener(e -> {
            String recipeName = recipeNameField.getText();
            String ingredients = ingredientsArea.getText();
            String preparation = preparationArea.getText();

            if (!recipeName.isEmpty() && !ingredients.isEmpty() && !preparation.isEmpty()) {
                try (FileWriter writer = new FileWriter(recipeName.replace(" ", "_") + ".txt")) {
                    writer.write("Nome da receita: " + recipeName + "\n\n");
                    writer.write("Ingredientes:\n" + ingredients + "\n");
                    writer.write("Modo de preparo:\n" + preparation + "\n");
                    JOptionPane.showMessageDialog(this, "Receita salva com sucesso!");
                    dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar a receita: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            }
        });
    }
}

// Classe para pesquisar receitas
class SearchRecipe extends JFrame {
    {
        // Criação da janela principal
        JFrame frame = new JFrame("Pesquisa de Arquivos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Botão "Pesquisar"
        JButton searchButton = new JButton("Pesquisar Arquivos .txt");
        searchButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Painel para exibir a lista de arquivos
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Ação do botão "Pesquisar"
        searchButton.addActionListener(e -> {
            // Caminho da pasta a ser pesquisada
            String folderPath = "C:\\Users\\Riescos\\Desktop\\Projetos Java\\Trabalho Lucilia";
            File folder = new File(folderPath);

            if (!folder.exists() || !folder.isDirectory()) {
                JOptionPane.showMessageDialog(frame, "Pasta não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lista arquivos .txt na pasta
            File[] txtFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

            // Limpa resultados anteriores
            resultPanel.removeAll();

            if (txtFiles != null && txtFiles.length > 0) {
                for (File file : txtFiles) {
                    JButton fileButton = new JButton(file.getName());
                    fileButton.setFont(new Font("Arial", Font.PLAIN, 14));

                    // Ação para abrir o arquivo ao clicar
                    fileButton.addActionListener(ev -> openFile(file));

                    resultPanel.add(fileButton);
                }
            } else {
                JLabel noFilesLabel = new JLabel("Nenhum arquivo .txt encontrado.");
                noFilesLabel.setFont(new Font("Arial", Font.ITALIC, 14));
                resultPanel.add(noFilesLabel);
            }

            resultPanel.revalidate();
            resultPanel.repaint();
        });

        frame.add(searchButton, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Método para abrir o arquivo e exibir o conteúdo
    private static void openFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Lê o conteúdo do arquivo
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            // Exibe o conteúdo em uma nova janela
            JFrame fileFrame = new JFrame("Visualizando: " + file.getName());
            fileFrame.setSize(600, 400);
            fileFrame.setLocationRelativeTo(null);

            JTextArea textArea = new JTextArea(content.toString());
            textArea.setFont(new Font("Arial", Font.PLAIN, 14));
            textArea.setEditable(false);

            JScrollPane textScrollPane = new JScrollPane(textArea);
            fileFrame.add(textScrollPane);
            fileFrame.setVisible(true);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
