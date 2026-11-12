import java.io.DataInputStream;
import java.util.Random;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

public final class e extends a {
   private static boolean iQ = false;
   private final d iR;
   public c[] o;
   public c[] p;
   public c[] q;
   public c[] r;
   public int s;
   public int t;
   public String u;
   public int v = 0;
   public boolean w = false;
   public static int x = 0;
   public static final int[] y = new int[]{10240, 12288, 33792, 29696, 30720, 32768};
   public static final int[] z = new int[]{23, 24, 21, 20, 22, 19};
   public static int A;
   public static int B;
   public boolean C;
   public boolean D = false;
   public boolean E = false;
   public int F = 176;
   public int G = 208;
   public static int H;
   public static int I;
   public static int J;
   public int K;
   public int L = 0;
   public static int M;
   public static int N;
   public int O;
   public static Random P;
   private static boolean iS = false;
   private b iT;
   private Image iU;
   private static int iV;
   private static int iW;
   public int[] Q = new int[4];
   private c[] iX;
   public int R = 0;
   private static boolean iY = false;
   private static boolean iZ = false;
   public static int[] S;
   public static byte[] T;
   public int U;
   public int V;
   public int W;
   public boolean X;
   public boolean Y;
   public boolean Z;
   public boolean aa;
   public int ab;
   public int ac;
   public int ad;
   public int ae;
   public boolean af;
   public int ag;
   public int ah;
   public boolean ai;
   public boolean aj;
   public boolean ak;
   public boolean al;
   public boolean am;
   public boolean an;
   public boolean ao;
   public boolean ap;
   public boolean aq;
   public boolean ar;
   public static byte as = 4;
   public boolean at;
   public int au;
   public boolean av;
   public int aw = 0;
   public int ax = 0;
   public int ay = 200;
   public int az = -1;
   public int aA = -1;
   public int aB;
   public int aC;
   public int aD;
   public int aE = -1;
   public int[] aF;
   public int[] aG;
   public boolean[] aH;
   public boolean[] aI;
   public int aJ = 0;
   public int aK = 0;
   public int aL = 45;
   public static c[] aM;
   public int aN;
   public int aO;
   public int aP;
   public int aQ;
   public int[] aR = new int[2];
   public boolean aS = false;
   public boolean aT;
   public int aU = -1;
   public c[] aV;
   public static final byte[] aW = new byte[]{11, 2, 2, 2, 2};
   public boolean aX = false;
   public int aY;
   public int[] aZ = new int[40];
   public int[] ba = new int[40];
   public int bb;
   public int bc;
   public boolean bd;
   public int be;
   public boolean bf = false;
   public boolean bg = false;
   public int bh = 0;
   public static byte bi = 3;
   public boolean bj;
   public static byte bk = 0;
   public boolean bl = false;
   public int bm = 0;
   public int bn = 0;
   public int bo;
   public static String bp;
   public int bq;
   public int br;
   public static c[] bs;
   public int bt = 0;
   public int bu;
   private byte[] ja;
   private short[] jb;
   private static int jc;
   private static int jd;
   private static int je;
   private static int jf;
   private static int jg;
   public int bv;
   public int[] bw;
   public int[] bx;
   public int by;
   public int bz;
   public int bA;
   public int bB;
   public int bC;
   public int bD;
   public int bE = -1;
   public static final int[] bF = new int[]{9217, 19456, 20480, 21504, 22528, 23552, 24576, 25600, 26624, 27648};
   public int bG;
   public int bH;
   public int bI;
   public String bJ;
   public static final int[] bK = new int[]{-1, -1, -1, 2, 0, 1, 1, 0, 2, 2};
   private byte[] jh;
   private int ji;
   private int jj;
   private int jk;
   public c[] bL;
   public static int[] bM = new int[10];
   public int[] bN = new int[10];
   public int[] bO = new int[10];
   public int[] bP = new int[10];
   public int bQ = -1;
   public c[] bR;
   public int[] bS = new int[10];
   public static int[] bT = new int[10];
   public int[] bU = new int[10];
   public static int[] bV = new int[10];
   public int[] bW = new int[10];
   public int[] bX = new int[10];
   public int[] bY = new int[10];
   public boolean[] bZ = new boolean[10];
   public static int ca = -1;
   public c[] cb;
   public int[] cc = new int[20];
   public static int[] cd = new int[20];
   public static int[] ce = new int[20];
   public int[] cf = new int[20];
   public int[] cg = new int[20];
   public static int ch = -1;
   public c[] ci;
   public int[] cj = new int[20];
   public int[] ck = new int[20];
   public static int[] cl = new int[20];
   public int[] cm = new int[20];
   public int[] cn = new int[20];
   public static int co = -1;
   public int[] cp;
   public static int[] cq;
   public int[] cr;
   public int[] cs;
   public c[] ct;
   public c[] cu;
   public int cv = -1;
   public int cw;
   public int cx;
   public int cy;
   public static int cz;
   public static c[] cA;
   public int cB = -1;
   public int[] cC;
   public static int[] cD;
   public boolean[] cE;
   public int[] cF;
   public int[] cG;
   public boolean[] cH;
   public static int[] cI;
   public int[] cJ;
   public static int[] cK;
   public int[] cL;
   public static int cM = -1;
   public c[] cN;
   public static int[] cO;
   public static int[] cP;
   public static int[] cQ;
   public int[] cR;
   public static int[] cS;
   public static int[] cT;
   public int[] cU;
   public int[] cV;
   public int[] cW;
   public static int[] cX;
   public int[] cY;
   public static boolean[] cZ;
   public boolean[] da;
   public byte[] db;
   public int[] dc;
   public static int[] dd;
   public int[] de;
   public int[] df;
   public int[] dg;
   public static int[] dh;
   public int[] di;
   public static int[] dj;
   public int[] dk;
   public c[] dl;
   public int[] dm = new int[10];
   public int[] dn = new int[10];
   public int[] do = new int[10];
   public static int[] dp = new int[10];
   public static int[] dq = new int[10];
   public int[] dr = new int[10];
   public int[] ds = new int[10];
   public int dt = -1;
   public c[] du;
   public int[] dv = new int[15];
   public int[] dw = new int[15];
   public static int[] dx = new int[15];
   public int[] dy = new int[15];
   public int[] dz = new int[15];
   public int[] dA = new int[15];
   public static int[] dB = new int[15];
   public int[] dC = new int[15];
   public int dD = -1;
   public boolean dE;
   public boolean dF;
   public c[] dG;
   public static c[] dH;
   public c[] dI;
   public c[] dJ;
   public int dK;
   public int dL = 0;
   public int dM;
   public static int[] dN = new int[15];
   public int[] dO = new int[15];
   public int dP;
   public static int dQ;
   public int dR;
   public byte dS;
   public int[] dT = new int[15];
   public byte[] dU = new byte[15];
   public int[] dV = new int[15];
   public byte[] dW = new byte[15];
   public static int[] dX = new int[15];
   public int[] dY = new int[15];
   public static int[] dZ = new int[15];
   public static int[] ea = new int[15];
   public int[] eb = new int[15];
   public byte[] ec = new byte[15];
   public int[] ed = new int[15];
   public int[] ee = new int[15];
   public static byte[] ef = new byte[15];
   public static byte[] eg = new byte[15];
   public static byte[] eh = new byte[15];
   public short[] ei = new short[15];
   public short[] ej = new short[15];
   public byte[] ek = new byte[15];
   public boolean[] el = new boolean[15];
   public boolean[] em = new boolean[15];
   public boolean[] en = new boolean[15];
   public static int[] eo = new int[15];
   public byte[] ep = new byte[15];
   public int[] eq = new int[15];
   public static int[] er = new int[15];
   public int[] es = new int[15];
   public int[] et = new int[15];
   public boolean[] eu = new boolean[15];
   public static int[] ev = new int[15];
   public static boolean[] ew = new boolean[15];
   public int[] ex = new int[15];
   public static int[] ey = new int[15];
   public static boolean[] ez = new boolean[15];
   public static short[] eA = new short[30];
   public static boolean eB = true;
   public boolean eC;
   public boolean eD = true;
   public static boolean eE = true;
   public int eF;
   public int[] eG;
   public int[] eH;
   public int[] eI;
   public int[] eJ;
   public boolean[] eK;
   public c[] eL;
   public int[] eM;
   public int[] eN;
   public int[] eO;
   public static byte[] eP;
   public c[] eQ;
   public int[] eR;
   public int[] eS;
   public int[] eT;
   public static int[] eU;
   public int[] eV;
   public int[] eW;
   public int[] eX;
   public byte[] eY;
   public byte[] eZ;
   public static boolean[] fa;
   public static int fb = -1;
   public c[] fc;
   public int fd = -1;
   public int[] fe;
   public int[] ff;
   public static int[] fg;
   public static int[] fh;
   public int[] fi;
   public static int[] fj;
   public int[] fk;
   public byte[] fl;
   public byte[] fm;
   public static boolean[] fn;
   public static boolean[] fo;
   public c[] fp;
   public int fq = -1;
   public int fr;
   public int fs;
   public static boolean[] ft;
   public int[] fu;
   public int[] fv;
   public static int[] fw;
   public int[] fx = new int[10];
   public c[] fy;
   public int fz = -1;
   public int fA = 0;
   public c[] fB;
   public static final int[] fC = new int[80];
   public static final int[] fD = new int[80];
   public static final int[] fE = new int[20];
   public static final int[] fF = new int[20];
   public static final int[] fG = new int[20];
   public static final int[] fH = new int[20];
   public static final int[] fI = new int[20];
   public static final int[] fJ = new int[20];
   public int fK = -1;
   public c[] fL;
   public static final byte[] fM = new byte[]{2, 2, 2, 4, 4, 4, 8};
   public static final byte[] fN = new byte[]{4, 4, 10, 10, 18, 18};
   public static final byte[] fO = new byte[]{2, 3, 4, 5, 6};
   public static final int[] fP = new int[]{128, 256, 512, 1024, 1536};
   public int fQ = 0;
   public boolean fR;
   public int fS;
   public int fT;
   public int fU;
   public static boolean fV;
   public int fW;
   public int fX;
   public static final int[] fY = new int[]{2, 4, 5, 6, 8};
   public boolean fZ;
   public int ga;
   public int gb;
   public int gc;
   public int gd;
   public int ge;
   public int gf;
   public static int gg;
   public static int gh;
   public int gi = -1;
   public boolean gj;
   public static boolean gk;
   public static boolean gl;
   public boolean gm;
   public boolean gn;
   public boolean go;
   public byte gp;
   public boolean gq;
   public boolean gr;
   public boolean gs;
   public boolean gt;
   public static boolean gu;
   public static boolean gv;
   public boolean gw;
   public boolean gx;
   public boolean gy;
   public boolean gz;
   public int gA;
   public static int gB;
   public boolean gC;
   public boolean gD;
   public boolean gE;
   public static boolean gF;
   public static int gG;
   public int gH;
   public boolean gI;
   public static int gJ;
   public int gK;
   public static int gL;
   public int gM = 0;
   public int gN = -1;
   public int gO;
   public int gP;
   public static boolean gQ;
   public static byte gR;
   public byte gS;
   public static byte gT;
   public static byte gU;
   public static final int[] gV;
   public static final int[] gW;
   public static final int[] gX;
   public static final int[] gY;
   public static byte gZ;
   public static byte ha;
   public int hb;
   public int hc;
   public int hd;
   public int he;
   public static boolean hf;
   public static boolean hg;
   public boolean hh = false;
   public static int hi;
   public static byte hj;
   public int hk;
   public static int hl;
   public int hm;
   public int hn;
   public int ho;
   public static int hp;
   public int hq;
   public int hr;
   public int hs;
   public int ht;
   public int hu;
   public static int hv;
   public int hw;
   public short[] hx = new short[20];
   public static boolean hy;
   public boolean hz;
   public boolean hA;
   public int hB;
   public int hC;
   public int hD;
   public int hE = -1;
   public static int hF;
   public boolean hG = true;
   public static boolean hH;
   public boolean hI = false;
   public boolean hJ = false;
   public boolean hK = false;
   public int hL = 0;
   public int hM;
   public int hN;
   public int hO = 0;
   public int hP = 0;
   public short[] hQ = new short[256];
   public int hR = 0;
   public int hS;
   public int hT = -1;
   public int hU;
   public boolean hV;
   public static int hW;
   public static int hX;
   public static int hY;
   public static int hZ;
   public static String[] ia;
   public static String[] ib;
   public static byte[] ic;
   public static int id;
   public static int ie;
   public static int if;
   public static boolean ig;
   public static final int[] ih;
   public static final int[] ii;
   public static final int[] ij;
   public static final byte[] ik;
   public static final byte[] il;
   public int im;
   public int in;
   public int io;
   public int ip;
   public int iq;
   public int ir;
   public int is;
   public boolean it;
   public static byte[] iu;
   public static byte[] iv;
   public static int[] iw;
   public static boolean ix;
   public int iy;
   public int iz = -1;
   public static int[] iA;
   public static int iB;
   public int iC;
   public int iD;
   public int iE;
   public boolean iF;
   public boolean iG = false;
   public boolean iH = false;
   public int iI;
   public int iJ;
   public int iK;
   public int iL;
   public int iM;
   public boolean iN = false;
   public int iO;
   public static boolean iP;

   static {
      int[] var10000 = new int[]{320, 420, 690, 400, 500, 590, 1030};
      gL = 0;
      gV = new int[]{1000, 2000, 3000, 4000};
      gW = new int[]{1000, 2000, 3000, 4000};
      gX = new int[]{1000, 2000, 3000, 4000};
      gY = new int[]{1000, 2000, 3000, 4000};
      gZ = 0;
      ha = 0;
      hf = false;
      hg = false;
      hF = -1;
      hH = true;
      var10000 = new int[]{-1, -1, -1};
      byte[] var1 = new byte[]{13, 4, 4};
      ih = new int[]{40, -1, -3, -3, -3};
      ii = new int[]{42, -1, -3, -3, -3};
      ij = new int[]{44, -1, -3, -3, -3};
      ik = new byte[]{13, 4, 2, 2, 2};
      il = new byte[]{13, 4, 4, 4, 4};
      int[] var2 = new int[]{-1, 64};
      ix = true;
   }

   private void i() {
      int var1;
      if ((var1 = super.h) != 0) {
         if ((var1 & 2097408) != 0) {
            this.s = 8;
         } else if ((var1 & 4194336) != 0) {
            this.s = 5;
         } else if ((var1 & 1048580) != 0) {
            this.s = 2;
         } else if ((var1 & 262146) != 0) {
            this.s = 1;
         } else if ((var1 & 16777280) != 0) {
            this.s = 6;
         } else if ((var1 & 131072) != 0) {
            this.s = 17;
         } else if ((var1 & 524288) != 0) {
            this.s = 19;
         } else if ((var1 & 8388608) != 0) {
            this.s = 23;
         } else if ((var1 & 33554432) != 0) {
            this.s = 25;
         } else if ((var1 & 65536) != 0) {
            this.s = 16;
         } else if ((var1 & 1024) != 0) {
            this.s = 10;
         } else if ((var1 & 8) != 0) {
            this.s = 3;
         } else if ((var1 & 134217728) != 0) {
            this.s = 27;
         } else {
            this.s = 0;
         }

         if ((var1 & 603979776) != 0) {
            this.s = 29;
            return;
         }
      } else {
         this.s = 0;
      }

   }

   private void j() {
      int var1;
      if ((var1 = super.g) != 0) {
         if ((var1 & 2097408) != 0) {
            this.t = 8;
         } else if ((var1 & 4194336) != 0) {
            this.t = 5;
         } else if ((var1 & 1048580) != 0) {
            this.t = 2;
         } else if ((var1 & 262146) != 0) {
            this.t = 1;
         } else if ((var1 & 16777280) != 0) {
            this.t = 6;
         } else if ((var1 & 131072) != 0) {
            this.t = 17;
         } else if ((var1 & 524288) != 0) {
            this.t = 19;
         } else if ((var1 & 8388608) != 0) {
            this.t = 23;
         } else if ((var1 & 33554432) != 0) {
            this.t = 25;
         } else if ((var1 & 65536) != 0) {
            this.t = 16;
         } else if ((var1 & 1024) != 0) {
            this.t = 10;
         } else if ((var1 & 8) != 0) {
            this.t = 3;
         } else if ((var1 & 134217728) != 0) {
            this.t = 27;
         } else if ((var1 & 604012544) != 0) {
            this.t = 29;
         } else {
            this.t = 0;
         }
      } else {
         this.t = 0;
      }
   }

   public e(GOWMIDlet var1) {
      super(var1);
      super.m = 100;
      super.l = 20;
      ((a)this).c(176, 208);
      this.iR = new d(this, 12);
      this.iR.a(false);
      J = 0;

      try {
         this.C = a.a((String)((MIDlet)var1).getAppProperty("ENABLECHEATS"), (int)0) == 1;
      } catch (Exception var4) {
         this.C = false;
      }

      try {
         this.bI = a.a((String)((MIDlet)var1).getAppProperty("DEMO"), (int)0);
         this.bJ = ((MIDlet)var1).getAppProperty("DemoBuyURL");
      } catch (Exception var3) {
         this.bI = 0;
         this.bJ = "";
      }

      try {
         this.u = ((MIDlet)var1).getAppProperty("carrierurl");
      } catch (Exception var2) {
         this.u = null;
      }
   }

   public final void i(int var1) {
      if (var1 != 0) {
         if (var1 == 1) {
            if (J != 102 && J != 107) {
               this.ag = this.ab >> 8;
               this.ah = this.ac >> 8;
            }

            if (J != 107 && J != 102) {
               this.K = J;
            }

            if (J != 100 && J != 78 && J != 105 && J != 108 && J != 101 && J != 102 && J != 104 && J != 106 && J != 109 && J != 103 && J != 79 && J != -3 && J != 16 && J != 107 && J != 0 && J != 1) {
               J = 80;
               this.hV = false;
            } else if (J != 79 && J != 106 && J != 108 && J != 109 && J != -3 && J != 1 && J != 16 && J != 107 && J != 0 && J != 78) {
               this.a(I - (A << 1), I - (A << 1), 35, 35);
               this.hV = false;
               this.U = I - (A << 1) << 8;
               this.V = I - (A << 1) << 8;
               this.X = false;
               J = 102;
               this.iF = true;
            }

            this.iN = false;
            this.iG = true;
         } else if (var1 == 5) {
            if (this.hV && J == 80) {
               this.hV = false;
               this.y(0);
            }
         } else if (var1 == 2) {
            if (J == 80) {
               this.y(0);
            }
         } else if (var1 == 3) {
            this.iR.c();
         }
      }

      super.i(var1);
   }

   public final void a() {
      M = super.k >> this.hL;
      ((a)this).f();
      this.i();
      this.j();
      switch (J) {
         case 0:
            if (this.L <= 13) {
               this.l();
               return;
            }

            this.iX = null;
            ((a)this).a((DataInputStream)null);
            this.iG = true;
            this.A(1);
            this.iX = null;
            ib = new String[3];
            ib[0] = ((a)this).e(36);
            ib[1] = "";
            ib[2] = "";
            this.a(ij, il);
            J = 78;
            ((a)this).d();
            this.L = 0;
            return;
         case 1:
            this.k();
            if (this.w) {
               ac();
               if (this.v == 0) {
                  this.Z();
                  this.b(false);
                  T = ((a)this).c(this.bE);
                  this.gi = -1;
                  dQ = -1;
                  this.dR = -1;
               }

               if (this.v <= 10) {
                  this.a(T, false);
                  return;
               }

               ((a)this).e();
               T = null;
               if (this.eF >= 1) {
                  --this.eF;
               }

               this.r();
               int var5 = this.v;
               this.v = 0;
               this.v = var5;
               this.w = false;
               this.b(false);
               this.m();
               this.iT.b(iV, iW);
               this.a(I, I, 35, 35);
               this.bg = true;
               if (this.R <= 0) {
                  this.am = true;
                  return;
               }
            }

            return;
         case 78:
            if (this.s == 27 || this.s == 8) {
               ((a)this).f(y[x]);
               this.iG = true;
               ((a)this).h();
               ib = new String[3];
               ib[0] = ((a)this).e(36);
               ib[1] = "";
               ib[2] = "";
               this.a(ij, il);
               J = 79;
               return;
            }

            if (this.s == 1) {
               if (x > 0) {
                  --x;
                  return;
               }

               return;
            } else {
               if (this.s == 6) {
                  if (x < 5) {
                     ++x;
                     return;
                  }
               } else if (this.s == 29) {
                  this.ai();
                  return;
               }

               return;
            }
         case 79:
            this.ae();
            this.k();
            if ((this.s == 27 || this.s == 29) && this.U == 0) {
               if (this.s == 27) {
                  this.iR.a(true);
               } else {
                  this.iR.a(false);
               }

               iQ = this.iR.b();
               this.y(0);
               if (this.ab == I << 8) {
                  this.a(0, 0, 35, 35);
               } else {
                  this.a(I, I, 35, 35);
               }

               this.iG = true;
               return;
            }

            return;
         case 80:
            this.O = P.nextInt();
            if (this.U == 0) {
               this.ah();
               this.ag();
            }

            this.k();
            if (this.s == 48 && this.C) {
               for(int var4 = 0; var4 < 10; ++var4) {
                  int var10001 = 9 + var4;
                  int[] var10002 = iA;
                  iA[var10001] = var10002[9 + var4] |= 50331648;
               }

               return;
            }

            return;
         case 101:
         case 102:
         case 105:
         case 108:
            break;
         case 104:
            this.hL = 0;
            if (this.fz == 10) {
               this.hq += 3;
               this.hn += this.hq / 64;
               if (this.hn > this.hq) {
                  this.hn = this.hq;
               }
            } else {
               this.hr += 3;
               hp += this.hr / 64;
               if (hp > this.hr) {
                  hp = this.hr;
               }
            }

            if (this.fA <= 0) {
               J = 100;
               this.iF = true;
               if (this.hn > this.hq && this.fz == 10) {
                  this.hn = this.hq;
               } else if (this.fz == 9) {
                  hp = this.hr;
               }
            }

            this.fA -= 3;
         case 100:
            this.bt += M;
            if (this.bt >= 250) {
               this.bt = 0;
               if (this.bu == 0) {
                  this.bu = 16777215;
               } else {
                  this.bu = 0;
               }
            }

            this.bd = false;
            break;
         case 106:
            this.k();
            this.t(134);
            if (!this.t(133)) {
               if (this.bj) {
                  c var1 = aM[this.bl ? 4 : 1];
                  c var2 = aM[this.bl ? 8 : 5];
                  if (!this.bl) {
                     for(int var6 = 0; var6 <= bi; ++var6) {
                        aM[1 + var6] = var6 + 1 > bi ? var1 : aM[1 + var6 + 1];
                        aM[5 + var6] = var6 + 1 > bi ? var2 : aM[5 + var6 + 1];
                     }
                  } else {
                     for(int var3 = 0; var3 <= bi; ++var3) {
                        aM[4 - var3] = var3 + 1 > bi ? var1 : aM[4 - var3 - 1];
                        aM[8 - var3] = var3 + 1 > bi ? var2 : aM[8 - var3 - 1];
                     }
                  }
               }

               this.bj = false;
               ((a)this).a(133, 1025, 2);
            }

            this.o();
            this.ae();
            return;
         case 107:
            if (!this.aX) {
               this.aa();
            }

            if (this.aX) {
               if (this.t == 1) {
                  this.aY = 24;
               } else {
                  this.aY = 4;
               }

               if (this.t == 6) {
                  this.aY = -24;
               }

               this.ir += M * this.aY;
               if (this.ir < 0) {
                  this.ir = 0;
               }

               if ((this.ir >> 8 > 50 + this.G - A || this.t == 29 && this.U == this.ab) && J == 107 && this.ab < I << 8) {
                  this.a(I, I, 35, 35);
                  if (this.R > 0) {
                     this.am = true;
                  } else {
                     this.an = true;
                     this.R = -1;
                  }
               }
            } else if (this.s == 29 && this.U == this.ab) {
               this.ai = true;
               this.a(I, I, 35, 35);
               this.iG = true;
            }

            this.k();
            return;
         case 109:
            this.k();
            if (this.U == this.ab && this.V == this.ac && !this.X) {
               if (this.s == 27) {
                  ((a)this).a(133, 1025, 2);
                  ((a)this).a(134, 1025, 3);
                  if (this.bI > 0 && this.bG >= this.bI) {
                     iP = true;
                     ((a)this).b(this.bJ);
                     this.ai();
                  } else {
                     this.bo = (256 - (this.hu & 255)) * 208 >> 8;
                     this.av = true;
                     this.z(1);
                     this.a(I, I, 35, 35);
                  }
               }

               if (this.s == 29) {
                  this.a(I, I, 35, 35);
                  this.ap = true;
                  this.cv = -1;
                  return;
               }
            }

            return;
         default:
            return;
      }

      this.O = P.nextInt();
      if ((J == 100 || J == 105 || J == 108) && !this.bd) {
         this.x();
      }

      if (J == 105) {
         this.t = 0;
         this.s = 0;
      }

      if (J == 101) {
         if (this.U >= this.ab && this.U < this.G << 8) {
            this.be += M >> 4 - (this.t == 8 ? 3 : 0);
         }

         this.p();
      } else {
         this.be = 0;
      }

      this.m();
      this.iT.b(iV, iW);
      this.k();
   }

   private void a(int var1, int var2, int var3, int var4) {
      this.Z = this.U <= var1 << 8;
      this.aa = this.V <= var2 << 8;
      this.X = this.aa || this.Z;
      if (this.X) {
         this.W = 375;
      }

      this.ab = var1 << 8;
      this.ac = var2 << 8;
      this.ad = var3;
      this.ae = var4;
      this.w = false;
      this.aq = false;
      this.ap = false;
      this.Y = false;
   }

   private void k() {
      if (this.U >= this.ab && this.V >= this.ac && (J == 105 || J == 100)) {
         this.bd = false;
      }

      if (this.U == this.ab && this.V == this.ac && this.W > 0) {
         if (this.W == 375 && J != 101 && J != 105) {
            this.y(1);
         }

         this.W -= M;
         if (this.W < 0) {
            this.W = 0;
            return;
         }
      } else {
         if (J == 1) {
            if (this.U == this.ab && this.V == this.ac && this.X && this.R <= 0) {
               if (!this.am) {
                  if (!iS) {
                     this.a(I - (A << 1), I - (A << 1), 35, 35);
                  } else {
                     this.a(I - (A << 1) - A, I - (A << 1) - A, 35, 35);
                  }

                  this.X = false;
               } else {
                  this.a(0, 0, 35, 35);
                  J = 100;
                  this.iF = true;
                  this.v = 0;
                  this.R = 0;
                  this.bg = false;
                  this.am = false;
               }
            } else if (this.R > 0 && this.v >= 10 && this.U >= this.ab && this.V >= this.ac && this.X) {
               this.bg = true;
               int[] var2;
               (var2 = new int[3])[0] = -1;
               var2[1] = this.R;
               var2[2] = -1;
               this.cv = -1;
               this.a(var2, aW, 8, 25, 147, 158, 30);
               this.aX = true;
               this.a(25, 25, 35, 35);
               this.ir = 0;
               J = 107;
            } else if (this.R > 0 && this.v > 10 && !this.X && this.U == this.ab && this.V == this.ac) {
               this.aX = true;
               this.bg = true;
               this.a(I, I, 35, 35);
               this.w = true;
            } else if (this.U == this.ab && this.V == this.ac && !this.X && !this.w) {
               this.X = false;
               this.w = true;
            }
         } else if (J == 101) {
            if (this.U == this.ab && this.V == this.ac && this.X && this.aX) {
               this.a(this.aR, aW, 8, 25, 147, 158, 30);
               if (ib != null) {
                  ib[0] = "";
                  ib[2] = "";
               }

               this.a(25, 25, 35, 35);
               J = 107;
               this.ir = 0;
            }
         } else if (J == 102) {
            if (this.U == this.ab && this.V == this.ac && this.X) {
               if (this.aq) {
                  this.a(this.ag, this.ah, 35, 35);
                  J = this.K;
                  this.aq = false;
                  this.X = false;
                  this.a(this.aR, aW, 8, 0, 147, A * 5 + B, 30);
               } else if (this.ap) {
                  this.ap = false;
                  this.Z();
                  this.y(0);
                  this.b(true);
                  ((a)this).a((DataInputStream)null);
                  iB = 0;
                  this.iC = 0;
                  J = 80;
                  this.a(0, 0, 35, 35);
                  this.z(1);
               } else if (this.ao) {
                  this.ai();
               } else if (this.ar) {
                  ((a)this).h();
                  ib = new String[3];
                  ib[0] = "";
                  ib[2] = "";
                  ((a)this).a(this.q);
                  ib[1] = ((a)this).e(114) + "\n" + ((a)this).e(292) + "\n\n\n\n";
                  this.a(ih, ik);
                  this.a(25, 25, 35, 35);
                  J = 107;
                  this.ir = 0;
               } else {
                  this.a(I - (A << 1), I - (A << 1), 35, 35);
                  this.Y = true;
               }

               this.X = false;
            }
         } else if (J == 80) {
            if (this.U == this.ab && this.V == this.ac && this.X && this.ak) {
               this.ak = false;
               ((a)this).h();
               ib = new String[3];
               ib[0] = "";
               ib[2] = "";
               ((a)this).a(this.q);
               if (this.aj) {
                  ib[1] = ((a)this).e(115);
                  int var1;
                  if ((var1 = ib[1].indexOf(37)) != -1) {
                     ib[1] = ib[1].substring(0, var1) + "1.4.58" + ib[1].substring(var1 + 1, ib[1].length());
                  }

                  this.a(ii, ik);
               } else {
                  ib[1] = ((a)this).e(114) + "\n" + ((a)this).e(292) + "\n\n\n\n";
                  this.a(ih, ik);
               }

               this.a(25, 25, 35, 35);
               J = 107;
            }
         } else if (J == 79) {
            if (this.U == this.ab && this.V == this.ac && this.X) {
               this.a(0, 0, 35, 35);
               this.iN = false;
               this.iG = true;
               this.z(1);
               J = 80;
            }
         } else if (J != 107) {
            if (J == 109) {
               if (this.U == this.ab && this.V == this.ac && this.X) {
                  if (!this.ap && !this.av) {
                     this.a(I - (A << 1), I - (A << 1), 35, 35);
                  } else if (this.av) {
                     ((a)this).a(133, 1025, 2);
                     ((a)this).a(134, 1025, 3);
                     J = 106;
                     this.a(0, 0, 35, 35);
                  } else {
                     J = 80;
                     this.y(0);
                     this.ap = false;
                     this.z(1);
                     if (this.bH >= 10) {
                        iB = 9;
                     } else {
                        iB = this.bH + 9;
                     }

                     this.Z();
                     this.b(true);
                     ((a)this).a((DataInputStream)null);
                     this.bg = false;
                     this.iC = 0;
                     this.a(0, 0, 35, 35);
                  }
               }
            } else if (J == 108 && this.U == this.ab && this.V == this.ac && this.X) {
               if (!this.ap && !this.aq) {
                  if (iS) {
                     this.a(I - (A << 1) - A - (A >> 2), I - (A << 1) - A - (A >> 2), 35, 35);
                  } else {
                     this.a(I - B - (A >> 2), I - B - (A >> 2), 35, 35);
                  }

                  this.Y = true;
               } else if (!this.aq && this.ap) {
                  this.ap = false;
                  this.Z();
                  this.y(0);
                  this.b(true);
                  ((a)this).a((DataInputStream)null);
                  this.iC = 0;
                  iB = 0;
                  J = 80;
                  this.a(0, 0, 35, 35);
                  this.z(1);
               } else {
                  J = 100;
                  this.iF = true;
                  this.a(0, 0, 35, 35);
                  this.Y = true;
               }
            }
         } else {
            if (this.U != this.ab || this.V == this.ac) {
               ((a)this).h();
            }

            if (this.U == this.ab && this.V == this.ac && this.ai) {
               if (!this.ar) {
                  J = 80;
                  this.ai = false;
                  iB = 0;
                  this.iC = 0;
                  this.a(0, 0, 35, 35);
               } else if (this.U == I << 8) {
                  this.cv = this.au;
                  this.ar = false;
                  this.ai = false;
                  this.a(I - (A << 1), I - (A << 1), 35, 35);
                  J = 102;
               }
            } else if (this.U == this.ab && this.V == this.ac && this.an && this.R < 0) {
               this.a(I - (A << 1), I - (A << 1), 35, 35);
               this.bg = true;
               this.an = false;
               this.iN = false;
               ++iB;
               ++this.bG;
               if (this.bG > this.bH && (this.bI <= 0 || this.bG < this.bI)) {
                  this.bH = this.bG;
               }

               if (this.bG < 10) {
                  this.bE = bF[this.bG];
               }

               this.af = true;
               this.w = false;
               this.Z();
               if (this.bG < 10) {
                  J = 109;
               } else {
                  this.Z();
                  this.y(0);
                  this.b(true);
                  ((a)this).a((DataInputStream)null);
                  this.a(0, 0, 35, 35);
                  this.iC = 0;
                  iB = 0;
                  this.bg = false;
                  J = 80;
                  this.z(1);
               }
            } else if (this.U >= this.ab && this.V >= this.ac && this.R > 0 && this.am) {
               J = 100;
               this.iF = true;
               this.bg = false;
               this.am = false;
               this.v = 0;
               this.R = 0;
               this.a(0, 0, 35, 35);
            }
         }

         if (J == 102 && this.Y && this.W < 0) {
            this.Y = false;
            J = this.K;
            this.bd = true;
            this.iF = true;
         }

         if (this.U > 87) {
            if (J == 108 && this.U == this.ab && !this.X) {
               if (this.s != 8 && this.s != 27) {
                  if (this.s == 29) {
                     this.a(I, I, 35, 35);
                     this.ap = true;
                  }
               } else if (!iS) {
                  this.ge = this.gc;
                  this.gf = this.gd;
                  hy = this.hz;
                  iV = this.ge - 88 - ((hy ? 1 : -1) * 176 >> 2);
                  iW = this.gf - 168;
                  this.aP = iV << 8;
                  this.aQ = iW << 8;
                  this.hA = false;
                  this.s(0);
                  this.hn = this.hq;
                  this.ho = this.hq;
                  hp = this.hr;
                  this.a(I, I, 35, 35);
                  this.Y = true;
                  this.iF = true;
                  this.gN = -1;
                  this.aq = true;
               }
            } else if (J == 102 && this.U == this.ab && !this.X) {
               if (this.s != 8 && this.s != 27) {
                  if (this.s == 29) {
                     if (this.at) {
                        this.at = false;
                     } else {
                        this.a(I, I, 35, 35);
                        this.aq = true;
                     }
                  } else if (this.s == 2) {
                     if (!this.at) {
                        ++as;
                        if (as > 4) {
                           as = 4;
                        }
                     }
                  } else if (this.s == 5 && !this.at) {
                     --as;
                     if (as < 1) {
                        as = 1;
                     }
                  }
               } else if (as == 4) {
                  this.iR.a(!this.iR.b());
                  iQ = this.iR.b();
               } else if (as == 2 && this.at) {
                  this.a(I, I, 35, 35);
                  this.cv = -1;
                  this.ap = true;
               } else if (as == 1 && this.at) {
                  this.a(I, I, 35, 35);
                  this.ao = true;
               } else if ((as == 2 || as == 1) && !this.at) {
                  this.at = true;
               } else if (as == 3) {
                  this.aX = false;
                  this.a(I, I, 35, 35);
                  this.ar = true;
                  this.au = this.cv;
                  this.cv = -1;
               } else {
                  this.at = false;
                  this.ar = false;
               }
            }
         }

         if (this.U < this.ab && this.Z && (this.af && this.V <= this.V || !this.af)) {
            this.iF = true;
            this.U += M * this.ad;
            if (this.U >= this.ab) {
               this.U = this.ab;
               if (J == 105) {
                  this.bd = false;
               }
            }
         }

         if (this.V < this.ac && this.aa) {
            this.V += M * this.ad;
            this.iF = true;
            if (this.V > this.ac) {
               this.V = this.ac;
            }
         }

         if (this.U > this.ab && !this.Z) {
            this.iF = true;
            this.U -= M * this.ad;
            if (this.U <= this.ab) {
               this.U = this.ab;
            }

            this.al = true;
         }

         if (this.V > this.ac && !this.aa) {
            this.iF = true;
            this.V -= M * this.ae;
            if (this.V < this.ac) {
               this.V = this.ac;
            }
         }
      }

   }

