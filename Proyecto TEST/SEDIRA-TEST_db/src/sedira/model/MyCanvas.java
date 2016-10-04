/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sedira.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javafx.scene.canvas.Canvas;
import javax.swing.JLabel;
import org.jfree.fx.FXGraphics2D;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

/**
 *
 * @author Hefner Francisco
 */
    class MyCanvas extends Canvas {

        private FXGraphics2D g2;

        private TeXIcon icon;

        public MyCanvas(String formulEnTex ) {
            this.g2 = new FXGraphics2D(getGraphicsContext2D());
             this.g2.scale(0.8, 0.8);

            String FormulaString = formulEnTex;

            TeXFormula formula = new TeXFormula(FormulaString);

            this.icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);

            widthProperty().addListener(evt -> draw());
            heightProperty().addListener(evt -> draw());
        }

        private void draw() {
            double width = getWidth();
            double height = getHeight();
            getGraphicsContext2D().clearRect(0, 0, width, height);

            BufferedImage image = new BufferedImage(icon.getIconWidth(),
                    icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D gg = image.createGraphics();
            gg.setColor(Color.WHITE);
            gg.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
            JLabel jl = new JLabel();
            jl.setForeground(new Color(0, 0, 0));
            icon.paintIcon(jl, gg, 0, 0);

            this.g2.drawImage(image, 0, 0, null);
        }

        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double prefWidth(double height) {
            return getWidth();
        }

        @Override
        public double prefHeight(double width) {
            return getHeight();
        }
    }
