package com.usg.book.adapter.out.persistence;

import com.usg.book.IntegrationExternalApiMockingTestSupporter;
import com.usg.book.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class MemberPersistenceAdapterIntegrationTest extends IntegrationExternalApiMockingTestSupporter {

    @Autowired
    private MemberPersistenceAdapter memberPersistenceAdapter;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    @DisplayName("레디스를 이용해 회원 정보를 저장한다.")
    void saveMemberTest() {
        // given
        Member member = createMember();

        // when
        memberPersistenceAdapter.saveMember(member);

        // then
        String nickname = redisTemplate.opsForValue().get("member : " + member.getEmail());
        Assertions.assertThat(nickname).isEqualTo(member.getNickname());
    }

    private Member createMember() {
        return Member.builder()
                .email("email")
                .nickname("nickname")
                .build();
    }
}