   private void a(int var1, int var2, int var3, short[] var4, boolean var5) {
      for(int var6 = 0; var6 < var4.length; ++var6) {
         var4[var6] = 0;
      }

      ((a)this).b(var4, var3, var5 ? 0 : 2);

      for(int var7 = 0; var7 < var4.length; var7 += 5) {
         var4[var7] = (short)(var4[var7] + var1);
         var4[var7 + 1] = (short)(var4[var7 + 1] + var2);
      }

   }

   private void l() {
      switch (this.L) {
         case 0:
            ((a)this).h(138);
            this.F = 176;
            this.G = 208;
            H = this.F >> 1;
            I = this.G >> 1;
            ((a)this).f(10240);
            byte[] var1;
            S = new int[(var1 = ((a)this).c(1053)).length / 3];
            int var2 = 0;

            for(int var5 = 0; var5 < var1.length; var5 += 3) {
               S[var2] = var1[var5] << 18 | var1[var5 + 1] << 10 | var1[var5 + 2] << 2;
               ++var2;
            }

            P = new Random();
            this.b(true);
            this.iX = ((a)this).b((int)1037);
            this.ct = ((a)this).b((int)1035);
            ((a)this).g(1024);
            ((a)this).a(1024, true);
            this.cu = ((a)this).b((int)1030);
            this.fy = ((a)this).b((int)1038);
            ((a)this).g(1026);
            ((a)this).a(1026, true);
            break;
         case 1:
            this.q = ((a)this).b((int)1031);
            ((a)this).a(this.q);
            A = ((a)this).c();
            B = A >> 1;
            this.iO = ((a)this).a("W") * 4;
            this.fB = ((a)this).b((int)1034);

            for(int var3 = 0; var3 < 5; ++var3) {
               this.ae();
            }
         case 2:
         case 6:
         case 9:
         case 10:
         case 11:
         default:
            break;
         case 3:
            ((a)this).g(1025);
            ((a)this).a(1025, true);
            aM = ((a)this).b((int)1036);
            ((a)this).g(1027);
            ((a)this).a(1027, true);
            this.aV = ((a)this).b((int)1040);
            bs = ((a)this).b((int)1029);
            this.af();
            break;
         case 4:
            this.iR.a(0, 1063, -1, 0);
            this.iR.a(1, 1064, 1, 0);
            this.iR.a(2, 1062, 1, 0);
            break;
         case 5:
            ((a)this).g(2048);
            ((a)this).a(2048, true);
            ((a)this).g(2049);
            ((a)this).g(2050);
            ((a)this).a(2049, true);
            ((a)this).a(2050, true);
            break;
         case 7:
            ((a)this).g(3074);
            ((a)this).a(3074, true);
            ((a)this).g(3072);
            ((a)this).a(3072, true);
            ((a)this).g(3073);
            ((a)this).a(3073, true);
            ((a)this).g(5122);
            ((a)this).a(5122, true);
            break;
         case 8:
            ((a)this).g(5121);
            ((a)this).a(5121, true);
            ((a)this).g(6145);
            ((a)this).a(6145, true);
            ((a)this).g(6144);
            ((a)this).a(6144, true);
            ((a)this).g(5120);
            ((a)this).a(5120, true);
            ((a)this).g(28672);
            ((a)this).a(28672, true);
            ((a)this).g(7168);
            ((a)this).a(7168, true);
            break;
         case 12:
            iA[5] = (iA[5] & -256) + 68;
            iA[5] &= -50331649;
            if (this.u == null) {
               iA[6] &= -50331649;
            }
            break;
         case 13:
            ((a)this).h();
            if (!iZ) {
               iY = true;
               this.iX = ((a)this).b((int)8193);
               ((a)this).g(8192);
               ((a)this).a(0, 8192, 0);
               iZ = true;
            } else {
               iZ = ((a)this).b(0, super.k);
               if (!iZ) {
                  N = 0;
               }
            }

            ((a)this).h();
      }

      if (!iZ && this.L <= 13) {
         ++this.L;
      }

      for(int var4 = 0; var4 < 6 && !iZ; ++var4) {
         this.ae();
      }

   }

   private void b(boolean var1) {
      if (var1) {
         this.r = ((a)this).b((int)1039);
         this.o = ((a)this).b((int)1032);
         this.p = ((a)this).b((int)1033);
         this.iN = false;
         this.iG = true;
         this.iy = this.r[3].c;
         ((a)this).d();
         this.bG = this.bH;
         ((a)this).a((DataInputStream)null);
      } else {
         ((a)this).a(132, 1027, 10);
         this.r = null;
         this.o = null;
         this.p = null;
      }
   }

   private void m() {
      int var1 = M << 8;
      int var2;
      int var3;
      if (this.az > 0) {
         var2 = this.az;
         var3 = this.aA;
      } else {
         var2 = this.ge;
         var3 = this.gf;
      }

      int var10000 = (hy && this.az < 0 ? 1 : -1) * (hF != 109 && hF != 110 ? 176 : -176) >> 2;
      int var4 = 0;
      var4 = var10000 - (this.gr ? (hy ? this.F >> 2 : -(this.F >> 2)) : 0);
      if (hF == 116) {
         var4 = 0;
      }

      int var5 = this.gr ? 52 : 0;
      int var6 = var2 - 88 + var4;
      int var7 = var3 - 168 + var5;
      int var8 = (var6 - iV + this.aJ) * M;
      int var9 = (var7 - iW + this.aK) * M;
      if (Math.abs(var8) > var1) {
         if (var8 > 0) {
            var8 = var1;
         } else {
            var8 = -var1;
         }
      }

      if (Math.abs(var9) > var1) {
         if (var9 > 0) {
            var9 = var1;
         } else {
            var9 = -var1;
         }
      }

      this.aP += var8;
      this.aQ += var9;
      var6 = this.aP >> 8;
      var7 = this.aQ >> 8;
      this.aN = iV - var6;
      this.aO = iW - var7;
      iV = var6;
      iW = var7;
      boolean var10 = false;
      boolean var11 = false;
      if (this.az < 0) {
         for(int var12 = this.aE - 1; var12 >= 0; --var12) {
            if (a(this.aF[var12], this.aG[var12], iV, iW, 176, 208)) {
               if (!this.aI[var12]) {
                  var10 = true;
                  int var13 = this.aL * M >> 8;
                  if (this.aH[var12]) {
                     this.aJ -= var13;
                  } else {
                     this.aJ += var13;
                  }

                  if (!a(this.aF[var12], this.aG[var12], iV + this.aJ, iW + this.aK, 176, 208)) {
                     this.aJ = this.aF[var12] - iV - (this.aH[var12] ? 176 : 0);
                  }
               } else {
                  var11 = true;
                  int var18 = this.aL * M >> 8;
                  if (this.aH[var12]) {
                     this.aK -= var18;
                  } else {
                     this.aK += var18;
                  }

                  if (!a(this.aF[var12], this.aG[var12], iV + this.aJ, iW + this.aK, 176, 208)) {
                     this.aK = this.aG[var12] - iW - (this.aH[var12] ? 208 : 0);
                  }
               }
            }
         }

         for(int var17 = this.fd; var17 >= 0; --var17) {
            if (fn[var17] && a(fh[var17] + (fo[var17] ? fj[var17] : 0), this.fi[var17], iV, iW, 176, 208)) {
               var10 = true;
               int var19 = this.aL * M >> 8;
               if (fo[var17]) {
                  this.aJ -= var19;
               } else {
                  this.aJ += var19;
               }

               if (!a(fh[var17] + (fo[var17] ? fj[var17] : 0), this.fi[var17], iV + this.aJ, iW + this.aK, 176, 208)) {
                  this.aJ = fh[var17] + (fo[var17] ? fj[var17] : 0) - iV - (fo[var17] ? 176 : 0);
               }
            }
         }

         iV += this.aJ;
         iW += this.aK;
         if (!var10 && this.aJ != 0) {
            if (this.aJ > 0) {
               this.aJ -= this.aL * M >> 8;
               if (this.aJ < 0) {
                  this.aJ = 0;
               }
            } else {
               this.aJ += this.aL * M >> 8;
               if (this.aJ > 0) {
                  this.aJ = 0;
               }
            }
         }

         if (!var11 && this.aK != 0) {
            if (this.aK > 0) {
               this.aK -= this.aL * M >> 8;
               if (this.aK < 0) {
                  this.aK = 0;
               }
            } else {
               this.aK += this.aL * M >> 8;
               if (this.aK > 0) {
                  this.aK = 0;
               }
            }
         }

         this.aN -= this.aJ;
         this.aO -= this.aK;
         if (this.aw != 0) {
            if (this.ax + this.ax * this.hL >= this.ay) {
               iW += this.aw;
               iV += this.aw * ((this.O & 1) == 1 ? -1 : 1);
               this.aw += this.aw < 0 ? 1 : -1;
               this.aw = -this.aw;
               this.ax = 0;
            } else {
               this.ax += M;
            }
         } else {
            this.ay = 0;
         }
      } else if (var8 != 0 && this.aB <= 3500) {
         this.aB += M;
      } else {
         this.az = -1;
         this.aA = -1;
         this.iF = true;
         this.aB = 0;
      }

      if (iV < 0) {
         iV = 0;
         this.aP = 0;
         this.aN = 0;
      }

      if (iV >= this.aC) {
         iV = this.aC;
         this.aP = this.aC << 8;
         this.aN = 0;
      }

      if (iW < 0) {
         iW = 0;
         this.aQ = 0;
         this.aO = 0;
      }

      if (iW >= this.aD) {
         iW = this.aD;
         this.aQ = this.aD << 8;
         this.aO = 0;
      }

   }

   private void n() {
      this.it = false;
      this.aR[0] = -1;
      this.aR[1] = this.cw;
      ib = new String[3];
      ib[0] = "";
      ib[1] = ((a)this).e(this.aR[1]);
      ((a)this).a(this.q);
      if (ib[1].charAt(0) == '!') {
         J = 105;
         this.bd = true;
         this.s(this.aZ[this.bb]);
         if (this.dP != -1) {
            this.i(this.ba[this.bc], this.dP);
            return;
         }
      } else {
         if (ib[1].charAt(0) == '^') {
            this.cv = a.a((String)ib[1].substring(1, 2), (int)0);
            if (this.cv > 3) {
               int var1 = this.cv - 3 - 1;
               this.fQ = var1;
               hf = this.fQ == 1;
               hg = this.fQ == 2;
               this.hh = this.fQ == 3;
            }
         } else {
            this.cv = -1;
         }

         ib[1].charAt(0);
         if (ib[1].charAt(0) == '*') {
            if (ib[1].length() != 1) {
               this.aX = true;
               this.a(I, I, 35, 35);
            } else {
               this.bf = true;
            }

            this.iG = true;
            return;
         }

         this.aX = false;
         this.a(this.aR, aW, 8, 0, 147, A * 5 + B, 30);
      }

   }

   private void o() {
      if (this.bm <= 0 && !this.bj) {
         switch (this.s) {
            case 1:
            case 2:
               ((a)this).a(133, 1025, 1);
               this.bj = true;
               ++bk;
               if (bk > bi) {
                  bk = 0;
               }

               this.bl = false;
               break;
            case 3:
            case 29:
               ((a)this).a(133, 1025, 4);
               if (!this.av) {
                  J = 100;
               } else {
                  this.av = false;
                  J = 1;
                  this.a(I, I, 35, 35);
               }

               this.iF = true;
               break;
            case 5:
            case 6:
               ((a)this).a(133, 1025, 0);
               this.bj = true;
               --bk;
               if (bk < 0) {
                  bk = bi;
               }

               this.bl = true;
         }

         switch (this.t) {
            case 8:
            case 27:
               if (this.hu > 0 && this.hu >= this.bh) {
                  if (this.bh < 16) {
                     ++this.bh;
                  }
               } else if (this.hu < this.bh) {
                  this.bh = this.hu;
               } else if (this.hu <= 0) {
                  this.bh = 0;
               }

               switch (bk) {
                  case 0:
                     if (gT <= 3) {
                        this.hb += this.bh;
                        if (this.hb >= gV[gT]) {
                           this.bh = gV[gT] - (this.hb - this.bh);
                           if (gT == 0 || gT == 1 || gT == 2) {
                              ++gR;
                           }

                           this.hb = 0;
                           ++gT;
                           this.bm = 2500;
                        }
                     } else {
                        this.bh = 0;
                     }
                     break;
                  case 1:
                     if (gU <= 3) {
                        this.hc += this.bh;
                        if (this.hc < gW[gU]) {
                           break;
                        }

                        this.bh = gW[gU] - (this.hc - this.bh);
                        if (gU == 0 || gU == 1 || gU == 3) {
                           ++this.gS;
                        }

                        this.hc = 0;
                        ++gU;
                        this.bm = 2500;
                        break;
                     }

                     this.bh = 0;
                     break;
                  case 2:
                     if (gZ < 4) {
                        this.hd += this.bh;
                        if (this.hd >= gX[gZ]) {
                           this.bh = gX[gZ] - (this.hd - this.bh);
                           this.hd = 0;
                           ++gZ;
                           this.bm = 2500;
                        }
                     } else {
                        this.bh = 0;
                     }
                     break;
                  case 3:
                     if (ha < 4) {
                        this.he += this.bh;
                        if (this.he >= gY[ha]) {
                           this.bh = gY[ha] - (this.he - this.bh);
                           this.he = 0;
                           ++ha;
                           this.bm = 2500;
                        }
                     } else {
                        this.bh = 0;
                     }
               }

               this.hu -= this.bh;
               if (this.hu < 0) {
                  this.hu = 0;
               }

               this.bo = (256 - (this.hu & 255)) * 208 >> 8;
               break;
            default:
               this.bh = 0;
         }
      } else {
         this.bm -= M;
      }

      if (this.C) {
         if (this.t == 25) {
            this.hu += 20;
            return;
         }

         if (this.t == 23) {
            this.hu -= 20;
            if (this.hu < 0) {
               this.hu = 0;
            }
         }
      }

   }

   private void p() {
      if (this.U >= this.ab && this.it) {
         switch (this.s) {
            case 1:
            case 2:
               if (this.ip >= 5) {
                  this.be = 0;
                  this.ip -= 5;
                  return;
               }

               if (this.cw > this.cx) {
                  --this.cw;
                  this.be = 0;
                  this.n();
                  return;
               }
               break;
            case 5:
            case 6:
            case 8:
            case 27:
               if (this.iq > 0) {
                  this.be = 0;
                  this.ip = this.iq;
                  return;
               }

               if ((this.cv <= 3 || this.s != 27) && this.cv > 3) {
                  break;
               }

               this.be = 0;
               this.ip = -1;
               this.iq = -1;
               ++this.cw;
               if (this.cw >= this.cy) {
                  if (!this.bf) {
                     J = 100;
                     this.a(0, 0, 35, 35);
                     this.iF = true;
                     this.aX = false;
                     this.cy = -1;
                     this.cw = -1;
                     this.cx = -1;
                     return;
                  }

                  ((a)this).h();
                  ++this.bG;
                  if (this.bG > this.bH && (this.bI <= 0 || this.bG < this.bI)) {
                     this.bH = this.bG;
                  }

                  this.bE = bF[this.bG];
                  this.Z();
                  this.iG = true;
                  this.iX = null;
                  this.bg = true;
                  J = 1;
                  this.w = false;
                  this.a(I, I, 35, 35);
                  return;
               }

               this.n();
               return;
            case 29:
               if (this.U == this.ab) {
                  this.ag = this.ab >> 8;
                  this.ah = this.ac >> 8;
                  this.K = J;
                  J = 102;
                  as = 4;
                  this.a(I, I, 35, 35);
               }
         }
      }

   }

   public final void a(Graphics var1) {
      switch (J) {
         case 0:
            this.u(var1);
            if (!iZ && this.L <= 13) {
               a(var1, this.L, 13, true);
               return;
            }
            break;
         case 1:
         case 80:
         case 109:
            if (!this.bg && J != 1) {
               this.w(var1);
            }

            this.c(var1);
            if (J == 109 && this.U == this.ab && this.V == this.ac && !this.X) {
               this.a(var1, 28, 29);
               return;
            }
            break;
         case 78:
            this.v(var1);
            this.r[0].a(var1, H, 0, 0);
            this.r[1].a(var1, H, 0, 0);
            int var2 = this.r[1].b + this.r[1].h + (A >> 1);

            for(int var3 = 0; var3 <= 5; ++var3) {
               ((a)this).a(var1, ((a)this).e(z[var3]), H, var2, 1);
               if (var3 == x) {
                  bs[2].a(var1, (H >> 1) - bs[2].c, var2 + (A >> 2), 0);
                  bs[3].a(var1, this.F - (H >> 1), var2 + (A >> 2), 0);
               }

               var2 = var2 + A + (A >> 2);
            }

            if (this.ab != I << 8) {
               this.a(var1, 0, 1);
               return;
            }

            ((a)this).b(var1);
            return;
         case 79:
            this.v(var1);
            this.r[0].a(var1, H, 0, 0);
            this.r[1].a(var1, H, 0, 0);
            this.a(var1, false);
            this.c(var1);
            if (this.ab != I << 8) {
               this.a(var1, 28, 29);
               return;
            }

            ((a)this).b(var1);
            return;
         case 100:
         case 101:
         case 102:
         case 104:
         case 105:
         case 108:
            if (J == 100 && this.U <= 0 && !this.iF) {
               ((a)this).a(var1, 0, 0, 176, 194);
            } else {
               this.iF = true;
               ((a)this).b(var1);
            }

            this.d(var1);
            if (J != 101) {
               this.f(var1);
            }

            if (J == 100) {
               if (this.aS && this.aT == hy && !fV && a(this.ge, this.gf, iV, iW, this.F, this.G)) {
                  ((a)this).a(var1, this.aV, 131, 12, this.G - 25, 0);
               }

               if (fV && this.fW >= 0 && this.fW < this.fU && this.gi >= 0) {
                  ((a)this).a(var1, this.aV, 131, dX[this.gi] - iV, this.dY[this.gi] - this.ej[this.gi] - (this.ej[this.gi] >> 2) - iW, 0);
               }
            }

            if (J == 104) {
               var1.setColor(855564);
               var1.fillRect(H >> 3, I - (I >> 1), this.F - (H >> 2), I + A);
               var1.setColor(4269080);
               var1.drawRect((H >> 3) - 1, I - (I >> 1) - 1, this.F - (H >> 2) + 1, I + A + 1);
               var1.setColor(5911589);
               var1.drawRect((H >> 3) - 2, I - (I >> 1) - 2, this.F - (H >> 2) + 3, I + A + 3);
               this.cu[0].a(var1, (H >> 3) - 3, I - (I >> 1) - 2, 0);
               this.cu[0].a(var1, this.F - (H >> 3) + 3, I - (I >> 1) - 2, 2);
               this.cu[0].a(var1, (H >> 3) - 3, I + (I >> 1) + 2 + A, 1);
               this.cu[0].a(var1, this.F - (H >> 3) + 3, I + (I >> 1) + 2 + A, 3);
               ((a)this).a(var1, this.fy, 136, H, I - (A << 1), 0);
               if (this.fz == 9) {
                  ((a)this).a(var1, ((a)this).e(148), H, I + A, 1);
                  if (this.fA < 96) {
                     ((a)this).a(var1, ((a)this).e(189), H, I + (A << 1) + A, 1);
                  } else {
                     ((a)this).a(var1, ((a)this).e(151), H, I + (A << 1) + A, 1);
                  }
               } else {
                  ((a)this).a(var1, ((a)this).e(147), H, I + A, 1);
                  if (this.fA < 96) {
                     ((a)this).a(var1, ((a)this).e(158), H, I + (A << 1) + A, 1);
                  } else {
                     ((a)this).a(var1, ((a)this).e(151), H, I + (A << 1) + A, 1);
                  }
               }
            }

            this.c(var1);
            if (J == 101 && this.U >= this.ab && !this.aX) {
               this.a(var1, false);
            }

            switch (J) {
               case 100:
                  if (this.iF) {
                     this.a(var1, 160, 197);
                     return;
                  }

                  return;
               case 101:
                  if (this.it) {
                     this.a(var1, 174, 197);
                     return;
                  }

                  this.a(var1, -1, -1);
                  return;
               case 102:
                  if (this.U == this.ab && !this.X) {
                     if ((as == 1 || as == 2) && this.at) {
                        this.a(var1, 28, 29);
                        return;
                     }

                     if (as == 4 && as != 3) {
                        this.a(var1, 200, 199);
                        return;
                     }

                     this.a(var1, 0, 199);
                     return;
                  }

                  return;
               case 103:
               default:
                  return;
               case 104:
                  return;
               case 105:
                  this.a(var1, -1, 197);
                  return;
            }
         case 106:
            this.e(var1);
            this.c(var1);
            if (this.bm <= 0) {
               if (this.hu > 0) {
                  this.a(var1, !this.bj ? (this.bq <= 3 ? 149 : -1) : -1, !this.bj ? (this.av ? 174 : 2) : -1);
                  return;
               }

               this.a(var1, -1, !this.bj ? (this.av ? 174 : 2) : -1);
               return;
            }

            this.a(var1, -1, -1);
            return;
         case 107:
            if (super.n) {
               this.v(var1);
               this.a(var1, true);
            }

            this.c(var1);
            if (J == 107 && this.U <= 6400) {
               if (!this.aX) {
                  this.a(var1, -1, 2);
                  return;
               }

               this.a(var1, -1, 240);
               return;
            }
      }

   }

   private void c(Graphics var1) {
      int var2 = this.U >> 8;
      int var3 = this.V >> 8;
      if (var2 > 0) {
         if ((!this.X || this.Y || this.ap || this.ar || this.aq || this.ao) && (J == 108 || J == 102 || J == 1 || J == 109)) {
            var1.setColor(0);
            var1.fillRect(0, var2 + 1, 176, 208 - (var3 << 1) - 1);
            if (J == 108) {
               if (!iS) {
                  ((a)this).a(var1, ((a)this).e(173), H, I + B + (A >> 3), 33);
               } else {
                  ((a)this).a(var1, ((a)this).e(173), H, I - (A << 1) + (A >> 1), 33);
                  ((a)this).a(var1, ((a)this).e(206), H, I - (A >> 2) + (A >> 1), 33);
                  ((a)this).a(var1, String.valueOf(this.dM), H, I + A + (A >> 2), 33);
                  if (this.dM > this.hD) {
                     ((a)this).a(var1, ((a)this).e(207), H, I + (A << 1) + (A >> 1), 33);
                  } else {
                     ((a)this).a(var1, ((a)this).e(208) + String.valueOf(this.hD), H, I + (A << 1) + (A >> 1), 33);
                  }
               }
            } else if (J == 102) {
               ((a)this).a(var1, !iS ? ((a)this).e(54 + this.bG) : ((a)this).e(66), H, I - (A >> 1), 33);
               if (as == 4) {
                  ((a)this).a(var1, ((a)this).e(47) + " " + (this.iR.b() ? ((a)this).e(234) : ((a)this).e(235)), H, I + A + B, 33);
                  bs[3].a(var1, this.F - 5 - 7, I + (A >> 1) + 4, 0);
               } else if (as == 2 && !this.at) {
                  ((a)this).a(var1, ((a)this).e(50), H, I + A + B, 33);
                  bs[2].a(var1, 12 - bs[2].c, I + (A >> 1) + 4, 0);
                  bs[3].a(var1, this.F - 5 - 7, I + (A >> 1) + 4, 0);
               } else if (as == 2 && this.at) {
                  ((a)this).a(var1, ((a)this).e(186), H, I + A + B, 33);
               } else if (as == 1 && !this.at) {
                  ((a)this).a(var1, ((a)this).e(1), H, I + A + B, 33);
                  bs[2].a(var1, 12 - bs[2].c, I + (A >> 1) + 4, 0);
               } else if (as == 1 && this.at) {
                  ((a)this).a(var1, ((a)this).e(185), H, I + A + B, 33);
               } else if (as == 3) {
                  ((a)this).a(var1, ((a)this).e(46), H, I + A + B, 33);
                  bs[3].a(var1, this.F - 5 - 7, I + (A >> 1) + 4, 0);
                  bs[2].a(var1, 12 - bs[2].c, I + (A >> 1) + 4, 0);
               }
            } else if (J == 1) {
               ((a)this).a(this.q);
               if (iS) {
                  ((a)this).a(var1, ((a)this).e(66), H, I - A - (A >> 1), 33);
                  ((a)this).a(var1, ((a)this).e(190), H, I - (A >> 1), 33);
                  ((a)this).a(var1, ((a)this).e(238), H, I + (A >> 1), 33);
                  ((a)this).a(var1, ((a)this).e(39), H, I + A + (A >> 1) + (A >> 2), 33);
                  a(var1, this.v, 11, true);
               } else {
                  ((a)this).a(var1, ((a)this).e(54 + this.bG), H, I - B, 33);
                  ((a)this).a(var1, ((a)this).e(39), H, I + B, 33);
                  a(var1, this.v, 11, true);
               }
            } else if (J == 109) {
               if (this.bG >= this.bI && this.bI != 0) {
                  ((a)this).a(var1, ((a)this).e(279), H, I, 33);
                  ((a)this).a(var1, ((a)this).e(280), H, I + A, 33);
               } else {
                  ((a)this).a(var1, ((a)this).e(54 + this.bG - 1) + ((a)this).e(169), H, I, 33);
                  ((a)this).a(var1, ((a)this).e(168) + ((a)this).e(54 + this.bG) + ((a)this).e(159), H, I + A, 33);
               }
            }
         }

         var1.setColor(855564);
         var1.fillRect(0, 0, 176, var2);
         var1.setColor(4269080);
         var1.drawLine(0, var2 - 1, 176, var2 - 1);
         var1.setColor(5911589);
         var1.drawLine(0, var2, 176, var2);
         var1.setColor(4269080);
         var1.drawLine(0, var2 - 1, 176, var2 - 1);
         var1.setColor(855564);
         var1.fillRect(0, 208 - var3, 176, var3);
         var1.setColor(10246204);
         var1.drawLine(0, 208 - var3, 176, 208 - var3);
         var1.setColor(4269080);
         var1.drawLine(0, 208 - var3 + 1, 176, 208 - var3 + 1);
         if (!this.X && J == 108) {
            this.a(var1, iS ? -1 : 198, 45);
         }

         this.cu[0].a(var1, 0, var2, 1);
         this.cu[0].a(var1, 176, var2, 3);
         this.cu[0].a(var1, 0, 208 - (var3 - 1), 0);
         this.cu[0].a(var1, 176, 208 - (var3 - 1), 2);
      }

      if (J == 80 && this.U == 0) {
         switch (this.iL) {
            case 0:
               this.a(var1, 0, 1);
               return;
            case 9:
               if (this.bH >= iB - this.iL + 1) {
                  this.a(var1, 196, 2);
                  return;
               }

               this.a(var1, 120, 2);
               return;
            case 23:
               this.a(var1, 28, 29);
               return;
            case 30:
               this.a(var1, 28, 29);
               return;
            default:
               this.a(var1, 0, 2);
         }
      }

   }

   private void d(Graphics var1) {
      this.iT.a(var1);
      var1.translate(-iV, -(iW + this.bn));
      this.i(var1);
      this.l(var1);
      this.m(var1);
      this.p(var1);
      this.j(var1);
      this.q(var1);
      this.n(var1);
      this.o(var1);
      this.g(var1);
      if (this.gi >= 0 && !fV && hF != 109 && hF != 113 && this.dU[this.gi] != 2) {
         this.s(var1);
         this.r(var1);
      } else {
         this.r(var1);
         this.s(var1);
      }

      this.h(var1);
      this.t(var1);
      this.k(var1);
      var1.translate(iV, iW + this.bn);
   }

