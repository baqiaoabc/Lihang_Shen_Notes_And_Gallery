package entity.eventFactory;

public class FactoryProducer {

    public static AbstractEventFactory getFactory(int type){
        if(type == 0){
            return new NonSpeakerEventFactory();
        }
        else if (type == 1){
            return new OneSpeakerEventFactory();
        }
        else if (type == 2) {
            return new MultiSpeakerEventFactory();
        }
        return null;
    }
}

