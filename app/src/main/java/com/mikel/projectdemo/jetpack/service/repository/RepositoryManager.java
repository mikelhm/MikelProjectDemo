package com.mikel.projectdemo.jetpack.service.repository;

import android.content.Context;

import com.mikel.projectdemo.jetpack.service.db.database.AppDatabase;
import com.mikel.projectdemo.jetpack.service.model.Poetry;

import java.util.ArrayList;
import java.util.List;

public class RepositoryManager {

    public static RepositoryManager mInstance;

    public static String[] CACHE_TITLE = {
            "多丽·咏白菊",
            "山居秋暝",
            "游山西村"};
    public static String[] CACHE_CONTENT ={
            "小楼寒，夜长帘幕低垂。恨萧萧、无情风雨，夜来揉损琼肌。也不似、贵妃醉脸，也不似、孙寿愁眉。韩令偷香，徐娘傅粉，莫将比拟未新奇。细看取、屈平陶令，风韵正相宜。微风起，清芬酝藉，不减酴醿。 \n" + "渐秋阑、雪清玉瘦，向人无限依依。似愁凝、汉皋解佩，似泪洒、纨扇题诗。朗月清风，浓烟暗雨，天教憔悴度芳姿。纵爱惜、不知从此，留得几多时。人情好，何须更忆，泽畔东篱。",
            "空山新雨后，天气晚来秋。\n" + "明月松间照，清泉石上流。\n" + "竹喧归浣女，莲动下渔舟。\n" + "随意春芳歇，王孙自可留。",
            "莫笑农家腊酒浑，丰年留客足鸡豚。\n" + "山重水复疑无路，柳暗花明又一村。\n" + "箫鼓追随春社近，衣冠简朴古风存。\n" + "从今若许闲乘月，拄杖无时夜叩门。"};
    public static String[] CACHE_AUTHOR ={
            "李清照",
            "王维",
            "陆游"};
    public static RepositoryManager getInstance() {
        if (mInstance == null) {
            synchronized (RepositoryManager.class) {
                if (mInstance == null) {
                    mInstance = new RepositoryManager();
                }
            }
        }
        return mInstance;
    }

    public RepositoryManager(){}

