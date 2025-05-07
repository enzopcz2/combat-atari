import Collision.Box;

public interface ISolid {
   double getCx();

   double getCy();

   double getWidth();

   double getHeight();

   static Box toBox(ISolid var0) {
      return new Box(var0.getCx(), var0.getCy(), var0.getWidth(), var0.getHeight());
   }
}
