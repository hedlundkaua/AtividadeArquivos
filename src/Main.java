import model.entities.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc= new Scanner(System.in);
        String lerArquivo = "C:\\Windows\\Temp\\input.csv";

        List<Product> list = new ArrayList<>();

        System.out.print("Entre com o caminho do arquivo: ");
        String sourceFileStr = sc.nextLine();

        //encapsula o processo de acessar os arquivos e trabalhar com os mesmos
        File sourceFile = new File(sourceFileStr);
        //pega o nome do caminho do arquivo, sem o arquivo
        String sourceFolderStr = sourceFile.getParent();

        //cria a pasta
        boolean success = new File(sourceFolderStr + "\\out").mkdir();
        //cria o arquivo na pasta criada
        String targetFileStr = sourceFolderStr + "\\out\\summary.csv";

        //tenta ler o sourceFileStr
        try (BufferedReader bf = new BufferedReader(new FileReader(sourceFileStr))){
            String itemCsv = bf.readLine();
            while (itemCsv != null){
                String[] fields = itemCsv.split(",");
                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);

                list.add(new Product(name, price, quantity));

                itemCsv = bf.readLine();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){
                for(Product item : list){
                    bw.write(item.getName() + "," + String.format("%.2f",item.total()) + "," + item.getQuantity());
                    bw.newLine();
                }
                System.out.println(targetFileStr +" CEATED");
            }catch (IOException e){
                System.out.println("Error writing file: " + e.getMessage());
            }

        }catch (IOException e){
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}