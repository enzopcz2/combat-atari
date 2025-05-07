import Collision.Box;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Combat {
   public static final int TOP_HEADER_HEIGHT = 80;
   public static final int SCREEN_WIDTH = 800;
   public static final int SCREEN_HEIGHT = 600;
   public static final String PLAYER1 = "Player 1";
   public static final String PLAYER2 = "Player 2";
   private static Player[] players;
   private static double[][] playerSafePositions;
   private static PlayerController[] controllers;
   private static Wall[] walls;
   private static Score[] scores;
   private static Collection<Shot> shots;
   private static int currentFPS;

   public static void main(String[] var0) {
      long var1 = 16L;
      GameLib.initGraphics("Combat", 800, 600);
      initGame();
      boolean var3 = true;
      int var4 = 0;
      long var5 = System.currentTimeMillis();
      long var7 = System.currentTimeMillis();

      while(true) {
         if (var3) {
            if (GameLib.isKeyPressed(15)) {
               var3 = false;
            }

            drawStartScreen();
         } else {
            drawGame();
            updateGame(var1);
         }

         GameLib.display();
         ++var4;
         long var9 = System.currentTimeMillis();
         if (var9 - var5 >= 1000L) {
            var5 = var9;
            currentFPS = var4;
            var4 = 0;
         }

         var1 = var9 - var7;
         var7 = var9;
      }
   }

   public static void addShot(Shot var0) {
      shots.add(var0);
   }

   private static void initGame() {
      players = initPlayers();
      walls = initWalls();
      scores = initScores();
      shots = new HashSet();
   }

   private static void drawStartScreen() {
      Wall[] var0 = walls;
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         Wall var3 = var0[var2];
         var3.draw();
      }

      GameLib.setColor(Color.YELLOW);
      GameLib.drawStyledText("Pressione <ENTER> para jogar!", 40.0D, 2, 28, false, 500);
      GameLib.setColor(Color.GREEN);
      GameLib.drawText("Jogador da esquerda", 490.0D, 0);
      GameLib.drawText(" WASD:   mover", 520.0D, 0);
      GameLib.drawText(" Espaço: atirar", 550.0D, 0);
      GameLib.setColor(Color.BLUE);
      GameLib.drawText("Jogador da direita", 490.0D, 1);
      GameLib.drawText("↑←↓→:   mover ", 520.0D, 1);
      GameLib.drawText("Shift: atirar ", 550.0D, 1);
   }

   private static void updateGame(long var0) {
      savePositions();
      PlayerController[] var2 = controllers;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PlayerController var5 = var2[var4];
         var5.doInputs(var0);
      }

      for(int var6 = 0; var6 < players.length; ++var6) {
         collidePlayer(var6);
      }

      updateShots(var0);
   }

   private static void savePositions() {
      for(int var0 = 0; var0 < players.length; ++var0) {
         Player var1 = players[var0];
         double[] var2 = playerSafePositions[var0];
         var2[0] = var1.getCx();
         var2[1] = var1.getCy();
      }

   }

   private static void updateShots(long var0) {
      Iterator var2 = shots.iterator();

      while(var2.hasNext()) {
         Shot var3 = (Shot)var2.next();
         var3.update(var0);
         Player[] var4 = players;
         int var5 = var4.length;

         int var6;
         label49:
         for(var6 = 0; var6 < var5; ++var6) {
            Player var7 = var4[var6];
            if (var3.checkCollision(var7)) {
               var3.onPlayerCollision(var7);
               Score[] var8 = scores;
               int var9 = var8.length;
               int var10 = 0;

               while(true) {
                  if (var10 >= var9) {
                     break label49;
                  }

                  Score var11 = var8[var10];
                  if (var11.getPlayerId().equals(var3.getOwner().getId())) {
                     var11.inc();
                  }

                  ++var10;
               }
            }
         }

         Wall[] var14 = walls;
         var5 = var14.length;

         for(var6 = 0; var6 < var5; ++var6) {
            Wall var16 = var14[var6];
            if (var3.checkCollision(var16)) {
               var3.onWallCollision();
            }
         }
      }

      ArrayList var12 = new ArrayList();
      Iterator var13 = shots.iterator();

      while(var13.hasNext()) {
         Shot var15 = (Shot)var13.next();
         if (!var15.isActive()) {
            var12.add(var15);
         }
      }

      shots.removeAll(var12);
   }

   private static void collidePlayer(int var0) {
      Player var1 = players[var0];
      double var2 = playerSafePositions[var0][0];
      double var4 = playerSafePositions[var0][1];
      double var6 = var1.getCx() - var2;
      double var8 = var1.getCy() - var4;
      var1.setPosition(var2, var4);
      Box var10 = ISolid.toBox(var1);
      double var11 = 1.0D;
      Player[] var13 = players;
      int var14 = var13.length;

      int var15;
      Collision var17;
      for(var15 = 0; var15 < var14; ++var15) {
         Player var16 = var13[var15];
         if (var16 != var1) {
            var17 = Collision.collideDynamic(var10, ISolid.toBox(var16), var6, var8);
            var11 = Math.min(var11, var17.time);
         }
      }

      Wall[] var18 = walls;
      var14 = var18.length;

      for(var15 = 0; var15 < var14; ++var15) {
         Wall var21 = var18[var15];
         var17 = Collision.collideDynamic(var10, ISolid.toBox(var21), var6, var8);
         var11 = Math.min(var11, var17.time);
      }

      double var19 = var1.getCx();
      double var20 = var1.getCy();
      var1.setPosition(var19 + var6 * var11, var20 + var8 * var11);
   }

   private static void drawGame() {
      GameLib.setColor(Color.YELLOW);
      GameLib.drawText("Combat!", 40.0D, 2);
      GameLib.setColor(Color.YELLOW);
      GameLib.drawText("FPS: " + currentFPS, 15.0D, 0, 12);
      Wall[] var0 = walls;
      int var1 = var0.length;

      int var2;
      for(var2 = 0; var2 < var1; ++var2) {
         Wall var3 = var0[var2];
         var3.draw();
      }

      Player[] var4 = players;
      var1 = var4.length;

      for(var2 = 0; var2 < var1; ++var2) {
         Player var8 = var4[var2];
         var8.draw();
      }

      Iterator var5 = shots.iterator();

      while(var5.hasNext()) {
         Shot var7 = (Shot)var5.next();
         var7.draw();
      }

      Score[] var6 = scores;
      var1 = var6.length;

      for(var2 = 0; var2 < var1; ++var2) {
         Score var9 = var6[var2];
         var9.draw();
      }

   }

   private static Player[] initPlayers() {
      Player[] var0 = new Player[2];
      playerSafePositions = new double[][]{{0.0D, 0.0D}, {0.0D, 0.0D}};
      BufferedImage var1 = GameLib.createBitmap(9, 9, new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
      var0[0] = new Player(80.0D, 340.0D, 32.0D, 32.0D, 0.0D, var1, Color.GREEN, "Player 1", 0.08D);
      var0[1] = new Player(720.0D, 340.0D, 32.0D, 32.0D, 180.0D, var1, Color.BLUE, "Player 2", 0.08D);
      controllers = new PlayerController[]{new PlayerController(var0[0], 13, 12, 6, 11, 10), new PlayerController(var0[1], 0, 1, 2, 3, 14)};
      return var0;
   }

   private static Wall[] initWalls() {
      Wall[] var0 = new Wall[]{new Wall(10.0D, 330.0D, 20.0D, 520.0D, Color.WHITE, "Left"), new Wall(790.0D, 330.0D, 20.0D, 520.0D, Color.WHITE, "Right"), new Wall(400.0D, 80.0D, 800.0D, 20.0D, Color.WHITE, "Top"), new Wall(400.0D, 590.0D, 800.0D, 20.0D, Color.WHITE, "Bottom")};
      Wall[] var1 = Map.getWalls();
      ArrayList var2 = new ArrayList();
      var2.addAll(List.of(var0));
      var2.addAll(List.of(var1));
      return (Wall[])var2.toArray(new Wall[0]);
   }

   private static Score[] initScores() {
      Score[] var0 = new Score[]{new Score("Player 1"), new Score("Player 2")};
      return var0;
   }
}
