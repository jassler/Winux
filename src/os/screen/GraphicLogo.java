package os.screen;

import kernel.Kernel;

public class GraphicLogo {

    public static void doIntro(int sleep) {
        int start = 0xC8;
        int end = 0xF7;
        int dist = end - start;

        Graphic g = new Graphic();
        for(int y = 0; y < Graphic.HEIGHT; y++) {
            for(int x = 0; x < Graphic.WIDTH; x++) {
                g.setPtrAndInc((byte) ((y * dist / 200) + start));
            }
        }

        start = 0x38;
        end = 0x67;
        GraphicLogo.slapLogoOnTop(g, sleep, start, end);
    }

    /**
     * Slap some beautiful logo on top
     *
     * @param g Graphic object to slap
     * @param sleepLength Sleep between each column
     * @param s Starting color
     * @param e Ending color
     */
    public static void slapLogoOnTop(Graphic g, int sleepLength, int s, int e) {
        int d = e - s;
        // todo actually do something with parameters


        // generated with a python script looking at a black-n-white 320x200 image
/*
# pip3 install pillow
from PIL import Image
im = Image.open("winux2.png")
# optional
# im = im.resize((320, 200))

pix = im.load()

start = 0x38
end = 0x67
distance = end - start
didSleep = False

for x in range(0, im.size[0]):
  # optional delay
  if (x % 2) == 1 and not didSleep:
    print('Kernel.sleep(sleepLength);')
    didSleep = True

  for y in range(0, im.size[1]):
    if pix[x,y][0] > 200:
      # encountered white pixel
      didSleep = False
      print('g.mem.img[{}] = {};'.format(x + y * 320, hex((x * distance) // im.size[0] + start)))
  */

        Kernel.sleep(sleepLength);
        g.mem.img[28282]=(byte)((122*d)/320+s);
        g.mem.img[28602]=(byte)((122*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[27963]=(byte)((123*d)/320+s);
        g.mem.img[28283]=(byte)((123*d)/320+s);
        g.mem.img[28603]=(byte)((123*d)/320+s);
        g.mem.img[28923]=(byte)((123*d)/320+s);
        g.mem.img[29243]=(byte)((123*d)/320+s);
        g.mem.img[28604]=(byte)((124*d)/320+s);
        g.mem.img[28924]=(byte)((124*d)/320+s);
        g.mem.img[29244]=(byte)((124*d)/320+s);
        g.mem.img[29564]=(byte)((124*d)/320+s);
        g.mem.img[29884]=(byte)((124*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29885]=(byte)((125*d)/320+s);
        g.mem.img[30205]=(byte)((125*d)/320+s);
        g.mem.img[30525]=(byte)((125*d)/320+s);
        g.mem.img[30845]=(byte)((125*d)/320+s);
        g.mem.img[31165]=(byte)((125*d)/320+s);
        g.mem.img[30846]=(byte)((126*d)/320+s);
        g.mem.img[31166]=(byte)((126*d)/320+s);
        g.mem.img[31486]=(byte)((126*d)/320+s);
        g.mem.img[31806]=(byte)((126*d)/320+s);
        g.mem.img[32126]=(byte)((126*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[30847]=(byte)((127*d)/320+s);
        g.mem.img[31167]=(byte)((127*d)/320+s);
        g.mem.img[31487]=(byte)((127*d)/320+s);
        g.mem.img[31807]=(byte)((127*d)/320+s);
        g.mem.img[29888]=(byte)((128*d)/320+s);
        g.mem.img[30208]=(byte)((128*d)/320+s);
        g.mem.img[30528]=(byte)((128*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[28289]=(byte)((129*d)/320+s);
        g.mem.img[28609]=(byte)((129*d)/320+s);
        g.mem.img[28929]=(byte)((129*d)/320+s);
        g.mem.img[29249]=(byte)((129*d)/320+s);
        g.mem.img[29569]=(byte)((129*d)/320+s);
        g.mem.img[28290]=(byte)((130*d)/320+s);
        g.mem.img[28610]=(byte)((130*d)/320+s);
        g.mem.img[28930]=(byte)((130*d)/320+s);
        g.mem.img[29250]=(byte)((130*d)/320+s);
        g.mem.img[29570]=(byte)((130*d)/320+s);
        g.mem.img[29890]=(byte)((130*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[28931]=(byte)((131*d)/320+s);
        g.mem.img[29251]=(byte)((131*d)/320+s);
        g.mem.img[29571]=(byte)((131*d)/320+s);
        g.mem.img[29891]=(byte)((131*d)/320+s);
        g.mem.img[30211]=(byte)((131*d)/320+s);
        g.mem.img[30531]=(byte)((131*d)/320+s);
        g.mem.img[30212]=(byte)((132*d)/320+s);
        g.mem.img[30532]=(byte)((132*d)/320+s);
        g.mem.img[30852]=(byte)((132*d)/320+s);
        g.mem.img[31172]=(byte)((132*d)/320+s);
        g.mem.img[31492]=(byte)((132*d)/320+s);
        g.mem.img[31812]=(byte)((132*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[30853]=(byte)((133*d)/320+s);
        g.mem.img[31173]=(byte)((133*d)/320+s);
        g.mem.img[31493]=(byte)((133*d)/320+s);
        g.mem.img[31813]=(byte)((133*d)/320+s);
        g.mem.img[32133]=(byte)((133*d)/320+s);
        g.mem.img[30214]=(byte)((134*d)/320+s);
        g.mem.img[30534]=(byte)((134*d)/320+s);
        g.mem.img[30854]=(byte)((134*d)/320+s);
        g.mem.img[31174]=(byte)((134*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[28935]=(byte)((135*d)/320+s);
        g.mem.img[29255]=(byte)((135*d)/320+s);
        g.mem.img[29575]=(byte)((135*d)/320+s);
        g.mem.img[29895]=(byte)((135*d)/320+s);
        g.mem.img[28296]=(byte)((136*d)/320+s);
        g.mem.img[28616]=(byte)((136*d)/320+s);
        g.mem.img[28936]=(byte)((136*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[28299]=(byte)((139*d)/320+s);
        g.mem.img[29579]=(byte)((139*d)/320+s);
        g.mem.img[29899]=(byte)((139*d)/320+s);
        g.mem.img[30219]=(byte)((139*d)/320+s);
        g.mem.img[30539]=(byte)((139*d)/320+s);
        g.mem.img[30859]=(byte)((139*d)/320+s);
        g.mem.img[31179]=(byte)((139*d)/320+s);
        g.mem.img[31499]=(byte)((139*d)/320+s);
        g.mem.img[31819]=(byte)((139*d)/320+s);
        g.mem.img[32139]=(byte)((139*d)/320+s);
        g.mem.img[27980]=(byte)((140*d)/320+s);
        g.mem.img[29580]=(byte)((140*d)/320+s);
        g.mem.img[29900]=(byte)((140*d)/320+s);
        g.mem.img[30220]=(byte)((140*d)/320+s);
        g.mem.img[30540]=(byte)((140*d)/320+s);
        g.mem.img[30860]=(byte)((140*d)/320+s);
        g.mem.img[31180]=(byte)((140*d)/320+s);
        g.mem.img[31500]=(byte)((140*d)/320+s);
        g.mem.img[31820]=(byte)((140*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29583]=(byte)((143*d)/320+s);
        g.mem.img[29903]=(byte)((143*d)/320+s);
        g.mem.img[30223]=(byte)((143*d)/320+s);
        g.mem.img[30543]=(byte)((143*d)/320+s);
        g.mem.img[30863]=(byte)((143*d)/320+s);
        g.mem.img[31183]=(byte)((143*d)/320+s);
        g.mem.img[31503]=(byte)((143*d)/320+s);
        g.mem.img[31823]=(byte)((143*d)/320+s);
        g.mem.img[32143]=(byte)((143*d)/320+s);
        g.mem.img[29584]=(byte)((144*d)/320+s);
        g.mem.img[29904]=(byte)((144*d)/320+s);
        g.mem.img[30224]=(byte)((144*d)/320+s);
        g.mem.img[30544]=(byte)((144*d)/320+s);
        g.mem.img[30864]=(byte)((144*d)/320+s);
        g.mem.img[31184]=(byte)((144*d)/320+s);
        g.mem.img[31504]=(byte)((144*d)/320+s);
        g.mem.img[31824]=(byte)((144*d)/320+s);
        g.mem.img[32144]=(byte)((144*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29585]=(byte)((145*d)/320+s);
        g.mem.img[29905]=(byte)((145*d)/320+s);
        g.mem.img[29586]=(byte)((146*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29587]=(byte)((147*d)/320+s);
        g.mem.img[29588]=(byte)((148*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29589]=(byte)((149*d)/320+s);
        g.mem.img[29909]=(byte)((149*d)/320+s);
        g.mem.img[29590]=(byte)((150*d)/320+s);
        g.mem.img[29910]=(byte)((150*d)/320+s);
        g.mem.img[30230]=(byte)((150*d)/320+s);
        g.mem.img[30550]=(byte)((150*d)/320+s);
        g.mem.img[30870]=(byte)((150*d)/320+s);
        g.mem.img[31190]=(byte)((150*d)/320+s);
        g.mem.img[31510]=(byte)((150*d)/320+s);
        g.mem.img[31830]=(byte)((150*d)/320+s);
        g.mem.img[32150]=(byte)((150*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29911]=(byte)((151*d)/320+s);
        g.mem.img[30231]=(byte)((151*d)/320+s);
        g.mem.img[30551]=(byte)((151*d)/320+s);
        g.mem.img[30871]=(byte)((151*d)/320+s);
        g.mem.img[31191]=(byte)((151*d)/320+s);
        g.mem.img[31511]=(byte)((151*d)/320+s);
        g.mem.img[31831]=(byte)((151*d)/320+s);
        g.mem.img[32151]=(byte)((151*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29594]=(byte)((154*d)/320+s);
        g.mem.img[29914]=(byte)((154*d)/320+s);
        g.mem.img[30234]=(byte)((154*d)/320+s);
        g.mem.img[30554]=(byte)((154*d)/320+s);
        g.mem.img[30874]=(byte)((154*d)/320+s);
        g.mem.img[31194]=(byte)((154*d)/320+s);
        g.mem.img[31514]=(byte)((154*d)/320+s);
        g.mem.img[31834]=(byte)((154*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29595]=(byte)((155*d)/320+s);
        g.mem.img[29915]=(byte)((155*d)/320+s);
        g.mem.img[30235]=(byte)((155*d)/320+s);
        g.mem.img[30555]=(byte)((155*d)/320+s);
        g.mem.img[30875]=(byte)((155*d)/320+s);
        g.mem.img[31515]=(byte)((155*d)/320+s);
        g.mem.img[31835]=(byte)((155*d)/320+s);
        g.mem.img[32155]=(byte)((155*d)/320+s);
        g.mem.img[32156]=(byte)((156*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[32157]=(byte)((157*d)/320+s);
        g.mem.img[32158]=(byte)((158*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[32159]=(byte)((159*d)/320+s);
        g.mem.img[31840]=(byte)((160*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29601]=(byte)((161*d)/320+s);
        g.mem.img[29921]=(byte)((161*d)/320+s);
        g.mem.img[30241]=(byte)((161*d)/320+s);
        g.mem.img[30561]=(byte)((161*d)/320+s);
        g.mem.img[30881]=(byte)((161*d)/320+s);
        g.mem.img[31201]=(byte)((161*d)/320+s);
        g.mem.img[31521]=(byte)((161*d)/320+s);
        g.mem.img[31841]=(byte)((161*d)/320+s);
        g.mem.img[32161]=(byte)((161*d)/320+s);
        g.mem.img[29602]=(byte)((162*d)/320+s);
        g.mem.img[31842]=(byte)((162*d)/320+s);
        g.mem.img[32162]=(byte)((162*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29604]=(byte)((164*d)/320+s);
        g.mem.img[32164]=(byte)((164*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29605]=(byte)((165*d)/320+s);
        g.mem.img[29925]=(byte)((165*d)/320+s);
        g.mem.img[31845]=(byte)((165*d)/320+s);
        g.mem.img[32165]=(byte)((165*d)/320+s);
        g.mem.img[29606]=(byte)((166*d)/320+s);
        g.mem.img[29926]=(byte)((166*d)/320+s);
        g.mem.img[30246]=(byte)((166*d)/320+s);
        g.mem.img[31526]=(byte)((166*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[30247]=(byte)((167*d)/320+s);
        g.mem.img[30567]=(byte)((167*d)/320+s);
        g.mem.img[30887]=(byte)((167*d)/320+s);
        g.mem.img[31207]=(byte)((167*d)/320+s);
        g.mem.img[30568]=(byte)((168*d)/320+s);
        g.mem.img[30888]=(byte)((168*d)/320+s);
        g.mem.img[31208]=(byte)((168*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[30569]=(byte)((169*d)/320+s);
        g.mem.img[30889]=(byte)((169*d)/320+s);
        g.mem.img[31209]=(byte)((169*d)/320+s);
        g.mem.img[30250]=(byte)((170*d)/320+s);
        g.mem.img[31210]=(byte)((170*d)/320+s);
        g.mem.img[31530]=(byte)((170*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29611]=(byte)((171*d)/320+s);
        g.mem.img[29931]=(byte)((171*d)/320+s);
        g.mem.img[31531]=(byte)((171*d)/320+s);
        g.mem.img[31851]=(byte)((171*d)/320+s);
        g.mem.img[29612]=(byte)((172*d)/320+s);
        g.mem.img[31852]=(byte)((172*d)/320+s);
        g.mem.img[32172]=(byte)((172*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[31853]=(byte)((173*d)/320+s);
        g.mem.img[32173]=(byte)((173*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[33138]=(byte)((178*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[29619]=(byte)((179*d)/320+s);
        g.mem.img[32819]=(byte)((179*d)/320+s);
        g.mem.img[33139]=(byte)((179*d)/320+s);
        g.mem.img[29300]=(byte)((180*d)/320+s);
        g.mem.img[29620]=(byte)((180*d)/320+s);
        g.mem.img[32820]=(byte)((180*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[28661]=(byte)((181*d)/320+s);
        g.mem.img[28981]=(byte)((181*d)/320+s);
        g.mem.img[29301]=(byte)((181*d)/320+s);
        g.mem.img[29621]=(byte)((181*d)/320+s);
        g.mem.img[32501]=(byte)((181*d)/320+s);
        g.mem.img[32821]=(byte)((181*d)/320+s);
        g.mem.img[28342]=(byte)((182*d)/320+s);
        g.mem.img[28662]=(byte)((182*d)/320+s);
        g.mem.img[29302]=(byte)((182*d)/320+s);
        g.mem.img[29622]=(byte)((182*d)/320+s);
        g.mem.img[32182]=(byte)((182*d)/320+s);
        g.mem.img[32502]=(byte)((182*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[28343]=(byte)((183*d)/320+s);
        g.mem.img[29303]=(byte)((183*d)/320+s);
        g.mem.img[29623]=(byte)((183*d)/320+s);
        g.mem.img[31863]=(byte)((183*d)/320+s);
        g.mem.img[28344]=(byte)((184*d)/320+s);
        g.mem.img[29304]=(byte)((184*d)/320+s);
        g.mem.img[29624]=(byte)((184*d)/320+s);
        g.mem.img[29944]=(byte)((184*d)/320+s);
        g.mem.img[31544]=(byte)((184*d)/320+s);
        g.mem.img[31864]=(byte)((184*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[28345]=(byte)((185*d)/320+s);
        g.mem.img[29625]=(byte)((185*d)/320+s);
        g.mem.img[29945]=(byte)((185*d)/320+s);
        g.mem.img[30265]=(byte)((185*d)/320+s);
        g.mem.img[30585]=(byte)((185*d)/320+s);
        g.mem.img[30905]=(byte)((185*d)/320+s);
        g.mem.img[31225]=(byte)((185*d)/320+s);
        g.mem.img[28026]=(byte)((186*d)/320+s);
        g.mem.img[28346]=(byte)((186*d)/320+s);
        g.mem.img[29626]=(byte)((186*d)/320+s);
        g.mem.img[29946]=(byte)((186*d)/320+s);
        g.mem.img[30266]=(byte)((186*d)/320+s);
        g.mem.img[30586]=(byte)((186*d)/320+s);
        g.mem.img[30906]=(byte)((186*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[31870]=(byte)((190*d)/320+s);
        g.mem.img[32190]=(byte)((190*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[31871]=(byte)((191*d)/320+s);
        g.mem.img[32191]=(byte)((191*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[32194]=(byte)((194*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[28355]=(byte)((195*d)/320+s);
        g.mem.img[28675]=(byte)((195*d)/320+s);
        g.mem.img[31875]=(byte)((195*d)/320+s);
        g.mem.img[32195]=(byte)((195*d)/320+s);
        g.mem.img[28356]=(byte)((196*d)/320+s);
        g.mem.img[31556]=(byte)((196*d)/320+s);
        g.mem.img[31876]=(byte)((196*d)/320+s);
        g.mem.img[32196]=(byte)((196*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[28357]=(byte)((197*d)/320+s);
        g.mem.img[30917]=(byte)((197*d)/320+s);
        g.mem.img[31237]=(byte)((197*d)/320+s);
        g.mem.img[31877]=(byte)((197*d)/320+s);
        g.mem.img[32197]=(byte)((197*d)/320+s);
        g.mem.img[28358]=(byte)((198*d)/320+s);
        g.mem.img[28678]=(byte)((198*d)/320+s);
        g.mem.img[30278]=(byte)((198*d)/320+s);
        g.mem.img[30598]=(byte)((198*d)/320+s);
        g.mem.img[30918]=(byte)((198*d)/320+s);
        g.mem.img[31878]=(byte)((198*d)/320+s);
        g.mem.img[32198]=(byte)((198*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[28359]=(byte)((199*d)/320+s);
        g.mem.img[28679]=(byte)((199*d)/320+s);
        g.mem.img[29959]=(byte)((199*d)/320+s);
        g.mem.img[30279]=(byte)((199*d)/320+s);
        g.mem.img[31879]=(byte)((199*d)/320+s);
        g.mem.img[32199]=(byte)((199*d)/320+s);
        g.mem.img[28360]=(byte)((200*d)/320+s);
        g.mem.img[28680]=(byte)((200*d)/320+s);
        g.mem.img[29000]=(byte)((200*d)/320+s);
        g.mem.img[29320]=(byte)((200*d)/320+s);
        g.mem.img[29640]=(byte)((200*d)/320+s);
        g.mem.img[29960]=(byte)((200*d)/320+s);
        g.mem.img[31880]=(byte)((200*d)/320+s);
        g.mem.img[32200]=(byte)((200*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[28681]=(byte)((201*d)/320+s);
        g.mem.img[29001]=(byte)((201*d)/320+s);
        g.mem.img[29321]=(byte)((201*d)/320+s);
        g.mem.img[31881]=(byte)((201*d)/320+s);
        g.mem.img[32201]=(byte)((201*d)/320+s);
        g.mem.img[31882]=(byte)((202*d)/320+s);
        g.mem.img[32202]=(byte)((202*d)/320+s);
        Kernel.sleep(sleepLength);
        g.mem.img[31883]=(byte)((203*d)/320+s);
        g.mem.img[32203]=(byte)((203*d)/320+s);
        Kernel.sleep(sleepLength);
    }
}
