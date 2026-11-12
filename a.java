import com.nokia.mid.ui.FullCanvas;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;

public abstract class a extends FullCanvas implements Runnable {
   public int a;
   public int b;
   private long o;
   public MIDlet c;
   public d d;
   private boolean p;
   public boolean e;
   private boolean q;
   private int r = -1;
   private int[] s;
   private byte[] t;
   public byte[] f;
   private boolean[] u;
   private int v;
   private byte[] w;
   private short[] x;
   private c[] y;
   private char[] z;
   private Font A = Font.getFont(0, 0, 0);
   private short[] B;
   private int[][] C;
   private byte[][] D;
   private int[][] E;
   private char[][] F;
   private int[][] G;
   private int[][] H;
   private int[][] I;
   private char[][] J;
   private char[][] K;
   private DataInputStream L;
   private short[] M;
   private int N;
   private static final byte[] O = new byte[32];
   public volatile int g;
   public volatile int h;
   private volatile int P;
   private volatile int Q;
   private volatile int R;
   public boolean i;
   public boolean j;
   private boolean S;
   public int k = 10;
   public int l = 10;
   public int m = 100;
   private boolean T;
   private int U;
   private int V;
   private int W;
   private int X;
   public volatile boolean n;
   private volatile boolean Y;
   private volatile boolean Z;
   private Thread aa;
   private volatile boolean ab;
   private volatile boolean ac;
   private volatile long ad;
   private volatile int ae = -1;
   private int af = 2;
   private volatile boolean ag;

   static {
      O[1] = -1;
      O[2] = -3;
      O[3] = 35;
      O[4] = -8;
      O[5] = -4;
      O[6] = -2;
      O[8] = -5;
      O[9] = -10;
      O[10] = 42;

      for(int var0 = 0; var0 < 10; ++var0) {
         O[16 + var0] = (byte)(48 + var0);
      }

      O[27] = -6;
      O[29] = -7;
   }

   public abstract void a(Graphics var1);

   public abstract void a();

   public static int a(String var0, int var1) {
      try {
         return Integer.parseInt(var0);
      } catch (Exception var2) {
         return var1;
      }
   }

   public static void a(int[] var0, int var1) {
      int var5 = var1--;

      boolean var2;
      do {
         if (var5 != 1) {
            var5 = var5 * 197 >> 8;
         }

         var2 = false;
         int var3 = var1 - var5;

         for(int var4 = var1; var3 >= 0; --var4) {
            if (var0[var3] > var0[var4]) {
               int var6 = var0[var3];
               var0[var3] = var0[var4];
               var0[var4] = var6;
               var2 = true;
            }

            --var3;
         }
      } while(var2 || var5 > 1);

   }

   public static byte[] a(int var0) {
      RecordStore var2 = null;

      byte[] var1;
      try {
         int var3 = (var2 = RecordStore.openRecordStore(String.valueOf(var0), false)).getNumRecords();
         int var4 = 0;

         for(int var5 = 1; var5 <= var3; ++var5) {
            var4 += var2.getRecordSize(var5);
         }

         var1 = new byte[var4];
         var4 = 0;

         for(int var9 = 1; var9 <= var3; ++var9) {
            var4 += var2.getRecord(var9, var1, var4);
         }
      } catch (Throwable var7) {
         var1 = (byte[])null;
      }

      if (var2 != null) {
         try {
            var2.closeRecordStore();
         } catch (Throwable var6) {
         }
      }

      return var1;
   }

   public static boolean a(int var0, byte[] var1) {
      String var2 = String.valueOf(var0);
      RecordStore var3 = null;
      boolean var4 = true;

      try {
         String[] var5;
         if ((var5 = RecordStore.listRecordStores()) != null) {
            for(int var6 = 0; var6 < var5.length; ++var6) {
               if (var2.equals(var5[var6])) {
                  RecordStore.deleteRecordStore(var2);
                  break;
               }
            }
         }

         if (var1 != null) {
            var3 = RecordStore.openRecordStore(var2, true);
            int var12 = (var1.length + 786432 - 1) / 786432;
            int var7 = 0;

            for(int var8 = 1; var8 <= var12; ++var8) {
               int var9;
               if ((var9 = var1.length - var7) > 786432) {
                  var9 = 786432;
               }

               var3.addRecord(var1, var7, var9);
               var7 += var9;
            }
         }
      } catch (Throwable var11) {
         var4 = false;
      }

      if (var3 != null) {
         try {
            var3.closeRecordStore();
         } catch (Throwable var10) {
         }
      }

      return var4;
   }

   public final synchronized void a(boolean var1) {
      if (var1 || !this.p) {
         if (var1) {
            byte[] var3;
            (var3 = new byte[3])[0] = (byte)(this.e ? 1 : 0);
            var3[2] = (byte)(this.q ? 1 : 0);
            a(-9, var3);
            return;
         }

         this.p = true;
         byte[] var2;
         if ((var2 = a((int)-9)) == null || var2.length != 3) {
            var2 = new byte[]{-1, -1, -1};
            a(-9, var2);
         }

         this.e = var2[0] != 0;
         this.q = var2[2] != 0;
      }

   }