   private void e(Graphics var1) {
      this.br = 0;
      var1.setColor(0);
      var1.fillRect(H - 5 - 25, 0, H + 5 + 25, 208);
      var1.fillRect(5, 0, 70, this.bo + this.br);
      ((a)this).a(this.q);
      this.b(var1, false);
      ((a)this).a(var1, aM, 134, 5, this.bo + this.br, 0);
      ((a)this).a(var1, aM, 133, 0, this.br, 0);
      bp = "~ " + String.valueOf(this.hu);
      ((a)this).a(var1, bp, 13, 4, 4);
      bp = ((a)this).e(116 + bk);
      bs[0].a(var1, 127, 24 - bs[0].d, 0);
      if (bp.indexOf(10) != -1) {
         ((a)this).a(var1, bp.substring(0, bp.indexOf(10)), 127, 26, 1);
         ((a)this).a(var1, bp.substring(bp.indexOf(10) + 1, bp.length()), 127, 26 + A, 1);
      } else {
         ((a)this).a(var1, ((a)this).e(116 + bk), 127, 26, 1);
      }

      int var2 = 0;
      if (bk == 0) {
         this.bq = gT;
         if (gT <= 3) {
            var2 = gV[this.bq] - this.hb;
         }
      } else if (bk == 1) {
         this.bq = gU;
         if (gU <= 3) {
            var2 = gW[this.bq] - this.hc;
         }
      } else if (bk == 3) {
         this.bq = ha;
         if (ha <= 3) {
            var2 = gY[this.bq] - this.he;
         }
      } else {
         this.bq = gZ;
         if (gZ <= 3) {
            var2 = gX[this.bq] - this.hd;
         }
      }

      if (this.bq <= 3) {
         ((a)this).a(var1, ((a)this).e(223) + String.valueOf(this.bq + 1), 127, 26 + (A << 1), 1);
         ((a)this).a(var1, String.valueOf(var2) + "~", 152, 52 + (A << 1), 8);
         ((a)this).a(var1, ((a)this).e(224), 127, 52 + (A << 1) + A, 1);
         ((a)this).a(var1, ((a)this).e(225), 127, 52 + (A << 2), 1);
      } else {
         ((a)this).a(var1, ((a)this).e(241), 127, 26 + (A << 1), 1);
      }

      bs[1].a(var1, 127, 26 + (A << 1) + A, 0);
      if (this.bm > 0) {
         var1.setColor(855564);
         var1.fillRect(H - (H >> 1), I - (I >> 2), H, (I >> 1) + A);
         var1.setColor(4269080);
         var1.drawRect(H - (H >> 1) - 1, I - (I >> 2) - 1, H + 1, (I >> 1) + 1 + A);
         var1.setColor(5911589);
         var1.drawRect(H - (H >> 1) - 2, I - (I >> 2) - 2, H + 3, (I >> 1) + 3 + A);
         if (bp.indexOf(10) != -1) {
            ((a)this).a(var1, bp.substring(0, bp.indexOf(10)), H, I - A - (A >> 1), 1);
            ((a)this).a(var1, bp.substring(bp.indexOf(10) + 1, bp.length()), H, I - (A >> 1), 1);
         } else {
            ((a)this).a(var1, ((a)this).e(116 + bk), H, I - (I >> 2), 1);
         }

         ((a)this).a(var1, ((a)this).e(222), H, I + A, 1);
      }

   }

   private void f(Graphics var1) {
      ((a)this).a(var1, aM, 133, 0, 0, 0);
      int var2;
      if ((var2 = 5 + this.fQ + (bk > 0 ? 4 - bk : 0)) > 8) {
         var2 = 5 + (var2 - 8) - 1;
      }

      aM[var2].a(var1, 6, 17, 0);
      var1.setColor(4342338);
      var1.drawRect(42, 13, this.hq * 6 >> 8, 3);
      var1.drawRect(42, 17, this.hr * 6 >> 8, 2);
      var1.setColor(0);
      var1.fillRect(42, 14, this.hq * 6 >> 8, 2);
      var1.fillRect(42, 17, this.hr * 6 >> 8, 2);
      var1.setColor(4783872);
      var1.fillRect(42, 14, this.hn * 6 >> 8, 2);
      if (hp <= 0 && (hf || this.hh || hg)) {
         var1.setColor(this.bu);
         var1.fillRect(42, 17, this.hr * 6 >> 8, 2);
      } else {
         var1.setColor(104191);
         var1.fillRect(42, 17, hp * 6 >> 8, 2);
      }

      ((a)this).a(var1, String.valueOf(this.hu), 58, 24, 1);
   }

   private void g(Graphics var1) {
      for(int var2 = this.fq; var2 >= 0; --var2) {
         int var3 = this.fu[var2];
         int var4 = this.fv[var2];
         int var5 = var3 - iV;
         int var6 = var4 - iW;
         if (var5 + (this.fr << 1) >= 0 && var6 + this.fs >= 0 && var5 - this.fr <= 176 && var6 - this.fs <= 208) {
            ((a)this).a(var1, this.fp, var2 + 121, var3, var4, 0);
         }
      }

   }

   private void h(Graphics var1) {
      for(int var2 = this.fK; var2 >= 0; --var2) {
         for(int var3 = 0; var3 < 4; ++var3) {
            int var4 = (var2 << 2) + var3;
            int var5 = fC[var4];
            int var6 = fD[var4];
            int var7 = var5 - iV;
            int var8 = var6 - iW;
            if (var7 + this.fB[fI[var2]].c >= 0 && var8 + this.fB[fI[var2]].d >= 0 && var7 - this.fB[fI[var2]].c <= 176 && var8 - this.fB[fI[var2]].d <= 208) {
               this.fB[(fI[var2] << 2) + var3].a(var1, var5, var6, 0);
            }
         }
      }

   }

   private void i(Graphics var1) {
      for(int var2 = ch; var2 >= 0; --var2) {
         if (this.cc[var2] >= 0) {
            int var3 = cd[var2];
            int var4 = ce[var2];
            int var5 = var3 - iV;
            int var6 = var4 - iW;
            if (var5 + this.cg[var2] >= 0 && var6 + this.cf[var2] >= 0 && var5 - this.cg[var2] <= 176 && var6 - this.cf[var2] <= 208) {
               ((a)this).a(var1, this.cb, var2 + 10, var3, var4, 0);
            }
         }
      }

   }

   private void j(Graphics var1) {
      for(int var2 = ca; var2 >= 0; --var2) {
         if (this.bS[var2] >= 0) {
            int var3 = bT[var2];
            int var4 = this.bU[var2];
            int var5 = var3 - iV;
            int var6 = var4 - iW;
            if (var5 + this.bY[var2] >= 0 && var6 + this.bX[var2] >= 0 && var5 - this.bY[var2] <= 176 && var6 - this.bX[var2] <= 208) {
               ((a)this).a(var1, this.bR, var2 + 30, var3, var4, 0);
            }
         }
      }

   }

   private void k(Graphics var1) {
      for(int var2 = co; var2 >= 0; --var2) {
         if (this.cj[var2] >= 0) {
            int var3 = this.ck[var2];
            int var4 = cl[var2];
            int var5 = var3 - iV;
            int var6 = var4 - iW;
            if (var5 + this.cn[var2] >= 0 && var6 + this.cm[var2] >= 0 && var5 - this.cn[var2] <= 176 && var6 - this.cm[var2] <= 208) {
               ((a)this).a(var1, this.ci, var2 + 40, var3, var4, 0);
            }
         }
      }

   }

   private void l(Graphics var1) {
      for(int var2 = this.dD; var2 >= 0; --var2) {
         if (this.dv[var2] >= 0) {
            int var3 = dx[var2];
            int var4 = this.dy[var2];
            int var5 = var3 - iV;
            int var6 = var4 - iW;
            if (var5 + this.dC[var2] >= 0 && var6 + dB[var2] >= 0 && var5 - this.dC[var2] <= 176 && var6 - dB[var2] <= 208) {
               ((a)this).a(var1, this.du, var2 + 70, var3, var4, 0);
            }
         }
      }

   }

   private void m(Graphics var1) {
      for(int var2 = this.dt; var2 >= 0; --var2) {
         if (this.dm[var2] >= 0) {
            int var3 = dp[var2];
            int var4 = dq[var2];
            int var5 = var3 - iV;
            int var6 = var4 - iW;
            if (var5 + this.ds[var2] >= 0 && var6 + this.dr[var2] >= 0 && var5 - this.ds[var2] <= 176 && var6 - this.dr[var2] <= 208) {
               ((a)this).a(var1, this.dl, var2 + 60, var3, var4, this.do[var2]);
            }
         }
      }

   }

   private void n(Graphics var1) {
      for(int var2 = fb; var2 >= 0; --var2) {
         if (this.eR[var2] >= 0) {
            int var3 = this.eS[var2];
            int var4 = this.eT[var2];
            int var5 = var3 - iV;
            int var6 = var4 - iW;
            if (var5 + this.eW[var2] >= 0 && var6 + this.eX[var2] >= 0 && var5 <= 176 && var6 - this.eX[var2] <= 208) {
               ((a)this).a(var1, this.eQ, var2 + 111, var3, var4, 0);
            }
         }
      }

   }

   private void o(Graphics var1) {
      for(int var2 = this.fd; var2 >= 0; --var2) {
         if (this.fe[var2] >= 0) {
            int var3 = fh[var2];
            int var4 = this.fi[var2];
            int var5 = var3 - iV;
            int var6 = var4 - iW;
            if (var5 + (fj[var2] << 1) >= 0 && var6 + this.fk[var2] >= 0 && var5 - fj[var2] <= 176 && var6 - this.fk[var2] <= 208) {
               ((a)this).a(var1, this.fc, var2 + 116, var3, var4, 0);
            }
         }
      }

   }

   private void p(Graphics var1) {
      for(int var2 = this.cB; var2 >= 0; --var2) {
         int var3 = this.cC[var2];
         int var4 = cD[var2];
         int var5 = var3 - iV;
         int var6 = var4 - iW;
         if (var5 + this.cF[var2] >= 0 && var6 + this.cG[var2] >= 0 && var5 - this.cF[var2] <= 176 && var6 - this.cG[var2] <= 208) {
            ((a)this).a(var1, cA, var2 + 106, var3, var4, 0);
         }
      }

   }

   private void q(Graphics var1) {
      for(int var2 = cM; var2 >= 0; --var2) {
         if (cX[var2] >= 0) {
            int var3 = cQ[var2];
            int var4 = this.cR[var2];
            int var5 = var3 - iV;
            int var6 = var4 - iW;
            if (var5 + cT[var2] >= 0 && var6 + this.cV[var2] >= 0 && var5 - cT[var2] <= 176 && var6 - this.cV[var2] <= 208) {
               ((a)this).a(var1, this.cN, var2 + 100, var3, var4, 0);
            }
         }
      }

   }

   private void r(Graphics var1) {
      for(int var2 = this.eF; var2 >= 0; --var2) {
         if (this.dT[var2] >= 0) {
            int var3 = dX[var2];
            int var4 = this.dY[var2];
            int var5 = var3 - iV;
            int var6 = var4 - iW;
            if (var5 + this.ei[var2] >= 0 && var6 + this.ej[var2] >= 0 && var5 - this.ei[var2] <= 176 && var6 - this.ej[var2] <= 208) {
               boolean var7 = this.en[var2];
               switch (this.dU[var2]) {
                  case 1:
                     ((a)this).a(var1, this.dG, var2 + 85, dX[var2], this.dY[var2], var7 ? 0 : 2);
                     break;
                  case 2:
                     ((a)this).a(var1, dH, var2 + 85, dX[var2], this.dY[var2], var7 ? 0 : 2);
                     break;
                  case 3:
                     if (dQ == var2) {
                        ((a)this).a(var1, this.dJ, var2 + 85, dX[var2], this.dY[var2], var7 ? 0 : 2);
                     } else {
                        ((a)this).a(var1, this.dI, var2 + 85, dX[var2], this.dY[var2], var7 ? 0 : 2);
                     }
               }

               if (this.eb[var2] > 0) {
                  ((a)this).a(var1, this.bL, 137, dX[var2], this.dY[var2], 0);
                  ((a)this).b(137, M);
               }

               if (J == 100 && (this.gi < 0 && this.eb[var2] <= 0 && eo[var2] < er[var2] >> 1 && !this.el[var2] && eo[var2] >= 0 || this.dU[var2] == 3 && this.dV[var2] == 12 && dQ != var2 && this.dR != var2 || this.dU[var2] == 2 && this.dV[var2] == 12 && dQ != var2)) {
                  ((a)this).a(var1, this.aV, 132, dX[var2], this.dY[var2] - this.ej[var2] - (this.dR == var2 ? -(this.ej[var2] >> 3) : this.ej[var2] >> 2), 0);
               }
            }
         }
      }

   }

   private void s(Graphics var1) {
      ((a)this).a(var1, this.fL, 135, this.ge, this.gf, hy ? 0 : 2);
   }

   private void t(Graphics var1) {
      for(int var2 = this.bQ; var2 >= 0; --var2) {
         if (bM[var2] >= 0) {
            ((a)this).a(var1, this.bL, bM[var2], this.bN[var2], this.bO[var2], this.bP[var2]);
         }
      }

   }

   private void d(int var1, int var2) {
      switch (var1) {
         case 0:
            for(int var47 = 0; var47 < var2; ++var47) {
               int var62 = this.m(10);
               int var76 = this.bw[var47] / 16;
               int var87 = this.bx[var47] / 16;

               for(int var94 = 0; var94 <= var62; ++var94) {
                  this.b(var76 + var94, var87, 8192);
               }
            }

            return;
         case 3:
            this.aE = var2;
            this.aI = new boolean[var2];
            this.aH = new boolean[var2];
            this.aF = new int[var2];
            this.aG = new int[var2];

            for(int var23 = 0; var23 < var2; ++var23) {
               this.aF[var23] = this.bw[var23];
               this.aG[var23] = this.bx[var23];
               this.aI[var23] = this.m(1) == 1;
               this.aH[var23] = this.m(1) == 1;
            }

            return;
         case 4:
            this.fq = var2 - 1;
            this.fx = new int[var2];
            this.fu = new int[var2];
            this.fv = new int[var2];
            ft = new boolean[var2];
            fw = new int[var2];
            int var46 = 1 << this.bG;

            for(int var61 = 0; var61 < var2; ++var61) {
               this.fx[var61] = this.m(3);
               fw[var61] = this.m(10);
               if (this.fx[var61] == 3) {
                  if (fw[var61] > 0) {
                     fw[var61] = -1;
                  } else {
                     fw[var61] = -2;
                  }
               }

               this.fu[var61] = this.bw[var61];
               this.fv[var61] = this.bx[var61];
               if ((fw[var61] != -1 || (hi & var46) != var46) && (fw[var61] != -2 || (hi & var46 << 10) != var46 << 10)) {
                  ft[var61] = false;
               } else {
                  ft[var61] = true;
                  fw[var61] = 0;
                  int[] var96 = this.fx;
                  var96[var61] += 4;
               }

               ((a)this).a(121 + var61, 5120, this.fx[var61]);
               if (ft[var61]) {
                  ((a)this).b(121 + var61, 2000);
               }

               ((a)this).a(this.fp, 121 + var61, 0, 0, 0, this.Q);
               this.fr = this.Q[2];
               this.fs = this.Q[3];
            }
         case 1:
         case 2:
         case 15:
         case 16:
         case 19:
         case 20:
         case 24:
         default:
            return;
         case 5:
            for(int var45 = 0; var45 < var2; ++var45) {
               int var60 = this.m(10);
               int var75 = this.bw[var45] / 16;
               int var86 = this.bx[var45] / 16;

               for(int var93 = 0; var93 <= var60; ++var93) {
                  this.b(var75, var86 + var93, 8);
               }
            }

            return;
         case 6:
            for(int var13 = 0; var13 < 40; ++var13) {
               this.aZ[var13] = this.m(10);
            }

            for(int var14 = 0; var14 < 40; ++var14) {
               this.ba[var14] = this.m(10);
            }

            return;
         case 7:
            this.fe = new int[var2];
            this.ff = new int[var2];
            fg = new int[var2];
            fh = new int[var2];
            this.fi = new int[var2];
            fj = new int[var2];
            this.fk = new int[var2];
            this.fl = new byte[var2];
            this.fm = new byte[var2];
            fn = new boolean[var2];
            fo = new boolean[var2];

            for(int var44 = 0; var44 < var2; ++var44) {
               this.fd = var44;
               this.fe[var44] = this.m(6) << 1;
               fn[var44] = this.m(1) == 1;
               fo[var44] = this.m(1) == 1;
               ((a)this).a(116 + var44, 2048, this.fe[var44]);
               ((a)this).a(this.fc, 116 + var44, 0, 0, 0, this.Q);
               fh[var44] = this.bw[var44];
               this.fi[var44] = this.bx[var44];
               this.ff[var44] = this.bw[var44] / 16;
               fg[var44] = this.bx[var44] / 16;
               fj[var44] = this.Q[2];
               this.fk[var44] = this.Q[3];
               this.fl[var44] = (byte)(fj[var44] / 16);
               this.fm[var44] = (byte)(this.fk[var44] / 16);
               int var59 = this.ff[var44];
               int var74 = fg[var44];
               if (!((a)this).b(116 + var44, 1)) {
                  this.l(var44, 2);
                  this.q(var44);
               } else {
                  for(int var85 = 0; var85 < this.fl[var44]; ++var85) {
                     for(int var92 = 1; var92 <= this.fm[var44]; ++var92) {
                        this.b(var59 + var85, var74 - var92, 1);
                     }
                  }
               }
            }

            return;
         case 8:
            this.cp = new int[var2];
            cq = new int[var2];
            this.cr = new int[var2];
            this.cs = new int[var2];
            cz = var2 - 1;

            for(int var43 = 0; var43 < var2; ++var43) {
               this.cp[var43] = this.bw[var43] / 16;
               cq[var43] = this.bx[var43] / 16;
               this.b(this.cp[var43], cq[var43], 128);
               this.cr[var43] = this.m(10);
               this.cs[var43] = this.m(3);
            }

            return;
         case 9:
            this.db = new byte[var2];
            this.dc = new int[var2];
            dd = new int[var2];

            for(int var42 = 0; var42 < var2; ++var42) {
               int var58 = this.m(10);
               this.db[var42] = (byte)this.m(3);
               int var73 = this.bw[var42] / 16;
               int var84 = this.bx[var42] / 16;
               this.dc[var42] = var73;
               dd[var42] = var84;

               for(int var91 = 0; var91 <= var58; ++var91) {
                  this.b(var73, var84 + var91, 256);
               }
            }

            return;
         case 10:
            for(int var41 = 0; var41 < var2; ++var41) {
               int var57 = this.m(10);
               int var72 = this.bw[var41] / 16;
               int var83 = this.bx[var41] / 16;

               for(int var90 = 0; var90 <= var57; ++var90) {
                  this.b(var72 + var90, var83, 64);
               }
            }

            return;
         case 11:
         case 12:
         case 13:
            c[] var22 = (c[])null;
            byte var26 = -1;
            byte var28 = -1;
            if (var1 == 11) {
               var22 = this.dG;
               var26 = 1;
               var28 = 0;
               if (this.dE) {
               }
            } else if (var1 == 12) {
               var22 = dH;
               var26 = 2;
               var28 = 0;
               if (this.dF) {
               }
            } else if (var1 == 13) {
               var22 = this.dI;
               var26 = 3;
               var28 = 0;
            }

            for(int var40 = this.eF; var40 < this.eF + var2; ++var40) {
               this.dT[var40] = var40;
               dX[var40] = this.bw[var40 - this.eF];
               this.dY[var40] = this.bx[var40 - this.eF];
               this.ed[var40] = dX[var40] / 16;
               this.ee[var40] = (this.dY[var40] - 1) / 16;
               this.b(this.ed[var40], this.ee[var40] - 1, 2);
               this.dV[var40] = var28;
               this.dT[var40] = var40;
               this.dU[var40] = var26;
               ey[var40] = 0;
               dZ[var40] = dX[var40];
               ea[var40] = this.dY[var40];
               this.et[var40] = this.dY[var40] - this.ee[var40] * 16 << 8;
               this.eb[var40] = 0;
               eg[var40] = (byte)this.m(3);
               eh[var40] = eg[var40];
               this.ex[var40] = this.m(10) * 1000;
               this.dP = this.m(1) == 1 ? var40 : 0;
               ev[var40] = this.ex[var40];
               er[var40] = this.m(16);
               eo[var40] = er[var40];
               this.eq[var40] = this.m(16);
               this.ep[var40] = (byte)this.m(10);
               this.ec[var40] = (byte)this.m(10);
               this.en[var40] = this.m(1) != 1;
               ew[var40] = this.en[var40];
               ez[var40] = this.m(1) == 1;
               if (var1 == 13) {
                  boolean var56 = this.m(1) == 1;
                  boolean var71 = this.m(1) == 1;
                  if (ez[var40]) {
                     var28 = 1;
                  }

                  if (var56 && !var71) {
                     this.dJ = ((a)this).b(13313, bK[this.bG] >= 0 ? ((a)this).c(13315 + bK[this.bG]) : null);
                     ((a)this).g(13312);
                     dQ = var40;
                     int[] var95 = eo;
                     var95[var40] <<= 2;
                     er[var40] = eo[var40];
                  } else if (var71) {
                     this.dJ = ((a)this).b((int)14337);
                     ((a)this).g(14336);
                     this.dR = var40;
                     dQ = var40;
                     int[] var10000 = eo;
                     var10000[var40] <<= 2;
                     er[var40] = eo[var40];
                  } else {
                     ((a)this).g(15360);
                  }
               } else if (ez[var40]) {
                  var28 = 1;
               }

               this.i(var28, var40);
               ((a)this).a(var22, 85 + var40, 0, 0, 0, this.Q);
               this.ei[var40] = (short)this.Q[2];
               ef[var40] = (byte)(this.ei[var40] / 16 >> 1);
               if (ef[var40] == 0) {
                  ef[var40] = 1;
               }

               this.ej[var40] = (short)this.Q[3];
               this.ek[var40] = (byte)(this.ej[var40] / 16);
            }

            this.eF += var2;
            return;
         case 14:
            this.eG = new int[var2];
            this.eH = new int[var2];
            this.eI = new int[var2 * 15];
            this.eJ = new int[var2 * 15];
            this.eK = new boolean[var2 * 15];

            for(int var39 = 0; var39 < var2; ++var39) {
               int var55 = this.m(10);
               this.eG[var39] = this.bw[var39] / 16;
               this.eH[var39] = this.bx[var39] / 16;

               for(int var69 = 0; var69 < 15; ++var69) {
                  if (var69 < 15) {
                     this.eI[var69 + var39 * 15] = this.m(10);
                     this.eJ[var69 + var39 * 15] = this.m(10);
                     this.eK[var69 + var39 * 15] = this.m(1) != 1;
                  } else {
                     this.m(10);
                     this.m(10);
                     this.m(1);
                  }
               }

               for(int var70 = 0; var70 < var55; ++var70) {
                  this.b(this.eG[var39], this.eH[var39] + var70, 4);
               }
            }

            return;
         case 17:
            co = var2 - 1;

            for(int var21 = 0; var21 < var2; ++var21) {
               this.cj[var21] = this.m(6);
               this.ck[var21] = this.bw[var21];
               cl[var21] = this.bx[var21];
               ((a)this).a(40 + var21, 3072, this.cj[var21]);
               ((a)this).a(this.ci, 40 + var21, 0, 0, 0, this.Q);
               this.cn[var21] = this.Q[2];
               this.cm[var21] = this.Q[3];
            }

            return;
         case 18:
            for(int var38 = 0; var38 < var2; ++var38) {
               int var54 = this.m(10);
               int var68 = this.bw[var38] / 16;
               int var82 = this.bx[var38] / 16;

               for(int var89 = 0; var89 <= var54; ++var89) {
                  this.b(var68 + var89, var82, 16);
               }
            }

            return;
         case 21:
            this.dt = var2 - 1;

            for(int var37 = 0; var37 < var2; ++var37) {
               this.dm[var37] = this.m(6) << 1;
               dp[var37] = this.bw[var37];
               dq[var37] = this.bx[var37];
               this.do[var37] = 0;
               ((a)this).a(60 + var37, 5121, this.dm[var37]);
               ((a)this).a(this.dl, 60 + var37, 0, 0, 0, this.Q);
               this.ds[var37] = this.Q[2];
               this.dr[var37] = this.Q[3];
            }

            return;
         case 22:
            for(int var15 = 0; var15 < var2; ++var15) {
               int var3 = 0;

               for(int var19 = 0; var19 < 1; ++var19) {
                  if (this.m(1) == 1) {
                     var3 |= 1 << var19;
                  }
               }

               int var20 = this.bw[var15] / 16;
               int var25 = this.bx[var15] / 16;
               int var27 = this.m(10);
               int var36 = this.m(10);
               int var53 = this.m(10);
               int var67 = this.m(10);

               for(int var78 = 0; var78 <= var27; ++var78) {
                  this.b(var20, var25 - var78, var3);
               }

               for(int var79 = 0; var79 <= var36; ++var79) {
                  this.b(var20, var25 + var79, var3);
               }

               for(int var80 = 0; var80 <= var53; ++var80) {
                  this.b(var20 - var80, var25, var3);
               }

               for(int var81 = 0; var81 <= var67; ++var81) {
                  this.b(var20 + var81, var25, var3);
               }
            }

            return;
         case 23:
            this.eM = new int[var2];
            this.eN = new int[var2];
            this.eO = new int[var2];
            eP = new byte[var2];
            this.bv = var2;

            for(int var18 = var2 - 1; var18 >= 0; --var18) {
               this.eM[var18] = this.m(8);
               this.eN[var18] = this.bw[var2 - var18 - 1];
               this.eO[var18] = this.bx[var2 - var18 - 1];
               eP[var18] = (byte)this.m(3);
            }

            return;
         case 25:
            for(int var35 = 0; var35 < var2; ++var35) {
               this.b(this.bw[var35] / 16, this.bx[var35] / 16, 1024);
            }

            return;
         case 26:
            this.ga = this.bw[0];
            this.gb = this.bx[0];
            this.gc = this.ga;
            this.gd = this.gb;
            this.gy = false;
            this.gr = false;
            this.hn = this.hq;
            hp = this.hr;
            this.hA = false;
            gk = false;
            this.ge = this.ga;
            this.gf = this.gb;
            return;
         case 27:
            fb = var2 - 1;
            this.eR = new int[var2];
            this.eS = new int[var2];
            this.eT = new int[var2];
            eU = new int[var2];
            this.eV = new int[var2];
            this.eW = new int[var2];
            this.eX = new int[var2];
            this.eY = new byte[var2];
            this.eZ = new byte[var2];
            fa = new boolean[var2];

            for(int var34 = 0; var34 < var2; ++var34) {
               this.eR[var34] = this.m(3);
               fa[var34] = this.m(1) == 1;
               ((a)this).a(111 + var34, 6144, this.eR[var34]);
               ((a)this).a(this.eQ, 111 + var34, 0, 0, 0, this.Q);
               this.eS[var34] = this.bw[var34];
               this.eT[var34] = this.bx[var34];
               eU[var34] = this.bw[var34] / 16;
               this.eV[var34] = this.bx[var34] / 16;
               this.eW[var34] = this.Q[2];
               this.eX[var34] = this.Q[3];
               this.eY[var34] = (byte)(this.eW[var34] / 16);
               this.eZ[var34] = (byte)(this.eX[var34] / 16);
               int var52 = eU[var34];
               int var66 = this.eV[var34];

               for(int var77 = 0; var77 < this.eY[var34]; ++var77) {
                  for(int var88 = 1; var88 <= this.eZ[var34]; ++var88) {
                     this.b(var52 + var77, var66 - var88, 1);
                     this.b(var52 + var77, var66 - var88, 512);
                     if (fa[var34]) {
                        this.b(var52 + var77, var66 - var88, 8);
                     }
                  }
               }

               this.b(var52, var66, 1);
            }

            return;
         case 28:
            this.df = new int[var2];
            this.de = new int[var2];
            this.dg = new int[var2];
            dh = new int[var2];
            this.di = new int[var2];
            dj = new int[var2];
            this.dk = new int[var2];

            for(int var33 = 0; var33 < var2; ++var33) {
               this.de[var33] = this.bw[var33] / 16;
               this.df[var33] = this.bx[var33] / 16;
               this.dg[var33] = this.m(10);
               dh[var33] = this.m(10);
               this.di[var33] = this.m(3);
               this.dk[var33] = this.m(10);
               dj[var33] = 0;

               for(int var51 = 0; var51 <= this.dg[var33]; ++var51) {
                  for(int var65 = 0; var65 <= dh[var33]; ++var65) {
                     this.b(this.de[var33] + var51, this.df[var33] + var65, 32);
                  }
               }
            }

            return;
         case 29:
            for(int var32 = 0; var32 < var2; ++var32) {
               int var50 = this.m(10);
               int var64 = this.bw[var32] / 16;
               int var11 = this.bx[var32] / 16;

               for(int var12 = 0; var12 <= var50; ++var12) {
                  this.b(var64 + var12, var11, 16384);
               }
            }

            return;
         case 30:
            for(int var31 = 0; var31 < var2; ++var31) {
               if (this.m(1) != 0) {
                  this.b(this.bw[var31] / 16, this.bx[var31] / 16, 2048);
               } else {
                  this.b(this.bw[var31] / 16, this.bx[var31] / 16, 4096);
               }
            }

            return;
         case 31:
            for(int var17 = 0; var17 < var2; ++var17) {
               int var4 = 0;

               for(int var6 = 0; var6 < 1; ++var6) {
                  if (this.m(1) == 1) {
                     var4 |= 1 << var6;
                  }
               }

               int var24 = this.bw[var17] / 16;
               int var7 = this.bx[var17] / 16;
               this.b(var24, var7, var4);
            }

            return;
         case 32:
            ca = var2 - 1;

            for(int var16 = 0; var16 < var2; ++var16) {
               this.bS[var16] = this.m(6) << 1;
               bT[var16] = this.bw[var16];
               this.bU[var16] = this.bx[var16];
               this.bZ[var16] = this.m(1) == 1;
               ((a)this).a(30 + var16, 5122, this.bS[var16]);
               ((a)this).b(this.hx, 30 + var16, 0);
               bV[var16] = this.hx[0] + bT[var16];
               this.bW[var16] = this.hx[1] + this.bU[var16];
               this.bY[var16] = this.hx[2];
               this.bX[var16] = this.hx[3];
            }

            return;
         case 33:
            this.dD = var2 - 1;

            for(int var30 = 0; var30 < var2; ++var30) {
               this.dv[var30] = this.m(6);
               this.dw[var30] = this.m(10);
               int var49 = this.m(3) * 100;
               dx[var30] = this.bw[var30];
               this.dy[var30] = this.bx[var30];
               this.dz[var30] = this.bw[var30];
               this.dA[var30] = this.bx[var30];
               ((a)this).a(70 + var30, 6145, this.dv[var30]);
               ((a)this).a(this.du, 70 + var30, 0, 0, 0, this.Q);
               ((a)this).b(70 + var30, var49);
               this.dC[var30] = this.Q[2];
               dB[var30] = this.Q[3];
            }

            return;
         case 34:
            this.cB = var2 - 1;
            this.cC = new int[var2];
            cD = new int[var2];
            this.cH = new boolean[var2];
            cK = new int[var2];
            this.cJ = new int[var2];
            this.cL = new int[var2 + 1 << 2];
            cI = new int[var2];
            this.cF = new int[var2];
            this.cE = new boolean[var2];
            this.cG = new int[var2];

            for(int var29 = 0; var29 < var2; ++var29) {
               int var48 = this.bw[var29] / 16;
               int var63 = this.bx[var29] / 16;
               this.cC[var29] = var48 * 16;
               cD[var29] = var63 * 16;
               this.cJ[var29] = var48;
               cK[var29] = var63;
               this.cH[var29] = false;
               this.cE[var29] = this.m(1) == 1;
               cI[var29] = 0 + (this.cE[var29] ? 4 : 0);
               this.cL[var29 << 2] = this.m(3);
               this.cL[(var29 << 2) + 1] = this.m(3);
               this.cL[(var29 << 2) + 2] = this.m(3);
               this.cL[(var29 << 2) + 3] = this.m(3);
               ((a)this).a(106 + var29, 2050, cI[var29]);
               ((a)this).a(cA, 106 + var29, 0, 0, 0, this.Q);
               this.cF[var29] = this.Q[2];
               this.cG[var29] = this.Q[3];
            }

            return;
         case 35:
            cM = var2 - 1;
            cQ = new int[var2];
            this.cR = new int[var2];
            cO = new int[var2];
            cP = new int[var2];
            cZ = new boolean[var2];
            this.cW = new int[var2];
            cX = new int[var2];
            this.cY = new int[var2];
            cS = new int[var2];
            this.cU = new int[var2];
            cT = new int[var2];
            this.cV = new int[var2];
            this.da = new boolean[var2];

            for(int var8 = 0; var8 < var2; ++var8) {
               cQ[var8] = this.bw[var8];
               this.cR[var8] = this.bx[var8];
               cO[var8] = this.bw[var8] / 16;
               cP[var8] = this.bx[var8] / 16;
               this.cW[var8] = this.m(3);
               this.cY[var8] = this.m(10);
               cZ[var8] = this.m(1) == 1;
               this.da[var8] = this.m(1) == 1;
               cX[var8] = (this.cY[var8] << 2) + 0;
               ((a)this).a(100 + var8, 2049, cX[var8]);
               ((a)this).a(this.cN, 100 + var8, 0, 0, 0, this.Q);
               cS[var8] = this.Q[2] / 16;
               this.cU[var8] = this.Q[3] / 16;
               cT[var8] = this.Q[2];
               this.cV[var8] = this.Q[3];
               cX[var8] = (this.cY[var8] << 2) + 2 - (cZ[var8] ? 2 : 0);
               ((a)this).a(100 + var8, 2049, cX[var8]);

               for(int var9 = 0; var9 < this.cU[var8]; ++var9) {
                  for(int var10 = 0; var10 < cS[var8]; ++var10) {
                     if (cZ[var8]) {
                        this.c(cO[var8] + var10, cP[var8] - var9, 1);
                     } else {
                        this.b(cO[var8] + var10, cP[var8] - var9, 1);
                     }
                  }
               }
            }

            return;
         case 36:
            ch = var2 - 1;

            for(int var5 = 0; var5 < var2; ++var5) {
               this.cc[var5] = this.m(6);
               cd[var5] = this.bw[var5];
               ce[var5] = this.bx[var5];
               ((a)this).a(10 + var5, 3074, this.cc[var5]);
               ((a)this).a(this.cb, 10 + var5, 0, 0, 0, this.Q);
               this.cg[var5] = this.Q[2];
               this.cf[var5] = this.Q[3];
            }

      }
   }

