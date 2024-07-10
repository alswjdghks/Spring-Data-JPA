package study.data_jpa.repository;

public interface NestedClosedProjections {

    String getUsername(); // 최적화
    TeamInfo getTeam(); // 두 번째부터는 엔티티를 다 가져옴

    interface TeamInfo{
        String getName();
    }
}