   public final c[] b(int var1) {
      return this.b(var1, (byte[])null);
   }

   public final c[] b(int var1, byte[] var2) {
      try {
         DataInputStream var6;
         int var3 = (var6 = this.d(var1)).readChar();
         var6.readByte();
         int var4 = 0;
         byte[] var7 = new byte[var6.readShort() - 4];
         int var5 = var6.readChar();
         char var8 = var6.readChar();
         var6.readFully(var7);
         if (this.u == null) {
            this.u = new boolean[16];
         }

         for(int var17 = 0; var17 < 16; ++var17) {
            this.u[var17] = (var5 >> var17 & 1) == 0;
         }

         var5 = 5;
         var4 = 0;
         int[] var9 = new int[10];

         for(int var10 = 0; var10 < 3; ++var10) {
            for(int var11 = 0; var11 < (var10 == 0 ? 2 : 4); ++var11) {
               var9[var4++] = this.u[var10] ? (this.u[var5++] ? 2 : 1) : 0;
            }
         }

         Image var23 = this.d(var3, var2);
         short[] var24 = new short[10];
         c[] var12 = new c[var8];

         for(int var13 = 0; var13 < var8; ++var13) {
            var12[var13] = new c(var23);
            var5 = 0;

            for(int var14 = 0; var14 < var24.length; ++var14) {
               var4 = 0;
               if ((var3 = var9[var14] - 1) >= 0) {
                  var5 += var13 << var3;
                  if (var3 == 0) {
                     var4 = var7[var5];
                     if (var14 > 1 && var14 != 6 && var14 != 7) {
                        var4 &= 255;
                     }
                  } else {
                     var4 = (var7[var5] & 255) << 8 | var7[var5 + 1] & 255;
                  }

                  var5 += var8 - var13 << var3;
               }

               var24[var14] = (short)var4;
            }

            var12[var13].a(var24);
         }

         return var12;
      } catch (Exception var15) {
         return null;
      }
   }

   public final Image c(int var1, byte[] var2) {
      Image var3 = null;
      this.m(var1);
      var1 &= 1023;
      int var4 = this.l(var1);
      this.a(this.t, var4, var2);
      var3 = Image.createImage(this.t, var4, this.s[var1]);
      this.a(this.t, var4, var2);
      return var3;
   }