   private void a(byte[] var1, boolean var2) {
      switch (this.v) {
         case 0:
            if (!var2) {
               this.iT = null;
               this.jb = null;
               this.ja = null;
            }

            for(int var3 = 0; var3 < 10; ++var3) {
               bM[var3] = -1;
            }

            this.a(var1);
            this.m(1);
            this.m(10);
            this.c(var2);
            this.m(1);
            this.bA = this.m(16);
            this.bB = this.m(4);
            int[] var10 = new int[this.bA];

            for(int var4 = 0; var4 < this.bA; ++var4) {
               var10[var4] = this.m(this.bB);
            }

            this.bC = l(this.bA);
            int var13 = 0;
            int var5 = 0;
            if (var2 && jd != 0) {
               var13 = jd;
               var5 = jc;
            }

            jc = this.m(16);
            jd = this.m(16);
            if (!var2) {
               this.by = 16 * jc;
               this.bz = 16 * jd;
               this.aC = this.by - 176;
               this.aD = this.bz - 208;
            }

            je = jc * jd;
            if (!var2) {
               this.jb = new short[je];
            }

            this.ja = new byte[je];

            for(int var6 = 0; var6 < this.ja.length; ++var6) {
               this.ja[var6] = (byte)this.m(this.bC);
            }

            this.m(8);
            this.b(this.bA, var2);
            this.iT = b.a(176, 208);
            this.iT.a(jc, jd, this.iU, 16, 16);
            int var7 = 0;

            for(int var8 = 0; var8 < jd; ++var8) {
               for(int var9 = 0; var9 < jc; ++var9) {
                  this.iT.a(var9, var8, 1 + var10[this.ja[var7] & 255]);
                  ++var7;
               }
            }

            this.ja = null;
            this.bA = this.m(10);
            if (!var2) {
               jf = jc * 16;
               jg = jd * 16;
            }

            this.bD = l(jf * jg + 1);
            jf = jc * 16;
            jg = jd * 16;
            if (var2 && var13 != 0) {
               jd = var13;
               jc = var5;
            }
         case 1:
         default:
            break;
         case 2:
            if (!var2) {
               this.fc = ((a)this).b(2051, bK[this.bG] >= 0 ? ((a)this).c(2059 + bK[this.bG]) : null);
            }
            break;
         case 3:
            if (!var2) {
               this.fL = ((a)this).b(28673, bK[this.bG] >= 0 ? ((a)this).c(28675 + bK[this.bG]) : null);
            }
            break;
         case 4:
            if (!var2) {
               this.eL = ((a)this).b(3076, bK[this.bG] >= 0 ? ((a)this).c(3084 + bK[this.bG]) : null);
            }
            break;
         case 5:
            if (!var2) {
               if (this.dE) {
                  this.dG = ((a)this).b(18433, bK[this.bG] >= 0 ? ((a)this).c(18435 + bK[this.bG]) : null);
                  ((a)this).g(18432);
               } else {
                  this.dG = ((a)this).b(4097, bK[this.bG] >= 0 ? ((a)this).c(4099 + bK[this.bG]) : null);
                  ((a)this).g(4096);
               }

               if (this.dF) {
                  dH = ((a)this).b(16385, bK[this.bG] >= 0 ? ((a)this).c(16387 + bK[this.bG]) : null);
                  ((a)this).g(16384);
               } else {
                  dH = ((a)this).b(17409, bK[this.bG] >= 0 ? ((a)this).c(17411 + bK[this.bG]) : null);
                  ((a)this).g(17408);
               }

               this.dI = ((a)this).b(15361, bK[this.bG] >= 0 ? ((a)this).c(15363 + bK[this.bG]) : null);
               ((a)this).g(15360);
            }
            break;
         case 6:
            if (!var2) {
               this.bR = ((a)this).b(5125, bK[this.bG] >= 0 ? ((a)this).c(5142 + bK[this.bG]) : null);
               this.dl = ((a)this).b(5124, bK[this.bG] >= 0 ? ((a)this).c(5132 + bK[this.bG]) : null);
               this.eQ = ((a)this).b(6146, bK[this.bG] >= 0 ? ((a)this).c(6150 + bK[this.bG]) : null);
            }
            break;
         case 7:
            if (!var2) {
               this.du = ((a)this).b(6147, bK[this.bG] >= 0 ? ((a)this).c(6153 + bK[this.bG]) : null);
               this.fp = ((a)this).b(5123, bK[this.bG] >= 0 ? ((a)this).c(5129 + bK[this.bG]) : null);
            }
            break;
         case 8:
            if (!var2) {
               this.cN = ((a)this).b(2052, bK[this.bG] >= 0 ? ((a)this).c(2062 + bK[this.bG]) : null);
               cA = ((a)this).b(2053, bK[this.bG] >= 0 ? ((a)this).c(2065 + bK[this.bG]) : null);
            }
            break;
         case 9:
            if (!var2) {
               this.cb = ((a)this).b(3077, bK[this.bG] >= 0 ? ((a)this).c(3087 + bK[this.bG]) : null);
               this.ci = ((a)this).b(3075, bK[this.bG] >= 0 ? ((a)this).c(3081 + bK[this.bG]) : null);
               this.bL = ((a)this).b(7169, (byte[])null);
               ((a)this).a(137, 7168, 31);
            }
      }

      if (this.v == 10 && !var2 || var2) {
         this.bv = 0;
         if (!var2) {
            this.iT.a(1000);
         }

         this.hR = 0;

         while(this.bA-- > 0) {
            int var11 = this.m(10);
            int var14 = this.m(16);
            int var15 = 0;
            this.bw = new int[var14];
            this.bx = new int[var14];
            int var16 = 0;

            for(int var17 = 0; var17 < var14; ++var17) {
               for(var15 += this.m(this.bD); var15 >= jf; ++var16) {
                  var15 -= jf;
               }

               this.bw[var17] = var15;
               this.bx[var17] = var16;
            }

            this.d(var11, var14);
         }

         for(int var12 = this.fq; var12 >= 0; --var12) {
            if (ft[var12]) {
               this.e(this.fu[var12] / 16, this.fv[var12] / 16);
            }
         }

         this.jh = null;
         this.q();
         this.ab();
         ((a)this).a((DataInputStream)null);
      }

      ++this.v;
   }

   private void q() {
      short[] var1 = new short[256];
      int var2 = 0;

      for(int var3 = 0; var3 < this.bv; ++var3) {
         ((a)this).a(136, 3073, this.eM[var3]);
         var2 += ((a)this).a((short[])var1, 136, 0);
      }

      this.iT.a(this.bv + var2, jc <= jd);

      for(int var7 = 0; var7 <= 7; ++var7) {
         for(int var4 = this.bv - 1; var4 >= 0; --var4) {
            if (eP[var4] == var7) {
               ((a)this).a(136, 3073, this.eM[var4]);
               int var5 = ((a)this).a((short[])var1, 136, 0);

               for(int var6 = 0; var6 < var5; ++var6) {
                  this.iT.a(this.eL[var1[var6 * 4]], this.eN[var4] + var1[var6 * 4 + 1], this.eO[var4] + var1[var6 * 4 + 2], var1[var6 * 4 + 3]);
               }
            }
         }
      }

      this.eN = null;
      this.eO = null;
      this.eM = null;
      eP = null;
   }

   private void c(boolean var1) {
      if (!var1) {
         this.R = this.m(10);
         hy = this.m(1) != 1;
         this.hz = hy;
         this.dE = this.m(1) == 1;
         this.dF = this.m(1) == 1;
         this.m(1);
         int var2 = this.m(16);
         this.hw = this.hw < var2 ? var2 : this.hw;
      }

   }

   private static int l(int var0) {
      int var1 = -1;

      int var10000;
      do {
         var10000 = var0 - 1;
         ++var1;
      } while(var10000 >> var1 > 0);

      return var1;
   }

   private void a(byte[] var1) {
      this.jh = var1;
      this.ji = 0;
      this.jj = 0;
      this.jk = 0;
   }

   private int m(int var1) {
      int var2 = 0;

      int var4;
      for(int var3 = var1; var3 > 0; var3 -= var4) {
         if (this.jk == 0) {
            this.jj = this.jh[this.ji++] & 255;
            this.jk = 8;
         }

         var4 = Math.min(var3, this.jk);
         var2 |= (this.jj >> 8 - this.jk & ~(-1 << var4)) << var1 - var3;
         this.jk -= var4;
      }

      return var2;
   }

   private void b(int var1, boolean var2) {
      for(int var3 = 0; var3 < var1; ++var3) {
         for(int var4 = 0; var4 < 1; ++var4) {
            if (this.m(1) == 1 && !var2) {
               short[] var10000 = this.jb;
               var10000[var3] = (short)(var10000[var3] | 1 << var4);
            }
         }
      }

      if (!var2) {
         this.iU = ((a)this).c(2058, bK[this.bG] >= 0 ? ((a)this).c(2068 + bK[this.bG]) : null);
      }

   }

   private void n(int var1) {
      ((a)this).a(0 + this.bQ, 0 + bM[var1]);
      this.bP[var1] = this.bP[this.bQ];
      this.bN[var1] = this.bN[this.bQ];
      this.bO[var1] = this.bO[this.bQ];
      bM[this.bQ] = -1;
      if (this.bQ >= 0) {
         --this.bQ;
      }

   }

   private void o(int var1) {
      if (this.bS[ca] >= 0) {
         ((a)this).a(ca + 30, var1 + 30);
      }

      this.bS[var1] = this.bS[ca];
      bT[var1] = bT[ca];
      this.bU[var1] = this.bU[ca];
      this.bY[var1] = this.bY[ca];
      this.bX[var1] = this.bX[ca];
      bV[var1] = bV[ca];
      this.bW[var1] = this.bW[ca];
      this.bS[ca] = -1;
      if (ca > 0) {
         --ca;
      }

   }

   private void p(int var1) {
      if (this.dm[this.dt] >= 0) {
         ((a)this).a(this.dt + 60, var1 + 60);
      }

      this.dm[var1] = this.dm[this.dt];
      dp[var1] = dp[this.dt];
      dq[var1] = dq[this.dt];
      this.do[var1] = this.do[this.dt];
      this.dn[var1] = this.dn[this.dt];
      this.dr[var1] = this.dr[var1];
      this.ds[var1] = this.ds[var1];
      this.dm[this.dt] = -1;
      if (this.dt >= 0) {
         --this.dt;
      }

   }

   private void q(int var1) {
      if (this.fe[this.fd] >= 0) {
         ((a)this).a(this.fd + 116, var1 + 116);
      }

      this.fe[var1] = this.fe[this.fd];
      fh[var1] = fh[this.fd];
      this.fi[var1] = this.fi[this.fd];
      this.ff[var1] = this.ff[this.fd];
      fg[var1] = fg[this.fd];
      this.fl[var1] = this.fl[this.fd];
      this.fm[var1] = this.fm[this.fd];
      this.fe[this.fd] = -1;
      if (this.fd >= 0) {
         --this.fd;
      }

   }

   private void e(int var1, int var2) {
      this.c(var1 + 1, var2, 128);
      this.c(var1 - 1, var2 + 1, 128);
      this.c(var1 - 1, var2, 128);
      this.c(var1 + 1, var2 + 1, 128);
      this.c(var1, var2 + 1, 128);
      this.c(var1, var2, 128);
   }

   private void r(int var1) {
      int var2 = var1 << 2;
      int var3 = this.fK << 2;
      System.arraycopy(fC, var3, fC, var2, 4);
      System.arraycopy(fD, var3, fD, var2, 4);
      fH[var1] = fH[this.fK];
      fE[var1] = fE[this.fK];
      fF[var1] = fF[this.fK];
      fG[var1] = fG[this.fK];
      fI[var1] = fI[this.fK];
      fJ[var1] = fJ[this.fK];
      if (this.fK >= 0) {
         --this.fK;
      }

   }

   private void b(int var1, int var2, int var3, int var4) {
      if (var4 != 0 && (var3 != 0 || hv < this.hw)) {
         if (var3 == 1 || var3 == 2) {
            var4 <<= 3;
         }

         byte var5;
         byte var6;
         if (var4 > 60) {
            var5 = 18;
            var6 = 9;
         } else {
            var5 = 6;
            var6 = 3;
         }

         int var7 = -1;
         if (var3 == 3) {
            for(int var8 = this.eF; var8 >= 0; --var8) {
               if (a(iV - 64, iW - 64, 304, 336, dX[var8] - this.ei[var8], this.dY[var8] - this.ej[var8], this.ei[var8] << 1, this.ej[var8])) {
                  ++var7;
                  if (var7 >= this.Q.length) {
                     var7 = this.Q.length - 1;
                     break;
                  }

                  this.Q[var7] = var8;
               }
            }

            if (var7 == -1) {
               return;
            }

            hp -= 256;
            if (hp <= 0) {
               hp = 0;
               this.W();
            }
         }

         int var13 = 0;
         boolean var9 = false;

         while(!var9) {
            this.O = P.nextInt();
            if (this.fK >= 18 && var4 > 0) {
               var9 = true;
               int[] var24 = fJ;
               int var32 = this.fK - 1;
               var24[var32] += var4;
               var4 = 0;
            } else {
               ++this.fK;
               int var10 = this.fK << 2;
               int var11 = (this.O & 128) > 0 ? 1 : -1;
               int var12 = var1 + var11 * (this.O & 9);
               fC[var10++] = var12;
               fC[var10++] = var12;
               fC[var10++] = var12;
               fC[var10++] = var12;
               var10 -= 4;
               fE[this.fK] = var11 * (this.O & 7) << 8;
               var11 = (this.O & 256) > 0 ? 1 : -1;
               var12 = var2 + var11 * (this.O & 9) - 7;
               fD[var10++] = var12;
               fD[var10++] = var12;
               fD[var10++] = var12;
               fD[var10] = var12;
               fF[this.fK] = (this.O >> 3 & 7) << 8;
               fI[this.fK] = var3;
               fH[this.fK] = 0;
               if (var3 == 3) {
                  fG[this.fK] = this.Q[var13];
                  ++var13;
                  if (var13 > var7) {
                     var13 = 0;
                  }
               } else {
                  fG[this.fK] = -1;
               }

               if (var4 > 24) {
                  fJ[this.fK] = var5;
                  var4 -= var5;
               } else {
                  fJ[this.fK] = var6;
                  var4 -= var6;
               }

               if (var4 < 0) {
                  int[] var10000 = fJ;
                  int var31 = this.fK;
                  var10000[var31] += var4;
                  var4 = 0;
                  var9 = true;
               }
            }
         }

      }
   }

   private void r() {
      this.gm = false;
      this.gi = -1;
      fV = false;
      this.aS = false;
      this.hA = false;
      this.W();
      this.gA = this.ge / 16;
      gB = this.gf / 16;
      this.hn = this.hq;
      this.ho = this.hn;
      iV = this.ge - 88 - ((hy ? 1 : -1) * 176 >> 2);
      iW = this.gf - 168;
      this.aP = iV << 8;
      this.aQ = iW << 8;
      this.gN = -1;
      this.gO = -1;
      this.s(0);
      ((a)this).a(133, 1025, 4);
      ((a)this).a(this.fL, 135, 0, 0, 0, this.Q);
      this.hm = this.gf % 16;
      hl = this.ge % 16;
      gG = this.Q[2] >> 1;
      this.gH = this.Q[3];
      this.hk = this.gH / 16;
      this.hB = gG >> 1;
      this.hC = -(gG >> 1);
   }

   private void f(int var1, int var2) {
      if (super.a != 0) {
         this.ge += (!hy || super.a <= 0) && (hy || super.a >= 0) ? this.hC : this.hB - 1;
      }

      if (super.b < 0) {
         this.gf -= this.gH;
      } else {
         --this.gf;
      }

      int var5 = var1;
      int var6 = this.w();
      hl = this.ge - var1 * 16 << 8;
      if (hl <= 0) {
         var5 = var1 - 1;
         hl += 4096;
      } else if (hl >= 4096) {
         var5 = var1 + 1;
         hl -= 4096;
      }

      this.hm = this.gf - var6 * 16 << 8;
      if (super.a != 0) {
         this.ge -= (!hy || super.a <= 0) && (hy || super.a >= 0) ? this.hC : this.hB - 1;
      }

      if (super.b < 0) {
         this.gf += this.gH;
      } else {
         ++this.gf;
      }

      int var7 = (super.a > 0 ? super.a : -super.a) << 8;
      int var8 = (super.b > 0 ? super.b : -super.b) << 8;
      int var9 = var7;
      int var10 = var8;
      this.hG = true;
      hH = true;
      if (var7 > var8) {
         if (var7 > 4096) {
            var8 = (var8 * 16 << 8) / var7;
            var7 = 4096;
         }
      } else if (var8 > 4096) {
         var7 = (var7 * 16 << 8) / var8;
         var8 = 4096;
      }

      int var3;
      if ((!hy || super.a <= 0) && (hy || super.a >= 0)) {
         var3 = -var7;
      } else {
         var3 = var7;
      }

      int var4 = super.b > 0 ? var8 : -var8;
      int var11 = 0;
      int var12 = 0;
      this.hM = 0;
      this.hN = 0;

      while(var11 < var9 || var12 < var10) {
         hl += var3;
         this.hm += var4;
         if (hl >= 4096) {
            hl -= 4096;
            ++var5;
         } else if (hl < 0) {
            hl += 4096;
            --var5;
         }

         if (this.hm >= 4096) {
            this.hm -= 4096;
            ++var6;
         } else if (this.hm < 0) {
            this.hm += 4096;
            --var6;
         }

         this.g(var5, var6);
         if ((this.h(var5, var6) & 32) == 0) {
            this.gO = -1;
            this.gN = -1;
         } else {
            this.iF = true;
            if (this.gN == -1) {
               for(int var13 = 0; var13 < this.de.length; ++var13) {
                  if (a(var1, var2, this.de[var13], this.df[var13], this.dg[var13], dh[var13])) {
                     if (dj[var13] < this.dk[var13] && this.dk[var13] > 0 && this.gO <= 0) {
                        this.gN = var13;
                        this.gO = 2000;
                        if (dj[0] < this.dk[var13]) {
                           gQ = true;
                        }
                     } else {
                        this.gO = -1;
                        this.gN = -1;
                        this.iF = true;
                     }
                  }
               }
            }
         }

         if (this.hG) {
            this.hM += var3;
            var11 += var7;
         } else {
            hl -= var3;
            var9 = var11;
            if ((this.h(var1, var2) & 1) != 0 && !this.hJ) {
               this.hM = 0;
               if ((!hy || super.a <= 0) && (hy || super.a >= 0)) {
                  this.ge = var1 * 16 + (gG >> 1);
               } else {
                  this.ge = (var1 + 1) * 16 - (gG >> 1);
               }
            }
         }

         if (hH) {
            this.hN += var4;
            var12 += var8;
         } else {
            this.hm -= var4;
            var10 = var12;
            if (super.b < 0 && this.gr) {
               var6 += this.hk + 1;
            }
         }
      }

      this.ge += this.hM >> 8;
      this.gf += this.hN >> 8;
      if (var11 > var9) {
         int var14 = var11 - var9 >> 8;
         this.ge -= var3 > 0 ? var14 : -var14;
      }

      if (var12 > var10) {
         int var15 = var12 - var10 >> 8;
         this.gf -= var4 > 0 ? var15 : -var15;
      }

      if (!this.hG) {
         if (hl < 0) {
            --var5;
         } else if (hl > 16) {
            ++var5;
         }
      }

      this.gA = var5;
      gB = var6;
   }

   private boolean s() {
      int var1 = (!hy || this.hE != 22) && (hy || this.hE != 23) ? -1 : 1;
      byte var2 = 0;
      if (this.gp == 1) {
         var2 = 1;
      }

      int var3 = eU[gh] + (var1 < 0 ? this.eY[gh] - 1 : 0);
      int var4 = gB;
      int var5 = this.eZ[gh];
      if (this.hE == 23 && var5 <= this.hk) {
         var5 = this.hk + 1;
      }

      for(int var6 = 0; var6 < var5; ++var6) {
         if ((this.h(var3 + var1 + var1, var4 - var6) & 1) != 0) {
            return false;
         }

         if (this.hE == 23 && ((this.h(var3 + var1 + var1 + var1, var4 - var6) & 1) != 0 || (this.h(var3 + var1 + var1 + var1 + var1, var4 - var6) & 1) != 0)) {
            return false;
         }

         if ((this.h(var3 + var1 * (this.eY[gh] + var2), var4 - var6) & 1) != 0) {
            var2 = 0;
         }
      }

      if (this.hE == 23 && (this.h(this.gA + var1, var4) & 1) != 0) {
         return false;
      } else {
         for(int var8 = 0; var8 < this.eY[gh]; ++var8) {
            for(int var7 = 0; var7 < this.eZ[gh]; ++var7) {
               this.c(eU[gh] + var8, var4 - var7, 1);
               this.c(eU[gh] + var8, var4 - var7, 512);
               if (fa[gh]) {
                  this.c(eU[gh] + var8, var4 - var7, 8);
               }
            }
         }

         int[] var10000 = eU;
         int var10001 = gh;
         var10000[var10001] += var1 > 0 ? 1 + var2 : -(1 + var2);

         for(int var9 = 0; var9 < this.eY[gh]; ++var9) {
            for(int var10 = 0; var10 < this.eZ[gh]; ++var10) {
               this.b(eU[gh] + var9, var4 - var10, 1);
               this.b(eU[gh] + var9, var4 - var10, 512);
               if (fa[gh]) {
                  this.b(var3 + var9 + var2 + this.eY[gh] * var1, var4 - var10, 8);
               }
            }
         }

         this.hK = true;
         ((a)this).a(111 + gh, 6144, this.eR[gh] + (3 << var2));
         return true;
      }
   }

   private void g(int var1, int var2) {
      boolean var3 = hy && super.a > 0 || !hy && super.a < 0;
      if ((this.h(var1, var2) & 1024) != 0) {
         this.gc = var1 * 16;
         this.gd = var2 * 16;
         this.hz = hy;
      }

      if ((this.h(var1, var2) & 256) != 0) {
         int var4;
         for(var4 = var2; (this.h(var1, var4) & 256) != 0; --var4) {
         }

         ++var4;

         for(int var5 = 0; var5 < this.db.length; ++var5) {
            if (this.dc[var5] == var1 && dd[var5] == var4) {
               for(int var6 = 0; var6 < cZ.length; ++var6) {
                  if (this.cW[var6] == this.db[var5]) {
                     cZ[var6] = !cZ[var6];
                  }
               }
               break;
            }
         }

         while((this.h(var1, var4) & 256) != 0) {
            this.c(var1, var4, 256);
            ++var4;
         }
      }

      if ((this.h(var1, var2) & 4) != 0) {
         int var15 = var2;

         for(int var22 = 0; (this.h(var1, var2 - var22) & 4) != 0; ++var22) {
            --var15;
            this.c(var1, var2 - var22, 4);
         }

         int var23 = 1;
         ++var15;

         while((this.h(var1, var2 + var23) & 4) != 0) {
            this.c(var1, var2 + var23, 4);
            ++var23;
         }

         int var26 = -1;

         for(int var7 = 0; var7 < this.eG.length; ++var7) {
            if (this.eG[var7] == var1 && this.eH[var7] == var15) {
               var26 = var7;
               break;
            }
         }

         for(int var29 = 0; var29 <= this.eF; ++var29) {
            eg[var29] = eh[var29];
            if (this.eI[this.ec[var29] + var26 * 15] != 0) {
               dZ[var29] = this.eI[this.ec[var29] + var26 * 15] * 16;
               ea[var29] = this.eJ[this.ec[var29] + var26 * 15] * 16;
               ew[var29] = this.eK[this.ec[var29] + var26 * 15];
               if (!a(iV - 64, iW - 64, 304, 336, dX[var29] - this.ei[var29], this.dY[var29] - this.ej[var29], this.ei[var29] << 1, this.ej[var29])) {
                  eo[var29] = er[var29];
                  dX[var29] = dZ[var29];
                  this.dY[var29] = ea[var29];
                  this.c(this.ed[var29], this.ee[var29] - 1, 2);
                  this.ed[var29] = dX[var29] / 16;
                  this.ee[var29] = this.dY[var29] / 16;
                  this.b(this.ed[var29], this.ee[var29] - 1, 2);
                  switch (this.dU[var29]) {
                     case 1:
                        this.i(0, var29);
                        break;
                     case 2:
                        this.i(0, var29);
                        break;
                     case 3:
                        this.i(0, var29);
                  }
               }
            }
         }
      }

      int var17 = 0;
      this.hI = false;

      for(int var24 = this.gH; var24 > 0; var24 -= 16) {
         if (var2 < jd) {
            if (var2 - var17 < 0 || var2 - var17 >= jd) {
               this.hG = false;
               break;
            }

            if (var24 > 0) {
               if (this.hG && var1 >= 0) {
                  this.hG = (this.h(var1, var2 - var17) & 1) == 0;
               } else {
                  this.hG = false;
               }

               if (!this.hG) {
                  if (this.hE != 22 && this.gx && (this.h(var1, var2 - var17) & 512) != 0) {
                     int var27 = hy ? 1 : -1;

                     int var30;
                     for(var30 = 0; (this.h(var1 + var30, var2) & 512) != 0 && !hy; var30 += var27) {
                     }

                     var30 += hy ? 0 : 1;

                     for(int var8 = fb; var8 >= 0; --var8) {
                        if (eU[var8] == var1 + var30 && this.eV[var8] == var2 + 1) {
                           this.hJ = true;
                           this.ge = var1 * 16 + (!hy ? this.hB : this.hC);
                           this.s(21);
                           gh = var8;
                        }
                     }
                  }
                  break;
               }
            }

            if (var1 < 0 || var1 >= jc) {
               this.hG = false;
            }

            if (super.b >= 0) {
               ++var17;
            } else {
               --var17;
            }

            if (gu && var24 - 16 <= 0 && super.b == 0 && (this.h(var1, var2 - var17) & 16) == 0) {
               this.hG = false;
               this.s(26);
               ++this.gf;
            }
         }
      }

      int var25 = var1;
      if (super.a == 0 && super.b != 0) {
         var25 = var1 + (hy ? 1 : -1);
      }

      if (super.b >= 0) {
         var17 = this.hk;
         if (this.hm < 2048) {
            ++var17;
         }
      } else {
         var17 = 0;
      }

      int var28 = var2 - var17;
      int var32 = hy ? -1 : 1;
      if (this.hn > 0 && !this.gz && (this.h(var25, var28 - 1) & 1) == 0 && ((this.h(var25, var28) & 1) != 0 || this.gy) && (this.h(var25 + var32, var28) & 1) == 0 && (this.h(var25 + var32, var28 - 1) & 1) == 0 && (this.h(var25 + var32, var28 + this.hk) & 1) == 0 && super.b != 0 && (!this.gr || this.gy) && !gv && !this.gC && J != 105) {
         if (this.gy && super.b < 0) {
            this.s(38);
            int var34 = 0;
            if ((this.h(var25, var2) & 1) == 0) {
               ++var34;
            }

            this.gf = (var2 + var34) * 16 + this.gH;
         } else if (this.hE != 40 && !this.gr) {
            this.s(37);
            this.gf = (var2 - var17) * 16 + this.gH;
         }

         this.hN = 0;
         if (!hy) {
            ++var25;
         }

         this.ge = var25 * 16 + (hy ? -(gG >> 1) : gG >> 1);
         this.hM = 0;
         hH = false;
         this.hG = false;
      } else {
         if (!this.hG && super.a != 0) {
            var17 = var3 ? -1 : 1;
         } else {
            var17 = 0;
         }

         int var33 = var2;
         int var9;
         if ((var9 = super.b > 0 ? var2 - this.hk : var2 + this.hk) < 0) {
            var9 = 0;
         }

         if (super.b != 0) {
            boolean var10 = false;

            for(int var11 = gG; var11 > 0; var11 -= 16) {
               if (var1 + var17 >= 0 && var1 + var17 < jc) {
                  if (hH) {
                     hH = (this.h(var1 + var17, var33) & 1) == 0;
                     if (((this.h(var1 + var17, var9) & 16) != 0 || (this.h(var1 + var17, var9) & 8192) != 0) && hF != 121 && this.hE != 38 && this.hn > 0) {
                        if ((this.h(var1 + var17, var9) & 8192) != 0 && hF != 24 && hF != 25 && !this.gt) {
                           this.s(120);
                           this.gf = (var9 + 1) * 16 + this.gH;
                           hH = false;
                           this.hN = 0;
                           this.gD = true;
                        } else if ((this.h(var1 + var17, var9) & 16) != 0) {
                           this.gf = (var9 + 1) * 16 + this.gH;
                           hH = false;
                           this.hN = 0;
                           this.s(45);
                        }
                     }
                  }

                  if (!hH && super.b > 0 && !gu && this.hE != 38) {
                     this.gf = var2 * 16;
                     this.hN = 0;
                     this.hI = true;
                     int var12 = var1;
                     if ((!hy || super.a <= 0) && (hy || super.a >= 0)) {
                        if (super.a != 0) {
                           var12 = var1 + 1;
                        }
                     } else {
                        var12 = var1 - 1;
                     }

                     if ((this.h(var12, var2) & 16384) != 0 && hF != 48) {
                        this.s(50);
                     } else if (this.hE != 77 && this.hE != 95 && this.hE != 108 && this.hE != 114 && this.hE != 115 && this.hE != 116 && this.hE != 60 && hF != 120 && hF != 121 && hF != 48) {
                        if (fV) {
                           this.t();
                        }

                        this.s(27);
                        if (this.hO >= 4) {
                           this.aw = this.hO;
                        }
                     } else if (this.hE != 77 && this.hE != 60) {
                        if (this.hE == 95 && !var10) {
                           var10 = true;
                           this.b(this.ge, this.gf - this.gH + (this.gH >> 2), 3, fP[ha]);
                        }
                     } else {
                        this.gC = true;
                        this.aw += 6;
                        this.ab();
                        this.y(2);
                     }
                  }

                  if (!hH && (hF == 24 || hF == 25) && super.b < 0) {
                     this.s(99);
                     break;
                  }

                  if (var33 < 0 || var33 >= jd) {
                     hH = false;
                  }

                  if (super.a == 0) {
                     break;
                  }
               }

               int var10000;
               if (var3) {
                  --var17;
                  var10000 = var17;
               } else {
                  ++var17;
                  var10000 = var17;
               }

               var17 = var10000;
            }
         }

         if (this.gr && this.hE != 38) {
            var1 += hy ? 1 : -1;
            if ((this.h(var1, var33) & 8) == 0) {
               hH = false;
               this.gf = (var33 + (super.b < 0 ? 1 : 0)) * 16 + (super.b < 0 ? this.gH : 0);
               if (super.b >= 0 || (this.h(var1, var33) & 1) != 0) {
                  if (hF == 34) {
                     this.s(26);
                  } else {
                     this.s(33);
                  }

                  hH = false;
               }

               if (super.b < 0) {
                  this.gy = true;
               }
            }

            var1 = var1;
         }

         if (!this.hG && super.a != 0 && !this.hI && this.hE != 38 && !this.gr && !this.gt) {
            if ((!hy || super.a <= 0 || hl << 8 <= 16 - (gG >> 1)) && (hy || super.a >= 0 || hl >> 8 >= gG >> 1)) {
               if (!hy && super.a > 0 && hl << 8 > 16 - (gG >> 1) || hy && super.a < 0 && hl >> 8 < gG >> 1) {
                  this.ge = (var1 + 1) * 16 - this.hC;
                  this.hJ = true;
                  if ((this.h(var1, var33 + (super.b > 0 ? -1 : 1)) & 8) != 0 && (this.h(var1, var33 + (super.b > 0 ? -this.hk : this.hk)) & 8) != 0 && super.b != 0) {
                     this.s(33);
                  }
               }
            } else {
               this.ge = var1 * 16 - this.hB;
               this.hJ = true;
               if ((this.h(var1, var33 + (super.b > 0 ? -1 : 1)) & 8) != 0 && (this.h(var1, var33 + (super.b > 0 ? -this.hk : this.hk)) & 8) != 0 && this.hE != 38 && super.b != 0) {
                  this.s(33);
               }
            }

            this.hM = 0;
         }

         if (!this.hG && super.a != 0) {
            if (this.hE == 9) {
               this.s(0);
            } else if (this.hE == 51) {
               this.s(50);
            }
         }

         if (fV && (!this.hG || !hH)) {
            this.t();
         }

      }
   }

