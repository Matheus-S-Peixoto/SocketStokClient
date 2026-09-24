package org.stok.client;

import org.stok.client.protocol.Actions;
import org.stok.client.protocol.ProtocolParser;
import org.stok.client.protocol.request.Request;
import org.stok.client.ui.UI;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


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

                    if(newRequest.getAction() == Actions.W_JSON) {
                        String writtenJson = readJsonFromVsCode();

                        System.out.println("\nRequest escrito: ");
                        String parsedJson = parser.parseWrittenJson(writtenJson);
                        System.out.println(parsedJson);

                        output.println(parsedJson);
                    }
                    if(newRequest.getAction() == Actions.EXIT) {
                        break;
                    }

                    String jsonRequest = parser.parseRequest(newRequest);
                    output.println(jsonRequest);

                    String jsonResponse = input.readLine();
                    if (jsonResponse == null) {
                        System.out.println("Servidor fechou a conexão.");
                        break;
                    }

                    System.out.println("\nResposta Crua: ");
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

    public static String readJsonFromVim()
            throws IOException, InterruptedException {

        Path tempFile = Files.createTempFile("socketstok-request-", ".json");

        try {
            String template = """
            {
              "action": "",
              "id": null,
              "body": {}
            }
            """;

            Files.writeString(tempFile, template);

            Process process = new ProcessBuilder(
                    "vim",
                    tempFile.toString()
            )
                    .inheritIO()
                    .start();

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new IOException("Vim exited with code: " + exitCode);
            }

            return Files.readString(tempFile);

        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    public static String readJsonFromVsCode() throws IOException {
        Path tempFile = Files.createTempFile("socketstok-request-", ".json");

        try {
            String template = """
            {
              "action": "",
              "id": null,
              "body": {}
            }
            """;

            Files.writeString(tempFile, template);

            Process process = new ProcessBuilder(
                    "code",
                    "--wait",
                    tempFile.toString()
            )
                    .inheritIO()
                    .start();

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new IOException("VS Code exited with code: " + exitCode);
            }

            return Files.readString(tempFile);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}