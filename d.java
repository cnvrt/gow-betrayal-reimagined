import com.nokia.mid.sound.Sound;
import java.io.ByteArrayInputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;

public final class d implements Runnable {
   private a a;
   private int b;
   private volatile boolean c;
   private int d;
   private int e;
   private byte[][] f;
   private int[] g;
   private int[] h;
   private byte[] i;
   private Sound j;
   private int k;
   private Player l;
   private int m;

   public final synchronized void a() {
      if (!this.c) {
         this.c = true;
         this.b(-1);
      }

   }

   public final boolean b() {
      this.a.a(false);
      if (!this.c) {
         if (this.a.j) {
            if (this.a.i) {
               return true;
            }
         } else if (this.a.e) {
            return true;
         }
      }

      return false;
   }

   public final void a(boolean var1) {
      if (this.b() != var1) {
         if (this.a.j) {
            this.a.i = var1;
            return;
         }

         this.c();
         this.a.e = var1;
         this.a.a(true);
      }

   }

   private static String a(byte var0) {
      if (var0 == 14) {
         return "video/3gp";
      } else if (var0 == 16) {
         return "video/h263-2000";
      } else if (var0 == 19) {
         return "video/mpeg4";
      } else if (var0 == 20) {
         return "video/x-pn-realvideo";
      } else if (var0 == 1) {
         return "audio/x-wav";
      } else if (var0 == 2) {
         return "audio/mpeg";
      } else if (var0 == 3) {
         return "audio/amr";
      } else if (var0 == 5) {
         return "audio/basic";
      } else if (var0 == 7) {
         return "application/vnd.nokia.ringing-tone";
      } else {
         return var0 == 8 ? "audio/x-tone-seq" : "audio/midi";
      }
   }

   public final synchronized void run() {
      int var1;
      if (this.b() && this.a.e && (var1 = this.d) >= 0) {
         if (this.e >= 0) {
            this.c();
         }

         if (this.m != var1 && this.l != null) {
            this.l.close();
            this.l = null;
            this.m = -1;
         }

         if (this.k != var1 && this.j != null) {
            this.j.release();
            this.j = null;
            this.k = -1;
         }

         this.d = -1;
         if (this.i[var1] == 7) {
            if (this.j == null) {
               this.j = new Sound(this.f[var1], 1);
               this.k = var1;
            }

            int var2 = this.g[var1];
            this.j.play(var2 == -1 ? 0 : var2);
         } else {
            if (this.l != null && this.l.getState() != 0) {
               try {
                  this.l.setMediaTime(0L);
               } catch (Throwable var5) {
               }
            } else {
               try {
                  this.l = null;
                  this.l = Manager.createPlayer(new ByteArrayInputStream(this.f[var1]), a(this.i[var1]));
                  this.m = var1;
               } catch (Throwable var6) {
                  return;
               }
            }

            try {
               this.l.setLoopCount(this.g[var1]);
            } catch (Throwable var4) {
            }

            try {
               this.l.start();
            } catch (Throwable var3) {
               return;
            }
         }

         this.e = var1;
      }

   }

   public final synchronized void a(int var1, int var2, int var3, int var4) {
      if (!this.c) {
         if (this.f[var1] != null) {
            this.b(var1);
         }

         byte[] var5 = this.a.k(var2);
         this.i[var1] = this.a.f[var2 & 1023];
         this.f[var1] = var5;
         this.g[var1] = var3;
         this.h[var1] = var4;
      }

   }

   private synchronized void b(int var1) {
      if (var1 == -1) {
         for(int var2 = 0; var2 < this.b; ++var2) {
            this.b(var2);
         }

      } else {
         if (this.e == var1 || this.d == var1) {
            this.c();
         }

         if (this.m == var1 && this.l != null) {
            this.l.close();
            this.l = null;
            this.m = -1;
         }

         if (this.k == var1 && this.j != null) {
            this.j.release();
            this.j = null;
            this.k = -1;
         }

         this.f[var1] = null;
      }
   }

   public final void a(int var1) {
      if (this.b() && this.a.e && this.f[var1] != null && (this.d < 0 || this.h[this.d] <= this.h[var1])) {
         int var2;
         if ((var2 = this.e) >= 0) {
            if (this.m == var2) {
               Player var3;
               if ((var3 = this.l) != null && var3.getState() == 400 && this.h[var2] > this.h[var1]) {
                  return;
               }
            } else {
               Sound var4;
               if (this.k == var2 && (var4 = this.j) != null && var4.getState() == 0 && this.h[var2] > this.h[var1]) {
                  return;
               }
            }
         }

         this.d = var1;
      }

   }

   public final synchronized void c() {
      if (this.e >= 0) {
         if (this.l != null) {
            try {
               if (this.l.getState() == 400) {
                  this.l.stop();
               }
            } catch (Throwable var2) {
            }
         }

         if (this.j != null) {
            try {
               if (this.j.getState() == 0) {
                  this.j.stop();
               }
            } catch (Throwable var1) {
            }
         }
      }

      this.d = -1;
      this.e = -1;
   }

   public d(a var1, int var2) {
      this.a = var1;
      this.d = -1;
      this.e = -1;
      this.b = var2;
      this.f = new byte[var2][];
      this.i = new byte[var2];
      this.g = new int[var2];
      this.h = new int[var2];
      this.k = -1;
      this.m = -1;
      var1.d = this;
   }
}
