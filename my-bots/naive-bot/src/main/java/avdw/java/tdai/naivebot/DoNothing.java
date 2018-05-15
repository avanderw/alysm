package avdw.java.tdai.naivebot;

class DoNothing extends ABehaviourTree {
    private BotResponse botResponse;

    public DoNothing(BotResponse botResponse) {
        super();

        this.botResponse = botResponse;
    }

    @Override
    public Status process() {
        botResponse.doNothing();

        return Status.Success;
    }
}
