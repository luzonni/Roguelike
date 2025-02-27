package com.retronova.menus;

import com.retronova.engine.Activity;
import com.retronova.engine.Configs;
import com.retronova.engine.Engine;

import com.retronova.graphics.FontG;
import com.retronova.inputs.mouse.Mouse;
import com.retronova.inputs.mouse.Mouse_Button;

import java.awt.*;

public class Menu implements Activity {

    private Rectangle[] quadrados;
    private final Color[] coresQuadrados = {new Color(0x00A878), new Color(0x4169E1), new Color(0x708090)};
    private final String[] quadradosNomes = {"Play", "Options", "Exit"};
    private final Font fonteQuadrados = FontG.font(8 * Configs.UISCALE);
    private FontMetrics fmQuadrados;

    public Menu() {
        quadrados = new Rectangle[3];
        telacheia();
    }

    private void telacheia() {
        int x = Engine.window.getWidth() / 2;
        int y = Engine.window.getHeight() / 2;
        int larguraBotao = 200;
        int alturaBotao = 50;

        quadrados[0] = new Rectangle(x - larguraBotao / 2, y - 150, larguraBotao, alturaBotao);
        quadrados[1] = new Rectangle(x - larguraBotao / 2, y - 50, larguraBotao, alturaBotao);
        quadrados[2] = new Rectangle(x - larguraBotao / 2, y + 50, larguraBotao, alturaBotao);
    }

    @Override
    public void tick() {
        telacheia();
        if (Mouse.clickOn(Mouse_Button.LEFT, quadrados[0])) {
            System.out.println("Seleção de personagens aberta");
            Engine.setActivity(new Personagens());
        } else if (Mouse.clickOn(Mouse_Button.LEFT, quadrados[1])) {
            System.out.println("Opções");
        } else if (Mouse.clickOn(Mouse_Button.LEFT, quadrados[2])) {
            Engine.CLOSE();
            System.out.println("Jogo fechado");
        }
    }

    @Override
    public void render(Graphics2D g) {
        fmQuadrados = g.getFontMetrics(fonteQuadrados);
        g.setFont(fonteQuadrados);

        for (int i = 0; i < quadrados.length; i++) {
            g.setColor(coresQuadrados[i]);
            g.fillRect(quadrados[i].x, quadrados[i].y, quadrados[i].width, quadrados[i].height);
            g.setColor(Color.WHITE);
            g.drawRect(quadrados[i].x, quadrados[i].y, quadrados[i].width, quadrados[i].height);
            g.drawString(quadradosNomes[i], quadrados[i].x + (quadrados[i].width - fmQuadrados.stringWidth(quadradosNomes[i])) / 2, quadrados[i].y + (quadrados[i].height - fmQuadrados.getHeight()) / 2 + fmQuadrados.getAscent());
        }
    }

    @Override
    public void dispose() {
    }
}