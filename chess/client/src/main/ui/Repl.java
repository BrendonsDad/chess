package ui;

import java.util.Scanner;

public class Repl {

    private ClientUI client;

    public Repl(String serverUrl) {
        this.client = new ClientUI(serverUrl);
    }
    public void run() {
        System.out.println("\uD83D\uDC51 Welcome to 240 chess. Type Help to get started. \uD83D\uDC51 \n");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            System.out.println(client.returnState());
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(result);
            } catch (Throwable e) {
                System.out.print(e.getMessage());
            }
        }
        System.out.println();
    }
}
