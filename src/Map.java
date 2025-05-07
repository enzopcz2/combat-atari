import java.awt.Color;
import java.util.Random;

public class Map {
   private static Random rand = new Random();
   private static int lastSpawnPoint;

   Map() {
   }

   static Wall[] getWalls() {
      return new Wall[]{wr(0.15D, 0.5D, 0.02D, 0.15D), wr(0.13D, 0.425D, 0.02D, 0.02D), wr(0.13D, 0.575D, 0.02D, 0.02D), wr(0.85D, 0.5D, 0.02D, 0.15D), wr(0.87D, 0.425D, 0.02D, 0.02D), wr(0.87D, 0.575D, 0.02D, 0.02D), wr(0.3D, 0.5D, 0.1D, 0.1D), wr(0.7D, 0.5D, 0.1D, 0.1D), wr(0.5D, 0.25D, 0.04D, 0.2D), wr(0.5D, 0.75D, 0.04D, 0.2D), wr(0.15D, 0.8D, 0.05D, 0.02D), wr(0.165D, 0.82D, 0.02D, 0.05D), wr(0.85D, 0.8D, 0.05D, 0.02D), wr(0.835D, 0.82D, 0.02D, 0.05D), wr(0.85D, 0.2D, 0.05D, 0.02D), wr(0.835D, 0.18D, 0.02D, 0.05D), wr(0.15D, 0.2D, 0.05D, 0.02D), wr(0.165D, 0.18D, 0.02D, 0.05D)};
   }

   public static double[] getRandomRespawn() {
      double[][] var0 = getRespawns();

      int var1;
      do {
         var1 = rand.nextInt(var0.length);
      } while(var1 == lastSpawnPoint);

      lastSpawnPoint = var1;
      return var0[var1];
   }

   private static double[][] getRespawns() {
      return new double[][]{r(0.11D, 0.11D), r(0.89D, 0.11D), r(0.11D, 0.89D), r(0.89D, 0.89D)};
   }

   private static double[] r(double var0, double var2) {
      double var4 = 800.0D;
      double var6 = 600.0D;
      double var8 = 80.0D;
      return new double[]{var0 * var4, var8 + var2 * (var6 - var8)};
   }

   private static Wall wr(double var0, double var2, double var4, double var6) {
      double var8 = 800.0D;
      double var10 = 600.0D;
      double var12 = 80.0D;
      return new Wall(var0 * var8, var12 + var2 * (var10 - var12), var4 * var8, var6 * var10, new Color(0.5F, 0.5F, 0.5F), "");
   }
}
