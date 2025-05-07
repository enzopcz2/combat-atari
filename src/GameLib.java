import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RescaleOp;
import javax.swing.JFrame;

public class GameLib {
   public static int WIDTH = 480;
   public static int HEIGHT = 700;
   private static final int TARGET_FPS = 60;
   public static final int ALIGN_LEFT = 0;
   public static final int ALIGN_RIGHT = 1;
   public static final int ALIGN_CENTER = 2;
   public static final int KEY_UP = 0;
   public static final int KEY_DOWN = 1;
   public static final int KEY_LEFT = 2;
   public static final int KEY_RIGHT = 3;
   public static final int KEY_CONTROL = 4;
   public static final int KEY_ESCAPE = 5;
   public static final int KEY_A = 6;
   public static final int KEY_Z = 7;
   public static final int KEY_K = 8;
   public static final int KEY_M = 9;
   public static final int KEY_SPACE = 10;
   public static final int KEY_D = 11;
   public static final int KEY_S = 12;
   public static final int KEY_W = 13;
   public static final int KEY_SHIFT = 14;
   public static final int KEY_ENTER = 15;
   private static JFrame frame = null;
   private static Graphics g = null;
   private static MyKeyAdapter keyboard = null;
   private static Canvas canvas;
   private static long lastFrameTime;

   public static void initGraphics() {
      initGraphics("Projeto COO", WIDTH, HEIGHT);
   }

   public static void initGraphics(String var0, int var1, int var2) {
      WIDTH = var1;
      HEIGHT = var2;
      frame = new JFrame(var0);
      frame.setDefaultCloseOperation(3);
      frame.setSize(WIDTH, HEIGHT);
      frame.setResizable(false);
      canvas = new Canvas();
      canvas.setPreferredSize(new Dimension(var1, var2));
      canvas.setMinimumSize(new Dimension(var1, var2));
      canvas.setBounds(0, 0, var1, var2);
      frame.add(canvas);
      keyboard = new MyKeyAdapter();
      canvas.addKeyListener(keyboard);
      frame.pack();
      frame.setVisible(true);
      canvas.createBufferStrategy(2);
      g = canvas.getBufferStrategy().getDrawGraphics();
      canvas.requestFocus();
      frame.pack();
   }

   public static void setColor(Color var0) {
      g.setColor(var0);
   }

   public static void drawLine(double var0, double var2, double var4, double var6) {
      g.drawLine((int)Math.round(var0), (int)Math.round(var2), (int)Math.round(var4), (int)Math.round(var6));
   }

   public static void drawCircle(double var0, double var2, double var4) {
      int var6 = (int)Math.round(var0 - var4);
      int var7 = (int)Math.round(var2 - var4);
      int var8 = (int)Math.round(2.0D * var4);
      int var9 = (int)Math.round(2.0D * var4);
      g.drawOval(var6, var7, var8, var9);
   }

   public static void drawDiamond(double var0, double var2, double var4) {
      int var6 = (int)Math.round(var0);
      int var7 = (int)Math.round(var2 - var4);
      int var8 = (int)Math.round(var0 + var4);
      int var9 = (int)Math.round(var2);
      int var10 = (int)Math.round(var0);
      int var11 = (int)Math.round(var2 + var4);
      int var12 = (int)Math.round(var0 - var4);
      int var13 = (int)Math.round(var2);
      drawLine((double)var6, (double)var7, (double)var8, (double)var9);
      drawLine((double)var8, (double)var9, (double)var10, (double)var11);
      drawLine((double)var10, (double)var11, (double)var12, (double)var13);
      drawLine((double)var12, (double)var13, (double)var6, (double)var7);
   }

   public static BufferedImage createBitmap(int var0, int var1, byte[] var2) {
      BufferedImage var3 = new BufferedImage(var0, var1, 2);
      int var4 = 0;
      int var5 = Color.white.getRGB();

      for(int var6 = 0; var6 < var1; ++var6) {
         for(int var7 = 0; var7 < var0; ++var7) {
            byte var8 = var2[var4++];
            if (var8 == 1) {
               var3.setRGB(var7, var6, var5);
            }
         }
      }

      return var3;
   }