   private int h(int var1, int var2) {
      return var1 >= 0 && var1 < jc && var2 >= 0 && var2 < jd ? this.jb[var1 + jc * var2] : 65;
   }

   private void b(int var1, int var2, int var3) {
      if (var1 >= 0 && var1 < jc && var2 >= 0 && var2 < jd) {
         short[] var10000 = this.jb;
         int var10001 = var1 + jc * var2;
         var10000[var10001] = (short)(var10000[var10001] | var3);
      }
   }

   private void c(int var1, int var2, int var3) {
      if (var1 >= 0 && var1 < jc && var2 >= 0 && var2 < jd) {
         short[] var10000 = this.jb;
         int var10001 = var1 + jc * var2;
         var10000[var10001] = (short)(var10000[var10001] & ~var3);
      }
   }

   private void t() {
      if (this.dU[this.gi] == 1) {
         this.i(0, this.gi);
      } else if (this.dU[this.gi] == 2) {
         this.i(0, this.gi);
      } else if (this.dU[this.gi] == 3) {
         this.i(0, this.gi);
      }

      this.s(26);
      fV = false;
      this.hL = 0;
   }

   private void i(int var1, int var2) {
      this.em[var2] = this.dU[var2] == 1 && (var1 == 13 || var1 == 17 || var1 == 23 || var1 == 12 || var1 == 15 || var1 == 16 || var1 == 14 || var1 == 21);
      this.el[var2] = this.dU[var2] == 1 && (var1 == 19 || var1 == 18 || var1 == 26 || var1 == 20);
      if (this.dU[var2] == 1) {
         if (var1 != 5 && var1 != 6 && var1 != 16 && var1 != 15) {
            if (var1 != 8 && var1 != 9 && var1 != 10 && var1 != 24) {
               if (var1 != 2 && var1 != 7) {
                  if (var1 != 11 && var1 != 21 && var1 != 3) {
                     if (var1 == 1) {
                        ey[var2] = P.nextInt() & 511;
                     }

                     this.dW[var2] = -1;
                  } else {
                     this.dW[var2] = 3;
                  }
               } else {
                  this.dW[var2] = 2;
               }
            } else {
               this.eb[var2] = 0;
               this.dW[var2] = 1;
            }
         } else {
            this.dW[var2] = 0;
         }
      } else if (this.dU[var2] == 2) {
         if (var1 != 7 && var1 != 12 && var1 != 13) {
            this.dW[var2] = -1;
         } else {
            this.eb[var2] = 0;
            this.dW[var2] = 1;
         }

         if (var1 == 1) {
            ey[var2] = P.nextInt() & 511;
         }
      } else if (this.dU[var2] == 3) {
         if (var1 != 7 && var1 != 13 && var1 != 17 && var1 != 16) {
            this.dW[var2] = -1;
         } else {
            this.eb[var2] = 0;
            this.dW[var2] = 1;
         }

         if (var1 == 1) {
            ey[var2] = P.nextInt() & 511;
         }
      }

      if ((this.gi != -1 && var2 != this.gi || this.az > 0) && !this.el[var2] && var1 != 7 && var1 != 11) {
         switch (this.dU[var2]) {
            case 1:
               if (this.dV[var2] == 0 && var1 != 27) {
                  return;
               }

               if (var1 != 27) {
                  var1 = 0;
               }
               break;
            case 2:
               if ((this.dV[var2] == 0 || this.dV[var2] == 12 || this.dV[var2] == 10) && var1 != 14) {
                  return;
               }

               if (var1 != 14) {
                  var1 = 0;
               }
               break;
            case 3:
               if ((this.dV[var2] == 0 || this.dV[var2] == 12 || this.dV[var2] == 11) && var1 != 18) {
                  return;
               }

               if (var1 != 18) {
                  var1 = 0;
               }
         }
      }

      this.dV[var2] = var1;
      if (this.dU[var2] == 1) {
         if (this.dE) {
            ((a)this).a(85 + var2, 18432, var1);
         } else {
            ((a)this).a(85 + var2, 4096, var1);
         }
      } else if (this.dU[var2] == 2) {
         if (this.dF) {
            ((a)this).a(85 + var2, 16384, var1);
         } else {
            ((a)this).a(85 + var2, 17408, var1);
         }
      } else {
         if (this.dU[var2] == 3) {
            if (dQ == var2 && this.dR != var2) {
               ((a)this).a(85 + var2, 13312, var1);
               return;
            }

            if (this.dR == var2) {
               ((a)this).a(85 + var2, 14336, var1);
               return;
            }

            ((a)this).a(85 + var2, 15360, var1);
         }

      }
   }

   private void u() {
      this.s(14);
      this.i(24, this.gi);
      this.b(dX[this.gi], this.dY[this.gi] - (this.ej[this.gi] >> 1), 1, this.eq[this.gi]);
      eo[this.gi] = -1;
      this.a(dX[this.gi] - (this.ei[this.gi] >> 1), this.dY[this.gi] - (this.ej[this.gi] >> 1), 22, !hy ? 2 : 0, false);
   }

   private void s(int var1) {
      this.fS = 0;
      this.fT = 0;
      gg = 0;
      this.hE = var1;
      hj = 0;
      boolean var2 = false;
      if (var1 == 22 && hf) {
         var1 = 20;
      }

      if ((var1 == 5 || var1 == 8) && hf) {
         ++var1;
      } else if (var1 == 100 && hf) {
         ++var1;
      } else if (var1 == 38 && hf) {
         var1 = 39;
      } else if (var1 == 27 && hf) {
         var1 = 29;
      } else if (var1 == 3) {
         if (hf) {
            var1 = 4;
         }
      } else if (var1 == 97 && hf) {
         var1 = 98;
      }

      if (var1 != 14 && var1 != 88 && var1 != 90 && var1 != 89 && var1 != 91 && var1 != 108 && var1 != 109 && var1 != 114 && var1 != 116 && var1 != 115 && var1 != 113 && var1 != 93 && var1 != 92 && var1 != 12 && var1 != 13 && var1 != 117 && (this.dR != this.gi || this.gi >= 0 && this.dV[this.gi] != 16)) {
         fV = false;
         this.hL = 0;
         this.gi = -1;
         this.iF = true;
      }

      if (var1 != 31) {
         this.gq = false;
      } else {
         this.gq = true;
      }

      if (var1 == 0) {
         this.gz = false;
         if (this.gD) {
            var1 = 10;
         } else if (hf) {
            var1 = 1;
         }
      } else if (var1 == 31 && hf) {
         var1 = 32;
      } else if (var1 != 54 && var1 != 36 && var1 != 47) {
         this.gI = false;
         this.gz = false;
         this.fR = false;
      } else {
         this.gz = true;
         var2 = true;
         if (!this.gr && !gu) {
            if (this.fR) {
               this.fR = false;
               this.gK = 0;
            }

            if (hf) {
               var1 = 72 + this.gK;
               if (this.gK < this.gS) {
                  ++this.gK;
               } else {
                  this.gI = false;
                  var1 = 77;
                  this.hE = 77;
                  this.fR = true;
               }
            } else {
               var1 += this.gK;
               if (this.gK < gR) {
                  ++this.gK;
               } else {
                  var1 = 60;
                  this.fR = true;
                  this.gI = false;
                  this.hE = 60;
               }
            }
         }
      }

      gl = var1 == 7 || var1 == 5 || var1 == 8 || var1 == 6 || var1 == 101 || var1 == 119 || var1 == 118 || var1 == 100 || var1 == 5;
      if (var1 == 67) {
         var2 = true;
         this.gt = true;
         if (hf) {
            var1 = 83 + this.gK;
            if (this.gK > 3 - (gT < 3 ? 1 : 0)) {
               var1 = 83;
               this.gK = 0;
            } else if (this.gK == 2 - (gT < 3 ? 1 : 0)) {
               var1 = 85;
            }
         } else {
            var1 = 67 + this.gK;
            if (this.gK >= 3 - (gT < 3 ? 1 : 0)) {
               var1 = 67;
               this.gK = 0;
            } else if (this.gK == 2 - (gT < 3 ? 1 : 0)) {
               var1 = 69;
            }
         }

         ++this.gK;
      } else {
         this.gt = false;
      }

      if (var1 == 91 || var1 == 92 || var1 == 93) {
         this.gt = true;
      }

      if (!this.gt && !this.gz) {
         this.gK = 0;
      }

      if (var1 != 35 && var1 != 34 && var1 != 36 && var1 != 33) {
         this.gr = false;
         this.gy = false;
      } else {
         this.gr = true;
      }

      gu = var1 == 45 || var1 == 47 || var1 == 46 || var1 == 119 || var1 == 118;
      if (var1 == 9) {
         this.gx = true;
         if (this.gD) {
            var1 = 11;
         }
      } else {
         this.gx = false;
      }

      if (var1 != 37 && var1 != 40) {
         gv = false;
         this.gy = false;
      } else {
         gv = true;
      }

      this.gw = var1 == 30;
      this.go = var1 == 21 || var1 == 22 || var1 == 23 || var1 == 20;
      this.gs = var1 == 50 || var1 == 51;
      if (var1 != 15 && var1 != 17) {
         this.gn = false;
      } else {
         this.gn = true;
         if (hf) {
            ++var1;
         }
      }

      if (var2 && !gu && this.hn > 0) {
         int var3 = this.O & 1;
         this.y(3 + var3);
      }

      hF = var1;
      ((a)this).a(135, 28672, var1);
   }

   private boolean t(int var1) {
      return ((a)this).b(var1, M);
   }

   private int v() {
      return this.ge / 16;
   }

   private int w() {
      int var1;
      return (var1 = this.gf / 16) > jd ? jd : var1;
   }

   private void x() {
      int var1 = this.v();
      int var2 = (this.hm >> 8) + 1 >= 16 ? gB + 1 : gB;
      int var3 = this.ge - (gG >> 1);
      int var4 = this.gf;
      this.gC = gk;
      boolean var5 = this.gD;
      if (!this.gC) {
         this.gD = (this.h(var1, var2) & 8192) != 0 && hF != 99 && hF != 24 && hF != 25 && hF != 26;
         this.gC = (this.h(var1, var2) & 1) != 0;
         this.gE = (this.h(var1, var2 - 1) & 2048) != 0;
         gF = (this.h(var1, var2 - 1) & 4096) != 0;
      }

      if (this.gD && !var5) {
         if (this.gx) {
            this.gC = true;
            this.s(9);
         }
      } else if (!this.gD && var5 && this.gx) {
         this.s(9);
      }

      boolean var6 = ((a)this).b(135, M);
      gg += M;
      this.j(var1, var2);
      if (this.hE != 52 && this.hE != 53) {
         if (this.hE == 26) {
            super.b += this.hO;
            this.hP += M;
            if (this.hP > 200) {
               this.hP -= 200;
               this.hO += this.hO >= 8 ? 0 : 4;
            }
         } else {
            this.hP = 0;
            this.hO = 0;
         }

         if (super.b < 0 && this.hE != 60) {
            this.gC = false;
         }

         if (J != 105 && J != 108 && J != 101) {
            if (!fV && this.gi < 0 && this.az < 0) {
               switch (this.t) {
                  case 1:
                     this.H();
                     break;
                  case 2:
                     this.K();
                  case 3:
                  case 4:
                  case 7:
                  case 8:
                  default:
                     break;
                  case 5:
                     this.J();
                     break;
                  case 6:
                     this.I();
               }
            }

            if (!fV && this.az < 0) {
               var6 = this.f(var6);
               if (this.C) {
                  if (this.s == 23) {
                     this.hn = -1;
                  }

                  if (this.s == 16) {
                     this.D = !this.D;
                  }

                  if (this.s == 25) {
                     this.hn = this.hq;
                  }

                  if (this.s == 19) {
                     this.E = !this.E;
                  }
               }
            } else if (this.az < 0) {
               this.A();
            }

            this.z();
            if (!this.gC && !this.gD && !this.gt && !gu && !gk && this.hE != 26 && this.hE != 99 && this.hE != 48 && (this.gK != 5 && this.gK != 6 && this.gK != this.gS || !hf) && !this.gw && var6) {
               this.e(var6);
            } else if (!var6) {
               this.d(var6);
            } else if (this.hE == 3 && !this.hA) {
               this.s(28);
            }

            if (this.hn <= 0 && this.gi < 0 && (!this.go || hF == 21)) {
               if (!this.gC && hF != 26) {
                  this.s(26);
               } else if (this.gC && hF != 52 && hF != 53) {
                  this.s(52);
                  this.hn = 0;
               }
            }
         } else {
            this.g(var6);
         }

         this.f(var1, var2);
      } else if (this.hE == 52 && !var6 && J != 108) {
         this.a(I, I, 35, 35);
         J = 108;
         this.U = 0;
         this.V = 0;
      }

      if (!this.gz && !this.gt) {
         gJ = 0;
      } else {
         gJ += M;
      }

      if (this.D) {
         this.hn = this.hq;
      }

      this.B();
      if (hF == 9 && ((this.h(this.gA + (hy ? 1 : -1), gB + 2) & 16384) != 0 || (this.h(this.gA + (hy ? 1 : -1), gB + 3) & 16384) != 0)) {
         this.s(48);
      }

      this.a(this.ge, this.gf, 135, this.hx, hy);
      this.gM -= M;
      if (this.gM < 0 && gL != 0) {
         this.gM = 0;
         gL = 0;
         this.iF = true;
      }

      this.C();
      this.y();
      this.L();
      this.M();
      this.N();
      this.O();
      this.P();
      this.Q();
      this.R();
      this.S();
      this.T();
      this.U();
      this.k(var3, var4);
      this.t(132);
      this.V();
   }

   private void y() {
      for(int var1 = this.bQ; var1 >= 0; --var1) {
         if (bM[var1] >= 0 && !this.t(bM[var1] + 0)) {
            this.n(var1);
         }
      }

   }

   private void d(boolean var1) {
      if (this.hE == 31 && !var1) {
         for(int var2 = this.fq; var2 >= 0; --var2) {
            if (!ft[var2] && a(this.fu[var2], this.fv[var2], this.fr, this.fs, this.ge - this.hB, this.gf, gG, this.gH)) {
               this.fx[var2] += 4;
               this.s(31);
               ((a)this).a(121 + var2, 5120, this.fx[var2]);
               ft[var2] = true;
               this.e(this.gA, gB);
            }
         }
      }

      if (this.hE == 27 && !this.hA && !hf) {
         this.s(28);
      } else if (this.hE == 27 && this.hA) {
         this.s(3);
      } else if (this.hA && this.hE == 3) {
         this.s(3);
      } else if (this.hE == 38) {
         this.s(0);
      } else if (this.hE == 40) {
         this.s(26);
      } else if (hF == 121) {
         if ((this.h(this.gA, gB + 1) & 8192) != 0) {
            this.gD = true;
         }

         this.s(0);
      } else if (hF == 120) {
         this.gf += 16;
         this.s(26);
      } else if (this.hE != 47 && this.hE != 119 && this.hE != 118) {
         if (this.hE == 97) {
            this.s(3);
         } else if (this.hE == 88) {
            if (this.fW >= this.fU) {
               eo[this.gi] = 1;
               this.s(89);
               this.b(dX[this.gi], this.dY[this.gi] - (this.ej[this.gi] >> 1), 2, this.eq[this.gi] >> 1);
               this.i(10, this.gi);
            } else {
               this.s(90);
               this.i(0, this.gi);
            }

            fV = false;
            this.hL = 0;
         } else if (this.gz && this.gI) {
            hy = this.fZ;
            this.s(54);
            gJ = 0;
            this.gI = false;
         } else {
            if ((this.gC || !this.gI) && !fV) {
               if (this.hE == 23) {
                  this.s(21);
                  return;
               }

               if (this.hE == 36) {
                  this.s(33);
                  return;
               }

               if ((!this.hA || !this.gC) && (this.gC || this.gD && hF != 26 && hF != 25 && hF != 70 && !this.gt && hF != 24)) {
                  if (hF == 48) {
                     this.s(50);
                     return;
                  }

                  this.s(0);
                  return;
               }

               if (this.hE != 26 && this.gt) {
                  this.s(70);
                  return;
               }

               if ((!this.gD || hF == 70) && !this.gC && !gu) {
                  this.s(26);
               }
            } else {
               if (!fV) {
                  this.s(67);
                  hy = this.fZ;
                  this.gI = false;
                  return;
               }

               fV = false;
               this.hL = 0;
               this.gI = false;
               if (this.fW >= this.fU) {
                  if (dQ != this.gi) {
                     eo[this.gi] = 1;
                     this.s(92);
                     this.b(dX[this.gi], this.dY[this.gi] - (this.ej[this.gi] >> 1), 1, this.eq[this.gi]);
                     this.i(11, this.gi);
                     return;
                  }

                  if (this.dR != this.gi) {
                     this.s(109);
                     this.b(dX[this.gi], this.dY[this.gi] - (this.ej[this.gi] >> 1), 2, this.eq[this.gi]);
                     this.b(dX[this.gi], this.dY[this.gi] - (this.ej[this.gi] >> 1), 1, this.eq[this.gi]);
                     eo[this.gi] = er[this.gi];
                     if (this.bG >= 4) {
                        this.i(14, this.gi);
                        return;
                     }

                     this.i(15, this.gi);
                     return;
                  }

                  if (this.dR == this.gi) {
                     this.s(115);
                     eo[this.gi] = er[this.gi];
                     this.en[this.gi] = hy;
                     this.b(dX[this.gi], this.dY[this.gi] - (this.ej[this.gi] >> 1), 2, this.eq[this.gi]);
                     this.b(dX[this.gi], this.dY[this.gi] - (this.ej[this.gi] >> 1), 1, this.eq[this.gi]);
                     this.i(14, this.gi);
                     ++this.dS;
                     if (this.dS >= 3) {
                        this.eC = true;
                     }

                     if (this.dS > 3) {
                        eo[this.gi] = -1;
                        return;
                     }
                  }
               } else {
                  if (dQ != this.gi) {
                     this.s(93);
                     this.i(0, this.gi);
                     return;
                  }

                  if (this.dR != this.gi) {
                     eo[this.gi] = er[this.gi];
                     this.i(0, this.gi);
                     this.s(110);
                     return;
                  }

                  if (this.dR == this.gi) {
                     this.s(116);
                     eo[this.gi] = er[this.gi];
                     this.en[this.gi] = hy;
                     this.i(12, this.gi);
                     return;
                  }
               }
            }

         }
      } else {
         this.s(45);
      }
   }

   private void e(boolean var1) {
      if ((this.hE != 24 && this.hE != 25 && this.hE != 44 && this.hE != 38 || var1) && (this.hE == 24 || this.hE == 25 || this.hE == 44 || this.hE == 40 || this.hE == 38 || this.hE == 108 || this.hE == 114 || this.hE == 116 || this.hE == 115 || hF == 121 || hF == 120 || gv || this.gr || gu)) {
         if ((this.hE == 47 || this.hE == 118 || this.hE == 119) && !var1) {
            this.s(45);
         } else {
            if (this.gC && this.hE != 24 && this.hE != 26) {
               this.s(0);
            }

         }
      } else {
         this.s(26);
      }
   }

   private void z() {
      if (this.t != 6 && this.hA) {
         this.hA = false;
         this.s(0);
      }

      if (this.t != 2 && this.t != 5) {
         if (this.hE == 9) {
            this.s(0);
         } else if (this.hE == 46) {
            this.s(45);
         } else if (this.gs && this.hE != 50) {
            this.s(50);
         }
      }

      if (this.t != 1 && hF == 35 || this.t != 6 && hF == 34) {
         this.s(33);
      }

      if ((this.t != 8 || hp <= 0) && this.hE == 94) {
         this.s(0);
         if (hp <= 0) {
            hp = 0;
         }

         this.W();
      }

      if (this.t == 8 && this.hE == 94) {
         hp -= 12;
      }

   }

   private boolean f(boolean var1) {
      switch (this.s) {
         case 1:
            this.G();
            break;
         case 2:
            this.D();
            break;
         case 3:
            this.bj = false;
            ((a)this).a(133, 1025, 2);
            ((a)this).a(134, 1025, 3);
            this.bo = (256 - (this.hu & 255)) * 208 >> 8;
            J = 106;
            break;
         case 5:
            this.E();
            break;
         case 6:
            this.F();
            break;
         case 8:
            var1 = this.h(var1);
            break;
         case 10:
         case 27:
            if (!this.hA && !this.gz) {
               ++this.fQ;
               if (this.fQ > 3) {
                  this.fQ = 0;
               }

               hf = this.fQ == 1;
               hg = this.fQ == 2;
               this.hh = this.fQ == 3;
               if (this.hE == 0) {
                  this.s(0);
               }
            }
            break;
         case 29:
            if (this.ab == this.U) {
               this.ag = this.ab >> 8;
               this.ah = this.ac >> 8;
               this.K = J;
               this.a(I, I, 35, 35);
               J = 102;
               as = 4;
            }
      }

      return var1;
   }

   private void j(int var1, int var2) {
      if (!fV && (this.h(var1, var2) & 128) > 0) {
         for(int var3 = cz; var3 >= 0; --var3) {
            if (this.cp[var3] == var1 && cq[var3] == var2) {
               this.cw = this.cr[var3];
               this.cx = this.cr[var3];
               String var4 = ((a)this).e(this.cw);
               if (!this.aS && var4.charAt(0) == '@') {
                  this.aS = true;
                  this.aT = a.a((String)var4.substring(2, 3), (int)0) == 1;
                  this.aU = a.a((String)var4.substring(1, 2), (int)0);
                  ((a)this).a(131, 1027, this.aU);
               } else if (this.aS) {
                  int var7 = super.a;
                  int var6 = super.b;
                  this.t(131);
                  super.a = var7;
                  super.b = var6;
               } else {
                  J = 101;
                  this.a(79, 25, 35, 35);
                  if (J != 105) {
                     this.cy = this.cx + this.cs[var3];

                     for(int var5 = cz; var5 >= 0; --var5) {
                        if (this.cr[var5] == this.cw) {
                           this.c(this.cp[var5], cq[var5], 128);
                        }
                     }

                     ib = new String[2];
                     this.n();
                  }
               }
            }
         }

      } else {
         this.aS = false;
      }
   }

   private void A() {
      boolean var1 = false;
      int var2 = super.a;
      int var3 = super.b;
      this.t(131);
      super.a = var2;
      super.b = var3;
      if (this.fW >= 0 && this.fW < this.fU) {
         switch (this.s) {
            case 1:
               if (this.fX == 0) {
                  var1 = true;
               }
               break;
            case 2:
               if (this.fX == 1) {
                  var1 = true;
               }
            case 3:
            case 4:
            case 7:
            default:
               break;
            case 5:
               if (this.fX == 3) {
                  var1 = true;
               }
               break;
            case 6:
               if (this.fX == 4) {
                  var1 = true;
               }
               break;
            case 8:
               if (this.fX == 2) {
                  var1 = true;
               }
         }

         if (var1) {
            int var4 = this.X();
            this.fX = var4;
            ++this.fW;
            ((a)this).a(131, 1027, fY[this.fX]);
            return;
         }

         if (this.s != 0 && this.s != 27 && this.s != 29 && this.s != 4 && this.s != 9) {
            this.fW = -1;
         }
      }

   }

   private void g(boolean var1) {
      if (J == 105) {
         if (!this.gC && !this.gt && this.hE != 24 && !this.gD && !gu && !gk) {
            if (this.hE == 37) {
               this.s(40);
               ++this.gf;
            } else {
               this.s(26);
            }
         }

         if (!var1 && this.hE != 26) {
            if (this.aZ[this.bb + 1] != 0 || this.ba[this.bc + 1] != 0 && this.dP >= 0) {
               if (this.aZ[this.bb + 1] == 0) {
                  this.s(this.aZ[this.bb]);
               } else {
                  ++this.bb;
                  this.s(this.aZ[this.bb]);
               }
            } else {
               this.Y();
            }
         }

         if (this.s == 29 && this.ab == this.U && this.ac == this.V) {
            this.ag = this.ab >> 8;
            this.ah = this.ac >> 8;
            this.K = J;
            J = 102;
            as = 4;
            this.a(I, I, 35, 35);
         }
      }

   }

   private void B() {
      if (this.hn >= this.ho && !this.gm || this.gi >= 0 || gl || this.go || this.gz || hF == 60 || hF == 77 || this.hn <= 0 || !this.gC || this.ge == this.gc && this.gf == this.gd) {
         if (gu && this.hn < this.ho && !gl && !this.gz) {
            if (this.gj == hy) {
               this.s(118);
            } else {
               this.s(119);
            }
         } else if (this.gi >= 0) {
            this.hn = this.ho;
         }
      } else {
         this.gm = false;
         if (!this.hA && hF != 27 && hF != 28 && hF != 29) {
            if (this.ho - this.hn > 50) {
               hy = this.gj;
               this.s(7);
            } else if (hy == this.gj) {
               this.s(5);
            } else {
               this.s(100);
            }
         } else if (this.hA) {
            this.gm = false;
            if (hF != 97) {
               hy = this.gj;
               this.s(97);
            }
         }
      }

      this.ho = this.hn;
   }

   private void C() {
      if (this.gN != -1 && this.dk[this.gN] - dj[this.gN] <= 3 && this.gO > 0) {
         if (this.dk[this.gN] <= 0) {
            this.gO -= M;
            if (this.gO <= 0) {
               this.iF = true;
            }
         }

         this.gP -= M;
         if (this.gP < 0) {
            this.gP = 200 - this.gP;
            gQ = !gQ;
            this.iF = true;
         }
      }

   }

   private boolean h(boolean var1) {
      if (!this.gq && !this.go && !this.gn && this.gC && this.gi < 0 && !this.gD) {
         if (this.hE != 0 && this.hE != 3) {
            if (this.gC && !hg && !this.hh && (!hf || hp >= 0)) {
               short var3 = 150;
               if (hf && this.gK > this.gS || !hf && this.gK > gR) {
                  var3 = 600;
               }

               if (gJ > var3) {
                  this.gI = true;
               }
            }
         } else {
            this.hA = false;
            var1 = true;
            if (hg && hp > gZ + 1 << 2) {
               this.s(94);
            } else if (this.hh && hp > 256 && this.fK < 10) {
               this.s(95);
               this.W();
            } else {
               hg = false;
               this.hh = false;
               if (!hf || hp <= 0) {
                  this.W();
               }

               this.fZ = hy;
               if (!hf || hp > 0) {
                  this.s(54);
               }
            }
         }
      } else if (this.gi >= 0 && eo[this.gi] > 0) {
         if (this.dU[this.gi] == 1) {
            this.s(13);
            this.i(10, this.gi);
            this.b(dX[this.gi], this.dY[this.gi] - (this.ej[this.gi] >> 1), 0, this.eq[this.gi]);
            eo[this.gi] = -1;
         } else if (this.dU[this.gi] != 2) {
         }
      } else if (!this.gC && !this.gD && !this.gr && !gu && hF != 120 && hF != 121 && !this.gz && this.hE != 37) {
         if (hf && hp <= 0 || hg || this.hh) {
            this.W();
         }

         if (!this.gt) {
            this.fZ = hy;
            this.s(67);
         } else {
            short var2 = 150;
            if (hf && this.gK > this.gS || !hf && this.gK > gR) {
               var2 = 600;
            }

            if (gJ > var2) {
               this.gI = true;
            }
         }
      } else if (gu && !this.gz) {
         if (this.hE != 47) {
            this.W();
            this.s(47);
         }
      } else if (this.gr && !this.gz) {
         this.W();
         this.s(36);
      } else if (hF == 21) {
         this.s(0);
      }

      return var1;
   }

   private void D() {
      if (this.go && this.hE == 21) {
         if (hy) {
            this.s(23);
            this.gp = 0;
         } else {
            this.s(22);
            this.gp = 1;
         }

         if (!this.s()) {
            this.s(21);
         } else {
            this.y(3);
         }
      } else if (this.gi >= 0 && eo[this.gi] > 0 && this.dU[this.gi] == 1 && this.hE != 14 && !this.gr) {
         hy = false;
         this.en[this.gi] = false;
         this.u();
      } else {
         if (hy && (this.gz || this.gt) && this.gi < 0 && !this.gr) {
            short var1 = 150;
            if (hf && this.gK > this.gS || !hf && this.gK > gR) {
               var1 = 600;
            }

            if (gJ > var1) {
               this.gI = true;
               this.fZ = false;
               return;
            }
         } else if (this.gr && !this.gz) {
            if (hy) {
               hy = false;
               this.s(44);
               return;
            }
         } else if (gv && !hy) {
            this.s(38);
         }

      }
   }

   private void E() {
      if (this.go && this.hE == 21) {
         if (hy) {
            this.s(22);
            this.gp = 1;
         } else {
            this.s(23);
            this.gp = 0;
         }

         if (!this.s()) {
            this.s(21);
         } else {
            this.y(3);
         }
      } else if (this.gi >= 0 && eo[this.gi] > 0 && this.dU[this.gi] == 1 && this.hE != 14 && !this.gr) {
         hy = true;
         this.en[this.gi] = true;
         this.u();
      } else {
         if (!hy && (this.gz || this.gt) && this.gi < 0 && !this.gr && !gu) {
            short var1 = 150;
            if (hf && this.gK > this.gS || !hf && this.gK > gR) {
               var1 = 600;
            }

            if (gJ > var1) {
               this.gI = true;
               this.fZ = true;
               return;
            }
         } else if (this.gr && !this.gz) {
            if (!hy) {
               hy = true;
               this.s(44);
               return;
            }
         } else if (gv && hy) {
            this.s(38);
         }

      }
   }

   private void F() {
      if (!this.gz && (this.hE == 0 || this.hE == 9) && this.gC) {
         for(int var1 = this.eF; var1 >= 0; --var1) {
            if ((this.gf == this.dY[var1] || this.gf + 1 == this.dY[var1]) && a(dX[var1] - (this.ei[var1] >> 1) - (gG >> 1), this.dY[var1], this.ei[var1] + gG, this.ej[var1], this.ge, this.gf, gG, this.gH)) {
               boolean var2 = true;

               for(int var3 = this.gA; var3 < this.ed[var1]; ++var3) {
                  var2 &= (this.h(var3, gB) & 1) == 0;
               }

               if ((this.eb[var1] <= 0 || this.dU[var1] == 3 && this.dV[var1] == 12) && var2) {
                  this.iF = true;
                  if (dQ != var1 && this.dU[var1] == 1 && eo[var1] > 0 && eo[var1] < er[var1] >> 1) {
                     this.gi = var1;
                     this.ge = dX[var1];
                     this.en[var1] = hy;
                     super.a = 0;
                     this.s(12);
                     this.i(22, var1);
                     break;
                  }

                  if (this.dU[var1] != 2 || (eo[var1] <= 0 || eo[var1] >= er[var1] >> 1) && this.dV[var1] != 12) {
                     if (this.dU[var1] != 3 || (eo[var1] <= 0 || eo[var1] >= er[var1] >> 1) && (this.dV[var1] != 12 || dQ == var1)) {
                        continue;
                     }

                     this.gi = var1;
                     if (this.dV[var1] == 12 && dQ != var1) {
                        this.s(113);
                        this.b(dX[this.gi], this.dY[this.gi] - (this.ej[this.gi] >> 1), 0, this.eq[this.gi] >> 1);
                        this.ge = dX[var1];
                        hy = this.en[var1];
                        this.eb[var1] = 0;
                        eo[var1] = -1;
                        this.i(16, var1);
                        break;
                     }

                     this.ge = dX[var1];
                     this.en[var1] = hy;
                     super.a = 0;
                     this.hL = 1;
                     fV = true;
                     this.fW = 0;
                     this.fU = 3;
                     this.fX = this.X();
                     ((a)this).a(131, 1027, fY[this.fX]);
                     if (dQ != var1 && this.dR != var1) {
                        this.s(91);
                        this.i(14, var1);
                     } else if (this.dR == var1) {
                        this.s(114);
                        this.i(13, var1);
                     } else if (dQ == var1) {
                        this.s(108);
                        this.i(13, var1);
                     }

                     this.en[var1] = !hy;
                     break;
                  }

                  this.gi = var1;
                  if (this.dV[var1] != 12 && this.dV[var1] != 10) {
                     this.ge = dX[var1];
                     this.en[var1] = hy;
                     super.a = 0;
                     this.s(88);
                     this.hL = 1;
                     fV = true;
                     this.fU = 2;
                     this.fW = 0;
                     this.fX = this.X();
                     ((a)this).a(131, 1027, fY[this.fX]);
                     this.i(11, var1);
                     this.en[var1] = !hy;
                     break;
                  }

                  this.s(117);
                  this.ge = dX[var1];
                  eo[var1] = -1;
                  this.b(dX[this.gi], this.dY[this.gi] - (this.ej[this.gi] >> 1), 2, this.eq[this.gi] >> 1);
                  this.i(13, var1);
                  break;
               }
            }
         }
      }

      if (gv) {
         this.s(40);
         ++this.gf;
      } else if (this.hE == 0) {
         if (hy) {
            for(int var4 = this.fq; var4 >= 0; --var4) {
               if (!ft[var4] && a(this.fu[var4], this.fv[var4], this.fr, this.fs, this.ge - this.hB, this.gf, gG, this.gH) && Math.abs(this.gf - this.fv[var4]) < 8) {
                  this.s(31);
                  this.ge = this.fu[var4] + (this.fr >> 1);
               }
            }
         }

         for(int var5 = this.cB; var5 >= 0; --var5) {
            if (hy && this.cE[var5] && a(this.ge - (gG >> 1), this.gf - this.gH, gG, this.gH, this.cC[var5] - 16, cD[var5], 32, 16)) {
               this.hS = var5;
               this.hT = var5;
               if (!this.cH[var5]) {
                  this.s(15);
               } else {
                  this.s(17);
               }

               this.ge = this.cC[var5];
               this.cH[var5] = !this.cH[var5];
            }
         }
      }

      if (this.gC && !this.gs && !this.go && !this.gn && !this.gq && this.gi < 0 && hF != 24 && hF != 48 && hF != 30) {
         this.hA = true;
         this.s(3);
      }

      if (this.gD) {
         this.s(26);
      }

   }

