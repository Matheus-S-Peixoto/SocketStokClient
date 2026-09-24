package org.stok.client.ui;

import org.stok.client.protocol.Actions;
import org.stok.client.protocol.request.Request;
import org.stok.client.protocol.request.RequestBody;

import java.math.BigDecimal;
import java.util.Scanner;

public class UI {
    private final Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    public Request showMenu() {
        System.out.println("\n===---------+++--------===");
        System.out.println("         SocketStok       ");
        System.out.println("===---------+++--------===");
        System.out.println("> [1] Ver Estoque.");
        System.out.println("> [2] Ver um Produto.");
        System.out.println("> [3] Criar Novo Produto.");
        System.out.println("> [4] Editar um Produto.");
        System.out.println("> [5] Deletar um Produto.");
        System.out.println("> [6] Adicionar Estoque.");
        System.out.println("> [7] Vender.");
        System.out.println("> [8] Remover do Estoque.");
        System.out.println("> [0] Sair.\n");

        System.out.print("Ação Desejada: ");

        String input = scanner.nextLine();
        int action = Integer.parseInt(input);

        Request newRequest = new Request();

        return switch (action) {
            case 1 -> returnInfo(newRequest);
            case 2 -> showInfo(newRequest);
            case 3 -> showCreate(newRequest);
            case 4 -> showEdit(newRequest);
            case 5 -> showRemove(newRequest);
            case 6 -> showAdd(newRequest);
            case 7 -> showSell(newRequest);
            case 8 -> showLoss(newRequest);
            case 0 -> returnExit(newRequest);
            default -> throw new IllegalArgumentException("Opção Inválida");
        };
    }

    public Request returnInfo(Request req) {
        req.setAction(Actions.P_INFO);
        return req;
    }

    public Request showInfo(Request req) {
        System.out.println("\n===---------+++--------===");
        System.out.println("         SocketStok       ");
        System.out.println("===---------+++--------===");

        System.out.print("> Digite o ID do produto que quer ver: ");
        req.setId(Integer.parseInt(scanner.nextLine()));

        req.setAction(Actions.P_INFO);
        return req;
    }

    public Request showCreate(Request req) {
        RequestBody reqBody = new RequestBody();

        System.out.println("\n===---------+++--------===");
        System.out.println("         SocketStok       ");
        System.out.println("===---------+++--------===");

        System.out.print("> Digite o nome do novo produto: ");
        String newName = scanner.nextLine();
        if (!newName.isBlank()) {
            reqBody.setName(newName);
        }

        System.out.print("> Digite a descrição do novo produto: ");
        String newDesc = scanner.nextLine();
        if (!newDesc.isBlank()) {
            reqBody.setDescription(newDesc);
        }

        System.out.print("> Digite o preço do novo produto: ");
        reqBody.setAmount(new BigDecimal(scanner.nextLine()));

        System.out.print("> Digite o código do novo produto (15 caracteres.): ");
        reqBody.setCode(scanner.nextLine());

        req.setBody(reqBody);
        req.setAction(Actions.P_CREATE);
        return req;
    }

    public Request showEdit(Request req) {
        RequestBody reqBody = new RequestBody();

        System.out.println("\n===---------+++--------===");
        System.out.println("         SocketStok       ");
        System.out.println("===---------+++--------===");
        System.out.println("\nPara pular um campo precione Enter\n");

        System.out.print("> Digite o ID do produto que quer editar: ");
        req.setId(Integer.parseInt(scanner.nextLine()));

        System.out.print("> Digite o novo nome do produto: ");
        String newName = scanner.nextLine();
        if (!newName.isBlank()) {
            reqBody.setName(newName);
        }

        System.out.print("> Digite a nova descrição do produto: ");
        String newDesc = scanner.nextLine();
        if (!newDesc.isBlank()) {
            reqBody.setDescription(newDesc);
        }

        System.out.print("> Digite o novo preço do produto: ");
        String newAmount = scanner.nextLine();
        if(newAmount != null) {
            reqBody.setAmount(newAmount);
        }

        System.out.print("> Digite o novo código do produto (15 caracteres): ");
        String newCode = scanner.nextLine();
        if (!newCode.isBlank()) {
            reqBody.setCode(newCode);
        }

        System.out.print("> Digite a nova quantidade em estoque do produto: ");
        String newQuantity = scanner.nextLine();
        if(!newQuantity.isBlank()) {
            reqBody.setQuantity(Integer.parseInt(newQuantity));
        }

        req.setBody(reqBody);
        req.setAction(Actions.P_EDIT);
        return req;
    }

    public Request showRemove(Request req) {
        System.out.println("\n===---------+++--------===");
        System.out.println("         SocketStok       ");
        System.out.println("===---------+++--------===");

        System.out.print("> Digite o ID do produto que quer remover do estoque: ");
        req.setId(Integer.parseInt(scanner.nextLine()));

        req.setAction(Actions.P_REMOVE);
        return req;
    }

    public Request showAdd(Request req) {
        RequestBody reqBody = new RequestBody();

        System.out.println("\n===---------+++--------===");
        System.out.println("         SocketStok       ");
        System.out.println("===---------+++--------===");

        System.out.print("> Digite o ID do produto que quer repor o estoque: ");
        req.setId(Integer.parseInt(scanner.nextLine()));

        System.out.print("> Quantos itens quer adicionar ao estoque? ");
        reqBody.setQuantity(Integer.parseInt(scanner.nextLine()));

        req.setBody(reqBody);
        req.setAction(Actions.S_ADD);
        return req;
    }

    public Request showSell(Request req) {
        RequestBody reqBody = new RequestBody();

        System.out.println("\n===---------+++--------===");
        System.out.println("         SocketStok       ");
        System.out.println("===---------+++--------===");

        System.out.print("> Digite o ID do produto que quer vender: ");
        req.setId(Integer.parseInt(scanner.nextLine()));

        System.out.print("> Quantos itens quer vender? ");
        reqBody.setQuantity(Integer.parseInt(scanner.nextLine()));

        req.setBody(reqBody);
        req.setAction(Actions.S_SELL);
        return req;
    }

    public Request showLoss(Request req) {
        RequestBody reqBody = new RequestBody();

        System.out.println("\n===---------+++--------===");
        System.out.println("         SocketStok       ");
        System.out.println("===---------+++--------===");

        System.out.print("> Digite o ID do produto que quer dar baixa no estoque: ");
        req.setId(Integer.parseInt(scanner.nextLine()));

        System.out.print("> Quantos itens quer dar baixa? ");
        reqBody.setQuantity(Integer.parseInt(scanner.nextLine()));

        req.setBody(reqBody);
        req.setAction(Actions.S_LOSS);
        return req;
    }

    private Request returnExit(Request req) {
        req.setAction(Actions.EXIT);
        return req;
    }
}

