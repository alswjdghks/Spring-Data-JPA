package study.data_jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username,int age);
//    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username")String username,@Param("age")int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.data_jpa.dto.MemberDto(m.id,m.username,t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names")Collection<String> names);

    List<Member> findListByUsername(String name);
    Member findMemberByUsername(String name);
    Optional<Member> findOptionalByUsername(String name);

//    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m.username) from Member m")
//    count 쿼리를 분리할 수 있음. <- Page 에서 카운트 쿼리는 join 진행해서 복잡한 쿼리가 생성됨
//    데이터는 left join, 카운트는 left join 안해도됨
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true) // 벌크성 수정, 삭제 쿼리는 @Modifying 어노테이션을 사용해야함. 사용하지 않으면 예외 발생
//  벌크 연산은 영속성 컨텍스트를 무시하고 실행하기 때문에, 영속성 컨텍스트에 있는 엔티티 상태와 DB에 엔티티 상태가 달라질 수 있다.
//  -> clearAutomatically = true 로 설정 (영속성 컨텍스트 초기화)
    @Query(value = "update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);
}