   private void a(byte[] var1, int var2, byte[] var3) {
      if (var3 != null) {
         DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(var1, var2, var1.length - var2));

         try {
            this.b(var4);
            int var5 = 0;

            byte var6;
            for(int var8 = var2 + this.v; var5 < var3.length; var3[var5++] = var6) {
               var6 = var1[var8];
               var1[var8++] = var3[var5];
            }

            return;
         } catch (IOException var7) {
         }
      }

   }

   private int b(DataInputStream var1) throws IOException {
      var1.skip(8L);
      this.v = 8;

      while(true) {
         int var2 = var1.readInt();
         int var3 = var1.readInt();
         this.v += 8;
         if (1347179589 == var3) {
            return var2;
         }

         if (1229278788 == var3) {
            return -1;
         }

         var1.skip((long)(var2 + 4));
         this.v += var2 + 4;
      }
   }

   public final byte[] c(int var1) {
      this.m(var1);
      var1 &= 1023;
      byte[] var2 = new byte[this.s[var1]];
      System.arraycopy(this.t, this.l(var1), var2, 0, var2.length);
      return var2;
   }

   public final DataInputStream d(int var1) {
      this.m(var1);
      var1 &= 1023;
      return new DataInputStream(new ByteArrayInputStream(this.t, this.l(var1), this.s[var1]));
   }

   private int l(int var1) {
      int var2 = 0;

      while(true) {
         --var1;
         if (var1 < 0) {
            return var2;
         }

         var2 += this.s[var1];
      }
   }

   private void m(int var1) {
      if ((var1 = var1 >> 10) != 0 && var1 != this.r) {
         if (var1 > 0) {
            try {
               DataInputStream var2 = new DataInputStream(this.getClass().getResourceAsStream("/RP" + var1));
               this.a(var2);
               var2.close();
            } catch (Exception var3) {
            }
         } else {
            this.a((DataInputStream)null);
         }

         this.r = var1;
      }
   }

   public final boolean a(DataInputStream var1) {
      this.s = null;
      this.f = null;
      this.t = null;
      if (var1 == null) {
         this.r = -1;
         return false;
      } else {
         this.r = 0;

         try {
            int var4 = var1.read();
            int var2 = 1 + (var4 & 1) << 1;
            var4 = var1.readChar();
            byte[] var5 = new byte[var2 * var4];
            var1.readFully(var5);
            this.s = new int[var4];
            int var3 = 0;

            for(int var6 = 0; var6 < var4; ++var6) {
               for(int var7 = var2 - 1; var7 >= 0; --var7) {
                  int[] var10000 = this.s;
                  var10000[var6] |= (255 & var5[var3++]) << (var7 << 3);
               }
            }

            this.f = new byte[var4];
            var1.readFully(this.f);
            this.t = new byte[var1.readInt()];
            var1.readFully(this.t);
            return true;
         } catch (Exception var8) {
            return false;
         }
      }
   }

   public final String e(int var1) {
      short var2 = this.x[var1];

      try {
         return new String(this.w, var2, this.x[var1 + 1] - var2, "UTF-8");
      } catch (Exception var3) {
         return null;
      }
   }

   public final void f(int var1) {
      if (var1 == -1) {
         this.w = null;
         this.x = null;
      } else {
         try {
            DataInputStream var2 = this.d(var1);
            this.x = new short[var2.readShort() + 1];
            int var3 = this.s[var1 & 1023];
            this.w = new byte[var3 - (this.x.length << 1)];
            short var4 = 0;

            int var5;
            for(var5 = 0; var5 < this.x.length - 1; ++var5) {
               this.x[var5] = var4;
               if ((var3 = var2.readShort()) > 0) {
                  var2.readFully(this.w, var4, var3);
               }

               var4 += var3;
            }

            this.x[var5] = var4;
         } catch (Exception var6) {
         }
      }
   }

   public static void a(Graphics var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      b(var0, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public static void b(Graphics var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      var0.drawRGB(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public final void a(c[] var1) {
      this.y = var1;
      if (this.y != null && var1[3].h == 255) {
         var1[3].h = 0;
         short var2 = 0;

         for(int var3 = 0; var3 < var1.length; ++var3) {
            c var4 = var1[var3];
            var2 = (short)(var2 + (var4.a << 8) + (var4.b & 255));
            var4.a = var4.e;
            var4.b = var4.f;
            var4.e = var2;
         }
      }

   }

   private void n(int var1) {
      if (this.z == null || this.z.length < var1) {
         this.z = new char[var1];
      }

   }

   public final int a(String var1) {
      return this.a((String)var1, 0, var1.length());
   }

   public final int a(String var1, int var2, int var3) {
      this.n(var3);

      for(int var4 = 0; var4 < var3; ++var4) {
         this.z[var4] = var1.charAt(var2 + var4);
      }

      return this.a((char[])this.z, 0, var3);
   }

   public final int a(char var1) {
      this.n(1);
      this.z[0] = var1;
      return this.a((char[])this.z, 0, 1);
   }

   public final int a(char[] var1, int var2, int var3) {
      if (this.y != null) {
         var3 += var2;

         int var4;
         for(var4 = 0; var2 < var3; ++var2) {
            c var5 = this.b(var1[var2]);
            var4 += var5 != null ? var5.g : this.y[2].h;
         }

         return var4;
      } else {
         return var3 == 0 ? 0 : this.A.charsWidth(var1, var2, var3);
      }
   }

   private c b(char var1) {
      int var2 = 0;
      int var3 = this.y.length - 1;

      while(var2 <= var3) {
         int var4 = var2 + var3 >> 1;
         char var5;
         if ((var5 = (char)this.y[var4].e) < var1) {
            var2 = var4 + 1;
         } else {
            if (var5 <= var1) {
               return this.y[var4];
            }

            var3 = var4 - 1;
         }
      }

      return null;
   }

   public final int b() {
      return this.y != null ? this.y[1].h : this.A.getBaselinePosition();
   }

   public final int c() {
      return this.y != null ? this.y[0].h : this.A.getHeight();
   }

   public final void a(Graphics var1, String var2, int var3, int var4, int var5) {
      this.a(var1, (String)var2, 0, var2.length(), var3, var4, var5);
   }

   public final void a(Graphics var1, String var2, int var3, int var4, int var5, int var6, int var7) {
      this.n(var4);

      for(int var8 = 0; var8 < var4; ++var8) {
         this.z[var8] = var2.charAt(var3 + var8);
      }

      this.a(var1, (char[])this.z, 0, var4, var5, var6, var7);
   }

   public final void a(Graphics var1, char[] var2, int var3, int var4, int var5, int var6, int var7) {
      if ((var7 & 64) != 0) {
         var6 -= this.b();
      } else if ((var7 & 32) != 0) {
         var6 -= this.c();
      }

      if ((var7 & 9) != 0) {
         var5 -= this.a(var2, var3, var4) >> (var7 & 1);
      }

      if (this.y != null) {
         for(int var9 = var4 + var3; var3 < var9; ++var3) {
            c var8;
            if ((var8 = this.b(var2[var3])) != null) {
               var8.a(var1, var5, var6, 0);
               var5 += var8.g;
            } else {
               var5 += this.y[2].h;
            }
         }

      } else {
         if (var4 != 0) {
            if (var1.getFont() != this.A) {
               var1.setFont(this.A);
            }

            var1.drawChars(var2, var3, var4, var5, var6, 20);
         }

      }
   }

   public static void a(Image var0, int var1, int var2, int var3, int var4, Graphics var5, int var6, int var7) {
      int var8 = var5.getClipX();
      int var9 = var5.getClipY();
      int var10 = var5.getClipWidth();
      int var11 = var5.getClipHeight();
      var5.clipRect(var6, var7, var3, var4);
      var5.drawImage(var0, var6 - var1, var7 - var2, 20);
      var5.setClip(var8, var9, var10, var11);
   }

   public static int a(int[] var0, int var1, int var2) {
      int var3 = 0;

      while(var3 <= var1) {
         int var4 = var3 + var1 >> 1;
         int var5;
         if ((var5 = var0[var4]) < var2) {
            var3 = var4 + 1;
         } else {
            if (var5 <= var2) {
               return var4;
            }

            var1 = var4 - 1;
         }
      }

      return var1 + 1;
   }

   public final void g(int var1) {
      if (this.B != null) {
         for(int var2 = 0; var2 < this.B.length; ++var2) {
            if (var1 == (this.B[var2] & 32767)) {
               return;
            }
         }
      }

      this.B = a(this.B, (short)var1);
      int var3 = this.B.length - 1;

      try {
         this.L = this.d(var1);
         this.L.read();
         int var4 = this.L.readChar();
         this.C = a(this.C, var4);
         int[] var5 = this.C[var3];
         var1 = this.a(var5);

         for(int var14 = 0; var14 < var4; ++var14) {
            var5[var14] |= (63 & this.j()) << 10;
         }

         this.D = a(this.D, var1);
         byte[] var6 = this.D[var3];
         this.E = a(this.E, var1);
         var5 = this.E[var3];

         for(int var15 = 0; var15 < var1; ++var15) {
            var4 = this.i();
            var6[var15] = (byte)var4;
            var5[var15] = var4 >> 8;
         }

         this.b(var5);
         char var7 = this.L.readChar();
         this.F = a((char[][])this.F, (int)var7);
         this.G = a((int[][])this.G, (int)var7);
         var1 = this.a(this.G[var3]);
         this.H = a(this.H, var1);
         var5 = this.H[var3];
         int var8 = 0;

         for(int var16 = 0; var16 < var1; ++var16) {
            if (var8 == 0) {
               var4 = this.i();
               var8 = 8;
            }

            var8 -= 2;
            var5[var16] = 3 & var4 >> var8;
         }

         this.b(var5);
         var1 = 0;

         for(int var17 = 0; var17 < var7; ++var17) {
            var4 = this.i();
            int[] var10000 = this.G[var3];
            var10000[var17] |= var4 << 8;
            this.F[var3][var17] = (char)var1;
            var1 += var4;
         }

         this.I = a(this.I, var1);
         this.J = a(this.J, var1);
         var5 = this.I[var3];
         char[] var9 = this.J[var3];

         for(int var18 = 0; var18 < var1; var5[var18++] = this.j() << 21) {
         }

         int var29;
         for(int var19 = 0; var19 < var1; var5[var29] |= this.j() << 21 >>> 11) {
            var29 = var19++;
         }

         for(int var20 = 0; var20 < var1; var5[var29] |= this.i()) {
            var29 = var20++;
         }

         for(int var21 = 0; var21 < var1; var9[var21++] = (char)(this.i() << 6)) {
         }

         for(int var22 = 0; var22 < var1; var9[var29] |= (char)this.i()) {
            var29 = var22++;
         }

         var4 = this.L.readChar();
         this.K = a(this.K, var4);

         for(int var23 = 0; var23 < var4; ++var23) {
            this.K[var3][var23] = (char)this.i();
         }
      } catch (Exception var10) {
      }

      this.L = null;
   }

   private int a(int[] var1) throws Exception {
      int var2 = 0;

      int var3;
      for(int var4 = 0; var4 < var1.length; var2 += var3) {
         var3 = this.i();
         var1[var4++] = var2 << 16 | var3;
      }

      return var2;
   }

   private void b(int[] var1) throws Exception {
      int var2 = var1.length;

      int var10001;
      for(int var3 = 0; var3 < var2; var1[var10001] |= this.i() << 22) {
         var10001 = var3++;
      }

      for(int var4 = 0; var4 < var2; var1[var10001] |= this.j() << 22 >>> 10) {
         var10001 = var4++;
      }

      for(int var5 = 0; var5 < var2; var1[var10001] |= this.j() << 22 >>> 20) {
         var10001 = var5++;
      }

   }

   private int i() throws Exception {
      int var1;
      return (var1 = this.L.read()) == 255 ? this.L.readChar() : var1;
   }

   private int j() throws Exception {
      byte var1;
      return (var1 = this.L.readByte()) == -128 ? this.L.readShort() : var1;
   }

   private static short[] a(short[] var0, short var1) {
      if (var0 == null) {
         var0 = new short[0];
      }

      short[] var2 = new short[var0.length + 1];
      System.arraycopy(var0, 0, var2, 0, var0.length);
      var2[var0.length] = var1;
      return var2;
   }

   private static int[][] a(int[][] var0, int var1) {
      if (var0 == null) {
         var0 = new int[0][];
      }

      int[][] var2 = new int[var0.length + 1][];
      System.arraycopy(var0, 0, var2, 0, var0.length);
      var2[var0.length] = new int[var1];
      return var2;
   }

   private static byte[][] a(byte[][] var0, int var1) {
      if (var0 == null) {
         var0 = new byte[0][];
      }

      byte[][] var2 = new byte[var0.length + 1][];
      System.arraycopy(var0, 0, var2, 0, var0.length);
      var2[var0.length] = new byte[var1];
      return var2;
   }

   private static char[][] a(char[][] var0, int var1) {
      if (var0 == null) {
         var0 = new char[0][];
      }

      char[][] var2 = new char[var0.length + 1][];
      System.arraycopy(var0, 0, var2, 0, var0.length);
      var2[var0.length] = new char[var1];
      return var2;
   }

   private static short[] a(short[] var0, int var1) {
      if (var0 == null) {
         var0 = new short[0];
      }

      if (var1 > var0.length) {
         short[] var2 = new short[var1];
         System.arraycopy(var0, 0, var2, 0, var0.length);
         var0 = var2;
      }

      return var0;
   }

   public final void a(int var1, boolean var2) {
      int var3;
      for(var3 = 0; var1 != (this.B[var3] & 32767); ++var3) {
      }

      if (var2) {
         short[] var4 = this.B;
         var4[var3] = (short)(var4[var3] | '耀');
      } else {
         short[] var10000 = this.B;
         var10000[var3] = (short)(var10000[var3] & 32767);
      }
   }

   private static void a(Object[] var0, char[] var1, Object[] var2) {
      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = var0[var1[var3]];
      }

   }

   public final void d() {
      int var2 = 0;
      if (this.B != null) {
         for(int var1 = 0; var1 < this.B.length; ++var1) {
            if (this.B[var1] < 0) {
               ++var2;
            }
         }
      }

      if (var2 > 0) {
         char[] var3 = new char[var2];
         short[] var4 = this.B;
         this.B = new short[var2];
         int var6 = 0;

         for(int var5 = 0; var5 < var4.length; ++var5) {
            if (var4[var5] < 0) {
               var3[var6] = (char)var5;
               this.B[var6++] = var4[var5];
            }
         }

         a(this.C, var3, this.C = new int[var2][]);
         a(this.D, var3, this.D = new byte[var2][]);
         a(this.E, var3, this.E = new int[var2][]);
         a(this.F, var3, this.F = new char[var2][]);
         a(this.G, var3, this.G = new int[var2][]);
         a(this.H, var3, this.H = new int[var2][]);
         a(this.I, var3, this.I = new int[var2][]);
         a(this.J, var3, this.J = new char[var2][]);
         a(this.K, var3, this.K = new char[var2][]);
      } else {
         this.B = null;
         this.C = null;
         this.D = null;
         this.E = null;
         this.F = null;
         this.G = null;
         this.H = null;
         this.I = null;
         this.J = null;
         this.K = null;
         this.M = null;
      }
   }

   public final void h(int var1) {
      this.M = a(this.M, var1 * 8);
   }

   public final void a(int var1, int var2, int var3) {
      int var4;
      for(var4 = 0; var2 != (this.B[var4] & 32767); ++var4) {
      }

      var1 *= 8;
      this.M = a(this.M, var1 + 8);
      this.M[var1 + 0] = (short)var4;
      this.M[var1 + 1] = (short)var3;
      this.M[var1 + 2] = 0;
      this.M[var1 + 3] = (short)(this.C[var4][var3] << 16 >> 26);
      this.M[var1 + 4] = 0;
      this.M[var1 + 5] = 0;
      this.M[var1 + 6] = 0;
      this.M[var1 + 7] = 0;
   }

   public final void a(int var1, int var2) {
      var1 *= 8;
      var2 *= 8;
      this.M = a(this.M, var2 + 8);
      System.arraycopy(this.M, var1, this.M, var2, 8);
   }

   public final boolean b(int var1, int var2) {
      var1 *= 8;
      int var3;
      if ((var3 = this.M[var1 + 3]) == 0) {
         this.a = 0;
         this.b = 0;
         return false;
      } else {
         short var4 = this.M[var1 + 0];
         int var5 = this.M[var1 + 2];
         var2 += this.M[var1 + 4] & '\uffff';
         int var6 = this.M[var1 + 5];
         int var7 = this.M[var1 + 6];
         int var8;
         int var9 = (var8 = this.C[var4][this.M[var1 + 1]]) >>> 16;
         int var10 = var8 & 1023;
         int var11 = var5;
         int var13 = var5 == this.M[var1 + 7] ? 0 : this.D[var4][var9 + var5] >> 6 & 3;

         char var12;
         while(var2 >= (var12 = this.K[var4][this.D[var4][var9 + var5] & 63])) {
            var2 -= var12;
            ++var5;
            if (var5 == var10) {
               if (var3 > 0) {
                  --var3;
                  if (var3 == 0) {
                     --var5;
                     var2 = var12;
                     break;
                  }
               }

               var5 = 0;
               var8 = this.E[var4][var9 + (var10 - 1)];
               var6 -= var8 << 10 >> 22;
               var7 -= var8 << 20 >> 22;
            }

            int var14;
            if ((var14 = this.D[var4][var9 + var5] >> 6 & 3) >= var13) {
               var13 = var14;
               var11 = var5;
            }
         }

         int var26 = (var8 = this.E[var4][var9 + var5]) << 10 >> 22;
         int var15 = var8 << 20 >> 22;
         if ((var8 & 3) > 0 && var3 != 0 && (var5 + 1 != var10 || var3 != 1)) {
            int var16;
            int var17;
            if (var5 + 1 == var10) {
               var16 = (var8 = this.E[var4][var9 + var5]) << 10 >> 22;
               var17 = var8 << 20 >> 22;
               var8 = 0;
            } else {
               var16 = 0;
               var17 = 0;
               var8 = var5 + 1;
            }

            var8 = this.E[var4][var9 + var8];
            var16 += var8 << 10 >> 22;
            var17 += var8 << 20 >> 22;
            int var18 = (var2 << 12) / var12;
            var26 += (var16 - var26) * var18 >> 12;
            var15 += (var17 - var15) * var18 >> 12;
         }

         this.a = var26 - var6;
         this.b = var15 - var7;
         this.M[var1 + 2] = (short)var5;
         this.M[var1 + 3] = (short)var3;
         this.M[var1 + 4] = (short)var2;
         this.M[var1 + 5] = (short)var26;
         this.M[var1 + 6] = (short)var15;
         this.M[var1 + 7] = (short)var11;
         return var3 != 0;
      }
   }

   public final void a(c[] var1, int var2, int var3, int var4, int var5, int[] var6) {
      var2 = this.o(var2);
      int var19;
      int var7 = (var19 = this.G[this.N][var2]) >>> 16;
      int[] var8 = this.H[this.N];
      int var9 = var7 + (var19 & 255);
      int var10 = Integer.MAX_VALUE;
      int var11 = Integer.MAX_VALUE;
      int var12 = Integer.MIN_VALUE;

      int var13;
      c var15;
      int var17;
      for(var13 = Integer.MIN_VALUE; var7 < var9; var13 = Math.max(var13, var17 + var15.d)) {
         int var14 = (var2 = var8[var7++]) & 3 ^ var5;
         int var16 = (var15 = var1[var2 >>> 22]).a + (var2 << 10 >> 22);
         if ((var14 & 2) != 0) {
            var16 = -(var16 + var15.c);
         }

         var17 = var15.b + (var2 << 20 >> 22);
         if ((var14 & 1) != 0) {
            var17 = -(var17 + var15.d);
         }

         var10 = Math.min(var10, var16);
         var11 = Math.min(var11, var17);
         var12 = Math.max(var12, var16 + var15.c);
      }

      var6[0] = var3 + var10;
      var6[1] = var4 + var11;
      var6[2] = var12 - var10;
      var6[3] = var13 - var11;
   }

   public final int a(short[] var1, int var2, int var3) {
      var2 = this.o(var2);
      int var11;
      int var4 = (var11 = this.G[this.N][var2]) >>> 16;
      int[] var5 = this.H[this.N];
      int var6 = var11 & 255;
      int var7 = var4 + var6;

      int var9;
      for(int var8 = 0; var4 < var7 && var8 + 3 < var1.length; var1[var8++] = (short)var9) {
         var2 = var5[var4++];
         var1[var8++] = (short)(var2 >>> 22);
         var9 = var2 & 3 ^ var3;
         var1[var8++] = (short)(((var9 & 2) == 0 ? 1 : -1) * (var2 << 10 >> 22));
         var1[var8++] = (short)(((var9 & 1) == 0 ? 1 : -1) * (var2 << 20 >> 22));
      }

      return var6;
   }

   public final void a(Graphics var1, c[] var2, int var3, int var4, int var5, int var6) {
      this.b(var1, var2, this.o(var3), var4, var5, var6);
   }

   private void b(Graphics var1, c[] var2, int var3, int var4, int var5, int var6) {
      int var13;
      int var7 = (var13 = this.G[this.N][var3]) >>> 16;
      int[] var8 = this.H[this.N];

      int var11;
      c var12;
      for(int var9 = var7 + (var13 & 255); var7 < var9; var12.b = (short)(var12.b - var11)) {
         int var10 = (var3 = var8[var7++]) << 10 >> 22;
         var11 = var3 << 20 >> 22;
         c var10000 = var12 = var2[var3 >>> 22];
         var10000.a = (short)(var10000.a + var10);
         var12.b = (short)(var12.b + var11);
         var12.a(var1, var4, var5, var3 & 3 ^ var6);
         var12.a = (short)(var12.a - var10);
      }

   }

   public final int b(short[] var1, int var2, int var3) {
      var2 = this.o(var2);
      int var4 = this.F[this.N][var2];
      int var5 = this.G[this.N][var2] >> 8 & 255;
      int var6 = var4 + var5;

      int var12;
      for(int var7 = 0; var4 < var6 && var7 + 4 < var1.length; var1[var7++] = (short)var12) {
         int var8 = (var2 = this.I[this.N][var4]) >> 21;
         int var9 = var2 << 11 >> 21;
         int var10 = var2 & 1023;
         int var11 = (var2 = this.J[this.N][var4++]) >> 6;
         var12 = var2 & 63;
         if ((var3 & 2) != 0) {
            var8 = -(var8 + var10);
         }

         if ((var3 & 1) != 0) {
            var9 = -(var9 + var11);
         }

         var1[var7++] = (short)var8;
         var1[var7++] = (short)var9;
         var1[var7++] = (short)var10;
         var1[var7++] = (short)var11;
      }

      return var5;
   }

   private int o(int var1) {
      var1 *= 8;
      this.N = this.M[var1 + 0];
      int var2 = this.M[var1 + 1];
      var2 = (this.C[this.N][var2] >>> 16) + this.M[var1 + 7];
      return this.E[this.N][var2] >>> 22;
   }

   public final synchronized void e() {
      this.P = 0;
      this.Q = 0;
      this.R |= this.g;
      this.h = 0;
      this.g = 0;
   }

   public final synchronized void f() {
      this.h = this.P;
      this.P = 0;
      this.R &= this.Q;
      this.Q &= ~this.R;
      this.g = this.h | this.Q;
      this.R = 0;
   }

   private void b(int var1, boolean var2) {
      if (var1 != 0) {
         synchronized(this) {
            for(int var4 = 1; var4 < O.length; ++var4) {
               if (O[var4] == var1) {
                  int var5 = 1 << var4;
                  if (var2) {
                     this.P |= var5;
                     this.Q |= var5;
                     this.R &= ~var5;
                  } else {
                     this.R |= var5;
                  }
               }
            }

         }
      }
   }

   public void keyPressed(int var1) {
      if (this.j && this.S) {
         this.j = false;
         this.e = this.i;
         this.ag = true;
      }

      var1 = p(var1);
      this.b(var1, true);
      super.keyPressed(var1);
   }

   public void keyReleased(int var1) {
      var1 = p(var1);
      this.b(var1, false);
      super.keyReleased(var1);
   }

   public void keyRepeated(int var1) {
      var1 = p(var1);
      super.keyRepeated(var1);
   }

   private static int p(int var0) {
      return var0;
   }

   public final void g() {
      this.k = this.l;
      this.T = true;
   }

   public final void paint(Graphics var1) {
      if (this.Z) {
         if (176 != this.U || 208 != this.V) {
            if (this.n) {
               int var3 = 208 - this.V + 1 >>> 1;
               var1.setColor(-16777216);
               if (var3 > 0) {
                  if (this.X > 0) {
                     var1.fillRect(0, 0, 176, this.X);
                  }

                  var1.fillRect(0, 208 - var3, 176, var3);
               }

               if ((var3 = 176 - this.U + 1 >>> 1) > 0) {
                  if (this.W > 0) {
                     var1.fillRect(0, this.X, this.W, this.V);
                  }

                  var1.fillRect(176 - var3, this.X, var3, this.V);
               }
            }

            var1.translate(this.W, this.X);
         }

         this.b(var1);
         this.a(var1);
         this.Z = false;
      }
   }

   public final void c(int var1, int var2) {
      this.Y = true;
      if (var1 <= 0) {
         var1 = 176;
      }

      if (var2 <= 0) {
         var2 = 208;
      }

      this.U = var1;
      this.V = var2;
      this.W = 176 - var1;
      this.X = 208 - var2;
      if (this.W < 0) {
         ++this.W;
      }

      if (this.X < 0) {
         ++this.X;
      }

      this.W >>= 1;
      this.X >>= 1;
   }

   public final void b(Graphics var1) {
      var1.setClip(0, 0, this.U, this.V);
   }

   public final void a(Graphics var1, int var2, int var3, int var4, int var5) {
      if ((this.W | this.X) != 0) {
         if (var2 < 0) {
            var4 += var2;
            var2 = 0;
         }

         int var6;
         if ((var6 = var2 + var4 - this.U) > 0) {
            var4 -= var6;
         }

         if (var3 < 0) {
            var5 += var3;
            var3 = 0;
         }

         int var7;
         if ((var7 = var3 + var5 - this.V) > 0) {
            var5 -= var7;
         }
      }

      var1.setClip(var2, var3, var4, var5);
   }

   public final void h() {
      this.Y = true;
   }

   public void i(int var1) {
      if (var1 == 3) {
         if (this.d != null) {
            this.d.a();
         }

         this.ac = true;
         this.c.notifyDestroyed();
      }

      if (var1 == 0 || var1 == 1 || var1 == 2) {
         this.Y = true;
         this.T = true;
         this.e();
      }

   }

   public final void hideNotify() {
      this.j(1);
   }

   public final void showNotify() {
      this.j(2);
      if (this.aa == null) {
         this.aa = new Thread(this);
         this.aa.start();
      }

   }

   public final synchronized void j(int var1) {
      if (var1 == 3) {
         this.i(var1);
      } else if (this.ab && (var1 == 1 || var1 == 2)) {
         if (var1 == 1) {
            this.S = false;
            if (!this.j) {
               this.j = true;
               this.i = this.e;
               this.e = false;
            }
         }

         synchronized(this.aa) {
            this.ae = var1;
            this.ad = System.currentTimeMillis();
         }
      }
   }

   private void k() {
      while(this.ae != -1) {
         if (this.af != 1) {
            if (this.d != null) {
               this.d.c();
            }

            this.i(1);
            this.af = 1;
         }

         synchronized(this.aa) {
            if (this.ae == 1) {
               this.ae = -1;
            }
         }

         while(this.ae == 2 && ((Displayable)this).isShown()) {
            if ((int)(System.currentTimeMillis() - this.ad) < 750) {
               try {
                  Thread.sleep(250L);
                  Thread.yield();
               } catch (Exception var3) {
               }
            } else {
               synchronized(this.aa) {
                  if (this.ae != 2) {
                     continue;
                  }

                  this.ae = -1;
               }

               if (this.d != null) {
                  this.d.c();
               }

               this.i(2);
               this.af = 2;
               this.S = true;
            }
         }
      }

      if (this.ag) {
         this.ag = false;
         this.i(5);
      }

   }

   public final boolean b(String var1) {
      if (this.d != null) {
         this.d.c();
      }

      try {
         if (this.c.platformRequest(var1)) {
            this.j(3);
         }

         return true;
      } catch (Throwable var2) {
         return false;
      }
   }

   public final Image d(int var1, byte[] var2) {
      return this.c(var1, var2);
   }

   public final byte[] k(int var1) {
      return this.c(var1);
   }

   public final void run() {
      this.ab = true;
      this.i(0);

      for(; !this.ac; Thread.yield()) {
         this.k();
         if (!((Displayable)this).isShown()) {
            try {
               Thread.sleep(100L);
            } catch (Exception var6) {
            }

            this.T = true;
         } else {
            long var1 = System.currentTimeMillis();
            if (this.T) {
               this.T = false;
               this.k = this.l;
            } else {
               int var3;
               if ((var3 = (int)(var1 - this.o)) > this.m || var3 < 0) {
                  var3 = this.m;
               }

               this.k = var3;
               if (this.k < this.l) {
                  int var4;
                  if ((var4 = this.l - this.k) < 10) {
                     var4 = 10;
                  }

                  this.k = this.l;

                  try {
                     Thread.sleep((long)var4);
                  } catch (Exception var9) {
                  }
               }
            }

            this.o = var1;
            this.a();
            if (this.d != null) {
               this.d.run();
            }

            try {
               Thread.sleep(20L);
            } catch (Exception var8) {
            }

            if (this.ac) {
               return;
            }

            if (((Displayable)this).isShown()) {
               this.n = this.Y;
               this.Y = false;
               this.Z = true;
               long var10 = System.currentTimeMillis();

               do {
                  ((Canvas)this).repaint();
                  ((Canvas)this).serviceRepaints();
                  if (this.Z) {
                     if ((int)(System.currentTimeMillis() - var10) > 250 || !((Displayable)this).isShown()) {
                        this.Y = true;
                        break;
                     }

                     Thread.yield();
                     if (!this.Z) {
                        break;
                     }

                     try {
                        Thread.sleep(20L);
                     } catch (Exception var7) {
                     }
                  }
               } while(this.Z);

               this.Z = false;
            }
         }
      }

   }

   public a(MIDlet var1) {
      this.c = var1;
      this.c(176, 208);
   }
}
