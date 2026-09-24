package org.stok.client.ui;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UITest {
    UI ui = new UI();

    @Test
    void showMenu() {
        ui.showMenu();
    }
}