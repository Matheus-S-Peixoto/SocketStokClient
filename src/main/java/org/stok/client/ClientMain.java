package org.stok.client;

import org.stok.client.protocol.Actions;
import org.stok.client.protocol.ProtocolParser;
import org.stok.client.protocol.request.Request;
import org.stok.client.ui.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientMain {
    private static final UI ui = new UI();
    private static final ProtocolParser parser = new ProtocolParser();

    public static void main(String[] args) {
        final String HOST = args[0];
        final int PORT = Integer.parseInt(args[1]);

        System.out.println("Connecting to server...");

        try (
                Socket socket = new Socket(HOST, PORT);
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                PrintWriter output = new PrintWriter(
                        socket.getOutputStream(), true
                )
        ) {
            System.out.println("Connected to SocketStok Server!");

            while (true) {
                try {
                    Request newRequest = ui.showMenu();

                    if(newRequest.getAction() == Actions.EXIT) {
                        break;
                    }

                    output.println(parser.parseRequest(newRequest));

                    String jsonResponse = input.readLine();
                    if (jsonResponse == null) {
                        System.out.println("Servidor fechou a conexão.");
                        break;
                    }

                    System.out.println("Resposta Crua: ");
                    System.out.println(jsonResponse);
                    System.out.println("\nResposta com indentação: ");
                    System.out.println(parser.prettyPrint(jsonResponse));
                } catch (NumberFormatException e) {
                    System.out.println("\nDigite um número válido!\n");
                } catch (IllegalArgumentException e) {
                    System.out.println("\n" + e.getMessage() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Could not connect to server: " + e.getMessage());
        }
    }
}