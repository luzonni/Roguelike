package com.retronova.game.interfaces;

import com.retronova.engine.Activity;
import com.retronova.engine.Configs;
import com.retronova.engine.Engine;
import com.retronova.engine.sound.Musics;
import com.retronova.engine.sound.Sound;
import com.retronova.game.Game;
import com.retronova.engine.graphics.FontG;
import com.retronova.engine.inputs.keyboard.KeyBoard;
import com.retronova.engine.inputs.mouse.Mouse;
import com.retronova.engine.inputs.mouse.Mouse_Button;
import com.retronova.menus.Menu;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Pause implements Activity {

    private Rectangle[] quadrados;
    private final Font fonteTitulo = FontG.font(15 * Configs.UiScale());
    private final Color corFundo = new Color(50, 50, 50, 150);
    private final Color corBotao = new Color(0xc17564);
    private final Color corTexto = Color.WHITE;
    private final String[] quadradosNomes = {"Continue", "Restart", "Options", "Lobby", "Main Menu", "Quit"};
    private int quadradoSeta = -1;
    private Game game;


    public Pause(Game game) {
        this.game = game;
        telacheia();
    }

    private void telacheia() {
        quadrados = new Rectangle[6];

        int x = Engine.window.getWidth() / 2;
        int y = 300; // Moved buttons up
        int larguraBotao = 200;
        int alturaBotao = 50;

        quadrados[0] = new Rectangle(x - larguraBotao / 2, y - 150, larguraBotao, alturaBotao);
        quadrados[1] = new Rectangle(x - larguraBotao / 2, y - 50, larguraBotao, alturaBotao);
        quadrados[2] = new Rectangle(x - larguraBotao / 2, y + 50, larguraBotao, alturaBotao);
        quadrados[3] = new Rectangle(x - larguraBotao / 2, y + 150, larguraBotao, alturaBotao);
        quadrados[4] = new Rectangle(x - larguraBotao / 2, y + 250, larguraBotao, alturaBotao);
        quadrados[5] = new Rectangle(x - larguraBotao / 2, y + 350, larguraBotao, alturaBotao);
    }

    @Override
    public void tick() {
        atualizarSeta();
        if (KeyBoard.KeyPressed("ESCAPE")) {
            System.out.println("Jogo pausado");
            Engine.pause(null);
            return;
        }

        for (int i = 0; i < quadrados.length; i++) {
            if (Mouse.clickOn(Mouse_Button.LEFT, quadrados[i])) {
                switch (i) {
                    case 0:
                        System.out.println("Clicou em Resume");
                        Engine.pause(null);
                        break;
                    case 1:
                        System.out.println("Clicou em Restart");
                        Engine.pause(null);
                        Game.restart();
                        Sound.stop(Musics.Music3);
                        Sound.play(Musics.Music3, true);
                        break;
                    case 2:
                        System.out.println("Clicou em Options");
                        break;
                    case 3:
                        System.out.println("Clicou em Looby");
                        Sound.stop(Musics.Music3);
                        Sound.play(Musics.Music2, true);
                        game.changeMap(null);
                        Engine.pause(null);
                        break;
                    case 4:
                        System.out.println("Clicou em Main Menu");
                        Engine.pause(null);
                        Sound.stop(Musics.Music3);
                        Engine.setActivity(new Menu());
                        break;
                    case 5:
                        System.out.println("Clicou em Quit");
                        Engine.CLOSE();
                        break;
                    default:
                        System.out.println("Botão desconhecido");
                        break;
                }
            }
        }
    }

    private void atualizarSeta() {
        quadradoSeta = -1;
        for (int i = 0; i < quadrados.length; i++) {
            if (quadrados[i].contains(Mouse.getX(), Mouse.getY())) {
                quadradoSeta = i;
                break;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        telacheia();
        desenharFundo(g);
        desenharTitulo(g);
        desenharBotoes(g);
        desenharSeta(g);
    }

    private void desenharFundo(Graphics2D g) {
        g.setColor(corFundo);
        g.fillRect(0, 0, Engine.window.getWidth(), Engine.window.getHeight());
    }

    private void desenharTitulo(Graphics2D g) {
        g.setFont(fonteTitulo);
        g.setColor(corTexto);
        FontMetrics fmPause = g.getFontMetrics();
        int x = (Engine.window.getWidth() - fmPause.stringWidth("Game Paused")) / 2;
        int y = 50;
        g.drawString("Game Paused", x, y + fmPause.getAscent());
    }

    private void desenharBotoes(Graphics2D g) {
        Stroke defaultStroke = g.getStroke();

        for (int i = 0; i < quadrados.length; i++) {
            int tamanhoFonte = 8 * Configs.UiScale();
            Font fonteAtual = FontG.font(tamanhoFonte);
            FontMetrics fmQuadrados = g.getFontMetrics(fonteAtual);

            while (fmQuadrados.stringWidth(quadradosNomes[i]) > quadrados[i].width - 20) {
                tamanhoFonte--;
                fonteAtual = FontG.font(tamanhoFonte);
                fmQuadrados = g.getFontMetrics(fonteAtual);
            }

            RoundRectangle2D arredondar = new RoundRectangle2D.Double(quadrados[i].x, quadrados[i].y, quadrados[i].width, quadrados[i].height, 15, 15);

            GradientPaint gradient = new GradientPaint(quadrados[i].x, quadrados[i].y, corBotao.brighter(), quadrados[i].x, quadrados[i].y + quadrados[i].height, corBotao.darker());

            g.setPaint(gradient);
            g.fill(arredondar);

            g.setColor(new Color(0x872341));
            g.setStroke(new BasicStroke(1.0f));
            g.draw(arredondar);

            if (quadrados[i].contains(Mouse.getX(), Mouse.getY())) {
                g.setColor(Color.WHITE);
                g.setStroke(new BasicStroke(2.0f));
                g.draw(arredondar);
            }

            g.setStroke(defaultStroke);

            g.setColor(corTexto);
            g.setFont(fonteAtual);
            g.drawString(quadradosNomes[i], quadrados[i].x + (quadrados[i].width - fmQuadrados.stringWidth(quadradosNomes[i])) / 2, quadrados[i].y + (quadrados[i].height - fmQuadrados.getHeight()) / 2 + fmQuadrados.getAscent());
        }
    }


    private void desenharSeta(Graphics2D g) {
        if (quadradoSeta != -1) {
            int setaX = quadrados[quadradoSeta].x - 20;
            int setaY = quadrados[quadradoSeta].y + quadrados[quadradoSeta].height / 2;
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(3.0f));
            g.drawLine(setaX, setaY, setaX - 10, setaY - 8);
            g.drawLine(setaX, setaY, setaX - 10, setaY + 8);
        }
    }

    @Override
    public void dispose() {
    }
}