   public static void drawImage(BufferedImage var0, double var1, double var3, double var5, double var7) {
      Graphics2D var9 = (Graphics2D)g;
      AffineTransform var10 = new AffineTransform();
      var10.translate(var1, var3);
      var10.scale(var7, var7);
      var10.rotate(Math.toRadians(var5));
      var10.translate((double)(var0.getWidth((ImageObserver)null) / -2), (double)(var0.getHeight((ImageObserver)null) / -2));
      Color var11 = g.getColor();
      float[] var12 = new float[]{(float)var11.getRed() / 255.0F, (float)var11.getGreen() / 255.0F, (float)var11.getBlue() / 255.0F, 1.0F};
      RescaleOp var13 = new RescaleOp(var12, new float[]{0.0F, 0.0F, 0.0F, 0.0F}, (RenderingHints)null);
      AffineTransform var14 = var9.getTransform();
      var9.transform(var10);
      var9.drawImage(var0, var13, 0, 0);
      var9.setTransform(var14);
   }

   public static void drawPlayer(double var0, double var2, double var4) {
      drawLine(var0 - var4, var2 + var4, var0, var2 - var4);
      drawLine(var0 + var4, var2 + var4, var0, var2 - var4);
      drawLine(var0 - var4, var2 + var4, var0, var2 + var4 * 0.5D);
      drawLine(var0 + var4, var2 + var4, var0, var2 + var4 * 0.5D);
   }

   public static void drawExplosion(double var0, double var2, double var4) {
      if (!(var4 < 0.0D) && !(var4 > 1.0D)) {
         byte var6 = 5;
         int var7 = (int)(255.0D - Math.pow(var4, (double)var6) * 255.0D);
         int var8 = (int)(128.0D - Math.pow(var4, (double)var6) * 128.0D);
         byte var9 = 0;
         setColor(new Color(var7, var8, var9));
         drawCircle(var0, var2, var4 * var4 * 40.0D);
         drawCircle(var0, var2, var4 * var4 * 40.0D + 1.0D);
      }
   }

   public static void fillRect(double var0, double var2, double var4, double var6) {
      int var8 = (int)Math.round(var0 - var4 / 2.0D);
      int var9 = (int)Math.round(var2 - var6 / 2.0D);
      g.fillRect(var8, var9, (int)Math.round(var4), (int)Math.round(var6));
   }

   public static void drawStyledText(String var0, double var1, int var3, int var4, boolean var5, int var6) {
      if (var6 == 0 || System.currentTimeMillis() / (long)var6 % 2L == 0L) {
         if (var5) {
            drawText(var0, var1 - 1.0D, var3, var4);
         }

         drawText(var0, var1, var3, var4);
         if (var5) {
            drawText(var0, var1 + 1.0D, var3, var4);
         }
      }

   }

   public static void drawText(String var0, double var1, int var3) {
      drawText(var0, var1, var3, 28);
   }

   public static void drawText(String var0, double var1, int var3, int var4) {
      int var5 = 0;
      g.setFont(new Font("Monospaced", 0, var4));
      FontMetrics var7 = g.getFontMetrics();
      int var6 = var7.stringWidth(var0);
      if (var3 == 0) {
         var5 = 40;
      } else if (var3 == 1) {
         var5 = WIDTH - var6 - 40;
      } else if (var3 == 2) {
         var5 = WIDTH / 2 - var6 / 2;
      }

      g.drawString(var0, var5, (int)Math.round(var1));
   }

   public static void display() {
      g.dispose();
      canvas.getBufferStrategy().show();
      sync();
      g = canvas.getBufferStrategy().getDrawGraphics();
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, frame.getWidth() - 1, frame.getHeight() - 1);
      g.setColor(Color.WHITE);
   }

   private static void sync() {
      Toolkit.getDefaultToolkit().sync();
      if (lastFrameTime != 0L) {
         long var0 = System.nanoTime();
         long var2 = var0 - lastFrameTime;
         if (var2 < 16666666L) {
            try {
               long var4 = (16666666L - var2) / 1000000L;
               Thread.sleep(var4);
            } catch (InterruptedException var6) {
            }
         }
      }

      lastFrameTime = System.nanoTime();
   }

   public static boolean isKeyPressed(int var0) {
      return keyboard.isKeyPressed(var0);
   }

   public static void debugKeys() {
      keyboard.debug();
   }

   public static Graphics2D getGraphics() {
      return (Graphics2D)g;
   }
}
