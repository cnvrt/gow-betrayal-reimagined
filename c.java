import com.nokia.mid.ui.DirectUtils;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class c {
   private static final short[] k = new short[]{0, 16384, 8192, 24576};
   private Image l;
   public short a;
   public short b;
   public short c;
   public short d;
   public short e;
   public short f;
   public short g;
   public short h;
   public short i;
   public short j;

   public c(Image var1) {
      this.l = var1;
   }

   public final void a(short[] var1) {
      this.a = var1[0];
      this.b = var1[1];
      this.i = var1[2];
      this.j = var1[3];
      this.c = var1[4];
      this.d = var1[5];
      this.e = var1[6];
      this.f = var1[7];
      this.g = var1[8];
      this.h = var1[9];
   }

   public final void a(Graphics var1, int var2, int var3, int var4) {
      var2 += (var4 & 2) == 0 ? this.a : -(this.a + this.c);
      var3 += (var4 & 1) == 0 ? this.b : -(this.b + this.d);
      int var5 = var1.getClipX();
      int var6 = var1.getClipY();
      int var7 = var1.getClipWidth();
      int var8 = var1.getClipHeight();
      int var9 = 0;
      int var10 = 0;
      if (var4 != 0) {
         var9 = var1.getTranslateX();
         var10 = var1.getTranslateY();
         if (var9 != 0 || var10 != 0) {
            var1.translate(-var9, -var10);
            var2 += var9;
            var3 += var10;
            var1.setClip(var5 + var9, var6 + var10, var7, var8);
         }
      }

      var1.clipRect(var2, var3, this.c, this.d);
      var2 += (var4 & 2) == 0 ? -this.i : this.i + this.c - this.l.getWidth();
      var3 += (var4 & 1) == 0 ? -this.j : this.j + this.d - this.l.getHeight();
      if (var4 == 0) {
         var1.drawImage(this.l, var2, var3, 20);
      } else {
         DirectUtils.getDirectGraphics(var1).drawImage(this.l, var2, var3, 20, k[var4]);
      }

      if (var4 != 0 && (var9 != 0 || var10 != 0)) {
         var1.translate(var9, var10);
      }

      var1.setClip(var5, var6, var7, var8);
   }

   public final int a(int var1) {
      return (var1 & 2) == 0 ? this.a : -(this.a + this.c);
   }

   public final int b(int var1) {
      return (var1 & 1) == 0 ? this.b : -(this.b + this.d);
   }
}
