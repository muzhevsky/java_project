package muzhevsky.org.core.enums;

public enum EstimateStatus {
    ONUSERCONSIDERMENT("ожидает оценки пользователя"),
    REJECTED("отклонена пользователем"),
    ACCEPTED("принята пользователем");
    private String value;
    EstimateStatus(String value){
        this.value = value;
    }

    public String get(){
        return value;
    }
}
