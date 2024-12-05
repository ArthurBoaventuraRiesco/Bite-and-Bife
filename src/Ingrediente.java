

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Ingrediente {

    private final String nome;
    private final double quantidade;
    private final String unidade;

    public Ingrediente(String nome, double quantidade, String unidade) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
    }
    public String getNome() {
        return nome;
    }
    public double getQuantidade() {
        return quantidade;
    }
    public String getUnidade() {
        return unidade;
    }
    @Override
    public String toString() {
        return "Ingrediente: " + nome + ", Quantidade: " + quantidade + " " + unidade;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Ingrediente> ingredientes = new ArrayList<>();

        List<String> unidadesValidas = Arrays.asList("kg", "gramas", "litros", "ml", "unidade","a gosto");

        System.out.println("Digite o nome da receita: ");
        String nomereceita = scanner.nextLine();

        while (true) {
            System.out.print("Digite o nome do ingrediente (ou 'parar' para finalizar): ");
            String nome = scanner.nextLine();

            if (nome.equalsIgnoreCase("parar")) {
                break;
            }

            System.out.print("Digite a quantidade do ingrediente: ");
            double quantidade = scanner.nextDouble();
            while (quantidade <= 0) {
                System.out.println("A quantidade deve ser maior que zero.");
                System.out.print("Digite a quantidade do ingrediente: ");
                quantidade = scanner.nextDouble();
            }
            scanner.nextLine();

            String unidade;
            while (true) {
                System.out.print("Digite a unidade de medida (kg, gramas, litros, ml, unidade,a gosto): ");
                unidade = scanner.nextLine();
                if (unidadesValidas.contains(unidade.toLowerCase())) {
                    break;
                } else {
                    System.out.println("Unidade invalida, digite de novo.");
                }
            }
            Ingrediente ingrediente = new Ingrediente(nome, quantidade, unidade);
            ingredientes.add(ingrediente);
        }
        System.out.println("\nNome da receita: " + nomereceita);
        System.out.println("\nDigite o modo de preparo: ");
        String modopreparo = scanner.nextLine();

        System.out.println("Ingredientes registrados: ");
        for (Ingrediente ingrediente : ingredientes) {
            System.out.println(ingrediente);
        }
        System.out.println("Receita concluida.");
        try {
            String nomeArquivo = nomereceita.replace(" ", "_") + ".txt";
            try (FileWriter writer = new FileWriter(nomeArquivo)) {
                writer.write("Nome da receita: " + nomereceita + "\n\n");
                writer.write("Ingredientes:\n");
                for (Ingrediente ingrediente : ingredientes) {
                    writer.write(ingrediente + "\n");
                }
                writer.write("\nModo de preparo:\n" + modopreparo + "\n");
                System.out.println("\nReceita salva no arquivo '" + nomeArquivo + "'.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar a receita: " + e.getMessage());
        }
    }
}