    /**
     * 构造本地测试数据
     * @param context
     */
    public void buildLocalTestData(Context context) {
        AppDatabase.getInstance(context).poetryDao().deleteAll();
        List<Poetry> poetryList = new ArrayList<>();
        Poetry poetry1 = new Poetry();
        poetry1.poetry_id = 1;
        poetry1.title = "1.将进酒";
        poetry1.content = "君不见黄河之水天上来，奔流到海不复回。\n" +
                "君不见高堂明镜悲白发，朝如青丝暮成雪。\n" +
                "人生得意须尽欢，莫使金樽空对月。\n" +
                "天生我材必有用，千金散尽还复来。\n" +
                "烹羊宰牛且为乐，会须一饮三百杯。\n" +
                "岑夫子，丹丘生，将进酒，杯莫停。\n" +
                "与君歌一曲，请君为我倾耳听。(倾耳听 一作：侧耳听)\n" +
                "钟鼓馔玉不足贵，但愿长醉不愿醒。(不足贵 一作：何足贵；不愿醒 一作：不复醒)\n" +
                "古来圣贤皆寂寞，惟有饮者留其名。(古来 一作：自古；惟 通：唯)\n" +
                "陈王昔时宴平乐，斗酒十千恣欢谑。\n" +
                "主人何为言少钱，径须沽取对君酌。\n" +
                "五花马、千金裘，呼儿将出换美酒，与尔同销万古愁。";
        poetry1.authors = "李白";
        poetryList.add(poetry1);

        Poetry poetry2 = new Poetry();
        poetry2.poetry_id = 2;
        poetry2.title = "2.蜀道难";
        poetry2.content = "噫吁嚱，危乎高哉！\n" +
                "蜀道之难，难于上青天！\n" +
                "蚕丛及鱼凫，开国何茫然！\n" +
                "尔来四万八千岁，不与秦塞通人烟。\n" +
                "西当太白有鸟道，可以横绝峨眉巅。\n" +
                "地崩山摧壮士死，然后天梯石栈相钩连。\n" +
                "上有六龙回日之高标，下有冲波逆折之回川。\n" +
                "黄鹤之飞尚不得过，猿猱欲度愁攀援。\n" +
                "青泥何盘盘，百步九折萦岩峦。\n" +
                "扪参历井仰胁息，以手抚膺坐长叹。\n" +
                "\n" +
                "问君西游何时还？畏途巉岩不可攀。\n" +
                "但见悲鸟号古木，雄飞雌从绕林间。\n" +
                "又闻子规啼夜月，愁空山。\n" +
                "蜀道之难，难于上青天，使人听此凋朱颜！\n" +
                "连峰去天不盈尺，枯松倒挂倚绝壁。\n" +
                "飞湍瀑流争喧豗，砯崖转石万壑雷。\n" +
                "其险也如此，嗟尔远道之人胡为乎来哉！(也如此 一作：也若此)\n" +
                "\n" +
                "剑阁峥嵘而崔嵬，一夫当关，万夫莫开。\n" +
                "所守或匪亲，化为狼与豺。\n" +
                "朝避猛虎，夕避长蛇，磨牙吮血，杀人如麻。\n" +
                "锦城虽云乐，不如早还家。\n" +
                "蜀道之难，难于上青天，侧身西望长咨嗟！";
        poetry2.authors = "李白";
        poetryList.add(poetry2);

        Poetry poetry3 = new Poetry();
        poetry3.poetry_id = 3;
        poetry3.title = "3.宣州谢脁楼饯别校书叔云";
        poetry3.content = "弃我去者，昨日之日不可留；\n" +
                "乱我心者，今日之日多烦忧。\n" +
                "长风万里送秋雁，对此可以酣高楼。\n" +
                "蓬莱文章建安骨，中间小谢又清发。\n" +
                "俱怀逸兴壮思飞，欲上青天揽明月。(揽 一作：览；明月 一作：日月)\n" +
                "抽刀断水水更流，举杯销愁愁更愁。(销愁 一作：消愁)\n" +
                "人生在世不称意，明朝散发弄扁舟。";
        poetry3.authors = "李白";
        poetryList.add(poetry3);

        Poetry poetry4 = new Poetry();
        poetry4.poetry_id = 4;
        poetry4.title = "4.侠客行";
        poetry4.content = "赵客缦胡缨，吴钩霜雪明。\n" +
                "银鞍照白马，飒沓如流星。\n" +
                "十步杀一人，千里不留行。\n" +
                "事了拂衣去，深藏身与名。\n" +
                "闲过信陵饮，脱剑膝前横。\n" +
                "将炙啖朱亥，持觞劝侯嬴。\n" +
                "三杯吐然诺，五岳倒为轻。\n" +
                "眼花耳热后，意气素霓生。\n" +
                "救赵挥金槌，邯郸先震惊。\n" +
                "千秋二壮士，烜赫大梁城。\n" +
                "纵死侠骨香，不惭世上英。\n" +
                "谁能书阁下，白首太玄经。";
        poetry4.authors = "李白";
        poetryList.add(poetry4);

        Poetry poetry5 = new Poetry();
        poetry5.poetry_id = 5;
        poetry5.title = "5.把酒问月·故人贾淳令予问之";
        poetry5.content = "青天有月来几时？我今停杯一问之。\n" +
                "人攀明月不可得，月行却与人相随。\n" +
                "皎如飞镜临丹阙，绿烟灭尽清辉发。\n" +
                "但见宵从海上来，宁知晓向云间没。\n" +
                "白兔捣药秋复春，嫦娥孤栖与谁邻？\n" +
                "今人不见古时月，今月曾经照古人。\n" +
                "古人今人若流水，共看明月皆如此。\n" +
                "唯愿当歌对酒时，月光长照金樽里。";
        poetry5.authors = "李白";
        poetryList.add(poetry5);

        Poetry poetry6 = new Poetry();
        poetry6.poetry_id = 6;
        poetry6.title = "6.茅屋为秋风所破歌";
        poetry6.content = "八月秋高风怒号，卷我屋上三重茅。茅飞渡江洒江郊，高者挂罥长林梢，下者飘转沉塘坳。\n" +
                "南村群童欺我老无力，忍能对面为盗贼。公然抱茅入竹去，唇焦口燥呼不得，归来倚杖自叹息。\n" +
                "俄顷风定云墨色，秋天漠漠向昏黑。布衾多年冷似铁，娇儿恶卧踏里裂。床头屋漏无干处，雨脚如麻未断绝。自经丧乱少睡眠，长夜沾湿何由彻！\n" +
                "安得广厦千万间，大庇天下寒士俱欢颜！风雨不动安如山。呜呼！何时眼前突兀见此屋，吾庐独破受冻死亦足！(亦足 一作：意足)";
        poetry6.authors = "杜甫";
        poetryList.add(poetry6);

        Poetry poetry7 = new Poetry();
        poetry7.poetry_id = 7;
        poetry7.title = "7.石壕吏";
        poetry7.content = "暮投石壕村，有吏夜捉人。\n" +
                "老翁逾墙走，老妇出门看。(出门看 一作：出看门)\n" +
                "吏呼一何怒！妇啼一何苦！\n" +
                "听妇前致词：三男邺城戍。\n" +
                "一男附书至，二男新战死。\n" +
                "存者且偷生，死者长已矣！\n" +
                "室中更无人，惟有乳下孙。\n" +
                "有孙母未去，出入无完裙。\n" +
                "老妪力虽衰，请从吏夜归。\n" +
                "急应河阳役，犹得备晨炊。\n" +
                "夜久语声绝，如闻泣幽咽。\n" +
                "天明登前途，独与老翁别。";
        poetry7.authors = "杜甫";
        poetryList.add(poetry7);

        Poetry poetry8 = new Poetry();
        poetry8.poetry_id = 8;
        poetry8.title = "8.赠卫八处士";
        poetry8.content = "人生不相见，动如参与商。\n" +
                "今夕复何夕，共此灯烛光。\n" +
                "少壮能几时，鬓发各已苍。\n" +
                "访旧半为鬼，惊呼热中肠。\n" +
                "焉知二十载，重上君子堂。\n" +
                "昔别君未婚，儿女忽成行。\n" +
                "怡然敬父执，问我来何方。\n" +
                "问答乃未已，驱儿罗酒浆。(乃未已 一作：未及已；驱儿 一作：儿女)\n" +
                "夜雨剪春韭，新炊间黄粱。\n" +
                "主称会面难，一举累十觞。\n" +
                "十觞亦不醉，感子故意长。\n" +
                "明日隔山岳，世事两茫茫。";
        poetry8.authors = "杜甫";
        poetryList.add(poetry8);

        Poetry poetry9 = new Poetry();
        poetry9.poetry_id = 9;
        poetry9.title = "9.兵车行";
        poetry9.content = "车辚辚，马萧萧，行人弓箭各在腰。\n" +
                "耶娘妻子走相送，尘埃不见咸阳桥。(耶娘 一作：爷娘)\n" +
                "牵衣顿足拦道哭，哭声直上干云霄。\n" +
                "道旁过者问行人，行人但云点行频。\n" +
                "或从十五北防河，便至四十西营田。\n" +
                "去时里正与裹头，归来头白还戍边。\n" +
                "边庭流血成海水，武皇开边意未已。\n" +
                "君不闻，汉家山东二百州，千村万落生荆杞。\n" +
                "纵有健妇把锄犁，禾生陇亩无东西。\n" +
                "况复秦兵耐苦战，被驱不异犬与鸡。\n" +
                "长者虽有问，役夫敢申恨？\n" +
                "且如今年冬，未休关西卒。\n" +
                "县官急索租，租税从何出？\n" +
                "信知生男恶，反是生女好。\n" +
                "生女犹得嫁比邻，生男埋没随百草。\n" +
                "君不见，青海头，古来白骨无人收。\n" +
                "新鬼烦冤旧鬼哭，天阴雨湿声啾啾！";
        poetry9.authors = "杜甫";
        poetryList.add(poetry9);

        Poetry poetry10 = new Poetry();
        poetry10.poetry_id = 10;
        poetry10.title = "10.饮中八仙歌";
        poetry10.content = "知章骑马似乘船，眼花落井水底眠。\n" +
                "汝阳三斗始朝天，道逢麴车口流涎，恨不移封向酒泉。\n" +
                "左相日兴费万钱，饮如长鲸吸百川，衔杯乐圣称避贤。\n" +
                "宗之潇洒美少年，举觞白眼望青天，皎如玉树临风前。\n" +
                "苏晋长斋绣佛前，醉中往往爱逃禅。\n" +
                "李白斗酒诗百篇，长安市上酒家眠，(斗酒 一作：一斗)\n" +
                "天子呼来不上船，自称臣是酒中仙。\n" +
                "张旭三杯草圣传，脱帽露顶王公前，挥毫落纸如云烟。\n" +
                "焦遂五斗方卓然，高谈雄辩惊四筵。";
        poetry10.authors = "杜甫";
        poetryList.add(poetry10);

        Poetry poetry11 = new Poetry();
        poetry11.poetry_id = 11;
        poetry11.title = "11.水调歌头·明月几时有";
        poetry11.content = "明月几时有？把酒问青天。不知天上宫阙，今夕是何年。我欲乘风归去，又恐琼楼玉宇，高处不胜寒。起舞弄清影，何似在人间。(何似 一作：何时；又恐 一作：惟 / 唯恐)\n" +
                "转朱阁，低绮户，照无眠。不应有恨，何事长向别时圆？人有悲欢离合，月有阴晴圆缺，此事古难全。但愿人长久，千里共婵娟。(长向 一作：偏向)";
        poetry11.authors = "苏轼";
        poetryList.add(poetry11);


        Poetry poetry12 = new Poetry();
        poetry12.poetry_id = 12;
        poetry12.title = "12.念奴娇·赤壁怀古";
        poetry12.content = "大江东去，浪淘尽，千古风流人物。\n" +
                "故垒西边，人道是，三国周郎赤壁。\n" +
                "乱石穿空，惊涛拍岸，卷起千堆雪。(穿空 一作：崩云)\n" +
                "江山如画，一时多少豪杰。\n" +
                "\n" +
                "遥想公瑾当年，小乔初嫁了，雄姿英发。\n" +
                "羽扇纶巾，谈笑间，樯橹灰飞烟灭。(樯橹 一作：强虏)\n" +
                "故国神游，多情应笑我，早生华发。\n" +
                "人生如梦，一尊还酹江月。(人生 一作：人间；尊 同：樽)";
        poetry12.authors = "苏轼";
        poetryList.add(poetry12);

        Poetry poetry13 = new Poetry();
        poetry13.poetry_id = 13;
        poetry13.title = "13.江城子·乙卯正月二十日夜记梦";
        poetry13.content = "十年生死两茫茫，不思量，自难忘。千里孤坟，无处话凄凉。纵使相逢应不识，尘满面，鬓如霜。\n" +
                "夜来幽梦忽还乡，小轩窗，正梳妆。相顾无言，惟有泪千行。料得年年肠断处，明月夜，短松冈。(肠断 一作：断肠)";
        poetry13.authors = "苏轼";
        poetryList.add(poetry13);

        Poetry poetry14 = new Poetry();
        poetry14.poetry_id = 14;
        poetry14.title = "14.江城子·密州出猎";
        poetry14.content = "老夫聊发少年狂，左牵黄，右擎苍，锦帽貂裘，千骑卷平冈。为报倾城随太守，亲射虎，看孙郎。\n" +
                "酒酣胸胆尚开张。鬓微霜，又何妨！持节云中，何日遣冯唐？会挽雕弓如满月，西北望，射天狼。";
        poetry14.authors = "苏轼";
        poetryList.add(poetry14);


        Poetry poetry15 = new Poetry();
        poetry15.poetry_id = 15;
        poetry15.title = "15.赤壁赋";
        poetry15.content = "壬戌之秋，七月既望，苏子与客泛舟，游于赤壁之下。清风徐来，水波不兴。举酒属客，诵明月之诗，歌窈窕之章。少焉，月出于东山之上，徘徊于斗牛之间。白露横江，水光接天。纵一苇之所如，凌万顷之茫然。浩浩乎如冯虚御风，而不知其所止；飘飘乎如遗世独立，羽化而登仙。(冯 通：凭)\n" +
                "\n" +
                "　　于是饮酒乐甚，扣舷而歌之。歌曰：“桂棹兮兰桨，击空明兮溯流光。渺渺兮予怀，望美人兮天一方。”客有吹洞箫者，倚歌而和之。其声呜呜然，如怨如慕，如泣如诉；余音袅袅，不绝如缕。舞幽壑之潜蛟，泣孤舟之嫠妇。\n" +
                "\n" +
                "　　苏子愀然，正襟危坐，而问客曰：“何为其然也？”客曰：“‘月明星稀，乌鹊南飞。’此非曹孟德之诗乎？西望夏口，东望武昌，山川相缪，郁乎苍苍，此非孟德之困于周郎者乎？方其破荆州，下江陵，顺流而东也，舳舻千里，旌旗蔽空，酾酒临江，横槊赋诗，固一世之雄也，而今安在哉？况吾与子渔樵于江渚之上，侣鱼虾而友麋鹿，驾一叶之扁舟，举匏樽以相属。寄蜉蝣于天地，渺沧海之一粟。哀吾生之须臾，羡长江之无穷。挟飞仙以遨游，抱明月而长终。知不可乎骤得，托遗响于悲风。”\n" +
                "\n" +
                "　　苏子曰：“客亦知夫水与月乎？逝者如斯，而未尝往也；盈虚者如彼，而卒莫消长也。盖将自其变者而观之，则天地曾不能以一瞬；自其不变者而观之，则物与我皆无尽也，而又何羡乎！且夫天地之间，物各有主，苟非吾之所有，虽一毫而莫取。惟江上之清风，与山间之明月，耳得之而为声，目遇之而成色，取之无禁，用之不竭。是造物者之无尽藏也，而吾与子之所共适。”(共适 一作：共食)\n" +
                "\n" +
                "　　客喜而笑，洗盏更酌。肴核既尽，杯盘狼籍。相与枕藉乎舟中，不知东方之既白。";
        poetry15.authors = "苏轼";
        poetryList.add(poetry15);

        AppDatabase.getInstance(context).poetryDao().insertAll(poetryList);
    }
}
