package server.client.viber;

import org.apache.log4j.Logger;
import server.client.rest.client.ApacheRestClient;
import server.client.rest.model.RequestModel;

import java.util.Random;

import static util.ApplicationPropertyUtil.applicationPropertyGet;

public class BaseViberRestClient {
    private final Logger LOGGER = Logger.getLogger(this.getClass());
    private static final Random RANDOM = new Random();
    private final static String[] SMILE_COLLECTION =
            ("smiley)(sad)(wink)(angry)(inlove)(yummi)(laugh)(surprised)(moa)(happy)(cry)(sick)(shy)(teeth)(tongue)" +
                    "(confused)(crazy)(flirt)(mad)(money)(depressed)(scream)(nerd)(not_sure)(cool)(eek)(exhausted)" +
                    "(mwah)(happycry)(huh)(dizzy)(dead)(straight)(yo)(wtf)(weak)(what)(wink2)(oh)(ohno)(upset)(ugh)" +
                    "(teary)(singing)(silly)(eyeroll)(crying)(hmm)(mischievous)(meh)(ninja)(spiderman)(batman)(devil)" +
                    "(angel)(heart)(heart_break)(purple_heart)(yellow_heart)(blue_heart)(heart_lock)(arrow_heart)" +
                    "(2_hearts)(black_heart)(orange_heart)(unlike)(like)(V)(fu)(clap)(prayer_hands)(fist)(waving)" +
                    "(pointer)(rockon)(footsteps)(muscle)(thinking)(zzz)(!)(ring)(crown)(trophy)(diamond)(Q)($)(hammer)" +
                    "(wrench)(key)(lock)(guitar)(trumpet)(tape)(TV)(video)(drum)(speaker)(music)(microphone)(bell)(chick)" +
                    "(kangaroo)(ladybug)(sheep)(koala)(monkey)(panda)(turtle)(bunny)(dragonfly)(dog)(cat)(bat)(bee)(fly)" +
                    "(squirrel)(snake)(snail)(goldfish)(shark)(fox)(porcupine)(penguin)(owl)(pig)(octopus)(dinosaur)(paw)" +
                    "(poo)(cap)(bowtie)(tiara)(santa_hat)(partyhat)(fidora)(cactus)(clover)(sprout)(palmtree)" +
                    "(christmas_tree)(bouquet)(blue_flower)(sunflower)(flower)(mapleleaf)(sun)(moon)(cloud)(rain)" +
                    "(droplet)(full_moon)(earth)(rainbow)(lightening)(tornado)(shooting_star)(star)(umbrella)(snowman)" +
                    "(snowflake)(fan)(sunglasses)(bikini)(flipflop)(relax)(phone)(nobattery)(battery)(time)(camera)(meds)" +
                    "(termometer)(syringe)(knife)(telephone)(ruler)(scissor)(paperclip)(pencil)(magnify)(angrymark)(weight)" +
                    "(letter)(book)(glasses)(boxing)(light_bulb)(lantern)(fire)(torch)(skull)(gift)(kiss)(cigarette)(bomb)" +
                    "(ghost)(robot)(alien)(golf)(golfball)(baseball)(basketball)(soccer)(tennis)(football)(8ball)" +
                    "(beachball)(iceskate)(target)(racing_flag)(console)(dice)(cards)(balloon2)(balloon1)(chicken)" +
                    "(burger)(pizza)(noodles)(sushi1)(bacon)(hotdog)(egg)(donut)(sushi2)(hotsauce)(ice_cream)(popsicle)" +
                    "(cupcake)(croissant)(popcorn)(cake_slice)(cookie)(lollipop)(chocolate)(cake)(cherry)(banana)" +
                    "(watermelon)(strawberry)(pineapple)(apple)(peach)(lemon)(grapes)(pea)(eggplant)(corn)(mushroom)" +
                    "(coffee)(champagne)(martini)(wine)(beer)(soda)(cocktail)(cutlery)(party_popper)(confetti_ball)" +
                    "(car)(airplane)(bicycle)(policecar)(ambulance)(taxi)(trafficlight)(stop_sign)(ufo)(rocket)(run)" +
                    "(paintbrush)(color_palette)(down_graph)(up_graph)(shrug)(crystal_ball)(checkmark)(tablet)(baby_bottle)" +
                    "(anchor)(spiral)(over18)(do_not_enter)(handicap)(first_aid)(moneybag)(eyes").split("\\)\\(");
    private static final BaseViberRestClient INSTANCE = new BaseViberRestClient();
    private ApacheRestClient apacheRestClient = new ApacheRestClient();

    String randomSmiles() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            res.append("(").append(SMILE_COLLECTION[RANDOM.nextInt(SMILE_COLLECTION.length - 1)]).append(")");
        }
        return res.toString();
    }

    void sendViberMessage(String text) {
        RequestModel request = new RequestModel();
        request.setAllLog(true);
        request.setBody(text);
        request.setHost("chatapi.viber.com");
        request.setBodyEncoding("UTF-8");
        request.setProtocol("https://");
        request.setPath("/pa/send_message");
        request.setMethod("POST");
        String type = "application/json";
        request.addHeader("Content-Type", type);
        request.addHeader("X-Viber-Auth-Token", applicationPropertyGet("viber.token"));
        LOGGER.info(apacheRestClient.call(request).getBody());
    }

}