   private void G() {
      if (this.gi < 0) {
         if (this.hE == 21) {
            this.s(24);
            return;
         }

         if (hF == 120) {
            this.s(121);
            return;
         }

         if (!this.gw && hy && this.gE) {
            this.s(30);
            return;
         }

         if (!this.gw && !hy && gF) {
            this.s(30);
            return;
         }

         if (!this.gq && !this.gn && !this.gr && !gu && !gv && (this.gC || this.gD) && !this.go && this.gi < 0 && !this.gz) {
            if (this.hA) {
               this.hA = false;
               this.s(0);
               return;
            }

            if (this.hE != 24 && this.hE != 26 && this.hE != 3 && this.hE != 38 && this.hE != 30) {
               this.s(24);
               return;
            }
         } else {
            if (gv) {
               this.s(38);
               return;
            }

            if (this.hE == 24) {
               this.s(25);
            }
         }
      }

   }

   private void H() {
      if (this.gr && this.hE == 33 && ((this.h(this.gA + (hy ? 1 : -1), gB - this.hk - 1) & 8) != 0 || (this.h(this.gA + (hy ? 1 : -1), gB - this.hk - 1) & 1) == 0)) {
         this.s(35);
      }

   }

   private void I() {
      if (this.gr) {
         if (this.hE == 33) {
            if ((this.h(this.gA + (hy ? 1 : -1), gB + 1) & 8) != 0) {
               this.s(34);
               return;
            }

            this.s(26);
            return;
         }
      } else {
         if (gu) {
            this.s(26);
            return;
         }

         if (hF == 120) {
            this.s(26);
            this.gf += 16;
         }
      }

   }

   private void J() {
      if (!this.gr && !gv && !this.go && this.hE != 44 && this.hE != 22 && this.hE != 38) {
         if (gu && !gl && !this.gz) {
            if (this.hE != 46) {
               this.s(46);
            }

            hy = true;
            return;
         }

         if (this.hE != 0 && this.hE != 3) {
            if (this.hE == 50) {
               hy = true;
               if ((this.h(this.gA + 1, gB) & 1) == 0) {
                  this.s(51);
               }
            }
         } else {
            if (((this.h(this.gA + 1, gB - 1) & 1) != 0 || (this.h(this.gA + 1, gB) & 1) != 0 || (this.h(this.gA + 1, gB - 2) & 1) != 0) && (this.h(this.gA + 1, gB) & 512) == 0) {
               if ((this.h(this.gA + 1, gB) & 1) != 0) {
                  this.ge = (this.gA + 1) * 16 - (gG >> 1);
               }
            } else {
               if ((this.h(this.gA + 1, gB) & 512) != 0) {
                  int var1 = (this.h(this.gA, gB) & 512) != 0 ? -1 : 0;
                  this.ge = (this.gA + 1 + var1) * 16 - (gG >> 1);
               }

               this.s(9);
               this.hA = false;
            }

            hy = true;
         }

         if (this.hE == 9 || this.hE == 51) {
            hy = true;
         }
      }

   }

   private void K() {
      if (!this.gr && !gv && !this.go && this.hE != 44 && this.hE != 22 && this.hE != 38) {
         if (gu && !gl && !this.gz) {
            if (this.hE != 46) {
               this.s(46);
            }

            hy = false;
            return;
         }

         if (this.hE != 0 && this.hE != 3) {
            if (this.hE == 50) {
               hy = false;
               if ((this.h(this.gA - 1, gB) & 1) == 0) {
                  this.s(51);
               }
            }
         } else {
            hy = false;
            if (((this.h(this.gA - 1, gB) & 1) != 0 || (this.h(this.gA, gB) & 1) != 0 || (this.h(this.gA - 1, gB - 1) & 1) != 0 || (this.h(this.gA - 1, gB - 2) & 1) != 0) && (this.h(this.gA - 1, gB) & 512) == 0) {
               if ((this.h(this.gA - 1, gB) & 1) != 0 || (this.h(this.gA, gB) & 1) != 0 || (this.h(this.gA - 1, gB - 1) & 1) != 0 || (this.h(this.gA - 1, gB - 2) & 1) != 0) {
                  this.ge = (this.gA + ((this.h(this.gA, gB) & 1) != 0 ? 1 : 0)) * 16 + (gG >> 1);
               }
            } else {
               if ((this.h(this.gA - 1, gB) & 512) != 0) {
                  int var1 = (this.h(this.gA, gB) & 512) != 0 ? 1 : 0;
                  this.ge = (this.gA + var1) * 16 + (gG >> 1);
               }

               this.s(9);
               this.hA = false;
            }
         }

         if (this.hE == 9 || this.hE == 51) {
            hy = false;
         }
      }

   }

   private void L() {
      for(int var1 = this.fq; var1 >= 0; --var1) {
         if (this.fx[var1] >= 0) {
            int var2 = this.fu[var1] - iV;
            int var3 = this.fv[var1] - iW;
            if (var2 + this.fr >= 0 && var3 + this.fs >= 0 && var2 - this.fr <= 176 && var3 - this.fs <= 208 && !this.t(var1 + 121) && fw[var1] != 0 && this.fx[var1] < 8 && this.fx[var1] >= 4) {
               int[] var10000 = this.fx;
               var10000[var1] += 4;
               ((a)this).a(121 + var1, 5120, this.fx[var1]);
               if (fw[var1] == -1) {
                  hi |= 1 << this.bG;
                  this.fA = 192;
                  ++this.hs;
                  J = 104;
                  this.fz = 10;
                  ((a)this).a(136, 1026, 0);
               } else if (fw[var1] == -2) {
                  hi |= 1 << this.bG << 10;
                  this.fA = 192;
                  ++this.ht;
                  J = 104;
                  this.fz = 9;
                  ((a)this).a(136, 1026, 1);
               } else if (fw[var1] != 0) {
                  this.b(this.fu[var1] + (this.fr >> 1), this.fv[var1] - this.fs - 7, this.fx[var1] - 8, fw[var1]);
               }
            }
         }
      }

   }

   private void M() {
      for(int var1 = this.fK; var1 >= 0; --var1) {
         int var2 = var1 << 2;
         int var3 = 9 * M;
         int var4 = fG[var1] < 0 ? this.ge : dX[fG[var1]];
         int var5 = fG[var1] < 0 ? this.gf : this.dY[fG[var1]];
         if (var4 < fC[var2] && fE[var1] > -3584) {
            int[] var8 = fE;
            var8[var1] -= var3;
         } else if (var4 > fC[var2] && fE[var1] < 3584) {
            int[] var10000 = fE;
            var10000[var1] += var3;
         } else {
            fE[var1] >>= 2;
         }

         if (var5 - (this.gH >> 1) + 8 < fD[var2] && fF[var1] > -3584) {
            int[] var11 = fF;
            var11[var1] -= var3;
         } else if (var5 - (this.gH >> 1) - 8 > fD[var2] && fF[var1] < 3584) {
            int[] var10 = fF;
            var10[var1] += var3;
         } else {
            int[] var9 = fF;
            var9[var1] -= fF[var1] >> 3;
         }

         if (a(fC[var2], fD[var2], var4 + this.hC, var5 - this.gH, gG, this.gH)) {
            if (fH[var1] > 75) {
               if (fI[var1] == 1) {
                  if (this.hn < this.hq && this.hn > 0 && J != 108) {
                     this.hn += fJ[var1];
                     this.ho += fJ[var1];
                     if (this.hn > this.hq) {
                        this.hn = this.hq;
                        this.ho = this.hq;
                     }
                  }
               } else if (fI[var1] == 2) {
                  hp += fJ[var1];
                  if (hp > this.hr) {
                     hp = this.hr;
                  }
               } else if (fI[var1] == 0) {
                  this.hu += fJ[var1];
                  hv += fJ[var1];
                  if (hv > this.hw) {
                     this.hu += this.hw - hv;
                     hv = this.hw;
                  }
               } else if (fI[var1] == 3) {
                  int var6 = fG[var1];
                  byte var7 = this.dU[var6];
                  if (var6 != this.gi && (this.dU[var6] != 1 || this.dV[var6] != 22)) {
                     int[] var12 = eo;
                     int var10001 = fG[var1];
                     var12[var10001] -= fJ[var1];
                  }

                  fG[var1] = -1;
                  if (var6 != this.gi && eo[var6] <= 0 && this.dW[var6] != 1 && (this.dU[var6] != 1 || this.dV[var6] != 22) && this.dW[var6] != 3) {
                     if (dQ == var6) {
                        eo[var6] = (er[var6] >> 1) - 1;
                     } else {
                        eo[var6] = -1;
                        if (!this.em[var6] && !this.el[var6]) {
                           if (var7 == 1) {
                              this.i(8, var6);
                              this.b(dX[var6], this.dY[var6] - (this.ej[var6] >> 1), 0, this.eq[var6]);
                           } else if (var7 == 2) {
                              this.i(7, var6);
                              this.b(dX[var6], this.dY[var6] - (this.ej[var6] >> 1), 2, this.eq[var6]);
                           } else if (var7 == 3) {
                              if (this.dV[var6] != 17) {
                                 this.i(7, var6);
                              }

                              this.b(dX[var6], this.dY[var6] - (this.ej[var6] >> 1), 0, this.eq[var6]);
                           }
                        } else {
                           this.i(11, var6);
                        }
                     }
                  } else if (this.dU[var6] == 2 && this.dV[var6] == 12) {
                     eo[var6] = 1;
                  }
               }

               this.r(var1);
            } else {
               int[] var13 = fH;
               var13[var1] += M;
            }
         }

         int[] var14 = fC;
         var14[var2] += fE[var1] >> 8;
         var14 = fD;
         var14[var2] += fF[var1] >> 8;
         System.arraycopy(fC, var2, fC, var2 + 1, 3);
         System.arraycopy(fD, var2, fD, var2 + 1, 3);
      }

   }

   private void N() {
      for(int var2 = ch + 10; var2 >= 10; --var2) {
         int var1 = var2 - 10;
         if (this.cc[var1] >= 0) {
            int var3 = cd[var1] - iV;
            int var4 = ce[var1] - iW;
            if (var3 + this.cg[var1] >= 0 && var4 + this.cf[var1] >= 0 && var3 - this.cg[var1] <= 176 && var4 - this.cf[var1] <= 208) {
               if (!this.t(var2)) {
                  this.cc[var1] = -1;
               }

               int[] var10000 = cd;
               var10000[var1] += super.a;
               var10000 = ce;
               var10000[var1] += super.b;
            }
         }
      }

   }

   private void O() {
      for(int var2 = co + 40; var2 >= 40; --var2) {
         int var1 = var2 - 40;
         if (this.cj[var1] >= 0) {
            int var3 = this.ck[var1] - iV;
            int var4 = cl[var1] - iW;
            if (var3 + this.cn[var1] >= 0 && var4 + this.cm[var1] >= 0 && var3 - this.cn[var1] <= 176 && var4 - this.cm[var1] <= 208) {
               if (!this.t(var2)) {
                  this.cj[var1] = -1;
               }

               int[] var10000 = this.ck;
               var10000[var1] += super.a;
               var10000 = cl;
               var10000[var1] += super.b;
            }
         }
      }

   }

   private void P() {
      for(int var2 = ca + 30; var2 >= 30; --var2) {
         int var1 = var2 - 30;
         if (this.bS[var1] >= 0) {
            if (!this.t(var2) && this.bZ[var1]) {
               this.bS[var1] = -1;
               this.o(var1);
            } else if ((this.bS[var1] & 1) == 0 && a(bV[var1], this.bW[var1], this.bY[var1], this.bX[var1], this.ge - this.hB, this.gf - this.gH, gG, this.gH)) {
               int var10002 = this.bS[var1]++;
               ((a)this).a(var2, 5122, this.bS[var1]);
            }

            int[] var10000 = bT;
            var10000[var1] += super.a;
            var10000 = this.bU;
            var10000[var1] += super.b;
         }
      }

   }

   private void Q() {
      for(int var1 = this.cB; var1 >= 0; --var1) {
         int var2 = var1 + 106;
         int var3 = this.cE[var1] ? 4 : 0;
         boolean var4 = this.t(var2);
         int var5 = cI[var1];
         if (!this.cE[var1] && (this.gC && a(this.ge - (gG >> 1), this.gf - this.gH, gG, this.gH, this.cC[var1], cD[var1], 16, 16) || (this.h(this.cJ[var1], cK[var1]) & 1) != 0)) {
            if (!this.cH[var1]) {
               this.cH[var1] = true;
               this.x(var1);
               this.hT = var1;
            }
         } else if (!this.cE[var1] && this.cH[var1]) {
            this.cH[var1] = false;
            this.x(var1);
            this.hT = var1;
         }

         if (!var4) {
            boolean var6 = false;
            if (!this.cH[var1]) {
               if (var5 == 1 + var3) {
                  cI[var1] = 2 + var3;
                  ((a)this).a(var2, 2050, cI[var1]);
                  var6 = true;
               }
            } else if (var5 == 3 + var3) {
               cI[var1] = 0 + var3;
               ((a)this).a(var2, 2050, cI[var1]);
               var6 = true;
            }

            if ((var5 == 3 + var3 || var5 == 1 + var3) && !var6 && this.hS >= 0) {
               this.x(this.hS);
               this.hS = -1;
            }
         }

         if (this.hT >= 0 && this.hT == var1) {
            this.hT = -1;
            if (var5 != 0 + var3 && var5 != 3 + var3) {
               if (var5 == 2 + var3 || var5 == 1 + var3) {
                  cI[var1] = 3 + var3;
                  ((a)this).a(var2, 2050, cI[var1]);
               }
            } else {
               cI[var1] = 1 + var3;
               ((a)this).a(var2, 2050, cI[var1]);
            }
         }
      }

   }

   private void R() {
      for(int var1 = cM; var1 >= 0; --var1) {
         int var2 = var1 + 100;
         boolean var3 = this.t(var2);
         int var4 = cX[var1] - (this.cY[var1] << 2);
         if (var3) {
            if (cZ[var1]) {
               if (var4 == 2) {
                  cX[var1] = (this.cY[var1] << 2) + 3;
                  ((a)this).a(var2, 2049, cX[var1]);
               }
            } else if (var4 == 0) {
               cX[var1] = (this.cY[var1] << 2) + 1;
               ((a)this).a(var2, 2049, cX[var1]);
            }
         } else if (!cZ[var1]) {
            if (var4 == 3) {
               cX[var1] = (this.cY[var1] << 2) + 0;
               ((a)this).a(var2, 2049, cX[var1]);
            } else if (var4 == 1) {
               cX[var1] = (this.cY[var1] << 2) + 2;
               ((a)this).a(var2, 2049, cX[var1]);

               for(int var7 = 0; var7 < this.cU[var1]; ++var7) {
                  for(int var8 = 0; var8 < cS[var1]; ++var8) {
                     this.b(cO[var1] + var8, cP[var1] - var7, 1);
                  }
               }
            }
         } else if (var4 == 1) {
            cX[var1] = (this.cY[var1] << 2) + 2;
            ((a)this).a(var2, 2049, cX[var1]);
         } else if (var4 == 3) {
            cX[var1] = (this.cY[var1] << 2) + 0;
            ((a)this).a(var2, 2049, cX[var1]);

            for(int var5 = 0; var5 < this.cU[var1]; ++var5) {
               for(int var6 = 0; var6 < cS[var1]; ++var6) {
                  this.c(cO[var1] + var6, cP[var1] - var5, 1);
               }
            }
         }
      }

   }

   private void S() {
      for(int var2 = this.dt + 60; var2 >= 60; --var2) {
         int var1 = var2 - 60;
         if (this.dm[var1] >= 0) {
            int var3 = dp[var1] - iV;
            int var4 = dq[var1] - iW;
            if (var3 + this.ds[var1] >= 0 && var4 + this.dr[var1] >= 0 && var3 - this.ds[var1] <= 176 && var4 - this.dr[var1] <= 208) {
               boolean var5 = !this.t(var2);
               if (this.do[var1] == 2) {
                  int[] var10000 = dp;
                  var10000[var1] -= super.a;
                  var10000 = dq;
                  var10000[var1] -= super.b;
               } else {
                  int[] var7 = dp;
                  var7[var1] += super.a;
                  var7 = dq;
                  var7[var1] += super.b;
               }

               if (!this.gz && !this.gt && hF != 30 || (this.dm[var1] & 1) != 0 || this.hx[2] == 0 && this.hx[7] == 0 || !a(this.hx[0], this.hx[1], this.hx[2], this.hx[3], dp[var1], dq[var1] - this.dr[var1], this.ds[var1], this.dr[var1]) && !a(this.hx[5], this.hx[6], this.hx[7], this.hx[8], dp[var1], dq[var1] - this.dr[var1], this.ds[var1], this.dr[var1])) {
                  if (var5 && (this.dm[var1] & 1) == 1) {
                     this.l(var1, 1);
                     this.p(var1);
                  }
               } else {
                  int var10002 = this.dm[var1]++;
                  ((a)this).a(var2, 5121, this.dm[var1]);
                  this.b(dp[var1], dq[var1] - (this.dr[var1] >> 1), 1, 25);
               }
            }
         }
      }

   }

   private void T() {
      for(int var1 = fb; var1 >= 0; --var1) {
         if (this.hK) {
            this.hK = false;
         } else if (this.t(var1 + 111)) {
            int[] var10000 = this.eS;
            var10000[var1] += (!hy || this.hE != 22) && (hy || this.hE != 23) ? -super.a : super.a;
            var10000 = this.eT;
            var10000[var1] += super.b;
         } else {
            ((a)this).a(var1 + 111, 6144, this.eR[var1]);
         }
      }

   }

   private void U() {
      for(int var1 = this.fd; var1 >= 0; --var1) {
         int var2 = fh[var1] - iV;
         int var3 = this.fi[var1] - iW;
         if (var2 + fj[var1] >= 0 && var3 + this.fk[var1] >= 0 && var2 - fj[var1] <= 176 && var3 - this.fk[var1] <= 208) {
            for(int var4 = 0; var4 < this.hx.length; var4 += 5) {
               if ((this.fe[var1] & 1) == 0) {
                  if (!hg && this.hx[var4 + 3] != 0 && a(this.hx[var4], this.hx[var4 + 1], this.hx[var4 + 2], this.hx[var4 + 3], fh[var1], this.fi[var1] - this.fk[var1], fj[var1], this.fk[var1])) {
                     if (!((a)this).b(116 + var1, 5)) {
                        this.b(fh[var1], this.fi[var1] - (this.fk[var1] >> 1), 0, 50);
                        this.l(var1, 2);

                        for(int var5 = 1; var5 <= this.fm[var1]; ++var5) {
                           for(int var6 = 0; var6 < this.fl[var1]; ++var6) {
                              this.c(this.ff[var1] + var6, fg[var1] - var5, 1);
                           }
                        }

                        int var10002 = this.fe[var1]++;
                        ((a)this).a(var1 + 116, 2048, this.fe[var1]);
                        this.q(var1);
                     }

                     this.a(fh[var1] + (hy ? 0 : fj[var1]), this.hx[1] + (this.hx[3] >> 1), 28, !hy ? 2 : 0, false);
                  }
               } else {
                  ((a)this).b(116 + var1, M);
               }
            }
         }
      }

   }

   private void k(int var1, int var2) {
      for(int var4 = this.dD + 70; var4 >= 70; --var4) {
         int var3 = var4 - 70;
         if (this.dv[var3] >= 0) {
            boolean var5 = !this.t(var4);
            int[] var10000 = dx;
            var10000[var3] += super.a;
            var10000 = this.dy;
            var10000[var3] += super.b;
            if (var5) {
               dx[var3] = this.dz[var3];
               this.dy[var3] = this.dA[var3];
               ((a)this).a(var4, 6145, this.dv[var3]);
            }

            this.a(dx[var3], this.dy[var3], var4, eA, true);
            if (this.gi < 0 && eA[2] > 0 && a(eA[0], eA[1], eA[2], eA[3], var1, var2 - this.gH, gG, this.gH)) {
               if (!this.eC) {
                  this.hn -= this.dw[var3] * M >> 2;
                  if (this.hn < 0) {
                     this.hn = 0;
                  }
               }

               this.ho = this.hn;
               if (!gl && !this.go && !this.gz && hF != 52 && hF != 53 && hF != 60 && hF != 77 && this.gC) {
                  this.s(5);
               }
            }
         }
      }

   }

