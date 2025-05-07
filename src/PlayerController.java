public class PlayerController {
   private Player player;
   private int forward;
   private int backwards;
   private int rotateLeft;
   private int rotateRight;
   private int fire;

   PlayerController(Player var1, int var2, int var3, int var4, int var5, int var6) {
      this.player = var1;
      this.forward = var2;
      this.backwards = var3;
      this.rotateLeft = var4;
      this.rotateRight = var5;
      this.fire = var6;
   }

   void doInputs(long var1) {
      if (GameLib.isKeyPressed(this.forward)) {
         this.player.moveForwards(var1);
      }

      if (GameLib.isKeyPressed(this.backwards)) {
         this.player.moveBackwards(var1);
      }

      if (GameLib.isKeyPressed(this.rotateLeft)) {
         this.player.rotateLeft(var1);
      }

      if (GameLib.isKeyPressed(this.rotateRight)) {
         this.player.rotateRight(var1);
      }

      if (GameLib.isKeyPressed(this.fire) && this.player.canFire()) {
         this.player.fire();
      }

   }
}
