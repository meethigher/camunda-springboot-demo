package top.meethigher.external;

public class ExternalApp {
    public static void main(String[] args) {
        new TimeoutReminder().run();
        new ScheduledFixedReminder().run();
    }
}