   private void V() {
      for(int var2 = this.eF + 85; var2 >= 85; --var2) {
         int var1 = var2 - 85;
         boolean var3 = false;
         if (this.dT[var1] >= 0) {
            int var4 = this.dY[var1];
            int var5 = dX[var1];
            byte var6 = this.dU[var1];
            int var7 = this.dV[var1];
            this.c(this.ed[var1], this.ee[var1] - 1, 2);
            this.et[var1] = var4 - this.ee[var1] * 16 << 8;
            this.ed[var1] = u(var1);
            int var8 = this.ed[var1];
            int var9 = (this.et[var1] >> 8) + 1 >= 16 ? this.ee[var1] + 1 : this.ee[var1];
            int var10 = this.ge - var5;
            int var11 = this.gf - var4;
            int var12 = Math.abs(var10);
            this.dK = var12;
            int var13 = Math.abs(var11);
            eE = (this.h(var8, var9) & 1) != 0;
            if ((this.h(var8, var9) & 64) != 0 && this.dW[var1] != 1 && dX[var1] > 0) {
               eo[var1] = -1;
               if (var6 == 1) {
                  this.i(8, var1);
                  this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 0, this.eq[var1]);
               } else if (var6 == 2) {
                  this.i(7, var1);
                  this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 2, this.eq[var1]);
               }
            }

            if (dX[var1] > -10 && this.dY[var1] > -10 && (a(iV - 64, iW - 64, 304, 336, dX[var1] - this.ei[var1], this.dY[var1] - this.ej[var1], this.ei[var1] << 1, this.ej[var1]) || this.dP == var1 && J == 105 || this.dU[var1] == 1 && (this.dV[var1] == 11 || this.dV[var1] == 27 || this.dV[var1] == 8 || this.dV[var1] == 9 || this.dV[var1] == 10 || this.dV[var1] == 7) || this.dU[var1] == 2 && (this.dV[var1] == 8 || this.dV[var1] == 14 || this.dV[var1] == 7 || this.dV[var1] == 10 || this.dV[var1] == 9) || this.dU[var1] == 3 && (this.dV[var1] == 10 || this.dV[var1] == 18 || this.dV[var1] == 7 || this.dV[var1] == 13 || this.dV[var1] == 17 || this.dV[var1] == 8))) {
               boolean var14 = false;
               if (this.eb[var1] <= 500) {
                  var14 = !((a)this).b(var2, M - (this.eb[var1] >> 5));
               }

               if (this.dV[var1] == 11 && this.dU[var1] == 1 || this.dV[var1] == 8 && this.dU[var1] == 2 || this.dV[var1] == 10 && this.dU[var1] == 3) {
                  super.b += dN[var1];
                  int[] var10000 = this.dO;
                  var10000[var1] += M;
                  if (this.dO[var1] > 200) {
                     var10000 = this.dO;
                     var10000[var1] -= 200;
                     var10000 = dN;
                     var10000[var1] += dN[var1] >= 8 ? 0 : 4;
                  }
               } else {
                  this.dO[var1] = 0;
                  dN[var1] = 0;
               }

               if (J != 105 && this.eb[var1] <= 500) {
                  if (var14) {
                     if (J != 105) {
                        switch (var6) {
                           case 1:
                              if (var7 != 8 && var7 != 9 && var7 != 10) {
                                 if (!this.em[var1] && !this.el[var1]) {
                                    if (var12 < 48 && var13 < 16) {
                                       if (Math.abs(this.O & 3) == 3) {
                                          this.i(6, var1);
                                       } else {
                                          this.i(5, var1);
                                       }

                                       this.en[var1] = var10 >= 0;
                                    } else if (var7 != 0 && var13 < 32 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] : -ef[var1]), this.ee[var1] - 1) & 3) == 0 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] + 1 : -(ef[var1] + 1)), this.ee[var1] - 1) & 3) == 0 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] : -ef[var1]), this.ee[var1]) & 3) != 0 && var12 < 144) {
                                       if (ey[var1] < 0) {
                                          this.i(1, var1);
                                       } else {
                                          this.i(0, var1);
                                          int[] var36 = ey;
                                          var36[var1] -= M;
                                       }
                                    } else if (this.dV[var1] == 25) {
                                       this.i(1, var1);
                                    } else {
                                       if (this.dV[var1] == 27) {
                                          eE = true;
                                       }

                                       this.i(0, var1);
                                    }

                                    if (ez[var1] && this.dV[var1] == 1) {
                                       int var24;
                                       if ((var24 = dX[var1] - dZ[var1]) > 64) {
                                          this.en[var1] = false;
                                       } else if (var24 < 64) {
                                          this.en[var1] = true;
                                       }
                                    }
                                 } else if (this.em[var1]) {
                                    if (var7 != 16 && var7 != 15 && var11 > 0 && var11 < 32 && var12 < 16) {
                                       this.i(16, var1);
                                    } else if (var7 != 15 && var7 != 16 && var11 < 0 && var11 > -64 && var12 < 16) {
                                       this.i(15, var1);
                                    } else if (var11 < 0 && var7 != 12 && var7 != 15 && var7 != 16 && var7 != 21) {
                                       this.i(12, var1);
                                    } else if (var7 != 13 && var7 != 15 && var7 != 16 && var7 != 21) {
                                       this.i(13, var1);
                                    } else if (var7 != 21) {
                                       this.i(23, var1);
                                    }
                                 } else if (this.el[var1] && (var7 == 20 || var7 == 19)) {
                                    this.i(26, var1);
                                 }
                              } else {
                                 if (this.dL < 25) {
                                    this.l(var1, 0);
                                 }

                                 this.w(var1);
                                 this.a(dX[var1], this.dY[var1], 26, 0, false);
                                 dX[var1] = -1000;
                                 this.dY[var1] = -1000;
                                 var5 = -1000;
                                 var4 = -1000;
                                 ++this.dM;
                                 if (iS) {
                                    this.iF = true;
                                 }

                                 this.i(0, var1);
                              }
                              break;
                           case 2:
                              if (var7 != 7 && var7 != 12 && var7 != 13) {
                                 if (var7 != 12) {
                                    byte var22 = 0;
                                    if (this.dF) {
                                       var22 = 1;
                                    }

                                    if (this.dV[var1] == 10) {
                                       this.i(12, var1);
                                    } else if (var12 < 16 * (3 + var22) && var13 < 16) {
                                       if (Math.abs(this.O & 3) == 3) {
                                          this.i(6, var1);
                                       } else {
                                          this.i(5, var1);
                                       }

                                       this.en[var1] = var10 >= 0;
                                    } else {
                                       if (this.dV[var1] == 27) {
                                          eE = true;
                                       }

                                       this.i(0, var1);
                                    }
                                 }
                              } else {
                                 if (this.dL < 25) {
                                    this.l(var1, 0);
                                 }

                                 this.w(var1);
                                 this.a(dX[var1], this.dY[var1], 26, 0, false);
                                 dX[var1] = -1000;
                                 this.dY[var1] = -1000;
                                 var5 = -1000;
                                 var4 = -1000;
                                 this.i(0, var1);
                                 ++this.dM;
                                 if (iS) {
                                    this.iF = true;
                                 }
                              }

                              if (ez[var1] && this.dV[var1] == 1) {
                                 int var23;
                                 if ((var23 = dX[var1] - dZ[var1]) > 64) {
                                    this.en[var1] = false;
                                 } else if (var23 < 64) {
                                    this.en[var1] = true;
                                 }
                              }
                              break;
                           case 3:
                              if ((var7 == 7 || var7 == 13 || var7 == 17) && dQ != var1 && this.dR != var1 || dQ == var1 && (var7 == 7 || var7 == 16) || this.dR == var1 && var7 == 16) {
                                 if (dQ == var1 && var7 == 7 || this.dR == var1) {
                                    if (this.dR != var1) {
                                       this.cw = 266;
                                       this.cx = 266;
                                       this.cy = 274;
                                    } else {
                                       this.cw = 243;
                                       this.cx = 243;
                                       this.cy = 245;
                                    }

                                    this.a(79, 25, 35, 35);
                                    J = 101;
                                    this.n();
                                 }

                                 if (this.dL < 25) {
                                    this.l(var1, 0);
                                 }

                                 this.w(var1);
                                 if (dQ != var1) {
                                    this.a(dX[var1], this.dY[var1], 26, 0, false);
                                 }

                                 dX[var1] = -1000;
                                 this.dY[var1] = -1000;
                                 var5 = -1000;
                                 var4 = -1000;
                                 this.i(0, var1);
                                 ++this.dM;
                                 if (iS) {
                                    this.iF = true;
                                 }
                              } else if (dQ == var1 && this.dR != var1 && this.dV[var1] == 15) {
                                 this.i(16, var1);
                              }

                              if (dQ != var1 || this.dR == var1 || this.dV[var1] != 16) {
                                 if (var7 == 11 && dQ != var1) {
                                    this.i(12, var1);
                                 } else if (var7 == 16 && dQ != var1 && this.dR != var1) {
                                    this.i(17, var1);
                                    eo[var1] = -1;
                                 } else if (var7 == 12 && dQ != var1) {
                                    this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 1, this.eq[var1]);
                                    this.i(13, var1);
                                    eo[var1] = -1;
                                 } else if (var12 < 16 * (3 + (dQ == var1 ? 1 : 0)) && var13 < 16 && this.gi < 0) {
                                    if (Math.abs(this.O & 3) == 3) {
                                       if (dQ == var1 && (this.O & 1) != 0) {
                                          this.i(12, var1);
                                       } else {
                                          this.i(6, var1);
                                       }
                                    } else {
                                       this.i(3, var1);
                                    }

                                    if (!fV) {
                                       this.en[var1] = var10 >= 8;
                                    }
                                 } else if (var7 != 0 && this.gi != var1) {
                                    super.a = 0;
                                    super.b = 0;
                                    this.i(0, var1);
                                 }

                                 if (ez[var1] && this.dV[var1] == 1) {
                                    int var21;
                                    if ((var21 = dX[var1] - dZ[var1]) > 64) {
                                       this.en[var1] = false;
                                    } else if (var21 < 64) {
                                       this.en[var1] = true;
                                    }
                                 }

                                 if (this.dR == var1 && var7 == 14 && this.dS >= 3) {
                                    this.i(16, var1);
                                 } else if (this.dR == var1 && var7 == 14 && this.dS < 3) {
                                    this.i(17, var1);
                                    this.en[var1] = !this.en[var1];
                                 }
                              }
                        }
                     }
                  } else if (J != 105) {
                     switch (var6) {
                        case 1:
                           if (var7 == 1) {
                              if (var10 > 16 && !this.en[var1]) {
                                 this.en[var1] = true;
                              } else if (var10 < -16 && this.en[var1]) {
                                 this.en[var1] = false;
                              }

                              if (var12 < 48 && var13 < 16) {
                                 this.i(6, var1);
                                 this.en[var1] = var10 >= 0;
                              } else if (var12 < 32 && var13 > 16 && var13 < 128 && this.gr && !this.dE || var12 < 64 && var13 > 16 && var13 < 32 && gu && !this.dE || var11 > 0 && var13 < 32 && var12 < 48 && this.hE != 37 && this.hE != 38 && !gu && !gv && this.hE != 38) {
                                 this.i(3, var1);
                              }
                           } else if (this.em[var1]) {
                              if (var7 != 16 && var7 != 21 && var11 > 0 && var11 < 32 && var12 < 16) {
                                 this.i(16, var1);
                              } else if (var7 != 15 && var7 != 21 && var11 < 0 && var11 > -64 && var12 < 16) {
                                 this.i(15, var1);
                              } else if (var11 < 0 && var7 != 12 && var7 != 15 && var7 != 16 && var7 != 21) {
                                 this.i(12, var1);
                              } else if (var7 != 13 && var7 != 15 && var7 != 21 && var7 != 16 && var11 > 0) {
                                 this.i(13, var1);
                              } else if (var7 == 21 && eE) {
                                 this.i(0, var1);
                              }
                           } else if (!this.el[var1]) {
                              if ((this.h(this.ed[var1], this.ee[var1] - 1) & 512) != 0 && this.dV[var1] != 8) {
                                 this.en[var1] = !hy;
                                 eo[var1] = -1;
                                 this.i(8, var1);
                                 this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 0, this.eq[var1]);
                              } else if (var7 == 0 && var13 < 32 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] : -ef[var1]), this.ee[var1] - 1) & 3) == 0 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] + 1 : -(ef[var1] + 1)), this.ee[var1] - 1) & 3) == 0 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] : -ef[var1]), this.ee[var1]) & 3) != 0 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] + 1 : -(ef[var1] + 1)), this.ee[var1]) & 3) != 0 && var12 < 144) {
                                 if (ey[var1] < 0) {
                                    this.i(1, var1);
                                 } else {
                                    int[] var35 = ey;
                                    var35[var1] -= M;
                                 }
                              } else if (var7 == 0 && var11 <= 32 && var11 > 0 && this.gC && var12 < 48) {
                                 this.i(3, var1);
                              } else if (var7 == 21) {
                                 this.i(0, var1);
                              } else if (this.dV[var1] == 0 && var12 < 48 && var13 < 16) {
                                 if (Math.abs(this.O & 3) == 3) {
                                    this.i(6, var1);
                                 } else {
                                    this.i(5, var1);
                                 }

                                 this.en[var1] = var10 >= 0;
                              }
                           } else {
                              if (var10 > 16 && var12 < 72 && this.dV[var1] != 19 && this.dV[var1] != 18 && (this.h(this.ed[var1] + 1, this.ee[var1] - this.ek[var1] - 1) & 16) != 0) {
                                 this.i(18, var1);
                                 this.en[var1] = true;
                              } else if (var10 < -16 && var12 < 72 && this.dV[var1] != 19 && this.dV[var1] != 18 && (this.h(this.ed[var1] - 1, this.ee[var1] - this.ek[var1] - 1) & 16) != 0) {
                                 this.i(18, var1);
                                 this.en[var1] = false;
                              }

                              if (this.dV[var1] != 19 && var12 < 48 && var13 < 16) {
                                 this.i(19, var1);
                                 this.en[var1] = var10 >= 0;
                              }
                           }
                           break;
                        case 2:
                           byte var15 = 0;
                           if (this.dF) {
                              var15 = 1;
                           }

                           if (var7 != 1) {
                              if ((this.h(this.ed[var1], this.ee[var1] - 1) & 512) != 0 && this.dV[var1] != 7) {
                                 this.en[var1] = !hy;
                                 eo[var1] = -1;
                                 this.i(7, var1);
                                 this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 2, this.eq[var1]);
                              } else if (var7 == 0 && var13 < 32 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] : -ef[var1]), this.ee[var1] - 1) & 3) == 0 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] + 1 : -(ef[var1] + 1)), this.ee[var1] - 1) & 3) == 0 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] : -ef[var1]), this.ee[var1]) & 3) != 0 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] + 1 : -(ef[var1] + 1)), this.ee[var1]) & 3) != 0 && var12 < 144) {
                                 if (ey[var1] < 0) {
                                    this.i(1, var1);
                                 } else {
                                    int[] var34 = ey;
                                    var34[var1] -= M;
                                 }
                              } else if (this.dV[var1] == 0) {
                                 byte var16 = 0;
                                 if (this.dF) {
                                    var16 = 1;
                                 }

                                 if (var12 < 16 * (3 + var16) && var13 < 16) {
                                    if (Math.abs(this.O & 3) == 3) {
                                       this.i(6, var1);
                                    } else {
                                       this.i(5, var1);
                                    }

                                    this.en[var1] = var10 >= 0;
                                 }
                              }
                           } else {
                              if (var10 > 16 && !this.en[var1]) {
                                 this.en[var1] = true;
                              } else if (var10 < -16 && this.en[var1]) {
                                 this.en[var1] = false;
                              }

                              if (var12 < 16 * (3 + var15) && var13 < 16) {
                                 this.i(6, var1);
                                 this.en[var1] = var10 >= 0;
                              }
                           }
                           break;
                        case 3:
                           if (var7 != 12) {
                              if (var7 == 1) {
                                 if (var10 > 16 && !this.en[var1]) {
                                    this.en[var1] = true;
                                 } else if (var10 < -16 && this.en[var1]) {
                                    this.en[var1] = false;
                                 }

                                 if (var12 < 16 * (3 + (dQ == var1 ? 1 : 0)) && var13 < 16) {
                                    this.i(6, var1);
                                    this.en[var1] = var10 >= 0;
                                 }
                              } else if ((this.h(this.ed[var1], this.ee[var1] - 1) & 512) != 0 && this.dV[var1] != 7) {
                                 eo[var1] = -1;
                                 this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 0, this.eq[var1]);
                                 this.i(7, var1);
                                 this.en[var1] = !hy;
                              } else if (var7 == 0 && var13 < 32 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] + 1 : -(ef[var1] + 1)), this.ee[var1] - 1) & 3) == 0 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] + 1 : -(ef[var1] + 1)), this.ee[var1] - 1) & 3) == 0 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] + 1 : -(ef[var1] + 1)), this.ee[var1]) & 3) != 0 && (this.h(this.ed[var1] + (var10 > 0 ? ef[var1] + 1 : -(ef[var1] + 1)), this.ee[var1]) & 3) != 0 && var12 < 144) {
                                 if (ey[var1] < 0) {
                                    this.i(1, var1);
                                 } else {
                                    int[] var33 = ey;
                                    var33[var1] -= M;
                                 }
                              }
                           }

                           if (var7 == 0 && var12 < 16 * (3 + (dQ == var1 ? 1 : 0)) && var13 < 16 && this.gi < 0) {
                              if (Math.abs(this.O & 3) == 3) {
                                 if (dQ == var1 && (this.O & 1) != 0) {
                                    this.i(12, var1);
                                 } else {
                                    this.i(6, var1);
                                 }
                              } else {
                                 this.i(3, var1);
                              }

                              if (!fV) {
                                 this.en[var1] = var10 >= 8;
                              }
                           }
                     }
                  }
               } else if ((var1 == this.dP || this.eb[var1] > 0) && this.eb[var1] <= 0 && var14) {
                  if (this.aZ[this.bb + 1] == 0 && this.ba[this.bc] == 0) {
                     this.Y();
                  } else if (this.ba[this.bc + 1] == 0) {
                     this.i(this.ba[this.bc], var1);
                  } else {
                     ++this.bc;
                     this.i(this.ba[this.bc], var1);
                  }
               }

               if (this.eb[var1] <= 500) {
                  this.d(this.ed[var1], this.ee[var1], var1);
               }

               if (!eE && !this.em[var1] && !this.el[var1] && this.dW[var1] != 1 && (this.dU[var1] == 1 && this.dV[var1] != 11 && this.dV[var1] != 27 && this.dV[var1] != 3 && this.dV[var1] != 11 && this.dV[var1] != 24 || this.dU[var1] == 2 && this.dV[var1] != 8 && this.dV[var1] != 14 || this.dU[var1] == 3 && this.dV[var1] != 10 && this.dV[var1] != 18) && this.dR != var1) {
                  switch (this.dU[var1]) {
                     case 1:
                        this.i(11, var1);
                        break;
                     case 2:
                        this.i(8, var1);
                        break;
                     case 3:
                        this.i(10, var1);
                  }
               }

               if (!eB && (this.dV[var1] == 1 || this.dV[var1] == 18 || this.dV[var1] == 1 || this.dV[var1] == 1)) {
                  switch (this.dU[var1]) {
                     case 1:
                        this.i(0, var1);
                        break;
                     case 2:
                        this.i(0, var1);
                        break;
                     case 3:
                        this.i(0, var1);
                  }
               }
            }

            if (J != 105 && dX[var1] < 0 && (eg[var1] > 0 || iS)) {
               int[] var37 = ev;
               var37[var1] -= M;
               if (ev[var1] <= 0 && this.bQ < 9 && (Math.abs(dZ[var1] - this.ge) > 32 || Math.abs(ea[var1] - this.gf) > 32)) {
                  if ((this.h(dZ[var1] / 16, ea[var1] / 16 - 1) & 1) == 0 && (this.h(dZ[var1] / 16, ea[var1] / 16 - 1) & 2) == 0) {
                     --eg[var1];
                     ev[var1] = this.ex[var1];
                     this.el[var1] = false;
                     dX[var1] = dZ[var1];
                     this.dY[var1] = ea[var1];
                     eo[var1] = er[var1];
                     this.ed[var1] = dX[var1] / 16;
                     this.ee[var1] = this.dY[var1] / 16;
                     this.b(this.ed[var1], this.ee[var1] - 1, 2);
                     this.a(dX[var1], this.dY[var1], 25, 0, false);
                     switch (this.dU[var1]) {
                        case 1:
                           if (this.el[var1]) {
                              this.i(26, var1);
                           } else {
                              this.i(27, var1);
                           }
                           break;
                        case 2:
                           this.i(14, var1);
                           break;
                        case 3:
                           this.i(18, var1);
                     }
                  } else {
                     ev[var1] = 1500;
                  }
               }
            }

            this.a(var5, var4, var1 + 85, eA, this.en[var1]);

            for(int var25 = 0; var25 < this.hx.length; var25 += 5) {
               if (this.hx[var25 + 2] != 0) {
                  int var19;
                  if (!hf && !hg) {
                     if (var25 == 0) {
                        var19 = this.fS;
                     } else {
                        var19 = this.fT;
                     }
                  } else {
                     var19 = 0;
                  }

                  if (var19 < 2 && a(this.hx[var25], this.hx[var25 + 1], this.hx[var25 + 2], this.hx[var25 + 3], eA[0], eA[1], eA[2], eA[3])) {
                     int var27 = eA[0] + (this.en[var1] ? eA[7] : 0);
                     int var17 = eA[1] + (eA[3] >> 1);
                     if (eA[1] < eA[3]) {
                        var17 = eA[3];
                     } else if (var17 > this.dY[var1]) {
                        var17 = this.dY[var1];
                     }

                     ++var19;
                     int var18 = hf ? fN[this.gK] << gU : fM[this.gK] << gT;
                     if (this.E) {
                        var18 = 10000;
                     }

                     if (hf) {
                        hp -= (fN[this.gK] << 1) + fN[this.gK];
                     }

                     if (hf && !this.gt && hj < 3 || !hf && !hg) {
                        int[] var40 = eo;
                        var40[var1] -= var18;
                        if (!hf) {
                           if (!var3) {
                              this.y(3 + (this.O & 1));
                           }

                           if (var19 == 1) {
                              ++gL;
                           }
                        } else if (!var3) {
                           this.y(3 + (this.O & 1));
                        }

                        var3 = true;
                        this.iF = true;
                        this.gM = 2000;
                     } else if (hg) {
                        int[] var39 = this.eb;
                        var39[var1] += (fO[gZ] << 5) + M;
                     }

                     if (eo[var1] <= 0 && this.dW[var1] != 1) {
                        eo[var1] = -1;
                        if (!this.em[var1] && !this.el[var1]) {
                           if (eE) {
                              if (var6 == 1) {
                                 this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 0, this.eq[var1]);
                                 this.i(9, var1);
                              } else if (var6 == 2) {
                                 this.i(7, var1);
                                 this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 2, this.eq[var1]);
                              } else if (var6 == 3) {
                                 if (var7 == 12 && dQ != var1) {
                                    this.i(13, var1);
                                    this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 1, this.eq[var1]);
                                 } else if (dQ != var1 && this.dR != var1) {
                                    this.i(7, var1);
                                    this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 0, this.eq[var1]);
                                 } else {
                                    eo[var1] = (er[var1] >> 1) - 1;
                                 }
                              }
                           }
                        } else {
                           this.i(11, var1);
                        }

                        this.a(var27, var17, 0, !hy ? 2 : 0, true);
                     } else if (this.dW[var1] != 1 && this.dW[var1] != 3) {
                        if (this.eb[var1] <= 0) {
                           if (this.em[var1]) {
                              this.i(17, var1);
                           } else if (this.el[var1]) {
                              this.i(20, var1);
                           } else {
                              this.en[var1] = var10 > 0;
                              if (var6 == 1) {
                                 if (this.gi == -1 && hF != 60 && hF != 77) {
                                    this.i(2, var1);
                                 } else {
                                    this.i(7, var1);
                                 }
                              } else if (var6 == 2) {
                                 if (this.gi == -1 && hF != 60 && hF != 77) {
                                    this.i(2, var1);
                                 } else {
                                    this.i(9, var1);
                                 }
                              } else if (var6 == 3) {
                                 if (var7 == 12 && dQ != var1) {
                                    this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 1, this.eq[var1]);
                                    this.i(13, var1);
                                 } else if ((dQ == var1 || this.gi == -1) && hF != 60 && hF != 77) {
                                    this.i(4, var1);
                                 } else {
                                    this.i(8, var1);
                                 }
                              }

                              if (!fV) {
                                 this.en[var1] = dX[var1] - this.ge <= 0;
                              }
                           }
                        }

                        if ((!hf || hf && hj < 3) && !hg) {
                           ++hj;
                           this.a(var27, var17, 0, !hy ? 2 : 0, true);
                        }
                     }

                     if (!hf && !hg && this.gi != -1) {
                        this.hx[var25] = 0;
                        this.hx[var25 + 1] = 0;
                        this.hx[var25 + 2] = 0;
                        this.hx[var25 + 3] = 0;
                     }

                     if (var25 == 0) {
                        this.fS = var19;
                     } else {
                        this.fT = var19;
                     }
                  }

                  if (var19 < 2 && a(this.hx[var25], this.hx[var25 + 1], this.hx[var25 + 2], this.hx[var25 + 3], eA[5], eA[6], eA[7], eA[8])) {
                     int var28 = eA[5] + (this.en[var1] ? eA[7] : 0);
                     int var29 = this.hx[var25 + 1] + (this.hx[var25 + 3] >> 1);
                     ++var19;
                     int var30 = hf ? fN[this.gK] << gU : fM[this.gK] << gT;
                     if (this.E) {
                        var30 = 10000;
                     }

                     if (hf && hj < 3 || !hf && !hg) {
                        label1090: {
                           int[] var42 = eo;
                           var42[var1] -= var30;
                           if (!hf) {
                              if (!var3) {
                                 this.y(3 + (this.O & 1));
                              }

                              if (var19 != 1) {
                                 break label1090;
                              }
                           } else if (!var3) {
                              this.y(3 + (this.O & 1));
                           }

                           ++gL;
                        }

                        this.iF = true;
                        var3 = true;
                        this.gM = 2000;
                     } else if (hg) {
                        int[] var41 = this.eb;
                        var41[var1] += (fO[gZ] << 5) + M;
                     }

                     if (eo[var1] <= 0 && this.dW[var1] != 1) {
                        eo[var1] = -1;
                        if (!this.em[var1] && !this.el[var1]) {
                           if (eE) {
                              if (var6 == 1) {
                                 this.i(8, var1);
                                 this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 0, this.eq[var1]);
                              } else if (var6 == 2) {
                                 this.i(7, var1);
                                 this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 2, this.eq[var1]);
                              } else if (var6 == 3) {
                                 if (var7 == 12 && dQ != var1 && this.dR != var1) {
                                    this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 1, this.eq[var1]);
                                    this.i(13, var1);
                                 } else if (dQ != var1 && this.dR != var1) {
                                    this.b(dX[var1], this.dY[var1] - (this.ej[var1] >> 1), 0, this.eq[var1]);
                                    this.i(7, var1);
                                 } else {
                                    eo[var1] = (er[var1] >> 1) - 1;
                                 }
                              }
                           }
                        } else {
                           this.i(11, var1);
                        }

                        this.a(var28, var29, 0, !hy ? 2 : 0, true);
                     } else if (this.dW[var1] != 1 && this.dW[var1] != 3) {
                        if (this.eb[var1] <= 0) {
                           if (this.em[var1]) {
                              this.i(17, var1);
                           } else if (this.el[var1]) {
                              this.i(20, var1);
                           } else {
                              this.en[var1] = var10 > 0;
                              if (var6 == 1) {
                                 if (this.gi == -1 && hF != 60 && hF != 77) {
                                    this.i(2, var1);
                                 } else {
                                    this.i(7, var1);
                                 }
                              } else if (var6 == 2) {
                                 if (this.gi == -1 && hF != 60 && hF != 77) {
                                    this.i(2, var1);
                                 } else {
                                    this.i(9, var1);
                                 }
                              } else if (var6 == 3) {
                                 if (this.gi == -1 && hF != 60 && hF != 77) {
                                    this.i(4, var1);
                                 } else {
                                    this.i(8, var1);
                                 }
                              }
                           }
                        }

                        if ((!hf || hf && hj < 3) && !hg) {
                           ++hj;
                           this.a(var28, var29, 0, !hy ? 2 : 0, true);
                        }
                     }

                     if (!hf && this.gi != -1) {
                        this.hx[var25] = 0;
                        this.hx[var25 + 1] = 0;
                        this.hx[var25 + 2] = 0;
                        this.hx[var25 + 3] = 0;
                     }

                     if (var25 == 0) {
                        this.fS = var19;
                     } else {
                        this.fT = var19;
                     }
                  }
               }
            }

            if (eA[12] > 0 && a(this.ge - this.hB, this.gf - this.gH, gG, this.gH, eA[10], eA[11], eA[12], eA[13])) {
               int var26 = this.ep[var1];
               if (this.dV[var1] == 6) {
                  var26 <<= 1;
               }

               if (dQ != var1) {
                  var26 <<= 1;
               }

               if (dQ != var1 && this.dV[var1] == 12) {
                  var26 <<= 2;
               }

               if (!this.hA) {
                  if (!this.eC) {
                     this.hn -= var26;
                     if (this.hn < 0) {
                        this.hn = 0;
                     }
                  }
               } else {
                  this.gm = true;
               }

               this.gj = !this.en[var1];
            }
         }

         if (this.eb[var1] > 0) {
            int[] var43 = this.eb;
            var43[var1] -= M + (er[var1] >> 6);
         }
      }

   }

   private void W() {
      hg = false;
      this.hh = false;
      hf = false;
      this.fQ = 0;
   }

   private int X() {
      int var1;
      do {
         var1 = Math.abs(this.O % 5);
         this.O = P.nextInt();
      } while(this.fX == var1);

      return var1;
   }

   private void Y() {
      this.bb += 2;
      this.bc += 2;
      ++this.cw;
      if (this.cw >= this.cy) {
         this.a(0, 0, 35, 35);
         J = 100;
         this.iF = true;
         this.aX = false;
         this.cy = -1;
         this.cw = -1;
         this.cx = -1;
      } else {
         this.cx = this.cw;
         this.n();
         if (!this.bf) {
            J = 101;
         } else {
            this.iN = false;
            ++this.bG;
            if (this.bG > this.bH && (this.bI <= 0 || this.bG < this.bI)) {
               this.bH = this.bG;
            }

            ++this.bE;
            this.bE = bF[this.bG];
            this.bg = true;
            this.w = false;
            this.af = true;
            this.a(I, I, 35, 35);
            J = 109;
            this.af = true;
         }
      }
   }

   private void l(int var1, int var2) {
      c[] var3 = (c[])null;
      int var4 = 85 + var1;
      boolean var5;
      int var6;
      int var7;
      if (var2 == 0) {
         if (this.dU[var1] == 1) {
            var3 = this.dG;
         } else if (this.dU[var1] == 2) {
            var3 = dH;
         } else if (this.dU[var1] == 3) {
            if (dQ != var1) {
               var3 = this.dI;
            } else {
               var3 = this.dJ;
            }
         }

         var5 = this.en[var1];
         var6 = dX[var1];
         var7 = this.dY[var1];
      } else if (var2 == 1) {
         var3 = this.dl;
         var4 = 60 + var1;
         var5 = this.do[var1] != 2;
         var6 = dp[var1];
         var7 = dq[var1];
      } else {
         var3 = this.fc;
         var4 = 116 + var1;
         var5 = true;
         var6 = fh[var1];
         var7 = this.fi[var1];
      }

      int var8 = ((a)this).a(this.hQ, var4, var5 ? 0 : 2);
      if (this.hR + var8 < 1000) {
         this.hR += var8;

         for(int var9 = 0; var9 < var8; ++var9) {
            int var10 = var9 << 2;
            this.iT.b(var3[this.hQ[var10]], var6 + this.hQ[var10 + 1], var7 + this.hQ[var10 + 2], this.hQ[var10 + 3]);
         }
      }

   }

   private static int u(int var0) {
      return dX[var0] / 16;
   }

   private int v(int var1) {
      int var2;
      return (var2 = this.dY[var1] / 16) > jd ? jd : var2;
   }

   private void d(int var1, int var2, int var3) {
      int var6 = dX[var3];
      int var7 = this.dY[var3];
      boolean var8 = this.en[var3];
      short var9 = this.ej[var3];
      int var10 = this.ei[var3] >> 1;
      int var11 = (super.a > 0 ? super.a : -super.a) << 8;
      int var12 = (super.b > 0 ? super.b : -super.b) << 8;
      int var13 = var11;
      int var14 = var12;
      if (super.a != 0) {
         var6 += (!this.en[var3] || super.a <= 0) && (this.en[var3] || super.a >= 0) ? -var10 : var10 - 1;
      }

      if (super.b < 0) {
         var7 -= var9;
         int[] var10000 = this.dY;
         var10000[var3] -= var9;
      } else {
         --var7;
      }

      int var15 = var1;
      int var16 = this.v(var3);
      this.es[var3] = var6 - var1 * 16 << 8;
      if (this.es[var3] < 0) {
         var15 = var1 - 1;
         int[] var25 = this.es;
         var25[var3] += 4096;
      } else if (this.es[var3] > 4096) {
         var15 = var1 + 1;
         int[] var26 = this.es;
         var26[var3] -= 4096;
      }

      this.et[var3] = var7 - var16 * 16 << 8;
      if (super.a != 0) {
         var6 -= (!var8 || super.a <= 0) && (var8 || super.a >= 0) ? -var10 : var10 - 1;
      }

      if (super.b < 0) {
         var7 += var9;
         int[] var27 = this.dY;
         var27[var3] += var9;
      } else {
         ++var7;
      }

      eB = true;
      this.eD = true;
      if (var11 > var12) {
         if (var11 > 4096) {
            var12 = (var12 * 16 << 8) / var11;
            var11 = 4096;
         }
      } else if (var12 > 4096) {
         var11 = (var11 * 16 << 8) / var12;
         var12 = 4096;
      }

      int var4;
      if ((!var8 || super.a <= 0) && (var8 || super.a >= 0)) {
         var4 = -var11;
      } else {
         var4 = var11;
      }

      int var5 = super.b > 0 ? var12 : -var12;
      int var17 = 0;
      int var18 = 0;
      this.hM = 0;
      this.hN = 0;
      if (super.a != 0 && !this.el[var3] && this.dW[var3] != 1) {
         this.e(this.ed[var3], this.ee[var3] - 1 - (super.b < 0 ? this.ek[var3] : 0), var3);
      }

      while(var17 < var13 || var18 < var14) {
         int[] var28 = this.es;
         var28[var3] += var4;
         var28 = this.et;
         var28[var3] += var5;
         if (this.es[var3] >= 4096) {
            var28 = this.es;
            var28[var3] -= 4096;
            ++var15;
         } else if (this.es[var3] < 0) {
            var28 = this.es;
            var28[var3] += 4096;
            --var15;
         }

         if (this.et[var3] >= 4096) {
            var28 = this.et;
            var28[var3] -= 4096;
            ++var16;
         } else if (this.et[var3] < 0) {
            var28 = this.et;
            var28[var3] += 4096;
            --var16;
         }

         this.hJ = false;
         this.e(var15, var16, var3);
         if (eB) {
            this.hM += var4;
            var17 += var11;
         } else {
            var28 = this.es;
            var28[var3] -= var4;
            var13 = var17;
            if ((this.h(var1, var2) & 1) != 0 && !this.hJ) {
               this.hM = 0;
               this.hJ = true;
               if ((!this.en[var3] || super.a <= 0) && (this.en[var3] || super.a >= 0)) {
                  dX[var3] = var1 * 16 + (this.ei[var3] >> 1);
               } else {
                  dX[var3] = (var1 + 1) * 16 - (this.ei[var3] >> 1);
               }
            }
         }

         if (this.eD) {
            this.hN += var5;
            var18 += var12;
         } else {
            var28 = this.et;
            var28[var3] -= var5;
            var14 = var18;
         }
      }

      var6 += this.hM >> 8;
      var7 += this.hN >> 8;
      if (var17 > var13) {
         int var19 = var17 - var13 >> 8;
         var6 -= var4 > 0 ? var19 : -var19;
      }

      if (var18 > var14) {
         int var24 = var18 - var14 >> 8;
         var7 -= var5 > 0 ? var24 : -var24;
      }

      if (eB && !this.hJ) {
         dX[var3] = var6;
      }

      if (this.eD) {
         this.ee[var3] = var7 / 16;
         this.dY[var3] = var7;
      }

      this.b(this.ed[var3], this.ee[var3] - 1, 2);
   }

   private void Z() {
      this.eC = false;
      this.iq = 0;
      this.ip = 0;
      this.bf = false;
      this.bb = 0;
      this.dS = 0;
      this.bc = 0;
      this.aZ = new int[40];
      this.ba = new int[40];
      this.at = false;
      this.hS = -1;
      this.az = -1;
      this.aB = 0;
      this.aA = -1;
      this.eF = 0;
      fb = -1;
      this.dD = -1;
      dQ = -1;
      this.dR = -1;
      fb = -1;
      this.dt = -1;
      this.bQ = -1;
      ca = -1;
      co = -1;
      this.cB = -1;
      cM = -1;
      this.fd = -1;
      this.fq = -1;
      this.fK = -1;
      ch = -1;
      this.fc = null;
      cA = null;
      this.cN = null;
      this.eL = null;
      this.cb = null;
      this.ci = null;
      this.dG = null;
      dH = null;
      this.dI = null;
      this.dJ = null;
      this.bR = null;
      this.dl = null;
      this.fp = null;
      this.du = null;
      this.eQ = null;
      this.fL = null;
      this.aX = false;
      this.aE = -1;
      ((a)this).d();
      this.iT = null;
      this.iU = null;
   }

   private void e(int var1, int var2, int var3) {
      short var4 = this.ej[var3];
      boolean var5;
      boolean var6 = (var5 = this.en[var3]) && super.a > 0 || !var5 && super.a < 0;
      int var7 = 0;
      boolean var8 = false;
      this.hI = false;
      this.hJ = false;

      for(int var9 = var4; var9 > 0 && eB; var9 -= 16) {
         if (var2 < jd) {
            if (var2 - var7 < 0 || var2 - var7 >= jd) {
               break;
            }

            if (var9 > 0) {
               if (eB && var1 >= 0) {
                  eB = (this.h(var1, var2 - var7) & 1) == 0;
                  if (var7 == 0) {
                     var8 = !eB && (this.h(var1, var2 - var7 - 1) & 1) == 0;
                  }

                  boolean var10 = eB;
                  if (this.dU[var3] == 1 && this.dV[var3] == 1 || this.dU[var3] == 2 && this.dV[var3] == 1 || this.dU[var3] == 3 && this.dV[var3] == 1) {
                     eB &= (this.h(var1, var2 - var7) & 2) == 0 || !eE || this.el[var3] || var1 == this.ed[var3];
                  }

                  if (var10 != eB) {
                     ey[var3] = 1500;
                  }
               }

               if (!eB) {
                  this.hJ = true;
                  if (var8 && super.a > 0 && (this.en[var3] || super.a < 0 && !this.en[var3]) && super.b == 0 && (this.h(var1, var2 - var7 - 1) & 1) == 0) {
                     switch (this.dU[var3]) {
                        case 1:
                           this.i(3, var3);
                        case 2:
                        case 3:
                     }
                  }
                  break;
               }
            }

            if (var1 < 0 || var1 >= jc) {
               eB = false;
               var5 = !var5;
            }

            if (super.b >= 0) {
               ++var7;
            } else {
               --var7;
            }

            if (this.dU[var3] == 1 && this.el[var3] && this.dV[var3] != 26 && var9 - 16 < 0 && (this.h(var1, var2 - var7) & 16) == 0) {
               eB = false;
               this.i(26, var3);
               int var10002 = this.dY[var3]++;
               break;
            }
         }
      }

      if (eB && this.dV[var3] == 1 || this.dV[var3] == 0) {
         eB = (this.h(var1, var2 + 1) & 1) == 1;
      }

      int var27 = var1;
      if (super.a == 0 && super.b != 0) {
         var27 = var1 + (var5 ? 1 : -1);
      }

      if (super.b >= 0) {
         var7 = this.ek[var3];
         if (this.et[var3] < 2048) {
            ++var7;
         }
      } else {
         var7 = 0;
      }

      int var28 = var2 - var7;
      int var11 = var5 ? -1 : 1;
      if (this.dU[var3] == 1 && (this.h(var27, var28 - 1) & 1) == 0 && ((this.h(var27, var28) & 1) != 0 || this.eu[var3]) && (this.h(var27 + var11, var28) & 1) == 0 && (this.h(var27 + var11, var28 - 1) & 1) == 0 && (this.h(var27 + var11, var28 + this.ek[var3]) & 1) == 0 && super.b != 0 && (!this.em[var3] || this.eu[var3]) && !eE && this.dV[var3] != 21 && this.dV[var3] != 11) {
         if (this.eu[var3] && super.b < 0) {
            this.i(21, var3);
            int var29 = 0;
            if ((this.h(var27, var2) & 1) == 0) {
               ++var29;
            }

            this.dY[var3] = (var2 + var29) * 16 + var4;
         }

         this.hN = 0;
         if (!var5) {
            ++var27;
         }

         this.hJ = true;
         dX[var3] = var27 * 16 + (var5 ? -(this.ei[var3] >> 1) : this.ei[var3] >> 1);
         this.hM = 0;
         this.eD = false;
         eB = false;
      } else {
         if (!eB && super.a != 0) {
            var7 = var6 ? -1 : 1;
         } else {
            var7 = 0;
         }

         int var12 = var2;
         int var13;
         if ((var13 = super.b > 0 ? var2 - this.ek[var3] : var2 + this.ek[var3]) < 0) {
            var13 = 0;
         }

         if (super.b != 0) {
            for(int var14 = this.ei[var3]; var14 > 0; var14 -= 16) {
               if (var1 + var7 >= 0 && var1 + var7 < jc) {
                  if (this.eD) {
                     this.eD = (this.h(var1 + var7, var12) & 1) == 0;
                     if (this.dU[var3] == 1 && !this.dE && eo[var3] > 0 && (this.h(var1 + var7, var13) & 16) != 0) {
                        this.dY[var3] = (var13 + 1) * 16 + var4;
                        this.eD = false;
                        this.hN = 0;
                        this.i(26, var3);
                     }
                  }

                  if (!this.eD && super.b > 0 && !this.el[var3] && this.dV[var3] != 21) {
                     this.dY[var3] = var2 * 16;
                     this.hN = 0;
                     this.hI = true;
                     eE = true;
                     switch (this.dU[var3]) {
                        case 1:
                           if (eo[var3] < 0) {
                              this.b(dX[var3], this.dY[var3] - (this.ej[var3] >> 1), 0, this.eq[var3]);
                              this.i(8, var3);
                           } else {
                              if (this.dV[var3] == 25) {
                                 break;
                              }

                              if (this.dV[var3] != 3 && this.dV[var3] != 27) {
                                 this.i(7, var3);
                                 break;
                              }

                              this.i(25, var3);
                           }
                           break;
                        case 2:
                           if (eo[var3] < 0) {
                              this.b(dX[var3], this.dY[var3] - (this.ej[var3] >> 1), 2, this.eq[var3]);
                              this.i(7, var3);
                           } else if (this.dV[var3] != 14) {
                              this.i(9, var3);
                           }
                           break;
                        case 3:
                           this.aw += 6;
                           if (eE) {
                              eB = true;
                           }

                           if (this.dV[var3] != 6 && dQ != var3) {
                              if (eo[var3] < 0) {
                                 this.b(dX[var3], this.dY[var3] - (this.ej[var3] >> 1), 0, this.eq[var3]);
                                 this.i(7, var3);
                              } else if (this.dV[var3] != 18) {
                                 this.i(8, var3);
                              }
                           } else if (dQ != var3) {
                              if (this.gC && this.gi < 0 && this.dK < 64 && hF != 52 && hF != 53 && hF != 48) {
                                 this.s(27);
                              }
                           } else if (eo[var3] < 0) {
                              this.i(7, var3);
                              this.b(dX[var3], this.dY[var3] - (this.ej[var3] >> 1), 0, this.eq[var3]);
                           } else {
                              int var40 = dX[var3]++;
                           }
                     }
                  }

                  if (!this.eD) {
                     break;
                  }

                  if (var12 < 0 || var12 >= jd) {
                     this.eD = false;
                  }

                  if (super.a == 0) {
                     break;
                  }
               }

               int var10000;
               if (var6) {
                  --var7;
                  var10000 = var7;
               } else {
                  ++var7;
                  var10000 = var7;
               }

               var7 = var10000;
            }
         }

         if (this.em[var3] && this.dV[var3] != 21) {
            var1 += var5 ? 1 : -1;
            if ((this.h(var1, var12) & 8) == 0) {
               this.eD = false;
               this.dY[var3] = (var12 + (super.b < 0 ? 1 : -this.ek[var3] - 1)) * 16 + var4;
               this.eu[var3] = true;
            }

            var1 = var1;
         } else {
            this.eu[var3] = false;
         }

         if (!eB && super.a != 0 && !this.hI && !this.em[var3]) {
            if ((!var5 || super.a <= 0) && (var5 || super.a >= 0)) {
               this.hJ = true;
               int var32;
               int var33 = (var32 = (var1 + 1) * 16 + (this.ei[var3] >> 1) + 1) + (this.ei[var3] >> 1);
               int var34 = var32 / 16;
               int var35 = var33 / 16;
               int var36 = var34 < var35 ? 1 : -1;
               boolean var37 = true;
               int var38 = Math.abs(var35 - var34);

               for(int var39 = 0; var39 <= var38; ++var39) {
                  var37 &= (this.h(var34 + var39 * var36, var2) & 1) == 0;
               }

               if (var37) {
                  dX[var3] = var32;
               }

               if (this.dU[var3] == 1 && eo[var3] > 0 && !this.dE && (this.h(var1, var12 + (super.b > 0 ? -1 : 1)) & 8) != 0 && (this.h(var1, var12 + (super.b > 0 ? -this.ek[var3] : this.ek[var3])) & 8) != 0 && super.b != 0) {
                  this.i(23, var3);
               }
            } else {
               this.hJ = true;
               int var31;
               int var15 = (var31 = var1 * 16 - (this.ei[var3] >> 1) - 1) - (this.ei[var3] >> 1);
               int var16 = var31 / 16;
               int var17 = var15 / 16;
               int var18 = var16 < var17 ? 1 : -1;
               boolean var19 = true;
               int var20 = Math.abs(var17 - var16);

               for(int var21 = 0; var21 <= var20; ++var21) {
                  var19 &= (this.h(var16 + var21 * var18, var2) & 1) == 0;
               }

               if (var19) {
                  dX[var3] = var31;
               }

               if (this.dU[var3] == 1 && eo[var3] > 0 && !this.dE && (this.h(var1, var12 + (super.b > 0 ? -1 : 1)) & 8) != 0 && (this.h(var1, var12 + (super.b > 0 ? -this.ek[var3] : this.ek[var3])) & 8) != 0 && super.b != 0) {
                  this.i(23, var3);
               }
            }

            this.hM = 0;
         }

      }
   }

   private void w(int var1) {
      int var2 = dZ[var1] / 16;
      int var3 = ea[var1] / 16;
      if ((this.h(var2, var3) & 32) > 0) {
         int var4 = -1;

         for(int var5 = 0; var5 < this.de.length; ++var5) {
            if (a(var2, var3, this.de[var5], this.df[var5], this.dg[var5], dh[var5])) {
               var4 = var5;
            }
         }

         if (var4 != -1) {
            int var10002 = dj[var4]++;
            if (dj[var4] >= this.dk[var4]) {
               this.dk[var4] = -1;
               dj[var4] = -1000000;

               for(int var6 = cM; var6 >= 0; --var6) {
                  if (this.cW[var6] == this.di[var4]) {
                     cZ[var6] = !cZ[var6];
                     if (this.da[var6]) {
                        this.iF = true;
                        this.az = cQ[var6];
                        this.aA = this.cR[var6];
                     }
                  }
               }
            }
         }
      }

   }

   private void x(int var1) {
      for(int var2 = var1 << 2; var2 < (var1 << 2) + 4; ++var2) {
         if (this.cL[var2] != 0) {
            for(int var3 = cM; var3 >= 0; --var3) {
               if (this.cW[var3] == this.cL[var2]) {
                  cZ[var3] = !cZ[var3];
                  if (this.da[var3]) {
                     this.az = cQ[var3];
                     this.aA = this.cR[var3];
                     this.iF = true;
                  }
               }
            }
         }
      }

   }

   private void a(int var1, int var2, int var3, int var4, boolean var5) {
      int var6 = var3;
      if (var5) {
         int var7 = this.gK - 1;
         if (!hf && hF == 60) {
            var7 = 6;
         }

         if (this.gt) {
            if (hf) {
               var6 = 17;
            } else {
               var6 = 8;
            }
         } else if (hf) {
            var6 = 11;
         } else {
            var6 = 1;
         }

         var6 += var7;
      }

      if (this.bQ < 9 && (var3 != 0 || this.bQ < 9)) {
         ++this.bQ;
         if (bM[this.bQ] < 0) {
            this.bP[this.bQ] = var4;
            this.bN[this.bQ] = var1;
            this.bO[this.bQ] = var2;
            bM[this.bQ] = this.bQ;
            ((a)this).a(this.bQ, 7168, var6);
         }
      }

   }

   private void a(int[] var1, byte[] var2) {
      this.hU = 220;
      ((a)this).a(this.q);
      this.a(var1, var2, 12, 30, 147, 158, this.hU);
   }

   private void aa() {
      N += M;
      if (N >= 50) {
         N = 0;
         if (this.t == 1) {
            this.i(true);
            ((a)this).h();
         }

         if (this.t == 6) {
            this.i(false);
            ((a)this).h();
         }
      }

   }

   private static boolean a(int var0, int var1, int var2, int var3, int var4, int var5) {
      return var0 > var2 && var0 < var2 + var4 && var1 > var3 && var1 < var3 + var5;
   }

   private static boolean a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var2 != 0 && var6 != 0) {
         return var0 + var2 > var4 && var4 + var6 > var0 && var1 + var3 > var5 && var5 + var7 > var1;
      } else {
         return false;
      }
   }

   private void y(int var1) {
      if (var1 != 3 && var1 != 3 && var1 != 3 && var1 != 3) {
         if (var1 == 0 || var1 == 2 || var1 == 1) {
            if (!this.hV) {
               this.iR.a(var1);
               if (var1 == 0 && iQ) {
                  this.hV = true;
               }
            }

         }
      }
   }

   private void ab() {
      this.hV = false;
      this.iR.c();
   }

   private void u(Graphics var1) {
      this.v(var1);
      if (!iY) {
         this.iX[0].a(var1, 88, I, 0);
         this.iX[1].a(var1, 88, I, 0);
      } else {
         ((a)this).a(var1, this.iX, 0, 88, I, 0);
      }
   }

   private void v(Graphics var1) {
      ((a)this).b(var1);
      this.iF = true;
      var1.setColor(0);
      this.iG = false;
      var1.fillRect(0, 0, 176, 208);
   }

   private void a(int[] var1, byte[] var2, int var3, int var4, int var5, int var6, int var7) {
      ia = new String[var7];
      ic = new byte[var7];
      hW = var3;
      hX = var4;
      hY = var5;
      hZ = var6;
      if = var1[0];
      int var9 = 0;
      ((a)this).h();
      int var10 = 0;
      String var11 = "";
      this.im = 0;

      for(int var8 = 0; var8 < var1.length; ++var8) {
         byte var12 = var2[var8];
         int var14;
         if ((var14 = var1[var8]) >= 0) {
            var11 = ((a)this).e(var1[var8]);
         } else if (var14 == -1) {
            ia[var10] = "";
            ic[var10++] = var12;
            var11 = "";
         } else if (var14 == -3) {
            var11 = ib[var9++];
         }

         if (!var11.equals("")) {
            if (var11.charAt(0) == '&') {
               var11 = var11.substring(2, var11.length());
            } else if (var11.charAt(0) == '^') {
               var11 = var11.substring(2, var11.length());
            } else if (var11.charAt(0) == '*') {
               var11 = var11.substring(1, var11.length());
            }
         }

         int var13 = var11.length();
         int var15 = 0;

         int var21;
         for(int var16 = 0; var15 < var13; var16 = var15 = var21 + 1) {
            int var18 = 0;
            boolean var19 = false;
            int var17;
            if (this.cv >= 0 && var10 % 5 * A < this.ct[0].d << 1) {
               var17 = var5 - (this.ct[0].c + 8);
            } else {
               var17 = var5 - 1 - 4;
            }

            while(var18 <= var17 && var16 < var13) {
               if ((var21 = var11.charAt(var16)) == 10) {
                  var19 = true;
                  break;
               }

               var18 += ((a)this).a((char)var21);
               ++var16;
            }

            if (var8 == 0 && if != -1) {
               this.in = 0;
               this.io = var16;
               var16 = var13;
            }

            if (var19) {
               var21 = var16;
            } else if (var16 == var13) {
               var21 = var13 - 1;
            } else if ((var21 = Math.max(var11.lastIndexOf(32, var16), var11.lastIndexOf(46, var16 - 1))) == -1) {
               var21 = Math.min(var16 - 1, var13 - 1);
            } else if (var21 < var15) {
               var21 = var16 - 1;
            }

            if (var21 == var11.length()) {
               --var21;
            }

            ia[var10] = var11.substring(var15, var21 + 1).trim();
            ic[var10++] = var12;
            this.im += A;
         }
      }

      id = var10;
      ie = 0;
      this.ip = 0;
      ig = false;
      ((a)this).g();
   }

   private static void ac() {
      ia = null;
      ic = null;
      ib = null;
   }

   private void i(boolean var1) {
      if (var1) {
         if (ie > 0) {
            --ie;
            ((a)this).h();
            return;
         }
      } else if (ie < id && !ig) {
         ++ie;
         ((a)this).h();
      }

   }

   private void a(Graphics var1, boolean var2) {
      ((a)this).a(var1, 0, hX, 176, hZ);
      byte var4 = 0;
      this.it = true;
      if (hZ / A >= id) {
         var4 = 2;
      }

      int var6 = hX;
      if (this.aX) {
         var6 = 183 - (this.ir >> 8);
      } else if (J == 79) {
         var6 += 25;
      }

      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      if (J == 101) {
         var6 -= ((a)this).c() >> 1;
      }

      this.is = 0;

      int var3;
      for(var3 = this.ip > 0 ? this.ip : ie; var3 < id && var6 < hX + hZ; ++var3) {
         var11 = var3;
         if (var9 < 1) {
            var11 = var9;
         }

         byte var8;
         int var12 = ((var8 = ic[var11]) & 2) != 0 ? 4 : 1;
         int var7 = hW + var4 + ((var8 & 2) != 0 ? 0 : hY - 4 >> 1) + 1;
         if (this.cv >= 0 && var6 - A - hX < this.ct[0].d) {
            var7 += this.ct[0].c + 8;
         }

         String var13 = ia[var11];
         if (J == 101 && !var13.equals("")) {
            if (var13.length() > this.be - this.is) {
               var13 = var13.substring(0, this.be - this.is);
               this.is += var13.length();
               this.it = false;
            } else {
               this.is += var13.length();
            }
         }

         if (var3 == ie && if != -1) {
            if (this.o != null) {
               ((a)this).a(this.p);
            } else {
               ((a)this).a(this.q);
            }
         } else {
            ((a)this).a(this.q);
         }

         ((a)this).h();
         ((a)this).a(var1, var3 == ie && if != -1 ? var13.substring(this.in, this.io) : var13, var7, var6, var12);
         var6 += A;
         if (J == 101 && var6 >= hZ && var11 < id) {
            this.iq = var11;
            if (ia[var11].equals("") || this.iq + 1 == id) {
               this.iq = -1;
            }
            break;
         }

         this.iq = -1;
         if (var9++ < 2) {
            var10 = var6;
         }
      }

      ig = var3 == id;
      ((a)this).b(var1);
      this.iF = true;
      if (var4 == 0 && var2 && !this.aX) {
         var1.setColor(10377532);
         int var16 = hZ - (var10 - 25) - 4;
         var1.drawRect(hW + hY + 7 - 1, var10 - 4, 4, var16);
         int var18 = var10 + ie * var16 / id - 3;
         int var14 = var10 + var3 * var16 / id - 3;
         var1.setColor(15425792);
         var1.fillRect(hW + hY + 7, var18, 3, var14 - var18 - 1);
      }

      if (this.cv >= 0) {
         if (this.cv > 3) {
            int var17 = this.cv - 3 - 1 + 5;
            byte var19 = 0;
            byte var20 = 0;
            if (var17 == 8) {
               var19 = -3;
            }

            if (var17 == 6) {
               var20 = -3;
            }

            int var15;
            if ((var15 = var17 + (bk > 0 ? 4 - bk : 0)) > 8) {
               var15 = 5 + (var15 - 8) - 1;
            }

            aM[40].a(var1, hW + (this.ct[this.cv].c >> 3) - aM[40].a, hX + A - aM[40].b, 0);
            aM[var15].a(var1, 13 - aM[var15].a + var19, 15 - aM[var15].b + var20, 0);
         } else {
            this.ct[this.cv].a(var1, hW + (this.ct[this.cv].c >> 3), hX + A, 0);
         }

         ((a)this).a(136, 1024, 0);
         ((a)this).a(var1, this.ct, 136, hW + (this.ct[this.cv].c >> 3), hX + A, 0);
      }

      if ((var11 < id - 1 || this.cw < this.cy - 1) && J == 101) {
         bs[1].a(var1, H, 79 - bs[1].d - 2, 0);
      }

   }

   private void z(int var1) {
      iu = new byte[76];
      int var2 = 0;
      var2 = a(gT, iu, 0);
      var2 = a(gU, iu, var2);
      var2 = a(gR, iu, var2);
      var2 = a(this.gS, iu, var2);
      var2 = a(gZ, iu, var2);
      var2 = a(ha, iu, var2);
      var2 = a(this.hs, iu, var2);
      var2 = a(this.ht, iu, var2);
      var2 = a(this.hu, iu, var2);
      var2 = a(this.hd, iu, var2);
      var2 = a(this.he, iu, var2);
      var2 = a(this.hb, iu, var2);
      var2 = a(this.hc, iu, var2);
      var2 = a(this.bH, iu, var2);
      var2 = a(hv, iu, var2);
      var2 = a(this.hw, iu, var2);
      var2 = a(hi, iu, var2);
      if (this.dM > this.hD && iS) {
         this.hD = this.dM;
      }

      a(this.hD, iu, var2);
      this.ad();
      a.a(var1 + 1, iu);
      this.ad();
   }

   private void A(int var1) {
      iu = a.a(var1 + 1);
      if (iu == null) {
         gR = 3;
         this.gS = 2;
         gZ = 0;
         ha = 0;
         this.hs = 0;
         this.ht = 0;
         this.hq = 1024;
         this.hr = 1024;
         this.hu = 0;
         this.hd = 0;
         this.he = 0;
         this.hb = 0;
         this.hc = 0;
         gT = 0;
         gU = 0;
         this.bG = 0;
         this.bH = 0;
         hv = 0;
         this.hw = 0;
         hi = 0;
         this.hD = 0;
      } else {
         gT = (byte)a(iu, 0);
         gU = (byte)a(iu, 4);
         gR = (byte)a(iu, 8);
         this.gS = (byte)a(iu, 12);
         if (gR < 3) {
            gR = 3;
         }

         if (this.gS < 2) {
            this.gS = 2;
         }

         gZ = (byte)a(iu, 16);
         ha = (byte)a(iu, 20);
         this.hs = a(iu, 24);
         this.ht = a(iu, 28);
         this.hq = this.hs * 192 + 1024;
         this.hr = this.ht * 192 + 1024;
         this.hu = a(iu, 32);
         this.hd = a(iu, 36);
         this.he = a(iu, 40);
         this.hb = a(iu, 44);
         this.hc = a(iu, 48);
         this.bH = a(iu, 52);
         this.bG = this.bH;
         hv = a(iu, 56);
         this.hw = a(iu, 60);
         hi = a(iu, 64);
         this.hD = a(iu, 68);
      }

      this.ad();
   }

   private void ad() {
      for(int var1 = 0; var1 < 10; ++var1) {
         if (var1 <= this.bH) {
            int var10001 = 9 + var1;
            int[] var10002 = iA;
            iA[var10001] = var10002[9 + var1] |= 50331648;
         } else {
            iA[9 + var1] &= -50331649;
         }
      }

   }

   private static int a(int var0, byte[] var1, int var2) {
      var1[var2++] = (byte)(var0 >> 24 & 255);
      var1[var2++] = (byte)(var0 >> 16 & 255);
      var1[var2++] = (byte)(var0 >> 8 & 255);
      var1[var2++] = (byte)(var0 & 255);
      return var2;
   }

   private static int a(byte[] var0, int var1) {
      return ((var0[0 + var1] & 255) << 24) + ((var0[1 + var1] & 255) << 16) + ((var0[2 + var1] & 255) << 8) + (var0[3 + var1] & 255);
   }

   private void ae() {
      int var2 = 176 - (this.iy - (this.iy >> 2));
      if (iv == null) {
         iv = new byte[var2 * 91];
         iw = new int[var2 * 91];
      }

      int var3 = 0;
      if (ix) {
         for(int var4 = 0; var4 < var2; ++var4) {
            if ((var4 & 3) == 0) {
               var3 = P.nextInt();
            } else {
               var3 >>= 8;
            }

            iv[var4 + 90 * var2] = (byte)(var3 & 255);
            iv[var4 + 89 * var2] = (byte)Math.max(0, (iv[var4 + 90 * var2] & 255) - 4);
            ix = false;
         }
      } else {
         ix = true;
      }

      for(int var5 = 1; var5 < 90; ++var5) {
         int var1 = var5 * var2;

         for(int var6 = 0; var6 < var2; ++var6) {
            int var9 = var6 + var1;
            int var7 = (iv[var9] & 255) + (iv[var9 + var2] & 255);
            if (var5 > 22) {
               var7 = var7 + (iv[var9 + 1] & 255) + (iv[var9 - 1] & 255) >> 2;
            } else {
               var7 >>= 1;
            }

            var7 -= 2;
            if (var7 < 0) {
               var7 = 0;
            }

            iv[var9 - var2] = (byte)var7;
         }
      }

      int var10 = 0;

      for(int var14 = 0; var14 < 91; ++var14) {
         for(int var8 = 0; var8 < var2; ++var8) {
            int var11 = Math.max(iv[var10] & 255, 0);
            iw[var10] = S[var11];
            ++var10;
         }
      }

   }

   private void b(Graphics var1, boolean var2) {
      this.ae();
      int var3 = var2 ? 0 : 63;
      int var4 = var2 ? 109 : 110;
      a.a(var1, iw, 0, 176 - (this.iy - (this.iy >> 2)), var3, var4, 176 - (this.iy - (this.iy >> 2) + 1), 91, false);
   }

   private void a(Graphics var1, int var2, int var3) {
      this.iE = var3;
      this.iD = var2;
      ((a)this).a(this.q);
      var1.setColor(0);
      var1.fillRect(0, 194, 176, 14);
      var1.setColor(9787452);
      var1.drawLine(0, 194, 176, 194);
      var1.setColor(4269078);
      var1.drawLine(0, 195, 176, 195);
      if ((this.U == this.ab && this.V == this.ac || J == 106) && this.az < 0 && (!fV || J == 102)) {
         if (var2 != -1) {
            this.a(var1, 0, var2, 2);
         }

         if (var3 != -1) {
            this.a(var1, 176, var3, 0);
         }

         if (J == 100) {
            if (!iS) {
               if (this.gN == -1 || this.dk[this.gN] <= 0 && this.gO <= 0) {
                  if (gL > 1 && this.gM > 0 && J == 100 && this.V <= 0 && this.iz < gL) {
                     ((a)this).a(var1, gL + " " + ((a)this).e(171), 88, 194 + B - 3, 1);
                  }
               } else {
                  String var4 = this.dk[this.gN] - dj[this.gN] + " " + ((a)this).e(195);
                  int var5 = ((a)this).a(var4);
                  if (gQ && this.dk[this.gN] > 0) {
                     ((a)this).a(var1, var4, H, 194 + B - 3, 1);
                  }

                  if (this.gN >= 0 && this.dk[this.gN] > 0) {
                     int var6 = H - (var5 >> 1) - 5;
                     int var7 = H + (var5 >> 1) + 5;
                     var1.setColor(14172934);
                     var1.drawLine(var6 - 4, 195, var6 - 4, 208);
                     var1.drawLine(var7 + 4, 195, var7 + 4, 208);
                     var1.setColor(14038016);
                     var1.drawLine(var6 - 3, 195, var6 - 3, 208);
                     var1.drawLine(var7 + 3, 195, var7 + 3, 208);
                     var1.setColor(13116929);
                     var1.drawLine(var6 - 2, 195, var6 - 2, 208);
                     var1.drawLine(var7 + 2, 195, var7 + 2, 208);
                     var1.setColor(10885383);
                     var1.drawLine(var6 - 1, 195, var6 - 1, 208);
                     var1.drawLine(var7 + 1, 195, var7 + 1, 208);
                     var1.setColor(7146758);
                     var1.drawLine(var6, 195, var6, 208);
                     var1.drawLine(var7, 195, var7, 208);
                  }
               }
            } else if (this.dM >= 1000) {
               ((a)this).a(var1, ((a)this).e(275), 88, 194 + B - 3, 1);
            } else {
               ((a)this).a(var1, ((a)this).e(246) + this.dM, 88, 194 + B - 3, 1);
            }
         } else {
            this.iF = true;
         }
      }

      this.iF = false;
   }

   private void a(Graphics var1, int var2, int var3, int var4) {
      String var5 = ((a)this).e(var3);
      ((a)this).a(var1, var5, var2 - (var4 == 0 ? ((a)this).a(var5) + 2 : -2), 194 + B - 3, 20);
   }

   private static void a(Graphics var0, int var1, int var2, boolean var3) {
      if (J != 1) {
         var2 -= 2;
         var1 -= 2;
      }

      int var4 = 195;
      if (J == 1) {
         var4 = I + 5 + (A >> 2);
         if (iS) {
            var4 += A + (A >> 2);
         }
      }

      int var5 = var2 * 5 + (var2 + 1) * 2 - 1;
      int var6 = (176 - var5) / 2;
      var0.setColor(0);
      var0.fillRect(var6 - 1, var4 - 1, var5 + 3, 13);
      var0.setColor(15690496);
      var0.drawRect(var6, var4, var5, 10);
      var0.setColor(16711680);
      var4 += 2;
      var6 += 2;
      if (var3) {
         for(int var7 = 0; var7 < var1; ++var7) {
            var0.fillRect(var6, var4, 5, 7);
            var6 += 7;
         }
      }

      var0.setColor(3737600);
      int var10000 = var3 ? var1 : 0;
      int var11 = var3 ? var1 : 0;

      for(int var8 = var10000 < 0 ? 0 : var11; var8 < var2; ++var8) {
         if (!var3) {
            if (var8 == var1) {
               var0.setColor(16711680);
            } else {
               var0.setColor(3737600);
            }
         }

         var0.fillRect(var6, var4, 5, 7);
         var6 += 7;
      }

   }

   private void af() {
      byte[] var1;
      int var2;
      iA = new int[var2 = (var1 = ((a)this).c(1028)).length >> 2];

      for(int var3 = var2 - 1; var3 >= 0; --var3) {
         int var4 = var3 << 2;
         iA[var3] = ((var1[var4] & 255) << 24) + ((var1[var4 + 1] & 255) << 16) + ((var1[var4 + 2] & 255) << 8) + (var1[var4 + 3] & 255);
      }

   }

   private static int m(int var0, int var1) {
      int var2 = var0;

      while(var0 < iA.length && (iA[var0] & 4194304) == 0) {
         ++var0;
         if ((iA[var0] & var1) != 0) {
            return var0;
         }

         if ((iA[var0] & 4194304) != 0) {
            return var2;
         }
      }

      return var2;
   }

   private static int n(int var0, int var1) {
      int var2 = var0;

      do {
         if ((iA[var0] & 8388608) == 0) {
            --var0;
            if ((iA[var0] & var1) != 0) {
               var2 = var0;
            }
         }

         if ((iA[var0] & 8388608) != 0 && (iA[var0] & var1) == 0) {
            return var2;
         }
      } while((iA[var0] & var1) == 0);

      return var2;
   }

   private void ag() {
      int var1 = iB;
      int var2;
      int var3 = (var2 = iA[iB]) >> 26;
      int var4 = var2 >> 8 & 255;
      switch (var3) {
         case 0:
            if (this.s == 8 || this.s == 27) {
               if (var4 == 9 && this.bG == 0) {
                  iS = false;
                  this.bG = 0;
                  this.bE = 9217;
                  this.a(I, I, 35, 35);
                  J = 1;
                  this.Z();
                  this.iR.c();
                  this.w = false;
                  this.X = true;
               } else {
                  var1 = var4;
               }
            }
            break;
         case 1:
            if (this.s == 8 || this.s == 27) {
               this.iR.a(!this.iR.b());
               iQ = this.iR.b();
               if (!this.iR.b()) {
                  this.hV = false;
               }

               this.y(0);
            }
         case 2:
         case 7:
         default:
            break;
         case 3:
            if (this.s == 8 || this.s == 27) {
               this.aj = false;
               this.ak = true;
               this.a(I, I, 35, 35);
            }
            break;
         case 4:
            if (this.s == 8 || this.s == 27) {
               this.aj = true;
               this.ak = true;
               this.a(I, I, 35, 35);
            }
            break;
         case 5:
            if (this.s == 8 || this.s == 27) {
               this.bG = iB - this.iL;
               this.bg = false;
               this.bE = bF[this.bG];
               iS = false;
               this.a(I, I, 35, 35);
               J = 1;
               this.w = false;
               this.Z();
               this.ab();
            }
            break;
         case 6:
            if (this.s == 8 || this.s == 27) {
               this.bE = 9216;
               this.bG = 0;
               this.dM = 0;
               iS = true;
               this.a(I, I, 35, 35);
               J = 1;
               this.w = false;
               this.Z();
               this.ab();
            }
            break;
         case 8:
            if (this.s == 8 || this.s == 27) {
               this.ai();
            }
            break;
         case 9:
            if (this.s == 8 || this.s == 27) {
               this.b(false);
               J = this.K;
            }
            break;
         case 10:
            if (this.s == 8 || this.s == 27) {
               this.Z();
               this.y(0);
               ((a)this).a((DataInputStream)null);
               this.z(1);
               this.iC = iB;
               var1 = var4;
            }
            break;
         case 11:
            if (this.s == 8 || this.s == 27 || this.s == 29) {
               if (this.s != 29) {
                  if (this.iH) {
                     iP = true;
                     ((a)this).b(this.u);
                     this.ai();
                  } else {
                     byte[] var5 = new byte[76];
                     a.a(2, var5);
                     this.A(1);
                  }
               }

               var1 = var4;
               if (this.iH) {
                  var1 = 0;
                  this.iH = false;
               }
            }
            break;
         case 12:
            if (this.s == 8 || this.s == 27) {
               ++x;
               if (x > 5) {
                  x = 0;
               }

               ((a)this).f(y[x]);
               this.iF = true;
            }
      }

      if (this.s != 0) {
         this.iN = false;
         this.iG = true;
      }

      if (this.s == 2) {
         var1 = n(var1, 33554432);
      }

      if (this.s == 5) {
         var1 = m(var1, 33554432);
      }

      if (this.bf) {
         this.iN = false;
         ++iB;
         ++this.bG;
         if (this.bG > this.bH) {
            this.bH = this.bG;
         }

         ++this.bE;
         J = 1;
         this.af = true;
         this.w = false;
         this.ab();
      }

      if (this.s == 29 && var3 != 11) {
         if (this.iE != 2 && this.iE != 29) {
            if (this.iE == 1) {
               var1 = 23;
            }
         } else {
            var1 = this.iC;
            this.iN = false;
         }
      } else if (this.s == 27 && var3 == 0 && this.iD == 0 && (var4 != 9 || this.bG != 0)) {
         var1 = var4;
         this.iN = false;
      }

      if (iB != var1) {
         iB = var1;
         if ((iA[iB] & 33554432) == 0) {
            iB = m(var1, 33554432);
         }

         ((a)this).h();
      }

   }

   private void ah() {
      if (this.s == 8 || this.s == 27) {
         int var1 = iA[iB];
         this.iG = true;
         switch (var1 & 255) {
            case 5:
               this.iH = true;
         }
      }

   }

   private void ai() {
      if (J != 0 && J != 79 && !iP) {
         this.z(1);
      }

      ((a)this).j(3);
   }

   private void w(Graphics var1) {
      int var2 = 0;
      int var3 = 0;
      if (this.F > this.G) {
         var2 = -(this.F >> 3);
      }

      if (this.iG || (this.U > 0 || this.al) && this.iN && J != 1) {
         this.v(var1);
      }

      ((a)this).a(this.p);
      ((a)this).a(var1, ((a)this).e(274), H + var2, this.r[0].d + this.r[1].d, 1);
      ((a)this).a(this.o);
      int var4 = iB;
      int var6 = 0;

      while(var4 == 1 || var4 > 1 && (iA[var4] & 8388608) == 0) {
         --var4;
         if ((iA[var4] & 16777216) != 0) {
            ++var6;
         }
      }

      int var7 = var4;
      var4 = iB;

      for(int var8 = 0; var8 < var6; ++var8) {
         var4 = n(var4, 16777216);
      }

      this.iL = var4;

      for(int var18 = 0; var18 < var6; ++var18) {
         var4 = n(var4, 33554432);
      }

      this.iM = var4;
      this.iI = 0;
      int var10 = var4;
      int var11 = var4;
      if (this.iN && this.U <= 0 && !this.al) {
         ((a)this).a(var1, 0, 103, 176, 91);
      } else {
         ((a)this).b(var1);
      }

      this.b(var1, true);

      for(int var13 = this.iN && this.U <= 0 && !this.al ? 2 : 0; var13 <= 4; ++var13) {
         this.r[var13].a(var1, var13 < 2 ? H + var2 : this.F + 0, var13 < 2 ? 0 : this.G, 0);
      }

      if (this.al) {
         this.al = false;
      }

      this.iN = true;
      this.iK = this.r[0].d + this.r[1].d + (A << 1);
      if (-this.r[2].b + this.iK >= this.G && var2 == 0) {
         var3 = -(this.F >> 3);
      }

      while(true) {
         if ((iA[var11] & 16777216) != 0) {
            var10 = var11;
         }

         if ((iA[var11] & 33554432) != 0) {
            var7 = var11;
         }

         if ((iA[var11] & 4194304) != 0) {
            this.iI = this.F < this.iI ? this.F : this.iI;
            this.iJ = 88;
            var1.setColor(0, 0, 255);
            var4 = this.iL;
            ((a)this).a(this.o);

            boolean var19;
            do {
               int var9;
               int var5 = (var9 = iA[var4]) >> 26;
               if ((var9 & 16777216) != 0) {
                  int var14;
                  if ((var14 = var9 & 255) == 23) {
                     int var15 = x + 1 > 5 ? 0 : x + 1;
                     var14 = z[var15];
                  }

                  String var20 = ((a)this).e(var14);
                  switch (var5) {
                     case 1:
                        var20 = var20 + " " + (iQ ? ((a)this).e(234) : ((a)this).e(235));
                     case 0:
                     case 3:
                     case 4:
                     case 5:
                     case 6:
                     case 8:
                     case 9:
                     case 10:
                     case 11:
                     case 12:
                        if (var4 == iB) {
                           if (var14 != 28 && var14 != 29) {
                              if (var4 > this.iM) {
                                 bs[2].a(var1, H - this.iO - (bs[2].c << 1) + var2 + var3, this.iK + (A >> 3) + 4, 0);
                              }

                              ((a)this).a(var1, var20, this.iJ + var2 + var3, this.iK, 1);
                              if (var4 < var7) {
                                 bs[3].a(var1, H + this.iO + bs[3].c + var2 + var3, this.iK + (A >> 3) + 4, 0);
                              }
                           }

                           this.iK += A + (A >> 2);
                        }
                     case 2:
                     case 7:
                  }
               }

               if (var19 = var4 < var10) {
                  var4 = m(var4, 16777216);
               }
            } while(var19);

            return;
         }

         ++var11;
      }
   }
}
