public class Collision {
   double time;
   double normalX;
   double normalY;

   Collision(double var1, double var3, double var5) {
      this.time = var1;
      this.normalX = var3;
      this.normalY = var5;
   }

   public static boolean checkCollision(ISolid var0, ISolid var1) {
      return checkCollision(ISolid.toBox(var0), ISolid.toBox(var1));
   }

   static Collision collideDynamic(Collision.Box var0, Collision.Box var1, double var2, double var4) {
      try {
         var0.x += var2;
         var0.y += var4;
         if (!checkCollision(var0, var1)) {
            Collision var6 = new Collision(1.0D, 0.0D, 0.0D);
            return var6;
         }
      } finally {
         var0.x -= var2;
         var0.y -= var4;
      }

      double var34 = Double.POSITIVE_INFINITY;
      double var8 = 0.0D;
      double var10 = 0.0D;
      double var12;
      double var16;
      if (var2 > 0.0D) {
         var12 = var1.x - (var0.x + var0.w);
         var16 = var1.x + var1.w - var0.x;
      } else {
         var12 = var1.x + var1.w - var0.x;
         var16 = var1.x - (var0.x + var0.w);
      }

      double var14;
      double var18;
      if (var4 > 0.0D) {
         var14 = var1.y - (var0.y + var0.h);
         var18 = var1.y + var1.h - var0.y;
      } else {
         var14 = var1.y + var1.h - var0.y;
         var18 = var1.y - (var0.y + var0.h);
      }

      double var20;
      double var24;
      if (var2 == 0.0D) {
         var20 = -var34;
         var24 = var34;
      } else {
         var20 = var12 / var2;
         var24 = var16 / var2;
      }

      double var22;
      double var26;
      if (var4 == 0.0D) {
         var22 = -var34;
         var26 = var34;
      } else {
         var22 = var14 / var4;
         var26 = var18 / var4;
      }

      double var28 = Math.max(var20, var22);
      double var30 = Math.min(var24, var26);
      if (!(var28 > var30) && (!(var20 < 0.0D) || !(var22 < 0.0D)) && !(var20 > 1.0D) && !(var22 > 1.0D)) {
         if (var20 > var22) {
            var8 = var12 < 0.0D ? 1.0D : -1.0D;
         } else {
            var10 = var14 < 0.0D ? 1.0D : -1.0D;
         }

         return new Collision(var28, var8, var10);
      } else {
         return new Collision(1.0D, 0.0D, 0.0D);
      }
   }

   private static boolean checkCollision(Collision.Box var0, Collision.Box var1) {
      if (var0.x + var0.width < var1.x) {
         return false;
      } else if (var0.x > var1.x + var1.width) {
         return false;
      } else if (var0.y > var1.y + var1.height) {
         return false;
      } else {
         return !(var0.y + var0.height < var1.y);
      }
   }
}
