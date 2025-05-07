class Collision$Box {
   double x;
   double y;
   double w;
   double width;
   double height;
   double h;

   Collision$Box(double var1, double var3, double var5, double var7) {
      this.x = var1 - var5 / 2.0D;
      this.y = var3 - var7 / 2.0D;
      this.width = var5;
      this.height = var7;
      this.w = var5;
      this.h = var7;
   }

   public double cx() {
      return this.x + this.w / 2.0D;
   }

   public double cy() {
      return this.y + this.h / 2.0D;
   }

   public String toString() {
      return String.format("[%.0f, %.0f, %.0f, %.0f]", this.x, this.y, this.w, this.h);
   }
}
